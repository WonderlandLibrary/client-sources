/*  1:   */ package org.newdawn.slick.svg.inkscape;
/*  2:   */ 
/*  3:   */ import org.newdawn.slick.svg.NonGeometricData;
/*  4:   */ import org.w3c.dom.Element;
/*  5:   */ 
/*  6:   */ public class InkscapeNonGeometricData
/*  7:   */   extends NonGeometricData
/*  8:   */ {
/*  9:   */   private Element element;
/* 10:   */   
/* 11:   */   public InkscapeNonGeometricData(String metaData, Element element)
/* 12:   */   {
/* 13:23 */     super(metaData);
/* 14:   */     
/* 15:25 */     this.element = element;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public String getAttribute(String attribute)
/* 19:   */   {
/* 20:32 */     String result = super.getAttribute(attribute);
/* 21:33 */     if (result == null) {
/* 22:34 */       result = this.element.getAttribute(attribute);
/* 23:   */     }
/* 24:37 */     return result;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public Element getElement()
/* 28:   */   {
/* 29:46 */     return this.element;
/* 30:   */   }
/* 31:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.svg.inkscape.InkscapeNonGeometricData
 * JD-Core Version:    0.7.0.1
 */