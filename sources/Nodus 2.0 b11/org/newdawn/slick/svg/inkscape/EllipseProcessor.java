/*  1:   */ package org.newdawn.slick.svg.inkscape;
/*  2:   */ 
/*  3:   */ import org.newdawn.slick.geom.Ellipse;
/*  4:   */ import org.newdawn.slick.geom.Shape;
/*  5:   */ import org.newdawn.slick.geom.Transform;
/*  6:   */ import org.newdawn.slick.svg.Diagram;
/*  7:   */ import org.newdawn.slick.svg.Figure;
/*  8:   */ import org.newdawn.slick.svg.Loader;
/*  9:   */ import org.newdawn.slick.svg.NonGeometricData;
/* 10:   */ import org.newdawn.slick.svg.ParsingException;
/* 11:   */ import org.w3c.dom.Element;
/* 12:   */ 
/* 13:   */ public class EllipseProcessor
/* 14:   */   implements ElementProcessor
/* 15:   */ {
/* 16:   */   public void process(Loader loader, Element element, Diagram diagram, Transform t)
/* 17:   */     throws ParsingException
/* 18:   */   {
/* 19:24 */     Transform transform = Util.getTransform(element);
/* 20:25 */     transform = new Transform(t, transform);
/* 21:   */     
/* 22:27 */     float x = Util.getFloatAttribute(element, "cx");
/* 23:28 */     float y = Util.getFloatAttribute(element, "cy");
/* 24:29 */     float rx = Util.getFloatAttribute(element, "rx");
/* 25:30 */     float ry = Util.getFloatAttribute(element, "ry");
/* 26:   */     
/* 27:32 */     Ellipse ellipse = new Ellipse(x, y, rx, ry);
/* 28:33 */     Shape shape = ellipse.transform(transform);
/* 29:   */     
/* 30:35 */     NonGeometricData data = Util.getNonGeometricData(element);
/* 31:36 */     data.addAttribute("cx", x);
/* 32:37 */     data.addAttribute("cy", y);
/* 33:38 */     data.addAttribute("rx", rx);
/* 34:39 */     data.addAttribute("ry", ry);
/* 35:   */     
/* 36:41 */     diagram.addFigure(new Figure(1, shape, data, transform));
/* 37:   */   }
/* 38:   */   
/* 39:   */   public boolean handles(Element element)
/* 40:   */   {
/* 41:48 */     if (element.getNodeName().equals("ellipse")) {
/* 42:49 */       return true;
/* 43:   */     }
/* 44:51 */     if ((element.getNodeName().equals("path")) && 
/* 45:52 */       ("arc".equals(element.getAttributeNS("http://sodipodi.sourceforge.net/DTD/sodipodi-0.dtd", "type")))) {
/* 46:53 */       return true;
/* 47:   */     }
/* 48:57 */     return false;
/* 49:   */   }
/* 50:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.svg.inkscape.EllipseProcessor
 * JD-Core Version:    0.7.0.1
 */