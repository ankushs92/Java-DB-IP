package in.ankushs.dbip.parser;

import in.ankushs.dbip.utils.PreConditions;

import java.util.Arrays;

public final class CsvParserImpl implements CsvParser {
	
	public static CsvParserImpl parser = null;
	
	private CsvParserImpl(){}
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
