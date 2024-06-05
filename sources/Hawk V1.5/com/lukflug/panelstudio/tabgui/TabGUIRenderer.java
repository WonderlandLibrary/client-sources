package com.lukflug.panelstudio.tabgui;

import com.lukflug.panelstudio.Context;
import com.lukflug.panelstudio.theme.ColorScheme;

public interface TabGUIRenderer {
   boolean isEscapeKey(int var1);

   ColorScheme getColorScheme();

   int getHeight();

   int getBorder();

   boolean isSelectKey(int var1);

   boolean isDownKey(int var1);

   void renderBackground(Context var1, int var2, int var3);

   void renderCaption(Context var1, String var2, int var3, int var4, boolean var5);

   boolean isUpKey(int var1);
}
