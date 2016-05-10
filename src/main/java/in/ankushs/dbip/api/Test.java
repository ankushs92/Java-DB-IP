package in.ankushs.dbip.api;

import java.net.InetAddress;
import java.util.TreeMap;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.net.InetAddresses;

public class Test {
	public static void main(String[] args) {
		InetAddress ip = InetAddresses.forString(
	            Iterables.getLast(
	                  Splitter.on('/').omitEmptyStrings().split("2001:d58:ffff:ffff:ffff:ffff:ffff:ffff")
	           )
	         );
		System.out.println(InetAddresses.coerceToInteger(InetAddresses.getCoercedIPv4Address(ip)));

		System.out.println(InetAddresses.coerceToInteger(InetAddresses.forString("164.249.132.41")));

		System.out.println(InetAddresses.coerceToInteger(InetAddresses.forString("2001:d67:ffff:ffff:ffff:ffff:ffff:ffff")));

//		DbIpClient client = new DbIpClient(new File("/Users/Ankush/Downloads/dbip-city-latest.csv.gz"));
//		GeoEntity entity = client.lookup("2001:d67:ffff:ffff:ffff:ffff:ffff:ffff");
//		System.out.println(entity);
//		entity = client.lookup("2001:d67:ffff:ffff:ffff:ffff:ffff:ffff");
//		System.out.println(entity);
//		System.out.println(InetAddresses.coerceToInteger(InetAddresses.forString("2001:d67:ffff:ffff:ffff:ffff:ffff:ffff")));
		TreeMap<Integer,String> trees = new TreeMap<>();
		trees.put(1,"a");
		trees.put(1,"b");
		System.out.println(trees);
	}
}
