package com.example.editme.modules.render;

import com.example.editme.commands.Command;
import com.example.editme.events.RenderEvent;
import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.client.EntityUtil;
import com.example.editme.util.client.Friends;
import com.example.editme.util.color.ColourUtils;
import com.example.editme.util.color.HueCycler;
import com.example.editme.util.setting.SettingsManager;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

@Module.Info(
   name = "Tracers",
   description = "Draws lines to other living entities",
   category = Module.Category.RENDER
)
public class Tracers extends Module {
   private Setting opacity = this.register(SettingsManager.floatBuilder("Opacity").withRange(0.0F, 1.0F).withValue((Number)1.0F));
   private Setting players = this.register(SettingsManager.b("Players", true));
   private Setting mobs = this.register(SettingsManager.b("Mobs", false));
   private Setting exploitRange = this.register(SettingsManager.d("Exploit Min Range", 200.0D));
   private Setting friends = this.register(SettingsManager.b("Friends", true));
   HueCycler cycler = new HueCycler(3600);
   private Setting range = this.register(SettingsManager.d("Range", 200.0D));
   private Setting animals = this.register(SettingsManager.b("Animals", false));
   private Setting exploit = this.register(SettingsManager.b("Tracer Exploit", false));
   private Setting ghasts = this.register(SettingsManager.b("Ghasts", false));
   public ArrayList exploitedPlayers = new ArrayList();

   private void drawRainbowToEntity(Entity var1, float var2) {
      Vec3d var3 = (new Vec3d(0.0D, 0.0D, 1.0D)).func_178789_a(-((float)Math.toRadians((double)Minecraft.func_71410_x().field_71439_g.field_70125_A))).func_178785_b(-((float)Math.toRadians((double)Minecraft.func_71410_x().field_71439_g.field_70177_z)));
      double[] var4 = interpolate(var1);
      double var5 = var4[0];
      double var7 = var4[1];
      double var9 = var4[2];
      double var11 = var3.field_72450_a;
      double var13 = var3.field_72448_b + (double)mc.field_71439_g.func_70047_e();
      double var15 = var3.field_72449_c;
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(3042);
      GL11.glLineWidth(1.5F);
      GL11.glDisable(3553);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      this.cycler.reset();
      this.cycler.setNext(var2);
      GlStateManager.func_179140_f();
      GL11.glLoadIdentity();
      mc.field_71460_t.func_78467_g(mc.func_184121_ak());
      GL11.glBegin(1);
      GL11.glVertex3d(var5, var7, var9);
      GL11.glVertex3d(var11, var13, var15);
      this.cycler.setNext(var2);
      GL11.glVertex3d(var11, var13, var15);
      GL11.glVertex3d(var11, var13, var15);
      GL11.glEnd();
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glColor3d(1.0D, 1.0D, 1.0D);
      GlStateManager.func_179145_e();
   }

   public static double interpolate(double var0, double var2) {
      return var2 + (var0 - var2) * (double)mc.func_184121_ak();
   }

   private boolean lambda$onWorldRender$1(Entity var1) {
      return var1 instanceof EntityPlayer ? (Boolean)this.players.getValue() && mc.field_71439_g != var1 : (EntityUtil.isPassive(var1) ? (Boolean)this.animals.getValue() : (Boolean)this.mobs.getValue());
   }

   private static boolean lambda$onWorldRender$0(Entity var0) {
      return !EntityUtil.isFakeLocalPlayer(var0);
   }

   public void onWorldRender(RenderEvent var1) {
      GlStateManager.func_179094_E();
      Minecraft.func_71410_x().field_71441_e.field_72996_f.stream().filter(EntityUtil::isLiving).filter(Tracers::lambda$onWorldRender$0).filter(this::lambda$onWorldRender$1).forEach(this::lambda$onWorldRender$2);
      Minecraft.func_71410_x().field_71441_e.field_72996_f.stream().filter(EntityUtil::isLiving).filter(Tracers::lambda$onWorldRender$3).filter(this::lambda$onWorldRender$4).forEach(this::lambda$onWorldRender$5);
      GlStateManager.func_179121_F();
   }

   public static double[] interpolate(Entity var0) {
      double var1 = interpolate(var0.field_70165_t, var0.field_70142_S) - mc.func_175598_ae().field_78725_b;
      double var3 = interpolate(var0.field_70163_u, var0.field_70137_T) - mc.func_175598_ae().field_78726_c;
      double var5 = interpolate(var0.field_70161_v, var0.field_70136_U) - mc.func_175598_ae().field_78723_d;
      return new double[]{var1, var3, var5};
   }

   public static void drawLine(double var0, double var2, double var4, double var6, float var8, float var9, float var10, float var11) {
      Vec3d var12 = (new Vec3d(0.0D, 0.0D, 1.0D)).func_178789_a(-((float)Math.toRadians((double)Minecraft.func_71410_x().field_71439_g.field_70125_A))).func_178785_b(-((float)Math.toRadians((double)Minecraft.func_71410_x().field_71439_g.field_70177_z)));
      drawLineFromPosToPos(var12.field_72450_a, var12.field_72448_b + (double)mc.field_71439_g.func_70047_e(), var12.field_72449_c, var0, var2, var4, var6, var8, var9, var10, var11);
   }

   public static void drawLineFromPosToPos(double var0, double var2, double var4, double var6, double var8, double var10, double var12, float var14, float var15, float var16, float var17) {
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(3042);
      GL11.glLineWidth(1.5F);
      GL11.glDisable(3553);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      GL11.glColor4f(var14, var15, var16, var17);
      GlStateManager.func_179140_f();
      GL11.glLoadIdentity();
      mc.field_71460_t.func_78467_g(mc.func_184121_ak());
      GL11.glBegin(1);
      GL11.glVertex3d(var0, var2, var4);
      GL11.glVertex3d(var6, var8, var10);
      GL11.glVertex3d(var6, var8, var10);
      GL11.glVertex3d(var6, var8 + var12, var10);
      GL11.glEnd();
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glColor3d(1.0D, 1.0D, 1.0D);
      GlStateManager.func_179145_e();
   }

   private void entityExploit(Entity var1) {
      if (!this.exploitedPlayers.contains(var1)) {
         this.exploitedPlayers.add(var1);
         int var2 = (int)var1.field_70165_t;
         int var3 = (int)var1.field_70163_u;
         int var4 = (int)var1.field_70161_v;
         Command.sendRawChatMessage(String.valueOf((new StringBuilder()).append(ChatFormatting.RED.toString()).append("Player exploit found ").append(var1.func_70005_c_()).append(" at: ").append(var2).append(" ").append(var3).append(" ").append(var4)));
      }
   }

   private boolean lambda$onWorldRender$4(Entity var1) {
      return var1 instanceof EntityGhast && (Boolean)this.ghasts.getValue();
   }

   public static void drawLineToEntity(Entity var0, float var1, float var2, float var3, float var4) {
      double[] var5 = interpolate(var0);
      drawLine(var5[0], var5[1], var5[2], (double)var0.field_70131_O, var1, var2, var3, var4);
   }

   private void lambda$onWorldRender$5(Entity var1) {
      int var2 = this.getColour(var1);
      if (var2 == Integer.MIN_VALUE) {
         if (!(Boolean)this.friends.getValue()) {
            return;
         }

         var2 = this.cycler.current();
      }

      float var3 = (float)(var2 >>> 16 & 255) / 255.0F;
      float var4 = (float)(var2 >>> 8 & 255) / 255.0F;
      float var5 = (float)(var2 & 255) / 255.0F;
      drawLineToEntity(var1, var3, var4, var5, (Float)this.opacity.getValue());
   }

   private int getColour(Entity var1) {
      if (var1 instanceof EntityPlayer) {
         return Friends.isFriend(var1.func_70005_c_()) ? Integer.MIN_VALUE : ColourUtils.Colors.WHITE;
      } else {
         return EntityUtil.isPassive(var1) ? ColourUtils.Colors.GREEN : ColourUtils.Colors.RED;
      }
   }

   private static boolean lambda$onWorldRender$3(Entity var0) {
      return !EntityUtil.isFakeLocalPlayer(var0);
   }

   private void lambda$onWorldRender$2(Entity var1) {
      if ((double)mc.field_71439_g.func_70032_d(var1) < (Double)this.range.getValue()) {
         int var2 = this.getColour(var1);
         if (var2 == Integer.MIN_VALUE) {
            if (!(Boolean)this.friends.getValue()) {
               return;
            }

            var2 = this.cycler.current();
         }

         float var3 = (float)(var2 >>> 16 & 255) / 255.0F;
         float var4 = (float)(var2 >>> 8 & 255) / 255.0F;
         float var5 = (float)(var2 & 255) / 255.0F;
         drawLineToEntity(var1, var3, var4, var5, (Float)this.opacity.getValue());
      }

      if ((double)mc.field_71439_g.func_70032_d(var1) > (Double)this.exploitRange.getValue()) {
         this.entityExploit(var1);
      }

   }

   public void onUpdate() {
      this.cycler.next();
   }
}
