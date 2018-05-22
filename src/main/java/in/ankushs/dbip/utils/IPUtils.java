package in.ankushs.dbip.utils;

import lombok.val;

import java.math.BigInteger;
import java.net.InetAddress;

/**
 * Created by Ankush on 07/02/17.
 */
public class IPUtils {

    private IPUtils() {}

    /**
     * Resolve a InetAddress to BigInteger
     * @param inetAddress
     * @return
     */
    public static BigInteger ipv6ToBigInteger(final InetAddress inetAddress) {
        Assert.notNull(inetAddress,"inetAddress cannot be null");
        val bytes = inetAddress.getAddress();
        return new BigInteger(1, bytes);
    }
}
