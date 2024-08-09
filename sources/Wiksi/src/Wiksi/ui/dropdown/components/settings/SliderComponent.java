package src.Wiksi.ui.dropdown.components.settings;

import com.mojang.blaze3d.matrix.MatrixStack;
import src.Wiksi.functions.settings.impl.SliderSetting;
import src.Wiksi.ui.dropdown.impl.Component;
import src.Wiksi.utils.math.MathUtil;
import src.Wiksi.utils.render.ColorUtils;
import src.Wiksi.utils.render.Cursors;
import src.Wiksi.utils.render.DisplayUtils;
import src.Wiksi.utils.render.font.Fonts;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.glfw.GLFW;

public class SliderComponent extends Component {
   private final SliderSetting setting;
   private float anim;
   private boolean drag;
   private boolean hovered = false;

   public SliderComponent(SliderSetting setting) {
      this.setting = setting;
      this.setHeight(18.0F);
   }

   public void render(MatrixStack stack, float mouseX, float mouseY) {
      super.render(stack, mouseX, mouseY);
      Fonts.montserrat.drawText(stack, this.setting.getName(), this.getX() + 5.0F, this.getY() + 2.25F + 1.0F, ColorUtils.rgb(255, 255, 255), 5.5F, 0.05F);
      Fonts.montserrat.drawText(stack, String.valueOf(this.setting.get()), this.getX() + this.getWidth() - 5.0F - Fonts.montserrat.getWidth(String.valueOf(this.setting.get()), 5.5F), this.getY() + 2.25F + 1.0F, ColorUtils.rgb(255, 255, 255), 5.5F, 0.05F);
      DisplayUtils.drawRoundedRect(this.getX() + 5.0F, this.getY() + 11.0F, this.getWidth() - 10.0F, 2.0F, 0.6F, ColorUtils.rgb(28, 28, 31));
      this.anim = MathUtil.fast(this.anim, (this.getWidth() - 10.0F) * ((Float)this.setting.get() - this.setting.min) / (this.setting.max - this.setting.min), 20.0F);
      float sliderWidth = this.anim;
      DisplayUtils.drawRoundedRect(this.getX() + 5.0F, this.getY() + 11.0F, sliderWidth, 2.0F, 0.6F, ColorUtils.rgb(25, 26, 50));
      DisplayUtils.drawCircle(this.getX() + 5.0F + sliderWidth, this.getY() + 12.0F, 5.0F, ColorUtils.rgb(129, 135, 255));
      DisplayUtils.drawShadowCircle(this.getX() + 5.0F + sliderWidth, this.getY() + 12.0F, 6.0F, ColorUtils.rgba(1129, 135, 255, 80));
      if (this.drag) {
         GLFW.glfwSetCursor(Minecraft.getInstance().getMainWindow().getHandle(), GLFW.glfwCreateStandardCursor(221189));
         this.setting.set((float)MathHelper.clamp(MathUtil.round((double)((mouseX - this.getX() - 5.0F) / (this.getWidth() - 10.0F) * (this.setting.max - this.setting.min) + this.setting.min), (double)this.setting.increment), (double)this.setting.min, (double)this.setting.max));
      }

      if (this.isHovered(mouseX, mouseY)) {
         if (MathUtil.isHovered(mouseX, mouseY, this.getX() + 5.0F, this.getY() + 10.0F, this.getWidth() - 10.0F, 3.0F)) {
            if (!this.hovered) {
               GLFW.glfwSetCursor(Minecraft.getInstance().getMainWindow().getHandle(), Cursors.RESIZEH);
               this.hovered = true;
            }
         } else if (this.hovered) {
            GLFW.glfwSetCursor(Minecraft.getInstance().getMainWindow().getHandle(), Cursors.ARROW);
            this.hovered = false;
         }
      }

   }

   public void mouseClick(float mouseX, float mouseY, int mouse) {
      if (MathUtil.isHovered(mouseX, mouseY, this.getX() + 5.0F, this.getY() + 10.0F, this.getWidth() - 10.0F, 3.0F)) {
         this.drag = true;
      }

      super.mouseClick(mouseX, mouseY, mouse);
   }

   public void mouseRelease(float mouseX, float mouseY, int mouse) {
      this.drag = false;
      super.mouseRelease(mouseX, mouseY, mouse);
   }

   public boolean isVisible() {
      return (Boolean)this.setting.visible.get();
   }
}
