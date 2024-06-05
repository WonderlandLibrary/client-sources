package com.lukflug.panelstudio.theme;

import com.lukflug.panelstudio.Context;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

public class FixedDescription implements DescriptionRenderer {
   protected Point pos;

   public FixedDescription(Point var1) {
      this.pos = var1;
   }

   public void renderDescription(Context var1) {
      if (var1.getDescription() != null) {
         Rectangle var2 = new Rectangle(this.pos, new Dimension(var1.getInterface().getFontWidth(var1.getDescription()), var1.getInterface().getFontHeight()));
         Color var3 = new Color(0, 0, 0);
         var1.getInterface().fillRect(var2, var3, var3, var3, var3);
         Color var4 = new Color(255, 255, 255);
         var1.getInterface().drawRect(var2, var4, var4, var4, var4);
         var1.getInterface().drawString(this.pos, var1.getDescription(), var4);
      }

   }
}
