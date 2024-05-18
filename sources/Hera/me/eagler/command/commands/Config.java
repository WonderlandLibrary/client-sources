/*     */ package me.eagler.command.commands;
/*     */ 
/*     */ import me.eagler.command.Command;
/*     */ import me.eagler.file.ConfigSaver;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Config
/*     */   extends Command
/*     */ {
/*     */   public String getCommand() {
/*  14 */     return "config";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHelp() {
/*  21 */     return "Loads configs.";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUsage() {
/*  28 */     return ".config NAME / .config load NAME / .config save NAME";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCommand(String message, String[] args) throws Exception {
/*  35 */     if (args.length == 1 || args.length == 2) {
/*     */       
/*  37 */       if (args.length == 2)
/*     */       {
/*  39 */         if (args[0].equalsIgnoreCase("save"))
/*     */         {
/*  41 */           String config = args[1];
/*     */           
/*  43 */           if (!this.configLoader.isConfigExisting(config))
/*     */           {
/*  45 */             ConfigSaver.saveConfig(args[1]);
/*     */             
/*  47 */             sendMessage("The Config §c§l" + args[1] + " §fhas been saved.");
/*     */           }
/*     */           else
/*     */           {
/*  51 */             sendMessage("§cThis config already exists.");
/*     */           }
/*     */         
/*     */         }
/*  55 */         else if (args[0].equalsIgnoreCase("load"))
/*     */         {
/*  57 */           if (this.configLoader.isConfigExisting(args[1]))
/*     */           {
/*  59 */             this.configLoader.loadConfig(args[1]);
/*     */             
/*  61 */             sendMessage("The Config §c§l" + args[1] + " §fhas been loaded.");
/*     */           }
/*  63 */           else if (ConfigSaver.isConfigExisting(args[1]))
/*     */           {
/*  65 */             ConfigSaver.loadConfig(args[1]);
/*     */             
/*  67 */             sendMessage("The Config §c§l" + args[1] + " §fhas been loaded.");
/*     */           }
/*     */           else
/*     */           {
/*  71 */             sendMessage("§cThis config doesn't exists.");
/*     */           }
/*     */         
/*     */         }
/*     */         else
/*     */         {
/*  77 */           sendUsage();
/*     */         
/*     */         }
/*     */ 
/*     */       
/*     */       }
/*  83 */       else if (this.configLoader.isConfigExisting(args[0]))
/*     */       {
/*  85 */         this.configLoader.loadConfig(args[0]);
/*     */         
/*  87 */         sendMessage("The Config §c§l" + args[0] + " §fhas been loaded.");
/*     */       }
/*  89 */       else if (ConfigSaver.isConfigExisting(args[0]))
/*     */       {
/*  91 */         ConfigSaver.loadConfig(args[0]);
/*     */         
/*  93 */         sendMessage("The Config §c§l" + args[0] + " §fhas been loaded.");
/*     */       }
/*     */       else
/*     */       {
/*  97 */         sendMessage("§cThis config doesn't exists.");
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 105 */       sendUsage();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\command\commands\Config.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */