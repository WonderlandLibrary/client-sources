/*  1:   */ package net.minecraft.command;
/*  2:   */ 
/*  3:   */ import net.minecraft.world.WorldServer;
/*  4:   */ import net.minecraft.world.storage.WorldInfo;
/*  5:   */ 
/*  6:   */ public class CommandToggleDownfall
/*  7:   */   extends CommandBase
/*  8:   */ {
/*  9:   */   private static final String __OBFID = "CL_00001184";
/* 10:   */   
/* 11:   */   public String getCommandName()
/* 12:   */   {
/* 13:12 */     return "toggledownfall";
/* 14:   */   }
/* 15:   */   
/* 16:   */   public int getRequiredPermissionLevel()
/* 17:   */   {
/* 18:20 */     return 2;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public String getCommandUsage(ICommandSender par1ICommandSender)
/* 22:   */   {
/* 23:25 */     return "commands.downfall.usage";
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/* 27:   */   {
/* 28:30 */     toggleDownfall();
/* 29:31 */     notifyAdmins(par1ICommandSender, "commands.downfall.success", new Object[0]);
/* 30:   */   }
/* 31:   */   
/* 32:   */   protected void toggleDownfall()
/* 33:   */   {
/* 34:39 */     WorldInfo var1 = net.minecraft.server.MinecraftServer.getServer().worldServers[0].getWorldInfo();
/* 35:40 */     var1.setRaining(!var1.isRaining());
/* 36:   */   }
/* 37:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.command.CommandToggleDownfall
 * JD-Core Version:    0.7.0.1
 */