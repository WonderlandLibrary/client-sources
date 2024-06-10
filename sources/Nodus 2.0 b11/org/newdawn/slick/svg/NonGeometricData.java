/*   1:    */ package org.newdawn.slick.svg;
/*   2:    */ 
/*   3:    */ import java.util.Properties;
/*   4:    */ import org.newdawn.slick.Color;
/*   5:    */ 
/*   6:    */ public class NonGeometricData
/*   7:    */ {
/*   8:    */   public static final String ID = "id";
/*   9:    */   public static final String FILL = "fill";
/*  10:    */   public static final String STROKE = "stroke";
/*  11:    */   public static final String OPACITY = "opacity";
/*  12:    */   public static final String STROKE_WIDTH = "stroke-width";
/*  13:    */   public static final String STROKE_MITERLIMIT = "stroke-miterlimit";
/*  14:    */   public static final String STROKE_DASHARRAY = "stroke-dasharray";
/*  15:    */   public static final String STROKE_DASHOFFSET = "stroke-dashoffset";
/*  16:    */   public static final String STROKE_OPACITY = "stroke-opacity";
/*  17:    */   public static final String NONE = "none";
/*  18: 37 */   private String metaData = "";
/*  19: 39 */   private Properties props = new Properties();
/*  20:    */   
/*  21:    */   public NonGeometricData(String metaData)
/*  22:    */   {
/*  23: 47 */     this.metaData = metaData;
/*  24: 48 */     addAttribute("stroke-width", "1");
/*  25:    */   }
/*  26:    */   
/*  27:    */   private String morphColor(String str)
/*  28:    */   {
/*  29: 58 */     if (str.equals("")) {
/*  30: 59 */       return "#000000";
/*  31:    */     }
/*  32: 61 */     if (str.equals("white")) {
/*  33: 62 */       return "#ffffff";
/*  34:    */     }
/*  35: 64 */     if (str.equals("black")) {
/*  36: 65 */       return "#000000";
/*  37:    */     }
/*  38: 68 */     return str;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void addAttribute(String attribute, String value)
/*  42:    */   {
/*  43: 78 */     if (value == null) {
/*  44: 79 */       value = "";
/*  45:    */     }
/*  46: 82 */     if (attribute.equals("fill")) {
/*  47: 83 */       value = morphColor(value);
/*  48:    */     }
/*  49: 85 */     if ((attribute.equals("stroke-opacity")) && 
/*  50: 86 */       (value.equals("0"))) {
/*  51: 87 */       this.props.setProperty("stroke", "none");
/*  52:    */     }
/*  53: 90 */     if (attribute.equals("stroke-width"))
/*  54:    */     {
/*  55: 91 */       if (value.equals("")) {
/*  56: 92 */         value = "1";
/*  57:    */       }
/*  58: 94 */       if (value.endsWith("px")) {
/*  59: 95 */         value = value.substring(0, value.length() - 2);
/*  60:    */       }
/*  61:    */     }
/*  62: 98 */     if (attribute.equals("stroke"))
/*  63:    */     {
/*  64: 99 */       if ("none".equals(this.props.getProperty("stroke"))) {
/*  65:100 */         return;
/*  66:    */       }
/*  67:102 */       if ("".equals(this.props.getProperty("stroke"))) {
/*  68:103 */         return;
/*  69:    */       }
/*  70:105 */       value = morphColor(value);
/*  71:    */     }
/*  72:108 */     this.props.setProperty(attribute, value);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public boolean isColor(String attribute)
/*  76:    */   {
/*  77:118 */     return getAttribute(attribute).startsWith("#");
/*  78:    */   }
/*  79:    */   
/*  80:    */   public String getMetaData()
/*  81:    */   {
/*  82:128 */     return this.metaData;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public String getAttribute(String attribute)
/*  86:    */   {
/*  87:138 */     return this.props.getProperty(attribute);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public Color getAsColor(String attribute)
/*  91:    */   {
/*  92:148 */     if (!isColor(attribute)) {
/*  93:149 */       throw new RuntimeException("Attribute " + attribute + " is not specified as a color:" + getAttribute(attribute));
/*  94:    */     }
/*  95:152 */     int col = Integer.parseInt(getAttribute(attribute).substring(1), 16);
/*  96:    */     
/*  97:154 */     return new Color(col);
/*  98:    */   }
/*  99:    */   
/* 100:    */   public String getAsReference(String attribute)
/* 101:    */   {
/* 102:164 */     String value = getAttribute(attribute);
/* 103:165 */     if (value.length() < 7) {
/* 104:166 */       return "";
/* 105:    */     }
/* 106:169 */     value = value.substring(5, value.length() - 1);
/* 107:    */     
/* 108:171 */     return value;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public float getAsFloat(String attribute)
/* 112:    */   {
/* 113:181 */     String value = getAttribute(attribute);
/* 114:182 */     if (value == null) {
/* 115:183 */       return 0.0F;
/* 116:    */     }
/* 117:    */     try
/* 118:    */     {
/* 119:187 */       return Float.parseFloat(value);
/* 120:    */     }
/* 121:    */     catch (NumberFormatException e)
/* 122:    */     {
/* 123:189 */       throw new RuntimeException("Attribute " + attribute + " is not specified as a float:" + getAttribute(attribute));
/* 124:    */     }
/* 125:    */   }
/* 126:    */   
/* 127:    */   public boolean isFilled()
/* 128:    */   {
/* 129:199 */     return isColor("fill");
/* 130:    */   }
/* 131:    */   
/* 132:    */   public boolean isStroked()
/* 133:    */   {
/* 134:208 */     return (isColor("stroke")) && (getAsFloat("stroke-width") > 0.0F);
/* 135:    */   }
/* 136:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.svg.NonGeometricData
 * JD-Core Version:    0.7.0.1
 */