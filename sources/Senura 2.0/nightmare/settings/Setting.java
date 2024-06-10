/*     */ package nightmare.settings;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import nightmare.module.Module;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Setting
/*     */ {
/*     */   private String name;
/*     */   private Module parent;
/*     */   private String mode;
/*     */   private String sval;
/*     */   private ArrayList<String> options;
/*     */   private boolean bval;
/*     */   private double dval;
/*     */   private double min;
/*     */   private double max;
/*     */   private boolean onlyint = false;
/*     */   
/*     */   public Setting(String name, Module parent, String sval, ArrayList<String> options) {
/*  25 */     this.name = name;
/*  26 */     this.parent = parent;
/*  27 */     this.sval = sval;
/*  28 */     this.options = options;
/*  29 */     this.mode = "Combo";
/*     */   }
/*     */   
/*     */   public Setting(String name, Module parent, boolean bval) {
/*  33 */     this.name = name;
/*  34 */     this.parent = parent;
/*  35 */     this.bval = bval;
/*  36 */     this.mode = "Check";
/*     */   }
/*     */   
/*     */   public Setting(String name, Module parent, double dval, double min, double max, boolean onlyint) {
/*  40 */     this.name = name;
/*  41 */     this.parent = parent;
/*  42 */     this.dval = dval;
/*  43 */     this.min = min;
/*  44 */     this.max = max;
/*  45 */     this.onlyint = onlyint;
/*  46 */     this.mode = "Slider";
/*     */   }
/*     */   
/*     */   public String getName() {
/*  50 */     return this.name;
/*     */   }
/*     */   
/*     */   public Module getParentMod() {
/*  54 */     return this.parent;
/*     */   }
/*     */   
/*     */   public String getValString() {
/*  58 */     return this.sval;
/*     */   }
/*     */   
/*     */   public void setValString(String in) {
/*  62 */     this.sval = in;
/*     */   }
/*     */   
/*     */   public ArrayList<String> getOptions() {
/*  66 */     return this.options;
/*     */   }
/*     */   
/*     */   public boolean getValBoolean() {
/*  70 */     return this.bval;
/*     */   }
/*     */   
/*     */   public void setValBoolean(boolean in) {
/*  74 */     this.bval = in;
/*     */   }
/*     */   
/*     */   public double getValDouble() {
/*  78 */     if (this.onlyint) {
/*  79 */       this.dval = (int)this.dval;
/*     */     }
/*  81 */     return this.dval;
/*     */   }
/*     */   
/*     */   public void setValDouble(double in) {
/*  85 */     this.dval = in;
/*     */   }
/*     */   
/*     */   public double getMin() {
/*  89 */     return this.min;
/*     */   }
/*     */   
/*     */   public double getMax() {
/*  93 */     return this.max;
/*     */   }
/*     */   
/*     */   public boolean isCombo() {
/*  97 */     return this.mode.equalsIgnoreCase("Combo");
/*     */   }
/*     */   
/*     */   public boolean isCheck() {
/* 101 */     return this.mode.equalsIgnoreCase("Check");
/*     */   }
/*     */   
/*     */   public boolean isSlider() {
/* 105 */     return this.mode.equalsIgnoreCase("Slider");
/*     */   }
/*     */   
/*     */   public boolean onlyInt() {
/* 109 */     return this.onlyint;
/*     */   }
/*     */   
/*     */   public String getMode() {
/* 113 */     return this.mode;
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\settings\Setting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */