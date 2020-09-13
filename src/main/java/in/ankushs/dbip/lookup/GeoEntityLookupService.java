package in.ankushs.dbip.lookup;

import in.ankushs.dbip.api.GeoEntity;
import io.vertx.core.Future;

import java.net.InetAddress;

public interface GeoEntityLookupService {
	GeoEntity lookup(InetAddress inetAddress);

	Future<GeoEntity> lookupAsync(InetAddress address);

}
