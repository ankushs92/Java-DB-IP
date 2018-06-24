package in.ankushs.dbip.api;

import com.google.common.net.InetAddresses;
import in.ankushs.dbip.cache.JavaTreeMapDbIpCacheImpl;
import in.ankushs.dbip.domain.GeoEntity;
import in.ankushs.dbip.exceptions.InvalidIPException;
import in.ankushs.dbip.importer.ResourceImporter;
import in.ankushs.dbip.utils.Assert;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.File;
import java.net.InetAddress;

import static java.lang.Boolean.valueOf;

/**
 * 
 * Loads file to memory and looks up ip address .
 * @author Ankush Sharma
 */
@Slf4j
public final class DbIpClient {

	/*
	 * Flag to determine whether the db-ip file has already been cached into the JVM, or in the process of being cached
	 * */
	private static boolean flag = false;

	/**
	 * Constructor. Initializing the constructor also loads the file into the JVM
	 * @param gzip the gzip file to load
	 */
	public DbIpClient(final File gzip) {
		Assert.checkExpression(!gzip.exists(), "file " + gzip.getName() + " does not exist");
		synchronized (valueOf(flag)) {
			if(!flag) {
				flag = true;
				log.info("Loading file into repository ");
				new ResourceImporter().load(gzip);
				log.info("Loading finished");
			}
			else {
				log.info(" DbIp csv file has already been loaded ");
			}
		}
	}


	/**
	 * Resolve an ip to its Geolocation
	 * @param ip the ip to resolve
	 * @return GeoEntity
	 * @throws InvalidIPException  if invalid IP is passed
	 */
	public GeoEntity lookup(final String ip) {
		InetAddress inetAddress;
		try {
			inetAddress = InetAddresses.forString(ip);
		}
		catch(final IllegalArgumentException ex) {
			throw new InvalidIPException("Invalid IP passed : " + ip);
		}
		return lookup(inetAddress);
	}

	/**
	 * Resolve an ip to its Geolocation
	 * @param inetAddress the ip to resolve, passed as an InetAddress object
	 * @return GeoEntity
	 * @throws InvalidIPException  if invalid IP is passed
	 */
	public GeoEntity lookup(final InetAddress inetAddress) {
		Assert.notNull(inetAddress, "inetAddress cannot be null");
		val cache = new JavaTreeMapDbIpCacheImpl();
		return cache.get(inetAddress);
	}
}
