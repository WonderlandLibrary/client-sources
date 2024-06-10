/*   1:    */ package net.minecraft.entity;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Collection;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import java.util.concurrent.locks.Lock;
/*  11:    */ import java.util.concurrent.locks.ReadWriteLock;
/*  12:    */ import java.util.concurrent.locks.ReentrantReadWriteLock;
/*  13:    */ import net.minecraft.crash.CrashReport;
/*  14:    */ import net.minecraft.crash.CrashReportCategory;
/*  15:    */ import net.minecraft.item.ItemStack;
/*  16:    */ import net.minecraft.network.PacketBuffer;
/*  17:    */ import net.minecraft.util.ChunkCoordinates;
/*  18:    */ import net.minecraft.util.ReportedException;
/*  19:    */ import org.apache.commons.lang3.ObjectUtils;
/*  20:    */ 
/*  21:    */ public class DataWatcher
/*  22:    */ {
/*  23:    */   private final Entity field_151511_a;
/*  24: 24 */   private boolean isBlank = true;
/*  25: 25 */   private static final HashMap dataTypes = new HashMap();
/*  26: 26 */   private final Map watchedObjects = new HashMap();
/*  27:    */   private boolean objectChanged;
/*  28: 30 */   private ReadWriteLock lock = new ReentrantReadWriteLock();
/*  29:    */   private static final String __OBFID = "CL_00001559";
/*  30:    */   
/*  31:    */   public DataWatcher(Entity p_i45313_1_)
/*  32:    */   {
/*  33: 35 */     this.field_151511_a = p_i45313_1_;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void addObject(int par1, Object par2Obj)
/*  37:    */   {
/*  38: 44 */     Integer var3 = (Integer)dataTypes.get(par2Obj.getClass());
/*  39: 46 */     if (var3 == null) {
/*  40: 48 */       throw new IllegalArgumentException("Unknown data type: " + par2Obj.getClass());
/*  41:    */     }
/*  42: 50 */     if (par1 > 31) {
/*  43: 52 */       throw new IllegalArgumentException("Data value id is too big with " + par1 + "! (Max is " + 31 + ")");
/*  44:    */     }
/*  45: 54 */     if (this.watchedObjects.containsKey(Integer.valueOf(par1))) {
/*  46: 56 */       throw new IllegalArgumentException("Duplicate id value for " + par1 + "!");
/*  47:    */     }
/*  48: 60 */     WatchableObject var4 = new WatchableObject(var3.intValue(), par1, par2Obj);
/*  49: 61 */     this.lock.writeLock().lock();
/*  50: 62 */     this.watchedObjects.put(Integer.valueOf(par1), var4);
/*  51: 63 */     this.lock.writeLock().unlock();
/*  52: 64 */     this.isBlank = false;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void addObjectByDataType(int par1, int par2)
/*  56:    */   {
/*  57: 73 */     WatchableObject var3 = new WatchableObject(par2, par1, null);
/*  58: 74 */     this.lock.writeLock().lock();
/*  59: 75 */     this.watchedObjects.put(Integer.valueOf(par1), var3);
/*  60: 76 */     this.lock.writeLock().unlock();
/*  61: 77 */     this.isBlank = false;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public byte getWatchableObjectByte(int par1)
/*  65:    */   {
/*  66: 85 */     return ((Byte)getWatchedObject(par1).getObject()).byteValue();
/*  67:    */   }
/*  68:    */   
/*  69:    */   public short getWatchableObjectShort(int par1)
/*  70:    */   {
/*  71: 90 */     return ((Short)getWatchedObject(par1).getObject()).shortValue();
/*  72:    */   }
/*  73:    */   
/*  74:    */   public int getWatchableObjectInt(int par1)
/*  75:    */   {
/*  76: 98 */     return ((Integer)getWatchedObject(par1).getObject()).intValue();
/*  77:    */   }
/*  78:    */   
/*  79:    */   public float getWatchableObjectFloat(int par1)
/*  80:    */   {
/*  81:103 */     return ((Float)getWatchedObject(par1).getObject()).floatValue();
/*  82:    */   }
/*  83:    */   
/*  84:    */   public String getWatchableObjectString(int par1)
/*  85:    */   {
/*  86:111 */     return (String)getWatchedObject(par1).getObject();
/*  87:    */   }
/*  88:    */   
/*  89:    */   public ItemStack getWatchableObjectItemStack(int par1)
/*  90:    */   {
/*  91:119 */     return (ItemStack)getWatchedObject(par1).getObject();
/*  92:    */   }
/*  93:    */   
/*  94:    */   private WatchableObject getWatchedObject(int par1)
/*  95:    */   {
/*  96:127 */     this.lock.readLock().lock();
/*  97:    */     try
/*  98:    */     {
/*  99:132 */       var2 = (WatchableObject)this.watchedObjects.get(Integer.valueOf(par1));
/* 100:    */     }
/* 101:    */     catch (Throwable var6)
/* 102:    */     {
/* 103:    */       WatchableObject var2;
/* 104:136 */       CrashReport var4 = CrashReport.makeCrashReport(var6, "Getting synched entity data");
/* 105:137 */       CrashReportCategory var5 = var4.makeCategory("Synched entity data");
/* 106:138 */       var5.addCrashSection("Data ID", Integer.valueOf(par1));
/* 107:139 */       throw new ReportedException(var4);
/* 108:    */     }
/* 109:    */     WatchableObject var2;
/* 110:142 */     this.lock.readLock().unlock();
/* 111:143 */     return var2;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void updateObject(int par1, Object par2Obj)
/* 115:    */   {
/* 116:151 */     WatchableObject var3 = getWatchedObject(par1);
/* 117:153 */     if (ObjectUtils.notEqual(par2Obj, var3.getObject()))
/* 118:    */     {
/* 119:155 */       var3.setObject(par2Obj);
/* 120:156 */       this.field_151511_a.func_145781_i(par1);
/* 121:157 */       var3.setWatched(true);
/* 122:158 */       this.objectChanged = true;
/* 123:    */     }
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void setObjectWatched(int par1)
/* 127:    */   {
/* 128:164 */     getWatchedObject(par1).watched = true;
/* 129:165 */     this.objectChanged = true;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public boolean hasChanges()
/* 133:    */   {
/* 134:170 */     return this.objectChanged;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public static void writeWatchedListToPacketBuffer(List p_151507_0_, PacketBuffer p_151507_1_)
/* 138:    */     throws IOException
/* 139:    */   {
/* 140:179 */     if (p_151507_0_ != null)
/* 141:    */     {
/* 142:181 */       Iterator var2 = p_151507_0_.iterator();
/* 143:183 */       while (var2.hasNext())
/* 144:    */       {
/* 145:185 */         WatchableObject var3 = (WatchableObject)var2.next();
/* 146:186 */         writeWatchableObjectToPacketBuffer(p_151507_1_, var3);
/* 147:    */       }
/* 148:    */     }
/* 149:190 */     p_151507_1_.writeByte(127);
/* 150:    */   }
/* 151:    */   
/* 152:    */   public List getChanged()
/* 153:    */   {
/* 154:195 */     ArrayList var1 = null;
/* 155:197 */     if (this.objectChanged)
/* 156:    */     {
/* 157:199 */       this.lock.readLock().lock();
/* 158:200 */       Iterator var2 = this.watchedObjects.values().iterator();
/* 159:202 */       while (var2.hasNext())
/* 160:    */       {
/* 161:204 */         WatchableObject var3 = (WatchableObject)var2.next();
/* 162:206 */         if (var3.isWatched())
/* 163:    */         {
/* 164:208 */           var3.setWatched(false);
/* 165:210 */           if (var1 == null) {
/* 166:212 */             var1 = new ArrayList();
/* 167:    */           }
/* 168:215 */           var1.add(var3);
/* 169:    */         }
/* 170:    */       }
/* 171:219 */       this.lock.readLock().unlock();
/* 172:    */     }
/* 173:222 */     this.objectChanged = false;
/* 174:223 */     return var1;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void func_151509_a(PacketBuffer p_151509_1_)
/* 178:    */     throws IOException
/* 179:    */   {
/* 180:228 */     this.lock.readLock().lock();
/* 181:229 */     Iterator var2 = this.watchedObjects.values().iterator();
/* 182:231 */     while (var2.hasNext())
/* 183:    */     {
/* 184:233 */       WatchableObject var3 = (WatchableObject)var2.next();
/* 185:234 */       writeWatchableObjectToPacketBuffer(p_151509_1_, var3);
/* 186:    */     }
/* 187:237 */     this.lock.readLock().unlock();
/* 188:238 */     p_151509_1_.writeByte(127);
/* 189:    */   }
/* 190:    */   
/* 191:    */   public List getAllWatched()
/* 192:    */   {
/* 193:243 */     ArrayList var1 = null;
/* 194:244 */     this.lock.readLock().lock();
/* 195:    */     WatchableObject var3;
/* 196:247 */     for (Iterator var2 = this.watchedObjects.values().iterator(); var2.hasNext(); var1.add(var3))
/* 197:    */     {
/* 198:249 */       var3 = (WatchableObject)var2.next();
/* 199:251 */       if (var1 == null) {
/* 200:253 */         var1 = new ArrayList();
/* 201:    */       }
/* 202:    */     }
/* 203:257 */     this.lock.readLock().unlock();
/* 204:258 */     return var1;
/* 205:    */   }
/* 206:    */   
/* 207:    */   private static void writeWatchableObjectToPacketBuffer(PacketBuffer p_151510_0_, WatchableObject p_151510_1_)
/* 208:    */     throws IOException
/* 209:    */   {
/* 210:267 */     int var2 = (p_151510_1_.getObjectType() << 5 | p_151510_1_.getDataValueId() & 0x1F) & 0xFF;
/* 211:268 */     p_151510_0_.writeByte(var2);
/* 212:270 */     switch (p_151510_1_.getObjectType())
/* 213:    */     {
/* 214:    */     case 0: 
/* 215:273 */       p_151510_0_.writeByte(((Byte)p_151510_1_.getObject()).byteValue());
/* 216:274 */       break;
/* 217:    */     case 1: 
/* 218:277 */       p_151510_0_.writeShort(((Short)p_151510_1_.getObject()).shortValue());
/* 219:278 */       break;
/* 220:    */     case 2: 
/* 221:281 */       p_151510_0_.writeInt(((Integer)p_151510_1_.getObject()).intValue());
/* 222:282 */       break;
/* 223:    */     case 3: 
/* 224:285 */       p_151510_0_.writeFloat(((Float)p_151510_1_.getObject()).floatValue());
/* 225:286 */       break;
/* 226:    */     case 4: 
/* 227:289 */       p_151510_0_.writeStringToBuffer((String)p_151510_1_.getObject());
/* 228:290 */       break;
/* 229:    */     case 5: 
/* 230:293 */       ItemStack var4 = (ItemStack)p_151510_1_.getObject();
/* 231:294 */       p_151510_0_.writeItemStackToBuffer(var4);
/* 232:295 */       break;
/* 233:    */     case 6: 
/* 234:298 */       ChunkCoordinates var3 = (ChunkCoordinates)p_151510_1_.getObject();
/* 235:299 */       p_151510_0_.writeInt(var3.posX);
/* 236:300 */       p_151510_0_.writeInt(var3.posY);
/* 237:301 */       p_151510_0_.writeInt(var3.posZ);
/* 238:    */     }
/* 239:    */   }
/* 240:    */   
/* 241:    */   public static List readWatchedListFromPacketBuffer(PacketBuffer p_151508_0_)
/* 242:    */     throws IOException
/* 243:    */   {
/* 244:311 */     ArrayList var1 = null;
/* 245:313 */     for (byte var2 = p_151508_0_.readByte(); var2 != 127; var2 = p_151508_0_.readByte())
/* 246:    */     {
/* 247:315 */       if (var1 == null) {
/* 248:317 */         var1 = new ArrayList();
/* 249:    */       }
/* 250:320 */       int var3 = (var2 & 0xE0) >> 5;
/* 251:321 */       int var4 = var2 & 0x1F;
/* 252:322 */       WatchableObject var5 = null;
/* 253:324 */       switch (var3)
/* 254:    */       {
/* 255:    */       case 0: 
/* 256:327 */         var5 = new WatchableObject(var3, var4, Byte.valueOf(p_151508_0_.readByte()));
/* 257:328 */         break;
/* 258:    */       case 1: 
/* 259:331 */         var5 = new WatchableObject(var3, var4, Short.valueOf(p_151508_0_.readShort()));
/* 260:332 */         break;
/* 261:    */       case 2: 
/* 262:335 */         var5 = new WatchableObject(var3, var4, Integer.valueOf(p_151508_0_.readInt()));
/* 263:336 */         break;
/* 264:    */       case 3: 
/* 265:339 */         var5 = new WatchableObject(var3, var4, Float.valueOf(p_151508_0_.readFloat()));
/* 266:340 */         break;
/* 267:    */       case 4: 
/* 268:343 */         var5 = new WatchableObject(var3, var4, p_151508_0_.readStringFromBuffer(32767));
/* 269:344 */         break;
/* 270:    */       case 5: 
/* 271:347 */         var5 = new WatchableObject(var3, var4, p_151508_0_.readItemStackFromBuffer());
/* 272:348 */         break;
/* 273:    */       case 6: 
/* 274:351 */         int var6 = p_151508_0_.readInt();
/* 275:352 */         int var7 = p_151508_0_.readInt();
/* 276:353 */         int var8 = p_151508_0_.readInt();
/* 277:354 */         var5 = new WatchableObject(var3, var4, new ChunkCoordinates(var6, var7, var8));
/* 278:    */       }
/* 279:357 */       var1.add(var5);
/* 280:    */     }
/* 281:360 */     return var1;
/* 282:    */   }
/* 283:    */   
/* 284:    */   public void updateWatchedObjectsFromList(List par1List)
/* 285:    */   {
/* 286:365 */     this.lock.writeLock().lock();
/* 287:366 */     Iterator var2 = par1List.iterator();
/* 288:368 */     while (var2.hasNext())
/* 289:    */     {
/* 290:370 */       WatchableObject var3 = (WatchableObject)var2.next();
/* 291:371 */       WatchableObject var4 = (WatchableObject)this.watchedObjects.get(Integer.valueOf(var3.getDataValueId()));
/* 292:373 */       if (var4 != null)
/* 293:    */       {
/* 294:375 */         var4.setObject(var3.getObject());
/* 295:376 */         this.field_151511_a.func_145781_i(var3.getDataValueId());
/* 296:    */       }
/* 297:    */     }
/* 298:380 */     this.lock.writeLock().unlock();
/* 299:381 */     this.objectChanged = true;
/* 300:    */   }
/* 301:    */   
/* 302:    */   public boolean getIsBlank()
/* 303:    */   {
/* 304:386 */     return this.isBlank;
/* 305:    */   }
/* 306:    */   
/* 307:    */   public void func_111144_e()
/* 308:    */   {
/* 309:391 */     this.objectChanged = false;
/* 310:    */   }
/* 311:    */   
/* 312:    */   static
/* 313:    */   {
/* 314:396 */     dataTypes.put(Byte.class, Integer.valueOf(0));
/* 315:397 */     dataTypes.put(Short.class, Integer.valueOf(1));
/* 316:398 */     dataTypes.put(Integer.class, Integer.valueOf(2));
/* 317:399 */     dataTypes.put(Float.class, Integer.valueOf(3));
/* 318:400 */     dataTypes.put(String.class, Integer.valueOf(4));
/* 319:401 */     dataTypes.put(ItemStack.class, Integer.valueOf(5));
/* 320:402 */     dataTypes.put(ChunkCoordinates.class, Integer.valueOf(6));
/* 321:    */   }
/* 322:    */   
/* 323:    */   public static class WatchableObject
/* 324:    */   {
/* 325:    */     private final int objectType;
/* 326:    */     private final int dataValueId;
/* 327:    */     private Object watchedObject;
/* 328:    */     private boolean watched;
/* 329:    */     private static final String __OBFID = "CL_00001560";
/* 330:    */     
/* 331:    */     public WatchableObject(int par1, int par2, Object par3Obj)
/* 332:    */     {
/* 333:415 */       this.dataValueId = par2;
/* 334:416 */       this.watchedObject = par3Obj;
/* 335:417 */       this.objectType = par1;
/* 336:418 */       this.watched = true;
/* 337:    */     }
/* 338:    */     
/* 339:    */     public int getDataValueId()
/* 340:    */     {
/* 341:423 */       return this.dataValueId;
/* 342:    */     }
/* 343:    */     
/* 344:    */     public void setObject(Object par1Obj)
/* 345:    */     {
/* 346:428 */       this.watchedObject = par1Obj;
/* 347:    */     }
/* 348:    */     
/* 349:    */     public Object getObject()
/* 350:    */     {
/* 351:433 */       return this.watchedObject;
/* 352:    */     }
/* 353:    */     
/* 354:    */     public int getObjectType()
/* 355:    */     {
/* 356:438 */       return this.objectType;
/* 357:    */     }
/* 358:    */     
/* 359:    */     public boolean isWatched()
/* 360:    */     {
/* 361:443 */       return this.watched;
/* 362:    */     }
/* 363:    */     
/* 364:    */     public void setWatched(boolean par1)
/* 365:    */     {
/* 366:448 */       this.watched = par1;
/* 367:    */     }
/* 368:    */   }
/* 369:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.DataWatcher
 * JD-Core Version:    0.7.0.1
 */