package com.lukflug.panelstudio.theme;

import com.lukflug.panelstudio.Context;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

public class MouseDescription implements DescriptionRenderer {
   protected Point offset;

   public MouseDescription(Point var1) {
      this.offset = var1;
   }

   public void renderDescription(Context var1) {
      if (var1.getDescription() != null) {
         Point var2 = var1.getInterface().getMouse();
         var2.translate(this.offset.x, this.offset.y);
         Rectangle var3 = new Rectangle(var2, new Dimension(var1.getInterface().getFontWidth(var1.getDescription()), var1.getInterface().getFontHeight()));
         Color var4 = new Color(0, 0, 0);
         var1.getInterface().fillRect(var3, var4, var4, var4, var4);
         Color var5 = new Color(255, 255, 255);
         var1.getInterface().drawRect(var3, var5, var5, var5, var5);
         var1.getInterface().drawString(var2, var1.getDescription(), var5);
      }

   }
}
