/*   1:    */ package net.minecraft.network.play.server;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.List;
/*   5:    */ import net.minecraft.entity.DataWatcher;
/*   6:    */ import net.minecraft.entity.Entity.EnumEntitySize;
/*   7:    */ import net.minecraft.entity.EntityList;
/*   8:    */ import net.minecraft.entity.EntityLivingBase;
/*   9:    */ import net.minecraft.network.INetHandler;
/*  10:    */ import net.minecraft.network.Packet;
/*  11:    */ import net.minecraft.network.PacketBuffer;
/*  12:    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*  13:    */ import net.minecraft.util.MathHelper;
/*  14:    */ 
/*  15:    */ public class S0FPacketSpawnMob
/*  16:    */   extends Packet
/*  17:    */ {
/*  18:    */   private int field_149042_a;
/*  19:    */   private int field_149040_b;
/*  20:    */   private int field_149041_c;
/*  21:    */   private int field_149038_d;
/*  22:    */   private int field_149039_e;
/*  23:    */   private int field_149036_f;
/*  24:    */   private int field_149037_g;
/*  25:    */   private int field_149047_h;
/*  26:    */   private byte field_149048_i;
/*  27:    */   private byte field_149045_j;
/*  28:    */   private byte field_149046_k;
/*  29:    */   private DataWatcher field_149043_l;
/*  30:    */   private List field_149044_m;
/*  31:    */   private static final String __OBFID = "CL_00001279";
/*  32:    */   
/*  33:    */   public S0FPacketSpawnMob() {}
/*  34:    */   
/*  35:    */   public S0FPacketSpawnMob(EntityLivingBase p_i45192_1_)
/*  36:    */   {
/*  37: 35 */     this.field_149042_a = p_i45192_1_.getEntityId();
/*  38: 36 */     this.field_149040_b = ((byte)EntityList.getEntityID(p_i45192_1_));
/*  39: 37 */     this.field_149041_c = p_i45192_1_.myEntitySize.multiplyBy32AndRound(p_i45192_1_.posX);
/*  40: 38 */     this.field_149038_d = MathHelper.floor_double(p_i45192_1_.posY * 32.0D);
/*  41: 39 */     this.field_149039_e = p_i45192_1_.myEntitySize.multiplyBy32AndRound(p_i45192_1_.posZ);
/*  42: 40 */     this.field_149048_i = ((byte)(int)(p_i45192_1_.rotationYaw * 256.0F / 360.0F));
/*  43: 41 */     this.field_149045_j = ((byte)(int)(p_i45192_1_.rotationPitch * 256.0F / 360.0F));
/*  44: 42 */     this.field_149046_k = ((byte)(int)(p_i45192_1_.rotationYawHead * 256.0F / 360.0F));
/*  45: 43 */     double var2 = 3.9D;
/*  46: 44 */     double var4 = p_i45192_1_.motionX;
/*  47: 45 */     double var6 = p_i45192_1_.motionY;
/*  48: 46 */     double var8 = p_i45192_1_.motionZ;
/*  49: 48 */     if (var4 < -var2) {
/*  50: 50 */       var4 = -var2;
/*  51:    */     }
/*  52: 53 */     if (var6 < -var2) {
/*  53: 55 */       var6 = -var2;
/*  54:    */     }
/*  55: 58 */     if (var8 < -var2) {
/*  56: 60 */       var8 = -var2;
/*  57:    */     }
/*  58: 63 */     if (var4 > var2) {
/*  59: 65 */       var4 = var2;
/*  60:    */     }
/*  61: 68 */     if (var6 > var2) {
/*  62: 70 */       var6 = var2;
/*  63:    */     }
/*  64: 73 */     if (var8 > var2) {
/*  65: 75 */       var8 = var2;
/*  66:    */     }
/*  67: 78 */     this.field_149036_f = ((int)(var4 * 8000.0D));
/*  68: 79 */     this.field_149037_g = ((int)(var6 * 8000.0D));
/*  69: 80 */     this.field_149047_h = ((int)(var8 * 8000.0D));
/*  70: 81 */     this.field_149043_l = p_i45192_1_.getDataWatcher();
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void readPacketData(PacketBuffer p_148837_1_)
/*  74:    */     throws IOException
/*  75:    */   {
/*  76: 89 */     this.field_149042_a = p_148837_1_.readVarIntFromBuffer();
/*  77: 90 */     this.field_149040_b = (p_148837_1_.readByte() & 0xFF);
/*  78: 91 */     this.field_149041_c = p_148837_1_.readInt();
/*  79: 92 */     this.field_149038_d = p_148837_1_.readInt();
/*  80: 93 */     this.field_149039_e = p_148837_1_.readInt();
/*  81: 94 */     this.field_149048_i = p_148837_1_.readByte();
/*  82: 95 */     this.field_149045_j = p_148837_1_.readByte();
/*  83: 96 */     this.field_149046_k = p_148837_1_.readByte();
/*  84: 97 */     this.field_149036_f = p_148837_1_.readShort();
/*  85: 98 */     this.field_149037_g = p_148837_1_.readShort();
/*  86: 99 */     this.field_149047_h = p_148837_1_.readShort();
/*  87:100 */     this.field_149044_m = DataWatcher.readWatchedListFromPacketBuffer(p_148837_1_);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void writePacketData(PacketBuffer p_148840_1_)
/*  91:    */     throws IOException
/*  92:    */   {
/*  93:108 */     p_148840_1_.writeVarIntToBuffer(this.field_149042_a);
/*  94:109 */     p_148840_1_.writeByte(this.field_149040_b & 0xFF);
/*  95:110 */     p_148840_1_.writeInt(this.field_149041_c);
/*  96:111 */     p_148840_1_.writeInt(this.field_149038_d);
/*  97:112 */     p_148840_1_.writeInt(this.field_149039_e);
/*  98:113 */     p_148840_1_.writeByte(this.field_149048_i);
/*  99:114 */     p_148840_1_.writeByte(this.field_149045_j);
/* 100:115 */     p_148840_1_.writeByte(this.field_149046_k);
/* 101:116 */     p_148840_1_.writeShort(this.field_149036_f);
/* 102:117 */     p_148840_1_.writeShort(this.field_149037_g);
/* 103:118 */     p_148840_1_.writeShort(this.field_149047_h);
/* 104:119 */     this.field_149043_l.func_151509_a(p_148840_1_);
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void processPacket(INetHandlerPlayClient p_149035_1_)
/* 108:    */   {
/* 109:124 */     p_149035_1_.handleSpawnMob(this);
/* 110:    */   }
/* 111:    */   
/* 112:    */   public List func_149027_c()
/* 113:    */   {
/* 114:129 */     if (this.field_149044_m == null) {
/* 115:131 */       this.field_149044_m = this.field_149043_l.getAllWatched();
/* 116:    */     }
/* 117:134 */     return this.field_149044_m;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public String serialize()
/* 121:    */   {
/* 122:142 */     return String.format("id=%d, type=%d, x=%.2f, y=%.2f, z=%.2f, xd=%.2f, yd=%.2f, zd=%.2f", new Object[] { Integer.valueOf(this.field_149042_a), Integer.valueOf(this.field_149040_b), Float.valueOf(this.field_149041_c / 32.0F), Float.valueOf(this.field_149038_d / 32.0F), Float.valueOf(this.field_149039_e / 32.0F), Float.valueOf(this.field_149036_f / 8000.0F), Float.valueOf(this.field_149037_g / 8000.0F), Float.valueOf(this.field_149047_h / 8000.0F) });
/* 123:    */   }
/* 124:    */   
/* 125:    */   public int func_149024_d()
/* 126:    */   {
/* 127:147 */     return this.field_149042_a;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public int func_149025_e()
/* 131:    */   {
/* 132:152 */     return this.field_149040_b;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public int func_149023_f()
/* 136:    */   {
/* 137:157 */     return this.field_149041_c;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public int func_149034_g()
/* 141:    */   {
/* 142:162 */     return this.field_149038_d;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public int func_149029_h()
/* 146:    */   {
/* 147:167 */     return this.field_149039_e;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public int func_149026_i()
/* 151:    */   {
/* 152:172 */     return this.field_149036_f;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public int func_149033_j()
/* 156:    */   {
/* 157:177 */     return this.field_149037_g;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public int func_149031_k()
/* 161:    */   {
/* 162:182 */     return this.field_149047_h;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public byte func_149028_l()
/* 166:    */   {
/* 167:187 */     return this.field_149048_i;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public byte func_149030_m()
/* 171:    */   {
/* 172:192 */     return this.field_149045_j;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public byte func_149032_n()
/* 176:    */   {
/* 177:197 */     return this.field_149046_k;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void processPacket(INetHandler p_148833_1_)
/* 181:    */   {
/* 182:202 */     processPacket((INetHandlerPlayClient)p_148833_1_);
/* 183:    */   }
/* 184:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.server.S0FPacketSpawnMob
 * JD-Core Version:    0.7.0.1
 */