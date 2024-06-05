package net.minecraft.client.renderer.tileentity;

import net.aA7;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms$TransformType;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureCompass;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
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
import net.optifine.Config;
import net.optifine.Reflector;
import net.shadersmod.client.ShadersTex;
import org.lwjgl.opengl.GL11;

public class RenderItemFrame extends Render {
   private static final ResourceLocation mapBackgroundTextures = new ResourceLocation("textures/map/map_background.png");
   private final Minecraft mc = Minecraft.getInstance();
   private final ModelResourceLocation itemFrameModel = new ModelResourceLocation("item_frame", "normal");
   private final ModelResourceLocation mapModel = new ModelResourceLocation("item_frame", "map");
   private RenderItem itemRenderer;
   private static final String h = "CL_00001002";

   public RenderItemFrame(RenderManager var1, RenderItem var2) {
      super(var1);
      this.itemRenderer = var2;
   }

   public void doRender(EntityItemFrame var1, double var2, double var4, double var6, float var8, float var9) {
      GlStateManager.pushMatrix();
      BlockPos var10 = var1.getHangingPosition();
      double var11 = (double)var10.getX() - var1.posX + var2;
      double var13 = (double)var10.getY() - var1.posY + var4;
      double var15 = (double)var10.getZ() - var1.posZ + var6;
      GlStateManager.translate(var11 + 0.5D, var13 + 0.5D, var15 + 0.5D);
      GlStateManager.rotate(180.0F - var1.rotationYaw, 0.0F, 1.0F, 0.0F);
      this.renderManager.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
      BlockRendererDispatcher var17 = this.mc.getBlockRendererDispatcher();
      ModelManager var18 = var17.getBlockModelShapes().getModelManager();
      IBakedModel var19;
      if(var1.getDisplayedItem() != null && var1.getDisplayedItem().getItem() == Items.filled_map) {
         var19 = var18.a(this.mapModel);
      } else {
         var19 = var18.a(this.itemFrameModel);
      }

      GlStateManager.pushMatrix();
      GlStateManager.translate(-0.5F, -0.5F, -0.5F);
      var17.getBlockModelRenderer().renderModelBrightnessColor(var19, 1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.popMatrix();
      GlStateManager.translate(0.0F, 0.0F, 0.4375F);
      this.renderItem(var1);
      GlStateManager.popMatrix();
      this.renderName(var1, var2 + (double)((float)var1.facingDirection.getFrontOffsetX() * 0.3F), var4 - 0.25D, var6 + (double)((float)var1.facingDirection.getFrontOffsetZ() * 0.3F));
   }

   protected ResourceLocation getEntityTexture(EntityItemFrame var1) {
      return null;
   }

   private void renderItem(EntityItemFrame var1) {
      ItemStack var2 = var1.getDisplayedItem();
      EntityItem var3 = new EntityItem(var1.worldObj, 0.0D, 0.0D, 0.0D, var2);
      Item var4 = var3.getEntityItem().getItem();
      var3.getEntityItem().stackSize = 1;
      var3.hoverStart = 0.0F;
      GlStateManager.pushMatrix();
      GlStateManager.disableLighting();
      int var5 = var1.getRotation();
      if(var4 instanceof ItemMap) {
         var5 = var5 % 4 * 2;
      }

      GlStateManager.rotate((float)var5 * 360.0F / 8.0F, 0.0F, 0.0F, 1.0F);
      if(!Reflector.a(Reflector.cP, new Object[]{var1, this})) {
         if(var4 instanceof ItemMap) {
            this.renderManager.renderEngine.bindTexture(mapBackgroundTextures);
            GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
            float var6 = 0.0078125F;
            GlStateManager.scale(var6, var6, var6);
            GlStateManager.translate(-64.0F, -64.0F, 0.0F);
            MapData var7 = Items.filled_map.getMapData(var3.getEntityItem(), var1.worldObj);
            GlStateManager.translate(0.0F, 0.0F, -1.0F);
            this.mc.entityRenderer.getMapItemRenderer().renderMap(var7, true);
         } else {
            TextureAtlasSprite var12 = null;
            if(var4 == Items.compass) {
               var12 = this.mc.getTextureMapBlocks().getAtlasSprite(TextureCompass.field_176608_l);
               if(Config.isShaders()) {
                  ShadersTex.bindTextureMapForUpdateAndRender(this.mc.getTextureManager(), TextureMap.locationBlocksTexture);
               } else {
                  this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
               }

               if(var12 instanceof TextureCompass) {
                  TextureCompass var13 = (TextureCompass)var12;
                  double var8 = var13.currentAngle;
                  double var10 = var13.angleDelta;
                  var13.currentAngle = 0.0D;
                  var13.angleDelta = 0.0D;
                  aA7.a(var13, var1.worldObj, var1.posX, var1.posZ, (double)MathHelper.wrapAngleTo180_float((float)(180 + var1.facingDirection.getHorizontalIndex() * 90)), false, true);
                  var13.currentAngle = var8;
                  var13.angleDelta = var10;
               } else {
                  var12 = null;
               }
            }

            GlStateManager.scale(0.5F, 0.5F, 0.5F);
            if(!this.itemRenderer.shouldRenderItemIn3D(var3.getEntityItem()) || var4 instanceof ItemSkull) {
               GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
            }

            GlStateManager.pushAttrib();
            RenderHelper.enableStandardItemLighting();
            this.itemRenderer.func_181564_a(var3.getEntityItem(), ItemCameraTransforms$TransformType.FIXED);
            RenderHelper.disableStandardItemLighting();
            GlStateManager.popAttrib();
            if(var12.getFrameCount() > 0) {
               var12.updateAnimation();
            }
         }
      }

      GlStateManager.enableLighting();
      GlStateManager.popMatrix();
   }

   protected void renderName(EntityItemFrame var1, double var2, double var4, double var6) {
      if(Minecraft.isGuiEnabled() && var1.getDisplayedItem() != null && var1.getDisplayedItem().hasDisplayName() && this.renderManager.pointedEntity == var1) {
         float var8 = 1.6F;
         float var9 = 0.016666668F * var8;
         double var10 = var1.getDistanceSqToEntity(this.renderManager.livingPlayer);
         float var12 = var1.isSneaking()?32.0F:64.0F;
         if(var10 < (double)(var12 * var12)) {
            String var13 = var1.getDisplayedItem().getDisplayName();
            if(var1.isSneaking()) {
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
               int var17 = var14.d(var13) / 2;
               GlStateManager.disableTexture2D();
               var16.begin(7, DefaultVertexFormats.POSITION_COLOR);
               var16.pos((double)(-var17 - 1), -1.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
               var16.pos((double)(-var17 - 1), 8.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
               var16.pos((double)(var17 + 1), 8.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
               var16.pos((double)(var17 + 1), -1.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
               var15.draw();
               GlStateManager.enableTexture2D();
               GlStateManager.depthMask(true);
               var14.drawString(var13, (float)(-var14.d(var13) / 2), 0.0F, 553648127);
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

   protected ResourceLocation getEntityTexture(Entity var1) {
      return this.getEntityTexture((EntityItemFrame)var1);
   }

   protected void renderName(Entity var1, double var2, double var4, double var6) {
      this.renderName((EntityItemFrame)var1, var2, var4, var6);
   }

   public void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9) {
      this.doRender((EntityItemFrame)var1, var2, var4, var6, var8, var9);
   }
}
