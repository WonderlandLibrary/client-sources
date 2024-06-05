package net.minecraft.client.gui;

import java.io.IOException;
import java.net.URI;
import net.af_;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GuiScreenDemo extends GuiScreen {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final ResourceLocation field_146348_f = new ResourceLocation("textures/gui/demo_background.png");

   public void initGui() {
      this.buttonList.clear();
      boolean var1 = true;
      this.buttonList.add(new GuiButton(1, this.width / 2 - 116, this.height / 2 + 62 + -16, 114, 20, I18n.format("demo.help.buy", new Object[0])));
      this.buttonList.add(new GuiButton(2, this.width / 2 + 2, this.height / 2 + 62 + -16, 114, 20, I18n.format("demo.help.later", new Object[0])));
   }

   protected void actionPerformed(GuiButton var1) throws IOException {
      switch(var1.id) {
      case 1:
         var1.enabled = false;
         String var10000 = "java.awt.Desktop";

         try {
            Class var2 = af_.a(var10000);
            Object var3 = af_.b(var2, "getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
            af_.b(var2, "browse", new Class[]{URI.class}).invoke(var3, new Object[]{new URI("http://www.minecraft.net/store?source=demo")});
         } catch (Throwable var4) {
            LOGGER.error("Couldn\'t open link", var4);
         }
         break;
      case 2:
         this.mc.displayGuiScreen((GuiScreen)null);
         this.mc.setIngameFocus();
      }

   }

   public void updateScreen() {
      super.updateScreen();
   }

   public void drawDefaultBackground() {
      super.drawDefaultBackground();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      this.mc.getTextureManager().bindTexture(field_146348_f);
      int var1 = (this.width - 248) / 2;
      int var2 = (this.height - 166) / 2;
      this.drawTexturedModalRect(var1, var2, 0, 0, 248, 166);
   }

   public void drawScreen(int var1, int var2, float var3) {
      this.drawDefaultBackground();
      int var4 = (this.width - 248) / 2 + 10;
      int var5 = (this.height - 166) / 2 + 8;
      this.fontRendererObj.drawString(I18n.format("demo.help.title", new Object[0]), (float)var4, (float)var5, 2039583);
      var5 = var5 + 12;
      GameSettings var6 = this.mc.gameSettings;
      this.fontRendererObj.drawString(I18n.format("demo.help.movementShort", new Object[]{GameSettings.getKeyDisplayString(var6.keyBindForward.getKeyCode()), GameSettings.getKeyDisplayString(var6.keyBindLeft.getKeyCode()), GameSettings.getKeyDisplayString(var6.keyBindBack.getKeyCode()), GameSettings.getKeyDisplayString(var6.keyBindRight.getKeyCode())}), (float)var4, (float)var5, 5197647);
      this.fontRendererObj.drawString(I18n.format("demo.help.movementMouse", new Object[0]), (float)var4, (float)(var5 + 12), 5197647);
      this.fontRendererObj.drawString(I18n.format("demo.help.jump", new Object[]{GameSettings.getKeyDisplayString(var6.keyBindJump.getKeyCode())}), (float)var4, (float)(var5 + 24), 5197647);
      this.fontRendererObj.drawString(I18n.format("demo.help.inventory", new Object[]{GameSettings.getKeyDisplayString(var6.keyBindInventory.getKeyCode())}), (float)var4, (float)(var5 + 36), 5197647);
      this.fontRendererObj.drawSplitString(I18n.format("demo.help.fullWrapped", new Object[0]), var4, var5 + 68, 218, 2039583);
      super.drawScreen(var1, var2, var3);
   }
}
