package in.ankushs.dbip.lookup;

import java.io.File;
import java.net.InetAddress;

import in.ankushs.dbip.api.DbIpClient;
import in.ankushs.dbip.api.GeoEntity;
import in.ankushs.dbip.repository.DbIpRepository;
import in.ankushs.dbip.repository.JavaMapDbIpRepositoryImpl;
import in.ankushs.dbip.utils.PreConditions;

/**
 * 
 * Singleton class that resolves ip to Location info.
 * @author Ankush Sharma
 */
public final class GeoEntityLookupServiceImpl implements GeoEntityLookupService {
	
	private static final String UNKNOWN = "Unknown";
	
	private final DbIpRepository repository = JavaMapDbIpRepositoryImpl.getInstance();
	
	private static GeoEntityLookupServiceImpl instance = null;
	
	private GeoEntityLookupServiceImpl(){}
	public static GeoEntityLookupServiceImpl getInstance(){
		if(instance==null){
			return new GeoEntityLookupServiceImpl();
		}
		return instance;
	}
	
	@Override
	public GeoEntity lookup(final InetAddress inetAddress) {
		PreConditions.checkNull(inetAddress, "inetAddress cannot be null ");
	    GeoEntity geoEntity = repository.get(inetAddress);
		if( geoEntity == null ){
			geoEntity = new GeoEntity
								.Builder()
								.withCity(UNKNOWN)
								.withCountry(UNKNOWN)
								.withCountryCode(UNKNOWN)
								.withProvince(UNKNOWN)
								.build();
		}
		return geoEntity;
	}
}
