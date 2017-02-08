package in.ankushs.dbip.api;

import in.ankushs.dbip.utils.PreConditions;
/**
 * 
 *
 * @author Ankush Sharma
 */
public final class GeoEntity {
	private final String city;
	private final String country;
	private final String province;
	private final String countryCode;

	public GeoEntity(final Builder builder){
		this.city = builder.city;
		this.country = builder.country;
		this.province = builder.province;
		this.countryCode = builder.countryCode;
	}
	
	public static class Builder{
		private String countryCode;
		private String city;
		private String country;
		private String province;

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


	public String getCountryCode() {
		return countryCode;
	}

	@Override
	public String toString() {
		return "GeoEntity{" +
				"city='" + city + '\'' +
				", country='" + country + '\'' +
				", province='" + province + '\'' +
				", countryCode='" + countryCode + '\'' +
				'}';
	}
}
