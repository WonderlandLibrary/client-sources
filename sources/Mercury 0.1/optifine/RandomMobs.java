/*
 * Decompiled with CFR 0.145.
 */
package optifine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeGenBase;
import optifine.Config;
import optifine.RandomMobsProperties;

public class RandomMobs {
    private static Map locationProperties = new HashMap();
    private static RenderGlobal renderGlobal = null;
    private static boolean initialized = false;
    private static Random random = new Random();
    private static boolean working = false;
    public static final String SUFFIX_PNG = ".png";
    public static final String SUFFIX_PROPERTIES = ".properties";
    public static final String PREFIX_TEXTURES_ENTITY = "textures/entity/";
    public static final String PREFIX_MCPATCHER_MOB = "mcpatcher/mob/";
    private static final String[] DEPENDANT_SUFFIXES = new String[]{"_armor", "_eyes", "_exploding", "_shooting", "_fur", "_eyes", "_invulnerable", "_angry", "_tame", "_collar"};

    public static void entityLoaded(Entity entity, World world) {
        if (entity instanceof EntityLiving && world != null) {
            Entity es2;
            EntityLiving el2 = (EntityLiving)entity;
            el2.spawnPosition = el2.getPosition();
            el2.spawnBiome = world.getBiomeGenForCoords(el2.spawnPosition);
            WorldServer ws2 = Config.getWorldServer();
            if (ws2 != null && (es2 = ws2.getEntityByID(entity.getEntityId())) instanceof EntityLiving) {
                int id2;
                EntityLiving els = (EntityLiving)es2;
                UUID uuid = els.getUniqueID();
                long uuidLow = uuid.getLeastSignificantBits();
                el2.randomMobsId = id2 = (int)(uuidLow & Integer.MAX_VALUE);
            }
        }
    }

    public static void worldChanged(World oldWorld, World newWorld) {
        if (newWorld != null) {
            List entityList = newWorld.getLoadedEntityList();
            for (int e2 = 0; e2 < entityList.size(); ++e2) {
                Entity entity = (Entity)entityList.get(e2);
                RandomMobs.entityLoaded(entity, newWorld);
            }
        }
    }

    public static ResourceLocation getTextureLocation(ResourceLocation loc) {
        ResourceLocation var5;
        if (working) {
            return loc;
        }
        try {
            working = true;
            if (!initialized) {
                RandomMobs.initialize();
            }
            if (renderGlobal == null) {
                ResourceLocation entity1;
                ResourceLocation resourceLocation = entity1 = loc;
                return resourceLocation;
            }
            Entity entity = RandomMobs.renderGlobal.renderedEntity;
            if (!(entity instanceof EntityLiving)) {
                ResourceLocation entityLiving1;
                ResourceLocation resourceLocation = entityLiving1 = loc;
                return resourceLocation;
            }
            EntityLiving entityLiving = (EntityLiving)entity;
            String name = loc.getResourcePath();
            if (!name.startsWith(PREFIX_TEXTURES_ENTITY)) {
                ResourceLocation props1;
                ResourceLocation resourceLocation = props1 = loc;
                return resourceLocation;
            }
            RandomMobsProperties props = RandomMobs.getProperties(loc);
            if (props != null) {
                ResourceLocation var52;
                ResourceLocation resourceLocation = var52 = props.getTextureLocation(loc, entityLiving);
                return resourceLocation;
            }
            var5 = loc;
        }
        finally {
            working = false;
        }
        return var5;
    }

    private static RandomMobsProperties getProperties(ResourceLocation loc) {
        String name = loc.getResourcePath();
        RandomMobsProperties props = (RandomMobsProperties)locationProperties.get(name);
        if (props == null) {
            props = RandomMobs.makeProperties(loc);
            locationProperties.put(name, props);
        }
        return props;
    }

    private static RandomMobsProperties makeProperties(ResourceLocation loc) {
        RandomMobsProperties variants;
        String path = loc.getResourcePath();
        ResourceLocation propLoc = RandomMobs.getPropertyLocation(loc);
        if (propLoc != null && (variants = RandomMobs.parseProperties(propLoc, loc)) != null) {
            return variants;
        }
        ResourceLocation[] variants1 = RandomMobs.getTextureVariants(loc);
        return new RandomMobsProperties(path, variants1);
    }

    private static RandomMobsProperties parseProperties(ResourceLocation propLoc, ResourceLocation resLoc) {
        InputStream in2;
        String e2;
        block4 : {
            e2 = propLoc.getResourcePath();
            Config.dbg("RandomMobs: " + resLoc.getResourcePath() + ", variants: " + e2);
            in2 = Config.getResourceStream(propLoc);
            if (in2 != null) break block4;
            Config.warn("RandomMobs properties not found: " + e2);
            return null;
        }
        try {
            Properties props = new Properties();
            props.load(in2);
            in2.close();
            RandomMobsProperties rmp = new RandomMobsProperties(props, e2, resLoc);
            return !rmp.isValid(e2) ? null : rmp;
        }
        catch (FileNotFoundException var6) {
            Config.warn("RandomMobs file not found: " + resLoc.getResourcePath());
            return null;
        }
        catch (IOException var7) {
            var7.printStackTrace();
            return null;
        }
    }

    private static ResourceLocation getPropertyLocation(ResourceLocation loc) {
        ResourceLocation locProps;
        String pathProps;
        String path;
        ResourceLocation locMcp = RandomMobs.getMcpatcherLocation(loc);
        if (locMcp == null) {
            return null;
        }
        String domain = locMcp.getResourceDomain();
        String pathBase = path = locMcp.getResourcePath();
        if (path.endsWith(SUFFIX_PNG)) {
            pathBase = path.substring(0, path.length() - SUFFIX_PNG.length());
        }
        if (Config.hasResource(locProps = new ResourceLocation(domain, pathProps = String.valueOf(pathBase) + SUFFIX_PROPERTIES))) {
            return locProps;
        }
        String pathParent = RandomMobs.getParentPath(pathBase);
        if (pathParent == null) {
            return null;
        }
        ResourceLocation locParentProps = new ResourceLocation(domain, String.valueOf(pathParent) + SUFFIX_PROPERTIES);
        return Config.hasResource(locParentProps) ? locParentProps : null;
    }

    public static ResourceLocation getMcpatcherLocation(ResourceLocation loc) {
        String path = loc.getResourcePath();
        if (!path.startsWith(PREFIX_TEXTURES_ENTITY)) {
            return null;
        }
        String pathMcp = PREFIX_MCPATCHER_MOB + path.substring(PREFIX_TEXTURES_ENTITY.length());
        return new ResourceLocation(loc.getResourceDomain(), pathMcp);
    }

    public static ResourceLocation getLocationIndexed(ResourceLocation loc, int index) {
        if (loc == null) {
            return null;
        }
        String path = loc.getResourcePath();
        int pos = path.lastIndexOf(46);
        if (pos < 0) {
            return null;
        }
        String prefix = path.substring(0, pos);
        String suffix = path.substring(pos);
        String pathNew = String.valueOf(prefix) + index + suffix;
        ResourceLocation locNew = new ResourceLocation(loc.getResourceDomain(), pathNew);
        return locNew;
    }

    private static String getParentPath(String path) {
        for (int i2 = 0; i2 < DEPENDANT_SUFFIXES.length; ++i2) {
            String suffix = DEPENDANT_SUFFIXES[i2];
            if (!path.endsWith(suffix)) continue;
            String pathParent = path.substring(0, path.length() - suffix.length());
            return pathParent;
        }
        return null;
    }

    private static ResourceLocation[] getTextureVariants(ResourceLocation loc) {
        ArrayList<ResourceLocation> list = new ArrayList<ResourceLocation>();
        list.add(loc);
        ResourceLocation locMcp = RandomMobs.getMcpatcherLocation(loc);
        if (locMcp == null) {
            return null;
        }
        for (int locs = 1; locs < list.size() + 10; ++locs) {
            int index = locs + 1;
            ResourceLocation locIndex = RandomMobs.getLocationIndexed(locMcp, index);
            if (!Config.hasResource(locIndex)) continue;
            list.add(locIndex);
        }
        if (list.size() <= 1) {
            return null;
        }
        ResourceLocation[] var6 = list.toArray(new ResourceLocation[list.size()]);
        Config.dbg("RandomMobs: " + loc.getResourcePath() + ", variants: " + var6.length);
        return var6;
    }

    public static void resetTextures() {
        locationProperties.clear();
        if (Config.isRandomMobs()) {
            RandomMobs.initialize();
        }
    }

    private static void initialize() {
        renderGlobal = Config.getRenderGlobal();
        if (renderGlobal != null) {
            initialized = true;
            ArrayList<String> list = new ArrayList<String>();
            list.add("bat");
            list.add("blaze");
            list.add("cat/black");
            list.add("cat/ocelot");
            list.add("cat/red");
            list.add("cat/siamese");
            list.add("chicken");
            list.add("cow/cow");
            list.add("cow/mooshroom");
            list.add("creeper/creeper");
            list.add("enderman/enderman");
            list.add("enderman/enderman_eyes");
            list.add("ghast/ghast");
            list.add("ghast/ghast_shooting");
            list.add("iron_golem");
            list.add("pig/pig");
            list.add("sheep/sheep");
            list.add("sheep/sheep_fur");
            list.add("silverfish");
            list.add("skeleton/skeleton");
            list.add("skeleton/wither_skeleton");
            list.add("slime/slime");
            list.add("slime/magmacube");
            list.add("snowman");
            list.add("spider/cave_spider");
            list.add("spider/spider");
            list.add("spider_eyes");
            list.add("squid");
            list.add("villager/villager");
            list.add("villager/butcher");
            list.add("villager/farmer");
            list.add("villager/librarian");
            list.add("villager/priest");
            list.add("villager/smith");
            list.add("wither/wither");
            list.add("wither/wither_armor");
            list.add("wither/wither_invulnerable");
            list.add("wolf/wolf");
            list.add("wolf/wolf_angry");
            list.add("wolf/wolf_collar");
            list.add("wolf/wolf_tame");
            list.add("zombie_pigman");
            list.add("zombie/zombie");
            list.add("zombie/zombie_villager");
            for (int i2 = 0; i2 < list.size(); ++i2) {
                String name = (String)list.get(i2);
                String tex = PREFIX_TEXTURES_ENTITY + name + SUFFIX_PNG;
                ResourceLocation texLoc = new ResourceLocation(tex);
                if (!Config.hasResource(texLoc)) {
                    Config.warn("Not found: " + texLoc);
                }
                RandomMobs.getProperties(texLoc);
            }
        }
    }
}

