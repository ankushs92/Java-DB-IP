package in.ankushs.dbip

import in.ankushs.dbip.api.DbIpClient
import in.ankushs.dbip.api.GeoEntity
import in.ankushs.dbip.exceptions.InvalidIPException

class DbIpClientSpec extends BaseSpec {
	def client
	def setup(){
		client = new DbIpClient(file)
	}
	
	def "Pass a valid Ip.All should work fine"(){
		when : "Call the client"
			def ip = "216.159.232.248"
			def client = new DbIpClient(file)
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
			def client = new DbIpClient(file)
			GeoEntity geoEntity = client.lookup(ip)
		then : "Should return some info.No exception thrown"
			thrown InvalidIPException

	}
}
