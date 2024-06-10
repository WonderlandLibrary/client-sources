/*  1:   */ package net.minecraft.network.play.server;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import net.minecraft.entity.Entity;
/*  5:   */ import net.minecraft.network.INetHandler;
/*  6:   */ import net.minecraft.network.Packet;
/*  7:   */ import net.minecraft.network.PacketBuffer;
/*  8:   */ import net.minecraft.network.play.INetHandlerPlayClient;
/*  9:   */ 
/* 10:   */ public class S0BPacketAnimation
/* 11:   */   extends Packet
/* 12:   */ {
/* 13:   */   private int field_148981_a;
/* 14:   */   private int field_148980_b;
/* 15:   */   private static final String __OBFID = "CL_00001282";
/* 16:   */   
/* 17:   */   public S0BPacketAnimation() {}
/* 18:   */   
/* 19:   */   public S0BPacketAnimation(Entity p_i45172_1_, int p_i45172_2_)
/* 20:   */   {
/* 21:20 */     this.field_148981_a = p_i45172_1_.getEntityId();
/* 22:21 */     this.field_148980_b = p_i45172_2_;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void readPacketData(PacketBuffer p_148837_1_)
/* 26:   */     throws IOException
/* 27:   */   {
/* 28:29 */     this.field_148981_a = p_148837_1_.readVarIntFromBuffer();
/* 29:30 */     this.field_148980_b = p_148837_1_.readUnsignedByte();
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void writePacketData(PacketBuffer p_148840_1_)
/* 33:   */     throws IOException
/* 34:   */   {
/* 35:38 */     p_148840_1_.writeVarIntToBuffer(this.field_148981_a);
/* 36:39 */     p_148840_1_.writeByte(this.field_148980_b);
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void processPacket(INetHandlerPlayClient p_148979_1_)
/* 40:   */   {
/* 41:44 */     p_148979_1_.handleAnimation(this);
/* 42:   */   }
/* 43:   */   
/* 44:   */   public String serialize()
/* 45:   */   {
/* 46:52 */     return String.format("id=%d, type=%d", new Object[] { Integer.valueOf(this.field_148981_a), Integer.valueOf(this.field_148980_b) });
/* 47:   */   }
/* 48:   */   
/* 49:   */   public int func_148978_c()
/* 50:   */   {
/* 51:57 */     return this.field_148981_a;
/* 52:   */   }
/* 53:   */   
/* 54:   */   public int func_148977_d()
/* 55:   */   {
/* 56:62 */     return this.field_148980_b;
/* 57:   */   }
/* 58:   */   
/* 59:   */   public void processPacket(INetHandler p_148833_1_)
/* 60:   */   {
/* 61:67 */     processPacket((INetHandlerPlayClient)p_148833_1_);
/* 62:   */   }
/* 63:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.server.S0BPacketAnimation
 * JD-Core Version:    0.7.0.1
 */