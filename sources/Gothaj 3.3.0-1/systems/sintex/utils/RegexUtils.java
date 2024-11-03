package systems.sintex.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {
   public static String findFirst(String string, String regex) {
      Pattern pattern = Pattern.compile(regex);
      Matcher matcher = pattern.matcher(string);
      return matcher.find() ? matcher.group() : null;
   }
}
