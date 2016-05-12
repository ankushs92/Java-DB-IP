package in.ankushs.dbip.api;

import java.io.File;
import java.util.TreeMap;

import com.google.common.net.InetAddresses;

public class Test {
	public static void main(String[] args) {
		File gzip = new File("/Users/Ankush/Downloads/dbip-city-latest.csv.gz");
		DbIpClient client = new DbIpClient(gzip);
		GeoEntity geoEntity = client.lookup("31.45.127.255");
		String city = geoEntity.getCity();
		String country = geoEntity.getCountry();
		String province = geoEntity.getProvince();
		
		System.out.println("city : " + city);
		System.out.println("province : " + province);
		System.out.println("country : " + country);

	} 
}
