/*  1:   */ package me.connorm.Nodus.command.commands;
/*  2:   */ 
/*  3:   */ import me.connorm.Nodus.Nodus;
/*  4:   */ import me.connorm.Nodus.command.NodusCommand;
/*  5:   */ import me.connorm.Nodus.module.NodusModuleManager;
/*  6:   */ 
/*  7:   */ public class ChestESP
/*  8:   */   extends NodusCommand
/*  9:   */ {
/* 10:   */   public ChestESP()
/* 11:   */   {
/* 12:10 */     super("chestesp");
/* 13:   */   }
/* 14:   */   
/* 15:   */   public void runCommand(String commandRun, String[] commandArgs)
/* 16:   */   {
/* 17:   */     try
/* 18:   */     {
/* 19:18 */       if (commandArgs[0].equalsIgnoreCase("tracers"))
/* 20:   */       {
/* 21:20 */         Nodus.theNodus.moduleManager.chestESPModule.tracersEnabled = (!Nodus.theNodus.moduleManager.chestESPModule.tracersEnabled);
/* 22:21 */         Nodus.theNodus.addChatMessage("ChestESP tracers set to " + Nodus.theNodus.moduleManager.chestESPModule.tracersEnabled);
/* 23:   */       }
/* 24:   */     }
/* 25:   */     catch (Exception commandSyntaxException)
/* 26:   */     {
/* 27:25 */       commandSyntaxException.printStackTrace();
/* 28:26 */       Nodus.theNodus.addChatMessage("Command Usage: " + getSyntax());
/* 29:   */     }
/* 30:   */   }
/* 31:   */   
/* 32:   */   public String getDescription()
/* 33:   */   {
/* 34:33 */     return "Toggles ChestESP tracers.";
/* 35:   */   }
/* 36:   */   
/* 37:   */   public String getSyntax()
/* 38:   */   {
/* 39:39 */     return "tracers";
/* 40:   */   }
/* 41:   */   
/* 42:   */   public String getConsoleDisplay()
/* 43:   */   {
/* 44:45 */     return "chestesp";
/* 45:   */   }
/* 46:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.command.commands.ChestESP
 * JD-Core Version:    0.7.0.1
 */