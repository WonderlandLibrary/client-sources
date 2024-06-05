package com.lukflug.panelstudio.settings;

import java.awt.Color;

public interface ColorSetting {
   void setValue(Color var1);

   Color getValue();

   Color getColor();

   boolean getRainbow();

   void setRainbow(boolean var1);
}
