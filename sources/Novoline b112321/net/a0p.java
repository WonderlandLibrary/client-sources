package net;

import java.awt.Color;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;
import net.D7;
import net.Ls;
import net.a6_;
import net.aZ8;
import net.arJ;
import net.asx;
import net.au7;
import net.ava;
import net.aww;
import net.gZ;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

class a0p {
   private final EntityLivingBase a;
   private double[] b;
   final arJ c;

   public a0p(arJ var1, EntityLivingBase var2) {
      this.c = var1;
      this.b = new double[]{0.0D, 0.0D, 0.0D};
      this.a = var2;
   }

   void c() {
      ava.h();
      GL11.glPushMatrix();
      ScaledResolution var2 = new ScaledResolution(Minecraft.getMinecraft());
      au7 var3 = gZ.g().k().a(this.a.getName());
      float var4 = (float)(this.b[0] / (double)var2.getScaleFactor());
      float var5 = (float)(this.b[1] / (double)var2.getScaleFactor());
      float var6 = (float)(this.b[2] / (double)var2.getScaleFactor());
      List var7 = (List)((arJ)this.c.b(arJ.class)).a().a();
      List var8 = (List)((arJ)this.c.b(arJ.class)).e().a();
      String var9 = var8.contains("Health")?(((arJ)this.c.b(arJ.class)).c().b("Value")?" " + (int)(this.a.getHealth() + this.a.getAbsorptionAmount()):""):"";
      String var10 = var7.contains("Distance")?" " + (int)Minecraft.getMinecraft().thePlayer.getDistanceToEntity(this.a) + "m":"";
      String var11 = this.a.getDisplayName().getFormattedText();
      GL11.glTranslatef(var4, var5, var6);
      float var12 = 1.0F;
      switch(Minecraft.getMinecraft().gameSettings.guiScale) {
      case 0:
         var12 = 0.5F;
      case 1:
         var12 = 2.0F;
      case 3:
         var12 = 0.6666667F;
      case 2:
      }

      if(this.b[2] >= 0.0D && this.b[2] < 1.0D) {
         ScaledResolution var13 = new ScaledResolution(arJ.d(this.c));
         double var14 = (double)var13.getScaleFactor() / Math.pow((double)var13.getScaleFactor(), 2.0D);
         GL11.glScaled(var14, var14, var14);
         GlStateManager.disableDepth();
         String var16 = (var3 == au7.FRIEND?EnumChatFormatting.AQUA + "[FRIEND] ":(((asx)this.c.b(asx.class)).y() && ((asx)this.c.b(asx.class)).h().a((Object)"Teams") && aww.a((ICommandSender)this.a)?"§a[TEAM]§c ":(var3 == au7.TARGET?"§c[TARGET]§r ":""))) + EnumChatFormatting.RESET + var11 + EnumChatFormatting.GRAY + var10;
         float var17 = Math.abs(-(this.a(var16) / 2.0F) - 3.0F - (this.a(var16) / 2.0F + 4.0F));
         float var18 = (float)((int)(this.a.getMaxHealth() + this.a.getAbsorptionAmount()));
         float var19 = 100.0F / var18;
         float var20 = (float)((int)((this.a.getHealth() + this.a.getAbsorptionAmount()) * var19));
         float var21 = var17 / 100.0F;
         int var22 = var8.contains("Health") && ((arJ)this.c.b(arJ.class)).c().b("Value")?5:0;
         float var23 = this.a(var16) / 2.0F;
         ScaledResolution var24 = new ScaledResolution(Minecraft.getMinecraft());
         float var25 = (float)var24.getScaledWidth() / 2.0F;
         float var26 = (float)var24.getScaledHeight() / 2.0F;
         float var27 = (float)D7.a.a(var16) / 2.0F * 0.5F;
         float var28 = 6.75F;
         float var29 = var25 / var12 + var27;
         float var30 = var25 / var12 - var27;
         float var31 = var26 / var12 - var28;
         float var32 = var26 / var12 + var28;
         if(var8.contains("Background")) {
            a6_.b(-var23 - 2.0F - (float)var22, -8.0F - this.a(), this.a(var16 + var9) / 2.0F + (((arJ)this.c.b(arJ.class)).c().b("Bar")?2.0F:this.a(var9) - 3.0F - (float)var22), (float)(var8.contains("Health")?(((arJ)this.c.b(arJ.class)).c().b("Bar")?6:5):5) - this.a(), (new Color(var3 == au7.TARGET?100:0, var3 != au7.FRIEND && (!((asx)this.c.b(asx.class)).y() || !((asx)this.c.b(asx.class)).h().a((Object)"Teams") || !aww.a((ICommandSender)this.a))?0:90, var3 == au7.FRIEND?120:(aww.a((ICommandSender)this.a)?15:0), ((Integer)((arJ)this.c.b(arJ.class)).f().a()).intValue())).getRGB());
         }

         if(var7.contains("Armor")) {
            this.a((EntityPlayer)this.a);
         }

         this.a(var16, -var23 - (float)var22, -this.a() - 5.0F, 16777215);
         this.a(var9, var23 - (float)var22, -this.a() - 5.0F, this.d());
         if(var8.contains("Health") && ((arJ)this.c.b(arJ.class)).c().b("Bar")) {
            Gui.a((double)(-var23 - 2.0F), (double)(5.0F - this.a()), (double)(-var23 - 5.0F + var20 * var21), (double)(6.0F - this.a()), this.d());
         }

         GlStateManager.enableDepth();
         GL11.glPopMatrix();
      } else {
         GlStateManager.popMatrix();
      }
   }

   private void a(String var1, float var2, float var3, int var4) {
      int var5 = ava.e();
      if(((String)arJ.a(this.c).a()).equalsIgnoreCase("Client")) {
         D7.a.b(var1, var2, var3, var4);
      }

      arJ.c(this.c).fontRendererObj.b(var1, var2, var3, var4);
   }

   private float a(String var1) {
      int var2 = ava.e();
      return ((String)arJ.a(this.c).a()).equalsIgnoreCase("Client")?(float)D7.a.a(var1):(float)arJ.b(this.c).fontRendererObj.d(var1);
   }

   private float a() {
      ava.h();
      float var2 = Minecraft.getMinecraft().thePlayer.getDistanceToEntity(this.a);
      return ((arJ)this.c.b(arJ.class)).c().b("Bar")?(float)Math.max((double)this.b() * (var2 >= 110.0F?0.058D:0.032D + (double)(4.0F / var2)), 1.0D):(float)Math.max((double)this.b() * (var2 >= 110.0F?0.046D:0.02D + (double)(4.0F / var2)), 1.0D);
   }

   private int d() {
      float var1 = this.a.getHealth();
      float var2 = this.a.getMaxHealth();
      float var3 = Math.max(0.0F, Math.min(var1, var2) / var2);
      return Color.HSBtoRGB(var3 / 3.0F, 1.0F, 1.0F) | -16777216;
   }

   private int b() {
      int var1 = (int)Math.abs(Minecraft.getMinecraft().thePlayer.posX - this.a.posX);
      int var2 = (int)Math.abs(Minecraft.getMinecraft().thePlayer.posY - this.a.posY);
      int var3 = (int)Math.abs(Minecraft.getMinecraft().thePlayer.posZ - this.a.posZ);
      return (int)Math.sqrt((double)(var1 * var1 + var2 * var2 + var3 * var3));
   }

   private double[] a(double var1, double var3, double var5) {
      FloatBuffer var8 = BufferUtils.createFloatBuffer(3);
      IntBuffer var9 = BufferUtils.createIntBuffer(16);
      ava.h();
      FloatBuffer var10 = BufferUtils.createFloatBuffer(16);
      FloatBuffer var11 = BufferUtils.createFloatBuffer(16);
      GL11.glGetFloat(2982, var10);
      GL11.glGetFloat(2983, var11);
      GL11.glGetInteger(2978, var9);
      boolean var12 = Ls.a((float)var1, (float)var3, (float)var5, var10, var11, var9, var8);
      return var12?new double[]{(double)var8.get(0), (double)((float)Display.getHeight() - var8.get(1)), (double)var8.get(2)}:null;
   }

   private void a(EntityPlayer var1) {
      ItemStack[] var3 = var1.bJ.armorInventory;
      int var5 = 0;
      ava.e();
      int var7 = var3.length;
      int var8 = 0;
      if(var8 < var7) {
         ItemStack var9 = var3[var8];
         if(var9 != null) {
            var5 -= 8;
         }

         ++var8;
      }

      if(var1.getCurrentEquippedItem() != null) {
         var5 = var5 - 8;
         ItemStack var6 = var1.getCurrentEquippedItem().copy();
         if(var6.h() && (var6.getItem() instanceof ItemTool || var6.getItem() instanceof ItemArmor)) {
            var6.stackSize = 1;
         }

         this.a(var6, var5, -25.0F - this.a() * 1.5F);
         var5 = var5 + 16;
      }

      var3 = var1.bJ.armorInventory;
      int var13 = 3;
      ItemStack var4 = var3[var13];
      this.a(var4, var5, -25.0F - this.a() * 1.5F);
      var5 = var5 + 16;
      --var13;
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
   }

   private void a(ItemStack var1, int var2, float var3) {
      GlStateManager.pushMatrix();
      GlStateManager.depthMask(true);
      GlStateManager.clear(256);
      RenderHelper.enableStandardItemLighting();
      Minecraft.getMinecraft().a().a = -150.0F;
      GlStateManager.disableDepth();
      GlStateManager.disableTexture2D();
      GlStateManager.enableBlend();
      GlStateManager.enableAlpha();
      GlStateManager.enableTexture2D();
      GlStateManager.enableLighting();
      GlStateManager.enableDepth();
      GlStateManager.scale(0.7D, 0.7D, 0.7D);
      Minecraft.getMinecraft().a().b(var1, (float)var2, var3);
      Minecraft.getMinecraft().a().a(aZ8.a, var1, var2, (int)var3);
      Minecraft.getMinecraft().a().a = 0.0F;
      RenderHelper.disableStandardItemLighting();
      GlStateManager.disableCull();
      GlStateManager.enableAlpha();
      GlStateManager.disableBlend();
      GlStateManager.disableLighting();
      float var4 = 0.5F;
      GlStateManager.scale(0.5F, 0.5F, 0.5F);
      GlStateManager.disableDepth();
      GlStateManager.enableDepth();
      GlStateManager.scale(2.0F, 2.0F, 2.0F);
      GlStateManager.popMatrix();
   }

   static EntityLivingBase a(a0p var0) {
      return var0.a;
   }

   static double[] a(a0p var0, double[] var1) {
      return var0.b = var1;
   }

   static double[] a(a0p var0, double var1, double var3, double var5) {
      return var0.a(var1, var3, var5);
   }
}
