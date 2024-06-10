/*   1:    */ package net.minecraft.util;
/*   2:    */ 
/*   3:    */ import com.google.gson.JsonDeserializationContext;
/*   4:    */ import com.google.gson.JsonDeserializer;
/*   5:    */ import com.google.gson.JsonElement;
/*   6:    */ import com.google.gson.JsonObject;
/*   7:    */ import com.google.gson.JsonPrimitive;
/*   8:    */ import com.google.gson.JsonSerializationContext;
/*   9:    */ import com.google.gson.JsonSerializer;
/*  10:    */ import java.lang.reflect.Type;
/*  11:    */ import net.minecraft.event.ClickEvent;
/*  12:    */ import net.minecraft.event.ClickEvent.Action;
/*  13:    */ import net.minecraft.event.HoverEvent;
/*  14:    */ import net.minecraft.event.HoverEvent.Action;
/*  15:    */ 
/*  16:    */ public class ChatStyle
/*  17:    */ {
/*  18:    */   private ChatStyle parentStyle;
/*  19:    */   private EnumChatFormatting color;
/*  20:    */   private Boolean bold;
/*  21:    */   private Boolean italic;
/*  22:    */   private Boolean underlined;
/*  23:    */   private Boolean strikethrough;
/*  24:    */   private Boolean obfuscated;
/*  25:    */   private ClickEvent chatClickEvent;
/*  26:    */   private HoverEvent chatHoverEvent;
/*  27: 31 */   private static final ChatStyle rootStyle = new ChatStyle()
/*  28:    */   {
/*  29:    */     private static final String __OBFID = "CL_00001267";
/*  30:    */     
/*  31:    */     public EnumChatFormatting getColor()
/*  32:    */     {
/*  33: 36 */       return null;
/*  34:    */     }
/*  35:    */     
/*  36:    */     public boolean getBold()
/*  37:    */     {
/*  38: 40 */       return false;
/*  39:    */     }
/*  40:    */     
/*  41:    */     public boolean getItalic()
/*  42:    */     {
/*  43: 44 */       return false;
/*  44:    */     }
/*  45:    */     
/*  46:    */     public boolean getStrikethrough()
/*  47:    */     {
/*  48: 48 */       return false;
/*  49:    */     }
/*  50:    */     
/*  51:    */     public boolean getUnderlined()
/*  52:    */     {
/*  53: 52 */       return false;
/*  54:    */     }
/*  55:    */     
/*  56:    */     public boolean getObfuscated()
/*  57:    */     {
/*  58: 56 */       return false;
/*  59:    */     }
/*  60:    */     
/*  61:    */     public ClickEvent getChatClickEvent()
/*  62:    */     {
/*  63: 60 */       return null;
/*  64:    */     }
/*  65:    */     
/*  66:    */     public HoverEvent getChatHoverEvent()
/*  67:    */     {
/*  68: 64 */       return null;
/*  69:    */     }
/*  70:    */     
/*  71:    */     public ChatStyle setColor(EnumChatFormatting p_150238_1_)
/*  72:    */     {
/*  73: 68 */       throw new UnsupportedOperationException();
/*  74:    */     }
/*  75:    */     
/*  76:    */     public ChatStyle setBold(Boolean p_150227_1_)
/*  77:    */     {
/*  78: 72 */       throw new UnsupportedOperationException();
/*  79:    */     }
/*  80:    */     
/*  81:    */     public ChatStyle setItalic(Boolean p_150217_1_)
/*  82:    */     {
/*  83: 76 */       throw new UnsupportedOperationException();
/*  84:    */     }
/*  85:    */     
/*  86:    */     public ChatStyle setStrikethrough(Boolean p_150225_1_)
/*  87:    */     {
/*  88: 80 */       throw new UnsupportedOperationException();
/*  89:    */     }
/*  90:    */     
/*  91:    */     public ChatStyle setUnderlined(Boolean p_150228_1_)
/*  92:    */     {
/*  93: 84 */       throw new UnsupportedOperationException();
/*  94:    */     }
/*  95:    */     
/*  96:    */     public ChatStyle setObfuscated(Boolean p_150237_1_)
/*  97:    */     {
/*  98: 88 */       throw new UnsupportedOperationException();
/*  99:    */     }
/* 100:    */     
/* 101:    */     public ChatStyle setChatClickEvent(ClickEvent p_150241_1_)
/* 102:    */     {
/* 103: 92 */       throw new UnsupportedOperationException();
/* 104:    */     }
/* 105:    */     
/* 106:    */     public ChatStyle setChatHoverEvent(HoverEvent p_150209_1_)
/* 107:    */     {
/* 108: 96 */       throw new UnsupportedOperationException();
/* 109:    */     }
/* 110:    */     
/* 111:    */     public ChatStyle setParentStyle(ChatStyle p_150221_1_)
/* 112:    */     {
/* 113:100 */       throw new UnsupportedOperationException();
/* 114:    */     }
/* 115:    */     
/* 116:    */     public String toString()
/* 117:    */     {
/* 118:104 */       return "Style.ROOT";
/* 119:    */     }
/* 120:    */     
/* 121:    */     public ChatStyle createShallowCopy()
/* 122:    */     {
/* 123:108 */       return this;
/* 124:    */     }
/* 125:    */     
/* 126:    */     public ChatStyle createDeepCopy()
/* 127:    */     {
/* 128:112 */       return this;
/* 129:    */     }
/* 130:    */     
/* 131:    */     public String getFormattingCode()
/* 132:    */     {
/* 133:116 */       return "";
/* 134:    */     }
/* 135:    */   };
/* 136:    */   private static final String __OBFID = "CL_00001266";
/* 137:    */   
/* 138:    */   public EnumChatFormatting getColor()
/* 139:    */   {
/* 140:126 */     return this.color == null ? getParent().getColor() : this.color;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public boolean getBold()
/* 144:    */   {
/* 145:134 */     return this.bold == null ? getParent().getBold() : this.bold.booleanValue();
/* 146:    */   }
/* 147:    */   
/* 148:    */   public boolean getItalic()
/* 149:    */   {
/* 150:142 */     return this.italic == null ? getParent().getItalic() : this.italic.booleanValue();
/* 151:    */   }
/* 152:    */   
/* 153:    */   public boolean getStrikethrough()
/* 154:    */   {
/* 155:150 */     return this.strikethrough == null ? getParent().getStrikethrough() : this.strikethrough.booleanValue();
/* 156:    */   }
/* 157:    */   
/* 158:    */   public boolean getUnderlined()
/* 159:    */   {
/* 160:158 */     return this.underlined == null ? getParent().getUnderlined() : this.underlined.booleanValue();
/* 161:    */   }
/* 162:    */   
/* 163:    */   public boolean getObfuscated()
/* 164:    */   {
/* 165:166 */     return this.obfuscated == null ? getParent().getObfuscated() : this.obfuscated.booleanValue();
/* 166:    */   }
/* 167:    */   
/* 168:    */   public boolean isEmpty()
/* 169:    */   {
/* 170:174 */     return (this.bold == null) && (this.italic == null) && (this.strikethrough == null) && (this.underlined == null) && (this.obfuscated == null) && (this.color == null) && (this.chatClickEvent == null) && (this.chatHoverEvent == null);
/* 171:    */   }
/* 172:    */   
/* 173:    */   public ClickEvent getChatClickEvent()
/* 174:    */   {
/* 175:182 */     return this.chatClickEvent == null ? getParent().getChatClickEvent() : this.chatClickEvent;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public HoverEvent getChatHoverEvent()
/* 179:    */   {
/* 180:190 */     return this.chatHoverEvent == null ? getParent().getChatHoverEvent() : this.chatHoverEvent;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public ChatStyle setColor(EnumChatFormatting p_150238_1_)
/* 184:    */   {
/* 185:199 */     this.color = p_150238_1_;
/* 186:200 */     return this;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public ChatStyle setBold(Boolean p_150227_1_)
/* 190:    */   {
/* 191:209 */     this.bold = p_150227_1_;
/* 192:210 */     return this;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public ChatStyle setItalic(Boolean p_150217_1_)
/* 196:    */   {
/* 197:219 */     this.italic = p_150217_1_;
/* 198:220 */     return this;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public ChatStyle setStrikethrough(Boolean p_150225_1_)
/* 202:    */   {
/* 203:229 */     this.strikethrough = p_150225_1_;
/* 204:230 */     return this;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public ChatStyle setUnderlined(Boolean p_150228_1_)
/* 208:    */   {
/* 209:239 */     this.underlined = p_150228_1_;
/* 210:240 */     return this;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public ChatStyle setObfuscated(Boolean p_150237_1_)
/* 214:    */   {
/* 215:249 */     this.obfuscated = p_150237_1_;
/* 216:250 */     return this;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public ChatStyle setChatClickEvent(ClickEvent p_150241_1_)
/* 220:    */   {
/* 221:258 */     this.chatClickEvent = p_150241_1_;
/* 222:259 */     return this;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public ChatStyle setChatHoverEvent(HoverEvent p_150209_1_)
/* 226:    */   {
/* 227:267 */     this.chatHoverEvent = p_150209_1_;
/* 228:268 */     return this;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public ChatStyle setParentStyle(ChatStyle p_150221_1_)
/* 232:    */   {
/* 233:277 */     this.parentStyle = p_150221_1_;
/* 234:278 */     return this;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public String getFormattingCode()
/* 238:    */   {
/* 239:286 */     if (isEmpty()) {
/* 240:288 */       return this.parentStyle != null ? this.parentStyle.getFormattingCode() : "";
/* 241:    */     }
/* 242:292 */     StringBuilder var1 = new StringBuilder();
/* 243:294 */     if (getColor() != null) {
/* 244:296 */       var1.append(getColor());
/* 245:    */     }
/* 246:299 */     if (getBold()) {
/* 247:301 */       var1.append(EnumChatFormatting.BOLD);
/* 248:    */     }
/* 249:304 */     if (getItalic()) {
/* 250:306 */       var1.append(EnumChatFormatting.ITALIC);
/* 251:    */     }
/* 252:309 */     if (getUnderlined()) {
/* 253:311 */       var1.append(EnumChatFormatting.UNDERLINE);
/* 254:    */     }
/* 255:314 */     if (getObfuscated()) {
/* 256:316 */       var1.append(EnumChatFormatting.OBFUSCATED);
/* 257:    */     }
/* 258:319 */     if (getStrikethrough()) {
/* 259:321 */       var1.append(EnumChatFormatting.STRIKETHROUGH);
/* 260:    */     }
/* 261:324 */     return var1.toString();
/* 262:    */   }
/* 263:    */   
/* 264:    */   private ChatStyle getParent()
/* 265:    */   {
/* 266:333 */     return this.parentStyle == null ? rootStyle : this.parentStyle;
/* 267:    */   }
/* 268:    */   
/* 269:    */   public String toString()
/* 270:    */   {
/* 271:338 */     return "Style{hasParent=" + (this.parentStyle != null) + ", color=" + this.color + ", bold=" + this.bold + ", italic=" + this.italic + ", underlined=" + this.underlined + ", obfuscated=" + this.obfuscated + ", clickEvent=" + getChatClickEvent() + ", hoverEvent=" + getChatHoverEvent() + '}';
/* 272:    */   }
/* 273:    */   
/* 274:    */   public boolean equals(Object par1Obj)
/* 275:    */   {
/* 276:343 */     if (this == par1Obj) {
/* 277:345 */       return true;
/* 278:    */     }
/* 279:347 */     if (!(par1Obj instanceof ChatStyle)) {
/* 280:349 */       return false;
/* 281:    */     }
/* 282:353 */     ChatStyle var2 = (ChatStyle)par1Obj;
/* 283:356 */     if ((getBold() == var2.getBold()) && (getColor() == var2.getColor()) && (getItalic() == var2.getItalic()) && (getObfuscated() == var2.getObfuscated()) && (getStrikethrough() == var2.getStrikethrough()) && (getUnderlined() == var2.getUnderlined())) {
/* 284:360 */       if (getChatClickEvent() != null ? 
/* 285:    */       
/* 286:362 */         getChatClickEvent().equals(var2.getChatClickEvent()) : 
/* 287:    */         
/* 288:    */ 
/* 289:    */ 
/* 290:    */ 
/* 291:367 */         var2.getChatClickEvent() == null) {
/* 292:372 */         if (getChatHoverEvent() != null ? 
/* 293:    */         
/* 294:374 */           getChatHoverEvent().equals(var2.getChatHoverEvent()) : 
/* 295:    */           
/* 296:    */ 
/* 297:    */ 
/* 298:    */ 
/* 299:379 */           var2.getChatHoverEvent() == null)
/* 300:    */         {
/* 301:384 */           boolean var10000 = true;
/* 302:385 */           return var10000;
/* 303:    */         }
/* 304:    */       }
/* 305:    */     }
/* 306:389 */     boolean var10000 = false;
/* 307:390 */     return var10000;
/* 308:    */   }
/* 309:    */   
/* 310:    */   public int hashCode()
/* 311:    */   {
/* 312:396 */     int var1 = this.color.hashCode();
/* 313:397 */     var1 = 31 * var1 + this.bold.hashCode();
/* 314:398 */     var1 = 31 * var1 + this.italic.hashCode();
/* 315:399 */     var1 = 31 * var1 + this.underlined.hashCode();
/* 316:400 */     var1 = 31 * var1 + this.strikethrough.hashCode();
/* 317:401 */     var1 = 31 * var1 + this.obfuscated.hashCode();
/* 318:402 */     var1 = 31 * var1 + this.chatClickEvent.hashCode();
/* 319:403 */     var1 = 31 * var1 + this.chatHoverEvent.hashCode();
/* 320:404 */     return var1;
/* 321:    */   }
/* 322:    */   
/* 323:    */   public ChatStyle createShallowCopy()
/* 324:    */   {
/* 325:414 */     ChatStyle var1 = new ChatStyle();
/* 326:415 */     var1.bold = this.bold;
/* 327:416 */     var1.italic = this.italic;
/* 328:417 */     var1.strikethrough = this.strikethrough;
/* 329:418 */     var1.underlined = this.underlined;
/* 330:419 */     var1.obfuscated = this.obfuscated;
/* 331:420 */     var1.color = this.color;
/* 332:421 */     var1.chatClickEvent = this.chatClickEvent;
/* 333:422 */     var1.chatHoverEvent = this.chatHoverEvent;
/* 334:423 */     var1.parentStyle = this.parentStyle;
/* 335:424 */     return var1;
/* 336:    */   }
/* 337:    */   
/* 338:    */   public ChatStyle createDeepCopy()
/* 339:    */   {
/* 340:433 */     ChatStyle var1 = new ChatStyle();
/* 341:434 */     var1.setBold(Boolean.valueOf(getBold()));
/* 342:435 */     var1.setItalic(Boolean.valueOf(getItalic()));
/* 343:436 */     var1.setStrikethrough(Boolean.valueOf(getStrikethrough()));
/* 344:437 */     var1.setUnderlined(Boolean.valueOf(getUnderlined()));
/* 345:438 */     var1.setObfuscated(Boolean.valueOf(getObfuscated()));
/* 346:439 */     var1.setColor(getColor());
/* 347:440 */     var1.setChatClickEvent(getChatClickEvent());
/* 348:441 */     var1.setChatHoverEvent(getChatHoverEvent());
/* 349:442 */     return var1;
/* 350:    */   }
/* 351:    */   
/* 352:    */   public static class Serializer
/* 353:    */     implements JsonDeserializer, JsonSerializer
/* 354:    */   {
/* 355:    */     private static final String __OBFID = "CL_00001268";
/* 356:    */     
/* 357:    */     public ChatStyle deserialize(JsonElement p_150204_1_, Type p_150204_2_, JsonDeserializationContext p_150204_3_)
/* 358:    */     {
/* 359:451 */       if (p_150204_1_.isJsonObject())
/* 360:    */       {
/* 361:453 */         ChatStyle var4 = new ChatStyle();
/* 362:454 */         JsonObject var5 = p_150204_1_.getAsJsonObject();
/* 363:456 */         if (var5.has("bold")) {
/* 364:458 */           var4.bold = Boolean.valueOf(var5.get("bold").getAsBoolean());
/* 365:    */         }
/* 366:461 */         if (var5.has("italic")) {
/* 367:463 */           var4.italic = Boolean.valueOf(var5.get("italic").getAsBoolean());
/* 368:    */         }
/* 369:466 */         if (var5.has("underlined")) {
/* 370:468 */           var4.underlined = Boolean.valueOf(var5.get("underlined").getAsBoolean());
/* 371:    */         }
/* 372:471 */         if (var5.has("strikethrough")) {
/* 373:473 */           var4.strikethrough = Boolean.valueOf(var5.get("strikethrough").getAsBoolean());
/* 374:    */         }
/* 375:476 */         if (var5.has("obfuscated")) {
/* 376:478 */           var4.obfuscated = Boolean.valueOf(var5.get("obfuscated").getAsBoolean());
/* 377:    */         }
/* 378:481 */         if (var5.has("color")) {
/* 379:483 */           var4.color = ((EnumChatFormatting)p_150204_3_.deserialize(var5.get("color"), EnumChatFormatting.class));
/* 380:    */         }
/* 381:488 */         if (var5.has("clickEvent"))
/* 382:    */         {
/* 383:490 */           JsonObject var6 = var5.getAsJsonObject("clickEvent");
/* 384:491 */           ClickEvent.Action var7 = ClickEvent.Action.getValueByCanonicalName(var6.getAsJsonPrimitive("action").getAsString());
/* 385:492 */           String var8 = var6.getAsJsonPrimitive("value").getAsString();
/* 386:494 */           if ((var7 != null) && (var8 != null) && (var7.shouldAllowInChat())) {
/* 387:496 */             var4.chatClickEvent = new ClickEvent(var7, var8);
/* 388:    */           }
/* 389:    */         }
/* 390:500 */         if (var5.has("hoverEvent"))
/* 391:    */         {
/* 392:502 */           JsonObject var6 = var5.getAsJsonObject("hoverEvent");
/* 393:503 */           HoverEvent.Action var9 = HoverEvent.Action.getValueByCanonicalName(var6.getAsJsonPrimitive("action").getAsString());
/* 394:504 */           IChatComponent var10 = (IChatComponent)p_150204_3_.deserialize(var6.get("value"), IChatComponent.class);
/* 395:506 */           if ((var9 != null) && (var10 != null) && (var9.shouldAllowInChat())) {
/* 396:508 */             var4.chatHoverEvent = new HoverEvent(var9, var10);
/* 397:    */           }
/* 398:    */         }
/* 399:512 */         return var4;
/* 400:    */       }
/* 401:516 */       return null;
/* 402:    */     }
/* 403:    */     
/* 404:    */     public JsonElement serialize(ChatStyle p_150203_1_, Type p_150203_2_, JsonSerializationContext p_150203_3_)
/* 405:    */     {
/* 406:522 */       if (p_150203_1_.isEmpty()) {
/* 407:524 */         return null;
/* 408:    */       }
/* 409:528 */       JsonObject var4 = new JsonObject();
/* 410:530 */       if (p_150203_1_.bold != null) {
/* 411:532 */         var4.addProperty("bold", p_150203_1_.bold);
/* 412:    */       }
/* 413:535 */       if (p_150203_1_.italic != null) {
/* 414:537 */         var4.addProperty("italic", p_150203_1_.italic);
/* 415:    */       }
/* 416:540 */       if (p_150203_1_.underlined != null) {
/* 417:542 */         var4.addProperty("underlined", p_150203_1_.underlined);
/* 418:    */       }
/* 419:545 */       if (p_150203_1_.strikethrough != null) {
/* 420:547 */         var4.addProperty("strikethrough", p_150203_1_.strikethrough);
/* 421:    */       }
/* 422:550 */       if (p_150203_1_.obfuscated != null) {
/* 423:552 */         var4.addProperty("obfuscated", p_150203_1_.obfuscated);
/* 424:    */       }
/* 425:555 */       if (p_150203_1_.color != null) {
/* 426:557 */         var4.add("color", p_150203_3_.serialize(p_150203_1_.color));
/* 427:    */       }
/* 428:562 */       if (p_150203_1_.chatClickEvent != null)
/* 429:    */       {
/* 430:564 */         JsonObject var5 = new JsonObject();
/* 431:565 */         var5.addProperty("action", p_150203_1_.chatClickEvent.getAction().getCanonicalName());
/* 432:566 */         var5.addProperty("value", p_150203_1_.chatClickEvent.getValue());
/* 433:567 */         var4.add("clickEvent", var5);
/* 434:    */       }
/* 435:570 */       if (p_150203_1_.chatHoverEvent != null)
/* 436:    */       {
/* 437:572 */         JsonObject var5 = new JsonObject();
/* 438:573 */         var5.addProperty("action", p_150203_1_.chatHoverEvent.getAction().getCanonicalName());
/* 439:574 */         var5.add("value", p_150203_3_.serialize(p_150203_1_.chatHoverEvent.getValue()));
/* 440:575 */         var4.add("hoverEvent", var5);
/* 441:    */       }
/* 442:578 */       return var4;
/* 443:    */     }
/* 444:    */     
/* 445:    */     public JsonElement serialize(Object par1Obj, Type par2Type, JsonSerializationContext par3JsonSerializationContext)
/* 446:    */     {
/* 447:584 */       return serialize((ChatStyle)par1Obj, par2Type, par3JsonSerializationContext);
/* 448:    */     }
/* 449:    */   }
/* 450:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.util.ChatStyle
 * JD-Core Version:    0.7.0.1
 */