package in.ankushs.dbip.model;

import in.ankushs.dbip.api.GeoEntity;

import java.net.InetAddress;

public interface GeoAttributes {

	InetAddress getStartInetAddress();

	InetAddress getEndInetAddress();

	GeoEntity getGeoEntity();
}
