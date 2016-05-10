package in.ankushs.dbip

import java.io.File;

import spock.lang.Specification

class BaseSpec extends Specification{
	
	static final LOCATION="/Users/Ankush/Downloads/dbip-city-latest.csv.gz"
	File file = new File(LOCATION)

}
