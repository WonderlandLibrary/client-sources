package src.Wiksi.ui.dropdown.components.settings;

import com.mojang.blaze3d.matrix.MatrixStack;
import src.Wiksi.functions.settings.impl.BooleanSetting;
import src.Wiksi.functions.settings.impl.ModeListSetting;
import src.Wiksi.ui.dropdown.impl.Component;
import src.Wiksi.utils.math.MathUtil;
import src.Wiksi.utils.render.ColorUtils;
import src.Wiksi.utils.render.Cursors;
import src.Wiksi.utils.render.DisplayUtils;
import src.Wiksi.utils.render.font.Fonts;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public class MultiBoxComponent extends Component {
   final ModeListSetting setting;
   float width = 0.0F;
   float heightPadding = 0.0F;
   float spacing = 2.0F;

   public MultiBoxComponent(ModeListSetting setting) {
      this.setting = setting;
      this.setHeight(22.0F);
   }

   public void render(MatrixStack stack, float mouseX, float mouseY) {
      super.render(stack, mouseX, mouseY);
      DisplayUtils.drawShadow(this.getX() + 5.0F, this.getY() + 9.0F, this.width + 5.0F, 10.0F + this.heightPadding, 10, ColorUtils.rgba(25, 26, 40, 45));
      DisplayUtils.drawRoundedRect(this.getX() + 5.0F, this.getY() + 9.0F, this.width + 5.0F, 10.0F + this.heightPadding, 2.0F, ColorUtils.rgba(25, 26, 40, 45));
      float offset = 0.0F;
      float heightoff = 0.0F;
      boolean plused = false;
      boolean anyHovered = false;

      float textWidth;
      for(Iterator var8 = ((List)this.setting.get()).iterator(); var8.hasNext(); offset += textWidth + this.spacing) {
         BooleanSetting text = (BooleanSetting)var8.next();
         textWidth = Fonts.montserrat.getWidth(text.getName(), 5.5F, 0.05F) + 2.0F;
         float textHeight = Fonts.montserrat.getHeight(5.5F) + 1.0F;
         if (offset + textWidth + this.spacing >= this.getWidth() - 10.0F) {
            offset = 0.0F;
            heightoff += textHeight + this.spacing;
            plused = true;
         }

         if (MathUtil.isHovered(mouseX, mouseY, this.getX() + 8.0F + offset, this.getY() + 11.5F + heightoff, textWidth, textHeight)) {
            anyHovered = true;
         }

         if ((Boolean)text.get()) {
            Fonts.montserrat.drawText(stack, text.getName(), this.getX() + 8.0F + offset, this.getY() + 11.5F + heightoff, ColorUtils.rgba(255, 255, 255, 255), 5.5F, 0.07F);
            DisplayUtils.drawRoundedRect(this.getX() + 7.0F + offset, this.getY() + 10.5F + heightoff, textWidth + 0.8F, textHeight + 0.8F, 1.5F, ColorUtils.rgba(129, 135, 255, 165));
         } else {
            Fonts.montserrat.drawText(stack, text.getName(), this.getX() + 8.0F + offset, this.getY() + 11.5F + heightoff, ColorUtils.rgba(255, 255, 255, 255), 5.5F, 0.05F);
            DisplayUtils.drawRoundedRect(this.getX() + 7.0F + offset, this.getY() + 10.5F + heightoff, textWidth + 0.8F, textHeight + 0.8F, 1.5F, ColorUtils.rgba(25, 26, 40, 165));
         }
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
      this.heightPadding = heightoff;
   }

   public void mouseClick(float mouseX, float mouseY, int mouse) {
      float offset = 0.0F;
      float heightoff = 0.0F;

      float textWidth;
      for(Iterator var6 = ((List)this.setting.get()).iterator(); var6.hasNext(); offset += textWidth + this.spacing) {
         BooleanSetting text = (BooleanSetting)var6.next();
         textWidth = Fonts.montserrat.getWidth(text.getName(), 5.5F, 0.05F) + 2.0F;
         float textHeight = Fonts.montserrat.getHeight(5.5F) + 1.0F;
         if (offset + textWidth + this.spacing >= this.getWidth() - 10.0F) {
            offset = 0.0F;
            heightoff += textHeight + this.spacing;
         }

         if (MathUtil.isHovered(mouseX, mouseY, this.getX() + 8.0F + offset, this.getY() + 11.5F + heightoff, textWidth, textHeight)) {
            text.set(!(Boolean)text.get());
         }
      }

      super.mouseClick(mouseX, mouseY, mouse);
   }

   public boolean isVisible() {
      return (Boolean)this.setting.visible.get();
   }
}
