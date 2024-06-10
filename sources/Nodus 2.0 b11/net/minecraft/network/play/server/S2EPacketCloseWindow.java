/*  1:   */ package net.minecraft.network.play.server;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import net.minecraft.network.INetHandler;
/*  5:   */ import net.minecraft.network.Packet;
/*  6:   */ import net.minecraft.network.PacketBuffer;
/*  7:   */ import net.minecraft.network.play.INetHandlerPlayClient;
/*  8:   */ 
/*  9:   */ public class S2EPacketCloseWindow
/* 10:   */   extends Packet
/* 11:   */ {
/* 12:   */   private int field_148896_a;
/* 13:   */   private static final String __OBFID = "CL_00001292";
/* 14:   */   
/* 15:   */   public S2EPacketCloseWindow() {}
/* 16:   */   
/* 17:   */   public S2EPacketCloseWindow(int p_i45183_1_)
/* 18:   */   {
/* 19:18 */     this.field_148896_a = p_i45183_1_;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void processPacket(INetHandlerPlayClient p_148895_1_)
/* 23:   */   {
/* 24:23 */     p_148895_1_.handleCloseWindow(this);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void readPacketData(PacketBuffer p_148837_1_)
/* 28:   */     throws IOException
/* 29:   */   {
/* 30:31 */     this.field_148896_a = p_148837_1_.readUnsignedByte();
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void writePacketData(PacketBuffer p_148840_1_)
/* 34:   */     throws IOException
/* 35:   */   {
/* 36:39 */     p_148840_1_.writeByte(this.field_148896_a);
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void processPacket(INetHandler p_148833_1_)
/* 40:   */   {
/* 41:44 */     processPacket((INetHandlerPlayClient)p_148833_1_);
/* 42:   */   }
/* 43:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.server.S2EPacketCloseWindow
 * JD-Core Version:    0.7.0.1
 */