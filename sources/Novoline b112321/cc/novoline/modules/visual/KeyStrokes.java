package cc.novoline.modules.visual;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.Render2DEvent;
import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.configurations.annotation.Property;
import cc.novoline.modules.configurations.property.object.ColorProperty;
import cc.novoline.modules.configurations.property.object.IntProperty;
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import cc.novoline.modules.visual.HUD;
import cc.novoline.utils.RenderUtils;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.EnumSet;
import net.Jz;
import net.za;
import net.minecraft.client.gui.Gui;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;

public class KeyStrokes extends AbstractModule {
   private double I = 1.0D;
   private double N = 0.0D;
   private final Dimension J = Toolkit.getDefaultToolkit().getScreenSize();
   @Property("ks-x")
   private final IntProperty F;
   @Property("ks-y")
   private final IntProperty D;
   @Property("color")
   private final ColorProperty E;
   private double K;
   private double x;
   private double G;
   private double C;
   private double y;
   private double M;
   private double H;
   private double B;
   private double A;
   private double z;
   private double O;
   private double L;

   public KeyStrokes(@NotNull ModuleManager var1) {
      super(var1, EnumModuleType.VISUALS, "KeyStrokes");
      this.F = (IntProperty)((IntProperty)PropertyFactory.createInt(Integer.valueOf(40)).minimum(Integer.valueOf(1))).maximum(Integer.valueOf((int)this.J.getWidth() / 2));
      this.D = (IntProperty)((IntProperty)PropertyFactory.createInt(Integer.valueOf(40)).minimum(Integer.valueOf(1))).maximum(Integer.valueOf((int)this.J.getHeight() / 2));
      this.E = PropertyFactory.createColor(Integer.valueOf(-1));
      this.K = 50.0D;
      this.x = 0.0D;
      this.G = 50.0D;
      this.C = 0.0D;
      this.y = 50.0D;
      this.M = 0.0D;
      this.H = 50.0D;
      this.B = 0.0D;
      this.A = 50.0D;
      this.z = 0.0D;
      this.O = 50.0D;
      this.L = 0.0D;
      Manager.put(new Setting("KS_COLOR", "Color", SettingType.COLOR_PICKER, this, this.E, (EnumSet)null));
   }

   @EventTarget
   public void a(Render2DEvent var1) {
      this.i();
      this.f();
      this.b();
      this.j();
      this.h();
      this.d();
   }

   private void i() {
      String var2 = Keyboard.getKeyName(this.mc.gameSettings.keyBindForward.getKeyCode()).toUpperCase();
      HUD.h();
      int var3 = ((Integer)this.F.get()).intValue() + 28;
      int var4 = ((Integer)this.D.get()).intValue();
      if(this.mc.gameSettings.keyBindForward.isPressed()) {
         this.K = 150.0D;
         this.x = 0.0D;
      }

      if(this.x < 30.0D) {
         this.x += 0.2D;
      }

      if(this.x > 10.0D) {
         this.K = Math.max(0.0D, this.K - 1.0D);
      }

      Gui.drawRect(var3, var4, var3 + 25, var4 + 25, (new Color(0, 0, 0, 100)).getRGB());
      za.b(false);
      Gui.drawRect(var3, var4, var3 + 25, var4 + 25, -16777216);
      za.a(true);
      RenderUtils.drawFilledCircle((float)var3 + 12.5F, (float)var4 + 12.5F, (float)this.x, (new Color(this.E.h(), this.E.f(), this.E.c(), (int)this.K)).getRGB());
      za.b();
      Jz.a.drawString(var2, (float)var3 + 12.0F - (float)(Jz.a.stringWidth(var2) / 2) - 0.5F, (float)var4 + 12.5F - (float)(Jz.a.getHeight() / 2), -1);
   }

   private void f() {
      HUD.h();
      String var2 = Keyboard.getKeyName(this.mc.gameSettings.keyBindLeft.getKeyCode()).toUpperCase();
      int var3 = ((Integer)this.F.get()).intValue();
      int var4 = ((Integer)this.D.get()).intValue() + 28;
      if(this.mc.gameSettings.keyBindLeft.isPressed()) {
         this.G = 150.0D;
         this.C = 0.0D;
      }

      if(this.C < 30.0D) {
         this.C += 0.2D;
      }

      if(this.C > 10.0D) {
         this.G = Math.max(0.0D, this.G - 1.0D);
      }

      Gui.drawRect(var3, var4, var3 + 25, var4 + 25, (new Color(0, 0, 0, 100)).getRGB());
      za.b(false);
      Gui.drawRect(var3, var4, var3 + 25, var4 + 25, -16777216);
      za.a(true);
      RenderUtils.drawFilledCircle((float)var3 + 12.5F, (float)var4 + 12.0F, (float)this.C, (new Color(this.E.h(), this.E.f(), this.E.c(), (int)this.G)).getRGB());
      za.b();
      Jz.a.drawString(var2, (float)var3 + 12.5F - (float)(Jz.a.stringWidth(var2) / 2) - 0.5F, (float)var4 + 12.5F - (float)(Jz.a.getHeight() / 2), -1);
   }

   private void b() {
      String var2 = Keyboard.getKeyName(this.mc.gameSettings.keyBindBack.getKeyCode()).toUpperCase();
      int var3 = ((Integer)this.F.get()).intValue() + 28;
      HUD.h();
      int var4 = ((Integer)this.D.get()).intValue() + 28;
      if(this.mc.gameSettings.keyBindBack.isPressed()) {
         this.y = 150.0D;
         this.M = 0.0D;
      }

      if(this.M < 30.0D) {
         this.M += 0.2D;
      }

      if(this.M > 10.0D) {
         this.y = Math.max(0.0D, this.y - 1.0D);
      }

      Gui.drawRect(var3, var4, var3 + 25, var4 + 25, (new Color(0, 0, 0, 100)).getRGB());
      za.b(false);
      Gui.drawRect(var3, var4, var3 + 25, var4 + 25, -16777216);
      za.a(true);
      RenderUtils.drawFilledCircle((float)var3 + 12.5F, (float)var4 + 12.5F, (float)this.M, (new Color(this.E.h(), this.E.f(), this.E.c(), (int)this.y)).getRGB());
      za.b();
      Jz.a.drawString(var2, (float)var3 + 12.5F - (float)(Jz.a.stringWidth(var2) / 2) - 0.5F, (float)var4 + 12.5F - (float)(Jz.a.getHeight() / 2), -1);
   }

   private void j() {
      HUD.h();
      String var2 = Keyboard.getKeyName(this.mc.gameSettings.keyBindRight.getKeyCode()).toUpperCase();
      int var3 = ((Integer)this.F.get()).intValue() + 56;
      int var4 = ((Integer)this.D.get()).intValue() + 28;
      if(this.mc.gameSettings.keyBindRight.isPressed()) {
         this.H = 150.0D;
         this.B = 0.0D;
      }

      if(this.B < 30.0D) {
         this.B += 0.2D;
      }

      if(this.B > 10.0D) {
         this.H = Math.max(0.0D, this.H - 1.0D);
      }

      Gui.drawRect(var3, var4, var3 + 25, var4 + 25, (new Color(0, 0, 0, 100)).getRGB());
      za.b(false);
      Gui.drawRect(var3, var4, var3 + 25, var4 + 25, -16777216);
      za.a(true);
      RenderUtils.drawFilledCircle((float)var3 + 12.5F, (float)var4 + 12.5F, (float)this.B, (new Color(this.E.h(), this.E.f(), this.E.c(), (int)this.H)).getRGB());
      za.b();
      Jz.a.drawString(var2, (float)var3 + 12.0F - (float)(Jz.a.stringWidth(var2) / 2) - 0.5F, (float)var4 + 12.5F - (float)(Jz.a.getHeight() / 2), -1);
   }

   private void h() {
      HUD.e();
      String var2 = "LMB";
      int var3 = ((Integer)this.F.get()).intValue();
      int var4 = ((Integer)this.D.get()).intValue() + 56;
      if(this.z < 30.0D) {
         this.z += 0.2D;
      }

      if(this.z > 10.0D) {
         this.A = Math.max(0.0D, this.A - 1.0D);
      }

      Gui.drawRect((double)var3, (double)var4, (double)var3 + 39.5D, (double)(var4 + 25), (new Color(0, 0, 0, 100)).getRGB());
      za.b(false);
      Gui.drawRect((double)var3, (double)var4, (double)var3 + 39.5D, (double)(var4 + 25), -16777216);
      za.a(true);
      RenderUtils.drawFilledCircle((float)var3 + 19.75F, (float)var4 + 12.5F, (float)this.z, (new Color(this.E.h(), this.E.f(), this.E.c(), (int)this.A)).getRGB());
      za.b();
      Jz.a.drawString(var2, (float)var3 + 19.75F - (float)(Jz.a.stringWidth(var2) / 2) - 0.5F, (float)var4 + 12.5F - (float)(Jz.a.getHeight() / 2), -1);
   }

   public void k() {
      this.A = 150.0D;
      this.z = 0.0D;
   }

   private void d() {
      String var2 = "RMB";
      HUD.h();
      float var3 = (float)((Integer)this.F.get()).intValue() + 41.5F;
      float var4 = (float)((Integer)this.D.get()).intValue() + 56.0F;
      if(this.L < 30.0D) {
         this.L += 0.2D;
      }

      if(this.L > 10.0D) {
         this.O = Math.max(0.0D, this.O - 1.0D);
      }

      Gui.drawRect((double)var3, (double)var4, (double)var3 + 39.5D, (double)(var4 + 25.0F), (new Color(0, 0, 0, 100)).getRGB());
      za.b(false);
      Gui.drawRect((double)var3, (double)var4, (double)var3 + 39.5D, (double)(var4 + 25.0F), -16777216);
      za.a(true);
      RenderUtils.drawFilledCircle(var3 + 19.75F, var4 + 12.5F, (float)this.L, (new Color(this.E.h(), this.E.f(), this.E.c(), (int)this.O)).getRGB());
      za.b();
      Jz.a.drawString(var2, var3 + 19.0F - (float)(Jz.a.stringWidth(var2) / 2) - 0.5F, var4 + 12.5F - (float)(Jz.a.getHeight() / 2), -1);
   }

   public void e() {
      this.O = 150.0D;
      this.L = 0.0D;
   }

   public IntProperty g() {
      return this.F;
   }

   public IntProperty c() {
      return this.D;
   }
}
