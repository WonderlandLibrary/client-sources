package cc.novoline.gui.field;

import cc.novoline.gui.Element;
import cc.novoline.gui.label.Label;

public interface Field extends Element {
   void setHint(Label var1);

   Label getLabel();
}
