package com.lukflug.panelstudio.hud;

import com.lukflug.panelstudio.Context;
import com.lukflug.panelstudio.Interface;
import com.lukflug.panelstudio.PanelConfig;
import com.lukflug.panelstudio.theme.Renderer;
import java.awt.Point;

public class ListComponent extends HUDComponent {
   protected boolean lastRight = false;
   protected boolean lastUp = false;
   protected HUDList list;

   public int getWidth(Interface var1) {
      int var2 = var1.getFontWidth(this.getTitle());

      for(int var3 = 0; var3 < this.list.getSize(); ++var3) {
         String var4 = this.list.getItem(var3);
         var2 = Math.max(var2, var1.getFontWidth(var4));
      }

      return var2;
   }

   public void render(Context var1) {
      super.render(var1);

      for(int var2 = 0; var2 < this.list.getSize(); ++var2) {
         String var3 = this.list.getItem(var2);
         Point var4 = var1.getPos();
         if (this.list.sortUp()) {
            var4.translate(0, var1.getSize().height - (var2 + 1) * var1.getInterface().getFontHeight());
         } else {
            var4.translate(0, var2 * var1.getInterface().getFontHeight());
         }

         if (this.list.sortRight()) {
            var4.translate(this.getWidth(var1.getInterface()) - var1.getInterface().getFontWidth(var3), 0);
         }

         var1.getInterface().drawString(var4, var3, this.list.getItemColor(var2));
      }

   }

   public void loadConfig(Interface var1, PanelConfig var2) {
      super.loadConfig(var1, var2);
      this.lastUp = this.list.sortUp();
      this.lastRight = this.list.sortRight();
   }

   public ListComponent(String var1, Renderer var2, Point var3, HUDList var4) {
      super(var1, var2, var3);
      this.list = var4;
   }

   public Point getPosition(Interface var1) {
      int var2 = this.getWidth(var1);
      int var3 = this.renderer.getHeight(false) + (this.list.getSize() - 1) * var1.getFontHeight();
      if (this.lastUp != this.list.sortUp()) {
         if (this.list.sortUp()) {
            this.position.translate(0, var3);
         } else {
            this.position.translate(0, -var3);
         }

         this.lastUp = this.list.sortUp();
      }

      if (this.lastRight != this.list.sortRight()) {
         if (this.list.sortRight()) {
            this.position.translate(var2, 0);
         } else {
            this.position.translate(-var2, 0);
         }

         this.lastRight = this.list.sortRight();
      }

      if (this.list.sortUp()) {
         return this.list.sortRight() ? new Point(this.position.x - var2, this.position.y - var3) : new Point(this.position.x, this.position.y - var3);
      } else {
         return this.list.sortRight() ? new Point(new Point(this.position.x - var2, this.position.y)) : new Point(this.position);
      }
   }

   public void setPosition(Interface var1, Point var2) {
      int var3 = this.getWidth(var1);
      int var4 = this.renderer.getHeight(false) + (this.list.getSize() - 1) * var1.getFontHeight();
      if (this.list.sortUp()) {
         if (this.list.sortRight()) {
            this.position = new Point(var2.x + var3, var2.y + var4);
         } else {
            this.position = new Point(var2.x, var2.y + var4);
         }
      } else if (this.list.sortRight()) {
         this.position = new Point(var2.x + var3, var2.y);
      } else {
         this.position = new Point(var2);
      }

   }

   public void getHeight(Context var1) {
      var1.setHeight(this.renderer.getHeight(false) + (this.list.getSize() - 1) * var1.getInterface().getFontHeight());
   }
}
