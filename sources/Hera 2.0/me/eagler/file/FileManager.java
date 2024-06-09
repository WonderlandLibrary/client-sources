/*     */ package me.eagler.file;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ import java.io.FileWriter;
/*     */ import java.io.PrintWriter;
/*     */ import me.eagler.Client;
/*     */ import me.eagler.gui.GuiChangeName;
/*     */ import me.eagler.module.Module;
/*     */ import net.minecraft.client.Minecraft;
/*     */ 
/*     */ 
/*     */ public class FileManager
/*     */ {
/*  16 */   public Minecraft mc = Minecraft.getMinecraft();
/*     */ 
/*     */   
/*     */   public void load() {
/*  20 */     File folder = new File(this.mc.mcDataDir + "\\Hera");
/*     */     
/*  22 */     if (!folder.exists())
/*     */     {
/*  24 */       folder.mkdir();
/*     */     }
/*     */ 
/*     */     
/*  28 */     loadModules();
/*  29 */     loadKeys();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveModules() {
/*  35 */     File file = new File(this.mc.mcDataDir + "\\Hera\\modules.txt");
/*     */ 
/*     */     
/*     */     try {
/*  39 */       PrintWriter printWriter = new PrintWriter(new FileWriter(file));
/*     */       
/*  41 */       for (Module module : Client.instance.getModuleManager().getModules()) {
/*     */         
/*  43 */         String modname = module.getName();
/*     */         
/*  45 */         String string = String.valueOf(String.valueOf(modname)) + ":" + module.isEnabled();
/*     */         
/*  47 */         printWriter.println(string);
/*     */       } 
/*     */ 
/*     */       
/*  51 */       printWriter.close();
/*     */     }
/*  53 */     catch (Exception e) {
/*     */       
/*  55 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveName(String name) {
/*  63 */     File file = new File(this.mc.mcDataDir + "\\Hera\\name.txt");
/*     */ 
/*     */     
/*     */     try {
/*  67 */       PrintWriter printWriter = new PrintWriter(new FileWriter(file));
/*     */       
/*  69 */       printWriter.println(name);
/*     */       
/*  71 */       printWriter.close();
/*     */     }
/*  73 */     catch (Exception e) {
/*     */       
/*  75 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadName() {
/*     */     try {
/*  86 */       File file = new File(this.mc.mcDataDir + "\\Hera\\name.txt");
/*     */       
/*  88 */       if (!file.exists())
/*     */       {
/*  90 */         PrintWriter printWriter = new PrintWriter(file);
/*     */         
/*  92 */         printWriter.println();
/*     */         
/*  94 */         printWriter.close();
/*     */       }
/*  96 */       else if (file.exists())
/*     */       {
/*  98 */         BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
/*     */         
/* 100 */         String name = bufferedReader.readLine();
/*     */         
/* 102 */         GuiChangeName.name = name;
/*     */       }
/*     */     
/*     */     }
/* 106 */     catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadModules() {
/*     */     try {
/* 115 */       File file = new File(this.mc.mcDataDir + "\\Hera\\modules.txt");
/*     */       
/* 117 */       if (!file.exists())
/*     */       {
/* 119 */         PrintWriter printWriter = new PrintWriter(new FileWriter(file));
/*     */         
/* 121 */         printWriter.println();
/*     */         
/* 123 */         printWriter.close();
/*     */       }
/* 125 */       else if (file.exists())
/*     */       {
/* 127 */         BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
/*     */         
/* 129 */         for (String readString = ""; (readString = bufferedReader.readLine()) != null; )
/*     */         {
/* 131 */           String[] split = readString.split(":");
/*     */           
/* 133 */           Module mod = Client.instance.getModuleManager().getModuleByName(split[0]);
/*     */           
/* 135 */           boolean enabled = Boolean.parseBoolean(split[1]);
/*     */           
/* 137 */           if (mod != null)
/*     */           {
/* 139 */             mod.setEnabled(enabled);
/*     */           
/*     */           }
/*     */         }
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 147 */     catch (Exception e) {
/*     */       
/* 149 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveKeys() {
/* 157 */     File file = new File(this.mc.mcDataDir + "\\Hera\\keys.txt");
/*     */ 
/*     */     
/*     */     try {
/* 161 */       PrintWriter writer = new PrintWriter(new FileWriter(file));
/*     */       
/* 163 */       for (Module module : Client.instance.getModuleManager().getModules()) {
/*     */         
/* 165 */         String modulename = module.getName();
/*     */         
/* 167 */         int modulekey = module.getKey();
/*     */         
/* 169 */         String endstring = String.valueOf(String.valueOf(modulename)) + ":" + modulekey;
/*     */         
/* 171 */         writer.println(endstring);
/*     */       } 
/*     */ 
/*     */       
/* 175 */       writer.close();
/*     */     }
/* 177 */     catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadKeys() {
/*     */     try {
/* 188 */       File file = new File(this.mc.mcDataDir + "\\Hera\\keys.txt");
/*     */       
/* 190 */       if (!file.exists())
/*     */       {
/* 192 */         PrintWriter printWriter = new PrintWriter(new FileWriter(file));
/*     */         
/* 194 */         printWriter.println();
/*     */         
/* 196 */         printWriter.close();
/*     */       }
/* 198 */       else if (file.exists())
/*     */       {
/* 200 */         BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
/*     */         
/* 202 */         for (String readString = ""; (readString = bufferedReader.readLine()) != null; )
/*     */         {
/* 204 */           String[] split = readString.split(":");
/*     */           
/* 206 */           Module mod = Client.instance.getModuleManager().getModuleByName(split[0]);
/*     */           
/* 208 */           int key = Integer.parseInt(split[1]);
/*     */           
/* 210 */           mod.setKey(key);
/*     */         }
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 216 */     catch (Exception e) {
/*     */       
/* 218 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double getPanel(String panel, String xory) {
/* 226 */     double pos = 0.0D;
/*     */ 
/*     */     
/*     */     try {
/* 230 */       File file = new File((Minecraft.getMinecraft()).mcDataDir + "\\Hera\\panels\\" + panel + ".txt");
/*     */       
/* 232 */       if (!file.exists())
/*     */       {
/* 234 */         PrintWriter printWriter = new PrintWriter(new FileWriter(file));
/* 235 */         printWriter.println();
/* 236 */         printWriter.close();
/*     */       }
/* 238 */       else if (file.exists())
/*     */       {
/* 240 */         BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
/*     */         
/* 242 */         for (String readString = ""; (readString = bufferedReader.readLine()) != null; )
/*     */         {
/* 244 */           String[] split = readString.split(":");
/*     */           
/* 246 */           if (xory.equalsIgnoreCase("x")) {
/*     */             
/* 248 */             pos = Double.parseDouble(split[0]);
/*     */             
/*     */             continue;
/*     */           } 
/*     */           
/* 253 */           if (xory.equalsIgnoreCase("y")) {
/* 254 */             pos = Double.parseDouble(split[1]);
/*     */           }
/*     */         }
/*     */       
/*     */       }
/*     */     
/* 260 */     } catch (Exception e) {
/*     */       
/* 262 */       e.printStackTrace();
/*     */     } 
/*     */ 
/*     */     
/* 266 */     return pos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean wasPanelExtended(String panel) {
/* 272 */     boolean extended = false;
/*     */ 
/*     */     
/*     */     try {
/* 276 */       File file = new File((Minecraft.getMinecraft()).mcDataDir + "\\Hera\\panels\\" + panel + ".txt");
/*     */       
/* 278 */       if (!file.exists())
/*     */       {
/* 280 */         PrintWriter printWriter = new PrintWriter(new FileWriter(file));
/* 281 */         printWriter.println();
/* 282 */         printWriter.close();
/*     */       }
/* 284 */       else if (file.exists())
/*     */       {
/* 286 */         BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
/*     */         
/* 288 */         for (String readString = ""; (readString = bufferedReader.readLine()) != null; )
/*     */         {
/* 290 */           String[] split = readString.split(":");
/*     */           
/* 292 */           if (split[2].equalsIgnoreCase("true")) {
/*     */             
/* 294 */             extended = true;
/*     */             
/*     */             continue;
/*     */           } 
/*     */           
/* 299 */           if (split[2].equalsIgnoreCase("false")) {
/* 300 */             extended = false;
/*     */           }
/*     */         }
/*     */       
/*     */       }
/*     */     
/* 306 */     } catch (Exception e) {
/*     */       
/* 308 */       e.printStackTrace();
/*     */     } 
/*     */ 
/*     */     
/* 312 */     return extended;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void savePanel(String panel, double x, double y, boolean extended) {
/* 318 */     File file = new File((Minecraft.getMinecraft()).mcDataDir + "\\Hera\\panels\\" + panel + ".txt");
/*     */     
/* 320 */     File file2 = new File((Minecraft.getMinecraft()).mcDataDir + "\\Hera\\panels\\");
/*     */     
/* 322 */     if (!file2.exists())
/*     */     {
/* 324 */       file2.mkdirs();
/*     */     }
/*     */     
/*     */     try {
/* 328 */       PrintWriter printWriter = new PrintWriter(new FileWriter(file));
/* 329 */       double posX = x;
/* 330 */       double posY = y;
/* 331 */       String string = String.valueOf(String.valueOf(x)) + ":" + y + ":" + extended;
/* 332 */       printWriter.println(string);
/* 333 */       printWriter.close();
/*     */     }
/* 335 */     catch (Exception e) {
/*     */       
/* 337 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\file\FileManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */