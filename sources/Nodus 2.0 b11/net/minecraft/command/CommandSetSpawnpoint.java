/*  1:   */ package net.minecraft.command;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import net.minecraft.entity.player.EntityPlayerMP;
/*  5:   */ import net.minecraft.server.MinecraftServer;
/*  6:   */ import net.minecraft.util.ChunkCoordinates;
/*  7:   */ 
/*  8:   */ public class CommandSetSpawnpoint
/*  9:   */   extends CommandBase
/* 10:   */ {
/* 11:   */   private static final String __OBFID = "CL_00001026";
/* 12:   */   
/* 13:   */   public String getCommandName()
/* 14:   */   {
/* 15:14 */     return "spawnpoint";
/* 16:   */   }
/* 17:   */   
/* 18:   */   public int getRequiredPermissionLevel()
/* 19:   */   {
/* 20:22 */     return 2;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String getCommandUsage(ICommandSender par1ICommandSender)
/* 24:   */   {
/* 25:27 */     return "commands.spawnpoint.usage";
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/* 29:   */   {
/* 30:32 */     EntityPlayerMP var3 = par2ArrayOfStr.length == 0 ? getCommandSenderAsPlayer(par1ICommandSender) : getPlayer(par1ICommandSender, par2ArrayOfStr[0]);
/* 31:34 */     if (par2ArrayOfStr.length == 4)
/* 32:   */     {
/* 33:36 */       if (var3.worldObj != null)
/* 34:   */       {
/* 35:38 */         byte var4 = 1;
/* 36:39 */         int var5 = 30000000;
/* 37:40 */         int var10 = var4 + 1;
/* 38:41 */         int var6 = parseIntBounded(par1ICommandSender, par2ArrayOfStr[var4], -var5, var5);
/* 39:42 */         int var7 = parseIntBounded(par1ICommandSender, par2ArrayOfStr[(var10++)], 0, 256);
/* 40:43 */         int var8 = parseIntBounded(par1ICommandSender, par2ArrayOfStr[(var10++)], -var5, var5);
/* 41:44 */         var3.setSpawnChunk(new ChunkCoordinates(var6, var7, var8), true);
/* 42:45 */         notifyAdmins(par1ICommandSender, "commands.spawnpoint.success", new Object[] { var3.getCommandSenderName(), Integer.valueOf(var6), Integer.valueOf(var7), Integer.valueOf(var8) });
/* 43:   */       }
/* 44:   */     }
/* 45:   */     else
/* 46:   */     {
/* 47:50 */       if (par2ArrayOfStr.length > 1) {
/* 48:52 */         throw new WrongUsageException("commands.spawnpoint.usage", new Object[0]);
/* 49:   */       }
/* 50:55 */       ChunkCoordinates var11 = var3.getPlayerCoordinates();
/* 51:56 */       var3.setSpawnChunk(var11, true);
/* 52:57 */       notifyAdmins(par1ICommandSender, "commands.spawnpoint.success", new Object[] { var3.getCommandSenderName(), Integer.valueOf(var11.posX), Integer.valueOf(var11.posY), Integer.valueOf(var11.posZ) });
/* 53:   */     }
/* 54:   */   }
/* 55:   */   
/* 56:   */   public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/* 57:   */   {
/* 58:66 */     return (par2ArrayOfStr.length != 1) && (par2ArrayOfStr.length != 2) ? null : getListOfStringsMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getAllUsernames());
/* 59:   */   }
/* 60:   */   
/* 61:   */   public boolean isUsernameIndex(String[] par1ArrayOfStr, int par2)
/* 62:   */   {
/* 63:74 */     return par2 == 0;
/* 64:   */   }
/* 65:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.command.CommandSetSpawnpoint
 * JD-Core Version:    0.7.0.1
 */