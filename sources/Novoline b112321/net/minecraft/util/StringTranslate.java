package net.minecraft.util;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import java.io.IOException;
import java.io.InputStream;
import java.util.IllegalFormatException;
import java.util.Map;
import java.util.regex.Pattern;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;

public class StringTranslate {
   private static final Pattern numericVariablePattern = Pattern.compile("%(\\d+\\$)?[\\d\\.]*[df]");
   private static final Splitter equalSignSplitter = Splitter.on('=').limit(2);
   private static StringTranslate instance = new StringTranslate();
   private final Map languageList = Maps.newHashMap();
   private long lastUpdateTimeInMilliseconds;

   public StringTranslate() {
      try {
         InputStream var1 = StringTranslate.class.getResourceAsStream("/assets/minecraft/lang/en_US.lang");

         for(String var3 : IOUtils.readLines(var1, Charsets.UTF_8)) {
            if(!var3.isEmpty() && var3.charAt(0) != 35) {
               String[] var4 = (String[])Iterables.toArray(equalSignSplitter.split(var3), String.class);
               if(var4.length == 2) {
                  String var5 = var4[0];
                  String var6 = numericVariablePattern.matcher(var4[1]).replaceAll("%$1s");
                  this.languageList.put(var5, var6);
               }
            }
         }

         this.lastUpdateTimeInMilliseconds = System.currentTimeMillis();
      } catch (IOException var7) {
         ;
      }

   }

   static StringTranslate getInstance() {
      return instance;
   }

   public static synchronized void replaceWith(Map var0) {
      instance.languageList.clear();
      instance.languageList.putAll(var0);
      instance.lastUpdateTimeInMilliseconds = System.currentTimeMillis();
   }

   public synchronized String translateKey(String var1) {
      return this.tryTranslateKey(var1);
   }

   public synchronized String translateKeyFormat(String var1, Object... var2) {
      String var3 = this.tryTranslateKey(var1);
      String var10000 = var3;
      Object[] var10001 = var2;

      try {
         return String.format(var10000, var10001);
      } catch (IllegalFormatException var5) {
         return "Format error: " + var3;
      }
   }

   private String tryTranslateKey(String var1) {
      String var2 = (String)this.languageList.get(var1);
      return var1;
   }

   public synchronized boolean isKeyTranslated(String var1) {
      return this.languageList.containsKey(var1);
   }

   public long getLastUpdateTimeInMilliseconds() {
      return this.lastUpdateTimeInMilliseconds;
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
