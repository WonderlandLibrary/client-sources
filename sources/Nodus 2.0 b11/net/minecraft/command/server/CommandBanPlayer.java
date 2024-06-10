/*  1:   */ package net.minecraft.command.server;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import net.minecraft.command.CommandBase;
/*  5:   */ import net.minecraft.command.ICommandSender;
/*  6:   */ import net.minecraft.command.WrongUsageException;
/*  7:   */ import net.minecraft.entity.player.EntityPlayerMP;
/*  8:   */ import net.minecraft.network.NetHandlerPlayServer;
/*  9:   */ import net.minecraft.server.MinecraftServer;
/* 10:   */ import net.minecraft.server.management.BanEntry;
/* 11:   */ import net.minecraft.server.management.BanList;
/* 12:   */ import net.minecraft.server.management.ServerConfigurationManager;
/* 13:   */ import net.minecraft.util.IChatComponent;
/* 14:   */ 
/* 15:   */ public class CommandBanPlayer
/* 16:   */   extends CommandBase
/* 17:   */ {
/* 18:   */   private static final String __OBFID = "CL_00000165";
/* 19:   */   
/* 20:   */   public String getCommandName()
/* 21:   */   {
/* 22:17 */     return "ban";
/* 23:   */   }
/* 24:   */   
/* 25:   */   public int getRequiredPermissionLevel()
/* 26:   */   {
/* 27:25 */     return 3;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public String getCommandUsage(ICommandSender par1ICommandSender)
/* 31:   */   {
/* 32:30 */     return "commands.ban.usage";
/* 33:   */   }
/* 34:   */   
/* 35:   */   public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender)
/* 36:   */   {
/* 37:38 */     return (MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().isListActive()) && (super.canCommandSenderUseCommand(par1ICommandSender));
/* 38:   */   }
/* 39:   */   
/* 40:   */   public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/* 41:   */   {
/* 42:43 */     if ((par2ArrayOfStr.length >= 1) && (par2ArrayOfStr[0].length() > 0))
/* 43:   */     {
/* 44:45 */       EntityPlayerMP var3 = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(par2ArrayOfStr[0]);
/* 45:46 */       BanEntry var4 = new BanEntry(par2ArrayOfStr[0]);
/* 46:47 */       var4.setBannedBy(par1ICommandSender.getCommandSenderName());
/* 47:49 */       if (par2ArrayOfStr.length >= 2) {
/* 48:51 */         var4.setBanReason(func_147178_a(par1ICommandSender, par2ArrayOfStr, 1).getUnformattedText());
/* 49:   */       }
/* 50:54 */       MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().put(var4);
/* 51:56 */       if (var3 != null) {
/* 52:58 */         var3.playerNetServerHandler.kickPlayerFromServer("You are banned from this server.");
/* 53:   */       }
/* 54:61 */       notifyAdmins(par1ICommandSender, "commands.ban.success", new Object[] { par2ArrayOfStr[0] });
/* 55:   */     }
/* 56:   */     else
/* 57:   */     {
/* 58:65 */       throw new WrongUsageException("commands.ban.usage", new Object[0]);
/* 59:   */     }
/* 60:   */   }
/* 61:   */   
/* 62:   */   public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/* 63:   */   {
/* 64:74 */     return par2ArrayOfStr.length >= 1 ? getListOfStringsMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getAllUsernames()) : null;
/* 65:   */   }
/* 66:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.command.server.CommandBanPlayer
 * JD-Core Version:    0.7.0.1
 */