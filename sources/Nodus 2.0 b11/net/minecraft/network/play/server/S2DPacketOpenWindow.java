/*   1:    */ package net.minecraft.network.play.server;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import net.minecraft.network.INetHandler;
/*   5:    */ import net.minecraft.network.Packet;
/*   6:    */ import net.minecraft.network.PacketBuffer;
/*   7:    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*   8:    */ 
/*   9:    */ public class S2DPacketOpenWindow
/*  10:    */   extends Packet
/*  11:    */ {
/*  12:    */   private int field_148909_a;
/*  13:    */   private int field_148907_b;
/*  14:    */   private String field_148908_c;
/*  15:    */   private int field_148905_d;
/*  16:    */   private boolean field_148906_e;
/*  17:    */   private int field_148904_f;
/*  18:    */   private static final String __OBFID = "CL_00001293";
/*  19:    */   
/*  20:    */   public S2DPacketOpenWindow() {}
/*  21:    */   
/*  22:    */   public S2DPacketOpenWindow(int p_i45184_1_, int p_i45184_2_, String p_i45184_3_, int p_i45184_4_, boolean p_i45184_5_)
/*  23:    */   {
/*  24: 23 */     this.field_148909_a = p_i45184_1_;
/*  25: 24 */     this.field_148907_b = p_i45184_2_;
/*  26: 25 */     this.field_148908_c = p_i45184_3_;
/*  27: 26 */     this.field_148905_d = p_i45184_4_;
/*  28: 27 */     this.field_148906_e = p_i45184_5_;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public S2DPacketOpenWindow(int p_i45185_1_, int p_i45185_2_, String p_i45185_3_, int p_i45185_4_, boolean p_i45185_5_, int p_i45185_6_)
/*  32:    */   {
/*  33: 32 */     this(p_i45185_1_, p_i45185_2_, p_i45185_3_, p_i45185_4_, p_i45185_5_);
/*  34: 33 */     this.field_148904_f = p_i45185_6_;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void processPacket(INetHandlerPlayClient p_148903_1_)
/*  38:    */   {
/*  39: 38 */     p_148903_1_.handleOpenWindow(this);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void readPacketData(PacketBuffer p_148837_1_)
/*  43:    */     throws IOException
/*  44:    */   {
/*  45: 46 */     this.field_148909_a = p_148837_1_.readUnsignedByte();
/*  46: 47 */     this.field_148907_b = p_148837_1_.readUnsignedByte();
/*  47: 48 */     this.field_148908_c = p_148837_1_.readStringFromBuffer(32);
/*  48: 49 */     this.field_148905_d = p_148837_1_.readUnsignedByte();
/*  49: 50 */     this.field_148906_e = p_148837_1_.readBoolean();
/*  50: 52 */     if (this.field_148907_b == 11) {
/*  51: 54 */       this.field_148904_f = p_148837_1_.readInt();
/*  52:    */     }
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void writePacketData(PacketBuffer p_148840_1_)
/*  56:    */     throws IOException
/*  57:    */   {
/*  58: 63 */     p_148840_1_.writeByte(this.field_148909_a);
/*  59: 64 */     p_148840_1_.writeByte(this.field_148907_b);
/*  60: 65 */     p_148840_1_.writeStringToBuffer(this.field_148908_c);
/*  61: 66 */     p_148840_1_.writeByte(this.field_148905_d);
/*  62: 67 */     p_148840_1_.writeBoolean(this.field_148906_e);
/*  63: 69 */     if (this.field_148907_b == 11) {
/*  64: 71 */       p_148840_1_.writeInt(this.field_148904_f);
/*  65:    */     }
/*  66:    */   }
/*  67:    */   
/*  68:    */   public int func_148901_c()
/*  69:    */   {
/*  70: 77 */     return this.field_148909_a;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public int func_148899_d()
/*  74:    */   {
/*  75: 82 */     return this.field_148907_b;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public String func_148902_e()
/*  79:    */   {
/*  80: 87 */     return this.field_148908_c;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public int func_148898_f()
/*  84:    */   {
/*  85: 92 */     return this.field_148905_d;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public boolean func_148900_g()
/*  89:    */   {
/*  90: 97 */     return this.field_148906_e;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public int func_148897_h()
/*  94:    */   {
/*  95:102 */     return this.field_148904_f;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void processPacket(INetHandler p_148833_1_)
/*  99:    */   {
/* 100:107 */     processPacket((INetHandlerPlayClient)p_148833_1_);
/* 101:    */   }
/* 102:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.server.S2DPacketOpenWindow
 * JD-Core Version:    0.7.0.1
 */