package cc.novoline.gui;

public interface Element {
   void draw(int var1, int var2);

   void onDraw(int var1, int var2);

   boolean isVisible();

   void setVisible(boolean var1);

   int getX();

   void setX(int var1);

   int getY();

   void setY(int var1);

   default void setPosition(int var1, int var2) {
      this.setX(var1);
      this.setY(var2);
   }
}
