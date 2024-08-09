package src.Wiksi.ui.dropdown.components.settings;

import com.mojang.blaze3d.matrix.MatrixStack;
import src.Wiksi.functions.settings.impl.ModeSetting;
import src.Wiksi.ui.dropdown.impl.Component;
import src.Wiksi.utils.math.MathUtil;
import src.Wiksi.utils.render.ColorUtils;
import src.Wiksi.utils.render.Cursors;
import src.Wiksi.utils.render.DisplayUtils;
import src.Wiksi.utils.render.font.Fonts;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public class ModeComponent extends Component {
   final ModeSetting setting;
   float width = 0.0F;
   float heightplus = 0.0F;
   float spacing = 4.0F;

   public ModeComponent(ModeSetting setting) {
      this.setting = setting;
      this.setHeight(22.0F);
   }

   public void render(MatrixStack stack, float mouseX, float mouseY) {
      super.render(stack, mouseX, mouseY);
      Fonts.montserrat.drawText(stack, this.setting.getName(), this.getX() + 5.0F, this.getY() + 2.0F, ColorUtils.rgba(255, 255, 255, 255), 5.5F, 0.05F);
      DisplayUtils.drawShadow(this.getX() + 5.0F, this.getY() + 9.0F, this.width + 5.0F, 10.0F + this.heightplus, 10, ColorUtils.rgba(25, 26, 40, 45));
      DisplayUtils.drawRoundedRect(this.getX() + 5.0F, this.getY() + 9.0F, this.width + 5.0F, 10.0F + this.heightplus, 2.0F, ColorUtils.rgba(25, 26, 40, 45));
      float offset = 0.0F;
      float heightoff = 0.0F;
      boolean plused = false;
      boolean anyHovered = false;
      String[] var8 = this.setting.strings;
      int var9 = var8.length;

      for(int var10 = 0; var10 < var9; ++var10) {
         String text = var8[var10];
         float textWidth = Fonts.montserrat.getWidth(text, 5.5F, 0.05F) + 2.0F;
         float textHeight = Fonts.montserrat.getHeight(5.5F) + 1.0F;
         if (offset + textWidth + this.spacing >= this.getWidth() - 10.0F) {
            offset = 0.0F;
            heightoff += textHeight + this.spacing;
            plused = true;
         }

         if (MathUtil.isHovered(mouseX, mouseY, this.getX() + 8.0F + offset, this.getY() + 11.5F + heightoff, textWidth, textHeight)) {
            anyHovered = true;
         }

         if (text.equals(this.setting.get())) {
            Fonts.montserrat.drawText(stack, text, this.getX() + 8.0F + offset, this.getY() + 11.5F + heightoff, ColorUtils.rgb(114, 118, 134), 5.5F, 0.07F);
            Fonts.montserrat.drawText(stack, text, this.getX() + 8.0F + offset, this.getY() + 11.5F + heightoff, ColorUtils.rgba(255, 255, 255, 255), 5.5F, 0.07F);
            DisplayUtils.drawRoundedRect(this.getX() + 7.0F + offset, this.getY() + 10.5F + heightoff, textWidth + 0.8F, textHeight + 0.8F, 1.5F, ColorUtils.rgba(129, 135, 255, 165));
         } else {
            Fonts.montserrat.drawText(stack, text, this.getX() + 8.0F + offset, this.getY() + 11.5F + heightoff, ColorUtils.rgb(46, 47, 51), 5.5F, 0.05F);
            Fonts.montserrat.drawText(stack, text, this.getX() + 8.0F + offset, this.getY() + 11.5F + heightoff, ColorUtils.rgba(255, 255, 255, 255), 5.5F, 0.05F);
            DisplayUtils.drawRoundedRect(this.getX() + 7.0F + offset, this.getY() + 10.5F + heightoff, textWidth + 0.8F, textHeight + 0.8F, 1.5F, ColorUtils.rgba(25, 26, 40, 165));
         }

         offset += textWidth + this.spacing;
      }

      if (this.isHovered(mouseX, mouseY)) {
         if (anyHovered) {
            GLFW.glfwSetCursor(Minecraft.getInstance().getMainWindow().getHandle(), Cursors.HAND);
         } else {
            GLFW.glfwSetCursor(Minecraft.getInstance().getMainWindow().getHandle(), Cursors.ARROW);
         }
      }

      this.width = plused ? this.getWidth() - 15.0F : offset;
      this.setHeight(22.0F + heightoff);
      this.heightplus = heightoff;
   }

   public void mouseClick(float mouseX, float mouseY, int mouse) {
      float offset = 0.0F;
      float heightoff = 0.0F;
      String[] var6 = this.setting.strings;
      int var7 = var6.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         String text = var6[var8];
         float textWidth = Fonts.montserrat.getWidth(text, 5.5F, 0.05F) + 2.0F;
         float textHeight = Fonts.montserrat.getHeight(5.5F) + 1.0F;
         if (offset + textWidth + this.spacing >= this.getWidth() - 10.0F) {
            offset = 0.0F;
            heightoff += textHeight + this.spacing;
         }

         if (MathUtil.isHovered(mouseX, mouseY, this.getX() + 8.0F + offset, this.getY() + 11.5F + heightoff, textWidth, textHeight)) {
            this.setting.set(text);
         }

         offset += textWidth + this.spacing;
      }

      super.mouseClick(mouseX, mouseY, mouse);
   }

   public boolean isVisible() {
      return (Boolean)this.setting.visible.get();
   }
}
