package net.minecraft.client.resources;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import java.awt.image.BufferedImage;
import net.nk;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.resources.SkinManager$SkinAvailableCallback;
import net.minecraft.util.ResourceLocation;

class SkinManager$2 implements IImageBuffer {
   final IImageBuffer val$imageBuffer;
   final SkinManager$SkinAvailableCallback val$skinAvailableCallback;
   final Type val$type;
   final ResourceLocation val$resourceLocation;
   final MinecraftProfileTexture val$profileTexture;
   final nk e;

   SkinManager$2(nk var1, IImageBuffer var2, SkinManager$SkinAvailableCallback var3, Type var4, ResourceLocation var5, MinecraftProfileTexture var6) {
      this.e = var1;
      this.val$imageBuffer = var2;
      this.val$skinAvailableCallback = var3;
      this.val$type = var4;
      this.val$resourceLocation = var5;
      this.val$profileTexture = var6;
   }

   public BufferedImage parseUserSkin(BufferedImage var1) {
      return this.val$imageBuffer != null?this.val$imageBuffer.parseUserSkin(var1):var1;
   }

   public void skinAvailable() {
      if(this.val$imageBuffer != null) {
         this.val$imageBuffer.skinAvailable();
      }

      if(this.val$skinAvailableCallback != null) {
         this.val$skinAvailableCallback.skinAvailable(this.val$type, this.val$resourceLocation, this.val$profileTexture);
      }

   }
}
