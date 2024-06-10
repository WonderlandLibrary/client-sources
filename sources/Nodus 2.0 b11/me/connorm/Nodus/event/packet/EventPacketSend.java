/*  1:   */ package me.connorm.Nodus.event.packet;
/*  2:   */ 
/*  3:   */ import me.connorm.lib.event.Cancellable;
/*  4:   */ import net.minecraft.network.Packet;
/*  5:   */ 
/*  6:   */ public class EventPacketSend
/*  7:   */   extends EventPacket
/*  8:   */   implements Cancellable
/*  9:   */ {
/* 10:   */   private boolean isCancelled;
/* 11:   */   
/* 12:   */   public EventPacketSend(Packet thePacket)
/* 13:   */   {
/* 14:12 */     super(thePacket);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public boolean isCancelled()
/* 18:   */   {
/* 19:18 */     return this.isCancelled;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void setCancelled(boolean newState)
/* 23:   */   {
/* 24:24 */     this.isCancelled = newState;
/* 25:   */   }
/* 26:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.event.packet.EventPacketSend
 * JD-Core Version:    0.7.0.1
 */