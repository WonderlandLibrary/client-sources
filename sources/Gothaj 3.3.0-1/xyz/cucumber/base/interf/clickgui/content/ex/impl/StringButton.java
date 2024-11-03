package xyz.cucumber.base.interf.clickgui.content.ex.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.opengl.GL11;
import xyz.cucumber.base.module.settings.StringSettings;
import xyz.cucumber.base.utils.RenderUtils;
import xyz.cucumber.base.utils.position.PositionUtils;
import xyz.cucumber.base.utils.render.Fonts;
import xyz.cucumber.base.utils.render.StencilUtils;

public class StringButton extends SettingsButton {
   private StringSettings setting;
   private GuiTextField textField;
   private double animation;

   public StringButton(StringSettings setting, PositionUtils pos) {
      this.settingMain = setting;
      this.setting = setting;
      this.position = pos;
      this.textField = new GuiTextField(0, Minecraft.getMinecraft().fontRendererObj, 0, 0, 100, 12);
      this.textField.setText(setting.getString());
   }

   @Override
   public void draw(int mouseX, int mouseY) {
      Fonts.getFont("rb-r").drawString(this.setting.getName(), this.position.getX() + 8.0, this.position.getY() + 3.0, -1);
      this.textField.xPosition = (int)(this.position.getX2() - 5.0 - (double)this.textField.width);
      this.textField.yPosition = (int)(this.position.getY() + 1.0);
      RenderUtils.drawRoundedRect(
         (double)this.textField.xPosition,
         (double)this.textField.yPosition,
         (double)(this.textField.xPosition + this.textField.width),
         (double)(this.textField.yPosition + this.textField.height),
         -15132391,
         1.0F
      );
      if (this.textField.isFocused()) {
         this.animation = (this.animation * 9.0 + (double)(this.textField.width / 2) - 3.0) / 10.0;
      } else {
         this.animation = this.animation * 9.0 / 10.0;
      }

      RenderUtils.drawLine(
         (double)(this.textField.xPosition + this.textField.width / 2) - this.animation,
         (double)(this.textField.yPosition + this.textField.height - 1),
         (double)(this.textField.xPosition + this.textField.width / 2) + this.animation,
         (double)(this.textField.yPosition + this.textField.height - 1),
         -12424715,
         1.0F
      );
      String render = this.textField.getText();
      if (!this.textField.getText().equals("") && this.textField.getText() != null) {
         this.setting.setString(render);
      } else {
         render = "Add your text";
      }

      StencilUtils.initStencil();
      GL11.glEnable(2960);
      StencilUtils.bindWriteStencilBuffer();
      RenderUtils.drawRoundedRect(
         (double)this.textField.xPosition,
         (double)this.textField.yPosition,
         (double)(this.textField.xPosition + this.textField.width),
         (double)(this.textField.yPosition + this.textField.height),
         -15132391,
         1.0F
      );
      StencilUtils.bindReadStencilBuffer(1);
      Fonts.getFont("rb-r")
         .drawString(
            render,
            (double)(this.textField.xPosition + this.textField.width / 2) - Fonts.getFont("rb-r").getWidth(render) / 2.0,
            (double)((float)(this.textField.yPosition + this.textField.height / 2) - Fonts.getFont("rb-r").getHeight(render) / 2.0F),
            -5592406
         );
      StencilUtils.uninitStencilBuffer();
   }

   @Override
   public void click(int mouseX, int mouseY, int button) {
      this.textField.mouseClicked(mouseX, mouseY, button);
   }

   @Override
   public void key(char character, int key) {
      if (character == '\t' && !this.textField.isFocused()) {
         this.textField.setFocused(true);
      }

      this.textField.textboxKeyTyped(character, key);
   }

   public StringSettings getSetting() {
      return this.setting;
   }

   public void setSetting(StringSettings setting) {
      this.setting = setting;
   }
}
