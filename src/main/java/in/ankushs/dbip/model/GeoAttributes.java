package in.ankushs.dbip.model;

import java.net.InetAddress;

import in.ankushs.dbip.api.GeoEntity;

public interface GeoAttributes {
	int getStartIp();
	int getEndIp();
	GeoEntity getGeoEntity();
}
