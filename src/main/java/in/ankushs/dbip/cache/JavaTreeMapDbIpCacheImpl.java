package in.ankushs.dbip.cache;

import com.google.common.net.InetAddresses;
import in.ankushs.dbip.domain.GeoAttributes;
import in.ankushs.dbip.domain.GeoEntity;
import in.ankushs.dbip.utils.Assert;
import in.ankushs.dbip.utils.IPUtils;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.util.Objects;
import java.util.TreeMap;

/**
 * 
 * as repository.
 * @author Ankush Sharma
 */
@Slf4j
public final class JavaTreeMapDbIpCacheImpl implements DbIpCache {

	private static final TreeMap<Integer,GeoEntity> IPV4_CACHE = new TreeMap<>();
	private static final TreeMap<BigInteger,GeoEntity> IPV6_CACHE = new TreeMap<>();

	/**
	 * Lookup GeoEntity for an InetAddress
	 * @param inetAddress The InetAddress to be resolved.
	 * @return A GeoEntity for an InetAddress
	 */
	@Override
	public GeoEntity get(final InetAddress inetAddress) {
		Assert.notNull(inetAddress, "inetAddress must not be null");
		if(inetAddress instanceof Inet4Address) {
			val startIpNum = InetAddresses.coerceToInteger(inetAddress);

			return Objects.isNull(IPV4_CACHE.floorEntry(startIpNum)) ? GeoEntity.EMPTY
					: IPV4_CACHE.floorEntry(startIpNum).getValue() ;
		}
		else {
			val startIpBigInt = IPUtils.ipv6ToBigInteger(inetAddress);
			return Objects.isNull(IPV6_CACHE.floorEntry(startIpBigInt)) ? GeoEntity.EMPTY
					: IPV6_CACHE.floorEntry(startIpBigInt).getValue();

		}
	}

	/**
	 * Put a GeoEntity object to Cache
	 */
	@Override
	public void put(final GeoAttributes geoAttributes) {
		Assert.notNull(geoAttributes, "geoAttributes must not be null");
		val startInetAddress = geoAttributes.getStartInetAddress();
		val endInetAddress = geoAttributes.getEndInetAddress();
		val geoEntity = geoAttributes.getGeoEntity();

		if(startInetAddress instanceof Inet6Address
				&& endInetAddress instanceof Inet6Address)
		{
			val startIpBigInt = IPUtils.ipv6ToBigInteger(startInetAddress);
			IPV6_CACHE.put(startIpBigInt, geoEntity);
		}
		else if (startInetAddress instanceof Inet4Address
				&& endInetAddress instanceof Inet4Address)
		{
			val startIpNum = InetAddresses.coerceToInteger(startInetAddress);
			IPV4_CACHE.put(startIpNum,geoEntity);
		}
		else {
			log.error("This should never happen. startInetAddress : {}, endInetAddress : {} ", startInetAddress, endInetAddress);
		}
	}
}
