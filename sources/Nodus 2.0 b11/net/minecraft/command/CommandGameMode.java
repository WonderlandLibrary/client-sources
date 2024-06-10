/*  1:   */ package net.minecraft.command;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import net.minecraft.entity.player.EntityPlayerMP;
/*  5:   */ import net.minecraft.server.MinecraftServer;
/*  6:   */ import net.minecraft.util.ChatComponentTranslation;
/*  7:   */ import net.minecraft.world.WorldSettings;
/*  8:   */ import net.minecraft.world.WorldSettings.GameType;
/*  9:   */ 
/* 10:   */ public class CommandGameMode
/* 11:   */   extends CommandBase
/* 12:   */ {
/* 13:   */   private static final String __OBFID = "CL_00000448";
/* 14:   */   
/* 15:   */   public String getCommandName()
/* 16:   */   {
/* 17:15 */     return "gamemode";
/* 18:   */   }
/* 19:   */   
/* 20:   */   public int getRequiredPermissionLevel()
/* 21:   */   {
/* 22:23 */     return 2;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public String getCommandUsage(ICommandSender par1ICommandSender)
/* 26:   */   {
/* 27:28 */     return "commands.gamemode.usage";
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/* 31:   */   {
/* 32:33 */     if (par2ArrayOfStr.length > 0)
/* 33:   */     {
/* 34:35 */       WorldSettings.GameType var3 = getGameModeFromCommand(par1ICommandSender, par2ArrayOfStr[0]);
/* 35:36 */       EntityPlayerMP var4 = par2ArrayOfStr.length >= 2 ? getPlayer(par1ICommandSender, par2ArrayOfStr[1]) : getCommandSenderAsPlayer(par1ICommandSender);
/* 36:37 */       var4.setGameType(var3);
/* 37:38 */       var4.fallDistance = 0.0F;
/* 38:39 */       ChatComponentTranslation var5 = new ChatComponentTranslation("gameMode." + var3.getName(), new Object[0]);
/* 39:41 */       if (var4 != par1ICommandSender) {
/* 40:43 */         notifyAdmins(par1ICommandSender, 1, "commands.gamemode.success.other", new Object[] { var4.getCommandSenderName(), var5 });
/* 41:   */       } else {
/* 42:47 */         notifyAdmins(par1ICommandSender, 1, "commands.gamemode.success.self", new Object[] { var5 });
/* 43:   */       }
/* 44:   */     }
/* 45:   */     else
/* 46:   */     {
/* 47:52 */       throw new WrongUsageException("commands.gamemode.usage", new Object[0]);
/* 48:   */     }
/* 49:   */   }
/* 50:   */   
/* 51:   */   protected WorldSettings.GameType getGameModeFromCommand(ICommandSender par1ICommandSender, String par2Str)
/* 52:   */   {
/* 53:61 */     return (!par2Str.equalsIgnoreCase(WorldSettings.GameType.SURVIVAL.getName())) && (!par2Str.equalsIgnoreCase("s")) ? WorldSettings.GameType.CREATIVE : (!par2Str.equalsIgnoreCase(WorldSettings.GameType.CREATIVE.getName())) && (!par2Str.equalsIgnoreCase("c")) ? WorldSettings.GameType.ADVENTURE : (!par2Str.equalsIgnoreCase(WorldSettings.GameType.ADVENTURE.getName())) && (!par2Str.equalsIgnoreCase("a")) ? WorldSettings.getGameTypeById(parseIntBounded(par1ICommandSender, par2Str, 0, WorldSettings.GameType.values().length - 2)) : WorldSettings.GameType.SURVIVAL;
/* 54:   */   }
/* 55:   */   
/* 56:   */   public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/* 57:   */   {
/* 58:69 */     return par2ArrayOfStr.length == 2 ? getListOfStringsMatchingLastWord(par2ArrayOfStr, getListOfPlayerUsernames()) : par2ArrayOfStr.length == 1 ? getListOfStringsMatchingLastWord(par2ArrayOfStr, new String[] { "survival", "creative", "adventure" }) : null;
/* 59:   */   }
/* 60:   */   
/* 61:   */   protected String[] getListOfPlayerUsernames()
/* 62:   */   {
/* 63:77 */     return MinecraftServer.getServer().getAllUsernames();
/* 64:   */   }
/* 65:   */   
/* 66:   */   public boolean isUsernameIndex(String[] par1ArrayOfStr, int par2)
/* 67:   */   {
/* 68:85 */     return par2 == 1;
/* 69:   */   }
/* 70:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.command.CommandGameMode
 * JD-Core Version:    0.7.0.1
 */