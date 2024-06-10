/*   1:    */ package net.minecraft.network.play.server;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.zip.DataFormatException;
/*   5:    */ import java.util.zip.Deflater;
/*   6:    */ import java.util.zip.Inflater;
/*   7:    */ import net.minecraft.network.INetHandler;
/*   8:    */ import net.minecraft.network.Packet;
/*   9:    */ import net.minecraft.network.PacketBuffer;
/*  10:    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*  11:    */ import net.minecraft.world.World;
/*  12:    */ import net.minecraft.world.WorldProvider;
/*  13:    */ import net.minecraft.world.chunk.Chunk;
/*  14:    */ import net.minecraft.world.chunk.NibbleArray;
/*  15:    */ import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
/*  16:    */ 
/*  17:    */ public class S21PacketChunkData
/*  18:    */   extends Packet
/*  19:    */ {
/*  20:    */   private int field_149284_a;
/*  21:    */   private int field_149282_b;
/*  22:    */   private int field_149283_c;
/*  23:    */   private int field_149280_d;
/*  24:    */   private byte[] field_149281_e;
/*  25:    */   private byte[] field_149278_f;
/*  26:    */   private boolean field_149279_g;
/*  27:    */   private int field_149285_h;
/*  28: 25 */   private static byte[] field_149286_i = new byte[196864];
/*  29:    */   private static final String __OBFID = "CL_00001304";
/*  30:    */   
/*  31:    */   public S21PacketChunkData() {}
/*  32:    */   
/*  33:    */   public S21PacketChunkData(Chunk p_i45196_1_, boolean p_i45196_2_, int p_i45196_3_)
/*  34:    */   {
/*  35: 32 */     this.field_149284_a = p_i45196_1_.xPosition;
/*  36: 33 */     this.field_149282_b = p_i45196_1_.zPosition;
/*  37: 34 */     this.field_149279_g = p_i45196_2_;
/*  38: 35 */     Extracted var4 = func_149269_a(p_i45196_1_, p_i45196_2_, p_i45196_3_);
/*  39: 36 */     Deflater var5 = new Deflater(-1);
/*  40: 37 */     this.field_149280_d = var4.field_150281_c;
/*  41: 38 */     this.field_149283_c = var4.field_150280_b;
/*  42:    */     try
/*  43:    */     {
/*  44: 42 */       this.field_149278_f = var4.field_150282_a;
/*  45: 43 */       var5.setInput(var4.field_150282_a, 0, var4.field_150282_a.length);
/*  46: 44 */       var5.finish();
/*  47: 45 */       this.field_149281_e = new byte[var4.field_150282_a.length];
/*  48: 46 */       this.field_149285_h = var5.deflate(this.field_149281_e);
/*  49:    */     }
/*  50:    */     finally
/*  51:    */     {
/*  52: 50 */       var5.end();
/*  53:    */     }
/*  54:    */   }
/*  55:    */   
/*  56:    */   public static int func_149275_c()
/*  57:    */   {
/*  58: 56 */     return 196864;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void readPacketData(PacketBuffer p_148837_1_)
/*  62:    */     throws IOException
/*  63:    */   {
/*  64: 64 */     this.field_149284_a = p_148837_1_.readInt();
/*  65: 65 */     this.field_149282_b = p_148837_1_.readInt();
/*  66: 66 */     this.field_149279_g = p_148837_1_.readBoolean();
/*  67: 67 */     this.field_149283_c = p_148837_1_.readShort();
/*  68: 68 */     this.field_149280_d = p_148837_1_.readShort();
/*  69: 69 */     this.field_149285_h = p_148837_1_.readInt();
/*  70: 71 */     if (field_149286_i.length < this.field_149285_h) {
/*  71: 73 */       field_149286_i = new byte[this.field_149285_h];
/*  72:    */     }
/*  73: 76 */     p_148837_1_.readBytes(field_149286_i, 0, this.field_149285_h);
/*  74: 77 */     int var2 = 0;
/*  75: 80 */     for (int var3 = 0; var3 < 16; var3++) {
/*  76: 82 */       var2 += (this.field_149283_c >> var3 & 0x1);
/*  77:    */     }
/*  78: 85 */     var3 = 12288 * var2;
/*  79: 87 */     if (this.field_149279_g) {
/*  80: 89 */       var3 += 256;
/*  81:    */     }
/*  82: 92 */     this.field_149278_f = new byte[var3];
/*  83: 93 */     Inflater var4 = new Inflater();
/*  84: 94 */     var4.setInput(field_149286_i, 0, this.field_149285_h);
/*  85:    */     try
/*  86:    */     {
/*  87: 98 */       var4.inflate(this.field_149278_f);
/*  88:    */     }
/*  89:    */     catch (DataFormatException var9)
/*  90:    */     {
/*  91:102 */       throw new IOException("Bad compressed data format");
/*  92:    */     }
/*  93:    */     finally
/*  94:    */     {
/*  95:106 */       var4.end();
/*  96:    */     }
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void writePacketData(PacketBuffer p_148840_1_)
/* 100:    */     throws IOException
/* 101:    */   {
/* 102:115 */     p_148840_1_.writeInt(this.field_149284_a);
/* 103:116 */     p_148840_1_.writeInt(this.field_149282_b);
/* 104:117 */     p_148840_1_.writeBoolean(this.field_149279_g);
/* 105:118 */     p_148840_1_.writeShort((short)(this.field_149283_c & 0xFFFF));
/* 106:119 */     p_148840_1_.writeShort((short)(this.field_149280_d & 0xFFFF));
/* 107:120 */     p_148840_1_.writeInt(this.field_149285_h);
/* 108:121 */     p_148840_1_.writeBytes(this.field_149281_e, 0, this.field_149285_h);
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void processPacket(INetHandlerPlayClient p_149277_1_)
/* 112:    */   {
/* 113:126 */     p_149277_1_.handleChunkData(this);
/* 114:    */   }
/* 115:    */   
/* 116:    */   public String serialize()
/* 117:    */   {
/* 118:134 */     return String.format("x=%d, z=%d, full=%b, sects=%d, add=%d, size=%d", new Object[] { Integer.valueOf(this.field_149284_a), Integer.valueOf(this.field_149282_b), Boolean.valueOf(this.field_149279_g), Integer.valueOf(this.field_149283_c), Integer.valueOf(this.field_149280_d), Integer.valueOf(this.field_149285_h) });
/* 119:    */   }
/* 120:    */   
/* 121:    */   public byte[] func_149272_d()
/* 122:    */   {
/* 123:139 */     return this.field_149278_f;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public static Extracted func_149269_a(Chunk p_149269_0_, boolean p_149269_1_, int p_149269_2_)
/* 127:    */   {
/* 128:144 */     int var3 = 0;
/* 129:145 */     ExtendedBlockStorage[] var4 = p_149269_0_.getBlockStorageArray();
/* 130:146 */     int var5 = 0;
/* 131:147 */     Extracted var6 = new Extracted();
/* 132:148 */     byte[] var7 = field_149286_i;
/* 133:150 */     if (p_149269_1_) {
/* 134:152 */       p_149269_0_.sendUpdates = true;
/* 135:    */     }
/* 136:157 */     for (int var8 = 0; var8 < var4.length; var8++) {
/* 137:159 */       if ((var4[var8] != null) && ((!p_149269_1_) || (!var4[var8].isEmpty())) && ((p_149269_2_ & 1 << var8) != 0))
/* 138:    */       {
/* 139:161 */         var6.field_150280_b |= 1 << var8;
/* 140:163 */         if (var4[var8].getBlockMSBArray() != null)
/* 141:    */         {
/* 142:165 */           var6.field_150281_c |= 1 << var8;
/* 143:166 */           var5++;
/* 144:    */         }
/* 145:    */       }
/* 146:    */     }
/* 147:171 */     for (var8 = 0; var8 < var4.length; var8++) {
/* 148:173 */       if ((var4[var8] != null) && ((!p_149269_1_) || (!var4[var8].isEmpty())) && ((p_149269_2_ & 1 << var8) != 0))
/* 149:    */       {
/* 150:175 */         byte[] var9 = var4[var8].getBlockLSBArray();
/* 151:176 */         System.arraycopy(var9, 0, var7, var3, var9.length);
/* 152:177 */         var3 += var9.length;
/* 153:    */       }
/* 154:    */     }
/* 155:183 */     for (var8 = 0; var8 < var4.length; var8++) {
/* 156:185 */       if ((var4[var8] != null) && ((!p_149269_1_) || (!var4[var8].isEmpty())) && ((p_149269_2_ & 1 << var8) != 0))
/* 157:    */       {
/* 158:187 */         NibbleArray var10 = var4[var8].getMetadataArray();
/* 159:188 */         System.arraycopy(var10.data, 0, var7, var3, var10.data.length);
/* 160:189 */         var3 += var10.data.length;
/* 161:    */       }
/* 162:    */     }
/* 163:193 */     for (var8 = 0; var8 < var4.length; var8++) {
/* 164:195 */       if ((var4[var8] != null) && ((!p_149269_1_) || (!var4[var8].isEmpty())) && ((p_149269_2_ & 1 << var8) != 0))
/* 165:    */       {
/* 166:197 */         NibbleArray var10 = var4[var8].getBlocklightArray();
/* 167:198 */         System.arraycopy(var10.data, 0, var7, var3, var10.data.length);
/* 168:199 */         var3 += var10.data.length;
/* 169:    */       }
/* 170:    */     }
/* 171:203 */     if (!p_149269_0_.worldObj.provider.hasNoSky) {
/* 172:205 */       for (var8 = 0; var8 < var4.length; var8++) {
/* 173:207 */         if ((var4[var8] != null) && ((!p_149269_1_) || (!var4[var8].isEmpty())) && ((p_149269_2_ & 1 << var8) != 0))
/* 174:    */         {
/* 175:209 */           NibbleArray var10 = var4[var8].getSkylightArray();
/* 176:210 */           System.arraycopy(var10.data, 0, var7, var3, var10.data.length);
/* 177:211 */           var3 += var10.data.length;
/* 178:    */         }
/* 179:    */       }
/* 180:    */     }
/* 181:216 */     if (var5 > 0) {
/* 182:218 */       for (var8 = 0; var8 < var4.length; var8++) {
/* 183:220 */         if ((var4[var8] != null) && ((!p_149269_1_) || (!var4[var8].isEmpty())) && (var4[var8].getBlockMSBArray() != null) && ((p_149269_2_ & 1 << var8) != 0))
/* 184:    */         {
/* 185:222 */           NibbleArray var10 = var4[var8].getBlockMSBArray();
/* 186:223 */           System.arraycopy(var10.data, 0, var7, var3, var10.data.length);
/* 187:224 */           var3 += var10.data.length;
/* 188:    */         }
/* 189:    */       }
/* 190:    */     }
/* 191:229 */     if (p_149269_1_)
/* 192:    */     {
/* 193:231 */       byte[] var11 = p_149269_0_.getBiomeArray();
/* 194:232 */       System.arraycopy(var11, 0, var7, var3, var11.length);
/* 195:233 */       var3 += var11.length;
/* 196:    */     }
/* 197:236 */     var6.field_150282_a = new byte[var3];
/* 198:237 */     System.arraycopy(var7, 0, var6.field_150282_a, 0, var3);
/* 199:238 */     return var6;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public int func_149273_e()
/* 203:    */   {
/* 204:243 */     return this.field_149284_a;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public int func_149271_f()
/* 208:    */   {
/* 209:248 */     return this.field_149282_b;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public int func_149276_g()
/* 213:    */   {
/* 214:253 */     return this.field_149283_c;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public int func_149270_h()
/* 218:    */   {
/* 219:258 */     return this.field_149280_d;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public boolean func_149274_i()
/* 223:    */   {
/* 224:263 */     return this.field_149279_g;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public void processPacket(INetHandler p_148833_1_)
/* 228:    */   {
/* 229:268 */     processPacket((INetHandlerPlayClient)p_148833_1_);
/* 230:    */   }
/* 231:    */   
/* 232:    */   public static class Extracted
/* 233:    */   {
/* 234:    */     public byte[] field_150282_a;
/* 235:    */     public int field_150280_b;
/* 236:    */     public int field_150281_c;
/* 237:    */     private static final String __OBFID = "CL_00001305";
/* 238:    */   }
/* 239:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.server.S21PacketChunkData
 * JD-Core Version:    0.7.0.1
 */