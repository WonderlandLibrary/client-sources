/*    */ package org.newdawn.slick.svg.inkscape;
/*    */ 
/*    */ import org.newdawn.slick.geom.Ellipse;
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
/*    */ public class EllipseProcessor
/*    */   implements ElementProcessor
/*    */ {
/*    */   public void process(Loader loader, Element element, Diagram diagram, Transform t) throws ParsingException {
/* 24 */     Transform transform = Util.getTransform(element);
/* 25 */     transform = new Transform(t, transform);
/*    */     
/* 27 */     float x = Util.getFloatAttribute(element, "cx");
/* 28 */     float y = Util.getFloatAttribute(element, "cy");
/* 29 */     float rx = Util.getFloatAttribute(element, "rx");
/* 30 */     float ry = Util.getFloatAttribute(element, "ry");
/*    */     
/* 32 */     Ellipse ellipse = new Ellipse(x, y, rx, ry);
/* 33 */     Shape shape = ellipse.transform(transform);
/*    */     
/* 35 */     NonGeometricData data = Util.getNonGeometricData(element);
/* 36 */     data.addAttribute("cx", "" + x);
/* 37 */     data.addAttribute("cy", "" + y);
/* 38 */     data.addAttribute("rx", "" + rx);
/* 39 */     data.addAttribute("ry", "" + ry);
/*    */     
/* 41 */     diagram.addFigure(new Figure(1, shape, data, transform));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean handles(Element element) {
/* 48 */     if (element.getNodeName().equals("ellipse")) {
/* 49 */       return true;
/*    */     }
/* 51 */     if (element.getNodeName().equals("path") && 
/* 52 */       "arc".equals(element.getAttributeNS("http://sodipodi.sourceforge.net/DTD/sodipodi-0.dtd", "type"))) {
/* 53 */       return true;
/*    */     }
/*    */ 
/*    */     
/* 57 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\svg\inkscape\EllipseProcessor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */