package in.ankushs.dbip.repository;

import com.google.common.net.InetAddresses;
import in.ankushs.dbip.api.GeoEntity;
import in.ankushs.dbip.model.GeoAttributes;
import in.ankushs.dbip.utils.IPUtils;
import in.ankushs.dbip.utils.Json;
import in.ankushs.dbip.utils.PreConditions;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.redis.client.RedisAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.util.Collections;
import java.util.List;

public class RedisDbIpRepositoryImpl implements AsyncDbIpRepository {

    private static final String SORTED_SET = "geoip";
    private static final Logger logger = LoggerFactory.getLogger(RedisDbIpRepositoryImpl.class);
    private final RedisAPI redis;

    public RedisDbIpRepositoryImpl(final RedisAPI redis) {
        this.redis = redis;
    }

    @Override
    public Future<GeoEntity> get(final InetAddress inetAddress) {
        PreConditions.checkNull(inetAddress, "inetAddress must not be null");
        String ip = "";
        if(inetAddress instanceof Inet4Address) {
            final Integer startIpNum = InetAddresses.coerceToInteger(inetAddress);
            ip = String.valueOf(startIpNum);
        }
        else {
            final BigInteger startIpBigInt = IPUtils.ipv6ToBigInteger(inetAddress);
            ip = startIpBigInt.toString();
        }
        final String zRangeByScoreCmd = String.format("ZRANGEBYSCORE %s %s +inf LIMIT 0 1", SORTED_SET, ip);
        final List<String> zRangeCmds = Collections.singletonList(zRangeByScoreCmd);
        final Promise<GeoEntity> promise = Promise.promise();

        redis.zrangebyscore(zRangeCmds, getResult -> {
           if(getResult.succeeded()) {
               final String result = getResult.result().toString();
               final GeoEntity geoEntity = Json.toObject(result, GeoEntity.class);
               promise.complete(geoEntity);
           }
           else {
               logger.error("", getResult.cause());
           }
        });

        return promise.future();
    }

    @Override
    public Future<Void> save(final GeoAttributes geoAttributes) {
        PreConditions.checkNull(geoAttributes, "geoAttributes must not be null");
        final InetAddress startInetAddress = geoAttributes.getStartInetAddress();
        final InetAddress endInetAddress = geoAttributes.getEndInetAddress();
        final GeoEntity geoEntity = geoAttributes.getGeoEntity();
        final Promise<Void> promise = Promise.promise();

        try {
            final String json = Json.encode(geoEntity);
            String ip = "";
            if(startInetAddress instanceof Inet6Address
                    && endInetAddress instanceof Inet6Address)
            {
                final BigInteger startIpBigInt = IPUtils.ipv6ToBigInteger(startInetAddress);
                ip = startIpBigInt.toString();
            }
            else if (startInetAddress instanceof Inet4Address
                    && endInetAddress instanceof Inet4Address)
            {
                final Integer startIpNum = InetAddresses.coerceToInteger(startInetAddress);
                ip = String.valueOf(startIpNum);
            }
            final String insertCmd = String.format("ZADD %s %s %s", SORTED_SET, ip, json);
            final List<String> zaddCmds = Collections.singletonList(insertCmd);
            redis.zadd(zaddCmds,  insert -> {
                if(insert.succeeded()) {
                    logger.debug("Successfully inserted cmd {}", insertCmd);
                    promise.complete();
                }
                else {
                    logger.error("", insert.cause());
                    promise.fail(insert.cause());
                }
            });

        }
        catch (final Exception e) {
            logger.error("", e);
        }
        return promise.future();
    }

    public static void main(String[] args) {
        System.out.println(new BigInteger("1201212121212121212").toString());
    }
}
