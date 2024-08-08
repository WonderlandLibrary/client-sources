package com.example.editme.util.render;

import com.example.editme.gui.font.CFontRenderer;
import com.example.editme.util.client.Friends;
import java.awt.Color;
import java.awt.Font;
import java.util.Iterator;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.opengl.GL11;

public class NametagRenderer {
   public static final CFontRenderer fontRenderer = new CFontRenderer(new Font("Arial", 1, 16), true, true);

   private static void drawBorderedRect(double var0, double var2, double var4, double var6, double var8, int var10, int var11) {
      enableGL2D();
      fakeGuiRect(var0 + var8, var2 + var8, var4 - var8, var6 - var8, var10);
      fakeGuiRect(var0 + var8, var2, var4 - var8, var2 + var8, var11);
      fakeGuiRect(var0, var2, var0 + var8, var6, var11);
      fakeGuiRect(var4 - var8, var2, var4, var6, var11);
      fakeGuiRect(var0 + var8, var6 - var8, var4 - var8, var6, var11);
      disableGL2D();
   }

   private static void renderItem(EntityPlayer var0, ItemStack var1, int var2, int var3) {
      GL11.glPushMatrix();
      GL11.glDepthMask(true);
      GlStateManager.func_179086_m(256);
      GlStateManager.func_179097_i();
      GlStateManager.func_179126_j();
      RenderHelper.func_74519_b();
      Minecraft.func_71410_x().func_175599_af().field_77023_b = -100.0F;
      GlStateManager.func_179152_a(1.0F, 1.0F, 0.01F);
      Minecraft.func_71410_x().func_175599_af().func_180450_b(var1, var2, var3 / 2 - 12);
      renderItemOverlayIntoGUI(var1, var2, var3 / 2 - 12, (String)null);
      Minecraft.func_71410_x().func_175599_af().field_77023_b = 0.0F;
      GlStateManager.func_179152_a(1.0F, 1.0F, 1.0F);
      RenderHelper.func_74518_a();
      GlStateManager.func_179141_d();
      GlStateManager.func_179084_k();
      GlStateManager.func_179140_f();
      GlStateManager.func_179139_a(0.5D, 0.5D, 0.5D);
      GlStateManager.func_179097_i();
      renderEnchantText(var0, var1, var2, var3 - 18);
      GlStateManager.func_179126_j();
      GlStateManager.func_179152_a(2.0F, 2.0F, 2.0F);
      GL11.glPopMatrix();
   }

   private static void renderItemOverlayIntoGUI(ItemStack var0, int var1, int var2, @Nullable String var3) {
      if (!var0.func_190926_b()) {
         if (var0.func_190916_E() != 1 || var3 != null) {
            String var4 = var3 == null ? String.valueOf(var0.func_190916_E()) : var3;
            GlStateManager.func_179140_f();
            GlStateManager.func_179097_i();
            GlStateManager.func_179084_k();
            fontRenderer.drawStringWithShadow(var4, (double)((float)(var1 + 19 - 2 - fontRenderer.getStringWidth(var4))), (double)((float)(var2 + 6 + 3)), 16777215);
            GlStateManager.func_179145_e();
            GlStateManager.func_179126_j();
            GlStateManager.func_179147_l();
         }

         if (var0.func_77973_b().showDurabilityBar(var0)) {
            GlStateManager.func_179140_f();
            GlStateManager.func_179097_i();
            GlStateManager.func_179090_x();
            GlStateManager.func_179118_c();
            GlStateManager.func_179084_k();
            Tessellator var11 = Tessellator.func_178181_a();
            BufferBuilder var5 = var11.func_178180_c();
            double var6 = var0.func_77973_b().getDurabilityForDisplay(var0);
            int var8 = var0.func_77973_b().getRGBDurabilityForDisplay(var0);
            int var9 = Math.round(13.0F - (float)var6 * 13.0F);
            draw(var5, var1 + 2, var2 + 13, 13, 2, 0, 0, 0, 255);
            draw(var5, var1 + 2, var2 + 13, var9, 1, var8 >> 16 & 255, var8 >> 8 & 255, var8 & 255, 255);
            GlStateManager.func_179147_l();
            GlStateManager.func_179141_d();
            GlStateManager.func_179098_w();
            GlStateManager.func_179145_e();
            GlStateManager.func_179126_j();
         }

         EntityPlayerSP var12 = Minecraft.func_71410_x().field_71439_g;
         float var13 = var12 == null ? 0.0F : var12.func_184811_cZ().func_185143_a(var0.func_77973_b(), Minecraft.func_71410_x().func_184121_ak());
         if (var13 > 0.0F) {
            GlStateManager.func_179140_f();
            GlStateManager.func_179097_i();
            GlStateManager.func_179090_x();
            Tessellator var14 = Tessellator.func_178181_a();
            BufferBuilder var7 = var14.func_178180_c();
            draw(var7, var1, var2 + MathHelper.func_76141_d(16.0F * (1.0F - var13)), 16, MathHelper.func_76123_f(16.0F * var13), 255, 255, 255, 127);
            GlStateManager.func_179098_w();
            GlStateManager.func_179145_e();
            GlStateManager.func_179126_j();
         }
      }

   }

   private static void draw(BufferBuilder var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      var0.func_181668_a(7, DefaultVertexFormats.field_181706_f);
      var0.func_181662_b((double)var1, (double)var2, 0.0D).func_181669_b(var5, var6, var7, var8).func_181675_d();
      var0.func_181662_b((double)var1, (double)(var2 + var4), 0.0D).func_181669_b(var5, var6, var7, var8).func_181675_d();
      var0.func_181662_b((double)(var1 + var3), (double)(var2 + var4), 0.0D).func_181669_b(var5, var6, var7, var8).func_181675_d();
      var0.func_181662_b((double)(var1 + var3), (double)var2, 0.0D).func_181669_b(var5, var6, var7, var8).func_181675_d();
      Tessellator.func_178181_a().func_78381_a();
   }

   public static void drawGhostNameplate(EntityPlayer var0, double var1, double var3, double var5, NonNullList var7) {
      GlStateManager.func_179094_E();
      GL11.glTranslated((double)((float)var1), (double)((float)var3) + 2.5D, (double)((float)var5));
      GL11.glNormal3f(0.0F, 1.0F, 0.0F);
      GL11.glRotatef(-Minecraft.func_71410_x().func_175598_ae().field_78735_i, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(Minecraft.func_71410_x().func_175598_ae().field_78732_j, 1.0F, 0.0F, 0.0F);
      String var8 = String.valueOf((new StringBuilder()).append(var0.func_70005_c_()).append("§a ").append(MathHelper.func_76123_f(var0.func_110143_aJ() + var0.func_110139_bj())));
      var8 = var8.replace(".0", "");
      float var9 = Minecraft.func_71410_x().field_71439_g.func_70032_d(var0);
      float var10 = 0.016666668F * getNametagSize(var0);
      GL11.glScalef(-var10, -var10, var10);
      GlStateManager.func_179140_f();
      GlStateManager.func_179132_a(false);
      GlStateManager.func_179097_i();
      GlStateManager.func_179147_l();
      GlStateManager.func_187428_a(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
      int var11 = fontRenderer.getStringWidth(var8) / 2;
      GlStateManager.func_179090_x();
      drawBorderedRect((double)(-var11 - 2), 10.0D, (double)(var11 + 1), 20.0D, 0.0D, Friends.isFriend(var8) ? (new Color(0, 130, 0)).getRGB() : 8617341, -1);
      fontRenderer.drawString(var8, (float)(-var11), 11.0F, -1);
      int var12 = 0;
      Iterator var13 = var7.iterator();

      ItemStack var14;
      while(var13.hasNext()) {
         var14 = (ItemStack)var13.next();
         if (var14 != null) {
            var12 -= 8;
         }
      }

      if (var0.func_184614_ca() != null) {
         var12 -= 8;
         ItemStack var17 = var0.func_184614_ca().func_77946_l();
         renderItem(var0, (ItemStack)var17, var12, -10);
         var12 += 16;
      }

      for(int var18 = 3; var18 >= 0; --var18) {
         ItemStack var15 = (ItemStack)var0.field_71071_by.field_70460_b.get(var18);
         if (var15 != null) {
            ItemStack var16 = var15.func_77946_l();
            renderItem(var0, var16, var12, -10);
            var12 += 16;
         }
      }

      if (var0.func_184592_cb() != null) {
         var12 += 0;
         var14 = var0.func_184592_cb().func_77946_l();
         renderItem(var0, var14, var12, -10);
         var12 += 8;
      }

      GlStateManager.func_179098_w();
      GlStateManager.func_179132_a(true);
      GlStateManager.func_179145_e();
      GlStateManager.func_179084_k();
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.func_179121_F();
   }

   public static void drawNameplate(EntityPlayer var0, float var1, float var2, float var3) {
      GlStateManager.func_179094_E();
      GL11.glTranslated((double)var1, (double)var2 + 2.5D, (double)var3);
      GL11.glNormal3f(0.0F, 1.0F, 0.0F);
      GL11.glRotatef(-Minecraft.func_71410_x().func_175598_ae().field_78735_i, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(Minecraft.func_71410_x().func_175598_ae().field_78732_j, 1.0F, 0.0F, 0.0F);
      String var4 = String.valueOf((new StringBuilder()).append(var0.func_70005_c_()).append("§a ").append(MathHelper.func_76123_f(var0.func_110143_aJ() + var0.func_110139_bj())));
      var4 = var4.replace(".0", "");
      float var5 = 0.016666668F * getNametagSize(var0);
      GL11.glScalef(-var5, -var5, var5);
      GlStateManager.func_179140_f();
      GlStateManager.func_179132_a(false);
      GlStateManager.func_179097_i();
      GlStateManager.func_179147_l();
      GlStateManager.func_187428_a(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
      int var6 = fontRenderer.getStringWidth(var4) / 2;
      GlStateManager.func_179090_x();
      fontRenderer.drawString(var4, (float)(-var6), 10.0F, -1);
      int var7 = 0;
      Iterator var8 = var0.field_71071_by.field_70460_b.iterator();

      ItemStack var9;
      while(var8.hasNext()) {
         var9 = (ItemStack)var8.next();
         if (var9 != null) {
            var7 -= 8;
         }
      }

      if (var0.func_184614_ca() != null) {
         var7 -= 7;
         ItemStack var12 = var0.func_184614_ca().func_77946_l();
         renderItem(var0, (ItemStack)var12, var7, -8);
         var7 += 14;
      }

      for(int var13 = 3; var13 >= 0; --var13) {
         ItemStack var10 = (ItemStack)var0.field_71071_by.field_70460_b.get(var13);
         if (var10 != null) {
            ItemStack var11 = var10.func_77946_l();
            renderItem(var0, var11, var7, -8);
            var7 += 14;
         }
      }

      if (var0.func_184592_cb() != null) {
         var7 += 0;
         var9 = var0.func_184592_cb().func_77946_l();
         renderItem(var0, var9, var7, -8);
         var7 += 7;
      }

      GlStateManager.func_179098_w();
      GlStateManager.func_179132_a(true);
      GlStateManager.func_179145_e();
      GlStateManager.func_179084_k();
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.func_179121_F();
   }

   private static float getNametagSize(EntityLivingBase var0) {
      ScaledResolution var1 = new ScaledResolution(Minecraft.func_71410_x());
      double var2 = (double)var1.func_78325_e() / Math.pow((double)var1.func_78325_e(), 2.0D);
      return (float)var2 + Minecraft.func_71410_x().field_71439_g.func_70032_d(var0) / 7.0F;
   }

   private static void enableGL2D() {
      GL11.glDisable(2929);
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glDepthMask(true);
      GL11.glEnable(2848);
      GL11.glHint(3154, 4354);
      GL11.glHint(3155, 4354);
   }

   private static void disableGL2D() {
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
      GL11.glHint(3154, 4352);
      GL11.glHint(3155, 4352);
   }

   private static void fakeGuiRect(double var0, double var2, double var4, double var6, int var8) {
      double var9;
      if (var0 < var4) {
         var9 = var0;
         var0 = var4;
         var4 = var9;
      }

      if (var2 < var6) {
         var9 = var2;
         var2 = var6;
         var6 = var9;
      }

      float var15 = (float)(var8 >> 24 & 255) / 255.0F;
      float var10 = (float)(var8 >> 16 & 255) / 255.0F;
      float var11 = (float)(var8 >> 8 & 255) / 255.0F;
      float var12 = (float)(var8 & 255) / 255.0F;
      Tessellator var13 = Tessellator.func_178181_a();
      BufferBuilder var14 = var13.func_178180_c();
      GlStateManager.func_179147_l();
      GlStateManager.func_179090_x();
      GlStateManager.func_187428_a(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
      GlStateManager.func_179131_c(var10, var11, var12, var15);
      var14.func_181668_a(7, DefaultVertexFormats.field_181705_e);
      var14.func_181662_b(var0, var6, 0.0D).func_181675_d();
      var14.func_181662_b(var4, var6, 0.0D).func_181675_d();
      var14.func_181662_b(var4, var2, 0.0D).func_181675_d();
      var14.func_181662_b(var0, var2, 0.0D).func_181675_d();
      var13.func_78381_a();
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
   }

   private static void renderEnchantText(EntityPlayer var0, ItemStack var1, int var2, int var3) {
      int var4 = var3 - 24;
      int var5 = var4 - -5;
      if (var1.func_77973_b() instanceof ItemArmor || var1.func_77973_b() instanceof ItemSword || var1.func_77973_b() instanceof ItemTool) {
         fontRenderer.drawStringWithShadow(String.valueOf((new StringBuilder()).append(var1.func_77958_k() - var1.func_77952_i()).append("§4")), (double)(var2 * 2 + 8), (double)(var3 + 26), -1);
      }

      NBTTagList var6 = var1.func_77986_q();

      for(int var7 = 0; var7 < var6.func_74745_c(); ++var7) {
         short var8 = var6.func_150305_b(var7).func_74765_d("id");
         short var9 = var6.func_150305_b(var7).func_74765_d("lvl");
         Enchantment var10 = Enchantment.func_185262_c(var8);
         if (var10 != null) {
            String var11 = var10.func_190936_d() ? String.valueOf((new StringBuilder()).append(TextFormatting.RED).append(var10.func_77316_c(var9).substring(11).substring(0, 1).toLowerCase())) : var10.func_77316_c(var9).substring(0, 1).toLowerCase();
            var11 = String.valueOf((new StringBuilder()).append(var11).append(var9));
            GL11.glPushMatrix();
            GL11.glScalef(0.9F, 0.9F, 0.0F);
            fontRenderer.drawStringWithShadow(var11, (double)(var2 * 2 + 13), (double)var5, -1);
            GL11.glScalef(1.0F, 1.0F, 1.0F);
            GL11.glPopMatrix();
            var4 += 8;
            var5 -= 10;
         }
      }

   }
}
