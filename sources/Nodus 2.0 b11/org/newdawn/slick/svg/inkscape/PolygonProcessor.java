/*   1:    */ package org.newdawn.slick.svg.inkscape;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.StringTokenizer;
/*   5:    */ import org.newdawn.slick.geom.Polygon;
/*   6:    */ import org.newdawn.slick.geom.Shape;
/*   7:    */ import org.newdawn.slick.geom.Transform;
/*   8:    */ import org.newdawn.slick.svg.Diagram;
/*   9:    */ import org.newdawn.slick.svg.Figure;
/*  10:    */ import org.newdawn.slick.svg.Loader;
/*  11:    */ import org.newdawn.slick.svg.NonGeometricData;
/*  12:    */ import org.newdawn.slick.svg.ParsingException;
/*  13:    */ import org.w3c.dom.Element;
/*  14:    */ 
/*  15:    */ public class PolygonProcessor
/*  16:    */   implements ElementProcessor
/*  17:    */ {
/*  18:    */   private static int processPoly(Polygon poly, Element element, StringTokenizer tokens)
/*  19:    */     throws ParsingException
/*  20:    */   {
/*  21: 33 */     int count = 0;
/*  22:    */     
/*  23: 35 */     ArrayList pts = new ArrayList();
/*  24: 36 */     boolean moved = false;
/*  25: 37 */     boolean closed = false;
/*  26: 39 */     while (tokens.hasMoreTokens())
/*  27:    */     {
/*  28: 40 */       String nextToken = tokens.nextToken();
/*  29: 41 */       if (!nextToken.equals("L"))
/*  30:    */       {
/*  31: 44 */         if (nextToken.equals("z"))
/*  32:    */         {
/*  33: 45 */           closed = true;
/*  34: 46 */           break;
/*  35:    */         }
/*  36: 48 */         if (nextToken.equals("M"))
/*  37:    */         {
/*  38: 49 */           if (!moved) {
/*  39: 50 */             moved = true;
/*  40:    */           } else {
/*  41: 54 */             return 0;
/*  42:    */           }
/*  43:    */         }
/*  44:    */         else
/*  45:    */         {
/*  46: 56 */           if (nextToken.equals("C")) {
/*  47: 57 */             return 0;
/*  48:    */           }
/*  49: 60 */           String tokenX = nextToken;
/*  50: 61 */           String tokenY = tokens.nextToken();
/*  51:    */           try
/*  52:    */           {
/*  53: 64 */             float x = Float.parseFloat(tokenX);
/*  54: 65 */             float y = Float.parseFloat(tokenY);
/*  55:    */             
/*  56: 67 */             poly.addPoint(x, y);
/*  57: 68 */             count++;
/*  58:    */           }
/*  59:    */           catch (NumberFormatException e)
/*  60:    */           {
/*  61: 70 */             throw new ParsingException(element.getAttribute("id"), "Invalid token in points list", e);
/*  62:    */           }
/*  63:    */         }
/*  64:    */       }
/*  65:    */     }
/*  66: 74 */     poly.setClosed(closed);
/*  67: 75 */     return count;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void process(Loader loader, Element element, Diagram diagram, Transform t)
/*  71:    */     throws ParsingException
/*  72:    */   {
/*  73: 83 */     Transform transform = Util.getTransform(element);
/*  74: 84 */     transform = new Transform(t, transform);
/*  75:    */     
/*  76: 86 */     String points = element.getAttribute("points");
/*  77: 87 */     if (element.getNodeName().equals("path")) {
/*  78: 88 */       points = element.getAttribute("d");
/*  79:    */     }
/*  80: 91 */     StringTokenizer tokens = new StringTokenizer(points, ", ");
/*  81: 92 */     Polygon poly = new Polygon();
/*  82: 93 */     int count = processPoly(poly, element, tokens);
/*  83:    */     
/*  84: 95 */     NonGeometricData data = Util.getNonGeometricData(element);
/*  85: 96 */     if (count > 3)
/*  86:    */     {
/*  87: 97 */       Shape shape = poly.transform(transform);
/*  88:    */       
/*  89: 99 */       diagram.addFigure(new Figure(5, shape, data, transform));
/*  90:    */     }
/*  91:    */   }
/*  92:    */   
/*  93:    */   public boolean handles(Element element)
/*  94:    */   {
/*  95:107 */     if (element.getNodeName().equals("polygon")) {
/*  96:108 */       return true;
/*  97:    */     }
/*  98:111 */     if ((element.getNodeName().equals("path")) && 
/*  99:112 */       (!"arc".equals(element.getAttributeNS("http://sodipodi.sourceforge.net/DTD/sodipodi-0.dtd", "type")))) {
/* 100:113 */       return true;
/* 101:    */     }
/* 102:117 */     return false;
/* 103:    */   }
/* 104:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.svg.inkscape.PolygonProcessor
 * JD-Core Version:    0.7.0.1
 */