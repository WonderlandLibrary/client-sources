/*    */ package org.newdawn.slick.svg.inkscape;
/*    */ 
/*    */ import org.newdawn.slick.svg.NonGeometricData;
/*    */ import org.w3c.dom.Element;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InkscapeNonGeometricData
/*    */   extends NonGeometricData
/*    */ {
/*    */   private Element element;
/*    */   
/*    */   public InkscapeNonGeometricData(String metaData, Element element) {
/* 23 */     super(metaData);
/*    */     
/* 25 */     this.element = element;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getAttribute(String attribute) {
/* 32 */     String result = super.getAttribute(attribute);
/* 33 */     if (result == null) {
/* 34 */       result = this.element.getAttribute(attribute);
/*    */     }
/*    */     
/* 37 */     return result;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Element getElement() {
/* 46 */     return this.element;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\svg\inkscape\InkscapeNonGeometricData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */