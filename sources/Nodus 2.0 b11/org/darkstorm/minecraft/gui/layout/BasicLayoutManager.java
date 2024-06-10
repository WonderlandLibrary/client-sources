/*  1:   */ package org.darkstorm.minecraft.gui.layout;
/*  2:   */ 
/*  3:   */ import java.awt.Dimension;
/*  4:   */ import java.awt.Rectangle;
/*  5:   */ 
/*  6:   */ public class BasicLayoutManager
/*  7:   */   implements LayoutManager
/*  8:   */ {
/*  9:   */   public void reposition(Rectangle area, Rectangle[] componentAreas, Constraint[][] constraints)
/* 10:   */   {
/* 11: 8 */     int offset = 0;
/* 12: 9 */     for (Rectangle componentArea : componentAreas)
/* 13:   */     {
/* 14:10 */       if (componentArea == null) {
/* 15:11 */         throw new NullPointerException();
/* 16:   */       }
/* 17:12 */       componentArea.x = area.x;
/* 18:13 */       area.y += offset;
/* 19:14 */       offset += componentArea.height;
/* 20:   */     }
/* 21:   */   }
/* 22:   */   
/* 23:   */   public Dimension getOptimalPositionedSize(Rectangle[] componentAreas, Constraint[][] constraints)
/* 24:   */   {
/* 25:21 */     int width = 0;int height = 0;
/* 26:22 */     for (Rectangle component : componentAreas)
/* 27:   */     {
/* 28:23 */       if (component == null) {
/* 29:24 */         throw new NullPointerException();
/* 30:   */       }
/* 31:25 */       height += component.height;
/* 32:26 */       width = Math.max(width, component.width);
/* 33:   */     }
/* 34:28 */     return new Dimension(width, height);
/* 35:   */   }
/* 36:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.darkstorm.minecraft.gui.layout.BasicLayoutManager
 * JD-Core Version:    0.7.0.1
 */