package in.ankushs.dbip.repository;

import in.ankushs.dbip.api.GeoEntity;
import in.ankushs.dbip.model.GeoAttributes;
import io.vertx.core.Future;

import java.net.InetAddress;

public interface AsyncDbIpRepository {

    Future<GeoEntity> get(InetAddress inetAddress);

    Future<Void> save(GeoAttributes geoAttributes);

}
