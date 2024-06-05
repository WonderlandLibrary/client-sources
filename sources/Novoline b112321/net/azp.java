package net;

import java.util.Random;
import net.atX;
import net.avj;
import net.gZ;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms$TransformType;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class azp extends Render {
   private final atX h;
   private final Random g = new Random();
   public static long f;
   public static double i;
   public static Random e = new Random();

   public azp(RenderManager var1, atX var2) {
      super(var1);
      this.h = var2;
      this.shadowSize = 0.15F;
      this.shadowOpaque = 0.75F;
   }

   private int a(EntityItem var1, double var2, double var4, double var6, float var8, IBakedModel var9) {
      ItemStack var10 = var1.i();
      Item var11 = var10.getItem();
      return 0;
   }

   private int a(ItemStack var1) {
      byte var2 = 1;
      if(var1.stackSize > 48) {
         var2 = 5;
      } else if(var1.stackSize > 32) {
         var2 = 4;
      } else if(var1.stackSize > 16) {
         var2 = 3;
      } else if(var1.stackSize > 1) {
         var2 = 2;
      }

      return var2;
   }

   public void b(EntityItem var1, double var2, double var4, double var6, float var8, float var9) {
      ItemStack var10 = var1.i();
      this.g.setSeed(187L);
      boolean var11 = false;
      if(this.bindEntityTexture(var1)) {
         this.renderManager.s.b(this.a(var1)).setBlurMipmap(false, false);
         var11 = true;
      }

      GlStateManager.enableRescaleNormal();
      GlStateManager.alphaFunc(516, 0.1F);
      GlStateManager.enableBlend();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.pushMatrix();
      IBakedModel var12 = this.h.b().getItemModel(var10);
      int var13 = this.a(var1, var2, var4, var6, var9, var12);

      for(int var14 = 0; var14 < var13; ++var14) {
         if(var12.isGui3d()) {
            GlStateManager.pushMatrix();
            float var15 = (this.g.nextFloat() * 2.0F - 1.0F) * 0.15F;
            float var16 = (this.g.nextFloat() * 2.0F - 1.0F) * 0.15F;
            float var17 = (this.g.nextFloat() * 2.0F - 1.0F) * 0.15F;
            GlStateManager.translate(var15, var16, var17);
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
            var12.getItemCameraTransforms().applyTransform(ItemCameraTransforms$TransformType.GROUND);
            this.h.a(var10, var12);
            GlStateManager.popMatrix();
         } else {
            GlStateManager.pushMatrix();
            var12.getItemCameraTransforms().applyTransform(ItemCameraTransforms$TransformType.GROUND);
            this.h.a(var10, var12);
            GlStateManager.popMatrix();
            float var19 = var12.getItemCameraTransforms().ground.scale.x;
            float var20 = var12.getItemCameraTransforms().ground.scale.y;
            float var21 = var12.getItemCameraTransforms().ground.scale.z;
            GlStateManager.translate(0.0F * var19, 0.0F * var20, 0.046875F * var21);
         }
      }

      GlStateManager.popMatrix();
      GlStateManager.disableRescaleNormal();
      GlStateManager.disableBlend();
      this.bindEntityTexture(var1);
      this.renderManager.s.b(this.a(var1)).restoreLastBlurMipmap();
      super.doRender(var1, var2, var4, var6, var8, var9);
   }

   private void a(EntityItem var1, double var2, double var4, double var6, float var8, float var9) {
      Minecraft var10 = Minecraft.getMinecraft();
      i = (double)(System.nanoTime() - f) / 5.0E13D;
      if(!var10.inGameHasFocus) {
         i = 0.0D;
      }

      ItemStack var11 = var1.i();
      int var12;
      if(var11.getItem() != null) {
         var12 = Item.b(var11.getItem()) + var11.getMetadata();
      } else {
         var12 = 187;
      }

      e.setSeed((long)var12);
      Minecraft.getMinecraft().ab().a(this.a(var1));
      Minecraft.getMinecraft().ab().b(this.a(var1)).setBlurMipmap(false, false);
      GlStateManager.enableRescaleNormal();
      GlStateManager.alphaFunc(516, 0.1F);
      GlStateManager.enableBlend();
      RenderHelper.enableStandardItemLighting();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.pushMatrix();
      IBakedModel var13 = var10.a().b().getItemModel(var11);
      boolean var14 = var13.isGui3d();
      boolean var15 = var13.isGui3d();
      int var16 = this.a(var11);
      GlStateManager.translate((float)var2, (float)var4, (float)var6);
      if(var13.isGui3d()) {
         GlStateManager.scale(0.5F, 0.5F, 0.5F);
      }

      GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
      GL11.glRotatef(var1.rotationYaw, 0.0F, 0.0F, 1.0F);
      GlStateManager.translate(0.0D, 0.0D, -0.08D);
      if(var10.getRenderManager().options != null) {
         if(!var1.onGround) {
            double var17 = i * 2.0D;
            var1.rotationPitch = (float)((double)var1.rotationPitch + var17);
         }

         GlStateManager.rotate(var1.rotationPitch, 1.0F, 0.0F, 0.0F);
      }

      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

      for(int var21 = 0; var21 < var16; ++var21) {
         GlStateManager.pushMatrix();
         float var18 = (e.nextFloat() * 2.0F - 1.0F) * 0.15F;
         float var19 = (e.nextFloat() * 2.0F - 1.0F) * 0.15F;
         float var20 = (e.nextFloat() * 2.0F - 1.0F) * 0.15F;
         GlStateManager.translate(var18, var19, var20);
         var10.a().a(var11, var13);
         GlStateManager.popMatrix();
      }

      GlStateManager.popMatrix();
      GlStateManager.disableRescaleNormal();
      GlStateManager.disableBlend();
      Minecraft.getMinecraft().ab().a(this.a(var1));
      Minecraft.getMinecraft().ab().b(this.a(var1)).restoreLastBlurMipmap();
   }

   public void c(EntityItem var1, double var2, double var4, double var6, float var8, float var9) {
      if(((avj)gZ.g().d().b(avj.class)).y()) {
         this.a(var1, var2, var4, var6, var8, var9);
      } else {
         this.b(var1, var2, var4, var6, var8, var9);
      }

   }

   protected ResourceLocation a(EntityItem var1) {
      return TextureMap.locationBlocksTexture;
   }
}
