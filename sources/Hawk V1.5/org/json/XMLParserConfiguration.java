package org.json;

public class XMLParserConfiguration {
   public final boolean keepStrings;
   public static final XMLParserConfiguration KEEP_STRINGS = new XMLParserConfiguration(true);
   public static final XMLParserConfiguration ORIGINAL = new XMLParserConfiguration();
   public final boolean convertNilAttributeToNull;
   public final String cDataTagName;

   public XMLParserConfiguration(boolean var1, String var2) {
      this.keepStrings = var1;
      this.cDataTagName = var2;
      this.convertNilAttributeToNull = false;
   }

   public XMLParserConfiguration() {
      this(false, "content", false);
   }

   public XMLParserConfiguration(boolean var1) {
      this(var1, "content", false);
   }

   public XMLParserConfiguration(boolean var1, String var2, boolean var3) {
      this.keepStrings = var1;
      this.cDataTagName = var2;
      this.convertNilAttributeToNull = var3;
   }

   public XMLParserConfiguration(String var1) {
      this(false, var1, false);
   }
}
