package com.lukflug.panelstudio.tabgui;

import com.lukflug.panelstudio.Animation;
import com.lukflug.panelstudio.FixedComponent;
import com.lukflug.panelstudio.Interface;
import com.lukflug.panelstudio.PanelConfig;
import java.awt.Point;

public class TabGUI extends TabGUIContainer implements FixedComponent {
   protected Point position;
   protected int width;

   public void saveConfig(Interface var1, PanelConfig var2) {
      var2.savePositon(this.position);
   }

   public int getWidth(Interface var1) {
      return this.width;
   }

   public void loadConfig(Interface var1, PanelConfig var2) {
      Point var3 = var2.loadPosition();
      if (var3 != null) {
         this.position = var3;
      }

   }

   public TabGUI(String var1, TabGUIRenderer var2, Animation var3, Point var4, int var5) {
      super(var1, var2, var3);
      this.position = var4;
      this.width = var5;
   }

   public void setPosition(Interface var1, Point var2) {
      this.position = var2;
   }

   public Point getPosition(Interface var1) {
      return new Point(this.position);
   }
}
