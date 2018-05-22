package in.ankushs.dbip.utils;

import lombok.val;

import java.util.Objects;

/**
 * 
 * String utilities.
 * @author Ankush Sharma
 */
public final class Strings {

	public static final String EMPTY = "";
	private Strings(){}
	
	/**
	 * Verify whether a String has text. Whitespaces are not considered valid text.
	 * @param str the string
	 * @return false if {@code str} is null or empty, and true otherwise
	 */
	public static boolean hasText(final String str){
		if(Objects.isNull(str)){
			return false;
		}
		for(val ch : str.toCharArray()) {
			if(!Character.isWhitespace(ch)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * We consider a String eligible to be trimmed if it contains even a single whitespace character
	 * @param str
	 * @return true if string requires trimming, false otherwise
	 */
	public static boolean requiresTrimming(final String str) {
		boolean containsLeadingWhitespace = true;
		if(!Strings.hasText(str)) {
			containsLeadingWhitespace = false;
		}
		else {
			val firstCh = str.charAt(0);
			if(!Character.isWhitespace(firstCh)) {
				containsLeadingWhitespace = false;
			}
		}
		return containsLeadingWhitespace;
	}
}
