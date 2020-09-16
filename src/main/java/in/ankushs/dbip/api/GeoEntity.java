package in.ankushs.dbip.api;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 *
 * @author Ankush Sharma
 */
public final class GeoEntity {

//	city":"Limburg Province","country":"Belgium","province":"Flanders","countryCode":"BE","isp":"Telenet"}"; line
	@JsonProperty("city")
	private  String city;
	@JsonProperty("country")
	private  String country;

	@JsonProperty("province")
	private  String province;

	@JsonProperty("countryCode")
	private  String countryCode;

	@JsonProperty("isp")
	private  String isp;

	public GeoEntity() {}

	public GeoEntity(final Builder builder){
		this.city = builder.city;
		this.country = builder.country;
		this.province = builder.province;
		this.countryCode = builder.countryCode;
		this.isp = builder.isp;
	}
	 
	public static class Builder{
		private String countryCode;
		private String city;
		private String country;
		private String province;
		private String isp;


		public Builder withIsp(final String isp){
			this.isp = isp;
			return this;
		}

		public Builder withCountryCode(final String countryCode){
			this.countryCode = countryCode;
			return this;
		}
		
		public Builder withCity(final String city ){
			this.city = city;
			return this;
		}
		
		public Builder withCountry(final String country ){
			this.country = country;
			return this;
		}
		
		public Builder withProvince(final String province ){
			this.province = province;
			return this;
		}
		
		public GeoEntity build(){
			return new GeoEntity(this);
		}
	}

	public String getCity() {
		return city;
	}

	public String getCountry() {
		return country;
	}

	public String getProvince() {
		return province;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public void setIsp(String isp) {
		this.isp = isp;
	}

	@Override
	public String toString() {
		return "GeoEntity{" +
				"city='" + city + '\'' +
				", country='" + country + '\'' +
				", province='" + province + '\'' +
				", countryCode='" + countryCode + '\'' +
				", isp='" + isp + '\'' +
				'}';
	}

	public String getCountryCode() {
		return countryCode;
	}

	public String getIsp() {
		return isp;
	}

	public static void main(String[] args) {

	}
}
