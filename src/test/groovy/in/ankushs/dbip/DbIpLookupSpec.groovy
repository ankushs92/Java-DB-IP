package in.ankushs.dbip

import in.ankushs.dbip.api.DbIpClient
import in.ankushs.dbip.api.GeoEntity

class DbIpLookupSpec extends BaseSpec{
	
	static final LOCATION="/Users/Ankush/Downloads/dbip-city-latest.csv.gz"
	File file = new File(LOCATION)
	
	//Format City,State,Country
	def "New Delhi,Delhi,India"(){
		given:
			def client = new DbIpClient(file)
		when:
			def ip = "59.178.193.100"
			GeoEntity geoEntity = client.lookup(ip)
			
		then:
			geoEntity.city == 'New Delhi'
			geoEntity.country == 'India'
			geoEntity.state == 'Delhi'
	}
	
	def "Mézin,Aquitaine-Limousin-Poitou-Charentes,France"(){
		given:
			def client = new DbIpClient(file)
		when:
			def ip = "90.31.200.197"
			GeoEntity geoEntity = client.lookup(ip)
		then:
			geoEntity.city == 'Bourlens'
			geoEntity.country == 'France'
			geoEntity.state == 'Aquitaine'
	}
	
	def "Columbus,Ohio,United States"(){
		given:
			def client = new DbIpClient(file)
		when:
			def ip = "164.249.132.41"
			GeoEntity geoEntity = client.lookup(ip)
		then:
			geoEntity.city == 'Columbus'
			geoEntity.country == 'United States'
			geoEntity.state == 'Ohio'
	}
	
	def "Newark,New Jersey,United States"(){
		given:
			def client = new DbIpClient(file)
		when:
			def ip = "48.180.50.198"
			GeoEntity geoEntity = client.lookup(ip)
		then:
			geoEntity.city == 'Newark'
			geoEntity.country == 'United States'
			geoEntity.state == 'New Jersey'
	}
	
	def "Lisbon,Lisbon District,Portugal"(){
		given:
			def client = new DbIpClient(file)
		when:
			def ip = "82.154.109.118"
			GeoEntity geoEntity = client.lookup(ip)
		then:
			geoEntity.city == 'Lisbon'
			geoEntity.country == 'Portugal'
			geoEntity.state == 'Lisbon District'
	}
	
	def "Melbourne,Victoria,Australia"(){
		given:
			def client = new DbIpClient(file)
		when:
			def ip = "49.199.255.255"
			GeoEntity geoEntity = client.lookup(ip)
		then:
			geoEntity.city == 'Melbourne'
			geoEntity.country == 'Australia'
			geoEntity.state == 'Victoria'
	}
	
	def "Auckland,Auckland,New Zealand"(){
		given:
			def client = new DbIpClient(file)
		when:
			def ip = "222.152.255.255"
			GeoEntity geoEntity = client.lookup(ip)
		then:
			geoEntity.city == 'Auckland'
			geoEntity.country == 'New Zealand'
			geoEntity.state == 'Auckland'
	}
	
}
