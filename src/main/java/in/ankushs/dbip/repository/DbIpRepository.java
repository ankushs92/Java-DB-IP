package in.ankushs.dbip.repository;

import java.net.InetAddress;

import in.ankushs.dbip.api.GeoEntity;
import in.ankushs.dbip.model.GeoAttributes;

public interface DbIpRepository {
	GeoEntity get( InetAddress inetAddress);
	void save(GeoAttributes geoAttributes);
}
