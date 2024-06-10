/*  1:   */ package net.minecraft.command.server;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import java.util.Map;
/*  5:   */ import java.util.regex.Matcher;
/*  6:   */ import java.util.regex.Pattern;
/*  7:   */ import net.minecraft.command.CommandBase;
/*  8:   */ import net.minecraft.command.ICommandSender;
/*  9:   */ import net.minecraft.command.SyntaxErrorException;
/* 10:   */ import net.minecraft.command.WrongUsageException;
/* 11:   */ import net.minecraft.server.MinecraftServer;
/* 12:   */ import net.minecraft.server.management.BanList;
/* 13:   */ import net.minecraft.server.management.ServerConfigurationManager;
/* 14:   */ 
/* 15:   */ public class CommandPardonIp
/* 16:   */   extends CommandBase
/* 17:   */ {
/* 18:   */   private static final String __OBFID = "CL_00000720";
/* 19:   */   
/* 20:   */   public String getCommandName()
/* 21:   */   {
/* 22:17 */     return "pardon-ip";
/* 23:   */   }
/* 24:   */   
/* 25:   */   public int getRequiredPermissionLevel()
/* 26:   */   {
/* 27:25 */     return 3;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender)
/* 31:   */   {
/* 32:33 */     return (MinecraftServer.getServer().getConfigurationManager().getBannedIPs().isListActive()) && (super.canCommandSenderUseCommand(par1ICommandSender));
/* 33:   */   }
/* 34:   */   
/* 35:   */   public String getCommandUsage(ICommandSender par1ICommandSender)
/* 36:   */   {
/* 37:38 */     return "commands.unbanip.usage";
/* 38:   */   }
/* 39:   */   
/* 40:   */   public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/* 41:   */   {
/* 42:43 */     if ((par2ArrayOfStr.length == 1) && (par2ArrayOfStr[0].length() > 1))
/* 43:   */     {
/* 44:45 */       Matcher var3 = CommandBanIp.field_147211_a.matcher(par2ArrayOfStr[0]);
/* 45:47 */       if (var3.matches())
/* 46:   */       {
/* 47:49 */         MinecraftServer.getServer().getConfigurationManager().getBannedIPs().remove(par2ArrayOfStr[0]);
/* 48:50 */         notifyAdmins(par1ICommandSender, "commands.unbanip.success", new Object[] { par2ArrayOfStr[0] });
/* 49:   */       }
/* 50:   */       else
/* 51:   */       {
/* 52:54 */         throw new SyntaxErrorException("commands.unbanip.invalid", new Object[0]);
/* 53:   */       }
/* 54:   */     }
/* 55:   */     else
/* 56:   */     {
/* 57:59 */       throw new WrongUsageException("commands.unbanip.usage", new Object[0]);
/* 58:   */     }
/* 59:   */   }
/* 60:   */   
/* 61:   */   public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/* 62:   */   {
/* 63:68 */     return par2ArrayOfStr.length == 1 ? getListOfStringsFromIterableMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getConfigurationManager().getBannedIPs().getBannedList().keySet()) : null;
/* 64:   */   }
/* 65:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.command.server.CommandPardonIp
 * JD-Core Version:    0.7.0.1
 */