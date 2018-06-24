package in.ankushs.dbip.importer;

import com.google.common.collect.Interner;
import com.google.common.collect.Interners;
import com.google.common.net.InetAddresses;
import in.ankushs.dbip.cache.JavaTreeMapDbIpCacheImpl;
import in.ankushs.dbip.domain.GeoAttributesImpl;
import in.ankushs.dbip.utils.Assert;
import in.ankushs.dbip.utils.Country;
import in.ankushs.dbip.utils.Gzip;
import in.ankushs.dbip.utils.Strings;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.zip.GZIPInputStream;

/**
 * Singleton class responsible for loading the entire file into the JVM.
 *
 * @author Ankush Sharma
 */
@Slf4j
public final class ResourceImporter {

    private static final String COMMA_DELIMITER = ",";
    private static final Interner<String> interner = Interners.newWeakInterner();

    private static String[] parseRecord(final String csvRecord) {
        Assert.notEmptyString(csvRecord, "null or empty csvRecord was passed");

        return Arrays
                .stream(csvRecord.split(COMMA_DELIMITER))
                .map(str -> normalize(str.replace("\"", "")))
                .toArray(String[]::new);
    }

    private static String normalize(final String str) {
        String result = str;
        if (Strings.requiresTrimming(str)) {
            result = str.trim();
        }
        return result;
    }

    /**
     * Loads the file into JVM, reading line by line.
     * @param file The dbip-city-latest.csv.gz file as a File object.
     */
    public void load(final File file) {

        try {
            Assert.checkExpression(!Gzip.isGzipped(file), "Not a  gzip file");
        } catch (final IOException ex) {
            log.error("", ex);
        }

        try (val fis = new FileInputStream(file);
             val gis = new GZIPInputStream(fis);
             val decorator = new InputStreamReader(gis, StandardCharsets.UTF_8);
             val reader = new BufferedReader(decorator)
        )
        {
            log.debug("Reading dbip data from {}", file.getName());
            String line;
            int i = 0;
            val cache = new JavaTreeMapDbIpCacheImpl();
            while ((line = reader.readLine()) != null) {
                i++;
                val array = parseRecord(line);
                val geoAttributes = new GeoAttributesImpl
                        .Builder()
                        .withStartInetAddress(InetAddresses.forString(array[0]))
                        .withEndInetAddress(InetAddresses.forString(array[1]))
                        .withCountryCode(array[2])
                        .withCountry(Country.from(array[2]))
                        .withProvince(interner.intern(array[3]))
                        .withCity(interner.intern(array[4]))
                        .build();
                cache.put(geoAttributes);
                if (i % 100000 == 0) {
                    log.debug("Loaded {} entries", i);
                }
            }
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

}
