package in.ankushs.dbip.api;

import com.google.common.net.InetAddresses;
import in.ankushs.dbip.exceptions.InvalidIPException;
import in.ankushs.dbip.importer.ResourceImporter;
import in.ankushs.dbip.lookup.GeoEntityLookupService;
import in.ankushs.dbip.lookup.GeoEntityLookupServiceImpl;
import in.ankushs.dbip.repository.RedisDbIpRepositoryImpl;
import in.ankushs.dbip.utils.PreConditions;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisAPI;
import io.vertx.redis.client.RedisOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.InetAddress;
/**
 * 
 * Class responsible for loading data into the JVM and also an API for resolving ip.
 * @author Ankush Sharma
 */
public final class DbIpClient {
	
	private static final Logger logger = LoggerFactory.getLogger(DbIpClient.class);

	/*
	 * The dbip-city-latest.csv.gz file 
	 * */
	private final File file ;
	private final boolean usingRedis;

	private final RedisOptions redisOptions;
	private final Redis redis;

	//Singleton
	private final GeoEntityLookupService lookupService;
	
	/*
	 * Indicates whether the file has been loaded into the JVM.
	 * */
	private static boolean flag = false;
	
	
	/**
	 * Create a new DbIpClient . 
	 * Once an instance has been created, the allLoaded flag is set to true.
	 * Any futher initializations of the DbIpClient  will not load data into memory again.
	 * @param gzip The dbip-city-latest.csv.gz file as a File object.
	 * @throws IllegalArgumentException if {@code gzip} does not exist.
	 */
	public DbIpClient(final File gzip) {
		PreConditions.checkExpression(!gzip.exists(), "file " + gzip.getName() + " does not exist");
		this.usingRedis = false;
		this.file = gzip;
		this.redisOptions = null;
		this.redis = null;
		this.lookupService = new GeoEntityLookupServiceImpl();
		if (!flag) {
			flag = true;
			logger.info("Loading db ip into repository ");
			new ResourceImporter().load(gzip);
			logger.info("Loading finished");
		} else {
			logger.info(" DbIp csv file has already been loaded ");
		}
	}


	public DbIpClient(final File gzip, final RedisOptions redisOptions) {
		PreConditions.checkExpression(!gzip.exists(), "file " + gzip.getName() + " does not exist");
		this.file = gzip;
		this.redisOptions = redisOptions;
		this.usingRedis = true;

		final VertxOptions vertxOptions = new VertxOptions();
		vertxOptions.setBlockedThreadCheckInterval(10000000L);
		final Vertx vertx = Vertx.vertx(vertxOptions);
		final Redis redis = Redis.createClient(vertx, redisOptions);
		this.redis = redis;
		final RedisAPI redisApi = RedisAPI.api(redis);
		this.lookupService = new GeoEntityLookupServiceImpl(redisApi);

		redis.connect(onConnect -> {
			if(onConnect.succeeded()) {
				logger.info("Successfully connected to Redis");
				final boolean savedAlready = false;
				if(!savedAlready) {
					new ResourceImporter(RedisAPI.api(onConnect.result())).load(file);
				}
			}
			else {
				logger.error("Error connecting to Redis ", onConnect.cause());
			}
		});
	}
	
	
	/**
	 * Returns a loaded GeoEntity object for a given {@code ip}
	 * If nothing can be resolved for an {@code ip} , then the city,state and country 
	 * for the GeoEntity will be set to 'Unknown'
	 * Any futher initializations of the DbIpClient  will not load data into memory again.
	 * @param ip The ip (as String) to be resolved.
	 * @return a GeoEntity object representing city,state and province info
	 */
	public GeoEntity lookup(final String ip) {
		InetAddress inetAddress = null;
		try{
			inetAddress = InetAddresses.forString(ip);
		}
		catch(final IllegalArgumentException ex){
			logger.error("Invalid IP given",ex);
			throw new InvalidIPException("Invalid IP passed");
		}
		return lookup(inetAddress);
	}
	
	/**
	 * Returns a loaded GeoEntity object for a given {@code inetAddress}
	 * If nothing can be resolved for an {@code inetAddress} , then the city,state and country 
	 * for the GeoEntity will be set to 'Unknown'
	 * @param inetAddress The inetAddress (as InetAddress) to be resolved.
	 * @return a GeoEntity object representing city,state and province info
	 */
	public GeoEntity lookup(final InetAddress inetAddress){
		PreConditions.checkNull(inetAddress, "inetAddress cannot be null");
		return lookupService.lookup(inetAddress);
	}

	public Future<GeoEntity> lookupAsync(final String ip) {
		PreConditions.checkNull(ip, "inetAddress cannot be null");
		final InetAddress  inetAddress = InetAddresses.forString(ip);
		return lookupService.lookupAsync(inetAddress);
	}

	public static void main(String[] args) {
		String host = "localhost";
		int port = 6379;
		RedisOptions redisOptions = new RedisOptions();
		DbIpClient dbIpClient = new DbIpClient(new File("/Users/ankushsharma/Downloads/ranjeet_ranjan-adosiz-geoip-vertx-e33c76f3158d/src/main/resources/dbip-full-2018-04.csv.gz"), redisOptions);

//		String ip = "84.170.151.254";
//		dbIpClient.lookupAsync(ip).onComplete(a -> {
//			System.out.println(a);
//		});


	}

}
