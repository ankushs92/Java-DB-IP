package in.ankushs.dbip.api;

import java.io.File;
import java.util.TreeMap;

import com.google.common.net.InetAddresses;

public class Test {
	public static void main(String[] args) {


		DbIpClient client = new DbIpClient(new File("/Users/Ankush/Downloads/dbip-city-latest.csv.gz"));
		System.out.println(client.lookup("31.45.127.255"));
		TreeMap<Integer,String> trees = new TreeMap<>();
		trees.put(1,"a");
		trees.put(1,"b");
		System.out.println(trees);
	}
}
