/*     */ package org.newdawn.slick;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import javax.jnlp.ServiceManager;
/*     */ import org.newdawn.slick.muffin.FileMuffin;
/*     */ import org.newdawn.slick.muffin.Muffin;
/*     */ import org.newdawn.slick.muffin.WebstartMuffin;
/*     */ import org.newdawn.slick.util.Log;
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
/*     */ public class SavedState
/*     */ {
/*     */   private String fileName;
/*     */   private Muffin muffin;
/*  26 */   private HashMap numericData = new HashMap<Object, Object>();
/*     */   
/*  28 */   private HashMap stringData = new HashMap<Object, Object>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SavedState(String fileName) throws SlickException {
/*  39 */     this.fileName = fileName;
/*     */     
/*  41 */     if (isWebstartAvailable()) {
/*  42 */       this.muffin = (Muffin)new WebstartMuffin();
/*     */     } else {
/*     */       
/*  45 */       this.muffin = (Muffin)new FileMuffin();
/*     */     } 
/*     */     
/*     */     try {
/*  49 */       load();
/*  50 */     } catch (IOException e) {
/*  51 */       throw new SlickException("Failed to load state on startup", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getNumber(String nameOfField) {
/*  62 */     return getNumber(nameOfField, 0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getNumber(String nameOfField, double defaultValue) {
/*  73 */     Double value = (Double)this.numericData.get(nameOfField);
/*     */     
/*  75 */     if (value == null) {
/*  76 */       return defaultValue;
/*     */     }
/*     */     
/*  79 */     return value.doubleValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNumber(String nameOfField, double value) {
/*  90 */     this.numericData.put(nameOfField, new Double(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getString(String nameOfField) {
/* 100 */     return getString(nameOfField, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getString(String nameOfField, String defaultValue) {
/* 111 */     String value = (String)this.stringData.get(nameOfField);
/*     */     
/* 113 */     if (value == null) {
/* 114 */       return defaultValue;
/*     */     }
/*     */     
/* 117 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setString(String nameOfField, String value) {
/* 128 */     this.stringData.put(nameOfField, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void save() throws IOException {
/* 137 */     this.muffin.saveFile(this.numericData, this.fileName + "_Number");
/* 138 */     this.muffin.saveFile(this.stringData, this.fileName + "_String");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void load() throws IOException {
/* 147 */     this.numericData = this.muffin.loadFile(this.fileName + "_Number");
/* 148 */     this.stringData = this.muffin.loadFile(this.fileName + "_String");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 155 */     this.numericData.clear();
/* 156 */     this.stringData.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isWebstartAvailable() {
/*     */     try {
/* 166 */       Class.forName("javax.jnlp.ServiceManager");
/*     */       
/* 168 */       ServiceManager.lookup("javax.jnlp.PersistenceService");
/* 169 */       Log.info("Webstart detected using Muffins");
/* 170 */     } catch (Exception e) {
/* 171 */       Log.info("Using Local File System");
/* 172 */       return false;
/*     */     } 
/* 174 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\SavedState.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */