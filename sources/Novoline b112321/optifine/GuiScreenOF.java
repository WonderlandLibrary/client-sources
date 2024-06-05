package optifine;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import java.io.IOException;
import java.util.List;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiVideoSettings;
import optifine.MatchBlock;

public class GuiScreenOF extends GuiScreen {
   protected void actionPerformedRightClick(GuiButton var1) throws IOException {
   }

   protected void mouseClicked(int var1, int var2, int var3) throws IOException {
      PacketRemapper[] var4 = MatchBlock.b();
      super.mouseClicked(var1, var2, var3);
      if(var3 == 1) {
         GuiButton var5 = getSelectedButton(this.buttonList, var1, var2);
         if(var5 != null && var5.enabled) {
            var5.playPressSound(this.mc.getSoundHandler());
            this.actionPerformedRightClick(var5);
         }
      }

   }

   public static GuiButton getSelectedButton(List var0, int var1, int var2) {
      MatchBlock.b();
      int var4 = 0;
      if(var4 < var0.size()) {
         GuiButton var5 = (GuiButton)var0.get(var4);
         if(var5.visible) {
            int var6 = GuiVideoSettings.getButtonWidth(var5);
            int var7 = GuiVideoSettings.getButtonHeight(var5);
            if((double)var1 >= var5.m && (double)var2 >= var5.h && (double)var1 < var5.m + (double)var6 && (double)var2 < var5.h + (double)var7) {
               return var5;
            }
         }

         ++var4;
      }

      return null;
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
