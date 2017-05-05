package in.ankushs.dbip.model;

import java.net.InetAddress;

import in.ankushs.dbip.api.GeoEntity;

public final class GeoAttributesImpl implements GeoAttributes {

	private final String city;
	private final String country;
	private final String province ;
	private final String countryCode;
	private final String isp;
	private final InetAddress startInetAddress;
	private final InetAddress endInetAddress;

	
	private GeoAttributesImpl(final Builder builder){
		this.startInetAddress = builder.startInetAddress;
		this.endInetAddress = builder.endInetAddress;
		this.city = builder.city;
		this.country = builder.country;
		this.province = builder.province;
		this.countryCode = builder.countryCode;
		this.isp = builder.isp;
	}
	
	public static class Builder{
		private  InetAddress startInetAddress;
		private  InetAddress endInetAddress;
		private  String city;
		private  String country;
		private  String province ;
		private  String countryCode;
		private String isp;


		public Builder withIsp(final String isp){
			this.isp = isp;
			return this;
		}
		public Builder withStartInetAddress(final InetAddress startInetAddress){
			this.startInetAddress = startInetAddress;
			return this;
		}

		public Builder withCountryCode(final String countryCode){
			this.countryCode = countryCode;
			return this;
		}
		public Builder withEndInetAddress(final InetAddress endInetAddress){
			this.endInetAddress = endInetAddress;
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
	public InetAddress getStartInetAddress() {
		return startInetAddress;
	}

	@Override
	public InetAddress getEndInetAddress() {
		return endInetAddress;
	}

	@Override
	public GeoEntity getGeoEntity() {
		return new GeoEntity.Builder()
						.withCity(city)
						.withCountry(country)
						.withCountryCode(countryCode)
						.withProvince(province)
						.withIsp(isp)
						.build();
	}
	
	
}
