/*  1:   */ package net.minecraft.command;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import net.minecraft.server.MinecraftServer;
/*  5:   */ import net.minecraft.util.ChatComponentText;
/*  6:   */ import net.minecraft.util.IChatComponent;
/*  7:   */ import net.minecraft.world.GameRules;
/*  8:   */ import net.minecraft.world.WorldServer;
/*  9:   */ 
/* 10:   */ public class CommandGameRule
/* 11:   */   extends CommandBase
/* 12:   */ {
/* 13:   */   private static final String __OBFID = "CL_00000475";
/* 14:   */   
/* 15:   */   public String getCommandName()
/* 16:   */   {
/* 17:14 */     return "gamerule";
/* 18:   */   }
/* 19:   */   
/* 20:   */   public int getRequiredPermissionLevel()
/* 21:   */   {
/* 22:22 */     return 2;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public String getCommandUsage(ICommandSender par1ICommandSender)
/* 26:   */   {
/* 27:27 */     return "commands.gamerule.usage";
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/* 31:   */   {
/* 32:34 */     if (par2ArrayOfStr.length == 2)
/* 33:   */     {
/* 34:36 */       String var6 = par2ArrayOfStr[0];
/* 35:37 */       String var7 = par2ArrayOfStr[1];
/* 36:38 */       GameRules var8 = getGameRules();
/* 37:40 */       if (var8.hasRule(var6))
/* 38:   */       {
/* 39:42 */         var8.setOrCreateGameRule(var6, var7);
/* 40:43 */         notifyAdmins(par1ICommandSender, "commands.gamerule.success", new Object[0]);
/* 41:   */       }
/* 42:   */       else
/* 43:   */       {
/* 44:47 */         notifyAdmins(par1ICommandSender, "commands.gamerule.norule", new Object[] { var6 });
/* 45:   */       }
/* 46:   */     }
/* 47:50 */     else if (par2ArrayOfStr.length == 1)
/* 48:   */     {
/* 49:52 */       String var6 = par2ArrayOfStr[0];
/* 50:53 */       GameRules var4 = getGameRules();
/* 51:55 */       if (var4.hasRule(var6))
/* 52:   */       {
/* 53:57 */         String var5 = var4.getGameRuleStringValue(var6);
/* 54:58 */         par1ICommandSender.addChatMessage(new ChatComponentText(var6).appendText(" = ").appendText(var5));
/* 55:   */       }
/* 56:   */       else
/* 57:   */       {
/* 58:62 */         notifyAdmins(par1ICommandSender, "commands.gamerule.norule", new Object[] { var6 });
/* 59:   */       }
/* 60:   */     }
/* 61:65 */     else if (par2ArrayOfStr.length == 0)
/* 62:   */     {
/* 63:67 */       GameRules var3 = getGameRules();
/* 64:68 */       par1ICommandSender.addChatMessage(new ChatComponentText(joinNiceString(var3.getRules())));
/* 65:   */     }
/* 66:   */     else
/* 67:   */     {
/* 68:72 */       throw new WrongUsageException("commands.gamerule.usage", new Object[0]);
/* 69:   */     }
/* 70:   */   }
/* 71:   */   
/* 72:   */   public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/* 73:   */   {
/* 74:81 */     return par2ArrayOfStr.length == 2 ? getListOfStringsMatchingLastWord(par2ArrayOfStr, new String[] { "true", "false" }) : par2ArrayOfStr.length == 1 ? getListOfStringsMatchingLastWord(par2ArrayOfStr, getGameRules().getRules()) : null;
/* 75:   */   }
/* 76:   */   
/* 77:   */   private GameRules getGameRules()
/* 78:   */   {
/* 79:89 */     return MinecraftServer.getServer().worldServerForDimension(0).getGameRules();
/* 80:   */   }
/* 81:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.command.CommandGameRule
 * JD-Core Version:    0.7.0.1
 */