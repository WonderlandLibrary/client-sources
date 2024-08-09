package src.Wiksi.ui.dropdown;

import com.mojang.blaze3d.matrix.MatrixStack;
import src.Wiksi.Wiksi;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.ui.dropdown.components.ModuleComponent;
import src.Wiksi.ui.dropdown.impl.Component;
import src.Wiksi.ui.dropdown.impl.IBuilder;
import src.Wiksi.utils.math.MathUtil;
import src.Wiksi.utils.render.ColorUtils;
import src.Wiksi.utils.render.DisplayUtils;
import src.Wiksi.utils.render.Scissor;
import src.Wiksi.utils.render.font.Fonts;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.MathHelper;

public class Panel implements IBuilder {
   private final Category category;
   protected float x;
   protected float y;
   protected final float width = 105.0F;
   protected final float height = 220.0F;
   private List<ModuleComponent> modules = new ArrayList();
   private float scroll;
   private float animatedScrool;
   float max = 0.0F;

   public Panel(Category category) {
      this.category = category;
      Iterator var2 = Wiksi.getInstance().getFunctionRegistry().getFunctions().iterator();

      while(var2.hasNext()) {
         Function function = (Function)var2.next();
         if (function.getCategory() == category) {
            ModuleComponent component = new ModuleComponent(function);
            component.setPanel(this);
            this.modules.add(component);
         }
      }

   }

   public void render(MatrixStack stack, float mouseX, float mouseY) {
      this.animatedScrool = MathUtil.fast(this.animatedScrool, this.scroll, 10.0F);
      float header = 20.0F;
      float headerFont = 8.0F;
      DisplayUtils.drawRoundedRect(this.x, this.y, 105.0F, 220.0F, 13.0F, ColorUtils.rgba(25, 26, 40, 255));
      DisplayUtils.drawRoundedRect(this.x + 3.8F, this.y + 3.5F, 97.0F, 213.0F, 12.0F, ColorUtils.rgba(25, 26, 40, 255));
      Fonts.montserrat.drawCenteredText(stack, this.category.name(), this.x + 52.5F, this.y + header / 2.0F - Fonts.montserrat.getHeight(headerFont) / 2.0F - 1.0F + 5.0F, ColorUtils.rgb(255, 255, 255), headerFont, 0.1F);
      this.drawComponents(stack, mouseX, mouseY);
   }

   private void drawComponents(MatrixStack stack, float mouseX, float mouseY) {
      float animationValue = (float)DropDown.getAnimation().getValue() * DropDown.scale;
      float halfAnimationValueRest = (1.0F - animationValue) / 2.0F;
      float height = this.getHeight();
      float testX = this.getX() + this.getWidth() * halfAnimationValueRest;
      float testY = this.getY() + 25.0F + height * halfAnimationValueRest;
      float testW = this.getWidth() * animationValue;
      float testH = height * animationValue - 33.0F;
      testX = testX * animationValue + ((float)Minecraft.getInstance().getMainWindow().getScaledWidth() - testW) * halfAnimationValueRest;
      Scissor.push();
      Scissor.setFromComponentCoordinates((double)testX, (double)testY, (double)testW, (double)testH);
      float offset = -1.0F;
      float header = 25.0F;
      if (this.max > height - header - 10.0F) {
         this.scroll = MathHelper.clamp(this.scroll, -this.max + height - header - 10.0F, 0.0F);
         this.animatedScrool = MathHelper.clamp(this.animatedScrool, -this.max + height - header - 10.0F, 0.0F);
      } else {
         this.scroll = 0.0F;
         this.animatedScrool = 0.0F;
      }

      Iterator var13 = this.modules.iterator();

      while(true) {
         ModuleComponent component;
         do {
            if (!var13.hasNext()) {
               this.max = offset;
               Scissor.unset();
               Scissor.pop();
               return;
            }

            component = (ModuleComponent)var13.next();
         } while(Wiksi.getInstance().getDropDown().searchCheck(component.getFunction().getName()));

         component.setX(this.getX() + 5.0F);
         component.setY(this.getY() + header + offset + 6.0F + this.animatedScrool);
         component.setWidth(this.getWidth() - 10.0F);
         component.setHeight(15.0F);
         component.animation.update();
         if (component.animation.getValue() > 0.0D) {
            float componentOffset = 0.0F;
            ObjectListIterator var16 = component.getComponents().iterator();

            while(var16.hasNext()) {
               Component component2 = (Component)var16.next();
               if (component2.isVisible()) {
                  componentOffset += component2.getHeight() + 5.0F;
               }
            }

            componentOffset = (float)((double)componentOffset * component.animation.getValue());
            component.setHeight(component.getHeight() + componentOffset);
         }

         component.render(stack, mouseX, mouseY);
         offset += component.getHeight() + 6.0F;
      }
   }

   public void mouseClick(float mouseX, float mouseY, int button) {
      Iterator var4 = this.modules.iterator();

      while(var4.hasNext()) {
         ModuleComponent component = (ModuleComponent)var4.next();
         if (!Wiksi.getInstance().getDropDown().searchCheck(component.getFunction().getName()) && mouseX >= this.getX() && mouseX <= this.getX() + this.getWidth() && mouseY >= this.getY() && mouseY <= this.getY() + this.getHeight()) {
            component.mouseClick(mouseX, mouseY, button);
         }
      }

   }

   public void keyPressed(int key, int scanCode, int modifiers) {
      Iterator var4 = this.modules.iterator();

      while(var4.hasNext()) {
         ModuleComponent component = (ModuleComponent)var4.next();
         component.keyPressed(key, scanCode, modifiers);
      }

   }

   public void charTyped(char codePoint, int modifiers) {
      Iterator var3 = this.modules.iterator();

      while(var3.hasNext()) {
         ModuleComponent component = (ModuleComponent)var3.next();
         component.charTyped(codePoint, modifiers);
      }

   }

   public void mouseRelease(float mouseX, float mouseY, int button) {
      Iterator var4 = this.modules.iterator();

      while(var4.hasNext()) {
         ModuleComponent component = (ModuleComponent)var4.next();
         component.mouseRelease(mouseX, mouseY, button);
      }

   }

   public Category getCategory() {
      return this.category;
   }

   public float getX() {
      return this.x;
   }

   public float getY() {
      return this.y;
   }

   public float getWidth() {
      Objects.requireNonNull(this);
      return 105.0F;
   }

   public float getHeight() {
      Objects.requireNonNull(this);
      return 220.0F;
   }

   public List<ModuleComponent> getModules() {
      return this.modules;
   }

   public float getScroll() {
      return this.scroll;
   }

   public float getAnimatedScrool() {
      return this.animatedScrool;
   }

   public float getMax() {
      return this.max;
   }

   public void setX(float x) {
      this.x = x;
   }

   public void setY(float y) {
      this.y = y;
   }

   public void setModules(List<ModuleComponent> modules) {
      this.modules = modules;
   }

   public void setScroll(float scroll) {
      this.scroll = scroll;
   }

   public void setAnimatedScrool(float animatedScrool) {
      this.animatedScrool = animatedScrool;
   }

   public void setMax(float max) {
      this.max = max;
   }
}
