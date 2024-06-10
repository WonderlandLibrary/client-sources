/*  1:   */ package net.minecraft.network.handshake.client;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import net.minecraft.network.EnumConnectionState;
/*  5:   */ import net.minecraft.network.INetHandler;
/*  6:   */ import net.minecraft.network.Packet;
/*  7:   */ import net.minecraft.network.PacketBuffer;
/*  8:   */ import net.minecraft.network.handshake.INetHandlerHandshakeServer;
/*  9:   */ 
/* 10:   */ public class C00Handshake
/* 11:   */   extends Packet
/* 12:   */ {
/* 13:   */   private int field_149600_a;
/* 14:   */   private String field_149598_b;
/* 15:   */   private int field_149599_c;
/* 16:   */   private EnumConnectionState field_149597_d;
/* 17:   */   private static final String __OBFID = "CL_00001372";
/* 18:   */   
/* 19:   */   public C00Handshake() {}
/* 20:   */   
/* 21:   */   public C00Handshake(int p_i45266_1_, String p_i45266_2_, int p_i45266_3_, EnumConnectionState p_i45266_4_)
/* 22:   */   {
/* 23:22 */     this.field_149600_a = p_i45266_1_;
/* 24:23 */     this.field_149598_b = p_i45266_2_;
/* 25:24 */     this.field_149599_c = p_i45266_3_;
/* 26:25 */     this.field_149597_d = p_i45266_4_;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void readPacketData(PacketBuffer p_148837_1_)
/* 30:   */     throws IOException
/* 31:   */   {
/* 32:33 */     this.field_149600_a = p_148837_1_.readVarIntFromBuffer();
/* 33:34 */     this.field_149598_b = p_148837_1_.readStringFromBuffer(255);
/* 34:35 */     this.field_149599_c = p_148837_1_.readUnsignedShort();
/* 35:36 */     this.field_149597_d = EnumConnectionState.func_150760_a(p_148837_1_.readVarIntFromBuffer());
/* 36:   */   }
/* 37:   */   
/* 38:   */   public void writePacketData(PacketBuffer p_148840_1_)
/* 39:   */     throws IOException
/* 40:   */   {
/* 41:44 */     p_148840_1_.writeVarIntToBuffer(this.field_149600_a);
/* 42:45 */     p_148840_1_.writeStringToBuffer(this.field_149598_b);
/* 43:46 */     p_148840_1_.writeShort(this.field_149599_c);
/* 44:47 */     p_148840_1_.writeVarIntToBuffer(this.field_149597_d.func_150759_c());
/* 45:   */   }
/* 46:   */   
/* 47:   */   public void processPacket(INetHandlerHandshakeServer p_149596_1_)
/* 48:   */   {
/* 49:52 */     p_149596_1_.processHandshake(this);
/* 50:   */   }
/* 51:   */   
/* 52:   */   public boolean hasPriority()
/* 53:   */   {
/* 54:61 */     return true;
/* 55:   */   }
/* 56:   */   
/* 57:   */   public EnumConnectionState func_149594_c()
/* 58:   */   {
/* 59:66 */     return this.field_149597_d;
/* 60:   */   }
/* 61:   */   
/* 62:   */   public int func_149595_d()
/* 63:   */   {
/* 64:71 */     return this.field_149600_a;
/* 65:   */   }
/* 66:   */   
/* 67:   */   public void processPacket(INetHandler p_148833_1_)
/* 68:   */   {
/* 69:76 */     processPacket((INetHandlerHandshakeServer)p_148833_1_);
/* 70:   */   }
/* 71:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.handshake.client.C00Handshake
 * JD-Core Version:    0.7.0.1
 */