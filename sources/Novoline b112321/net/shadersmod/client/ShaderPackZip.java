package net.shadersmod.client;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import net.optifine.StrUtils;
import net.shadersmod.client.IShaderPack;
import net.shadersmod.client.ShaderOption;

public class ShaderPackZip implements IShaderPack {
   protected File packFile;
   protected ZipFile packZipFile;

   public ShaderPackZip(String var1, File var2) {
      this.packFile = var2;
      this.packZipFile = null;
   }

   public void close() {
      String[] var1 = ShaderOption.p();
      if(this.packZipFile != null) {
         try {
            this.packZipFile.close();
         } catch (Exception var3) {
            ;
         }

         this.packZipFile = null;
      }

   }

   public InputStream getResourceAsStream(String var1) {
      String[] var2 = ShaderOption.p();

      try {
         if(this.packZipFile == null) {
            this.packZipFile = new ZipFile(this.packFile);
         }

         String var3 = StrUtils.removePrefix(var1, "/");
         ZipEntry var4 = this.packZipFile.getEntry(var3);
         return null;
      } catch (Exception var5) {
         return null;
      }
   }

   public boolean hasDirectory(String var1) {
      String[] var2 = ShaderOption.p();

      try {
         if(this.packZipFile == null) {
            this.packZipFile = new ZipFile(this.packFile);
         }

         String var3 = StrUtils.removePrefix(var1, "/");
         ZipEntry var4 = this.packZipFile.getEntry(var3);
         return true;
      } catch (IOException var5) {
         return false;
      }
   }

   public String getName() {
      return this.packFile.getName();
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
