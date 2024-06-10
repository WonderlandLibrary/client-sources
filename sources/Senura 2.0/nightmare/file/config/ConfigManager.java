/*     */ package nightmare.file.config;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import nightmare.Nightmare;
/*     */ import nightmare.module.Module;
/*     */ import nightmare.settings.Setting;
/*     */ 
/*     */ 
/*     */ public class ConfigManager
/*     */ {
/*  18 */   private Minecraft mc = Minecraft.func_71410_x();
/*     */   
/*     */   private File configDir;
/*     */   
/*     */   private File dataFile;
/*     */ 
/*     */   
/*     */   public ConfigManager() {
/*  26 */     this.configDir = new File(this.mc.field_71412_D, "Senura/config");
/*  27 */     this.dataFile = new File(this.configDir, "Config.txt");
/*     */     
/*  29 */     if (!this.configDir.exists()) {
/*  30 */       this.configDir.mkdir();
/*     */     }
/*     */     
/*  33 */     if (!this.dataFile.exists()) {
/*     */       try {
/*  35 */         this.dataFile.createNewFile();
/*  36 */       } catch (IOException e) {
/*  37 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*  40 */     load();
/*     */   }
/*     */ 
/*     */   
/*     */   public void save() {
/*  45 */     ArrayList<String> toSave = new ArrayList<>();
/*     */     
/*  47 */     for (Module mod : Nightmare.instance.moduleManager.modules) {
/*  48 */       toSave.add("MOD:" + mod.getName() + ":" + mod.isToggled() + ":" + mod.getKey());
/*     */     }
/*     */     
/*  51 */     for (Setting set : Nightmare.instance.settingsManager.getSettings()) {
/*  52 */       if (set.isCheck()) {
/*  53 */         toSave.add("SET:" + set.getName() + ":" + set.getParentMod().getName() + ":" + set.getValBoolean());
/*     */       }
/*  55 */       if (set.isCombo()) {
/*  56 */         toSave.add("SET:" + set.getName() + ":" + set.getParentMod().getName() + ":" + set.getValString());
/*     */       }
/*  58 */       if (set.isSlider()) {
/*  59 */         toSave.add("SET:" + set.getName() + ":" + set.getParentMod().getName() + ":" + set.getValDouble());
/*     */       }
/*     */     } 
/*     */     
/*     */     try {
/*  64 */       PrintWriter pw = new PrintWriter(this.dataFile);
/*  65 */       for (String str : toSave) {
/*  66 */         pw.println(str);
/*     */       }
/*  68 */       pw.close();
/*  69 */     } catch (FileNotFoundException e) {
/*  70 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void load() {
/*  76 */     ArrayList<String> lines = new ArrayList<>();
/*     */     
/*     */     try {
/*  79 */       BufferedReader reader = new BufferedReader(new FileReader(this.dataFile));
/*  80 */       String line = reader.readLine();
/*  81 */       while (line != null) {
/*  82 */         lines.add(line);
/*  83 */         line = reader.readLine();
/*     */       } 
/*  85 */       reader.close();
/*  86 */     } catch (Exception e) {
/*  87 */       e.printStackTrace();
/*     */     } 
/*     */     
/*  90 */     for (String s : lines) {
/*  91 */       String[] args = s.split(":");
/*  92 */       if (s.toLowerCase().startsWith("mod:")) {
/*  93 */         Module m = Nightmare.instance.moduleManager.getModuleByName(args[1]);
/*  94 */         if (m != null) {
/*  95 */           m.setToggled(Boolean.parseBoolean(args[2]));
/*  96 */           m.setKey(Integer.parseInt(args[3]));
/*     */         }  continue;
/*  98 */       }  if (s.toLowerCase().startsWith("set:")) {
/*  99 */         Module m = Nightmare.instance.moduleManager.getModuleByName(args[2]);
/* 100 */         if (m != null) {
/* 101 */           Setting set = Nightmare.instance.settingsManager.getSettingByName(m, args[1]);
/* 102 */           if (set != null) {
/* 103 */             if (set.isCheck()) {
/* 104 */               set.setValBoolean(Boolean.parseBoolean(args[3]));
/*     */             }
/* 106 */             if (set.isCombo()) {
/* 107 */               set.setValString(args[3]);
/*     */             }
/* 109 */             if (set.isSlider()) {
/* 110 */               set.setValDouble(Double.parseDouble(args[3]));
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public File getConfigDir() {
/* 119 */     return this.configDir;
/*     */   }
/*     */   
/*     */   public File getDataFile() {
/* 123 */     return this.dataFile;
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\file\config\ConfigManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */