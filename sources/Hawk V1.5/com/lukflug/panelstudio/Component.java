package com.lukflug.panelstudio;

public interface Component {
   void releaseFocus();

   void handleScroll(Context var1, int var2);

   void exit(Context var1);

   void enter(Context var1);

   void getHeight(Context var1);

   void handleKey(Context var1, int var2);

   String getTitle();

   void handleButton(Context var1, int var2);

   void render(Context var1);
}
