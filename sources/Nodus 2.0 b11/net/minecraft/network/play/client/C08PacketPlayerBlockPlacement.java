/*   1:    */ package net.minecraft.network.play.client;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import net.minecraft.item.ItemStack;
/*   5:    */ import net.minecraft.network.INetHandler;
/*   6:    */ import net.minecraft.network.Packet;
/*   7:    */ import net.minecraft.network.PacketBuffer;
/*   8:    */ import net.minecraft.network.play.INetHandlerPlayServer;
/*   9:    */ 
/*  10:    */ public class C08PacketPlayerBlockPlacement
/*  11:    */   extends Packet
/*  12:    */ {
/*  13:    */   private int field_149583_a;
/*  14:    */   private int field_149581_b;
/*  15:    */   private int field_149582_c;
/*  16:    */   private int field_149579_d;
/*  17:    */   private ItemStack field_149580_e;
/*  18:    */   private float field_149577_f;
/*  19:    */   private float field_149578_g;
/*  20:    */   private float field_149584_h;
/*  21:    */   private static final String __OBFID = "CL_00001371";
/*  22:    */   
/*  23:    */   public C08PacketPlayerBlockPlacement() {}
/*  24:    */   
/*  25:    */   public C08PacketPlayerBlockPlacement(int p_i45265_1_, int p_i45265_2_, int p_i45265_3_, int p_i45265_4_, ItemStack p_i45265_5_, float p_i45265_6_, float p_i45265_7_, float p_i45265_8_)
/*  26:    */   {
/*  27: 26 */     this.field_149583_a = p_i45265_1_;
/*  28: 27 */     this.field_149581_b = p_i45265_2_;
/*  29: 28 */     this.field_149582_c = p_i45265_3_;
/*  30: 29 */     this.field_149579_d = p_i45265_4_;
/*  31: 30 */     this.field_149580_e = (p_i45265_5_ != null ? p_i45265_5_.copy() : null);
/*  32: 31 */     this.field_149577_f = p_i45265_6_;
/*  33: 32 */     this.field_149578_g = p_i45265_7_;
/*  34: 33 */     this.field_149584_h = p_i45265_8_;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void readPacketData(PacketBuffer p_148837_1_)
/*  38:    */     throws IOException
/*  39:    */   {
/*  40: 41 */     this.field_149583_a = p_148837_1_.readInt();
/*  41: 42 */     this.field_149581_b = p_148837_1_.readUnsignedByte();
/*  42: 43 */     this.field_149582_c = p_148837_1_.readInt();
/*  43: 44 */     this.field_149579_d = p_148837_1_.readUnsignedByte();
/*  44: 45 */     this.field_149580_e = p_148837_1_.readItemStackFromBuffer();
/*  45: 46 */     this.field_149577_f = (p_148837_1_.readUnsignedByte() / 16.0F);
/*  46: 47 */     this.field_149578_g = (p_148837_1_.readUnsignedByte() / 16.0F);
/*  47: 48 */     this.field_149584_h = (p_148837_1_.readUnsignedByte() / 16.0F);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void writePacketData(PacketBuffer p_148840_1_)
/*  51:    */     throws IOException
/*  52:    */   {
/*  53: 56 */     p_148840_1_.writeInt(this.field_149583_a);
/*  54: 57 */     p_148840_1_.writeByte(this.field_149581_b);
/*  55: 58 */     p_148840_1_.writeInt(this.field_149582_c);
/*  56: 59 */     p_148840_1_.writeByte(this.field_149579_d);
/*  57: 60 */     p_148840_1_.writeItemStackToBuffer(this.field_149580_e);
/*  58: 61 */     p_148840_1_.writeByte((int)(this.field_149577_f * 16.0F));
/*  59: 62 */     p_148840_1_.writeByte((int)(this.field_149578_g * 16.0F));
/*  60: 63 */     p_148840_1_.writeByte((int)(this.field_149584_h * 16.0F));
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void processPacket(INetHandlerPlayServer p_149572_1_)
/*  64:    */   {
/*  65: 68 */     p_149572_1_.processPlayerBlockPlacement(this);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public int func_149576_c()
/*  69:    */   {
/*  70: 73 */     return this.field_149583_a;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public int func_149571_d()
/*  74:    */   {
/*  75: 78 */     return this.field_149581_b;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public int func_149570_e()
/*  79:    */   {
/*  80: 83 */     return this.field_149582_c;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public int func_149568_f()
/*  84:    */   {
/*  85: 88 */     return this.field_149579_d;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public ItemStack func_149574_g()
/*  89:    */   {
/*  90: 93 */     return this.field_149580_e;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public float func_149573_h()
/*  94:    */   {
/*  95: 98 */     return this.field_149577_f;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public float func_149569_i()
/*  99:    */   {
/* 100:103 */     return this.field_149578_g;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public float func_149575_j()
/* 104:    */   {
/* 105:108 */     return this.field_149584_h;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void processPacket(INetHandler p_148833_1_)
/* 109:    */   {
/* 110:113 */     processPacket((INetHandlerPlayServer)p_148833_1_);
/* 111:    */   }
/* 112:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.client.C08PacketPlayerBlockPlacement
 * JD-Core Version:    0.7.0.1
 */