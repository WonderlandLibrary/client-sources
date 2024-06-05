package net;

import cc.novoline.Novoline;
import cc.novoline.commands.impl.ConfigCommand;
import cc.novoline.modules.visual.HUD;
import cc.novoline.utils.RenderUtils;
import cc.novoline.utils.Timer;
import cc.novoline.utils.fonts.impl.Fonts$SF$SF_17;
import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import net.aHW;
import net.acE;
import net.ayv;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class ad_ {
   private double j;
   private double a;
   private double e;
   private double b;
   private boolean i;
   private boolean k;
   private aHW h;
   private List c;
   private String f;
   private Timer d;
   private static int g;

   public ad_(double var1, double var3, aHW var5) {
      e();
      super();
      this.c = new CopyOnWriteArrayList();
      this.f = "";
      this.d = new Timer();
      this.j = var1;
      this.a = var3;
      this.h = var5;
      Iterator var7 = Novoline.getInstance().getModuleManager().getConfigManager().getConfigs().iterator();
      if(var7.hasNext()) {
         String var8 = (String)var7.next();
         this.c.add(new ayv(this, var8));
      }

   }

   public void a(int var1, int var2) {
      int var10000 = a();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      int var3 = var10000;
      Minecraft var4 = Minecraft.getInstance();
      int var5 = var4.getDebugFPS();
      if(this.c(var1, var2) || this.i && this.b < 1.0D) {
         this.b += 0.0025D * (double)(2000 / var5);
      }

      if(!this.c(var1, var2) && this.b > 0.0D) {
         this.b -= 0.0025D * (double)(2000 / var5);
      }

      this.b = MathHelper.clamp_double(this.b, 0.0D, 1.0D);
      this.e = 5.0D * this.b;
      var4.getTextureManager().bindTexture(new ResourceLocation("novoline/clickgui/windows/configs.png"));
      Gui.drawModalRectWithCustomSizedTexture((int)this.j, (int)(this.a - this.e), 128.0F, 128.0F, 32, 32, 32.0F, 32.0F);
      if(acE.b() == null) {
         ++var3;
         b(var3);
      }

   }

   public void b(int var1, int var2) {
      Gui.drawRect(5, 15, 205, 30, (new Color(76, 76, 76)).getRGB());
      a();
      Gui.drawRect(5, 5, 205, 15, (new Color(65, 160, 114)).getRGB());
      ((HUD)Novoline.getInstance().getModuleManager().getModule(HUD.class)).a(Fonts$SF$SF_17.SF_17, "Configs", (float)(100 - Fonts$SF$SF_17.SF_17.stringWidth("Configs") / 2), 7.0F);
      if(var1 >= 10 && var1 <= 50 && var2 >= 17 && var2 <= 28) {
         Fonts$SF$SF_17.SF_17.drawString("Back", 10.0D, 20.0D, (new Color(255, 255, 255)).darker().darker().getRGB(), true);
      }

      Fonts$SF$SF_17.SF_17.drawString("Back", 10.0D, 20.0D, (new Color(220, 220, 220)).darker().darker().getRGB(), true);
      if(var1 >= 205 - Fonts$SF$SF_17.SF_17.stringWidth("Refresh") - 5 && var1 <= 205 && var2 >= 17 && var2 <= 28) {
         Fonts$SF$SF_17.SF_17.drawString("Refresh", (double)(205 - Fonts$SF$SF_17.SF_17.stringWidth("Refresh") - 5), 20.0D, (new Color(255, 255, 255)).darker().darker().getRGB(), true);
      }

      Fonts$SF$SF_17.SF_17.drawString("Refresh", (double)(205 - Fonts$SF$SF_17.SF_17.stringWidth("Refresh") - 5), 20.0D, (new Color(220, 220, 220)).darker().darker().getRGB(), true);
      this.c.forEach(ad_::lambda$drawConfigs$0);
      Gui.drawRect(5, 30 + this.c.size() * 17, 205, 30 + this.c.size() * 17 + 15, (new Color(76, 76, 76)).getRGB());
      Gui.drawRect(10, 30 + this.c.size() * 17 + 11, 100, 30 + this.c.size() * 17 + 12, Color.WHITE.getRGB());
      Fonts$SF$SF_17.SF_17.drawString(this.f + (this.k?" ...":""), 10.0D, (double)(30 + this.c.size() * 17 + 4), -1, true);
      if(Keyboard.isKeyDown(14) && this.d.check(200.0F)) {
         this.f = this.f.substring(0, this.f.length() - 1);
         this.d.reset();
      }

      Gui.drawRect(110, 30 + this.c.size() * 17 + 2, 200, 30 + this.c.size() * 17 + 13, (new Color(31, 31, 31)).getRGB());
      if(var1 >= 110 && var1 <= 200 && var2 >= 30 + this.c.size() * 17 + 2 && var2 <= 30 + this.c.size() * 17 + 13) {
         RenderUtils.drawBorderedRect(110.0F, (float)(30 + this.c.size() * 17 + 2), 200.0F, (float)(30 + this.c.size() * 17 + 15), 1.0F, (new Color(100, 100, 100)).getRGB(), 268435455);
      }

      Fonts$SF$SF_17.SF_17.drawString("Create config", (double)(155 - Fonts$SF$SF_17.SF_17.stringWidth("Create config") / 2), (double)(30 + this.c.size() * 17 + 5), -1, true);
   }

   public void a(int var1, int var2, int var3) {
      int var4 = a();
      if(this.c(var1, var2) && var3 == 0) {
         this.h.d().a(false);
         this.i = !this.i;
      }

      if(this.i) {
         if(var1 >= 10 && var1 <= 50 && var2 >= 17 && var2 <= 28) {
            this.i = false;
         }

         if(var1 >= 205 - Fonts$SF$SF_17.SF_17.stringWidth("Refresh") - 5 && var1 <= 205 && var2 >= 17 && var2 <= 28) {
            this.b();
         }

         this.c.forEach(ad_::lambda$mouseClicked$1);
         if(var1 >= 10 && var1 <= 100 && var2 >= 30 + this.c.size() * 17 && var2 <= 30 + this.c.size() * 17 + 10) {
            this.k = !this.k;
         }

         this.k = false;
         if(!this.f.isEmpty() && var3 == 0 && var1 >= 110 && var1 <= 200 && var2 >= 30 + this.c.size() * 17 + 2 && var2 <= 30 + this.c.size() * 17 + 13) {
            ConfigCommand.saveConfig(Novoline.getInstance().getModuleManager().getConfigManager(), this.f);
            this.b();
            this.f = "";
         }
      }

   }

   public void a(char var1, int var2) {
      int var3 = e();
      if(this.k && var2 != 14 && var2 != 157 && var2 != 29 && var2 != 54 && var2 != 42 && var2 != 15 && var2 != 58 && var2 != 211 && var2 != 199 && var2 != 210 && var2 != 200 && var2 != 208 && var2 != 205 && var2 != 203 && var2 != 56 && var2 != 184 && var2 != 197 && var2 != 70 && var2 != 207 && var2 != 201 && var2 != 209 && var2 != 221 && var2 != 59 && var2 != 60 && var2 != 62 && var2 != 63 && var2 != 64 && var2 != 65 && var2 != 66 && var2 != 67 && var2 != 68 && var2 != 87 && var2 != 88 && this.f.length() < 16) {
         this.f = this.f + var1;
      }

   }

   public void b() {
      e();
      this.c.clear();
      Iterator var2 = Novoline.getInstance().getModuleManager().getConfigManager().getConfigs().iterator();
      if(var2.hasNext()) {
         String var3 = (String)var2.next();
         this.c.add(new ayv(this, var3));
      }

   }

   public List d() {
      return this.c;
   }

   public boolean c() {
      return this.i;
   }

   public void a(boolean var1) {
      this.i = var1;
   }

   private boolean c(int var1, int var2) {
      int var3 = a();
      return (double)var1 >= this.j && (double)var1 <= this.j + 32.0D && (double)var2 >= this.a && (double)var2 <= this.a + 32.0D;
   }

   private static void lambda$mouseClicked$1(int var0, int var1, int var2, ayv var3) {
      var3.a(var0, var1, var2);
   }

   private static void lambda$drawConfigs$0(int var0, int var1, ayv var2) {
      var2.a(var0, var1);
   }

   public static void b(int var0) {
      g = var0;
   }

   public static int e() {
      return g;
   }

   public static int a() {
      int var0 = e();
      return 95;
   }

   static {
      b(0);
   }
}
