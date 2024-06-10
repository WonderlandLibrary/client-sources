/*  1:   */ package net.minecraft.command;
/*  2:   */ 
/*  3:   */ import java.util.Iterator;
/*  4:   */ import java.util.List;
/*  5:   */ import net.minecraft.entity.player.EntityPlayerMP;
/*  6:   */ import net.minecraft.server.MinecraftServer;
/*  7:   */ import net.minecraft.server.management.ServerConfigurationManager;
/*  8:   */ import net.minecraft.util.ChatComponentTranslation;
/*  9:   */ import net.minecraft.world.WorldSettings.GameType;
/* 10:   */ 
/* 11:   */ public class CommandDefaultGameMode
/* 12:   */   extends CommandGameMode
/* 13:   */ {
/* 14:   */   private static final String __OBFID = "CL_00000296";
/* 15:   */   
/* 16:   */   public String getCommandName()
/* 17:   */   {
/* 18:15 */     return "defaultgamemode";
/* 19:   */   }
/* 20:   */   
/* 21:   */   public String getCommandUsage(ICommandSender par1ICommandSender)
/* 22:   */   {
/* 23:20 */     return "commands.defaultgamemode.usage";
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/* 27:   */   {
/* 28:25 */     if (par2ArrayOfStr.length > 0)
/* 29:   */     {
/* 30:27 */       WorldSettings.GameType var3 = getGameModeFromCommand(par1ICommandSender, par2ArrayOfStr[0]);
/* 31:28 */       setGameType(var3);
/* 32:29 */       notifyAdmins(par1ICommandSender, "commands.defaultgamemode.success", new Object[] { new ChatComponentTranslation("gameMode." + var3.getName(), new Object[0]) });
/* 33:   */     }
/* 34:   */     else
/* 35:   */     {
/* 36:33 */       throw new WrongUsageException("commands.defaultgamemode.usage", new Object[0]);
/* 37:   */     }
/* 38:   */   }
/* 39:   */   
/* 40:   */   protected void setGameType(WorldSettings.GameType par1EnumGameType)
/* 41:   */   {
/* 42:39 */     MinecraftServer var2 = MinecraftServer.getServer();
/* 43:40 */     var2.setGameType(par1EnumGameType);
/* 44:43 */     if (var2.getForceGamemode())
/* 45:   */     {
/* 46:   */       EntityPlayerMP var4;
/* 47:45 */       for (Iterator var3 = MinecraftServer.getServer().getConfigurationManager().playerEntityList.iterator(); var3.hasNext(); var4.fallDistance = 0.0F)
/* 48:   */       {
/* 49:47 */         var4 = (EntityPlayerMP)var3.next();
/* 50:48 */         var4.setGameType(par1EnumGameType);
/* 51:   */       }
/* 52:   */     }
/* 53:   */   }
/* 54:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.command.CommandDefaultGameMode
 * JD-Core Version:    0.7.0.1
 */