package in.ankushs.dbip.api;

import in.ankushs.dbip.utils.PreConditions;

public final class GeoEntity {
	private final String city;
	private final String country;
	private final String state;
	
	public GeoEntity(final Builder builder){
		this.city = builder.city;
		this.country = builder.country;
		this.state = builder.state;
	}
	
	public static class Builder{
		private String city;
		private String country;
		private String state;
		
		public Builder withCity(final String city ){
			this.city = city;
			return this;
		}
		
		public Builder withCountry(final String country ){
			this.country = country;
			return this;
		}
		
		public Builder withState(final String state ){
			this.state = state;
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

	public String getState() {
		return state;
	}

	@Override
	public String toString() {
		return "GeoEntity [city=" + city + ", country=" + country + ", state=" + state + "]";
	}
	
	
}
