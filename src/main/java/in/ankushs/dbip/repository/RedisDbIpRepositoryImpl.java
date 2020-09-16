package in.ankushs.dbip.repository;

import com.google.common.net.InetAddresses;
import in.ankushs.dbip.api.GeoEntity;
import in.ankushs.dbip.model.GeoAttributes;
import in.ankushs.dbip.utils.IPUtils;
import in.ankushs.dbip.utils.Json;
import in.ankushs.dbip.utils.PreConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RedisDbIpRepositoryImpl implements DbIpRepository {

    private static final String SORTED_SET = "geoip";
    private static final Logger logger = LoggerFactory.getLogger(RedisDbIpRepositoryImpl.class);
    private final Jedis jedis;

    private static final ExecutorService exec = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() / 2);

    public RedisDbIpRepositoryImpl(final Jedis jedis) {
        this.jedis = jedis;
    }

    @Override
    public GeoEntity get(final InetAddress inetAddress) {
        PreConditions.checkNull(inetAddress, "inetAddress must not be null");
        long ip = 0L;
        if(inetAddress instanceof Inet4Address) {
            final Integer startIpNum = InetAddresses.coerceToInteger(inetAddress);
            ip = startIpNum;
        }
        else {
            final BigInteger startIpBigInt = IPUtils.ipv6ToBigInteger(inetAddress);
            ip = startIpBigInt.intValue();
        }
        //        String key, double max, double min, int offset, int count
        final Set<String> jsons = jedis.zrangeByScore(SORTED_SET, ip, Double.MAX_VALUE, 0, 1);
        if(jsons != null && !jsons.isEmpty()) {
            final Map<Object, Object> member = Json.toObject(jsons.stream().findAny().get(), Map.class);
            final Map<String, String> result = (Map<String, String>) member.entrySet().stream().findAny().get().getValue();
            return new GeoEntity.Builder()
                    .withCity(result.get("city"))
                    .withCountry(result.get("country"))
                    .withIsp(result.get("isp"))
                    .withCountryCode(result.get("countryCode"))
                    .withProvince(result.get("province"))
                    .build();
//            {city=Kaohsiung, country='Taiwan, Province Of China', province=Kaohsiung, countryCode=TW, isp=HINET}
        }
        return null;
    }

    @Override
    public void save(final GeoAttributes geoAttributes) {
        PreConditions.checkNull(geoAttributes, "geoAttributes must not be null");
        final InetAddress startInetAddress = geoAttributes.getStartInetAddress();
        final InetAddress endInetAddress = geoAttributes.getEndInetAddress();
        final GeoEntity geoEntity = geoAttributes.getGeoEntity();

        try {
            final UUID uuid = UUID.randomUUID();
            final Map<UUID, GeoEntity> member = Collections.singletonMap(uuid, geoEntity);
            final String json = Json.encode(member);

            long ip = 0L;
            if(startInetAddress instanceof Inet6Address
                    && endInetAddress instanceof Inet6Address)
            {
                final BigInteger startIpBigInt = IPUtils.ipv6ToBigInteger(endInetAddress);
                ip = startIpBigInt.longValue();
            }
            else if (startInetAddress instanceof Inet4Address
                    && endInetAddress instanceof Inet4Address)
            {
                final Integer startIpNum = InetAddresses.coerceToInteger(endInetAddress);
                ip = startIpNum;
            }

            jedis.zadd(SORTED_SET, ip, json);

        }
        catch (final Exception e) {
            logger.error("", e);
        }
    }

    public static void main(String[] args) {
        System.out.println(new BigInteger("1201212121212121212").intValue());
    }
}
