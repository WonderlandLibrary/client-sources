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
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.ResourceLocation;
import optifine.ReflectorForge;

public class DefaultResourcePack implements IResourcePack {
   private final Map field_152781_b;
   private static final String __OBFID = "CL_00001073";
   public static final Set defaultResourceDomains = ImmutableSet.of("minecraft", "realms");

   public IMetadataSection getPackMetadata(IMetadataSerializer var1, String var2) throws IOException {
      try {
         FileInputStream var3 = new FileInputStream((File)this.field_152781_b.get("pack.mcmeta"));
         return AbstractResourcePack.readMetadata(var1, var3, var2);
      } catch (RuntimeException var4) {
         return null;
      } catch (FileNotFoundException var5) {
         return null;
      }
   }

   public Set getResourceDomains() {
      return defaultResourceDomains;
   }

   public boolean resourceExists(ResourceLocation var1) {
      return this.getResourceStream(var1) != null || this.field_152781_b.containsKey(var1.toString());
   }

   private InputStream getResourceStream(ResourceLocation var1) {
      String var2 = String.valueOf((new StringBuilder("/assets/")).append(var1.getResourceDomain()).append("/").append(var1.getResourcePath()));
      InputStream var3 = ReflectorForge.getOptiFineResourceStream(var2);
      return var3 != null ? var3 : DefaultResourcePack.class.getResourceAsStream(String.valueOf((new StringBuilder("/assets/")).append(var1.getResourceDomain()).append("/").append(var1.getResourcePath())));
   }

   public InputStream func_152780_c(ResourceLocation var1) throws IOException {
      File var2 = (File)this.field_152781_b.get(var1.toString());
      return var2 != null && var2.isFile() ? new FileInputStream(var2) : null;
   }

   public BufferedImage getPackImage() throws IOException {
      return TextureUtil.func_177053_a(DefaultResourcePack.class.getResourceAsStream(String.valueOf((new StringBuilder("/")).append((new ResourceLocation("pack.png")).getResourcePath()))));
   }

   public InputStream getInputStream(ResourceLocation var1) throws IOException {
      InputStream var2 = this.getResourceStream(var1);
      if (var2 != null) {
         return var2;
      } else {
         InputStream var3 = this.func_152780_c(var1);
         if (var3 != null) {
            return var3;
         } else {
            throw new FileNotFoundException(var1.getResourcePath());
         }
      }
   }

   public String getPackName() {
      return "Default";
   }

   public DefaultResourcePack(Map var1) {
      this.field_152781_b = var1;
   }
}
