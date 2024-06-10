/*  1:   */ package net.minecraft.command.server;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import net.minecraft.command.CommandBase;
/*  5:   */ import net.minecraft.command.ICommandSender;
/*  6:   */ import net.minecraft.command.WrongUsageException;
/*  7:   */ import net.minecraft.server.MinecraftServer;
/*  8:   */ import net.minecraft.server.management.ServerConfigurationManager;
/*  9:   */ import net.minecraft.util.ChatComponentTranslation;
/* 10:   */ import net.minecraft.util.IChatComponent;
/* 11:   */ 
/* 12:   */ public class CommandEmote
/* 13:   */   extends CommandBase
/* 14:   */ {
/* 15:   */   private static final String __OBFID = "CL_00000351";
/* 16:   */   
/* 17:   */   public String getCommandName()
/* 18:   */   {
/* 19:17 */     return "me";
/* 20:   */   }
/* 21:   */   
/* 22:   */   public int getRequiredPermissionLevel()
/* 23:   */   {
/* 24:25 */     return 0;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public String getCommandUsage(ICommandSender par1ICommandSender)
/* 28:   */   {
/* 29:30 */     return "commands.me.usage";
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/* 33:   */   {
/* 34:35 */     if (par2ArrayOfStr.length > 0)
/* 35:   */     {
/* 36:37 */       IChatComponent var3 = func_147176_a(par1ICommandSender, par2ArrayOfStr, 0, par1ICommandSender.canCommandSenderUseCommand(1, "me"));
/* 37:38 */       MinecraftServer.getServer().getConfigurationManager().func_148539_a(new ChatComponentTranslation("chat.type.emote", new Object[] { par1ICommandSender.func_145748_c_(), var3 }));
/* 38:   */     }
/* 39:   */     else
/* 40:   */     {
/* 41:42 */       throw new WrongUsageException("commands.me.usage", new Object[0]);
/* 42:   */     }
/* 43:   */   }
/* 44:   */   
/* 45:   */   public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/* 46:   */   {
/* 47:51 */     return getListOfStringsMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getAllUsernames());
/* 48:   */   }
/* 49:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.command.server.CommandEmote
 * JD-Core Version:    0.7.0.1
 */