/*  1:   */ package net.minecraft.command;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import net.minecraft.entity.player.EntityPlayerMP;
/*  5:   */ import net.minecraft.network.NetHandlerPlayServer;
/*  6:   */ import net.minecraft.server.MinecraftServer;
/*  7:   */ import net.minecraft.server.management.ServerConfigurationManager;
/*  8:   */ import net.minecraft.util.IChatComponent;
/*  9:   */ 
/* 10:   */ public class CommandServerKick
/* 11:   */   extends CommandBase
/* 12:   */ {
/* 13:   */   private static final String __OBFID = "CL_00000550";
/* 14:   */   
/* 15:   */   public String getCommandName()
/* 16:   */   {
/* 17:13 */     return "kick";
/* 18:   */   }
/* 19:   */   
/* 20:   */   public int getRequiredPermissionLevel()
/* 21:   */   {
/* 22:21 */     return 3;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public String getCommandUsage(ICommandSender par1ICommandSender)
/* 26:   */   {
/* 27:26 */     return "commands.kick.usage";
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/* 31:   */   {
/* 32:31 */     if ((par2ArrayOfStr.length > 0) && (par2ArrayOfStr[0].length() > 1))
/* 33:   */     {
/* 34:33 */       EntityPlayerMP var3 = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(par2ArrayOfStr[0]);
/* 35:34 */       String var4 = "Kicked by an operator.";
/* 36:35 */       boolean var5 = false;
/* 37:37 */       if (var3 == null) {
/* 38:39 */         throw new PlayerNotFoundException();
/* 39:   */       }
/* 40:43 */       if (par2ArrayOfStr.length >= 2)
/* 41:   */       {
/* 42:45 */         var4 = func_147178_a(par1ICommandSender, par2ArrayOfStr, 1).getUnformattedText();
/* 43:46 */         var5 = true;
/* 44:   */       }
/* 45:49 */       var3.playerNetServerHandler.kickPlayerFromServer(var4);
/* 46:51 */       if (var5) {
/* 47:53 */         notifyAdmins(par1ICommandSender, "commands.kick.success.reason", new Object[] { var3.getCommandSenderName(), var4 });
/* 48:   */       } else {
/* 49:57 */         notifyAdmins(par1ICommandSender, "commands.kick.success", new Object[] { var3.getCommandSenderName() });
/* 50:   */       }
/* 51:   */     }
/* 52:   */     else
/* 53:   */     {
/* 54:63 */       throw new WrongUsageException("commands.kick.usage", new Object[0]);
/* 55:   */     }
/* 56:   */   }
/* 57:   */   
/* 58:   */   public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/* 59:   */   {
/* 60:72 */     return par2ArrayOfStr.length >= 1 ? getListOfStringsMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getAllUsernames()) : null;
/* 61:   */   }
/* 62:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.command.CommandServerKick
 * JD-Core Version:    0.7.0.1
 */