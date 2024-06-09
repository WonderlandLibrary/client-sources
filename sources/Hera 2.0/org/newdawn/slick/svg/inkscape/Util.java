/*     */ package org.newdawn.slick.svg.inkscape;
/*     */ 
/*     */ import java.util.StringTokenizer;
/*     */ import org.newdawn.slick.geom.Transform;
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
/*     */ public class Util
/*     */ {
/*     */   public static final String INKSCAPE = "http://www.inkscape.org/namespaces/inkscape";
/*     */   public static final String SODIPODI = "http://sodipodi.sourceforge.net/DTD/sodipodi-0.dtd";
/*     */   public static final String XLINK = "http://www.w3.org/1999/xlink";
/*     */   
/*     */   static NonGeometricData getNonGeometricData(Element element) {
/*  30 */     String meta = getMetaData(element);
/*     */     
/*  32 */     NonGeometricData data = new InkscapeNonGeometricData(meta, element);
/*  33 */     data.addAttribute("id", element.getAttribute("id"));
/*  34 */     data.addAttribute("fill", getStyle(element, "fill"));
/*  35 */     data.addAttribute("stroke", getStyle(element, "stroke"));
/*  36 */     data.addAttribute("opacity", getStyle(element, "opacity"));
/*  37 */     data.addAttribute("stroke-dasharray", getStyle(element, "stroke-dasharray"));
/*  38 */     data.addAttribute("stroke-dashoffset", getStyle(element, "stroke-dashoffset"));
/*  39 */     data.addAttribute("stroke-miterlimit", getStyle(element, "stroke-miterlimit"));
/*  40 */     data.addAttribute("stroke-opacity", getStyle(element, "stroke-opacity"));
/*  41 */     data.addAttribute("stroke-width", getStyle(element, "stroke-width"));
/*     */     
/*  43 */     return data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String getMetaData(Element element) {
/*  54 */     String label = element.getAttributeNS("http://www.inkscape.org/namespaces/inkscape", "label");
/*  55 */     if (label != null && !label.equals("")) {
/*  56 */       return label;
/*     */     }
/*     */     
/*  59 */     return element.getAttribute("id");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String getStyle(Element element, String styleName) {
/*  70 */     String value = element.getAttribute(styleName);
/*     */     
/*  72 */     if (value != null && value.length() > 0) {
/*  73 */       return value;
/*     */     }
/*     */     
/*  76 */     String style = element.getAttribute("style");
/*  77 */     return extractStyle(style, styleName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String extractStyle(String style, String attribute) {
/*  88 */     if (style == null) {
/*  89 */       return "";
/*     */     }
/*     */     
/*  92 */     StringTokenizer tokens = new StringTokenizer(style, ";");
/*     */     
/*  94 */     while (tokens.hasMoreTokens()) {
/*  95 */       String token = tokens.nextToken();
/*  96 */       String key = token.substring(0, token.indexOf(':'));
/*  97 */       if (key.equals(attribute)) {
/*  98 */         return token.substring(token.indexOf(':') + 1);
/*     */       }
/*     */     } 
/*     */     
/* 102 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Transform getTransform(Element element) {
/* 112 */     return getTransform(element, "transform");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Transform getTransform(Element element, String attribute) {
/* 123 */     String str = element.getAttribute(attribute);
/* 124 */     if (str == null) {
/* 125 */       return new Transform();
/*     */     }
/*     */     
/* 128 */     if (str.equals(""))
/* 129 */       return new Transform(); 
/* 130 */     if (str.startsWith("translate")) {
/* 131 */       str = str.substring(0, str.length() - 1);
/* 132 */       str = str.substring("translate(".length());
/* 133 */       StringTokenizer tokens = new StringTokenizer(str, ", ");
/* 134 */       float x = Float.parseFloat(tokens.nextToken());
/* 135 */       float y = Float.parseFloat(tokens.nextToken());
/*     */       
/* 137 */       return Transform.createTranslateTransform(x, y);
/* 138 */     }  if (str.startsWith("matrix")) {
/* 139 */       float[] pose = new float[6];
/* 140 */       str = str.substring(0, str.length() - 1);
/* 141 */       str = str.substring("matrix(".length());
/* 142 */       StringTokenizer tokens = new StringTokenizer(str, ", ");
/* 143 */       float[] tr = new float[6];
/* 144 */       for (int j = 0; j < tr.length; j++) {
/* 145 */         tr[j] = Float.parseFloat(tokens.nextToken());
/*     */       }
/*     */       
/* 148 */       pose[0] = tr[0];
/* 149 */       pose[1] = tr[2];
/* 150 */       pose[2] = tr[4];
/* 151 */       pose[3] = tr[1];
/* 152 */       pose[4] = tr[3];
/* 153 */       pose[5] = tr[5];
/*     */       
/* 155 */       return new Transform(pose);
/*     */     } 
/*     */     
/* 158 */     return new Transform();
/*     */   }
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
/*     */   static float getFloatAttribute(Element element, String attr) throws ParsingException {
/* 171 */     String cx = element.getAttribute(attr);
/* 172 */     if (cx == null || cx.equals("")) {
/* 173 */       cx = element.getAttributeNS("http://sodipodi.sourceforge.net/DTD/sodipodi-0.dtd", attr);
/*     */     }
/*     */     
/*     */     try {
/* 177 */       return Float.parseFloat(cx);
/* 178 */     } catch (NumberFormatException e) {
/* 179 */       throw new ParsingException(element, "Invalid value for: " + attr, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getAsReference(String value) {
/* 190 */     if (value.length() < 2) {
/* 191 */       return "";
/*     */     }
/*     */     
/* 194 */     value = value.substring(1, value.length());
/*     */     
/* 196 */     return value;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\svg\inkscape\Util.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */