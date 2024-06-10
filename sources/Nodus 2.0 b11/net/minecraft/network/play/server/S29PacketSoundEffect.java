/*   1:    */ package net.minecraft.network.play.server;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import net.minecraft.network.INetHandler;
/*   5:    */ import net.minecraft.network.Packet;
/*   6:    */ import net.minecraft.network.PacketBuffer;
/*   7:    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*   8:    */ import org.apache.commons.lang3.Validate;
/*   9:    */ 
/*  10:    */ public class S29PacketSoundEffect
/*  11:    */   extends Packet
/*  12:    */ {
/*  13:    */   private String field_149219_a;
/*  14:    */   private int field_149217_b;
/*  15: 14 */   private int field_149218_c = 2147483647;
/*  16:    */   private int field_149215_d;
/*  17:    */   private float field_149216_e;
/*  18:    */   private int field_149214_f;
/*  19:    */   private static final String __OBFID = "CL_00001309";
/*  20:    */   
/*  21:    */   public S29PacketSoundEffect() {}
/*  22:    */   
/*  23:    */   public S29PacketSoundEffect(String p_i45200_1_, double p_i45200_2_, double p_i45200_4_, double p_i45200_6_, float p_i45200_8_, float p_i45200_9_)
/*  24:    */   {
/*  25: 24 */     Validate.notNull(p_i45200_1_, "name", new Object[0]);
/*  26: 25 */     this.field_149219_a = p_i45200_1_;
/*  27: 26 */     this.field_149217_b = ((int)(p_i45200_2_ * 8.0D));
/*  28: 27 */     this.field_149218_c = ((int)(p_i45200_4_ * 8.0D));
/*  29: 28 */     this.field_149215_d = ((int)(p_i45200_6_ * 8.0D));
/*  30: 29 */     this.field_149216_e = p_i45200_8_;
/*  31: 30 */     this.field_149214_f = ((int)(p_i45200_9_ * 63.0F));
/*  32: 32 */     if (this.field_149214_f < 0) {
/*  33: 34 */       this.field_149214_f = 0;
/*  34:    */     }
/*  35: 37 */     if (this.field_149214_f > 255) {
/*  36: 39 */       this.field_149214_f = 255;
/*  37:    */     }
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void readPacketData(PacketBuffer p_148837_1_)
/*  41:    */     throws IOException
/*  42:    */   {
/*  43: 48 */     this.field_149219_a = p_148837_1_.readStringFromBuffer(256);
/*  44: 49 */     this.field_149217_b = p_148837_1_.readInt();
/*  45: 50 */     this.field_149218_c = p_148837_1_.readInt();
/*  46: 51 */     this.field_149215_d = p_148837_1_.readInt();
/*  47: 52 */     this.field_149216_e = p_148837_1_.readFloat();
/*  48: 53 */     this.field_149214_f = p_148837_1_.readUnsignedByte();
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void writePacketData(PacketBuffer p_148840_1_)
/*  52:    */     throws IOException
/*  53:    */   {
/*  54: 61 */     p_148840_1_.writeStringToBuffer(this.field_149219_a);
/*  55: 62 */     p_148840_1_.writeInt(this.field_149217_b);
/*  56: 63 */     p_148840_1_.writeInt(this.field_149218_c);
/*  57: 64 */     p_148840_1_.writeInt(this.field_149215_d);
/*  58: 65 */     p_148840_1_.writeFloat(this.field_149216_e);
/*  59: 66 */     p_148840_1_.writeByte(this.field_149214_f);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public String func_149212_c()
/*  63:    */   {
/*  64: 71 */     return this.field_149219_a;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public double func_149207_d()
/*  68:    */   {
/*  69: 76 */     return this.field_149217_b / 8.0F;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public double func_149211_e()
/*  73:    */   {
/*  74: 81 */     return this.field_149218_c / 8.0F;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public double func_149210_f()
/*  78:    */   {
/*  79: 86 */     return this.field_149215_d / 8.0F;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public float func_149208_g()
/*  83:    */   {
/*  84: 91 */     return this.field_149216_e;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public float func_149209_h()
/*  88:    */   {
/*  89: 96 */     return this.field_149214_f / 63.0F;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void processPacket(INetHandlerPlayClient p_149213_1_)
/*  93:    */   {
/*  94:101 */     p_149213_1_.handleSoundEffect(this);
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void processPacket(INetHandler p_148833_1_)
/*  98:    */   {
/*  99:106 */     processPacket((INetHandlerPlayClient)p_148833_1_);
/* 100:    */   }
/* 101:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.server.S29PacketSoundEffect
 * JD-Core Version:    0.7.0.1
 */