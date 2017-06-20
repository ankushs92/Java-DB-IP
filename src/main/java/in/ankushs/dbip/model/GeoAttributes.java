package in.ankushs.dbip.model;

import java.net.InetAddress;

import in.ankushs.dbip.api.GeoEntity;

public interface GeoAttributes {

	InetAddress getStartInetAddress();

	InetAddress getEndInetAddress();

	GeoEntity getGeoEntity();
}
