package in.ankushs.dbip

import in.ankushs.dbip.api.DbIpClient
import in.ankushs.dbip.domain.GeoEntity
import in.ankushs.dbip.exceptions.InvalidIPException
import spock.lang.Specification

class DbIpClientSpec extends Specification {
	def client
	def setup(){
		client = new DbIpClient(new File("/Users/Ankush/Downloads/dbip-city-2017-02.csv.gz"))
	}
	
	def "Pass a valid Ip.All should work fine"(){
		when : "Call the client"
			def ip = "216.159.232.248"

			GeoEntity geoEntity = client.lookup(ip)
		then : "Should return some info.No exception thrown"
		geoEntity.city == 'Columbus'
		geoEntity.country == 'United States'
		geoEntity.province == 'Ohio'

	}
	
	def "Pass invalid Ip.Expect InvalidIPException to be thrown"(){
		given : "Some ip"
			def ip = "invalidIp"
		when : "Call the client"

			GeoEntity geoEntity = client.lookup(ip)
		then : "Should return some info.No exception thrown"
			thrown InvalidIPException

	}


}
