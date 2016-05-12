package in.ankushs.dbip.repository;

import java.net.InetAddress;
import java.util.TreeMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.google.common.net.InetAddresses;

import in.ankushs.dbip.api.GeoEntity;
import in.ankushs.dbip.model.GeoAttributes;
import in.ankushs.dbip.utils.PreConditions;
/**
 * 
 * Singletonclass that uses a <a href="https://docs.oracle.com/javase/7/docs/api/java/util/TreeMap.html">TreeMap</a>
 * as repository.
 * @author Ankush Sharma
 */
public final class JavaMapDbIpRepositoryImpl implements DbIpRepository {
	
	private static JavaMapDbIpRepositoryImpl instance = null;
	
	private JavaMapDbIpRepositoryImpl(){}
	public static JavaMapDbIpRepositoryImpl getInstance(){
		if(instance==null){
			return new JavaMapDbIpRepositoryImpl();
		}
		return instance;
	}
	
	private static final TreeMap<Integer,GeoEntity> repository = new TreeMap<>();
	
	/**
	 * Lookup GeoEntity for an InetAddress
	 * @param inetAddress The InetAddress to be resolved.
	 * @return A GeoEntity for an InetAddress
	 */
	@Override
	public GeoEntity get(final InetAddress inetAddress) {
		PreConditions.checkNull(inetAddress, "inetAddress must not be null");
		final Integer startIpNum = InetAddresses.coerceToInteger(inetAddress);
		 
		return repository.floorEntry(startIpNum) == null ? null 
				: repository.floorEntry(startIpNum).getValue() ;
	}

	/**
	 * Save GeoEntity for an InetAddress
	 * @param geoAttributes The attributes to be saved . Contains the attributes that will be needed 
	 * as key and value to be put inside the TreeMap.
	 */
	@Override
	public void save(final GeoAttributes geoAttributes) {
		PreConditions.checkNull(geoAttributes, "geoAttributes must not be null");
		final Integer startIpNum = InetAddresses.coerceToInteger(geoAttributes.getStartIp());
		final GeoEntity geoEntity = geoAttributes.getGeoEntity();
		repository.put(startIpNum,geoEntity);
	}
}
