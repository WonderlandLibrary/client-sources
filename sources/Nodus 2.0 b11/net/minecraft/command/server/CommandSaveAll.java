/*  1:   */ package net.minecraft.command.server;
/*  2:   */ 
/*  3:   */ import net.minecraft.command.CommandBase;
/*  4:   */ import net.minecraft.command.ICommandSender;
/*  5:   */ import net.minecraft.server.MinecraftServer;
/*  6:   */ import net.minecraft.server.management.ServerConfigurationManager;
/*  7:   */ import net.minecraft.util.ChatComponentTranslation;
/*  8:   */ import net.minecraft.world.MinecraftException;
/*  9:   */ import net.minecraft.world.WorldServer;
/* 10:   */ 
/* 11:   */ public class CommandSaveAll
/* 12:   */   extends CommandBase
/* 13:   */ {
/* 14:   */   private static final String __OBFID = "CL_00000826";
/* 15:   */   
/* 16:   */   public String getCommandName()
/* 17:   */   {
/* 18:17 */     return "save-all";
/* 19:   */   }
/* 20:   */   
/* 21:   */   public String getCommandUsage(ICommandSender par1ICommandSender)
/* 22:   */   {
/* 23:22 */     return "commands.save.usage";
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/* 27:   */   {
/* 28:27 */     MinecraftServer var3 = MinecraftServer.getServer();
/* 29:28 */     par1ICommandSender.addChatMessage(new ChatComponentTranslation("commands.save.start", new Object[0]));
/* 30:30 */     if (var3.getConfigurationManager() != null) {
/* 31:32 */       var3.getConfigurationManager().saveAllPlayerData();
/* 32:   */     }
/* 33:   */     try
/* 34:   */     {
/* 35:41 */       for (int var4 = 0; var4 < var3.worldServers.length; var4++) {
/* 36:43 */         if (var3.worldServers[var4] != null)
/* 37:   */         {
/* 38:45 */           WorldServer var5 = var3.worldServers[var4];
/* 39:46 */           boolean var6 = var5.levelSaving;
/* 40:47 */           var5.levelSaving = false;
/* 41:48 */           var5.saveAllChunks(true, null);
/* 42:49 */           var5.levelSaving = var6;
/* 43:   */         }
/* 44:   */       }
/* 45:53 */       if ((par2ArrayOfStr.length > 0) && ("flush".equals(par2ArrayOfStr[0])))
/* 46:   */       {
/* 47:55 */         par1ICommandSender.addChatMessage(new ChatComponentTranslation("commands.save.flushStart", new Object[0]));
/* 48:57 */         for (var4 = 0; var4 < var3.worldServers.length; var4++) {
/* 49:59 */           if (var3.worldServers[var4] != null)
/* 50:   */           {
/* 51:61 */             WorldServer var5 = var3.worldServers[var4];
/* 52:62 */             boolean var6 = var5.levelSaving;
/* 53:63 */             var5.levelSaving = false;
/* 54:64 */             var5.saveChunkData();
/* 55:65 */             var5.levelSaving = var6;
/* 56:   */           }
/* 57:   */         }
/* 58:69 */         par1ICommandSender.addChatMessage(new ChatComponentTranslation("commands.save.flushEnd", new Object[0]));
/* 59:   */       }
/* 60:   */     }
/* 61:   */     catch (MinecraftException var7)
/* 62:   */     {
/* 63:74 */       notifyAdmins(par1ICommandSender, "commands.save.failed", new Object[] { var7.getMessage() });
/* 64:75 */       return;
/* 65:   */     }
/* 66:78 */     notifyAdmins(par1ICommandSender, "commands.save.success", new Object[0]);
/* 67:   */   }
/* 68:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.command.server.CommandSaveAll
 * JD-Core Version:    0.7.0.1
 */