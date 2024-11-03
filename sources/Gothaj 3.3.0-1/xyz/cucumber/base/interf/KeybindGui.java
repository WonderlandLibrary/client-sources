package xyz.cucumber.base.interf;

import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import xyz.cucumber.base.Client;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.utils.RenderUtils;
import xyz.cucumber.base.utils.render.Fonts;

public class KeybindGui extends GuiScreen {
   private Mod mod;

   public KeybindGui(Mod mod) {
      this.mod = mod;
   }

   @Override
   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      RenderUtils.drawRect(0.0, 0.0, (double)this.width, (double)this.height, -1879048192);
      Fonts.getFont("rb-m-13")
         .drawString(
            this.mod.getName(),
            (double)(this.width / 2) - Fonts.getFont("rb-m-13").getWidth(this.mod.getName()) / 2.0,
            (double)(this.height / 2 - 10),
            -12424715
         );
      Fonts.getFont("rb-r")
         .drawString(
            "Press any key on keyboard (ESCAPE FOR UNBIND)",
            (double)(this.width / 2) - Fonts.getFont("rb-r").getWidth("Press any key on keyboard (ESCAPE FOR UNBIND)") / 2.0,
            (double)(this.height / 2),
            -1
         );
   }

   @Override
   protected void keyTyped(char typedChar, int keyCode) throws IOException {
      if (keyCode == 1) {
         this.mod.setKey(0);
         Client.INSTANCE.getCommandManager().sendChatMessage("Â§c" + this.mod.getName() + "Â§7 was unbound!");
      } else {
         this.mod.setKey(keyCode);
         Client.INSTANCE.getCommandManager().sendChatMessage("Â§a" + this.mod.getName() + "Â§7 was successfully bound to " + keyCode);
      }

      Minecraft.getMinecraft().thePlayer.closeScreen();
   }
}
