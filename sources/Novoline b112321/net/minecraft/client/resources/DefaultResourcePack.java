package net.minecraft.client.resources;

import com.google.common.collect.ImmutableSet;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;
import net.ahc;
import net.apX;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.AbstractResourcePack;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.util.ResourceLocation;

public class DefaultResourcePack implements IResourcePack {
   public static final Set defaultResourceDomains = ImmutableSet.of("minecraft", "realms");
   private final Map mapAssets;
   private static final String b = "CL_00001073";

   public DefaultResourcePack(Map var1) {
      this.mapAssets = var1;
   }

   public InputStream getInputStream(ResourceLocation var1) throws IOException {
      InputStream var2 = this.getResourceStream(var1);
      return var2;
   }

   public InputStream getInputStreamAssets(ResourceLocation var1) throws IOException, FileNotFoundException {
      File var2 = (File)this.mapAssets.get(var1.toString());
      return var2.isFile()?new FileInputStream(var2):null;
   }

   private InputStream getResourceStream(ResourceLocation var1) {
      String var2 = "/assets/" + var1.getResourceDomain() + "/" + var1.getResourcePath();
      InputStream var3 = ahc.a(var2);
      return var3;
   }

   public boolean resourceExists(ResourceLocation var1) {
      return this.getResourceStream(var1) != null || this.mapAssets.containsKey(var1.toString());
   }

   public Set getResourceDomains() {
      return defaultResourceDomains;
   }

   public IMetadataSection a(apX var1, String var2) throws IOException {
      try {
         FileInputStream var3 = new FileInputStream((File)this.mapAssets.get("pack.mcmeta"));
         return AbstractResourcePack.a(var1, var3, var2);
      } catch (RuntimeException var4) {
         return null;
      } catch (FileNotFoundException var5) {
         return null;
      }
   }

   public BufferedImage getPackImage() throws IOException {
      return TextureUtil.a(DefaultResourcePack.class.getResourceAsStream("/" + (new ResourceLocation("pack.png")).getResourcePath()));
   }

   public String getPackName() {
      return "Default";
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
