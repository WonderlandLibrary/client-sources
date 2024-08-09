package src.Wiksi.ui.dropdown;

import com.mojang.blaze3d.matrix.MatrixStack;
import src.Wiksi.Wiksi;
import src.Wiksi.functions.api.Category;
import src.Wiksi.ui.styles.Style;
import src.Wiksi.utils.math.MathUtil;
import src.Wiksi.utils.render.ColorUtils;
import src.Wiksi.utils.render.Cursors;
import src.Wiksi.utils.render.DisplayUtils;
import src.Wiksi.utils.render.Scissor;
import src.Wiksi.utils.render.font.Fonts;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.glfw.GLFW;

public class PanelStyle extends Panel {
   private final ResourceLocation brushIcon = new ResourceLocation("Wiksi/images/hud/brush.png");
   float max = 0.0F;

   public PanelStyle(Category category) {
      super(category);
   }

   public void render(MatrixStack stack, float mouseX, float mouseY) {
      float header = 25.0F;
      float headerFont = 8.0F;
      this.setAnimatedScrool(MathUtil.fast(this.getAnimatedScrool(), this.getScroll(), 10.0F));
      DisplayUtils.drawRoundedRect(this.x, this.y + 19.0F, 105.0F, 175.0F, 13.0F, ColorUtils.rgba(25, 26, 40, 255));
      Fonts.montserrat.drawText(stack, "Theme Editor", this.x + 10.0F, this.y + header / 2.0F - Fonts.montserrat.getHeight(headerFont) / 2.0F + 18.0F, ColorUtils.rgb(255, 255, 255), headerFont - 1.5F, 0.1F);
      DisplayUtils.drawImage(this.brushIcon, this.x + 105.0F - 20.0F, this.y + header, 10.0F, 10.0F, -1);
      if (this.max > 220.0F - header + 40.0F) {
         this.setScroll(MathHelper.clamp(this.getScroll(), -this.max + 220.0F - header - 10.0F, 0.0F));
         this.setAnimatedScrool(MathHelper.clamp(this.getAnimatedScrool(), -this.max + 220.0F - header - 10.0F, 0.0F));
      } else {
         this.setScroll(0.0F);
         this.setAnimatedScrool(0.0F);
      }

      float animationValue = (float)DropDown.getAnimation().getValue() * DropDown.scale;
      float halfAnimationValueRest = (1.0F - animationValue) / 2.0F;
      float height = this.getHeight();
      float testX = this.getX() + this.getWidth() * halfAnimationValueRest;
      float testY = this.getY() + 25.0F + height * halfAnimationValueRest;
      float testW = this.getWidth() * animationValue;
      float testH = height * animationValue - 56.0F;
      testX = testX * animationValue + ((float)Minecraft.getInstance().getMainWindow().getScaledWidth() - testW) * halfAnimationValueRest;
      Scissor.push();
      Scissor.setFromComponentCoordinates((double)testX, (double)testY, (double)testW, (double)testH);
      int offset = 1;
      boolean hovered = false;
      float x = this.x + 5.0F;
      float y = this.y + header + 5.0F + (float)offset + this.getAnimatedScrool();
      float H = 12.0F;

      for(Iterator var18 = Wiksi.getInstance().getStyleManager().getStyleList().iterator(); var18.hasNext(); ++offset) {
         Style style = (Style)var18.next();
         if (MathUtil.isHovered(mouseX, mouseY, x + 5.0F, y, 85.0F, H)) {
            hovered = true;
         }

         if (Wiksi.getInstance().getStyleManager().getCurrentStyle() == style) {
            Fonts.montserrat.drawText(stack, style.getStyleName(), x + 0.75F + 52.5F - 28.0F, y + H / 2.0F + 7.0F - Fonts.montserrat.getHeight(6.0F) / 2.0F, style.getFirstColor().getRGB(), 6.0F, 0.05F);
            DisplayUtils.drawRoundedRect(x + 5.0F, y + 7.0F, 12.5F, H, 10.0F, style.getFirstColor().getRGB());
            DisplayUtils.drawShadow(x + 5.0F, y + 7.5F, 12.5F, H, 10, style.getFirstColor().getRGB());
         }

         DisplayUtils.drawRoundedRect(x + 5.0F, y + 7.5F, 12.5F, H, 2.0F, style.getFirstColor().getRGB());
         Fonts.montserrat.drawText(stack, style.getStyleName(), x + 0.75F + 52.5F - 28.0F, y + H / 2.0F + 7.0F - Fonts.montserrat.getHeight(6.0F) / 2.0F, -1, 6.0F, 0.05F);
         y += 5.0F + H;
      }

      if (MathUtil.isHovered(mouseX, mouseY, x, y, 105.0F, height)) {
         if (hovered) {
            GLFW.glfwSetCursor(Minecraft.getInstance().getMainWindow().getHandle(), Cursors.HAND);
         } else {
            GLFW.glfwSetCursor(Minecraft.getInstance().getMainWindow().getHandle(), Cursors.ARROW);
         }
      }

      Scissor.unset();
      Scissor.pop();
      this.max = (float)(offset * Wiksi.getInstance().getStyleManager().getStyleList().size()) * 1.21F;
   }

   public void keyPressed(int key, int scanCode, int modifiers) {
   }

   public void mouseClick(float mouseX, float mouseY, int button) {
      float header = 25.0F;
      int offset = 0;
      float x = this.getX() + 5.0F;
      float y = this.getY() + (float)offset + header + 5.0F + this.getAnimatedScrool();

      for(Iterator var8 = Wiksi.getInstance().getStyleManager().getStyleList().iterator(); var8.hasNext(); ++offset) {
         Style style = (Style)var8.next();
         float barHeight = 12.0F;
         float barY = y + 7.5F;
         if (MathUtil.isHovered(mouseX, mouseY, x + 5.0F, barY, 12.5F, barHeight)) {
            Wiksi.getInstance().getStyleManager().setCurrentStyle(style);
         }

         y += 5.0F + barHeight;
      }

   }

   public void mouseRelease(float mouseX, float mouseY, int button) {
   }

   public ResourceLocation getBrushIcon() {
      return this.brushIcon;
   }

   public float getMax() {
      return this.max;
   }
}
