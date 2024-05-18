package net.minecraft.entity;

import net.minecraft.world.*;
import java.lang.reflect.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.effect.*;
import org.apache.logging.log4j.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.boss.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.item.*;
import com.google.common.collect.*;
import java.util.*;
import net.minecraft.stats.*;

public class EntityList
{
    private static final Map<Class<? extends Entity>, String> classToStringMapping;
    private static final String[] I;
    private static final Logger logger;
    private static final Map<Class<? extends Entity>, Integer> classToIDMapping;
    public static final Map<Integer, EntityEggInfo> entityEggs;
    private static final Map<String, Integer> stringToIDMapping;
    private static final Map<String, Class<? extends Entity>> stringToClassMapping;
    private static final Map<Integer, Class<? extends Entity>> idToClassMapping;
    
    private static void addMapping(final Class<? extends Entity> clazz, final String s, final int n) {
        if (EntityList.stringToClassMapping.containsKey(s)) {
            throw new IllegalArgumentException(EntityList.I[0x93 ^ 0xA4] + s);
        }
        if (EntityList.idToClassMapping.containsKey(n)) {
            throw new IllegalArgumentException(EntityList.I[0xF ^ 0x37] + n);
        }
        if (n == 0) {
            throw new IllegalArgumentException(EntityList.I[0x6F ^ 0x56] + n);
        }
        if (clazz == null) {
            throw new IllegalArgumentException(EntityList.I[0x9 ^ 0x33] + n);
        }
        EntityList.stringToClassMapping.put(s, clazz);
        EntityList.classToStringMapping.put(clazz, s);
        EntityList.idToClassMapping.put(n, clazz);
        EntityList.classToIDMapping.put(clazz, n);
        EntityList.stringToIDMapping.put(s, n);
    }
    
    public static Entity createEntityByID(final int n, final World world) {
        Entity entity = null;
        try {
            final Class<? extends Entity> classFromID = getClassFromID(n);
            if (classFromID != null) {
                final Class<? extends Entity> clazz = classFromID;
                final Class[] array = new Class[" ".length()];
                array["".length()] = World.class;
                final Constructor<? extends Entity> constructor = clazz.getConstructor((Class<?>[])array);
                final Object[] array2 = new Object[" ".length()];
                array2["".length()] = world;
                entity = (Entity)constructor.newInstance(array2);
                "".length();
                if (1 >= 4) {
                    throw null;
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        if (entity == null) {
            EntityList.logger.warn(EntityList.I[0x1C ^ 0x5F] + n);
        }
        return entity;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static Entity createEntityFromNBT(final NBTTagCompound nbtTagCompound, final World world) {
        Entity entity = null;
        if (EntityList.I[0x52 ^ 0x69].equals(nbtTagCompound.getString(EntityList.I[0x92 ^ 0xAE]))) {
            nbtTagCompound.setString(EntityList.I[0x3A ^ 0x7], EntityMinecart.EnumMinecartType.byNetworkID(nbtTagCompound.getInteger(EntityList.I[0xB2 ^ 0x8C])).getName());
            nbtTagCompound.removeTag(EntityList.I[0x91 ^ 0xAE]);
        }
        try {
            final Class<? extends Entity> clazz = EntityList.stringToClassMapping.get(nbtTagCompound.getString(EntityList.I[0x31 ^ 0x71]));
            if (clazz != null) {
                final Class<? extends Entity> clazz2 = clazz;
                final Class[] array = new Class[" ".length()];
                array["".length()] = World.class;
                final Constructor<? extends Entity> constructor = clazz2.getConstructor((Class<?>[])array);
                final Object[] array2 = new Object[" ".length()];
                array2["".length()] = world;
                entity = (Entity)constructor.newInstance(array2);
                "".length();
                if (0 >= 2) {
                    throw null;
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        if (entity != null) {
            entity.readFromNBT(nbtTagCompound);
            "".length();
            if (4 < 4) {
                throw null;
            }
        }
        else {
            EntityList.logger.warn(EntityList.I[0x45 ^ 0x4] + nbtTagCompound.getString(EntityList.I[0x25 ^ 0x67]));
        }
        return entity;
    }
    
    public static String getEntityString(final Entity entity) {
        return EntityList.classToStringMapping.get(entity.getClass());
    }
    
    public static void func_151514_a() {
    }
    
    public static boolean isStringEntityName(final Entity entity, final String s) {
        String entityString = getEntityString(entity);
        if (entityString == null && entity instanceof EntityPlayer) {
            entityString = EntityList.I[0xE5 ^ 0xA0];
            "".length();
            if (0 < 0) {
                throw null;
            }
        }
        else if (entityString == null && entity instanceof EntityLightningBolt) {
            entityString = EntityList.I[0x5A ^ 0x1C];
        }
        return s.equals(entityString);
    }
    
    private static void addMapping(final Class<? extends Entity> clazz, final String s, final int n, final int n2, final int n3) {
        addMapping(clazz, s, n);
        EntityList.entityEggs.put(n, new EntityEggInfo(n, n2, n3));
    }
    
    static {
        I();
        logger = LogManager.getLogger();
        stringToClassMapping = Maps.newHashMap();
        classToStringMapping = Maps.newHashMap();
        idToClassMapping = Maps.newHashMap();
        classToIDMapping = Maps.newHashMap();
        stringToIDMapping = Maps.newHashMap();
        entityEggs = Maps.newLinkedHashMap();
        addMapping(EntityItem.class, EntityList.I["".length()], " ".length());
        addMapping(EntityXPOrb.class, EntityList.I[" ".length()], "  ".length());
        addMapping(EntityEgg.class, EntityList.I["  ".length()], 0xC ^ 0xB);
        addMapping(EntityLeashKnot.class, EntityList.I["   ".length()], 0x44 ^ 0x4C);
        addMapping(EntityPainting.class, EntityList.I[0xD ^ 0x9], 0x7 ^ 0xE);
        addMapping(EntityArrow.class, EntityList.I[0x13 ^ 0x16], 0x9A ^ 0x90);
        addMapping(EntitySnowball.class, EntityList.I[0x5E ^ 0x58], 0x3F ^ 0x34);
        addMapping(EntityLargeFireball.class, EntityList.I[0x5B ^ 0x5C], 0x7E ^ 0x72);
        addMapping(EntitySmallFireball.class, EntityList.I[0x87 ^ 0x8F], 0x57 ^ 0x5A);
        addMapping(EntityEnderPearl.class, EntityList.I[0xB ^ 0x2], 0xAE ^ 0xA0);
        addMapping(EntityEnderEye.class, EntityList.I[0x2E ^ 0x24], 0x10 ^ 0x1F);
        addMapping(EntityPotion.class, EntityList.I[0x56 ^ 0x5D], 0x69 ^ 0x79);
        addMapping(EntityExpBottle.class, EntityList.I[0xD ^ 0x1], 0x14 ^ 0x5);
        addMapping(EntityItemFrame.class, EntityList.I[0x78 ^ 0x75], 0x68 ^ 0x7A);
        addMapping(EntityWitherSkull.class, EntityList.I[0x9C ^ 0x92], 0x75 ^ 0x66);
        addMapping(EntityTNTPrimed.class, EntityList.I[0xB7 ^ 0xB8], 0x91 ^ 0x85);
        addMapping(EntityFallingBlock.class, EntityList.I[0x92 ^ 0x82], 0x3E ^ 0x2B);
        addMapping(EntityFireworkRocket.class, EntityList.I[0xA2 ^ 0xB3], 0x71 ^ 0x67);
        addMapping(EntityArmorStand.class, EntityList.I[0xB1 ^ 0xA3], 0xA5 ^ 0xBB);
        addMapping(EntityBoat.class, EntityList.I[0x48 ^ 0x5B], 0x6E ^ 0x47);
        addMapping(EntityMinecartEmpty.class, EntityMinecart.EnumMinecartType.RIDEABLE.getName(), 0xEF ^ 0xC5);
        addMapping(EntityMinecartChest.class, EntityMinecart.EnumMinecartType.CHEST.getName(), 0x5D ^ 0x76);
        addMapping(EntityMinecartFurnace.class, EntityMinecart.EnumMinecartType.FURNACE.getName(), 0x4D ^ 0x61);
        addMapping(EntityMinecartTNT.class, EntityMinecart.EnumMinecartType.TNT.getName(), 0x63 ^ 0x4E);
        addMapping(EntityMinecartHopper.class, EntityMinecart.EnumMinecartType.HOPPER.getName(), 0x30 ^ 0x1E);
        addMapping(EntityMinecartMobSpawner.class, EntityMinecart.EnumMinecartType.SPAWNER.getName(), 0x8 ^ 0x27);
        addMapping(EntityMinecartCommandBlock.class, EntityMinecart.EnumMinecartType.COMMAND_BLOCK.getName(), 0x33 ^ 0x1B);
        addMapping(EntityLiving.class, EntityList.I[0xA3 ^ 0xB7], 0x87 ^ 0xB7);
        addMapping(EntityMob.class, EntityList.I[0x32 ^ 0x27], 0x59 ^ 0x68);
        addMapping(EntityCreeper.class, EntityList.I[0x5B ^ 0x4D], 0x85 ^ 0xB7, 321009 + 732405 - 591007 + 432324, "".length());
        addMapping(EntitySkeleton.class, EntityList.I[0x27 ^ 0x30], 0x87 ^ 0xB4, 4526502 + 8228899 - 6332181 + 6274829, 1051481 + 1889357 - 655456 + 2517507);
        addMapping(EntitySpider.class, EntityList.I[0x32 ^ 0x2A], 0x75 ^ 0x41, 3292374 + 3146281 - 4035433 + 1016209, 8836611 + 3477945 - 2601067 + 1300157);
        addMapping(EntityGiantZombie.class, EntityList.I[0x79 ^ 0x60], 0xA7 ^ 0x92);
        addMapping(EntityZombie.class, EntityList.I[0x45 ^ 0x5F], 0x5C ^ 0x6A, 1217 + 21088 - 10 + 22680, 317401 + 2320298 + 1133388 + 4198806);
        addMapping(EntitySlime.class, EntityList.I[0x7A ^ 0x61], 0x83 ^ 0xB4, 2709527 + 4006331 - 6176843 + 4810423, 1728717 + 4330825 - 1924777 + 4171777);
        addMapping(EntityGhast.class, EntityList.I[0xAE ^ 0xB2], 0xA6 ^ 0x9E, 5998551 + 4810335 - 10326640 + 15900211, 10264389 + 1853413 - 10492540 + 10743822);
        addMapping(EntityPigZombie.class, EntityList.I[0x1B ^ 0x6], 0x3A ^ 0x3, 14888936 + 2117348 - 9712769 + 8079688, 2124565 + 926672 + 1837865 + 120603);
        addMapping(EntityEnderman.class, EntityList.I[0x2A ^ 0x34], 0x42 ^ 0x78, 782691 + 802267 - 1383476 + 1245964, "".length());
        addMapping(EntityCaveSpider.class, EntityList.I[0xA1 ^ 0xBE], 0xBD ^ 0x86, 796712 + 647617 - 730289 + 89366, 8066991 + 7480562 - 11126560 + 6592653);
        addMapping(EntitySilverfish.class, EntityList.I[0x53 ^ 0x73], 0x44 ^ 0x78, 2248105 + 5972456 - 4577225 + 3593894, 1514518 + 372112 - 864930 + 2136364);
        addMapping(EntityBlaze.class, EntityList.I[0xA1 ^ 0x80], 0xA ^ 0x37, 15001221 + 4468930 - 12999700 + 9696974, 6161225 + 16510977 - 6244752 + 347844);
        addMapping(EntityMagmaCube.class, EntityList.I[0x81 ^ 0xA3], 0x89 ^ 0xB7, 1972443 + 2548180 - 2551890 + 1439139, 11494219 + 14491721 - 23390147 + 13983791);
        addMapping(EntityDragon.class, EntityList.I[0x3B ^ 0x18], 0x22 ^ 0x1D);
        addMapping(EntityWither.class, EntityList.I[0xA4 ^ 0x80], 0x68 ^ 0x28);
        addMapping(EntityBat.class, EntityList.I[0x76 ^ 0x53], 0xD1 ^ 0x90, 3849099 + 1369867 - 1932407 + 1710097, 240844 + 164985 - 270505 + 851571);
        addMapping(EntityWitch.class, EntityList.I[0xC ^ 0x2A], 0x52 ^ 0x10, 602353 + 1105556 - 124122 + 1824085, 5114902 + 1186427 - 3344176 + 2392285);
        addMapping(EntityEndermite.class, EntityList.I[0x9D ^ 0xBA], 0x47 ^ 0x4, 732938 + 124470 + 105795 + 484243, 3833821 + 942130 - 4295393 + 6756672);
        addMapping(EntityGuardian.class, EntityList.I[0x90 ^ 0xB8], 0x17 ^ 0x53, 837081 + 5316732 - 5228072 + 5005893, 7358065 + 6050078 + 2054162 + 363919);
        addMapping(EntityPig.class, EntityList.I[0x13 ^ 0x3A], 0xDB ^ 0x81, 4091264 + 9064979 - 3594909 + 6209708, 10046421 + 11148861 - 16656354 + 9838895);
        addMapping(EntitySheep.class, EntityList.I[0x9 ^ 0x23], 0x13 ^ 0x48, 5104103 + 7619050 - 556371 + 3031401, 4021045 + 2690476 + 8306321 + 1740355);
        addMapping(EntityCow.class, EntityList.I[0x5F ^ 0x74], 0xE5 ^ 0xB9, 3184779 + 3158496 - 6306695 + 4433730, 5501004 + 1494124 + 1620286 + 1977259);
        addMapping(EntityChicken.class, EntityList.I[0x6E ^ 0x42], 0x46 ^ 0x1B, 10170103 + 10316267 - 12411715 + 2518018, 5244359 + 11853653 - 16234381 + 15848049);
        addMapping(EntitySquid.class, EntityList.I[0x30 ^ 0x1D], 0x5 ^ 0x5B, 1639350 + 128033 - 105215 + 581237, 390593 + 6691604 - 1523099 + 1815903);
        addMapping(EntityWolf.class, EntityList.I[0xAA ^ 0x84], 0xD ^ 0x52, 12050808 + 10707408 - 16207401 + 7593652, 2958684 + 3570417 - 4389014 + 11405279);
        addMapping(EntityMooshroom.class, EntityList.I[0xAD ^ 0x82], 0x2C ^ 0x4C, 3843034 + 2489000 - 6326096 + 10483678, 621257 + 10213471 - 8398593 + 9603984);
        addMapping(EntitySnowman.class, EntityList.I[0x78 ^ 0x48], 0x69 ^ 0x8);
        addMapping(EntityOcelot.class, EntityList.I[0x9D ^ 0xAC], 0xCF ^ 0xAD, 15058955 + 683018 - 15039588 + 15017676, 2872993 + 4711811 - 2525794 + 594546);
        addMapping(EntityIronGolem.class, EntityList.I[0x4 ^ 0x36], 0x52 ^ 0x31);
        addMapping(EntityHorse.class, EntityList.I[0x4B ^ 0x78], 0x29 ^ 0x4D, 11779813 + 2255239 - 4885573 + 3474006, 6962577 + 56861 + 1923617 + 6713137);
        addMapping(EntityRabbit.class, EntityList.I[0x3A ^ 0xE], 0xF2 ^ 0x97, 1458378 + 1664642 + 2748626 + 4179746, 7171377 + 2310260 - 6159352 + 4232836);
        addMapping(EntityVillager.class, EntityList.I[0x8 ^ 0x3D], 0xD ^ 0x75, 4842606 + 16882 - 778976 + 1570995, 11101092 + 6090387 - 7055389 + 2285912);
        addMapping(EntityEnderCrystal.class, EntityList.I[0xAA ^ 0x9C], 52 + 35 - 32 + 145);
    }
    
    public static Entity createEntityByName(final String s, final World world) {
        Entity entity = null;
        try {
            final Class<? extends Entity> clazz = EntityList.stringToClassMapping.get(s);
            if (clazz != null) {
                final Class<? extends Entity> clazz2 = clazz;
                final Class[] array = new Class[" ".length()];
                array["".length()] = World.class;
                final Constructor<? extends Entity> constructor = clazz2.getConstructor((Class<?>[])array);
                final Object[] array2 = new Object[" ".length()];
                array2["".length()] = world;
                entity = (Entity)constructor.newInstance(array2);
                "".length();
                if (0 <= -1) {
                    throw null;
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return entity;
    }
    
    public static boolean isStringValidEntityName(final String s) {
        if (!EntityList.I[0xE3 ^ 0xA4].equals(s) && !getEntityNameList().contains(s)) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    public static Class<? extends Entity> getClassFromID(final int n) {
        return EntityList.idToClassMapping.get(n);
    }
    
    public static int getIDFromString(final String s) {
        final Integer n = EntityList.stringToIDMapping.get(s);
        int intValue;
        if (n == null) {
            intValue = (0x59 ^ 0x3);
            "".length();
            if (4 < 3) {
                throw null;
            }
        }
        else {
            intValue = n;
        }
        return intValue;
    }
    
    public static List<String> getEntityNameList() {
        final Set<String> keySet = EntityList.stringToClassMapping.keySet();
        final ArrayList arrayList = Lists.newArrayList();
        final Iterator<String> iterator = keySet.iterator();
        "".length();
        if (2 >= 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            final String s = iterator.next();
            if ((EntityList.stringToClassMapping.get(s).getModifiers() & 353 + 576 - 684 + 779) != 868 + 473 - 815 + 498) {
                arrayList.add(s);
            }
        }
        arrayList.add(EntityList.I[0xE5 ^ 0xA1]);
        return (List<String>)arrayList;
    }
    
    private static void I() {
        (I = new String[0xF0 ^ 0xB8])["".length()] = I("-\u0006\u0007\f", "drbaQ");
        EntityList.I[" ".length()] = I("\u0014\u001d\b?#", "LMGMA");
        EntityList.I["  ".length()] = I("%\u0007=(:\u001f*( ", "qoOGM");
        EntityList.I["   ".length()] = I("\"\u0011\u0000$:%\u001a\u000e#", "ntaWR");
        EntityList.I[0x79 ^ 0x7D] = I("\u0011\u000b#+\u0005(\u0004-", "AjJEq");
        EntityList.I[0x57 ^ 0x52] = I("/+8\u0003!", "nYJlV");
        EntityList.I[0x68 ^ 0x6E] = I("\u0002\u001d\u000b1\u00040\u001f\b", "QsdFf");
        EntityList.I[0x83 ^ 0x84] = I("1$>*\r\u0016! ", "wMLOo");
        EntityList.I[0x1D ^ 0x15] = I("5#\t\b\u0007 '\u001a\u0001\t\u0007\"\u0004", "fNhdk");
        EntityList.I[0xAB ^ 0xA2] = I("-:6\f>\u0017\u0017*\u0007,\u000b\"!\u0002;\u0015", "yRDcI");
        EntityList.I[0x59 ^ 0x53] = I("01\u00067\"0&\u0007\u001d6&!\u0004\u0016%\u0019", "uHcxD");
        EntityList.I[0x0 ^ 0xB] = I("\f%%\u001e\u00126\u001d8\u0005\f7#", "XMWqe");
        EntityList.I[0x3F ^ 0x33] = I("\r\u00100<;7=:#\u000e6\f6?)", "YxBSL");
        EntityList.I[0x46 ^ 0x4B] = I("\u000f\u00124&54\u0007<.", "FfQKs");
        EntityList.I[0x31 ^ 0x3F] = I("\u0005!6*\u0012 \u001b)7\u001b>", "RHBBw");
        EntityList.I[0x96 ^ 0x99] = I("\u0013089\u0006'\u0016? ", "CBQTc");
        EntityList.I[0xB8 ^ 0xA8] = I("?\u000b\u0019#\u001d\u0017\r&.\u001a\u001d", "yjuOt");
        EntityList.I[0x94 ^ 0x85] = I("/1\u0015\u000f'\u0006*\f\u0019\u0002\u0006;\f\u000f$,6\u0013\u0003$\u0010", "iXgjP");
        EntityList.I[0x66 ^ 0x74] = I("8?,= *9 <6", "yMARR");
        EntityList.I[0x13 ^ 0x0] = I("\u000f\u0006$\u0010", "MiEdv");
        EntityList.I[0x1D ^ 0x9] = I("/8\u0014", "bWvLy");
        EntityList.I[0x2D ^ 0x38] = I("\f?\u0003 \u0016$\"", "APmSb");
        EntityList.I[0xB5 ^ 0xA3] = I(":\u0011\u0006.!\u001c\u0011", "yccKQ");
        EntityList.I[0x57 ^ 0x40] = I("\u0015<\u0000!428\u000b", "FWeMQ");
        EntityList.I[0x3D ^ 0x25] = I("\u001863\u000e\u001c9", "KFZjy");
        EntityList.I[0x88 ^ 0x91] = I("\u0002%\u0010:5", "ELqTA");
        EntityList.I[0x61 ^ 0x7B] = I("4\u0005'\r0\u000b", "njJoY");
        EntityList.I[0xA ^ 0x11] = I("?.\u0000\f.", "lBiaK");
        EntityList.I[0xA4 ^ 0xB8] = I("*0\t\u0003\u0010", "mXhpd");
        EntityList.I[0x32 ^ 0x2F] = I("\u0002!\u001f-\u001c?*\u0011\u0012", "RHxws");
        EntityList.I[0x7 ^ 0x19] = I("\u001d\u0014\u0005705\u001b\u000f", "XzaRB");
        EntityList.I[0xB2 ^ 0xAD] = I("\n#\u0000\u000e49+\u0012\u000e\u0015", "IBvkg");
        EntityList.I[0x9F ^ 0xBF] = I(":=%\u0000\u0000\u001b2 \u0005\r", "iTIve");
        EntityList.I[0x35 ^ 0x14] = I("\u0016&5\u0018\u000b", "TJTbn");
        EntityList.I[0x4F ^ 0x6D] = I("\u001e+\u001a\b$>#\u0001\f", "RJliw");
        EntityList.I[0x40 ^ 0x63] = I("1>5\u0004(0\"0\u00065\u001a", "tPQaZ");
        EntityList.I[0xB5 ^ 0x91] = I("\u0014?$.)1\u0014?5?", "CVPFL");
        EntityList.I[0x6A ^ 0x4F] = I("&07", "dQCim");
        EntityList.I[0x82 ^ 0xA4] = I("9\u0010\u001e\u0014\u000e", "nyjwf");
        EntityList.I[0xA9 ^ 0x8E] = I("\u0004\u00170*9,\u0010 *", "AyTOK");
        EntityList.I[0x1D ^ 0x35] = I("\u0002<,\u0018!,(#", "EIMjE");
        EntityList.I[0x3F ^ 0x16] = I("=\n\u000e", "mciEr");
        EntityList.I[0xAD ^ 0x87] = I("%\u001c\u000e<2", "vtkYB");
        EntityList.I[0x1F ^ 0x34] = I("\u0014\u00178", "WxOsu");
        EntityList.I[0x9A ^ 0xB6] = I("%\u001f\u00117 \u0003\u0019", "fwxTK");
        EntityList.I[0x5 ^ 0x28] = I("2\u001c\u001994", "amlPP");
        EntityList.I[0x8E ^ 0xA0] = I("699\u001c", "aVUzT");
        EntityList.I[0x9D ^ 0xB2] = I(")\u0014\u0019\f\u0016\u000b\u000e\u0007'\u000b\u0013", "dajdd");
        EntityList.I[0x20 ^ 0x10] = I("\t\u0014\u0003\u00164;\u0014", "Zzlay");
        EntityList.I[0x38 ^ 0x9] = I("9(\b!\u001f\u0002", "vRmMp");
        EntityList.I[0xAE ^ 0x9C] = I("\u001e\u000f+\u00157/\u00035>9$\u0003*", "HfGyV");
        EntityList.I[0x16 ^ 0x25] = I("\u0013-1,\u001e/\u000b*7\u00193", "VCEEj");
        EntityList.I[0xA1 ^ 0x95] = I("\u0016\n)\u0007\u00180", "DkKeq");
        EntityList.I[0x5A ^ 0x6F] = I("\u0005\u001c#$\u00174\u0010=", "SuOHv");
        EntityList.I[0x77 ^ 0x41] = I("\u0006\u001b7\u0007\u001c\u0000\u0007*\u0011\u001a\"\u0019", "CuSbn");
        EntityList.I[0x4C ^ 0x7B] = I("\u001d'h&9t\u0002$=/5\u00071o81\u0004!<>1\u0011-+pt", "TcHOJ");
        EntityList.I[0x41 ^ 0x79] = I("\u0001\nO\u0018\th/\u0003\u0003\u001f)*\u0016Q\b-)\u0006\u0002\u000e-<\n\u0015@h", "HNoqz");
        EntityList.I[0xB0 ^ 0x89] = I(",\u000e>\u001a'\u001bO\"\u0011/\u0006\u001c$\u0011:O\u001b?T:\n\u001c5\u0006>\n\u000bp\u001d,UO", "ooPtH");
        EntityList.I[0xAF ^ 0x95] = I(",\u000e!\u001f%\u001bO=\u0014-\u0006\u001c;\u00148O\u0001:\u001d&O\f#\u00100\u0015O)\u001e8O\u0006+Kj", "ooOqJ");
        EntityList.I[0x3 ^ 0x38] = I("\u00181\u0005\t\t4*\u001f", "UXklj");
        EntityList.I[0xA2 ^ 0x9E] = I("\u0007)", "nMYNz");
        EntityList.I[0x1A ^ 0x27] = I("\u001b4", "rPNrH");
        EntityList.I[0xB6 ^ 0x88] = I("?2\n\u001c", "kKzye");
        EntityList.I[0x3B ^ 0x4] = I("\u0006 \u0004\u001c", "RYtyw");
        EntityList.I[0xCD ^ 0x8D] = I("\u0019\u0013", "pwQjC");
        EntityList.I[0xDD ^ 0x9C] = I("\u001a&\"\u0012\" #,B\u0017'9\"\u0016+i:\"\u0016:i$/B", "IMKbR");
        EntityList.I[0x2 ^ 0x40] = I("8\u0006", "QbHeC");
        EntityList.I[0xF9 ^ 0xBA] = I("\u0004\t=\u00174>\f3G\u00019\u0016=\u0013=w\u0015=\u0013,w\u000b0G", "WbTgD");
        EntityList.I[0xFE ^ 0xBA] = I("!\u0011*\u0012%\u0003\u0011#\u001d\u0013\u0002\u00149", "mxMzQ");
        EntityList.I[0xF5 ^ 0xB0] = I("\u0019$\u0007\u000f\u0014;", "IHfvq");
        EntityList.I[0xE9 ^ 0xAF] = I("\u000b\u0011\n\u0007 )\u0011\u0003\b\u0016(\u0014\u0019", "GxmoT");
        EntityList.I[0x13 ^ 0x54] = I("\u0006\"5:\"$", "VNTCG");
    }
    
    public static int getEntityID(final Entity entity) {
        final Integer n = EntityList.classToIDMapping.get(entity.getClass());
        int n2;
        if (n == null) {
            n2 = "".length();
            "".length();
            if (0 == 4) {
                throw null;
            }
        }
        else {
            n2 = n;
        }
        return n2;
    }
    
    public static String getStringFromID(final int n) {
        return EntityList.classToStringMapping.get(getClassFromID(n));
    }
    
    public static class EntityEggInfo
    {
        public final int secondaryColor;
        public final StatBase field_151512_d;
        public final int spawnedID;
        public final int primaryColor;
        public final StatBase field_151513_e;
        
        public EntityEggInfo(final int spawnedID, final int primaryColor, final int secondaryColor) {
            this.spawnedID = spawnedID;
            this.primaryColor = primaryColor;
            this.secondaryColor = secondaryColor;
            this.field_151512_d = StatList.getStatKillEntity(this);
            this.field_151513_e = StatList.getStatEntityKilledBy(this);
        }
        
        private static String I(final String s, final String s2) {
            final StringBuilder sb = new StringBuilder();
            final char[] charArray = s2.toCharArray();
            int length = "".length();
            final char[] charArray2 = s.toCharArray();
            final int length2 = charArray2.length;
            int i = "".length();
            while (i < length2) {
                sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                ++length;
                ++i;
                "".length();
                if (4 <= 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
}
