package rina.turok.turok.values;

public class TurokString {
   private String name;
   private String tag;
   private String value;

   public TurokString(String name, String tag, String string) {
      this.name = name;
      this.tag = tag;
      this.value = string;
   }

   public void set_value(String string) {
      this.value = string;
   }

   public String get_name() {
      return this.name;
   }

   public String get_tag() {
      return this.tag;
   }

   public String get_value() {
      return this.value;
   }

   public static String to_string(String value) {
      return value;
   }

   public static String to_string(boolean value) {
      return Boolean.toString(value);
   }

   public static String to_string(double value) {
      return Double.toString(value);
   }

   public static String to_string(float value) {
      return Float.toString(value);
   }

   public static String to_string(int value) {
      return Integer.toString(value);
   }
}
