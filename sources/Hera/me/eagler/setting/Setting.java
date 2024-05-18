/*     */ package me.eagler.setting;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import me.eagler.module.Module;
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
/*     */ public class Setting
/*     */ {
/*     */   public String settingname;
/*     */   public Module module;
/*     */   public String mode;
/*     */   public String firstMode;
/*     */   public ArrayList<String> options;
/*     */   public boolean firstBoolean;
/*     */   public double firstValue;
/*     */   public double minValue;
/*     */   public double maxValue;
/*     */   public boolean onlyint = false;
/*     */   
/*     */   public Setting(String settingname, Module module, String firstMode, ArrayList<String> options) {
/*  29 */     this.settingname = settingname;
/*  30 */     this.module = module;
/*  31 */     this.firstMode = firstMode;
/*  32 */     this.options = options;
/*  33 */     this.mode = "Combobox";
/*     */   }
/*     */   
/*     */   public Setting(String settingname, Module module, boolean firstBoolean) {
/*  37 */     this.settingname = settingname;
/*  38 */     this.module = module;
/*  39 */     this.firstBoolean = firstBoolean;
/*  40 */     this.mode = "Checkbox";
/*     */   }
/*     */ 
/*     */   
/*     */   public Setting(String settingname, Module module, double firstValue, double minValue, double maxValue, boolean onlyint) {
/*  45 */     this.settingname = settingname;
/*  46 */     this.module = module;
/*  47 */     this.firstValue = firstValue;
/*  48 */     this.minValue = minValue;
/*  49 */     this.maxValue = maxValue;
/*  50 */     this.onlyint = onlyint;
/*  51 */     this.mode = "Valueslider";
/*     */   }
/*     */   
/*     */   public String getSettingname() {
/*  55 */     return this.settingname;
/*     */   }
/*     */   
/*     */   public Module getModule() {
/*  59 */     return this.module;
/*     */   }
/*     */   
/*     */   public String getMode() {
/*  63 */     return this.firstMode;
/*     */   }
/*     */   
/*     */   public void setMode(String firstMode) {
/*  67 */     this.firstMode = firstMode;
/*     */   }
/*     */   
/*     */   public double getValue() {
/*  71 */     if (this.onlyint)
/*  72 */       this.firstValue = (int)this.firstValue; 
/*  73 */     return this.firstValue;
/*     */   }
/*     */   
/*     */   public void setValue(double firstValue) {
/*  77 */     this.firstValue = firstValue;
/*     */   }
/*     */   
/*     */   public boolean getBoolean() {
/*  81 */     return this.firstBoolean;
/*     */   }
/*     */   
/*     */   public void setBoolean(boolean firstBoolean) {
/*  85 */     this.firstBoolean = firstBoolean;
/*     */   }
/*     */   
/*     */   public double getMinValue() {
/*  89 */     return this.minValue;
/*     */   }
/*     */   
/*     */   public double getMaxValue() {
/*  93 */     return this.maxValue;
/*     */   }
/*     */   
/*     */   public boolean isCombobox() {
/*  97 */     return this.mode.equalsIgnoreCase("Combobox");
/*     */   }
/*     */   
/*     */   public boolean isCheckbox() {
/* 101 */     return this.mode.equalsIgnoreCase("Checkbox");
/*     */   }
/*     */   
/*     */   public boolean isValueslider() {
/* 105 */     return this.mode.equalsIgnoreCase("Valueslider");
/*     */   }
/*     */   
/*     */   public boolean isOnlyint() {
/* 109 */     return this.onlyint;
/*     */   }
/*     */   
/*     */   public ArrayList<String> getOptions() {
/* 113 */     return this.options;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\setting\Setting.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */