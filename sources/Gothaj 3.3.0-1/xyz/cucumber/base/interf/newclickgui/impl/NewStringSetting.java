package xyz.cucumber.base.interf.newclickgui.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import xyz.cucumber.base.module.settings.ModuleSettings;
import xyz.cucumber.base.module.settings.StringSettings;
import xyz.cucumber.base.utils.RenderUtils;
import xyz.cucumber.base.utils.render.Fonts;

public class NewStringSetting extends NewSetting {
   private GuiTextField field;

   public NewStringSetting(ModuleSettings setting) {
      this.setting = (StringSettings)setting;
      this.field = new GuiTextField(0, Minecraft.getMinecraft().fontRendererObj, 0, 0, 90, 10);
      this.field.setText(((StringSettings)setting).getString());
      this.position.setWidth(294.0);
      this.position.setHeight(14.0);
   }

   @Override
   public void draw(int mouseX, int mouseY) {
      this.field.xPosition = (int)this.position.getX2() - 102;
      this.field.yPosition = (int)this.position.getY() + 1;
      Fonts.getFont("rb-m").drawString(this.setting.getName(), this.position.getX() + 12.0, (double)(this.field.yPosition + 3), -1);
      RenderUtils.drawRoundedRectWithCorners(
         (double)this.field.xPosition,
         (double)this.field.yPosition,
         (double)(this.field.xPosition + this.field.width),
         (double)(this.field.yPosition + this.field.height),
         1610612736,
         2.0,
         true,
         true,
         true,
         true
      );
      ((StringSettings)this.setting).setString(this.field.getText());
      Fonts.getFont("rb-r-13").drawString(((StringSettings)this.setting).getString(), (double)this.field.xPosition, (double)(this.field.yPosition + 3), -1);
   }

   @Override
   public void onClick(int mouseX, int mouseY, int b) {
      this.field.mouseClicked(mouseX, mouseY, b);
   }

   @Override
   public void onKey(char chr, int key) {
      if (chr == '\t' && !this.field.isFocused()) {
         this.field.setFocused(true);
      }

      this.field.textboxKeyTyped(chr, key);
   }
}
