package lunadevs.luna.Connector.UI.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.newdawn.slick.TrueTypeFont;

import com.zCore.Core.zCore;

import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class ChatUtil {
	
	private static Pattern pattern;
	private static Matcher matcher;
	static zCore z = new zCore();
 
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,3})$";
	
	public static boolean isAllowedCharacter(char character) {
        return character != 167 && character >= 32 && character != 127;
    }
	
	public static boolean isValidUsername(String line) {
		for(char c : line.toCharArray()) {
			if (!Character.isLetterOrDigit(c) && c != '_') {
				return false;
			}
		}
		return true;
	}
	
    public static void send(String message) {
        if (z.w() != null && z.player() != null) {
            message = "[v][[t]KiLO[v]][t]: " + message;
            z.player().addChatMessage(makeComponent(message));
        }
    }
    
    public static String addFormat(final String message, final String regex) {
        return message.replaceAll("(?i)" + regex + "([0-9a-fklmnor])", "§$1");
    }
    
    private static IChatComponent makeComponent(String message) {
        message = message.replaceAll("\t", "    ");
        for (final String key : ColorUtil.colors.keySet()) {
            final ChatColor c = ColorUtil.colors.get(key);
            message = message.replace(c.regex, "&" + c.color);
        }
        message = addFormat(message, "&");
        final String[] parts = message.split("§");
        final IChatComponent icc = new ChatComponentText("");
        String[] array;
        for (int length = (array = parts).length, j = 0; j < length; ++j) {
            String part = array[j];
            if (part.length() > 0) {
                final char c2 = part.charAt(0);
                part = part.substring(1);
                final ChatStyle style = new ChatStyle();
                switch (c2) {
                    case 'k': {
                        style.setObfuscated(true);
                        break;
                    }
                    case 'l': {
                        style.setBold(true);
                        break;
                    }
                    case 'm': {
                        style.setUnderlined(true);
                        break;
                    }
                    case 'n': {
                        style.setStrikethrough(true);
                        break;
                    }
                    case 'o': {
                        style.setItalic(true);
                        break;
                    }
                    default: {
                        style.setColor(charToFormat(c2));
                        break;
                    }
                }
                final String[] lines = part.split("\n");
                for (int i = 0; i < lines.length; ++i) {
                    final String line = lines[i];
                    icc.appendSibling(new ChatComponentText(line).setChatStyle(style));
                    if (i != lines.length - 1) {
                        icc.appendSibling(new ChatComponentText("\n"));
                    }
                }
            }
        }
        return icc;
    }
	
    public static EnumChatFormatting charToFormat(final char c) {
        EnumChatFormatting[] values;
        for (int length = (values = EnumChatFormatting.values()).length, i = 0; i < length; ++i) {
            final EnumChatFormatting ecf = values[i];
        }
        return EnumChatFormatting.RESET;
    }
    
	public static boolean isValidEmail(String line) {
		for(char c : line.toCharArray()) {
			if (!Character.isLetterOrDigit(c) && c != '_' && c != '@' && c != '.') {
				return false;
			}
		}
		if (!validateEmail(line)) {
			return false;
		}
		return true;
	}
 
	public static boolean validateEmail(final String hex) {
		pattern = Pattern.compile(EMAIL_PATTERN);
 
		matcher = pattern.matcher(hex);
		return matcher.matches();
	}
	
	public static boolean isNumber(String line) {
		try {
			Integer.parseInt(line);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static String insertAt(String line, String character, int index) {
		try {
			String pref = line.substring(0, index);
			String suff = line.substring(index, line.length());
			return pref+character+suff;
		} catch (Exception e) {
			return line;
		}
	}
	
	public static String replaceAt(String line, String character, int start, int end) {
		start = Math.min(Math.max(0, start), line.length());
		end = Math.min(Math.max(0, end), line.length());
		
		String pref = line.substring(0, start>end?end:start);
		String suff = line.substring(start>end?start:end, line.length());
		return pref+character+suff;
	}
	
	public static int getMousePos(String text, TrueTypeFont font, float x, int mx, float scroll) {
		int before = -1;
		int after = -1;
		for(int i = 0; i <= text.length(); i++) {
			int wb = font.getWidth(text.substring(0, Math.max(i-1, 0)));
			int wa = font.getWidth(text.substring(0, i));
			if (wa > mx-x-scroll) {
				before = wb;
				after = wa;
				break;
			}
		}
		if (mx-x-scroll >= font.getWidth(text)) {
			after = font.getWidth(text);
		}
		int to = (Math.abs(mx-x-scroll-before) < Math.abs(mx-x-scroll-after))?before:after;
		for(int i = 0; i < text.length(); i++) {
			if (font.getWidth(text.substring(0, i)) == to) {
				to = i;
				break;
			}
		}
		if (to > text.length()) {
			to = text.length();
		}
		return to;
	}
	
	public static Format getChatFormatter(char c) {
		if ((c >= '0' && c <= '9') || (c >= 'a' && c <='f')) {
			return Format.COLOR;
		}
		switch (c) {
		case 'k':
			return Format.FORMAT;
		case 'l':
			return Format.FORMAT;
		case 'm':
			return Format.FORMAT;
		case 'n':
			return Format.FORMAT;
		case 'o':
			return Format.FORMAT;
		case 'r':
			return Format.FORMAT;
		default:
			return Format.NONE;
		}
	}
	
	public static org.newdawn.slick.Color getColorFromChar(char c) {
		char[] chars = new char[] {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
		int[] colors = new int[] {0xFF000000, 0xFF0000AA, 0xFF00AA00, 0xFF00AAAA, 0xFFAA0000, 0xFFAA00AA, 0xFFFFAA00, 0xFFAAAAAA, 0xFF555555, 0xFF5555FF, 0xFF55FF55, 0xFF55FFFF, 0xFFFF5555, 0xFFFF55FF, 0xFFFFFF55, 0xFFFEFEFE};
		
		int color = -1;
		
		for(int i = 0; i < chars.length; i++) {
			if (chars[i] == c) {
				color = colors[i];
				break;
			}
		}
		
		if (color == -1) {
			return null;
		}
		return new org.newdawn.slick.Color(color);
	}
	
	public static String clearFormat(String s) {
		List<String> formats = new ArrayList<String>();
		
		for(int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '\u00a7') {
				formats.add(s.substring(i, Math.min(i+2, s.length())));
			}
		}
		
		for(String st : formats) {
			s = s.replace(st, "");
		}
		return s;
	}
}