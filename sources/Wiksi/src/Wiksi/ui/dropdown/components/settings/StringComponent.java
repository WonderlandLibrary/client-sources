package src.Wiksi.ui.dropdown.components.settings;

import com.mojang.blaze3d.matrix.MatrixStack;
import src.Wiksi.functions.settings.impl.StringSetting;
import src.Wiksi.ui.dropdown.impl.Component;
import src.Wiksi.utils.math.MathUtil;
import src.Wiksi.utils.render.ColorUtils;
import src.Wiksi.utils.render.Cursors;
import src.Wiksi.utils.render.DisplayUtils;
import src.Wiksi.utils.render.font.Fonts;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import org.apache.commons.lang3.math.NumberUtils;
import org.lwjgl.glfw.GLFW;

public class StringComponent extends Component {
   private final StringSetting setting;
   private boolean typing;
   private String text = "";
   private static final int X_OFFSET = 5;
   private static final int Y_OFFSET = 10;
   private static final int WIDTH_OFFSET = -9;
   private static final int TEXT_Y_OFFSET = -7;
   private boolean hovered = false;

   public StringComponent(StringSetting setting) {
      this.setting = setting;
      this.setHeight(24.0F);
   }

   public void render(MatrixStack stack, float mouseX, float mouseY) {
      super.render(stack, mouseX, mouseY);
      this.text = (String)this.setting.get();
      if (this.setting.isOnlyNumber() && !NumberUtils.isNumber(this.text)) {
         this.text = this.text.replaceAll("[a-zA-Z]", "");
      }

      float x = this.calculateX();
      float y = this.calculateY();
      float width = this.calculateWidth();
      String settingName = this.setting.getName();
      String settingDesc = this.setting.getDescription();
      String textToDraw = (String)this.setting.get();
      if (!this.typing && ((String)this.setting.get()).isEmpty()) {
         textToDraw = settingDesc;
      }

      if (this.setting.isOnlyNumber() && !NumberUtils.isNumber(textToDraw)) {
         textToDraw = textToDraw.replaceAll("[a-zA-Z]", "");
      }

      float height = this.calculateHeight(textToDraw, width - 1.0F);
      this.drawSettingName(stack, settingName, x, y);
      this.drawBackground(x, y, width, height);
      this.drawTextWithLineBreaks(stack, textToDraw + (this.typing && this.text.length() < 59 && System.currentTimeMillis() % 1000L > 500L ? "_" : ""), x + 1.0F, y + Fonts.montserrat.getHeight(6.0F) / 2.0F, width - 1.0F);
      if (this.isHovered(mouseX, mouseY)) {
         if (MathUtil.isHovered(mouseX, mouseY, x, y, width, height)) {
            if (!this.hovered) {
               GLFW.glfwSetCursor(Minecraft.getInstance().getMainWindow().getHandle(), Cursors.IBEAM);
               this.hovered = true;
            }
         } else if (this.hovered) {
            GLFW.glfwSetCursor(Minecraft.getInstance().getMainWindow().getHandle(), Cursors.ARROW);
            this.hovered = false;
         }
      }

      this.setHeight(height + 12.0F);
   }

   private void drawTextWithLineBreaks(MatrixStack stack, String text, float x, float y, float maxWidth) {
      String[] lines = text.split("\n");
      float currentY = y;
      String[] var8 = lines;
      int var9 = lines.length;

      for(int var10 = 0; var10 < var9; ++var10) {
         String line = var8[var10];
         List<String> wrappedLines = this.wrapText(line, 6.0F, maxWidth);

         for(Iterator var13 = wrappedLines.iterator(); var13.hasNext(); currentY += Fonts.montserrat.getHeight(6.0F)) {
            String wrappedLine = (String)var13.next();
            Fonts.montserrat.drawText(stack, wrappedLine, x, currentY, ColorUtils.rgba(255, 255, 255, 255), 6.0F);
         }
      }

   }

   private List<String> wrapText(String text, float size, float maxWidth) {
      List<String> lines = new ArrayList();
      String[] words = text.split(" ");
      StringBuilder currentLine = new StringBuilder();
      String[] var7 = words;
      int var8 = words.length;

      for(int var9 = 0; var9 < var8; ++var9) {
         String word = var7[var9];
         if (Fonts.montserrat.getWidth(word, size) <= maxWidth) {
            if (Fonts.montserrat.getWidth(currentLine.toString() + word, size) <= maxWidth) {
               currentLine.append(word).append(" ");
            } else {
               lines.add(currentLine.toString());
               currentLine = (new StringBuilder(word)).append(" ");
            }
         } else {
            if (!currentLine.toString().isEmpty()) {
               lines.add(currentLine.toString());
               currentLine = new StringBuilder();
            }

            currentLine = this.breakAndAddWord(word, currentLine, size, maxWidth, lines);
         }
      }

      if (!currentLine.toString().isEmpty()) {
         lines.add(currentLine.toString());
      }

      return lines;
   }

   private StringBuilder breakAndAddWord(String word, StringBuilder currentLine, float size, float maxWidth, List<String> lines) {
      int wordLength = word.length();

      for(int i = 0; i < wordLength; ++i) {
         char c = word.charAt(i);
         String var10000 = currentLine.toString();
         String nextPart = var10000 + c;
         if (Fonts.montserrat.getWidth(nextPart, size) <= maxWidth) {
            currentLine.append(c);
         } else {
            lines.add(currentLine.toString());
            currentLine = new StringBuilder(String.valueOf(c));
         }
      }

      return currentLine;
   }

   private float calculateX() {
      return this.getX() + 5.0F;
   }

   private float calculateY() {
      return this.getY() + 10.0F;
   }

   private float calculateWidth() {
      return this.getWidth() + -9.0F;
   }

   private float calculateHeight(String text, float maxWidth) {
      List<String> wrappedLines = this.wrapText(text, 6.0F, maxWidth);
      int numberOfLines = wrappedLines.size();
      float lineHeight = Fonts.montserrat.getHeight(6.0F);
      float spacingBetweenLines = 1.5F;
      float initialHeight = 5.0F;
      return initialHeight + (float)numberOfLines * lineHeight + (float)(numberOfLines - 1);
   }

   private void drawSettingName(MatrixStack stack, String settingName, float x, float y) {
      Fonts.montserrat.drawText(stack, settingName, x, y + -7.0F, ColorUtils.rgba(255, 255, 255, 255), 6.0F);
   }

   private void drawBackground(float x, float y, float width, float height) {
      DisplayUtils.drawRoundedRect(x, y, width, height, 4.0F, ColorUtils.rgba(25, 26, 40, 165));
   }

   public void charTyped(char codePoint, int modifiers) {
      if (!this.setting.isOnlyNumber() || NumberUtils.isNumber(String.valueOf(codePoint))) {
         if (this.typing && this.text.length() < 60) {
            this.text = this.text + codePoint;
            this.setting.set(this.text);
         }

         super.charTyped(codePoint, modifiers);
      }
   }

   public void keyPressed(int key, int scanCode, int modifiers) {
      if (this.typing) {
         if (Screen.isPaste(key)) {
            this.pasteFromClipboard();
         }

         if (key == 259) {
            this.deleteLastCharacter();
         }

         if (key == 257) {
            this.typing = false;
         }
      }

      super.keyPressed(key, scanCode, modifiers);
   }

   public void mouseClick(float mouseX, float mouseY, int mouse) {
      if (this.isHovered(mouseX, mouseY)) {
         this.typing = !this.typing;
      } else {
         this.typing = false;
      }

      super.mouseClick(mouseX, mouseY, mouse);
   }

   private boolean isControlDown() {
      return GLFW.glfwGetKey(Minecraft.getInstance().getMainWindow().getHandle(), 341) == 1 || GLFW.glfwGetKey(Minecraft.getInstance().getMainWindow().getHandle(), 345) == 1;
   }

   private void pasteFromClipboard() {
      try {
         String var10001 = this.text;
         this.text = var10001 + GLFW.glfwGetClipboardString(Minecraft.getInstance().getMainWindow().getHandle());
         this.setting.set(this.text);
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   private void deleteLastCharacter() {
      if (!this.text.isEmpty()) {
         this.text = this.text.substring(0, this.text.length() - 1);
         this.setting.set(this.text);
      }

   }

   public boolean isVisible() {
      return (Boolean)this.setting.visible.get();
   }
}
