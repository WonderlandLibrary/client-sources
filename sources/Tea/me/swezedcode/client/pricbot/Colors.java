package me.swezedcode.client.pricbot;

public class Colors {
	public static final String NORMAL = "\u000f";
	public static final String BOLD = "\u0002";
	public static final String UNDERLINE = "\u001f";
	public static final String REVERSE = "\u0016";
	public static final String WHITE = "\u000300";
	public static final String BLACK = "\u000301";
	public static final String DARK_BLUE = "\u000302";
	public static final String DARK_GREEN = "\u000303";
	public static final String RED = "\u000304";
	public static final String BROWN = "\u000305";
	public static final String PURPLE = "\u000306";
	public static final String OLIVE = "\u000307";
	public static final String YELLOW = "\u000308";
	public static final String GREEN = "\u000309";
	public static final String TEAL = "\u000310";
	public static final String CYAN = "\u000311";
	public static final String BLUE = "\u000312";
	public static final String MAGENTA = "\u000313";
	public static final String DARK_GRAY = "\u000314";
	public static final String LIGHT_GRAY = "\u000315";

	public static String removeColors(final String line) {
		final int length = line.length();
		final StringBuffer buffer = new StringBuffer();
		int i = 0;
		while (i < length) {
			char ch = line.charAt(i);
			if (ch == '\u0003') {
				if (++i >= length) {
					continue;
				}
				ch = line.charAt(i);
				if (!Character.isDigit(ch)) {
					continue;
				}
				if (++i < length) {
					ch = line.charAt(i);
					if (Character.isDigit(ch)) {
						++i;
					}
				}
				if (i >= length) {
					continue;
				}
				ch = line.charAt(i);
				if (ch != ',') {
					continue;
				}
				if (++i < length) {
					ch = line.charAt(i);
					if (Character.isDigit(ch)) {
						if (++i >= length) {
							continue;
						}
						ch = line.charAt(i);
						if (!Character.isDigit(ch)) {
							continue;
						}
						++i;
					} else {
						--i;
					}
				} else {
					--i;
				}
			} else if (ch == '\u000f') {
				++i;
			} else {
				buffer.append(ch);
				++i;
			}
		}
		return buffer.toString();
	}

	public static String removeFormatting(final String line) {
		final int length = line.length();
		final StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; ++i) {
			final char ch = line.charAt(i);
			if (ch != '\u000f' && ch != '\u0002' && ch != '\u001f' && ch != '\u0016') {
				buffer.append(ch);
			}
		}
		return buffer.toString();
	}

	public static String removeFormattingAndColors(final String line) {
		return removeFormatting(removeColors(line));
	}
}
