/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.entity.item.EntityMinecartCommandBlock;
import net.minecraft.entity.item.EntityMinecartEmpty;
import net.minecraft.entity.item.EntityMinecartFurnace;
import net.minecraft.entity.item.EntityMinecartHopper;
import net.minecraft.entity.item.EntityMinecartMobSpawner;
import net.minecraft.entity.item.EntityMinecartTNT;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityElderGuardian;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.monster.EntityEvoker;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGiantZombie;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.monster.EntityHusk;
import net.minecraft.entity.monster.EntityIllusionIllager;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityStray;
import net.minecraft.entity.monster.EntityVex;
import net.minecraft.entity.monster.EntityVindicator;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityDonkey;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityMule;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityParrot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySkeletonHorse;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.passive.EntityZombieHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityDragonFireball;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntityEvokerFangs;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.entity.projectile.EntityLlamaSpit;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntityShulkerBullet;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.entity.projectile.EntitySpectralArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistryNamespaced;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityList {
    public static final ResourceLocation field_191307_a = new ResourceLocation("lightning_bolt");
    private static final ResourceLocation field_191310_e = new ResourceLocation("player");
    private static final Logger LOGGER = LogManager.getLogger();
    public static final RegistryNamespaced<ResourceLocation, Class<? extends Entity>> field_191308_b = new RegistryNamespaced();
    public static final Map<ResourceLocation, EntityEggInfo> ENTITY_EGGS = Maps.newLinkedHashMap();
    public static final Set<ResourceLocation> field_191309_d = Sets.newHashSet();
    private static final List<String> field_191311_g = Lists.newArrayList();

    @Nullable
    public static ResourceLocation func_191301_a(Entity p_191301_0_) {
        return EntityList.func_191306_a(p_191301_0_.getClass());
    }

    @Nullable
    public static ResourceLocation func_191306_a(Class<? extends Entity> p_191306_0_) {
        return field_191308_b.getNameForObject(p_191306_0_);
    }

    @Nullable
    public static String getEntityString(Entity entityIn) {
        int i = field_191308_b.getIDForObject(entityIn.getClass());
        return i == -1 ? null : field_191311_g.get(i);
    }

    @Nullable
    public static String func_191302_a(@Nullable ResourceLocation p_191302_0_) {
        int i = field_191308_b.getIDForObject(field_191308_b.getObject(p_191302_0_));
        return i == -1 ? null : field_191311_g.get(i);
    }

    @Nullable
    public static Class<? extends Entity> getClassFromID(int entityID) {
        return field_191308_b.getObjectById(entityID);
    }

    @Nullable
    public static Class<? extends Entity> func_192839_a(String p_192839_0_) {
        return field_191308_b.getObject(new ResourceLocation(p_192839_0_));
    }

    @Nullable
    public static Entity func_191304_a(@Nullable Class<? extends Entity> p_191304_0_, World p_191304_1_) {
        if (p_191304_0_ == null) {
            return null;
        }
        try {
            return p_191304_0_.getConstructor(World.class).newInstance(p_191304_1_);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @Nullable
    public static Entity createEntityByID(int entityID, World worldIn) {
        return EntityList.func_191304_a(EntityList.getClassFromID(entityID), worldIn);
    }

    @Nullable
    public static Entity createEntityByIDFromName(ResourceLocation name, World worldIn) {
        return EntityList.func_191304_a(field_191308_b.getObject(name), worldIn);
    }

    @Nullable
    public static Entity createEntityFromNBT(NBTTagCompound nbt, World worldIn) {
        ResourceLocation resourcelocation = new ResourceLocation(nbt.getString("id"));
        Entity entity = EntityList.createEntityByIDFromName(resourcelocation, worldIn);
        if (entity == null) {
            LOGGER.warn("Skipping Entity with id {}", (Object)resourcelocation);
        } else {
            entity.readFromNBT(nbt);
        }
        return entity;
    }

    public static Set<ResourceLocation> getEntityNameList() {
        return field_191309_d;
    }

    public static boolean isStringEntityName(Entity entityIn, ResourceLocation entityName) {
        ResourceLocation resourcelocation = EntityList.func_191306_a(entityIn.getClass());
        if (resourcelocation != null) {
            return resourcelocation.equals(entityName);
        }
        if (entityIn instanceof EntityPlayer) {
            return field_191310_e.equals(entityName);
        }
        return entityIn instanceof EntityLightningBolt ? field_191307_a.equals(entityName) : false;
    }

    public static boolean isStringValidEntityName(ResourceLocation entityName) {
        return field_191310_e.equals(entityName) || EntityList.getEntityNameList().contains(entityName);
    }

    public static String func_192840_b() {
        StringBuilder stringbuilder = new StringBuilder();
        for (ResourceLocation resourcelocation : EntityList.getEntityNameList()) {
            stringbuilder.append(resourcelocation).append(", ");
        }
        stringbuilder.append(field_191310_e);
        return stringbuilder.toString();
    }

    public static void init() {
        EntityList.func_191303_a(1, "item", EntityItem.class, "Item");
        EntityList.func_191303_a(2, "xp_orb", EntityXPOrb.class, "XPOrb");
        EntityList.func_191303_a(3, "area_effect_cloud", EntityAreaEffectCloud.class, "AreaEffectCloud");
        EntityList.func_191303_a(4, "elder_guardian", EntityElderGuardian.class, "ElderGuardian");
        EntityList.func_191303_a(5, "wither_skeleton", EntityWitherSkeleton.class, "WitherSkeleton");
        EntityList.func_191303_a(6, "stray", EntityStray.class, "Stray");
        EntityList.func_191303_a(7, "egg", EntityEgg.class, "ThrownEgg");
        EntityList.func_191303_a(8, "leash_knot", EntityLeashKnot.class, "LeashKnot");
        EntityList.func_191303_a(9, "painting", EntityPainting.class, "Painting");
        EntityList.func_191303_a(10, "arrow", EntityTippedArrow.class, "Arrow");
        EntityList.func_191303_a(11, "snowball", EntitySnowball.class, "Snowball");
        EntityList.func_191303_a(12, "fireball", EntityLargeFireball.class, "Fireball");
        EntityList.func_191303_a(13, "small_fireball", EntitySmallFireball.class, "SmallFireball");
        EntityList.func_191303_a(14, "ender_pearl", EntityEnderPearl.class, "ThrownEnderpearl");
        EntityList.func_191303_a(15, "eye_of_ender_signal", EntityEnderEye.class, "EyeOfEnderSignal");
        EntityList.func_191303_a(16, "potion", EntityPotion.class, "ThrownPotion");
        EntityList.func_191303_a(17, "xp_bottle", EntityExpBottle.class, "ThrownExpBottle");
        EntityList.func_191303_a(18, "item_frame", EntityItemFrame.class, "ItemFrame");
        EntityList.func_191303_a(19, "wither_skull", EntityWitherSkull.class, "WitherSkull");
        EntityList.func_191303_a(20, "tnt", EntityTNTPrimed.class, "PrimedTnt");
        EntityList.func_191303_a(21, "falling_block", EntityFallingBlock.class, "FallingSand");
        EntityList.func_191303_a(22, "fireworks_rocket", EntityFireworkRocket.class, "FireworksRocketEntity");
        EntityList.func_191303_a(23, "husk", EntityHusk.class, "Husk");
        EntityList.func_191303_a(24, "spectral_arrow", EntitySpectralArrow.class, "SpectralArrow");
        EntityList.func_191303_a(25, "shulker_bullet", EntityShulkerBullet.class, "ShulkerBullet");
        EntityList.func_191303_a(26, "dragon_fireball", EntityDragonFireball.class, "DragonFireball");
        EntityList.func_191303_a(27, "zombie_villager", EntityZombieVillager.class, "ZombieVillager");
        EntityList.func_191303_a(28, "skeleton_horse", EntitySkeletonHorse.class, "SkeletonHorse");
        EntityList.func_191303_a(29, "zombie_horse", EntityZombieHorse.class, "ZombieHorse");
        EntityList.func_191303_a(30, "armor_stand", EntityArmorStand.class, "ArmorStand");
        EntityList.func_191303_a(31, "donkey", EntityDonkey.class, "Donkey");
        EntityList.func_191303_a(32, "mule", EntityMule.class, "Mule");
        EntityList.func_191303_a(33, "evocation_fangs", EntityEvokerFangs.class, "EvocationFangs");
        EntityList.func_191303_a(34, "evocation_illager", EntityEvoker.class, "EvocationIllager");
        EntityList.func_191303_a(35, "vex", EntityVex.class, "Vex");
        EntityList.func_191303_a(36, "vindication_illager", EntityVindicator.class, "VindicationIllager");
        EntityList.func_191303_a(37, "illusion_illager", EntityIllusionIllager.class, "IllusionIllager");
        EntityList.func_191303_a(40, "commandblock_minecart", EntityMinecartCommandBlock.class, EntityMinecart.Type.COMMAND_BLOCK.getName());
        EntityList.func_191303_a(41, "boat", EntityBoat.class, "Boat");
        EntityList.func_191303_a(42, "minecart", EntityMinecartEmpty.class, EntityMinecart.Type.RIDEABLE.getName());
        EntityList.func_191303_a(43, "chest_minecart", EntityMinecartChest.class, EntityMinecart.Type.CHEST.getName());
        EntityList.func_191303_a(44, "furnace_minecart", EntityMinecartFurnace.class, EntityMinecart.Type.FURNACE.getName());
        EntityList.func_191303_a(45, "tnt_minecart", EntityMinecartTNT.class, EntityMinecart.Type.TNT.getName());
        EntityList.func_191303_a(46, "hopper_minecart", EntityMinecartHopper.class, EntityMinecart.Type.HOPPER.getName());
        EntityList.func_191303_a(47, "spawner_minecart", EntityMinecartMobSpawner.class, EntityMinecart.Type.SPAWNER.getName());
        EntityList.func_191303_a(50, "creeper", EntityCreeper.class, "Creeper");
        EntityList.func_191303_a(51, "skeleton", EntitySkeleton.class, "Skeleton");
        EntityList.func_191303_a(52, "spider", EntitySpider.class, "Spider");
        EntityList.func_191303_a(53, "giant", EntityGiantZombie.class, "Giant");
        EntityList.func_191303_a(54, "zombie", EntityZombie.class, "Zombie");
        EntityList.func_191303_a(55, "slime", EntitySlime.class, "Slime");
        EntityList.func_191303_a(56, "ghast", EntityGhast.class, "Ghast");
        EntityList.func_191303_a(57, "zombie_pigman", EntityPigZombie.class, "PigZombie");
        EntityList.func_191303_a(58, "enderman", EntityEnderman.class, "Enderman");
        EntityList.func_191303_a(59, "cave_spider", EntityCaveSpider.class, "CaveSpider");
        EntityList.func_191303_a(60, "silverfish", EntitySilverfish.class, "Silverfish");
        EntityList.func_191303_a(61, "blaze", EntityBlaze.class, "Blaze");
        EntityList.func_191303_a(62, "magma_cube", EntityMagmaCube.class, "LavaSlime");
        EntityList.func_191303_a(63, "ender_dragon", EntityDragon.class, "EnderDragon");
        EntityList.func_191303_a(64, "wither", EntityWither.class, "WitherBoss");
        EntityList.func_191303_a(65, "bat", EntityBat.class, "Bat");
        EntityList.func_191303_a(66, "witch", EntityWitch.class, "Witch");
        EntityList.func_191303_a(67, "endermite", EntityEndermite.class, "Endermite");
        EntityList.func_191303_a(68, "guardian", EntityGuardian.class, "Guardian");
        EntityList.func_191303_a(69, "shulker", EntityShulker.class, "Shulker");
        EntityList.func_191303_a(90, "pig", EntityPig.class, "Pig");
        EntityList.func_191303_a(91, "sheep", EntitySheep.class, "Sheep");
        EntityList.func_191303_a(92, "cow", EntityCow.class, "Cow");
        EntityList.func_191303_a(93, "chicken", EntityChicken.class, "Chicken");
        EntityList.func_191303_a(94, "squid", EntitySquid.class, "Squid");
        EntityList.func_191303_a(95, "wolf", EntityWolf.class, "Wolf");
        EntityList.func_191303_a(96, "mooshroom", EntityMooshroom.class, "MushroomCow");
        EntityList.func_191303_a(97, "snowman", EntitySnowman.class, "SnowMan");
        EntityList.func_191303_a(98, "ocelot", EntityOcelot.class, "Ozelot");
        EntityList.func_191303_a(99, "villager_golem", EntityIronGolem.class, "VillagerGolem");
        EntityList.func_191303_a(100, "horse", EntityHorse.class, "Horse");
        EntityList.func_191303_a(101, "rabbit", EntityRabbit.class, "Rabbit");
        EntityList.func_191303_a(102, "polar_bear", EntityPolarBear.class, "PolarBear");
        EntityList.func_191303_a(103, "llama", EntityLlama.class, "Llama");
        EntityList.func_191303_a(104, "llama_spit", EntityLlamaSpit.class, "LlamaSpit");
        EntityList.func_191303_a(105, "parrot", EntityParrot.class, "Parrot");
        EntityList.func_191303_a(120, "villager", EntityVillager.class, "Villager");
        EntityList.func_191303_a(200, "ender_crystal", EntityEnderCrystal.class, "EnderCrystal");
        EntityList.func_191305_a("bat", 4996656, 986895);
        EntityList.func_191305_a("blaze", 16167425, 16775294);
        EntityList.func_191305_a("cave_spider", 803406, 11013646);
        EntityList.func_191305_a("chicken", 0xA1A1A1, 0xFF0000);
        EntityList.func_191305_a("cow", 4470310, 0xA1A1A1);
        EntityList.func_191305_a("creeper", 894731, 0);
        EntityList.func_191305_a("donkey", 5457209, 8811878);
        EntityList.func_191305_a("elder_guardian", 13552826, 7632531);
        EntityList.func_191305_a("enderman", 0x161616, 0);
        EntityList.func_191305_a("endermite", 0x161616, 0x6E6E6E);
        EntityList.func_191305_a("evocation_illager", 0x959B9B, 1973274);
        EntityList.func_191305_a("ghast", 0xF9F9F9, 0xBCBCBC);
        EntityList.func_191305_a("guardian", 5931634, 15826224);
        EntityList.func_191305_a("horse", 12623485, 0xEEE500);
        EntityList.func_191305_a("husk", 7958625, 15125652);
        EntityList.func_191305_a("llama", 12623485, 10051392);
        EntityList.func_191305_a("magma_cube", 0x340000, 0xFCFC00);
        EntityList.func_191305_a("mooshroom", 10489616, 0xB7B7B7);
        EntityList.func_191305_a("mule", 1769984, 5321501);
        EntityList.func_191305_a("ocelot", 15720061, 5653556);
        EntityList.func_191305_a("parrot", 894731, 0xFF0000);
        EntityList.func_191305_a("pig", 15771042, 14377823);
        EntityList.func_191305_a("polar_bear", 0xF2F2F2, 0x959590);
        EntityList.func_191305_a("rabbit", 10051392, 7555121);
        EntityList.func_191305_a("sheep", 0xE7E7E7, 0xFFB5B5);
        EntityList.func_191305_a("shulker", 9725844, 5060690);
        EntityList.func_191305_a("silverfish", 0x6E6E6E, 0x303030);
        EntityList.func_191305_a("skeleton", 0xC1C1C1, 0x494949);
        EntityList.func_191305_a("skeleton_horse", 6842447, 15066584);
        EntityList.func_191305_a("slime", 5349438, 8306542);
        EntityList.func_191305_a("spider", 3419431, 11013646);
        EntityList.func_191305_a("squid", 2243405, 7375001);
        EntityList.func_191305_a("stray", 0x617677, 0xDDEAEA);
        EntityList.func_191305_a("vex", 8032420, 15265265);
        EntityList.func_191305_a("villager", 5651507, 12422002);
        EntityList.func_191305_a("vindication_illager", 0x959B9B, 2580065);
        EntityList.func_191305_a("witch", 0x340000, 5349438);
        EntityList.func_191305_a("wither_skeleton", 0x141414, 0x474D4D);
        EntityList.func_191305_a("wolf", 0xD7D3D3, 13545366);
        EntityList.func_191305_a("zombie", 44975, 7969893);
        EntityList.func_191305_a("zombie_horse", 3232308, 9945732);
        EntityList.func_191305_a("zombie_pigman", 15373203, 5009705);
        EntityList.func_191305_a("zombie_villager", 5651507, 7969893);
        field_191309_d.add(field_191307_a);
    }

    private static void func_191303_a(int p_191303_0_, String p_191303_1_, Class<? extends Entity> p_191303_2_, String p_191303_3_) {
        try {
            p_191303_2_.getConstructor(World.class);
        } catch (NoSuchMethodException var5) {
            throw new RuntimeException("Invalid class " + p_191303_2_ + " no constructor taking " + World.class.getName());
        }
        if ((p_191303_2_.getModifiers() & 0x400) == 1024) {
            throw new RuntimeException("Invalid abstract class " + p_191303_2_);
        }
        ResourceLocation resourcelocation = new ResourceLocation(p_191303_1_);
        field_191308_b.register(p_191303_0_, resourcelocation, p_191303_2_);
        field_191309_d.add(resourcelocation);
        while (field_191311_g.size() <= p_191303_0_) {
            field_191311_g.add(null);
        }
        field_191311_g.set(p_191303_0_, p_191303_3_);
    }

    protected static EntityEggInfo func_191305_a(String p_191305_0_, int p_191305_1_, int p_191305_2_) {
        ResourceLocation resourcelocation = new ResourceLocation(p_191305_0_);
        return ENTITY_EGGS.put(resourcelocation, new EntityEggInfo(resourcelocation, p_191305_1_, p_191305_2_));
    }

    public static class EntityEggInfo {
        public final ResourceLocation spawnedID;
        public final int primaryColor;
        public final int secondaryColor;
        public final StatBase killEntityStat;
        public final StatBase entityKilledByStat;

        public EntityEggInfo(ResourceLocation p_i47341_1_, int p_i47341_2_, int p_i47341_3_) {
            this.spawnedID = p_i47341_1_;
            this.primaryColor = p_i47341_2_;
            this.secondaryColor = p_i47341_3_;
            this.killEntityStat = StatList.getStatKillEntity(this);
            this.entityKilledByStat = StatList.getStatEntityKilledBy(this);
        }
    }
}

