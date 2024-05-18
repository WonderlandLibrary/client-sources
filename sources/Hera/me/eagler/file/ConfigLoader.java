/*     */ package me.eagler.file;
/*     */ 
/*     */ import me.eagler.Client;
/*     */ import me.eagler.setting.SettingManager;
/*     */ 
/*     */ 
/*     */ public class ConfigLoader
/*     */ {
/*     */   public void loadConfig(String config) {
/*  10 */     SettingManager settingManager = Client.instance.getSettingManager();
/*     */     
/*  12 */     if (config.equalsIgnoreCase("cubecraft")) {
/*     */ 
/*     */ 
/*     */       
/*  16 */       settingManager.getSettingByName("Range").setValue(6.0D);
/*  17 */       settingManager.getSettingByName("Delay").setValue(175.0D);
/*  18 */       settingManager.getSettingByName("CPS").setValue(0.0D);
/*  19 */       settingManager.getSettingByName("CantSee").setBoolean(false);
/*     */ 
/*     */ 
/*     */       
/*  23 */       settingManager.getSettingByName("SpeedMode").setMode("CCOnGround");
/*     */ 
/*     */ 
/*     */       
/*  27 */       settingManager.getSettingByName("CSDelay").setValue(1.0D);
/*  28 */       settingManager.getSettingByName("Instant").setBoolean(false);
/*     */ 
/*     */ 
/*     */       
/*  32 */       settingManager.getSettingByName("AutoArmorDelay").setValue(250.0D);
/*  33 */       settingManager.getSettingByName("AAMode").setMode("OpenInv");
/*     */ 
/*     */ 
/*     */       
/*  37 */       settingManager.getSettingByName("KBMode").setMode("Null");
/*     */ 
/*     */ 
/*     */       
/*  41 */       settingManager.getSettingByName("ScaffoldDelay").setValue(100.0D);
/*  42 */       settingManager.getSettingByName("Boost").setValue(0.0D);
/*  43 */       settingManager.getSettingByName("NoSprint").setBoolean(true);
/*  44 */       settingManager.getSettingByName("Sneak").setBoolean(false);
/*  45 */       settingManager.getSettingByName("CubeCraft").setBoolean(true);
/*     */ 
/*     */ 
/*     */       
/*  49 */       settingManager.getSettingByName("ICMode").setMode("OpenInv");
/*  50 */       settingManager.getSettingByName("InvCleanerDelay").setValue(200.0D);
/*  51 */       settingManager.getSettingByName("InvSort").setBoolean(false);
/*     */ 
/*     */ 
/*     */       
/*  55 */       settingManager.getSettingByName("Gomme").setBoolean(true);
/*     */ 
/*     */ 
/*     */       
/*  59 */       settingManager.getSettingByName("ChestESPMode").setMode("Normal");
/*     */     }
/*  61 */     else if (config.equalsIgnoreCase("gomme")) {
/*     */ 
/*     */ 
/*     */       
/*  65 */       settingManager.getSettingByName("Range").setValue(3.9D);
/*  66 */       settingManager.getSettingByName("Delay").setValue(175.0D);
/*  67 */       settingManager.getSettingByName("CPS").setValue(0.0D);
/*  68 */       settingManager.getSettingByName("CantSee").setBoolean(false);
/*     */ 
/*     */ 
/*     */       
/*  72 */       settingManager.getSettingByName("SpeedMode").setMode("Gomme");
/*     */ 
/*     */ 
/*     */       
/*  76 */       settingManager.getSettingByName("CSDelay").setValue(150.0D);
/*  77 */       settingManager.getSettingByName("Instant").setBoolean(false);
/*     */ 
/*     */ 
/*     */       
/*  81 */       settingManager.getSettingByName("AutoArmorDelay").setValue(250.0D);
/*  82 */       settingManager.getSettingByName("AAMode").setMode("AAC");
/*     */ 
/*     */ 
/*     */       
/*  86 */       settingManager.getSettingByName("KBMode").setMode("Null");
/*     */ 
/*     */ 
/*     */       
/*  90 */       settingManager.getSettingByName("ScaffoldDelay").setValue(291.0D);
/*  91 */       settingManager.getSettingByName("Boost").setValue(0.0D);
/*  92 */       settingManager.getSettingByName("NoSprint").setBoolean(true);
/*  93 */       settingManager.getSettingByName("Sneak").setBoolean(false);
/*  94 */       settingManager.getSettingByName("CubeCraft").setBoolean(false);
/*     */ 
/*     */ 
/*     */       
/*  98 */       settingManager.getSettingByName("ICMode").setMode("OpenInv");
/*  99 */       settingManager.getSettingByName("InvCleanerDelay").setValue(300.0D);
/* 100 */       settingManager.getSettingByName("InvSort").setBoolean(false);
/*     */ 
/*     */ 
/*     */       
/* 104 */       settingManager.getSettingByName("Gomme").setBoolean(true);
/*     */ 
/*     */ 
/*     */       
/* 108 */       settingManager.getSettingByName("ChestESPMode").setMode("Normal");
/*     */     }
/* 110 */     else if (config.equalsIgnoreCase("gommeqsg")) {
/*     */ 
/*     */ 
/*     */       
/* 114 */       settingManager.getSettingByName("Range").setValue(3.9D);
/* 115 */       settingManager.getSettingByName("Delay").setValue(300.0D);
/* 116 */       settingManager.getSettingByName("CPS").setValue(0.0D);
/* 117 */       settingManager.getSettingByName("CantSee").setBoolean(false);
/* 118 */       settingManager.getSettingByName("AutoBlock").setBoolean(false);
/*     */ 
/*     */ 
/*     */       
/* 122 */       settingManager.getSettingByName("SpeedMode").setMode("Gomme");
/*     */ 
/*     */ 
/*     */       
/* 126 */       settingManager.getSettingByName("CSDelay").setValue(150.0D);
/* 127 */       settingManager.getSettingByName("Instant").setBoolean(false);
/*     */ 
/*     */ 
/*     */       
/* 131 */       settingManager.getSettingByName("AutoArmorDelay").setValue(250.0D);
/* 132 */       settingManager.getSettingByName("AAMode").setMode("AAC");
/*     */ 
/*     */ 
/*     */       
/* 136 */       settingManager.getSettingByName("KBMode").setMode("Reverse");
/*     */ 
/*     */ 
/*     */       
/* 140 */       settingManager.getSettingByName("ScaffoldDelay").setValue(291.0D);
/* 141 */       settingManager.getSettingByName("Boost").setValue(0.0D);
/* 142 */       settingManager.getSettingByName("NoSprint").setBoolean(true);
/* 143 */       settingManager.getSettingByName("Sneak").setBoolean(false);
/* 144 */       settingManager.getSettingByName("CubeCraft").setBoolean(false);
/*     */ 
/*     */ 
/*     */       
/* 148 */       settingManager.getSettingByName("ICMode").setMode("OpenInv");
/* 149 */       settingManager.getSettingByName("InvCleanerDelay").setValue(300.0D);
/* 150 */       settingManager.getSettingByName("InvSort").setBoolean(false);
/*     */ 
/*     */ 
/*     */       
/* 154 */       settingManager.getSettingByName("Gomme").setBoolean(true);
/*     */ 
/*     */ 
/*     */       
/* 158 */       settingManager.getSettingByName("ChestESPMode").setMode("Normal");
/*     */     }
/* 160 */     else if (config.equalsIgnoreCase("hive")) {
/*     */ 
/*     */ 
/*     */       
/* 164 */       settingManager.getSettingByName("Range").setValue(4.3D);
/* 165 */       settingManager.getSettingByName("Delay").setValue(70.0D);
/* 166 */       settingManager.getSettingByName("CPS").setValue(13.0D);
/* 167 */       settingManager.getSettingByName("CantSee").setBoolean(false);
/* 168 */       settingManager.getSettingByName("AutoBlock").setBoolean(false);
/*     */ 
/*     */ 
/*     */       
/* 172 */       settingManager.getSettingByName("SpeedMode").setMode("Gomme");
/*     */ 
/*     */ 
/*     */       
/* 176 */       settingManager.getSettingByName("CSDelay").setValue(93.0D);
/* 177 */       settingManager.getSettingByName("Instant").setBoolean(true);
/*     */ 
/*     */ 
/*     */       
/* 181 */       settingManager.getSettingByName("AutoArmorDelay").setValue(139.0D);
/* 182 */       settingManager.getSettingByName("AAMode").setMode("Normal");
/*     */ 
/*     */ 
/*     */       
/* 186 */       settingManager.getSettingByName("KBMode").setMode("Null");
/*     */ 
/*     */ 
/*     */       
/* 190 */       settingManager.getSettingByName("ScaffoldDelay").setValue(256.0D);
/* 191 */       settingManager.getSettingByName("Boost").setValue(0.0D);
/* 192 */       settingManager.getSettingByName("NoSprint").setBoolean(true);
/* 193 */       settingManager.getSettingByName("Sneak").setBoolean(false);
/* 194 */       settingManager.getSettingByName("CubeCraft").setBoolean(false);
/*     */ 
/*     */ 
/*     */       
/* 198 */       settingManager.getSettingByName("ICMode").setMode("Normal");
/* 199 */       settingManager.getSettingByName("InvCleanerDelay").setValue(139.0D);
/* 200 */       settingManager.getSettingByName("InvSort").setBoolean(false);
/*     */ 
/*     */ 
/*     */       
/* 204 */       settingManager.getSettingByName("Gomme").setBoolean(false);
/*     */ 
/*     */ 
/*     */       
/* 208 */       settingManager.getSettingByName("ChestESPMode").setMode("Hive");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isConfigExisting(String config) {
/* 216 */     if (config.equalsIgnoreCase("cubecraft"))
/*     */     {
/* 218 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 222 */     if (config.equalsIgnoreCase("gomme"))
/*     */     {
/* 224 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 228 */     if (config.equalsIgnoreCase("gommeqsg"))
/*     */     {
/* 230 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 234 */     if (config.equalsIgnoreCase("hive"))
/*     */     {
/* 236 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 240 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\file\ConfigLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */