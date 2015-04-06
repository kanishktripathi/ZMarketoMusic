/*
 * 
 */
package com.kanishk.marketo.navigate;

import com.kanishk.marketo.constants.Constants;
import com.kanishk.marketo.constants.ErrorMessages;
import com.kanishk.marketo.constants.MarketoException;

/**
 * The Class Utils. Utility class for performing common operations for validation. 
 */
public class Utils {
	
	/**
	 * Checks if is array size matches the length parameter.
	 *
	 * @param array the input array
	 * @param length the check value to match
	 * @return true, if is array length equals length parameter
	 */
	public static boolean isArrayLengthMatches(String[] array, int length) {
		return array.length == length;
	}
	
	/**
	 * Prints the given text.
	 * @param text the text to print
	 */
	public static void print(String text) {
		System.out.println(text);
	}
	
	/**
	 * Validation for the commands of type &lt;command-name &gt; &lt;option &gt;
	 * Example: print song, print artist, sort artist.
	 *
	 * @param commandOptions the command options
	 * @param validOptions the set of valid command to validate
	 * @return true, if successful
	 * @throws MarketoException the marketo exception
	 */
	public static boolean checkStringOption(String commandOptions[], String... validOptions) 
			throws MarketoException {
		boolean validCommandOption = false;
		if(isArrayLengthMatches(commandOptions, 2)) {
			commandOptions[1] = commandOptions[1].toLowerCase();
			for(String option : validOptions) {
				if(option.equals(commandOptions[1])) {
					validCommandOption = true;
					break;
				}
			}
		}
		if(!validCommandOption) {
			throw new MarketoException(ErrorMessages.OPTION_ERROR);
		}
		return validCommandOption;
	}
	
	/**
	 * Validate and return id. Validates the command and extracts id from it.
	 * Currently checks for integer based id. If the id is a valid number, 
	 * returns the extracted id, else throws exception.
	 *
	 * @param commandOptions the command options array
	 * @return true, if successful
	 * @throws MarketoException the marketo exception
	 */
	public static int validateAndReturnId(String[] commandOptions) throws MarketoException {
		if(isArrayLengthMatches(commandOptions, 2)) {
			String id = commandOptions[1];
			try {
				return Integer.parseInt(id);
			} catch(NumberFormatException e) {
				throw new MarketoException(ErrorMessages.ID_ERROR);
			}
		} else {
			throw new MarketoException(ErrorMessages.ID_ERROR);
		}
	}
	
	
	/**
	 * Validate and search param. Takes commandText for search from the user
	 * and validates it. The command should be in the form: search &lt;<search option(title or
	 * artist)&gt; "search text". Example: To search for songs by Metallica:
	 * search artist "Metallica"
	 * @param commandText the command text
	 * @return the string[] returns the string array containing the search option and
	 * search text
	 * @throws MarketoException the marketo exception
	 */
	public static String[] validateAndReturnSearchParam(String commandText) 
			throws MarketoException {
		commandText = commandText.toLowerCase();
		if(commandText.lastIndexOf('\"') == commandText.length() - 1) {//If the command ends with "
			String[] splitFirst = commandText.split("\"");// Split search text
			if(splitFirst.length == 2) {
				String[] splitNext = splitFirst[0].split(" ");// Split command option
				if(splitNext.length == 2) {// Check if the command is in for search <search option>
					splitNext[0] = splitNext[1];
					splitNext[1] = splitFirst[1];// Put search text and search option together
					if(Constants.TITLE.equals(splitNext[0]) || Constants.ARTIST.equals(splitNext[0])) {
						return splitNext;
					}
				}
			}			
		}
		throw new MarketoException(ErrorMessages.INVALID_SEARCH);
	}
}
