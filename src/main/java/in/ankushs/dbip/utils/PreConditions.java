package in.ankushs.dbip.utils;


/**
 * Contains some run of the mill static methods for pre-conditions validations.
 * @author Ankush Sharma
 */

public final class PreConditions {
	
	
  private PreConditions(){}
	
  /**
   * Ensures the object passed to the method is not null
   *
   * @param t The object against which the validation will be performed
   * @param errorMsg The message to throw in case {@code t} turns out to be null.
   * @throws IllegalArgumentException if {@code t} is null
   */
	public static <T> void checkNull(final T  t , final String errorMsg){
		if(t==null){
			throw new IllegalArgumentException(errorMsg);
		}
	}
	
	/**
	  * Ensures the string passed to the method is not empty .
	  * The string will be considered empty if it is null or has no text after being trimmed.
	  *
	  * @param str The string against which the validation will be performed
      * @param errorMsg The message to throw in case {@code str} turns out to have no text.
      * @throws IllegalArgumentException if {@code str} has no text
	 */
	public static void checkEmptyString(final String str , final String errorMsg){
		if(!Strings.hasText(str)){
			throw new IllegalArgumentException(errorMsg);
		}
	}
	
	/**
	  * Ensures the truth of the expression passed.
	  *
	  * @param expression The expression being evaluated 
     * @param errorMsg The message to throw in case {@code expression} turns out to be false.
     * @throws IllegalArgumentException if {@code expression} is expression
	 */
	public static  void checkExpression(final boolean expression , final String errorMsg){
		if(expression){
			throw new IllegalArgumentException(errorMsg);
		}
	}
}
