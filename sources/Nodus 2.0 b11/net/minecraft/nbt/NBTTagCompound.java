/*   1:    */ package net.minecraft.nbt;
/*   2:    */ 
/*   3:    */ import java.io.DataInput;
/*   4:    */ import java.io.DataOutput;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.Map;
/*   9:    */ import java.util.Set;
/*  10:    */ import java.util.concurrent.Callable;
/*  11:    */ import net.minecraft.crash.CrashReport;
/*  12:    */ import net.minecraft.crash.CrashReportCategory;
/*  13:    */ import net.minecraft.util.ReportedException;
/*  14:    */ import org.apache.logging.log4j.LogManager;
/*  15:    */ import org.apache.logging.log4j.Logger;
/*  16:    */ 
/*  17:    */ public class NBTTagCompound
/*  18:    */   extends NBTBase
/*  19:    */ {
/*  20: 19 */   private static final Logger logger = ;
/*  21: 24 */   private Map tagMap = new HashMap();
/*  22:    */   private static final String __OBFID = "CL_00001215";
/*  23:    */   
/*  24:    */   void write(DataOutput par1DataOutput)
/*  25:    */     throws IOException
/*  26:    */   {
/*  27: 32 */     Iterator var2 = this.tagMap.keySet().iterator();
/*  28: 34 */     while (var2.hasNext())
/*  29:    */     {
/*  30: 36 */       String var3 = (String)var2.next();
/*  31: 37 */       NBTBase var4 = (NBTBase)this.tagMap.get(var3);
/*  32: 38 */       func_150298_a(var3, var4, par1DataOutput);
/*  33:    */     }
/*  34: 41 */     par1DataOutput.writeByte(0);
/*  35:    */   }
/*  36:    */   
/*  37:    */   void load(DataInput par1DataInput, int par2)
/*  38:    */     throws IOException
/*  39:    */   {
/*  40: 49 */     if (par2 > 512) {
/*  41: 51 */       throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
/*  42:    */     }
/*  43: 55 */     this.tagMap.clear();
/*  44:    */     byte var3;
/*  45: 58 */     while ((var3 = func_150300_a(par1DataInput)) != 0)
/*  46:    */     {
/*  47:    */       byte var3;
/*  48: 60 */       String var4 = func_150294_b(par1DataInput);
/*  49: 61 */       NBTBase var5 = func_150293_a(var3, var4, par1DataInput, par2 + 1);
/*  50: 62 */       this.tagMap.put(var4, var5);
/*  51:    */     }
/*  52:    */   }
/*  53:    */   
/*  54:    */   public Set func_150296_c()
/*  55:    */   {
/*  56: 69 */     return this.tagMap.keySet();
/*  57:    */   }
/*  58:    */   
/*  59:    */   public byte getId()
/*  60:    */   {
/*  61: 77 */     return 10;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setTag(String par1Str, NBTBase par2NBTBase)
/*  65:    */   {
/*  66: 85 */     this.tagMap.put(par1Str, par2NBTBase);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void setByte(String par1Str, byte par2)
/*  70:    */   {
/*  71: 93 */     this.tagMap.put(par1Str, new NBTTagByte(par2));
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void setShort(String par1Str, short par2)
/*  75:    */   {
/*  76:101 */     this.tagMap.put(par1Str, new NBTTagShort(par2));
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void setInteger(String par1Str, int par2)
/*  80:    */   {
/*  81:109 */     this.tagMap.put(par1Str, new NBTTagInt(par2));
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setLong(String par1Str, long par2)
/*  85:    */   {
/*  86:117 */     this.tagMap.put(par1Str, new NBTTagLong(par2));
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void setFloat(String par1Str, float par2)
/*  90:    */   {
/*  91:125 */     this.tagMap.put(par1Str, new NBTTagFloat(par2));
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setDouble(String par1Str, double par2)
/*  95:    */   {
/*  96:133 */     this.tagMap.put(par1Str, new NBTTagDouble(par2));
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void setString(String par1Str, String par2Str)
/* 100:    */   {
/* 101:141 */     this.tagMap.put(par1Str, new NBTTagString(par2Str));
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setByteArray(String par1Str, byte[] par2ArrayOfByte)
/* 105:    */   {
/* 106:149 */     this.tagMap.put(par1Str, new NBTTagByteArray(par2ArrayOfByte));
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setIntArray(String par1Str, int[] par2ArrayOfInteger)
/* 110:    */   {
/* 111:157 */     this.tagMap.put(par1Str, new NBTTagIntArray(par2ArrayOfInteger));
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void setBoolean(String par1Str, boolean par2)
/* 115:    */   {
/* 116:165 */     setByte(par1Str, (byte)(par2 ? 1 : 0));
/* 117:    */   }
/* 118:    */   
/* 119:    */   public NBTBase getTag(String par1Str)
/* 120:    */   {
/* 121:173 */     return (NBTBase)this.tagMap.get(par1Str);
/* 122:    */   }
/* 123:    */   
/* 124:    */   public byte func_150299_b(String p_150299_1_)
/* 125:    */   {
/* 126:178 */     NBTBase var2 = (NBTBase)this.tagMap.get(p_150299_1_);
/* 127:179 */     return var2 != null ? var2.getId() : 0;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public boolean hasKey(String par1Str)
/* 131:    */   {
/* 132:187 */     return this.tagMap.containsKey(par1Str);
/* 133:    */   }
/* 134:    */   
/* 135:    */   public boolean func_150297_b(String p_150297_1_, int p_150297_2_)
/* 136:    */   {
/* 137:192 */     byte var3 = func_150299_b(p_150297_1_);
/* 138:194 */     if (var3 == p_150297_2_) {
/* 139:196 */       return true;
/* 140:    */     }
/* 141:198 */     if (p_150297_2_ != 99)
/* 142:    */     {
/* 143:200 */       if (var3 > 0) {
/* 144:202 */         logger.warn("NBT tag {} was of wrong type; expected {}, found {}", new Object[] { p_150297_1_, func_150283_g(p_150297_2_), func_150283_g(var3) });
/* 145:    */       }
/* 146:205 */       return false;
/* 147:    */     }
/* 148:209 */     return (var3 == 1) || (var3 == 2) || (var3 == 3) || (var3 == 4) || (var3 == 5) || (var3 == 6);
/* 149:    */   }
/* 150:    */   
/* 151:    */   public byte getByte(String par1Str)
/* 152:    */   {
/* 153:    */     try
/* 154:    */     {
/* 155:220 */       return !this.tagMap.containsKey(par1Str) ? 0 : ((NBTBase.NBTPrimitive)this.tagMap.get(par1Str)).func_150290_f();
/* 156:    */     }
/* 157:    */     catch (ClassCastException var3) {}
/* 158:224 */     return 0;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public short getShort(String par1Str)
/* 162:    */   {
/* 163:    */     try
/* 164:    */     {
/* 165:235 */       return !this.tagMap.containsKey(par1Str) ? 0 : ((NBTBase.NBTPrimitive)this.tagMap.get(par1Str)).func_150289_e();
/* 166:    */     }
/* 167:    */     catch (ClassCastException var3) {}
/* 168:239 */     return 0;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public int getInteger(String par1Str)
/* 172:    */   {
/* 173:    */     try
/* 174:    */     {
/* 175:250 */       return !this.tagMap.containsKey(par1Str) ? 0 : ((NBTBase.NBTPrimitive)this.tagMap.get(par1Str)).func_150287_d();
/* 176:    */     }
/* 177:    */     catch (ClassCastException var3) {}
/* 178:254 */     return 0;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public long getLong(String par1Str)
/* 182:    */   {
/* 183:    */     try
/* 184:    */     {
/* 185:265 */       return !this.tagMap.containsKey(par1Str) ? 0L : ((NBTBase.NBTPrimitive)this.tagMap.get(par1Str)).func_150291_c();
/* 186:    */     }
/* 187:    */     catch (ClassCastException var3) {}
/* 188:269 */     return 0L;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public float getFloat(String par1Str)
/* 192:    */   {
/* 193:    */     try
/* 194:    */     {
/* 195:280 */       return !this.tagMap.containsKey(par1Str) ? 0.0F : ((NBTBase.NBTPrimitive)this.tagMap.get(par1Str)).func_150288_h();
/* 196:    */     }
/* 197:    */     catch (ClassCastException var3) {}
/* 198:284 */     return 0.0F;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public double getDouble(String par1Str)
/* 202:    */   {
/* 203:    */     try
/* 204:    */     {
/* 205:295 */       return !this.tagMap.containsKey(par1Str) ? 0.0D : ((NBTBase.NBTPrimitive)this.tagMap.get(par1Str)).func_150286_g();
/* 206:    */     }
/* 207:    */     catch (ClassCastException var3) {}
/* 208:299 */     return 0.0D;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public String getString(String par1Str)
/* 212:    */   {
/* 213:    */     try
/* 214:    */     {
/* 215:310 */       return !this.tagMap.containsKey(par1Str) ? "" : ((NBTBase)this.tagMap.get(par1Str)).func_150285_a_();
/* 216:    */     }
/* 217:    */     catch (ClassCastException var3) {}
/* 218:314 */     return "";
/* 219:    */   }
/* 220:    */   
/* 221:    */   public byte[] getByteArray(String par1Str)
/* 222:    */   {
/* 223:    */     try
/* 224:    */     {
/* 225:325 */       return !this.tagMap.containsKey(par1Str) ? new byte[0] : ((NBTTagByteArray)this.tagMap.get(par1Str)).func_150292_c();
/* 226:    */     }
/* 227:    */     catch (ClassCastException var3)
/* 228:    */     {
/* 229:329 */       throw new ReportedException(createCrashReport(par1Str, 7, var3));
/* 230:    */     }
/* 231:    */   }
/* 232:    */   
/* 233:    */   public int[] getIntArray(String par1Str)
/* 234:    */   {
/* 235:    */     try
/* 236:    */     {
/* 237:340 */       return !this.tagMap.containsKey(par1Str) ? new int[0] : ((NBTTagIntArray)this.tagMap.get(par1Str)).func_150302_c();
/* 238:    */     }
/* 239:    */     catch (ClassCastException var3)
/* 240:    */     {
/* 241:344 */       throw new ReportedException(createCrashReport(par1Str, 11, var3));
/* 242:    */     }
/* 243:    */   }
/* 244:    */   
/* 245:    */   public NBTTagCompound getCompoundTag(String par1Str)
/* 246:    */   {
/* 247:    */     try
/* 248:    */     {
/* 249:356 */       return !this.tagMap.containsKey(par1Str) ? new NBTTagCompound() : (NBTTagCompound)this.tagMap.get(par1Str);
/* 250:    */     }
/* 251:    */     catch (ClassCastException var3)
/* 252:    */     {
/* 253:360 */       throw new ReportedException(createCrashReport(par1Str, 10, var3));
/* 254:    */     }
/* 255:    */   }
/* 256:    */   
/* 257:    */   public NBTTagList getTagList(String p_150295_1_, int p_150295_2_)
/* 258:    */   {
/* 259:    */     try
/* 260:    */     {
/* 261:371 */       if (func_150299_b(p_150295_1_) != 9) {
/* 262:373 */         return new NBTTagList();
/* 263:    */       }
/* 264:377 */       NBTTagList var3 = (NBTTagList)this.tagMap.get(p_150295_1_);
/* 265:378 */       return (var3.tagCount() > 0) && (var3.func_150303_d() != p_150295_2_) ? new NBTTagList() : var3;
/* 266:    */     }
/* 267:    */     catch (ClassCastException var4)
/* 268:    */     {
/* 269:383 */       throw new ReportedException(createCrashReport(p_150295_1_, 9, var4));
/* 270:    */     }
/* 271:    */   }
/* 272:    */   
/* 273:    */   public boolean getBoolean(String par1Str)
/* 274:    */   {
/* 275:393 */     return getByte(par1Str) != 0;
/* 276:    */   }
/* 277:    */   
/* 278:    */   public void removeTag(String par1Str)
/* 279:    */   {
/* 280:401 */     this.tagMap.remove(par1Str);
/* 281:    */   }
/* 282:    */   
/* 283:    */   public String toString()
/* 284:    */   {
/* 285:406 */     String var1 = "{";
/* 286:    */     String var3;
/* 287:409 */     for (Iterator var2 = this.tagMap.keySet().iterator(); var2.hasNext(); var1 = var1 + var3 + ':' + this.tagMap.get(var3) + ',') {
/* 288:411 */       var3 = (String)var2.next();
/* 289:    */     }
/* 290:414 */     return var1 + "}";
/* 291:    */   }
/* 292:    */   
/* 293:    */   public boolean hasNoTags()
/* 294:    */   {
/* 295:422 */     return this.tagMap.isEmpty();
/* 296:    */   }
/* 297:    */   
/* 298:    */   private CrashReport createCrashReport(final String par1Str, final int par2, ClassCastException par3ClassCastException)
/* 299:    */   {
/* 300:430 */     CrashReport var4 = CrashReport.makeCrashReport(par3ClassCastException, "Reading NBT data");
/* 301:431 */     CrashReportCategory var5 = var4.makeCategoryDepth("Corrupt NBT tag", 1);
/* 302:432 */     var5.addCrashSectionCallable("Tag type found", new Callable()
/* 303:    */     {
/* 304:    */       private static final String __OBFID = "CL_00001216";
/* 305:    */       
/* 306:    */       public String call()
/* 307:    */       {
/* 308:437 */         return NBTBase.NBTTypes[((NBTBase)NBTTagCompound.this.tagMap.get(par1Str)).getId()];
/* 309:    */       }
/* 310:439 */     });
/* 311:440 */     var5.addCrashSectionCallable("Tag type expected", new Callable()
/* 312:    */     {
/* 313:    */       private static final String __OBFID = "CL_00001217";
/* 314:    */       
/* 315:    */       public String call()
/* 316:    */       {
/* 317:445 */         return NBTBase.NBTTypes[par2];
/* 318:    */       }
/* 319:447 */     });
/* 320:448 */     var5.addCrashSection("Tag name", par1Str);
/* 321:449 */     return var4;
/* 322:    */   }
/* 323:    */   
/* 324:    */   public NBTBase copy()
/* 325:    */   {
/* 326:457 */     NBTTagCompound var1 = new NBTTagCompound();
/* 327:458 */     Iterator var2 = this.tagMap.keySet().iterator();
/* 328:460 */     while (var2.hasNext())
/* 329:    */     {
/* 330:462 */       String var3 = (String)var2.next();
/* 331:463 */       var1.setTag(var3, ((NBTBase)this.tagMap.get(var3)).copy());
/* 332:    */     }
/* 333:466 */     return var1;
/* 334:    */   }
/* 335:    */   
/* 336:    */   public boolean equals(Object par1Obj)
/* 337:    */   {
/* 338:471 */     if (super.equals(par1Obj))
/* 339:    */     {
/* 340:473 */       NBTTagCompound var2 = (NBTTagCompound)par1Obj;
/* 341:474 */       return this.tagMap.entrySet().equals(var2.tagMap.entrySet());
/* 342:    */     }
/* 343:478 */     return false;
/* 344:    */   }
/* 345:    */   
/* 346:    */   public int hashCode()
/* 347:    */   {
/* 348:484 */     return super.hashCode() ^ this.tagMap.hashCode();
/* 349:    */   }
/* 350:    */   
/* 351:    */   private static void func_150298_a(String p_150298_0_, NBTBase p_150298_1_, DataOutput p_150298_2_)
/* 352:    */     throws IOException
/* 353:    */   {
/* 354:489 */     p_150298_2_.writeByte(p_150298_1_.getId());
/* 355:491 */     if (p_150298_1_.getId() != 0)
/* 356:    */     {
/* 357:493 */       p_150298_2_.writeUTF(p_150298_0_);
/* 358:494 */       p_150298_1_.write(p_150298_2_);
/* 359:    */     }
/* 360:    */   }
/* 361:    */   
/* 362:    */   private static byte func_150300_a(DataInput p_150300_0_)
/* 363:    */     throws IOException
/* 364:    */   {
/* 365:500 */     return p_150300_0_.readByte();
/* 366:    */   }
/* 367:    */   
/* 368:    */   private static String func_150294_b(DataInput p_150294_0_)
/* 369:    */     throws IOException
/* 370:    */   {
/* 371:505 */     return p_150294_0_.readUTF();
/* 372:    */   }
/* 373:    */   
/* 374:    */   static NBTBase func_150293_a(byte p_150293_0_, String p_150293_1_, DataInput p_150293_2_, int p_150293_3_)
/* 375:    */   {
/* 376:510 */     NBTBase var4 = NBTBase.func_150284_a(p_150293_0_);
/* 377:    */     try
/* 378:    */     {
/* 379:514 */       var4.load(p_150293_2_, p_150293_3_);
/* 380:515 */       return var4;
/* 381:    */     }
/* 382:    */     catch (IOException var8)
/* 383:    */     {
/* 384:519 */       CrashReport var6 = CrashReport.makeCrashReport(var8, "Loading NBT data");
/* 385:520 */       CrashReportCategory var7 = var6.makeCategory("NBT Tag");
/* 386:521 */       var7.addCrashSection("Tag name", p_150293_1_);
/* 387:522 */       var7.addCrashSection("Tag type", Byte.valueOf(p_150293_0_));
/* 388:523 */       throw new ReportedException(var6);
/* 389:    */     }
/* 390:    */   }
/* 391:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.nbt.NBTTagCompound
 * JD-Core Version:    0.7.0.1
 */