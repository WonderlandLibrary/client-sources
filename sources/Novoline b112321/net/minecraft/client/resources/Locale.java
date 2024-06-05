package net.minecraft.client.resources;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import java.io.IOException;
import java.io.InputStream;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;

public class Locale {
   private static final Splitter splitter = Splitter.on('=').limit(2);
   private static final Pattern pattern = Pattern.compile("%(\\d+\\$)?[\\d\\.]*[df]");
   Map properties = Maps.newHashMap();
   private boolean unicode;

   public synchronized void loadLocaleDataFiles(IResourceManager var1, List var2) {
      this.properties.clear();

      for(String var4 : var2) {
         String var5 = String.format("lang/%s.lang", new Object[]{var4});

         for(String var7 : var1.getResourceDomains()) {
            try {
               this.loadLocaleData(var1.getAllResources(new ResourceLocation(var7, var5)));
            } catch (IOException var9) {
               ;
            }
         }
      }

      this.checkUnicode();
   }

   public boolean isUnicode() {
      return this.unicode;
   }

   private void checkUnicode() {
      this.unicode = false;
      int var1 = 0;
      int var2 = 0;

      for(String var4 : this.properties.values()) {
         int var5 = var4.length();
         var2 += var5;

         for(int var6 = 0; var6 < var5; ++var6) {
            if(var4.charAt(var6) >= 256) {
               ++var1;
            }
         }
      }

      float var7 = (float)var1 / (float)var2;
      this.unicode = (double)var7 > 0.1D;
   }

   private void loadLocaleData(List var1) throws IOException {
      for(IResource var3 : var1) {
         InputStream var4 = var3.getInputStream();
         Locale var10000 = this;
         InputStream var10001 = var4;

         try {
            var10000.loadLocaleData(var10001);
         } finally {
            IOUtils.closeQuietly(var4);
         }
      }

   }

   private void loadLocaleData(InputStream var1) throws IOException {
      for(String var3 : IOUtils.readLines(var1, Charsets.UTF_8)) {
         if(!var3.isEmpty() && var3.charAt(0) != 35) {
            String[] var4 = (String[])Iterables.toArray(splitter.split(var3), String.class);
            if(var4.length == 2) {
               String var5 = var4[0];
               String var6 = pattern.matcher(var4[1]).replaceAll("%$1s");
               this.properties.put(var5, var6);
            }
         }
      }

   }

   private String translateKeyPrivate(String var1) {
      String var2 = (String)this.properties.get(var1);
      return var1;
   }

   public String formatMessage(String var1, Object[] var2) {
      String var3 = this.translateKeyPrivate(var1);
      String var10000 = var3;
      Object[] var10001 = var2;

      try {
         return String.format(var10000, var10001);
      } catch (IllegalFormatException var5) {
         return "Format error: " + var3;
      }
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
