/*     */ package org.newdawn.slick.svg.inkscape;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import org.newdawn.slick.Color;
/*     */ import org.newdawn.slick.geom.Transform;
/*     */ import org.newdawn.slick.svg.Diagram;
/*     */ import org.newdawn.slick.svg.Gradient;
/*     */ import org.newdawn.slick.svg.Loader;
/*     */ import org.newdawn.slick.svg.ParsingException;
/*     */ import org.newdawn.slick.util.Log;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefsProcessor
/*     */   implements ElementProcessor
/*     */ {
/*     */   public boolean handles(Element element) {
/*  26 */     if (element.getNodeName().equals("defs")) {
/*  27 */       return true;
/*     */     }
/*     */     
/*  30 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void process(Loader loader, Element element, Diagram diagram, Transform transform) throws ParsingException {
/*  37 */     NodeList patterns = element.getElementsByTagName("pattern");
/*     */     
/*  39 */     for (int i = 0; i < patterns.getLength(); i++) {
/*  40 */       Element pattern = (Element)patterns.item(i);
/*  41 */       NodeList list = pattern.getElementsByTagName("image");
/*  42 */       if (list.getLength() == 0) {
/*  43 */         Log.warn("Pattern 1981 does not specify an image. Only image patterns are supported.");
/*     */       } else {
/*     */         
/*  46 */         Element image = (Element)list.item(0);
/*     */         
/*  48 */         String patternName = pattern.getAttribute("id");
/*  49 */         String ref = image.getAttributeNS("http://www.w3.org/1999/xlink", "href");
/*  50 */         diagram.addPatternDef(patternName, ref);
/*     */       } 
/*     */     } 
/*  53 */     NodeList linear = element.getElementsByTagName("linearGradient");
/*  54 */     ArrayList<Gradient> toResolve = new ArrayList();
/*     */     
/*  56 */     for (int j = 0; j < linear.getLength(); j++) {
/*  57 */       Element lin = (Element)linear.item(j);
/*  58 */       String name = lin.getAttribute("id");
/*  59 */       Gradient gradient = new Gradient(name, false);
/*     */       
/*  61 */       gradient.setTransform(Util.getTransform(lin, "gradientTransform"));
/*     */       
/*  63 */       if (stringLength(lin.getAttribute("x1")) > 0) {
/*  64 */         gradient.setX1(Float.parseFloat(lin.getAttribute("x1")));
/*     */       }
/*  66 */       if (stringLength(lin.getAttribute("x2")) > 0) {
/*  67 */         gradient.setX2(Float.parseFloat(lin.getAttribute("x2")));
/*     */       }
/*  69 */       if (stringLength(lin.getAttribute("y1")) > 0) {
/*  70 */         gradient.setY1(Float.parseFloat(lin.getAttribute("y1")));
/*     */       }
/*  72 */       if (stringLength(lin.getAttribute("y2")) > 0) {
/*  73 */         gradient.setY2(Float.parseFloat(lin.getAttribute("y2")));
/*     */       }
/*     */       
/*  76 */       String ref = lin.getAttributeNS("http://www.w3.org/1999/xlink", "href");
/*  77 */       if (stringLength(ref) > 0) {
/*  78 */         gradient.reference(ref.substring(1));
/*  79 */         toResolve.add(gradient);
/*     */       } else {
/*  81 */         NodeList steps = lin.getElementsByTagName("stop");
/*  82 */         for (int m = 0; m < steps.getLength(); m++) {
/*  83 */           Element s = (Element)steps.item(m);
/*  84 */           float offset = Float.parseFloat(s.getAttribute("offset"));
/*     */           
/*  86 */           String colInt = Util.extractStyle(s.getAttribute("style"), "stop-color");
/*  87 */           String opaInt = Util.extractStyle(s.getAttribute("style"), "stop-opacity");
/*     */           
/*  89 */           int col = Integer.parseInt(colInt.substring(1), 16);
/*  90 */           Color stopColor = new Color(col);
/*  91 */           stopColor.a = Float.parseFloat(opaInt);
/*     */           
/*  93 */           gradient.addStep(offset, stopColor);
/*     */         } 
/*     */         
/*  96 */         gradient.getImage();
/*     */       } 
/*     */       
/*  99 */       diagram.addGradient(name, gradient);
/*     */     } 
/*     */     
/* 102 */     NodeList radial = element.getElementsByTagName("radialGradient"); int k;
/* 103 */     for (k = 0; k < radial.getLength(); k++) {
/* 104 */       Element rad = (Element)radial.item(k);
/* 105 */       String name = rad.getAttribute("id");
/* 106 */       Gradient gradient = new Gradient(name, true);
/*     */       
/* 108 */       gradient.setTransform(Util.getTransform(rad, "gradientTransform"));
/*     */       
/* 110 */       if (stringLength(rad.getAttribute("cx")) > 0) {
/* 111 */         gradient.setX1(Float.parseFloat(rad.getAttribute("cx")));
/*     */       }
/* 113 */       if (stringLength(rad.getAttribute("cy")) > 0) {
/* 114 */         gradient.setY1(Float.parseFloat(rad.getAttribute("cy")));
/*     */       }
/* 116 */       if (stringLength(rad.getAttribute("fx")) > 0) {
/* 117 */         gradient.setX2(Float.parseFloat(rad.getAttribute("fx")));
/*     */       }
/* 119 */       if (stringLength(rad.getAttribute("fy")) > 0) {
/* 120 */         gradient.setY2(Float.parseFloat(rad.getAttribute("fy")));
/*     */       }
/* 122 */       if (stringLength(rad.getAttribute("r")) > 0) {
/* 123 */         gradient.setR(Float.parseFloat(rad.getAttribute("r")));
/*     */       }
/*     */       
/* 126 */       String ref = rad.getAttributeNS("http://www.w3.org/1999/xlink", "href");
/* 127 */       if (stringLength(ref) > 0) {
/* 128 */         gradient.reference(ref.substring(1));
/* 129 */         toResolve.add(gradient);
/*     */       } else {
/* 131 */         NodeList steps = rad.getElementsByTagName("stop");
/* 132 */         for (int m = 0; m < steps.getLength(); m++) {
/* 133 */           Element s = (Element)steps.item(m);
/* 134 */           float offset = Float.parseFloat(s.getAttribute("offset"));
/*     */           
/* 136 */           String colInt = Util.extractStyle(s.getAttribute("style"), "stop-color");
/* 137 */           String opaInt = Util.extractStyle(s.getAttribute("style"), "stop-opacity");
/*     */           
/* 139 */           int col = Integer.parseInt(colInt.substring(1), 16);
/* 140 */           Color stopColor = new Color(col);
/* 141 */           stopColor.a = Float.parseFloat(opaInt);
/*     */           
/* 143 */           gradient.addStep(offset, stopColor);
/*     */         } 
/*     */         
/* 146 */         gradient.getImage();
/*     */       } 
/*     */       
/* 149 */       diagram.addGradient(name, gradient);
/*     */     } 
/*     */     
/* 152 */     for (k = 0; k < toResolve.size(); k++) {
/* 153 */       ((Gradient)toResolve.get(k)).resolve(diagram);
/* 154 */       ((Gradient)toResolve.get(k)).getImage();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int stringLength(String value) {
/* 165 */     if (value == null) {
/* 166 */       return 0;
/*     */     }
/*     */     
/* 169 */     return value.length();
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\svg\inkscape\DefsProcessor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */