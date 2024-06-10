/*   1:    */ package net.minecraft.network.play.server;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.List;
/*   5:    */ import java.util.zip.DataFormatException;
/*   6:    */ import java.util.zip.Deflater;
/*   7:    */ import java.util.zip.Inflater;
/*   8:    */ import net.minecraft.network.INetHandler;
/*   9:    */ import net.minecraft.network.Packet;
/*  10:    */ import net.minecraft.network.PacketBuffer;
/*  11:    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*  12:    */ import net.minecraft.world.World;
/*  13:    */ import net.minecraft.world.WorldProvider;
/*  14:    */ import net.minecraft.world.chunk.Chunk;
/*  15:    */ 
/*  16:    */ public class S26PacketMapChunkBulk
/*  17:    */   extends Packet
/*  18:    */ {
/*  19:    */   private int[] field_149266_a;
/*  20:    */   private int[] field_149264_b;
/*  21:    */   private int[] field_149265_c;
/*  22:    */   private int[] field_149262_d;
/*  23:    */   private byte[] field_149263_e;
/*  24:    */   private byte[][] field_149260_f;
/*  25:    */   private int field_149261_g;
/*  26:    */   private boolean field_149267_h;
/*  27: 24 */   private static byte[] field_149268_i = new byte[0];
/*  28:    */   private static final String __OBFID = "CL_00001306";
/*  29:    */   
/*  30:    */   public S26PacketMapChunkBulk() {}
/*  31:    */   
/*  32:    */   public S26PacketMapChunkBulk(List p_i45197_1_)
/*  33:    */   {
/*  34: 31 */     int var2 = p_i45197_1_.size();
/*  35: 32 */     this.field_149266_a = new int[var2];
/*  36: 33 */     this.field_149264_b = new int[var2];
/*  37: 34 */     this.field_149265_c = new int[var2];
/*  38: 35 */     this.field_149262_d = new int[var2];
/*  39: 36 */     this.field_149260_f = new byte[var2][];
/*  40: 37 */     this.field_149267_h = ((!p_i45197_1_.isEmpty()) && (!((Chunk)p_i45197_1_.get(0)).worldObj.provider.hasNoSky));
/*  41: 38 */     int var3 = 0;
/*  42: 40 */     for (int var4 = 0; var4 < var2; var4++)
/*  43:    */     {
/*  44: 42 */       var5 = (Chunk)p_i45197_1_.get(var4);
/*  45: 43 */       S21PacketChunkData.Extracted var6 = S21PacketChunkData.func_149269_a(var5, true, 65535);
/*  46: 45 */       if (field_149268_i.length < var3 + var6.field_150282_a.length)
/*  47:    */       {
/*  48: 47 */         byte[] var7 = new byte[var3 + var6.field_150282_a.length];
/*  49: 48 */         System.arraycopy(field_149268_i, 0, var7, 0, field_149268_i.length);
/*  50: 49 */         field_149268_i = var7;
/*  51:    */       }
/*  52: 52 */       System.arraycopy(var6.field_150282_a, 0, field_149268_i, var3, var6.field_150282_a.length);
/*  53: 53 */       var3 += var6.field_150282_a.length;
/*  54: 54 */       this.field_149266_a[var4] = var5.xPosition;
/*  55: 55 */       this.field_149264_b[var4] = var5.zPosition;
/*  56: 56 */       this.field_149265_c[var4] = var6.field_150280_b;
/*  57: 57 */       this.field_149262_d[var4] = var6.field_150281_c;
/*  58: 58 */       this.field_149260_f[var4] = var6.field_150282_a;
/*  59:    */     }
/*  60: 61 */     Deflater var11 = new Deflater(-1);
/*  61:    */     try
/*  62:    */     {
/*  63: 65 */       var11.setInput(field_149268_i, 0, var3);
/*  64: 66 */       var11.finish();
/*  65: 67 */       this.field_149263_e = new byte[var3];
/*  66: 68 */       this.field_149261_g = var11.deflate(this.field_149263_e);
/*  67:    */     }
/*  68:    */     finally
/*  69:    */     {
/*  70: 72 */       var11.end();
/*  71:    */     }
/*  72:    */   }
/*  73:    */   
/*  74:    */   public static int func_149258_c()
/*  75:    */   {
/*  76: 78 */     return 5;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void readPacketData(PacketBuffer p_148837_1_)
/*  80:    */     throws IOException
/*  81:    */   {
/*  82: 86 */     short var2 = p_148837_1_.readShort();
/*  83: 87 */     this.field_149261_g = p_148837_1_.readInt();
/*  84: 88 */     this.field_149267_h = p_148837_1_.readBoolean();
/*  85: 89 */     this.field_149266_a = new int[var2];
/*  86: 90 */     this.field_149264_b = new int[var2];
/*  87: 91 */     this.field_149265_c = new int[var2];
/*  88: 92 */     this.field_149262_d = new int[var2];
/*  89: 93 */     this.field_149260_f = new byte[var2][];
/*  90: 95 */     if (field_149268_i.length < this.field_149261_g) {
/*  91: 97 */       field_149268_i = new byte[this.field_149261_g];
/*  92:    */     }
/*  93:100 */     p_148837_1_.readBytes(field_149268_i, 0, this.field_149261_g);
/*  94:101 */     byte[] var3 = new byte[S21PacketChunkData.func_149275_c() * var2];
/*  95:102 */     Inflater var4 = new Inflater();
/*  96:103 */     var4.setInput(field_149268_i, 0, this.field_149261_g);
/*  97:    */     try
/*  98:    */     {
/*  99:107 */       var4.inflate(var3);
/* 100:    */     }
/* 101:    */     catch (DataFormatException var12)
/* 102:    */     {
/* 103:111 */       throw new IOException("Bad compressed data format");
/* 104:    */     }
/* 105:    */     finally
/* 106:    */     {
/* 107:115 */       var4.end();
/* 108:    */     }
/* 109:118 */     int var5 = 0;
/* 110:120 */     for (int var6 = 0; var6 < var2; var6++)
/* 111:    */     {
/* 112:122 */       this.field_149266_a[var6] = p_148837_1_.readInt();
/* 113:123 */       this.field_149264_b[var6] = p_148837_1_.readInt();
/* 114:124 */       this.field_149265_c[var6] = p_148837_1_.readShort();
/* 115:125 */       this.field_149262_d[var6] = p_148837_1_.readShort();
/* 116:126 */       int var7 = 0;
/* 117:127 */       int var8 = 0;
/* 118:130 */       for (int var9 = 0; var9 < 16; var9++)
/* 119:    */       {
/* 120:132 */         var7 += (this.field_149265_c[var6] >> var9 & 0x1);
/* 121:133 */         var8 += (this.field_149262_d[var6] >> var9 & 0x1);
/* 122:    */       }
/* 123:136 */       var9 = 8192 * var7 + 256;
/* 124:137 */       var9 += 2048 * var8;
/* 125:139 */       if (this.field_149267_h) {
/* 126:141 */         var9 += 2048 * var7;
/* 127:    */       }
/* 128:144 */       this.field_149260_f[var6] = new byte[var9];
/* 129:145 */       System.arraycopy(var3, var5, this.field_149260_f[var6], 0, var9);
/* 130:146 */       var5 += var9;
/* 131:    */     }
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void writePacketData(PacketBuffer p_148840_1_)
/* 135:    */     throws IOException
/* 136:    */   {
/* 137:155 */     p_148840_1_.writeShort(this.field_149266_a.length);
/* 138:156 */     p_148840_1_.writeInt(this.field_149261_g);
/* 139:157 */     p_148840_1_.writeBoolean(this.field_149267_h);
/* 140:158 */     p_148840_1_.writeBytes(this.field_149263_e, 0, this.field_149261_g);
/* 141:160 */     for (int var2 = 0; var2 < this.field_149266_a.length; var2++)
/* 142:    */     {
/* 143:162 */       p_148840_1_.writeInt(this.field_149266_a[var2]);
/* 144:163 */       p_148840_1_.writeInt(this.field_149264_b[var2]);
/* 145:164 */       p_148840_1_.writeShort((short)(this.field_149265_c[var2] & 0xFFFF));
/* 146:165 */       p_148840_1_.writeShort((short)(this.field_149262_d[var2] & 0xFFFF));
/* 147:    */     }
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void processPacket(INetHandlerPlayClient p_149259_1_)
/* 151:    */   {
/* 152:171 */     p_149259_1_.handleMapChunkBulk(this);
/* 153:    */   }
/* 154:    */   
/* 155:    */   public int func_149255_a(int p_149255_1_)
/* 156:    */   {
/* 157:176 */     return this.field_149266_a[p_149255_1_];
/* 158:    */   }
/* 159:    */   
/* 160:    */   public int func_149253_b(int p_149253_1_)
/* 161:    */   {
/* 162:181 */     return this.field_149264_b[p_149253_1_];
/* 163:    */   }
/* 164:    */   
/* 165:    */   public int func_149254_d()
/* 166:    */   {
/* 167:186 */     return this.field_149266_a.length;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public byte[] func_149256_c(int p_149256_1_)
/* 171:    */   {
/* 172:191 */     return this.field_149260_f[p_149256_1_];
/* 173:    */   }
/* 174:    */   
/* 175:    */   public String serialize()
/* 176:    */   {
/* 177:199 */     StringBuilder var1 = new StringBuilder();
/* 178:201 */     for (int var2 = 0; var2 < this.field_149266_a.length; var2++)
/* 179:    */     {
/* 180:203 */       if (var2 > 0) {
/* 181:205 */         var1.append(", ");
/* 182:    */       }
/* 183:208 */       var1.append(String.format("{x=%d, z=%d, sections=%d, adds=%d, data=%d}", new Object[] { Integer.valueOf(this.field_149266_a[var2]), Integer.valueOf(this.field_149264_b[var2]), Integer.valueOf(this.field_149265_c[var2]), Integer.valueOf(this.field_149262_d[var2]), Integer.valueOf(this.field_149260_f[var2].length) }));
/* 184:    */     }
/* 185:211 */     return String.format("size=%d, chunks=%d[%s]", new Object[] { Integer.valueOf(this.field_149261_g), Integer.valueOf(this.field_149266_a.length), var1 });
/* 186:    */   }
/* 187:    */   
/* 188:    */   public int[] func_149252_e()
/* 189:    */   {
/* 190:216 */     return this.field_149265_c;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public int[] func_149257_f()
/* 194:    */   {
/* 195:221 */     return this.field_149262_d;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public void processPacket(INetHandler p_148833_1_)
/* 199:    */   {
/* 200:226 */     processPacket((INetHandlerPlayClient)p_148833_1_);
/* 201:    */   }
/* 202:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.server.S26PacketMapChunkBulk
 * JD-Core Version:    0.7.0.1
 */