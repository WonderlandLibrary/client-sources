/*  1:   */ package net.minecraft.command;
/*  2:   */ 
/*  3:   */ import net.minecraft.server.MinecraftServer;
/*  4:   */ 
/*  5:   */ public class CommandSetPlayerTimeout
/*  6:   */   extends CommandBase
/*  7:   */ {
/*  8:   */   private static final String __OBFID = "CL_00000999";
/*  9:   */   
/* 10:   */   public String getCommandName()
/* 11:   */   {
/* 12:11 */     return "setidletimeout";
/* 13:   */   }
/* 14:   */   
/* 15:   */   public int getRequiredPermissionLevel()
/* 16:   */   {
/* 17:19 */     return 3;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public String getCommandUsage(ICommandSender par1ICommandSender)
/* 21:   */   {
/* 22:24 */     return "commands.setidletimeout.usage";
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/* 26:   */   {
/* 27:29 */     if (par2ArrayOfStr.length == 1)
/* 28:   */     {
/* 29:31 */       int var3 = parseIntWithMin(par1ICommandSender, par2ArrayOfStr[0], 0);
/* 30:32 */       MinecraftServer.getServer().func_143006_e(var3);
/* 31:33 */       notifyAdmins(par1ICommandSender, "commands.setidletimeout.success", new Object[] { Integer.valueOf(var3) });
/* 32:   */     }
/* 33:   */     else
/* 34:   */     {
/* 35:37 */       throw new WrongUsageException("commands.setidletimeout.usage", new Object[0]);
/* 36:   */     }
/* 37:   */   }
/* 38:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.command.CommandSetPlayerTimeout
 * JD-Core Version:    0.7.0.1
 */