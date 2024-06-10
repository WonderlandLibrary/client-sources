/*  1:   */ package org.newdawn.slick.svg.inkscape;
/*  2:   */ 
/*  3:   */ import org.newdawn.slick.geom.Transform;
/*  4:   */ import org.newdawn.slick.svg.Diagram;
/*  5:   */ import org.newdawn.slick.svg.Loader;
/*  6:   */ import org.newdawn.slick.svg.ParsingException;
/*  7:   */ import org.w3c.dom.Element;
/*  8:   */ 
/*  9:   */ public class GroupProcessor
/* 10:   */   implements ElementProcessor
/* 11:   */ {
/* 12:   */   public boolean handles(Element element)
/* 13:   */   {
/* 14:20 */     if (element.getNodeName().equals("g")) {
/* 15:21 */       return true;
/* 16:   */     }
/* 17:23 */     return false;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void process(Loader loader, Element element, Diagram diagram, Transform t)
/* 21:   */     throws ParsingException
/* 22:   */   {
/* 23:30 */     Transform transform = Util.getTransform(element);
/* 24:31 */     transform = new Transform(t, transform);
/* 25:   */     
/* 26:33 */     loader.loadChildren(element, transform);
/* 27:   */   }
/* 28:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.svg.inkscape.GroupProcessor
 * JD-Core Version:    0.7.0.1
 */