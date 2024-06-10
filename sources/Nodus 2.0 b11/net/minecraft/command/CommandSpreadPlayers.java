/*   1:    */ package net.minecraft.command;
/*   2:    */ 
/*   3:    */ import com.google.common.collect.Lists;
/*   4:    */ import com.google.common.collect.Maps;
/*   5:    */ import com.google.common.collect.Sets;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.Collections;
/*   8:    */ import java.util.HashMap;
/*   9:    */ import java.util.HashSet;
/*  10:    */ import java.util.Iterator;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Random;
/*  13:    */ import net.minecraft.block.Block;
/*  14:    */ import net.minecraft.block.material.Material;
/*  15:    */ import net.minecraft.entity.EntityLivingBase;
/*  16:    */ import net.minecraft.entity.player.EntityPlayer;
/*  17:    */ import net.minecraft.entity.player.EntityPlayerMP;
/*  18:    */ import net.minecraft.scoreboard.Team;
/*  19:    */ import net.minecraft.server.MinecraftServer;
/*  20:    */ import net.minecraft.server.management.ServerConfigurationManager;
/*  21:    */ import net.minecraft.util.ChatComponentTranslation;
/*  22:    */ import net.minecraft.util.MathHelper;
/*  23:    */ import net.minecraft.world.World;
/*  24:    */ 
/*  25:    */ public class CommandSpreadPlayers
/*  26:    */   extends CommandBase
/*  27:    */ {
/*  28:    */   private static final String __OBFID = "CL_00001080";
/*  29:    */   
/*  30:    */   public String getCommandName()
/*  31:    */   {
/*  32: 29 */     return "spreadplayers";
/*  33:    */   }
/*  34:    */   
/*  35:    */   public int getRequiredPermissionLevel()
/*  36:    */   {
/*  37: 37 */     return 2;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public String getCommandUsage(ICommandSender par1ICommandSender)
/*  41:    */   {
/*  42: 42 */     return "commands.spreadplayers.usage";
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
/*  46:    */   {
/*  47: 47 */     if (par2ArrayOfStr.length < 6) {
/*  48: 49 */       throw new WrongUsageException("commands.spreadplayers.usage", new Object[0]);
/*  49:    */     }
/*  50: 53 */     byte var3 = 0;
/*  51: 54 */     int var17 = var3 + 1;
/*  52: 55 */     double var4 = func_110666_a(par1ICommandSender, (0.0D / 0.0D), par2ArrayOfStr[var3]);
/*  53: 56 */     double var6 = func_110666_a(par1ICommandSender, (0.0D / 0.0D), par2ArrayOfStr[(var17++)]);
/*  54: 57 */     double var8 = parseDoubleWithMin(par1ICommandSender, par2ArrayOfStr[(var17++)], 0.0D);
/*  55: 58 */     double var10 = parseDoubleWithMin(par1ICommandSender, par2ArrayOfStr[(var17++)], var8 + 1.0D);
/*  56: 59 */     boolean var12 = parseBoolean(par1ICommandSender, par2ArrayOfStr[(var17++)]);
/*  57: 60 */     ArrayList var13 = Lists.newArrayList();
/*  58: 64 */     while (var17 < par2ArrayOfStr.length)
/*  59:    */     {
/*  60: 66 */       String var14 = par2ArrayOfStr[(var17++)];
/*  61: 68 */       if (PlayerSelector.hasArguments(var14))
/*  62:    */       {
/*  63: 70 */         EntityPlayerMP[] var18 = PlayerSelector.matchPlayers(par1ICommandSender, var14);
/*  64: 72 */         if ((var18 == null) || (var18.length == 0)) {
/*  65: 74 */           throw new PlayerNotFoundException();
/*  66:    */         }
/*  67: 77 */         Collections.addAll(var13, var18);
/*  68:    */       }
/*  69:    */       else
/*  70:    */       {
/*  71: 81 */         EntityPlayerMP var15 = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(var14);
/*  72: 83 */         if (var15 == null) {
/*  73: 85 */           throw new PlayerNotFoundException();
/*  74:    */         }
/*  75: 88 */         var13.add(var15);
/*  76:    */       }
/*  77:    */     }
/*  78: 92 */     if (var13.isEmpty()) {
/*  79: 94 */       throw new PlayerNotFoundException();
/*  80:    */     }
/*  81: 97 */     par1ICommandSender.addChatMessage(new ChatComponentTranslation("commands.spreadplayers.spreading." + (var12 ? "teams" : "players"), new Object[] { Integer.valueOf(var13.size()), Double.valueOf(var10), Double.valueOf(var4), Double.valueOf(var6), Double.valueOf(var8) }));
/*  82: 98 */     func_110669_a(par1ICommandSender, var13, new Position(var4, var6), var8, var10, ((EntityLivingBase)var13.get(0)).worldObj, var12);
/*  83:    */   }
/*  84:    */   
/*  85:    */   private void func_110669_a(ICommandSender par1ICommandSender, List par2List, Position par3CommandSpreadPlayersPosition, double par4, double par6, World par8World, boolean par9)
/*  86:    */   {
/*  87:106 */     Random var10 = new Random();
/*  88:107 */     double var11 = par3CommandSpreadPlayersPosition.field_111101_a - par6;
/*  89:108 */     double var13 = par3CommandSpreadPlayersPosition.field_111100_b - par6;
/*  90:109 */     double var15 = par3CommandSpreadPlayersPosition.field_111101_a + par6;
/*  91:110 */     double var17 = par3CommandSpreadPlayersPosition.field_111100_b + par6;
/*  92:111 */     Position[] var19 = func_110670_a(var10, par9 ? func_110667_a(par2List) : par2List.size(), var11, var13, var15, var17);
/*  93:112 */     int var20 = func_110668_a(par3CommandSpreadPlayersPosition, par4, par8World, var10, var11, var13, var15, var17, var19, par9);
/*  94:113 */     double var21 = func_110671_a(par2List, par8World, var19, par9);
/*  95:114 */     notifyAdmins(par1ICommandSender, "commands.spreadplayers.success." + (par9 ? "teams" : "players"), new Object[] { Integer.valueOf(var19.length), Double.valueOf(par3CommandSpreadPlayersPosition.field_111101_a), Double.valueOf(par3CommandSpreadPlayersPosition.field_111100_b) });
/*  96:116 */     if (var19.length > 1) {
/*  97:118 */       par1ICommandSender.addChatMessage(new ChatComponentTranslation("commands.spreadplayers.info." + (par9 ? "teams" : "players"), new Object[] { String.format("%.2f", new Object[] { Double.valueOf(var21) }), Integer.valueOf(var20) }));
/*  98:    */     }
/*  99:    */   }
/* 100:    */   
/* 101:    */   private int func_110667_a(List par1List)
/* 102:    */   {
/* 103:124 */     HashSet var2 = Sets.newHashSet();
/* 104:125 */     Iterator var3 = par1List.iterator();
/* 105:127 */     while (var3.hasNext())
/* 106:    */     {
/* 107:129 */       EntityLivingBase var4 = (EntityLivingBase)var3.next();
/* 108:131 */       if ((var4 instanceof EntityPlayer)) {
/* 109:133 */         var2.add(var4.getTeam());
/* 110:    */       } else {
/* 111:137 */         var2.add(null);
/* 112:    */       }
/* 113:    */     }
/* 114:141 */     return var2.size();
/* 115:    */   }
/* 116:    */   
/* 117:    */   private int func_110668_a(Position par1CommandSpreadPlayersPosition, double par2, World par4World, Random par5Random, double par6, double par8, double par10, double par12, Position[] par14ArrayOfCommandSpreadPlayersPosition, boolean par15)
/* 118:    */   {
/* 119:146 */     boolean var16 = true;
/* 120:147 */     double var18 = 3.402823466385289E+038D;
/* 121:150 */     for (int var17 = 0; (var17 < 10000) && (var16); var17++)
/* 122:    */     {
/* 123:152 */       var16 = false;
/* 124:153 */       var18 = 3.402823466385289E+038D;
/* 125:157 */       for (int var20 = 0; var20 < par14ArrayOfCommandSpreadPlayersPosition.length; var20++)
/* 126:    */       {
/* 127:159 */         Position var21 = par14ArrayOfCommandSpreadPlayersPosition[var20];
/* 128:160 */         int var22 = 0;
/* 129:161 */         Position var23 = new Position();
/* 130:163 */         for (int var24 = 0; var24 < par14ArrayOfCommandSpreadPlayersPosition.length; var24++) {
/* 131:165 */           if (var20 != var24)
/* 132:    */           {
/* 133:167 */             Position var25 = par14ArrayOfCommandSpreadPlayersPosition[var24];
/* 134:168 */             double var26 = var21.func_111099_a(var25);
/* 135:169 */             var18 = Math.min(var26, var18);
/* 136:171 */             if (var26 < par2)
/* 137:    */             {
/* 138:173 */               var22++;
/* 139:174 */               var23.field_111101_a += var25.field_111101_a - var21.field_111101_a;
/* 140:175 */               var23.field_111100_b += var25.field_111100_b - var21.field_111100_b;
/* 141:    */             }
/* 142:    */           }
/* 143:    */         }
/* 144:180 */         if (var22 > 0)
/* 145:    */         {
/* 146:182 */           var23.field_111101_a /= var22;
/* 147:183 */           var23.field_111100_b /= var22;
/* 148:184 */           double var30 = var23.func_111096_b();
/* 149:186 */           if (var30 > 0.0D)
/* 150:    */           {
/* 151:188 */             var23.func_111095_a();
/* 152:189 */             var21.func_111094_b(var23);
/* 153:    */           }
/* 154:    */           else
/* 155:    */           {
/* 156:193 */             var21.func_111097_a(par5Random, par6, par8, par10, par12);
/* 157:    */           }
/* 158:196 */           var16 = true;
/* 159:    */         }
/* 160:199 */         if (var21.func_111093_a(par6, par8, par10, par12)) {
/* 161:201 */           var16 = true;
/* 162:    */         }
/* 163:    */       }
/* 164:205 */       if (!var16)
/* 165:    */       {
/* 166:207 */         Position[] var28 = par14ArrayOfCommandSpreadPlayersPosition;
/* 167:208 */         int var29 = par14ArrayOfCommandSpreadPlayersPosition.length;
/* 168:210 */         for (int var22 = 0; var22 < var29; var22++)
/* 169:    */         {
/* 170:212 */           Position var23 = var28[var22];
/* 171:214 */           if (!var23.func_111098_b(par4World))
/* 172:    */           {
/* 173:216 */             var23.func_111097_a(par5Random, par6, par8, par10, par12);
/* 174:217 */             var16 = true;
/* 175:    */           }
/* 176:    */         }
/* 177:    */       }
/* 178:    */     }
/* 179:223 */     if (var17 >= 10000) {
/* 180:225 */       throw new CommandException("commands.spreadplayers.failure." + (par15 ? "teams" : "players"), new Object[] { Integer.valueOf(par14ArrayOfCommandSpreadPlayersPosition.length), Double.valueOf(par1CommandSpreadPlayersPosition.field_111101_a), Double.valueOf(par1CommandSpreadPlayersPosition.field_111100_b), String.format("%.2f", new Object[] { Double.valueOf(var18) }) });
/* 181:    */     }
/* 182:229 */     return var17;
/* 183:    */   }
/* 184:    */   
/* 185:    */   private double func_110671_a(List par1List, World par2World, Position[] par3ArrayOfCommandSpreadPlayersPosition, boolean par4)
/* 186:    */   {
/* 187:235 */     double var5 = 0.0D;
/* 188:236 */     int var7 = 0;
/* 189:237 */     HashMap var8 = Maps.newHashMap();
/* 190:239 */     for (int var9 = 0; var9 < par1List.size(); var9++)
/* 191:    */     {
/* 192:241 */       EntityLivingBase var10 = (EntityLivingBase)par1List.get(var9);
/* 193:    */       Position var11;
/* 194:    */       Position var11;
/* 195:244 */       if (par4)
/* 196:    */       {
/* 197:246 */         Team var12 = (var10 instanceof EntityPlayer) ? var10.getTeam() : null;
/* 198:248 */         if (!var8.containsKey(var12)) {
/* 199:250 */           var8.put(var12, par3ArrayOfCommandSpreadPlayersPosition[(var7++)]);
/* 200:    */         }
/* 201:253 */         var11 = (Position)var8.get(var12);
/* 202:    */       }
/* 203:    */       else
/* 204:    */       {
/* 205:257 */         var11 = par3ArrayOfCommandSpreadPlayersPosition[(var7++)];
/* 206:    */       }
/* 207:260 */       var10.setPositionAndUpdate(MathHelper.floor_double(var11.field_111101_a) + 0.5F, var11.func_111092_a(par2World), MathHelper.floor_double(var11.field_111100_b) + 0.5D);
/* 208:261 */       double var17 = 1.7976931348623157E+308D;
/* 209:263 */       for (int var14 = 0; var14 < par3ArrayOfCommandSpreadPlayersPosition.length; var14++) {
/* 210:265 */         if (var11 != par3ArrayOfCommandSpreadPlayersPosition[var14])
/* 211:    */         {
/* 212:267 */           double var15 = var11.func_111099_a(par3ArrayOfCommandSpreadPlayersPosition[var14]);
/* 213:268 */           var17 = Math.min(var15, var17);
/* 214:    */         }
/* 215:    */       }
/* 216:272 */       var5 += var17;
/* 217:    */     }
/* 218:275 */     var5 /= par1List.size();
/* 219:276 */     return var5;
/* 220:    */   }
/* 221:    */   
/* 222:    */   private Position[] func_110670_a(Random par1Random, int par2, double par3, double par5, double par7, double par9)
/* 223:    */   {
/* 224:281 */     Position[] var11 = new Position[par2];
/* 225:283 */     for (int var12 = 0; var12 < var11.length; var12++)
/* 226:    */     {
/* 227:285 */       Position var13 = new Position();
/* 228:286 */       var13.func_111097_a(par1Random, par3, par5, par7, par9);
/* 229:287 */       var11[var12] = var13;
/* 230:    */     }
/* 231:290 */     return var11;
/* 232:    */   }
/* 233:    */   
/* 234:    */   static class Position
/* 235:    */   {
/* 236:    */     double field_111101_a;
/* 237:    */     double field_111100_b;
/* 238:    */     private static final String __OBFID = "CL_00001105";
/* 239:    */     
/* 240:    */     Position() {}
/* 241:    */     
/* 242:    */     Position(double par1, double par3)
/* 243:    */     {
/* 244:303 */       this.field_111101_a = par1;
/* 245:304 */       this.field_111100_b = par3;
/* 246:    */     }
/* 247:    */     
/* 248:    */     double func_111099_a(Position par1CommandSpreadPlayersPosition)
/* 249:    */     {
/* 250:309 */       double var2 = this.field_111101_a - par1CommandSpreadPlayersPosition.field_111101_a;
/* 251:310 */       double var4 = this.field_111100_b - par1CommandSpreadPlayersPosition.field_111100_b;
/* 252:311 */       return Math.sqrt(var2 * var2 + var4 * var4);
/* 253:    */     }
/* 254:    */     
/* 255:    */     void func_111095_a()
/* 256:    */     {
/* 257:316 */       double var1 = func_111096_b();
/* 258:317 */       this.field_111101_a /= var1;
/* 259:318 */       this.field_111100_b /= var1;
/* 260:    */     }
/* 261:    */     
/* 262:    */     float func_111096_b()
/* 263:    */     {
/* 264:323 */       return MathHelper.sqrt_double(this.field_111101_a * this.field_111101_a + this.field_111100_b * this.field_111100_b);
/* 265:    */     }
/* 266:    */     
/* 267:    */     public void func_111094_b(Position par1CommandSpreadPlayersPosition)
/* 268:    */     {
/* 269:328 */       this.field_111101_a -= par1CommandSpreadPlayersPosition.field_111101_a;
/* 270:329 */       this.field_111100_b -= par1CommandSpreadPlayersPosition.field_111100_b;
/* 271:    */     }
/* 272:    */     
/* 273:    */     public boolean func_111093_a(double par1, double par3, double par5, double par7)
/* 274:    */     {
/* 275:334 */       boolean var9 = false;
/* 276:336 */       if (this.field_111101_a < par1)
/* 277:    */       {
/* 278:338 */         this.field_111101_a = par1;
/* 279:339 */         var9 = true;
/* 280:    */       }
/* 281:341 */       else if (this.field_111101_a > par5)
/* 282:    */       {
/* 283:343 */         this.field_111101_a = par5;
/* 284:344 */         var9 = true;
/* 285:    */       }
/* 286:347 */       if (this.field_111100_b < par3)
/* 287:    */       {
/* 288:349 */         this.field_111100_b = par3;
/* 289:350 */         var9 = true;
/* 290:    */       }
/* 291:352 */       else if (this.field_111100_b > par7)
/* 292:    */       {
/* 293:354 */         this.field_111100_b = par7;
/* 294:355 */         var9 = true;
/* 295:    */       }
/* 296:358 */       return var9;
/* 297:    */     }
/* 298:    */     
/* 299:    */     public int func_111092_a(World par1World)
/* 300:    */     {
/* 301:363 */       int var2 = MathHelper.floor_double(this.field_111101_a);
/* 302:364 */       int var3 = MathHelper.floor_double(this.field_111100_b);
/* 303:366 */       for (int var4 = 256; var4 > 0; var4--) {
/* 304:368 */         if (par1World.getBlock(var2, var4, var3).getMaterial() != Material.air) {
/* 305:370 */           return var4 + 1;
/* 306:    */         }
/* 307:    */       }
/* 308:374 */       return 257;
/* 309:    */     }
/* 310:    */     
/* 311:    */     public boolean func_111098_b(World par1World)
/* 312:    */     {
/* 313:379 */       int var2 = MathHelper.floor_double(this.field_111101_a);
/* 314:380 */       int var3 = MathHelper.floor_double(this.field_111100_b);
/* 315:381 */       short var4 = 256;
/* 316:383 */       if (var4 <= 0) {
/* 317:385 */         return false;
/* 318:    */       }
/* 319:389 */       Material var5 = par1World.getBlock(var2, var4, var3).getMaterial();
/* 320:390 */       return (!var5.isLiquid()) && (var5 != Material.fire);
/* 321:    */     }
/* 322:    */     
/* 323:    */     public void func_111097_a(Random par1Random, double par2, double par4, double par6, double par8)
/* 324:    */     {
/* 325:396 */       this.field_111101_a = MathHelper.getRandomDoubleInRange(par1Random, par2, par6);
/* 326:397 */       this.field_111100_b = MathHelper.getRandomDoubleInRange(par1Random, par4, par8);
/* 327:    */     }
/* 328:    */   }
/* 329:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.command.CommandSpreadPlayers
 * JD-Core Version:    0.7.0.1
 */