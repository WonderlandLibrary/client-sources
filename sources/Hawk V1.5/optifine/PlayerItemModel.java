package optifine;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

public class PlayerItemModel {
   private ResourceLocation locationMissing = new ResourceLocation("textures/blocks/wool_colored_red.png");
   public static final int ATTACH_RIGHT_ARM = 3;
   public static final int ATTACH_CAPE = 6;
   private ResourceLocation textureLocation = null;
   public static final int ATTACH_LEFT_LEG = 4;
   private PlayerItemRenderer[] modelRenderers = new PlayerItemRenderer[0];
   public static final int ATTACH_HEAD = 1;
   private DynamicTexture texture = null;
   public static final int ATTACH_BODY = 0;
   private Dimension textureSize = null;
   public static final int ATTACH_RIGHT_LEG = 5;
   private boolean usePlayerTexture = false;
   private BufferedImage textureImage = null;
   public static final int ATTACH_LEFT_ARM = 2;

   public void render(ModelBiped var1, AbstractClientPlayer var2, float var3, float var4) {
      TextureManager var5 = Config.getTextureManager();
      if (this.usePlayerTexture) {
         var5.bindTexture(var2.getLocationSkin());
      } else if (this.textureLocation != null) {
         if (this.texture == null && this.textureImage != null) {
            this.texture = new DynamicTexture(this.textureImage);
            Minecraft.getMinecraft().getTextureManager().loadTexture(this.textureLocation, this.texture);
         }

         var5.bindTexture(this.textureLocation);
      } else {
         var5.bindTexture(this.locationMissing);
      }

      for(int var6 = 0; var6 < this.modelRenderers.length; ++var6) {
         PlayerItemRenderer var7 = this.modelRenderers[var6];
         GlStateManager.pushMatrix();
         if (var2.isSneaking()) {
            GlStateManager.translate(0.0F, 0.2F, 0.0F);
         }

         var7.render(var1, var3);
         GlStateManager.popMatrix();
      }

   }

   public BufferedImage getTextureImage() {
      return this.textureImage;
   }

   public void setTextureLocation(ResourceLocation var1) {
      this.textureLocation = var1;
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

   public void setTextureImage(BufferedImage var1) {
      this.textureImage = var1;
   }

   public PlayerItemModel(Dimension var1, boolean var2, PlayerItemRenderer[] var3) {
      this.textureSize = var1;
      this.usePlayerTexture = var2;
      this.modelRenderers = var3;
   }

   public boolean isUsePlayerTexture() {
      return this.usePlayerTexture;
   }

   public DynamicTexture getTexture() {
      return this.texture;
   }

   public ResourceLocation getTextureLocation() {
      return this.textureLocation;
   }
}
