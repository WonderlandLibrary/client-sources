package net.shadersmod.client;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import net.a0R;
import net.apX;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.client.resources.data.AnimationMetadataSectionSerializer;
import net.minecraft.client.resources.data.FontMetadataSection;
import net.minecraft.client.resources.data.FontMetadataSectionSerializer;
import net.minecraft.client.resources.data.IMetadataSectionSerializer;
import net.minecraft.client.resources.data.LanguageMetadataSectionSerializer;
import net.minecraft.client.resources.data.PackMetadataSection;
import net.minecraft.client.resources.data.PackMetadataSectionSerializer;
import net.minecraft.client.resources.data.TextureMetadataSection;
import net.minecraft.client.resources.data.TextureMetadataSectionSerializer;
import net.shadersmod.client.ShaderOption;
import net.shadersmod.client.Shaders;
import org.apache.commons.io.IOUtils;

public class SimpleShaderTexture extends AbstractTexture {
   private String texturePath;
   private static final apX h = b();

   public SimpleShaderTexture(String var1) {
      this.texturePath = var1;
   }

   public void loadTexture(IResourceManager var1) throws IOException {
      ShaderOption.p();
      this.deleteGlTexture();
      InputStream var3 = Shaders.getShaderPackResourceStream(this.texturePath);
      if(var3 == null) {
         throw new FileNotFoundException("Shader texture not found: " + this.texturePath);
      } else {
         InputStream var10000 = var3;

         try {
            BufferedImage var4 = TextureUtil.a(var10000);
            TextureMetadataSection var5 = this.loadTextureMetadataSection();
            TextureUtil.uploadTextureImageAllocate(this.getGlTextureId(), var4, var5.getTextureBlur(), var5.getTextureClamp());
         } finally {
            IOUtils.closeQuietly(var3);
         }

      }
   }

   private TextureMetadataSection loadTextureMetadataSection() {
      // $FF: Couldn't be decompiled
   }

   private static apX b() {
      apX var0 = new apX();
      var0.a((IMetadataSectionSerializer)(new TextureMetadataSectionSerializer()), (Class)TextureMetadataSection.class);
      var0.a((IMetadataSectionSerializer)(new FontMetadataSectionSerializer()), (Class)FontMetadataSection.class);
      var0.a((IMetadataSectionSerializer)(new AnimationMetadataSectionSerializer()), (Class)AnimationMetadataSection.class);
      var0.a((IMetadataSectionSerializer)(new PackMetadataSectionSerializer()), (Class)PackMetadataSection.class);
      var0.a((IMetadataSectionSerializer)(new LanguageMetadataSectionSerializer()), (Class)a0R.class);
      return var0;
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
