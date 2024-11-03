package net.augustus.clickgui.buttons;

import java.awt.Color;
import net.augustus.clickgui.screens.ColorPickerGui;
import net.augustus.events.EventClickSetting;
import net.augustus.modules.Module;
import net.augustus.settings.BooleanValue;
import net.augustus.settings.BooleansSetting;
import net.augustus.settings.ColorSetting;
import net.augustus.settings.DoubleValue;
import net.augustus.settings.Setting;
import net.augustus.settings.StringValue;
import net.augustus.utils.EventHandler;
import net.augustus.utils.TimeHelper;
import net.augustus.utils.interfaces.MC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Keyboard;

public class SettingsButton extends GuiButton implements MC {
   private Color color;
   private Module module;
   private Setting setting;
   private boolean visible;
   private boolean dragging;
   private boolean dropdownVisible = false;
   private String name;
   private int optionsCounter = 0;
   private final TimeHelper timeHelper = new TimeHelper();

   public SettingsButton(int id, int x, int y, int width, int height, String message, Color color, Module module, Setting setting) {
      super(id, x, y, width, height, message);
      this.color = color;
      this.module = module;
      this.setting = setting;
      this.name = message;
   }

   private int getHoverColor(Color color, double addBrightness) {
      int colorRGB;
      if (this.hovered) {
         float[] hsbColor = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
         float f = (float)((double)hsbColor[2] + addBrightness);
         if ((double)hsbColor[2] + addBrightness > 1.0) {
            f = 1.0F;
         } else if ((double)hsbColor[2] + addBrightness < 0.0) {
            f = 0.0F;
         }

         colorRGB = Color.HSBtoRGB(hsbColor[0], hsbColor[1], f);
      } else {
         colorRGB = color.getRGB();
      }

      return colorRGB;
   }

   protected int getHoverColor2(Color color, double x, double y, double width, double height, double mouseX, double mouseY, double addBrightness) {
      int colorRGB;
      if (this.mouseOver(mouseX, mouseY, x, y, width, height)) {
         float[] hsbColor = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
         float f = (float)((double)hsbColor[2] + addBrightness);
         if ((double)hsbColor[2] + addBrightness > 1.0) {
            f = 1.0F;
         } else if ((double)hsbColor[2] + addBrightness < 0.0) {
            f = 0.0F;
         }

         colorRGB = Color.HSBtoRGB(hsbColor[0], hsbColor[1], f);
      } else {
         colorRGB = color.getRGB();
      }

      return colorRGB;
   }

   public boolean mouseOver(double mouseX, double mouseY, double posX, double posY, double width, double height) {
      return mouseX >= posX && mouseX < posX + width && mouseY >= posY && mouseY < posY + height;
   }

   public void onKey(int key) {
      if (this.hovered && (key == 203 || key == 205)) {
         this.timeHelper.reset();
         this.arrow();
      }
   }

   public void tick() {
      if (this.hovered && this.timeHelper.reached(500L) && (Keyboard.isKeyDown(203) || Keyboard.isKeyDown(205))) {
         this.arrow();
      }
   }

   private void arrow() {
      double add = 0.001;
      if (this.setting instanceof DoubleValue) {
         DoubleValue doubleValue = (DoubleValue)this.setting;
         if (doubleValue.getDecimalPlaces() == 0) {
            add = 1.0;
         } else if (doubleValue.getDecimalPlaces() == 1) {
            add = 0.1;
         } else if (doubleValue.getDecimalPlaces() == 2) {
            add = 0.01;
         }

         double var1 = 0.0;
         if (Keyboard.isKeyDown(203)) {
            var1 = doubleValue.getValue() - add;
         } else if (Keyboard.isKeyDown(205)) {
            var1 = doubleValue.getValue() + add;
         }

         if (var1 <= doubleValue.getMaxValue() && var1 >= doubleValue.getMinValue() && (Keyboard.isKeyDown(203) || Keyboard.isKeyDown(205))) {
            doubleValue.setValue(var1);
         }
      }
   }

   public void click3(double mouseX, double mouseY, int button) {
      if (this.visible && (button == 0 || button == 1)) {
         boolean bl = this.mousePressed(mc, (int)mouseX, (int)mouseY);
         if (!bl) {
            if (this.setting instanceof StringValue && this.isDropdownVisible()) {
               StringValue stringValue = (StringValue)this.setting;
               int i = 1;

               for(String options : stringValue.getStringList()) {
                  double dropY = (double)(this.yPosition + this.height * i);
                  if (this.mouseOver(mouseX, mouseY, (double)this.xPosition, dropY, (double)this.width, (double)this.height)) {
                     stringValue.setString(options);
                     this.playPressSound(mc.getSoundHandler());
                  }

                  ++i;
               }
            } else if (this.setting instanceof BooleansSetting && this.isDropdownVisible()) {
               BooleansSetting booleansSetting = (BooleansSetting)this.setting;
               int i = 1;

               for(Setting s : booleansSetting.getSettingList()) {
                  double dropY = (double)(this.yPosition + this.height * i);
                  if (this.mouseOver(mouseX, mouseY, (double)this.xPosition, dropY, (double)this.width, (double)this.height)) {
                     if (s instanceof BooleanValue) {
                        BooleanValue booleanValue = (BooleanValue)s;
                        booleanValue.setBoolean(!booleanValue.getBoolean());
                     }

                     this.playPressSound(mc.getSoundHandler());
                  }

                  ++i;
               }
            }
         } else {
            if (this.setting instanceof BooleanValue) {
               BooleanValue booleanValue = (BooleanValue)this.setting;
               booleanValue.setBoolean(!booleanValue.getBoolean());
            } else if (this.setting instanceof DoubleValue) {
               this.dragging = true;
            } else if (this.setting instanceof StringValue || this.setting instanceof BooleansSetting) {
               this.dropdownVisible = !this.dropdownVisible;
            } else if (this.setting instanceof ColorSetting) {
               mc.displayGuiScreen(new ColorPickerGui((ColorSetting)this.setting));
            }

            this.playPressSound(mc.getSoundHandler());
         }

         EventClickSetting eventClickSetting = new EventClickSetting();
         EventHandler.call(eventClickSetting);
      }
   }

   public void mouseReleased() {
      this.dragging = false;
   }

   @Override
   public void drawButton(Minecraft mc, int mouseX, int mouseY) {
      if (!this.visible) {
         this.hovered = false;
      } else {
         this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
         FontRenderer fontrenderer = mc.fontRendererObj;
         GlStateManager.color(1.0F, 1.0F, 1.0F);
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         GlStateManager.blendFunc(770, 771);
         if (this.setting instanceof BooleanValue) {
            this.drawCheckBox(fontrenderer, mouseX, mouseY);
         } else if (this.setting instanceof DoubleValue) {
            this.drawSlider(fontrenderer, mouseX, mouseY);
         } else if (this.setting instanceof StringValue) {
            this.drawOptions(fontrenderer, mouseX, mouseY);
         } else if (this.setting instanceof BooleansSetting) {
            this.drawMultipleOptions(fontrenderer, mouseX, mouseY);
         } else if (this.setting instanceof ColorSetting) {
            this.drawColorButton(fontrenderer, mouseX, mouseY);
         }
      }
   }

   private void drawCheckBox(FontRenderer fontRenderer, int mouseX, int mouseY) {
      BooleanValue setting = (BooleanValue)this.setting;
      Color colorTrue = new Color(69, 118, 161);
      Color colorFalse = new Color(139, 141, 145);
      int colorRGB;
      if (setting.getBoolean()) {
         colorRGB = this.getHoverColor(colorTrue, 0.2);
      } else {
         colorRGB = this.getHoverColor(colorFalse, -0.2);
      }

      drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, this.color.getRGB());
      fontRenderer.drawStringWithShadow("x", (float)(this.xPosition + 5), (float)(this.yPosition + this.height / 2 - fontRenderer.FONT_HEIGHT / 2), colorRGB);
      fontRenderer.drawStringWithShadow(
         this.displayString,
         (float)(this.xPosition + 11 + fontRenderer.getStringWidth("x")),
         (float)(this.yPosition + this.height / 2 - fontRenderer.FONT_HEIGHT / 2),
         colorFalse.getRGB()
      );
   }

   private void drawSlider(FontRenderer fr, int mouseX, int mouseY) {
      DoubleValue setting = (DoubleValue)this.setting;
      double sliderWidth = (double)(this.width - 4);
      double sliderHeight = 10.0;
      double sliderX = (double)(this.xPosition + 2);
      double sliderY = (double)(this.yPosition + 2);
      double sliderRight = sliderX + sliderWidth;
      double sliderBottom = sliderY + sliderHeight;
      Color backgroundSliderColor = new Color(75, 75, 75, 60);
      Color sliderColor = new Color(40, 146, 224, 220);
      String currentValue;
      if (setting.getDecimalPlaces() == 0) {
         currentValue = String.valueOf((int)this.round(setting.getValue(), setting.getDecimalPlaces()));
      } else {
         currentValue = String.valueOf(this.round(setting.getValue(), setting.getDecimalPlaces()));
      }

      String text = "" + this.name + ": " + currentValue;
      setting.setValue(this.round(setting.getValue(), setting.getDecimalPlaces()));
      double percentageOfCurrentValue = (setting.getValue() - setting.getMinValue()) / (setting.getMaxValue() - setting.getMinValue());
      if (this.dragging) {
         double var1 = setting.getMinValue()
            + MathHelper.clamp_double(((double)mouseX - sliderX) / sliderWidth, 0.0, 1.0) * (setting.getMaxValue() - setting.getMinValue());
         setting.setValue(var1);
      }

      int backgroundSliderColorRGB = this.getHoverColor(backgroundSliderColor, 0.2F);
      int sliderColorRGB = this.getHoverColor(sliderColor, 0.12F);
      drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, this.color.getRGB());
      double value1 = sliderX + percentageOfCurrentValue * sliderWidth;
      drawRect((int)sliderX, (int)sliderY, (int)sliderRight, (int)sliderBottom, backgroundSliderColorRGB);
      drawRect((int)sliderX, (int)sliderY, (int)value1, (int)sliderBottom, sliderColorRGB);
      this.drawCenteredString(fr, text, (int)((double)((int)sliderX) + sliderWidth / 2.0), (int)(sliderY + 1.0), new Color(139, 141, 145).getRGB());
   }

   private void drawOptions(FontRenderer fr, int mouseX, int mouseY) {
      StringValue stringValue = (StringValue)this.setting;
      if (this.visible) {
         Color color = new Color(139, 141, 145);
         int colorRGB = this.getHoverColor(color, -0.2F);
         drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, this.color.getRGB());
         drawRect(
            this.xPosition + 1,
            this.yPosition + this.height - 3,
            this.xPosition + this.width - 2,
            this.yPosition + this.height - 1,
            new Color(72, 79, 89, 160).getRGB()
         );
         fr.drawStringWithShadow(
            this.name,
            (float)((int)((float)this.xPosition + (float)this.width / 2.0F - (float)fr.getStringWidth(this.name) / 2.0F)),
            (float)((int)((float)this.yPosition + (float)this.height / 2.0F - (float)fr.FONT_HEIGHT / 2.0F)),
            colorRGB
         );
         if (this.dropdownVisible) {
            int counter = 1;

            for(String options : stringValue.getStringList()) {
               double dropY = (double)(this.yPosition + counter * this.height);
               Color isCurrentString = new Color(69, 118, 161);
               Color isNotCurrentString = new Color(139, 141, 145);
               int stringColorRGB;
               if (options.equals(stringValue.getSelected())) {
                  stringColorRGB = this.getHoverColor2(
                     isCurrentString, (double)this.xPosition, dropY, (double)this.width, (double)this.height, (double)mouseX, (double)mouseY, 0.2F
                  );
               } else {
                  stringColorRGB = this.getHoverColor2(
                     isNotCurrentString, (double)this.xPosition, dropY, (double)this.width, (double)this.height, (double)mouseX, (double)mouseY, -0.2F
                  );
               }

               drawRect(this.xPosition, (int)dropY, this.xPosition + this.width, (int)(dropY + (double)this.height), this.color.getRGB());
               fr.drawStringWithShadow(
                  options,
                  (float)((int)((float)this.xPosition + (float)this.width / 2.0F - (float)fr.getStringWidth(options) / 2.0F)),
                  (float)((int)(dropY + (double)((float)this.height / 2.0F) - (double)((float)fr.FONT_HEIGHT / 2.0F))),
                  stringColorRGB
               );
               ++counter;
            }
         }
      }
   }

   private void drawMultipleOptions(FontRenderer fr, int mouseX, int mouseY) {
      BooleansSetting booleansSetting = (BooleansSetting)this.setting;
      if (this.visible) {
         Color color = new Color(139, 141, 145);
         int colorRGB = this.getHoverColor(color, -0.2F);
         drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, this.color.getRGB());
         drawRect(
            this.xPosition + 1,
            this.yPosition + this.height - 3,
            this.xPosition + this.width - 2,
            this.yPosition + this.height - 1,
            new Color(72, 79, 89, 160).getRGB()
         );
         fr.drawStringWithShadow(
            this.name,
            (float)((int)((float)this.xPosition + (float)this.width / 2.0F - (float)fr.getStringWidth(this.name) / 2.0F)),
            (float)((int)((float)this.yPosition + (float)this.height / 2.0F - (float)fr.FONT_HEIGHT / 2.0F)),
            colorRGB
         );
         if (this.dropdownVisible) {
            int counter = 1;

            for(Setting s : booleansSetting.getSettingList()) {
               if (s == null) {
                  break;
               }

               double dropY = (double)(this.yPosition + counter * this.height);
               Color isCurrentString = new Color(69, 118, 161);
               Color isNotCurrentString = new Color(139, 141, 145);
               if (s instanceof BooleanValue) {
                  int stringColorRGB;
                  if (((BooleanValue)s).getBoolean()) {
                     stringColorRGB = this.getHoverColor2(
                        isCurrentString, (double)this.xPosition, dropY, (double)this.width, (double)this.height, (double)mouseX, (double)mouseY, 0.2F
                     );
                  } else {
                     stringColorRGB = this.getHoverColor2(
                        isNotCurrentString, (double)this.xPosition, dropY, (double)this.width, (double)this.height, (double)mouseX, (double)mouseY, -0.2F
                     );
                  }

                  drawRect(this.xPosition, (int)dropY, this.xPosition + this.width, (int)(dropY + (double)this.height), this.color.getRGB());
                  fr.drawStringWithShadow(
                     s.getName(),
                     (float)((int)((float)this.xPosition + (float)this.width / 2.0F - (float)fr.getStringWidth(s.getName()) / 2.0F)),
                     (float)((int)(dropY + (double)((float)this.height / 2.0F) - (double)((float)fr.FONT_HEIGHT / 2.0F))),
                     stringColorRGB
                  );
                  ++counter;
               }
            }
         }
      }
   }

   private void drawColorButton(FontRenderer fr, int mouseX, int mouseY) {
      ColorSetting setting = (ColorSetting)this.setting;
      Color color = new Color(139, 141, 145);
      if (this.hovered) {
         color = setting.getColor();
      }

      drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, this.color.getRGB());
      fr.drawStringWithShadow(
         this.name,
         (float)((int)((float)this.xPosition + (float)this.width / 2.0F - (float)fr.getStringWidth(this.name) / 2.0F)),
         (float)((int)((float)this.yPosition + (float)this.height / 2.0F - (float)fr.FONT_HEIGHT / 2.0F) + 1),
         color.getRGB()
      );
   }

   private double round(double value, int decimalPoints) {
      if (decimalPoints == 0) {
         return (double)((int)value);
      } else {
         double d = Math.pow(10.0, (double)decimalPoints);
         return (double)Math.round(value * d) / d;
      }
   }

   public Module getModule() {
      return this.module;
   }

   public void setModule(Module module) {
      this.module = module;
   }

   public boolean isVisible() {
      return this.visible;
   }

   public void setVisible(boolean visible) {
      this.visible = visible;
   }

   public Color getColor() {
      return this.color;
   }

   public void setColor(Color color) {
      this.color = color;
   }

   public Setting getSetting() {
      return this.setting;
   }

   public void setSetting(Setting setting) {
      this.setting = setting;
   }

   public boolean isDragging() {
      return this.dragging;
   }

   public void setDragging(boolean dragging) {
      this.dragging = dragging;
   }

   public boolean isDropdownVisible() {
      return this.dropdownVisible;
   }

   public void setDropdownVisible(boolean dropdownVisible) {
      this.dropdownVisible = dropdownVisible;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public int getOptionsCounter() {
      return this.optionsCounter;
   }

   public void setOptionsCounter(int optionsCounter) {
      this.optionsCounter = optionsCounter;
   }
}
