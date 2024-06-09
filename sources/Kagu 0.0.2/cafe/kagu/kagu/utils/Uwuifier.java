package cafe.kagu.kagu.utils;

import org.apache.commons.lang3.RandomUtils;

/**
 * 
 * @author https://modrinth.com/user/Ran
 * https://modrinth.com/mod/uwuifier
 * Modified to be better
 */
public class Uwuifier {
	
	/**
	 * Manual override for us to decide which strings do and don't get uwuified
	 */
	private static boolean enabled = true;
	
	/**
	 * Enables the uwuifier
	 */
	public static void enable() {
		enabled = true;
	}
	
	/**
	 * Disables the uwuifier
	 */
	public static void disable() {
		enabled = false;
	}
	
	/**
	 * UwUifys a string
	 * @param stringToUwuify The string to uwuify
	 * @return The uwuified string
	 */
	public static String uwuify(String stringToUwuify) {
		if (!enabled)
			return stringToUwuify;
		stringToUwuify = uwuifyWithoutCuteFace(stringToUwuify);
		switch (RandomUtils.nextInt(0, 8)) {
			case 0:break;
			case 1:stringToUwuify += " >_<";break;
			case 2:stringToUwuify += " >~<";break;
			case 3:stringToUwuify += " >w<";break;
			case 4:stringToUwuify += " :3";break;
			case 5:stringToUwuify += " ;-;";break;
			case 6:stringToUwuify += " :P";break;
			case 7:stringToUwuify += " >//w//<";break;
		}
		return stringToUwuify;
	}
	
	/**
	 * UwUifys a string but doesn't add a cute face to the end
	 * @param stringToUwuify The string to uwuify
	 * @return The uwuified string
	 */
	public static String uwuifyWithoutCuteFace(String stringToUwuify) {
		if (!enabled)
			return stringToUwuify;
		stringToUwuify = stringToUwuify.toLowerCase().replaceAll("now", "nyow").replaceAll("you", "nyu").replaceAll("nya", "nya~~").replaceAll("hi", "hai")
				.replaceAll("novoline", "nowoline").replaceAll("r|l", "w").replaceAll("n([aeiou])", "ny$1").replaceAll("ove", "uve").replaceAll("uck", "uwq")
				.replaceFirst(" i", " i-i").replaceFirst("i ", "i-i ").replaceFirst("(?s)(.*)i-i-i", "$1i-i");
		return stringToUwuify;
	}
	
}
