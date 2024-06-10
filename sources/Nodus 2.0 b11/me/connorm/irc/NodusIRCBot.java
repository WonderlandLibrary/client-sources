/*  1:   */ package me.connorm.irc;
/*  2:   */ 
/*  3:   */ import me.connorm.Nodus.Nodus;
/*  4:   */ import me.connorm.irc.lib.NickAlreadyInUseException;
/*  5:   */ import me.connorm.irc.lib.PircBot;
/*  6:   */ import net.minecraft.client.Minecraft;
/*  7:   */ import net.minecraft.util.Session;
/*  8:   */ 
/*  9:   */ public class NodusIRCBot
/* 10:   */   extends PircBot
/* 11:   */ {
/* 12: 9 */   public static String ircHost = "irc.freenode.net";
/* 13:10 */   public static String ircChannel = "#nodus2.0";
/* 14:12 */   public static String ircUsername = Nodus.theNodus.getMinecraft().session.getUsername();
/* 15:   */   public static IRCBot ircBot;
/* 16:   */   
/* 17:   */   public NodusIRCBot(String ircUsername)
/* 18:   */   {
/* 19:17 */     ircUsername = ircUsername;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public static void runBot()
/* 23:   */   {
/* 24:   */     try
/* 25:   */     {
/* 26:24 */       ircBot = new IRCBot(ircUsername);
/* 27:25 */       ircBot.setVerbose(true);
/* 28:26 */       ircBot.connect(ircHost);
/* 29:27 */       ircBot.joinChannel(ircChannel);
/* 30:   */     }
/* 31:   */     catch (NickAlreadyInUseException nicknameException)
/* 32:   */     {
/* 33:30 */       ircUsername += "_";
/* 34:   */     }
/* 35:   */     catch (Exception ircBotException)
/* 36:   */     {
/* 37:33 */       ircBotException.printStackTrace();
/* 38:   */     }
/* 39:   */   }
/* 40:   */   
/* 41:   */   public static String getUsername()
/* 42:   */   {
/* 43:39 */     return ircUsername;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public static void setUsername(String newUsername)
/* 47:   */   {
/* 48:44 */     ircUsername = newUsername;
/* 49:   */   }
/* 50:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.irc.NodusIRCBot
 * JD-Core Version:    0.7.0.1
 */