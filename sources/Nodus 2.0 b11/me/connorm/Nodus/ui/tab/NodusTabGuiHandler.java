/*  1:   */ package me.connorm.Nodus.ui.tab;
/*  2:   */ 
/*  3:   */ import me.connorm.Nodus.event.other.EventKeyPress;
/*  4:   */ import me.connorm.Nodus.ui.UIRenderer;
/*  5:   */ import me.connorm.lib.EventManager;
/*  6:   */ import me.connorm.lib.EventTarget;
/*  7:   */ 
/*  8:   */ public class NodusTabGuiHandler
/*  9:   */ {
/* 10:   */   public NodusTabGuiHandler()
/* 11:   */   {
/* 12:14 */     EventManager.register(this);
/* 13:   */   }
/* 14:   */   
/* 15:   */   @EventTarget
/* 16:   */   public void handleMenuActions(EventKeyPress theEvent)
/* 17:   */   {
/* 18:20 */     int eventKey = theEvent.getKeyCode();
/* 19:21 */     if (eventKey == 200) {
/* 20:23 */       UIRenderer.clientHacks.handler.gui.parseKeyUp();
/* 21:   */     }
/* 22:25 */     if (eventKey == 208) {
/* 23:27 */       UIRenderer.clientHacks.handler.gui.parseKeyDown();
/* 24:   */     }
/* 25:29 */     if (eventKey == 203) {
/* 26:31 */       UIRenderer.clientHacks.handler.gui.parseKeyLeft();
/* 27:   */     }
/* 28:33 */     if (eventKey == 205) {
/* 29:35 */       UIRenderer.clientHacks.handler.gui.parseKeyRight();
/* 30:   */     }
/* 31:37 */     if (eventKey == 28) {
/* 32:39 */       UIRenderer.clientHacks.handler.gui.parseKeyToggle();
/* 33:   */     }
/* 34:   */   }
/* 35:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.ui.tab.NodusTabGuiHandler
 * JD-Core Version:    0.7.0.1
 */