/*  1:   */ package me.connorm.Nodus.command.commands;
/*  2:   */ 
/*  3:   */ import me.connorm.Nodus.Nodus;
/*  4:   */ import me.connorm.Nodus.command.NodusCommand;
/*  5:   */ import me.connorm.Nodus.module.NodusModuleManager;
/*  6:   */ import me.connorm.Nodus.module.core.NodusModule;
/*  7:   */ 
/*  8:   */ public class Toggle
/*  9:   */   extends NodusCommand
/* 10:   */ {
/* 11:   */   public Toggle()
/* 12:   */   {
/* 13:11 */     super("toggle");
/* 14:   */   }
/* 15:   */   
/* 16:   */   public void runCommand(String commandRun, String[] commandArgs)
/* 17:   */   {
/* 18:   */     try
/* 19:   */     {
/* 20:19 */       for (NodusModule nodusModule : Nodus.theNodus.moduleManager.getModules()) {
/* 21:21 */         if (commandArgs[0].equalsIgnoreCase(nodusModule.getName())) {
/* 22:23 */           nodusModule.toggleModule();
/* 23:   */         }
/* 24:   */       }
/* 25:   */     }
/* 26:   */     catch (Exception commandSyntaxException)
/* 27:   */     {
/* 28:28 */       commandSyntaxException.printStackTrace();
/* 29:29 */       Nodus.theNodus.addChatMessage("Command Usage: " + getSyntax());
/* 30:   */     }
/* 31:   */   }
/* 32:   */   
/* 33:   */   public String getDescription()
/* 34:   */   {
/* 35:36 */     return "Toggles a module";
/* 36:   */   }
/* 37:   */   
/* 38:   */   public String getSyntax()
/* 39:   */   {
/* 40:42 */     return "<module>";
/* 41:   */   }
/* 42:   */   
/* 43:   */   public String getConsoleDisplay()
/* 44:   */   {
/* 45:48 */     return "toggle";
/* 46:   */   }
/* 47:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.command.commands.Toggle
 * JD-Core Version:    0.7.0.1
 */