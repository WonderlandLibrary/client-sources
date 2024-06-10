/*  1:   */ package net.minecraft.network.play.server;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import net.minecraft.network.INetHandler;
/*  5:   */ import net.minecraft.network.Packet;
/*  6:   */ import net.minecraft.network.PacketBuffer;
/*  7:   */ import net.minecraft.network.play.INetHandlerPlayClient;
/*  8:   */ import net.minecraft.potion.PotionEffect;
/*  9:   */ 
/* 10:   */ public class S1DPacketEntityEffect
/* 11:   */   extends Packet
/* 12:   */ {
/* 13:   */   private int field_149434_a;
/* 14:   */   private byte field_149432_b;
/* 15:   */   private byte field_149433_c;
/* 16:   */   private short field_149431_d;
/* 17:   */   private static final String __OBFID = "CL_00001343";
/* 18:   */   
/* 19:   */   public S1DPacketEntityEffect() {}
/* 20:   */   
/* 21:   */   public S1DPacketEntityEffect(int p_i45237_1_, PotionEffect p_i45237_2_)
/* 22:   */   {
/* 23:22 */     this.field_149434_a = p_i45237_1_;
/* 24:23 */     this.field_149432_b = ((byte)(p_i45237_2_.getPotionID() & 0xFF));
/* 25:24 */     this.field_149433_c = ((byte)(p_i45237_2_.getAmplifier() & 0xFF));
/* 26:26 */     if (p_i45237_2_.getDuration() > 32767) {
/* 27:28 */       this.field_149431_d = 32767;
/* 28:   */     } else {
/* 29:32 */       this.field_149431_d = ((short)p_i45237_2_.getDuration());
/* 30:   */     }
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void readPacketData(PacketBuffer p_148837_1_)
/* 34:   */     throws IOException
/* 35:   */   {
/* 36:41 */     this.field_149434_a = p_148837_1_.readInt();
/* 37:42 */     this.field_149432_b = p_148837_1_.readByte();
/* 38:43 */     this.field_149433_c = p_148837_1_.readByte();
/* 39:44 */     this.field_149431_d = p_148837_1_.readShort();
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void writePacketData(PacketBuffer p_148840_1_)
/* 43:   */     throws IOException
/* 44:   */   {
/* 45:52 */     p_148840_1_.writeInt(this.field_149434_a);
/* 46:53 */     p_148840_1_.writeByte(this.field_149432_b);
/* 47:54 */     p_148840_1_.writeByte(this.field_149433_c);
/* 48:55 */     p_148840_1_.writeShort(this.field_149431_d);
/* 49:   */   }
/* 50:   */   
/* 51:   */   public boolean func_149429_c()
/* 52:   */   {
/* 53:60 */     return this.field_149431_d == 32767;
/* 54:   */   }
/* 55:   */   
/* 56:   */   public void processPacket(INetHandlerPlayClient p_149430_1_)
/* 57:   */   {
/* 58:65 */     p_149430_1_.handleEntityEffect(this);
/* 59:   */   }
/* 60:   */   
/* 61:   */   public int func_149426_d()
/* 62:   */   {
/* 63:70 */     return this.field_149434_a;
/* 64:   */   }
/* 65:   */   
/* 66:   */   public byte func_149427_e()
/* 67:   */   {
/* 68:75 */     return this.field_149432_b;
/* 69:   */   }
/* 70:   */   
/* 71:   */   public byte func_149428_f()
/* 72:   */   {
/* 73:80 */     return this.field_149433_c;
/* 74:   */   }
/* 75:   */   
/* 76:   */   public short func_149425_g()
/* 77:   */   {
/* 78:85 */     return this.field_149431_d;
/* 79:   */   }
/* 80:   */   
/* 81:   */   public void processPacket(INetHandler p_148833_1_)
/* 82:   */   {
/* 83:90 */     processPacket((INetHandlerPlayClient)p_148833_1_);
/* 84:   */   }
/* 85:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.server.S1DPacketEntityEffect
 * JD-Core Version:    0.7.0.1
 */