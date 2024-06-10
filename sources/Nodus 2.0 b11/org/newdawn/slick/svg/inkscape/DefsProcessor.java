/*   1:    */ package org.newdawn.slick.svg.inkscape;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import org.newdawn.slick.Color;
/*   5:    */ import org.newdawn.slick.geom.Transform;
/*   6:    */ import org.newdawn.slick.svg.Diagram;
/*   7:    */ import org.newdawn.slick.svg.Gradient;
/*   8:    */ import org.newdawn.slick.svg.Loader;
/*   9:    */ import org.newdawn.slick.svg.ParsingException;
/*  10:    */ import org.newdawn.slick.util.Log;
/*  11:    */ import org.w3c.dom.Element;
/*  12:    */ import org.w3c.dom.NodeList;
/*  13:    */ 
/*  14:    */ public class DefsProcessor
/*  15:    */   implements ElementProcessor
/*  16:    */ {
/*  17:    */   public boolean handles(Element element)
/*  18:    */   {
/*  19: 26 */     if (element.getNodeName().equals("defs")) {
/*  20: 27 */       return true;
/*  21:    */     }
/*  22: 30 */     return false;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public void process(Loader loader, Element element, Diagram diagram, Transform transform)
/*  26:    */     throws ParsingException
/*  27:    */   {
/*  28: 37 */     NodeList patterns = element.getElementsByTagName("pattern");
/*  29: 39 */     for (int i = 0; i < patterns.getLength(); i++)
/*  30:    */     {
/*  31: 40 */       Element pattern = (Element)patterns.item(i);
/*  32: 41 */       NodeList list = pattern.getElementsByTagName("image");
/*  33: 42 */       if (list.getLength() == 0)
/*  34:    */       {
/*  35: 43 */         Log.warn("Pattern 1981 does not specify an image. Only image patterns are supported.");
/*  36:    */       }
/*  37:    */       else
/*  38:    */       {
/*  39: 46 */         Element image = (Element)list.item(0);
/*  40:    */         
/*  41: 48 */         String patternName = pattern.getAttribute("id");
/*  42: 49 */         String ref = image.getAttributeNS("http://www.w3.org/1999/xlink", "href");
/*  43: 50 */         diagram.addPatternDef(patternName, ref);
/*  44:    */       }
/*  45:    */     }
/*  46: 53 */     NodeList linear = element.getElementsByTagName("linearGradient");
/*  47: 54 */     ArrayList toResolve = new ArrayList();
/*  48: 56 */     for (int i = 0; i < linear.getLength(); i++)
/*  49:    */     {
/*  50: 57 */       Element lin = (Element)linear.item(i);
/*  51: 58 */       String name = lin.getAttribute("id");
/*  52: 59 */       Gradient gradient = new Gradient(name, false);
/*  53:    */       
/*  54: 61 */       gradient.setTransform(Util.getTransform(lin, "gradientTransform"));
/*  55: 63 */       if (stringLength(lin.getAttribute("x1")) > 0) {
/*  56: 64 */         gradient.setX1(Float.parseFloat(lin.getAttribute("x1")));
/*  57:    */       }
/*  58: 66 */       if (stringLength(lin.getAttribute("x2")) > 0) {
/*  59: 67 */         gradient.setX2(Float.parseFloat(lin.getAttribute("x2")));
/*  60:    */       }
/*  61: 69 */       if (stringLength(lin.getAttribute("y1")) > 0) {
/*  62: 70 */         gradient.setY1(Float.parseFloat(lin.getAttribute("y1")));
/*  63:    */       }
/*  64: 72 */       if (stringLength(lin.getAttribute("y2")) > 0) {
/*  65: 73 */         gradient.setY2(Float.parseFloat(lin.getAttribute("y2")));
/*  66:    */       }
/*  67: 76 */       String ref = lin.getAttributeNS("http://www.w3.org/1999/xlink", "href");
/*  68: 77 */       if (stringLength(ref) > 0)
/*  69:    */       {
/*  70: 78 */         gradient.reference(ref.substring(1));
/*  71: 79 */         toResolve.add(gradient);
/*  72:    */       }
/*  73:    */       else
/*  74:    */       {
/*  75: 81 */         NodeList steps = lin.getElementsByTagName("stop");
/*  76: 82 */         for (int j = 0; j < steps.getLength(); j++)
/*  77:    */         {
/*  78: 83 */           Element s = (Element)steps.item(j);
/*  79: 84 */           float offset = Float.parseFloat(s.getAttribute("offset"));
/*  80:    */           
/*  81: 86 */           String colInt = Util.extractStyle(s.getAttribute("style"), "stop-color");
/*  82: 87 */           String opaInt = Util.extractStyle(s.getAttribute("style"), "stop-opacity");
/*  83:    */           
/*  84: 89 */           int col = Integer.parseInt(colInt.substring(1), 16);
/*  85: 90 */           Color stopColor = new Color(col);
/*  86: 91 */           stopColor.a = Float.parseFloat(opaInt);
/*  87:    */           
/*  88: 93 */           gradient.addStep(offset, stopColor);
/*  89:    */         }
/*  90: 96 */         gradient.getImage();
/*  91:    */       }
/*  92: 99 */       diagram.addGradient(name, gradient);
/*  93:    */     }
/*  94:102 */     NodeList radial = element.getElementsByTagName("radialGradient");
/*  95:103 */     for (int i = 0; i < radial.getLength(); i++)
/*  96:    */     {
/*  97:104 */       Element rad = (Element)radial.item(i);
/*  98:105 */       String name = rad.getAttribute("id");
/*  99:106 */       Gradient gradient = new Gradient(name, true);
/* 100:    */       
/* 101:108 */       gradient.setTransform(Util.getTransform(rad, "gradientTransform"));
/* 102:110 */       if (stringLength(rad.getAttribute("cx")) > 0) {
/* 103:111 */         gradient.setX1(Float.parseFloat(rad.getAttribute("cx")));
/* 104:    */       }
/* 105:113 */       if (stringLength(rad.getAttribute("cy")) > 0) {
/* 106:114 */         gradient.setY1(Float.parseFloat(rad.getAttribute("cy")));
/* 107:    */       }
/* 108:116 */       if (stringLength(rad.getAttribute("fx")) > 0) {
/* 109:117 */         gradient.setX2(Float.parseFloat(rad.getAttribute("fx")));
/* 110:    */       }
/* 111:119 */       if (stringLength(rad.getAttribute("fy")) > 0) {
/* 112:120 */         gradient.setY2(Float.parseFloat(rad.getAttribute("fy")));
/* 113:    */       }
/* 114:122 */       if (stringLength(rad.getAttribute("r")) > 0) {
/* 115:123 */         gradient.setR(Float.parseFloat(rad.getAttribute("r")));
/* 116:    */       }
/* 117:126 */       String ref = rad.getAttributeNS("http://www.w3.org/1999/xlink", "href");
/* 118:127 */       if (stringLength(ref) > 0)
/* 119:    */       {
/* 120:128 */         gradient.reference(ref.substring(1));
/* 121:129 */         toResolve.add(gradient);
/* 122:    */       }
/* 123:    */       else
/* 124:    */       {
/* 125:131 */         NodeList steps = rad.getElementsByTagName("stop");
/* 126:132 */         for (int j = 0; j < steps.getLength(); j++)
/* 127:    */         {
/* 128:133 */           Element s = (Element)steps.item(j);
/* 129:134 */           float offset = Float.parseFloat(s.getAttribute("offset"));
/* 130:    */           
/* 131:136 */           String colInt = Util.extractStyle(s.getAttribute("style"), "stop-color");
/* 132:137 */           String opaInt = Util.extractStyle(s.getAttribute("style"), "stop-opacity");
/* 133:    */           
/* 134:139 */           int col = Integer.parseInt(colInt.substring(1), 16);
/* 135:140 */           Color stopColor = new Color(col);
/* 136:141 */           stopColor.a = Float.parseFloat(opaInt);
/* 137:    */           
/* 138:143 */           gradient.addStep(offset, stopColor);
/* 139:    */         }
/* 140:146 */         gradient.getImage();
/* 141:    */       }
/* 142:149 */       diagram.addGradient(name, gradient);
/* 143:    */     }
/* 144:152 */     for (int i = 0; i < toResolve.size(); i++)
/* 145:    */     {
/* 146:153 */       ((Gradient)toResolve.get(i)).resolve(diagram);
/* 147:154 */       ((Gradient)toResolve.get(i)).getImage();
/* 148:    */     }
/* 149:    */   }
/* 150:    */   
/* 151:    */   private int stringLength(String value)
/* 152:    */   {
/* 153:165 */     if (value == null) {
/* 154:166 */       return 0;
/* 155:    */     }
/* 156:169 */     return value.length();
/* 157:    */   }
/* 158:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.svg.inkscape.DefsProcessor
 * JD-Core Version:    0.7.0.1
 */