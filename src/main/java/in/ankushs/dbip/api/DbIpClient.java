package in.ankushs.dbip.api;

import java.io.File;
import java.net.InetAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.net.InetAddresses;

import in.ankushs.dbip.exceptions.InvalidIPException;
import in.ankushs.dbip.importer.ResourceImporter;
import in.ankushs.dbip.lookup.GeoEntityLookupService;
import in.ankushs.dbip.lookup.GeoEntityLookupServiceImpl;
import in.ankushs.dbip.utils.PreConditions;
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
	
	private final GeoEntityLookupService lookupService = new GeoEntityLookupServiceImpl();
	
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
	public DbIpClient(final File gzip){
		PreConditions.checkExpression(!gzip.exists(), "file " + gzip.getName() + " does not exist");
		this.file = gzip;
		if(!flag){
			flag = true;
			logger.info("Loading db ip into repository ");
			new ResourceImporter().load(gzip);
			logger.info("Loading finished");
		}
		else{
			logger.info(" DbIp csv file has already been loaded ");
		}
	}
	
	
	/**
	 * Returns a loaded GeoEntity object for a given {@code ip}
	 * If nothing can be resolved for an {@code ip} , then the city,state and country 
	 * for the GeoEntity will be set to 'Unknown'
	 * Any futher initializations of the DbIpClient  will not load data into memory again.
	 * @param gzip The dbip-city-latest.csv.gz file as a File object.
	 * @throws InvalidIPException if {@code ip} is not a valid IP address.
	 */
	public GeoEntity lookup(final String ip){
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
	 * @param gzip The dbip-city-latest.csv.gz file as a File object.
	 * @throws InvalidIPException if {@code inetAddress} is not a valid IP address.
	 */
	public GeoEntity lookup(final InetAddress inetAddress){
		PreConditions.checkNull(inetAddress, "inetAddress cannot be null");
		return lookupService.lookup(inetAddress);
	}
	
}
