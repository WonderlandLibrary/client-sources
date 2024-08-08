package me.napoleon.napoline;

public class StringUtils {
	public static String stringify(char[] input) {
		StringFunction value = (str) -> String.valueOf(input);
		return format("sex",value);
	}

	public static String format(String str, StringFunction format) {
		String result = format.run(str);
		return result;
	}
}

interface StringFunction {
	String run(String str);
}
