package cc.novoline.gui.group2;

import cc.novoline.gui.Element;
import java.awt.Color;

public interface Group extends Element {
   int getColor();

   void setColor(int var1);

   default void setColor(Color var1) {
      this.setColor(var1.getRGB());
   }
}
