/*   1:    */ package net.minecraft.command;
/*   2:    */ 
/*   3:    */ import java.util.Collections;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import java.util.Set;
/*   9:    */ import java.util.regex.Matcher;
/*  10:    */ import java.util.regex.Pattern;
/*  11:    */ import net.minecraft.entity.player.EntityPlayerMP;
/*  12:    */ import net.minecraft.server.MinecraftServer;
/*  13:    */ import net.minecraft.server.management.ServerConfigurationManager;
/*  14:    */ import net.minecraft.util.ChunkCoordinates;
/*  15:    */ import net.minecraft.util.IChatComponent;
/*  16:    */ import net.minecraft.util.MathHelper;
/*  17:    */ import net.minecraft.world.World;
/*  18:    */ import net.minecraft.world.WorldSettings.GameType;
/*  19:    */ 
/*  20:    */ public class PlayerSelector
/*  21:    */ {
/*  22: 23 */   private static final Pattern tokenPattern = Pattern.compile("^@([parf])(?:\\[([\\w=,!-]*)\\])?$");
/*  23: 28 */   private static final Pattern intListPattern = Pattern.compile("\\G([-!]?[\\w-]*)(?:$|,)");
/*  24: 33 */   private static final Pattern keyValueListPattern = Pattern.compile("\\G(\\w+)=([-!]?[\\w-]*)(?:$|,)");
/*  25:    */   private static final String __OBFID = "CL_00000086";
/*  26:    */   
/*  27:    */   public static EntityPlayerMP matchOnePlayer(ICommandSender par0ICommandSender, String par1Str)
/*  28:    */   {
/*  29: 41 */     EntityPlayerMP[] var2 = matchPlayers(par0ICommandSender, par1Str);
/*  30: 42 */     return (var2 != null) && (var2.length == 1) ? var2[0] : null;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public static IChatComponent func_150869_b(ICommandSender p_150869_0_, String p_150869_1_)
/*  34:    */   {
/*  35: 47 */     EntityPlayerMP[] var2 = matchPlayers(p_150869_0_, p_150869_1_);
/*  36: 49 */     if ((var2 != null) && (var2.length != 0))
/*  37:    */     {
/*  38: 51 */       IChatComponent[] var3 = new IChatComponent[var2.length];
/*  39: 53 */       for (int var4 = 0; var4 < var3.length; var4++) {
/*  40: 55 */         var3[var4] = var2[var4].func_145748_c_();
/*  41:    */       }
/*  42: 58 */       return CommandBase.joinNiceString(var3);
/*  43:    */     }
/*  44: 62 */     return null;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public static EntityPlayerMP[] matchPlayers(ICommandSender par0ICommandSender, String par1Str)
/*  48:    */   {
/*  49: 71 */     Matcher var2 = tokenPattern.matcher(par1Str);
/*  50: 73 */     if (!var2.matches()) {
/*  51: 75 */       return null;
/*  52:    */     }
/*  53: 79 */     Map var3 = getArgumentMap(var2.group(2));
/*  54: 80 */     String var4 = var2.group(1);
/*  55: 81 */     int var5 = getDefaultMinimumRange(var4);
/*  56: 82 */     int var6 = getDefaultMaximumRange(var4);
/*  57: 83 */     int var7 = getDefaultMinimumLevel(var4);
/*  58: 84 */     int var8 = getDefaultMaximumLevel(var4);
/*  59: 85 */     int var9 = getDefaultCount(var4);
/*  60: 86 */     int var10 = WorldSettings.GameType.NOT_SET.getID();
/*  61: 87 */     ChunkCoordinates var11 = par0ICommandSender.getPlayerCoordinates();
/*  62: 88 */     Map var12 = func_96560_a(var3);
/*  63: 89 */     String var13 = null;
/*  64: 90 */     String var14 = null;
/*  65: 91 */     boolean var15 = false;
/*  66: 93 */     if (var3.containsKey("rm"))
/*  67:    */     {
/*  68: 95 */       var5 = MathHelper.parseIntWithDefault((String)var3.get("rm"), var5);
/*  69: 96 */       var15 = true;
/*  70:    */     }
/*  71: 99 */     if (var3.containsKey("r"))
/*  72:    */     {
/*  73:101 */       var6 = MathHelper.parseIntWithDefault((String)var3.get("r"), var6);
/*  74:102 */       var15 = true;
/*  75:    */     }
/*  76:105 */     if (var3.containsKey("lm")) {
/*  77:107 */       var7 = MathHelper.parseIntWithDefault((String)var3.get("lm"), var7);
/*  78:    */     }
/*  79:110 */     if (var3.containsKey("l")) {
/*  80:112 */       var8 = MathHelper.parseIntWithDefault((String)var3.get("l"), var8);
/*  81:    */     }
/*  82:115 */     if (var3.containsKey("x"))
/*  83:    */     {
/*  84:117 */       var11.posX = MathHelper.parseIntWithDefault((String)var3.get("x"), var11.posX);
/*  85:118 */       var15 = true;
/*  86:    */     }
/*  87:121 */     if (var3.containsKey("y"))
/*  88:    */     {
/*  89:123 */       var11.posY = MathHelper.parseIntWithDefault((String)var3.get("y"), var11.posY);
/*  90:124 */       var15 = true;
/*  91:    */     }
/*  92:127 */     if (var3.containsKey("z"))
/*  93:    */     {
/*  94:129 */       var11.posZ = MathHelper.parseIntWithDefault((String)var3.get("z"), var11.posZ);
/*  95:130 */       var15 = true;
/*  96:    */     }
/*  97:133 */     if (var3.containsKey("m")) {
/*  98:135 */       var10 = MathHelper.parseIntWithDefault((String)var3.get("m"), var10);
/*  99:    */     }
/* 100:138 */     if (var3.containsKey("c")) {
/* 101:140 */       var9 = MathHelper.parseIntWithDefault((String)var3.get("c"), var9);
/* 102:    */     }
/* 103:143 */     if (var3.containsKey("team")) {
/* 104:145 */       var14 = (String)var3.get("team");
/* 105:    */     }
/* 106:148 */     if (var3.containsKey("name")) {
/* 107:150 */       var13 = (String)var3.get("name");
/* 108:    */     }
/* 109:153 */     World var16 = var15 ? par0ICommandSender.getEntityWorld() : null;
/* 110:156 */     if ((!var4.equals("p")) && (!var4.equals("a")))
/* 111:    */     {
/* 112:158 */       if (!var4.equals("r")) {
/* 113:160 */         return null;
/* 114:    */       }
/* 115:164 */       List var17 = MinecraftServer.getServer().getConfigurationManager().findPlayers(var11, var5, var6, 0, var10, var7, var8, var12, var13, var14, var16);
/* 116:165 */       Collections.shuffle(var17);
/* 117:166 */       var17 = var17.subList(0, Math.min(var9, var17.size()));
/* 118:167 */       return (var17 != null) && (!var17.isEmpty()) ? (EntityPlayerMP[])var17.toArray(new EntityPlayerMP[0]) : new EntityPlayerMP[0];
/* 119:    */     }
/* 120:172 */     List var17 = MinecraftServer.getServer().getConfigurationManager().findPlayers(var11, var5, var6, var9, var10, var7, var8, var12, var13, var14, var16);
/* 121:173 */     return (var17 != null) && (!var17.isEmpty()) ? (EntityPlayerMP[])var17.toArray(new EntityPlayerMP[0]) : new EntityPlayerMP[0];
/* 122:    */   }
/* 123:    */   
/* 124:    */   public static Map func_96560_a(Map par0Map)
/* 125:    */   {
/* 126:180 */     HashMap var1 = new HashMap();
/* 127:181 */     Iterator var2 = par0Map.keySet().iterator();
/* 128:183 */     while (var2.hasNext())
/* 129:    */     {
/* 130:185 */       String var3 = (String)var2.next();
/* 131:187 */       if ((var3.startsWith("score_")) && (var3.length() > "score_".length()))
/* 132:    */       {
/* 133:189 */         String var4 = var3.substring("score_".length());
/* 134:190 */         var1.put(var4, Integer.valueOf(MathHelper.parseIntWithDefault((String)par0Map.get(var3), 1)));
/* 135:    */       }
/* 136:    */     }
/* 137:194 */     return var1;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public static boolean matchesMultiplePlayers(String par0Str)
/* 141:    */   {
/* 142:202 */     Matcher var1 = tokenPattern.matcher(par0Str);
/* 143:204 */     if (var1.matches())
/* 144:    */     {
/* 145:206 */       Map var2 = getArgumentMap(var1.group(2));
/* 146:207 */       String var3 = var1.group(1);
/* 147:208 */       int var4 = getDefaultCount(var3);
/* 148:210 */       if (var2.containsKey("c")) {
/* 149:212 */         var4 = MathHelper.parseIntWithDefault((String)var2.get("c"), var4);
/* 150:    */       }
/* 151:215 */       return var4 != 1;
/* 152:    */     }
/* 153:219 */     return false;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public static boolean hasTheseArguments(String par0Str, String par1Str)
/* 157:    */   {
/* 158:228 */     Matcher var2 = tokenPattern.matcher(par0Str);
/* 159:230 */     if (var2.matches())
/* 160:    */     {
/* 161:232 */       String var3 = var2.group(1);
/* 162:233 */       return (par1Str == null) || (par1Str.equals(var3));
/* 163:    */     }
/* 164:237 */     return false;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public static boolean hasArguments(String par0Str)
/* 168:    */   {
/* 169:246 */     return hasTheseArguments(par0Str, null);
/* 170:    */   }
/* 171:    */   
/* 172:    */   private static final int getDefaultMinimumRange(String par0Str)
/* 173:    */   {
/* 174:254 */     return 0;
/* 175:    */   }
/* 176:    */   
/* 177:    */   private static final int getDefaultMaximumRange(String par0Str)
/* 178:    */   {
/* 179:262 */     return 0;
/* 180:    */   }
/* 181:    */   
/* 182:    */   private static final int getDefaultMaximumLevel(String par0Str)
/* 183:    */   {
/* 184:270 */     return 2147483647;
/* 185:    */   }
/* 186:    */   
/* 187:    */   private static final int getDefaultMinimumLevel(String par0Str)
/* 188:    */   {
/* 189:278 */     return 0;
/* 190:    */   }
/* 191:    */   
/* 192:    */   private static final int getDefaultCount(String par0Str)
/* 193:    */   {
/* 194:286 */     return par0Str.equals("a") ? 0 : 1;
/* 195:    */   }
/* 196:    */   
/* 197:    */   private static Map getArgumentMap(String par0Str)
/* 198:    */   {
/* 199:294 */     HashMap var1 = new HashMap();
/* 200:296 */     if (par0Str == null) {
/* 201:298 */       return var1;
/* 202:    */     }
/* 203:302 */     Matcher var2 = intListPattern.matcher(par0Str);
/* 204:303 */     int var3 = 0;
/* 205:306 */     for (int var4 = -1; var2.find(); var4 = var2.end())
/* 206:    */     {
/* 207:308 */       String var5 = null;
/* 208:310 */       switch (var3++)
/* 209:    */       {
/* 210:    */       case 0: 
/* 211:313 */         var5 = "x";
/* 212:314 */         break;
/* 213:    */       case 1: 
/* 214:317 */         var5 = "y";
/* 215:318 */         break;
/* 216:    */       case 2: 
/* 217:321 */         var5 = "z";
/* 218:322 */         break;
/* 219:    */       case 3: 
/* 220:325 */         var5 = "r";
/* 221:    */       }
/* 222:328 */       if ((var5 != null) && (var2.group(1).length() > 0)) {
/* 223:330 */         var1.put(var5, var2.group(1));
/* 224:    */       }
/* 225:    */     }
/* 226:334 */     if (var4 < par0Str.length())
/* 227:    */     {
/* 228:336 */       var2 = keyValueListPattern.matcher(var4 == -1 ? par0Str : par0Str.substring(var4));
/* 229:338 */       while (var2.find()) {
/* 230:340 */         var1.put(var2.group(1), var2.group(2));
/* 231:    */       }
/* 232:    */     }
/* 233:344 */     return var1;
/* 234:    */   }
/* 235:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.command.PlayerSelector
 * JD-Core Version:    0.7.0.1
 */