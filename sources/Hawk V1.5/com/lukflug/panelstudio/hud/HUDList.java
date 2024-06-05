package com.lukflug.panelstudio.hud;

import java.awt.Color;

public interface HUDList {
   boolean sortUp();

   String getItem(int var1);

   int getSize();

   boolean sortRight();

   Color getItemColor(int var1);
}
