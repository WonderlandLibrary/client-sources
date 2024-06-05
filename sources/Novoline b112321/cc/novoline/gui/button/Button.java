package cc.novoline.gui.button;

import cc.novoline.gui.Element;
import cc.novoline.gui.label.Label;

public interface Button extends Element {
   void click(int var1);

   Label getName();

   void setName(Label var1);
}
