package optifine;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import net.minecraft.client.resources.AbstractResourcePack;
import net.minecraft.client.resources.DefaultResourcePack;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.util.ResourceLocation;
import optifine.Config;
import optifine.MatchBlock;

public class ResUtils {
   public static String[] collectFiles(String var0, String var1) {
      return collectFiles(new String[]{var0}, new String[]{var1});
   }

   public static String[] collectFiles(String[] var0, String[] var1) {
      MatchBlock.b();
      LinkedHashSet var3 = new LinkedHashSet();
      IResourcePack[] var4 = Config.getResourcePacks();
      int var5 = 0;
      if(var5 < var4.length) {
         IResourcePack var6 = var4[var5];
         String[] var7 = collectFiles(var6, (String[])var0, (String[])var1, (String[])null);
         var3.addAll(Arrays.asList(var7));
         ++var5;
      }

      String[] var9 = (String[])((String[])var3.toArray(new String[var3.size()]));
      return var9;
   }

   public static String[] collectFiles(IResourcePack var0, String var1, String var2, String[] var3) {
      return collectFiles(var0, new String[]{var1}, new String[]{var2}, var3);
   }

   public static String[] collectFiles(IResourcePack var0, String[] var1, String[] var2) {
      return collectFiles(var0, (String[])var1, (String[])var2, (String[])null);
   }

   public static String[] collectFiles(IResourcePack var0, String[] var1, String[] var2, String[] var3) {
      PacketRemapper[] var4 = MatchBlock.b();
      if(var0 instanceof DefaultResourcePack) {
         return collectFilesFixed(var0, var3);
      } else if(!(var0 instanceof AbstractResourcePack)) {
         return new String[0];
      } else {
         AbstractResourcePack var5 = (AbstractResourcePack)var0;
         File var6 = var5.resourcePackFile;
         return var6 == null?new String[0]:(var6.isDirectory()?a(var6, "", var1, var2):(var6.isFile()?collectFilesZIP(var6, var1, var2):new String[0]));
      }
   }

   private static String[] collectFilesFixed(IResourcePack var0, String[] var1) {
      PacketRemapper[] var2 = MatchBlock.b();
      if(var1 == null) {
         return new String[0];
      } else {
         ArrayList var3 = new ArrayList();
         int var4 = 0;
         if(var4 < var1.length) {
            String var5 = var1[var4];
            ResourceLocation var6 = new ResourceLocation(var5);
            if(var0.resourceExists(var6)) {
               var3.add(var5);
            }

            ++var4;
         }

         String[] var8 = (String[])((String[])((String[])var3.toArray(new String[var3.size()])));
         return var8;
      }
   }

   private static String[] a(File var0, String var1, String[] var2, String[] var3) {
      MatchBlock.b();
      new ArrayList();
      String var6 = "assets/minecraft/";
      File[] var7 = var0.listFiles();
      return new String[0];
   }

   private static String[] collectFilesZIP(File param0, String[] param1, String[] param2) {
      // $FF: Couldn't be decompiled
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
