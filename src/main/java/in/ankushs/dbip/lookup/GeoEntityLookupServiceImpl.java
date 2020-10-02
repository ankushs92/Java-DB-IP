package in.ankushs.dbip.lookup;

import in.ankushs.dbip.api.CityProvinceCountry;
import in.ankushs.dbip.api.GeoEntity;
import in.ankushs.dbip.api.ProvinceCountry;
import in.ankushs.dbip.repository.DbIpRepository;
import in.ankushs.dbip.repository.JavaMapDbIpRepositoryImpl;
import in.ankushs.dbip.repository.RedisDbIpRepositoryImpl;
import in.ankushs.dbip.utils.PreConditions;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.net.InetAddress;
import java.util.Set;

/**
 * 
 * Singleton class that resolves ip to Location info.
 * @author Ankush Sharma
 */
public final class GeoEntityLookupServiceImpl implements GeoEntityLookupService {
	
	private static final String UNKNOWN = "Unknown";
	
	private final DbIpRepository repository = JavaMapDbIpRepositoryImpl.getInstance();
	
	private static GeoEntityLookupServiceImpl instance = null;

	private final JedisPool jedisPool;
	private final RedisDbIpRepositoryImpl redisDbIpRepository;

	public GeoEntityLookupServiceImpl() {
		this.jedisPool = null;
		this.redisDbIpRepository = null;
	}

	public GeoEntityLookupServiceImpl(final JedisPool jedisPool, final boolean fullLoadingEnabled) {
		this.jedisPool = jedisPool;
		this.redisDbIpRepository = new RedisDbIpRepositoryImpl(jedisPool, fullLoadingEnabled);
	}


	@Override
	public GeoEntity lookup(final InetAddress inetAddress) {
		PreConditions.checkNull(inetAddress, "inetAddress cannot be null ");
		GeoEntity geoEntity = null;
		if(jedisPool != null) {
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

	@Override
	public Set<String> getCountries() {
		return redisDbIpRepository.countries();
	}

	@Override
	public Set<ProvinceCountry> getProvinceCountries() {
		return redisDbIpRepository.provinceCountries();
	}

	@Override
	public Set<CityProvinceCountry> getCityProvinceCountries() {
		return redisDbIpRepository.citiesCountryProvince();
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

