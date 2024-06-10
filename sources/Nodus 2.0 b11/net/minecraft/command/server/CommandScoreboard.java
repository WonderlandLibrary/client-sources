/*   1:    */ package net.minecraft.command.server;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Arrays;
/*   5:    */ import java.util.Collection;
/*   6:    */ import java.util.HashSet;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import java.util.Set;
/*  11:    */ import net.minecraft.command.CommandBase;
/*  12:    */ import net.minecraft.command.CommandException;
/*  13:    */ import net.minecraft.command.ICommandSender;
/*  14:    */ import net.minecraft.command.SyntaxErrorException;
/*  15:    */ import net.minecraft.command.WrongUsageException;
/*  16:    */ import net.minecraft.entity.player.EntityPlayer;
/*  17:    */ import net.minecraft.entity.player.EntityPlayerMP;
/*  18:    */ import net.minecraft.scoreboard.IScoreObjectiveCriteria;
/*  19:    */ import net.minecraft.scoreboard.Score;
/*  20:    */ import net.minecraft.scoreboard.ScoreObjective;
/*  21:    */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*  22:    */ import net.minecraft.scoreboard.Scoreboard;
/*  23:    */ import net.minecraft.server.MinecraftServer;
/*  24:    */ import net.minecraft.util.ChatComponentText;
/*  25:    */ import net.minecraft.util.ChatComponentTranslation;
/*  26:    */ import net.minecraft.util.ChatStyle;
/*  27:    */ import net.minecraft.util.EnumChatFormatting;
/*  28:    */ import net.minecraft.util.IChatComponent;
/*  29:    */ import net.minecraft.world.WorldServer;
/*  30:    */ 
/*  31:    */ public class CommandScoreboard
/*  32:    */   extends CommandBase
/*  33:    */ {
/*  34:    */   private static final String __OBFID = "CL_00000896";
/*  35:    */   
/*  36:    */   public String getCommandName()
/*  37:    */   {
/*  38: 33 */     return "scoreboard";
/*  39:    */   }
/*  40:    */   
/*  41:    */   public int getRequiredPermissionLevel()
/*  42:    */   {
/*  43: 41 */     return 2;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public String getCommandUsage(ICommandSender par1ICommandSender)
/*  47:    */   {
/*  48: 46 */     return "commands.scoreboard.usage";
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/*  52:    */   {
/*  53: 51 */     if (par2ArrayOfStr.length >= 1)
/*  54:    */     {
/*  55: 53 */       if (par2ArrayOfStr[0].equalsIgnoreCase("objectives"))
/*  56:    */       {
/*  57: 55 */         if (par2ArrayOfStr.length == 1) {
/*  58: 57 */           throw new WrongUsageException("commands.scoreboard.objectives.usage", new Object[0]);
/*  59:    */         }
/*  60: 60 */         if (par2ArrayOfStr[1].equalsIgnoreCase("list"))
/*  61:    */         {
/*  62: 62 */           func_147196_d(par1ICommandSender);
/*  63:    */         }
/*  64: 64 */         else if (par2ArrayOfStr[1].equalsIgnoreCase("add"))
/*  65:    */         {
/*  66: 66 */           if (par2ArrayOfStr.length < 4) {
/*  67: 68 */             throw new WrongUsageException("commands.scoreboard.objectives.add.usage", new Object[0]);
/*  68:    */           }
/*  69: 71 */           func_147193_c(par1ICommandSender, par2ArrayOfStr, 2);
/*  70:    */         }
/*  71: 73 */         else if (par2ArrayOfStr[1].equalsIgnoreCase("remove"))
/*  72:    */         {
/*  73: 75 */           if (par2ArrayOfStr.length != 3) {
/*  74: 77 */             throw new WrongUsageException("commands.scoreboard.objectives.remove.usage", new Object[0]);
/*  75:    */           }
/*  76: 80 */           func_147191_h(par1ICommandSender, par2ArrayOfStr[2]);
/*  77:    */         }
/*  78:    */         else
/*  79:    */         {
/*  80: 84 */           if (!par2ArrayOfStr[1].equalsIgnoreCase("setdisplay")) {
/*  81: 86 */             throw new WrongUsageException("commands.scoreboard.objectives.usage", new Object[0]);
/*  82:    */           }
/*  83: 89 */           if ((par2ArrayOfStr.length != 3) && (par2ArrayOfStr.length != 4)) {
/*  84: 91 */             throw new WrongUsageException("commands.scoreboard.objectives.setdisplay.usage", new Object[0]);
/*  85:    */           }
/*  86: 94 */           func_147198_k(par1ICommandSender, par2ArrayOfStr, 2);
/*  87:    */         }
/*  88: 97 */         return;
/*  89:    */       }
/*  90:100 */       if (par2ArrayOfStr[0].equalsIgnoreCase("players"))
/*  91:    */       {
/*  92:102 */         if (par2ArrayOfStr.length == 1) {
/*  93:104 */           throw new WrongUsageException("commands.scoreboard.players.usage", new Object[0]);
/*  94:    */         }
/*  95:107 */         if (par2ArrayOfStr[1].equalsIgnoreCase("list"))
/*  96:    */         {
/*  97:109 */           if (par2ArrayOfStr.length > 3) {
/*  98:111 */             throw new WrongUsageException("commands.scoreboard.players.list.usage", new Object[0]);
/*  99:    */           }
/* 100:114 */           func_147195_l(par1ICommandSender, par2ArrayOfStr, 2);
/* 101:    */         }
/* 102:116 */         else if (par2ArrayOfStr[1].equalsIgnoreCase("add"))
/* 103:    */         {
/* 104:118 */           if (par2ArrayOfStr.length != 5) {
/* 105:120 */             throw new WrongUsageException("commands.scoreboard.players.add.usage", new Object[0]);
/* 106:    */           }
/* 107:123 */           func_147197_m(par1ICommandSender, par2ArrayOfStr, 2);
/* 108:    */         }
/* 109:125 */         else if (par2ArrayOfStr[1].equalsIgnoreCase("remove"))
/* 110:    */         {
/* 111:127 */           if (par2ArrayOfStr.length != 5) {
/* 112:129 */             throw new WrongUsageException("commands.scoreboard.players.remove.usage", new Object[0]);
/* 113:    */           }
/* 114:132 */           func_147197_m(par1ICommandSender, par2ArrayOfStr, 2);
/* 115:    */         }
/* 116:134 */         else if (par2ArrayOfStr[1].equalsIgnoreCase("set"))
/* 117:    */         {
/* 118:136 */           if (par2ArrayOfStr.length != 5) {
/* 119:138 */             throw new WrongUsageException("commands.scoreboard.players.set.usage", new Object[0]);
/* 120:    */           }
/* 121:141 */           func_147197_m(par1ICommandSender, par2ArrayOfStr, 2);
/* 122:    */         }
/* 123:    */         else
/* 124:    */         {
/* 125:145 */           if (!par2ArrayOfStr[1].equalsIgnoreCase("reset")) {
/* 126:147 */             throw new WrongUsageException("commands.scoreboard.players.usage", new Object[0]);
/* 127:    */           }
/* 128:150 */           if (par2ArrayOfStr.length != 3) {
/* 129:152 */             throw new WrongUsageException("commands.scoreboard.players.reset.usage", new Object[0]);
/* 130:    */           }
/* 131:155 */           func_147187_n(par1ICommandSender, par2ArrayOfStr, 2);
/* 132:    */         }
/* 133:158 */         return;
/* 134:    */       }
/* 135:161 */       if (par2ArrayOfStr[0].equalsIgnoreCase("teams"))
/* 136:    */       {
/* 137:163 */         if (par2ArrayOfStr.length == 1) {
/* 138:165 */           throw new WrongUsageException("commands.scoreboard.teams.usage", new Object[0]);
/* 139:    */         }
/* 140:168 */         if (par2ArrayOfStr[1].equalsIgnoreCase("list"))
/* 141:    */         {
/* 142:170 */           if (par2ArrayOfStr.length > 3) {
/* 143:172 */             throw new WrongUsageException("commands.scoreboard.teams.list.usage", new Object[0]);
/* 144:    */           }
/* 145:175 */           func_147186_g(par1ICommandSender, par2ArrayOfStr, 2);
/* 146:    */         }
/* 147:177 */         else if (par2ArrayOfStr[1].equalsIgnoreCase("add"))
/* 148:    */         {
/* 149:179 */           if (par2ArrayOfStr.length < 3) {
/* 150:181 */             throw new WrongUsageException("commands.scoreboard.teams.add.usage", new Object[0]);
/* 151:    */           }
/* 152:184 */           func_147185_d(par1ICommandSender, par2ArrayOfStr, 2);
/* 153:    */         }
/* 154:186 */         else if (par2ArrayOfStr[1].equalsIgnoreCase("remove"))
/* 155:    */         {
/* 156:188 */           if (par2ArrayOfStr.length != 3) {
/* 157:190 */             throw new WrongUsageException("commands.scoreboard.teams.remove.usage", new Object[0]);
/* 158:    */           }
/* 159:193 */           func_147194_f(par1ICommandSender, par2ArrayOfStr, 2);
/* 160:    */         }
/* 161:195 */         else if (par2ArrayOfStr[1].equalsIgnoreCase("empty"))
/* 162:    */         {
/* 163:197 */           if (par2ArrayOfStr.length != 3) {
/* 164:199 */             throw new WrongUsageException("commands.scoreboard.teams.empty.usage", new Object[0]);
/* 165:    */           }
/* 166:202 */           func_147188_j(par1ICommandSender, par2ArrayOfStr, 2);
/* 167:    */         }
/* 168:204 */         else if (par2ArrayOfStr[1].equalsIgnoreCase("join"))
/* 169:    */         {
/* 170:206 */           if ((par2ArrayOfStr.length < 4) && ((par2ArrayOfStr.length != 3) || (!(par1ICommandSender instanceof EntityPlayer)))) {
/* 171:208 */             throw new WrongUsageException("commands.scoreboard.teams.join.usage", new Object[0]);
/* 172:    */           }
/* 173:211 */           func_147190_h(par1ICommandSender, par2ArrayOfStr, 2);
/* 174:    */         }
/* 175:213 */         else if (par2ArrayOfStr[1].equalsIgnoreCase("leave"))
/* 176:    */         {
/* 177:215 */           if ((par2ArrayOfStr.length < 3) && (!(par1ICommandSender instanceof EntityPlayer))) {
/* 178:217 */             throw new WrongUsageException("commands.scoreboard.teams.leave.usage", new Object[0]);
/* 179:    */           }
/* 180:220 */           func_147199_i(par1ICommandSender, par2ArrayOfStr, 2);
/* 181:    */         }
/* 182:    */         else
/* 183:    */         {
/* 184:224 */           if (!par2ArrayOfStr[1].equalsIgnoreCase("option")) {
/* 185:226 */             throw new WrongUsageException("commands.scoreboard.teams.usage", new Object[0]);
/* 186:    */           }
/* 187:229 */           if ((par2ArrayOfStr.length != 4) && (par2ArrayOfStr.length != 5)) {
/* 188:231 */             throw new WrongUsageException("commands.scoreboard.teams.option.usage", new Object[0]);
/* 189:    */           }
/* 190:234 */           func_147200_e(par1ICommandSender, par2ArrayOfStr, 2);
/* 191:    */         }
/* 192:237 */         return;
/* 193:    */       }
/* 194:    */     }
/* 195:241 */     throw new WrongUsageException("commands.scoreboard.usage", new Object[0]);
/* 196:    */   }
/* 197:    */   
/* 198:    */   protected Scoreboard func_147192_d()
/* 199:    */   {
/* 200:246 */     return MinecraftServer.getServer().worldServerForDimension(0).getScoreboard();
/* 201:    */   }
/* 202:    */   
/* 203:    */   protected ScoreObjective func_147189_a(String p_147189_1_, boolean p_147189_2_)
/* 204:    */   {
/* 205:251 */     Scoreboard var3 = func_147192_d();
/* 206:252 */     ScoreObjective var4 = var3.getObjective(p_147189_1_);
/* 207:254 */     if (var4 == null) {
/* 208:256 */       throw new CommandException("commands.scoreboard.objectiveNotFound", new Object[] { p_147189_1_ });
/* 209:    */     }
/* 210:258 */     if ((p_147189_2_) && (var4.getCriteria().isReadOnly())) {
/* 211:260 */       throw new CommandException("commands.scoreboard.objectiveReadOnly", new Object[] { p_147189_1_ });
/* 212:    */     }
/* 213:264 */     return var4;
/* 214:    */   }
/* 215:    */   
/* 216:    */   protected ScorePlayerTeam func_147183_a(String p_147183_1_)
/* 217:    */   {
/* 218:270 */     Scoreboard var2 = func_147192_d();
/* 219:271 */     ScorePlayerTeam var3 = var2.getTeam(p_147183_1_);
/* 220:273 */     if (var3 == null) {
/* 221:275 */       throw new CommandException("commands.scoreboard.teamNotFound", new Object[] { p_147183_1_ });
/* 222:    */     }
/* 223:279 */     return var3;
/* 224:    */   }
/* 225:    */   
/* 226:    */   protected void func_147193_c(ICommandSender p_147193_1_, String[] p_147193_2_, int p_147193_3_)
/* 227:    */   {
/* 228:285 */     String var4 = p_147193_2_[(p_147193_3_++)];
/* 229:286 */     String var5 = p_147193_2_[(p_147193_3_++)];
/* 230:287 */     Scoreboard var6 = func_147192_d();
/* 231:288 */     IScoreObjectiveCriteria var7 = (IScoreObjectiveCriteria)IScoreObjectiveCriteria.field_96643_a.get(var5);
/* 232:290 */     if (var7 == null) {
/* 233:292 */       throw new WrongUsageException("commands.scoreboard.objectives.add.wrongType", new Object[] { var5 });
/* 234:    */     }
/* 235:294 */     if (var6.getObjective(var4) != null) {
/* 236:296 */       throw new CommandException("commands.scoreboard.objectives.add.alreadyExists", new Object[] { var4 });
/* 237:    */     }
/* 238:298 */     if (var4.length() > 16) {
/* 239:300 */       throw new SyntaxErrorException("commands.scoreboard.objectives.add.tooLong", new Object[] { var4, Integer.valueOf(16) });
/* 240:    */     }
/* 241:302 */     if (var4.length() == 0) {
/* 242:304 */       throw new WrongUsageException("commands.scoreboard.objectives.add.usage", new Object[0]);
/* 243:    */     }
/* 244:308 */     if (p_147193_2_.length > p_147193_3_)
/* 245:    */     {
/* 246:310 */       String var8 = func_147178_a(p_147193_1_, p_147193_2_, p_147193_3_).getUnformattedText();
/* 247:312 */       if (var8.length() > 32) {
/* 248:314 */         throw new SyntaxErrorException("commands.scoreboard.objectives.add.displayTooLong", new Object[] { var8, Integer.valueOf(32) });
/* 249:    */       }
/* 250:317 */       if (var8.length() > 0) {
/* 251:319 */         var6.addScoreObjective(var4, var7).setDisplayName(var8);
/* 252:    */       } else {
/* 253:323 */         var6.addScoreObjective(var4, var7);
/* 254:    */       }
/* 255:    */     }
/* 256:    */     else
/* 257:    */     {
/* 258:328 */       var6.addScoreObjective(var4, var7);
/* 259:    */     }
/* 260:331 */     notifyAdmins(p_147193_1_, "commands.scoreboard.objectives.add.success", new Object[] { var4 });
/* 261:    */   }
/* 262:    */   
/* 263:    */   protected void func_147185_d(ICommandSender p_147185_1_, String[] p_147185_2_, int p_147185_3_)
/* 264:    */   {
/* 265:337 */     String var4 = p_147185_2_[(p_147185_3_++)];
/* 266:338 */     Scoreboard var5 = func_147192_d();
/* 267:340 */     if (var5.getTeam(var4) != null) {
/* 268:342 */       throw new CommandException("commands.scoreboard.teams.add.alreadyExists", new Object[] { var4 });
/* 269:    */     }
/* 270:344 */     if (var4.length() > 16) {
/* 271:346 */       throw new SyntaxErrorException("commands.scoreboard.teams.add.tooLong", new Object[] { var4, Integer.valueOf(16) });
/* 272:    */     }
/* 273:348 */     if (var4.length() == 0) {
/* 274:350 */       throw new WrongUsageException("commands.scoreboard.teams.add.usage", new Object[0]);
/* 275:    */     }
/* 276:354 */     if (p_147185_2_.length > p_147185_3_)
/* 277:    */     {
/* 278:356 */       String var6 = func_147178_a(p_147185_1_, p_147185_2_, p_147185_3_).getUnformattedText();
/* 279:358 */       if (var6.length() > 32) {
/* 280:360 */         throw new SyntaxErrorException("commands.scoreboard.teams.add.displayTooLong", new Object[] { var6, Integer.valueOf(32) });
/* 281:    */       }
/* 282:363 */       if (var6.length() > 0) {
/* 283:365 */         var5.createTeam(var4).setTeamName(var6);
/* 284:    */       } else {
/* 285:369 */         var5.createTeam(var4);
/* 286:    */       }
/* 287:    */     }
/* 288:    */     else
/* 289:    */     {
/* 290:374 */       var5.createTeam(var4);
/* 291:    */     }
/* 292:377 */     notifyAdmins(p_147185_1_, "commands.scoreboard.teams.add.success", new Object[] { var4 });
/* 293:    */   }
/* 294:    */   
/* 295:    */   protected void func_147200_e(ICommandSender p_147200_1_, String[] p_147200_2_, int p_147200_3_)
/* 296:    */   {
/* 297:383 */     ScorePlayerTeam var4 = func_147183_a(p_147200_2_[(p_147200_3_++)]);
/* 298:385 */     if (var4 != null)
/* 299:    */     {
/* 300:387 */       String var5 = p_147200_2_[(p_147200_3_++)].toLowerCase();
/* 301:389 */       if ((!var5.equalsIgnoreCase("color")) && (!var5.equalsIgnoreCase("friendlyfire")) && (!var5.equalsIgnoreCase("seeFriendlyInvisibles"))) {
/* 302:391 */         throw new WrongUsageException("commands.scoreboard.teams.option.usage", new Object[0]);
/* 303:    */       }
/* 304:393 */       if (p_147200_2_.length == 4)
/* 305:    */       {
/* 306:395 */         if (var5.equalsIgnoreCase("color")) {
/* 307:397 */           throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { var5, joinNiceStringFromCollection(EnumChatFormatting.getValidValues(true, false)) });
/* 308:    */         }
/* 309:399 */         if ((!var5.equalsIgnoreCase("friendlyfire")) && (!var5.equalsIgnoreCase("seeFriendlyInvisibles"))) {
/* 310:401 */           throw new WrongUsageException("commands.scoreboard.teams.option.usage", new Object[0]);
/* 311:    */         }
/* 312:405 */         throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { var5, joinNiceStringFromCollection(Arrays.asList(new String[] { "true", "false" })) });
/* 313:    */       }
/* 314:410 */       String var6 = p_147200_2_[(p_147200_3_++)];
/* 315:412 */       if (var5.equalsIgnoreCase("color"))
/* 316:    */       {
/* 317:414 */         EnumChatFormatting var7 = EnumChatFormatting.getValueByName(var6);
/* 318:416 */         if ((var7 == null) || (var7.isFancyStyling())) {
/* 319:418 */           throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { var5, joinNiceStringFromCollection(EnumChatFormatting.getValidValues(true, false)) });
/* 320:    */         }
/* 321:421 */         var4.setNamePrefix(var7.toString());
/* 322:422 */         var4.setNameSuffix(EnumChatFormatting.RESET.toString());
/* 323:    */       }
/* 324:424 */       else if (var5.equalsIgnoreCase("friendlyfire"))
/* 325:    */       {
/* 326:426 */         if ((!var6.equalsIgnoreCase("true")) && (!var6.equalsIgnoreCase("false"))) {
/* 327:428 */           throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { var5, joinNiceStringFromCollection(Arrays.asList(new String[] { "true", "false" })) });
/* 328:    */         }
/* 329:431 */         var4.setAllowFriendlyFire(var6.equalsIgnoreCase("true"));
/* 330:    */       }
/* 331:433 */       else if (var5.equalsIgnoreCase("seeFriendlyInvisibles"))
/* 332:    */       {
/* 333:435 */         if ((!var6.equalsIgnoreCase("true")) && (!var6.equalsIgnoreCase("false"))) {
/* 334:437 */           throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { var5, joinNiceStringFromCollection(Arrays.asList(new String[] { "true", "false" })) });
/* 335:    */         }
/* 336:440 */         var4.setSeeFriendlyInvisiblesEnabled(var6.equalsIgnoreCase("true"));
/* 337:    */       }
/* 338:443 */       notifyAdmins(p_147200_1_, "commands.scoreboard.teams.option.success", new Object[] { var5, var4.getRegisteredName(), var6 });
/* 339:    */     }
/* 340:    */   }
/* 341:    */   
/* 342:    */   protected void func_147194_f(ICommandSender p_147194_1_, String[] p_147194_2_, int p_147194_3_)
/* 343:    */   {
/* 344:450 */     Scoreboard var4 = func_147192_d();
/* 345:451 */     ScorePlayerTeam var5 = func_147183_a(p_147194_2_[(p_147194_3_++)]);
/* 346:453 */     if (var5 != null)
/* 347:    */     {
/* 348:455 */       var4.removeTeam(var5);
/* 349:456 */       notifyAdmins(p_147194_1_, "commands.scoreboard.teams.remove.success", new Object[] { var5.getRegisteredName() });
/* 350:    */     }
/* 351:    */   }
/* 352:    */   
/* 353:    */   protected void func_147186_g(ICommandSender p_147186_1_, String[] p_147186_2_, int p_147186_3_)
/* 354:    */   {
/* 355:462 */     Scoreboard var4 = func_147192_d();
/* 356:464 */     if (p_147186_2_.length > p_147186_3_)
/* 357:    */     {
/* 358:466 */       ScorePlayerTeam var5 = func_147183_a(p_147186_2_[(p_147186_3_++)]);
/* 359:468 */       if (var5 == null) {
/* 360:470 */         return;
/* 361:    */       }
/* 362:473 */       Collection var6 = var5.getMembershipCollection();
/* 363:475 */       if (var6.size() <= 0) {
/* 364:477 */         throw new CommandException("commands.scoreboard.teams.list.player.empty", new Object[] { var5.getRegisteredName() });
/* 365:    */       }
/* 366:480 */       ChatComponentTranslation var7 = new ChatComponentTranslation("commands.scoreboard.teams.list.player.count", new Object[] { Integer.valueOf(var6.size()), var5.getRegisteredName() });
/* 367:481 */       var7.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
/* 368:482 */       p_147186_1_.addChatMessage(var7);
/* 369:483 */       p_147186_1_.addChatMessage(new ChatComponentText(joinNiceString(var6.toArray())));
/* 370:    */     }
/* 371:    */     else
/* 372:    */     {
/* 373:487 */       Collection var9 = var4.getTeams();
/* 374:489 */       if (var9.size() <= 0) {
/* 375:491 */         throw new CommandException("commands.scoreboard.teams.list.empty", new Object[0]);
/* 376:    */       }
/* 377:494 */       ChatComponentTranslation var10 = new ChatComponentTranslation("commands.scoreboard.teams.list.count", new Object[] { Integer.valueOf(var9.size()) });
/* 378:495 */       var10.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
/* 379:496 */       p_147186_1_.addChatMessage(var10);
/* 380:497 */       Iterator var11 = var9.iterator();
/* 381:499 */       while (var11.hasNext())
/* 382:    */       {
/* 383:501 */         ScorePlayerTeam var8 = (ScorePlayerTeam)var11.next();
/* 384:502 */         p_147186_1_.addChatMessage(new ChatComponentTranslation("commands.scoreboard.teams.list.entry", new Object[] { var8.getRegisteredName(), var8.func_96669_c(), Integer.valueOf(var8.getMembershipCollection().size()) }));
/* 385:    */       }
/* 386:    */     }
/* 387:    */   }
/* 388:    */   
/* 389:    */   protected void func_147190_h(ICommandSender p_147190_1_, String[] p_147190_2_, int p_147190_3_)
/* 390:    */   {
/* 391:509 */     Scoreboard var4 = func_147192_d();
/* 392:510 */     String var5 = p_147190_2_[(p_147190_3_++)];
/* 393:511 */     HashSet var6 = new HashSet();
/* 394:512 */     HashSet var7 = new HashSet();
/* 395:515 */     if (((p_147190_1_ instanceof EntityPlayer)) && (p_147190_3_ == p_147190_2_.length))
/* 396:    */     {
/* 397:517 */       var8 = getCommandSenderAsPlayer(p_147190_1_).getCommandSenderName();
/* 398:519 */       if (var4.func_151392_a(var8, var5)) {
/* 399:521 */         var6.add(var8);
/* 400:    */       } else {
/* 401:525 */         var7.add(var8);
/* 402:    */       }
/* 403:    */     }
/* 404:    */     else
/* 405:    */     {
/* 406:530 */       while (p_147190_3_ < p_147190_2_.length)
/* 407:    */       {
/* 408:    */         String var8;
/* 409:532 */         String var8 = func_96332_d(p_147190_1_, p_147190_2_[(p_147190_3_++)]);
/* 410:534 */         if (var4.func_151392_a(var8, var5)) {
/* 411:536 */           var6.add(var8);
/* 412:    */         } else {
/* 413:540 */           var7.add(var8);
/* 414:    */         }
/* 415:    */       }
/* 416:    */     }
/* 417:545 */     if (!var6.isEmpty()) {
/* 418:547 */       notifyAdmins(p_147190_1_, "commands.scoreboard.teams.join.success", new Object[] { Integer.valueOf(var6.size()), var5, joinNiceString(var6.toArray(new String[0])) });
/* 419:    */     }
/* 420:550 */     if (!var7.isEmpty()) {
/* 421:552 */       throw new CommandException("commands.scoreboard.teams.join.failure", new Object[] { Integer.valueOf(var7.size()), var5, joinNiceString(var7.toArray(new String[0])) });
/* 422:    */     }
/* 423:    */   }
/* 424:    */   
/* 425:    */   protected void func_147199_i(ICommandSender p_147199_1_, String[] p_147199_2_, int p_147199_3_)
/* 426:    */   {
/* 427:558 */     Scoreboard var4 = func_147192_d();
/* 428:559 */     HashSet var5 = new HashSet();
/* 429:560 */     HashSet var6 = new HashSet();
/* 430:563 */     if (((p_147199_1_ instanceof EntityPlayer)) && (p_147199_3_ == p_147199_2_.length))
/* 431:    */     {
/* 432:565 */       var7 = getCommandSenderAsPlayer(p_147199_1_).getCommandSenderName();
/* 433:567 */       if (var4.func_96524_g(var7)) {
/* 434:569 */         var5.add(var7);
/* 435:    */       } else {
/* 436:573 */         var6.add(var7);
/* 437:    */       }
/* 438:    */     }
/* 439:    */     else
/* 440:    */     {
/* 441:578 */       while (p_147199_3_ < p_147199_2_.length)
/* 442:    */       {
/* 443:    */         String var7;
/* 444:580 */         String var7 = func_96332_d(p_147199_1_, p_147199_2_[(p_147199_3_++)]);
/* 445:582 */         if (var4.func_96524_g(var7)) {
/* 446:584 */           var5.add(var7);
/* 447:    */         } else {
/* 448:588 */           var6.add(var7);
/* 449:    */         }
/* 450:    */       }
/* 451:    */     }
/* 452:593 */     if (!var5.isEmpty()) {
/* 453:595 */       notifyAdmins(p_147199_1_, "commands.scoreboard.teams.leave.success", new Object[] { Integer.valueOf(var5.size()), joinNiceString(var5.toArray(new String[0])) });
/* 454:    */     }
/* 455:598 */     if (!var6.isEmpty()) {
/* 456:600 */       throw new CommandException("commands.scoreboard.teams.leave.failure", new Object[] { Integer.valueOf(var6.size()), joinNiceString(var6.toArray(new String[0])) });
/* 457:    */     }
/* 458:    */   }
/* 459:    */   
/* 460:    */   protected void func_147188_j(ICommandSender p_147188_1_, String[] p_147188_2_, int p_147188_3_)
/* 461:    */   {
/* 462:606 */     Scoreboard var4 = func_147192_d();
/* 463:607 */     ScorePlayerTeam var5 = func_147183_a(p_147188_2_[(p_147188_3_++)]);
/* 464:609 */     if (var5 != null)
/* 465:    */     {
/* 466:611 */       ArrayList var6 = new ArrayList(var5.getMembershipCollection());
/* 467:613 */       if (var6.isEmpty()) {
/* 468:615 */         throw new CommandException("commands.scoreboard.teams.empty.alreadyEmpty", new Object[] { var5.getRegisteredName() });
/* 469:    */       }
/* 470:619 */       Iterator var7 = var6.iterator();
/* 471:621 */       while (var7.hasNext())
/* 472:    */       {
/* 473:623 */         String var8 = (String)var7.next();
/* 474:624 */         var4.removePlayerFromTeam(var8, var5);
/* 475:    */       }
/* 476:627 */       notifyAdmins(p_147188_1_, "commands.scoreboard.teams.empty.success", new Object[] { Integer.valueOf(var6.size()), var5.getRegisteredName() });
/* 477:    */     }
/* 478:    */   }
/* 479:    */   
/* 480:    */   protected void func_147191_h(ICommandSender p_147191_1_, String p_147191_2_)
/* 481:    */   {
/* 482:634 */     Scoreboard var3 = func_147192_d();
/* 483:635 */     ScoreObjective var4 = func_147189_a(p_147191_2_, false);
/* 484:636 */     var3.func_96519_k(var4);
/* 485:637 */     notifyAdmins(p_147191_1_, "commands.scoreboard.objectives.remove.success", new Object[] { p_147191_2_ });
/* 486:    */   }
/* 487:    */   
/* 488:    */   protected void func_147196_d(ICommandSender p_147196_1_)
/* 489:    */   {
/* 490:642 */     Scoreboard var2 = func_147192_d();
/* 491:643 */     Collection var3 = var2.getScoreObjectives();
/* 492:645 */     if (var3.size() <= 0) {
/* 493:647 */       throw new CommandException("commands.scoreboard.objectives.list.empty", new Object[0]);
/* 494:    */     }
/* 495:651 */     ChatComponentTranslation var4 = new ChatComponentTranslation("commands.scoreboard.objectives.list.count", new Object[] { Integer.valueOf(var3.size()) });
/* 496:652 */     var4.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
/* 497:653 */     p_147196_1_.addChatMessage(var4);
/* 498:654 */     Iterator var5 = var3.iterator();
/* 499:656 */     while (var5.hasNext())
/* 500:    */     {
/* 501:658 */       ScoreObjective var6 = (ScoreObjective)var5.next();
/* 502:659 */       p_147196_1_.addChatMessage(new ChatComponentTranslation("commands.scoreboard.objectives.list.entry", new Object[] { var6.getName(), var6.getDisplayName(), var6.getCriteria().func_96636_a() }));
/* 503:    */     }
/* 504:    */   }
/* 505:    */   
/* 506:    */   protected void func_147198_k(ICommandSender p_147198_1_, String[] p_147198_2_, int p_147198_3_)
/* 507:    */   {
/* 508:666 */     Scoreboard var4 = func_147192_d();
/* 509:667 */     String var5 = p_147198_2_[(p_147198_3_++)];
/* 510:668 */     int var6 = Scoreboard.getObjectiveDisplaySlotNumber(var5);
/* 511:669 */     ScoreObjective var7 = null;
/* 512:671 */     if (p_147198_2_.length == 4) {
/* 513:673 */       var7 = func_147189_a(p_147198_2_[(p_147198_3_++)], false);
/* 514:    */     }
/* 515:676 */     if (var6 < 0) {
/* 516:678 */       throw new CommandException("commands.scoreboard.objectives.setdisplay.invalidSlot", new Object[] { var5 });
/* 517:    */     }
/* 518:682 */     var4.func_96530_a(var6, var7);
/* 519:684 */     if (var7 != null) {
/* 520:686 */       notifyAdmins(p_147198_1_, "commands.scoreboard.objectives.setdisplay.successSet", new Object[] { Scoreboard.getObjectiveDisplaySlot(var6), var7.getName() });
/* 521:    */     } else {
/* 522:690 */       notifyAdmins(p_147198_1_, "commands.scoreboard.objectives.setdisplay.successCleared", new Object[] { Scoreboard.getObjectiveDisplaySlot(var6) });
/* 523:    */     }
/* 524:    */   }
/* 525:    */   
/* 526:    */   protected void func_147195_l(ICommandSender p_147195_1_, String[] p_147195_2_, int p_147195_3_)
/* 527:    */   {
/* 528:697 */     Scoreboard var4 = func_147192_d();
/* 529:699 */     if (p_147195_2_.length > p_147195_3_)
/* 530:    */     {
/* 531:701 */       String var5 = func_96332_d(p_147195_1_, p_147195_2_[(p_147195_3_++)]);
/* 532:702 */       Map var6 = var4.func_96510_d(var5);
/* 533:704 */       if (var6.size() <= 0) {
/* 534:706 */         throw new CommandException("commands.scoreboard.players.list.player.empty", new Object[] { var5 });
/* 535:    */       }
/* 536:709 */       ChatComponentTranslation var7 = new ChatComponentTranslation("commands.scoreboard.players.list.player.count", new Object[] { Integer.valueOf(var6.size()), var5 });
/* 537:710 */       var7.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
/* 538:711 */       p_147195_1_.addChatMessage(var7);
/* 539:712 */       Iterator var8 = var6.values().iterator();
/* 540:714 */       while (var8.hasNext())
/* 541:    */       {
/* 542:716 */         Score var9 = (Score)var8.next();
/* 543:717 */         p_147195_1_.addChatMessage(new ChatComponentTranslation("commands.scoreboard.players.list.player.entry", new Object[] { Integer.valueOf(var9.getScorePoints()), var9.func_96645_d().getDisplayName(), var9.func_96645_d().getName() }));
/* 544:    */       }
/* 545:    */     }
/* 546:    */     else
/* 547:    */     {
/* 548:722 */       Collection var10 = var4.getObjectiveNames();
/* 549:724 */       if (var10.size() <= 0) {
/* 550:726 */         throw new CommandException("commands.scoreboard.players.list.empty", new Object[0]);
/* 551:    */       }
/* 552:729 */       ChatComponentTranslation var11 = new ChatComponentTranslation("commands.scoreboard.players.list.count", new Object[] { Integer.valueOf(var10.size()) });
/* 553:730 */       var11.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
/* 554:731 */       p_147195_1_.addChatMessage(var11);
/* 555:732 */       p_147195_1_.addChatMessage(new ChatComponentText(joinNiceString(var10.toArray())));
/* 556:    */     }
/* 557:    */   }
/* 558:    */   
/* 559:    */   protected void func_147197_m(ICommandSender p_147197_1_, String[] p_147197_2_, int p_147197_3_)
/* 560:    */   {
/* 561:738 */     String var4 = p_147197_2_[(p_147197_3_ - 1)];
/* 562:739 */     String var5 = func_96332_d(p_147197_1_, p_147197_2_[(p_147197_3_++)]);
/* 563:740 */     ScoreObjective var6 = func_147189_a(p_147197_2_[(p_147197_3_++)], true);
/* 564:741 */     int var7 = var4.equalsIgnoreCase("set") ? parseInt(p_147197_1_, p_147197_2_[(p_147197_3_++)]) : parseIntWithMin(p_147197_1_, p_147197_2_[(p_147197_3_++)], 1);
/* 565:742 */     Scoreboard var8 = func_147192_d();
/* 566:743 */     Score var9 = var8.func_96529_a(var5, var6);
/* 567:745 */     if (var4.equalsIgnoreCase("set")) {
/* 568:747 */       var9.func_96647_c(var7);
/* 569:749 */     } else if (var4.equalsIgnoreCase("add")) {
/* 570:751 */       var9.func_96649_a(var7);
/* 571:    */     } else {
/* 572:755 */       var9.func_96646_b(var7);
/* 573:    */     }
/* 574:758 */     notifyAdmins(p_147197_1_, "commands.scoreboard.players.set.success", new Object[] { var6.getName(), var5, Integer.valueOf(var9.getScorePoints()) });
/* 575:    */   }
/* 576:    */   
/* 577:    */   protected void func_147187_n(ICommandSender p_147187_1_, String[] p_147187_2_, int p_147187_3_)
/* 578:    */   {
/* 579:763 */     Scoreboard var4 = func_147192_d();
/* 580:764 */     String var5 = func_96332_d(p_147187_1_, p_147187_2_[(p_147187_3_++)]);
/* 581:765 */     var4.func_96515_c(var5);
/* 582:766 */     notifyAdmins(p_147187_1_, "commands.scoreboard.players.reset.success", new Object[] { var5 });
/* 583:    */   }
/* 584:    */   
/* 585:    */   public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/* 586:    */   {
/* 587:774 */     if (par2ArrayOfStr.length == 1) {
/* 588:776 */       return getListOfStringsMatchingLastWord(par2ArrayOfStr, new String[] { "objectives", "players", "teams" });
/* 589:    */     }
/* 590:780 */     if (par2ArrayOfStr[0].equalsIgnoreCase("objectives"))
/* 591:    */     {
/* 592:782 */       if (par2ArrayOfStr.length == 2) {
/* 593:784 */         return getListOfStringsMatchingLastWord(par2ArrayOfStr, new String[] { "list", "add", "remove", "setdisplay" });
/* 594:    */       }
/* 595:787 */       if (par2ArrayOfStr[1].equalsIgnoreCase("add"))
/* 596:    */       {
/* 597:789 */         if (par2ArrayOfStr.length == 4)
/* 598:    */         {
/* 599:791 */           Set var3 = IScoreObjectiveCriteria.field_96643_a.keySet();
/* 600:792 */           return getListOfStringsFromIterableMatchingLastWord(par2ArrayOfStr, var3);
/* 601:    */         }
/* 602:    */       }
/* 603:795 */       else if (par2ArrayOfStr[1].equalsIgnoreCase("remove"))
/* 604:    */       {
/* 605:797 */         if (par2ArrayOfStr.length == 3) {
/* 606:799 */           return getListOfStringsFromIterableMatchingLastWord(par2ArrayOfStr, func_147184_a(false));
/* 607:    */         }
/* 608:    */       }
/* 609:802 */       else if (par2ArrayOfStr[1].equalsIgnoreCase("setdisplay"))
/* 610:    */       {
/* 611:804 */         if (par2ArrayOfStr.length == 3) {
/* 612:806 */           return getListOfStringsMatchingLastWord(par2ArrayOfStr, new String[] { "list", "sidebar", "belowName" });
/* 613:    */         }
/* 614:809 */         if (par2ArrayOfStr.length == 4) {
/* 615:811 */           return getListOfStringsFromIterableMatchingLastWord(par2ArrayOfStr, func_147184_a(false));
/* 616:    */         }
/* 617:    */       }
/* 618:    */     }
/* 619:815 */     else if (par2ArrayOfStr[0].equalsIgnoreCase("players"))
/* 620:    */     {
/* 621:817 */       if (par2ArrayOfStr.length == 2) {
/* 622:819 */         return getListOfStringsMatchingLastWord(par2ArrayOfStr, new String[] { "set", "add", "remove", "reset", "list" });
/* 623:    */       }
/* 624:822 */       if ((!par2ArrayOfStr[1].equalsIgnoreCase("set")) && (!par2ArrayOfStr[1].equalsIgnoreCase("add")) && (!par2ArrayOfStr[1].equalsIgnoreCase("remove")))
/* 625:    */       {
/* 626:824 */         if (((par2ArrayOfStr[1].equalsIgnoreCase("reset")) || (par2ArrayOfStr[1].equalsIgnoreCase("list"))) && (par2ArrayOfStr.length == 3)) {
/* 627:826 */           return getListOfStringsFromIterableMatchingLastWord(par2ArrayOfStr, func_147192_d().getObjectiveNames());
/* 628:    */         }
/* 629:    */       }
/* 630:    */       else
/* 631:    */       {
/* 632:831 */         if (par2ArrayOfStr.length == 3) {
/* 633:833 */           return getListOfStringsMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getAllUsernames());
/* 634:    */         }
/* 635:836 */         if (par2ArrayOfStr.length == 4) {
/* 636:838 */           return getListOfStringsFromIterableMatchingLastWord(par2ArrayOfStr, func_147184_a(true));
/* 637:    */         }
/* 638:    */       }
/* 639:    */     }
/* 640:842 */     else if (par2ArrayOfStr[0].equalsIgnoreCase("teams"))
/* 641:    */     {
/* 642:844 */       if (par2ArrayOfStr.length == 2) {
/* 643:846 */         return getListOfStringsMatchingLastWord(par2ArrayOfStr, new String[] { "add", "remove", "join", "leave", "empty", "list", "option" });
/* 644:    */       }
/* 645:849 */       if (par2ArrayOfStr[1].equalsIgnoreCase("join"))
/* 646:    */       {
/* 647:851 */         if (par2ArrayOfStr.length == 3) {
/* 648:853 */           return getListOfStringsFromIterableMatchingLastWord(par2ArrayOfStr, func_147192_d().getTeamNames());
/* 649:    */         }
/* 650:856 */         if (par2ArrayOfStr.length >= 4) {
/* 651:858 */           return getListOfStringsMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getAllUsernames());
/* 652:    */         }
/* 653:    */       }
/* 654:    */       else
/* 655:    */       {
/* 656:863 */         if (par2ArrayOfStr[1].equalsIgnoreCase("leave")) {
/* 657:865 */           return getListOfStringsMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getAllUsernames());
/* 658:    */         }
/* 659:868 */         if ((!par2ArrayOfStr[1].equalsIgnoreCase("empty")) && (!par2ArrayOfStr[1].equalsIgnoreCase("list")) && (!par2ArrayOfStr[1].equalsIgnoreCase("remove")))
/* 660:    */         {
/* 661:870 */           if (par2ArrayOfStr[1].equalsIgnoreCase("option"))
/* 662:    */           {
/* 663:872 */             if (par2ArrayOfStr.length == 3) {
/* 664:874 */               return getListOfStringsFromIterableMatchingLastWord(par2ArrayOfStr, func_147192_d().getTeamNames());
/* 665:    */             }
/* 666:877 */             if (par2ArrayOfStr.length == 4) {
/* 667:879 */               return getListOfStringsMatchingLastWord(par2ArrayOfStr, new String[] { "color", "friendlyfire", "seeFriendlyInvisibles" });
/* 668:    */             }
/* 669:882 */             if (par2ArrayOfStr.length == 5)
/* 670:    */             {
/* 671:884 */               if (par2ArrayOfStr[3].equalsIgnoreCase("color")) {
/* 672:886 */                 return getListOfStringsFromIterableMatchingLastWord(par2ArrayOfStr, EnumChatFormatting.getValidValues(true, false));
/* 673:    */               }
/* 674:889 */               if ((par2ArrayOfStr[3].equalsIgnoreCase("friendlyfire")) || (par2ArrayOfStr[3].equalsIgnoreCase("seeFriendlyInvisibles"))) {
/* 675:891 */                 return getListOfStringsMatchingLastWord(par2ArrayOfStr, new String[] { "true", "false" });
/* 676:    */               }
/* 677:    */             }
/* 678:    */           }
/* 679:    */         }
/* 680:896 */         else if (par2ArrayOfStr.length == 3) {
/* 681:898 */           return getListOfStringsFromIterableMatchingLastWord(par2ArrayOfStr, func_147192_d().getTeamNames());
/* 682:    */         }
/* 683:    */       }
/* 684:    */     }
/* 685:903 */     return null;
/* 686:    */   }
/* 687:    */   
/* 688:    */   protected List func_147184_a(boolean p_147184_1_)
/* 689:    */   {
/* 690:909 */     Collection var2 = func_147192_d().getScoreObjectives();
/* 691:910 */     ArrayList var3 = new ArrayList();
/* 692:911 */     Iterator var4 = var2.iterator();
/* 693:913 */     while (var4.hasNext())
/* 694:    */     {
/* 695:915 */       ScoreObjective var5 = (ScoreObjective)var4.next();
/* 696:917 */       if ((!p_147184_1_) || (!var5.getCriteria().isReadOnly())) {
/* 697:919 */         var3.add(var5.getName());
/* 698:    */       }
/* 699:    */     }
/* 700:923 */     return var3;
/* 701:    */   }
/* 702:    */   
/* 703:    */   public boolean isUsernameIndex(String[] par1ArrayOfStr, int par2)
/* 704:    */   {
/* 705:931 */     return par2 == 2;
/* 706:    */   }
/* 707:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.command.server.CommandScoreboard
 * JD-Core Version:    0.7.0.1
 */