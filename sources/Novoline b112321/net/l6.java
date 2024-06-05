package net;

import cc.novoline.Novoline;
import cc.novoline.commands.impl.ConfigCommand;
import cc.novoline.gui.screen.dropdown.config.ConfigTab;
import cc.novoline.modules.visual.HUD;
import cc.novoline.utils.OpenGLUtil;
import cc.novoline.utils.Timer;
import cc.novoline.utils.fonts.impl.Fonts$SF$SF_18;
import java.awt.Color;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.MathHelper;

public class l6 {
   public int g;
   public static final Color d = new Color(255, 255, 255, 255);
   public static final Color c = new Color(0, 0, 0, 130);
   public static final Color h = new Color(0, 0, 0, 120);
   private final String b;
   private final ConfigTab f;
   private float i = 0.0F;
   private Timer a = new Timer();
   private static String e;

   public l6(String var1, ConfigTab var2) {
      this.b = var1;
      this.f = var2;
   }

   public void b(int var1, int var2) {
      d();
      this.g = (int)(this.f.getPosY() + 15.0F);
      boolean var4 = this.f.d() == this;
      HUD var5 = (HUD)Novoline.getInstance().getModuleManager().getModule(HUD.class);
      Iterator var6 = this.f.getConfigs().iterator();
      if(var6.hasNext()) {
         l6 var7 = (l6)var6.next();
         if(var7 == this) {
            ;
         }

         this.g += var7.a();
      }

      if(var4 && this.i < 1.0F) {
         this.i = (float)((double)this.i + 0.0025D * (double)(2000 / Minecraft.getInstance().getDebugFPS()));
      }

      if(!var4 && this.i > 0.0F) {
         this.i = (float)((double)this.i - 0.0025D * (double)(2000 / Minecraft.getInstance().getDebugFPS()));
      }

      this.i = MathHelper.clamp_float(this.i, 0.0F, 1.0F);
      Gui.drawRect((double)this.f.getPosX(), (double)this.g, (double)(this.f.getPosX() + 100.0F), (double)(this.g + this.a()), (new Color(40, 40, 40, 255)).getRGB());
      Fonts$SF$SF_18.SF_18.drawCenteredString(this.b, this.f.getPosX() + 50.0F, (float)(this.g + 4), OpenGLUtil.interpolateColor(d, new Color(var5.getColor().getRed(), var5.getColor().getGreen(), var5.getColor().getBlue(), 250), this.i), true);
   }

   public void a(char var1, int var2) {
   }

   public void a(int var1, int var2, int var3) {
      String var4 = d();
      if(this.a(var1, var2) && var3 == 0) {
         this.f.a(this);
         if(!this.a.check(200.0F)) {
            ConfigCommand.loadConfig(Novoline.getInstance().getModuleManager().getConfigManager(), this.b());
         }

         this.a.reset();
      }

   }

   public boolean a(int var1, int var2) {
      this.g = (int)(this.f.getPosY() + 15.0F);
      d();
      Iterator var4 = this.f.getConfigs().iterator();
      if(var4.hasNext()) {
         l6 var5 = (l6)var4.next();
         if(var5 == this) {
            ;
         }

         this.g += var5.a();
      }

      return (float)var1 >= this.f.getPosX() && var2 >= this.g && (float)var1 <= this.f.getPosX() + 101.0F && var2 <= this.g + this.a();
   }

   public int a() {
      return 14;
   }

   public String b() {
      return this.b;
   }

   public ConfigTab c() {
      return this.f;
   }

   static {
      b("R9Ilec");
   }

   public static void b(String var0) {
      e = var0;
   }

   public static String d() {
      return e;
   }
}
