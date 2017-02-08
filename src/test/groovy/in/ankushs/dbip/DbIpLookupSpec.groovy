package in.ankushs.dbip

import in.ankushs.dbip.api.DbIpClient
import in.ankushs.dbip.api.GeoEntity
import spock.lang.Specification

\
class DbIpLookupSpec extends Specification{

	def client
	def setup(){
		client = new DbIpClient(new File("/Users/Ankush/Downloads/dbip-city-2017-02.csv.gz"))
	}

	//Format :City,State,Country
	def "New Delhi,Delhi,India"(){
		when:
			def ip = "59.178.193.100"
			GeoEntity geoEntity = client.lookup(ip)
			
		then:
			geoEntity.city == 'New Delhi'
			geoEntity.country == 'India'
			geoEntity.province == 'Delhi'
			geoEntity.countryCode == 'IN'
	}
	
	def "Mézin,Aquitaine-Limousin-Poitou-Charentes,France"(){
		when:
			def ip = "90.31.200.197"
			GeoEntity geoEntity = client.lookup(ip)
		then:
			geoEntity.city == 'Bourlens'
			geoEntity.country == 'France'
			geoEntity.province == 'Aquitaine'
			geoEntity.countryCode == 'FR'

	}
	
	def "Columbus,Ohio,United States"(){
		when:
			def ip = "164.249.132.41"
			GeoEntity geoEntity = client.lookup(ip)
		then:
			geoEntity.city == 'Columbus'
			geoEntity.country == 'United States'
			geoEntity.province == 'Ohio'
			geoEntity.countryCode == 'US'

	}
	
	def "Newark,New Jersey,United States"(){
		when:
			def ip = "48.180.50.198"
			GeoEntity geoEntity = client.lookup(ip)
		then:
			geoEntity.city == 'Newark'
			geoEntity.country == 'United States'
			geoEntity.province == 'New Jersey'
			geoEntity.countryCode == 'US'

	}
	
	def "Lisbon,Lisbon District,Portugal"(){
		when:
			def ip = "82.154.109.118"
			GeoEntity geoEntity = client.lookup(ip)
		then:
			geoEntity.city == 'Lisbon'
			geoEntity.country == 'Portugal'
			geoEntity.province == 'Lisbon District'
			geoEntity.countryCode == 'PT'

	}
	
	def "Melbourne,Victoria,Australia"(){
		when:
			def ip = "49.199.255.255"
			GeoEntity geoEntity = client.lookup(ip)
		then:
			geoEntity.city == 'Melbourne'
			geoEntity.country == 'Australia'
			geoEntity.province == 'Victoria'
			geoEntity.countryCode == 'AU'

	}
	
	def "Auckland,Auckland,New Zealand"(){
		when:
			def ip = "222.152.255.255"
			GeoEntity geoEntity = client.lookup(ip)
		then:
			geoEntity.city == 'Auckland'
			geoEntity.country == 'New Zealand'
			geoEntity.province == 'Auckland'
			geoEntity.countryCode == 'NZ'

	}
	
	def "Yekaterinburg,Sverdlovsk Oblast,Russian Federation"(){
		when:
			def ip = "5.2.63.255"
			GeoEntity geoEntity = client.lookup(ip)
		then:
			geoEntity.city == 'Yekaterinburg'
			geoEntity.country == 'Russian Federation'
			geoEntity.province == 'Sverdlovsk Oblast'
			geoEntity.countryCode == 'RU'

	}
	
	def "Yekaterinburg,Sverdlovsk Oblast,Sri Lanka"(){
		when:
			def ip = "112.135.255.255"
			GeoEntity geoEntity = client.lookup(ip)
		then:
			geoEntity.city == 'Colombo'
			geoEntity.country == 'Sri Lanka'
			geoEntity.province == 'Western Province'
			geoEntity.countryCode == 'LK'

	}
	
	def "Dunker,Södermanland County,Sweden"(){
	when:
		def ip = "5.133.223.255"
		GeoEntity geoEntity = client.lookup(ip)
	then:
		geoEntity.city == 'Dunker'
		geoEntity.country == 'Sweden'
		geoEntity.province == 'Södermanland County'
		geoEntity.countryCode == 'SE'

	}
	
	def "Oslo,Oslo,Norway"(){
	when:
		def ip = "31.45.127.255"
		GeoEntity geoEntity = client.lookup(ip)
	then:
		geoEntity.city == 'Oslo'
		geoEntity.country == 'Norway'
		geoEntity.province == 'Oslo'
		geoEntity.countryCode == 'NO'
	}
	
	def "London,England,United Kingdom"(){

	when:
		def ip = "31.48.109.127"
		GeoEntity geoEntity = client.lookup(ip)
	then:
		geoEntity.city == 'London'
		geoEntity.country == 'United Kingdom'
		geoEntity.province == 'England'
		geoEntity.countryCode == 'GB'

	}
	
	def "Issy-les-Moulineaux,Ile-de-france,France"(){
	when:
		def ip = "194.3.31.52"
		GeoEntity geoEntity = client.lookup(ip)
	then:
		geoEntity.city == 'Issy-les-Moulineaux'
		geoEntity.country == 'France'
		geoEntity.province == 'Île-de-france'
		geoEntity.countryCode == 'FR'

	}
	
	def "Cairo,Cairo Governorate,Egypt"(){
	when:
		def ip = "197.55.197.243"
		GeoEntity geoEntity = client.lookup(ip)
	then:
		geoEntity.city == 'Cairo'
		geoEntity.country == 'Egypt'
		geoEntity.province == 'Cairo Governorate'
		geoEntity.countryCode == 'EG'

	}

	def "Seoul,Seoul,Korea, Republic of"(){
	when:
		def ip = "211.232.184.31"
		GeoEntity geoEntity = client.lookup(ip)
	then:
		geoEntity.city == 'Seoul'
		geoEntity.country == "'Korea, Republic of'"
		geoEntity.province == 'Seoul'
		geoEntity.countryCode == 'KR'

	}
	
	def "Florence,Tuscany,Italy"(){
	when:
		def ip = "31.196.74.119"
		GeoEntity geoEntity = client.lookup(ip)
	then:
		geoEntity.city == 'Florence'
		geoEntity.country == "Italy"
		geoEntity.province == 'Tuscany'
		geoEntity.countryCode == 'IT'

	}
	
	def "Amsterdam,North Holland,Netherlands"(){
	when:
		def ip = "92.68.194.100"
		GeoEntity geoEntity = client.lookup(ip)
	then:
		geoEntity.city == 'Amsterdam'
		geoEntity.country == "Netherlands"
		geoEntity.province == 'North Holland'
		geoEntity.countryCode == 'NL'

	}

	def "Bangkok,จังหวัด กรุงเทพมหานคร,Thailand"(){
	when:
		def ip = "1.0.255.255"
		GeoEntity geoEntity = client.lookup(ip)
	then:
		geoEntity.city == 'Bangkok'
		geoEntity.country == "Thailand"
		geoEntity.province == 'จังหวัด กรุงเทพมหานคร'
		geoEntity.countryCode == 'TH'

	}
	
	def "Minato,Tokyo,Japan"(){
	when:
		def ip = "1.1.112.245"
		GeoEntity geoEntity = client.lookup(ip)
	then:
		geoEntity.city == 'Minato'
		geoEntity.country == "Japan"
		geoEntity.province == 'Tokyo'
		geoEntity.countryCode == 'JP'

	}
	
	def "Ahmedabad,Gujarat,India"(){
	when:
		def ip = "1.23.30.240"
		GeoEntity geoEntity = client.lookup(ip)
	then:
		geoEntity.city == 'Ahmedabad'
		geoEntity.country == "India"
		geoEntity.province == 'Gujarat'
		geoEntity.countryCode == 'IN'

	}
	def "Adelaide,South Australia,Australia"(){
	when:
		def ip = "1.123.166.1"
		GeoEntity geoEntity = client.lookup(ip)
	then:
		geoEntity.city == 'Adelaide'
		geoEntity.country == "Australia"
		geoEntity.province == 'South Australia'
		geoEntity.countryCode == 'AU'

	}
	
	def "Minsyong Township,Chiayi County,'Taiwan, Province Of China'"(){
	when:
		def ip = "1.170.171.25"
		GeoEntity geoEntity = client.lookup(ip)
	then:
		geoEntity.city == 'Taitung City'
		geoEntity.country == "'Taiwan, Province Of China'"
		geoEntity.province == 'Taitung County'
		geoEntity.countryCode == 'TW'

	}

	def "Roma,Lazio,Italy"(){
	when:
		def ip = "2.40.217.22"
		GeoEntity geoEntity = client.lookup(ip)
	then:
		geoEntity.city == 'Milan'
		geoEntity.country == "Italy"
		geoEntity.province == 'Lombardy'
		geoEntity.countryCode == 'IT'

	}
	
	def "Ciudad de México,Tamaulipas,Mexico"(){
	when:
		def ip = "189.159.147.1"
		GeoEntity geoEntity = client.lookup(ip)
	then:
		geoEntity.city == 'Ciudad de México'
		geoEntity.country == "Mexico"
		geoEntity.province == 'Distrito Federal'
		geoEntity.countryCode == 'MX'

	}


	//=============== IPV6 ===================
//city,state,country
	def "Ebène,Plaines Wilhems District,Mauritus"(){
	when:
		def ip = "2c0f:ffbf:ffff:ffff:ffff:ffff:ffff:ffff"
	GeoEntity geoEntity = client.lookup(ip)
	then:
		geoEntity.city == 'Ebène'
		geoEntity.country == "Mauritius"
		geoEntity.province == 'Plaines Wilhems District'
		geoEntity.countryCode == 'MU'

	}

	def "New York,New York,United States of America"(){
		when:
		def ip = "2c10::"
		GeoEntity geoEntity = client.lookup(ip)
		then:
		geoEntity.city == 'New York'
		geoEntity.country == "United States"
		geoEntity.province == 'New York'
		geoEntity.countryCode == 'US'

	}


	def "Gaborone,South-east,Botswana"(){
		when:
		def ip = "2c0f:fac8:f2ff:ffff:ffff:ffff:ffff:ffff"
		GeoEntity geoEntity = client.lookup(ip)
		then:
		geoEntity.city == 'Gaborone'
		geoEntity.country == "Botswana"
		geoEntity.province == 'South-east'
		geoEntity.countryCode == 'BW'

	}

	def "Nairobi,Nairobi,Kenya"(){
		when:
		def ip = "2c0f:f468:ff2f:ffff:ffff:ffff:ffff:ffff"
		GeoEntity geoEntity = client.lookup(ip)
		then:
		geoEntity.city == 'Nairobi'
		geoEntity.country == "Kenya"
		geoEntity.province == 'Nairobi'
		geoEntity.countryCode == 'KE'

	}


	def "Vejle,Region Syddanmark,Denmark"(){
		when:
		def ip = "2a0b:ee00::"
		GeoEntity geoEntity = client.lookup(ip)
		then:
		geoEntity.city == 'Vejle'
		geoEntity.country == "Denmark"
		geoEntity.province == 'Region Syddanmark'
		geoEntity.countryCode == 'DK'

	}

	def "Tehran,Tehran Province,Iran, Islamic Republic Of"(){
		when:
			def ip = "2a0b:6b07:ffff:ffff:ffff:f1ff:ffff:ffff"
			GeoEntity geoEntity = client.lookup(ip)
		then:
			geoEntity.city == 'Tehran'
			geoEntity.country == "'Iran, Islamic Republic Of'"
			geoEntity.province == 'Tehran Province'
			geoEntity.countryCode == 'IR'

	}

	def "Amsterdam, North Holland,Netherlands"(){
		when:
			def ip = "2a0b:6eff:ffff:ffff:ffff:f2ff:ffff:ffff"
			GeoEntity geoEntity = client.lookup(ip)
		then:
			geoEntity.city == 'Amsterdam'
			geoEntity.country == "Netherlands"
			geoEntity.province == 'North Holland'
			geoEntity.countryCode == 'NL'

	}



	def "Modena, Emilia-romagna,Italy"(){
		when:
		def ip = "2a0b:5f00:0:ffff:ffff:fff2:ffff:ffff"
		GeoEntity geoEntity = client.lookup(ip)
		then:
		geoEntity.city == 'Modena'
		geoEntity.country == "Italy"
		geoEntity.province == 'Emilia-romagna'
		geoEntity.countryCode == 'IT'

	}
}	
