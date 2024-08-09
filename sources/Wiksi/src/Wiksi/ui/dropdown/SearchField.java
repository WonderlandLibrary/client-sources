package src.Wiksi.ui.dropdown;

import com.mojang.blaze3d.matrix.MatrixStack;
import src.Wiksi.utils.math.MathUtil;
import src.Wiksi.utils.render.ColorUtils;
import src.Wiksi.utils.render.DisplayUtils;
import src.Wiksi.utils.render.font.Fonts;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.vector.Vector4f;

public class SearchField {
   Minecraft mc = Minecraft.getInstance();
   private int x;
   private int y;
   private int width;
   private int height;
   private String text;
   private boolean isFocused;
   private boolean typing;
   private final String placeholder;

   public SearchField(int x, int y, int width, int height, String placeholder) {
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
      this.placeholder = placeholder;
      this.text = "";
      this.isFocused = false;
      this.typing = false;
   }

   public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
      this.mc.gameRenderer.setupOverlayRendering(2);
      DisplayUtils.drawShadow((float)this.x, (float)this.y, (float)this.width, (float)this.height, 15, ColorUtils.rgba(21, 24, 40, 165));
      DisplayUtils.drawRoundedRect((float)this.x, (float)this.y, (float)this.width, (float)this.height, new Vector4f(5.0F, 5.0F, 5.0F, 5.0F), ColorUtils.rgba(25, 26, 40, 165));
      String textToDraw = this.text.isEmpty() && !this.typing ? this.placeholder : this.text;
      String cursor = this.typing && System.currentTimeMillis() % 1000L > 500L ? "_" : "";
      Fonts.montserrat.drawText(matrixStack, textToDraw + cursor, (float)(this.x + 5), (float)(this.y + (this.height - 8) / 2 + 1), ColorUtils.rgb(255, 255, 255), 6.0F);
   }

   public boolean charTyped(char codePoint, int modifiers) {
      if (this.isFocused) {
         this.text = this.text + codePoint;
         return true;
      } else {
         return false;
      }
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (this.isFocused && keyCode == 259 && !this.text.isEmpty()) {
         this.text = this.text.substring(0, this.text.length() - 1);
         return true;
      } else {
         if (keyCode == 257 || keyCode == 256) {
            this.typing = false;
         }

         return false;
      }
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (!MathUtil.isHovered((float)mouseX, (float)mouseY, (float)this.x, (float)this.y, (float)this.width, (float)this.height)) {
         this.isFocused = false;
      }

      this.isFocused = MathUtil.isHovered((float)mouseX, (float)mouseY, (float)this.x, (float)this.y, (float)this.width, (float)this.height);
      this.typing = this.isFocused;
      return this.isFocused;
   }

   public boolean isEmpty() {
      return this.text.isEmpty();
   }

   public void setFocused(boolean focused) {
      this.isFocused = focused;
   }

   public void setMc(Minecraft mc) {
      this.mc = mc;
   }

   public void setX(int x) {
      this.x = x;
   }

   public void setY(int y) {
      this.y = y;
   }

   public void setWidth(int width) {
      this.width = width;
   }

   public void setHeight(int height) {
      this.height = height;
   }

   public void setText(String text) {
      this.text = text;
   }

   public void setTyping(boolean typing) {
      this.typing = typing;
   }

   public Minecraft getMc() {
      return this.mc;
   }

   public int getX() {
      return this.x;
   }

   public int getY() {
      return this.y;
   }

   public int getWidth() {
      return this.width;
   }

   public int getHeight() {
      return this.height;
   }

   public String getText() {
      return this.text;
   }

   public boolean isFocused() {
      return this.isFocused;
   }

   public boolean isTyping() {
      return this.typing;
   }

   public String getPlaceholder() {
      return this.placeholder;
   }
}
