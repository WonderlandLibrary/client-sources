package net;

import cc.novoline.Novoline;
import cc.novoline.gui.screen.dropdown.config.Config;
import cc.novoline.modules.visual.HUD;
import cc.novoline.utils.OpenGLUtil;
import cc.novoline.utils.fonts.impl.Fonts$SF$SF_19;
import java.awt.Color;
import java.util.Iterator;
import java.util.function.Consumer;
import net.K6;
import net.a1I;
import net.acE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.MathHelper;

public class ape extends Config {
   private final Consumer k;
   private float l = 0.0F;
   private String j;

   public ape(String var1, a1I var2, Consumer var3) {
      super((K6)null, var2);
      this.k = var3;
      this.j = var1;
   }

   public void b(int var1, int var2) {
      HUD var4 = (HUD)Novoline.getInstance().getModuleManager().getModule(HUD.class);
      this.y = (int)(this.b().getPosY() + 15.0F);
      Config.a();
      Iterator var5 = this.b().a().iterator();
      if(var5.hasNext()) {
         Config var6 = (Config)var5.next();
         if(var6 == this) {
            ;
         }

         this.y += var6.getYPerConfig();
      }

      if(this.isHovered(var1, var2) && this.l < 1.0F) {
         this.l = (float)((double)this.l + 0.0025D * (double)(2000 / Minecraft.getInstance().getDebugFPS()));
      }

      if(this.l > 0.0F) {
         this.l = (float)((double)this.l - 0.0025D * (double)(2000 / Minecraft.getInstance().getDebugFPS()));
      }

      this.l = MathHelper.clamp_float(this.l, 0.0F, 1.0F);
      Gui.drawRect((double)this.b().getPosX(), (double)this.y, (double)(this.b().getPosX() + 100.0F), (double)(this.y + this.getYPerConfig()), (new Color(40, 40, 40, 255)).getRGB());
      Fonts$SF$SF_19.SF_19.drawString(this.j, this.b().getPosX() + 2.0F, (float)(this.y + 4), OpenGLUtil.interpolateColor(new Color(255, 255, 255, 255), new Color(var4.getColor().getRed(), var4.getColor().getGreen(), var4.getColor().getBlue(), 250), this.l));
      if(acE.b() == null) {
         Config.b(false);
      }

   }

   public void a(int var1, int var2, int var3) {
      boolean var4 = Config.a();
      if(this.isHovered(var1, var2) && var3 == 0) {
         if(this.b().c() == null) {
            this.k.accept("");
         }

         this.k.accept(this.b().c().e());
      }

   }

   public Consumer a() {
      return this.k;
   }
}
