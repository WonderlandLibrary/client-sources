package com.example.editme.mixin.client;

import com.example.editme.util.module.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemShulkerBox;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(
   value = {GuiScreen.class},
   priority = Integer.MAX_VALUE
)
public class MixinGuiScreen {
   RenderItem itemRender = Minecraft.func_71410_x().func_175599_af();
   FontRenderer fontRenderer;

   public MixinGuiScreen() {
      this.fontRenderer = Minecraft.func_71410_x().field_71466_p;
   }

   @Inject(
      method = {"renderToolTip"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void renderToolTip(ItemStack var1, int var2, int var3, CallbackInfo var4) {
      if (ModuleManager.isModuleEnabled("ShulkerPreview") && var1.func_77973_b() instanceof ItemShulkerBox) {
         NBTTagCompound var5 = var1.func_77978_p();
         if (var5 != null && var5.func_150297_b("BlockEntityTag", 10)) {
            NBTTagCompound var6 = var5.func_74775_l("BlockEntityTag");
            if (var6.func_150297_b("Items", 9)) {
               var4.cancel();
               NonNullList var7 = NonNullList.func_191197_a(27, ItemStack.field_190927_a);
               ItemStackHelper.func_191283_b(var6, var7);
               GlStateManager.func_179147_l();
               GlStateManager.func_179101_C();
               RenderHelper.func_74518_a();
               GlStateManager.func_179140_f();
               GlStateManager.func_179097_i();
               int var8 = Math.max(144, this.fontRenderer.func_78256_a(var1.func_82833_r()) + 3);
               int var9 = var2 + 12;
               int var10 = var3 - 12;
               byte var11 = 57;
               this.itemRender.field_77023_b = 300.0F;
               this.drawGradientRectP(var9 - 3, var10 - 4, var9 + var8 + 3, var10 - 3, -267386864, -267386864);
               this.drawGradientRectP(var9 - 3, var10 + var11 + 3, var9 + var8 + 3, var10 + var11 + 4, -267386864, -267386864);
               this.drawGradientRectP(var9 - 3, var10 - 3, var9 + var8 + 3, var10 + var11 + 3, -267386864, -267386864);
               this.drawGradientRectP(var9 - 4, var10 - 3, var9 - 3, var10 + var11 + 3, -267386864, -267386864);
               this.drawGradientRectP(var9 + var8 + 3, var10 - 3, var9 + var8 + 4, var10 + var11 + 3, -267386864, -267386864);
               this.drawGradientRectP(var9 - 3, var10 - 3 + 1, var9 - 3 + 1, var10 + var11 + 3 - 1, 1347420415, 1344798847);
               this.drawGradientRectP(var9 + var8 + 2, var10 - 3 + 1, var9 + var8 + 3, var10 + var11 + 3 - 1, 1347420415, 1344798847);
               this.drawGradientRectP(var9 - 3, var10 - 3, var9 + var8 + 3, var10 - 3 + 1, 1347420415, 1347420415);
               this.drawGradientRectP(var9 - 3, var10 + var11 + 2, var9 + var8 + 3, var10 + var11 + 3, 1344798847, 1344798847);
               this.fontRenderer.func_78276_b(var1.func_82833_r(), var2 + 12, var3 - 12, 16777215);
               GlStateManager.func_179147_l();
               GlStateManager.func_179141_d();
               GlStateManager.func_179098_w();
               GlStateManager.func_179145_e();
               GlStateManager.func_179126_j();
               RenderHelper.func_74520_c();

               for(int var12 = 0; var12 < var7.size(); ++var12) {
                  int var13 = var2 + var12 % 9 * 16 + 11;
                  int var14 = var3 + var12 / 9 * 16 - 11 + 8;
                  ItemStack var15 = (ItemStack)var7.get(var12);
                  this.itemRender.func_180450_b(var15, var13, var14);
                  this.itemRender.func_180453_a(this.fontRenderer, var15, var13, var14, (String)null);
               }

               RenderHelper.func_74518_a();
               this.itemRender.field_77023_b = 0.0F;
               GlStateManager.func_179145_e();
               GlStateManager.func_179126_j();
               RenderHelper.func_74519_b();
               GlStateManager.func_179091_B();
            }
         }
      }

   }

   private void drawGradientRectP(int var1, int var2, int var3, int var4, int var5, int var6) {
      float var7 = (float)(var5 >> 24 & 255) / 255.0F;
      float var8 = (float)(var5 >> 16 & 255) / 255.0F;
      float var9 = (float)(var5 >> 8 & 255) / 255.0F;
      float var10 = (float)(var5 & 255) / 255.0F;
      float var11 = (float)(var6 >> 24 & 255) / 255.0F;
      float var12 = (float)(var6 >> 16 & 255) / 255.0F;
      float var13 = (float)(var6 >> 8 & 255) / 255.0F;
      float var14 = (float)(var6 & 255) / 255.0F;
      GlStateManager.func_179090_x();
      GlStateManager.func_179147_l();
      GlStateManager.func_179118_c();
      GlStateManager.func_187428_a(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
      GlStateManager.func_179103_j(7425);
      Tessellator var15 = Tessellator.func_178181_a();
      BufferBuilder var16 = var15.func_178180_c();
      var16.func_181668_a(7, DefaultVertexFormats.field_181706_f);
      var16.func_181662_b((double)var3, (double)var2, 300.0D).func_181666_a(var8, var9, var10, var7).func_181675_d();
      var16.func_181662_b((double)var1, (double)var2, 300.0D).func_181666_a(var8, var9, var10, var7).func_181675_d();
      var16.func_181662_b((double)var1, (double)var4, 300.0D).func_181666_a(var12, var13, var14, var11).func_181675_d();
      var16.func_181662_b((double)var3, (double)var4, 300.0D).func_181666_a(var12, var13, var14, var11).func_181675_d();
      var15.func_78381_a();
      GlStateManager.func_179103_j(7424);
      GlStateManager.func_179084_k();
      GlStateManager.func_179141_d();
      GlStateManager.func_179098_w();
   }
}
