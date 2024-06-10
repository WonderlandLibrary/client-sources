/*  1:   */ package me.connorm.Nodus.command.commands;
/*  2:   */ 
/*  3:   */ import me.connorm.Nodus.Nodus;
/*  4:   */ import me.connorm.Nodus.command.NodusCommand;
/*  5:   */ import me.connorm.Nodus.ui.gui.theme.nodus.ColorUtils;
/*  6:   */ 
/*  7:   */ public class Color
/*  8:   */   extends NodusCommand
/*  9:   */ {
/* 10:   */   public Color()
/* 11:   */   {
/* 12:11 */     super("color");
/* 13:   */   }
/* 14:   */   
/* 15:   */   public void runCommand(String commandRun, String[] commandArgs)
/* 16:   */   {
/* 17:   */     try
/* 18:   */     {
/* 19:19 */       if (commandArgs[0].equalsIgnoreCase("primary"))
/* 20:   */       {
/* 21:21 */         ColorUtils.toggleColor(1);
/* 22:22 */         Nodus.theNodus.addChatMessage("Toggled primary GUI color.");
/* 23:   */       }
/* 24:24 */       if (commandArgs[0].equalsIgnoreCase("secondary"))
/* 25:   */       {
/* 26:26 */         ColorUtils.toggleColor(2);
/* 27:27 */         Nodus.theNodus.addChatMessage("Toggled secondary GUI color.");
/* 28:   */       }
/* 29:   */     }
/* 30:   */     catch (Exception commandSyntaxException)
/* 31:   */     {
/* 32:31 */       commandSyntaxException.printStackTrace();
/* 33:32 */       Nodus.theNodus.addChatMessage("Command Usage: " + getSyntax());
/* 34:   */     }
/* 35:   */   }
/* 36:   */   
/* 37:   */   public String getDescription()
/* 38:   */   {
/* 39:39 */     return "toggles GUI colors";
/* 40:   */   }
/* 41:   */   
/* 42:   */   public String getSyntax()
/* 43:   */   {
/* 44:45 */     return "<primary, secondary>";
/* 45:   */   }
/* 46:   */   
/* 47:   */   public String getConsoleDisplay()
/* 48:   */   {
/* 49:51 */     return "color";
/* 50:   */   }
/* 51:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.command.commands.Color
 * JD-Core Version:    0.7.0.1
 */