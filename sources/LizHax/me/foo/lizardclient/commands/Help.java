/*    */ package me.foo.lizardclient.commands;
/*    */ 
/*    */ import me.foo.lizardclient.Client;
/*    */ import me.foo.lizardclient.command.Command;
/*    */ 
/*    */ public class Help
/*    */   extends Command
/*    */ {
/*    */   public String getAlias() {
/* 10 */     return "help";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 15 */     return "Show list of commands";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSyntax() {
/* 20 */     return ".help";
/*    */   }
/*    */ 
/*    */   
/*    */   public void onCommand(String command, String[] args) throws Exception {
/* 25 */     for (Command c : Client.commandManager.getCommands())
/* 26 */       Client.addChatMessage(String.valueOf(c.getAlias()) + ": " + c.getDescription()); 
/*    */   }
/*    */ }