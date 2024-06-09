package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.Set;
import com.google.common.collect.Lists;
import java.util.List;
import com.google.common.collect.Maps;
import org.apache.logging.log4j.LogManager;
import java.util.Map;
import org.apache.logging.log4j.Logger;

public class EntityList
{
    private static final Logger Â;
    private static final Map Ý;
    private static final Map Ø­áŒŠá;
    private static final Map Âµá€;
    private static final Map Ó;
    private static final Map à;
    public static final Map HorizonCode_Horizon_È;
    private static final String Ø = "CL_00001538";
    
    static {
        Â = LogManager.getLogger();
        Ý = Maps.newHashMap();
        Ø­áŒŠá = Maps.newHashMap();
        Âµá€ = Maps.newHashMap();
        Ó = Maps.newHashMap();
        à = Maps.newHashMap();
        HorizonCode_Horizon_È = Maps.newLinkedHashMap();
        HorizonCode_Horizon_È(EntityItem.class, "Item", 1);
        HorizonCode_Horizon_È(EntityXPOrb.class, "XPOrb", 2);
        HorizonCode_Horizon_È(EntityLeashKnot.class, "LeashKnot", 8);
        HorizonCode_Horizon_È(EntityPainting.class, "Painting", 9);
        HorizonCode_Horizon_È(EntityArrow.class, "Arrow", 10);
        HorizonCode_Horizon_È(EntitySnowball.class, "Snowball", 11);
        HorizonCode_Horizon_È(EntityLargeFireball.class, "Fireball", 12);
        HorizonCode_Horizon_È(EntitySmallFireball.class, "SmallFireball", 13);
        HorizonCode_Horizon_È(EntityEnderPearl.class, "ThrownEnderpearl", 14);
        HorizonCode_Horizon_È(EntityEnderEye.class, "EyeOfEnderSignal", 15);
        HorizonCode_Horizon_È(EntityPotion.class, "ThrownPotion", 16);
        HorizonCode_Horizon_È(EntityExpBottle.class, "ThrownExpBottle", 17);
        HorizonCode_Horizon_È(EntityItemFrame.class, "ItemFrame", 18);
        HorizonCode_Horizon_È(EntityWitherSkull.class, "WitherSkull", 19);
        HorizonCode_Horizon_È(EntityTNTPrimed.class, "PrimedTnt", 20);
        HorizonCode_Horizon_È(EntityFallingBlock.class, "FallingSand", 21);
        HorizonCode_Horizon_È(EntityFireworkRocket.class, "FireworksRocketEntity", 22);
        HorizonCode_Horizon_È(EntityArmorStand.class, "ArmorStand", 30);
        HorizonCode_Horizon_È(EntityBoat.class, "Boat", 41);
        HorizonCode_Horizon_È(EntityMinecartEmpty.class, EntityMinecart.HorizonCode_Horizon_È.HorizonCode_Horizon_È.Â(), 42);
        HorizonCode_Horizon_È(EntityMinecartChest.class, EntityMinecart.HorizonCode_Horizon_È.Â.Â(), 43);
        HorizonCode_Horizon_È(EntityMinecartFurnace.class, EntityMinecart.HorizonCode_Horizon_È.Ý.Â(), 44);
        HorizonCode_Horizon_È(EntityMinecartTNT.class, EntityMinecart.HorizonCode_Horizon_È.Ø­áŒŠá.Â(), 45);
        HorizonCode_Horizon_È(EntityMinecartHopper.class, EntityMinecart.HorizonCode_Horizon_È.Ó.Â(), 46);
        HorizonCode_Horizon_È(EntityMinecartMobSpawner.class, EntityMinecart.HorizonCode_Horizon_È.Âµá€.Â(), 47);
        HorizonCode_Horizon_È(EntityMinecartCommandBlock.class, EntityMinecart.HorizonCode_Horizon_È.à.Â(), 40);
        HorizonCode_Horizon_È(EntityLiving.class, "Mob", 48);
        HorizonCode_Horizon_È(EntityMob.class, "Monster", 49);
        HorizonCode_Horizon_È(EntityCreeper.class, "Creeper", 50, 894731, 0);
        HorizonCode_Horizon_È(EntitySkeleton.class, "Skeleton", 51, 12698049, 4802889);
        HorizonCode_Horizon_È(EntitySpider.class, "Spider", 52, 3419431, 11013646);
        HorizonCode_Horizon_È(EntityGiantZombie.class, "Giant", 53);
        HorizonCode_Horizon_È(EntityZombie.class, "Zombie", 54, 44975, 7969893);
        HorizonCode_Horizon_È(EntitySlime.class, "Slime", 55, 5349438, 8306542);
        HorizonCode_Horizon_È(EntityGhast.class, "Ghast", 56, 16382457, 12369084);
        HorizonCode_Horizon_È(EntityPigZombie.class, "PigZombie", 57, 15373203, 5009705);
        HorizonCode_Horizon_È(EntityEnderman.class, "Enderman", 58, 1447446, 0);
        HorizonCode_Horizon_È(EntityCaveSpider.class, "CaveSpider", 59, 803406, 11013646);
        HorizonCode_Horizon_È(EntitySilverfish.class, "Silverfish", 60, 7237230, 3158064);
        HorizonCode_Horizon_È(EntityBlaze.class, "Blaze", 61, 16167425, 16775294);
        HorizonCode_Horizon_È(EntityMagmaCube.class, "LavaSlime", 62, 3407872, 16579584);
        HorizonCode_Horizon_È(EntityDragon.class, "EnderDragon", 63);
        HorizonCode_Horizon_È(EntityWither.class, "WitherBoss", 64);
        HorizonCode_Horizon_È(EntityBat.class, "Bat", 65, 4996656, 986895);
        HorizonCode_Horizon_È(EntityWitch.class, "Witch", 66, 3407872, 5349438);
        HorizonCode_Horizon_È(EntityEndermite.class, "Endermite", 67, 1447446, 7237230);
        HorizonCode_Horizon_È(EntityGuardian.class, "Guardian", 68, 5931634, 15826224);
        HorizonCode_Horizon_È(EntityPig.class, "Pig", 90, 15771042, 14377823);
        HorizonCode_Horizon_È(EntitySheep.class, "Sheep", 91, 15198183, 16758197);
        HorizonCode_Horizon_È(EntityCow.class, "Cow", 92, 4470310, 10592673);
        HorizonCode_Horizon_È(EntityChicken.class, "Chicken", 93, 10592673, 16711680);
        HorizonCode_Horizon_È(EntitySquid.class, "Squid", 94, 2243405, 7375001);
        HorizonCode_Horizon_È(EntityWolf.class, "Wolf", 95, 14144467, 13545366);
        HorizonCode_Horizon_È(EntityMooshroom.class, "MushroomCow", 96, 10489616, 12040119);
        HorizonCode_Horizon_È(EntitySnowman.class, "SnowMan", 97);
        HorizonCode_Horizon_È(EntityOcelot.class, "Ozelot", 98, 15720061, 5653556);
        HorizonCode_Horizon_È(EntityIronGolem.class, "VillagerGolem", 99);
        HorizonCode_Horizon_È(EntityHorse.class, "EntityHorse", 100, 12623485, 15656192);
        HorizonCode_Horizon_È(EntityRabbit.class, "Rabbit", 101, 10051392, 7555121);
        HorizonCode_Horizon_È(EntityVillager.class, "Villager", 120, 5651507, 12422002);
        HorizonCode_Horizon_È(EntityEnderCrystal.class, "EnderCrystal", 200);
    }
    
    private static void HorizonCode_Horizon_È(final Class p_75618_0_, final String p_75618_1_, final int p_75618_2_) {
        if (EntityList.Ý.containsKey(p_75618_1_)) {
            throw new IllegalArgumentException("ID is already registered: " + p_75618_1_);
        }
        if (EntityList.Âµá€.containsKey(p_75618_2_)) {
            throw new IllegalArgumentException("ID is already registered: " + p_75618_2_);
        }
        if (p_75618_2_ == 0) {
            throw new IllegalArgumentException("Cannot register to reserved id: " + p_75618_2_);
        }
        if (p_75618_0_ == null) {
            throw new IllegalArgumentException("Cannot register null clazz for id: " + p_75618_2_);
        }
        EntityList.Ý.put(p_75618_1_, p_75618_0_);
        EntityList.Ø­áŒŠá.put(p_75618_0_, p_75618_1_);
        EntityList.Âµá€.put(p_75618_2_, p_75618_0_);
        EntityList.Ó.put(p_75618_0_, p_75618_2_);
        EntityList.à.put(p_75618_1_, p_75618_2_);
    }
    
    private static void HorizonCode_Horizon_È(final Class p_75614_0_, final String p_75614_1_, final int p_75614_2_, final int p_75614_3_, final int p_75614_4_) {
        HorizonCode_Horizon_È(p_75614_0_, p_75614_1_, p_75614_2_);
        EntityList.HorizonCode_Horizon_È.put(p_75614_2_, new HorizonCode_Horizon_È(p_75614_2_, p_75614_3_, p_75614_4_));
    }
    
    public static Entity HorizonCode_Horizon_È(final String p_75620_0_, final World worldIn) {
        Entity var2 = null;
        try {
            final Class var3 = EntityList.Ý.get(p_75620_0_);
            if (var3 != null) {
                var2 = var3.getConstructor(World.class).newInstance(worldIn);
            }
        }
        catch (Exception var4) {
            var4.printStackTrace();
        }
        return var2;
    }
    
    public static Entity HorizonCode_Horizon_È(final NBTTagCompound p_75615_0_, final World worldIn) {
        Entity var2 = null;
        if ("Minecart".equals(p_75615_0_.áˆºÑ¢Õ("id"))) {
            p_75615_0_.HorizonCode_Horizon_È("id", EntityMinecart.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_75615_0_.Ó("Type")).Â());
            p_75615_0_.Å("Type");
        }
        try {
            final Class var3 = EntityList.Ý.get(p_75615_0_.áˆºÑ¢Õ("id"));
            if (var3 != null) {
                var2 = var3.getConstructor(World.class).newInstance(worldIn);
            }
        }
        catch (Exception var4) {
            var4.printStackTrace();
        }
        if (var2 != null) {
            var2.Ó(p_75615_0_);
        }
        else {
            EntityList.Â.warn("Skipping Entity with id " + p_75615_0_.áˆºÑ¢Õ("id"));
        }
        return var2;
    }
    
    public static Entity HorizonCode_Horizon_È(final int p_75616_0_, final World worldIn) {
        Entity var2 = null;
        try {
            final Class var3 = HorizonCode_Horizon_È(p_75616_0_);
            if (var3 != null) {
                var2 = var3.getConstructor(World.class).newInstance(worldIn);
            }
        }
        catch (Exception var4) {
            var4.printStackTrace();
        }
        if (var2 == null) {
            EntityList.Â.warn("Skipping Entity with id " + p_75616_0_);
        }
        return var2;
    }
    
    public static int HorizonCode_Horizon_È(final Entity p_75619_0_) {
        final Integer var1 = EntityList.Ó.get(p_75619_0_.getClass());
        return (var1 == null) ? 0 : var1;
    }
    
    public static Class HorizonCode_Horizon_È(final int p_90035_0_) {
        return EntityList.Âµá€.get(p_90035_0_);
    }
    
    public static String Â(final Entity p_75621_0_) {
        return EntityList.Ø­áŒŠá.get(p_75621_0_.getClass());
    }
    
    public static int HorizonCode_Horizon_È(final String p_180122_0_) {
        final Integer var1 = EntityList.à.get(p_180122_0_);
        return (var1 == null) ? 90 : var1;
    }
    
    public static String Â(final int p_75617_0_) {
        return EntityList.Ø­áŒŠá.get(HorizonCode_Horizon_È(p_75617_0_));
    }
    
    public static void HorizonCode_Horizon_È() {
    }
    
    public static List Â() {
        final Set var0 = EntityList.Ý.keySet();
        final ArrayList var2 = Lists.newArrayList();
        for (final String var4 : var0) {
            final Class var5 = EntityList.Ý.get(var4);
            if ((var5.getModifiers() & 0x400) != 0x400) {
                var2.add(var4);
            }
        }
        var2.add("LightningBolt");
        return var2;
    }
    
    public static boolean HorizonCode_Horizon_È(final Entity p_180123_0_, final String p_180123_1_) {
        String var2 = Â(p_180123_0_);
        if (var2 == null && p_180123_0_ instanceof EntityPlayer) {
            var2 = "Player";
        }
        else if (var2 == null && p_180123_0_ instanceof EntityLightningBolt) {
            var2 = "LightningBolt";
        }
        return p_180123_1_.equals(var2);
    }
    
    public static boolean Â(final String p_180125_0_) {
        return "Player".equals(p_180125_0_) || Â().contains(p_180125_0_);
    }
    
    public static class HorizonCode_Horizon_È
    {
        public final int HorizonCode_Horizon_È;
        public final int Â;
        public final int Ý;
        public final StatBase Ø­áŒŠá;
        public final StatBase Âµá€;
        private static final String Ó = "CL_00001539";
        
        public HorizonCode_Horizon_È(final int p_i1583_1_, final int p_i1583_2_, final int p_i1583_3_) {
            this.HorizonCode_Horizon_È = p_i1583_1_;
            this.Â = p_i1583_2_;
            this.Ý = p_i1583_3_;
            this.Ø­áŒŠá = StatList.HorizonCode_Horizon_È(this);
            this.Âµá€ = StatList.Â(this);
        }
    }
}
