/*  1:   */ package net.minecraft.command.server;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import java.util.Map;
/*  5:   */ import java.util.Set;
/*  6:   */ import net.minecraft.command.CommandBase;
/*  7:   */ import net.minecraft.command.ICommandSender;
/*  8:   */ import net.minecraft.server.MinecraftServer;
/*  9:   */ import net.minecraft.server.management.BanList;
/* 10:   */ import net.minecraft.server.management.ServerConfigurationManager;
/* 11:   */ import net.minecraft.util.ChatComponentText;
/* 12:   */ import net.minecraft.util.ChatComponentTranslation;
/* 13:   */ 
/* 14:   */ public class CommandListBans
/* 15:   */   extends CommandBase
/* 16:   */ {
/* 17:   */   private static final String __OBFID = "CL_00000596";
/* 18:   */   
/* 19:   */   public String getCommandName()
/* 20:   */   {
/* 21:16 */     return "banlist";
/* 22:   */   }
/* 23:   */   
/* 24:   */   public int getRequiredPermissionLevel()
/* 25:   */   {
/* 26:24 */     return 3;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender)
/* 30:   */   {
/* 31:32 */     return ((MinecraftServer.getServer().getConfigurationManager().getBannedIPs().isListActive()) || (MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().isListActive())) && (super.canCommandSenderUseCommand(par1ICommandSender));
/* 32:   */   }
/* 33:   */   
/* 34:   */   public String getCommandUsage(ICommandSender par1ICommandSender)
/* 35:   */   {
/* 36:37 */     return "commands.banlist.usage";
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/* 40:   */   {
/* 41:42 */     if ((par2ArrayOfStr.length >= 1) && (par2ArrayOfStr[0].equalsIgnoreCase("ips")))
/* 42:   */     {
/* 43:44 */       par1ICommandSender.addChatMessage(new ChatComponentTranslation("commands.banlist.ips", new Object[] { Integer.valueOf(MinecraftServer.getServer().getConfigurationManager().getBannedIPs().getBannedList().size()) }));
/* 44:45 */       par1ICommandSender.addChatMessage(new ChatComponentText(joinNiceString(MinecraftServer.getServer().getConfigurationManager().getBannedIPs().getBannedList().keySet().toArray())));
/* 45:   */     }
/* 46:   */     else
/* 47:   */     {
/* 48:49 */       par1ICommandSender.addChatMessage(new ChatComponentTranslation("commands.banlist.players", new Object[] { Integer.valueOf(MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().getBannedList().size()) }));
/* 49:50 */       par1ICommandSender.addChatMessage(new ChatComponentText(joinNiceString(MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().getBannedList().keySet().toArray())));
/* 50:   */     }
/* 51:   */   }
/* 52:   */   
/* 53:   */   public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/* 54:   */   {
/* 55:59 */     return par2ArrayOfStr.length == 1 ? getListOfStringsMatchingLastWord(par2ArrayOfStr, new String[] { "players", "ips" }) : null;
/* 56:   */   }
/* 57:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.command.server.CommandListBans
 * JD-Core Version:    0.7.0.1
 */