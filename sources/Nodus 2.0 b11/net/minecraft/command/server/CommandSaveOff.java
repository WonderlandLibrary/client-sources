/*  1:   */ package net.minecraft.command.server;
/*  2:   */ 
/*  3:   */ import net.minecraft.command.CommandBase;
/*  4:   */ import net.minecraft.command.CommandException;
/*  5:   */ import net.minecraft.command.ICommandSender;
/*  6:   */ import net.minecraft.server.MinecraftServer;
/*  7:   */ import net.minecraft.world.WorldServer;
/*  8:   */ 
/*  9:   */ public class CommandSaveOff
/* 10:   */   extends CommandBase
/* 11:   */ {
/* 12:   */   private static final String __OBFID = "CL_00000847";
/* 13:   */   
/* 14:   */   public String getCommandName()
/* 15:   */   {
/* 16:15 */     return "save-off";
/* 17:   */   }
/* 18:   */   
/* 19:   */   public String getCommandUsage(ICommandSender par1ICommandSender)
/* 20:   */   {
/* 21:20 */     return "commands.save-off.usage";
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/* 25:   */   {
/* 26:25 */     MinecraftServer var3 = MinecraftServer.getServer();
/* 27:26 */     boolean var4 = false;
/* 28:28 */     for (int var5 = 0; var5 < var3.worldServers.length; var5++) {
/* 29:30 */       if (var3.worldServers[var5] != null)
/* 30:   */       {
/* 31:32 */         WorldServer var6 = var3.worldServers[var5];
/* 32:34 */         if (!var6.levelSaving)
/* 33:   */         {
/* 34:36 */           var6.levelSaving = true;
/* 35:37 */           var4 = true;
/* 36:   */         }
/* 37:   */       }
/* 38:   */     }
/* 39:42 */     if (var4) {
/* 40:44 */       notifyAdmins(par1ICommandSender, "commands.save.disabled", new Object[0]);
/* 41:   */     } else {
/* 42:48 */       throw new CommandException("commands.save-off.alreadyOff", new Object[0]);
/* 43:   */     }
/* 44:   */   }
/* 45:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.command.server.CommandSaveOff
 * JD-Core Version:    0.7.0.1
 */