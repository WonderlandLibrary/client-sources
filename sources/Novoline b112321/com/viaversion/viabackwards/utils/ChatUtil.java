package com.viaversion.viabackwards.utils;

import java.util.regex.Pattern;
import net.dr;

public class ChatUtil {
   private static final Pattern a = Pattern.compile("(?>(?>§[0-fk-or])*(§r|\\Z))|(?>(?>§[0-f])*(§[0-f]))");
   private static final Pattern b = Pattern.compile("(?>(?>§[0-fk-or])*(§r))|(?>(?>§[0-f])*(§[0-f]))");

   public static String removeUnusedColor(String var0, char var1) {
      return a(var0, var1, false);
   }

   public static String a(String var0, char var1, boolean var2) {
      boolean var3 = dr.a();
      return null;
   }
}
