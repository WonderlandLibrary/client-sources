/*  1:   */ package me.connorm.Nodus.command.commands;
/*  2:   */ 
/*  3:   */ import me.connorm.Nodus.Nodus;
/*  4:   */ import me.connorm.Nodus.command.NodusCommand;
/*  5:   */ import me.connorm.Nodus.module.NodusModuleManager;
/*  6:   */ import me.connorm.Nodus.module.core.NodusModule;
/*  7:   */ 
/*  8:   */ public class AllOff
/*  9:   */   extends NodusCommand
/* 10:   */ {
/* 11:   */   public AllOff()
/* 12:   */   {
/* 13:12 */     super("alloff");
/* 14:   */   }
/* 15:   */   
/* 16:   */   public void runCommand(String commandRun, String[] commandArgs)
/* 17:   */   {
/* 18:   */     try
/* 19:   */     {
/* 20:20 */       for (NodusModule nodusModule : Nodus.theNodus.moduleManager.getModules()) {
/* 21:22 */         if (nodusModule.isToggled()) {
/* 22:24 */           nodusModule.setToggled(false);
/* 23:   */         }
/* 24:   */       }
/* 25:27 */       Nodus.theNodus.addChatMessage("All mods set to off.");
/* 26:   */     }
/* 27:   */     catch (Exception commandSyntaxException)
/* 28:   */     {
/* 29:30 */       commandSyntaxException.printStackTrace();
/* 30:31 */       Nodus.theNodus.addChatMessage("Command Usage: " + getSyntax());
/* 31:   */     }
/* 32:   */   }
/* 33:   */   
/* 34:   */   public String getDescription()
/* 35:   */   {
/* 36:38 */     return "Turns all mods off";
/* 37:   */   }
/* 38:   */   
/* 39:   */   public String getSyntax()
/* 40:   */   {
/* 41:44 */     return "alloff";
/* 42:   */   }
/* 43:   */   
/* 44:   */   public String getConsoleDisplay()
/* 45:   */   {
/* 46:50 */     return "alloff";
/* 47:   */   }
/* 48:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.command.commands.AllOff
 * JD-Core Version:    0.7.0.1
 */