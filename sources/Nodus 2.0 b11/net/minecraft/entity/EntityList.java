/*   1:    */ package net.minecraft.entity;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Constructor;
/*   4:    */ import java.util.Collections;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.LinkedHashMap;
/*   7:    */ import java.util.Map;
/*   8:    */ import java.util.Set;
/*   9:    */ import net.minecraft.entity.ai.EntityMinecartMobSpawner;
/*  10:    */ import net.minecraft.entity.boss.EntityDragon;
/*  11:    */ import net.minecraft.entity.boss.EntityWither;
/*  12:    */ import net.minecraft.entity.item.EntityBoat;
/*  13:    */ import net.minecraft.entity.item.EntityEnderCrystal;
/*  14:    */ import net.minecraft.entity.item.EntityEnderEye;
/*  15:    */ import net.minecraft.entity.item.EntityEnderPearl;
/*  16:    */ import net.minecraft.entity.item.EntityExpBottle;
/*  17:    */ import net.minecraft.entity.item.EntityFallingBlock;
/*  18:    */ import net.minecraft.entity.item.EntityFireworkRocket;
/*  19:    */ import net.minecraft.entity.item.EntityItem;
/*  20:    */ import net.minecraft.entity.item.EntityItemFrame;
/*  21:    */ import net.minecraft.entity.item.EntityMinecartChest;
/*  22:    */ import net.minecraft.entity.item.EntityMinecartEmpty;
/*  23:    */ import net.minecraft.entity.item.EntityMinecartFurnace;
/*  24:    */ import net.minecraft.entity.item.EntityMinecartHopper;
/*  25:    */ import net.minecraft.entity.item.EntityMinecartTNT;
/*  26:    */ import net.minecraft.entity.item.EntityPainting;
/*  27:    */ import net.minecraft.entity.item.EntityTNTPrimed;
/*  28:    */ import net.minecraft.entity.item.EntityXPOrb;
/*  29:    */ import net.minecraft.entity.monster.EntityBlaze;
/*  30:    */ import net.minecraft.entity.monster.EntityCaveSpider;
/*  31:    */ import net.minecraft.entity.monster.EntityCreeper;
/*  32:    */ import net.minecraft.entity.monster.EntityEnderman;
/*  33:    */ import net.minecraft.entity.monster.EntityGhast;
/*  34:    */ import net.minecraft.entity.monster.EntityGiantZombie;
/*  35:    */ import net.minecraft.entity.monster.EntityIronGolem;
/*  36:    */ import net.minecraft.entity.monster.EntityMagmaCube;
/*  37:    */ import net.minecraft.entity.monster.EntityMob;
/*  38:    */ import net.minecraft.entity.monster.EntityPigZombie;
/*  39:    */ import net.minecraft.entity.monster.EntitySilverfish;
/*  40:    */ import net.minecraft.entity.monster.EntitySkeleton;
/*  41:    */ import net.minecraft.entity.monster.EntitySlime;
/*  42:    */ import net.minecraft.entity.monster.EntitySnowman;
/*  43:    */ import net.minecraft.entity.monster.EntitySpider;
/*  44:    */ import net.minecraft.entity.monster.EntityWitch;
/*  45:    */ import net.minecraft.entity.monster.EntityZombie;
/*  46:    */ import net.minecraft.entity.passive.EntityBat;
/*  47:    */ import net.minecraft.entity.passive.EntityChicken;
/*  48:    */ import net.minecraft.entity.passive.EntityCow;
/*  49:    */ import net.minecraft.entity.passive.EntityHorse;
/*  50:    */ import net.minecraft.entity.passive.EntityMooshroom;
/*  51:    */ import net.minecraft.entity.passive.EntityOcelot;
/*  52:    */ import net.minecraft.entity.passive.EntityPig;
/*  53:    */ import net.minecraft.entity.passive.EntitySheep;
/*  54:    */ import net.minecraft.entity.passive.EntitySquid;
/*  55:    */ import net.minecraft.entity.passive.EntityVillager;
/*  56:    */ import net.minecraft.entity.passive.EntityWolf;
/*  57:    */ import net.minecraft.entity.projectile.EntityArrow;
/*  58:    */ import net.minecraft.entity.projectile.EntityLargeFireball;
/*  59:    */ import net.minecraft.entity.projectile.EntityPotion;
/*  60:    */ import net.minecraft.entity.projectile.EntitySmallFireball;
/*  61:    */ import net.minecraft.entity.projectile.EntitySnowball;
/*  62:    */ import net.minecraft.entity.projectile.EntityWitherSkull;
/*  63:    */ import net.minecraft.nbt.NBTTagCompound;
/*  64:    */ import net.minecraft.stats.StatBase;
/*  65:    */ import net.minecraft.stats.StatList;
/*  66:    */ import net.minecraft.world.World;
/*  67:    */ import org.apache.logging.log4j.LogManager;
/*  68:    */ import org.apache.logging.log4j.Logger;
/*  69:    */ 
/*  70:    */ public class EntityList
/*  71:    */ {
/*  72: 71 */   private static final Logger logger = ;
/*  73: 74 */   private static Map stringToClassMapping = new HashMap();
/*  74: 77 */   private static Map classToStringMapping = new HashMap();
/*  75: 80 */   private static Map IDtoClassMapping = new HashMap();
/*  76: 83 */   private static Map classToIDMapping = new HashMap();
/*  77: 86 */   private static Map stringToIDMapping = new HashMap();
/*  78: 89 */   public static HashMap entityEggs = new LinkedHashMap();
/*  79:    */   private static final String __OBFID = "CL_00001538";
/*  80:    */   
/*  81:    */   private static void addMapping(Class par0Class, String par1Str, int par2)
/*  82:    */   {
/*  83: 97 */     if (stringToClassMapping.containsKey(par1Str)) {
/*  84: 99 */       throw new IllegalArgumentException("ID is already registered: " + par1Str);
/*  85:    */     }
/*  86:101 */     if (IDtoClassMapping.containsKey(Integer.valueOf(par2))) {
/*  87:103 */       throw new IllegalArgumentException("ID is already registered: " + par2);
/*  88:    */     }
/*  89:107 */     stringToClassMapping.put(par1Str, par0Class);
/*  90:108 */     classToStringMapping.put(par0Class, par1Str);
/*  91:109 */     IDtoClassMapping.put(Integer.valueOf(par2), par0Class);
/*  92:110 */     classToIDMapping.put(par0Class, Integer.valueOf(par2));
/*  93:111 */     stringToIDMapping.put(par1Str, Integer.valueOf(par2));
/*  94:    */   }
/*  95:    */   
/*  96:    */   private static void addMapping(Class par0Class, String par1Str, int par2, int par3, int par4)
/*  97:    */   {
/*  98:120 */     addMapping(par0Class, par1Str, par2);
/*  99:121 */     entityEggs.put(Integer.valueOf(par2), new EntityEggInfo(par2, par3, par4));
/* 100:    */   }
/* 101:    */   
/* 102:    */   public static Entity createEntityByName(String par0Str, World par1World)
/* 103:    */   {
/* 104:129 */     Entity var2 = null;
/* 105:    */     try
/* 106:    */     {
/* 107:133 */       Class var3 = (Class)stringToClassMapping.get(par0Str);
/* 108:135 */       if (var3 != null) {
/* 109:137 */         var2 = (Entity)var3.getConstructor(new Class[] { World.class }).newInstance(new Object[] { par1World });
/* 110:    */       }
/* 111:    */     }
/* 112:    */     catch (Exception var4)
/* 113:    */     {
/* 114:142 */       var4.printStackTrace();
/* 115:    */     }
/* 116:145 */     return var2;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public static Entity createEntityFromNBT(NBTTagCompound par0NBTTagCompound, World par1World)
/* 120:    */   {
/* 121:153 */     Entity var2 = null;
/* 122:155 */     if ("Minecart".equals(par0NBTTagCompound.getString("id")))
/* 123:    */     {
/* 124:157 */       switch (par0NBTTagCompound.getInteger("Type"))
/* 125:    */       {
/* 126:    */       case 0: 
/* 127:160 */         par0NBTTagCompound.setString("id", "MinecartRideable");
/* 128:161 */         break;
/* 129:    */       case 1: 
/* 130:164 */         par0NBTTagCompound.setString("id", "MinecartChest");
/* 131:165 */         break;
/* 132:    */       case 2: 
/* 133:168 */         par0NBTTagCompound.setString("id", "MinecartFurnace");
/* 134:    */       }
/* 135:171 */       par0NBTTagCompound.removeTag("Type");
/* 136:    */     }
/* 137:    */     try
/* 138:    */     {
/* 139:176 */       Class var3 = (Class)stringToClassMapping.get(par0NBTTagCompound.getString("id"));
/* 140:178 */       if (var3 != null) {
/* 141:180 */         var2 = (Entity)var3.getConstructor(new Class[] { World.class }).newInstance(new Object[] { par1World });
/* 142:    */       }
/* 143:    */     }
/* 144:    */     catch (Exception var4)
/* 145:    */     {
/* 146:185 */       var4.printStackTrace();
/* 147:    */     }
/* 148:188 */     if (var2 != null) {
/* 149:190 */       var2.readFromNBT(par0NBTTagCompound);
/* 150:    */     } else {
/* 151:194 */       logger.warn("Skipping Entity with id " + par0NBTTagCompound.getString("id"));
/* 152:    */     }
/* 153:197 */     return var2;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public static Entity createEntityByID(int par0, World par1World)
/* 157:    */   {
/* 158:205 */     Entity var2 = null;
/* 159:    */     try
/* 160:    */     {
/* 161:209 */       Class var3 = getClassFromID(par0);
/* 162:211 */       if (var3 != null) {
/* 163:213 */         var2 = (Entity)var3.getConstructor(new Class[] { World.class }).newInstance(new Object[] { par1World });
/* 164:    */       }
/* 165:    */     }
/* 166:    */     catch (Exception var4)
/* 167:    */     {
/* 168:218 */       var4.printStackTrace();
/* 169:    */     }
/* 170:221 */     if (var2 == null) {
/* 171:223 */       logger.warn("Skipping Entity with id " + par0);
/* 172:    */     }
/* 173:226 */     return var2;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public static int getEntityID(Entity par0Entity)
/* 177:    */   {
/* 178:234 */     Class var1 = par0Entity.getClass();
/* 179:235 */     return classToIDMapping.containsKey(var1) ? ((Integer)classToIDMapping.get(var1)).intValue() : 0;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public static Class getClassFromID(int par0)
/* 183:    */   {
/* 184:243 */     return (Class)IDtoClassMapping.get(Integer.valueOf(par0));
/* 185:    */   }
/* 186:    */   
/* 187:    */   public static String getEntityString(Entity par0Entity)
/* 188:    */   {
/* 189:251 */     return (String)classToStringMapping.get(par0Entity.getClass());
/* 190:    */   }
/* 191:    */   
/* 192:    */   public static String getStringFromID(int par0)
/* 193:    */   {
/* 194:259 */     Class var1 = getClassFromID(par0);
/* 195:260 */     return var1 != null ? (String)classToStringMapping.get(var1) : null;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public static Set func_151515_b()
/* 199:    */   {
/* 200:267 */     return Collections.unmodifiableSet(stringToIDMapping.keySet());
/* 201:    */   }
/* 202:    */   
/* 203:    */   static
/* 204:    */   {
/* 205:272 */     addMapping(EntityItem.class, "Item", 1);
/* 206:273 */     addMapping(EntityXPOrb.class, "XPOrb", 2);
/* 207:274 */     addMapping(EntityLeashKnot.class, "LeashKnot", 8);
/* 208:275 */     addMapping(EntityPainting.class, "Painting", 9);
/* 209:276 */     addMapping(EntityArrow.class, "Arrow", 10);
/* 210:277 */     addMapping(EntitySnowball.class, "Snowball", 11);
/* 211:278 */     addMapping(EntityLargeFireball.class, "Fireball", 12);
/* 212:279 */     addMapping(EntitySmallFireball.class, "SmallFireball", 13);
/* 213:280 */     addMapping(EntityEnderPearl.class, "ThrownEnderpearl", 14);
/* 214:281 */     addMapping(EntityEnderEye.class, "EyeOfEnderSignal", 15);
/* 215:282 */     addMapping(EntityPotion.class, "ThrownPotion", 16);
/* 216:283 */     addMapping(EntityExpBottle.class, "ThrownExpBottle", 17);
/* 217:284 */     addMapping(EntityItemFrame.class, "ItemFrame", 18);
/* 218:285 */     addMapping(EntityWitherSkull.class, "WitherSkull", 19);
/* 219:286 */     addMapping(EntityTNTPrimed.class, "PrimedTnt", 20);
/* 220:287 */     addMapping(EntityFallingBlock.class, "FallingSand", 21);
/* 221:288 */     addMapping(EntityFireworkRocket.class, "FireworksRocketEntity", 22);
/* 222:289 */     addMapping(EntityBoat.class, "Boat", 41);
/* 223:290 */     addMapping(EntityMinecartEmpty.class, "MinecartRideable", 42);
/* 224:291 */     addMapping(EntityMinecartChest.class, "MinecartChest", 43);
/* 225:292 */     addMapping(EntityMinecartFurnace.class, "MinecartFurnace", 44);
/* 226:293 */     addMapping(EntityMinecartTNT.class, "MinecartTNT", 45);
/* 227:294 */     addMapping(EntityMinecartHopper.class, "MinecartHopper", 46);
/* 228:295 */     addMapping(EntityMinecartMobSpawner.class, "MinecartSpawner", 47);
/* 229:296 */     addMapping(EntityMinecartCommandBlock.class, "MinecartCommandBlock", 40);
/* 230:297 */     addMapping(EntityLiving.class, "Mob", 48);
/* 231:298 */     addMapping(EntityMob.class, "Monster", 49);
/* 232:299 */     addMapping(EntityCreeper.class, "Creeper", 50, 894731, 0);
/* 233:300 */     addMapping(EntitySkeleton.class, "Skeleton", 51, 12698049, 4802889);
/* 234:301 */     addMapping(EntitySpider.class, "Spider", 52, 3419431, 11013646);
/* 235:302 */     addMapping(EntityGiantZombie.class, "Giant", 53);
/* 236:303 */     addMapping(EntityZombie.class, "Zombie", 54, 44975, 7969893);
/* 237:304 */     addMapping(EntitySlime.class, "Slime", 55, 5349438, 8306542);
/* 238:305 */     addMapping(EntityGhast.class, "Ghast", 56, 16382457, 12369084);
/* 239:306 */     addMapping(EntityPigZombie.class, "PigZombie", 57, 15373203, 5009705);
/* 240:307 */     addMapping(EntityEnderman.class, "Enderman", 58, 1447446, 0);
/* 241:308 */     addMapping(EntityCaveSpider.class, "CaveSpider", 59, 803406, 11013646);
/* 242:309 */     addMapping(EntitySilverfish.class, "Silverfish", 60, 7237230, 3158064);
/* 243:310 */     addMapping(EntityBlaze.class, "Blaze", 61, 16167425, 16775294);
/* 244:311 */     addMapping(EntityMagmaCube.class, "LavaSlime", 62, 3407872, 16579584);
/* 245:312 */     addMapping(EntityDragon.class, "EnderDragon", 63);
/* 246:313 */     addMapping(EntityWither.class, "WitherBoss", 64);
/* 247:314 */     addMapping(EntityBat.class, "Bat", 65, 4996656, 986895);
/* 248:315 */     addMapping(EntityWitch.class, "Witch", 66, 3407872, 5349438);
/* 249:316 */     addMapping(EntityPig.class, "Pig", 90, 15771042, 14377823);
/* 250:317 */     addMapping(EntitySheep.class, "Sheep", 91, 15198183, 16758197);
/* 251:318 */     addMapping(EntityCow.class, "Cow", 92, 4470310, 10592673);
/* 252:319 */     addMapping(EntityChicken.class, "Chicken", 93, 10592673, 16711680);
/* 253:320 */     addMapping(EntitySquid.class, "Squid", 94, 2243405, 7375001);
/* 254:321 */     addMapping(EntityWolf.class, "Wolf", 95, 14144467, 13545366);
/* 255:322 */     addMapping(EntityMooshroom.class, "MushroomCow", 96, 10489616, 12040119);
/* 256:323 */     addMapping(EntitySnowman.class, "SnowMan", 97);
/* 257:324 */     addMapping(EntityOcelot.class, "Ozelot", 98, 15720061, 5653556);
/* 258:325 */     addMapping(EntityIronGolem.class, "VillagerGolem", 99);
/* 259:326 */     addMapping(EntityHorse.class, "EntityHorse", 100, 12623485, 15656192);
/* 260:327 */     addMapping(EntityVillager.class, "Villager", 120, 5651507, 12422002);
/* 261:328 */     addMapping(EntityEnderCrystal.class, "EnderCrystal", 200);
/* 262:    */   }
/* 263:    */   
/* 264:    */   public static void func_151514_a() {}
/* 265:    */   
/* 266:    */   public static class EntityEggInfo
/* 267:    */   {
/* 268:    */     public final int spawnedID;
/* 269:    */     public final int primaryColor;
/* 270:    */     public final int secondaryColor;
/* 271:    */     public final StatBase field_151512_d;
/* 272:    */     public final StatBase field_151513_e;
/* 273:    */     private static final String __OBFID = "CL_00001539";
/* 274:    */     
/* 275:    */     public EntityEggInfo(int par1, int par2, int par3)
/* 276:    */     {
/* 277:342 */       this.spawnedID = par1;
/* 278:343 */       this.primaryColor = par2;
/* 279:344 */       this.secondaryColor = par3;
/* 280:345 */       this.field_151512_d = StatList.func_151182_a(this);
/* 281:346 */       this.field_151513_e = StatList.func_151176_b(this);
/* 282:    */     }
/* 283:    */   }
/* 284:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.EntityList
 * JD-Core Version:    0.7.0.1
 */