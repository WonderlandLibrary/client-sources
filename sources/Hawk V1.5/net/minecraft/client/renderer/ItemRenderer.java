package net.minecraft.client.renderer;

import hawk.Client;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.MapData;
import optifine.Config;
import optifine.DynamicLights;
import optifine.Reflector;
import org.lwjgl.opengl.GL11;
import shadersmod.client.Shaders;

public class ItemRenderer {
   private final Minecraft mc;
   private final RenderItem itemRenderer;
   private float equippedProgress;
   private float prevEquippedProgress;
   private static final ResourceLocation RES_MAP_BACKGROUND = new ResourceLocation("textures/map/map_background.png");
   private int equippedItemSlot = -1;
   private ItemStack itemToRender;
   private static final ResourceLocation RES_UNDERWATER_OVERLAY = new ResourceLocation("textures/misc/underwater.png");
   private final RenderManager field_178111_g;

   private void func_178103_d() {
      GlStateManager.translate(-0.5F, 0.2F, 0.0F);
      GlStateManager.rotate(30.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(-80.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.rotate(60.0F, 0.0F, 1.0F, 0.0F);
   }

   private void func_178108_a(float var1, TextureAtlasSprite var2) {
      this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
      Tessellator var3 = Tessellator.getInstance();
      WorldRenderer var4 = var3.getWorldRenderer();
      float var5 = 0.1F;
      GlStateManager.color(var5, var5, var5, 0.5F);
      GlStateManager.pushMatrix();
      float var6 = -1.0F;
      float var7 = 1.0F;
      float var8 = -1.0F;
      float var9 = 1.0F;
      float var10 = -0.5F;
      float var11 = var2.getMinU();
      float var12 = var2.getMaxU();
      float var13 = var2.getMinV();
      float var14 = var2.getMaxV();
      var4.startDrawingQuads();
      var4.addVertexWithUV((double)var6, (double)var8, (double)var10, (double)var12, (double)var14);
      var4.addVertexWithUV((double)var7, (double)var8, (double)var10, (double)var11, (double)var14);
      var4.addVertexWithUV((double)var7, (double)var9, (double)var10, (double)var11, (double)var13);
      var4.addVertexWithUV((double)var6, (double)var9, (double)var10, (double)var12, (double)var13);
      var3.draw();
      GlStateManager.popMatrix();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public void updateEquippedItem() {
      this.prevEquippedProgress = this.equippedProgress;
      EntityPlayerSP var1 = this.mc.thePlayer;
      ItemStack var2 = var1.inventory.getCurrentItem();
      boolean var3 = false;
      if (this.itemToRender != null && var2 != null) {
         if (!this.itemToRender.getIsItemStackEqual(var2)) {
            if (Reflector.ForgeItem_shouldCauseReequipAnimation.exists()) {
               boolean var4 = Reflector.callBoolean(this.itemToRender.getItem(), Reflector.ForgeItem_shouldCauseReequipAnimation, this.itemToRender, var2, this.equippedItemSlot != var1.inventory.currentItem);
               if (!var4) {
                  this.itemToRender = var2;
                  this.equippedItemSlot = var1.inventory.currentItem;
                  return;
               }
            }

            var3 = true;
         }
      } else if (this.itemToRender == null && var2 == null) {
         var3 = false;
      } else {
         var3 = true;
      }

      float var7 = 0.4F;
      float var5 = var3 ? 0.0F : 1.0F;
      float var6 = MathHelper.clamp_float(var5 - this.equippedProgress, -var7, var7);
      this.equippedProgress += var6;
      if (this.equippedProgress < 0.1F) {
         if (Config.isShaders()) {
            Shaders.itemToRender = var2;
         }

         this.itemToRender = var2;
         this.equippedItemSlot = var1.inventory.currentItem;
      }

   }

   private float func_178100_c(float var1) {
      float var2 = 1.0F - var1 / 45.0F + 0.1F;
      var2 = MathHelper.clamp_float(var2, 0.0F, 1.0F);
      var2 = -MathHelper.cos(var2 * 3.1415927F) * 0.5F + 0.5F;
      return var2;
   }

   private void func_178102_b(AbstractClientPlayer var1) {
      this.mc.getTextureManager().bindTexture(var1.getLocationSkin());
      Render var2 = this.field_178111_g.getEntityRenderObject(this.mc.thePlayer);
      RenderPlayer var3 = (RenderPlayer)var2;
      if (!var1.isInvisible()) {
         this.func_180534_a(var3);
         this.func_178106_b(var3);
      }

   }

   private void func_178104_a(AbstractClientPlayer var1, float var2) {
      float var3 = (float)var1.getItemInUseCount() - var2 + 1.0F;
      float var4 = var3 / (float)this.itemToRender.getMaxItemUseDuration();
      float var5 = MathHelper.abs(MathHelper.cos(var3 / 4.0F * 3.1415927F) * 0.1F);
      if (var4 >= 0.8F) {
         var5 = 0.0F;
      }

      GlStateManager.translate(0.0F, var5, 0.0F);
      float var6 = 1.0F - (float)Math.pow((double)var4, 27.0D);
      GlStateManager.translate(var6 * 0.6F, var6 * -0.5F, var6 * 0.0F);
      GlStateManager.rotate(var6 * 90.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(var6 * 10.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.rotate(var6 * 30.0F, 0.0F, 0.0F, 1.0F);
   }

   public void resetEquippedProgress2() {
      this.equippedProgress = 0.0F;
   }

   public void renderItem(EntityLivingBase var1, ItemStack var2, ItemCameraTransforms.TransformType var3) {
      if (var2 != null) {
         Item var4 = var2.getItem();
         Block var5 = Block.getBlockFromItem(var4);
         GlStateManager.pushMatrix();
         if (this.itemRenderer.func_175050_a(var2)) {
            GlStateManager.scale(2.0F, 2.0F, 2.0F);
            if (this.func_178107_a(var5)) {
               GlStateManager.depthMask(false);
            }
         }

         this.itemRenderer.func_175049_a(var2, var1, var3);
         if (this.func_178107_a(var5)) {
            GlStateManager.depthMask(true);
         }

         GlStateManager.popMatrix();
      }

   }

   private void func_178101_a(float var1, float var2) {
      GlStateManager.pushMatrix();
      GlStateManager.rotate(var1, 1.0F, 0.0F, 0.0F);
      GlStateManager.rotate(var2, 0.0F, 1.0F, 0.0F);
      RenderHelper.enableStandardItemLighting();
      GlStateManager.popMatrix();
   }

   private void func_178098_a(float var1, AbstractClientPlayer var2) {
      GlStateManager.rotate(-18.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.rotate(-12.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(-8.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.translate(-0.9F, 0.2F, 0.0F);
      float var3 = (float)this.itemToRender.getMaxItemUseDuration() - ((float)var2.getItemInUseCount() - var1 + 1.0F);
      float var4 = var3 / 20.0F;
      var4 = (var4 * var4 + var4 * 2.0F) / 3.0F;
      if (var4 > 1.0F) {
         var4 = 1.0F;
      }

      if (var4 > 0.1F) {
         float var5 = MathHelper.sin((var3 - 0.1F) * 1.3F);
         float var6 = var4 - 0.1F;
         float var7 = var5 * var6;
         GlStateManager.translate(var7 * 0.0F, var7 * 0.01F, var7 * 0.0F);
      }

      GlStateManager.translate(var4 * 0.0F, var4 * 0.0F, var4 * 0.1F);
      GlStateManager.scale(1.0F, 1.0F, 1.0F + var4 * 0.2F);
   }

   public void renderOverlays(float var1) {
      GlStateManager.disableAlpha();
      if (this.mc.thePlayer.isEntityInsideOpaqueBlock()) {
         BlockPos var2 = new BlockPos(this.mc.thePlayer);
         IBlockState var3 = this.mc.theWorld.getBlockState(var2);
         EntityPlayerSP var4 = this.mc.thePlayer;

         for(int var5 = 0; var5 < 8; ++var5) {
            double var6 = var4.posX + (double)(((float)((var5 >> 0) % 2) - 0.5F) * var4.width * 0.8F);
            double var8 = var4.posY + (double)(((float)((var5 >> 1) % 2) - 0.5F) * 0.1F);
            double var10 = var4.posZ + (double)(((float)((var5 >> 2) % 2) - 0.5F) * var4.width * 0.8F);
            var2 = new BlockPos(var6, var8 + (double)var4.getEyeHeight(), var10);
            IBlockState var12 = this.mc.theWorld.getBlockState(var2);
            if (var12.getBlock().isVisuallyOpaque()) {
               var3 = var12;
            }
         }

         if (var3.getBlock().getRenderType() != -1) {
            Object var13 = Reflector.getFieldValue(Reflector.RenderBlockOverlayEvent_OverlayType_BLOCK);
            if (!Reflector.callBoolean(Reflector.ForgeEventFactory_renderBlockOverlay, this.mc.thePlayer, var1, var13, var3, var2)) {
               this.func_178108_a(var1, this.mc.getBlockRendererDispatcher().func_175023_a().func_178122_a(var3));
            }
         }
      }

      if (!this.mc.thePlayer.func_175149_v()) {
         if (this.mc.thePlayer.isInsideOfMaterial(Material.water) && !Reflector.callBoolean(Reflector.ForgeEventFactory_renderWaterOverlay, this.mc.thePlayer, var1)) {
            this.renderWaterOverlayTexture(var1);
         }

         if (this.mc.thePlayer.isBurning() && !Reflector.callBoolean(Reflector.ForgeEventFactory_renderFireOverlay, this.mc.thePlayer, var1)) {
            this.renderFireInFirstPerson(var1);
         }
      }

      GlStateManager.enableAlpha();
   }

   private void renderFireInFirstPerson(float var1) {
      Tessellator var2 = Tessellator.getInstance();
      WorldRenderer var3 = var2.getWorldRenderer();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 0.9F);
      GlStateManager.depthFunc(519);
      GlStateManager.depthMask(false);
      GlStateManager.enableBlend();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      float var4 = 1.0F;

      for(int var5 = 0; var5 < 2; ++var5) {
         GlStateManager.pushMatrix();
         TextureAtlasSprite var6 = this.mc.getTextureMapBlocks().getAtlasSprite("minecraft:blocks/fire_layer_1");
         this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
         float var7 = var6.getMinU();
         float var8 = var6.getMaxU();
         float var9 = var6.getMinV();
         float var10 = var6.getMaxV();
         float var11 = (0.0F - var4) / 2.0F;
         float var12 = var11 + var4;
         float var13 = 0.0F - var4 / 2.0F;
         float var14 = var13 + var4;
         float var15 = -0.5F;
         GlStateManager.translate((float)(-(var5 * 2 - 1)) * 0.24F, -0.3F, 0.0F);
         GlStateManager.rotate((float)(var5 * 2 - 1) * 10.0F, 0.0F, 1.0F, 0.0F);
         var3.startDrawingQuads();
         var3.addVertexWithUV((double)var11, (double)var13, (double)var15, (double)var8, (double)var10);
         var3.addVertexWithUV((double)var12, (double)var13, (double)var15, (double)var7, (double)var10);
         var3.addVertexWithUV((double)var12, (double)var14, (double)var15, (double)var7, (double)var9);
         var3.addVertexWithUV((double)var11, (double)var14, (double)var15, (double)var8, (double)var9);
         var2.draw();
         GlStateManager.popMatrix();
      }

      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.disableBlend();
      GlStateManager.depthMask(true);
      GlStateManager.depthFunc(515);
   }

   private void func_178110_a(EntityPlayerSP var1, float var2) {
      float var3 = var1.prevRenderArmPitch + (var1.renderArmPitch - var1.prevRenderArmPitch) * var2;
      float var4 = var1.prevRenderArmYaw + (var1.renderArmYaw - var1.prevRenderArmYaw) * var2;
      GlStateManager.rotate((var1.rotationPitch - var3) * 0.1F, 1.0F, 0.0F, 0.0F);
      GlStateManager.rotate((var1.rotationYaw - var4) * 0.1F, 0.0F, 1.0F, 0.0F);
   }

   private void monsoon2() {
      GlStateManager.translate(-0.5F, 0.2F, 0.0F);
      GlStateManager.rotate(30.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(-80.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.rotate(60.0F, 0.0F, 1.0F, 0.0F);
   }

   private void func_180534_a(RenderPlayer var1) {
      GlStateManager.pushMatrix();
      GlStateManager.rotate(54.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(64.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.rotate(-62.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.translate(0.25F, -0.85F, 0.75F);
      var1.func_177138_b(this.mc.thePlayer);
      GlStateManager.popMatrix();
   }

   public void renderItemInFirstPerson(float var1) {
      float var2 = 1.0F - (this.prevEquippedProgress + (this.equippedProgress - this.prevEquippedProgress) * var1);
      EntityPlayerSP var3 = this.mc.thePlayer;
      float var4 = var3.getSwingProgress(var1);
      float var5 = var3.prevRotationPitch + (var3.rotationPitch - var3.prevRotationPitch) * var1;
      float var6 = var3.prevRotationYaw + (var3.rotationYaw - var3.prevRotationYaw) * var1;
      this.func_178101_a(var5, var6);
      this.func_178109_a(var3);
      this.func_178110_a(var3, var1);
      GlStateManager.enableRescaleNormal();
      GlStateManager.pushMatrix();
      if (this.itemToRender != null) {
         if (this.itemToRender.getItem() instanceof ItemMap) {
            this.func_178097_a(var3, var5, var2, var4);
         } else if (var3.getItemInUseCount() > 0) {
            EnumAction var7 = this.itemToRender.getItemUseAction();
            switch(var7) {
            case NONE:
               this.func_178096_b(var2, 0.0F);
               break;
            case EAT:
            case DRINK:
               this.func_178104_a(var3, var1);
               this.func_178096_b(var2, 0.0F);
               break;
            case BLOCK:
               float var8;
               if (Client.animations.animation.is("EZE")) {
                  var8 = (float)Math.sin(Math.sqrt((double)var4) * 3.141592653589793D);
                  this.func_178096_b(var2, 0.0F);
                  GlStateManager.translate(0.1F, 0.135F, -0.15F);
                  GL11.glRotated((double)(-var8 * 40.0F), (double)(var8 / 2.0F), 0.0D, 9.0D);
                  GL11.glRotated((double)(-var8 * 40.0F), 0.800000011920929D, (double)(var8 / 2.0F), 0.0D);
                  this.func_178103_d();
               }

               if (Client.animations.animation.is("Vanilla")) {
                  this.func_178096_b(var2, 0.0F);
                  this.func_178103_d();
               } else {
                  if (Client.animations.animation.is("Monsoon")) {
                     this.monsoon(var2, 0.0F);
                     GlStateManager.scale(2.0F, 2.0F, 2.0F);
                     this.func_178103_d();
                     var8 = MathHelper.sin(MathHelper.sqrt_float(var4) * 3.1415927F);
                     GlStateManager.translate(1.0D, -0.8D, 1.0D);
                     GlStateManager.rotate(-var8 * 50.0F / 2.0F, -8.0F, 0.4F, 9.0F);
                     GlStateManager.rotate(-var8 * 40.0F, 1.5F, -0.4F, -0.0F);
                  }

                  if (Client.animations.animation.is("Fan")) {
                     this.func_178096_b(var2, 0.0F);
                     this.fan();
                  }
               }
               break;
            case BOW:
               this.func_178096_b(var2, 0.0F);
               this.func_178098_a(var1, var3);
            }
         } else {
            this.func_178105_d(var4);
            this.func_178096_b(var2, var4);
         }

         this.renderItem(var3, this.itemToRender, ItemCameraTransforms.TransformType.FIRST_PERSON);
      } else if (!var3.isInvisible()) {
         this.func_178095_a(var3, var2, var4);
      }

      GlStateManager.popMatrix();
      GlStateManager.disableRescaleNormal();
      RenderHelper.disableStandardItemLighting();
   }

   public void resetEquippedProgress() {
      this.equippedProgress = 0.0F;
   }

   private void func_178106_b(RenderPlayer var1) {
      GlStateManager.pushMatrix();
      GlStateManager.rotate(92.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(45.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.rotate(41.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.translate(-0.3F, -1.1F, 0.45F);
      var1.func_177139_c(this.mc.thePlayer);
      GlStateManager.popMatrix();
   }

   private void func_178109_a(AbstractClientPlayer var1) {
      int var2 = this.mc.theWorld.getCombinedLight(new BlockPos(var1.posX, var1.posY + (double)var1.getEyeHeight(), var1.posZ), 0);
      if (Config.isDynamicLights()) {
         var2 = DynamicLights.getCombinedLight(this.mc.func_175606_aa(), var2);
      }

      float var3 = (float)(var2 & '\uffff');
      float var4 = (float)(var2 >> 16);
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var3, var4);
   }

   private void renderWaterOverlayTexture(float var1) {
      this.mc.getTextureManager().bindTexture(RES_UNDERWATER_OVERLAY);
      Tessellator var2 = Tessellator.getInstance();
      WorldRenderer var3 = var2.getWorldRenderer();
      float var4 = this.mc.thePlayer.getBrightness(var1);
      GlStateManager.color(var4, var4, var4, 0.5F);
      GlStateManager.enableBlend();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.pushMatrix();
      float var5 = 4.0F;
      float var6 = -1.0F;
      float var7 = 1.0F;
      float var8 = -1.0F;
      float var9 = 1.0F;
      float var10 = -0.5F;
      float var11 = -this.mc.thePlayer.rotationYaw / 64.0F;
      float var12 = this.mc.thePlayer.rotationPitch / 64.0F;
      var3.startDrawingQuads();
      var3.addVertexWithUV((double)var6, (double)var8, (double)var10, (double)(var5 + var11), (double)(var5 + var12));
      var3.addVertexWithUV((double)var7, (double)var8, (double)var10, (double)(0.0F + var11), (double)(var5 + var12));
      var3.addVertexWithUV((double)var7, (double)var9, (double)var10, (double)(0.0F + var11), (double)(0.0F + var12));
      var3.addVertexWithUV((double)var6, (double)var9, (double)var10, (double)(var5 + var11), (double)(0.0F + var12));
      var2.draw();
      GlStateManager.popMatrix();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.disableBlend();
   }

   private void func_178105_d(float var1) {
      float var2 = -0.4F * MathHelper.sin(MathHelper.sqrt_float(var1) * 3.1415927F);
      float var3 = 0.2F * MathHelper.sin(MathHelper.sqrt_float(var1) * 3.1415927F * 2.0F);
      float var4 = -0.2F * MathHelper.sin(var1 * 3.1415927F);
      GlStateManager.translate(var2, var3, var4);
   }

   private void func_178095_a(AbstractClientPlayer var1, float var2, float var3) {
      float var4 = -0.3F * MathHelper.sin(MathHelper.sqrt_float(var3) * 3.1415927F);
      float var5 = 0.4F * MathHelper.sin(MathHelper.sqrt_float(var3) * 3.1415927F * 2.0F);
      float var6 = -0.4F * MathHelper.sin(var3 * 3.1415927F);
      GlStateManager.translate(var4, var5, var6);
      GlStateManager.translate(0.64000005F, -0.6F, -0.71999997F);
      GlStateManager.translate(0.0F, var2 * -0.6F, 0.0F);
      GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
      float var7 = MathHelper.sin(var3 * var3 * 3.1415927F);
      float var8 = MathHelper.sin(MathHelper.sqrt_float(var3) * 3.1415927F);
      GlStateManager.rotate(var8 * 70.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(var7 * -20.0F, 0.0F, 0.0F, 1.0F);
      this.mc.getTextureManager().bindTexture(var1.getLocationSkin());
      GlStateManager.translate(-1.0F, 3.6F, 3.5F);
      GlStateManager.rotate(120.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.rotate(200.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.scale(1.0F, 1.0F, 1.0F);
      GlStateManager.translate(5.6F, 0.0F, 0.0F);
      Render var9 = this.field_178111_g.getEntityRenderObject(this.mc.thePlayer);
      RenderPlayer var10 = (RenderPlayer)var9;
      var10.func_177138_b(this.mc.thePlayer);
   }

   private boolean func_178107_a(Block var1) {
      return var1 != null && var1.getBlockLayer() == EnumWorldBlockLayer.TRANSLUCENT;
   }

   private void monsoon(float var1, float var2) {
      GlStateManager.translate(0.26F, -0.18F, -0.71999997F);
      GlStateManager.translate(0.0F, var1 * -0.6F, 0.0F);
      GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
      float var3 = MathHelper.sin(var2 * var2 * 3.1415927F);
      float var4 = MathHelper.sin(MathHelper.sqrt_float(var2) * 3.1415927F);
      GlStateManager.rotate(var3 * -20.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(var4 * -20.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.rotate(var4 * -80.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.scale(0.2F, 0.2F, 0.2F);
   }

   public ItemRenderer(Minecraft var1) {
      this.mc = var1;
      this.field_178111_g = var1.getRenderManager();
      this.itemRenderer = var1.getRenderItem();
   }

   private void func_178097_a(AbstractClientPlayer var1, float var2, float var3, float var4) {
      float var5 = -0.4F * MathHelper.sin(MathHelper.sqrt_float(var4) * 3.1415927F);
      float var6 = 0.2F * MathHelper.sin(MathHelper.sqrt_float(var4) * 3.1415927F * 2.0F);
      float var7 = -0.2F * MathHelper.sin(var4 * 3.1415927F);
      GlStateManager.translate(var5, var6, var7);
      float var8 = this.func_178100_c(var2);
      GlStateManager.translate(0.0F, 0.04F, -0.72F);
      GlStateManager.translate(0.0F, var3 * -1.2F, 0.0F);
      GlStateManager.translate(0.0F, var8 * -0.5F, 0.0F);
      GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(var8 * -85.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.rotate(0.0F, 1.0F, 0.0F, 0.0F);
      this.func_178102_b(var1);
      float var9 = MathHelper.sin(var4 * var4 * 3.1415927F);
      float var10 = MathHelper.sin(MathHelper.sqrt_float(var4) * 3.1415927F);
      GlStateManager.rotate(var9 * -20.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(var10 * -20.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.rotate(var10 * -80.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.scale(0.38F, 0.38F, 0.38F);
      GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.rotate(0.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.translate(-1.0F, -1.0F, 0.0F);
      GlStateManager.scale(0.015625F, 0.015625F, 0.015625F);
      this.mc.getTextureManager().bindTexture(RES_MAP_BACKGROUND);
      Tessellator var11 = Tessellator.getInstance();
      WorldRenderer var12 = var11.getWorldRenderer();
      GL11.glNormal3f(0.0F, 0.0F, -1.0F);
      var12.startDrawingQuads();
      var12.addVertexWithUV(-7.0D, 135.0D, 0.0D, 0.0D, 1.0D);
      var12.addVertexWithUV(135.0D, 135.0D, 0.0D, 1.0D, 1.0D);
      var12.addVertexWithUV(135.0D, -7.0D, 0.0D, 1.0D, 0.0D);
      var12.addVertexWithUV(-7.0D, -7.0D, 0.0D, 0.0D, 0.0D);
      var11.draw();
      MapData var13 = Items.filled_map.getMapData(this.itemToRender, this.mc.theWorld);
      if (var13 != null) {
         this.mc.entityRenderer.getMapItemRenderer().func_148250_a(var13, false);
      }

   }

   private void fan() {
      float var1 = (float)((int)((double)System.currentTimeMillis() / 1.5D % 360.0D));
      var1 = (var1 > 180.0F ? 360.0F - var1 : var1) * 2.0F;
      var1 /= 180.0F;
      float var2 = (float)((int)((double)System.currentTimeMillis() / 3.5D % 120.0D));
      var2 = (var2 > 30.0F ? 120.0F - var2 : var2) * 2.0F;
      var2 /= 1.0F;
      float var3 = (float)((int)((double)System.currentTimeMillis() / 3.5D % 110.0D));
      var3 = (var3 > 30.0F ? 110.0F - var3 : var3) * 2.0F;
      var3 /= 1.0F;
      boolean var4 = false;
      boolean var5 = false;
      byte var6 = 0;
      boolean var7 = false;
      float var8 = 0.0F;
      boolean var9 = false;
      boolean var10 = false;
      float var11 = 0.0F;
      boolean var12 = false;
      var7 = false;
      int var13 = (int)(System.currentTimeMillis() / 2L % 360L);
      byte var14 = 1;
      var8 = 1.0F;
      byte var15 = -59;
      byte var16 = -1;
      byte var17 = 0;
      byte var18 = 3;
      GlStateManager.translate(var11, 0.2F, -var8);
      GlStateManager.rotate((float)var15, (float)var16, (float)var17, (float)var18);
      GlStateManager.rotate((float)(-var13), (float)var14, (float)var6, 0.0F);
      GlStateManager.rotate(60.0F, 0.0F, 1.0F, 0.0F);
   }

   private void func_178096_b(float var1, float var2) {
      GlStateManager.translate(0.56F, -0.52F, -0.71999997F);
      GlStateManager.translate(0.0F, var1 * -0.6F, 0.0F);
      GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
      float var3 = MathHelper.sin(var2 * var2 * 3.1415927F);
      float var4 = MathHelper.sin(MathHelper.sqrt_float(var2) * 3.1415927F);
      GlStateManager.rotate(var3 * -20.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(var4 * -20.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.rotate(var4 * -80.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.scale(0.4F, 0.4F, 0.4F);
   }

   static final class SwitchEnumAction {
      static final int[] field_178094_a = new int[EnumAction.values().length];

      static {
         try {
            field_178094_a[EnumAction.NONE.ordinal()] = 1;
         } catch (NoSuchFieldError var5) {
         }

         try {
            field_178094_a[EnumAction.EAT.ordinal()] = 2;
         } catch (NoSuchFieldError var4) {
         }

         try {
            field_178094_a[EnumAction.DRINK.ordinal()] = 3;
         } catch (NoSuchFieldError var3) {
         }

         try {
            field_178094_a[EnumAction.BLOCK.ordinal()] = 4;
         } catch (NoSuchFieldError var2) {
         }

         try {
            field_178094_a[EnumAction.BOW.ordinal()] = 5;
         } catch (NoSuchFieldError var1) {
         }

      }
   }
}
