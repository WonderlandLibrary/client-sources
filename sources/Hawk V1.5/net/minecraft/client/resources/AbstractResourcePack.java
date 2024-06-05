package net.minecraft.client.resources;

import com.google.common.base.Charsets;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractResourcePack implements IResourcePack {
   private static final String __OBFID = "CL_00001072";
   public final File resourcePackFile;
   private static final Logger resourceLog = LogManager.getLogger();

   public IMetadataSection getPackMetadata(IMetadataSerializer var1, String var2) throws IOException {
      return readMetadata(var1, this.getInputStreamByName("pack.mcmeta"), var2);
   }

   public InputStream getInputStream(ResourceLocation var1) throws IOException {
      return this.getInputStreamByName(locationToName(var1));
   }

   protected void logNameNotLowercase(String var1) {
      resourceLog.warn("ResourcePack: ignored non-lowercase namespace: {} in {}", new Object[]{var1, this.resourcePackFile});
   }

   public String getPackName() {
      return this.resourcePackFile.getName();
   }

   protected abstract InputStream getInputStreamByName(String var1) throws IOException;

   public boolean resourceExists(ResourceLocation var1) {
      return this.hasResourceName(locationToName(var1));
   }

   public BufferedImage getPackImage() throws IOException {
      return TextureUtil.func_177053_a(this.getInputStreamByName("pack.png"));
   }

   public AbstractResourcePack(File var1) {
      this.resourcePackFile = var1;
   }

   static IMetadataSection readMetadata(IMetadataSerializer var0, InputStream var1, String var2) {
      JsonObject var3 = null;
      BufferedReader var4 = null;

      try {
         try {
            var4 = new BufferedReader(new InputStreamReader(var1, Charsets.UTF_8));
            var3 = (new JsonParser()).parse(var4).getAsJsonObject();
         } catch (RuntimeException var9) {
            throw new JsonParseException(var9);
         }

         IOUtils.closeQuietly(var4);
         return var0.parseMetadataSection(var2, var3);
      } finally {
         IOUtils.closeQuietly(var4);
      }
   }

   private static String locationToName(ResourceLocation var0) {
      return String.format("%s/%s/%s", "assets", var0.getResourceDomain(), var0.getResourcePath());
   }

   protected abstract boolean hasResourceName(String var1);

   protected static String getRelativeName(File var0, File var1) {
      return var0.toURI().relativize(var1.toURI()).getPath();
   }
}
