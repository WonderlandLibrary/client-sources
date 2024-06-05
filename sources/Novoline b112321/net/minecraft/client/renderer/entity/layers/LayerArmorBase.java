package net.minecraft.client.renderer.entity.layers;

import cc.novoline.Novoline;
import cc.novoline.modules.configurations.property.object.ColorProperty;
import cc.novoline.modules.visual.GlintColorize;
import cc.novoline.utils.RenderUtils;
import com.google.common.collect.Maps;
import java.awt.Color;
import java.util.Map;
import net.aEd;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase$LayerArmorBase$1;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.optifine.Config;
import net.optifine.CustomItems;
import net.optifine.Reflector;
import net.shadersmod.client.Shaders;
import net.shadersmod.client.ShadersRender;

public abstract class LayerArmorBase implements LayerRenderer {
   protected static final ResourceLocation ENCHANTED_ITEM_GLINT_RES = new ResourceLocation("textures/misc/enchanted_item_glint.png");
   protected ModelBase field_177189_c;
   protected ModelBase field_177186_d;
   private final RendererLivingEntity renderer;
   private float alpha = 1.0F;
   private float colorR = 1.0F;
   private float colorG = 1.0F;
   private float colorB = 1.0F;
   private boolean field_177193_i;
   private static final Map ARMOR_TEXTURE_RES_MAP = Maps.newHashMap();
   private static final String h = "CL_00002428";

   public LayerArmorBase(RendererLivingEntity var1) {
      this.renderer = var1;
      this.initArmor();
   }

   public void doRenderLayer(EntityLivingBase var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      this.renderLayer(var1, var2, var3, var4, var5, var6, var7, var8, 4);
      this.renderLayer(var1, var2, var3, var4, var5, var6, var7, var8, 3);
      this.renderLayer(var1, var2, var3, var4, var5, var6, var7, var8, 2);
      this.renderLayer(var1, var2, var3, var4, var5, var6, var7, var8, 1);
   }

   public boolean shouldCombineTextures() {
      return false;
   }

   private void renderLayer(EntityLivingBase var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, int var9) {
      ItemStack var10 = this.getCurrentArmor(var1, var9);
      if(var10.getItem() instanceof ItemArmor) {
         ItemArmor var11 = (ItemArmor)var10.getItem();
         ModelBase var12 = this.func_177175_a(var9);
         var12.setModelAttributes(this.renderer.getMainModel());
         var12.setLivingAnimations(var1, var2, var3, var4);
         if(Reflector.ForgeHooksClient.exists()) {
            var12 = this.a(var1, var10, var9, var12);
         }

         this.func_177179_a(var12, var9);
         boolean var13 = this.isSlotForLeggings(var9);
         if(!Config.isCustomItems() || !CustomItems.bindCustomArmorTexture(var10, 2, (String)null)) {
            if(Reflector.bS.d()) {
               this.renderer.bindTexture(this.getArmorResource(var1, var10, 2, (String)null));
            } else {
               this.renderer.bindTexture(this.getArmorResource(var11, var13));
            }
         }

         if(Reflector.bS.d()) {
            int var18 = var11.getColor(var10);
            if(var18 != -1) {
               float var19 = (float)(var18 >> 16 & 255) / 255.0F;
               float var20 = (float)(var18 >> 8 & 255) / 255.0F;
               float var21 = (float)(var18 & 255) / 255.0F;
               GlStateManager.color(this.colorR * var19, this.colorG * var20, this.colorB * var21, this.alpha);
               var12.render(var1, var2, var3, var5, var6, var7, var8);
               if(!Config.isCustomItems() || !CustomItems.bindCustomArmorTexture(var10, 2, "overlay")) {
                  this.renderer.bindTexture(this.getArmorResource(var1, var10, 2, "overlay"));
               }
            }

            GlStateManager.color(this.colorR, this.colorG, this.colorB, this.alpha);
            var12.render(var1, var2, var3, var5, var6, var7, var8);
            if(!this.field_177193_i && var10.isItemEnchanted() && (!Config.isCustomItems() || !aEd.a(var1, var10, var12, var2, var3, var4, var5, var6, var7, var8))) {
               this.func_177183_a(var1, var12, var2, var3, var4, var5, var6, var7, var8);
            }

            return;
         }

         switch(LayerArmorBase$LayerArmorBase$1.field_178747_a[var11.getArmorMaterial().ordinal()]) {
         case 1:
            int var14 = var11.getColor(var10);
            float var15 = (float)(var14 >> 16 & 255) / 255.0F;
            float var16 = (float)(var14 >> 8 & 255) / 255.0F;
            float var17 = (float)(var14 & 255) / 255.0F;
            GlStateManager.color(this.colorR * var15, this.colorG * var16, this.colorB * var17, this.alpha);
            var12.render(var1, var2, var3, var5, var6, var7, var8);
            if(!Config.isCustomItems() || !CustomItems.bindCustomArmorTexture(var10, 2, "overlay")) {
               this.renderer.bindTexture(this.getArmorResource(var11, var13, "overlay"));
            }
         case 2:
         case 3:
         case 4:
         case 5:
            GlStateManager.color(this.colorR, this.colorG, this.colorB, this.alpha);
            var12.render(var1, var2, var3, var5, var6, var7, var8);
         }

         if(!this.field_177193_i && var10.isItemEnchanted() && (!Config.isCustomItems() || !aEd.a(var1, var10, var12, var2, var3, var4, var5, var6, var7, var8))) {
            this.func_177183_a(var1, var12, var2, var3, var4, var5, var6, var7, var8);
         }
      }

   }

   public ItemStack getCurrentArmor(EntityLivingBase var1, int var2) {
      return var1.getCurrentArmor(var2 - 1);
   }

   public ModelBase func_177175_a(int var1) {
      return this.isSlotForLeggings(var1)?this.field_177189_c:this.field_177186_d;
   }

   private boolean isSlotForLeggings(int var1) {
      return var1 == 2;
   }

   private void func_177183_a(EntityLivingBase var1, ModelBase var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9) {
      if((!Config.isCustomItems() || CustomItems.isUseGlint()) && (!Config.isShaders() || !Shaders.isShadowPass)) {
         float var10 = (float)var1.ticksExisted + var5;
         this.renderer.bindTexture(ENCHANTED_ITEM_GLINT_RES);
         if(Config.isShaders()) {
            ShadersRender.renderEnchantedGlintBegin();
         }

         GlStateManager.enableBlend();
         GlStateManager.depthFunc(514);
         GlStateManager.depthMask(false);
         float var11 = 0.5F;
         GlStateManager.color(var11, var11, var11, 1.0F);

         for(int var12 = 0; var12 < 2; ++var12) {
            GlStateManager.disableLighting();
            GlStateManager.blendFunc(768, 1);
            float var13 = 0.76F;
            float var14 = 0.5F * var13;
            float var15 = 0.25F * var13;
            float var16 = 0.8F * var13;
            this.setupColor(var14, var15, var16);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            float var17 = 0.33333334F;
            GlStateManager.scale(var17, var17, var17);
            GlStateManager.rotate(30.0F - (float)var12 * 60.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.translate(0.0F, var10 * (0.001F + (float)var12 * 0.003F) * 20.0F, 0.0F);
            GlStateManager.matrixMode(5888);
            var2.render(var1, var3, var4, var6, var7, var8, var9);
         }

         GlStateManager.matrixMode(5890);
         GlStateManager.loadIdentity();
         GlStateManager.matrixMode(5888);
         GlStateManager.enableLighting();
         GlStateManager.depthMask(true);
         GlStateManager.depthFunc(515);
         GlStateManager.disableBlend();
         if(Config.isShaders()) {
            ShadersRender.renderEnchantedGlintEnd();
         }
      }

   }

   private void setupColor(float var1, float var2, float var3) {
      if(((GlintColorize)Novoline.getInstance().getModuleManager().getModule(GlintColorize.class)).isEnabled()) {
         GlintColorize var4 = (GlintColorize)Novoline.getInstance().getModuleManager().getModule(GlintColorize.class);
         ColorProperty var5 = var4.getColor();
         if(((Boolean)var4.getRainbow().get()).booleanValue()) {
            float var6 = (float)var4.getOffset() / 255.0F;
            float[] var7 = var5.getHSB();
            float[] var8 = RenderUtils.getRGBAs(Color.getHSBColor(var6, var7[1], var7[2]).getRGB());
            GlStateManager.color(var8[0], var8[1], var8[2], 1.0F);
         } else {
            Color var10 = var5.getAwtColor();
            int var11 = var10.getRed();
            int var12 = var10.getGreen();
            int var9 = var10.getBlue();
            GlStateManager.color((float)var11 / 255.0F, (float)var12 / 255.0F, (float)var9 / 255.0F, 1.0F);
         }
      } else {
         GlStateManager.color(var1, var2, var3, 1.0F);
      }

   }

   private ResourceLocation getArmorResource(ItemArmor var1, boolean var2) {
      return this.getArmorResource(var1, var2, (String)null);
   }

   private ResourceLocation getArmorResource(ItemArmor var1, boolean var2, String var3) {
      String var4 = String.format("textures/models/armor/%s_layer_%d%s.png", new Object[]{var1.getArmorMaterial().getName(), Integer.valueOf(2), ""});
      ResourceLocation var5 = (ResourceLocation)ARMOR_TEXTURE_RES_MAP.get(var4);
      var5 = new ResourceLocation(var4);
      ARMOR_TEXTURE_RES_MAP.put(var4, var5);
      return var5;
   }

   protected abstract void initArmor();

   protected abstract void func_177179_a(ModelBase var1, int var2);

   protected ModelBase a(EntityLivingBase var1, ItemStack var2, int var3, ModelBase var4) {
      return var4;
   }

   public ResourceLocation getArmorResource(Entity var1, ItemStack var2, int var3, String var4) {
      ItemArmor var5 = (ItemArmor)var2.getItem();
      String var6 = var5.getArmorMaterial().getName();
      String var7 = "minecraft";
      int var8 = var6.indexOf(58);
      if(var8 != -1) {
         var7 = var6.substring(0, var8);
         var6 = var6.substring(var8 + 1);
      }

      String var9 = String.format("%s:textures/models/armor/%s_layer_%d%s.png", new Object[]{var7, var6, Integer.valueOf(var3 == 2?2:1), ""});
      var9 = Reflector.e(Reflector.bS, new Object[]{var1, var2, var9, Integer.valueOf(var3), var4});
      ResourceLocation var10 = (ResourceLocation)ARMOR_TEXTURE_RES_MAP.get(var9);
      var10 = new ResourceLocation(var9);
      ARMOR_TEXTURE_RES_MAP.put(var9, var10);
      return var10;
   }
}
