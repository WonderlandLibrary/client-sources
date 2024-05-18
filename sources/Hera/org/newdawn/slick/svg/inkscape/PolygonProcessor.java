/*     */ package org.newdawn.slick.svg.inkscape;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.StringTokenizer;
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
/*     */ 
/*     */ public class PolygonProcessor
/*     */   implements ElementProcessor
/*     */ {
/*     */   private static int processPoly(Polygon poly, Element element, StringTokenizer tokens) throws ParsingException {
/*  33 */     int count = 0;
/*     */     
/*  35 */     ArrayList pts = new ArrayList();
/*  36 */     boolean moved = false;
/*  37 */     boolean closed = false;
/*     */     
/*  39 */     while (tokens.hasMoreTokens()) {
/*  40 */       String nextToken = tokens.nextToken();
/*  41 */       if (nextToken.equals("L")) {
/*     */         continue;
/*     */       }
/*  44 */       if (nextToken.equals("z")) {
/*  45 */         closed = true;
/*     */         break;
/*     */       } 
/*  48 */       if (nextToken.equals("M")) {
/*  49 */         if (!moved) {
/*  50 */           moved = true;
/*     */           
/*     */           continue;
/*     */         } 
/*  54 */         return 0;
/*     */       } 
/*  56 */       if (nextToken.equals("C")) {
/*  57 */         return 0;
/*     */       }
/*     */       
/*  60 */       String tokenX = nextToken;
/*  61 */       String tokenY = tokens.nextToken();
/*     */       
/*     */       try {
/*  64 */         float x = Float.parseFloat(tokenX);
/*  65 */         float y = Float.parseFloat(tokenY);
/*     */         
/*  67 */         poly.addPoint(x, y);
/*  68 */         count++;
/*  69 */       } catch (NumberFormatException e) {
/*  70 */         throw new ParsingException(element.getAttribute("id"), "Invalid token in points list", e);
/*     */       } 
/*     */     } 
/*     */     
/*  74 */     poly.setClosed(closed);
/*  75 */     return count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void process(Loader loader, Element element, Diagram diagram, Transform t) throws ParsingException {
/*  83 */     Transform transform = Util.getTransform(element);
/*  84 */     transform = new Transform(t, transform);
/*     */     
/*  86 */     String points = element.getAttribute("points");
/*  87 */     if (element.getNodeName().equals("path")) {
/*  88 */       points = element.getAttribute("d");
/*     */     }
/*     */     
/*  91 */     StringTokenizer tokens = new StringTokenizer(points, ", ");
/*  92 */     Polygon poly = new Polygon();
/*  93 */     int count = processPoly(poly, element, tokens);
/*     */     
/*  95 */     NonGeometricData data = Util.getNonGeometricData(element);
/*  96 */     if (count > 3) {
/*  97 */       Shape shape = poly.transform(transform);
/*     */       
/*  99 */       diagram.addFigure(new Figure(5, shape, data, transform));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean handles(Element element) {
/* 107 */     if (element.getNodeName().equals("polygon")) {
/* 108 */       return true;
/*     */     }
/*     */     
/* 111 */     if (element.getNodeName().equals("path") && 
/* 112 */       !"arc".equals(element.getAttributeNS("http://sodipodi.sourceforge.net/DTD/sodipodi-0.dtd", "type"))) {
/* 113 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 117 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\svg\inkscape\PolygonProcessor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */