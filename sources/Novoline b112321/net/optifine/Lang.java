package net.optifine;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.IResourcePack;
import net.optifine.Config;
import net.optifine.MatchBlock;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;

public class Lang {
   private static final Splitter splitter = Splitter.on('=').limit(2);
   private static final Pattern pattern = Pattern.compile("%(\\d+\\$)?[\\d\\.]*[df]");

   public static void resourcesReloaded() {
      Map var1 = I18n.getLocaleProperties();
      MatchBlock.b();
      ArrayList var2 = new ArrayList();
      String var3 = "optifine/lang/";
      String var4 = "en_US";
      String var5 = ".lang";
      var2.add(var3 + var4 + var5);
      if(!Config.getGameSettings().language.equals(var4)) {
         var2.add(var3 + Config.getGameSettings().language + var5);
      }

      String[] var6 = (String[])var2.toArray(new String[var2.size()]);
      loadResources(Config.getDefaultResourcePack(), var6, var1);
      IResourcePack[] var7 = Config.getResourcePacks();
      int var8 = 0;
      if(var8 < var7.length) {
         IResourcePack var9 = var7[var8];
         loadResources(var9, var6, var1);
         ++var8;
      }

   }

   private static void loadResources(IResourcePack param0, String[] param1, Map param2) {
      // $FF: Couldn't be decompiled
   }

   public static void loadLocaleData(InputStream var0, Map var1) throws IOException {
      MatchBlock.b();
      Iterator var3 = IOUtils.readLines(var0, Charsets.UTF_8).iterator();
      if(var3.hasNext()) {
         String var4 = (String)var3.next();
         if(!var4.isEmpty() && var4.charAt(0) != 35) {
            String[] var5 = (String[])Iterables.toArray(splitter.split(var4), String.class);
            if(var5 != null && var5.length == 2) {
               String var6 = var5[0];
               String var7 = pattern.matcher(var5[1]).replaceAll("%$1s");
               var1.put(var6, var7);
            }
         }
      }

   }

   public static String get(String var0) {
      return I18n.format(var0, new Object[0]);
   }

   public static String get(String var0, String var1) {
      MatchBlock.b();
      String var3 = I18n.format(var0, new Object[0]);
      return var3 != null && !var3.equals(var0)?var3:var1;
   }

   public static String getOn() {
      return I18n.format("options.on", new Object[0]);
   }

   public static String getOff() {
      return I18n.format("options.off", new Object[0]);
   }

   public static String getFast() {
      return I18n.format("options.graphics.fast", new Object[0]);
   }

   public static String getFancy() {
      return I18n.format("options.graphics.fancy", new Object[0]);
   }

   public static String getDefault() {
      return I18n.format("generator.default", new Object[0]);
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
