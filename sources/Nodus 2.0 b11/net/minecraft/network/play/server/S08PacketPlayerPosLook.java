/*  1:   */ package net.minecraft.network.play.server;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import net.minecraft.network.INetHandler;
/*  5:   */ import net.minecraft.network.Packet;
/*  6:   */ import net.minecraft.network.PacketBuffer;
/*  7:   */ import net.minecraft.network.play.INetHandlerPlayClient;
/*  8:   */ 
/*  9:   */ public class S08PacketPlayerPosLook
/* 10:   */   extends Packet
/* 11:   */ {
/* 12:   */   private double field_148940_a;
/* 13:   */   private double field_148938_b;
/* 14:   */   private double field_148939_c;
/* 15:   */   private float field_148936_d;
/* 16:   */   private float field_148937_e;
/* 17:   */   private boolean field_148935_f;
/* 18:   */   private static final String __OBFID = "CL_00001273";
/* 19:   */   
/* 20:   */   public S08PacketPlayerPosLook() {}
/* 21:   */   
/* 22:   */   public S08PacketPlayerPosLook(double p_i45164_1_, double p_i45164_3_, double p_i45164_5_, float p_i45164_7_, float p_i45164_8_, boolean p_i45164_9_)
/* 23:   */   {
/* 24:23 */     this.field_148940_a = p_i45164_1_;
/* 25:24 */     this.field_148938_b = p_i45164_3_;
/* 26:25 */     this.field_148939_c = p_i45164_5_;
/* 27:26 */     this.field_148936_d = p_i45164_7_;
/* 28:27 */     this.field_148937_e = p_i45164_8_;
/* 29:28 */     this.field_148935_f = p_i45164_9_;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void readPacketData(PacketBuffer p_148837_1_)
/* 33:   */     throws IOException
/* 34:   */   {
/* 35:36 */     this.field_148940_a = p_148837_1_.readDouble();
/* 36:37 */     this.field_148938_b = p_148837_1_.readDouble();
/* 37:38 */     this.field_148939_c = p_148837_1_.readDouble();
/* 38:39 */     this.field_148936_d = p_148837_1_.readFloat();
/* 39:40 */     this.field_148937_e = p_148837_1_.readFloat();
/* 40:41 */     this.field_148935_f = p_148837_1_.readBoolean();
/* 41:   */   }
/* 42:   */   
/* 43:   */   public void writePacketData(PacketBuffer p_148840_1_)
/* 44:   */     throws IOException
/* 45:   */   {
/* 46:49 */     p_148840_1_.writeDouble(this.field_148940_a);
/* 47:50 */     p_148840_1_.writeDouble(this.field_148938_b);
/* 48:51 */     p_148840_1_.writeDouble(this.field_148939_c);
/* 49:52 */     p_148840_1_.writeFloat(this.field_148936_d);
/* 50:53 */     p_148840_1_.writeFloat(this.field_148937_e);
/* 51:54 */     p_148840_1_.writeBoolean(this.field_148935_f);
/* 52:   */   }
/* 53:   */   
/* 54:   */   public void processPacket(INetHandlerPlayClient p_148934_1_)
/* 55:   */   {
/* 56:59 */     p_148934_1_.handlePlayerPosLook(this);
/* 57:   */   }
/* 58:   */   
/* 59:   */   public double func_148932_c()
/* 60:   */   {
/* 61:64 */     return this.field_148940_a;
/* 62:   */   }
/* 63:   */   
/* 64:   */   public double func_148928_d()
/* 65:   */   {
/* 66:69 */     return this.field_148938_b;
/* 67:   */   }
/* 68:   */   
/* 69:   */   public double func_148933_e()
/* 70:   */   {
/* 71:74 */     return this.field_148939_c;
/* 72:   */   }
/* 73:   */   
/* 74:   */   public float func_148931_f()
/* 75:   */   {
/* 76:79 */     return this.field_148936_d;
/* 77:   */   }
/* 78:   */   
/* 79:   */   public float func_148930_g()
/* 80:   */   {
/* 81:84 */     return this.field_148937_e;
/* 82:   */   }
/* 83:   */   
/* 84:   */   public boolean func_148929_h()
/* 85:   */   {
/* 86:89 */     return this.field_148935_f;
/* 87:   */   }
/* 88:   */   
/* 89:   */   public void processPacket(INetHandler p_148833_1_)
/* 90:   */   {
/* 91:94 */     processPacket((INetHandlerPlayClient)p_148833_1_);
/* 92:   */   }
/* 93:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.server.S08PacketPlayerPosLook
 * JD-Core Version:    0.7.0.1
 */