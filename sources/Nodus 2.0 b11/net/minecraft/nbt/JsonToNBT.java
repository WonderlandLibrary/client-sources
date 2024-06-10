/*   1:    */ package net.minecraft.nbt;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.Stack;
/*   6:    */ import org.apache.logging.log4j.LogManager;
/*   7:    */ import org.apache.logging.log4j.Logger;
/*   8:    */ 
/*   9:    */ public class JsonToNBT
/*  10:    */ {
/*  11: 11 */   private static final Logger logger = ;
/*  12:    */   private static final String __OBFID = "CL_00001232";
/*  13:    */   
/*  14:    */   public static NBTBase func_150315_a(String p_150315_0_)
/*  15:    */     throws NBTException
/*  16:    */   {
/*  17: 16 */     p_150315_0_ = p_150315_0_.trim();
/*  18: 17 */     int var1 = func_150310_b(p_150315_0_);
/*  19: 19 */     if (var1 != 1) {
/*  20: 21 */       throw new NBTException("Encountered multiple top tags, only one expected");
/*  21:    */     }
/*  22: 25 */     Any var2 = null;
/*  23: 27 */     if (p_150315_0_.startsWith("{")) {
/*  24: 29 */       var2 = func_150316_a("tag", p_150315_0_);
/*  25:    */     } else {
/*  26: 33 */       var2 = func_150316_a(func_150313_b(p_150315_0_, false), func_150311_c(p_150315_0_, false));
/*  27:    */     }
/*  28: 36 */     return var2.func_150489_a();
/*  29:    */   }
/*  30:    */   
/*  31:    */   static int func_150310_b(String p_150310_0_)
/*  32:    */     throws NBTException
/*  33:    */   {
/*  34: 42 */     int var1 = 0;
/*  35: 43 */     boolean var2 = false;
/*  36: 44 */     Stack var3 = new Stack();
/*  37: 46 */     for (int var4 = 0; var4 < p_150310_0_.length(); var4++)
/*  38:    */     {
/*  39: 48 */       char var5 = p_150310_0_.charAt(var4);
/*  40: 50 */       if (var5 == '"')
/*  41:    */       {
/*  42: 52 */         if ((var4 > 0) && (p_150310_0_.charAt(var4 - 1) == '\\'))
/*  43:    */         {
/*  44: 54 */           if (!var2) {
/*  45: 56 */             throw new NBTException("Illegal use of \\\": " + p_150310_0_);
/*  46:    */           }
/*  47:    */         }
/*  48:    */         else {
/*  49: 61 */           var2 = !var2;
/*  50:    */         }
/*  51:    */       }
/*  52: 64 */       else if (!var2) {
/*  53: 66 */         if ((var5 != '{') && (var5 != '['))
/*  54:    */         {
/*  55: 68 */           if ((var5 == '}') && ((var3.isEmpty()) || (((Character)var3.pop()).charValue() != '{'))) {
/*  56: 70 */             throw new NBTException("Unbalanced curly brackets {}: " + p_150310_0_);
/*  57:    */           }
/*  58: 73 */           if ((var5 == ']') && ((var3.isEmpty()) || (((Character)var3.pop()).charValue() != '['))) {
/*  59: 75 */             throw new NBTException("Unbalanced square brackets []: " + p_150310_0_);
/*  60:    */           }
/*  61:    */         }
/*  62:    */         else
/*  63:    */         {
/*  64: 80 */           if (var3.isEmpty()) {
/*  65: 82 */             var1++;
/*  66:    */           }
/*  67: 85 */           var3.push(Character.valueOf(var5));
/*  68:    */         }
/*  69:    */       }
/*  70:    */     }
/*  71: 90 */     if (var2) {
/*  72: 92 */       throw new NBTException("Unbalanced quotation: " + p_150310_0_);
/*  73:    */     }
/*  74: 94 */     if (!var3.isEmpty()) {
/*  75: 96 */       throw new NBTException("Unbalanced brackets: " + p_150310_0_);
/*  76:    */     }
/*  77: 98 */     if ((var1 == 0) && (!p_150310_0_.isEmpty())) {
/*  78:100 */       return 1;
/*  79:    */     }
/*  80:104 */     return var1;
/*  81:    */   }
/*  82:    */   
/*  83:    */   static Any func_150316_a(String p_150316_0_, String p_150316_1_)
/*  84:    */     throws NBTException
/*  85:    */   {
/*  86:110 */     p_150316_1_ = p_150316_1_.trim();
/*  87:111 */     func_150310_b(p_150316_1_);
/*  88:117 */     if (p_150316_1_.startsWith("{"))
/*  89:    */     {
/*  90:119 */       if (!p_150316_1_.endsWith("}")) {
/*  91:121 */         throw new NBTException("Unable to locate ending bracket for: " + p_150316_1_);
/*  92:    */       }
/*  93:125 */       p_150316_1_ = p_150316_1_.substring(1, p_150316_1_.length() - 1);
/*  94:126 */       Compound var7 = new Compound(p_150316_0_);
/*  95:128 */       while (p_150316_1_.length() > 0)
/*  96:    */       {
/*  97:130 */         String var3 = func_150314_a(p_150316_1_, false);
/*  98:132 */         if (var3.length() > 0)
/*  99:    */         {
/* 100:134 */           String var4 = func_150313_b(var3, false);
/* 101:135 */           String var5 = func_150311_c(var3, false);
/* 102:136 */           var7.field_150491_b.add(func_150316_a(var4, var5));
/* 103:138 */           if (p_150316_1_.length() < var3.length() + 1) {
/* 104:    */             break;
/* 105:    */           }
/* 106:143 */           char var6 = p_150316_1_.charAt(var3.length());
/* 107:145 */           if ((var6 != ',') && (var6 != '{') && (var6 != '}') && (var6 != '[') && (var6 != ']')) {
/* 108:147 */             throw new NBTException("Unexpected token '" + var6 + "' at: " + p_150316_1_.substring(var3.length()));
/* 109:    */           }
/* 110:150 */           p_150316_1_ = p_150316_1_.substring(var3.length() + 1);
/* 111:    */         }
/* 112:    */       }
/* 113:154 */       return var7;
/* 114:    */     }
/* 115:157 */     if ((p_150316_1_.startsWith("[")) && (!p_150316_1_.matches("\\[[-\\d|,\\s]+\\]")))
/* 116:    */     {
/* 117:159 */       if (!p_150316_1_.endsWith("]")) {
/* 118:161 */         throw new NBTException("Unable to locate ending bracket for: " + p_150316_1_);
/* 119:    */       }
/* 120:165 */       p_150316_1_ = p_150316_1_.substring(1, p_150316_1_.length() - 1);
/* 121:166 */       List var2 = new List(p_150316_0_);
/* 122:168 */       while (p_150316_1_.length() > 0)
/* 123:    */       {
/* 124:170 */         String var3 = func_150314_a(p_150316_1_, true);
/* 125:172 */         if (var3.length() > 0)
/* 126:    */         {
/* 127:174 */           String var4 = func_150313_b(var3, true);
/* 128:175 */           String var5 = func_150311_c(var3, true);
/* 129:176 */           var2.field_150492_b.add(func_150316_a(var4, var5));
/* 130:178 */           if (p_150316_1_.length() < var3.length() + 1) {
/* 131:    */             break;
/* 132:    */           }
/* 133:183 */           char var6 = p_150316_1_.charAt(var3.length());
/* 134:185 */           if ((var6 != ',') && (var6 != '{') && (var6 != '}') && (var6 != '[') && (var6 != ']')) {
/* 135:187 */             throw new NBTException("Unexpected token '" + var6 + "' at: " + p_150316_1_.substring(var3.length()));
/* 136:    */           }
/* 137:190 */           p_150316_1_ = p_150316_1_.substring(var3.length() + 1);
/* 138:    */         }
/* 139:    */         else
/* 140:    */         {
/* 141:194 */           logger.debug(p_150316_1_);
/* 142:    */         }
/* 143:    */       }
/* 144:198 */       return var2;
/* 145:    */     }
/* 146:203 */     return new Primitive(p_150316_0_, p_150316_1_);
/* 147:    */   }
/* 148:    */   
/* 149:    */   private static String func_150314_a(String p_150314_0_, boolean p_150314_1_)
/* 150:    */     throws NBTException
/* 151:    */   {
/* 152:209 */     int var2 = func_150312_a(p_150314_0_, ':');
/* 153:211 */     if ((var2 < 0) && (!p_150314_1_)) {
/* 154:213 */       throw new NBTException("Unable to locate name/value separator for string: " + p_150314_0_);
/* 155:    */     }
/* 156:217 */     int var3 = func_150312_a(p_150314_0_, ',');
/* 157:219 */     if ((var3 >= 0) && (var3 < var2) && (!p_150314_1_)) {
/* 158:221 */       throw new NBTException("Name error at: " + p_150314_0_);
/* 159:    */     }
/* 160:225 */     if ((p_150314_1_) && ((var2 < 0) || (var2 > var3))) {
/* 161:227 */       var2 = -1;
/* 162:    */     }
/* 163:230 */     Stack var4 = new Stack();
/* 164:231 */     int var5 = var2 + 1;
/* 165:232 */     boolean var6 = false;
/* 166:233 */     boolean var7 = false;
/* 167:234 */     boolean var8 = false;
/* 168:236 */     for (int var9 = 0; var5 < p_150314_0_.length(); var5++)
/* 169:    */     {
/* 170:238 */       char var10 = p_150314_0_.charAt(var5);
/* 171:240 */       if (var10 == '"')
/* 172:    */       {
/* 173:242 */         if ((var5 > 0) && (p_150314_0_.charAt(var5 - 1) == '\\'))
/* 174:    */         {
/* 175:244 */           if (!var6) {
/* 176:246 */             throw new NBTException("Illegal use of \\\": " + p_150314_0_);
/* 177:    */           }
/* 178:    */         }
/* 179:    */         else
/* 180:    */         {
/* 181:251 */           var6 = !var6;
/* 182:253 */           if ((var6) && (!var8)) {
/* 183:255 */             var7 = true;
/* 184:    */           }
/* 185:258 */           if (!var6) {
/* 186:260 */             var9 = var5;
/* 187:    */           }
/* 188:    */         }
/* 189:    */       }
/* 190:264 */       else if (!var6) {
/* 191:266 */         if ((var10 != '{') && (var10 != '['))
/* 192:    */         {
/* 193:268 */           if ((var10 == '}') && ((var4.isEmpty()) || (((Character)var4.pop()).charValue() != '{'))) {
/* 194:270 */             throw new NBTException("Unbalanced curly brackets {}: " + p_150314_0_);
/* 195:    */           }
/* 196:273 */           if ((var10 == ']') && ((var4.isEmpty()) || (((Character)var4.pop()).charValue() != '['))) {
/* 197:275 */             throw new NBTException("Unbalanced square brackets []: " + p_150314_0_);
/* 198:    */           }
/* 199:278 */           if ((var10 == ',') && (var4.isEmpty())) {
/* 200:280 */             return p_150314_0_.substring(0, var5);
/* 201:    */           }
/* 202:    */         }
/* 203:    */         else
/* 204:    */         {
/* 205:285 */           var4.push(Character.valueOf(var10));
/* 206:    */         }
/* 207:    */       }
/* 208:289 */       if (!Character.isWhitespace(var10))
/* 209:    */       {
/* 210:291 */         if ((!var6) && (var7) && (var9 != var5)) {
/* 211:293 */           return p_150314_0_.substring(0, var9 + 1);
/* 212:    */         }
/* 213:296 */         var8 = true;
/* 214:    */       }
/* 215:    */     }
/* 216:300 */     return p_150314_0_.substring(0, var5);
/* 217:    */   }
/* 218:    */   
/* 219:    */   private static String func_150313_b(String p_150313_0_, boolean p_150313_1_)
/* 220:    */     throws NBTException
/* 221:    */   {
/* 222:307 */     if (p_150313_1_)
/* 223:    */     {
/* 224:309 */       p_150313_0_ = p_150313_0_.trim();
/* 225:311 */       if ((p_150313_0_.startsWith("{")) || (p_150313_0_.startsWith("["))) {
/* 226:313 */         return "";
/* 227:    */       }
/* 228:    */     }
/* 229:317 */     int var2 = p_150313_0_.indexOf(':');
/* 230:319 */     if (var2 < 0)
/* 231:    */     {
/* 232:321 */       if (p_150313_1_) {
/* 233:323 */         return "";
/* 234:    */       }
/* 235:327 */       throw new NBTException("Unable to locate name/value separator for string: " + p_150313_0_);
/* 236:    */     }
/* 237:332 */     return p_150313_0_.substring(0, var2).trim();
/* 238:    */   }
/* 239:    */   
/* 240:    */   private static String func_150311_c(String p_150311_0_, boolean p_150311_1_)
/* 241:    */     throws NBTException
/* 242:    */   {
/* 243:338 */     if (p_150311_1_)
/* 244:    */     {
/* 245:340 */       p_150311_0_ = p_150311_0_.trim();
/* 246:342 */       if ((p_150311_0_.startsWith("{")) || (p_150311_0_.startsWith("["))) {
/* 247:344 */         return p_150311_0_;
/* 248:    */       }
/* 249:    */     }
/* 250:348 */     int var2 = p_150311_0_.indexOf(':');
/* 251:350 */     if (var2 < 0)
/* 252:    */     {
/* 253:352 */       if (p_150311_1_) {
/* 254:354 */         return p_150311_0_;
/* 255:    */       }
/* 256:358 */       throw new NBTException("Unable to locate name/value separator for string: " + p_150311_0_);
/* 257:    */     }
/* 258:363 */     return p_150311_0_.substring(var2 + 1).trim();
/* 259:    */   }
/* 260:    */   
/* 261:    */   private static int func_150312_a(String p_150312_0_, char p_150312_1_)
/* 262:    */   {
/* 263:369 */     int var2 = 0;
/* 264:371 */     for (boolean var3 = false; var2 < p_150312_0_.length(); var2++)
/* 265:    */     {
/* 266:373 */       char var4 = p_150312_0_.charAt(var2);
/* 267:375 */       if (var4 == '"')
/* 268:    */       {
/* 269:377 */         if ((var2 <= 0) || (p_150312_0_.charAt(var2 - 1) != '\\')) {
/* 270:379 */           var3 = !var3;
/* 271:    */         }
/* 272:    */       }
/* 273:382 */       else if (!var3)
/* 274:    */       {
/* 275:384 */         if (var4 == p_150312_1_) {
/* 276:386 */           return var2;
/* 277:    */         }
/* 278:389 */         if ((var4 == '{') || (var4 == '[')) {
/* 279:391 */           return -1;
/* 280:    */         }
/* 281:    */       }
/* 282:    */     }
/* 283:396 */     return -1;
/* 284:    */   }
/* 285:    */   
/* 286:    */   static abstract class Any
/* 287:    */   {
/* 288:    */     protected String field_150490_a;
/* 289:    */     private static final String __OBFID = "CL_00001233";
/* 290:    */     
/* 291:    */     public abstract NBTBase func_150489_a();
/* 292:    */   }
/* 293:    */   
/* 294:    */   static class Compound
/* 295:    */     extends JsonToNBT.Any
/* 296:    */   {
/* 297:401 */     protected ArrayList field_150491_b = new ArrayList();
/* 298:    */     private static final String __OBFID = "CL_00001234";
/* 299:    */     
/* 300:    */     public Compound(String p_i45137_1_)
/* 301:    */     {
/* 302:406 */       this.field_150490_a = p_i45137_1_;
/* 303:    */     }
/* 304:    */     
/* 305:    */     public NBTBase func_150489_a()
/* 306:    */     {
/* 307:411 */       NBTTagCompound var1 = new NBTTagCompound();
/* 308:412 */       Iterator var2 = this.field_150491_b.iterator();
/* 309:414 */       while (var2.hasNext())
/* 310:    */       {
/* 311:416 */         JsonToNBT.Any var3 = (JsonToNBT.Any)var2.next();
/* 312:417 */         var1.setTag(var3.field_150490_a, var3.func_150489_a());
/* 313:    */       }
/* 314:420 */       return var1;
/* 315:    */     }
/* 316:    */   }
/* 317:    */   
/* 318:    */   static class List
/* 319:    */     extends JsonToNBT.Any
/* 320:    */   {
/* 321:426 */     protected ArrayList field_150492_b = new ArrayList();
/* 322:    */     private static final String __OBFID = "CL_00001235";
/* 323:    */     
/* 324:    */     public List(String p_i45138_1_)
/* 325:    */     {
/* 326:431 */       this.field_150490_a = p_i45138_1_;
/* 327:    */     }
/* 328:    */     
/* 329:    */     public NBTBase func_150489_a()
/* 330:    */     {
/* 331:436 */       NBTTagList var1 = new NBTTagList();
/* 332:437 */       Iterator var2 = this.field_150492_b.iterator();
/* 333:439 */       while (var2.hasNext())
/* 334:    */       {
/* 335:441 */         JsonToNBT.Any var3 = (JsonToNBT.Any)var2.next();
/* 336:442 */         var1.appendTag(var3.func_150489_a());
/* 337:    */       }
/* 338:445 */       return var1;
/* 339:    */     }
/* 340:    */   }
/* 341:    */   
/* 342:    */   static class Primitive
/* 343:    */     extends JsonToNBT.Any
/* 344:    */   {
/* 345:    */     protected String field_150493_b;
/* 346:    */     private static final String __OBFID = "CL_00001236";
/* 347:    */     
/* 348:    */     public Primitive(String p_i45139_1_, String p_i45139_2_)
/* 349:    */     {
/* 350:464 */       this.field_150490_a = p_i45139_1_;
/* 351:465 */       this.field_150493_b = p_i45139_2_;
/* 352:    */     }
/* 353:    */     
/* 354:    */     public NBTBase func_150489_a()
/* 355:    */     {
/* 356:    */       try
/* 357:    */       {
/* 358:472 */         if (this.field_150493_b.matches("[-+]?[0-9]*\\.?[0-9]+[d|D]")) {
/* 359:474 */           return new NBTTagDouble(Double.parseDouble(this.field_150493_b.substring(0, this.field_150493_b.length() - 1)));
/* 360:    */         }
/* 361:476 */         if (this.field_150493_b.matches("[-+]?[0-9]*\\.?[0-9]+[f|F]")) {
/* 362:478 */           return new NBTTagFloat(Float.parseFloat(this.field_150493_b.substring(0, this.field_150493_b.length() - 1)));
/* 363:    */         }
/* 364:480 */         if (this.field_150493_b.matches("[-+]?[0-9]+[b|B]")) {
/* 365:482 */           return new NBTTagByte(Byte.parseByte(this.field_150493_b.substring(0, this.field_150493_b.length() - 1)));
/* 366:    */         }
/* 367:484 */         if (this.field_150493_b.matches("[-+]?[0-9]+[l|L]")) {
/* 368:486 */           return new NBTTagLong(Long.parseLong(this.field_150493_b.substring(0, this.field_150493_b.length() - 1)));
/* 369:    */         }
/* 370:488 */         if (this.field_150493_b.matches("[-+]?[0-9]+[s|S]")) {
/* 371:490 */           return new NBTTagShort(Short.parseShort(this.field_150493_b.substring(0, this.field_150493_b.length() - 1)));
/* 372:    */         }
/* 373:492 */         if (this.field_150493_b.matches("[-+]?[0-9]+")) {
/* 374:494 */           return new NBTTagInt(Integer.parseInt(this.field_150493_b.substring(0, this.field_150493_b.length())));
/* 375:    */         }
/* 376:496 */         if (this.field_150493_b.matches("[-+]?[0-9]*\\.?[0-9]+")) {
/* 377:498 */           return new NBTTagDouble(Double.parseDouble(this.field_150493_b.substring(0, this.field_150493_b.length())));
/* 378:    */         }
/* 379:500 */         if ((!this.field_150493_b.equalsIgnoreCase("true")) && (!this.field_150493_b.equalsIgnoreCase("false")))
/* 380:    */         {
/* 381:502 */           if ((this.field_150493_b.startsWith("[")) && (this.field_150493_b.endsWith("]")))
/* 382:    */           {
/* 383:504 */             if (this.field_150493_b.length() > 2)
/* 384:    */             {
/* 385:506 */               String var1 = this.field_150493_b.substring(1, this.field_150493_b.length() - 1);
/* 386:507 */               String[] var2 = var1.split(",");
/* 387:    */               try
/* 388:    */               {
/* 389:511 */                 if (var2.length <= 1) {
/* 390:513 */                   return new NBTTagIntArray(new int[] { Integer.parseInt(var1.trim()) });
/* 391:    */                 }
/* 392:517 */                 int[] var3 = new int[var2.length];
/* 393:519 */                 for (int var4 = 0; var4 < var2.length; var4++) {
/* 394:521 */                   var3[var4] = Integer.parseInt(var2[var4].trim());
/* 395:    */                 }
/* 396:524 */                 return new NBTTagIntArray(var3);
/* 397:    */               }
/* 398:    */               catch (NumberFormatException var5)
/* 399:    */               {
/* 400:529 */                 return new NBTTagString(this.field_150493_b);
/* 401:    */               }
/* 402:    */             }
/* 403:534 */             return new NBTTagIntArray();
/* 404:    */           }
/* 405:539 */           if ((this.field_150493_b.startsWith("\"")) && (this.field_150493_b.endsWith("\"")) && (this.field_150493_b.length() > 2)) {
/* 406:541 */             this.field_150493_b = this.field_150493_b.substring(1, this.field_150493_b.length() - 1);
/* 407:    */           }
/* 408:544 */           this.field_150493_b = this.field_150493_b.replaceAll("\\\\\"", "\"");
/* 409:545 */           return new NBTTagString(this.field_150493_b);
/* 410:    */         }
/* 411:550 */         return new NBTTagByte((byte)(Boolean.parseBoolean(this.field_150493_b) ? 1 : 0));
/* 412:    */       }
/* 413:    */       catch (NumberFormatException var6)
/* 414:    */       {
/* 415:555 */         this.field_150493_b = this.field_150493_b.replaceAll("\\\\\"", "\"");
/* 416:    */       }
/* 417:556 */       return new NBTTagString(this.field_150493_b);
/* 418:    */     }
/* 419:    */   }
/* 420:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.nbt.JsonToNBT
 * JD-Core Version:    0.7.0.1
 */