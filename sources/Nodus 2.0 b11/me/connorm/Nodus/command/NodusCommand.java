/*  1:   */ package me.connorm.Nodus.command;
/*  2:   */ 
/*  3:   */ public abstract class NodusCommand
/*  4:   */ {
/*  5:   */   private String commandName;
/*  6:   */   
/*  7:   */   public NodusCommand(String commandName)
/*  8:   */   {
/*  9: 9 */     this.commandName = commandName;
/* 10:   */   }
/* 11:   */   
/* 12:   */   public abstract void runCommand(String paramString, String[] paramArrayOfString);
/* 13:   */   
/* 14:   */   public abstract String getDescription();
/* 15:   */   
/* 16:   */   public abstract String getSyntax();
/* 17:   */   
/* 18:   */   public abstract String getConsoleDisplay();
/* 19:   */   
/* 20:   */   public String getCommandName()
/* 21:   */   {
/* 22:22 */     return this.commandName;
/* 23:   */   }
/* 24:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.command.NodusCommand
 * JD-Core Version:    0.7.0.1
 */