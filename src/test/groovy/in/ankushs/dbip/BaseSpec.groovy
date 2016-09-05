package in.ankushs.dbip

import java.io.File;

import spock.lang.Specification

class BaseSpec extends Specification{
	
	File file = new File(getClass().getClassLoader().getResource("dbip-city-2016-09.csv.gz").getFile());

}
