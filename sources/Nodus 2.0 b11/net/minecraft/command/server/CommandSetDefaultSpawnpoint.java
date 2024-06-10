/*  1:   */ package net.minecraft.command.server;
/*  2:   */ 
/*  3:   */ import net.minecraft.command.CommandBase;
/*  4:   */ import net.minecraft.command.ICommandSender;
/*  5:   */ import net.minecraft.command.WrongUsageException;
/*  6:   */ import net.minecraft.entity.player.EntityPlayerMP;
/*  7:   */ import net.minecraft.util.ChunkCoordinates;
/*  8:   */ import net.minecraft.world.World;
/*  9:   */ 
/* 10:   */ public class CommandSetDefaultSpawnpoint
/* 11:   */   extends CommandBase
/* 12:   */ {
/* 13:   */   private static final String __OBFID = "CL_00000973";
/* 14:   */   
/* 15:   */   public String getCommandName()
/* 16:   */   {
/* 17:14 */     return "setworldspawn";
/* 18:   */   }
/* 19:   */   
/* 20:   */   public int getRequiredPermissionLevel()
/* 21:   */   {
/* 22:22 */     return 2;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public String getCommandUsage(ICommandSender par1ICommandSender)
/* 26:   */   {
/* 27:27 */     return "commands.setworldspawn.usage";
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/* 31:   */   {
/* 32:32 */     if (par2ArrayOfStr.length == 3)
/* 33:   */     {
/* 34:34 */       if (par1ICommandSender.getEntityWorld() == null) {
/* 35:36 */         throw new WrongUsageException("commands.setworldspawn.usage", new Object[0]);
/* 36:   */       }
/* 37:39 */       byte var3 = 0;
/* 38:40 */       int var8 = var3 + 1;
/* 39:41 */       int var4 = parseIntBounded(par1ICommandSender, par2ArrayOfStr[var3], -30000000, 30000000);
/* 40:42 */       int var5 = parseIntBounded(par1ICommandSender, par2ArrayOfStr[(var8++)], 0, 256);
/* 41:43 */       int var6 = parseIntBounded(par1ICommandSender, par2ArrayOfStr[(var8++)], -30000000, 30000000);
/* 42:44 */       par1ICommandSender.getEntityWorld().setSpawnLocation(var4, var5, var6);
/* 43:45 */       notifyAdmins(par1ICommandSender, "commands.setworldspawn.success", new Object[] { Integer.valueOf(var4), Integer.valueOf(var5), Integer.valueOf(var6) });
/* 44:   */     }
/* 45:   */     else
/* 46:   */     {
/* 47:49 */       if (par2ArrayOfStr.length != 0) {
/* 48:51 */         throw new WrongUsageException("commands.setworldspawn.usage", new Object[0]);
/* 49:   */       }
/* 50:54 */       ChunkCoordinates var9 = getCommandSenderAsPlayer(par1ICommandSender).getPlayerCoordinates();
/* 51:55 */       par1ICommandSender.getEntityWorld().setSpawnLocation(var9.posX, var9.posY, var9.posZ);
/* 52:56 */       notifyAdmins(par1ICommandSender, "commands.setworldspawn.success", new Object[] { Integer.valueOf(var9.posX), Integer.valueOf(var9.posY), Integer.valueOf(var9.posZ) });
/* 53:   */     }
/* 54:   */   }
/* 55:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.command.server.CommandSetDefaultSpawnpoint
 * JD-Core Version:    0.7.0.1
 */