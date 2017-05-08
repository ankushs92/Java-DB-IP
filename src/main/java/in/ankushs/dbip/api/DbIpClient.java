package in.ankushs.dbip.api;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.TreeMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import in.ankushs.dbip.repository.JavaMapDbIpRepositoryImpl;
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
	//Singleton
	private final GeoEntityLookupService lookupService = GeoEntityLookupServiceImpl.getInstance();
	
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
			ResourceImporter.getInstance().load(gzip);
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
	 * @param ip The ip (as String) to be resolved.
	 * @return a GeoEntity object representing city,state and province info
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
	 * @param inetAddress The inetAddress (as InetAddress) to be resolved.
	 * @return a GeoEntity object representing city,state and province info
	 */
	public GeoEntity lookup(final InetAddress inetAddress){
		PreConditions.checkNull(inetAddress, "inetAddress cannot be null");
		return lookupService.lookup(inetAddress);
	}


	public TreeMap<Integer,GeoEntity> getIpv4Repo(){
		return  JavaMapDbIpRepositoryImpl.getIpv4Repository();
	}
}
