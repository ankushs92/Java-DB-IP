package in.ankushs.dbip.lookup;

import in.ankushs.dbip.api.GeoEntity;
import in.ankushs.dbip.repository.DbIpRepository;
import in.ankushs.dbip.repository.JavaMapDbIpRepositoryImpl;
import in.ankushs.dbip.repository.RedisDbIpRepositoryImpl;
import in.ankushs.dbip.utils.PreConditions;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.redis.client.RedisAPI;
import redis.clients.jedis.Jedis;

import java.net.InetAddress;

/**
 * 
 * Singleton class that resolves ip to Location info.
 * @author Ankush Sharma
 */
public final class GeoEntityLookupServiceImpl implements GeoEntityLookupService {
	
	private static final String UNKNOWN = "Unknown";
	
	private final DbIpRepository repository = JavaMapDbIpRepositoryImpl.getInstance();
	
	private static GeoEntityLookupServiceImpl instance = null;

	private final Jedis jedis;
	private final RedisDbIpRepositoryImpl redisDbIpRepository;

	public GeoEntityLookupServiceImpl() {
		this.jedis = null;
		this.redisDbIpRepository = null;
	}

	public GeoEntityLookupServiceImpl(final Jedis jedis) {
		this.jedis = jedis;
		this.redisDbIpRepository = new RedisDbIpRepositoryImpl(jedis);
	}


	@Override
	public GeoEntity lookup(final InetAddress inetAddress) {
		PreConditions.checkNull(inetAddress, "inetAddress cannot be null ");
		GeoEntity geoEntity = null;
		if(jedis != null) {
			geoEntity = redisDbIpRepository.get(inetAddress);
		}
		else {
			geoEntity = repository.get(inetAddress);
		}
		if( geoEntity == null ){
			geoEntity = new GeoEntity
								.Builder()
								.withCity(UNKNOWN)
								.withCountry(UNKNOWN)
								.withCountryCode(UNKNOWN)
								.withProvince(UNKNOWN)
								 .withIsp(UNKNOWN)
								.build();
		}
		return geoEntity;
	}

}//	@Override
//	public Future<GeoEntity> lookupAsync(InetAddress address) {
//		PreConditions.checkNull(address, "address cannot be null ");
//		Promise<GeoEntity> promise = Promise.promise();
//		redisDbIpRepository.get(address).onComplete(geo -> {
//			GeoEntity geoEntity = geo.result();
//			if(geoEntity == null) {
//				geoEntity = new GeoEntity
//						.Builder()
//						.withCity(UNKNOWN)
//						.withCountry(UNKNOWN)
//						.withCountryCode(UNKNOWN)
//						.withProvince(UNKNOWN)
//						.withIsp(UNKNOWN)
//						.build();
//			}
//			promise.complete(geoEntity);
//		});
//
//		return promise.future();
//	}

