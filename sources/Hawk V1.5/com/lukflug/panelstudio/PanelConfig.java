package com.lukflug.panelstudio;

import java.awt.Point;

public interface PanelConfig {
   void saveState(boolean var1);

   void savePositon(Point var1);

   boolean loadState();

   Point loadPosition();
}
