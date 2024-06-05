package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelArmorStand;
import net.minecraft.client.model.ModelArmorStandArmor;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.util.ResourceLocation;

public class ArmorStandRenderer extends RendererLivingEntity {
   private static final String __OBFID = "CL_00002447";
   public static final ResourceLocation field_177103_a = new ResourceLocation("textures/entity/armorstand/wood.png");

   protected ResourceLocation getEntityTexture(Entity var1) {
      return this.func_177102_a((EntityArmorStand)var1);
   }

   protected void rotateCorpse(EntityLivingBase var1, float var2, float var3, float var4) {
      this.func_177101_a((EntityArmorStand)var1, var2, var3, var4);
   }

   protected ResourceLocation func_177102_a(EntityArmorStand var1) {
      return field_177103_a;
   }

   public ModelBase getMainModel() {
      return this.func_177100_a();
   }

   protected boolean func_177099_b(EntityArmorStand var1) {
      return var1.getAlwaysRenderNameTag();
   }

   protected boolean canRenderName(EntityLivingBase var1) {
      return this.func_177099_b((EntityArmorStand)var1);
   }

   public ArmorStandRenderer(RenderManager var1) {
      super(var1, new ModelArmorStand(), 0.0F);
      LayerBipedArmor var2 = new LayerBipedArmor(this, this) {
         private static final String __OBFID = "CL_00002446";
         final ArmorStandRenderer this$0;

         protected void func_177177_a() {
            this.field_177189_c = new ModelArmorStandArmor(0.5F);
            this.field_177186_d = new ModelArmorStandArmor(1.0F);
         }

         {
            this.this$0 = var1;
         }
      };
      this.addLayer(var2);
      this.addLayer(new LayerHeldItem(this));
      this.addLayer(new LayerCustomHead(this.func_177100_a().bipedHead));
   }

   public ModelArmorStand func_177100_a() {
      return (ModelArmorStand)super.getMainModel();
   }

   protected boolean func_177070_b(Entity var1) {
      return this.func_177099_b((EntityArmorStand)var1);
   }

   protected void func_177101_a(EntityArmorStand var1, float var2, float var3, float var4) {
      GlStateManager.rotate(180.0F - var3, 0.0F, 1.0F, 0.0F);
   }
}
