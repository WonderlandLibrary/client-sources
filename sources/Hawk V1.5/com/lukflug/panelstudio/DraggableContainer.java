package com.lukflug.panelstudio;

import com.lukflug.panelstudio.settings.Toggleable;
import com.lukflug.panelstudio.theme.Renderer;
import java.awt.Point;

public class DraggableContainer extends CollapsibleContainer implements FixedComponent {
   protected Point attachPoint;
   protected boolean bodyDrag = false;
   protected boolean dragging = false;
   protected Point position;
   protected int width;

   public void setPosition(Interface var1, Point var2) {
      this.position = new Point(var2);
   }

   public void saveConfig(Interface var1, PanelConfig var2) {
      var2.savePositon(this.position);
      var2.saveState(this.open.isOn());
   }

   public Point getPosition(Interface var1) {
      if (this.dragging) {
         Point var2 = new Point(this.position);
         var2.translate(var1.getMouse().x - this.attachPoint.x, var1.getMouse().y - this.attachPoint.y);
         return var2;
      } else {
         return this.position;
      }
   }

   public DraggableContainer(String var1, String var2, Renderer var3, Toggleable var4, Animation var5, Toggleable var6, Point var7, int var8) {
      super(var1, var2, var3, var4, var5, var6);
      this.position = var7;
      this.width = var8;
   }

   protected void handleFocus(Context var1, boolean var2) {
      if (var2) {
         var1.requestFocus();
      }

   }

   public void loadConfig(Interface var1, PanelConfig var2) {
      Point var3 = var2.loadPosition();
      if (var3 != null) {
         this.position = var3;
      }

      if (this.open.isOn() != var2.loadState()) {
         this.open.toggle();
      }

   }

   public void handleButton(Context var1, int var2) {
      if (this.bodyDrag) {
         super.handleButton(var1, var2);
      } else {
         var1.setHeight(this.renderer.getHeight(this.open.getValue() != 0.0D));
      }

      if (var1.isClicked() && var2 == 0) {
         this.dragging = true;
         this.attachPoint = var1.getInterface().getMouse();
      } else if (!var1.getInterface().getButton(0) && this.dragging) {
         Point var3 = var1.getInterface().getMouse();
         this.dragging = false;
         Point var4 = this.getPosition(var1.getInterface());
         var4.translate(var3.x - this.attachPoint.x, var3.y - this.attachPoint.y);
         this.setPosition(var1.getInterface(), var4);
      }

      if (!this.bodyDrag) {
         super.handleButton(var1, var2);
      }

   }

   public int getWidth(Interface var1) {
      return this.width;
   }
}
