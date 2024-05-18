/*    */ package org.newdawn.slick.svg.inkscape;
/*    */ 
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
/*    */ 
/*    */ public class UseProcessor
/*    */   implements ElementProcessor
/*    */ {
/*    */   public boolean handles(Element element) {
/* 24 */     return element.getNodeName().equals("use");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void process(Loader loader, Element element, Diagram diagram, Transform transform) throws ParsingException {
/* 33 */     String ref = element.getAttributeNS("http://www.w3.org/1999/xlink", "href");
/* 34 */     String href = Util.getAsReference(ref);
/*    */     
/* 36 */     Figure referenced = diagram.getFigureByID(href);
/* 37 */     if (referenced == null) {
/* 38 */       throw new ParsingException(element, "Unable to locate referenced element: " + href);
/*    */     }
/*    */     
/* 41 */     Transform local = Util.getTransform(element);
/* 42 */     Transform trans = local.concatenate(referenced.getTransform());
/*    */     
/* 44 */     NonGeometricData data = Util.getNonGeometricData(element);
/* 45 */     Shape shape = referenced.getShape().transform(trans);
/* 46 */     data.addAttribute("fill", referenced.getData().getAttribute("fill"));
/* 47 */     data.addAttribute("stroke", referenced.getData().getAttribute("stroke"));
/* 48 */     data.addAttribute("opacity", referenced.getData().getAttribute("opacity"));
/* 49 */     data.addAttribute("stroke-width", referenced.getData().getAttribute("stroke-width"));
/*    */     
/* 51 */     Figure figure = new Figure(referenced.getType(), shape, data, trans);
/* 52 */     diagram.addFigure(figure);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\svg\inkscape\UseProcessor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */