package net.minecraft.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

public enum EnumChatFormatting {
   BLACK("BLACK", '0', 0),
   DARK_BLUE("DARK_BLUE", '1', 1),
   DARK_GREEN("DARK_GREEN", '2', 2),
   DARK_AQUA("DARK_AQUA", '3', 3),
   DARK_RED("DARK_RED", '4', 4),
   DARK_PURPLE("DARK_PURPLE", '5', 5),
   GOLD("GOLD", '6', 6),
   GRAY("GRAY", '7', 7),
   DARK_GRAY("DARK_GRAY", '8', 8),
   BLUE("BLUE", '9', 9),
   GREEN("GREEN", 'a', 10),
   AQUA("AQUA", 'b', 11),
   RED("RED", 'c', 12),
   LIGHT_PURPLE("LIGHT_PURPLE", 'd', 13),
   YELLOW("YELLOW", 'e', 14),
   WHITE("WHITE", 'f', 15),
   OBFUSCATED("OBFUSCATED", 'k', true),
   BOLD("BOLD", 'l', true),
   STRIKETHROUGH("STRIKETHROUGH", 'm', true),
   UNDERLINE("UNDERLINE", 'n', true),
   ITALIC("ITALIC", 'o', true),
   RESET("RESET", 'r', -1);

   private static final Map nameMapping = Maps.newHashMap();
   private static final Pattern e = Pattern.compile("(?i)§[0-9A-FK-OR]");
   private final String name;
   private final char formattingCode;
   private final boolean fancyStyling;
   private final String controlString;
   private final int colorIndex;

   private static String func_175745_c(String var0) {
      return var0.toLowerCase().replaceAll("[^a-z]", "");
   }

   private EnumChatFormatting(String var3, char var4, int var5) {
      this(var3, var4, false, var5);
   }

   private EnumChatFormatting(String var3, char var4, boolean var5) {
      this(var3, var4, var5, -1);
   }

   private EnumChatFormatting(String var3, char var4, boolean var5, int var6) {
      this.name = var3;
      this.formattingCode = var4;
      this.fancyStyling = var5;
      this.colorIndex = var6;
      this.controlString = "§" + var4;
   }

   public int getColorIndex() {
      return this.colorIndex;
   }

   public boolean isFancyStyling() {
      return this.fancyStyling;
   }

   public boolean isColor() {
      return !this.fancyStyling && this != RESET;
   }

   public String getFriendlyName() {
      return this.name().toLowerCase();
   }

   public String toString() {
      return this.controlString;
   }

   public static String a(String var0) {
      return null;
   }

   public static EnumChatFormatting b(String var0) {
      return null;
   }

   public static EnumChatFormatting a(int var0) {
      return RESET;
   }

   public static Collection getValidValues(boolean var0, boolean var1) {
      ArrayList var2 = Lists.newArrayList();

      for(EnumChatFormatting var6 : values()) {
         if(var6.isColor()) {
            ;
         }

         if(var6.isFancyStyling()) {
            ;
         }

         var2.add(var6.getFriendlyName());
      }

      return var2;
   }

   static {
      for(EnumChatFormatting var10 : values()) {
         nameMapping.put(func_175745_c(var10.name), var10);
      }

   }
}
