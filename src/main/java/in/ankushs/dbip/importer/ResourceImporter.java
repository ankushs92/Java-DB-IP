package in.ankushs.dbip.importer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.net.InetAddresses;

import in.ankushs.dbip.api.GeoEntity;
import in.ankushs.dbip.model.GeoAttributes;
import in.ankushs.dbip.model.GeoAttributesImpl;
import in.ankushs.dbip.parser.CsvParserImpl;
import in.ankushs.dbip.repository.DbIpRepository;
import in.ankushs.dbip.repository.JavaMapDbIpRepositoryImpl;
import in.ankushs.dbip.utils.CountryResolver;

public final class ResourceImporter {
	private static final Logger logger = LoggerFactory.getLogger(ResourceImporter.class);
	private final DbIpRepository repository = new JavaMapDbIpRepositoryImpl();
	public ResourceImporter instance =  null;
	
	public ResourceImporter getInstance(){
		if(instance==null){
			return new ResourceImporter();
		}
		return instance;
	}
	
	public void load(final File file){
		try (InputStream fis = new FileInputStream(file);
				InputStream gis = new GZIPInputStream(fis);
				Reader decorator = new InputStreamReader(gis, StandardCharsets.UTF_8);
				BufferedReader reader = new BufferedReader(decorator);) 
		{
			logger.debug("Reading dbip data from {}" ,  file.getName());
			String line = null;
			int i = 0;
			while ((line = reader.readLine()) != null) {
				i++;
				final String[] array  = new CsvParserImpl().parseRecord(line);
				final GeoAttributes geoAttributes = new GeoAttributesImpl.Builder()
						.withCity(array[4])
						.withCountry(CountryResolver.resolveToFullName(array[2]))
						.withState(array[3])
						.withEndIp(InetAddresses.forString(array[1]))
						.withStartIp(InetAddresses.forString(array[0])).build();
				repository.save(geoAttributes);
				if (i % 100000 == 0) {
					logger.debug("Loaded {} entries" , i);
				}
			}
		} catch (final  Exception e) {
			throw new RuntimeException(e);
		}
	}
}
