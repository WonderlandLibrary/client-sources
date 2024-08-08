/*    */ package me.foo.lizardclient.commands;
/*    */ 
/*    */ import me.foo.lizardclient.Client;
/*    */ import me.foo.lizardclient.command.Command;
/*    */ import me.foo.lizardclient.module.Module;
/*    */ import org.lwjgl.input.Keyboard;
/*    */ 
/*    */ 
/*    */ public class Bind
/*    */   extends Command
/*    */ {
/*    */   public String getAlias() {
/* 13 */     return "bind";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 18 */     return "Change the keybind for a module";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSyntax() {
/* 23 */     return ".bind set <module> <key> | .bind del <module> | .bind clear";
/*    */   }
/*    */ 
/*    */   
/*    */   public void onCommand(String command, String[] args) throws Exception {
/* 28 */     if (args[0].equalsIgnoreCase("set")) {
/*    */       
/* 30 */       args[2] = args[2].toUpperCase();
/* 31 */       int key = Keyboard.getKeyIndex(args[2]);
/* 32 */       for (Module m : Client.moduleManager.moduleList) {
/* 33 */         if (args[1].equalsIgnoreCase(m.name)) {
/* 34 */           if (m.keyBind != Keyboard.getKeyIndex(Keyboard.getKeyName(key))) {
/* 35 */             m.keyBind = Keyboard.getKeyIndex(Keyboard.getKeyName(key));
/* 36 */             Client.addChatMessage("Bound module §b" + m.name + "§r to key §b" + Keyboard.getKeyIndex(Keyboard.getKeyName(key)));
/*    */           } else {
/*    */             
/* 39 */             m.keyBind = 0;
/* 40 */             Client.addChatMessage("Removed keybind from module §b" + m.name);
/*    */           } 
/*    */           return;
/*    */         } 
/*    */       } 
/* 45 */       Client.addChatMessage("Could not find module " + args[1]);
/*    */     }
/* 47 */     else if (args[0].equalsIgnoreCase("del")) {
/*    */       
/* 49 */       for (Module m : Client.moduleManager.moduleList) {
/* 50 */         if (m.name.equalsIgnoreCase(args[1])) {
/* 51 */           m.keyBind = 0;
/* 52 */           Client.addChatMessage("Removed keybind from module §b" + m.name);
/*    */           return;
/*    */         } 
/*    */       } 
/* 56 */       Client.addChatMessage("Could not find module §b" + args[1]);
/*    */     }
/* 58 */     else if (args[0].equalsIgnoreCase("clear")) {
/* 59 */       for (Module m : Client.moduleManager.moduleList) {
/* 60 */         m.keyBind = 0;
/* 61 */         Client.addChatMessage("Removed keybind from all modules");
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Ben\Downloads\Lizard Client.jar!\me\foo\lizardclient\commands\Bind.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */