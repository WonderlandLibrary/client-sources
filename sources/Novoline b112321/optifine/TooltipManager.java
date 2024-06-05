package optifine;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiVideoSettings;
import net.minecraft.client.settings.GameSettings$Options;
import optifine.IOptionControl;
import optifine.Lang;
import optifine.MatchBlock;

public class TooltipManager {
   private GuiScreen guiScreen = null;
   private int lastMouseX = 0;
   private int lastMouseY = 0;
   private long mouseStillTime = 0L;

   public TooltipManager(GuiScreen var1) {
      this.guiScreen = var1;
   }

   public void a(int var1, int var2, List var3) {
      PacketRemapper[] var4 = MatchBlock.b();
      if(Math.abs(var1 - this.lastMouseX) <= 5 && Math.abs(var2 - this.lastMouseY) <= 5) {
         short var5 = 700;
         if(System.currentTimeMillis() >= this.mouseStillTime + (long)var5) {
            int var6 = this.guiScreen.width / 2 - 150;
            int var7 = this.guiScreen.height / 6 - 7;
            if(var2 <= var7 + 98) {
               var7 += 105;
            }

            int var8 = var6 + 150 + 150;
            int var9 = var7 + 84 + 10;
            GuiButton var10 = this.getSelectedButton(var1, var2, var3);
            if(var10 instanceof IOptionControl) {
               IOptionControl var11 = (IOptionControl)var10;
               GameSettings$Options var12 = var11.getOption();
               String[] var13 = getTooltipLines(var12);
               return;
            }
         }
      }

      this.lastMouseX = var1;
      this.lastMouseY = var2;
      this.mouseStillTime = System.currentTimeMillis();
   }

   private GuiButton getSelectedButton(int var1, int var2, List var3) {
      MatchBlock.b();
      byte var5 = 0;
      if(var5 >= var3.size()) {
         return null;
      } else {
         GuiButton var6 = (GuiButton)var3.get(var5);
         int var7 = GuiVideoSettings.getButtonWidth(var6);
         int var8 = GuiVideoSettings.getButtonHeight(var6);
         if((double)var1 >= var6.m && (double)var2 >= var6.h && (double)var1 < var6.m + (double)var7 && (double)var2 < var6.h + (double)var8) {
            boolean var10 = true;
         } else {
            boolean var10000 = false;
         }

         return var6;
      }
   }

   private static String[] getTooltipLines(GameSettings$Options var0) {
      return getTooltipLines(var0.getEnumString());
   }

   private static String[] getTooltipLines(String var0) {
      MatchBlock.b();
      ArrayList var2 = new ArrayList();
      int var3 = 0;
      if(var3 < 10) {
         String var4 = var0 + ".tooltip." + (var3 + 1);
         String var5 = Lang.get(var4, (String)null);
         var2.add(var5);
         ++var3;
      }

      if(var2.size() <= 0) {
         return null;
      } else {
         String[] var7 = (String[])((String[])((String[])var2.toArray(new String[var2.size()])));
         return var7;
      }
   }
}
