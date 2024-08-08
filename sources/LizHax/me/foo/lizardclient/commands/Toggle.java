/*    */ package me.foo.lizardclient.commands;
/*    */ 
/*    */ import me.foo.lizardclient.Client;
/*    */ import me.foo.lizardclient.command.Command;
/*    */ import me.foo.lizardclient.module.Module;
/*    */ 
/*    */ public class Toggle
/*    */   extends Command
/*    */ {
/*    */   public String getAlias() {
/* 11 */     return "toggle";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 16 */     return "Toggle a module";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSyntax() {
/* 21 */     return ".toggle <module>";
/*    */   }
/*    */ 
/*    */   
/*    */   public void onCommand(String command, String[] args) throws Exception {
/* 26 */     for (Module m : Client.moduleManager.moduleList) {
/* 27 */       if (m.name.equalsIgnoreCase(args[0])) {
/* 28 */         m.setEnabled(Boolean.valueOf(!m.isEnabled().booleanValue()));
/* 29 */         if (m.isEnabled().booleanValue()) {
/* 30 */           Client.addChatMessage("§aToggled module " + m.name);
/*    */         } else {
/* 32 */           Client.addChatMessage("§cToggled module " + m.name);
/*    */         }  return;
/*    */       } 
/*    */     } 
/* 36 */     Client.addChatMessage("§4§l No module found named §r'" + args[0] + "'§4§l!");
/*    */   }
/*    */ }


/* Location:              C:\Users\Ben\Downloads\Lizard Client.jar!\me\foo\lizardclient\commands\Toggle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */