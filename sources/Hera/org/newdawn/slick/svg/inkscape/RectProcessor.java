/*    */ package org.newdawn.slick.svg.inkscape;
/*    */ 
/*    */ import org.newdawn.slick.geom.Rectangle;
/*    */ import org.newdawn.slick.geom.Shape;
/*    */ import org.newdawn.slick.geom.Transform;
/*    */ import org.newdawn.slick.svg.Diagram;
/*    */ import org.newdawn.slick.svg.Figure;
/*    */ import org.newdawn.slick.svg.Loader;
/*    */ import org.newdawn.slick.svg.NonGeometricData;
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
/*    */ public class RectProcessor
/*    */   implements ElementProcessor
/*    */ {
/*    */   public void process(Loader loader, Element element, Diagram diagram, Transform t) throws ParsingException {
/* 24 */     Transform transform = Util.getTransform(element);
/* 25 */     transform = new Transform(t, transform);
/*    */     
/* 27 */     float width = Float.parseFloat(element.getAttribute("width"));
/* 28 */     float height = Float.parseFloat(element.getAttribute("height"));
/* 29 */     float x = Float.parseFloat(element.getAttribute("x"));
/* 30 */     float y = Float.parseFloat(element.getAttribute("y"));
/*    */     
/* 32 */     Rectangle rect = new Rectangle(x, y, width + 1.0F, height + 1.0F);
/* 33 */     Shape shape = rect.transform(transform);
/*    */     
/* 35 */     NonGeometricData data = Util.getNonGeometricData(element);
/* 36 */     data.addAttribute("width", "" + width);
/* 37 */     data.addAttribute("height", "" + height);
/* 38 */     data.addAttribute("x", "" + x);
/* 39 */     data.addAttribute("y", "" + y);
/*    */     
/* 41 */     diagram.addFigure(new Figure(3, shape, data, transform));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean handles(Element element) {
/* 48 */     if (element.getNodeName().equals("rect")) {
/* 49 */       return true;
/*    */     }
/*    */     
/* 52 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\svg\inkscape\RectProcessor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */