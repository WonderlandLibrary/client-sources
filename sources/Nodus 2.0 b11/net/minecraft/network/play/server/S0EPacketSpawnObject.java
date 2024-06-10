/*   1:    */ package net.minecraft.network.play.server;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import net.minecraft.entity.Entity;
/*   5:    */ import net.minecraft.network.INetHandler;
/*   6:    */ import net.minecraft.network.Packet;
/*   7:    */ import net.minecraft.network.PacketBuffer;
/*   8:    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*   9:    */ import net.minecraft.util.MathHelper;
/*  10:    */ 
/*  11:    */ public class S0EPacketSpawnObject
/*  12:    */   extends Packet
/*  13:    */ {
/*  14:    */   private int field_149018_a;
/*  15:    */   private int field_149016_b;
/*  16:    */   private int field_149017_c;
/*  17:    */   private int field_149014_d;
/*  18:    */   private int field_149015_e;
/*  19:    */   private int field_149012_f;
/*  20:    */   private int field_149013_g;
/*  21:    */   private int field_149021_h;
/*  22:    */   private int field_149022_i;
/*  23:    */   private int field_149019_j;
/*  24:    */   private int field_149020_k;
/*  25:    */   private static final String __OBFID = "CL_00001276";
/*  26:    */   
/*  27:    */   public S0EPacketSpawnObject() {}
/*  28:    */   
/*  29:    */   public S0EPacketSpawnObject(Entity p_i45165_1_, int p_i45165_2_)
/*  30:    */   {
/*  31: 30 */     this(p_i45165_1_, p_i45165_2_, 0);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public S0EPacketSpawnObject(Entity p_i45166_1_, int p_i45166_2_, int p_i45166_3_)
/*  35:    */   {
/*  36: 35 */     this.field_149018_a = p_i45166_1_.getEntityId();
/*  37: 36 */     this.field_149016_b = MathHelper.floor_double(p_i45166_1_.posX * 32.0D);
/*  38: 37 */     this.field_149017_c = MathHelper.floor_double(p_i45166_1_.posY * 32.0D);
/*  39: 38 */     this.field_149014_d = MathHelper.floor_double(p_i45166_1_.posZ * 32.0D);
/*  40: 39 */     this.field_149021_h = MathHelper.floor_float(p_i45166_1_.rotationPitch * 256.0F / 360.0F);
/*  41: 40 */     this.field_149022_i = MathHelper.floor_float(p_i45166_1_.rotationYaw * 256.0F / 360.0F);
/*  42: 41 */     this.field_149019_j = p_i45166_2_;
/*  43: 42 */     this.field_149020_k = p_i45166_3_;
/*  44: 44 */     if (p_i45166_3_ > 0)
/*  45:    */     {
/*  46: 46 */       double var4 = p_i45166_1_.motionX;
/*  47: 47 */       double var6 = p_i45166_1_.motionY;
/*  48: 48 */       double var8 = p_i45166_1_.motionZ;
/*  49: 49 */       double var10 = 3.9D;
/*  50: 51 */       if (var4 < -var10) {
/*  51: 53 */         var4 = -var10;
/*  52:    */       }
/*  53: 56 */       if (var6 < -var10) {
/*  54: 58 */         var6 = -var10;
/*  55:    */       }
/*  56: 61 */       if (var8 < -var10) {
/*  57: 63 */         var8 = -var10;
/*  58:    */       }
/*  59: 66 */       if (var4 > var10) {
/*  60: 68 */         var4 = var10;
/*  61:    */       }
/*  62: 71 */       if (var6 > var10) {
/*  63: 73 */         var6 = var10;
/*  64:    */       }
/*  65: 76 */       if (var8 > var10) {
/*  66: 78 */         var8 = var10;
/*  67:    */       }
/*  68: 81 */       this.field_149015_e = ((int)(var4 * 8000.0D));
/*  69: 82 */       this.field_149012_f = ((int)(var6 * 8000.0D));
/*  70: 83 */       this.field_149013_g = ((int)(var8 * 8000.0D));
/*  71:    */     }
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void readPacketData(PacketBuffer p_148837_1_)
/*  75:    */     throws IOException
/*  76:    */   {
/*  77: 92 */     this.field_149018_a = p_148837_1_.readVarIntFromBuffer();
/*  78: 93 */     this.field_149019_j = p_148837_1_.readByte();
/*  79: 94 */     this.field_149016_b = p_148837_1_.readInt();
/*  80: 95 */     this.field_149017_c = p_148837_1_.readInt();
/*  81: 96 */     this.field_149014_d = p_148837_1_.readInt();
/*  82: 97 */     this.field_149021_h = p_148837_1_.readByte();
/*  83: 98 */     this.field_149022_i = p_148837_1_.readByte();
/*  84: 99 */     this.field_149020_k = p_148837_1_.readInt();
/*  85:101 */     if (this.field_149020_k > 0)
/*  86:    */     {
/*  87:103 */       this.field_149015_e = p_148837_1_.readShort();
/*  88:104 */       this.field_149012_f = p_148837_1_.readShort();
/*  89:105 */       this.field_149013_g = p_148837_1_.readShort();
/*  90:    */     }
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void writePacketData(PacketBuffer p_148840_1_)
/*  94:    */     throws IOException
/*  95:    */   {
/*  96:114 */     p_148840_1_.writeVarIntToBuffer(this.field_149018_a);
/*  97:115 */     p_148840_1_.writeByte(this.field_149019_j);
/*  98:116 */     p_148840_1_.writeInt(this.field_149016_b);
/*  99:117 */     p_148840_1_.writeInt(this.field_149017_c);
/* 100:118 */     p_148840_1_.writeInt(this.field_149014_d);
/* 101:119 */     p_148840_1_.writeByte(this.field_149021_h);
/* 102:120 */     p_148840_1_.writeByte(this.field_149022_i);
/* 103:121 */     p_148840_1_.writeInt(this.field_149020_k);
/* 104:123 */     if (this.field_149020_k > 0)
/* 105:    */     {
/* 106:125 */       p_148840_1_.writeShort(this.field_149015_e);
/* 107:126 */       p_148840_1_.writeShort(this.field_149012_f);
/* 108:127 */       p_148840_1_.writeShort(this.field_149013_g);
/* 109:    */     }
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void processPacket(INetHandlerPlayClient p_149011_1_)
/* 113:    */   {
/* 114:133 */     p_149011_1_.handleSpawnObject(this);
/* 115:    */   }
/* 116:    */   
/* 117:    */   public String serialize()
/* 118:    */   {
/* 119:141 */     return String.format("id=%d, type=%d, x=%.2f, y=%.2f, z=%.2f", new Object[] { Integer.valueOf(this.field_149018_a), Integer.valueOf(this.field_149019_j), Float.valueOf(this.field_149016_b / 32.0F), Float.valueOf(this.field_149017_c / 32.0F), Float.valueOf(this.field_149014_d / 32.0F) });
/* 120:    */   }
/* 121:    */   
/* 122:    */   public int func_149001_c()
/* 123:    */   {
/* 124:146 */     return this.field_149018_a;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public int func_148997_d()
/* 128:    */   {
/* 129:151 */     return this.field_149016_b;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public int func_148998_e()
/* 133:    */   {
/* 134:156 */     return this.field_149017_c;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public int func_148994_f()
/* 138:    */   {
/* 139:161 */     return this.field_149014_d;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public int func_149010_g()
/* 143:    */   {
/* 144:166 */     return this.field_149015_e;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public int func_149004_h()
/* 148:    */   {
/* 149:171 */     return this.field_149012_f;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public int func_148999_i()
/* 153:    */   {
/* 154:176 */     return this.field_149013_g;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public int func_149008_j()
/* 158:    */   {
/* 159:181 */     return this.field_149021_h;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public int func_149006_k()
/* 163:    */   {
/* 164:186 */     return this.field_149022_i;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public int func_148993_l()
/* 168:    */   {
/* 169:191 */     return this.field_149019_j;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public int func_149009_m()
/* 173:    */   {
/* 174:196 */     return this.field_149020_k;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void func_148996_a(int p_148996_1_)
/* 178:    */   {
/* 179:201 */     this.field_149016_b = p_148996_1_;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void func_148995_b(int p_148995_1_)
/* 183:    */   {
/* 184:206 */     this.field_149017_c = p_148995_1_;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public void func_149005_c(int p_149005_1_)
/* 188:    */   {
/* 189:211 */     this.field_149014_d = p_149005_1_;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void func_149003_d(int p_149003_1_)
/* 193:    */   {
/* 194:216 */     this.field_149015_e = p_149003_1_;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public void func_149000_e(int p_149000_1_)
/* 198:    */   {
/* 199:221 */     this.field_149012_f = p_149000_1_;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void func_149007_f(int p_149007_1_)
/* 203:    */   {
/* 204:226 */     this.field_149013_g = p_149007_1_;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void func_149002_g(int p_149002_1_)
/* 208:    */   {
/* 209:231 */     this.field_149020_k = p_149002_1_;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public void processPacket(INetHandler p_148833_1_)
/* 213:    */   {
/* 214:236 */     processPacket((INetHandlerPlayClient)p_148833_1_);
/* 215:    */   }
/* 216:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.server.S0EPacketSpawnObject
 * JD-Core Version:    0.7.0.1
 */