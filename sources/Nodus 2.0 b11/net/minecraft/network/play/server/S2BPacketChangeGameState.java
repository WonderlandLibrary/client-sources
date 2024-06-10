/*  1:   */ package net.minecraft.network.play.server;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import net.minecraft.network.INetHandler;
/*  5:   */ import net.minecraft.network.Packet;
/*  6:   */ import net.minecraft.network.PacketBuffer;
/*  7:   */ import net.minecraft.network.play.INetHandlerPlayClient;
/*  8:   */ 
/*  9:   */ public class S2BPacketChangeGameState
/* 10:   */   extends Packet
/* 11:   */ {
/* 12:11 */   public static final String[] field_149142_a = { "tile.bed.notValid", 00"gameMode.changed" };
/* 13:   */   private int field_149140_b;
/* 14:   */   private float field_149141_c;
/* 15:   */   private static final String __OBFID = "CL_00001301";
/* 16:   */   
/* 17:   */   public S2BPacketChangeGameState() {}
/* 18:   */   
/* 19:   */   public S2BPacketChangeGameState(int p_i45194_1_, float p_i45194_2_)
/* 20:   */   {
/* 21:20 */     this.field_149140_b = p_i45194_1_;
/* 22:21 */     this.field_149141_c = p_i45194_2_;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void readPacketData(PacketBuffer p_148837_1_)
/* 26:   */     throws IOException
/* 27:   */   {
/* 28:29 */     this.field_149140_b = p_148837_1_.readUnsignedByte();
/* 29:30 */     this.field_149141_c = p_148837_1_.readFloat();
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void writePacketData(PacketBuffer p_148840_1_)
/* 33:   */     throws IOException
/* 34:   */   {
/* 35:38 */     p_148840_1_.writeByte(this.field_149140_b);
/* 36:39 */     p_148840_1_.writeFloat(this.field_149141_c);
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void processPacket(INetHandlerPlayClient p_149139_1_)
/* 40:   */   {
/* 41:44 */     p_149139_1_.handleChangeGameState(this);
/* 42:   */   }
/* 43:   */   
/* 44:   */   public int func_149138_c()
/* 45:   */   {
/* 46:49 */     return this.field_149140_b;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public float func_149137_d()
/* 50:   */   {
/* 51:54 */     return this.field_149141_c;
/* 52:   */   }
/* 53:   */   
/* 54:   */   public void processPacket(INetHandler p_148833_1_)
/* 55:   */   {
/* 56:59 */     processPacket((INetHandlerPlayClient)p_148833_1_);
/* 57:   */   }
/* 58:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.server.S2BPacketChangeGameState
 * JD-Core Version:    0.7.0.1
 */