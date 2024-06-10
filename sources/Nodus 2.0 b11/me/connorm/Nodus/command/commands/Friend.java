/*  1:   */ package me.connorm.Nodus.command.commands;
/*  2:   */ 
/*  3:   */ import me.connorm.Nodus.Nodus;
/*  4:   */ import me.connorm.Nodus.command.NodusCommand;
/*  5:   */ import me.connorm.Nodus.file.NodusFileManager;
/*  6:   */ import me.connorm.Nodus.relations.NodusRelationsManager;
/*  7:   */ import me.connorm.Nodus.relations.friend.NodusFriendManager;
/*  8:   */ 
/*  9:   */ public class Friend
/* 10:   */   extends NodusCommand
/* 11:   */ {
/* 12:   */   public Friend()
/* 13:   */   {
/* 14:10 */     super("friend");
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void runCommand(String commandRun, String[] commandArgs)
/* 18:   */   {
/* 19:   */     try
/* 20:   */     {
/* 21:18 */       if (commandArgs[0].equalsIgnoreCase("add"))
/* 22:   */       {
/* 23:20 */         String friendName = commandArgs[1];
/* 24:21 */         if (!Nodus.theNodus.relationsManager.friendManager.isFriend(friendName))
/* 25:   */         {
/* 26:23 */           Nodus.theNodus.relationsManager.friendManager.addFriend(friendName);
/* 27:24 */           Nodus.theNodus.addChatMessage(friendName + " is now your friend.");
/* 28:25 */           Nodus.theNodus.fileManager.saveFriends();
/* 29:   */         }
/* 30:   */         else
/* 31:   */         {
/* 32:28 */           Nodus.theNodus.addChatMessage(friendName + " is already your friend.");
/* 33:   */         }
/* 34:   */       }
/* 35:31 */       if (commandArgs[0].equalsIgnoreCase("del"))
/* 36:   */       {
/* 37:33 */         String friendName = commandArgs[1];
/* 38:34 */         if (!Nodus.theNodus.relationsManager.friendManager.isFriend(friendName))
/* 39:   */         {
/* 40:36 */           Nodus.theNodus.addChatMessage(friendName + " is not your friend.");
/* 41:   */         }
/* 42:   */         else
/* 43:   */         {
/* 44:39 */           Nodus.theNodus.relationsManager.friendManager.removeFriend(friendName);
/* 45:40 */           Nodus.theNodus.addChatMessage(friendName + " is no longer your friend.");
/* 46:41 */           Nodus.theNodus.fileManager.saveFriends();
/* 47:   */         }
/* 48:   */       }
/* 49:   */     }
/* 50:   */     catch (Exception commandSyntaxException)
/* 51:   */     {
/* 52:46 */       commandSyntaxException.printStackTrace();
/* 53:47 */       Nodus.theNodus.addChatMessage("Command Usage: " + getSyntax());
/* 54:   */     }
/* 55:   */   }
/* 56:   */   
/* 57:   */   public String getDescription()
/* 58:   */   {
/* 59:54 */     return "Adds friends.";
/* 60:   */   }
/* 61:   */   
/* 62:   */   public String getSyntax()
/* 63:   */   {
/* 64:60 */     return "add/del <name>";
/* 65:   */   }
/* 66:   */   
/* 67:   */   public String getConsoleDisplay()
/* 68:   */   {
/* 69:66 */     return "friend";
/* 70:   */   }
/* 71:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.command.commands.Friend
 * JD-Core Version:    0.7.0.1
 */