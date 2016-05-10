package in.ankushs.dbip.repository;

import java.net.InetAddress;
import java.util.TreeMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.google.common.net.InetAddresses;

import in.ankushs.dbip.api.GeoEntity;
import in.ankushs.dbip.model.GeoAttributes;
import in.ankushs.dbip.utils.PreConditions;

public class JavaMapDbIpRepositoryImpl implements DbIpRepository {
	
	private static JavaMapDbIpRepositoryImpl instance = null;
	public static JavaMapDbIpRepositoryImpl getInstance(){
		if(instance==null){
			return new JavaMapDbIpRepositoryImpl();
		}
		return instance;
	}
	
	private static final TreeMap<Integer,GeoEntity> repository = new TreeMap<>();

	@Override
	public GeoEntity get(final InetAddress inetAddress) {
		PreConditions.checkNull(inetAddress, "inetAddress must not be null");
		final Integer startIpNum = InetAddresses.coerceToInteger(inetAddress);
		 
		return repository.floorEntry(startIpNum) == null ? null 
				: repository.floorEntry(startIpNum).getValue() ;
	}

	@Override
	public void save(final GeoAttributes geoAttributes) {
		PreConditions.checkNull(geoAttributes, "geoAttributes must not be null");
		final Integer startIpNum = InetAddresses.coerceToInteger(geoAttributes.getStartIp());
		final GeoEntity geoEntity = geoAttributes.getGeoEntity();
		repository.put(startIpNum,geoEntity);
	}
}
