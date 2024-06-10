/*   1:    */ package net.minecraft.command;
/*   2:    */ 
/*   3:    */ import com.google.common.primitives.Doubles;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Collection;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.List;
/*   8:    */ import net.minecraft.block.Block;
/*   9:    */ import net.minecraft.entity.player.EntityPlayerMP;
/*  10:    */ import net.minecraft.item.Item;
/*  11:    */ import net.minecraft.server.MinecraftServer;
/*  12:    */ import net.minecraft.server.management.ServerConfigurationManager;
/*  13:    */ import net.minecraft.util.ChatComponentText;
/*  14:    */ import net.minecraft.util.ChatComponentTranslation;
/*  15:    */ import net.minecraft.util.ChatStyle;
/*  16:    */ import net.minecraft.util.EnumChatFormatting;
/*  17:    */ import net.minecraft.util.IChatComponent;
/*  18:    */ import net.minecraft.util.RegistryNamespaced;
/*  19:    */ 
/*  20:    */ public abstract class CommandBase
/*  21:    */   implements ICommand
/*  22:    */ {
/*  23:    */   private static IAdminCommand theAdmin;
/*  24:    */   private static final String __OBFID = "CL_00001739";
/*  25:    */   
/*  26:    */   public int getRequiredPermissionLevel()
/*  27:    */   {
/*  28: 27 */     return 4;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public List getCommandAliases()
/*  32:    */   {
/*  33: 32 */     return null;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender)
/*  37:    */   {
/*  38: 40 */     return par1ICommandSender.canCommandSenderUseCommand(getRequiredPermissionLevel(), getCommandName());
/*  39:    */   }
/*  40:    */   
/*  41:    */   public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/*  42:    */   {
/*  43: 48 */     return null;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public static int parseInt(ICommandSender par0ICommandSender, String par1Str)
/*  47:    */   {
/*  48:    */     try
/*  49:    */     {
/*  50: 58 */       return Integer.parseInt(par1Str);
/*  51:    */     }
/*  52:    */     catch (NumberFormatException var3)
/*  53:    */     {
/*  54: 62 */       throw new NumberInvalidException("commands.generic.num.invalid", new Object[] { par1Str });
/*  55:    */     }
/*  56:    */   }
/*  57:    */   
/*  58:    */   public static int parseIntWithMin(ICommandSender par0ICommandSender, String par1Str, int par2)
/*  59:    */   {
/*  60: 71 */     return parseIntBounded(par0ICommandSender, par1Str, par2, 2147483647);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public static int parseIntBounded(ICommandSender par0ICommandSender, String par1Str, int par2, int par3)
/*  64:    */   {
/*  65: 79 */     int var4 = parseInt(par0ICommandSender, par1Str);
/*  66: 81 */     if (var4 < par2) {
/*  67: 83 */       throw new NumberInvalidException("commands.generic.num.tooSmall", new Object[] { Integer.valueOf(var4), Integer.valueOf(par2) });
/*  68:    */     }
/*  69: 85 */     if (var4 > par3) {
/*  70: 87 */       throw new NumberInvalidException("commands.generic.num.tooBig", new Object[] { Integer.valueOf(var4), Integer.valueOf(par3) });
/*  71:    */     }
/*  72: 91 */     return var4;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public static double parseDouble(ICommandSender par0ICommandSender, String par1Str)
/*  76:    */   {
/*  77:    */     try
/*  78:    */     {
/*  79:102 */       double var2 = Double.parseDouble(par1Str);
/*  80:104 */       if (!Doubles.isFinite(var2)) {
/*  81:106 */         throw new NumberInvalidException("commands.generic.num.invalid", new Object[] { par1Str });
/*  82:    */       }
/*  83:110 */       return var2;
/*  84:    */     }
/*  85:    */     catch (NumberFormatException var4)
/*  86:    */     {
/*  87:115 */       throw new NumberInvalidException("commands.generic.num.invalid", new Object[] { par1Str });
/*  88:    */     }
/*  89:    */   }
/*  90:    */   
/*  91:    */   public static double parseDoubleWithMin(ICommandSender par0ICommandSender, String par1Str, double par2)
/*  92:    */   {
/*  93:125 */     return parseDoubleBounded(par0ICommandSender, par1Str, par2, 1.7976931348623157E+308D);
/*  94:    */   }
/*  95:    */   
/*  96:    */   public static double parseDoubleBounded(ICommandSender par0ICommandSender, String par1Str, double par2, double par4)
/*  97:    */   {
/*  98:134 */     double var6 = parseDouble(par0ICommandSender, par1Str);
/*  99:136 */     if (var6 < par2) {
/* 100:138 */       throw new NumberInvalidException("commands.generic.double.tooSmall", new Object[] { Double.valueOf(var6), Double.valueOf(par2) });
/* 101:    */     }
/* 102:140 */     if (var6 > par4) {
/* 103:142 */       throw new NumberInvalidException("commands.generic.double.tooBig", new Object[] { Double.valueOf(var6), Double.valueOf(par4) });
/* 104:    */     }
/* 105:146 */     return var6;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public static boolean parseBoolean(ICommandSender par0ICommandSender, String par1Str)
/* 109:    */   {
/* 110:156 */     if ((!par1Str.equals("true")) && (!par1Str.equals("1")))
/* 111:    */     {
/* 112:158 */       if ((!par1Str.equals("false")) && (!par1Str.equals("0"))) {
/* 113:160 */         throw new CommandException("commands.generic.boolean.invalid", new Object[] { par1Str });
/* 114:    */       }
/* 115:164 */       return false;
/* 116:    */     }
/* 117:169 */     return true;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public static EntityPlayerMP getCommandSenderAsPlayer(ICommandSender par0ICommandSender)
/* 121:    */   {
/* 122:178 */     if ((par0ICommandSender instanceof EntityPlayerMP)) {
/* 123:180 */       return (EntityPlayerMP)par0ICommandSender;
/* 124:    */     }
/* 125:184 */     throw new PlayerNotFoundException("You must specify which player you wish to perform this action on.", new Object[0]);
/* 126:    */   }
/* 127:    */   
/* 128:    */   public static EntityPlayerMP getPlayer(ICommandSender par0ICommandSender, String par1Str)
/* 129:    */   {
/* 130:190 */     EntityPlayerMP var2 = PlayerSelector.matchOnePlayer(par0ICommandSender, par1Str);
/* 131:192 */     if (var2 != null) {
/* 132:194 */       return var2;
/* 133:    */     }
/* 134:198 */     var2 = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(par1Str);
/* 135:200 */     if (var2 == null) {
/* 136:202 */       throw new PlayerNotFoundException();
/* 137:    */     }
/* 138:206 */     return var2;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public static String func_96332_d(ICommandSender par0ICommandSender, String par1Str)
/* 142:    */   {
/* 143:213 */     EntityPlayerMP var2 = PlayerSelector.matchOnePlayer(par0ICommandSender, par1Str);
/* 144:215 */     if (var2 != null) {
/* 145:217 */       return var2.getCommandSenderName();
/* 146:    */     }
/* 147:219 */     if (PlayerSelector.hasArguments(par1Str)) {
/* 148:221 */       throw new PlayerNotFoundException();
/* 149:    */     }
/* 150:225 */     return par1Str;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public static IChatComponent func_147178_a(ICommandSender p_147178_0_, String[] p_147178_1_, int p_147178_2_)
/* 154:    */   {
/* 155:231 */     return func_147176_a(p_147178_0_, p_147178_1_, p_147178_2_, false);
/* 156:    */   }
/* 157:    */   
/* 158:    */   public static IChatComponent func_147176_a(ICommandSender p_147176_0_, String[] p_147176_1_, int p_147176_2_, boolean p_147176_3_)
/* 159:    */   {
/* 160:236 */     ChatComponentText var4 = new ChatComponentText("");
/* 161:238 */     for (int var5 = p_147176_2_; var5 < p_147176_1_.length; var5++)
/* 162:    */     {
/* 163:240 */       if (var5 > p_147176_2_) {
/* 164:242 */         var4.appendText(" ");
/* 165:    */       }
/* 166:245 */       Object var6 = new ChatComponentText(p_147176_1_[var5]);
/* 167:247 */       if (p_147176_3_)
/* 168:    */       {
/* 169:249 */         IChatComponent var7 = PlayerSelector.func_150869_b(p_147176_0_, p_147176_1_[var5]);
/* 170:251 */         if (var7 != null) {
/* 171:253 */           var6 = var7;
/* 172:255 */         } else if (PlayerSelector.hasArguments(p_147176_1_[var5])) {
/* 173:257 */           throw new PlayerNotFoundException();
/* 174:    */         }
/* 175:    */       }
/* 176:261 */       var4.appendSibling((IChatComponent)var6);
/* 177:    */     }
/* 178:264 */     return var4;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public static String func_82360_a(ICommandSender par0ICommandSender, String[] par1ArrayOfStr, int par2)
/* 182:    */   {
/* 183:269 */     StringBuilder var3 = new StringBuilder();
/* 184:271 */     for (int var4 = par2; var4 < par1ArrayOfStr.length; var4++)
/* 185:    */     {
/* 186:273 */       if (var4 > par2) {
/* 187:275 */         var3.append(" ");
/* 188:    */       }
/* 189:278 */       String var5 = par1ArrayOfStr[var4];
/* 190:279 */       var3.append(var5);
/* 191:    */     }
/* 192:282 */     return var3.toString();
/* 193:    */   }
/* 194:    */   
/* 195:    */   public static double func_110666_a(ICommandSender par0ICommandSender, double par1, String par3Str)
/* 196:    */   {
/* 197:287 */     return func_110665_a(par0ICommandSender, par1, par3Str, -30000000, 30000000);
/* 198:    */   }
/* 199:    */   
/* 200:    */   public static double func_110665_a(ICommandSender par0ICommandSender, double par1, String par3Str, int par4, int par5)
/* 201:    */   {
/* 202:292 */     boolean var6 = par3Str.startsWith("~");
/* 203:294 */     if ((var6) && (Double.isNaN(par1))) {
/* 204:296 */       throw new NumberInvalidException("commands.generic.num.invalid", new Object[] { Double.valueOf(par1) });
/* 205:    */     }
/* 206:300 */     double var7 = var6 ? par1 : 0.0D;
/* 207:302 */     if ((!var6) || (par3Str.length() > 1))
/* 208:    */     {
/* 209:304 */       boolean var9 = par3Str.contains(".");
/* 210:306 */       if (var6) {
/* 211:308 */         par3Str = par3Str.substring(1);
/* 212:    */       }
/* 213:311 */       var7 += parseDouble(par0ICommandSender, par3Str);
/* 214:313 */       if ((!var9) && (!var6)) {
/* 215:315 */         var7 += 0.5D;
/* 216:    */       }
/* 217:    */     }
/* 218:319 */     if ((par4 != 0) || (par5 != 0))
/* 219:    */     {
/* 220:321 */       if (var7 < par4) {
/* 221:323 */         throw new NumberInvalidException("commands.generic.double.tooSmall", new Object[] { Double.valueOf(var7), Integer.valueOf(par4) });
/* 222:    */       }
/* 223:326 */       if (var7 > par5) {
/* 224:328 */         throw new NumberInvalidException("commands.generic.double.tooBig", new Object[] { Double.valueOf(var7), Integer.valueOf(par5) });
/* 225:    */       }
/* 226:    */     }
/* 227:332 */     return var7;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public static Item getItemByText(ICommandSender p_147179_0_, String p_147179_1_)
/* 231:    */   {
/* 232:343 */     Item var2 = (Item)Item.itemRegistry.getObject(p_147179_1_);
/* 233:345 */     if (var2 == null) {
/* 234:    */       try
/* 235:    */       {
/* 236:349 */         Item var3 = Item.getItemById(Integer.parseInt(p_147179_1_));
/* 237:351 */         if (var3 != null)
/* 238:    */         {
/* 239:353 */           ChatComponentTranslation var4 = new ChatComponentTranslation("commands.generic.deprecatedId", new Object[] { Item.itemRegistry.getNameForObject(var3) });
/* 240:354 */           var4.getChatStyle().setColor(EnumChatFormatting.GRAY);
/* 241:355 */           p_147179_0_.addChatMessage(var4);
/* 242:    */         }
/* 243:358 */         var2 = var3;
/* 244:    */       }
/* 245:    */       catch (NumberFormatException localNumberFormatException) {}
/* 246:    */     }
/* 247:366 */     if (var2 == null) {
/* 248:368 */       throw new NumberInvalidException("commands.give.notFound", new Object[] { p_147179_1_ });
/* 249:    */     }
/* 250:372 */     return var2;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public static Block getBlockByText(ICommandSender p_147180_0_, String p_147180_1_)
/* 254:    */   {
/* 255:383 */     if (Block.blockRegistry.containsKey(p_147180_1_)) {
/* 256:385 */       return (Block)Block.blockRegistry.getObject(p_147180_1_);
/* 257:    */     }
/* 258:    */     try
/* 259:    */     {
/* 260:391 */       int var2 = Integer.parseInt(p_147180_1_);
/* 261:393 */       if (Block.blockRegistry.containsID(var2))
/* 262:    */       {
/* 263:395 */         Block var3 = Block.getBlockById(var2);
/* 264:396 */         ChatComponentTranslation var4 = new ChatComponentTranslation("commands.generic.deprecatedId", new Object[] { Block.blockRegistry.getNameForObject(var3) });
/* 265:397 */         var4.getChatStyle().setColor(EnumChatFormatting.GRAY);
/* 266:398 */         p_147180_0_.addChatMessage(var4);
/* 267:399 */         return var3;
/* 268:    */       }
/* 269:    */     }
/* 270:    */     catch (NumberFormatException localNumberFormatException)
/* 271:    */     {
/* 272:407 */       throw new NumberInvalidException("commands.give.notFound", new Object[] { p_147180_1_ });
/* 273:    */     }
/* 274:    */   }
/* 275:    */   
/* 276:    */   public static String joinNiceString(Object[] par0ArrayOfObj)
/* 277:    */   {
/* 278:417 */     StringBuilder var1 = new StringBuilder();
/* 279:419 */     for (int var2 = 0; var2 < par0ArrayOfObj.length; var2++)
/* 280:    */     {
/* 281:421 */       String var3 = par0ArrayOfObj[var2].toString();
/* 282:423 */       if (var2 > 0) {
/* 283:425 */         if (var2 == par0ArrayOfObj.length - 1) {
/* 284:427 */           var1.append(" and ");
/* 285:    */         } else {
/* 286:431 */           var1.append(", ");
/* 287:    */         }
/* 288:    */       }
/* 289:435 */       var1.append(var3);
/* 290:    */     }
/* 291:438 */     return var1.toString();
/* 292:    */   }
/* 293:    */   
/* 294:    */   public static IChatComponent joinNiceString(IChatComponent[] p_147177_0_)
/* 295:    */   {
/* 296:447 */     ChatComponentText var1 = new ChatComponentText("");
/* 297:449 */     for (int var2 = 0; var2 < p_147177_0_.length; var2++)
/* 298:    */     {
/* 299:451 */       if (var2 > 0) {
/* 300:453 */         if (var2 == p_147177_0_.length - 1) {
/* 301:455 */           var1.appendText(" and ");
/* 302:457 */         } else if (var2 > 0) {
/* 303:459 */           var1.appendText(", ");
/* 304:    */         }
/* 305:    */       }
/* 306:463 */       var1.appendSibling(p_147177_0_[var2]);
/* 307:    */     }
/* 308:466 */     return var1;
/* 309:    */   }
/* 310:    */   
/* 311:    */   public static String joinNiceStringFromCollection(Collection par0Collection)
/* 312:    */   {
/* 313:476 */     return joinNiceString(par0Collection.toArray(new String[par0Collection.size()]));
/* 314:    */   }
/* 315:    */   
/* 316:    */   public static boolean doesStringStartWith(String par0Str, String par1Str)
/* 317:    */   {
/* 318:484 */     return par1Str.regionMatches(true, 0, par0Str, 0, par0Str.length());
/* 319:    */   }
/* 320:    */   
/* 321:    */   public static List getListOfStringsMatchingLastWord(String[] par0ArrayOfStr, String... par1ArrayOfStr)
/* 322:    */   {
/* 323:493 */     String var2 = par0ArrayOfStr[(par0ArrayOfStr.length - 1)];
/* 324:494 */     ArrayList var3 = new ArrayList();
/* 325:495 */     String[] var4 = par1ArrayOfStr;
/* 326:496 */     int var5 = par1ArrayOfStr.length;
/* 327:498 */     for (int var6 = 0; var6 < var5; var6++)
/* 328:    */     {
/* 329:500 */       String var7 = var4[var6];
/* 330:502 */       if (doesStringStartWith(var2, var7)) {
/* 331:504 */         var3.add(var7);
/* 332:    */       }
/* 333:    */     }
/* 334:508 */     return var3;
/* 335:    */   }
/* 336:    */   
/* 337:    */   public static List getListOfStringsFromIterableMatchingLastWord(String[] par0ArrayOfStr, Iterable par1Iterable)
/* 338:    */   {
/* 339:517 */     String var2 = par0ArrayOfStr[(par0ArrayOfStr.length - 1)];
/* 340:518 */     ArrayList var3 = new ArrayList();
/* 341:519 */     Iterator var4 = par1Iterable.iterator();
/* 342:521 */     while (var4.hasNext())
/* 343:    */     {
/* 344:523 */       String var5 = (String)var4.next();
/* 345:525 */       if (doesStringStartWith(var2, var5)) {
/* 346:527 */         var3.add(var5);
/* 347:    */       }
/* 348:    */     }
/* 349:531 */     return var3;
/* 350:    */   }
/* 351:    */   
/* 352:    */   public boolean isUsernameIndex(String[] par1ArrayOfStr, int par2)
/* 353:    */   {
/* 354:539 */     return false;
/* 355:    */   }
/* 356:    */   
/* 357:    */   public static void notifyAdmins(ICommandSender par0ICommandSender, String par1Str, Object... par2ArrayOfObj)
/* 358:    */   {
/* 359:544 */     notifyAdmins(par0ICommandSender, 0, par1Str, par2ArrayOfObj);
/* 360:    */   }
/* 361:    */   
/* 362:    */   public static void notifyAdmins(ICommandSender par0ICommandSender, int par1, String par2Str, Object... par3ArrayOfObj)
/* 363:    */   {
/* 364:549 */     if (theAdmin != null) {
/* 365:551 */       theAdmin.notifyAdmins(par0ICommandSender, par1, par2Str, par3ArrayOfObj);
/* 366:    */     }
/* 367:    */   }
/* 368:    */   
/* 369:    */   public static void setAdminCommander(IAdminCommand par0IAdminCommand)
/* 370:    */   {
/* 371:560 */     theAdmin = par0IAdminCommand;
/* 372:    */   }
/* 373:    */   
/* 374:    */   public int compareTo(ICommand par1ICommand)
/* 375:    */   {
/* 376:565 */     return getCommandName().compareTo(par1ICommand.getCommandName());
/* 377:    */   }
/* 378:    */   
/* 379:    */   public int compareTo(Object par1Obj)
/* 380:    */   {
/* 381:570 */     return compareTo((ICommand)par1Obj);
/* 382:    */   }
/* 383:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.command.CommandBase
 * JD-Core Version:    0.7.0.1
 */