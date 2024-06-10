/*  1:   */ package net.minecraft.command.server;
/*  2:   */ 
/*  3:   */ import com.google.gson.JsonParseException;
/*  4:   */ import java.util.List;
/*  5:   */ import net.minecraft.command.CommandBase;
/*  6:   */ import net.minecraft.command.ICommandSender;
/*  7:   */ import net.minecraft.command.SyntaxErrorException;
/*  8:   */ import net.minecraft.command.WrongUsageException;
/*  9:   */ import net.minecraft.entity.player.EntityPlayerMP;
/* 10:   */ import net.minecraft.server.MinecraftServer;
/* 11:   */ import net.minecraft.util.IChatComponent;
/* 12:   */ import net.minecraft.util.IChatComponent.Serializer;
/* 13:   */ import org.apache.commons.lang3.exception.ExceptionUtils;
/* 14:   */ 
/* 15:   */ public class CommandMessageRaw
/* 16:   */   extends CommandBase
/* 17:   */ {
/* 18:   */   private static final String __OBFID = "CL_00000667";
/* 19:   */   
/* 20:   */   public String getCommandName()
/* 21:   */   {
/* 22:20 */     return "tellraw";
/* 23:   */   }
/* 24:   */   
/* 25:   */   public int getRequiredPermissionLevel()
/* 26:   */   {
/* 27:28 */     return 2;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public String getCommandUsage(ICommandSender par1ICommandSender)
/* 31:   */   {
/* 32:33 */     return "commands.tellraw.usage";
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/* 36:   */   {
/* 37:38 */     if (par2ArrayOfStr.length < 2) {
/* 38:40 */       throw new WrongUsageException("commands.tellraw.usage", new Object[0]);
/* 39:   */     }
/* 40:44 */     EntityPlayerMP var3 = getPlayer(par1ICommandSender, par2ArrayOfStr[0]);
/* 41:45 */     String var4 = func_82360_a(par1ICommandSender, par2ArrayOfStr, 1);
/* 42:   */     try
/* 43:   */     {
/* 44:49 */       IChatComponent var5 = IChatComponent.Serializer.func_150699_a(var4);
/* 45:50 */       var3.addChatMessage(var5);
/* 46:   */     }
/* 47:   */     catch (JsonParseException var6)
/* 48:   */     {
/* 49:54 */       throw new SyntaxErrorException("commands.tellraw.jsonException", new Object[] { ExceptionUtils.getRootCause(var6).getMessage() });
/* 50:   */     }
/* 51:   */   }
/* 52:   */   
/* 53:   */   public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/* 54:   */   {
/* 55:64 */     return par2ArrayOfStr.length == 1 ? getListOfStringsMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getAllUsernames()) : null;
/* 56:   */   }
/* 57:   */   
/* 58:   */   public boolean isUsernameIndex(String[] par1ArrayOfStr, int par2)
/* 59:   */   {
/* 60:72 */     return par2 == 0;
/* 61:   */   }
/* 62:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.command.server.CommandMessageRaw
 * JD-Core Version:    0.7.0.1
 */