/*   1:    */ package net.minecraft.network.play.server;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import net.minecraft.entity.Entity;
/*   5:    */ import net.minecraft.network.INetHandler;
/*   6:    */ import net.minecraft.network.Packet;
/*   7:    */ import net.minecraft.network.PacketBuffer;
/*   8:    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*   9:    */ import net.minecraft.world.World;
/*  10:    */ 
/*  11:    */ public class S14PacketEntity
/*  12:    */   extends Packet
/*  13:    */ {
/*  14:    */   protected int field_149074_a;
/*  15:    */   protected byte field_149072_b;
/*  16:    */   protected byte field_149073_c;
/*  17:    */   protected byte field_149070_d;
/*  18:    */   protected byte field_149071_e;
/*  19:    */   protected byte field_149068_f;
/*  20:    */   protected boolean field_149069_g;
/*  21:    */   private static final String __OBFID = "CL_00001312";
/*  22:    */   
/*  23:    */   public S14PacketEntity() {}
/*  24:    */   
/*  25:    */   public S14PacketEntity(int p_i45206_1_)
/*  26:    */   {
/*  27: 26 */     this.field_149074_a = p_i45206_1_;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void readPacketData(PacketBuffer p_148837_1_)
/*  31:    */     throws IOException
/*  32:    */   {
/*  33: 34 */     this.field_149074_a = p_148837_1_.readInt();
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void writePacketData(PacketBuffer p_148840_1_)
/*  37:    */     throws IOException
/*  38:    */   {
/*  39: 42 */     p_148840_1_.writeInt(this.field_149074_a);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void processPacket(INetHandlerPlayClient p_149067_1_)
/*  43:    */   {
/*  44: 47 */     p_149067_1_.handleEntityMovement(this);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public String serialize()
/*  48:    */   {
/*  49: 55 */     return String.format("id=%d", new Object[] { Integer.valueOf(this.field_149074_a) });
/*  50:    */   }
/*  51:    */   
/*  52:    */   public String toString()
/*  53:    */   {
/*  54: 60 */     return "Entity_" + super.toString();
/*  55:    */   }
/*  56:    */   
/*  57:    */   public Entity func_149065_a(World p_149065_1_)
/*  58:    */   {
/*  59: 65 */     return p_149065_1_.getEntityByID(this.field_149074_a);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public byte func_149062_c()
/*  63:    */   {
/*  64: 70 */     return this.field_149072_b;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public byte func_149061_d()
/*  68:    */   {
/*  69: 75 */     return this.field_149073_c;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public byte func_149064_e()
/*  73:    */   {
/*  74: 80 */     return this.field_149070_d;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public byte func_149066_f()
/*  78:    */   {
/*  79: 85 */     return this.field_149071_e;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public byte func_149063_g()
/*  83:    */   {
/*  84: 90 */     return this.field_149068_f;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public boolean func_149060_h()
/*  88:    */   {
/*  89: 95 */     return this.field_149069_g;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void processPacket(INetHandler p_148833_1_)
/*  93:    */   {
/*  94:100 */     processPacket((INetHandlerPlayClient)p_148833_1_);
/*  95:    */   }
/*  96:    */   
/*  97:    */   public static class S16PacketEntityLook
/*  98:    */     extends S14PacketEntity
/*  99:    */   {
/* 100:    */     private static final String __OBFID = "CL_00001315";
/* 101:    */     
/* 102:    */     public S16PacketEntityLook()
/* 103:    */     {
/* 104:109 */       this.field_149069_g = true;
/* 105:    */     }
/* 106:    */     
/* 107:    */     public S16PacketEntityLook(int p_i45205_1_, byte p_i45205_2_, byte p_i45205_3_)
/* 108:    */     {
/* 109:114 */       super();
/* 110:115 */       this.field_149071_e = p_i45205_2_;
/* 111:116 */       this.field_149068_f = p_i45205_3_;
/* 112:117 */       this.field_149069_g = true;
/* 113:    */     }
/* 114:    */     
/* 115:    */     public void readPacketData(PacketBuffer p_148837_1_)
/* 116:    */       throws IOException
/* 117:    */     {
/* 118:122 */       super.readPacketData(p_148837_1_);
/* 119:123 */       this.field_149071_e = p_148837_1_.readByte();
/* 120:124 */       this.field_149068_f = p_148837_1_.readByte();
/* 121:    */     }
/* 122:    */     
/* 123:    */     public void writePacketData(PacketBuffer p_148840_1_)
/* 124:    */       throws IOException
/* 125:    */     {
/* 126:129 */       super.writePacketData(p_148840_1_);
/* 127:130 */       p_148840_1_.writeByte(this.field_149071_e);
/* 128:131 */       p_148840_1_.writeByte(this.field_149068_f);
/* 129:    */     }
/* 130:    */     
/* 131:    */     public String serialize()
/* 132:    */     {
/* 133:136 */       return super.serialize() + String.format(", yRot=%d, xRot=%d", new Object[] { Byte.valueOf(this.field_149071_e), Byte.valueOf(this.field_149068_f) });
/* 134:    */     }
/* 135:    */     
/* 136:    */     public void processPacket(INetHandler p_148833_1_)
/* 137:    */     {
/* 138:141 */       super.processPacket((INetHandlerPlayClient)p_148833_1_);
/* 139:    */     }
/* 140:    */   }
/* 141:    */   
/* 142:    */   public static class S15PacketEntityRelMove
/* 143:    */     extends S14PacketEntity
/* 144:    */   {
/* 145:    */     private static final String __OBFID = "CL_00001313";
/* 146:    */     
/* 147:    */     public S15PacketEntityRelMove() {}
/* 148:    */     
/* 149:    */     public S15PacketEntityRelMove(int p_i45203_1_, byte p_i45203_2_, byte p_i45203_3_, byte p_i45203_4_)
/* 150:    */     {
/* 151:153 */       super();
/* 152:154 */       this.field_149072_b = p_i45203_2_;
/* 153:155 */       this.field_149073_c = p_i45203_3_;
/* 154:156 */       this.field_149070_d = p_i45203_4_;
/* 155:    */     }
/* 156:    */     
/* 157:    */     public void readPacketData(PacketBuffer p_148837_1_)
/* 158:    */       throws IOException
/* 159:    */     {
/* 160:161 */       super.readPacketData(p_148837_1_);
/* 161:162 */       this.field_149072_b = p_148837_1_.readByte();
/* 162:163 */       this.field_149073_c = p_148837_1_.readByte();
/* 163:164 */       this.field_149070_d = p_148837_1_.readByte();
/* 164:    */     }
/* 165:    */     
/* 166:    */     public void writePacketData(PacketBuffer p_148840_1_)
/* 167:    */       throws IOException
/* 168:    */     {
/* 169:169 */       super.writePacketData(p_148840_1_);
/* 170:170 */       p_148840_1_.writeByte(this.field_149072_b);
/* 171:171 */       p_148840_1_.writeByte(this.field_149073_c);
/* 172:172 */       p_148840_1_.writeByte(this.field_149070_d);
/* 173:    */     }
/* 174:    */     
/* 175:    */     public String serialize()
/* 176:    */     {
/* 177:177 */       return super.serialize() + String.format(", xa=%d, ya=%d, za=%d", new Object[] { Byte.valueOf(this.field_149072_b), Byte.valueOf(this.field_149073_c), Byte.valueOf(this.field_149070_d) });
/* 178:    */     }
/* 179:    */     
/* 180:    */     public void processPacket(INetHandler p_148833_1_)
/* 181:    */     {
/* 182:182 */       super.processPacket((INetHandlerPlayClient)p_148833_1_);
/* 183:    */     }
/* 184:    */   }
/* 185:    */   
/* 186:    */   public static class S17PacketEntityLookMove
/* 187:    */     extends S14PacketEntity
/* 188:    */   {
/* 189:    */     private static final String __OBFID = "CL_00001314";
/* 190:    */     
/* 191:    */     public S17PacketEntityLookMove()
/* 192:    */     {
/* 193:192 */       this.field_149069_g = true;
/* 194:    */     }
/* 195:    */     
/* 196:    */     public S17PacketEntityLookMove(int p_i45204_1_, byte p_i45204_2_, byte p_i45204_3_, byte p_i45204_4_, byte p_i45204_5_, byte p_i45204_6_)
/* 197:    */     {
/* 198:197 */       super();
/* 199:198 */       this.field_149072_b = p_i45204_2_;
/* 200:199 */       this.field_149073_c = p_i45204_3_;
/* 201:200 */       this.field_149070_d = p_i45204_4_;
/* 202:201 */       this.field_149071_e = p_i45204_5_;
/* 203:202 */       this.field_149068_f = p_i45204_6_;
/* 204:203 */       this.field_149069_g = true;
/* 205:    */     }
/* 206:    */     
/* 207:    */     public void readPacketData(PacketBuffer p_148837_1_)
/* 208:    */       throws IOException
/* 209:    */     {
/* 210:208 */       super.readPacketData(p_148837_1_);
/* 211:209 */       this.field_149072_b = p_148837_1_.readByte();
/* 212:210 */       this.field_149073_c = p_148837_1_.readByte();
/* 213:211 */       this.field_149070_d = p_148837_1_.readByte();
/* 214:212 */       this.field_149071_e = p_148837_1_.readByte();
/* 215:213 */       this.field_149068_f = p_148837_1_.readByte();
/* 216:    */     }
/* 217:    */     
/* 218:    */     public void writePacketData(PacketBuffer p_148840_1_)
/* 219:    */       throws IOException
/* 220:    */     {
/* 221:218 */       super.writePacketData(p_148840_1_);
/* 222:219 */       p_148840_1_.writeByte(this.field_149072_b);
/* 223:220 */       p_148840_1_.writeByte(this.field_149073_c);
/* 224:221 */       p_148840_1_.writeByte(this.field_149070_d);
/* 225:222 */       p_148840_1_.writeByte(this.field_149071_e);
/* 226:223 */       p_148840_1_.writeByte(this.field_149068_f);
/* 227:    */     }
/* 228:    */     
/* 229:    */     public String serialize()
/* 230:    */     {
/* 231:228 */       return super.serialize() + String.format(", xa=%d, ya=%d, za=%d, yRot=%d, xRot=%d", new Object[] { Byte.valueOf(this.field_149072_b), Byte.valueOf(this.field_149073_c), Byte.valueOf(this.field_149070_d), Byte.valueOf(this.field_149071_e), Byte.valueOf(this.field_149068_f) });
/* 232:    */     }
/* 233:    */     
/* 234:    */     public void processPacket(INetHandler p_148833_1_)
/* 235:    */     {
/* 236:233 */       super.processPacket((INetHandlerPlayClient)p_148833_1_);
/* 237:    */     }
/* 238:    */   }
/* 239:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.server.S14PacketEntity
 * JD-Core Version:    0.7.0.1
 */