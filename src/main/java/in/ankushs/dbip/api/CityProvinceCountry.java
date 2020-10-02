package in.ankushs.dbip.api;

import java.util.Objects;

public class CityProvinceCountry {

    private final String city;
    private final String province;
    private final String country;

    public CityProvinceCountry(
            final String city,
            final String province,
            final String country
    )
    {
        this.city = city;
        this.province = province;
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public String getProvince() {
        return province;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CityProvinceCountry)) return false;
        CityProvinceCountry that = (CityProvinceCountry) o;
        return getCity().equals(that.getCity()) &&
                getProvince().equals(that.getProvince()) &&
                getCountry().equals(that.getCountry());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCity(), getProvince(), getCountry());
    }

    @Override
    public String toString() {
        return "CityProvinceCountry{" +
                "city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
