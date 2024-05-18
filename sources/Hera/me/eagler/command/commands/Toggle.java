/*    */ package me.eagler.command.commands;
/*    */ 
/*    */ import me.eagler.Client;
/*    */ import me.eagler.command.Command;
/*    */ import me.eagler.module.Module;
/*    */ import me.eagler.utils.PlayerUtils;
/*    */ 
/*    */ public class Toggle
/*    */   extends Command
/*    */ {
/*    */   public String getCommand() {
/* 12 */     return "toggle";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getHelp() {
/* 18 */     return "Toggle modules on or off.";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getUsage() {
/* 24 */     return ".toggle [MODULE]";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onCommand(String message, String[] args) throws Exception {
/* 30 */     if (args.length == 1) {
/*    */       
/* 32 */       if (args[0] != null)
/*    */       {
/* 34 */         String name = args[0];
/*    */         
/* 36 */         if (Client.instance.getModuleManager().getModuleByName(name) != null)
/*    */         {
/* 38 */           Module module = Client.instance.getModuleManager().getModuleByName(name);
/*    */           
/* 40 */           module.toggle();
/*    */           
/* 42 */           String status = "§c§loff";
/*    */           
/* 44 */           if (module.isEnabled())
/*    */           {
/* 46 */             status = "§a§lon";
/*    */           }
/*    */ 
/*    */           
/* 50 */           PlayerUtils.sendMessage("Toggled §l" + name + " " + status, true);
/*    */         }
/*    */         else
/*    */         {
/* 54 */           PlayerUtils.sendMessage(getUsage(), true);
/*    */         }
/*    */       
/*    */       }
/*    */       else
/*    */       {
/* 60 */         PlayerUtils.sendMessage(getUsage(), true);
/*    */       }
/*    */     
/*    */     }
/*    */     else {
/*    */       
/* 66 */       PlayerUtils.sendMessage(getUsage(), true);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\command\commands\Toggle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */