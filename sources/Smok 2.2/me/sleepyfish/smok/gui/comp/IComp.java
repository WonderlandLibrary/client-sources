package me.sleepyfish.smok.gui.comp;

// Class from SMok Client by SleepyFish
public interface IComp {
   void draw();

   void update(int var1, int var2);

   void mouseDown(int var1, int var2, int var3);

   void mouseReleased(int var1, int var2, int var3);

   void setComponentStartAt(int var1);

   void keyTyped(char var1, int var2);

   int getHeight();

   int getY();
}
