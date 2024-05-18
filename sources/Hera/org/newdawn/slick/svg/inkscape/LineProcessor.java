/*     */ package org.newdawn.slick.svg.inkscape;
/*     */ 
/*     */ import java.util.StringTokenizer;
/*     */ import org.newdawn.slick.geom.Line;
/*     */ import org.newdawn.slick.geom.Polygon;
/*     */ import org.newdawn.slick.geom.Shape;
/*     */ import org.newdawn.slick.geom.Transform;
/*     */ import org.newdawn.slick.svg.Diagram;
/*     */ import org.newdawn.slick.svg.Figure;
/*     */ import org.newdawn.slick.svg.Loader;
/*     */ import org.newdawn.slick.svg.NonGeometricData;
/*     */ import org.newdawn.slick.svg.ParsingException;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LineProcessor
/*     */   implements ElementProcessor
/*     */ {
/*     */   private static int processPoly(Polygon poly, Element element, StringTokenizer tokens) throws ParsingException {
/*  32 */     int count = 0;
/*     */     
/*  34 */     while (tokens.hasMoreTokens()) {
/*  35 */       String nextToken = tokens.nextToken();
/*  36 */       if (nextToken.equals("L")) {
/*     */         continue;
/*     */       }
/*  39 */       if (nextToken.equals("z")) {
/*     */         break;
/*     */       }
/*  42 */       if (nextToken.equals("M")) {
/*     */         continue;
/*     */       }
/*  45 */       if (nextToken.equals("C")) {
/*  46 */         return 0;
/*     */       }
/*     */       
/*  49 */       String tokenX = nextToken;
/*  50 */       String tokenY = tokens.nextToken();
/*     */       
/*     */       try {
/*  53 */         float x = Float.parseFloat(tokenX);
/*  54 */         float y = Float.parseFloat(tokenY);
/*     */         
/*  56 */         poly.addPoint(x, y);
/*  57 */         count++;
/*  58 */       } catch (NumberFormatException e) {
/*  59 */         throw new ParsingException(element.getAttribute("id"), "Invalid token in points list", e);
/*     */       } 
/*     */     } 
/*     */     
/*  63 */     return count;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void process(Loader loader, Element element, Diagram diagram, Transform t) throws ParsingException {
/*     */     float x1, y1, x2, y2;
/*  70 */     Transform transform = Util.getTransform(element);
/*  71 */     transform = new Transform(t, transform);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  78 */     if (element.getNodeName().equals("line")) {
/*  79 */       x1 = Float.parseFloat(element.getAttribute("x1"));
/*  80 */       x2 = Float.parseFloat(element.getAttribute("x2"));
/*  81 */       y1 = Float.parseFloat(element.getAttribute("y1"));
/*  82 */       y2 = Float.parseFloat(element.getAttribute("y2"));
/*     */     } else {
/*  84 */       String points = element.getAttribute("d");
/*  85 */       StringTokenizer tokens = new StringTokenizer(points, ", ");
/*  86 */       Polygon poly = new Polygon();
/*  87 */       if (processPoly(poly, element, tokens) == 2) {
/*  88 */         x1 = poly.getPoint(0)[0];
/*  89 */         y1 = poly.getPoint(0)[1];
/*  90 */         x2 = poly.getPoint(1)[0];
/*  91 */         y2 = poly.getPoint(1)[1];
/*     */       } else {
/*     */         return;
/*     */       } 
/*     */     } 
/*     */     
/*  97 */     float[] in = { x1, y1, x2, y2 };
/*  98 */     float[] out = new float[4];
/*     */     
/* 100 */     transform.transform(in, 0, out, 0, 2);
/* 101 */     Line line = new Line(out[0], out[1], out[2], out[3]);
/*     */     
/* 103 */     NonGeometricData data = Util.getNonGeometricData(element);
/* 104 */     data.addAttribute("x1", "" + x1);
/* 105 */     data.addAttribute("x2", "" + x2);
/* 106 */     data.addAttribute("y1", "" + y1);
/* 107 */     data.addAttribute("y2", "" + y2);
/*     */     
/* 109 */     diagram.addFigure(new Figure(2, (Shape)line, data, transform));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean handles(Element element) {
/* 116 */     if (element.getNodeName().equals("line")) {
/* 117 */       return true;
/*     */     }
/* 119 */     if (element.getNodeName().equals("path") && 
/* 120 */       !"arc".equals(element.getAttributeNS("http://sodipodi.sourceforge.net/DTD/sodipodi-0.dtd", "type"))) {
/* 121 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 125 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\svg\inkscape\LineProcessor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */