/*  1:   */ package net.minecraft.command.server;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import net.minecraft.command.CommandBase;
/*  5:   */ import net.minecraft.command.ICommandSender;
/*  6:   */ import net.minecraft.command.WrongUsageException;
/*  7:   */ import net.minecraft.server.MinecraftServer;
/*  8:   */ import net.minecraft.server.management.ServerConfigurationManager;
/*  9:   */ 
/* 10:   */ public class CommandDeOp
/* 11:   */   extends CommandBase
/* 12:   */ {
/* 13:   */   private static final String __OBFID = "CL_00000244";
/* 14:   */   
/* 15:   */   public String getCommandName()
/* 16:   */   {
/* 17:15 */     return "deop";
/* 18:   */   }
/* 19:   */   
/* 20:   */   public int getRequiredPermissionLevel()
/* 21:   */   {
/* 22:23 */     return 3;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public String getCommandUsage(ICommandSender par1ICommandSender)
/* 26:   */   {
/* 27:28 */     return "commands.deop.usage";
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/* 31:   */   {
/* 32:33 */     if ((par2ArrayOfStr.length == 1) && (par2ArrayOfStr[0].length() > 0))
/* 33:   */     {
/* 34:35 */       MinecraftServer.getServer().getConfigurationManager().removeOp(par2ArrayOfStr[0]);
/* 35:36 */       notifyAdmins(par1ICommandSender, "commands.deop.success", new Object[] { par2ArrayOfStr[0] });
/* 36:   */     }
/* 37:   */     else
/* 38:   */     {
/* 39:40 */       throw new WrongUsageException("commands.deop.usage", new Object[0]);
/* 40:   */     }
/* 41:   */   }
/* 42:   */   
/* 43:   */   public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/* 44:   */   {
/* 45:49 */     return par2ArrayOfStr.length == 1 ? getListOfStringsFromIterableMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getConfigurationManager().getOps()) : null;
/* 46:   */   }
/* 47:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.command.server.CommandDeOp
 * JD-Core Version:    0.7.0.1
 */