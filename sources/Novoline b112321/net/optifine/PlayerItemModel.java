package net.optifine;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import net.asJ;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.optifine.Config;
import net.optifine.MatchBlock;
import net.optifine.PlayerItemRenderer;

public class PlayerItemModel {
   private Dimension textureSize = null;
   private boolean usePlayerTexture = false;
   private PlayerItemRenderer[] modelRenderers = new PlayerItemRenderer[0];
   private ResourceLocation textureLocation = null;
   private BufferedImage textureImage = null;
   private DynamicTexture texture = null;
   private ResourceLocation locationMissing = new ResourceLocation("textures/blocks/wool_colored_red.png");
   public static final int ATTACH_BODY = 0;
   public static final int ATTACH_HEAD = 1;
   public static final int ATTACH_LEFT_ARM = 2;
   public static final int ATTACH_RIGHT_ARM = 3;
   public static final int ATTACH_LEFT_LEG = 4;
   public static final int ATTACH_RIGHT_LEG = 5;
   public static final int ATTACH_CAPE = 6;

   public PlayerItemModel(Dimension var1, boolean var2, PlayerItemRenderer[] var3) {
      this.textureSize = var1;
      this.usePlayerTexture = var2;
      this.modelRenderers = var3;
   }

   public void a(ModelBiped var1, asJ var2, float var3, float var4) {
      MatchBlock.b();
      TextureManager var6 = Config.getTextureManager();
      if(this.usePlayerTexture) {
         var6.bindTexture(var2.getLocationSkin());
      }

      if(this.textureLocation != null) {
         if(this.texture == null && this.textureImage != null) {
            this.texture = new DynamicTexture(this.textureImage);
            Minecraft.getInstance().getTextureManager().loadTexture(this.textureLocation, this.texture);
         }

         var6.bindTexture(this.textureLocation);
      }

      var6.bindTexture(this.locationMissing);
      int var7 = 0;
      if(var7 < this.modelRenderers.length) {
         PlayerItemRenderer var8 = this.modelRenderers[var7];
         GlStateManager.pushMatrix();
         if(var2.isSneaking()) {
            GlStateManager.translate(0.0F, 0.2F, 0.0F);
         }

         var8.render(var1, var3);
         GlStateManager.popMatrix();
         ++var7;
      }

   }

   public static ModelRenderer getAttachModel(ModelBiped var0, int var1) {
      switch(var1) {
      case 0:
         return var0.bipedBody;
      case 1:
         return var0.bipedHead;
      case 2:
         return var0.bipedLeftArm;
      case 3:
         return var0.bipedRightArm;
      case 4:
         return var0.bipedLeftLeg;
      case 5:
         return var0.bipedRightLeg;
      default:
         return null;
      }
   }

   public BufferedImage getTextureImage() {
      return this.textureImage;
   }

   public void setTextureImage(BufferedImage var1) {
      this.textureImage = var1;
   }

   public DynamicTexture getTexture() {
      return this.texture;
   }

   public ResourceLocation getTextureLocation() {
      return this.textureLocation;
   }

   public void setTextureLocation(ResourceLocation var1) {
      this.textureLocation = var1;
   }

   public boolean isUsePlayerTexture() {
      return this.usePlayerTexture;
   }
}
