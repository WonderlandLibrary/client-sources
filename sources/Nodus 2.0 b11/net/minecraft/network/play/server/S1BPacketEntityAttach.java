/*  1:   */ package net.minecraft.network.play.server;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import net.minecraft.entity.Entity;
/*  5:   */ import net.minecraft.network.INetHandler;
/*  6:   */ import net.minecraft.network.Packet;
/*  7:   */ import net.minecraft.network.PacketBuffer;
/*  8:   */ import net.minecraft.network.play.INetHandlerPlayClient;
/*  9:   */ 
/* 10:   */ public class S1BPacketEntityAttach
/* 11:   */   extends Packet
/* 12:   */ {
/* 13:   */   private int field_149408_a;
/* 14:   */   private int field_149406_b;
/* 15:   */   private int field_149407_c;
/* 16:   */   private static final String __OBFID = "CL_00001327";
/* 17:   */   
/* 18:   */   public S1BPacketEntityAttach() {}
/* 19:   */   
/* 20:   */   public S1BPacketEntityAttach(int p_i45218_1_, Entity p_i45218_2_, Entity p_i45218_3_)
/* 21:   */   {
/* 22:21 */     this.field_149408_a = p_i45218_1_;
/* 23:22 */     this.field_149406_b = p_i45218_2_.getEntityId();
/* 24:23 */     this.field_149407_c = (p_i45218_3_ != null ? p_i45218_3_.getEntityId() : -1);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void readPacketData(PacketBuffer p_148837_1_)
/* 28:   */     throws IOException
/* 29:   */   {
/* 30:31 */     this.field_149406_b = p_148837_1_.readInt();
/* 31:32 */     this.field_149407_c = p_148837_1_.readInt();
/* 32:33 */     this.field_149408_a = p_148837_1_.readUnsignedByte();
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void writePacketData(PacketBuffer p_148840_1_)
/* 36:   */     throws IOException
/* 37:   */   {
/* 38:41 */     p_148840_1_.writeInt(this.field_149406_b);
/* 39:42 */     p_148840_1_.writeInt(this.field_149407_c);
/* 40:43 */     p_148840_1_.writeByte(this.field_149408_a);
/* 41:   */   }
/* 42:   */   
/* 43:   */   public void processPacket(INetHandlerPlayClient p_149405_1_)
/* 44:   */   {
/* 45:48 */     p_149405_1_.handleEntityAttach(this);
/* 46:   */   }
/* 47:   */   
/* 48:   */   public int func_149404_c()
/* 49:   */   {
/* 50:53 */     return this.field_149408_a;
/* 51:   */   }
/* 52:   */   
/* 53:   */   public int func_149403_d()
/* 54:   */   {
/* 55:58 */     return this.field_149406_b;
/* 56:   */   }
/* 57:   */   
/* 58:   */   public int func_149402_e()
/* 59:   */   {
/* 60:63 */     return this.field_149407_c;
/* 61:   */   }
/* 62:   */   
/* 63:   */   public void processPacket(INetHandler p_148833_1_)
/* 64:   */   {
/* 65:68 */     processPacket((INetHandlerPlayClient)p_148833_1_);
/* 66:   */   }
/* 67:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.server.S1BPacketEntityAttach
 * JD-Core Version:    0.7.0.1
 */