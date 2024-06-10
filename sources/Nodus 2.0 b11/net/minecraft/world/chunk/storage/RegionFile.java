/*   1:    */ package net.minecraft.world.chunk.storage;
/*   2:    */ 
/*   3:    */ import java.io.BufferedInputStream;
/*   4:    */ import java.io.ByteArrayInputStream;
/*   5:    */ import java.io.ByteArrayOutputStream;
/*   6:    */ import java.io.DataInputStream;
/*   7:    */ import java.io.DataOutputStream;
/*   8:    */ import java.io.File;
/*   9:    */ import java.io.IOException;
/*  10:    */ import java.io.RandomAccessFile;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import java.util.zip.DeflaterOutputStream;
/*  13:    */ import java.util.zip.GZIPInputStream;
/*  14:    */ import java.util.zip.InflaterInputStream;
/*  15:    */ import net.minecraft.server.MinecraftServer;
/*  16:    */ 
/*  17:    */ public class RegionFile
/*  18:    */ {
/*  19: 20 */   private static final byte[] emptySector = new byte[4096];
/*  20:    */   private final File fileName;
/*  21:    */   private RandomAccessFile dataFile;
/*  22: 23 */   private final int[] offsets = new int[1024];
/*  23: 24 */   private final int[] chunkTimestamps = new int[1024];
/*  24:    */   private ArrayList sectorFree;
/*  25:    */   private int sizeDelta;
/*  26:    */   private long lastModified;
/*  27:    */   private static final String __OBFID = "CL_00000381";
/*  28:    */   
/*  29:    */   public RegionFile(File par1File)
/*  30:    */   {
/*  31: 34 */     this.fileName = par1File;
/*  32: 35 */     this.sizeDelta = 0;
/*  33:    */     try
/*  34:    */     {
/*  35: 39 */       if (par1File.exists()) {
/*  36: 41 */         this.lastModified = par1File.lastModified();
/*  37:    */       }
/*  38: 44 */       this.dataFile = new RandomAccessFile(par1File, "rw");
/*  39: 47 */       if (this.dataFile.length() < 4096L)
/*  40:    */       {
/*  41: 49 */         for (int var2 = 0; var2 < 1024; var2++) {
/*  42: 51 */           this.dataFile.writeInt(0);
/*  43:    */         }
/*  44: 54 */         for (var2 = 0; var2 < 1024; var2++) {
/*  45: 56 */           this.dataFile.writeInt(0);
/*  46:    */         }
/*  47: 59 */         this.sizeDelta += 8192;
/*  48:    */       }
/*  49: 62 */       if ((this.dataFile.length() & 0xFFF) != 0L) {
/*  50: 64 */         for (int var2 = 0; var2 < (this.dataFile.length() & 0xFFF); var2++) {
/*  51: 66 */           this.dataFile.write(0);
/*  52:    */         }
/*  53:    */       }
/*  54: 70 */       int var2 = (int)this.dataFile.length() / 4096;
/*  55: 71 */       this.sectorFree = new ArrayList(var2);
/*  56: 74 */       for (int var3 = 0; var3 < var2; var3++) {
/*  57: 76 */         this.sectorFree.add(Boolean.valueOf(true));
/*  58:    */       }
/*  59: 79 */       this.sectorFree.set(0, Boolean.valueOf(false));
/*  60: 80 */       this.sectorFree.set(1, Boolean.valueOf(false));
/*  61: 81 */       this.dataFile.seek(0L);
/*  62: 84 */       for (var3 = 0; var3 < 1024; var3++)
/*  63:    */       {
/*  64: 86 */         int var4 = this.dataFile.readInt();
/*  65: 87 */         this.offsets[var3] = var4;
/*  66: 89 */         if ((var4 != 0) && ((var4 >> 8) + (var4 & 0xFF) <= this.sectorFree.size())) {
/*  67: 91 */           for (int var5 = 0; var5 < (var4 & 0xFF); var5++) {
/*  68: 93 */             this.sectorFree.set((var4 >> 8) + var5, Boolean.valueOf(false));
/*  69:    */           }
/*  70:    */         }
/*  71:    */       }
/*  72: 98 */       for (var3 = 0; var3 < 1024; var3++)
/*  73:    */       {
/*  74:100 */         int var4 = this.dataFile.readInt();
/*  75:101 */         this.chunkTimestamps[var3] = var4;
/*  76:    */       }
/*  77:    */     }
/*  78:    */     catch (IOException var6)
/*  79:    */     {
/*  80:106 */       var6.printStackTrace();
/*  81:    */     }
/*  82:    */   }
/*  83:    */   
/*  84:    */   public synchronized DataInputStream getChunkDataInputStream(int par1, int par2)
/*  85:    */   {
/*  86:115 */     if (outOfBounds(par1, par2)) {
/*  87:117 */       return null;
/*  88:    */     }
/*  89:    */     try
/*  90:    */     {
/*  91:123 */       int var3 = getOffset(par1, par2);
/*  92:125 */       if (var3 == 0) {
/*  93:127 */         return null;
/*  94:    */       }
/*  95:131 */       int var4 = var3 >> 8;
/*  96:132 */       int var5 = var3 & 0xFF;
/*  97:134 */       if (var4 + var5 > this.sectorFree.size()) {
/*  98:136 */         return null;
/*  99:    */       }
/* 100:140 */       this.dataFile.seek(var4 * 4096);
/* 101:141 */       int var6 = this.dataFile.readInt();
/* 102:143 */       if (var6 > 4096 * var5) {
/* 103:145 */         return null;
/* 104:    */       }
/* 105:147 */       if (var6 <= 0) {
/* 106:149 */         return null;
/* 107:    */       }
/* 108:153 */       byte var7 = this.dataFile.readByte();
/* 109:156 */       if (var7 == 1)
/* 110:    */       {
/* 111:158 */         byte[] var8 = new byte[var6 - 1];
/* 112:159 */         this.dataFile.read(var8);
/* 113:160 */         return new DataInputStream(new BufferedInputStream(new GZIPInputStream(new ByteArrayInputStream(var8))));
/* 114:    */       }
/* 115:162 */       if (var7 == 2)
/* 116:    */       {
/* 117:164 */         byte[] var8 = new byte[var6 - 1];
/* 118:165 */         this.dataFile.read(var8);
/* 119:166 */         return new DataInputStream(new BufferedInputStream(new InflaterInputStream(new ByteArrayInputStream(var8))));
/* 120:    */       }
/* 121:170 */       return null;
/* 122:    */     }
/* 123:    */     catch (IOException var9) {}
/* 124:178 */     return null;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public DataOutputStream getChunkDataOutputStream(int par1, int par2)
/* 128:    */   {
/* 129:189 */     return outOfBounds(par1, par2) ? null : new DataOutputStream(new DeflaterOutputStream(new ChunkBuffer(par1, par2)));
/* 130:    */   }
/* 131:    */   
/* 132:    */   protected synchronized void write(int par1, int par2, byte[] par3ArrayOfByte, int par4)
/* 133:    */   {
/* 134:    */     try
/* 135:    */     {
/* 136:199 */       int var5 = getOffset(par1, par2);
/* 137:200 */       int var6 = var5 >> 8;
/* 138:201 */       int var7 = var5 & 0xFF;
/* 139:202 */       int var8 = (par4 + 5) / 4096 + 1;
/* 140:204 */       if (var8 >= 256) {
/* 141:206 */         return;
/* 142:    */       }
/* 143:209 */       if ((var6 != 0) && (var7 == var8))
/* 144:    */       {
/* 145:211 */         write(var6, par3ArrayOfByte, par4);
/* 146:    */       }
/* 147:    */       else
/* 148:    */       {
/* 149:217 */         for (int var9 = 0; var9 < var7; var9++) {
/* 150:219 */           this.sectorFree.set(var6 + var9, Boolean.valueOf(true));
/* 151:    */         }
/* 152:222 */         var9 = this.sectorFree.indexOf(Boolean.valueOf(true));
/* 153:223 */         int var10 = 0;
/* 154:226 */         if (var9 != -1) {
/* 155:228 */           for (int var11 = var9; var11 < this.sectorFree.size(); var11++)
/* 156:    */           {
/* 157:230 */             if (var10 != 0)
/* 158:    */             {
/* 159:232 */               if (((Boolean)this.sectorFree.get(var11)).booleanValue()) {
/* 160:234 */                 var10++;
/* 161:    */               } else {
/* 162:238 */                 var10 = 0;
/* 163:    */               }
/* 164:    */             }
/* 165:241 */             else if (((Boolean)this.sectorFree.get(var11)).booleanValue())
/* 166:    */             {
/* 167:243 */               var9 = var11;
/* 168:244 */               var10 = 1;
/* 169:    */             }
/* 170:247 */             if (var10 >= var8) {
/* 171:    */               break;
/* 172:    */             }
/* 173:    */           }
/* 174:    */         }
/* 175:254 */         if (var10 >= var8)
/* 176:    */         {
/* 177:256 */           var6 = var9;
/* 178:257 */           setOffset(par1, par2, var9 << 8 | var8);
/* 179:259 */           for (int var11 = 0; var11 < var8; var11++) {
/* 180:261 */             this.sectorFree.set(var6 + var11, Boolean.valueOf(false));
/* 181:    */           }
/* 182:264 */           write(var6, par3ArrayOfByte, par4);
/* 183:    */         }
/* 184:    */         else
/* 185:    */         {
/* 186:268 */           this.dataFile.seek(this.dataFile.length());
/* 187:269 */           var6 = this.sectorFree.size();
/* 188:271 */           for (int var11 = 0; var11 < var8; var11++)
/* 189:    */           {
/* 190:273 */             this.dataFile.write(emptySector);
/* 191:274 */             this.sectorFree.add(Boolean.valueOf(false));
/* 192:    */           }
/* 193:277 */           this.sizeDelta += 4096 * var8;
/* 194:278 */           write(var6, par3ArrayOfByte, par4);
/* 195:279 */           setOffset(par1, par2, var6 << 8 | var8);
/* 196:    */         }
/* 197:    */       }
/* 198:283 */       setChunkTimestamp(par1, par2, (int)(MinecraftServer.getSystemTimeMillis() / 1000L));
/* 199:    */     }
/* 200:    */     catch (IOException var12)
/* 201:    */     {
/* 202:287 */       var12.printStackTrace();
/* 203:    */     }
/* 204:    */   }
/* 205:    */   
/* 206:    */   private void write(int par1, byte[] par2ArrayOfByte, int par3)
/* 207:    */     throws IOException
/* 208:    */   {
/* 209:296 */     this.dataFile.seek(par1 * 4096);
/* 210:297 */     this.dataFile.writeInt(par3 + 1);
/* 211:298 */     this.dataFile.writeByte(2);
/* 212:299 */     this.dataFile.write(par2ArrayOfByte, 0, par3);
/* 213:    */   }
/* 214:    */   
/* 215:    */   private boolean outOfBounds(int par1, int par2)
/* 216:    */   {
/* 217:307 */     return (par1 < 0) || (par1 >= 32) || (par2 < 0) || (par2 >= 32);
/* 218:    */   }
/* 219:    */   
/* 220:    */   private int getOffset(int par1, int par2)
/* 221:    */   {
/* 222:315 */     return this.offsets[(par1 + par2 * 32)];
/* 223:    */   }
/* 224:    */   
/* 225:    */   public boolean isChunkSaved(int par1, int par2)
/* 226:    */   {
/* 227:323 */     return getOffset(par1, par2) != 0;
/* 228:    */   }
/* 229:    */   
/* 230:    */   private void setOffset(int par1, int par2, int par3)
/* 231:    */     throws IOException
/* 232:    */   {
/* 233:331 */     this.offsets[(par1 + par2 * 32)] = par3;
/* 234:332 */     this.dataFile.seek((par1 + par2 * 32) * 4);
/* 235:333 */     this.dataFile.writeInt(par3);
/* 236:    */   }
/* 237:    */   
/* 238:    */   private void setChunkTimestamp(int par1, int par2, int par3)
/* 239:    */     throws IOException
/* 240:    */   {
/* 241:341 */     this.chunkTimestamps[(par1 + par2 * 32)] = par3;
/* 242:342 */     this.dataFile.seek(4096 + (par1 + par2 * 32) * 4);
/* 243:343 */     this.dataFile.writeInt(par3);
/* 244:    */   }
/* 245:    */   
/* 246:    */   public void close()
/* 247:    */     throws IOException
/* 248:    */   {
/* 249:351 */     if (this.dataFile != null) {
/* 250:353 */       this.dataFile.close();
/* 251:    */     }
/* 252:    */   }
/* 253:    */   
/* 254:    */   class ChunkBuffer
/* 255:    */     extends ByteArrayOutputStream
/* 256:    */   {
/* 257:    */     private int chunkX;
/* 258:    */     private int chunkZ;
/* 259:    */     private static final String __OBFID = "CL_00000382";
/* 260:    */     
/* 261:    */     public ChunkBuffer(int par2, int par3)
/* 262:    */     {
/* 263:365 */       super();
/* 264:366 */       this.chunkX = par2;
/* 265:367 */       this.chunkZ = par3;
/* 266:    */     }
/* 267:    */     
/* 268:    */     public void close()
/* 269:    */       throws IOException
/* 270:    */     {
/* 271:372 */       RegionFile.this.write(this.chunkX, this.chunkZ, this.buf, this.count);
/* 272:    */     }
/* 273:    */   }
/* 274:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.chunk.storage.RegionFile
 * JD-Core Version:    0.7.0.1
 */