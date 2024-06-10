/*   1:    */ package org.newdawn.slick.svg.inkscape;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.StringTokenizer;
/*   5:    */ import org.newdawn.slick.geom.Path;
/*   6:    */ import org.newdawn.slick.geom.Shape;
/*   7:    */ import org.newdawn.slick.geom.Transform;
/*   8:    */ import org.newdawn.slick.svg.Diagram;
/*   9:    */ import org.newdawn.slick.svg.Figure;
/*  10:    */ import org.newdawn.slick.svg.Loader;
/*  11:    */ import org.newdawn.slick.svg.NonGeometricData;
/*  12:    */ import org.newdawn.slick.svg.ParsingException;
/*  13:    */ import org.w3c.dom.Element;
/*  14:    */ 
/*  15:    */ public class PathProcessor
/*  16:    */   implements ElementProcessor
/*  17:    */ {
/*  18:    */   private static Path processPoly(Element element, StringTokenizer tokens)
/*  19:    */     throws ParsingException
/*  20:    */   {
/*  21: 32 */     int count = 0;
/*  22:    */     
/*  23: 34 */     ArrayList pts = new ArrayList();
/*  24: 35 */     boolean moved = false;
/*  25: 36 */     boolean reasonToBePath = false;
/*  26: 37 */     Path path = null;
/*  27: 39 */     while (tokens.hasMoreTokens()) {
/*  28:    */       try
/*  29:    */       {
/*  30: 41 */         String nextToken = tokens.nextToken();
/*  31: 42 */         if (nextToken.equals("L"))
/*  32:    */         {
/*  33: 43 */           float x = Float.parseFloat(tokens.nextToken());
/*  34: 44 */           float y = Float.parseFloat(tokens.nextToken());
/*  35: 45 */           path.lineTo(x, y);
/*  36:    */         }
/*  37: 48 */         else if (nextToken.equals("z"))
/*  38:    */         {
/*  39: 49 */           path.close();
/*  40:    */         }
/*  41: 52 */         else if (nextToken.equals("M"))
/*  42:    */         {
/*  43: 53 */           if (!moved)
/*  44:    */           {
/*  45: 54 */             moved = true;
/*  46: 55 */             float x = Float.parseFloat(tokens.nextToken());
/*  47: 56 */             float y = Float.parseFloat(tokens.nextToken());
/*  48: 57 */             path = new Path(x, y);
/*  49:    */           }
/*  50:    */           else
/*  51:    */           {
/*  52: 61 */             reasonToBePath = true;
/*  53: 62 */             float x = Float.parseFloat(tokens.nextToken());
/*  54: 63 */             float y = Float.parseFloat(tokens.nextToken());
/*  55: 64 */             path.startHole(x, y);
/*  56:    */           }
/*  57:    */         }
/*  58: 68 */         else if (nextToken.equals("C"))
/*  59:    */         {
/*  60: 69 */           reasonToBePath = true;
/*  61: 70 */           float cx1 = Float.parseFloat(tokens.nextToken());
/*  62: 71 */           float cy1 = Float.parseFloat(tokens.nextToken());
/*  63: 72 */           float cx2 = Float.parseFloat(tokens.nextToken());
/*  64: 73 */           float cy2 = Float.parseFloat(tokens.nextToken());
/*  65: 74 */           float x = Float.parseFloat(tokens.nextToken());
/*  66: 75 */           float y = Float.parseFloat(tokens.nextToken());
/*  67: 76 */           path.curveTo(x, y, cx1, cy1, cx2, cy2);
/*  68:    */         }
/*  69:    */       }
/*  70:    */       catch (NumberFormatException e)
/*  71:    */       {
/*  72: 80 */         throw new ParsingException(element.getAttribute("id"), "Invalid token in points list", e);
/*  73:    */       }
/*  74:    */     }
/*  75: 84 */     if (!reasonToBePath) {
/*  76: 85 */       return null;
/*  77:    */     }
/*  78: 88 */     return path;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void process(Loader loader, Element element, Diagram diagram, Transform t)
/*  82:    */     throws ParsingException
/*  83:    */   {
/*  84: 96 */     Transform transform = Util.getTransform(element);
/*  85: 97 */     transform = new Transform(t, transform);
/*  86:    */     
/*  87: 99 */     String points = element.getAttribute("points");
/*  88:100 */     if (element.getNodeName().equals("path")) {
/*  89:101 */       points = element.getAttribute("d");
/*  90:    */     }
/*  91:104 */     StringTokenizer tokens = new StringTokenizer(points, ", ");
/*  92:105 */     Path path = processPoly(element, tokens);
/*  93:106 */     NonGeometricData data = Util.getNonGeometricData(element);
/*  94:107 */     if (path != null)
/*  95:    */     {
/*  96:108 */       Shape shape = path.transform(transform);
/*  97:    */       
/*  98:110 */       diagram.addFigure(new Figure(4, shape, data, transform));
/*  99:    */     }
/* 100:    */   }
/* 101:    */   
/* 102:    */   public boolean handles(Element element)
/* 103:    */   {
/* 104:118 */     if ((element.getNodeName().equals("path")) && 
/* 105:119 */       (!"arc".equals(element.getAttributeNS("http://sodipodi.sourceforge.net/DTD/sodipodi-0.dtd", "type")))) {
/* 106:120 */       return true;
/* 107:    */     }
/* 108:124 */     return false;
/* 109:    */   }
/* 110:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.svg.inkscape.PathProcessor
 * JD-Core Version:    0.7.0.1
 */