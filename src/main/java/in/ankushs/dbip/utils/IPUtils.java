package in.ankushs.dbip.utils;

import com.google.common.net.InetAddresses;

import java.math.BigInteger;
import java.net.InetAddress;

/**
 * Created by Ankush on 07/02/17.
 */
public class IPUtils {

    public static BigInteger ipv6ToBigInteger(final InetAddress inetAddress) {
        PreConditions.checkNull(inetAddress,"inetAddress cannot be null");
        final  byte[] bytes = inetAddress.getAddress();
        return new BigInteger(1, bytes);
    }


}
