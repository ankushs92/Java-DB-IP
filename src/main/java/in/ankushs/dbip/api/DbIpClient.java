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
	 * Indicates whether the file has been loaded into the JVM.
	 * */
	private static boolean flag = false;
	
	/**
	 * Create a new DbIpClient . 
	 * Once an instance has been created, the allLoaded flag is set to true.
	 * Any futher initializations of this constructor will not load data into the JVM again.
	 * @param gzip The dbip-city-latest.csv.gz file as a File object.
	 * @throws IllegalArgumentException if {@code gzip} does not exist.
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
	 * Returns a loaded GeoEntity object for a given {@code ip}
	 * If nothing can be resolved for an {@code ip} , then the city,state and country 
	 * for the GeoEntity will be set to 'Unknown'
	 * Any futher initializations of the DbIpClient  will not load data into memory again.
	 * @param ip The ip (as String) to be resolved.
	 * @return a GeoEntity object representing city,state and province info
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
	 * Returns a loaded GeoEntity object for a given {@code inetAddress}
	 * If nothing can be resolved for an {@code inetAddress} , then the city,state and country 
	 * for the GeoEntity will be set to 'Unknown'
	 * @param inetAddress The inetAddress (as InetAddress) to be resolved.
	 * @return a GeoEntity object representing city,state and province info
	 */
	public GeoEntity lookup(final InetAddress inetAddress) {
		Assert.notNull(inetAddress, "inetAddress cannot be null");
		val cache = new JavaTreeMapDbIpCacheImpl();
		return cache.get(inetAddress);
	}

	public static void main(String[] args) {
		val db = new DbIpClient(new File("/Users/ankushsharma/Downloads/dbip-city-2018-05.csv.gz"));
		System.out.println(db.lookup("103.54.24.110"));
	}
}
