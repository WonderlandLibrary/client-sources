/*  1:   */ package net.minecraft.command.server;
/*  2:   */ 
/*  3:   */ import net.minecraft.command.CommandBase;
/*  4:   */ import net.minecraft.command.ICommandSender;
/*  5:   */ import net.minecraft.server.MinecraftServer;
/*  6:   */ 
/*  7:   */ public class CommandStop
/*  8:   */   extends CommandBase
/*  9:   */ {
/* 10:   */   private static final String __OBFID = "CL_00001132";
/* 11:   */   
/* 12:   */   public String getCommandName()
/* 13:   */   {
/* 14:13 */     return "stop";
/* 15:   */   }
/* 16:   */   
/* 17:   */   public String getCommandUsage(ICommandSender par1ICommandSender)
/* 18:   */   {
/* 19:18 */     return "commands.stop.usage";
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/* 23:   */   {
/* 24:23 */     notifyAdmins(par1ICommandSender, "commands.stop.start", new Object[0]);
/* 25:24 */     MinecraftServer.getServer().initiateShutdown();
/* 26:   */   }
/* 27:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.command.server.CommandStop
 * JD-Core Version:    0.7.0.1
 */