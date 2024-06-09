/*     */ package me.eagler.module;
/*     */ 
/*     */ import me.eagler.Client;
/*     */ import me.eagler.setting.SettingManager;
/*     */ import net.minecraft.client.Minecraft;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Module
/*     */ {
/*  11 */   public Minecraft mc = Minecraft.getMinecraft();
/*     */   
/*     */   public SettingManager settingManager;
/*     */   
/*     */   private Category category;
/*     */   
/*     */   private String name;
/*     */   
/*     */   private String tag;
/*     */   
/*     */   private int key;
/*     */   
/*     */   private boolean enabled;
/*     */ 
/*     */   
/*     */   public Module(String name, String tag, int key, Category category) {
/*  27 */     this.settingManager = Client.instance.getSettingManager();
/*  28 */     setName(name);
/*  29 */     setTag(tag);
/*  30 */     setKey(key);
/*  31 */     setCategory(category);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Module(String name, String tag, Category category) {
/*  37 */     this.settingManager = Client.instance.getSettingManager();
/*  38 */     setName(name);
/*  39 */     setTag(tag);
/*  40 */     setKey(0);
/*  41 */     setCategory(category);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Module(String name, Category category) {
/*  47 */     this.settingManager = Client.instance.getSettingManager();
/*  48 */     setName(name);
/*  49 */     setTag(name);
/*  50 */     setKey(0);
/*  51 */     setCategory(category);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEnabled() {
/*  56 */     return this.enabled;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTag() {
/*  61 */     return this.tag;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  67 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getKey() {
/*  73 */     return this.key;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Category getCategory() {
/*  79 */     return this.category;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEnabled(boolean enabled) {
/*  85 */     this.enabled = enabled;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTag(String tag) {
/* 101 */     this.tag = tag;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setExtraTag(String extra) {
/* 107 */     if (Client.instance.getUtils().containsLetter(extra))
/*     */     {
/* 109 */       extra = String.valueOf(extra.substring(0, 1).toUpperCase()) + extra.substring(1).toLowerCase();
/*     */     }
/*     */ 
/*     */     
/* 113 */     if (this.settingManager.getSettingByName("ThemeMode").getMode().equalsIgnoreCase("Dark")) {
/*     */       
/* 115 */       setTag(String.valueOf(this.name) + " §7" + extra);
/*     */     }
/* 117 */     else if (this.settingManager.getSettingByName("ThemeMode").getMode().equalsIgnoreCase("Bright")) {
/*     */       
/* 119 */       setTag(String.valueOf(this.name) + " §8" + extra);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setExtraTag(double extra) {
/* 127 */     if (this.settingManager.getSettingByName("ThemeMode").getMode().equalsIgnoreCase("Dark")) {
/*     */       
/* 129 */       setTag(String.valueOf(this.name) + " §7" + extra);
/*     */     }
/* 131 */     else if (this.settingManager.getSettingByName("ThemeMode").getMode().equalsIgnoreCase("Bright")) {
/*     */       
/* 133 */       setTag(String.valueOf(this.name) + " §8" + extra);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setName(String name) {
/* 141 */     this.name = name;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setKey(int key) {
/* 147 */     this.key = key;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCategory(Category category) {
/* 153 */     this.category = category;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEnable() {
/* 159 */     Client.instance.getFileManager().saveModules();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDisable() {
/* 165 */     Client.instance.getFileManager().saveModules();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {}
/*     */ 
/*     */   
/*     */   public void onRender() {}
/*     */   
/*     */   public void toggle() {
/* 175 */     if (this.enabled) {
/*     */       
/* 177 */       setEnabled(false);
/*     */       
/* 179 */       onDisable();
/*     */     }
/*     */     else {
/*     */       
/* 183 */       setEnabled(true);
/*     */       
/* 185 */       onEnable();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\module\Module.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */