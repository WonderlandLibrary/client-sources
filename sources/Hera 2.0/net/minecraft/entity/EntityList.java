/*     */ package net.minecraft.entity;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.entity.ai.EntityMinecartMobSpawner;
/*     */ import net.minecraft.entity.boss.EntityDragon;
/*     */ import net.minecraft.entity.boss.EntityWither;
/*     */ import net.minecraft.entity.item.EntityArmorStand;
/*     */ import net.minecraft.entity.item.EntityBoat;
/*     */ import net.minecraft.entity.item.EntityEnderCrystal;
/*     */ import net.minecraft.entity.item.EntityEnderEye;
/*     */ import net.minecraft.entity.item.EntityEnderPearl;
/*     */ import net.minecraft.entity.item.EntityExpBottle;
/*     */ import net.minecraft.entity.item.EntityFallingBlock;
/*     */ import net.minecraft.entity.item.EntityFireworkRocket;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.item.EntityItemFrame;
/*     */ import net.minecraft.entity.item.EntityMinecart;
/*     */ import net.minecraft.entity.item.EntityMinecartChest;
/*     */ import net.minecraft.entity.item.EntityMinecartEmpty;
/*     */ import net.minecraft.entity.item.EntityMinecartFurnace;
/*     */ import net.minecraft.entity.item.EntityMinecartHopper;
/*     */ import net.minecraft.entity.item.EntityMinecartTNT;
/*     */ import net.minecraft.entity.item.EntityPainting;
/*     */ import net.minecraft.entity.item.EntityTNTPrimed;
/*     */ import net.minecraft.entity.item.EntityXPOrb;
/*     */ import net.minecraft.entity.monster.EntityBlaze;
/*     */ import net.minecraft.entity.monster.EntityCaveSpider;
/*     */ import net.minecraft.entity.monster.EntityCreeper;
/*     */ import net.minecraft.entity.monster.EntityEnderman;
/*     */ import net.minecraft.entity.monster.EntityEndermite;
/*     */ import net.minecraft.entity.monster.EntityGhast;
/*     */ import net.minecraft.entity.monster.EntityGiantZombie;
/*     */ import net.minecraft.entity.monster.EntityGuardian;
/*     */ import net.minecraft.entity.monster.EntityIronGolem;
/*     */ import net.minecraft.entity.monster.EntityMagmaCube;
/*     */ import net.minecraft.entity.monster.EntityMob;
/*     */ import net.minecraft.entity.monster.EntityPigZombie;
/*     */ import net.minecraft.entity.monster.EntitySilverfish;
/*     */ import net.minecraft.entity.monster.EntitySkeleton;
/*     */ import net.minecraft.entity.monster.EntitySlime;
/*     */ import net.minecraft.entity.monster.EntitySnowman;
/*     */ import net.minecraft.entity.monster.EntitySpider;
/*     */ import net.minecraft.entity.monster.EntityWitch;
/*     */ import net.minecraft.entity.monster.EntityZombie;
/*     */ import net.minecraft.entity.passive.EntityBat;
/*     */ import net.minecraft.entity.passive.EntityChicken;
/*     */ import net.minecraft.entity.passive.EntityCow;
/*     */ import net.minecraft.entity.passive.EntityHorse;
/*     */ import net.minecraft.entity.passive.EntityMooshroom;
/*     */ import net.minecraft.entity.passive.EntityOcelot;
/*     */ import net.minecraft.entity.passive.EntityPig;
/*     */ import net.minecraft.entity.passive.EntityRabbit;
/*     */ import net.minecraft.entity.passive.EntitySheep;
/*     */ import net.minecraft.entity.passive.EntitySquid;
/*     */ import net.minecraft.entity.passive.EntityVillager;
/*     */ import net.minecraft.entity.passive.EntityWolf;
/*     */ import net.minecraft.entity.projectile.EntityArrow;
/*     */ import net.minecraft.entity.projectile.EntityEgg;
/*     */ import net.minecraft.entity.projectile.EntityLargeFireball;
/*     */ import net.minecraft.entity.projectile.EntityPotion;
/*     */ import net.minecraft.entity.projectile.EntitySmallFireball;
/*     */ import net.minecraft.entity.projectile.EntitySnowball;
/*     */ import net.minecraft.entity.projectile.EntityWitherSkull;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.stats.StatBase;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.world.World;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityList
/*     */ {
/*  79 */   private static final Logger logger = LogManager.getLogger();
/*  80 */   private static final Map<String, Class<? extends Entity>> stringToClassMapping = Maps.newHashMap();
/*  81 */   private static final Map<Class<? extends Entity>, String> classToStringMapping = Maps.newHashMap();
/*  82 */   private static final Map<Integer, Class<? extends Entity>> idToClassMapping = Maps.newHashMap();
/*  83 */   private static final Map<Class<? extends Entity>, Integer> classToIDMapping = Maps.newHashMap();
/*  84 */   private static final Map<String, Integer> stringToIDMapping = Maps.newHashMap();
/*  85 */   public static final Map<Integer, EntityEggInfo> entityEggs = Maps.newLinkedHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void addMapping(Class<? extends Entity> entityClass, String entityName, int id) {
/*  92 */     if (stringToClassMapping.containsKey(entityName))
/*     */     {
/*  94 */       throw new IllegalArgumentException("ID is already registered: " + entityName);
/*     */     }
/*  96 */     if (idToClassMapping.containsKey(Integer.valueOf(id)))
/*     */     {
/*  98 */       throw new IllegalArgumentException("ID is already registered: " + id);
/*     */     }
/* 100 */     if (id == 0)
/*     */     {
/* 102 */       throw new IllegalArgumentException("Cannot register to reserved id: " + id);
/*     */     }
/* 104 */     if (entityClass == null)
/*     */     {
/* 106 */       throw new IllegalArgumentException("Cannot register null clazz for id: " + id);
/*     */     }
/*     */ 
/*     */     
/* 110 */     stringToClassMapping.put(entityName, entityClass);
/* 111 */     classToStringMapping.put(entityClass, entityName);
/* 112 */     idToClassMapping.put(Integer.valueOf(id), entityClass);
/* 113 */     classToIDMapping.put(entityClass, Integer.valueOf(id));
/* 114 */     stringToIDMapping.put(entityName, Integer.valueOf(id));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void addMapping(Class<? extends Entity> entityClass, String entityName, int entityID, int baseColor, int spotColor) {
/* 123 */     addMapping(entityClass, entityName, entityID);
/* 124 */     entityEggs.put(Integer.valueOf(entityID), new EntityEggInfo(entityID, baseColor, spotColor));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Entity createEntityByName(String entityName, World worldIn) {
/* 132 */     Entity entity = null;
/*     */ 
/*     */     
/*     */     try {
/* 136 */       Class<? extends Entity> oclass = stringToClassMapping.get(entityName);
/*     */       
/* 138 */       if (oclass != null)
/*     */       {
/* 140 */         entity = oclass.getConstructor(new Class[] { World.class }).newInstance(new Object[] { worldIn });
/*     */       }
/*     */     }
/* 143 */     catch (Exception exception) {
/*     */       
/* 145 */       exception.printStackTrace();
/*     */     } 
/*     */     
/* 148 */     return entity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Entity createEntityFromNBT(NBTTagCompound nbt, World worldIn) {
/* 156 */     Entity entity = null;
/*     */     
/* 158 */     if ("Minecart".equals(nbt.getString("id"))) {
/*     */       
/* 160 */       nbt.setString("id", EntityMinecart.EnumMinecartType.byNetworkID(nbt.getInteger("Type")).getName());
/* 161 */       nbt.removeTag("Type");
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 166 */       Class<? extends Entity> oclass = stringToClassMapping.get(nbt.getString("id"));
/*     */       
/* 168 */       if (oclass != null)
/*     */       {
/* 170 */         entity = oclass.getConstructor(new Class[] { World.class }).newInstance(new Object[] { worldIn });
/*     */       }
/*     */     }
/* 173 */     catch (Exception exception) {
/*     */       
/* 175 */       exception.printStackTrace();
/*     */     } 
/*     */     
/* 178 */     if (entity != null) {
/*     */       
/* 180 */       entity.readFromNBT(nbt);
/*     */     }
/*     */     else {
/*     */       
/* 184 */       logger.warn("Skipping Entity with id " + nbt.getString("id"));
/*     */     } 
/*     */     
/* 187 */     return entity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Entity createEntityByID(int entityID, World worldIn) {
/* 195 */     Entity entity = null;
/*     */ 
/*     */     
/*     */     try {
/* 199 */       Class<? extends Entity> oclass = getClassFromID(entityID);
/*     */       
/* 201 */       if (oclass != null)
/*     */       {
/* 203 */         entity = oclass.getConstructor(new Class[] { World.class }).newInstance(new Object[] { worldIn });
/*     */       }
/*     */     }
/* 206 */     catch (Exception exception) {
/*     */       
/* 208 */       exception.printStackTrace();
/*     */     } 
/*     */     
/* 211 */     if (entity == null)
/*     */     {
/* 213 */       logger.warn("Skipping Entity with id " + entityID);
/*     */     }
/*     */     
/* 216 */     return entity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getEntityID(Entity entityIn) {
/* 224 */     Integer integer = classToIDMapping.get(entityIn.getClass());
/* 225 */     return (integer == null) ? 0 : integer.intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public static Class<? extends Entity> getClassFromID(int entityID) {
/* 230 */     return idToClassMapping.get(Integer.valueOf(entityID));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getEntityString(Entity entityIn) {
/* 238 */     return classToStringMapping.get(entityIn.getClass());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getIDFromString(String entityName) {
/* 246 */     Integer integer = stringToIDMapping.get(entityName);
/* 247 */     return (integer == null) ? 90 : integer.intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getStringFromID(int entityID) {
/* 255 */     return classToStringMapping.get(getClassFromID(entityID));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void func_151514_a() {}
/*     */ 
/*     */   
/*     */   public static List<String> getEntityNameList() {
/* 264 */     Set<String> set = stringToClassMapping.keySet();
/* 265 */     List<String> list = Lists.newArrayList();
/*     */     
/* 267 */     for (String s : set) {
/*     */       
/* 269 */       Class<? extends Entity> oclass = stringToClassMapping.get(s);
/*     */       
/* 271 */       if ((oclass.getModifiers() & 0x400) != 1024)
/*     */       {
/* 273 */         list.add(s);
/*     */       }
/*     */     } 
/*     */     
/* 277 */     list.add("LightningBolt");
/* 278 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isStringEntityName(Entity entityIn, String entityName) {
/* 283 */     String s = getEntityString(entityIn);
/*     */     
/* 285 */     if (s == null && entityIn instanceof net.minecraft.entity.player.EntityPlayer) {
/*     */       
/* 287 */       s = "Player";
/*     */     }
/* 289 */     else if (s == null && entityIn instanceof net.minecraft.entity.effect.EntityLightningBolt) {
/*     */       
/* 291 */       s = "LightningBolt";
/*     */     } 
/*     */     
/* 294 */     return entityName.equals(s);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isStringValidEntityName(String entityName) {
/* 299 */     return !(!"Player".equals(entityName) && !getEntityNameList().contains(entityName));
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/* 304 */     addMapping((Class)EntityItem.class, "Item", 1);
/* 305 */     addMapping((Class)EntityXPOrb.class, "XPOrb", 2);
/* 306 */     addMapping((Class)EntityEgg.class, "ThrownEgg", 7);
/* 307 */     addMapping((Class)EntityLeashKnot.class, "LeashKnot", 8);
/* 308 */     addMapping((Class)EntityPainting.class, "Painting", 9);
/* 309 */     addMapping((Class)EntityArrow.class, "Arrow", 10);
/* 310 */     addMapping((Class)EntitySnowball.class, "Snowball", 11);
/* 311 */     addMapping((Class)EntityLargeFireball.class, "Fireball", 12);
/* 312 */     addMapping((Class)EntitySmallFireball.class, "SmallFireball", 13);
/* 313 */     addMapping((Class)EntityEnderPearl.class, "ThrownEnderpearl", 14);
/* 314 */     addMapping((Class)EntityEnderEye.class, "EyeOfEnderSignal", 15);
/* 315 */     addMapping((Class)EntityPotion.class, "ThrownPotion", 16);
/* 316 */     addMapping((Class)EntityExpBottle.class, "ThrownExpBottle", 17);
/* 317 */     addMapping((Class)EntityItemFrame.class, "ItemFrame", 18);
/* 318 */     addMapping((Class)EntityWitherSkull.class, "WitherSkull", 19);
/* 319 */     addMapping((Class)EntityTNTPrimed.class, "PrimedTnt", 20);
/* 320 */     addMapping((Class)EntityFallingBlock.class, "FallingSand", 21);
/* 321 */     addMapping((Class)EntityFireworkRocket.class, "FireworksRocketEntity", 22);
/* 322 */     addMapping((Class)EntityArmorStand.class, "ArmorStand", 30);
/* 323 */     addMapping((Class)EntityBoat.class, "Boat", 41);
/* 324 */     addMapping((Class)EntityMinecartEmpty.class, EntityMinecart.EnumMinecartType.RIDEABLE.getName(), 42);
/* 325 */     addMapping((Class)EntityMinecartChest.class, EntityMinecart.EnumMinecartType.CHEST.getName(), 43);
/* 326 */     addMapping((Class)EntityMinecartFurnace.class, EntityMinecart.EnumMinecartType.FURNACE.getName(), 44);
/* 327 */     addMapping((Class)EntityMinecartTNT.class, EntityMinecart.EnumMinecartType.TNT.getName(), 45);
/* 328 */     addMapping((Class)EntityMinecartHopper.class, EntityMinecart.EnumMinecartType.HOPPER.getName(), 46);
/* 329 */     addMapping((Class)EntityMinecartMobSpawner.class, EntityMinecart.EnumMinecartType.SPAWNER.getName(), 47);
/* 330 */     addMapping((Class)EntityMinecartCommandBlock.class, EntityMinecart.EnumMinecartType.COMMAND_BLOCK.getName(), 40);
/* 331 */     addMapping((Class)EntityLiving.class, "Mob", 48);
/* 332 */     addMapping((Class)EntityMob.class, "Monster", 49);
/* 333 */     addMapping((Class)EntityCreeper.class, "Creeper", 50, 894731, 0);
/* 334 */     addMapping((Class)EntitySkeleton.class, "Skeleton", 51, 12698049, 4802889);
/* 335 */     addMapping((Class)EntitySpider.class, "Spider", 52, 3419431, 11013646);
/* 336 */     addMapping((Class)EntityGiantZombie.class, "Giant", 53);
/* 337 */     addMapping((Class)EntityZombie.class, "Zombie", 54, 44975, 7969893);
/* 338 */     addMapping((Class)EntitySlime.class, "Slime", 55, 5349438, 8306542);
/* 339 */     addMapping((Class)EntityGhast.class, "Ghast", 56, 16382457, 12369084);
/* 340 */     addMapping((Class)EntityPigZombie.class, "PigZombie", 57, 15373203, 5009705);
/* 341 */     addMapping((Class)EntityEnderman.class, "Enderman", 58, 1447446, 0);
/* 342 */     addMapping((Class)EntityCaveSpider.class, "CaveSpider", 59, 803406, 11013646);
/* 343 */     addMapping((Class)EntitySilverfish.class, "Silverfish", 60, 7237230, 3158064);
/* 344 */     addMapping((Class)EntityBlaze.class, "Blaze", 61, 16167425, 16775294);
/* 345 */     addMapping((Class)EntityMagmaCube.class, "LavaSlime", 62, 3407872, 16579584);
/* 346 */     addMapping((Class)EntityDragon.class, "EnderDragon", 63);
/* 347 */     addMapping((Class)EntityWither.class, "WitherBoss", 64);
/* 348 */     addMapping((Class)EntityBat.class, "Bat", 65, 4996656, 986895);
/* 349 */     addMapping((Class)EntityWitch.class, "Witch", 66, 3407872, 5349438);
/* 350 */     addMapping((Class)EntityEndermite.class, "Endermite", 67, 1447446, 7237230);
/* 351 */     addMapping((Class)EntityGuardian.class, "Guardian", 68, 5931634, 15826224);
/* 352 */     addMapping((Class)EntityPig.class, "Pig", 90, 15771042, 14377823);
/* 353 */     addMapping((Class)EntitySheep.class, "Sheep", 91, 15198183, 16758197);
/* 354 */     addMapping((Class)EntityCow.class, "Cow", 92, 4470310, 10592673);
/* 355 */     addMapping((Class)EntityChicken.class, "Chicken", 93, 10592673, 16711680);
/* 356 */     addMapping((Class)EntitySquid.class, "Squid", 94, 2243405, 7375001);
/* 357 */     addMapping((Class)EntityWolf.class, "Wolf", 95, 14144467, 13545366);
/* 358 */     addMapping((Class)EntityMooshroom.class, "MushroomCow", 96, 10489616, 12040119);
/* 359 */     addMapping((Class)EntitySnowman.class, "SnowMan", 97);
/* 360 */     addMapping((Class)EntityOcelot.class, "Ozelot", 98, 15720061, 5653556);
/* 361 */     addMapping((Class)EntityIronGolem.class, "VillagerGolem", 99);
/* 362 */     addMapping((Class)EntityHorse.class, "EntityHorse", 100, 12623485, 15656192);
/* 363 */     addMapping((Class)EntityRabbit.class, "Rabbit", 101, 10051392, 7555121);
/* 364 */     addMapping((Class)EntityVillager.class, "Villager", 120, 5651507, 12422002);
/* 365 */     addMapping((Class)EntityEnderCrystal.class, "EnderCrystal", 200);
/*     */   }
/*     */ 
/*     */   
/*     */   public static class EntityEggInfo
/*     */   {
/*     */     public final int spawnedID;
/*     */     public final int primaryColor;
/*     */     public final int secondaryColor;
/*     */     public final StatBase field_151512_d;
/*     */     public final StatBase field_151513_e;
/*     */     
/*     */     public EntityEggInfo(int id, int baseColor, int spotColor) {
/* 378 */       this.spawnedID = id;
/* 379 */       this.primaryColor = baseColor;
/* 380 */       this.secondaryColor = spotColor;
/* 381 */       this.field_151512_d = StatList.getStatKillEntity(this);
/* 382 */       this.field_151513_e = StatList.getStatEntityKilledBy(this);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\entity\EntityList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */