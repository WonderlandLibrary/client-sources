package cc.novoline.gui.screen.dropdown.config;

import cc.novoline.Novoline;
import cc.novoline.gui.screen.dropdown.config.ConfigTab;
import cc.novoline.modules.visual.HUD;
import cc.novoline.utils.OpenGLUtil;
import cc.novoline.utils.fonts.impl.Fonts$SF$SF_19;
import java.awt.Color;
import java.util.Iterator;
import java.util.function.Consumer;
import net.acE;
import net.l6;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.MathHelper;

public class ConfigButton extends l6 {
   private final Consumer actionPerformed;
   private float fraction = 0.0F;

   public ConfigButton(String var1, ConfigTab var2, Consumer var3) {
      super(var1, var2);
      this.actionPerformed = var3;
   }

   public void b(int var1, int var2) {
      HUD var4 = (HUD)Novoline.getInstance().getModuleManager().getModule(HUD.class);
      l6.d();
      this.g = (int)(this.c().getPosY() + 15.0F);
      Iterator var5 = this.c().getConfigs().iterator();
      if(var5.hasNext()) {
         l6 var6 = (l6)var5.next();
         if(var6 == this) {
            ;
         }

         this.g += var6.a();
      }

      if(this.a(var1, var2) && this.fraction < 1.0F) {
         this.fraction = (float)((double)this.fraction + 0.0025D * (double)(2000 / Minecraft.getInstance().getDebugFPS()));
      }

      if(this.fraction > 0.0F) {
         this.fraction = (float)((double)this.fraction - 0.0025D * (double)(2000 / Minecraft.getInstance().getDebugFPS()));
      }

      this.fraction = MathHelper.clamp_float(this.fraction, 0.0F, 1.0F);
      Gui.drawRect((double)this.c().getPosX(), (double)this.g, (double)(this.c().getPosX() + 100.0F), (double)(this.g + this.a()), (new Color(40, 40, 40, 255)).getRGB());
      Fonts$SF$SF_19.SF_19.drawString(this.b(), this.c().getPosX() + 2.0F, (float)(this.g + 4), OpenGLUtil.interpolateColor(new Color(255, 255, 255, 255), new Color(var4.getColor().getRed(), var4.getColor().getGreen(), var4.getColor().getBlue(), 250), this.fraction));
      if(acE.b() == null) {
         l6.b("orJJec");
      }

   }

   public void a(int var1, int var2, int var3) {
      String var4 = l6.d();
      if(this.a(var1, var2) && var3 == 0) {
         if(this.c().d() == null) {
            this.actionPerformed.accept("");
         }

         this.actionPerformed.accept(this.c().d().b());
      }

   }

   public Consumer getActionPerformed() {
      return this.actionPerformed;
   }
}
