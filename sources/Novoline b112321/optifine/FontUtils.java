package optifine;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;
import net.minecraft.util.ResourceLocation;
import optifine.Config;
import optifine.MatchBlock;

public class FontUtils {
   public static Properties readFontProperties(ResourceLocation var0) {
      MatchBlock.b();
      String var2 = var0.getResourcePath();
      Properties var3 = new Properties();
      String var4 = ".png";
      if(!var2.endsWith(var4)) {
         return var3;
      } else {
         String var5 = var2.substring(0, var2.length() - var4.length()) + ".properties";

         try {
            ResourceLocation var6 = new ResourceLocation(var0.getResourceDomain(), var5);
            InputStream var7 = Config.getResourceStream(Config.getResourceManager(), var6);
            return var3;
         } catch (FileNotFoundException var8) {
            ;
         } catch (IOException var9) {
            var9.printStackTrace();
         }

         return var3;
      }
   }

   public static void readCustomCharWidths(Properties var0, float[] var1) {
      MatchBlock.b();
      Iterator var3 = var0.keySet().iterator();
      if(var3.hasNext()) {
         Object var4 = var3.next();
         String var5 = (String)var4;
         String var6 = "width.";
         if(var5.startsWith(var6)) {
            String var7 = var5.substring(var6.length());
            int var8 = Config.parseInt(var7, -1);
            if(var8 >= 0 && var8 < var1.length) {
               String var9 = var0.getProperty(var5);
               float var10 = Config.parseFloat(var9, -1.0F);
               if(var10 >= 0.0F) {
                  var1[var8] = var10;
               }
            }
         }
      }

   }

   public static float readFloat(Properties var0, String var1, float var2) {
      MatchBlock.b();
      String var4 = var0.getProperty(var1);
      if(var4 == null) {
         return var2;
      } else {
         float var5 = Config.parseFloat(var4, Float.MIN_VALUE);
         if(var5 == Float.MIN_VALUE) {
            Config.warn("Invalid value for " + var1 + ": " + var4);
            return var2;
         } else {
            return var5;
         }
      }
   }

   public static ResourceLocation getHdFontLocation(ResourceLocation var0) {
      PacketRemapper[] var1 = MatchBlock.b();
      if(!Config.isCustomFonts()) {
         return var0;
      } else if(var0 == null) {
         return var0;
      } else {
         String var2 = var0.getResourcePath();
         String var3 = "textures/";
         String var4 = "mcpatcher/";
         if(!var2.startsWith(var3)) {
            return var0;
         } else {
            var2 = var2.substring(var3.length());
            var2 = var4 + var2;
            ResourceLocation var5 = new ResourceLocation(var0.getResourceDomain(), var2);
            return Config.hasResource(Config.getResourceManager(), var5)?var5:var0;
         }
      }
   }

   private static FileNotFoundException a(FileNotFoundException var0) {
      return var0;
   }
}
