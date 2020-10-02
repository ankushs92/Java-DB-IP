package in.ankushs.dbip.repository;

import in.ankushs.dbip.api.GeoEntity;
import in.ankushs.dbip.model.GeoAttributes;

import java.net.InetAddress;

/**
 * 
 * Abstraction for repository.For instance,a repository can be a TreeMap, or Redis.
 * 
 * @author Ankush Sharma
 */
public interface DbIpRepository {

	GeoEntity get( InetAddress inetAddress);

	void save(GeoAttributes geoAttributes);

	boolean fullLoadingEnabled();
}
