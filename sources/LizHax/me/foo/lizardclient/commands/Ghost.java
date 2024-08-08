/*    */ package me.foo.lizardclient.commands;
/*    */ 
/*    */ import me.foo.lizardclient.Client;
/*    */ import me.foo.lizardclient.command.Command;
/*    */ 
/*    */ public class Ghost
/*    */   extends Command
/*    */ {
/*    */   public String getAlias() {
/* 10 */     return "ghost";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 15 */     return "Toggle ghost mode";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSyntax() {
/* 20 */     return ".ghost";
/*    */   }
/*    */ 
/*    */   
/*    */   public void onCommand(String command, String[] args) throws Exception {
/* 25 */     Client.uiRenderer.ghost = Boolean.valueOf(!Client.uiRenderer.ghost.booleanValue());
/*    */   }
/*    */ }


/* Location:              C:\Users\Ben\Downloads\Lizard Client.jar!\me\foo\lizardclient\commands\Ghost.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */