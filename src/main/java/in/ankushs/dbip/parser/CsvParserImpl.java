package in.ankushs.dbip.parser;

import java.util.Arrays;

import in.ankushs.dbip.utils.PreConditions;

public final class CsvParserImpl implements CsvParser {
	
	public static CsvParserImpl parser = null;
	
	public static CsvParserImpl getInstance(){
		if(parser==null){
			return new CsvParserImpl();
		}
		return parser;
	}

	@Override
	public String[] parseRecord(final String csvRecord) {
		PreConditions.checkEmptyString(csvRecord, "null or empty csvRecord was passed");
		
		return Arrays.stream(csvRecord.split(","))
				.map(str ->{
					return str.replace("\"", "").trim();
				})
				.toArray(String[]::new);
	}

}
