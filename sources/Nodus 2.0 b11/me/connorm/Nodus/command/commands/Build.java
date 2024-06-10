/*  1:   */ package me.connorm.Nodus.command.commands;
/*  2:   */ 
/*  3:   */ import me.connorm.Nodus.Nodus;
/*  4:   */ import me.connorm.Nodus.command.NodusCommand;
/*  5:   */ import me.connorm.Nodus.module.NodusModuleManager;
/*  6:   */ 
/*  7:   */ public class Build
/*  8:   */   extends NodusCommand
/*  9:   */ {
/* 10:   */   public Build()
/* 11:   */   {
/* 12:10 */     super("build");
/* 13:   */   }
/* 14:   */   
/* 15:   */   public void runCommand(String commandRun, String[] commandArgs)
/* 16:   */   {
/* 17:   */     try
/* 18:   */     {
/* 19:18 */       if (commandArgs[0].equalsIgnoreCase("mode"))
/* 20:   */       {
/* 21:20 */         if (commandArgs[1].equalsIgnoreCase("none"))
/* 22:   */         {
/* 23:22 */           Nodus.theNodus.moduleManager.buildModule.buildMode = 0;
/* 24:23 */           Nodus.theNodus.addChatMessage("Build mode set to none.");
/* 25:   */         }
/* 26:25 */         if (commandArgs[1].equalsIgnoreCase("floor"))
/* 27:   */         {
/* 28:27 */           Nodus.theNodus.moduleManager.buildModule.buildMode = 1;
/* 29:28 */           Nodus.theNodus.addChatMessage("Build mode set to floor.");
/* 30:   */         }
/* 31:30 */         if (commandArgs[1].equalsIgnoreCase("pole"))
/* 32:   */         {
/* 33:32 */           Nodus.theNodus.moduleManager.buildModule.buildMode = 2;
/* 34:33 */           Nodus.theNodus.addChatMessage("Build mode set to pole.");
/* 35:   */         }
/* 36:   */       }
/* 37:   */     }
/* 38:   */     catch (Exception commandSyntaxException)
/* 39:   */     {
/* 40:38 */       commandSyntaxException.printStackTrace();
/* 41:39 */       Nodus.theNodus.addChatMessage("Command Usage: " + getSyntax());
/* 42:   */     }
/* 43:   */   }
/* 44:   */   
/* 45:   */   public String getDescription()
/* 46:   */   {
/* 47:46 */     return "Edits build mode";
/* 48:   */   }
/* 49:   */   
/* 50:   */   public String getSyntax()
/* 51:   */   {
/* 52:52 */     return "mode <pole, floor, none>";
/* 53:   */   }
/* 54:   */   
/* 55:   */   public String getConsoleDisplay()
/* 56:   */   {
/* 57:58 */     return "build";
/* 58:   */   }
/* 59:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.command.commands.Build
 * JD-Core Version:    0.7.0.1
 */