package com.lukflug.panelstudio.theme;

import java.awt.Color;

public interface ColorScheme {
   Color getActiveColor();

   Color getInactiveColor();

   int getOpacity();

   Color getBackgroundColor();

   Color getFontColor();

   Color getOutlineColor();
}
