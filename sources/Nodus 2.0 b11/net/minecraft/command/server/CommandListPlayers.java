/*  1:   */ package net.minecraft.command.server;
/*  2:   */ 
/*  3:   */ import net.minecraft.command.CommandBase;
/*  4:   */ import net.minecraft.command.ICommandSender;
/*  5:   */ import net.minecraft.server.MinecraftServer;
/*  6:   */ import net.minecraft.server.management.ServerConfigurationManager;
/*  7:   */ import net.minecraft.util.ChatComponentText;
/*  8:   */ import net.minecraft.util.ChatComponentTranslation;
/*  9:   */ 
/* 10:   */ public class CommandListPlayers
/* 11:   */   extends CommandBase
/* 12:   */ {
/* 13:   */   private static final String __OBFID = "CL_00000615";
/* 14:   */   
/* 15:   */   public String getCommandName()
/* 16:   */   {
/* 17:15 */     return "list";
/* 18:   */   }
/* 19:   */   
/* 20:   */   public int getRequiredPermissionLevel()
/* 21:   */   {
/* 22:23 */     return 0;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public String getCommandUsage(ICommandSender par1ICommandSender)
/* 26:   */   {
/* 27:28 */     return "commands.players.usage";
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/* 31:   */   {
/* 32:33 */     par1ICommandSender.addChatMessage(new ChatComponentTranslation("commands.players.list", new Object[] { Integer.valueOf(MinecraftServer.getServer().getCurrentPlayerCount()), Integer.valueOf(MinecraftServer.getServer().getMaxPlayers()) }));
/* 33:34 */     par1ICommandSender.addChatMessage(new ChatComponentText(MinecraftServer.getServer().getConfigurationManager().getPlayerListAsString()));
/* 34:   */   }
/* 35:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.command.server.CommandListPlayers
 * JD-Core Version:    0.7.0.1
 */