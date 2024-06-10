/*  1:   */ package net.minecraft.command.server;
/*  2:   */ 
/*  3:   */ import net.minecraft.command.CommandBase;
/*  4:   */ import net.minecraft.command.CommandException;
/*  5:   */ import net.minecraft.command.ICommandSender;
/*  6:   */ import net.minecraft.command.WrongUsageException;
/*  7:   */ 
/*  8:   */ public class CommandTestFor
/*  9:   */   extends CommandBase
/* 10:   */ {
/* 11:   */   private static final String __OBFID = "CL_00001182";
/* 12:   */   
/* 13:   */   public String getCommandName()
/* 14:   */   {
/* 15:14 */     return "testfor";
/* 16:   */   }
/* 17:   */   
/* 18:   */   public int getRequiredPermissionLevel()
/* 19:   */   {
/* 20:22 */     return 2;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String getCommandUsage(ICommandSender par1ICommandSender)
/* 24:   */   {
/* 25:27 */     return "commands.testfor.usage";
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/* 29:   */   {
/* 30:32 */     if (par2ArrayOfStr.length != 1) {
/* 31:34 */       throw new WrongUsageException("commands.testfor.usage", new Object[0]);
/* 32:   */     }
/* 33:36 */     if (!(par1ICommandSender instanceof CommandBlockLogic)) {
/* 34:38 */       throw new CommandException("commands.testfor.failed", new Object[0]);
/* 35:   */     }
/* 36:42 */     getPlayer(par1ICommandSender, par2ArrayOfStr[0]);
/* 37:   */   }
/* 38:   */   
/* 39:   */   public boolean isUsernameIndex(String[] par1ArrayOfStr, int par2)
/* 40:   */   {
/* 41:51 */     return par2 == 0;
/* 42:   */   }
/* 43:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.command.server.CommandTestFor
 * JD-Core Version:    0.7.0.1
 */