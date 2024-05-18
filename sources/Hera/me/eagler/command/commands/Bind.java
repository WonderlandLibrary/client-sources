/*    */ package me.eagler.command.commands;
/*    */ 
/*    */ import me.eagler.Client;
/*    */ import me.eagler.command.Command;
/*    */ import me.eagler.module.Module;
/*    */ import me.eagler.utils.PlayerUtils;
/*    */ import org.lwjgl.input.Keyboard;
/*    */ 
/*    */ 
/*    */ public class Bind
/*    */   extends Command
/*    */ {
/*    */   public String getCommand() {
/* 14 */     return "bind";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getHelp() {
/* 20 */     return "Bind modules to a key.";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getUsage() {
/* 26 */     return ".bind [MODULE] [KEY]";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onCommand(String message, String[] args) throws Exception {
/* 32 */     if (args.length == 2) {
/*    */       
/* 34 */       String skey = args[1].toUpperCase();
/*    */       
/* 36 */       int key = Keyboard.getKeyIndex(skey);
/*    */       
/* 38 */       String name = args[0];
/*    */       
/* 40 */       if (Client.instance.getModuleManager().getModuleByName(name) != null)
/*    */       {
/* 42 */         Module module = Client.instance.getModuleManager().getModuleByName(name);
/*    */         
/* 44 */         module.setKey(key);
/*    */         
/* 46 */         PlayerUtils.sendMessage("The module §c§l" + name + " §fhas been bound to §c§l" + skey + "§f.", true);
/*    */         
/* 48 */         Client.instance.getFileManager().saveKeys();
/*    */       }
/*    */       else
/*    */       {
/* 52 */         PlayerUtils.sendMessage("§cThis module doesn't exist.", true);
/*    */       }
/*    */     
/*    */     }
/*    */     else {
/*    */       
/* 58 */       PlayerUtils.sendMessage(getUsage(), true);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\command\commands\Bind.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */