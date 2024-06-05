package net.minecraft.client.renderer.entity.layers;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import optifine.Config;
import optifine.CustomItems;
import optifine.Reflector;
import shadersmod.client.Shaders;
import shadersmod.client.ShadersRender;

public abstract class LayerArmorBase implements LayerRenderer {
   private static final Map field_177191_j = Maps.newHashMap();
   private float field_177185_g = 1.0F;
   private float field_177192_h = 1.0F;
   private static final String __OBFID = "CL_00002428";
   private float field_177184_f = 1.0F;
   protected ModelBase field_177186_d;
   protected static final ResourceLocation field_177188_b = new ResourceLocation("textures/misc/enchanted_item_glint.png");
   private boolean field_177193_i;
   protected ModelBase field_177189_c;
   private final RendererLivingEntity field_177190_a;
   private float field_177187_e = 1.0F;

   public LayerArmorBase(RendererLivingEntity var1) {
      this.field_177190_a = var1;
      this.func_177177_a();
   }

   private ResourceLocation func_177181_a(ItemArmor var1, boolean var2) {
      return this.func_177178_a(var1, var2, (String)null);
   }

   private boolean func_177180_b(int var1) {
      return var1 == 2;
   }

   public ItemStack func_177176_a(EntityLivingBase var1, int var2) {
      return var1.getCurrentArmor(var2 - 1);
   }

   public void doRenderLayer(EntityLivingBase var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      this.func_177182_a(var1, var2, var3, var4, var5, var6, var7, var8, 4);
      this.func_177182_a(var1, var2, var3, var4, var5, var6, var7, var8, 3);
      this.func_177182_a(var1, var2, var3, var4, var5, var6, var7, var8, 2);
      this.func_177182_a(var1, var2, var3, var4, var5, var6, var7, var8, 1);
   }

   private ResourceLocation func_177178_a(ItemArmor var1, boolean var2, String var3) {
      String var4 = String.format("textures/models/armor/%s_layer_%d%s.png", var1.getArmorMaterial().func_179242_c(), var2 ? 2 : 1, var3 == null ? "" : String.format("_%s", var3));
      ResourceLocation var5 = (ResourceLocation)field_177191_j.get(var4);
      if (var5 == null) {
         var5 = new ResourceLocation(var4);
         field_177191_j.put(var4, var5);
      }

      return var5;
   }

   public boolean shouldCombineTextures() {
      return false;
   }

   public ModelBase func_177175_a(int var1) {
      return this.func_177180_b(var1) ? this.field_177189_c : this.field_177186_d;
   }

   protected abstract void func_177179_a(ModelBase var1, int var2);

   private void func_177182_a(EntityLivingBase var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, int var9) {
      ItemStack var10 = this.func_177176_a(var1, var9);
      if (var10 != null && var10.getItem() instanceof ItemArmor) {
         ItemArmor var11 = (ItemArmor)var10.getItem();
         ModelBase var12 = this.func_177175_a(var9);
         var12.setModelAttributes(this.field_177190_a.getMainModel());
         var12.setLivingAnimations(var1, var2, var3, var4);
         if (Reflector.ForgeHooksClient_getArmorModel.exists()) {
            var12 = (ModelBase)Reflector.call(Reflector.ForgeHooksClient_getArmorModel, var1, var10, var9, var12);
         }

         this.func_177179_a(var12, var9);
         boolean var13 = this.func_177180_b(var9);
         if (!Config.isCustomItems() || !CustomItems.bindCustomArmorTexture(var10, var13 ? 2 : 1, (String)null)) {
            if (Reflector.ForgeHooksClient_getArmorTexture.exists()) {
               this.field_177190_a.bindTexture(this.getArmorResource(var1, var10, var13 ? 2 : 1, (String)null));
            } else {
               this.field_177190_a.bindTexture(this.func_177181_a(var11, var13));
            }
         }

         int var14;
         float var15;
         float var16;
         float var17;
         if (Reflector.ForgeHooksClient_getArmorTexture.exists()) {
            var14 = var11.getColor(var10);
            if (var14 != -1) {
               var15 = (float)(var14 >> 16 & 255) / 255.0F;
               var16 = (float)(var14 >> 8 & 255) / 255.0F;
               var17 = (float)(var14 & 255) / 255.0F;
               GlStateManager.color(this.field_177184_f * var15, this.field_177185_g * var16, this.field_177192_h * var17, this.field_177187_e);
               var12.render(var1, var2, var3, var5, var6, var7, var8);
               if (!Config.isCustomItems() || !CustomItems.bindCustomArmorTexture(var10, var13 ? 2 : 1, "overlay")) {
                  this.field_177190_a.bindTexture(this.getArmorResource(var1, var10, var13 ? 2 : 1, "overlay"));
               }
            }

            GlStateManager.color(this.field_177184_f, this.field_177185_g, this.field_177192_h, this.field_177187_e);
            var12.render(var1, var2, var3, var5, var6, var7, var8);
            if (!this.field_177193_i && var10.isItemEnchanted() && (!Config.isCustomItems() || !CustomItems.renderCustomArmorEffect(var1, var10, var12, var2, var3, var4, var5, var6, var7, var8))) {
               this.func_177183_a(var1, var12, var2, var3, var4, var5, var6, var7, var8);
            }

            return;
         }

         switch(var11.getArmorMaterial()) {
         case LEATHER:
            var14 = var11.getColor(var10);
            var15 = (float)(var14 >> 16 & 255) / 255.0F;
            var16 = (float)(var14 >> 8 & 255) / 255.0F;
            var17 = (float)(var14 & 255) / 255.0F;
            GlStateManager.color(this.field_177184_f * var15, this.field_177185_g * var16, this.field_177192_h * var17, this.field_177187_e);
            var12.render(var1, var2, var3, var5, var6, var7, var8);
            if (!Config.isCustomItems() || !CustomItems.bindCustomArmorTexture(var10, var13 ? 2 : 1, "overlay")) {
               this.field_177190_a.bindTexture(this.func_177178_a(var11, var13, "overlay"));
            }
         case CHAIN:
         case IRON:
         case GOLD:
         case DIAMOND:
            GlStateManager.color(this.field_177184_f, this.field_177185_g, this.field_177192_h, this.field_177187_e);
            var12.render(var1, var2, var3, var5, var6, var7, var8);
         }

         if (!this.field_177193_i && var10.isItemEnchanted() && (!Config.isCustomItems() || !CustomItems.renderCustomArmorEffect(var1, var10, var12, var2, var3, var4, var5, var6, var7, var8))) {
            this.func_177183_a(var1, var12, var2, var3, var4, var5, var6, var7, var8);
         }
      }

   }

   public ResourceLocation getArmorResource(Entity var1, ItemStack var2, int var3, String var4) {
      ItemArmor var5 = (ItemArmor)var2.getItem();
      String var6 = ((ItemArmor)var2.getItem()).getArmorMaterial().func_179242_c();
      String var7 = "minecraft";
      int var8 = var6.indexOf(58);
      if (var8 != -1) {
         var7 = var6.substring(0, var8);
         var6 = var6.substring(var8 + 1);
      }

      String var9 = String.format("%s:textures/models/armor/%s_layer_%d%s.png", var7, var6, var3 == 2 ? 2 : 1, var4 == null ? "" : String.format("_%s", var4));
      var9 = Reflector.callString(Reflector.ForgeHooksClient_getArmorTexture, var1, var2, var9, var3, var4);
      ResourceLocation var10 = (ResourceLocation)field_177191_j.get(var9);
      if (var10 == null) {
         var10 = new ResourceLocation(var9);
         field_177191_j.put(var9, var10);
      }

      return var10;
   }

   private void func_177183_a(EntityLivingBase var1, ModelBase var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9) {
      if (!Config.isCustomItems() || CustomItems.isUseGlint()) {
         if (Config.isShaders()) {
            if (Shaders.isShadowPass) {
               return;
            }

            ShadersRender.layerArmorBaseDrawEnchantedGlintBegin();
         }

         float var10 = (float)var1.ticksExisted + var5;
         this.field_177190_a.bindTexture(field_177188_b);
         GlStateManager.enableBlend();
         GlStateManager.depthFunc(514);
         GlStateManager.depthMask(false);
         float var11 = 0.5F;
         GlStateManager.color(var11, var11, var11, 1.0F);

         for(int var12 = 0; var12 < 2; ++var12) {
            GlStateManager.disableLighting();
            GlStateManager.blendFunc(768, 1);
            float var13 = 0.76F;
            GlStateManager.color(0.5F * var13, 0.25F * var13, 0.8F * var13, 1.0F);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            float var14 = 0.33333334F;
            GlStateManager.scale(var14, var14, var14);
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
         if (Config.isShaders()) {
            ShadersRender.layerArmorBaseDrawEnchantedGlintEnd();
         }
      }

   }

   protected abstract void func_177177_a();

   static final class SwitchArmorMaterial {
      private static final String __OBFID = "CL_00002427";
      static final int[] field_178747_a = new int[ItemArmor.ArmorMaterial.values().length];

      static {
         try {
            field_178747_a[ItemArmor.ArmorMaterial.LEATHER.ordinal()] = 1;
         } catch (NoSuchFieldError var5) {
         }

         try {
            field_178747_a[ItemArmor.ArmorMaterial.CHAIN.ordinal()] = 2;
         } catch (NoSuchFieldError var4) {
         }

         try {
            field_178747_a[ItemArmor.ArmorMaterial.IRON.ordinal()] = 3;
         } catch (NoSuchFieldError var3) {
         }

         try {
            field_178747_a[ItemArmor.ArmorMaterial.GOLD.ordinal()] = 4;
         } catch (NoSuchFieldError var2) {
         }

         try {
            field_178747_a[ItemArmor.ArmorMaterial.DIAMOND.ordinal()] = 5;
         } catch (NoSuchFieldError var1) {
         }

      }
   }
}
