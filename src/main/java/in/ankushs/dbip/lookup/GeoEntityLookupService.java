package in.ankushs.dbip.lookup;

import java.net.InetAddress;

import in.ankushs.dbip.api.GeoEntity;

public interface GeoEntityLookupService {
	GeoEntity lookup(InetAddress inetAddress);
}
