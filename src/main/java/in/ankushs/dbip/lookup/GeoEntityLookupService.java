package in.ankushs.dbip.lookup;

import in.ankushs.dbip.api.GeoEntity;

import java.net.InetAddress;

public interface GeoEntityLookupService {
	GeoEntity lookup(InetAddress inetAddress);

//	Future<GeoEntity> lookupAsync(InetAddress address);

}
