package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureCompass;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.MapData;
import optifine.Config;
import optifine.Reflector;
import org.lwjgl.opengl.GL11;
import shadersmod.client.ShadersTex;

public class RenderItemFrame extends Render {
   private final ModelResourceLocation field_177072_f = new ModelResourceLocation("item_frame", "normal");
   private final ModelResourceLocation field_177073_g = new ModelResourceLocation("item_frame", "map");
   private RenderItem field_177074_h;
   private static final ResourceLocation mapBackgroundTextures = new ResourceLocation("textures/map/map_background.png");
   private final Minecraft field_147917_g = Minecraft.getMinecraft();
   private static final String __OBFID = "CL_00001002";

   public void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9) {
      this.doRender((EntityItemFrame)var1, var2, var4, var6, var8, var9);
   }

   protected ResourceLocation getEntityTexture(Entity var1) {
      return this.getEntityTexture((EntityItemFrame)var1);
   }

   protected void func_177067_a(Entity var1, double var2, double var4, double var6) {
      this.func_147914_a((EntityItemFrame)var1, var2, var4, var6);
   }

   public void doRender(EntityItemFrame var1, double var2, double var4, double var6, float var8, float var9) {
      GlStateManager.pushMatrix();
      BlockPos var10 = var1.func_174857_n();
      double var11 = (double)var10.getX() - var1.posX + var2;
      double var13 = (double)var10.getY() - var1.posY + var4;
      double var15 = (double)var10.getZ() - var1.posZ + var6;
      GlStateManager.translate(var11 + 0.5D, var13 + 0.5D, var15 + 0.5D);
      GlStateManager.rotate(180.0F - var1.rotationYaw, 0.0F, 1.0F, 0.0F);
      this.renderManager.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
      BlockRendererDispatcher var17 = this.field_147917_g.getBlockRendererDispatcher();
      ModelManager var18 = var17.func_175023_a().func_178126_b();
      IBakedModel var19;
      if (var1.getDisplayedItem() != null && var1.getDisplayedItem().getItem() == Items.filled_map) {
         var19 = var18.getModel(this.field_177073_g);
      } else {
         var19 = var18.getModel(this.field_177072_f);
      }

      GlStateManager.pushMatrix();
      GlStateManager.translate(-0.5F, -0.5F, -0.5F);
      var17.func_175019_b().func_178262_a(var19, 1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.popMatrix();
      GlStateManager.translate(0.0F, 0.0F, 0.4375F);
      this.func_82402_b(var1);
      GlStateManager.popMatrix();
      this.func_147914_a(var1, var2 + (double)((float)var1.field_174860_b.getFrontOffsetX() * 0.3F), var4 - 0.25D, var6 + (double)((float)var1.field_174860_b.getFrontOffsetZ() * 0.3F));
   }

   public RenderItemFrame(RenderManager var1, RenderItem var2) {
      super(var1);
      this.field_177074_h = var2;
   }

   private void func_82402_b(EntityItemFrame var1) {
      ItemStack var2 = var1.getDisplayedItem();
      if (var2 != null) {
         EntityItem var3 = new EntityItem(var1.worldObj, 0.0D, 0.0D, 0.0D, var2);
         Item var4 = var3.getEntityItem().getItem();
         var3.getEntityItem().stackSize = 1;
         var3.hoverStart = 0.0F;
         GlStateManager.pushMatrix();
         GlStateManager.disableLighting();
         int var5 = var1.getRotation();
         if (var4 instanceof ItemMap) {
            var5 = var5 % 4 * 2;
         }

         GlStateManager.rotate((float)var5 * 360.0F / 8.0F, 0.0F, 0.0F, 1.0F);
         if (!Reflector.postForgeBusEvent(Reflector.RenderItemInFrameEvent_Constructor, var1, this)) {
            if (var4 instanceof ItemMap) {
               this.renderManager.renderEngine.bindTexture(mapBackgroundTextures);
               GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
               float var12 = 0.0078125F;
               GlStateManager.scale(var12, var12, var12);
               GlStateManager.translate(-64.0F, -64.0F, 0.0F);
               MapData var13 = Items.filled_map.getMapData(var3.getEntityItem(), var1.worldObj);
               GlStateManager.translate(0.0F, 0.0F, -1.0F);
               if (var13 != null) {
                  this.field_147917_g.entityRenderer.getMapItemRenderer().func_148250_a(var13, true);
               }
            } else {
               TextureAtlasSprite var6 = null;
               if (var4 == Items.compass) {
                  var6 = this.field_147917_g.getTextureMapBlocks().getAtlasSprite(TextureCompass.field_176608_l);
                  if (Config.isShaders()) {
                     ShadersTex.bindTextureMapForUpdateAndRender(this.field_147917_g.getTextureManager(), TextureMap.locationBlocksTexture);
                  } else {
                     this.field_147917_g.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
                  }

                  if (var6 instanceof TextureCompass) {
                     TextureCompass var7 = (TextureCompass)var6;
                     double var8 = var7.currentAngle;
                     double var10 = var7.angleDelta;
                     var7.currentAngle = 0.0D;
                     var7.angleDelta = 0.0D;
                     var7.updateCompass(var1.worldObj, var1.posX, var1.posZ, (double)MathHelper.wrapAngleTo180_float((float)(180 + var1.field_174860_b.getHorizontalIndex() * 90)), false, true);
                     var7.currentAngle = var8;
                     var7.angleDelta = var10;
                  } else {
                     var6 = null;
                  }
               }

               GlStateManager.scale(0.5F, 0.5F, 0.5F);
               if (!this.field_177074_h.func_175050_a(var3.getEntityItem()) || var4 instanceof ItemSkull) {
                  GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
               }

               GlStateManager.pushAttrib();
               RenderHelper.enableStandardItemLighting();
               this.field_177074_h.func_175043_b(var3.getEntityItem());
               RenderHelper.disableStandardItemLighting();
               GlStateManager.popAttrib();
               if (var6 != null && var6.getFrameCount() > 0) {
                  var6.updateAnimation();
               }
            }
         }

         GlStateManager.enableLighting();
         GlStateManager.popMatrix();
      }

      if (Config.isShaders()) {
         ShadersTex.updatingTex = null;
      }

   }

   protected void func_147914_a(EntityItemFrame var1, double var2, double var4, double var6) {
      if (Minecraft.isGuiEnabled() && var1.getDisplayedItem() != null && var1.getDisplayedItem().hasDisplayName() && this.renderManager.field_147941_i == var1) {
         float var8 = 1.6F;
         float var9 = 0.016666668F * var8;
         double var10 = var1.getDistanceSqToEntity(this.renderManager.livingPlayer);
         float var12 = var1.isSneaking() ? 32.0F : 64.0F;
         if (var10 < (double)(var12 * var12)) {
            String var13 = var1.getDisplayedItem().getDisplayName();
            if (var1.isSneaking()) {
               FontRenderer var14 = this.getFontRendererFromRenderManager();
               GlStateManager.pushMatrix();
               GlStateManager.translate((float)var2 + 0.0F, (float)var4 + var1.height + 0.5F, (float)var6);
               GL11.glNormal3f(0.0F, 1.0F, 0.0F);
               GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
               GlStateManager.rotate(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
               GlStateManager.scale(-var9, -var9, var9);
               GlStateManager.disableLighting();
               GlStateManager.translate(0.0F, 0.25F / var9, 0.0F);
               GlStateManager.depthMask(false);
               GlStateManager.enableBlend();
               GlStateManager.blendFunc(770, 771);
               Tessellator var15 = Tessellator.getInstance();
               WorldRenderer var16 = var15.getWorldRenderer();
               GlStateManager.func_179090_x();
               var16.startDrawingQuads();
               int var17 = var14.getStringWidth(var13) / 2;
               var16.func_178960_a(0.0F, 0.0F, 0.0F, 0.25F);
               var16.addVertex((double)(-var17 - 1), -1.0D, 0.0D);
               var16.addVertex((double)(-var17 - 1), 8.0D, 0.0D);
               var16.addVertex((double)(var17 + 1), 8.0D, 0.0D);
               var16.addVertex((double)(var17 + 1), -1.0D, 0.0D);
               var15.draw();
               GlStateManager.func_179098_w();
               GlStateManager.depthMask(true);
               var14.drawString(var13, (double)(-var14.getStringWidth(var13) / 2), 0.0D, 553648127);
               GlStateManager.enableLighting();
               GlStateManager.disableBlend();
               GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
               GlStateManager.popMatrix();
            } else {
               this.renderLivingLabel(var1, var13, var2, var4, var6, 64);
            }
         }
      }

   }

   protected ResourceLocation getEntityTexture(EntityItemFrame var1) {
      return null;
   }
}
