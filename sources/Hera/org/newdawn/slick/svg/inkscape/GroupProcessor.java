/*    */ package org.newdawn.slick.svg.inkscape;
/*    */ 
/*    */ import org.newdawn.slick.geom.Transform;
/*    */ import org.newdawn.slick.svg.Diagram;
/*    */ import org.newdawn.slick.svg.Loader;
/*    */ import org.newdawn.slick.svg.ParsingException;
/*    */ import org.w3c.dom.Element;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GroupProcessor
/*    */   implements ElementProcessor
/*    */ {
/*    */   public boolean handles(Element element) {
/* 20 */     if (element.getNodeName().equals("g")) {
/* 21 */       return true;
/*    */     }
/* 23 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void process(Loader loader, Element element, Diagram diagram, Transform t) throws ParsingException {
/* 30 */     Transform transform = Util.getTransform(element);
/* 31 */     transform = new Transform(t, transform);
/*    */     
/* 33 */     loader.loadChildren(element, transform);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\svg\inkscape\GroupProcessor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */