package in.ankushs.dbip.model;

import java.net.InetAddress;

import in.ankushs.dbip.api.GeoEntity;

public final class GeoAttributesImpl implements GeoAttributes {
	private final int startIp;
	private final int endIp;
	private final String city;
	private final String country;
	private final String province ;
	
	private GeoAttributesImpl(final Builder builder){
		this.startIp = builder.startIp;
		this.endIp = builder.endIp;
		this.city = builder.city;
		this.country = builder.country;
		this.province = builder.province;
	}
	
	public static class Builder{
		private  int startIp;
		private  int endIp;
		private  String city;
		private  String country;
		private  String province ;
		
		public Builder withStartIp(final int startIp){
			this.startIp = startIp;
			return this;
		}
		
		public Builder withEndIp(final int endIp){
			this.endIp = endIp;
			return this;
		}
		
		
		public Builder withCity(final String city){
			this.city = city;
			return this;
		}
		
		
		public Builder withCountry(final String country){
			this.country = country;
			return this;
		}
		
		
		public Builder withProvince(final String province){
			this.province = province;
			return this;
		}
		
		public GeoAttributesImpl build(){
			return new GeoAttributesImpl(this);
		}
	}

	@Override
	public int getStartIp() {
		return startIp;
	}

	@Override
	public int getEndIp() {
		return endIp;
	}

	@Override
	public GeoEntity getGeoEntity() {
		return new GeoEntity.Builder()
						.withCity(city).withCountry(country)
						.withProvince(province).build();
	}
	
	
}
