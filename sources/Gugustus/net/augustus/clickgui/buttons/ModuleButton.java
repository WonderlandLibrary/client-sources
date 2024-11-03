package net.augustus.clickgui.buttons;

import java.awt.Color;
import net.augustus.Augustus;
import net.augustus.clickgui.screens.KeySettingGui;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.ui.augustusmanager.AugustusSounds;
import net.augustus.utils.interfaces.MC;
import net.augustus.utils.sound.SoundUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Keyboard;

public class ModuleButton extends GuiButton implements MC {
   private final Color colorToggledFalse = new Color(139, 141, 145, 255);
   private final Color colorToggledTrue = new Color(67, 122, 163, 255);
   private final Color color;
   private Module module;
   private boolean visible;
   private Categorys parent;
   private boolean visibleSetting = false;
   private boolean displayKey;
   private int mouseWheelDelata = 0;

   public ModuleButton(int id, int x, int y, int width, int height, String message, Color color, Categorys parent, Module module) {
      super(id, x, y, width, height, message);
      this.color = color;
      this.module = module;
      this.parent = parent;
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

   public void click1(double mouseX, double mouseY, int button) {
      if (this.visible && this.enabled) {
         if (button == 0) {
            boolean bl = this.mousePressed(mc, (int)mouseX, (int)mouseY);
            if (bl) {
               if (!Augustus.getInstance().getModuleManager().arrayList.toggleSound.getBoolean()) {
                  if (AugustusSounds.currentSound.equals("Sigma")) {
                     if (this.module.isToggled()) {
                        SoundUtil.play(SoundUtil.toggleOffSound);
                     } else {
                        SoundUtil.play(SoundUtil.toggleOnSound);
                     }
                  } else {
                     this.playPressSound(mc.getSoundHandler());
                  }
               }

               this.module.toggle();
            }
         } else if (button == 1) {
            boolean bl = this.mousePressed(mc, (int)mouseX, (int)mouseY);
            if (bl) {
               this.playPressSound(mc.getSoundHandler());
               this.toggleSetting();
            }
         } else if (button == 2) {
            boolean bl = this.mousePressed(mc, (int)mouseX, (int)mouseY);
            if (bl) {
               this.playPressSound(mc.getSoundHandler());
               mc.displayGuiScreen(new KeySettingGui(this.module));
            }
         }
      }
   }

   public void toggleSetting() {
      this.visibleSetting = !this.visibleSetting;
   }

   @Override
   public void drawButton(Minecraft mc, int mouseX, int mouseY) {
      this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
      int textColor;
      if (this.module.isToggled()) {
         textColor = this.getHoverColor(this.colorToggledTrue, 0.2);
      } else {
         textColor = this.getHoverColor(this.colorToggledFalse, -0.2);
      }

      FontRenderer fontrenderer = mc.fontRendererObj;
      GlStateManager.color(1.0F, 1.0F, 1.0F);
      GlStateManager.enableBlend();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.blendFunc(770, 771);
      drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, this.color.getRGB());
      if (!Keyboard.isKeyDown(42) || Keyboard.getKeyName(this.module.getKey()).equals("NONE")) {
         this.drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, textColor);
      } else if (fontrenderer.getStringWidth(this.displayString + " §8(" + Keyboard.getKeyName(this.module.getKey()) + ")") > this.width - 2) {
         this.drawCenteredString(
            fontrenderer,
            " §8(" + Keyboard.getKeyName(this.module.getKey()) + ")",
            this.xPosition + this.width / 2,
            this.yPosition + (this.height - 8) / 2,
            textColor
         );
      } else {
         this.drawCenteredString(
            fontrenderer,
            this.displayString + " §8(" + Keyboard.getKeyName(this.module.getKey()) + ")",
            this.xPosition + this.width / 2,
            this.yPosition + (this.height - 8) / 2,
            textColor
         );
      }
   }

   public boolean isVisible() {
      return this.visible;
   }

   public void setVisible(boolean visible) {
      this.visible = visible;
   }

   public Module getModule() {
      return this.module;
   }

   public void setModule(Module module) {
      this.module = module;
   }

   public Categorys getParent() {
      return this.parent;
   }

   public void setParent(Categorys parent) {
      this.parent = parent;
   }

   public boolean hasVisibleSetting() {
      return this.visibleSetting;
   }

   public void setVisibleSetting(boolean visibleSetting) {
      this.visibleSetting = visibleSetting;
   }

   public int getMouseWheelDelata() {
      return this.mouseWheelDelata;
   }

   public void setMouseWheelDelata(int mouseWheelDelata) {
      this.mouseWheelDelata = mouseWheelDelata;
   }

   public void onClosed() {
   }

   public void onKey(int keyCode) {
   }
}
