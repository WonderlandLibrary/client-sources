/*   1:    */ package net.minecraft.network.play.server;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.List;
/*   7:    */ import net.minecraft.network.INetHandler;
/*   8:    */ import net.minecraft.network.Packet;
/*   9:    */ import net.minecraft.network.PacketBuffer;
/*  10:    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*  11:    */ import net.minecraft.util.Vec3;
/*  12:    */ import net.minecraft.world.ChunkPosition;
/*  13:    */ 
/*  14:    */ public class S27PacketExplosion
/*  15:    */   extends Packet
/*  16:    */ {
/*  17:    */   private double field_149158_a;
/*  18:    */   private double field_149156_b;
/*  19:    */   private double field_149157_c;
/*  20:    */   private float field_149154_d;
/*  21:    */   private List field_149155_e;
/*  22:    */   private float field_149152_f;
/*  23:    */   private float field_149153_g;
/*  24:    */   private float field_149159_h;
/*  25:    */   private static final String __OBFID = "CL_00001300";
/*  26:    */   
/*  27:    */   public S27PacketExplosion() {}
/*  28:    */   
/*  29:    */   public S27PacketExplosion(double p_i45193_1_, double p_i45193_3_, double p_i45193_5_, float p_i45193_7_, List p_i45193_8_, Vec3 p_i45193_9_)
/*  30:    */   {
/*  31: 30 */     this.field_149158_a = p_i45193_1_;
/*  32: 31 */     this.field_149156_b = p_i45193_3_;
/*  33: 32 */     this.field_149157_c = p_i45193_5_;
/*  34: 33 */     this.field_149154_d = p_i45193_7_;
/*  35: 34 */     this.field_149155_e = new ArrayList(p_i45193_8_);
/*  36: 36 */     if (p_i45193_9_ != null)
/*  37:    */     {
/*  38: 38 */       this.field_149152_f = ((float)p_i45193_9_.xCoord);
/*  39: 39 */       this.field_149153_g = ((float)p_i45193_9_.yCoord);
/*  40: 40 */       this.field_149159_h = ((float)p_i45193_9_.zCoord);
/*  41:    */     }
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void readPacketData(PacketBuffer p_148837_1_)
/*  45:    */     throws IOException
/*  46:    */   {
/*  47: 49 */     this.field_149158_a = p_148837_1_.readFloat();
/*  48: 50 */     this.field_149156_b = p_148837_1_.readFloat();
/*  49: 51 */     this.field_149157_c = p_148837_1_.readFloat();
/*  50: 52 */     this.field_149154_d = p_148837_1_.readFloat();
/*  51: 53 */     int var2 = p_148837_1_.readInt();
/*  52: 54 */     this.field_149155_e = new ArrayList(var2);
/*  53: 55 */     int var3 = (int)this.field_149158_a;
/*  54: 56 */     int var4 = (int)this.field_149156_b;
/*  55: 57 */     int var5 = (int)this.field_149157_c;
/*  56: 59 */     for (int var6 = 0; var6 < var2; var6++)
/*  57:    */     {
/*  58: 61 */       int var7 = p_148837_1_.readByte() + var3;
/*  59: 62 */       int var8 = p_148837_1_.readByte() + var4;
/*  60: 63 */       int var9 = p_148837_1_.readByte() + var5;
/*  61: 64 */       this.field_149155_e.add(new ChunkPosition(var7, var8, var9));
/*  62:    */     }
/*  63: 67 */     this.field_149152_f = p_148837_1_.readFloat();
/*  64: 68 */     this.field_149153_g = p_148837_1_.readFloat();
/*  65: 69 */     this.field_149159_h = p_148837_1_.readFloat();
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void writePacketData(PacketBuffer p_148840_1_)
/*  69:    */     throws IOException
/*  70:    */   {
/*  71: 77 */     p_148840_1_.writeFloat((float)this.field_149158_a);
/*  72: 78 */     p_148840_1_.writeFloat((float)this.field_149156_b);
/*  73: 79 */     p_148840_1_.writeFloat((float)this.field_149157_c);
/*  74: 80 */     p_148840_1_.writeFloat(this.field_149154_d);
/*  75: 81 */     p_148840_1_.writeInt(this.field_149155_e.size());
/*  76: 82 */     int var2 = (int)this.field_149158_a;
/*  77: 83 */     int var3 = (int)this.field_149156_b;
/*  78: 84 */     int var4 = (int)this.field_149157_c;
/*  79: 85 */     Iterator var5 = this.field_149155_e.iterator();
/*  80: 87 */     while (var5.hasNext())
/*  81:    */     {
/*  82: 89 */       ChunkPosition var6 = (ChunkPosition)var5.next();
/*  83: 90 */       int var7 = var6.field_151329_a - var2;
/*  84: 91 */       int var8 = var6.field_151327_b - var3;
/*  85: 92 */       int var9 = var6.field_151328_c - var4;
/*  86: 93 */       p_148840_1_.writeByte(var7);
/*  87: 94 */       p_148840_1_.writeByte(var8);
/*  88: 95 */       p_148840_1_.writeByte(var9);
/*  89:    */     }
/*  90: 98 */     p_148840_1_.writeFloat(this.field_149152_f);
/*  91: 99 */     p_148840_1_.writeFloat(this.field_149153_g);
/*  92:100 */     p_148840_1_.writeFloat(this.field_149159_h);
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void processPacket(INetHandlerPlayClient p_149151_1_)
/*  96:    */   {
/*  97:105 */     p_149151_1_.handleExplosion(this);
/*  98:    */   }
/*  99:    */   
/* 100:    */   public float func_149149_c()
/* 101:    */   {
/* 102:110 */     return this.field_149152_f;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public float func_149144_d()
/* 106:    */   {
/* 107:115 */     return this.field_149153_g;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public float func_149147_e()
/* 111:    */   {
/* 112:120 */     return this.field_149159_h;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public double func_149148_f()
/* 116:    */   {
/* 117:125 */     return this.field_149158_a;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public double func_149143_g()
/* 121:    */   {
/* 122:130 */     return this.field_149156_b;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public double func_149145_h()
/* 126:    */   {
/* 127:135 */     return this.field_149157_c;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public float func_149146_i()
/* 131:    */   {
/* 132:140 */     return this.field_149154_d;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public List func_149150_j()
/* 136:    */   {
/* 137:145 */     return this.field_149155_e;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void processPacket(INetHandler p_148833_1_)
/* 141:    */   {
/* 142:150 */     processPacket((INetHandlerPlayClient)p_148833_1_);
/* 143:    */   }
/* 144:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.server.S27PacketExplosion
 * JD-Core Version:    0.7.0.1
 */