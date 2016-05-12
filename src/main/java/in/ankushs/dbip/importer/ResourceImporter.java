package in.ankushs.dbip.importer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
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
	 * @param gzip The dbip-city-latest.csv.gz file as a File object.
	 * @throws IOException if {@code file} does not exist or is not gzipped.
	 */
	public void load(final File file) {
		
		try {
			PreConditions.checkExpression(!GzipUtils.isGzipped(file), "Not a  gzip file");
		} catch (final IOException ex) {
			logger.error("",ex);
		}
		
		try (InputStream fis = new FileInputStream(file);
			InputStream gis = new GZIPInputStream(fis);
			Reader decorator = new InputStreamReader(gis, StandardCharsets.UTF_8);
			BufferedReader reader = new BufferedReader(decorator);)
	{
			logger.debug("Reading dbip data from {}", file.getName());
			String line = null;
			int i = 0;
			while ((line = reader.readLine()) != null) {
				i++;
				final String[] array = csvParser.parseRecord(line);
				final GeoAttributes geoAttributes = new GeoAttributesImpl.Builder().withCity(array[4])
						.withCountry(CountryResolver.resolveToFullName(array[2])).withProvince(array[3])
						.withEndIp(InetAddresses.forString(array[1])).withStartIp(InetAddresses.forString(array[0]))
						.build();
				repository.save(geoAttributes);
				if (i % 100000 == 0) {
					logger.debug("Loaded {} entries", i);
				}
			}
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}
}
