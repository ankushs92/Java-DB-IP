package in.ankushs.dbip.domain;

import in.ankushs.dbip.utils.Strings;
import lombok.Builder;
import lombok.Data;

/**
 * 
 *
 * @author Ankush Sharma
 */
@Data
@Builder
public class GeoEntity {

	public static final GeoEntity EMPTY = empty();

	private final String city;
	private final String country;
	private final String province;
	private final String countryCode;


	private static GeoEntity empty() {
		return GeoEntity
				.builder()
					.city(Strings.EMPTY)
					.country(Strings.EMPTY)
					.province(Strings.EMPTY)
					.countryCode(Strings.EMPTY)
				.build();
	}

}
