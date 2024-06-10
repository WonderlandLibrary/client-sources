/*  1:   */ package me.connorm.Nodus.event.chat;
/*  2:   */ 
/*  3:   */ import me.connorm.lib.event.Event;
/*  4:   */ 
/*  5:   */ public class EventChat
/*  6:   */   implements Event
/*  7:   */ {
/*  8:   */   private String chatMessage;
/*  9:   */   
/* 10:   */   public EventChat(String chatMessage)
/* 11:   */   {
/* 12:11 */     this.chatMessage = chatMessage;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public String getChatMessage()
/* 16:   */   {
/* 17:16 */     return this.chatMessage;
/* 18:   */   }
/* 19:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.event.chat.EventChat
 * JD-Core Version:    0.7.0.1
 */