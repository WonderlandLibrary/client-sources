/*   1:    */ package org.newdawn.slick.svg.inkscape;
/*   2:    */ 
/*   3:    */ import java.util.StringTokenizer;
/*   4:    */ import org.newdawn.slick.geom.Transform;
/*   5:    */ import org.newdawn.slick.svg.NonGeometricData;
/*   6:    */ import org.newdawn.slick.svg.ParsingException;
/*   7:    */ import org.w3c.dom.Element;
/*   8:    */ 
/*   9:    */ public class Util
/*  10:    */ {
/*  11:    */   public static final String INKSCAPE = "http://www.inkscape.org/namespaces/inkscape";
/*  12:    */   public static final String SODIPODI = "http://sodipodi.sourceforge.net/DTD/sodipodi-0.dtd";
/*  13:    */   public static final String XLINK = "http://www.w3.org/1999/xlink";
/*  14:    */   
/*  15:    */   static NonGeometricData getNonGeometricData(Element element)
/*  16:    */   {
/*  17: 30 */     String meta = getMetaData(element);
/*  18:    */     
/*  19: 32 */     NonGeometricData data = new InkscapeNonGeometricData(meta, element);
/*  20: 33 */     data.addAttribute("id", element.getAttribute("id"));
/*  21: 34 */     data.addAttribute("fill", getStyle(element, "fill"));
/*  22: 35 */     data.addAttribute("stroke", getStyle(element, "stroke"));
/*  23: 36 */     data.addAttribute("opacity", getStyle(element, "opacity"));
/*  24: 37 */     data.addAttribute("stroke-dasharray", getStyle(element, "stroke-dasharray"));
/*  25: 38 */     data.addAttribute("stroke-dashoffset", getStyle(element, "stroke-dashoffset"));
/*  26: 39 */     data.addAttribute("stroke-miterlimit", getStyle(element, "stroke-miterlimit"));
/*  27: 40 */     data.addAttribute("stroke-opacity", getStyle(element, "stroke-opacity"));
/*  28: 41 */     data.addAttribute("stroke-width", getStyle(element, "stroke-width"));
/*  29:    */     
/*  30: 43 */     return data;
/*  31:    */   }
/*  32:    */   
/*  33:    */   static String getMetaData(Element element)
/*  34:    */   {
/*  35: 54 */     String label = element.getAttributeNS("http://www.inkscape.org/namespaces/inkscape", "label");
/*  36: 55 */     if ((label != null) && (!label.equals(""))) {
/*  37: 56 */       return label;
/*  38:    */     }
/*  39: 59 */     return element.getAttribute("id");
/*  40:    */   }
/*  41:    */   
/*  42:    */   static String getStyle(Element element, String styleName)
/*  43:    */   {
/*  44: 70 */     String value = element.getAttribute(styleName);
/*  45: 72 */     if ((value != null) && (value.length() > 0)) {
/*  46: 73 */       return value;
/*  47:    */     }
/*  48: 76 */     String style = element.getAttribute("style");
/*  49: 77 */     return extractStyle(style, styleName);
/*  50:    */   }
/*  51:    */   
/*  52:    */   static String extractStyle(String style, String attribute)
/*  53:    */   {
/*  54: 88 */     if (style == null) {
/*  55: 89 */       return "";
/*  56:    */     }
/*  57: 92 */     StringTokenizer tokens = new StringTokenizer(style, ";");
/*  58: 94 */     while (tokens.hasMoreTokens())
/*  59:    */     {
/*  60: 95 */       String token = tokens.nextToken();
/*  61: 96 */       String key = token.substring(0, token.indexOf(':'));
/*  62: 97 */       if (key.equals(attribute)) {
/*  63: 98 */         return token.substring(token.indexOf(':') + 1);
/*  64:    */       }
/*  65:    */     }
/*  66:102 */     return "";
/*  67:    */   }
/*  68:    */   
/*  69:    */   static Transform getTransform(Element element)
/*  70:    */   {
/*  71:112 */     return getTransform(element, "transform");
/*  72:    */   }
/*  73:    */   
/*  74:    */   static Transform getTransform(Element element, String attribute)
/*  75:    */   {
/*  76:123 */     String str = element.getAttribute(attribute);
/*  77:124 */     if (str == null) {
/*  78:125 */       return new Transform();
/*  79:    */     }
/*  80:128 */     if (str.equals("")) {
/*  81:129 */       return new Transform();
/*  82:    */     }
/*  83:130 */     if (str.startsWith("translate"))
/*  84:    */     {
/*  85:131 */       str = str.substring(0, str.length() - 1);
/*  86:132 */       str = str.substring("translate(".length());
/*  87:133 */       StringTokenizer tokens = new StringTokenizer(str, ", ");
/*  88:134 */       float x = Float.parseFloat(tokens.nextToken());
/*  89:135 */       float y = Float.parseFloat(tokens.nextToken());
/*  90:    */       
/*  91:137 */       return Transform.createTranslateTransform(x, y);
/*  92:    */     }
/*  93:138 */     if (str.startsWith("matrix"))
/*  94:    */     {
/*  95:139 */       float[] pose = new float[6];
/*  96:140 */       str = str.substring(0, str.length() - 1);
/*  97:141 */       str = str.substring("matrix(".length());
/*  98:142 */       StringTokenizer tokens = new StringTokenizer(str, ", ");
/*  99:143 */       float[] tr = new float[6];
/* 100:144 */       for (int j = 0; j < tr.length; j++) {
/* 101:145 */         tr[j] = Float.parseFloat(tokens.nextToken());
/* 102:    */       }
/* 103:148 */       pose[0] = tr[0];
/* 104:149 */       pose[1] = tr[2];
/* 105:150 */       pose[2] = tr[4];
/* 106:151 */       pose[3] = tr[1];
/* 107:152 */       pose[4] = tr[3];
/* 108:153 */       pose[5] = tr[5];
/* 109:    */       
/* 110:155 */       return new Transform(pose);
/* 111:    */     }
/* 112:158 */     return new Transform();
/* 113:    */   }
/* 114:    */   
/* 115:    */   static float getFloatAttribute(Element element, String attr)
/* 116:    */     throws ParsingException
/* 117:    */   {
/* 118:171 */     String cx = element.getAttribute(attr);
/* 119:172 */     if ((cx == null) || (cx.equals(""))) {
/* 120:173 */       cx = element.getAttributeNS("http://sodipodi.sourceforge.net/DTD/sodipodi-0.dtd", attr);
/* 121:    */     }
/* 122:    */     try
/* 123:    */     {
/* 124:177 */       return Float.parseFloat(cx);
/* 125:    */     }
/* 126:    */     catch (NumberFormatException e)
/* 127:    */     {
/* 128:179 */       throw new ParsingException(element, "Invalid value for: " + attr, e);
/* 129:    */     }
/* 130:    */   }
/* 131:    */   
/* 132:    */   public static String getAsReference(String value)
/* 133:    */   {
/* 134:190 */     if (value.length() < 2) {
/* 135:191 */       return "";
/* 136:    */     }
/* 137:194 */     value = value.substring(1, value.length());
/* 138:    */     
/* 139:196 */     return value;
/* 140:    */   }
/* 141:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.svg.inkscape.Util
 * JD-Core Version:    0.7.0.1
 */