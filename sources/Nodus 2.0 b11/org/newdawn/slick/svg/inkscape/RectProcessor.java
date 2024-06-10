/*  1:   */ package org.newdawn.slick.svg.inkscape;
/*  2:   */ 
/*  3:   */ import org.newdawn.slick.geom.Rectangle;
/*  4:   */ import org.newdawn.slick.geom.Shape;
/*  5:   */ import org.newdawn.slick.geom.Transform;
/*  6:   */ import org.newdawn.slick.svg.Diagram;
/*  7:   */ import org.newdawn.slick.svg.Figure;
/*  8:   */ import org.newdawn.slick.svg.Loader;
/*  9:   */ import org.newdawn.slick.svg.NonGeometricData;
/* 10:   */ import org.newdawn.slick.svg.ParsingException;
/* 11:   */ import org.w3c.dom.Element;
/* 12:   */ 
/* 13:   */ public class RectProcessor
/* 14:   */   implements ElementProcessor
/* 15:   */ {
/* 16:   */   public void process(Loader loader, Element element, Diagram diagram, Transform t)
/* 17:   */     throws ParsingException
/* 18:   */   {
/* 19:24 */     Transform transform = Util.getTransform(element);
/* 20:25 */     transform = new Transform(t, transform);
/* 21:   */     
/* 22:27 */     float width = Float.parseFloat(element.getAttribute("width"));
/* 23:28 */     float height = Float.parseFloat(element.getAttribute("height"));
/* 24:29 */     float x = Float.parseFloat(element.getAttribute("x"));
/* 25:30 */     float y = Float.parseFloat(element.getAttribute("y"));
/* 26:   */     
/* 27:32 */     Rectangle rect = new Rectangle(x, y, width + 1.0F, height + 1.0F);
/* 28:33 */     Shape shape = rect.transform(transform);
/* 29:   */     
/* 30:35 */     NonGeometricData data = Util.getNonGeometricData(element);
/* 31:36 */     data.addAttribute("width", width);
/* 32:37 */     data.addAttribute("height", height);
/* 33:38 */     data.addAttribute("x", x);
/* 34:39 */     data.addAttribute("y", y);
/* 35:   */     
/* 36:41 */     diagram.addFigure(new Figure(3, shape, data, transform));
/* 37:   */   }
/* 38:   */   
/* 39:   */   public boolean handles(Element element)
/* 40:   */   {
/* 41:48 */     if (element.getNodeName().equals("rect")) {
/* 42:49 */       return true;
/* 43:   */     }
/* 44:52 */     return false;
/* 45:   */   }
/* 46:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.svg.inkscape.RectProcessor
 * JD-Core Version:    0.7.0.1
 */