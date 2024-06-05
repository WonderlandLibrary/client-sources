package cc.novoline.gui.screen.dropdown.config;

import cc.novoline.Novoline;
import cc.novoline.modules.visual.HUD;
import cc.novoline.utils.OpenGLUtil;
import cc.novoline.utils.Timer;
import cc.novoline.utils.fonts.impl.Fonts$SF$SF_18;
import java.awt.Color;
import java.util.Iterator;
import net.K6;
import net.a1I;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.MathHelper;

public class Config {
   private K6 e;
   public int y;
   public a1I i;
   private float fraction = 0.0F;
   private Timer lastClick = new Timer();
   public static final Color WHITE = new Color(255, 255, 255, 255);
   public static final Color BACKGROUND_130 = new Color(0, 0, 0, 130);
   public static final Color BACKGROUND_120 = new Color(0, 0, 0, 120);
   private static boolean c;

   public Config(K6 var1, a1I var2) {
      this.e = var1;
      this.i = var2;
   }

   public void b(int var1, int var2) {
      a();
      this.y = (int)(this.i.getPosY() + 15.0F);
      boolean var4 = this.i.c() == this;
      HUD var5 = (HUD)Novoline.getInstance().getModuleManager().getModule(HUD.class);
      Iterator var6 = this.i.a().iterator();
      if(var6.hasNext()) {
         Config var7 = (Config)var6.next();
         if(var7 == this) {
            ;
         }

         this.y += var7.getYPerConfig();
      }

      if(var4 && this.fraction < 1.0F) {
         this.fraction = (float)((double)this.fraction + 0.0025D * (double)(2000 / Minecraft.getInstance().getDebugFPS()));
      }

      if(!var4 && this.fraction > 0.0F) {
         this.fraction = (float)((double)this.fraction - 0.0025D * (double)(2000 / Minecraft.getInstance().getDebugFPS()));
      }

      this.fraction = MathHelper.clamp_float(this.fraction, 0.0F, 1.0F);
      Gui.drawRect((double)this.i.getPosX(), (double)this.y, (double)(this.i.getPosX() + 100.0F), (double)(this.y + this.getYPerConfig()), (new Color(40, 40, 40, 255)).getRGB());
      Fonts$SF$SF_18.SF_18.drawCenteredString(this.e.g(), this.i.getPosX() + 50.0F, (float)(this.y + 4), OpenGLUtil.interpolateColor(WHITE, new Color(var5.getColor().getRed(), var5.getColor().getGreen(), var5.getColor().getBlue(), 250), this.fraction), true);
   }

   public boolean isHovered(int var1, int var2) {
      d();
      this.y = (int)(this.i.getPosY() + 15.0F);
      Iterator var4 = this.i.a().iterator();
      if(var4.hasNext()) {
         Config var5 = (Config)var4.next();
         if(var5 == this) {
            ;
         }

         this.y += var5.getYPerConfig();
      }

      return (float)var1 >= this.i.getPosX() && var2 >= this.y && (float)var1 <= this.i.getPosX() + 101.0F && var2 <= this.y + this.getYPerConfig();
   }

   public void a(int var1, int var2, int var3) {
      boolean var4 = a();
      if(this.isHovered(var1, var2) && var3 == 0) {
         this.i.a(this);
         if(!this.lastClick.check(200.0F)) {
            Novoline.getInstance().u().d(this.e.g());
         }

         this.lastClick.reset();
      }

   }

   public int getYPerConfig() {
      return 14;
   }

   public String e() {
      return this.e.g();
   }

   public a1I b() {
      return this.i;
   }

   static {
      b(true);
   }

   public static void b(boolean var0) {
      c = var0;
   }

   public static boolean d() {
      return c;
   }

   public static boolean a() {
      boolean var0 = d();
      return true;
   }
}
