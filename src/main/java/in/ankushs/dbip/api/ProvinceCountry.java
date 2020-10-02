package in.ankushs.dbip.api;

import java.util.Objects;

public class ProvinceCountry {

    private final String province;
    private final String country;

    public ProvinceCountry(
            final String province,
            final String country
    )
    {
        this.province = province;
        this.country = country;
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
        if (!(o instanceof ProvinceCountry)) return false;
        ProvinceCountry that = (ProvinceCountry) o;
        return getProvince().equals(that.getProvince()) &&
                getCountry().equals(that.getCountry());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProvince(), getCountry());
    }

    @Override
    public String toString() {
        return "ProvinceCountry{" +
                "province='" + province + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
