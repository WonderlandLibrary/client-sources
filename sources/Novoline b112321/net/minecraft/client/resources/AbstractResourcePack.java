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
import net.apX;
import net.sz;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractResourcePack implements IResourcePack {
   private static final Logger LOGGER = LogManager.getLogger();
   public final File resourcePackFile;

   public AbstractResourcePack(File var1) {
      this.resourcePackFile = var1;
   }

   private static String locationToName(ResourceLocation var0) {
      return String.format("%s/%s/%s", new Object[]{"assets", var0.getResourceDomain(), var0.getResourcePath()});
   }

   protected static String getRelativeName(File var0, File var1) {
      return var0.toURI().relativize(var1.toURI()).getPath();
   }

   public InputStream getInputStream(ResourceLocation var1) throws IOException {
      return this.getInputStreamByName(locationToName(var1));
   }

   public boolean resourceExists(ResourceLocation var1) {
      return this.hasResourceName(locationToName(var1));
   }

   protected abstract InputStream getInputStreamByName(String var1) throws IOException;

   protected abstract boolean hasResourceName(String var1);

   protected void logNameNotLowercase(String var1) {
      LOGGER.warn("ResourcePack: ignored non-lowercase namespace: {} in {}", new Object[]{var1, this.resourcePackFile});
   }

   public IMetadataSection a(apX var1, String var2) throws IOException {
      return a(var1, this.getInputStreamByName("pack.mcmeta"), var2);
   }

   static IMetadataSection a(apX var0, InputStream var1, String var2) {
      BufferedReader var4 = null;

      JsonObject var3;
      try {
         var4 = new BufferedReader(new InputStreamReader(var1, Charsets.UTF_8));
         var3 = (new JsonParser()).parse(var4).getAsJsonObject();
      } catch (RuntimeException var9) {
         throw new JsonParseException(var9);
      } finally {
         IOUtils.closeQuietly(var4);
      }

      return var0.a(var2, var3);
   }

   public BufferedImage getPackImage() throws IOException {
      return sz.a(TextureUtil.a(this.getInputStreamByName("pack.png")));
   }

   public String getPackName() {
      return this.resourcePackFile.getName();
   }
}
