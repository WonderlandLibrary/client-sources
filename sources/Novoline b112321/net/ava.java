package net;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Toolkit;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.Mt;
import net.UW;
import net.VN;
import net.a2V;
import net.a2t;
import net.a6_;
import net.a6d;
import net.aE3;
import net.aE8;
import net.aEB;
import net.aEl;
import net.aEs;
import net.aEu;
import net.aG6;
import net.aM5;
import net.aSt;
import net.aZM;
import net.abf;
import net.adZ;
import net.ae9;
import net.agu;
import net.as0;
import net.au7;
import net.av2;
import net.axu;
import net.bgM;
import net.dI;
import net.mt;
import net.sT;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class ava extends as0 {
   private SimpleDateFormat A = new SimpleDateFormat("HH:mm");
   private Dimension E = Toolkit.getDefaultToolkit().getScreenSize();
   private a2t y = aM5.a;
   @VN("client-name")
   private aEs S = axu.a("Novoline");
   @VN("scoreboard")
   private aE8 P = (aE8)((aE8)axu.b(Integer.valueOf(0)).d(Integer.valueOf(-150))).c(Integer.valueOf(200));
   @VN("scale")
   private aEl V = (aEl)((aEl)axu.a(Float.valueOf(1.0F)).d(Float.valueOf(0.4F))).c(Float.valueOf(3.0F));
   @VN("hud-elements")
   private aE3 K = axu.a((Object[])(new String[]{"ModuleList", "UserInfo", "ArmorHUD", "Scoreboard", "Bossbar", "PotionHUD", "Coords", "Name", "Time", "FPS"})).a((Object[])(new String[]{"ModuleList", "UserInfo", "TargetsList", "ArmorHUD", "Scoreboard", "Bossbar", "PotionHUD", "Coords", "Speed", "Name", "Time", "FPS", "Inventory", "Stats"}));
   @VN("hud-font")
   private aEs Q;
   @VN("suffix-type")
   private aEs D;
   @VN("background-alpha")
   private aE8 L;
   @VN("background-mode")
   private aEs B;
   @VN("inv-x")
   private aE8 O;
   @VN("inv-y")
   private aE8 I;
   @VN("tlist-x")
   private aE8 H;
   @VN("tlist-y")
   private aE8 N;
   @VN("hstats-x")
   private aE8 F;
   @VN("hstats-y")
   private aE8 R;
   @VN("notifications-sounds")
   private aEu C;
   @VN("array-color")
   private aEs U;
   @VN("hud-color")
   private aEs z;
   @VN("color-type")
   private aEs T;
   @VN("name-type")
   private aEs G;
   @VN("color")
   private aEB M;
   @VN("color-begin")
   private aEB J;
   @VN("color-end")
   private aEB W;
   private static int x;

   public ava(UW var1) {
      super(var1, "HUD", "HUD", 0, a2V.VISUALS, "Literally overlay", (String)null);
      h();
      this.Q = axu.a("Client").a(new String[]{"Client", "Vanilla"});
      this.D = axu.a("Simple").a(new String[]{"Simple", "Dash", "Bracket", "None"});
      this.L = (aE8)((aE8)axu.b(Integer.valueOf(50)).d(Integer.valueOf(1))).c(Integer.valueOf(255));
      this.B = axu.a("Bar").a(new String[]{"Outline", "Simple", "Bar"});
      this.O = (aE8)((aE8)axu.b(Integer.valueOf((int)this.E.getWidth() / 4)).d(Integer.valueOf(1))).c(Integer.valueOf((int)this.E.getWidth() / 2));
      this.I = (aE8)((aE8)axu.b(Integer.valueOf((int)this.E.getHeight() / 4)).d(Integer.valueOf(1))).c(Integer.valueOf((int)this.E.getHeight() / 2));
      this.H = (aE8)((aE8)axu.b(Integer.valueOf((int)this.E.getWidth() / 4)).d(Integer.valueOf(1))).c(Integer.valueOf((int)this.E.getWidth() / 2));
      this.N = (aE8)((aE8)axu.b(Integer.valueOf((int)this.E.getHeight() / 4)).d(Integer.valueOf(1))).c(Integer.valueOf((int)this.E.getHeight() / 2));
      this.F = (aE8)((aE8)axu.b(Integer.valueOf((int)this.E.getWidth() / 4)).d(Integer.valueOf(1))).c(Integer.valueOf((int)this.E.getWidth() / 2));
      this.R = (aE8)((aE8)axu.b(Integer.valueOf((int)this.E.getHeight() / 4)).d(Integer.valueOf(1))).c(Integer.valueOf((int)this.E.getHeight() / 2));
      this.C = axu.g();
      this.U = axu.a("Mono").a(new String[]{"Mono", "Rainbow", "Astolfo", "Gradient", "Type"});
      this.z = axu.a("Mono").a(new String[]{"Mono", "Array", "Dynamic"});
      this.T = axu.a("Static").a(new String[]{"Static", "Dynamic"});
      this.G = axu.a("Normal").a(new String[]{"Normal", "New"});
      this.M = axu.a(Integer.valueOf((new Color(200, 200, 200)).getRGB()));
      this.J = axu.a(Integer.valueOf((new Color(200, 200, 200)).getRGB()));
      this.W = axu.a(Integer.valueOf((new Color(200, 200, 200)).getRGB()));
      ae9.a(new adZ("CLIENT_NAME", "Client Name", a6d.TEXTBOX, this, "Client name", this.S, this::lambda$new$0));
      ae9.a(new adZ("HUDELEMENTS", "Elements", a6d.SELECTBOX, this, this.K));
      ae9.a(new adZ("HUD_NAME_TYPE", "Name Type", a6d.COMBOBOX, this, this.G, this::lambda$new$1));
      ae9.a(new adZ("HUD_FONT", "Font", a6d.COMBOBOX, this, this.Q));
      ae9.a(new adZ("SUFFIX_TYPE", "Suffix", a6d.COMBOBOX, this, this.D, this::lambda$new$2));
      ae9.a(new adZ("BACKGROUND_MODE", "Background", a6d.COMBOBOX, this, this.B));
      ae9.a(new adZ("ARRAY_COLOR", "Array Color", a6d.COMBOBOX, this, this.U));
      ae9.a(new adZ("HUD_COLOR", "HUD Color", a6d.COMBOBOX, this, this.z));
      ae9.a(new adZ("COLOR_TYPE", "Color Mode", a6d.COMBOBOX, this, this.T));
      ae9.a(new adZ("HUD_COLOR", "Color", a6d.COLOR_PICKER, this, this.M, (EnumSet)null, this::lambda$new$3));
      ae9.a(new adZ("HUD_COLOR_BEGIN", "Color Begin", a6d.COLOR_PICKER, this, this.J, (EnumSet)null, this::lambda$new$4));
      ae9.a(new adZ("HUD_COLOR_END", "Color End", a6d.COLOR_PICKER, this, this.W, (EnumSet)null, this::lambda$new$5));
      ae9.a(new adZ("FONT_SCALE", "HUD Scale", a6d.SLIDER, this, this.V, 0.1D, this::lambda$new$6));
      ae9.a(new adZ("SCOREBOARD_POS", "Scoreboard Height", a6d.SLIDER, this, this.P, 5.0D, this::lambda$new$7));
      ae9.a(new adZ("BACKGROUND_ALPHA", "Background Alpha", a6d.SLIDER, this, this.L, 5.0D, this::lambda$new$8));
      ae9.a(new adZ("NOTIFICATION-SOUNDS", "Notification sounds", a6d.CHECKBOX, this, this.C));
   }

   public aEl o() {
      return this.V;
   }

   public float j() {
      float var1 = this.Q.a("Vanilla")?1.0F:1.0F / ((Float)this.o().a()).floatValue();
      switch(Minecraft.getMinecraft().gameSettings.guiScale) {
      case 0:
         return var1 / 2.0F;
      case 1:
         return var1 / 0.5F;
      case 2:
      default:
         return var1;
      case 3:
         return var1 / 1.5F;
      }
   }

   public int b(float var1) {
      float var2 = 1.0F - MathHelper.abs(MathHelper.h((double)(var1 % 6000.0F / 6000.0F) * 3.141592653589793D * 2.0D) * 0.6F);
      Color var3 = Color.getHSBColor(this.M.i()[0], this.M.i()[1], var2);
      return (new Color(var3.getRed(), var3.getGreen(), var3.getBlue())).getRGB();
   }

   public int a(float var1) {
      Color var2 = Color.getHSBColor(var1, this.M.i()[1], this.M.i()[2]);
      return (new Color(var2.getRed(), var2.getGreen(), var2.getBlue())).getRGB();
   }

   public int a(float var1, float var2) {
      float var3 = 1.0F - MathHelper.abs(MathHelper.h((double)(var1 % 6000.0F / 6000.0F) * 3.141592653589793D * 2.0D) * 0.6F);
      Color var4 = Color.getHSBColor(var2, this.M.i()[1], var3);
      return (new Color(var4.getRed(), var4.getGreen(), var4.getBlue())).getRGB();
   }

   public int b(int var1, int var2) {
      h();
      double var4 = Math.ceil((double)(System.currentTimeMillis() - (long)var1 * (long)var2)) / 11.0D;
      var4 = var4 % 360.0D;
      float var6 = (double)((float)(var4 / 360.0D)) < 0.5D?-((float)(var4 / 360.0D)):(float)(var4 / 360.0D);
      Color var7 = Color.getHSBColor(var6, this.M.i()[1], this.M.i()[2]);
      return (new Color(var7.getRed(), var7.getGreen(), var7.getBlue())).getRGB();
   }

   public int a(int var1, int var2) {
      double var3 = Math.ceil((double)(System.currentTimeMillis() - (long)var1 * (long)var2)) / 11.0D;
      var3 = var3 % 360.0D;
      Color var5 = Color.getHSBColor((float)(var3 / 360.0D), this.M.i()[1], this.M.i()[2]);
      return (new Color(var5.getRed(), var5.getGreen(), var5.getBlue())).getRGB();
   }

   public int b(int var1, float var2, float var3, int var4) {
      int var5 = h();
      return this.U.a("Gradient")?this.a(this.J.a(), this.W.a(), var3):(this.U.a("Mono")?(this.T.a("Static")?this.q():this.b(var3)):(this.U.a("Type")?(this.T.a("Static")?this.a(var2):this.a(var3, var2)):(this.U.a("Rainbow")?(this.T.a("Static")?this.a(var1, 0):this.a(var1, var4)):(this.U.a("Astolfo")?(this.T.a("Static")?this.b(var1, 0):this.b(var1, var4)):this.q()))));
   }

   public int a(int var1, float var2, float var3, int var4) {
      int var5 = e();
      return this.z.a("Dynamic")?this.b(var3):(this.z.a("Mono")?this.q():this.b(var1, var2, var3, var4));
   }

   public int a(int var1, float var2, int var3) {
      int var4 = e();
      return this.z.a("Dynamic")?this.b(var2):(this.z.a("Mono")?this.q():this.b(var1, this.M.i()[0], var2, var3));
   }

   @agu
   public void g(aSt var1) {
      h();
      GL11.glPushMatrix();
      dI.a(this.w);
      if(this.K.a((Object)"Name") && !this.w.gameSettings.showDebugInfo) {
         label43: {
            if(this.G.a("Normal")) {
               this.j(var1);
               if(!this.K.a((Object)"Time")) {
                  break label43;
               }

               this.e(var1);
            }

            this.i(var1);
         }
      }

      if(this.K.a((Object)"Stats")) {
         this.D();
      }

      if(this.K.a((Object)"TargetsList")) {
         this.f(var1);
      }

      if(this.K.a((Object)"ArmorHUD")) {
         this.a(var1);
      }

      if(this.K.a((Object)"Inventory")) {
         this.s();
      }

      GL11.glPopMatrix();
      GL11.glPushMatrix();
      if(this.Q.a("Client")) {
         GL11.glScaled((double)((Float)this.V.a()).floatValue(), (double)((Float)this.V.a()).floatValue(), (double)((Float)this.V.a()).floatValue());
      }

      GL11.glScaled(1.0D, 1.0D, 1.0D);
      if(this.K.a((Object)"FPS")) {
         this.k(var1);
      }

      if(this.K.a((Object)"PotionHUD")) {
         this.h(var1);
      }

      if(this.K.a((Object)"UserInfo")) {
         this.c(var1);
      }

      if(this.K.a((Object)"Coords")) {
         this.m(var1);
      }

      if(this.K.a((Object)"Speed")) {
         this.d(var1);
      }

      if(this.K.a((Object)"ModuleList") && !this.w.gameSettings.showDebugInfo) {
         this.b(var1);
      }

      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glPopMatrix();
   }

   public aE8 b() {
      return this.O;
   }

   public aE8 E() {
      return this.I;
   }

   public void D() {
      av2 var2 = (av2)this.j.d().b(av2.class);
      double var3 = (double)((Integer)this.y().a()).intValue();
      h();
      double var5 = (double)((Integer)this.v().a()).intValue();
      double var7 = 200.0D;
      double var9 = 92.0D;
      int var11 = 0;
      long var12 = Minecraft.getSystemTime();
      Gui.a(var3, var5, var3 + var7, var5 + var9, (new Color(20, 20, 20, 170)).getRGB());
      int var14 = 0;
      if((double)var14 < var7) {
         Gui.a(var3 + (double)var14, var5, var3 + (double)var14 + 1.0D, var5 + 1.0D, this.a(var11, (float)var12, 3));
         var11 = var11 + 7;
         long var10000 = var12 - 30L;
         ++var14;
      }

      long var20 = System.currentTimeMillis() - var2.e();
      int var16 = (int)(var20 / 1000L);
      String var17 = (var16 / 3600 > 0?var16 / 3600 + "h ":"") + (var16 / 60 > 0?var16 / 60 % 60 + "m ":"") + var16 % 60 + "s";
      sT.a.a("Current Session", var3 + 2.5D, var5 + 5.0D, -1, true);
      abf.a.a("Playtime ", var3 + 4.0D, var5 + 20.0D, -1, true);
      abf.a.a(var17 + "", var3 + var7 - (double)abf.a.a(var17 + "") - 5.0D, var5 + 20.0D, -1, true);
      abf.a.a("Total staff bans ", var3 + 4.0D, var5 + 35.0D, -1, true);
      abf.a.a(var2.r() + "", var3 + var7 - (double)abf.a.a(var2.r() + "") - 5.0D, var5 + 35.0D, -1, true);
      abf.a.a("Total anticheat bans ", var3 + 4.0D, var5 + 50.0D, -1, true);
      abf.a.a(var2.g() + "", var3 + var7 - (double)abf.a.a(var2.g() + "") - 5.0D, var5 + 50.0D, -1, true);
      abf.a.a("Games Won ", var3 + 4.0D, var5 + 65.0D, -1, true);
      abf.a.a(var2.n() + "", var3 + var7 - (double)abf.a.a(var2.n() + "") - 5.0D, var5 + 65.0D, -1, true);
      abf.a.a("Players killed ", var3 + 4.0D, var5 + 80.0D, -1, true);
      abf.a.a(var2.a() + "", var3 + var7 - (double)abf.a.a(var2.a() + "") - 5.0D, var5 + 80.0D, -1, true);
   }

   public void s() {
      int var1 = e();
      if(!(this.w.currentScreen instanceof GuiChat)) {
         RenderHelper.enableGUIStandardItemLighting();
         GlStateManager.enableDepth();
         int var2 = ((Integer)this.O.a()).intValue();
         int var3 = ((Integer)this.I.a()).intValue();
         Gui.drawRect(var2, var3, var2 + 167, var3 + 73, (new Color(29, 29, 29, 255)).getRGB());
         Gui.drawRect(var2 + 1, var3 + 13, var2 + 166, var3 + 72, (new Color(40, 40, 40, 255)).getRGB());
         this.w.a.drawString("Your Inventory", (float)(var2 + 3), (float)(var3 + 3), (new Color(255, 255, 255)).getRGB(), true);
         boolean var4 = false;
         int var5 = 9;
         if(var5 < this.w.thePlayer.bo.inventorySlots.size() - 9) {
            Slot var6 = (Slot)this.w.thePlayer.bo.inventorySlots.get(var5);
            if(var6.getHasStack()) {
               var4 = true;
            }

            int var7 = var6.xDisplayPosition;
            int var8 = var6.yDisplayPosition;
            this.w.a().b(var6.getStack(), (float)(var2 + var7 - 4), (float)(var3 + var8 - 68));
            this.w.a().a(this.w.a, var6.getStack(), var2 + var7 - 4, var3 + var8 - 68, (String)null);
            ++var5;
         }

         if(this.w.currentScreen instanceof GuiInventory) {
            this.w.a.drawString("Already in inventory", (float)(var2 + 83 - this.w.a.d("Already in inventory") / 2), (float)(var3 + 36), (new Color(255, 255, 255)).getRGB(), true);
         }

         if(!var4) {
            this.w.a.drawString("Empty...", (float)(var2 + 83 - this.w.a.d("Empty...") / 2), (float)(var3 + 36), (new Color(255, 255, 255)).getRGB(), true);
         }

         RenderHelper.disableStandardItemLighting();
         GlStateManager.disableDepth();
      }

   }

   public List g() {
      h();
      FontRenderer var2 = this.w.fontRendererObj;
      String var3 = (String)this.Q.a();
      Stream var4 = this.j.d().a().stream();
      if(var3.equals("Vanilla")) {
         var4 = var4.sorted(ava::lambda$getModules$9);
      }

      var4 = var4.sorted(this::lambda$getModules$10);
      return (List)var4.filter(ava::lambda$getModules$11).collect(Collectors.toList());
   }

   public float a(as0 var1) {
      h();
      float var3 = 2.0F;
      Iterator var4 = this.g().iterator();
      if(var4.hasNext()) {
         as0 var5 = (as0)var4.next();
         if(var5 == var1) {
            return var3;
         }

         if(this.Q.a("Vanilla")) {
            var3 += (float)(this.w.fontRendererObj.f() + 2);
         }

         float var10000 = var3 + (float)(this.y.c() + 1);
      }

      return 2.0F;
   }

   @agu
   public void l(aSt var1) {
      int var2 = h();
      float var3 = this.Q.a("Vanilla")?(float)(-this.w.a.f() + 1):(float)(-this.y.c());
      Iterator var4 = this.g().iterator();
      if(var4.hasNext()) {
         as0 var5 = (as0)var4.next();
         double var6 = MathHelper.b((double)(this.w.getDebugFPS() / 30), 1.0D, 9999.0D);
         if(var5.y()) {
            var5.s = (float)((double)var5.s + (double)(this.Q.a("Vanilla")?(float)this.w.fontRendererObj.d(var5.k()):(float)this.y.a(var5.k()) - var5.s) * (0.2D / var6));
         }

         if(var5.s < 0.0F) {
            this.j.d().a().remove(var5);
         }

         if(var5.s > -2.0F) {
            var5.s = (float)((double)var5.s + (double)(-2.0F - var5.s) * (0.2D / var6));
         }

         var5.s = MathHelper.a(var5.s, -2.0F, this.Q.a("Vanilla")?(float)this.w.fontRendererObj.d(var5.k()):(float)this.y.a(var5.k()));
         if(this.Q.a("Vanilla")) {
            var3 += (float)this.w.fontRendererObj.f() + 1.0F;
         }

         var3 = var3 + (float)this.y.c() + 1.5F;
         var5.h = (float)((double)var5.h + (double)(var3 - var5.h) * (0.2D / var6));
      }

   }

   private void b(aSt var1) {
      e();
      float var3 = (float)Minecraft.getSystemTime();
      float var4 = this.Q.a("Vanilla")?(float)(-this.w.a.f() + 1):(float)(-this.y.c());
      String var6 = (String)this.Q.a();
      List var7 = this.g();
      int var8 = 1;
      if(((ava)this.b((Class)ava.class)).y() && ((List)this.K.a()).contains("ModuleList")) {
         Iterator var9 = var7.iterator();
         if(var9.hasNext()) {
            as0 var10 = (as0)var9.next();
            double var11 = MathHelper.b((double)(this.w.getDebugFPS() / 30), 1.0D, 9999.0D);
            if(var10.y()) {
               var10.s = (float)((double)var10.s + (double)(var6.equals("Vanilla")?(float)this.w.fontRendererObj.d(var10.k()):(float)this.y.a(var10.k()) - var10.s) * (0.2D / var11));
            }

            if(var10.s < 0.0F) {
               this.j.d().a().remove(var10);
            }

            if(var10.s > -2.0F) {
               var10.s = (float)((double)var10.s + (double)(-2.0F - var10.s) * (0.2D / var11));
            }

            var10.s = MathHelper.a(var10.s, -2.0F, var6.equals("Vanilla")?(float)this.w.fontRendererObj.d(var10.k()):(float)this.y.a(var10.k()));
            if(var6.equals("Vanilla")) {
               var4 += (float)this.w.fontRendererObj.f() + 1.0F;
            }

            var4 = var4 + (float)this.y.c() + 1.5F;
            var10.h = (float)((double)var10.h + (double)(var4 - var10.h) * (0.2D / var11));
            float var5 = var10.h;
            float var13 = (float)var1.a().a(this.w) * this.j() - var10.s - 2.0F;
            int var14 = (new Color(0, 0, 0, ((Integer)this.L.a()).intValue())).getRGB();
            int var15 = this.b(var8, var10.w(), var3, 110);
            float var16 = (float)var1.a().a(this.w) * this.j();
            float var17 = var6.equals("Vanilla")?(float)this.w.fontRendererObj.f():(float)this.y.c();
            float var18 = this.B.a("Bar")?2.5F:1.5F;
            if(this.B.a("Outline")) {
               Gui.a((double)(var16 - (var10.s + 6.0F) - var18), (double)var5 - (var7.indexOf(var10) == 0?1.5D:-0.5D), (double)(var16 - (var10.s + 5.0F) - var18), (double)(var5 + var17 + 2.0F), var15);
               if(var7.indexOf(var10) == var7.size() - 1) {
                  Gui.a((double)(var16 - (var10.s + 6.0F) - var18), (double)(var5 + var17 + 2.0F), (double)var16, (double)(var5 + var17 + 3.0F), var15);
               }

               if(var6.equals("Vanilla")) {
                  float var19 = (float)Math.abs(this.w.fontRendererObj.d(((as0)var7.get(var7.indexOf(var10))).k()) - this.w.fontRendererObj.d(((as0)var7.get(var7.indexOf(var10) + 1)).k()));
               }

               float var22 = (float)Math.abs(this.y.a(((as0)var7.get(var7.indexOf(var10))).k()) - this.y.a(((as0)var7.get(var7.indexOf(var10) + 1)).k()));
               Gui.a((double)(var16 - (var10.s + 5.0F) - var18), (double)(var5 + var17 + 1.0F), (double)(var16 - (var10.s + 5.0F) - var18 + var22), (double)(var5 + var17 + 2.0F), var15);
            }

            Gui.a((double)(var16 - (var10.s + 4.0F) - var18 - (float)(this.B.a("Outline")?1:0)), (double)(var5 - (var7.indexOf(var10) == 0?3.0F:0.5F)), (double)var16, (double)(var5 + var17 + (float)(var7.indexOf(var10) == var7.size() - 1?2:1)), var14);
            if(var6.equals("Vanilla")) {
               this.w.fontRendererObj.drawStringWithShadow(var10.k(), var13 - var18, var5 + 1.0F, var15);
            }

            this.y.a(var10.k(), (double)(var13 - var18), (double)(var5 + 1.0F), var15, true);
            if(this.B.a("Bar")) {
               Gui.a((double)(var16 - 1.0F), (double)(var5 - 1.0F), (double)var16, (double)(var5 + var17 + (float)(var7.indexOf(var10) == var7.size() - 1?2:1)), var15);
            }

            float var10000 = var3 - (this.U.a("Gradient")?225.0F:300.0F);
            ++var8;
         }
      }

   }

   public void a(String var1, int var2) {
      try {
         this.y = aG6.a((mt)null, Font.createFont(0, new FileInputStream(var1))).a(var2);
      } catch (IOException | FontFormatException var4) {
         var4.printStackTrace();
      }

   }

   public int a(Color var1, Color var2, float var3) {
      float var4 = 1.0F - MathHelper.abs(MathHelper.h((double)(var3 % 9000.0F / 9000.0F) * 3.141592653589793D * 3.0D));
      return a6_.a(var1, var2, var4);
   }

   private void a(aSt var1) {
      int var3 = this.q();
      GL11.glPushMatrix();
      h();
      ObjectArrayList var4 = new ObjectArrayList();
      boolean var5 = this.w.thePlayer.isEntityAlive() && this.w.thePlayer.isInsideOfMaterial(Material.water);
      int var6 = 3;
      ItemStack var7 = this.w.thePlayer.bJ.armorInventory[var6];
      if(var7 != null) {
         var4.add(var7);
      }

      --var6;
      if(this.w.thePlayer.getCurrentEquippedItem() != null) {
         var4.add(this.w.thePlayer.getCurrentEquippedItem());
      }

      var6 = -3;
      int var18 = -3;
      Iterator var8 = var4.iterator();
      if(var8.hasNext()) {
         ItemStack var9 = (ItemStack)var8.next();
         if(this.w.theWorld != null) {
            RenderHelper.enableGUIStandardItemLighting();
            var6 += 16;
            var18 += 32;
         }

         int var10 = var9.getMaxDamage() - var9.getItemDamage();
         String var11 = (String)this.Q.a();
         ScaledResolution var12 = var1.a();
         int var13 = var12.a(this.w);
         int var14 = var12.b(this.w);
         if(!var9.isStackable()) {
            RenderHelper.disableStandardItemLighting();
            if(!(var9.getItem() instanceof ItemPotion)) {
               if(var11.equals("Client")) {
                  float var15 = (float)(var14 - (var5?68:58));
                  Mt.a.a(String.valueOf(var10), (double)((float)var6 + (float)var13 / 2.0F - 1.5F), (double)var15, var3, true);
               }

               GL11.glPushMatrix();
               GL11.glScalef(0.5F, 0.5F, 0.5F);
               this.w.fontRendererObj.drawStringWithShadow(String.valueOf(var10), (float)(var18 + var13) - 4.0F, (float)(var14 * 2 - (var5?139:119)), var3);
               GL11.glScalef(2.0F, 2.0F, 2.0F);
               GL11.glPopMatrix();
            }
         }

         GlStateManager.pushMatrix();
         GlStateManager.disableAlpha();
         GlStateManager.clear(256);
         GlStateManager.enableBlend();
         this.w.a().a = -150.0F;
         this.w.a().b(var9, (float)(var6 + (int)((float)var13 / 2.0F)) - 4.0F, (float)var14 - 66.5F);
         if(var9.isStackable()) {
            RenderHelper.disableStandardItemLighting();
            if(var11.equals("Client")) {
               float var19 = (float)(var14 - (var5?68:58));
               Mt.a.a(String.valueOf(var9.stackSize), (double)((float)var6 + (float)var13 / 2.0F - 1.5F), (double)var19, var3, true);
            }

            GL11.glPushMatrix();
            GL11.glScalef(0.5F, 0.5F, 0.5F);
            this.w.fontRendererObj.drawStringWithShadow(String.valueOf(var9.stackSize), (float)(var18 + var13) - 4.0F, (float)(var14 * 2 - (var5?139:119)), var3);
            GL11.glScalef(2.0F, 2.0F, 2.0F);
            GL11.glPopMatrix();
         }

         this.w.a().a = 0.0F;
         GlStateManager.enableBlend();
         GlStateManager.scale(0.5F, 0.5F, 0.5F);
         GlStateManager.disableDepth();
         GlStateManager.disableLighting();
         GlStateManager.enableDepth();
         GlStateManager.scale(2.0F, 2.0F, 2.0F);
         GlStateManager.enableAlpha();
         GlStateManager.popMatrix();
         var9.getEnchantmentTagList();
      }

      GL11.glPopMatrix();
   }

   private void c(aSt var1) {
      h();
      StringBuilder var3 = new StringBuilder();
      int var4 = 1;
      if(var4 <= 4 - net.skidunion.J.aK.length()) {
         var3.append("0");
         ++var4;
      }

      String var8 = var3 + net.skidunion.J.aK;
      String var5 = "Build - " + EnumChatFormatting.RESET + this.j.z();
      String var6 = EnumChatFormatting.GRAY + var5 + EnumChatFormatting.GRAY + " | UID - " + EnumChatFormatting.WHITE + var8;
      if(this.Q.a("Client")) {
         this.y.a(var6, (double)((float)var1.a().a(this.w) * this.j() - (float)this.y.a(var6) - 2.0F), (double)((float)var1.a().b(this.w) * this.j() - (float)this.y.c() - 1.0F), (new Color(255, 255, 255)).getRGB(), true);
      }

      this.w.fontRendererObj.drawStringWithShadow(var6, (float)var1.a().a(this.w) * this.j() - (float)this.w.fontRendererObj.d(var6) - 1.0F, (float)var1.a().b(this.w) * this.j() - (float)this.w.fontRendererObj.f() - 1.0F, (new Color(255, 255, 255)).getRGB());
   }

   private String c(PotionEffect var1) {
      return I18n.format(Potion.potionTypes[var1.getPotionID()].getName(), new Object[0]);
   }

   private String b(PotionEffect var1) {
      return Potion.getDurationString(var1);
   }

   private String a(PotionEffect var1) {
      int var2 = e();
      return var1.getAmplifier() == 1?" II":(var1.getAmplifier() == 2?" III":(var1.getAmplifier() == 3?" IV":""));
   }

   private Collection x() {
      return this.w.thePlayer.getActivePotionEffects();
   }

   private void h(aSt var1) {
      h();
      float var3 = 0.0F;
      int var4 = 1;
      float var5 = (float)Minecraft.getSystemTime();
      Iterator var6 = ((List)this.x().stream().sorted(this::lambda$drawPotionHUD$12).collect(Collectors.toList())).iterator();
      if(var6.hasNext()) {
         PotionEffect var7 = (PotionEffect)var6.next();
         Potion var8 = Potion.potionTypes[var7.getPotionID()];
         int var9 = this.w.ingameGUI.getChatGUI().getChatOpen() && !this.K.a((Object)"UserInfo")?5:-10;
         Color var10 = new Color(var8.getLiquidColor());
         float[] var11 = Color.RGBtoHSB(var10.getRed(), var10.getGreen(), var10.getBlue(), new float[3]);
         int var12 = this.a(var4, var11[0], var5, 180);
         ScaledResolution var13 = var1.a();
         int var14 = (int)((float)var13.a(this.w) * this.j());
         int var15 = (int)((float)var13.b(this.w) * this.j());
         float var16 = (float)(var15 - this.y.c()) + var3 - (this.K.a((Object)"UserInfo")?(float)(this.y.c() + 14):12.5F) - (float)var9;
         String var17 = this.c(var7) + this.a(var7) + " §7" + this.b(var7);
         if(this.Q.a("Client")) {
            this.y.a(var17, (double)((float)(var14 - this.y.a(var17)) - 2.0F), (double)(var16 + 1.75F), var12, true);
         }

         this.w.fontRendererObj.drawString(var17, (float)(var14 - this.w.fontRendererObj.d(var17)) - 2.0F, var16 + 1.0F, var12, true);
         ++var4;
         var5 = var5 - 300.0F;
         var3 = var3 - (this.Q.a("Vanilla")?(float)this.w.fontRendererObj.f() + 0.5F:(float)this.y.c() + 1.0F);
      }

   }

   private void m(aSt var1) {
      e();
      Entity var3 = this.w.getRenderViewEntity();
      String var4 = "XYZ: ";
      String var5 = String.format("%.0f %.0f %.0f", new Object[]{Double.valueOf(var3.posX), Double.valueOf(var3.posY), Double.valueOf(var3.posZ)});
      float var6 = (float)var1.a().b(this.w) * this.j() - (this.Q.a("Vanilla")?(float)(this.w.fontRendererObj.f() + 1):(float)this.y.c() * 1.2F);
      float var7 = (float)var1.a().b(this.w) - 10.0F;
      if(this.Q.a("Client")) {
         this.a(this.y, var4, 1.0F, var6 + 1.5F);
         this.y.a(var5, (double)(1.0F + (float)this.y.a(var4)), (double)(var6 + 1.5F), (new Color(255, 255, 255)).getRGB(), true);
      }

      this.a(var4, 2.0F, var6 + 0.5F);
      this.w.fontRendererObj.drawString(var5, 2.0F + (float)this.y.a(var4), var6 + 0.5F, (new Color(255, 255, 255)).getRGB(), true);
   }

   private void k(aSt var1) {
      int var2 = e();
      float var3 = (float)var1.a().b(this.w) * this.j() - (((List)this.K.a()).contains("Coords")?(((List)this.K.a()).contains("Speed")?(this.Q.a("Vanilla")?(float)(this.w.fontRendererObj.f() * 3 + 2):(float)this.y.c() * 3.2F + 3.0F):(this.Q.a("Vanilla")?(float)(this.w.fontRendererObj.f() * 2 + 2):(float)this.y.c() * 2.2F + 1.0F)):(this.K.a((Object)"Speed")?(this.Q.a("Vanilla")?(float)(this.w.fontRendererObj.f() * 2 + 2):(float)this.y.c() * 2.2F + 2.0F):(this.Q.a("Vanilla")?(float)(this.w.fontRendererObj.f() + 2):(float)this.y.c() * 1.2F + 1.0F)));
      String var4 = "FPS: ";
      String var5 = String.valueOf(this.w.getDebugFPS());
      if(this.Q.a("Client")) {
         this.a(this.y, var4, 1.0F, var3 + 1.5F);
         this.y.a(var5, (double)this.y.a(var4), (double)(var3 + 1.5F), (new Color(255, 255, 255)).getRGB(), true);
      }

      this.a(var4, 2.0F, var3 + 0.5F);
      this.w.fontRendererObj.drawString(var5, 1.0F + (float)this.w.fontRendererObj.d(var4), var3 + 0.5F, (new Color(255, 255, 255)).getRGB(), true);
   }

   private void d(aSt var1) {
      int var2 = h();
      float var3 = (float)var1.a().b(this.w) * this.j() - (((List)this.K.a()).contains("Coords")?(this.Q.a("Vanilla")?(float)(this.w.fontRendererObj.f() * 2 + 2):(float)this.y.c() * 2.2F + 2.0F):(this.Q.a("Vanilla")?(float)(this.w.fontRendererObj.f() * 2 + 2):(float)this.y.c() * 1.2F + 1.0F));
      float var4 = this.w.timer.i;
      double var5 = MathHelper.b(this.w.thePlayer.ay() * 20.0D * (double)this.w.timer.i, 2);
      String var7 = "Speed: ";
      String var8 = String.format("%.2f", new Object[]{Double.valueOf(var5)});
      if(this.Q.a("Client")) {
         this.a(this.y, var7, 1.0F, var3 + 1.5F);
         this.y.a(var8, (double)this.y.a(var7), (double)(var3 + 1.5F), (new Color(255, 255, 255)).getRGB(), true);
         this.y.a(" b/s", (double)this.y.a(var7 + var8), (double)(var3 + 1.5F), (new Color(255, 255, 255)).getRGB(), true);
      }

      this.a(var7, 2.0F, var3 + 0.5F);
      this.w.fontRendererObj.drawString(var8, 2.0F + (float)this.y.a(var7), var3 + 0.5F, (new Color(255, 255, 255)).getRGB(), true);
      this.w.fontRendererObj.drawString(" b/s", 2.0F + (float)this.y.a(var7) + (var5 > 10.0D?25.5F:(var5 < 0.0D?(var5 < -9.0D?31.0F:24.5F):19.0F)), var3 + 0.5F, (new Color(255, 255, 255)).getRGB(), true);
   }

   public void a(a2t var1, String var2, float var3, float var4, float var5) {
      int var6 = h();
      float var8 = (float)Minecraft.getSystemTime();
      int var9 = 0;
      if(var9 < var2.length()) {
         int var10 = this.a(var8, var5);
         var1.a(String.valueOf(var2.charAt(var9)), (double)var3, (double)var4, var10, true);
         float var7 = var3 + (float)var1.a(String.valueOf(var2.charAt(var9))) + 0.5F;
         var8 = var8 - 300.0F;
         ++var9;
      }

   }

   public void a(String var1, float var2, float var3) {
      int var4 = e();
      float var6 = (float)Minecraft.getSystemTime();
      int var7 = 0;
      if(var7 < var1.length()) {
         int var8 = this.a(var7, this.M.i()[0], var6, 110);
         this.w.fontRendererObj.drawString(String.valueOf(var1.charAt(var7)), var2, var3, var8, true);
         float var5 = var2 + (float)this.w.fontRendererObj.d(String.valueOf(var1.charAt(var7))) + 0.1F;
         var6 = var6 - 300.0F;
         ++var7;
      }

   }

   public void a(a2t var1, String var2, float var3, float var4) {
      float var7 = (float)Minecraft.getSystemTime();
      h();
      int var8 = 0;
      if(var8 < var2.length()) {
         int var9 = this.a(var8, this.M.i()[0], var7, 110);
         var1.a(String.valueOf(var2.charAt(var8)), (double)var3, (double)var4, var9, true);
         float var6 = var3 + (float)var1.a(String.valueOf(var2.charAt(var8))) + 0.5F;
         var7 = var7 - 300.0F;
         ++var8;
      }

   }

   public String c() {
      int var1 = e();
      return ((String)this.S.a()).isEmpty()?"Novoline":(String)this.S.a();
   }

   public String C() {
      return (String)this.G.a();
   }

   private void i(aSt var1) {
      int var2 = h();
      if(this.K.a((Object)"Time")) {
         String var3 = this.c() + " " + this.a() + " - " + (bgM.d().a()?"Development Build ":"Release ") + this.j.z() + " - User: " + net.skidunion.J.V;
      }

      String var7 = this.c() + " - " + (bgM.d().a()?"Development Build ":"Release ") + this.j.z() + " - User: " + net.skidunion.J.V;
      Gui.a(2.0D, 2.5D, (double)(5 + abf.a.a(var7) + 3), 17.5D, (new Color(20, 20, 20, 170)).getRGB());
      float var4 = (float)Minecraft.getSystemTime();
      int var5 = 0;
      int var6 = 2;
      if(var6 < 5 + abf.a.a(var7) + 3) {
         Gui.a((double)var6, 2.5D, (double)(var6 + 1), 3.5D, this.a(var5, var4, 3));
         var5 = var5 + 7;
         var4 = var4 - 30.0F;
         ++var6;
      }

      abf.a.a(var7, 5.0D, 7.0D, -1, true);
   }

   private void j(aSt var1) {
      h();
      String var3 = this.c();
      byte var4 = 1;
      if(this.Q.a("Client")) {
         this.a(abf.a, var3, (float)var4, 4.5F);
      }

      this.a(var3, (float)var4 + 0.5F, 4.0F);
   }

   private String a() {
      return EnumChatFormatting.GRAY + "(" + EnumChatFormatting.RESET + this.A.format(new Date()) + EnumChatFormatting.GRAY + ")" + EnumChatFormatting.RESET;
   }

   private void e(aSt var1) {
      a2t var2 = abf.a;
      var2.a(this.a(), (double)(8.0F + (float)var2.a(this.c())), 4.5D, 16777215, true);
   }

   public void i() {
      int var2 = 13;
      h();
      int var3 = ((Integer)this.H.a()).intValue();
      int var4 = ((Integer)this.N.a()).intValue();
      List var5 = this.u();
      int var6 = 0;
      Iterator var7 = var5.iterator();
      if(var7.hasNext()) {
         Entity var8 = (Entity)var7.next();
         var2 += this.w.a.f() + 1;
         int var9 = this.w.a.d(var8.getName());
         if(var9 > var6) {
            var6 = var9;
         }
      }

      var6 = Math.max(100, var6 + 25);
      var6 = var6 + (var6 == 100?0:30);
      var2 = var2 - (this.w.a.f() + 1);
      if(var5.isEmpty()) {
         var2 -= 2;
      }

      Gui.drawRect(var3, var4 - 13, var3 + var6, var4 + var2, (new Color(29, 29, 29, 255)).getRGB());
      Gui.drawRect(var3 + 1, var4, var3 + var6 - 1, var4 + var2 - 1, (new Color(40, 40, 40, 255)).getRGB());
      this.w.a.drawString("Your Targets", (float)(var3 + 3), (float)(var4 - 10), (new Color(255, 255, 255)).getRGB(), true);
      Iterator var15 = var5.iterator();
      if(var15.hasNext()) {
         Entity var16 = (Entity)var15.next();
         this.w.a.b(var16.getName(), (float)(var3 + 3), (float)(var4 + 2), (new Color(200, 200, 200, 255)).getRGB());
         String var10 = (int)this.w.thePlayer.getDistanceToEntity(var16) + "m";
         this.w.a.b(var10, (float)(var3 + var6 - 1 - this.w.a.d(var10) - 1), (float)(var4 + 2), (new Color(200, 200, 200, 255)).getRGB());
         int var14 = var4 + this.w.a.f() + 1;
      }

   }

   public double[] k() {
      e();
      List var2 = this.u();
      int var3 = 13;
      int var4 = 0;
      Iterator var5 = var2.iterator();
      if(var5.hasNext()) {
         Entity var6 = (Entity)var5.next();
         var3 += this.w.a.f() + 1;
         int var7 = this.w.a.d(var6.getName());
         if(var7 > var4) {
            var4 = var7;
         }
      }

      var4 = Math.max(100, var4 + 25);
      var4 = var4 + (var4 == 100?0:30);
      var3 = var3 - (this.w.a.f() + 1);
      return new double[]{(double)var4, (double)var3};
   }

   private void f(aSt var1) {
      int var2 = e();
      if(!(this.w.currentScreen instanceof GuiChat)) {
         this.i();
      }

   }

   public aE8 t() {
      return this.H;
   }

   public aE8 m() {
      return this.N;
   }

   private List u() {
      List var1 = this.j.k().a(au7.TARGET);
      return (List)this.w.theWorld.getLoadedEntityList().stream().filter(ava::lambda$getLoadedTargets$13).collect(Collectors.toCollection(ObjectArrayList::<init>));
   }

   private int n() {
      List var2 = this.u();
      e();
      int var3 = 0;
      Iterator var4 = var2.iterator();
      if(var4.hasNext()) {
         Entity var5 = (Entity)var4.next();
         if(aZM.a.a(var5.getName()) > var3) {
            var3 = aZM.a.a(var5.getName());
         }
      }

      return var3 + 35;
   }

   public aE8 l() {
      return this.P;
   }

   public aE3 F() {
      return this.K;
   }

   public aEs A() {
      return this.Q;
   }

   public aEs w() {
      return this.D;
   }

   public aE8 B() {
      return this.L;
   }

   public aEs d() {
      return this.B;
   }

   public aEu r() {
      return this.C;
   }

   public Color z() {
      return this.M.a();
   }

   public int q() {
      return this.M.a().getRGB();
   }

   public aE8 y() {
      return this.F;
   }

   public aE8 v() {
      return this.R;
   }

   public aEB f() {
      return this.M;
   }

   private static boolean lambda$getLoadedTargets$13(List var0, Entity var1) {
      return var0.contains(var1.getName().toLowerCase());
   }

   private int lambda$drawPotionHUD$12(PotionEffect var1, PotionEffect var2) {
      return Integer.compare(this.y.a(this.c(var2) + " " + this.b(var2)), this.y.a(this.c(var1) + " " + this.b(var1)));
   }

   private static boolean lambda$getModules$11(as0 var0) {
      int var1 = h();
      return !var0.l();
   }

   private int lambda$getModules$10(as0 var1, as0 var2) {
      return Integer.compare(this.y.a(var2.k()), this.y.a(var1.k()));
   }

   private static int lambda$getModules$9(FontRenderer var0, as0 var1, as0 var2) {
      return Integer.compare(var0.d(var2.k()), var0.d(var1.k()));
   }

   private Boolean lambda$new$8() {
      return Boolean.valueOf(((List)this.K.a()).contains("ModuleList"));
   }

   private Boolean lambda$new$7() {
      return Boolean.valueOf(((List)this.K.a()).contains("Scoreboard"));
   }

   private Boolean lambda$new$6() {
      int var1 = e();
      return Boolean.valueOf(((List)this.K.a()).contains("ModuleList") && this.Q.a("Client"));
   }

   private Boolean lambda$new$5() {
      return Boolean.valueOf(this.U.a("Gradient"));
   }

   private Boolean lambda$new$4() {
      return Boolean.valueOf(this.U.a("Gradient"));
   }

   private Boolean lambda$new$3() {
      int var1 = h();
      return Boolean.valueOf(!this.U.a("Gradient") || !this.z.a("Array"));
   }

   private Boolean lambda$new$2() {
      return Boolean.valueOf(((List)this.K.a()).contains("ModuleList"));
   }

   private Boolean lambda$new$1() {
      return Boolean.valueOf(this.K.a((Object)"Name"));
   }

   private Boolean lambda$new$0() {
      return Boolean.valueOf(((List)this.K.a()).contains("Name"));
   }

   public static void b(int var0) {
      x = var0;
   }

   public static int h() {
      return x;
   }

   public static int e() {
      int var0 = h();
      return 117;
   }

   static {
      b(0);
   }
}
