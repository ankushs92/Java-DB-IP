package in.ankushs.dbip

import java.io.File;

import spock.lang.Specification

class BaseSpec extends Specification{
	
	static final LOCATION="/Users/Ankush/Downloads/db-ip/new/dbip-city-2016-06.csv.gz"
	File file = new File(LOCATION)

}
