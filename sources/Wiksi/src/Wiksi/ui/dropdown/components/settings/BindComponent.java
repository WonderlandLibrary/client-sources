package src.Wiksi.ui.dropdown.components.settings;

import com.mojang.blaze3d.matrix.MatrixStack;
import src.Wiksi.functions.settings.impl.BindSetting;
import src.Wiksi.ui.dropdown.impl.Component;
import src.Wiksi.utils.client.KeyStorage;
import src.Wiksi.utils.math.MathUtil;
import src.Wiksi.utils.render.ColorUtils;
import src.Wiksi.utils.render.Cursors;
import src.Wiksi.utils.render.DisplayUtils;
import src.Wiksi.utils.render.font.Fonts;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public class BindComponent extends Component {
   final BindSetting setting;
   boolean activated;
   boolean hovered = false;

   public BindComponent(BindSetting setting) {
      this.setting = setting;
      this.setHeight(16.0F);
   }

   public void render(MatrixStack stack, float mouseX, float mouseY) {
      super.render(stack, mouseX, mouseY);
      Fonts.montserrat.drawText(stack, this.setting.getName(), this.getX() + 5.0F, this.getY() + 3.25F + 1.0F, ColorUtils.rgb(160, 163, 175), 6.5F, 0.05F);
      String bind = KeyStorage.getKey((Integer)this.setting.get());
      if (bind == null || (Integer)this.setting.get() == -1) {
         bind = "Нету";
      }

      boolean next = Fonts.montserrat.getWidth(bind, 5.5F, this.activated ? 0.1F : 0.05F) >= 16.0F;
      float x = next ? this.getX() + 5.0F : this.getX() + this.getWidth() - 7.0F - Fonts.montserrat.getWidth(bind, 5.5F, this.activated ? 0.1F : 0.05F);
      float y = this.getY() + 2.75F + 2.75F + (float)(next ? 8 : 0);
      DisplayUtils.drawShadow(this.getX() + 5.0F, this.getY() + 9.0F, Fonts.montserrat.getWidth(bind, 5.5F, this.activated ? 0.1F : 0.05F) + 4.0F, 9.5F, 10, ColorUtils.rgba(25, 26, 40, 45));
      DisplayUtils.drawRoundedRect(this.getX() + 5.0F, this.getY() + 9.0F, Fonts.montserrat.getWidth(bind, 5.5F, this.activated ? 0.1F : 0.05F) + 4.0F, 9.5F, 2.0F, ColorUtils.rgba(25, 26, 40, 45));
      Fonts.montserrat.drawText(stack, bind, x, y, this.activated ? -1 : ColorUtils.rgb(255, 255, 255), 5.5F, this.activated ? 0.1F : 0.05F);
      if (this.isHovered(mouseX, mouseY)) {
         if (MathUtil.isHovered(mouseX, mouseY, x - 2.0F + 0.5F, y - 2.0F, Fonts.montserrat.getWidth(bind, 5.5F, this.activated ? 0.1F : 0.05F) + 4.0F, 9.5F)) {
            if (!this.hovered) {
               GLFW.glfwSetCursor(Minecraft.getInstance().getMainWindow().getHandle(), Cursors.HAND);
               this.hovered = true;
            }
         } else if (this.hovered) {
            GLFW.glfwSetCursor(Minecraft.getInstance().getMainWindow().getHandle(), Cursors.ARROW);
            this.hovered = false;
         }
      }

      this.setHeight(next ? 20.0F : 16.0F);
   }

   public void keyPressed(int key, int scanCode, int modifiers) {
      if (this.activated) {
         if (key == 261) {
            this.setting.set(-1);
            this.activated = false;
            return;
         }

         this.setting.set(key);
         this.activated = false;
      }

      super.keyPressed(key, scanCode, modifiers);
   }

   public void mouseClick(float mouseX, float mouseY, int mouse) {
      if (this.isHovered(mouseX, mouseY) && mouse == 0) {
         this.activated = !this.activated;
      }

      if (this.activated && mouse >= 1) {
         System.out.println(-100 + mouse);
         this.setting.set(-100 + mouse);
         this.activated = false;
      }

      super.mouseClick(mouseX, mouseY, mouse);
   }

   public void mouseRelease(float mouseX, float mouseY, int mouse) {
      super.mouseRelease(mouseX, mouseY, mouse);
   }

   public boolean isVisible() {
      return (Boolean)this.setting.visible.get();
   }
}
