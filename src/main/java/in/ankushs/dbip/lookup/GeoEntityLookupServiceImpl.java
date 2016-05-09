package in.ankushs.dbip.lookup;

import java.net.InetAddress;

import in.ankushs.dbip.api.GeoEntity;
import in.ankushs.dbip.repository.DbIpRepository;
import in.ankushs.dbip.repository.JavaMapDbIpRepositoryImpl;
import in.ankushs.dbip.utils.PreConditions;

public final class GeoEntityLookupServiceImpl implements GeoEntityLookupService {
	
	private static final String UNKNOWN = "UNKNOWN";
	private final DbIpRepository repository = new JavaMapDbIpRepositoryImpl();
	
	private GeoEntityLookupServiceImpl instance = null;
	
	public GeoEntityLookupServiceImpl getInstance(){
		if(instance==null){
			return this;
		}
		return instance;
	}
	
	@Override
	public GeoEntity lookup(final InetAddress inetAddress) {
		PreConditions.checkNull(inetAddress, "inetAddress cannot be null ");
	    GeoEntity geoEntity = repository.get(inetAddress);
		if( geoEntity == null ){
			geoEntity = new GeoEntity.Builder()
								.withCity(UNKNOWN).withCountry(UNKNOWN)
								.withState(UNKNOWN).build();
		}
		return geoEntity;
	}

}
