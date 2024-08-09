package src.Wiksi.ui.dropdown;

import com.mojang.blaze3d.matrix.MatrixStack;
import src.Wiksi.ui.dropdown.impl.IBuilder;
import src.Wiksi.utils.math.MathUtil;
import src.Wiksi.utils.render.ColorUtils;
import src.Wiksi.utils.render.DisplayUtils;
import net.minecraft.util.ResourceLocation;

public class ThemeEditor implements IBuilder {
   private int x;
   private int y;
   private int width;
   private int height;
   private final ResourceLocation themeIcon = new ResourceLocation("Wiksi/images/theme.png");
   public boolean isClickedButton = false;
   public boolean toRender = false;

   public ThemeEditor(int x, int y, int width, int height) {
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
   }

   public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
      DisplayUtils.drawImage(this.themeIcon, (float)this.x, (float)this.y, (float)this.width, (float)this.height, ColorUtils.getColor(1));
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      this.isClickedButton = MathUtil.isHovered((float)mouseX, (float)mouseY, (float)this.x, (float)this.y, (float)this.width, (float)this.height);
      if (this.isClickedButton && this.toRender) {
         this.isClickedButton = false;
         this.toRender = false;
         return this.isClickedButton;
      } else if (this.isClickedButton) {
         this.toRender = true;
         return this.isClickedButton;
      } else {
         return this.isClickedButton;
      }
   }

   public void mouseRelease(float mouseX, float mouseY, int button) {
   }
}
