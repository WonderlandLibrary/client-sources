/*  1:   */ package me.connorm.irc;
/*  2:   */ 
/*  3:   */ import me.connorm.Nodus.event.chat.EventChatSend;
/*  4:   */ import me.connorm.lib.EventManager;
/*  5:   */ import me.connorm.lib.EventTarget;
/*  6:   */ import net.minecraft.client.Minecraft;
/*  7:   */ import net.minecraft.client.entity.EntityClientPlayerMP;
/*  8:   */ import net.minecraft.util.ChatComponentText;
/*  9:   */ import net.minecraft.util.EnumChatFormatting;
/* 10:   */ 
/* 11:   */ public class NodusIRCHandler
/* 12:   */ {
/* 13:12 */   public char ircPrefix = '@';
/* 14:   */   
/* 15:   */   public NodusIRCHandler()
/* 16:   */   {
/* 17:16 */     EventManager.register(this);
/* 18:   */   }
/* 19:   */   
/* 20:   */   @EventTarget
/* 21:   */   public void sendChatMessage(EventChatSend theEvent)
/* 22:   */   {
/* 23:22 */     String chatMessage = theEvent.getChatMessage();
/* 24:23 */     if (chatMessage.startsWith(Character.toString(this.ircPrefix)))
/* 25:   */     {
/* 26:25 */       String rawMessage = chatMessage.replaceFirst("@", "");
/* 27:26 */       NodusIRCBot.ircBot.changeNick(NodusIRCBot.ircUsername);
/* 28:27 */       NodusIRCBot.ircBot.sendMessage(NodusIRCBot.ircChannel, rawMessage);
/* 29:28 */       String messageSender = NodusIRCBot.ircUsername;
/* 30:29 */       boolean isConnor = (messageSender == "WHConnor") || (messageSender == "carloshombre");
/* 31:30 */       String senderName = isConnor ? "[" + EnumChatFormatting.GREEN + "Nodus" + EnumChatFormatting.RED + "Dev" + EnumChatFormatting.RESET + "] ConnorM" : messageSender;
/* 32:31 */       Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("[" + EnumChatFormatting.GREEN + "IRC" + EnumChatFormatting.RESET + "] " + senderName + "> " + rawMessage));
/* 33:32 */       theEvent.setCancelled(true);
/* 34:   */     }
/* 35:   */   }
/* 36:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.irc.NodusIRCHandler
 * JD-Core Version:    0.7.0.1
 */