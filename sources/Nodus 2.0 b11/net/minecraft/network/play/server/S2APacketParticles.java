/*   1:    */ package net.minecraft.network.play.server;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import net.minecraft.network.INetHandler;
/*   5:    */ import net.minecraft.network.Packet;
/*   6:    */ import net.minecraft.network.PacketBuffer;
/*   7:    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*   8:    */ 
/*   9:    */ public class S2APacketParticles
/*  10:    */   extends Packet
/*  11:    */ {
/*  12:    */   private String field_149236_a;
/*  13:    */   private float field_149234_b;
/*  14:    */   private float field_149235_c;
/*  15:    */   private float field_149232_d;
/*  16:    */   private float field_149233_e;
/*  17:    */   private float field_149230_f;
/*  18:    */   private float field_149231_g;
/*  19:    */   private float field_149237_h;
/*  20:    */   private int field_149238_i;
/*  21:    */   private static final String __OBFID = "CL_00001308";
/*  22:    */   
/*  23:    */   public S2APacketParticles() {}
/*  24:    */   
/*  25:    */   public S2APacketParticles(String p_i45199_1_, float p_i45199_2_, float p_i45199_3_, float p_i45199_4_, float p_i45199_5_, float p_i45199_6_, float p_i45199_7_, float p_i45199_8_, int p_i45199_9_)
/*  26:    */   {
/*  27: 26 */     this.field_149236_a = p_i45199_1_;
/*  28: 27 */     this.field_149234_b = p_i45199_2_;
/*  29: 28 */     this.field_149235_c = p_i45199_3_;
/*  30: 29 */     this.field_149232_d = p_i45199_4_;
/*  31: 30 */     this.field_149233_e = p_i45199_5_;
/*  32: 31 */     this.field_149230_f = p_i45199_6_;
/*  33: 32 */     this.field_149231_g = p_i45199_7_;
/*  34: 33 */     this.field_149237_h = p_i45199_8_;
/*  35: 34 */     this.field_149238_i = p_i45199_9_;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void readPacketData(PacketBuffer p_148837_1_)
/*  39:    */     throws IOException
/*  40:    */   {
/*  41: 42 */     this.field_149236_a = p_148837_1_.readStringFromBuffer(64);
/*  42: 43 */     this.field_149234_b = p_148837_1_.readFloat();
/*  43: 44 */     this.field_149235_c = p_148837_1_.readFloat();
/*  44: 45 */     this.field_149232_d = p_148837_1_.readFloat();
/*  45: 46 */     this.field_149233_e = p_148837_1_.readFloat();
/*  46: 47 */     this.field_149230_f = p_148837_1_.readFloat();
/*  47: 48 */     this.field_149231_g = p_148837_1_.readFloat();
/*  48: 49 */     this.field_149237_h = p_148837_1_.readFloat();
/*  49: 50 */     this.field_149238_i = p_148837_1_.readInt();
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void writePacketData(PacketBuffer p_148840_1_)
/*  53:    */     throws IOException
/*  54:    */   {
/*  55: 58 */     p_148840_1_.writeStringToBuffer(this.field_149236_a);
/*  56: 59 */     p_148840_1_.writeFloat(this.field_149234_b);
/*  57: 60 */     p_148840_1_.writeFloat(this.field_149235_c);
/*  58: 61 */     p_148840_1_.writeFloat(this.field_149232_d);
/*  59: 62 */     p_148840_1_.writeFloat(this.field_149233_e);
/*  60: 63 */     p_148840_1_.writeFloat(this.field_149230_f);
/*  61: 64 */     p_148840_1_.writeFloat(this.field_149231_g);
/*  62: 65 */     p_148840_1_.writeFloat(this.field_149237_h);
/*  63: 66 */     p_148840_1_.writeInt(this.field_149238_i);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public String func_149228_c()
/*  67:    */   {
/*  68: 71 */     return this.field_149236_a;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public double func_149220_d()
/*  72:    */   {
/*  73: 76 */     return this.field_149234_b;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public double func_149226_e()
/*  77:    */   {
/*  78: 81 */     return this.field_149235_c;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public double func_149225_f()
/*  82:    */   {
/*  83: 86 */     return this.field_149232_d;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public float func_149221_g()
/*  87:    */   {
/*  88: 91 */     return this.field_149233_e;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public float func_149224_h()
/*  92:    */   {
/*  93: 96 */     return this.field_149230_f;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public float func_149223_i()
/*  97:    */   {
/*  98:101 */     return this.field_149231_g;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public float func_149227_j()
/* 102:    */   {
/* 103:106 */     return this.field_149237_h;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public int func_149222_k()
/* 107:    */   {
/* 108:111 */     return this.field_149238_i;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void processPacket(INetHandlerPlayClient p_149229_1_)
/* 112:    */   {
/* 113:116 */     p_149229_1_.handleParticles(this);
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void processPacket(INetHandler p_148833_1_)
/* 117:    */   {
/* 118:121 */     processPacket((INetHandlerPlayClient)p_148833_1_);
/* 119:    */   }
/* 120:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.server.S2APacketParticles
 * JD-Core Version:    0.7.0.1
 */