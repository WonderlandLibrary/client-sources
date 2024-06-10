/*  1:   */ package me.connorm.Nodus.event.packet;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import me.connorm.lib.event.Event;
/*  5:   */ import net.minecraft.network.Packet;
/*  6:   */ 
/*  7:   */ public class EventPacket
/*  8:   */   implements Event
/*  9:   */ {
/* 10:   */   private Packet thePacket;
/* 11:12 */   private ArrayList<Packet> thePackets = new ArrayList();
/* 12:   */   
/* 13:   */   public EventPacket(Packet thePacket)
/* 14:   */   {
/* 15:16 */     this.thePacket = thePacket;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public Packet getPacket()
/* 19:   */   {
/* 20:21 */     return this.thePacket;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public ArrayList<Packet> getPackets()
/* 24:   */   {
/* 25:26 */     return this.thePackets;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void addPacket(Packet packetToAdd)
/* 29:   */   {
/* 30:31 */     this.thePackets.add(packetToAdd);
/* 31:   */   }
/* 32:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.event.packet.EventPacket
 * JD-Core Version:    0.7.0.1
 */