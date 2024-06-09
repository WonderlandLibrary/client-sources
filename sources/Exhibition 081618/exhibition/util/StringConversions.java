package exhibition.util;

public class StringConversions {
   public static Object castNumber(String newValueText, Object currentValue) {
      if (newValueText.contains(".")) {
         return newValueText.toLowerCase().contains("f") ? Float.parseFloat(newValueText) : Double.parseDouble(newValueText);
      } else {
         return isNumeric(newValueText) ? Integer.parseInt(newValueText) : newValueText;
      }
   }

   public static boolean isNumeric(String text) {
      try {
         Integer.parseInt(text);
         return true;
      } catch (NumberFormatException var2) {
         return false;
      }
   }
}
