package in.ankushs.dbip.importer;

import com.google.common.collect.Interner;
import com.google.common.collect.Interners;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.zip.GZIPInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.net.InetAddresses;

import in.ankushs.dbip.model.GeoAttributes;
import in.ankushs.dbip.model.GeoAttributesImpl;
import in.ankushs.dbip.parser.CsvParser;
import in.ankushs.dbip.parser.CsvParserImpl;
import in.ankushs.dbip.repository.DbIpRepository;
import in.ankushs.dbip.repository.JavaMapDbIpRepositoryImpl;
import in.ankushs.dbip.utils.CountryResolver;
import in.ankushs.dbip.utils.GzipUtils;
import in.ankushs.dbip.utils.PreConditions;
/**
 * 
 * Singleton class responsible for loading the entire file into the JVM.
 * @author Ankush Sharma
 */
public final class ResourceImporter {

	private static final Logger logger = LoggerFactory.getLogger(ResourceImporter.class);
	private final DbIpRepository repository = JavaMapDbIpRepositoryImpl.getInstance();
	private final CsvParser csvParser =  CsvParserImpl.getInstance();
	private static ResourceImporter instance = null;

	private Interner<String> interner = Interners.newWeakInterner();

	private ResourceImporter(){}
	
	public static ResourceImporter getInstance() {
		if (instance == null) {
			return new ResourceImporter();
		}
		return instance;
	}
	
	/**
	 * Loads the file into JVM,reading line by line.
	 * Also transforms each line into a GeoEntity object, and save the object into the 
	 * repository. 
	 * @param file The dbip-city-latest.csv.gz file as a File object.
	 */
	public void load(final File file) {
		
		try {
			PreConditions.checkExpression(!GzipUtils.isGzipped(file), "Not a  gzip file");
		} catch (final IOException ex) {
			logger.error("",ex);
		}

		String[] array = null;
		
		try (final InputStream fis = new FileInputStream(file);
			 final InputStream gis = new GZIPInputStream(fis);
			 final Reader decorator = new InputStreamReader(gis, StandardCharsets.UTF_8);
			 final BufferedReader reader = new BufferedReader(decorator);
		)
	{
			logger.debug("Reading dbip data from {}", file.getName());
			String line = null;
			int i = 0;
			while ((line = reader.readLine()) != null) {
				i++;
				array = csvParser.parseRecord(line);
				String isp = "";
				try{
					 isp = interner.intern(array[12]);
				}
				catch(Exception ex){
				}

				final GeoAttributes geoAttributes = new GeoAttributesImpl
						.Builder()
						.withStartInetAddress(InetAddresses.forString(array[0]))
						.withEndInetAddress(InetAddresses.forString(array[1]))
						.withCountryCode(array[2])
						.withCountry(CountryResolver.resolveToFullName(array[2]))
						.withProvince(interner.intern(array[3]))
						.withCity(interner.intern(array[4]))
						.withIsp(isp)
						.build();

				repository.save(geoAttributes);
				if (i % 100000 == 0) {
					logger.debug("Loaded {} entries", i);
				}
			}
		}

		catch (final Exception e) {

			throw new RuntimeException(e);
		}
	}
}
