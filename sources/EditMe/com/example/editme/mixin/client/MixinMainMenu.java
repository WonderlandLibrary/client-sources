package com.example.editme.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.util.glu.Project;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(
   value = {GuiMainMenu.class},
   priority = Integer.MAX_VALUE
)
public class MixinMainMenu {
   @Overwrite
   private void func_73970_b(int var1, int var2, float var3) {
      Tessellator var4 = Tessellator.func_178181_a();
      BufferBuilder var5 = var4.func_178180_c();
      GlStateManager.func_179128_n(5889);
      GlStateManager.func_179094_E();
      GlStateManager.func_179096_D();
      Project.gluPerspective(120.0F, 1.0F, 0.05F, 10.0F);
      GlStateManager.func_179128_n(5888);
      GlStateManager.func_179094_E();
      GlStateManager.func_179096_D();
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.func_179114_b(180.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.func_179114_b(90.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.func_179147_l();
      GlStateManager.func_179118_c();
      GlStateManager.func_179129_p();
      GlStateManager.func_179132_a(false);
      GlStateManager.func_187428_a(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
      GlStateManager.func_179094_E();
      GlStateManager.func_179094_E();
      Minecraft.func_71410_x().func_110434_K().func_110577_a(new ResourceLocation("editme/panorama.png"));
      var5.func_181668_a(7, DefaultVertexFormats.field_181709_i);
      short var6 = 255;
      var5.func_181662_b(-1.0D, -1.0D, 1.0D).func_187315_a(0.0D, 0.0D).func_181669_b(255, 255, 255, var6).func_181675_d();
      var5.func_181662_b(1.0D, -1.0D, 1.0D).func_187315_a(1.0D, 0.0D).func_181669_b(255, 255, 255, var6).func_181675_d();
      var5.func_181662_b(1.0D, 1.0D, 1.0D).func_187315_a(1.0D, 1.0D).func_181669_b(255, 255, 255, var6).func_181675_d();
      var5.func_181662_b(-1.0D, 1.0D, 1.0D).func_187315_a(0.0D, 1.0D).func_181669_b(255, 255, 255, var6).func_181675_d();
      var4.func_78381_a();
      GlStateManager.func_179121_F();
      GlStateManager.func_179121_F();
      GlStateManager.func_179135_a(true, true, true, false);
      var5.func_178969_c(0.0D, 0.0D, 0.0D);
      GlStateManager.func_179135_a(true, true, true, true);
      GlStateManager.func_179128_n(5889);
      GlStateManager.func_179121_F();
      GlStateManager.func_179128_n(5888);
      GlStateManager.func_179121_F();
      GlStateManager.func_179132_a(true);
      GlStateManager.func_179089_o();
      GlStateManager.func_179126_j();
   }

   @Overwrite
   private void func_73968_a() {
   }
}
