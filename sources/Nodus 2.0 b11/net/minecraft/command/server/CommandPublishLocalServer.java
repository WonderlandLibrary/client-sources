/*  1:   */ package net.minecraft.command.server;
/*  2:   */ 
/*  3:   */ import net.minecraft.command.CommandBase;
/*  4:   */ import net.minecraft.command.ICommandSender;
/*  5:   */ import net.minecraft.server.MinecraftServer;
/*  6:   */ import net.minecraft.world.WorldSettings.GameType;
/*  7:   */ 
/*  8:   */ public class CommandPublishLocalServer
/*  9:   */   extends CommandBase
/* 10:   */ {
/* 11:   */   private static final String __OBFID = "CL_00000799";
/* 12:   */   
/* 13:   */   public String getCommandName()
/* 14:   */   {
/* 15:14 */     return "publish";
/* 16:   */   }
/* 17:   */   
/* 18:   */   public String getCommandUsage(ICommandSender par1ICommandSender)
/* 19:   */   {
/* 20:19 */     return "commands.publish.usage";
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/* 24:   */   {
/* 25:24 */     String var3 = MinecraftServer.getServer().shareToLAN(WorldSettings.GameType.SURVIVAL, false);
/* 26:26 */     if (var3 != null) {
/* 27:28 */       notifyAdmins(par1ICommandSender, "commands.publish.started", new Object[] { var3 });
/* 28:   */     } else {
/* 29:32 */       notifyAdmins(par1ICommandSender, "commands.publish.failed", new Object[0]);
/* 30:   */     }
/* 31:   */   }
/* 32:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.command.server.CommandPublishLocalServer
 * JD-Core Version:    0.7.0.1
 */