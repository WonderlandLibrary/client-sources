/*  1:   */ package org.newdawn.slick.svg.inkscape;
/*  2:   */ 
/*  3:   */ import org.newdawn.slick.geom.Shape;
/*  4:   */ import org.newdawn.slick.geom.Transform;
/*  5:   */ import org.newdawn.slick.svg.Diagram;
/*  6:   */ import org.newdawn.slick.svg.Figure;
/*  7:   */ import org.newdawn.slick.svg.Loader;
/*  8:   */ import org.newdawn.slick.svg.NonGeometricData;
/*  9:   */ import org.newdawn.slick.svg.ParsingException;
/* 10:   */ import org.w3c.dom.Element;
/* 11:   */ 
/* 12:   */ public class UseProcessor
/* 13:   */   implements ElementProcessor
/* 14:   */ {
/* 15:   */   public boolean handles(Element element)
/* 16:   */   {
/* 17:24 */     return element.getNodeName().equals("use");
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void process(Loader loader, Element element, Diagram diagram, Transform transform)
/* 21:   */     throws ParsingException
/* 22:   */   {
/* 23:33 */     String ref = element.getAttributeNS("http://www.w3.org/1999/xlink", "href");
/* 24:34 */     String href = Util.getAsReference(ref);
/* 25:   */     
/* 26:36 */     Figure referenced = diagram.getFigureByID(href);
/* 27:37 */     if (referenced == null) {
/* 28:38 */       throw new ParsingException(element, "Unable to locate referenced element: " + href);
/* 29:   */     }
/* 30:41 */     Transform local = Util.getTransform(element);
/* 31:42 */     Transform trans = local.concatenate(referenced.getTransform());
/* 32:   */     
/* 33:44 */     NonGeometricData data = Util.getNonGeometricData(element);
/* 34:45 */     Shape shape = referenced.getShape().transform(trans);
/* 35:46 */     data.addAttribute("fill", referenced.getData().getAttribute("fill"));
/* 36:47 */     data.addAttribute("stroke", referenced.getData().getAttribute("stroke"));
/* 37:48 */     data.addAttribute("opacity", referenced.getData().getAttribute("opacity"));
/* 38:49 */     data.addAttribute("stroke-width", referenced.getData().getAttribute("stroke-width"));
/* 39:   */     
/* 40:51 */     Figure figure = new Figure(referenced.getType(), shape, data, trans);
/* 41:52 */     diagram.addFigure(figure);
/* 42:   */   }
/* 43:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.svg.inkscape.UseProcessor
 * JD-Core Version:    0.7.0.1
 */