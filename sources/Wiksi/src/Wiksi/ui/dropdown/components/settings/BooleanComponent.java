package src.Wiksi.ui.dropdown.components.settings;

import com.mojang.blaze3d.matrix.MatrixStack;
import src.Wiksi.functions.settings.impl.BooleanSetting;
import src.Wiksi.ui.dropdown.impl.Component;
import src.Wiksi.utils.math.MathUtil;
import src.Wiksi.utils.render.ColorUtils;
import src.Wiksi.utils.render.Cursors;
import src.Wiksi.utils.render.DisplayUtils;
import src.Wiksi.utils.render.font.Fonts;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;
import ru.hogoshi.Animation;
import ru.hogoshi.util.Easings;

public class BooleanComponent extends Component {
   private final BooleanSetting setting;
   private Animation animation = new Animation();
   private float width;
   private float height;
   private boolean hovered = false;

   public BooleanComponent(BooleanSetting setting) {
      this.setting = setting;
      this.setHeight(16.0F);
      this.animation = this.animation.animate((Boolean)setting.get() ? 1.0D : 0.0D, 0.2D, Easings.CIRC_OUT);
   }

   public void render(MatrixStack stack, float mouseX, float mouseY) {
      super.render(stack, mouseX, mouseY);
      this.animation.update();
      Fonts.montserrat.drawText(stack, this.setting.getName(), this.getX() + 5.0F, this.getY() + 3.25F + 1.0F, ColorUtils.rgb(255, 255, 255), 6.5F, 0.05F);
      this.width = 15.0F;
      this.height = 7.0F;
      DisplayUtils.drawRoundedRect(this.getX() + this.getWidth() - this.width - 7.0F, this.getY() + this.getHeight() / 2.0F - this.height / 2.0F, this.width, this.height, 3.0F, ColorUtils.rgb(29, 29, 31));
      int color = ColorUtils.interpolate(ColorUtils.rgb(129, 135, 255), ColorUtils.rgb(129, 135, 255), 1.0F - (float)this.animation.getValue());
      DisplayUtils.drawCircle((float)((double)(this.getX() + this.getWidth() - this.width - 7.0F + 4.0F) + 7.0D * this.animation.getValue()), this.getY() + this.getHeight() / 2.0F - this.height / 2.0F + 3.5F, 5.0F, color);
      DisplayUtils.drawShadowCircle((float)((double)(this.getX() + this.getWidth() - this.width - 7.0F + 4.0F) + 7.0D * this.animation.getValue()), this.getY() + this.getHeight() / 2.0F - this.height / 2.0F + 3.5F, 7.0F, ColorUtils.setAlpha(color, (int)(128.0D * this.animation.getValue())));
      if (this.isHovered(mouseX, mouseY)) {
         if (MathUtil.isHovered(mouseX, mouseY, this.getX() + this.getWidth() - this.width - 7.0F, this.getY() + this.getHeight() / 2.0F - this.height / 2.0F, this.width, this.height)) {
            if (!this.hovered) {
               GLFW.glfwSetCursor(Minecraft.getInstance().getMainWindow().getHandle(), Cursors.HAND);
               this.hovered = true;
            }
         } else if (this.hovered) {
            GLFW.glfwSetCursor(Minecraft.getInstance().getMainWindow().getHandle(), Cursors.ARROW);
            this.hovered = false;
         }
      }

   }

   public void mouseClick(float mouseX, float mouseY, int mouse) {
      if (MathUtil.isHovered(mouseX, mouseY, this.getX() + this.getWidth() - this.width - 7.0F, this.getY() + this.getHeight() / 2.0F - this.height / 2.0F, this.width, this.height)) {
         this.setting.set(!(Boolean)this.setting.get());
         this.animation = this.animation.animate((Boolean)this.setting.get() ? 1.0D : 0.0D, 0.2D, Easings.CIRC_OUT);
      }

      super.mouseClick(mouseX, mouseY, mouse);
   }

   public boolean isVisible() {
      return (Boolean)this.setting.visible.get();
   }
}
