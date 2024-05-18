/*     */ package org.newdawn.slick.svg;
/*     */ 
/*     */ import java.util.Properties;
/*     */ import org.newdawn.slick.Color;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NonGeometricData
/*     */ {
/*     */   public static final String ID = "id";
/*     */   public static final String FILL = "fill";
/*     */   public static final String STROKE = "stroke";
/*     */   public static final String OPACITY = "opacity";
/*     */   public static final String STROKE_WIDTH = "stroke-width";
/*     */   public static final String STROKE_MITERLIMIT = "stroke-miterlimit";
/*     */   public static final String STROKE_DASHARRAY = "stroke-dasharray";
/*     */   public static final String STROKE_DASHOFFSET = "stroke-dashoffset";
/*     */   public static final String STROKE_OPACITY = "stroke-opacity";
/*     */   public static final String NONE = "none";
/*  37 */   private String metaData = "";
/*     */   
/*  39 */   private Properties props = new Properties();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NonGeometricData(String metaData) {
/*  47 */     this.metaData = metaData;
/*  48 */     addAttribute("stroke-width", "1");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String morphColor(String str) {
/*  58 */     if (str.equals("")) {
/*  59 */       return "#000000";
/*     */     }
/*  61 */     if (str.equals("white")) {
/*  62 */       return "#ffffff";
/*     */     }
/*  64 */     if (str.equals("black")) {
/*  65 */       return "#000000";
/*     */     }
/*     */     
/*  68 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAttribute(String attribute, String value) {
/*  78 */     if (value == null) {
/*  79 */       value = "";
/*     */     }
/*     */     
/*  82 */     if (attribute.equals("fill")) {
/*  83 */       value = morphColor(value);
/*     */     }
/*  85 */     if (attribute.equals("stroke-opacity") && 
/*  86 */       value.equals("0")) {
/*  87 */       this.props.setProperty("stroke", "none");
/*     */     }
/*     */     
/*  90 */     if (attribute.equals("stroke-width")) {
/*  91 */       if (value.equals("")) {
/*  92 */         value = "1";
/*     */       }
/*  94 */       if (value.endsWith("px")) {
/*  95 */         value = value.substring(0, value.length() - 2);
/*     */       }
/*     */     } 
/*  98 */     if (attribute.equals("stroke")) {
/*  99 */       if ("none".equals(this.props.getProperty("stroke"))) {
/*     */         return;
/*     */       }
/* 102 */       if ("".equals(this.props.getProperty("stroke"))) {
/*     */         return;
/*     */       }
/* 105 */       value = morphColor(value);
/*     */     } 
/*     */     
/* 108 */     this.props.setProperty(attribute, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isColor(String attribute) {
/* 118 */     return getAttribute(attribute).startsWith("#");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMetaData() {
/* 128 */     return this.metaData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAttribute(String attribute) {
/* 138 */     return this.props.getProperty(attribute);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getAsColor(String attribute) {
/* 148 */     if (!isColor(attribute)) {
/* 149 */       throw new RuntimeException("Attribute " + attribute + " is not specified as a color:" + getAttribute(attribute));
/*     */     }
/*     */     
/* 152 */     int col = Integer.parseInt(getAttribute(attribute).substring(1), 16);
/*     */     
/* 154 */     return new Color(col);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAsReference(String attribute) {
/* 164 */     String value = getAttribute(attribute);
/* 165 */     if (value.length() < 7) {
/* 166 */       return "";
/*     */     }
/*     */     
/* 169 */     value = value.substring(5, value.length() - 1);
/*     */     
/* 171 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getAsFloat(String attribute) {
/* 181 */     String value = getAttribute(attribute);
/* 182 */     if (value == null) {
/* 183 */       return 0.0F;
/*     */     }
/*     */     
/*     */     try {
/* 187 */       return Float.parseFloat(value);
/* 188 */     } catch (NumberFormatException e) {
/* 189 */       throw new RuntimeException("Attribute " + attribute + " is not specified as a float:" + getAttribute(attribute));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFilled() {
/* 199 */     return isColor("fill");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isStroked() {
/* 208 */     return (isColor("stroke") && getAsFloat("stroke-width") > 0.0F);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\svg\NonGeometricData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */