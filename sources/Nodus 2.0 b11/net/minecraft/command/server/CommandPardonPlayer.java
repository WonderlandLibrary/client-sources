/*  1:   */ package net.minecraft.command.server;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import java.util.Map;
/*  5:   */ import net.minecraft.command.CommandBase;
/*  6:   */ import net.minecraft.command.ICommandSender;
/*  7:   */ import net.minecraft.command.WrongUsageException;
/*  8:   */ import net.minecraft.server.MinecraftServer;
/*  9:   */ import net.minecraft.server.management.BanList;
/* 10:   */ import net.minecraft.server.management.ServerConfigurationManager;
/* 11:   */ 
/* 12:   */ public class CommandPardonPlayer
/* 13:   */   extends CommandBase
/* 14:   */ {
/* 15:   */   private static final String __OBFID = "CL_00000747";
/* 16:   */   
/* 17:   */   public String getCommandName()
/* 18:   */   {
/* 19:15 */     return "pardon";
/* 20:   */   }
/* 21:   */   
/* 22:   */   public int getRequiredPermissionLevel()
/* 23:   */   {
/* 24:23 */     return 3;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public String getCommandUsage(ICommandSender par1ICommandSender)
/* 28:   */   {
/* 29:28 */     return "commands.unban.usage";
/* 30:   */   }
/* 31:   */   
/* 32:   */   public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender)
/* 33:   */   {
/* 34:36 */     return (MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().isListActive()) && (super.canCommandSenderUseCommand(par1ICommandSender));
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/* 38:   */   {
/* 39:41 */     if ((par2ArrayOfStr.length == 1) && (par2ArrayOfStr[0].length() > 0))
/* 40:   */     {
/* 41:43 */       MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().remove(par2ArrayOfStr[0]);
/* 42:44 */       notifyAdmins(par1ICommandSender, "commands.unban.success", new Object[] { par2ArrayOfStr[0] });
/* 43:   */     }
/* 44:   */     else
/* 45:   */     {
/* 46:48 */       throw new WrongUsageException("commands.unban.usage", new Object[0]);
/* 47:   */     }
/* 48:   */   }
/* 49:   */   
/* 50:   */   public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/* 51:   */   {
/* 52:57 */     return par2ArrayOfStr.length == 1 ? getListOfStringsFromIterableMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().getBannedList().keySet()) : null;
/* 53:   */   }
/* 54:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.command.server.CommandPardonPlayer
 * JD-Core Version:    0.7.0.1
 */