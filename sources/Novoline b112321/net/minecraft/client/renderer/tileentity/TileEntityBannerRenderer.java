package net.minecraft.client.renderer.tileentity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBanner;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.LayeredColorMaskTexture;
import net.minecraft.client.renderer.tileentity.TileEntityBannerRenderer$TimedBannerTexture;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.tileentity.TileEntityBanner$EnumBannerPattern;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class TileEntityBannerRenderer extends TileEntitySpecialRenderer {
   private static final Map DESIGNS = Maps.newHashMap();
   private static final ResourceLocation BANNERTEXTURES = new ResourceLocation("textures/entity/banner_base.png");
   private ModelBanner bannerModel = new ModelBanner();

   public void renderTileEntityAt(TileEntityBanner var1, double var2, double var4, double var6, float var8, int var9) {
      boolean var10 = var1.getWorld() != null;
      boolean var11 = var1.getBlockType() == Blocks.standing_banner;
      int var12 = var1.getBlockMetadata();
      long var13 = var1.getWorld().getTotalWorldTime();
      GlStateManager.pushMatrix();
      float var15 = 0.6666667F;
      GlStateManager.translate((float)var2 + 0.5F, (float)var4 + 0.75F * var15, (float)var6 + 0.5F);
      float var16 = (float)(var12 * 360) / 16.0F;
      GlStateManager.rotate(-var16, 0.0F, 1.0F, 0.0F);
      this.bannerModel.bannerStand.showModel = true;
      BlockPos var19 = var1.getPos();
      float var17 = (float)(var19.getX() * 7 + var19.getY() * 9 + var19.getZ() * 13) + (float)var13 + var8;
      this.bannerModel.bannerSlate.rotateAngleX = (-0.0125F + 0.01F * MathHelper.cos(var17 * 3.1415927F * 0.02F)) * 3.1415927F;
      GlStateManager.enableRescaleNormal();
      ResourceLocation var18 = this.func_178463_a(var1);
      this.bindTexture(var18);
      GlStateManager.pushMatrix();
      GlStateManager.scale(var15, -var15, -var15);
      this.bannerModel.renderBanner();
      GlStateManager.popMatrix();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.popMatrix();
   }

   private ResourceLocation func_178463_a(TileEntityBanner var1) {
      String var2 = var1.func_175116_e();
      if(var2.isEmpty()) {
         return null;
      } else {
         TileEntityBannerRenderer$TimedBannerTexture var3 = (TileEntityBannerRenderer$TimedBannerTexture)DESIGNS.get(var2);
         if(DESIGNS.size() >= 256) {
            long var4 = System.currentTimeMillis();
            Iterator var6 = DESIGNS.keySet().iterator();

            while(var6.hasNext()) {
               String var7 = (String)var6.next();
               TileEntityBannerRenderer$TimedBannerTexture var8 = (TileEntityBannerRenderer$TimedBannerTexture)DESIGNS.get(var7);
               if(var4 - var8.systemTime > 60000L) {
                  Minecraft.getInstance().getTextureManager().deleteTexture(var8.bannerTexture);
                  var6.remove();
               }
            }

            if(DESIGNS.size() >= 256) {
               return null;
            }
         }

         List var10 = var1.getPatternList();
         List var5 = var1.getColorList();
         ArrayList var11 = Lists.newArrayList();

         for(TileEntityBanner$EnumBannerPattern var13 : var10) {
            var11.add("textures/entity/banner/" + var13.getPatternName() + ".png");
         }

         var3 = new TileEntityBannerRenderer$TimedBannerTexture();
         var3.bannerTexture = new ResourceLocation(var2);
         Minecraft.getInstance().getTextureManager().loadTexture(var3.bannerTexture, new LayeredColorMaskTexture(BANNERTEXTURES, var11, var5));
         DESIGNS.put(var2, var3);
         var3.systemTime = System.currentTimeMillis();
         return var3.bannerTexture;
      }
   }
}
