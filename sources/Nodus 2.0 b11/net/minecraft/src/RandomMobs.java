/*   1:    */ package net.minecraft.src;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Field;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import java.util.Random;
/*   9:    */ import java.util.UUID;
/*  10:    */ import net.minecraft.client.renderer.RenderGlobal;
/*  11:    */ import net.minecraft.entity.Entity;
/*  12:    */ import net.minecraft.entity.EntityLiving;
/*  13:    */ import net.minecraft.util.ResourceLocation;
/*  14:    */ import net.minecraft.world.World;
/*  15:    */ import net.minecraft.world.WorldServer;
/*  16:    */ 
/*  17:    */ public class RandomMobs
/*  18:    */ {
/*  19: 19 */   private static Map textureVariantsMap = new HashMap();
/*  20: 20 */   private static RenderGlobal renderGlobal = null;
/*  21: 21 */   private static boolean initialized = false;
/*  22: 22 */   private static Random random = new Random();
/*  23: 23 */   private static Field fieldEntityUuid = getField(Entity.class, UUID.class);
/*  24: 24 */   private static boolean working = false;
/*  25:    */   
/*  26:    */   public static void entityLoaded(Entity entity)
/*  27:    */   {
/*  28: 28 */     if ((entity instanceof EntityLiving))
/*  29:    */     {
/*  30: 30 */       EntityLiving el = (EntityLiving)entity;
/*  31: 31 */       WorldServer ws = Config.getWorldServer();
/*  32: 33 */       if (ws != null)
/*  33:    */       {
/*  34: 35 */         Entity es = ws.getEntityByID(entity.getEntityId());
/*  35: 37 */         if ((es instanceof EntityLiving))
/*  36:    */         {
/*  37: 39 */           EntityLiving els = (EntityLiving)es;
/*  38: 41 */           if (fieldEntityUuid != null) {
/*  39:    */             try
/*  40:    */             {
/*  41: 45 */               Object e = fieldEntityUuid.get(els);
/*  42: 46 */               fieldEntityUuid.set(el, e);
/*  43:    */             }
/*  44:    */             catch (Exception var6)
/*  45:    */             {
/*  46: 50 */               var6.printStackTrace();
/*  47: 51 */               fieldEntityUuid = null;
/*  48:    */             }
/*  49:    */           }
/*  50:    */         }
/*  51:    */       }
/*  52:    */     }
/*  53:    */   }
/*  54:    */   
/*  55:    */   private static Field getField(Class cls, Class fieldType)
/*  56:    */   {
/*  57:    */     try
/*  58:    */     {
/*  59: 63 */       Field[] e = cls.getDeclaredFields();
/*  60: 65 */       for (int i = 0; i < e.length; i++)
/*  61:    */       {
/*  62: 67 */         Field field = e[i];
/*  63: 68 */         Class type = field.getType();
/*  64: 70 */         if (type == fieldType)
/*  65:    */         {
/*  66: 72 */           field.setAccessible(true);
/*  67: 73 */           return field;
/*  68:    */         }
/*  69:    */       }
/*  70: 77 */       return null;
/*  71:    */     }
/*  72:    */     catch (Exception var6)
/*  73:    */     {
/*  74: 81 */       var6.printStackTrace();
/*  75:    */     }
/*  76: 82 */     return null;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public static void worldChanged(World oldWorld, World newWorld)
/*  80:    */   {
/*  81: 88 */     if (newWorld != null)
/*  82:    */     {
/*  83: 90 */       List entityList = newWorld.getLoadedEntityList();
/*  84: 92 */       for (int e = 0; e < entityList.size(); e++)
/*  85:    */       {
/*  86: 94 */         Entity entity = (Entity)entityList.get(e);
/*  87: 95 */         entityLoaded(entity);
/*  88:    */       }
/*  89:    */     }
/*  90:    */   }
/*  91:    */   
/*  92:    */   public static ResourceLocation getTextureLocation(ResourceLocation loc)
/*  93:    */   {
/*  94:102 */     if (working) {
/*  95:104 */       return loc;
/*  96:    */     }
/*  97:    */     try
/*  98:    */     {
/*  99:112 */       working = true;
/* 100:114 */       if (!initialized) {
/* 101:116 */         initialize();
/* 102:    */       }
/* 103:    */       ResourceLocation localResourceLocation1;
/* 104:119 */       if (renderGlobal == null)
/* 105:    */       {
/* 106:121 */         ResourceLocation entity1 = loc;
/* 107:122 */         return entity1;
/* 108:    */       }
/* 109:125 */       Entity entity = renderGlobal.renderedEntity;
/* 110:128 */       if (entity == null)
/* 111:    */       {
/* 112:130 */         ResourceLocation name1 = loc;
/* 113:131 */         return name1;
/* 114:    */       }
/* 115:134 */       if (!(entity instanceof EntityLiving))
/* 116:    */       {
/* 117:136 */         ResourceLocation name1 = loc;
/* 118:137 */         return name1;
/* 119:    */       }
/* 120:140 */       String name = loc.getResourcePath();
/* 121:142 */       if (name.startsWith("textures/entity/"))
/* 122:    */       {
/* 123:144 */         long uuidLow1 = entity.getUniqueID().getLeastSignificantBits();
/* 124:145 */         int id = (int)(uuidLow1 & 0x7FFFFFFF);
/* 125:146 */         ResourceLocation var6 = getTextureLocation(loc, id);
/* 126:147 */         return var6;
/* 127:    */       }
/* 128:150 */       uuidLow = loc;
/* 129:    */     }
/* 130:    */     finally
/* 131:    */     {
/* 132:    */       ResourceLocation uuidLow;
/* 133:154 */       working = false;
/* 134:    */     }
/* 135:    */     ResourceLocation uuidLow;
/* 136:154 */     working = false;
/* 137:    */     
/* 138:    */ 
/* 139:157 */     return uuidLow;
/* 140:    */   }
/* 141:    */   
/* 142:    */   private static ResourceLocation getTextureLocation(ResourceLocation loc, int randomId)
/* 143:    */   {
/* 144:163 */     if (randomId <= 0) {
/* 145:165 */       return loc;
/* 146:    */     }
/* 147:169 */     String name = loc.getResourcePath();
/* 148:170 */     ResourceLocation[] texLocs = (ResourceLocation[])textureVariantsMap.get(name);
/* 149:172 */     if (texLocs == null)
/* 150:    */     {
/* 151:174 */       texLocs = getTextureVariants(loc);
/* 152:175 */       textureVariantsMap.put(name, texLocs);
/* 153:    */     }
/* 154:178 */     if ((texLocs != null) && (texLocs.length > 0))
/* 155:    */     {
/* 156:180 */       int index = randomId % texLocs.length;
/* 157:181 */       ResourceLocation texLoc = texLocs[index];
/* 158:182 */       return texLoc;
/* 159:    */     }
/* 160:186 */     return loc;
/* 161:    */   }
/* 162:    */   
/* 163:    */   private static ResourceLocation[] getTextureVariants(ResourceLocation loc)
/* 164:    */   {
/* 165:193 */     TextureUtils.getTexture(loc);
/* 166:194 */     ResourceLocation[] texLocs = new ResourceLocation[0];
/* 167:195 */     String name = loc.getResourcePath();
/* 168:196 */     int pointPos = name.lastIndexOf('.');
/* 169:198 */     if (pointPos < 0) {
/* 170:200 */       return texLocs;
/* 171:    */     }
/* 172:204 */     String prefix = name.substring(0, pointPos);
/* 173:205 */     String suffix = name.substring(pointPos);
/* 174:206 */     String texEntStr = "textures/entity/";
/* 175:208 */     if (!prefix.startsWith(texEntStr)) {
/* 176:210 */       return texLocs;
/* 177:    */     }
/* 178:214 */     prefix = prefix.substring(texEntStr.length());
/* 179:215 */     prefix = "mcpatcher/mob/" + prefix;
/* 180:216 */     int countVariants = getCountTextureVariants(prefix, suffix);
/* 181:218 */     if (countVariants <= 1) {
/* 182:220 */       return texLocs;
/* 183:    */     }
/* 184:224 */     texLocs = new ResourceLocation[countVariants];
/* 185:225 */     texLocs[0] = loc;
/* 186:227 */     for (int i = 1; i < texLocs.length; i++)
/* 187:    */     {
/* 188:229 */       int texNum = i + 1;
/* 189:230 */       String texName = prefix + texNum + suffix;
/* 190:231 */       texLocs[i] = new ResourceLocation(loc.getResourceDomain(), texName);
/* 191:232 */       TextureUtils.getTexture(texLocs[i]);
/* 192:    */     }
/* 193:235 */     Config.dbg("RandomMobs: " + loc + ", variants: " + texLocs.length);
/* 194:236 */     return texLocs;
/* 195:    */   }
/* 196:    */   
/* 197:    */   private static int getCountTextureVariants(String prefix, String suffix)
/* 198:    */   {
/* 199:244 */     short maxNum = 1000;
/* 200:246 */     for (int num = 2; num < maxNum; num++)
/* 201:    */     {
/* 202:248 */       String variant = prefix + num + suffix;
/* 203:249 */       ResourceLocation loc = new ResourceLocation(variant);
/* 204:251 */       if (!Config.hasResource(loc)) {
/* 205:253 */         return num - 1;
/* 206:    */       }
/* 207:    */     }
/* 208:257 */     return maxNum;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public static void resetTextures()
/* 212:    */   {
/* 213:262 */     textureVariantsMap.clear();
/* 214:264 */     if (Config.isRandomMobs()) {
/* 215:266 */       initialize();
/* 216:    */     }
/* 217:    */   }
/* 218:    */   
/* 219:    */   private static void initialize()
/* 220:    */   {
/* 221:272 */     renderGlobal = Config.getRenderGlobal();
/* 222:274 */     if (renderGlobal != null)
/* 223:    */     {
/* 224:276 */       initialized = true;
/* 225:277 */       ArrayList list = new ArrayList();
/* 226:278 */       list.add("bat");
/* 227:279 */       list.add("blaze");
/* 228:280 */       list.add("cat/black");
/* 229:281 */       list.add("cat/ocelot");
/* 230:282 */       list.add("cat/red");
/* 231:283 */       list.add("cat/siamese");
/* 232:284 */       list.add("chicken");
/* 233:285 */       list.add("cow/cow");
/* 234:286 */       list.add("cow/mooshroom");
/* 235:287 */       list.add("creeper/creeper");
/* 236:288 */       list.add("enderman/enderman");
/* 237:289 */       list.add("enderman/enderman_eyes");
/* 238:290 */       list.add("ghast/ghast");
/* 239:291 */       list.add("ghast/ghast_shooting");
/* 240:292 */       list.add("iron_golem");
/* 241:293 */       list.add("pig/pig");
/* 242:294 */       list.add("sheep/sheep");
/* 243:295 */       list.add("sheep/sheep_fur");
/* 244:296 */       list.add("silverfish");
/* 245:297 */       list.add("skeleton/skeleton");
/* 246:298 */       list.add("skeleton/wither_skeleton");
/* 247:299 */       list.add("slime/slime");
/* 248:300 */       list.add("slime/magmacube");
/* 249:301 */       list.add("snowman");
/* 250:302 */       list.add("spider/cave_spider");
/* 251:303 */       list.add("spider/spider");
/* 252:304 */       list.add("spider_eyes");
/* 253:305 */       list.add("squid");
/* 254:306 */       list.add("villager/villager");
/* 255:307 */       list.add("villager/butcher");
/* 256:308 */       list.add("villager/farmer");
/* 257:309 */       list.add("villager/librarian");
/* 258:310 */       list.add("villager/priest");
/* 259:311 */       list.add("villager/smith");
/* 260:312 */       list.add("wither/wither");
/* 261:313 */       list.add("wither/wither_armor");
/* 262:314 */       list.add("wither/wither_invulnerable");
/* 263:315 */       list.add("wolf/wolf");
/* 264:316 */       list.add("wolf/wolf_angry");
/* 265:317 */       list.add("wolf/wolf_collar");
/* 266:318 */       list.add("wolf/wolf_tame");
/* 267:319 */       list.add("zombie_pigman");
/* 268:320 */       list.add("zombie/zombie");
/* 269:321 */       list.add("zombie/zombie_villager");
/* 270:323 */       for (int i = 0; i < list.size(); i++)
/* 271:    */       {
/* 272:325 */         String name = (String)list.get(i);
/* 273:326 */         String tex = "textures/entity/" + name + ".png";
/* 274:327 */         ResourceLocation texLoc = new ResourceLocation(tex);
/* 275:329 */         if (!Config.hasResource(texLoc)) {
/* 276:331 */           Config.warn("Not found: " + texLoc);
/* 277:    */         }
/* 278:334 */         getTextureLocation(texLoc, 100);
/* 279:    */       }
/* 280:    */     }
/* 281:    */   }
/* 282:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.src.RandomMobs
 * JD-Core Version:    0.7.0.1
 */