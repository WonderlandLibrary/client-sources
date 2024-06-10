/*  1:   */ package net.minecraft.command.server;
/*  2:   */ 
/*  3:   */ import java.util.Arrays;
/*  4:   */ import java.util.List;
/*  5:   */ import net.minecraft.command.CommandBase;
/*  6:   */ import net.minecraft.command.ICommandSender;
/*  7:   */ import net.minecraft.command.PlayerNotFoundException;
/*  8:   */ import net.minecraft.command.WrongUsageException;
/*  9:   */ import net.minecraft.entity.player.EntityPlayer;
/* 10:   */ import net.minecraft.entity.player.EntityPlayerMP;
/* 11:   */ import net.minecraft.server.MinecraftServer;
/* 12:   */ import net.minecraft.util.ChatComponentTranslation;
/* 13:   */ import net.minecraft.util.ChatStyle;
/* 14:   */ import net.minecraft.util.EnumChatFormatting;
/* 15:   */ import net.minecraft.util.IChatComponent;
/* 16:   */ 
/* 17:   */ public class CommandMessage
/* 18:   */   extends CommandBase
/* 19:   */ {
/* 20:   */   private static final String __OBFID = "CL_00000641";
/* 21:   */   
/* 22:   */   public List getCommandAliases()
/* 23:   */   {
/* 24:22 */     return Arrays.asList(new String[] { "w", "msg" });
/* 25:   */   }
/* 26:   */   
/* 27:   */   public String getCommandName()
/* 28:   */   {
/* 29:27 */     return "tell";
/* 30:   */   }
/* 31:   */   
/* 32:   */   public int getRequiredPermissionLevel()
/* 33:   */   {
/* 34:35 */     return 0;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public String getCommandUsage(ICommandSender par1ICommandSender)
/* 38:   */   {
/* 39:40 */     return "commands.message.usage";
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/* 43:   */   {
/* 44:45 */     if (par2ArrayOfStr.length < 2) {
/* 45:47 */       throw new WrongUsageException("commands.message.usage", new Object[0]);
/* 46:   */     }
/* 47:51 */     EntityPlayerMP var3 = getPlayer(par1ICommandSender, par2ArrayOfStr[0]);
/* 48:53 */     if (var3 == null) {
/* 49:55 */       throw new PlayerNotFoundException();
/* 50:   */     }
/* 51:57 */     if (var3 == par1ICommandSender) {
/* 52:59 */       throw new PlayerNotFoundException("commands.message.sameTarget", new Object[0]);
/* 53:   */     }
/* 54:63 */     IChatComponent var4 = func_147176_a(par1ICommandSender, par2ArrayOfStr, 1, !(par1ICommandSender instanceof EntityPlayer));
/* 55:64 */     ChatComponentTranslation var5 = new ChatComponentTranslation("commands.message.display.incoming", new Object[] { par1ICommandSender.func_145748_c_(), var4.createCopy() });
/* 56:65 */     ChatComponentTranslation var6 = new ChatComponentTranslation("commands.message.display.outgoing", new Object[] { var3.func_145748_c_(), var4.createCopy() });
/* 57:66 */     var5.getChatStyle().setColor(EnumChatFormatting.GRAY).setItalic(Boolean.valueOf(true));
/* 58:67 */     var6.getChatStyle().setColor(EnumChatFormatting.GRAY).setItalic(Boolean.valueOf(true));
/* 59:68 */     var3.addChatMessage(var5);
/* 60:69 */     par1ICommandSender.addChatMessage(var6);
/* 61:   */   }
/* 62:   */   
/* 63:   */   public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/* 64:   */   {
/* 65:79 */     return getListOfStringsMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getAllUsernames());
/* 66:   */   }
/* 67:   */   
/* 68:   */   public boolean isUsernameIndex(String[] par1ArrayOfStr, int par2)
/* 69:   */   {
/* 70:87 */     return par2 == 0;
/* 71:   */   }
/* 72:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.command.server.CommandMessage
 * JD-Core Version:    0.7.0.1
 */