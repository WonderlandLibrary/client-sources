package cc.novoline.gui.screen.dropdown.config;

import cc.novoline.gui.screen.dropdown.config.ConfigTab;
import cc.novoline.utils.Timer;
import cc.novoline.utils.fonts.impl.Fonts$SF$SF_17;
import java.awt.Color;
import java.util.Iterator;
import net.l6;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;

public class ConfigTextField extends l6 {
   private String value;
   private final Timer backspace = new Timer();

   public ConfigTextField(String var1, ConfigTab var2) {
      super(var1, var2);
   }

   public void b(int var1, int var2) {
      l6.d();
      String var4 = this.a();
      if(this.c().d() == this && Keyboard.isKeyDown(14) && this.backspace.delay(100.0D) && var4.length() >= 1) {
         this.setValue(var4.substring(0, var4.length() - 1));
         this.backspace.reset();
      }

      this.g = (int)(this.c().getPosY() + 15.0F);
      Iterator var5 = this.c().getConfigs().iterator();
      if(var5.hasNext()) {
         l6 var6 = (l6)var5.next();
         if(var6 == this) {
            ;
         }

         this.g += var6.a();
      }

      Gui.drawRect((double)this.c().getPosX(), (double)this.g, (double)(this.c().getPosX() + 100.0F), (double)(this.g + this.a()), (new Color(40, 40, 40, 255)).getRGB());
      Gui.drawRect((double)(this.c().getPosX() + 2.0F), (double)(this.g + 16), (double)(this.c().getPosX() + 101.0F - 6.0F), (double)this.g + 16.5D, (new Color(195, 195, 195, 220)).getRGB());
      Fonts$SF$SF_17.SF_17.drawString(this.b(), this.c().getPosX() + 2.0F, (float)this.g + 2.0F, (new Color(227, 227, 227, 255)).getRGB());
      if(Fonts$SF$SF_17.SF_17.stringWidth(var4) > 65) {
         Fonts$SF$SF_17.SF_17.drawString(Fonts$SF$SF_17.SF_17.trimStringToWidth(var4, 78, true), this.c().getPosX() + 2.0F, (float)(this.g + 10), -1);
      }

      Fonts$SF$SF_17.SF_17.drawString(var4, this.c().getPosX() + 2.0F, (float)(this.g + 10), -1);
   }

   public void a(int var1, int var2, int var3) {
      String var4 = l6.d();
      if(this.a(var1, var2)) {
         this.c().a(this);
      }

      if(this.c().d() == this) {
         this.c().a((l6)null);
      }

   }

   public void a(char var1, int var2) {
      String var3 = l6.d();
      if(this.c().d() == this) {
         if(var2 == 1 || var2 == 28) {
            this.c().a((l6)null);
         }

         if(var2 != 14 && var2 != 157 && var2 != 29 && var2 != 54 && var2 != 42 && var2 != 15 && var2 != 58 && var2 != 211 && var2 != 199 && var2 != 210 && var2 != 200 && var2 != 208 && var2 != 205 && var2 != 203 && var2 != 56 && var2 != 184 && var2 != 197 && var2 != 70 && var2 != 207 && var2 != 201 && var2 != 209 && var2 != 221 && var2 != 59 && var2 != 60 && var2 != 62 && var2 != 63 && var2 != 64 && var2 != 65 && var2 != 66 && var2 != 67 && var2 != 68 && var2 != 87 && var2 != 88) {
            this.setValue(this.a() + var1);
         }
      }

   }

   public String a() {
      String var1 = l6.d();
      return this.value == null?"":this.value;
   }

   public void setValue(String var1) {
      this.value = var1;
   }

   public int a() {
      return 19;
   }
}
