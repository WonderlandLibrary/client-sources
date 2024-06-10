/*   1:    */ package net.minecraft.network.play.client;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import net.minecraft.network.INetHandler;
/*   5:    */ import net.minecraft.network.Packet;
/*   6:    */ import net.minecraft.network.PacketBuffer;
/*   7:    */ import net.minecraft.network.play.INetHandlerPlayServer;
/*   8:    */ 
/*   9:    */ public class C03PacketPlayer
/*  10:    */   extends Packet
/*  11:    */ {
/*  12:    */   protected double field_149479_a;
/*  13:    */   protected double field_149477_b;
/*  14:    */   protected double field_149478_c;
/*  15:    */   protected double field_149475_d;
/*  16:    */   protected float field_149476_e;
/*  17:    */   protected float field_149473_f;
/*  18:    */   protected boolean field_149474_g;
/*  19:    */   protected boolean field_149480_h;
/*  20:    */   protected boolean field_149481_i;
/*  21:    */   private static final String __OBFID = "CL_00001360";
/*  22:    */   
/*  23:    */   public C03PacketPlayer() {}
/*  24:    */   
/*  25:    */   public C03PacketPlayer(boolean p_i45256_1_)
/*  26:    */   {
/*  27: 26 */     this.field_149474_g = p_i45256_1_;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void processPacket(INetHandlerPlayServer p_149468_1_)
/*  31:    */   {
/*  32: 31 */     p_149468_1_.processPlayer(this);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void readPacketData(PacketBuffer p_148837_1_)
/*  36:    */     throws IOException
/*  37:    */   {
/*  38: 39 */     this.field_149474_g = (p_148837_1_.readUnsignedByte() != 0);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void writePacketData(PacketBuffer p_148840_1_)
/*  42:    */     throws IOException
/*  43:    */   {
/*  44: 47 */     p_148840_1_.writeByte(this.field_149474_g ? 1 : 0);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public double func_149464_c()
/*  48:    */   {
/*  49: 52 */     return this.field_149479_a;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public double func_149467_d()
/*  53:    */   {
/*  54: 57 */     return this.field_149477_b;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public double func_149472_e()
/*  58:    */   {
/*  59: 62 */     return this.field_149478_c;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public double func_149471_f()
/*  63:    */   {
/*  64: 67 */     return this.field_149475_d;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public float func_149462_g()
/*  68:    */   {
/*  69: 72 */     return this.field_149476_e;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public float func_149470_h()
/*  73:    */   {
/*  74: 77 */     return this.field_149473_f;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public boolean func_149465_i()
/*  78:    */   {
/*  79: 82 */     return this.field_149474_g;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public boolean func_149466_j()
/*  83:    */   {
/*  84: 87 */     return this.field_149480_h;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public boolean func_149463_k()
/*  88:    */   {
/*  89: 92 */     return this.field_149481_i;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void func_149469_a(boolean p_149469_1_)
/*  93:    */   {
/*  94: 97 */     this.field_149480_h = p_149469_1_;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void processPacket(INetHandler p_148833_1_)
/*  98:    */   {
/*  99:102 */     processPacket((INetHandlerPlayServer)p_148833_1_);
/* 100:    */   }
/* 101:    */   
/* 102:    */   public static class C05PacketPlayerLook
/* 103:    */     extends C03PacketPlayer
/* 104:    */   {
/* 105:    */     private static final String __OBFID = "CL_00001363";
/* 106:    */     
/* 107:    */     public C05PacketPlayerLook()
/* 108:    */     {
/* 109:111 */       this.field_149481_i = true;
/* 110:    */     }
/* 111:    */     
/* 112:    */     public C05PacketPlayerLook(float p_i45255_1_, float p_i45255_2_, boolean p_i45255_3_)
/* 113:    */     {
/* 114:116 */       this.field_149476_e = p_i45255_1_;
/* 115:117 */       this.field_149473_f = p_i45255_2_;
/* 116:118 */       this.field_149474_g = p_i45255_3_;
/* 117:119 */       this.field_149481_i = true;
/* 118:    */     }
/* 119:    */     
/* 120:    */     public void readPacketData(PacketBuffer p_148837_1_)
/* 121:    */       throws IOException
/* 122:    */     {
/* 123:124 */       this.field_149476_e = p_148837_1_.readFloat();
/* 124:125 */       this.field_149473_f = p_148837_1_.readFloat();
/* 125:126 */       super.readPacketData(p_148837_1_);
/* 126:    */     }
/* 127:    */     
/* 128:    */     public void writePacketData(PacketBuffer p_148840_1_)
/* 129:    */       throws IOException
/* 130:    */     {
/* 131:131 */       p_148840_1_.writeFloat(this.field_149476_e);
/* 132:132 */       p_148840_1_.writeFloat(this.field_149473_f);
/* 133:133 */       super.writePacketData(p_148840_1_);
/* 134:    */     }
/* 135:    */     
/* 136:    */     public void processPacket(INetHandler p_148833_1_)
/* 137:    */     {
/* 138:138 */       super.processPacket((INetHandlerPlayServer)p_148833_1_);
/* 139:    */     }
/* 140:    */   }
/* 141:    */   
/* 142:    */   public static class C06PacketPlayerPosLook
/* 143:    */     extends C03PacketPlayer
/* 144:    */   {
/* 145:    */     private static final String __OBFID = "CL_00001362";
/* 146:    */     
/* 147:    */     public C06PacketPlayerPosLook()
/* 148:    */     {
/* 149:148 */       this.field_149480_h = true;
/* 150:149 */       this.field_149481_i = true;
/* 151:    */     }
/* 152:    */     
/* 153:    */     public C06PacketPlayerPosLook(double p_i45254_1_, double p_i45254_3_, double p_i45254_5_, double p_i45254_7_, float p_i45254_9_, float p_i45254_10_, boolean p_i45254_11_)
/* 154:    */     {
/* 155:154 */       this.field_149479_a = p_i45254_1_;
/* 156:155 */       this.field_149477_b = p_i45254_3_;
/* 157:156 */       this.field_149475_d = p_i45254_5_;
/* 158:157 */       this.field_149478_c = p_i45254_7_;
/* 159:158 */       this.field_149476_e = p_i45254_9_;
/* 160:159 */       this.field_149473_f = p_i45254_10_;
/* 161:160 */       this.field_149474_g = p_i45254_11_;
/* 162:161 */       this.field_149481_i = true;
/* 163:162 */       this.field_149480_h = true;
/* 164:    */     }
/* 165:    */     
/* 166:    */     public void readPacketData(PacketBuffer p_148837_1_)
/* 167:    */       throws IOException
/* 168:    */     {
/* 169:167 */       this.field_149479_a = p_148837_1_.readDouble();
/* 170:168 */       this.field_149477_b = p_148837_1_.readDouble();
/* 171:169 */       this.field_149475_d = p_148837_1_.readDouble();
/* 172:170 */       this.field_149478_c = p_148837_1_.readDouble();
/* 173:171 */       this.field_149476_e = p_148837_1_.readFloat();
/* 174:172 */       this.field_149473_f = p_148837_1_.readFloat();
/* 175:173 */       super.readPacketData(p_148837_1_);
/* 176:    */     }
/* 177:    */     
/* 178:    */     public void writePacketData(PacketBuffer p_148840_1_)
/* 179:    */       throws IOException
/* 180:    */     {
/* 181:178 */       p_148840_1_.writeDouble(this.field_149479_a);
/* 182:179 */       p_148840_1_.writeDouble(this.field_149477_b);
/* 183:180 */       p_148840_1_.writeDouble(this.field_149475_d);
/* 184:181 */       p_148840_1_.writeDouble(this.field_149478_c);
/* 185:182 */       p_148840_1_.writeFloat(this.field_149476_e);
/* 186:183 */       p_148840_1_.writeFloat(this.field_149473_f);
/* 187:184 */       super.writePacketData(p_148840_1_);
/* 188:    */     }
/* 189:    */     
/* 190:    */     public void processPacket(INetHandler p_148833_1_)
/* 191:    */     {
/* 192:189 */       super.processPacket((INetHandlerPlayServer)p_148833_1_);
/* 193:    */     }
/* 194:    */   }
/* 195:    */   
/* 196:    */   public static class C04PacketPlayerPosition
/* 197:    */     extends C03PacketPlayer
/* 198:    */   {
/* 199:    */     private static final String __OBFID = "CL_00001361";
/* 200:    */     
/* 201:    */     public C04PacketPlayerPosition()
/* 202:    */     {
/* 203:199 */       this.field_149480_h = true;
/* 204:    */     }
/* 205:    */     
/* 206:    */     public C04PacketPlayerPosition(double p_i45253_1_, double p_i45253_3_, double p_i45253_5_, double p_i45253_7_, boolean p_i45253_9_)
/* 207:    */     {
/* 208:204 */       this.field_149479_a = p_i45253_1_;
/* 209:205 */       this.field_149477_b = p_i45253_3_;
/* 210:206 */       this.field_149475_d = p_i45253_5_;
/* 211:207 */       this.field_149478_c = p_i45253_7_;
/* 212:208 */       this.field_149474_g = p_i45253_9_;
/* 213:209 */       this.field_149480_h = true;
/* 214:    */     }
/* 215:    */     
/* 216:    */     public void readPacketData(PacketBuffer p_148837_1_)
/* 217:    */       throws IOException
/* 218:    */     {
/* 219:214 */       this.field_149479_a = p_148837_1_.readDouble();
/* 220:215 */       this.field_149477_b = p_148837_1_.readDouble();
/* 221:216 */       this.field_149475_d = p_148837_1_.readDouble();
/* 222:217 */       this.field_149478_c = p_148837_1_.readDouble();
/* 223:218 */       super.readPacketData(p_148837_1_);
/* 224:    */     }
/* 225:    */     
/* 226:    */     public void writePacketData(PacketBuffer p_148840_1_)
/* 227:    */       throws IOException
/* 228:    */     {
/* 229:223 */       p_148840_1_.writeDouble(this.field_149479_a);
/* 230:224 */       p_148840_1_.writeDouble(this.field_149477_b);
/* 231:225 */       p_148840_1_.writeDouble(this.field_149475_d);
/* 232:226 */       p_148840_1_.writeDouble(this.field_149478_c);
/* 233:227 */       super.writePacketData(p_148840_1_);
/* 234:    */     }
/* 235:    */     
/* 236:    */     public void processPacket(INetHandler p_148833_1_)
/* 237:    */     {
/* 238:232 */       super.processPacket((INetHandlerPlayServer)p_148833_1_);
/* 239:    */     }
/* 240:    */   }
/* 241:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.client.C03PacketPlayer
 * JD-Core Version:    0.7.0.1
 */