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

public final class DbIpClient {
	
	private static final Logger logger = LoggerFactory.getLogger(DbIpClient.class);

	private final File file ;
	private final GeoEntityLookupService lookupService = new GeoEntityLookupServiceImpl();
	
	private static boolean flag = false;
	
	public DbIpClient(final File csvFile){
		PreConditions.checkExpression(!csvFile.exists(), "file " + csvFile.getName() + " does not exist");
		this.file = csvFile;
		if(!flag){
			flag = true;
			logger.info("Loading db ip into repository ");
			new ResourceImporter().load(csvFile);
			logger.info("Loading finished");
		}
		else{
			logger.info(" DbIp csv file has already been loaded ");
		}
	}
	
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
	
	public GeoEntity lookup(final InetAddress inetAddress){
		PreConditions.checkNull(inetAddress, "inetAddress cannot be null");
		return lookupService.lookup(inetAddress);
	}
	
}
