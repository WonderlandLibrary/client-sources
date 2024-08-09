package src.Wiksi.ui.dropdown.components;

import com.mojang.blaze3d.matrix.MatrixStack;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.settings.Setting;
import src.Wiksi.functions.settings.impl.BindSetting;
import src.Wiksi.functions.settings.impl.BooleanSetting;
import src.Wiksi.functions.settings.impl.ModeListSetting;
import src.Wiksi.functions.settings.impl.ModeSetting;
import src.Wiksi.functions.settings.impl.SliderSetting;
import src.Wiksi.functions.settings.impl.StringSetting;
import src.Wiksi.ui.dropdown.components.settings.BindComponent;
import src.Wiksi.ui.dropdown.components.settings.BooleanComponent;
import src.Wiksi.ui.dropdown.components.settings.ModeComponent;
import src.Wiksi.ui.dropdown.components.settings.MultiBoxComponent;
import src.Wiksi.ui.dropdown.components.settings.SliderComponent;
import src.Wiksi.ui.dropdown.components.settings.StringComponent;
import src.Wiksi.ui.dropdown.impl.Component;
import src.Wiksi.utils.client.KeyStorage;
import src.Wiksi.utils.math.MathUtil;
import src.Wiksi.utils.render.ColorUtils;
import src.Wiksi.utils.render.Cursors;
import src.Wiksi.utils.render.DisplayUtils;
import src.Wiksi.utils.render.Stencil;
import src.Wiksi.utils.render.font.Fonts;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.vector.Vector4f;
import org.lwjgl.glfw.GLFW;
import ru.hogoshi.Animation;
import ru.hogoshi.util.Easings;

public class ModuleComponent extends Component {
   private final Vector4f ROUNDING_VECTOR = new Vector4f(5.0F, 5.0F, 5.0F, 5.0F);
   private final Function function;
   public Animation animation = new Animation();
   public boolean open;
   private boolean bind;
   private final ObjectArrayList<Component> components = new ObjectArrayList();
   private boolean hovered = false;

   public ModuleComponent(Function function) {
      this.function = function;
      Iterator var2 = function.getSettings().iterator();

      while(var2.hasNext()) {
         Setting<?> setting = (Setting)var2.next();
         if (setting instanceof BooleanSetting) {
            BooleanSetting bool = (BooleanSetting)setting;
            this.components.add(new BooleanComponent(bool));
         }

         if (setting instanceof SliderSetting) {
            SliderSetting slider = (SliderSetting)setting;
            this.components.add(new SliderComponent(slider));
         }

         if (setting instanceof BindSetting) {
            BindSetting bind = (BindSetting)setting;
            this.components.add(new BindComponent(bind));
         }

         if (setting instanceof ModeSetting) {
            ModeSetting mode = (ModeSetting)setting;
            this.components.add(new ModeComponent(mode));
         }

         if (setting instanceof ModeListSetting) {
            ModeListSetting mode = (ModeListSetting)setting;
            this.components.add(new MultiBoxComponent(mode));
         }

         if (setting instanceof StringSetting) {
            StringSetting string = (StringSetting)setting;
            this.components.add(new StringComponent(string));
         }
      }

      this.animation = this.animation.animate(this.open ? 1.0D : 0.0D, 0.3D);
   }

   public void drawComponents(MatrixStack stack, float mouseX, float mouseY) {
      if (this.animation.getValue() > 0.0D) {
         if (this.animation.getValue() > 0.1D && this.components.stream().filter(Component::isVisible).count() >= 1L) {
            DisplayUtils.drawRectVerticalW((double)(this.getX() + 5.0F), (double)(this.getY() + 20.0F), (double)(this.getWidth() - 10.0F), 0.5D, ColorUtils.rgb(42, 44, 50), ColorUtils.rgb(28, 28, 33));
         }

         Stencil.initStencilToWrite();
         DisplayUtils.drawRoundedRect(this.getX() + 0.5F, this.getY() + 0.5F, this.getWidth() - 1.0F, this.getHeight() - 1.0F, this.ROUNDING_VECTOR, ColorUtils.rgba(23, 23, 23, 84));
         Stencil.readStencilBuffer(1);
         float y = this.getY() + 20.0F;
         ObjectListIterator var5 = this.components.iterator();

         while(var5.hasNext()) {
            Component component = (Component)var5.next();
            if (component.isVisible()) {
               component.setX(this.getX());
               component.setY(y);
               component.setWidth(this.getWidth());
               component.render(stack, mouseX, mouseY);
               y += component.getHeight();
            }
         }

         Stencil.uninitStencilBuffer();
      }

   }

   public void mouseRelease(float mouseX, float mouseY, int mouse) {
      ObjectListIterator var4 = this.components.iterator();

      while(var4.hasNext()) {
         Component component = (Component)var4.next();
         component.mouseRelease(mouseX, mouseY, mouse);
      }

      super.mouseRelease(mouseX, mouseY, mouse);
   }

   public void render(MatrixStack stack, float mouseX, float mouseY) {
      int color = ColorUtils.interpolate(-1, -1, (float)this.function.getAnimation().getValue());
      int color1 = ColorUtils.interpolate(ColorUtils.rgb(61, 61, 80), ColorUtils.rgb(35, 36, 50), (float)this.function.getAnimation().getValue());
      this.function.getAnimation().update();
      super.render(stack, mouseX, mouseY);
      this.drawOutlinedRect(mouseX, mouseY, color1);
      this.drawText(stack, color);
      this.drawComponents(stack, mouseX, mouseY);
   }

   public void mouseClick(float mouseX, float mouseY, int button) {
      if (this.isHovered(mouseX, mouseY, 20.0F)) {
         if (button == 0) {
            this.function.toggle();
         }

         if (button == 1) {
            this.open = !this.open;
            this.animation = this.animation.animate(this.open ? 1.0D : 0.0D, 0.2D, Easings.CIRC_OUT);
         }

         if (button == 2) {
            this.bind = !this.bind;
         }
      }

      if (this.isHovered(mouseX, mouseY) && this.open) {
         ObjectListIterator var4 = this.components.iterator();

         while(var4.hasNext()) {
            Component component = (Component)var4.next();
            if (component.isVisible()) {
               component.mouseClick(mouseX, mouseY, button);
            }
         }
      }

      super.mouseClick(mouseX, mouseY, button);
   }

   public void charTyped(char codePoint, int modifiers) {
      ObjectListIterator var3 = this.components.iterator();

      while(var3.hasNext()) {
         Component component = (Component)var3.next();
         if (component.isVisible()) {
            component.charTyped(codePoint, modifiers);
         }
      }

      super.charTyped(codePoint, modifiers);
   }

   public void keyPressed(int key, int scanCode, int modifiers) {
      ObjectListIterator var4 = this.components.iterator();

      while(var4.hasNext()) {
         Component component = (Component)var4.next();
         if (component.isVisible()) {
            component.keyPressed(key, scanCode, modifiers);
         }
      }

      if (this.bind) {
         if (key != 261 && key != 256) {
            this.function.setBind(key);
         } else {
            this.function.setBind(0);
         }

         this.bind = false;
      }

      super.keyPressed(key, scanCode, modifiers);
   }

   private void drawOutlinedRect(float mouseX, float mouseY, int color) {
      Stencil.initStencilToWrite();
      DisplayUtils.drawRoundedRect(this.getX() + 2.5F, this.getY() + 0.5F, this.getWidth() - 5.0F, this.getHeight() - 1.0F, this.ROUNDING_VECTOR, color);
      Stencil.readStencilBuffer(0);
      DisplayUtils.drawRoundedRect(this.getX() + 2.0F, this.getY(), this.getWidth() - 5.0F, this.getHeight(), this.ROUNDING_VECTOR, color);
      Stencil.uninitStencilBuffer();
      DisplayUtils.drawRoundedRect(this.getX() + 2.0F, this.getY(), this.getWidth() - 5.0F, this.getHeight(), this.ROUNDING_VECTOR, color);
      if (MathUtil.isHovered(mouseX, mouseY, this.getX(), this.getY(), this.getWidth(), 20.0F)) {
         if (!this.hovered) {
            GLFW.glfwSetCursor(Minecraft.getInstance().getMainWindow().getHandle(), Cursors.HAND);
            this.hovered = true;
         }
      } else if (this.hovered) {
         GLFW.glfwSetCursor(Minecraft.getInstance().getMainWindow().getHandle(), Cursors.ARROW);
         this.hovered = false;
      }

   }

   private void drawText(MatrixStack stack, int color) {
      if (color == ColorUtils.rgb(35, 36, 50)) {
         Fonts.mslight.drawText(stack, this.function.getName(), this.getX() + 6.0F, this.getY() + 6.5F - 3.0F, color, 5.0F, 0.1F);
      } else {
         Fonts.mslight.drawText(stack, this.function.getName(), this.getX() + 6.0F, this.getY() + 6.5F - 3.0F, color, 7.0F, 0.1F);
      }

      if (this.components.stream().filter(Component::isVisible).count() >= 1L) {
         if (this.bind) {
            Fonts.mslight.drawText(stack, this.function.getBind() == 0 ? "..." : KeyStorage.getReverseKey(this.function.getBind()), this.getX() + this.getWidth() - 6.0F - Fonts.mslight.getWidth(this.function.getBind() == 0 ? "..." : KeyStorage.getReverseKey(this.function.getBind()), 6.0F, 0.1F), this.getY() + Fonts.icons.getHeight(6.0F) + 1.0F, ColorUtils.rgb(161, 164, 177), 6.0F, 0.1F);
         } else {
            Fonts.icons.drawText(stack, !this.open ? "B" : "C", this.getX() + this.getWidth() - 6.0F - Fonts.icons.getWidth(!this.open ? "B" : "C", 6.0F), this.getY() + Fonts.icons.getHeight(6.0F) + 1.0F, ColorUtils.rgb(161, 164, 177), 6.0F);
         }
      } else if (this.bind) {
         Fonts.mslight.drawText(stack, this.function.getBind() == 0 ? "..." : KeyStorage.getReverseKey(this.function.getBind()), this.getX() + this.getWidth() - 6.0F - Fonts.mslight.getWidth(this.function.getBind() == 0 ? "..." : KeyStorage.getReverseKey(this.function.getBind()), 6.0F, 0.1F), this.getY() + Fonts.icons.getHeight(6.0F) + 1.0F, ColorUtils.rgb(161, 164, 177), 6.0F, 0.1F);
      }

   }

   public Vector4f getROUNDING_VECTOR() {
      return this.ROUNDING_VECTOR;
   }

   public Function getFunction() {
      return this.function;
   }

   public Animation getAnimation() {
      return this.animation;
   }

   public boolean isOpen() {
      return this.open;
   }

   public boolean isBind() {
      return this.bind;
   }

   public ObjectArrayList<Component> getComponents() {
      return this.components;
   }

   public boolean isHovered() {
      return this.hovered;
   }
}
