package com.lukflug.panelstudio;

import com.lukflug.panelstudio.theme.Renderer;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

public abstract class Slider extends FocusableComponent {
   protected boolean attached = false;

   public void render(Context var1) {
      super.render(var1);
      if (this.attached) {
         double var2 = (double)(var1.getInterface().getMouse().x - var1.getPos().x) / (double)(var1.getSize().width - 1);
         if (var2 < 0.0D) {
            var2 = 0.0D;
         } else if (var2 > 1.0D) {
            var2 = 1.0D;
         }

         this.setValue(var2);
      }

      if (!var1.getInterface().getButton(0)) {
         this.attached = false;
      }

      this.renderer.renderRect(var1, "", this.hasFocus(var1), false, new Rectangle(new Point(var1.getPos().x + (int)((double)var1.getSize().width * this.getValue()), var1.getPos().y), new Dimension((int)((double)var1.getSize().width * (1.0D - this.getValue())), this.renderer.getHeight(false))), false);
      this.renderer.renderRect(var1, this.title, this.hasFocus(var1), true, new Rectangle(var1.getPos(), new Dimension((int)((double)var1.getSize().width * this.getValue()), this.renderer.getHeight(false))), true);
   }

   protected abstract double getValue();

   public void handleButton(Context var1, int var2) {
      super.handleButton(var1, var2);
      if (var2 == 0 && var1.isClicked()) {
         this.attached = true;
      }

   }

   public Slider(String var1, String var2, Renderer var3) {
      super(var1, var2, var3);
   }

   protected abstract void setValue(double var1);
}
