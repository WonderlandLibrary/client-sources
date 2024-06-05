package com.lukflug.panelstudio.hud;

import com.lukflug.panelstudio.Context;
import com.lukflug.panelstudio.FixedComponent;
import com.lukflug.panelstudio.Interface;
import com.lukflug.panelstudio.PanelConfig;
import com.lukflug.panelstudio.theme.Renderer;
import java.awt.Point;

public abstract class HUDComponent implements FixedComponent {
   protected String title;
   protected Renderer renderer;
   protected Point position;

   public void setPosition(Interface var1, Point var2) {
      this.position = var2;
   }

   public void enter(Context var1) {
      this.getHeight(var1);
   }

   public void handleScroll(Context var1, int var2) {
      this.getHeight(var1);
   }

   public String getTitle() {
      return this.title;
   }

   public void render(Context var1) {
      this.getHeight(var1);
   }

   public HUDComponent(String var1, Renderer var2, Point var3) {
      this.title = var1;
      this.renderer = var2;
      this.position = var3;
   }

   public void exit(Context var1) {
      this.getHeight(var1);
   }

   public void releaseFocus() {
   }

   public void saveConfig(Interface var1, PanelConfig var2) {
      var2.savePositon(this.position);
   }

   public void handleKey(Context var1, int var2) {
      this.getHeight(var1);
   }

   public Point getPosition(Interface var1) {
      return new Point(this.position);
   }

   public void loadConfig(Interface var1, PanelConfig var2) {
      Point var3 = var2.loadPosition();
      if (var3 != null) {
         this.position = var3;
      }

   }

   public void handleButton(Context var1, int var2) {
      this.getHeight(var1);
   }
}
