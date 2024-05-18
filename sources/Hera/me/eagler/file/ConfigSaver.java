/*     */ package me.eagler.file;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ import java.io.FileWriter;
/*     */ import java.io.PrintWriter;
/*     */ import me.eagler.Client;
/*     */ import me.eagler.module.Category;
/*     */ import me.eagler.setting.Setting;
/*     */ import net.minecraft.client.Minecraft;
/*     */ 
/*     */ public class ConfigSaver {
/*     */   public static boolean isConfigExisting(String name) {
/*  15 */     File file = new File((Minecraft.getMinecraft()).mcDataDir + "\\Hera\\configs\\" + name.toUpperCase() + ".txt");
/*  16 */     if (file.exists())
/*  17 */       return true; 
/*  18 */     return false;
/*     */   }
/*     */   
/*     */   public static void saveConfig(String name) {
/*  22 */     File file2 = new File((Minecraft.getMinecraft()).mcDataDir + "\\Hera\\configs");
/*  23 */     if (!file2.exists())
/*  24 */       file2.mkdir(); 
/*  25 */     File file = new File((Minecraft.getMinecraft()).mcDataDir + "\\Hera\\configs\\" + name.toUpperCase() + ".txt");
/*     */     try {
/*  27 */       PrintWriter printWriter = new PrintWriter(new FileWriter(file));
/*  28 */       for (Setting setting : Client.instance.getSettingManager().getSettings()) {
/*  29 */         if (!name.equalsIgnoreCase("latest")) {
/*     */           
/*  31 */           if (setting.getModule().getCategory() != Category.Render && 
/*  32 */             setting.getModule().getCategory() != Category.Fun && 
/*  33 */             setting.getModule().getCategory() != Category.Gui) {
/*     */             
/*  35 */             String str = setting.getSettingname();
/*  36 */             if (setting.isCheckbox()) {
/*  37 */               boolean sboolean = setting.getBoolean();
/*  38 */               String string = String.valueOf(String.valueOf(str)) + ":" + sboolean;
/*  39 */               printWriter.println(string);
/*     */             } 
/*  41 */             if (setting.isCombobox()) {
/*  42 */               String mode = setting.getMode();
/*  43 */               String string = String.valueOf(String.valueOf(str)) + ":" + mode;
/*  44 */               printWriter.println(string);
/*     */             } 
/*  46 */             if (setting.isValueslider()) {
/*  47 */               double sdouble = setting.getValue();
/*  48 */               String string = String.valueOf(String.valueOf(str)) + ":" + sdouble;
/*  49 */               printWriter.println(string);
/*     */             } 
/*     */           } 
/*     */           
/*     */           continue;
/*     */         } 
/*  55 */         String sname = setting.getSettingname();
/*  56 */         if (setting.isCheckbox()) {
/*  57 */           boolean sboolean = setting.getBoolean();
/*  58 */           String string = String.valueOf(String.valueOf(sname)) + ":" + sboolean;
/*  59 */           printWriter.println(string);
/*     */         } 
/*  61 */         if (setting.isCombobox()) {
/*  62 */           String mode = setting.getMode();
/*  63 */           String string = String.valueOf(String.valueOf(sname)) + ":" + mode;
/*  64 */           printWriter.println(string);
/*     */         } 
/*  66 */         if (setting.isValueslider()) {
/*  67 */           double sdouble = setting.getValue();
/*  68 */           String string = String.valueOf(String.valueOf(sname)) + ":" + sdouble;
/*  69 */           printWriter.println(string);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/*  74 */       printWriter.close();
/*  75 */     } catch (Exception e) {
/*  76 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void loadConfig(String name) {
/*     */     try {
/*  82 */       File file = new File(
/*  83 */           (Minecraft.getMinecraft()).mcDataDir + "\\Hera\\configs\\" + name.toUpperCase() + ".txt");
/*  84 */       if (!file.exists())
/*     */         return; 
/*  86 */       if (file.exists()) {
/*  87 */         BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
/*  88 */         for (String readString = ""; (readString = bufferedReader.readLine()) != null; ) {
/*  89 */           String[] split = readString.split(":");
/*  90 */           String sname = split[0];
/*  91 */           String svalue = split[1];
/*  92 */           if (svalue.toLowerCase().contains("true") || svalue.toLowerCase().contains("false")) {
/*  93 */             boolean sboolean = Boolean.parseBoolean(svalue);
/*  94 */             Client.instance.getSettingManager().getSettingByName(sname).setBoolean(sboolean);
/*     */             continue;
/*     */           } 
/*  97 */           if (svalue.contains("0") || svalue.contains("1") || svalue.contains("2") || svalue.contains("3") || 
/*  98 */             svalue.contains("4") || svalue.contains("5") || svalue.contains("6") || 
/*  99 */             svalue.contains("7") || svalue.contains("8") || svalue.contains("9")) {
/*     */             double sdouble;
/*     */             try {
/* 102 */               sdouble = Double.parseDouble(svalue);
/* 103 */             } catch (Exception e) {
/* 104 */               sdouble = 0.0D;
/*     */             } 
/* 106 */             Client.instance.getSettingManager().getSettingByName(sname).setValue(sdouble);
/*     */             continue;
/*     */           } 
/* 109 */           String mode = svalue;
/* 110 */           Client.instance.getSettingManager().getSettingByName(sname).setMode(mode);
/*     */         } 
/*     */       } 
/* 113 */     } catch (Exception exception) {}
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\file\ConfigSaver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */