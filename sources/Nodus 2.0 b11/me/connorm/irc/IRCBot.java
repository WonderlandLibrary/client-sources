/*  1:   */ package me.connorm.irc;
/*  2:   */ 
/*  3:   */ import me.connorm.irc.lib.PircBot;
/*  4:   */ import net.minecraft.client.Minecraft;
/*  5:   */ import net.minecraft.client.entity.EntityClientPlayerMP;
/*  6:   */ import net.minecraft.util.ChatComponentText;
/*  7:   */ import net.minecraft.util.EnumChatFormatting;
/*  8:   */ 
/*  9:   */ public class IRCBot
/* 10:   */   extends PircBot
/* 11:   */ {
/* 12:   */   private String botUsername;
/* 13:   */   
/* 14:   */   public IRCBot(String botUsername)
/* 15:   */   {
/* 16:14 */     this.botUsername = botUsername;
/* 17:15 */     setName(botUsername);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void onMessage(String channel, String sender, String login, String hostname, String message)
/* 21:   */   {
/* 22:20 */     boolean isConnor = (sender == "WHConnor") || (sender == "carloshombre");
/* 23:21 */     String senderName = isConnor ? "[" + EnumChatFormatting.GREEN + "Nodus" + EnumChatFormatting.RED + "Dev" + EnumChatFormatting.RESET + "] ConnorM" : sender;
/* 24:22 */     Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("[" + EnumChatFormatting.GREEN + "IRC" + EnumChatFormatting.RESET + "] " + senderName + ": " + message));
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void setUsername(String newUsername)
/* 28:   */   {
/* 29:27 */     this.botUsername = newUsername;
/* 30:28 */     setName(newUsername);
/* 31:   */   }
/* 32:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.irc.IRCBot
 * JD-Core Version:    0.7.0.1
 */