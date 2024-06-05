package com.lukflug.panelstudio.mc8forge;

import com.lukflug.panelstudio.ClickGUI;
import java.awt.Point;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Mouse;

public abstract class MinecraftGUI extends GuiScreen {
   private boolean rButton = false;
   private boolean lButton = false;
   private Point mouse = new Point();

   protected abstract MinecraftGUI.GUIInterface getInterface();

   protected void keyTyped(char var1, int var2) {
      if (var2 == 1) {
         this.exitGUI();
      } else {
         this.getGUI().handleKey(var2);
      }

   }

   static Point access$2(MinecraftGUI var0) {
      return var0.mouse;
   }

   static float access$3(MinecraftGUI var0) {
      return var0.zLevel;
   }

   protected abstract int getScrollSpeed();

   protected abstract ClickGUI getGUI();

   protected void renderGUI() {
      this.getInterface().getMatrices();
      GLInterface.begin();
      this.getGUI().render();
      GLInterface.end();
   }

   static boolean access$0(MinecraftGUI var0) {
      return var0.lButton;
   }

   public boolean doesGuiPauseGame() {
      return false;
   }

   public void enterGUI() {
      Minecraft.getMinecraft().displayGuiScreen(this);
      this.getGUI().enter();
   }

   public void mouseClicked(int var1, int var2, int var3) {
      this.mouse = new Point(var1, var2);
      switch(var3) {
      case 0:
         this.lButton = true;
         break;
      case 1:
         this.rButton = true;
      }

      this.getGUI().handleButton(var3);
   }

   static boolean access$1(MinecraftGUI var0) {
      return var0.rButton;
   }

   public void exitGUI() {
      this.getGUI().exit();
      Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
   }

   public void mouseReleased(int var1, int var2, int var3) {
      this.mouse = new Point(var1, var2);
      switch(var3) {
      case 0:
         this.lButton = false;
         break;
      case 1:
         this.rButton = false;
      }

      this.getGUI().handleButton(var3);
   }

   public void drawScreen(int var1, int var2, float var3) {
      this.mouse = new Point(var1, var2);
      this.renderGUI();
      int var4 = Mouse.getDWheel();
      if (var4 != 0) {
         if (var4 > 0) {
            this.getGUI().handleScroll(-this.getScrollSpeed());
         } else {
            this.getGUI().handleScroll(this.getScrollSpeed());
         }
      }

   }

   public abstract class GUIInterface extends GLInterface {
      final MinecraftGUI this$0;

      protected float getZLevel() {
         return MinecraftGUI.access$3(this.this$0);
      }

      public GUIInterface(MinecraftGUI var1, boolean var2) {
         super(var2);
         this.this$0 = var1;
      }

      public Point getMouse() {
         return new Point(MinecraftGUI.access$2(this.this$0));
      }

      public boolean getButton(int var1) {
         switch(var1) {
         case 0:
            return MinecraftGUI.access$0(this.this$0);
         case 1:
            return MinecraftGUI.access$1(this.this$0);
         default:
            return false;
         }
      }
   }
}
