package in.ankushs.dbip.repository;

import com.google.common.net.InetAddresses;
import in.ankushs.dbip.api.CityProvinceCountry;
import in.ankushs.dbip.api.GeoEntity;
import in.ankushs.dbip.api.ProvinceCountry;
import in.ankushs.dbip.model.GeoAttributes;
import in.ankushs.dbip.utils.IPUtils;
import in.ankushs.dbip.utils.Json;
import in.ankushs.dbip.utils.PreConditions;
import in.ankushs.dbip.utils.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RedisDbIpRepositoryImpl implements DbIpRepository {

    private static final String SORTED_SET = "geoip";
    private static final Logger logger = LoggerFactory.getLogger(RedisDbIpRepositoryImpl.class);
    private final JedisPool jedisPool;
    private final boolean fullLoadingEnabled;

    private static Set<String> countries = new HashSet<>();
    private static Set<CityProvinceCountry> citiesCountryProvince = new HashSet<>();
    private static Set<ProvinceCountry> provinceCountries = new HashSet<>();


    public RedisDbIpRepositoryImpl(final JedisPool jedisPool,
                                   final boolean fullLoadingEnabled) {
        this.jedisPool = jedisPool;
        this.fullLoadingEnabled = fullLoadingEnabled;
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
        try(final Jedis jedis = jedisPool.getResource()) {
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
            }
        }
        return null;
    }

    @Override
    public void save(final GeoAttributes geoAttributes) {
        PreConditions.checkNull(geoAttributes, "geoAttributes must not be null");
        final InetAddress startInetAddress = geoAttributes.getStartInetAddress();
        final InetAddress endInetAddress = geoAttributes.getEndInetAddress();
        final GeoEntity geoEntity = geoAttributes.getGeoEntity();
        final String city = geoEntity.getCity();
        final String country = geoEntity.getCountry();
        final String province = geoEntity.getProvince();

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

            if(Strings.hasText(country)) {
                countries.add(country);
            }

            if(Strings.hasText(country) && Strings.hasText(province)) {
                provinceCountries.add(new ProvinceCountry(province, country));
            }

            if(Strings.hasText(country) && Strings.hasText(province) && Strings.hasText(city)) {
                citiesCountryProvince.add(new CityProvinceCountry(city, province, country));
            }


            if(fullLoadingEnabled) {
                try(final Jedis jedis = jedisPool.getResource()) {
                   jedis.zadd(SORTED_SET, ip, json);
                }
            }

        }
        catch (final Exception e) {
            logger.error("", e);
        }
    }

    @Override
    public boolean fullLoadingEnabled() {
        return false;
    }

    public Set<String> countries() {
        return countries;
    }

    public Set<CityProvinceCountry> citiesCountryProvince() {
        return citiesCountryProvince;
    }

    public Set<ProvinceCountry> provinceCountries() {
        return provinceCountries;
    }


    public static void main(String[] args) {
        System.out.println(new BigInteger("1201212121212121212").intValue());
    }
}
