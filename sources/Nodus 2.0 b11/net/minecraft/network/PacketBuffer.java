/*   1:    */ package net.minecraft.network;
/*   2:    */ 
/*   3:    */ import com.google.common.base.Charsets;
/*   4:    */ import io.netty.buffer.ByteBuf;
/*   5:    */ import io.netty.buffer.ByteBufAllocator;
/*   6:    */ import io.netty.buffer.ByteBufProcessor;
/*   7:    */ import java.io.IOException;
/*   8:    */ import java.io.InputStream;
/*   9:    */ import java.io.OutputStream;
/*  10:    */ import java.nio.ByteBuffer;
/*  11:    */ import java.nio.ByteOrder;
/*  12:    */ import java.nio.channels.GatheringByteChannel;
/*  13:    */ import java.nio.channels.ScatteringByteChannel;
/*  14:    */ import java.nio.charset.Charset;
/*  15:    */ import net.minecraft.item.Item;
/*  16:    */ import net.minecraft.item.ItemStack;
/*  17:    */ import net.minecraft.nbt.CompressedStreamTools;
/*  18:    */ import net.minecraft.nbt.NBTTagCompound;
/*  19:    */ 
/*  20:    */ public class PacketBuffer
/*  21:    */   extends ByteBuf
/*  22:    */ {
/*  23:    */   private final ByteBuf field_150794_a;
/*  24:    */   private static final String __OBFID = "CL_00001251";
/*  25:    */   
/*  26:    */   public PacketBuffer(ByteBuf p_i45154_1_)
/*  27:    */   {
/*  28: 28 */     this.field_150794_a = p_i45154_1_;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public static int getVarIntSize(int p_150790_0_)
/*  32:    */   {
/*  33: 37 */     return (p_150790_0_ & 0xF0000000) == 0 ? 4 : (p_150790_0_ & 0xFFE00000) == 0 ? 3 : (p_150790_0_ & 0xFFFFC000) == 0 ? 2 : (p_150790_0_ & 0xFFFFFF80) == 0 ? 1 : 5;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public int readVarIntFromBuffer()
/*  37:    */   {
/*  38: 46 */     int var1 = 0;
/*  39: 47 */     int var2 = 0;
/*  40:    */     byte var3;
/*  41:    */     do
/*  42:    */     {
/*  43: 52 */       var3 = readByte();
/*  44: 53 */       var1 |= (var3 & 0x7F) << var2++ * 7;
/*  45: 55 */       if (var2 > 5) {
/*  46: 57 */         throw new RuntimeException("VarInt too big");
/*  47:    */       }
/*  48: 60 */     } while ((var3 & 0x80) == 128);
/*  49: 62 */     return var1;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void writeVarIntToBuffer(int p_150787_1_)
/*  53:    */   {
/*  54: 73 */     while ((p_150787_1_ & 0xFFFFFF80) != 0)
/*  55:    */     {
/*  56: 75 */       writeByte(p_150787_1_ & 0x7F | 0x80);
/*  57: 76 */       p_150787_1_ >>>= 7;
/*  58:    */     }
/*  59: 79 */     writeByte(p_150787_1_);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void writeNBTTagCompoundToBuffer(NBTTagCompound p_150786_1_)
/*  63:    */     throws IOException
/*  64:    */   {
/*  65: 87 */     if (p_150786_1_ == null)
/*  66:    */     {
/*  67: 89 */       writeShort(-1);
/*  68:    */     }
/*  69:    */     else
/*  70:    */     {
/*  71: 93 */       byte[] var2 = CompressedStreamTools.compress(p_150786_1_);
/*  72: 94 */       writeShort((short)var2.length);
/*  73: 95 */       writeBytes(var2);
/*  74:    */     }
/*  75:    */   }
/*  76:    */   
/*  77:    */   public NBTTagCompound readNBTTagCompoundFromBuffer()
/*  78:    */     throws IOException
/*  79:    */   {
/*  80:104 */     short var1 = readShort();
/*  81:106 */     if (var1 < 0) {
/*  82:108 */       return null;
/*  83:    */     }
/*  84:112 */     byte[] var2 = new byte[var1];
/*  85:113 */     readBytes(var2);
/*  86:114 */     return CompressedStreamTools.decompress(var2);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void writeItemStackToBuffer(ItemStack p_150788_1_)
/*  90:    */     throws IOException
/*  91:    */   {
/*  92:123 */     if (p_150788_1_ == null)
/*  93:    */     {
/*  94:125 */       writeShort(-1);
/*  95:    */     }
/*  96:    */     else
/*  97:    */     {
/*  98:129 */       writeShort(Item.getIdFromItem(p_150788_1_.getItem()));
/*  99:130 */       writeByte(p_150788_1_.stackSize);
/* 100:131 */       writeShort(p_150788_1_.getItemDamage());
/* 101:132 */       NBTTagCompound var2 = null;
/* 102:134 */       if ((p_150788_1_.getItem().isDamageable()) || (p_150788_1_.getItem().getShareTag())) {
/* 103:136 */         var2 = p_150788_1_.stackTagCompound;
/* 104:    */       }
/* 105:139 */       writeNBTTagCompoundToBuffer(var2);
/* 106:    */     }
/* 107:    */   }
/* 108:    */   
/* 109:    */   public ItemStack readItemStackFromBuffer()
/* 110:    */     throws IOException
/* 111:    */   {
/* 112:148 */     ItemStack var1 = null;
/* 113:149 */     short var2 = readShort();
/* 114:151 */     if (var2 >= 0)
/* 115:    */     {
/* 116:153 */       byte var3 = readByte();
/* 117:154 */       short var4 = readShort();
/* 118:155 */       var1 = new ItemStack(Item.getItemById(var2), var3, var4);
/* 119:156 */       var1.stackTagCompound = readNBTTagCompoundFromBuffer();
/* 120:    */     }
/* 121:159 */     return var1;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public String readStringFromBuffer(int p_150789_1_)
/* 125:    */     throws IOException
/* 126:    */   {
/* 127:168 */     int var2 = readVarIntFromBuffer();
/* 128:170 */     if (var2 > p_150789_1_ * 4) {
/* 129:172 */       throw new IOException("The received encoded string buffer length is longer than maximum allowed (" + var2 + " > " + p_150789_1_ * 4 + ")");
/* 130:    */     }
/* 131:174 */     if (var2 < 0) {
/* 132:176 */       throw new IOException("The received encoded string buffer length is less than zero! Weird string!");
/* 133:    */     }
/* 134:180 */     String var3 = new String(readBytes(var2).array(), Charsets.UTF_8);
/* 135:182 */     if (var3.length() > p_150789_1_) {
/* 136:184 */       throw new IOException("The received string length is longer than maximum allowed (" + var2 + " > " + p_150789_1_ + ")");
/* 137:    */     }
/* 138:188 */     return var3;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void writeStringToBuffer(String p_150785_1_)
/* 142:    */     throws IOException
/* 143:    */   {
/* 144:198 */     byte[] var2 = p_150785_1_.getBytes(Charsets.UTF_8);
/* 145:200 */     if (var2.length > 32767) {
/* 146:202 */       throw new IOException("String too big (was " + p_150785_1_.length() + " bytes encoded, max " + 32767 + ")");
/* 147:    */     }
/* 148:206 */     writeVarIntToBuffer(var2.length);
/* 149:207 */     writeBytes(var2);
/* 150:    */   }
/* 151:    */   
/* 152:    */   public int capacity()
/* 153:    */   {
/* 154:213 */     return this.field_150794_a.capacity();
/* 155:    */   }
/* 156:    */   
/* 157:    */   public ByteBuf capacity(int p_capacity_1_)
/* 158:    */   {
/* 159:218 */     return this.field_150794_a.capacity(p_capacity_1_);
/* 160:    */   }
/* 161:    */   
/* 162:    */   public int maxCapacity()
/* 163:    */   {
/* 164:223 */     return this.field_150794_a.maxCapacity();
/* 165:    */   }
/* 166:    */   
/* 167:    */   public ByteBufAllocator alloc()
/* 168:    */   {
/* 169:228 */     return this.field_150794_a.alloc();
/* 170:    */   }
/* 171:    */   
/* 172:    */   public ByteOrder order()
/* 173:    */   {
/* 174:233 */     return this.field_150794_a.order();
/* 175:    */   }
/* 176:    */   
/* 177:    */   public ByteBuf order(ByteOrder p_order_1_)
/* 178:    */   {
/* 179:238 */     return this.field_150794_a.order(p_order_1_);
/* 180:    */   }
/* 181:    */   
/* 182:    */   public ByteBuf unwrap()
/* 183:    */   {
/* 184:243 */     return this.field_150794_a.unwrap();
/* 185:    */   }
/* 186:    */   
/* 187:    */   public boolean isDirect()
/* 188:    */   {
/* 189:248 */     return this.field_150794_a.isDirect();
/* 190:    */   }
/* 191:    */   
/* 192:    */   public int readerIndex()
/* 193:    */   {
/* 194:253 */     return this.field_150794_a.readerIndex();
/* 195:    */   }
/* 196:    */   
/* 197:    */   public ByteBuf readerIndex(int p_readerIndex_1_)
/* 198:    */   {
/* 199:258 */     return this.field_150794_a.readerIndex(p_readerIndex_1_);
/* 200:    */   }
/* 201:    */   
/* 202:    */   public int writerIndex()
/* 203:    */   {
/* 204:263 */     return this.field_150794_a.writerIndex();
/* 205:    */   }
/* 206:    */   
/* 207:    */   public ByteBuf writerIndex(int p_writerIndex_1_)
/* 208:    */   {
/* 209:268 */     return this.field_150794_a.writerIndex(p_writerIndex_1_);
/* 210:    */   }
/* 211:    */   
/* 212:    */   public ByteBuf setIndex(int p_setIndex_1_, int p_setIndex_2_)
/* 213:    */   {
/* 214:273 */     return this.field_150794_a.setIndex(p_setIndex_1_, p_setIndex_2_);
/* 215:    */   }
/* 216:    */   
/* 217:    */   public int readableBytes()
/* 218:    */   {
/* 219:278 */     return this.field_150794_a.readableBytes();
/* 220:    */   }
/* 221:    */   
/* 222:    */   public int writableBytes()
/* 223:    */   {
/* 224:283 */     return this.field_150794_a.writableBytes();
/* 225:    */   }
/* 226:    */   
/* 227:    */   public int maxWritableBytes()
/* 228:    */   {
/* 229:288 */     return this.field_150794_a.maxWritableBytes();
/* 230:    */   }
/* 231:    */   
/* 232:    */   public boolean isReadable()
/* 233:    */   {
/* 234:293 */     return this.field_150794_a.isReadable();
/* 235:    */   }
/* 236:    */   
/* 237:    */   public boolean isReadable(int p_isReadable_1_)
/* 238:    */   {
/* 239:298 */     return this.field_150794_a.isReadable(p_isReadable_1_);
/* 240:    */   }
/* 241:    */   
/* 242:    */   public boolean isWritable()
/* 243:    */   {
/* 244:303 */     return this.field_150794_a.isWritable();
/* 245:    */   }
/* 246:    */   
/* 247:    */   public boolean isWritable(int p_isWritable_1_)
/* 248:    */   {
/* 249:308 */     return this.field_150794_a.isWritable(p_isWritable_1_);
/* 250:    */   }
/* 251:    */   
/* 252:    */   public ByteBuf clear()
/* 253:    */   {
/* 254:313 */     return this.field_150794_a.clear();
/* 255:    */   }
/* 256:    */   
/* 257:    */   public ByteBuf markReaderIndex()
/* 258:    */   {
/* 259:318 */     return this.field_150794_a.markReaderIndex();
/* 260:    */   }
/* 261:    */   
/* 262:    */   public ByteBuf resetReaderIndex()
/* 263:    */   {
/* 264:323 */     return this.field_150794_a.resetReaderIndex();
/* 265:    */   }
/* 266:    */   
/* 267:    */   public ByteBuf markWriterIndex()
/* 268:    */   {
/* 269:328 */     return this.field_150794_a.markWriterIndex();
/* 270:    */   }
/* 271:    */   
/* 272:    */   public ByteBuf resetWriterIndex()
/* 273:    */   {
/* 274:333 */     return this.field_150794_a.resetWriterIndex();
/* 275:    */   }
/* 276:    */   
/* 277:    */   public ByteBuf discardReadBytes()
/* 278:    */   {
/* 279:338 */     return this.field_150794_a.discardReadBytes();
/* 280:    */   }
/* 281:    */   
/* 282:    */   public ByteBuf discardSomeReadBytes()
/* 283:    */   {
/* 284:343 */     return this.field_150794_a.discardSomeReadBytes();
/* 285:    */   }
/* 286:    */   
/* 287:    */   public ByteBuf ensureWritable(int p_ensureWritable_1_)
/* 288:    */   {
/* 289:348 */     return this.field_150794_a.ensureWritable(p_ensureWritable_1_);
/* 290:    */   }
/* 291:    */   
/* 292:    */   public int ensureWritable(int p_ensureWritable_1_, boolean p_ensureWritable_2_)
/* 293:    */   {
/* 294:353 */     return this.field_150794_a.ensureWritable(p_ensureWritable_1_, p_ensureWritable_2_);
/* 295:    */   }
/* 296:    */   
/* 297:    */   public boolean getBoolean(int p_getBoolean_1_)
/* 298:    */   {
/* 299:358 */     return this.field_150794_a.getBoolean(p_getBoolean_1_);
/* 300:    */   }
/* 301:    */   
/* 302:    */   public byte getByte(int p_getByte_1_)
/* 303:    */   {
/* 304:363 */     return this.field_150794_a.getByte(p_getByte_1_);
/* 305:    */   }
/* 306:    */   
/* 307:    */   public short getUnsignedByte(int p_getUnsignedByte_1_)
/* 308:    */   {
/* 309:368 */     return this.field_150794_a.getUnsignedByte(p_getUnsignedByte_1_);
/* 310:    */   }
/* 311:    */   
/* 312:    */   public short getShort(int p_getShort_1_)
/* 313:    */   {
/* 314:373 */     return this.field_150794_a.getShort(p_getShort_1_);
/* 315:    */   }
/* 316:    */   
/* 317:    */   public int getUnsignedShort(int p_getUnsignedShort_1_)
/* 318:    */   {
/* 319:378 */     return this.field_150794_a.getUnsignedShort(p_getUnsignedShort_1_);
/* 320:    */   }
/* 321:    */   
/* 322:    */   public int getMedium(int p_getMedium_1_)
/* 323:    */   {
/* 324:383 */     return this.field_150794_a.getMedium(p_getMedium_1_);
/* 325:    */   }
/* 326:    */   
/* 327:    */   public int getUnsignedMedium(int p_getUnsignedMedium_1_)
/* 328:    */   {
/* 329:388 */     return this.field_150794_a.getUnsignedMedium(p_getUnsignedMedium_1_);
/* 330:    */   }
/* 331:    */   
/* 332:    */   public int getInt(int p_getInt_1_)
/* 333:    */   {
/* 334:393 */     return this.field_150794_a.getInt(p_getInt_1_);
/* 335:    */   }
/* 336:    */   
/* 337:    */   public long getUnsignedInt(int p_getUnsignedInt_1_)
/* 338:    */   {
/* 339:398 */     return this.field_150794_a.getUnsignedInt(p_getUnsignedInt_1_);
/* 340:    */   }
/* 341:    */   
/* 342:    */   public long getLong(int p_getLong_1_)
/* 343:    */   {
/* 344:403 */     return this.field_150794_a.getLong(p_getLong_1_);
/* 345:    */   }
/* 346:    */   
/* 347:    */   public char getChar(int p_getChar_1_)
/* 348:    */   {
/* 349:408 */     return this.field_150794_a.getChar(p_getChar_1_);
/* 350:    */   }
/* 351:    */   
/* 352:    */   public float getFloat(int p_getFloat_1_)
/* 353:    */   {
/* 354:413 */     return this.field_150794_a.getFloat(p_getFloat_1_);
/* 355:    */   }
/* 356:    */   
/* 357:    */   public double getDouble(int p_getDouble_1_)
/* 358:    */   {
/* 359:418 */     return this.field_150794_a.getDouble(p_getDouble_1_);
/* 360:    */   }
/* 361:    */   
/* 362:    */   public ByteBuf getBytes(int p_getBytes_1_, ByteBuf p_getBytes_2_)
/* 363:    */   {
/* 364:423 */     return this.field_150794_a.getBytes(p_getBytes_1_, p_getBytes_2_);
/* 365:    */   }
/* 366:    */   
/* 367:    */   public ByteBuf getBytes(int p_getBytes_1_, ByteBuf p_getBytes_2_, int p_getBytes_3_)
/* 368:    */   {
/* 369:428 */     return this.field_150794_a.getBytes(p_getBytes_1_, p_getBytes_2_, p_getBytes_3_);
/* 370:    */   }
/* 371:    */   
/* 372:    */   public ByteBuf getBytes(int p_getBytes_1_, ByteBuf p_getBytes_2_, int p_getBytes_3_, int p_getBytes_4_)
/* 373:    */   {
/* 374:433 */     return this.field_150794_a.getBytes(p_getBytes_1_, p_getBytes_2_, p_getBytes_3_, p_getBytes_4_);
/* 375:    */   }
/* 376:    */   
/* 377:    */   public ByteBuf getBytes(int p_getBytes_1_, byte[] p_getBytes_2_)
/* 378:    */   {
/* 379:438 */     return this.field_150794_a.getBytes(p_getBytes_1_, p_getBytes_2_);
/* 380:    */   }
/* 381:    */   
/* 382:    */   public ByteBuf getBytes(int p_getBytes_1_, byte[] p_getBytes_2_, int p_getBytes_3_, int p_getBytes_4_)
/* 383:    */   {
/* 384:443 */     return this.field_150794_a.getBytes(p_getBytes_1_, p_getBytes_2_, p_getBytes_3_, p_getBytes_4_);
/* 385:    */   }
/* 386:    */   
/* 387:    */   public ByteBuf getBytes(int p_getBytes_1_, ByteBuffer p_getBytes_2_)
/* 388:    */   {
/* 389:448 */     return this.field_150794_a.getBytes(p_getBytes_1_, p_getBytes_2_);
/* 390:    */   }
/* 391:    */   
/* 392:    */   public ByteBuf getBytes(int p_getBytes_1_, OutputStream p_getBytes_2_, int p_getBytes_3_)
/* 393:    */     throws IOException
/* 394:    */   {
/* 395:453 */     return this.field_150794_a.getBytes(p_getBytes_1_, p_getBytes_2_, p_getBytes_3_);
/* 396:    */   }
/* 397:    */   
/* 398:    */   public int getBytes(int p_getBytes_1_, GatheringByteChannel p_getBytes_2_, int p_getBytes_3_)
/* 399:    */     throws IOException
/* 400:    */   {
/* 401:458 */     return this.field_150794_a.getBytes(p_getBytes_1_, p_getBytes_2_, p_getBytes_3_);
/* 402:    */   }
/* 403:    */   
/* 404:    */   public ByteBuf setBoolean(int p_setBoolean_1_, boolean p_setBoolean_2_)
/* 405:    */   {
/* 406:463 */     return this.field_150794_a.setBoolean(p_setBoolean_1_, p_setBoolean_2_);
/* 407:    */   }
/* 408:    */   
/* 409:    */   public ByteBuf setByte(int p_setByte_1_, int p_setByte_2_)
/* 410:    */   {
/* 411:468 */     return this.field_150794_a.setByte(p_setByte_1_, p_setByte_2_);
/* 412:    */   }
/* 413:    */   
/* 414:    */   public ByteBuf setShort(int p_setShort_1_, int p_setShort_2_)
/* 415:    */   {
/* 416:473 */     return this.field_150794_a.setShort(p_setShort_1_, p_setShort_2_);
/* 417:    */   }
/* 418:    */   
/* 419:    */   public ByteBuf setMedium(int p_setMedium_1_, int p_setMedium_2_)
/* 420:    */   {
/* 421:478 */     return this.field_150794_a.setMedium(p_setMedium_1_, p_setMedium_2_);
/* 422:    */   }
/* 423:    */   
/* 424:    */   public ByteBuf setInt(int p_setInt_1_, int p_setInt_2_)
/* 425:    */   {
/* 426:483 */     return this.field_150794_a.setInt(p_setInt_1_, p_setInt_2_);
/* 427:    */   }
/* 428:    */   
/* 429:    */   public ByteBuf setLong(int p_setLong_1_, long p_setLong_2_)
/* 430:    */   {
/* 431:488 */     return this.field_150794_a.setLong(p_setLong_1_, p_setLong_2_);
/* 432:    */   }
/* 433:    */   
/* 434:    */   public ByteBuf setChar(int p_setChar_1_, int p_setChar_2_)
/* 435:    */   {
/* 436:493 */     return this.field_150794_a.setChar(p_setChar_1_, p_setChar_2_);
/* 437:    */   }
/* 438:    */   
/* 439:    */   public ByteBuf setFloat(int p_setFloat_1_, float p_setFloat_2_)
/* 440:    */   {
/* 441:498 */     return this.field_150794_a.setFloat(p_setFloat_1_, p_setFloat_2_);
/* 442:    */   }
/* 443:    */   
/* 444:    */   public ByteBuf setDouble(int p_setDouble_1_, double p_setDouble_2_)
/* 445:    */   {
/* 446:503 */     return this.field_150794_a.setDouble(p_setDouble_1_, p_setDouble_2_);
/* 447:    */   }
/* 448:    */   
/* 449:    */   public ByteBuf setBytes(int p_setBytes_1_, ByteBuf p_setBytes_2_)
/* 450:    */   {
/* 451:508 */     return this.field_150794_a.setBytes(p_setBytes_1_, p_setBytes_2_);
/* 452:    */   }
/* 453:    */   
/* 454:    */   public ByteBuf setBytes(int p_setBytes_1_, ByteBuf p_setBytes_2_, int p_setBytes_3_)
/* 455:    */   {
/* 456:513 */     return this.field_150794_a.setBytes(p_setBytes_1_, p_setBytes_2_, p_setBytes_3_);
/* 457:    */   }
/* 458:    */   
/* 459:    */   public ByteBuf setBytes(int p_setBytes_1_, ByteBuf p_setBytes_2_, int p_setBytes_3_, int p_setBytes_4_)
/* 460:    */   {
/* 461:518 */     return this.field_150794_a.setBytes(p_setBytes_1_, p_setBytes_2_, p_setBytes_3_, p_setBytes_4_);
/* 462:    */   }
/* 463:    */   
/* 464:    */   public ByteBuf setBytes(int p_setBytes_1_, byte[] p_setBytes_2_)
/* 465:    */   {
/* 466:523 */     return this.field_150794_a.setBytes(p_setBytes_1_, p_setBytes_2_);
/* 467:    */   }
/* 468:    */   
/* 469:    */   public ByteBuf setBytes(int p_setBytes_1_, byte[] p_setBytes_2_, int p_setBytes_3_, int p_setBytes_4_)
/* 470:    */   {
/* 471:528 */     return this.field_150794_a.setBytes(p_setBytes_1_, p_setBytes_2_, p_setBytes_3_, p_setBytes_4_);
/* 472:    */   }
/* 473:    */   
/* 474:    */   public ByteBuf setBytes(int p_setBytes_1_, ByteBuffer p_setBytes_2_)
/* 475:    */   {
/* 476:533 */     return this.field_150794_a.setBytes(p_setBytes_1_, p_setBytes_2_);
/* 477:    */   }
/* 478:    */   
/* 479:    */   public int setBytes(int p_setBytes_1_, InputStream p_setBytes_2_, int p_setBytes_3_)
/* 480:    */     throws IOException
/* 481:    */   {
/* 482:538 */     return this.field_150794_a.setBytes(p_setBytes_1_, p_setBytes_2_, p_setBytes_3_);
/* 483:    */   }
/* 484:    */   
/* 485:    */   public int setBytes(int p_setBytes_1_, ScatteringByteChannel p_setBytes_2_, int p_setBytes_3_)
/* 486:    */     throws IOException
/* 487:    */   {
/* 488:543 */     return this.field_150794_a.setBytes(p_setBytes_1_, p_setBytes_2_, p_setBytes_3_);
/* 489:    */   }
/* 490:    */   
/* 491:    */   public ByteBuf setZero(int p_setZero_1_, int p_setZero_2_)
/* 492:    */   {
/* 493:548 */     return this.field_150794_a.setZero(p_setZero_1_, p_setZero_2_);
/* 494:    */   }
/* 495:    */   
/* 496:    */   public boolean readBoolean()
/* 497:    */   {
/* 498:553 */     return this.field_150794_a.readBoolean();
/* 499:    */   }
/* 500:    */   
/* 501:    */   public byte readByte()
/* 502:    */   {
/* 503:558 */     return this.field_150794_a.readByte();
/* 504:    */   }
/* 505:    */   
/* 506:    */   public short readUnsignedByte()
/* 507:    */   {
/* 508:563 */     return this.field_150794_a.readUnsignedByte();
/* 509:    */   }
/* 510:    */   
/* 511:    */   public short readShort()
/* 512:    */   {
/* 513:568 */     return this.field_150794_a.readShort();
/* 514:    */   }
/* 515:    */   
/* 516:    */   public int readUnsignedShort()
/* 517:    */   {
/* 518:573 */     return this.field_150794_a.readUnsignedShort();
/* 519:    */   }
/* 520:    */   
/* 521:    */   public int readMedium()
/* 522:    */   {
/* 523:578 */     return this.field_150794_a.readMedium();
/* 524:    */   }
/* 525:    */   
/* 526:    */   public int readUnsignedMedium()
/* 527:    */   {
/* 528:583 */     return this.field_150794_a.readUnsignedMedium();
/* 529:    */   }
/* 530:    */   
/* 531:    */   public int readInt()
/* 532:    */   {
/* 533:588 */     return this.field_150794_a.readInt();
/* 534:    */   }
/* 535:    */   
/* 536:    */   public long readUnsignedInt()
/* 537:    */   {
/* 538:593 */     return this.field_150794_a.readUnsignedInt();
/* 539:    */   }
/* 540:    */   
/* 541:    */   public long readLong()
/* 542:    */   {
/* 543:598 */     return this.field_150794_a.readLong();
/* 544:    */   }
/* 545:    */   
/* 546:    */   public char readChar()
/* 547:    */   {
/* 548:603 */     return this.field_150794_a.readChar();
/* 549:    */   }
/* 550:    */   
/* 551:    */   public float readFloat()
/* 552:    */   {
/* 553:608 */     return this.field_150794_a.readFloat();
/* 554:    */   }
/* 555:    */   
/* 556:    */   public double readDouble()
/* 557:    */   {
/* 558:613 */     return this.field_150794_a.readDouble();
/* 559:    */   }
/* 560:    */   
/* 561:    */   public ByteBuf readBytes(int p_readBytes_1_)
/* 562:    */   {
/* 563:618 */     return this.field_150794_a.readBytes(p_readBytes_1_);
/* 564:    */   }
/* 565:    */   
/* 566:    */   public ByteBuf readSlice(int p_readSlice_1_)
/* 567:    */   {
/* 568:623 */     return this.field_150794_a.readSlice(p_readSlice_1_);
/* 569:    */   }
/* 570:    */   
/* 571:    */   public ByteBuf readBytes(ByteBuf p_readBytes_1_)
/* 572:    */   {
/* 573:628 */     return this.field_150794_a.readBytes(p_readBytes_1_);
/* 574:    */   }
/* 575:    */   
/* 576:    */   public ByteBuf readBytes(ByteBuf p_readBytes_1_, int p_readBytes_2_)
/* 577:    */   {
/* 578:633 */     return this.field_150794_a.readBytes(p_readBytes_1_, p_readBytes_2_);
/* 579:    */   }
/* 580:    */   
/* 581:    */   public ByteBuf readBytes(ByteBuf p_readBytes_1_, int p_readBytes_2_, int p_readBytes_3_)
/* 582:    */   {
/* 583:638 */     return this.field_150794_a.readBytes(p_readBytes_1_, p_readBytes_2_, p_readBytes_3_);
/* 584:    */   }
/* 585:    */   
/* 586:    */   public ByteBuf readBytes(byte[] p_readBytes_1_)
/* 587:    */   {
/* 588:643 */     return this.field_150794_a.readBytes(p_readBytes_1_);
/* 589:    */   }
/* 590:    */   
/* 591:    */   public ByteBuf readBytes(byte[] p_readBytes_1_, int p_readBytes_2_, int p_readBytes_3_)
/* 592:    */   {
/* 593:648 */     return this.field_150794_a.readBytes(p_readBytes_1_, p_readBytes_2_, p_readBytes_3_);
/* 594:    */   }
/* 595:    */   
/* 596:    */   public ByteBuf readBytes(ByteBuffer p_readBytes_1_)
/* 597:    */   {
/* 598:653 */     return this.field_150794_a.readBytes(p_readBytes_1_);
/* 599:    */   }
/* 600:    */   
/* 601:    */   public ByteBuf readBytes(OutputStream p_readBytes_1_, int p_readBytes_2_)
/* 602:    */     throws IOException
/* 603:    */   {
/* 604:658 */     return this.field_150794_a.readBytes(p_readBytes_1_, p_readBytes_2_);
/* 605:    */   }
/* 606:    */   
/* 607:    */   public int readBytes(GatheringByteChannel p_readBytes_1_, int p_readBytes_2_)
/* 608:    */     throws IOException
/* 609:    */   {
/* 610:663 */     return this.field_150794_a.readBytes(p_readBytes_1_, p_readBytes_2_);
/* 611:    */   }
/* 612:    */   
/* 613:    */   public ByteBuf skipBytes(int p_skipBytes_1_)
/* 614:    */   {
/* 615:668 */     return this.field_150794_a.skipBytes(p_skipBytes_1_);
/* 616:    */   }
/* 617:    */   
/* 618:    */   public ByteBuf writeBoolean(boolean p_writeBoolean_1_)
/* 619:    */   {
/* 620:673 */     return this.field_150794_a.writeBoolean(p_writeBoolean_1_);
/* 621:    */   }
/* 622:    */   
/* 623:    */   public ByteBuf writeByte(int p_writeByte_1_)
/* 624:    */   {
/* 625:678 */     return this.field_150794_a.writeByte(p_writeByte_1_);
/* 626:    */   }
/* 627:    */   
/* 628:    */   public ByteBuf writeShort(int p_writeShort_1_)
/* 629:    */   {
/* 630:683 */     return this.field_150794_a.writeShort(p_writeShort_1_);
/* 631:    */   }
/* 632:    */   
/* 633:    */   public ByteBuf writeMedium(int p_writeMedium_1_)
/* 634:    */   {
/* 635:688 */     return this.field_150794_a.writeMedium(p_writeMedium_1_);
/* 636:    */   }
/* 637:    */   
/* 638:    */   public ByteBuf writeInt(int p_writeInt_1_)
/* 639:    */   {
/* 640:693 */     return this.field_150794_a.writeInt(p_writeInt_1_);
/* 641:    */   }
/* 642:    */   
/* 643:    */   public ByteBuf writeLong(long p_writeLong_1_)
/* 644:    */   {
/* 645:698 */     return this.field_150794_a.writeLong(p_writeLong_1_);
/* 646:    */   }
/* 647:    */   
/* 648:    */   public ByteBuf writeChar(int p_writeChar_1_)
/* 649:    */   {
/* 650:703 */     return this.field_150794_a.writeChar(p_writeChar_1_);
/* 651:    */   }
/* 652:    */   
/* 653:    */   public ByteBuf writeFloat(float p_writeFloat_1_)
/* 654:    */   {
/* 655:708 */     return this.field_150794_a.writeFloat(p_writeFloat_1_);
/* 656:    */   }
/* 657:    */   
/* 658:    */   public ByteBuf writeDouble(double p_writeDouble_1_)
/* 659:    */   {
/* 660:713 */     return this.field_150794_a.writeDouble(p_writeDouble_1_);
/* 661:    */   }
/* 662:    */   
/* 663:    */   public ByteBuf writeBytes(ByteBuf p_writeBytes_1_)
/* 664:    */   {
/* 665:718 */     return this.field_150794_a.writeBytes(p_writeBytes_1_);
/* 666:    */   }
/* 667:    */   
/* 668:    */   public ByteBuf writeBytes(ByteBuf p_writeBytes_1_, int p_writeBytes_2_)
/* 669:    */   {
/* 670:723 */     return this.field_150794_a.writeBytes(p_writeBytes_1_, p_writeBytes_2_);
/* 671:    */   }
/* 672:    */   
/* 673:    */   public ByteBuf writeBytes(ByteBuf p_writeBytes_1_, int p_writeBytes_2_, int p_writeBytes_3_)
/* 674:    */   {
/* 675:728 */     return this.field_150794_a.writeBytes(p_writeBytes_1_, p_writeBytes_2_, p_writeBytes_3_);
/* 676:    */   }
/* 677:    */   
/* 678:    */   public ByteBuf writeBytes(byte[] p_writeBytes_1_)
/* 679:    */   {
/* 680:733 */     return this.field_150794_a.writeBytes(p_writeBytes_1_);
/* 681:    */   }
/* 682:    */   
/* 683:    */   public ByteBuf writeBytes(byte[] p_writeBytes_1_, int p_writeBytes_2_, int p_writeBytes_3_)
/* 684:    */   {
/* 685:738 */     return this.field_150794_a.writeBytes(p_writeBytes_1_, p_writeBytes_2_, p_writeBytes_3_);
/* 686:    */   }
/* 687:    */   
/* 688:    */   public ByteBuf writeBytes(ByteBuffer p_writeBytes_1_)
/* 689:    */   {
/* 690:743 */     return this.field_150794_a.writeBytes(p_writeBytes_1_);
/* 691:    */   }
/* 692:    */   
/* 693:    */   public int writeBytes(InputStream p_writeBytes_1_, int p_writeBytes_2_)
/* 694:    */     throws IOException
/* 695:    */   {
/* 696:748 */     return this.field_150794_a.writeBytes(p_writeBytes_1_, p_writeBytes_2_);
/* 697:    */   }
/* 698:    */   
/* 699:    */   public int writeBytes(ScatteringByteChannel p_writeBytes_1_, int p_writeBytes_2_)
/* 700:    */     throws IOException
/* 701:    */   {
/* 702:753 */     return this.field_150794_a.writeBytes(p_writeBytes_1_, p_writeBytes_2_);
/* 703:    */   }
/* 704:    */   
/* 705:    */   public ByteBuf writeZero(int p_writeZero_1_)
/* 706:    */   {
/* 707:758 */     return this.field_150794_a.writeZero(p_writeZero_1_);
/* 708:    */   }
/* 709:    */   
/* 710:    */   public int indexOf(int p_indexOf_1_, int p_indexOf_2_, byte p_indexOf_3_)
/* 711:    */   {
/* 712:763 */     return this.field_150794_a.indexOf(p_indexOf_1_, p_indexOf_2_, p_indexOf_3_);
/* 713:    */   }
/* 714:    */   
/* 715:    */   public int bytesBefore(byte p_bytesBefore_1_)
/* 716:    */   {
/* 717:768 */     return this.field_150794_a.bytesBefore(p_bytesBefore_1_);
/* 718:    */   }
/* 719:    */   
/* 720:    */   public int bytesBefore(int p_bytesBefore_1_, byte p_bytesBefore_2_)
/* 721:    */   {
/* 722:773 */     return this.field_150794_a.bytesBefore(p_bytesBefore_1_, p_bytesBefore_2_);
/* 723:    */   }
/* 724:    */   
/* 725:    */   public int bytesBefore(int p_bytesBefore_1_, int p_bytesBefore_2_, byte p_bytesBefore_3_)
/* 726:    */   {
/* 727:778 */     return this.field_150794_a.bytesBefore(p_bytesBefore_1_, p_bytesBefore_2_, p_bytesBefore_3_);
/* 728:    */   }
/* 729:    */   
/* 730:    */   public int forEachByte(ByteBufProcessor p_forEachByte_1_)
/* 731:    */   {
/* 732:783 */     return this.field_150794_a.forEachByte(p_forEachByte_1_);
/* 733:    */   }
/* 734:    */   
/* 735:    */   public int forEachByte(int p_forEachByte_1_, int p_forEachByte_2_, ByteBufProcessor p_forEachByte_3_)
/* 736:    */   {
/* 737:788 */     return this.field_150794_a.forEachByte(p_forEachByte_1_, p_forEachByte_2_, p_forEachByte_3_);
/* 738:    */   }
/* 739:    */   
/* 740:    */   public int forEachByteDesc(ByteBufProcessor p_forEachByteDesc_1_)
/* 741:    */   {
/* 742:793 */     return this.field_150794_a.forEachByteDesc(p_forEachByteDesc_1_);
/* 743:    */   }
/* 744:    */   
/* 745:    */   public int forEachByteDesc(int p_forEachByteDesc_1_, int p_forEachByteDesc_2_, ByteBufProcessor p_forEachByteDesc_3_)
/* 746:    */   {
/* 747:798 */     return this.field_150794_a.forEachByteDesc(p_forEachByteDesc_1_, p_forEachByteDesc_2_, p_forEachByteDesc_3_);
/* 748:    */   }
/* 749:    */   
/* 750:    */   public ByteBuf copy()
/* 751:    */   {
/* 752:803 */     return this.field_150794_a.copy();
/* 753:    */   }
/* 754:    */   
/* 755:    */   public ByteBuf copy(int p_copy_1_, int p_copy_2_)
/* 756:    */   {
/* 757:808 */     return this.field_150794_a.copy(p_copy_1_, p_copy_2_);
/* 758:    */   }
/* 759:    */   
/* 760:    */   public ByteBuf slice()
/* 761:    */   {
/* 762:813 */     return this.field_150794_a.slice();
/* 763:    */   }
/* 764:    */   
/* 765:    */   public ByteBuf slice(int p_slice_1_, int p_slice_2_)
/* 766:    */   {
/* 767:818 */     return this.field_150794_a.slice(p_slice_1_, p_slice_2_);
/* 768:    */   }
/* 769:    */   
/* 770:    */   public ByteBuf duplicate()
/* 771:    */   {
/* 772:823 */     return this.field_150794_a.duplicate();
/* 773:    */   }
/* 774:    */   
/* 775:    */   public int nioBufferCount()
/* 776:    */   {
/* 777:828 */     return this.field_150794_a.nioBufferCount();
/* 778:    */   }
/* 779:    */   
/* 780:    */   public ByteBuffer nioBuffer()
/* 781:    */   {
/* 782:833 */     return this.field_150794_a.nioBuffer();
/* 783:    */   }
/* 784:    */   
/* 785:    */   public ByteBuffer nioBuffer(int p_nioBuffer_1_, int p_nioBuffer_2_)
/* 786:    */   {
/* 787:838 */     return this.field_150794_a.nioBuffer(p_nioBuffer_1_, p_nioBuffer_2_);
/* 788:    */   }
/* 789:    */   
/* 790:    */   public ByteBuffer internalNioBuffer(int p_internalNioBuffer_1_, int p_internalNioBuffer_2_)
/* 791:    */   {
/* 792:843 */     return this.field_150794_a.internalNioBuffer(p_internalNioBuffer_1_, p_internalNioBuffer_2_);
/* 793:    */   }
/* 794:    */   
/* 795:    */   public ByteBuffer[] nioBuffers()
/* 796:    */   {
/* 797:848 */     return this.field_150794_a.nioBuffers();
/* 798:    */   }
/* 799:    */   
/* 800:    */   public ByteBuffer[] nioBuffers(int p_nioBuffers_1_, int p_nioBuffers_2_)
/* 801:    */   {
/* 802:853 */     return this.field_150794_a.nioBuffers(p_nioBuffers_1_, p_nioBuffers_2_);
/* 803:    */   }
/* 804:    */   
/* 805:    */   public boolean hasArray()
/* 806:    */   {
/* 807:858 */     return this.field_150794_a.hasArray();
/* 808:    */   }
/* 809:    */   
/* 810:    */   public byte[] array()
/* 811:    */   {
/* 812:863 */     return this.field_150794_a.array();
/* 813:    */   }
/* 814:    */   
/* 815:    */   public int arrayOffset()
/* 816:    */   {
/* 817:868 */     return this.field_150794_a.arrayOffset();
/* 818:    */   }
/* 819:    */   
/* 820:    */   public boolean hasMemoryAddress()
/* 821:    */   {
/* 822:873 */     return this.field_150794_a.hasMemoryAddress();
/* 823:    */   }
/* 824:    */   
/* 825:    */   public long memoryAddress()
/* 826:    */   {
/* 827:878 */     return this.field_150794_a.memoryAddress();
/* 828:    */   }
/* 829:    */   
/* 830:    */   public String toString(Charset p_toString_1_)
/* 831:    */   {
/* 832:883 */     return this.field_150794_a.toString(p_toString_1_);
/* 833:    */   }
/* 834:    */   
/* 835:    */   public String toString(int p_toString_1_, int p_toString_2_, Charset p_toString_3_)
/* 836:    */   {
/* 837:888 */     return this.field_150794_a.toString(p_toString_1_, p_toString_2_, p_toString_3_);
/* 838:    */   }
/* 839:    */   
/* 840:    */   public int hashCode()
/* 841:    */   {
/* 842:893 */     return this.field_150794_a.hashCode();
/* 843:    */   }
/* 844:    */   
/* 845:    */   public boolean equals(Object par1Obj)
/* 846:    */   {
/* 847:898 */     return this.field_150794_a.equals(par1Obj);
/* 848:    */   }
/* 849:    */   
/* 850:    */   public int compareTo(ByteBuf par1Obj)
/* 851:    */   {
/* 852:903 */     return this.field_150794_a.compareTo(par1Obj);
/* 853:    */   }
/* 854:    */   
/* 855:    */   public String toString()
/* 856:    */   {
/* 857:908 */     return this.field_150794_a.toString();
/* 858:    */   }
/* 859:    */   
/* 860:    */   public ByteBuf retain(int p_retain_1_)
/* 861:    */   {
/* 862:913 */     return this.field_150794_a.retain(p_retain_1_);
/* 863:    */   }
/* 864:    */   
/* 865:    */   public ByteBuf retain()
/* 866:    */   {
/* 867:918 */     return this.field_150794_a.retain();
/* 868:    */   }
/* 869:    */   
/* 870:    */   public int refCnt()
/* 871:    */   {
/* 872:923 */     return this.field_150794_a.refCnt();
/* 873:    */   }
/* 874:    */   
/* 875:    */   public boolean release()
/* 876:    */   {
/* 877:928 */     return this.field_150794_a.release();
/* 878:    */   }
/* 879:    */   
/* 880:    */   public boolean release(int p_release_1_)
/* 881:    */   {
/* 882:933 */     return this.field_150794_a.release(p_release_1_);
/* 883:    */   }
/* 884:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.PacketBuffer
 * JD-Core Version:    0.7.0.1
 */