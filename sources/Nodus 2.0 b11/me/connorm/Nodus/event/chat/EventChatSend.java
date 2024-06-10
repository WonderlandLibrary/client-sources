/*  1:   */ package me.connorm.Nodus.event.chat;
/*  2:   */ 
/*  3:   */ import me.connorm.lib.event.Cancellable;
/*  4:   */ 
/*  5:   */ public class EventChatSend
/*  6:   */   extends EventChat
/*  7:   */   implements Cancellable
/*  8:   */ {
/*  9:   */   private boolean isCancelled;
/* 10:   */   
/* 11:   */   public EventChatSend(String chatMessage)
/* 12:   */   {
/* 13:11 */     super(chatMessage);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public boolean isCancelled()
/* 17:   */   {
/* 18:17 */     return this.isCancelled;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void setCancelled(boolean newState)
/* 22:   */   {
/* 23:23 */     this.isCancelled = newState;
/* 24:   */   }
/* 25:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.event.chat.EventChatSend
 * JD-Core Version:    0.7.0.1
 */