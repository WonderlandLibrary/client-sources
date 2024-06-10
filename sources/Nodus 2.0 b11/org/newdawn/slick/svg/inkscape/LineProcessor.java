/*   1:    */ package org.newdawn.slick.svg.inkscape;
/*   2:    */ 
/*   3:    */ import java.util.StringTokenizer;
/*   4:    */ import org.newdawn.slick.geom.Line;
/*   5:    */ import org.newdawn.slick.geom.Polygon;
/*   6:    */ import org.newdawn.slick.geom.Transform;
/*   7:    */ import org.newdawn.slick.svg.Diagram;
/*   8:    */ import org.newdawn.slick.svg.Figure;
/*   9:    */ import org.newdawn.slick.svg.Loader;
/*  10:    */ import org.newdawn.slick.svg.NonGeometricData;
/*  11:    */ import org.newdawn.slick.svg.ParsingException;
/*  12:    */ import org.w3c.dom.Element;
/*  13:    */ 
/*  14:    */ public class LineProcessor
/*  15:    */   implements ElementProcessor
/*  16:    */ {
/*  17:    */   private static int processPoly(Polygon poly, Element element, StringTokenizer tokens)
/*  18:    */     throws ParsingException
/*  19:    */   {
/*  20: 32 */     int count = 0;
/*  21: 34 */     while (tokens.hasMoreTokens())
/*  22:    */     {
/*  23: 35 */       String nextToken = tokens.nextToken();
/*  24: 36 */       if (!nextToken.equals("L"))
/*  25:    */       {
/*  26: 39 */         if (nextToken.equals("z")) {
/*  27:    */           break;
/*  28:    */         }
/*  29: 42 */         if (!nextToken.equals("M"))
/*  30:    */         {
/*  31: 45 */           if (nextToken.equals("C")) {
/*  32: 46 */             return 0;
/*  33:    */           }
/*  34: 49 */           String tokenX = nextToken;
/*  35: 50 */           String tokenY = tokens.nextToken();
/*  36:    */           try
/*  37:    */           {
/*  38: 53 */             float x = Float.parseFloat(tokenX);
/*  39: 54 */             float y = Float.parseFloat(tokenY);
/*  40:    */             
/*  41: 56 */             poly.addPoint(x, y);
/*  42: 57 */             count++;
/*  43:    */           }
/*  44:    */           catch (NumberFormatException e)
/*  45:    */           {
/*  46: 59 */             throw new ParsingException(element.getAttribute("id"), "Invalid token in points list", e);
/*  47:    */           }
/*  48:    */         }
/*  49:    */       }
/*  50:    */     }
/*  51: 63 */     return count;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void process(Loader loader, Element element, Diagram diagram, Transform t)
/*  55:    */     throws ParsingException
/*  56:    */   {
/*  57: 70 */     Transform transform = Util.getTransform(element);
/*  58: 71 */     transform = new Transform(t, transform);
/*  59:    */     float y2;
/*  60: 78 */     if (element.getNodeName().equals("line"))
/*  61:    */     {
/*  62: 79 */       float x1 = Float.parseFloat(element.getAttribute("x1"));
/*  63: 80 */       float x2 = Float.parseFloat(element.getAttribute("x2"));
/*  64: 81 */       float y1 = Float.parseFloat(element.getAttribute("y1"));
/*  65: 82 */       y2 = Float.parseFloat(element.getAttribute("y2"));
/*  66:    */     }
/*  67:    */     else
/*  68:    */     {
/*  69: 84 */       String points = element.getAttribute("d");
/*  70: 85 */       StringTokenizer tokens = new StringTokenizer(points, ", ");
/*  71: 86 */       Polygon poly = new Polygon();
/*  72:    */       float y2;
/*  73: 87 */       if (processPoly(poly, element, tokens) == 2)
/*  74:    */       {
/*  75: 88 */         float x1 = poly.getPoint(0)[0];
/*  76: 89 */         float y1 = poly.getPoint(0)[1];
/*  77: 90 */         float x2 = poly.getPoint(1)[0];
/*  78: 91 */         y2 = poly.getPoint(1)[1];
/*  79:    */       }
/*  80:    */       else
/*  81:    */       {
/*  82:    */         return;
/*  83:    */       }
/*  84:    */     }
/*  85:    */     float y2;
/*  86:    */     float x2;
/*  87:    */     float y1;
/*  88:    */     float x1;
/*  89: 97 */     float[] in = { x1, y1, x2, y2 };
/*  90: 98 */     float[] out = new float[4];
/*  91:    */     
/*  92:100 */     transform.transform(in, 0, out, 0, 2);
/*  93:101 */     Line line = new Line(out[0], out[1], out[2], out[3]);
/*  94:    */     
/*  95:103 */     NonGeometricData data = Util.getNonGeometricData(element);
/*  96:104 */     data.addAttribute("x1", x1);
/*  97:105 */     data.addAttribute("x2", x2);
/*  98:106 */     data.addAttribute("y1", y1);
/*  99:107 */     data.addAttribute("y2", y2);
/* 100:    */     
/* 101:109 */     diagram.addFigure(new Figure(2, line, data, transform));
/* 102:    */   }
/* 103:    */   
/* 104:    */   public boolean handles(Element element)
/* 105:    */   {
/* 106:116 */     if (element.getNodeName().equals("line")) {
/* 107:117 */       return true;
/* 108:    */     }
/* 109:119 */     if ((element.getNodeName().equals("path")) && 
/* 110:120 */       (!"arc".equals(element.getAttributeNS("http://sodipodi.sourceforge.net/DTD/sodipodi-0.dtd", "type")))) {
/* 111:121 */       return true;
/* 112:    */     }
/* 113:125 */     return false;
/* 114:    */   }
/* 115:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.svg.inkscape.LineProcessor
 * JD-Core Version:    0.7.0.1
 */