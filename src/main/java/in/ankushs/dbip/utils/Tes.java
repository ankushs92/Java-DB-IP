package in.ankushs.dbip.utils;

import java.io.File;

import in.ankushs.dbip.api.DbIpClient;

public class Tes {
	public static void main(String[] args) {
		File gzip = new File("/Users/Ankush/Downloads/dbip-city-latest.csv.gz");
		DbIpClient client = new DbIpClient(gzip);
		
	}
}
