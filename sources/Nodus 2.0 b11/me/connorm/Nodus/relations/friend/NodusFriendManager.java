/*  1:   */ package me.connorm.Nodus.relations.friend;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.Iterator;
/*  5:   */ 
/*  6:   */ public class NodusFriendManager
/*  7:   */ {
/*  8:10 */   private ArrayList<NodusFriend> theFriends = new ArrayList();
/*  9:   */   
/* 10:   */   public void addFriend(String friendName)
/* 11:   */   {
/* 12:14 */     this.theFriends.add(new NodusFriend(friendName));
/* 13:   */   }
/* 14:   */   
/* 15:   */   public void removeFriend(String friendName)
/* 16:   */   {
/* 17:19 */     Iterator friendIterator = this.theFriends.iterator();
/* 18:22 */     while (friendIterator.hasNext())
/* 19:   */     {
/* 20:27 */       NodusFriend friend = (NodusFriend)friendIterator.next();
/* 21:29 */       if (friend.getName().equalsIgnoreCase(friendName)) {
/* 22:33 */         this.theFriends.remove(friend);
/* 23:   */       }
/* 24:   */     }
/* 25:   */   }
/* 26:   */   
/* 27:   */   public boolean isFriend(String friendName)
/* 28:   */   {
/* 29:40 */     boolean isFriend = false;
/* 30:41 */     Iterator friendIterator = this.theFriends.iterator();
/* 31:44 */     while (friendIterator.hasNext())
/* 32:   */     {
/* 33:49 */       NodusFriend friend = (NodusFriend)friendIterator.next();
/* 34:51 */       if (friend.getName().equalsIgnoreCase(friendName)) {
/* 35:55 */         isFriend = true;
/* 36:   */       }
/* 37:   */     }
/* 38:58 */     return isFriend;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public NodusFriend[] getFriends()
/* 42:   */   {
/* 43:63 */     return (NodusFriend[])this.theFriends.toArray(new NodusFriend[this.theFriends.size()]);
/* 44:   */   }
/* 45:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.relations.friend.NodusFriendManager
 * JD-Core Version:    0.7.0.1
 */