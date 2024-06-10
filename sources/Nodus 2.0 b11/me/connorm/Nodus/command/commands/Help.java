/*  1:   */ package me.connorm.Nodus.command.commands;
/*  2:   */ 
/*  3:   */ import me.connorm.Nodus.Nodus;
/*  4:   */ import me.connorm.Nodus.command.NodusCommand;
/*  5:   */ import me.connorm.Nodus.command.NodusCommandManager;
/*  6:   */ 
/*  7:   */ public class Help
/*  8:   */   extends NodusCommand
/*  9:   */ {
/* 10:   */   public Help()
/* 11:   */   {
/* 12:10 */     super("help");
/* 13:   */   }
/* 14:   */   
/* 15:   */   public void runCommand(String commandRun, String[] commandArgs)
/* 16:   */   {
/* 17:16 */     for (NodusCommand theCommand : Nodus.theNodus.commandManager.getCommands()) {
/* 18:18 */       if (theCommand != this) {
/* 19:20 */         Nodus.theNodus.addChatMessage(theCommand.getCommandName() + " - " + theCommand.getDescription() + " | " + theCommand.getSyntax().toString());
/* 20:   */       }
/* 21:   */     }
/* 22:   */   }
/* 23:   */   
/* 24:   */   public String getDescription()
/* 25:   */   {
/* 26:28 */     return "Lists and gives descriptions and syntaxes";
/* 27:   */   }
/* 28:   */   
/* 29:   */   public String getSyntax()
/* 30:   */   {
/* 31:34 */     return "help";
/* 32:   */   }
/* 33:   */   
/* 34:   */   public String getConsoleDisplay()
/* 35:   */   {
/* 36:40 */     return "";
/* 37:   */   }
/* 38:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.command.commands.Help
 * JD-Core Version:    0.7.0.1
 */