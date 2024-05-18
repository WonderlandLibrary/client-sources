package optfine;

import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import java.util.*;
import java.io.*;
import net.minecraft.world.*;

public class RandomMobs
{
    private static final String[] DEPENDANT_SUFFIXES;
    private static Random random;
    private static RenderGlobal renderGlobal;
    private static final String[] I;
    public static final String SUFFIX_PROPERTIES;
    private static boolean initialized;
    public static final String PREFIX_TEXTURES_ENTITY;
    private static Map locationProperties;
    public static final String PREFIX_MCPATCHER_MOB;
    private static boolean working;
    public static final String SUFFIX_PNG;
    
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
            if (3 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static RandomMobsProperties getProperties(final ResourceLocation resourceLocation) {
        final String resourcePath = resourceLocation.getResourcePath();
        RandomMobsProperties properties = RandomMobs.locationProperties.get(resourcePath);
        if (properties == null) {
            properties = makeProperties(resourceLocation);
            RandomMobs.locationProperties.put(resourcePath, properties);
        }
        return properties;
    }
    
    public static void worldChanged(final World world, final World world2) {
        if (world2 != null) {
            final List<Entity> loadedEntityList = world2.getLoadedEntityList();
            int i = "".length();
            "".length();
            if (2 >= 3) {
                throw null;
            }
            while (i < loadedEntityList.size()) {
                entityLoaded(loadedEntityList.get(i));
                ++i;
            }
        }
    }
    
    public static ResourceLocation getTextureLocation(final ResourceLocation resourceLocation) {
        if (RandomMobs.working) {
            return resourceLocation;
        }
        try {
            RandomMobs.working = (" ".length() != 0);
            if (!RandomMobs.initialized) {
                initialize();
            }
            if (RandomMobs.renderGlobal != null) {
                final Entity renderedEntity = RandomMobs.renderGlobal.renderedEntity;
                if (!(renderedEntity instanceof EntityLiving)) {
                    return resourceLocation;
                }
                final EntityLiving entityLiving = (EntityLiving)renderedEntity;
                if (!resourceLocation.getResourcePath().startsWith(RandomMobs.I[0xF ^ 0x1])) {
                    return resourceLocation;
                }
                final RandomMobsProperties properties = getProperties(resourceLocation);
                if (properties == null) {
                    return resourceLocation;
                }
                return properties.getTextureLocation(resourceLocation, entityLiving);
            }
            else {
                "".length();
                if (2 >= 3) {
                    throw null;
                }
            }
        }
        finally {
            RandomMobs.working = ("".length() != 0);
        }
        RandomMobs.working = ("".length() != 0);
        return resourceLocation;
    }
    
    private static void I() {
        (I = new String[0x5A ^ 0x11])["".length()] = I("y\u0017!\u0018:2\u0015'\u001e/$", "WgSwJ");
        RandomMobs.I[" ".length()] = I(";\u000b\u001c;\f5\u0000\t(W;\u0007\u000eu", "VhlZx");
        RandomMobs.I["  ".length()] = I("\u001a/\r96\u001c/\u0006b&\u0000>\u001c9:A", "nJuMC");
        RandomMobs.I["   ".length()] = I("F\u001f \u0005", "hoNbI");
        RandomMobs.I[0x8C ^ 0x88] = I("\u0011\u0019=:\n<", "NxOWe");
        RandomMobs.I[0xAC ^ 0xA9] = I("\u000f75=+", "PRLXX");
        RandomMobs.I[0x1 ^ 0x7] = I(".<2\u001f\b\u001e=#\u0001\u0003", "qYJod");
        RandomMobs.I[0x61 ^ 0x66] = I("\u0010\u0001\u0006\u001c$;\u001b\u0000\u0014", "OrnsK");
        RandomMobs.I[0x0 ^ 0x8] = I("3\u0017,\u000b", "lqYyJ");
        RandomMobs.I[0xE ^ 0x7] = I("7\u00155)&", "hpLLU");
        RandomMobs.I[0x49 ^ 0x43] = I("\u0007\u0018\u0018!;4\u001f\u0013%/:\u001d\u0013", "XqvWN");
        RandomMobs.I[0xBC ^ 0xB7] = I("\u0012\u0014\u001f\"\u00174", "MuqEe");
        RandomMobs.I[0x64 ^ 0x68] = I("*2\n%\u0015", "uFkHp");
        RandomMobs.I[0x3A ^ 0x37] = I("\u0006,$6\u00028=", "YOKZn");
        RandomMobs.I[0x32 ^ 0x3C] = I("'\u0017\t\u001e\"!\u0017\u0002E2=\u0006\u0018\u001e.|", "SrqjW");
        RandomMobs.I[0x13 ^ 0x1C] = I("\u0004\u00054\u000b,;)5\r0lD", "VdZoC");
        RandomMobs.I[0xD5 ^ 0xC5] = I("Cf/#6\u0006'767Uf", "oFYBD");
        RandomMobs.I[0x92 ^ 0x83] = I("\u001d+(\"?\"\u0007)$#o:4) *82/5<j()$o,)3>+pf", "OJFFP");
        RandomMobs.I[0x65 ^ 0x77] = I("\u001c*(\u0006\u000b#\u0006)\u0000\u0017n-/\u000e\u0001n%)\u0016D($3\f\u0000tk", "NKFbd");
        RandomMobs.I[0x2D ^ 0x3E] = I("{5\u0017\u0000", "UEygE");
        RandomMobs.I[0x5F ^ 0x4B] = I("Y: =", "wJNZY");
        RandomMobs.I[0x7C ^ 0x69] = I("V\u0012\u001d\u00014\u001d\u0010\u001b\u0007!\u000b", "xbonD");
        RandomMobs.I[0x4B ^ 0x5D] = I("Y\u0016\u0004\u0019\u0011\u0012\u0014\u0002\u001f\u0004\u0004", "wfvva");
        RandomMobs.I[0x50 ^ 0x47] = I("'\u0011,\u0005\u001a!\u0011'^\n=\u0000=\u0005\u0016|", "StTqo");
        RandomMobs.I[0x78 ^ 0x60] = I("5\u000e\u0007\u0007\u000e;\u0005\u0012\u0014U5\u0002\u0015I", "Xmwfz");
        RandomMobs.I[0x34 ^ 0x2D] = I("\u0016\u0001\u001c\u0007\u0012\u0010\u0001\u0017\\\u0002\f\u0010\r\u0007\u001eM", "bddsg");
        RandomMobs.I[0xBF ^ 0xA5] = I("\u000b\u000b\t\"\t4'\b$\u0015cJ", "YjgFf");
        RandomMobs.I[0x3F ^ 0x24] = I("Id>++\f%&>*_d", "eDHJY");
        RandomMobs.I[0x62 ^ 0x7E] = I(")7\u0000", "KVtxU");
        RandomMobs.I[0xB1 ^ 0xAC] = I("-?$6)", "OSELL");
        RandomMobs.I[0x18 ^ 0x6] = I("(7-f\u0017'7:\"", "KVYIu");
        RandomMobs.I[0x9E ^ 0x81] = I("1\n\u001aj\u001b1\u000e\u0002*\u0000", "RknEt");
        RandomMobs.I[0x52 ^ 0x72] = I("\u0005\u00101j\u001d\u0003\u0015", "fqEEo");
        RandomMobs.I[0xE7 ^ 0xC6] = I("\u0015\r5U5\u001f\r,\u001f5\u0013", "vlAzF");
        RandomMobs.I[0x80 ^ 0xA2] = I("\t)!5$\u000f/", "jAHVO");
        RandomMobs.I[0x84 ^ 0xA7] = I("'\u0015/]\u001b+\r", "DzXrx");
        RandomMobs.I[0x36 ^ 0x12] = I("\u0019\t\ry\n\u0015\t\t>\u0015\u0015\t\u0017", "zfzVg");
        RandomMobs.I[0x52 ^ 0x77] = I("\u0007\u0007=\t\u0018\u0001\u0007w\u000f\u001a\u0001\u0010(\t\u001a", "duXlh");
        RandomMobs.I[0x39 ^ 0x1F] = I("\u001d\u0001\u000e\u000f:\u0015\u000e\u0004E-\u0016\u000b\u000f\u0018%\u0019\u0001", "xojjH");
        RandomMobs.I[0x33 ^ 0x14] = I("\u0010\t,\u0014\u0003\u0018\u0006&^\u0014\u001b\u0003-\u0003\u001c\u0014\t\u0017\u0014\b\u0010\u0014", "ugHqq");
        RandomMobs.I[0x3C ^ 0x14] = I(".\u0007\f\n\u0004f\b\u0005\u0018\u0003=", "Iomyp");
        RandomMobs.I[0xA3 ^ 0x8A] = I("\u0013\f\u000e\u001c\u0010[\u0003\u0007\u000e\u0017\u0000;\u001c\u0007\u000b\u001b\u0010\u0006\u0001\u0003", "tdood");
        RandomMobs.I[0x7D ^ 0x57] = I("\u0005\u0007)/5\u000b\u001a*$\u0007", "luFAj");
        RandomMobs.I[0x34 ^ 0x1F] = I("\u0019=\u0001~#\u00003", "iTfQS");
        RandomMobs.I[0x65 ^ 0x49] = I("4\u001e\r*3h\u0005\u0000*&7", "GvhOC");
        RandomMobs.I[0x7 ^ 0x2A] = I("\u0015\u0010\u0017#1I\u000b\u001a#$\u0016'\u001433", "fxrFA");
        RandomMobs.I[0x81 ^ 0xAF] = I("\u0018\u001a'\u0019\u001d\u0019\u0015\"\u001c\u0010", "ksKox");
        RandomMobs.I[0x61 ^ 0x4E] = I("\u0016)\u0007-\t\u0011-\fn\u001f\u000e'\u000e$\u0018\n,", "eBbAl");
        RandomMobs.I[0x78 ^ 0x48] = I("\u0015><\t\u0000\u0012:7J\u0012\u000f!1\u0000\u00179&2\u0000\t\u0003!6\u000b", "fUYee");
        RandomMobs.I[0x5E ^ 0x6F] = I("\u0000(>\u0018(\\7;\u001c \u0016", "sDWuM");
        RandomMobs.I[0x7E ^ 0x4C] = I("*?\u0004\u0018(v>\f\u0012 80\u0018\u0017(", "YSmuM");
        RandomMobs.I[0xBD ^ 0x8E] = I("\u0019\u0003#\u0011(\u000b\u0003", "jmLfE");
        RandomMobs.I[0x7A ^ 0x4E] = I("#\u0017\u0011+\b\"H\u001b.\u001b58\u000b?\u00044\u0002\n", "PgxOm");
        RandomMobs.I[0x24 ^ 0x11] = I("?=\u0010\t\u0002>b\n\u001d\u000e((\u000b", "LMymg");
        RandomMobs.I[0xF ^ 0x39] = I(":$\u0002\u0000\u001d;\u000b\u000e\u001d\u001d:", "ITkdx");
        RandomMobs.I[0x11 ^ 0x26] = I("7;\u0002:\u000b", "DJwSo");
        RandomMobs.I[0x9B ^ 0xA3] = I("\u001c\n \u0019\"\r\u0006>Z5\u0003\u000f \u0014$\u000f\u0011", "jcLuC");
        RandomMobs.I[0x44 ^ 0x7D] = I("\f:\u001e#2\u001d6\u0000`1\u000f'\u0011'6\b", "zSrOS");
        RandomMobs.I[0x87 ^ 0xBD] = I("\u0011\n6\r\"\u0000\u0006(N%\u0006\u00117\u00041", "gcZaC");
        RandomMobs.I[0xBA ^ 0x81] = I(",\u000444\n=\b*w\u00073\u000f*9\u00193\f6", "ZmXXk");
        RandomMobs.I[0xBD ^ 0x81] = I("8\u0002= \u000f)\u000e#c\u001e<\u00024?\u001a", "NkQLn");
        RandomMobs.I[0x94 ^ 0xA9] = I("\u0001-'$\u0012\u0010!9g\u0000\u001a-? ", "wDKHs");
        RandomMobs.I[0x84 ^ 0xBA] = I(";%\u0012\u0018\u0016>c\u0011\u0019\u0007$)\u0014", "LLfps");
        RandomMobs.I[0x61 ^ 0x5E] = I("#\r\u0017,5&K\u0014-$<\u0001\u0011\u001b1&\t\f6", "TdcDP");
        RandomMobs.I[0xF6 ^ 0xB6] = I("\u0013/-& \u0016i.'1\f#+\u0011,\n0,\"+\u000148,)\u0001", "dFYNE");
        RandomMobs.I[0x7C ^ 0x3D] = I("9\u0015(*\\9\u0015(*", "NzDLs");
        RandomMobs.I[0x22 ^ 0x60] = I("\u0011\b\u001d*F\u0011\b\u001d*6\u0007\t\u0016>\u0010", "fgqLi");
        RandomMobs.I[0xE ^ 0x4D] = I("3&-\u0010^3&-\u0010.'&-\u001a\u00106", "DIAvq");
        RandomMobs.I[0x74 ^ 0x30] = I("2\u0000:\u000f`2\u0000:\u000f\u00101\u000e;\f", "EoViO");
        RandomMobs.I[0x32 ^ 0x77] = I("\u0013# ;*\f\u0013=0$\u0004-#", "iLMYC");
        RandomMobs.I[0xC6 ^ 0x80] = I("\u000b\u0016\t\u00101\u0014V\u001e\u001d5\u0013\u0010\u0001", "qydrX");
        RandomMobs.I[0xEF ^ 0xA8] = I("#\t&+\u0004<I1&\u0000;\u000f.\u0016\u001b0\n'(\n<\u0014", "YfKIm");
        RandomMobs.I[0x60 ^ 0x28] = I("\u0000' 90\u0006'+b \u001a619<[", "tBXME");
        RandomMobs.I[0xD2 ^ 0x9B] = I("|1\u001e*", "RApMZ");
        RandomMobs.I[0x77 ^ 0x3D] = I("8-\u000eJ\u000e\u00197\u0014\u000eRV", "vBzjh");
    }
    
    public static void resetTextures() {
        RandomMobs.locationProperties.clear();
        if (Config.isRandomMobs()) {
            initialize();
        }
    }
    
    private static void initialize() {
        RandomMobs.renderGlobal = Config.getRenderGlobal();
        if (RandomMobs.renderGlobal != null) {
            RandomMobs.initialized = (" ".length() != 0);
            final ArrayList<String> list = new ArrayList<String>();
            list.add(RandomMobs.I[0x6A ^ 0x76]);
            list.add(RandomMobs.I[0x73 ^ 0x6E]);
            list.add(RandomMobs.I[0xBE ^ 0xA0]);
            list.add(RandomMobs.I[0xA3 ^ 0xBC]);
            list.add(RandomMobs.I[0x42 ^ 0x62]);
            list.add(RandomMobs.I[0xBD ^ 0x9C]);
            list.add(RandomMobs.I[0x3A ^ 0x18]);
            list.add(RandomMobs.I[0x39 ^ 0x1A]);
            list.add(RandomMobs.I[0x32 ^ 0x16]);
            list.add(RandomMobs.I[0x7A ^ 0x5F]);
            list.add(RandomMobs.I[0x3B ^ 0x1D]);
            list.add(RandomMobs.I[0x97 ^ 0xB0]);
            list.add(RandomMobs.I[0x7E ^ 0x56]);
            list.add(RandomMobs.I[0x3F ^ 0x16]);
            list.add(RandomMobs.I[0xA7 ^ 0x8D]);
            list.add(RandomMobs.I[0x2B ^ 0x0]);
            list.add(RandomMobs.I[0xBA ^ 0x96]);
            list.add(RandomMobs.I[0x6F ^ 0x42]);
            list.add(RandomMobs.I[0x73 ^ 0x5D]);
            list.add(RandomMobs.I[0xAC ^ 0x83]);
            list.add(RandomMobs.I[0x12 ^ 0x22]);
            list.add(RandomMobs.I[0x74 ^ 0x45]);
            list.add(RandomMobs.I[0x87 ^ 0xB5]);
            list.add(RandomMobs.I[0x9B ^ 0xA8]);
            list.add(RandomMobs.I[0x4C ^ 0x78]);
            list.add(RandomMobs.I[0x6A ^ 0x5F]);
            list.add(RandomMobs.I[0xBE ^ 0x88]);
            list.add(RandomMobs.I[0x9D ^ 0xAA]);
            list.add(RandomMobs.I[0x87 ^ 0xBF]);
            list.add(RandomMobs.I[0x97 ^ 0xAE]);
            list.add(RandomMobs.I[0x73 ^ 0x49]);
            list.add(RandomMobs.I[0x96 ^ 0xAD]);
            list.add(RandomMobs.I[0xA5 ^ 0x99]);
            list.add(RandomMobs.I[0x14 ^ 0x29]);
            list.add(RandomMobs.I[0x9F ^ 0xA1]);
            list.add(RandomMobs.I[0xBA ^ 0x85]);
            list.add(RandomMobs.I[0xCC ^ 0x8C]);
            list.add(RandomMobs.I[0x80 ^ 0xC1]);
            list.add(RandomMobs.I[0x17 ^ 0x55]);
            list.add(RandomMobs.I[0x38 ^ 0x7B]);
            list.add(RandomMobs.I[0xEA ^ 0xAE]);
            list.add(RandomMobs.I[0x68 ^ 0x2D]);
            list.add(RandomMobs.I[0x85 ^ 0xC3]);
            list.add(RandomMobs.I[0x7A ^ 0x3D]);
            int i = "".length();
            "".length();
            if (3 == -1) {
                throw null;
            }
            while (i < list.size()) {
                final ResourceLocation resourceLocation = new ResourceLocation(RandomMobs.I[0x6 ^ 0x4E] + list.get(i) + RandomMobs.I[0x31 ^ 0x78]);
                if (!Config.hasResource(resourceLocation)) {
                    Config.warn(RandomMobs.I[0xF7 ^ 0xBD] + resourceLocation);
                }
                getProperties(resourceLocation);
                ++i;
            }
        }
    }
    
    public static ResourceLocation getLocationIndexed(final ResourceLocation resourceLocation, final int n) {
        if (resourceLocation == null) {
            return null;
        }
        final String resourcePath = resourceLocation.getResourcePath();
        final int lastIndex = resourcePath.lastIndexOf(0x45 ^ 0x6B);
        if (lastIndex < 0) {
            return null;
        }
        return new ResourceLocation(resourceLocation.getResourceDomain(), String.valueOf(resourcePath.substring("".length(), lastIndex)) + n + resourcePath.substring(lastIndex));
    }
    
    private static RandomMobsProperties makeProperties(final ResourceLocation resourceLocation) {
        final String resourcePath = resourceLocation.getResourcePath();
        final ResourceLocation propertyLocation = getPropertyLocation(resourceLocation);
        if (propertyLocation != null) {
            final RandomMobsProperties properties = parseProperties(propertyLocation, resourceLocation);
            if (properties != null) {
                return properties;
            }
        }
        return new RandomMobsProperties(resourcePath, getTextureVariants(resourceLocation));
    }
    
    static {
        I();
        SUFFIX_PROPERTIES = RandomMobs.I["".length()];
        PREFIX_MCPATCHER_MOB = RandomMobs.I[" ".length()];
        PREFIX_TEXTURES_ENTITY = RandomMobs.I["  ".length()];
        SUFFIX_PNG = RandomMobs.I["   ".length()];
        RandomMobs.locationProperties = new HashMap();
        RandomMobs.renderGlobal = null;
        RandomMobs.initialized = ("".length() != 0);
        RandomMobs.random = new Random();
        RandomMobs.working = ("".length() != 0);
        final String[] dependant_SUFFIXES = new String[0x84 ^ 0x8E];
        dependant_SUFFIXES["".length()] = RandomMobs.I[0xA1 ^ 0xA5];
        dependant_SUFFIXES[" ".length()] = RandomMobs.I[0xAE ^ 0xAB];
        dependant_SUFFIXES["  ".length()] = RandomMobs.I[0x58 ^ 0x5E];
        dependant_SUFFIXES["   ".length()] = RandomMobs.I[0x8F ^ 0x88];
        dependant_SUFFIXES[0x33 ^ 0x37] = RandomMobs.I[0x3A ^ 0x32];
        dependant_SUFFIXES[0x61 ^ 0x64] = RandomMobs.I[0x3D ^ 0x34];
        dependant_SUFFIXES[0x87 ^ 0x81] = RandomMobs.I[0x4D ^ 0x47];
        dependant_SUFFIXES[0x0 ^ 0x7] = RandomMobs.I[0x7E ^ 0x75];
        dependant_SUFFIXES[0x4E ^ 0x46] = RandomMobs.I[0x70 ^ 0x7C];
        dependant_SUFFIXES[0xB7 ^ 0xBE] = RandomMobs.I[0xB ^ 0x6];
        DEPENDANT_SUFFIXES = dependant_SUFFIXES;
    }
    
    private static ResourceLocation[] getTextureVariants(final ResourceLocation resourceLocation) {
        final ArrayList<ResourceLocation> list = new ArrayList<ResourceLocation>();
        list.add(resourceLocation);
        final ResourceLocation mcpatcherLocation = getMcpatcherLocation(resourceLocation);
        if (mcpatcherLocation == null) {
            return null;
        }
        int i = " ".length();
        "".length();
        if (0 < 0) {
            throw null;
        }
        while (i < list.size() + (0x99 ^ 0x93)) {
            final ResourceLocation locationIndexed = getLocationIndexed(mcpatcherLocation, i + " ".length());
            if (Config.hasResource(locationIndexed)) {
                list.add(locationIndexed);
            }
            ++i;
        }
        if (list.size() <= " ".length()) {
            return null;
        }
        final ResourceLocation[] array = list.toArray(new ResourceLocation[list.size()]);
        Config.dbg(RandomMobs.I[0x74 ^ 0x6E] + resourceLocation.getResourcePath() + RandomMobs.I[0x15 ^ 0xE] + array.length);
        return array;
    }
    
    private static String getParentPath(final String s) {
        int i = "".length();
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (i < RandomMobs.DEPENDANT_SUFFIXES.length) {
            final String s2 = RandomMobs.DEPENDANT_SUFFIXES[i];
            if (s.endsWith(s2)) {
                return s.substring("".length(), s.length() - s2.length());
            }
            ++i;
        }
        return null;
    }
    
    private static RandomMobsProperties parseProperties(final ResourceLocation resourceLocation, final ResourceLocation resourceLocation2) {
        try {
            final String resourcePath = resourceLocation.getResourcePath();
            Config.dbg(RandomMobs.I[0x2 ^ 0xD] + resourceLocation2.getResourcePath() + RandomMobs.I[0x4C ^ 0x5C] + resourcePath);
            final InputStream resourceStream = Config.getResourceStream(resourceLocation);
            if (resourceStream == null) {
                Config.warn(RandomMobs.I[0x56 ^ 0x47] + resourcePath);
                return null;
            }
            final Properties properties = new Properties();
            properties.load(resourceStream);
            final RandomMobsProperties randomMobsProperties = new RandomMobsProperties(properties, resourcePath, resourceLocation2);
            RandomMobsProperties randomMobsProperties2;
            if (!randomMobsProperties.isValid(resourcePath)) {
                randomMobsProperties2 = null;
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
            else {
                randomMobsProperties2 = randomMobsProperties;
            }
            return randomMobsProperties2;
        }
        catch (FileNotFoundException ex2) {
            Config.warn(RandomMobs.I[0x3E ^ 0x2C] + resourceLocation2.getResourcePath());
            return null;
        }
        catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static void entityLoaded(final Entity entity) {
        if (entity instanceof EntityLiving) {
            final EntityLiving entityLiving = (EntityLiving)entity;
            final WorldServer worldServer = Config.getWorldServer();
            if (worldServer != null) {
                final Entity entityByID = worldServer.getEntityByID(entity.getEntityId());
                if (entityByID instanceof EntityLiving) {
                    entityLiving.randomMobsId = (int)(((EntityLiving)entityByID).getUniqueID().getLeastSignificantBits() & 0x7FFFFFFFL);
                    entityLiving.spawnPosition = entityLiving.getPosition();
                    entityLiving.spawnBiome = worldServer.getBiomeGenForCoords(entityLiving.spawnPosition);
                }
            }
        }
    }
    
    private static ResourceLocation getPropertyLocation(final ResourceLocation resourceLocation) {
        final ResourceLocation mcpatcherLocation = getMcpatcherLocation(resourceLocation);
        if (mcpatcherLocation == null) {
            return null;
        }
        final String resourceDomain = mcpatcherLocation.getResourceDomain();
        String s2;
        final String s = s2 = mcpatcherLocation.getResourcePath();
        if (s.endsWith(RandomMobs.I[0x6B ^ 0x78])) {
            s2 = s.substring("".length(), s.length() - RandomMobs.I[0x4D ^ 0x59].length());
        }
        final ResourceLocation resourceLocation2 = new ResourceLocation(resourceDomain, String.valueOf(s2) + RandomMobs.I[0xA ^ 0x1F]);
        if (Config.hasResource(resourceLocation2)) {
            return resourceLocation2;
        }
        final String parentPath = getParentPath(s2);
        if (parentPath == null) {
            return null;
        }
        final ResourceLocation resourceLocation3 = new ResourceLocation(resourceDomain, String.valueOf(parentPath) + RandomMobs.I[0x60 ^ 0x76]);
        ResourceLocation resourceLocation4;
        if (Config.hasResource(resourceLocation3)) {
            resourceLocation4 = resourceLocation3;
            "".length();
            if (1 < 0) {
                throw null;
            }
        }
        else {
            resourceLocation4 = null;
        }
        return resourceLocation4;
    }
    
    public static ResourceLocation getMcpatcherLocation(final ResourceLocation resourceLocation) {
        final String resourcePath = resourceLocation.getResourcePath();
        if (!resourcePath.startsWith(RandomMobs.I[0x80 ^ 0x97])) {
            return null;
        }
        return new ResourceLocation(resourceLocation.getResourceDomain(), RandomMobs.I[0x6E ^ 0x76] + resourcePath.substring(RandomMobs.I[0x4D ^ 0x54].length()));
    }
}
