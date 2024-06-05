package net.minecraft.client.renderer;

import cc.novoline.Novoline;
import cc.novoline.modules.visual.Animations;
import cc.novoline.modules.visual.Chams;
import net.asJ;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer$1;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms$TransformType;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
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
import net.optifine.Config;
import net.optifine.DynamicLights;
import net.optifine.Reflector;
import net.shadersmod.client.Shaders;
import org.lwjgl.opengl.GL11;

public class ItemRenderer {
   private static final ResourceLocation RES_MAP_BACKGROUND = new ResourceLocation("textures/map/map_background.png");
   private static final ResourceLocation RES_UNDERWATER_OVERLAY = new ResourceLocation("textures/misc/underwater.png");
   private final Minecraft mc;
   private ItemStack itemToRender;
   private float equippedProgress;
   private float prevEquippedProgress;
   private final RenderManager renderManager;
   private final RenderItem itemRenderer;
   private int equippedItemSlot = -1;
   private static final String g = "CL_00000953";
   private int i = 0;

   public ItemRenderer(Minecraft var1) {
      this.mc = var1;
      this.renderManager = var1.getRenderManager();
      this.itemRenderer = var1.getRenderItem();
   }

   public void renderItem(EntityLivingBase var1, ItemStack var2, ItemCameraTransforms$TransformType var3) {
      Item var4 = var2.getItem();
      Block var5 = Block.getBlockFromItem(var4);
      GlStateManager.pushMatrix();
      if(this.itemRenderer.shouldRenderItemIn3D(var2)) {
         GlStateManager.scale(2.0F, 2.0F, 2.0F);
         if(this.isBlockTranslucent(var5) && (!Config.isShaders() || !Shaders.renderItemKeepDepthMask)) {
            GlStateManager.depthMask(false);
         }
      } else {
         Animations var6 = (Animations)Novoline.getInstance().getModuleManager().getModule(Animations.class);
         if(var6.isEnabled()) {
            double var7 = 1.0D - ((Double)var6.getDownscaleFactor().get()).doubleValue();
            GlStateManager.scale(var7, var7, var7);
         }
      }

      this.itemRenderer.renderItemModelForEntity(var2, var1, var3);
      if(this.isBlockTranslucent(var5)) {
         GlStateManager.depthMask(true);
      }

      GlStateManager.popMatrix();
   }

   private boolean isBlockTranslucent(Block var1) {
      return var1.getBlockLayer() == EnumWorldBlockLayer.TRANSLUCENT;
   }

   private void func_178101_a(float var1, float var2) {
      GlStateManager.pushMatrix();
      GlStateManager.rotate(var1, 1.0F, 0.0F, 0.0F);
      GlStateManager.rotate(var2, 0.0F, 1.0F, 0.0F);
      RenderHelper.enableStandardItemLighting();
      GlStateManager.popMatrix();
   }

   private void a(asJ var1) {
      int var2 = this.mc.world.getCombinedLight(new BlockPos(var1.posX, var1.posY + (double)var1.getEyeHeight(), var1.posZ), 0);
      if(Config.al()) {
         var2 = DynamicLights.getCombinedLight(this.mc.getRenderViewEntity(), var2);
      }

      float var3 = (float)(var2 & '\uffff');
      float var4 = (float)(var2 >> 16);
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var3, var4);
   }

   private void func_178110_a(EntityPlayerSP var1, float var2) {
      float var3 = var1.prevRenderArmPitch + (var1.renderArmPitch - var1.prevRenderArmPitch) * var2;
      float var4 = var1.prevRenderArmYaw + (var1.renderArmYaw - var1.prevRenderArmYaw) * var2;
      GlStateManager.rotate((var1.rotationPitch - var3) * 0.1F, 1.0F, 0.0F, 0.0F);
      GlStateManager.rotate((var1.rotationYaw - var4) * 0.1F, 0.0F, 1.0F, 0.0F);
   }

   private float func_178100_c(float var1) {
      float var2 = 1.0F - var1 / 45.0F + 0.1F;
      var2 = MathHelper.clamp_float(var2, 0.0F, 1.0F);
      var2 = -MathHelper.cos(var2 * 3.1415927F) * 0.5F + 0.5F;
      return var2;
   }

   private void renderRightArm(RenderPlayer var1) {
      GlStateManager.pushMatrix();
      GlStateManager.rotate(54.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(64.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.rotate(-62.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.translate(0.25F, -0.85F, 0.75F);
      var1.a(this.mc.player);
      GlStateManager.popMatrix();
   }

   private void renderLeftArm(RenderPlayer var1) {
      GlStateManager.pushMatrix();
      GlStateManager.rotate(92.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(45.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.rotate(41.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.translate(-0.3F, -1.1F, 0.45F);
      var1.c(this.mc.player);
      GlStateManager.popMatrix();
   }

   private void b(asJ var1) {
      this.mc.getTextureManager().bindTexture(var1.getLocationSkin());
      Render var2 = this.renderManager.getEntityRenderObject(this.mc.player);
      RenderPlayer var3 = (RenderPlayer)var2;
      if(!var1.isInvisible()) {
         GlStateManager.disableCull();
         this.renderRightArm(var3);
         this.renderLeftArm(var3);
         GlStateManager.enableCull();
      }

   }

   private void a(asJ var1, float var2, float var3, float var4) {
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
      this.b(var1);
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
      var12.begin(7, DefaultVertexFormats.POSITION_TEX);
      var12.pos(-7.0D, 135.0D, 0.0D).tex(0.0D, 1.0D).endVertex();
      var12.pos(135.0D, 135.0D, 0.0D).tex(1.0D, 1.0D).endVertex();
      var12.pos(135.0D, -7.0D, 0.0D).tex(1.0D, 0.0D).endVertex();
      var12.pos(-7.0D, -7.0D, 0.0D).tex(0.0D, 0.0D).endVertex();
      var11.draw();
      MapData var13 = Items.filled_map.getMapData(this.itemToRender, this.mc.world);
      this.mc.entityRenderer.getMapItemRenderer().renderMap(var13, false);
   }

   private void b(asJ var1, float var2, float var3) {
      Chams var4 = (Chams)Novoline.getInstance().getModuleManager().getModule(Chams.class);
      if(var4.isEnabled() && ((Boolean)var4.isColored().get()).booleanValue() && ((Boolean)var4.getHand().get()).booleanValue()) {
         int var5 = var4.getHandColor().getAwtColor().getRGB();
         GL11.glPushAttrib(-1);
         GlStateManager.enableBlend();
         GL11.glBlendFunc(770, 771);
         GlStateManager.disableTexture2D();
         GlStateManager.b(!((Boolean)var4.isMaterial().get()).booleanValue());
         GL11.glColor4f((float)(var5 >> 16 & 255) / 255.0F, (float)(var5 >> 8 & 255) / 255.0F, (float)(var5 & 255) / 255.0F, Math.max(0.11764706F, var4.getVisibleAlpha() / 255.0F));
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
         GlStateManager.disableDepth();
         this.a(var1, var2, var3);
         GlStateManager.a(!((Boolean)var4.isMaterial().get()).booleanValue());
         GlStateManager.enableTexture2D();
         GlStateManager.disableBlend();
         GlStateManager.enableDepth();
         GL11.glPopAttrib();
      } else {
         this.a(var1, var2, var3);
      }

   }

   private void a(asJ var1, float var2, float var3) {
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
      Render var9 = this.renderManager.getEntityRenderObject(this.mc.player);
      GlStateManager.disableCull();
      RenderPlayer var10 = (RenderPlayer)var9;
      var10.a(this.mc.player);
      GlStateManager.enableCull();
   }

   private void func_178105_d(float var1) {
      float var2 = -0.4F * MathHelper.sin(MathHelper.sqrt_float(var1) * 3.1415927F);
      float var3 = 0.2F * MathHelper.sin(MathHelper.sqrt_float(var1) * 3.1415927F * 2.0F);
      float var4 = -0.2F * MathHelper.sin(var1 * 3.1415927F);
      GlStateManager.translate(var2, var3, var4);
   }

   private void a(asJ var1, float var2) {
      float var3 = (float)var1.getItemInUseCount() - var2 + 1.0F;
      float var4 = var3 / (float)this.itemToRender.getMaxItemUseDuration();
      float var5 = MathHelper.abs(MathHelper.cos(var3 / 4.0F * 3.1415927F) * 0.1F);
      if(var4 >= 0.8F) {
         var5 = 0.0F;
      }

      GlStateManager.translate(0.0F, var5, 0.0F);
      float var6 = 1.0F - (float)Math.pow((double)var4, 27.0D);
      GlStateManager.translate(var6 * 0.6F, var6 * -0.5F, var6 * 0.0F);
      GlStateManager.rotate(var6 * 90.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(var6 * 10.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.rotate(var6 * 30.0F, 0.0F, 0.0F, 1.0F);
   }

   private void transformFirstPersonItem(float var1, float var2) {
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

   private void a(float var1, asJ var2) {
      GlStateManager.rotate(-18.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.rotate(-12.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(-8.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.translate(-0.9F, 0.2F, 0.0F);
      float var3 = (float)this.itemToRender.getMaxItemUseDuration() - ((float)var2.getItemInUseCount() - var1 + 1.0F);
      float var4 = var3 / 20.0F;
      var4 = (var4 * var4 + var4 * 2.0F) / 3.0F;
      if(var4 > 1.0F) {
         var4 = 1.0F;
      }

      if(var4 > 0.1F) {
         float var5 = MathHelper.sin((var3 - 0.1F) * 1.3F);
         float var6 = var4 - 0.1F;
         float var7 = var5 * var6;
         GlStateManager.translate(var7 * 0.0F, var7 * 0.01F, var7 * 0.0F);
      }

      GlStateManager.translate(var4 * 0.0F, var4 * 0.0F, var4 * 0.1F);
      GlStateManager.scale(1.0F, 1.0F, 1.0F + var4 * 0.2F);
   }

   private void func_178103_d(float var1) {
      GlStateManager.translate(-0.5F, var1, 0.0F);
      GlStateManager.rotate(30.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(-80.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.rotate(60.0F, 0.0F, 1.0F, 0.0F);
   }

   public void renderItemInFirstPerson(float var1) {
      float var2 = ((Animations)Novoline.getInstance().getModuleManager().getModule(Animations.class)).isEnabled()?0.8F + ((Animations)Novoline.getInstance().getModuleManager().getModule(Animations.class)).getHeight() / 100.0F:1.0F;
      float var3 = var2 - (this.prevEquippedProgress + (this.equippedProgress - this.prevEquippedProgress) * var1);
      EntityPlayerSP var4 = this.mc.player;
      float var5 = var4.getSwingProgress(var1);
      float var6 = var4.prevRotationPitch + (var4.rotationPitch - var4.prevRotationPitch) * var1;
      float var7 = var4.prevRotationYaw + (var4.rotationYaw - var4.prevRotationYaw) * var1;
      float var8 = MathHelper.sin(MathHelper.sqrt_float(var5) * 3.1415927F);
      this.func_178101_a(var6, var7);
      this.a(var4);
      this.func_178110_a(var4, var1);
      GlStateManager.enableRescaleNormal();
      GlStateManager.pushMatrix();
      ++this.i;
      if(this.itemToRender != null) {
         if(this.itemToRender.getItem() instanceof ItemMap) {
            this.a(var4, var6, var3, var5);
         } else if(var4.getItemInUseCount() > 0) {
            EnumAction var9 = this.itemToRender.getItemUseAction();
            label0:
            switch(ItemRenderer$1.$SwitchMap$net$minecraft$item$EnumAction[var9.ordinal()]) {
            case 1:
               this.transformFirstPersonItem(var3, 0.0F);
               break;
            case 2:
            case 3:
               this.a(var4, var1);
               this.transformFirstPersonItem(var3, 0.0F);
               break;
            case 4:
               if(((Animations)Novoline.getInstance().getModuleManager().getModule(Animations.class)).isEnabled()) {
                  Animations var10 = (Animations)Novoline.getInstance().getModuleManager().getModule(Animations.class);
                  if(var10.getRotating()) {
                     GL11.glRotated((double)this.i, 0.0D, 0.0D, 1.0D);
                  }

                  String var11 = (String)var10.getAnim().get();
                  byte var12 = -1;
                  switch(var11.hashCode()) {
                  case -1943403498:
                     if(var11.equals("Spinning")) {
                        var12 = 15;
                     }
                     break;
                  case -1814666802:
                     if(var11.equals("Smooth")) {
                        var12 = 9;
                     }
                     break;
                  case -1808503203:
                     if(var11.equals("Stella")) {
                        var12 = 1;
                     }
                     break;
                  case -1807907582:
                     if(var11.equals("Styles")) {
                        var12 = 3;
                     }
                     break;
                  case -1805854619:
                     if(var11.equals("Swaing")) {
                        var12 = 7;
                     }
                     break;
                  case -1354722902:
                     if(var11.equals("Ethereal")) {
                        var12 = 13;
                     }
                     break;
                  case -672251180:
                     if(var11.equals("Interia")) {
                        var12 = 12;
                     }
                     break;
                  case -352259601:
                     if(var11.equals("Exhibition")) {
                        var12 = 14;
                     }
                     break;
                  case 48570:
                     if(var11.equals("1.7")) {
                        var12 = 2;
                     }
                     break;
                  case 77476110:
                     if(var11.equals("Punch")) {
                        var12 = 0;
                     }
                     break;
                  case 79882757:
                     if(var11.equals("Sigma")) {
                        var12 = 10;
                     }
                     break;
                  case 79973777:
                     if(var11.equals("Slide")) {
                        var12 = 11;
                     }
                     break;
                  case 80294102:
                     if(var11.equals("Swang")) {
                        var12 = 5;
                     }
                     break;
                  case 80294106:
                     if(var11.equals("Swank")) {
                        var12 = 4;
                     }
                     break;
                  case 80301790:
                     if(var11.equals("Swing")) {
                        var12 = 8;
                     }
                     break;
                  case 80307556:
                     if(var11.equals("Swong")) {
                        var12 = 6;
                     }
                  }

                  switch(var12) {
                  case 0:
                     this.transformFirstPersonItem(var3, 0.0F);
                     this.func_178103_d(0.2F);
                     GlStateManager.translate(0.1F, 0.2F, 0.3F);
                     GlStateManager.rotate(-var8 * 30.0F, -5.0F, 0.0F, 9.0F);
                     GlStateManager.rotate(-var8 * 10.0F, 1.0F, -0.4F, -0.5F);
                     break label0;
                  case 1:
                     this.transformFirstPersonItem(-0.1F, var5);
                     GlStateManager.translate(-0.5F, 0.4F, -0.2F);
                     GlStateManager.rotate(30.0F, 0.0F, 1.0F, 0.0F);
                     GlStateManager.rotate(-70.0F, 1.0F, 0.0F, 0.0F);
                     GlStateManager.rotate(40.0F, 0.0F, 1.0F, 0.0F);
                     break label0;
                  case 2:
                     this.transformFirstPersonItem(-0.1F, var5);
                     this.func_178103_d(0.2F);
                     break label0;
                  case 3:
                     this.transformFirstPersonItem(var3, 0.0F);
                     this.func_178103_d(0.2F);
                     float var13 = MathHelper.sin((double)MathHelper.sqrt_float(var5) * 3.141592653589793D);
                     GlStateManager.translate(-0.05F, 0.2F, 0.0F);
                     GlStateManager.rotate(-var13 * 70.0F / 2.0F, -8.0F, -0.0F, 9.0F);
                     GlStateManager.rotate(-var13 * 70.0F, 1.0F, -0.4F, -0.0F);
                     break label0;
                  case 4:
                     this.transformFirstPersonItem(var3 / 2.0F, var5);
                     float var14 = MathHelper.sin((double)MathHelper.sqrt_float(var5) * 3.141592653589793D);
                     GlStateManager.rotate(var14 * 30.0F, -var14, -0.0F, 9.0F);
                     GlStateManager.rotate(var14 * 40.0F, 1.0F, -var14, -0.0F);
                     this.func_178103_d(0.4F);
                     break label0;
                  case 5:
                     this.transformFirstPersonItem(var3 / 2.0F, var5);
                     float var15 = MathHelper.sin((double)MathHelper.sqrt_float(var5) * 3.141592653589793D);
                     GlStateManager.rotate(var15 * 30.0F / 2.0F, -var15, -0.0F, 9.0F);
                     GlStateManager.rotate(var15 * 40.0F, 1.0F, -var15 / 2.0F, -0.0F);
                     this.func_178103_d(0.4F);
                     break label0;
                  case 6:
                     this.transformFirstPersonItem(var3 / 2.0F, 0.0F);
                     float var16 = MathHelper.sin((double)(var5 * var5) * 3.141592653589793D);
                     GlStateManager.rotate(-var16 * 40.0F / 2.0F, var16 / 2.0F, -0.0F, 9.0F);
                     GlStateManager.rotate(-var16 * 30.0F, 1.0F, var16 / 2.0F, -0.0F);
                     this.func_178103_d(0.4F);
                     break label0;
                  case 7:
                     this.transformFirstPersonItem(var3 / 2.0F, -0.2F);
                     float var17 = MathHelper.sin((double)(var5 * var5) * 3.141592653589793D);
                     GlStateManager.rotate(-var17 / 19.0F, var17 / 20.0F, -0.0F, 9.0F);
                     GlStateManager.rotate(-var17 * 30.0F, 10.0F, var17 / 50.0F, 0.0F);
                     this.func_178103_d(0.4F);
                     break label0;
                  case 8:
                     this.transformFirstPersonItem(var3 / 2.0F, var5);
                     this.func_178103_d(0.4F);
                     break label0;
                  case 9:
                     this.transformFirstPersonItem(var3 / 1.5F, 0.0F);
                     this.func_178103_d(0.2F);
                     GlStateManager.translate(-0.05F, 0.3F, 0.3F);
                     GlStateManager.rotate(-var8 * 140.0F, 8.0F, 0.0F, 8.0F);
                     GlStateManager.rotate(var8 * 90.0F, 8.0F, 0.0F, 8.0F);
                     break label0;
                  case 10:
                     this.transformFirstPersonItem(var3 * 0.5F, 0.0F);
                     GlStateManager.rotate(-var8 * 55.0F / 2.0F, -8.0F, -0.0F, 9.0F);
                     GlStateManager.rotate(-var8 * 45.0F, 1.0F, var8 / 2.0F, -0.0F);
                     this.func_178103_d(0.2F);
                     GL11.glTranslated(1.2D, 0.3D, 0.5D);
                     GL11.glTranslatef(-1.0F, this.mc.player.isSneaking()?-0.1F:-0.2F, 0.2F);
                     break label0;
                  case 11:
                     this.transformFirstPersonItem(var3, 0.0F);
                     this.func_178103_d(0.2F);
                     GlStateManager.translate(-0.4F, 0.3F, 0.0F);
                     GlStateManager.rotate(-var8 * 35.0F, -8.0F, -0.0F, 9.0F);
                     GlStateManager.rotate(-var8 * 70.0F, 1.0F, -0.4F, -0.0F);
                     GL11.glTranslatef(-0.05F, this.mc.player.isSneaking()?-0.2F:0.0F, 0.1F);
                     break label0;
                  case 12:
                     this.transformFirstPersonItem(0.05F, var5);
                     GlStateManager.translate(-0.5F, 0.5F, 0.0F);
                     GlStateManager.rotate(30.0F, 0.0F, 1.0F, 0.0F);
                     GlStateManager.rotate(-80.0F, 1.0F, 0.0F, 0.0F);
                     GlStateManager.rotate(60.0F, 0.0F, 1.0F, 0.0F);
                     break label0;
                  case 13:
                     this.transformFirstPersonItem(var3, 0.0F);
                     this.func_178103_d(0.2F);
                     GlStateManager.translate(-0.05F, 0.2F, 0.2F);
                     GlStateManager.rotate(-var8 * 70.0F / 2.0F, -8.0F, -0.0F, 9.0F);
                     GlStateManager.rotate(-var8 * 70.0F, 1.0F, -0.4F, -0.0F);
                     break label0;
                  case 14:
                     GL11.glTranslated(-0.04D, 0.13D, 0.0D);
                     this.transformFirstPersonItem(var3 / 2.5F, 0.0F);
                     GlStateManager.rotate(-var8 * 40.0F / 2.0F, var8 / 2.0F, 1.0F, 4.0F);
                     GlStateManager.rotate(-var8 * 30.0F, 1.0F, var8 / 3.0F, -0.0F);
                     this.func_178103_d(0.2F);
                     break label0;
                  case 15:
                     GL11.glTranslated(-0.04D, 0.1D, 0.0D);
                     this.transformFirstPersonItem(var3 / 2.5F, 0.0F);
                     GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.2F);
                     GlStateManager.rotate((float)this.i, 0.0F, -1.0F, 0.0F);
                  }
               } else {
                  this.transformFirstPersonItem(var3, 0.0F);
                  this.func_178103_d(0.2F);
               }
               break;
            case 5:
               this.transformFirstPersonItem(var3, 0.0F);
               this.a(var1, var4);
            }
         } else {
            if(((Animations)Novoline.getInstance().getModuleManager().getModule(Animations.class)).isEnabled()) {
               Animations var18 = (Animations)Novoline.getInstance().getModuleManager().getModule(Animations.class);
               if(!var18.getHit().equalsIgnoreCase("Smooth")) {
                  this.func_178105_d(var5);
               }
            } else {
               this.func_178105_d(var5);
            }

            this.transformFirstPersonItem(var3 - 0.05F, var5);
         }

         this.renderItem(var4, this.itemToRender, ItemCameraTransforms$TransformType.FIRST_PERSON);
      } else if(!var4.isInvisible()) {
         this.b(var4, var3, var5);
      }

      GlStateManager.popMatrix();
      GlStateManager.disableRescaleNormal();
      RenderHelper.disableStandardItemLighting();
   }

   public void renderOverlays(float var1) {
      GlStateManager.disableAlpha();
      if(this.mc.player.isEntityInsideOpaqueBlock()) {
         IBlockState var2 = this.mc.world.getBlockState(new BlockPos(this.mc.player));
         BlockPos var3 = new BlockPos(this.mc.player);
         EntityPlayerSP var4 = this.mc.player;

         for(int var5 = 0; var5 < 8; ++var5) {
            double var6 = var4.posX + (double)(((float)(var5 % 2) - 0.5F) * var4.width * 0.8F);
            double var8 = var4.posY + (double)(((float)((var5 >> 1) % 2) - 0.5F) * 0.1F);
            double var10 = var4.posZ + (double)(((float)((var5 >> 2) % 2) - 0.5F) * var4.width * 0.8F);
            BlockPos var12 = new BlockPos(var6, var8 + (double)var4.getEyeHeight(), var10);
            IBlockState var13 = this.mc.world.getBlockState(var12);
            if(var13.getBlock().isVisuallyOpaque()) {
               var2 = var13;
               var3 = var12;
            }
         }

         if(var2.getBlock().getRenderType() != -1) {
            Object var14 = Reflector.getFieldValue(Reflector.RenderBlockOverlayEvent_OverlayType_BLOCK);
            if(!Reflector.b(Reflector.bD, new Object[]{this.mc.player, Float.valueOf(var1), var14, var2, var3})) {
               this.func_178108_a(var1, this.mc.getBlockRendererDispatcher().getBlockModelShapes().getTexture(var2));
            }
         }
      }

      if(!this.mc.player.isSpectator()) {
         if(this.mc.player.isInsideOfMaterial(Material.water) && !Reflector.b(Reflector.bl, new Object[]{this.mc.player, Float.valueOf(var1)})) {
            this.renderWaterOverlayTexture(var1);
         }

         if(this.mc.player.isBurning() && !Reflector.b(Reflector.f, new Object[]{this.mc.player, Float.valueOf(var1)})) {
            this.renderFireInFirstPerson(var1);
         }
      }

      GlStateManager.enableAlpha();
   }

   private void func_178108_a(float var1, TextureAtlasSprite var2) {
      this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
      Tessellator var3 = Tessellator.getInstance();
      WorldRenderer var4 = var3.getWorldRenderer();
      float var5 = 0.1F;
      GlStateManager.color(0.1F, 0.1F, 0.1F, 0.5F);
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
      var4.begin(7, DefaultVertexFormats.POSITION_TEX);
      var4.pos(-1.0D, -1.0D, -0.5D).tex((double)var12, (double)var14).endVertex();
      var4.pos(1.0D, -1.0D, -0.5D).tex((double)var11, (double)var14).endVertex();
      var4.pos(1.0D, 1.0D, -0.5D).tex((double)var11, (double)var13).endVertex();
      var4.pos(-1.0D, 1.0D, -0.5D).tex((double)var12, (double)var13).endVertex();
      var3.draw();
      GlStateManager.popMatrix();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
   }

   private void renderWaterOverlayTexture(float var1) {
      if(!Config.isShaders() || Shaders.isUnderwaterOverlay()) {
         this.mc.getTextureManager().bindTexture(RES_UNDERWATER_OVERLAY);
         Tessellator var2 = Tessellator.getInstance();
         WorldRenderer var3 = var2.getWorldRenderer();
         float var4 = this.mc.player.getBrightness(var1);
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
         float var11 = -this.mc.player.rotationYaw / 64.0F;
         float var12 = this.mc.player.rotationPitch / 64.0F;
         var3.begin(7, DefaultVertexFormats.POSITION_TEX);
         var3.pos(-1.0D, -1.0D, -0.5D).tex((double)(4.0F + var11), (double)(4.0F + var12)).endVertex();
         var3.pos(1.0D, -1.0D, -0.5D).tex((double)(0.0F + var11), (double)(4.0F + var12)).endVertex();
         var3.pos(1.0D, 1.0D, -0.5D).tex((double)(0.0F + var11), (double)(0.0F + var12)).endVertex();
         var3.pos(-1.0D, 1.0D, -0.5D).tex((double)(4.0F + var11), (double)(0.0F + var12)).endVertex();
         var2.draw();
         GlStateManager.popMatrix();
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.disableBlend();
      }

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
         var3.begin(7, DefaultVertexFormats.POSITION_TEX);
         var3.pos((double)var11, (double)var13, (double)var15).tex((double)var8, (double)var10).endVertex();
         var3.pos((double)var12, (double)var13, (double)var15).tex((double)var7, (double)var10).endVertex();
         var3.pos((double)var12, (double)var14, (double)var15).tex((double)var7, (double)var9).endVertex();
         var3.pos((double)var11, (double)var14, (double)var15).tex((double)var8, (double)var9).endVertex();
         var2.draw();
         GlStateManager.popMatrix();
      }

      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.disableBlend();
      GlStateManager.depthMask(true);
      GlStateManager.depthFunc(515);
   }

   public void updateEquippedItem() {
      this.prevEquippedProgress = this.equippedProgress;
      EntityPlayerSP var1 = this.mc.player;
      ItemStack var2 = var1.inventory.getCurrentItem();
      boolean var3 = false;
      if(this.itemToRender != null) {
         if(!this.itemToRender.getIsItemStackEqual(var2)) {
            if(Reflector.ab.d()) {
               boolean var4 = Reflector.d(this.itemToRender.getItem(), Reflector.ab, new Object[]{this.itemToRender, var2, Boolean.valueOf(this.equippedItemSlot != var1.inventory.currentItem)});
               this.itemToRender = var2;
               this.equippedItemSlot = var1.inventory.currentItem;
               return;
            }

            var3 = true;
         }
      } else {
         if(this.itemToRender == null) {
            ;
         }

         var3 = true;
      }

      float var9 = 0.4F;
      float var5 = 0.0F;
      float var6 = MathHelper.clamp_float(var5 - this.equippedProgress, -var9, var9);
      this.equippedProgress += var6;
      if(this.equippedProgress < 0.1F) {
         if(Config.isShaders()) {
            Shaders.setItemToRenderMain(var2);
         }

         this.itemToRender = var2;
         this.equippedItemSlot = var1.inventory.currentItem;
      }

   }

   public void resetEquippedProgress() {
      this.equippedProgress = 0.0F;
   }

   public void resetEquippedProgress2() {
      this.equippedProgress = 0.0F;
   }
}
