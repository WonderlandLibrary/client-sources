/*    */ package me.foo.lizardclient.commands;
/*    */ 
/*    */ import me.foo.lizardclient.Client;
/*    */ import me.foo.lizardclient.command.Command;
/*    */ import me.foo.lizardclient.module.Module;
/*    */ 
/*    */ public class CommandList
/*    */   extends Command
/*    */ {
/*    */   public String getAlias() {
/* 11 */     return "list";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 16 */     return "Show module list";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSyntax() {
/* 21 */     return ".list";
/*    */   }
/*    */ 
/*    */   
/*    */   public void onCommand(String command, String[] args) throws Exception {
/* 26 */     Client.addChatMessage("§lModule List:");
/* 27 */     for (Module m : Client.moduleManager.moduleList)
/* 28 */       Client.addChatMessage(m.name); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Ben\Downloads\Lizard Client.jar!\me\foo\lizardclient\commands\CommandList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */