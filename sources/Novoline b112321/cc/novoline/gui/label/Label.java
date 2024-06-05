package cc.novoline.gui.label;

import cc.novoline.gui.Element;
import cc.novoline.utils.fonts.api.FontRenderer;
import java.awt.Color;

public interface Label extends Element {
   String getText();

   void setText(String var1);

   int getColor();

   void setColor(int var1);

   default void setColor(Color var1) {
      this.setColor(var1.getRGB());
   }

   FontRenderer getFontRenderer();

   void setFontRenderer(FontRenderer var1);

   default int getWidth() {
      return this.getFontRenderer().stringWidth(this.getText());
   }
}
