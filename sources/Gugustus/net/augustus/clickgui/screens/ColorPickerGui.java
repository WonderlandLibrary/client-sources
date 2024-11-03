package net.augustus.clickgui.screens;

import java.awt.Color;
import java.io.IOException;
import net.augustus.Augustus;
import net.augustus.events.EventClickGui;
import net.augustus.material.themes.Dark;
import net.augustus.settings.ColorSetting;
import net.augustus.settings.Setting;
import net.augustus.utils.ColorPicker;
import net.augustus.utils.interfaces.MM;
import net.augustus.utils.interfaces.SM;
import net.lenni0451.eventapi.manager.EventManager;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;

public class ColorPickerGui extends GuiScreen implements MM, SM {
   private final Setting setting;
   private final ColorPicker colorPicker;

   public ColorPickerGui(ColorSetting setting) {
      this.setting = setting;
      this.colorPicker = new ColorPicker(70.0, setting.getColor(), setting::setColor);
   }

   @Override
   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      super.drawScreen(mouseX, mouseY, partialTicks);
      ScaledResolution sr = new ScaledResolution(this.mc);
      GL11.glPushMatrix();
      GL11.glScaled(1.5, 1.5, 1.0);
      this.drawCenteredString(
         this.fontRendererObj,
         "Select color:",
         (int)((double)sr.getScaledWidth() / 1.5 / 2.0),
         (int)((double)sr.getScaledHeight() / 1.5 / 2.0 - 58.666666666666664),
         new Color(229, 231, 235).getRGB()
      );
      GL11.glScaled(1.0, 1.0, 1.0);
      GL11.glPopMatrix();
      this.colorPicker.draw(mouseX, mouseY);
   }

   @Override
   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
      this.colorPicker.click((double)mouseX, (double)mouseY, mouseButton);
      super.mouseClicked(mouseX, mouseY, mouseButton);
   }

   @Override
   protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
      this.colorPicker.click((double)mouseX, (double)mouseY, clickedMouseButton);
      super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
   }

   @Override
   protected void keyTyped(char typedChar, int keyCode) throws IOException {
      if (keyCode == 1) {
         if(mm.clickGUI.mode.getSelected().equalsIgnoreCase("Default")) {
            mc.displayGuiScreen(Augustus.getInstance().getClickGui());
         } else if(mm.clickGUI.mode.getSelected().equalsIgnoreCase("Clean")) {
            //mc.displayGuiScreen(Augustus.getInstance().getPlaneGui());
         } else if(mm.clickGUI.mode.getSelected().equalsIgnoreCase("New")) {
            EventManager.call(new EventClickGui());
            mc.displayGuiScreen(new Dark());
         } else {
            mc.displayGuiScreen(Augustus.getInstance().getClickGui());
         }
      }
   }

   @Override
   public void onGuiClosed() {
   }
}
