package in.ankushs.dbip.lookup;

import in.ankushs.dbip.api.CityProvinceCountry;
import in.ankushs.dbip.api.GeoEntity;
import in.ankushs.dbip.api.ProvinceCountry;

import java.net.InetAddress;
import java.util.Set;

public interface GeoEntityLookupService {
	GeoEntity lookup(InetAddress inetAddress);

	Set<String> getCountries();

	Set<ProvinceCountry> getProvinceCountries();


	Set<CityProvinceCountry> getCityProvinceCountries();

//	Future<GeoEntity> lookupAsync(InetAddress address);

}
