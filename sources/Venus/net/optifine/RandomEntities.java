/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.ShoulderRidingEntity;
import net.minecraft.entity.passive.horse.HorseEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.optifine.Config;
import net.optifine.IRandomEntity;
import net.optifine.RandomEntity;
import net.optifine.RandomEntityProperties;
import net.optifine.RandomTileEntity;
import net.optifine.reflect.ReflectorRaw;
import net.optifine.util.PropertiesOrdered;
import net.optifine.util.ResUtils;
import net.optifine.util.StrUtils;

public class RandomEntities {
    private static Map<String, RandomEntityProperties> mapProperties = new HashMap<String, RandomEntityProperties>();
    private static boolean active = false;
    private static WorldRenderer renderGlobal;
    private static RandomEntity randomEntity;
    private static TileEntityRendererDispatcher tileEntityRendererDispatcher;
    private static RandomTileEntity randomTileEntity;
    private static boolean working;
    public static final String SUFFIX_PNG = ".png";
    public static final String SUFFIX_PROPERTIES = ".properties";
    public static final String PREFIX_TEXTURES_ENTITY = "textures/entity/";
    public static final String PREFIX_TEXTURES_PAINTING = "textures/painting/";
    public static final String PREFIX_TEXTURES = "textures/";
    public static final String PREFIX_OPTIFINE_RANDOM = "optifine/random/";
    public static final String PREFIX_OPTIFINE_MOB = "optifine/mob/";
    private static final String[] DEPENDANT_SUFFIXES;
    private static final String PREFIX_DYNAMIC_TEXTURE_HORSE = "horse/";
    private static final String[] HORSE_TEXTURES;
    private static final String[] HORSE_TEXTURES_ABBR;

    public static void entityLoaded(Entity entity2, World world) {
        if (world != null) {
            EntityDataManager entityDataManager = entity2.getDataManager();
            entityDataManager.spawnPosition = entity2.getPosition();
            entityDataManager.spawnBiome = world.getBiome(entityDataManager.spawnPosition);
            if (entity2 instanceof ShoulderRidingEntity) {
                ShoulderRidingEntity shoulderRidingEntity = (ShoulderRidingEntity)entity2;
                RandomEntities.checkEntityShoulder(shoulderRidingEntity, false);
            }
        }
    }

    public static void entityUnloaded(Entity entity2, World world) {
        if (entity2 instanceof ShoulderRidingEntity) {
            ShoulderRidingEntity shoulderRidingEntity = (ShoulderRidingEntity)entity2;
            RandomEntities.checkEntityShoulder(shoulderRidingEntity, true);
        }
    }

    private static void checkEntityShoulder(ShoulderRidingEntity shoulderRidingEntity, boolean bl) {
        LivingEntity livingEntity = shoulderRidingEntity.getOwner();
        if (livingEntity == null) {
            livingEntity = Config.getMinecraft().player;
        }
        if (livingEntity instanceof AbstractClientPlayerEntity) {
            AbstractClientPlayerEntity abstractClientPlayerEntity = (AbstractClientPlayerEntity)livingEntity;
            UUID uUID = shoulderRidingEntity.getUniqueID();
            if (bl) {
                CompoundNBT compoundNBT;
                CompoundNBT compoundNBT2 = abstractClientPlayerEntity.getLeftShoulderEntity();
                if (compoundNBT2 != null && compoundNBT2.contains("UUID") && Config.equals(compoundNBT2.getUniqueId("UUID"), uUID)) {
                    abstractClientPlayerEntity.entityShoulderLeft = shoulderRidingEntity;
                }
                if ((compoundNBT = abstractClientPlayerEntity.getRightShoulderEntity()) != null && compoundNBT.contains("UUID") && Config.equals(compoundNBT.getUniqueId("UUID"), uUID)) {
                    abstractClientPlayerEntity.entityShoulderRight = shoulderRidingEntity;
                }
            } else {
                EntityDataManager entityDataManager;
                EntityDataManager entityDataManager2 = shoulderRidingEntity.getDataManager();
                if (abstractClientPlayerEntity.entityShoulderLeft != null && Config.equals(abstractClientPlayerEntity.entityShoulderLeft.getUniqueID(), uUID)) {
                    entityDataManager = abstractClientPlayerEntity.entityShoulderLeft.getDataManager();
                    entityDataManager2.spawnPosition = entityDataManager.spawnPosition;
                    entityDataManager2.spawnBiome = entityDataManager.spawnBiome;
                    abstractClientPlayerEntity.entityShoulderLeft = null;
                }
                if (abstractClientPlayerEntity.entityShoulderRight != null && Config.equals(abstractClientPlayerEntity.entityShoulderRight.getUniqueID(), uUID)) {
                    entityDataManager = abstractClientPlayerEntity.entityShoulderRight.getDataManager();
                    entityDataManager2.spawnPosition = entityDataManager.spawnPosition;
                    entityDataManager2.spawnBiome = entityDataManager.spawnBiome;
                    abstractClientPlayerEntity.entityShoulderRight = null;
                }
            }
        }
    }

    public static void worldChanged(World world, World world2) {
        if (world2 instanceof ClientWorld) {
            ClientWorld clientWorld = (ClientWorld)world2;
            for (Entity entity2 : clientWorld.getAllEntities()) {
                RandomEntities.entityLoaded(entity2, world2);
            }
        }
        randomEntity.setEntity(null);
        randomTileEntity.setTileEntity(null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static ResourceLocation getTextureLocation(ResourceLocation resourceLocation) {
        ResourceLocation resourceLocation2;
        if (!active) {
            return resourceLocation;
        }
        if (working) {
            return resourceLocation;
        }
        try {
            working = true;
            IRandomEntity iRandomEntity = RandomEntities.getRandomEntityRendered();
            if (iRandomEntity != null) {
                String string = resourceLocation.getPath();
                if (string.startsWith(PREFIX_DYNAMIC_TEXTURE_HORSE)) {
                    string = RandomEntities.getHorseTexturePath(string, 6);
                }
                if (!string.startsWith(PREFIX_TEXTURES_ENTITY) && !string.startsWith(PREFIX_TEXTURES_PAINTING)) {
                    ResourceLocation resourceLocation3 = resourceLocation;
                    return resourceLocation3;
                }
                RandomEntityProperties randomEntityProperties = mapProperties.get(string);
                if (randomEntityProperties == null) {
                    ResourceLocation resourceLocation4 = resourceLocation;
                    return resourceLocation4;
                }
                ResourceLocation resourceLocation5 = randomEntityProperties.getTextureLocation(resourceLocation, iRandomEntity);
                return resourceLocation5;
            }
            resourceLocation2 = resourceLocation;
        } finally {
            working = false;
        }
        return resourceLocation2;
    }

    private static String getHorseTexturePath(String string, int n) {
        if (HORSE_TEXTURES != null && HORSE_TEXTURES_ABBR != null) {
            for (int i = 0; i < HORSE_TEXTURES_ABBR.length; ++i) {
                String string2 = HORSE_TEXTURES_ABBR[i];
                if (!string.startsWith(string2, n)) continue;
                return HORSE_TEXTURES[i];
            }
            return string;
        }
        return string;
    }

    public static IRandomEntity getRandomEntityRendered() {
        if (RandomEntities.renderGlobal.renderedEntity != null) {
            randomEntity.setEntity(RandomEntities.renderGlobal.renderedEntity);
            return randomEntity;
        }
        TileEntityRendererDispatcher tileEntityRendererDispatcher = RandomEntities.tileEntityRendererDispatcher;
        if (TileEntityRendererDispatcher.tileEntityRendered != null) {
            tileEntityRendererDispatcher = RandomEntities.tileEntityRendererDispatcher;
            TileEntity tileEntity = TileEntityRendererDispatcher.tileEntityRendered;
            if (tileEntity.getWorld() != null) {
                randomTileEntity.setTileEntity(tileEntity);
                return randomTileEntity;
            }
        }
        return null;
    }

    private static RandomEntityProperties makeProperties(ResourceLocation resourceLocation, boolean bl) {
        ResourceLocation[] resourceLocationArray;
        String string = resourceLocation.getPath();
        ResourceLocation resourceLocation2 = RandomEntities.getLocationProperties(resourceLocation, bl);
        if (resourceLocation2 != null && (resourceLocationArray = RandomEntities.parseProperties(resourceLocation2, resourceLocation)) != null) {
            return resourceLocationArray;
        }
        resourceLocationArray = RandomEntities.getLocationsVariants(resourceLocation, bl);
        return resourceLocationArray == null ? null : new RandomEntityProperties(string, resourceLocationArray);
    }

    private static RandomEntityProperties parseProperties(ResourceLocation resourceLocation, ResourceLocation resourceLocation2) {
        try {
            String string = resourceLocation.getPath();
            RandomEntities.dbg(resourceLocation2.getPath() + ", properties: " + string);
            InputStream inputStream = Config.getResourceStream(resourceLocation);
            if (inputStream == null) {
                RandomEntities.warn("Properties not found: " + string);
                return null;
            }
            PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
            propertiesOrdered.load(inputStream);
            inputStream.close();
            RandomEntityProperties randomEntityProperties = new RandomEntityProperties(propertiesOrdered, string, resourceLocation2);
            return !randomEntityProperties.isValid(string) ? null : randomEntityProperties;
        } catch (FileNotFoundException fileNotFoundException) {
            RandomEntities.warn("File not found: " + resourceLocation2.getPath());
            return null;
        } catch (IOException iOException) {
            iOException.printStackTrace();
            return null;
        }
    }

    private static ResourceLocation getLocationProperties(ResourceLocation resourceLocation, boolean bl) {
        String string;
        String string2;
        String string3;
        ResourceLocation resourceLocation2 = RandomEntities.getLocationRandom(resourceLocation, bl);
        if (resourceLocation2 == null) {
            return null;
        }
        String string4 = resourceLocation2.getNamespace();
        ResourceLocation resourceLocation3 = new ResourceLocation(string4, string3 = (string2 = StrUtils.removeSuffix(string = resourceLocation2.getPath(), SUFFIX_PNG)) + SUFFIX_PROPERTIES);
        if (Config.hasResource(resourceLocation3)) {
            return resourceLocation3;
        }
        String string5 = RandomEntities.getParentTexturePath(string2);
        if (string5 == null) {
            return null;
        }
        ResourceLocation resourceLocation4 = new ResourceLocation(string4, string5 + SUFFIX_PROPERTIES);
        return Config.hasResource(resourceLocation4) ? resourceLocation4 : null;
    }

    protected static ResourceLocation getLocationRandom(ResourceLocation resourceLocation, boolean bl) {
        String string = resourceLocation.getNamespace();
        String string2 = resourceLocation.getPath();
        String string3 = PREFIX_TEXTURES;
        String string4 = PREFIX_OPTIFINE_RANDOM;
        if (bl) {
            string3 = PREFIX_TEXTURES_ENTITY;
            string4 = PREFIX_OPTIFINE_MOB;
        }
        if (!string2.startsWith(string3)) {
            return null;
        }
        String string5 = StrUtils.replacePrefix(string2, string3, string4);
        return new ResourceLocation(string, string5);
    }

    private static String getPathBase(String string) {
        if (string.startsWith(PREFIX_OPTIFINE_RANDOM)) {
            return StrUtils.replacePrefix(string, PREFIX_OPTIFINE_RANDOM, PREFIX_TEXTURES);
        }
        return string.startsWith(PREFIX_OPTIFINE_MOB) ? StrUtils.replacePrefix(string, PREFIX_OPTIFINE_MOB, PREFIX_TEXTURES_ENTITY) : null;
    }

    protected static ResourceLocation getLocationIndexed(ResourceLocation resourceLocation, int n) {
        if (resourceLocation == null) {
            return null;
        }
        String string = resourceLocation.getPath();
        int n2 = string.lastIndexOf(46);
        if (n2 < 0) {
            return null;
        }
        String string2 = string.substring(0, n2);
        String string3 = string.substring(n2);
        String string4 = string2 + n + string3;
        return new ResourceLocation(resourceLocation.getNamespace(), string4);
    }

    private static String getParentTexturePath(String string) {
        for (int i = 0; i < DEPENDANT_SUFFIXES.length; ++i) {
            String string2 = DEPENDANT_SUFFIXES[i];
            if (!string.endsWith(string2)) continue;
            return StrUtils.removeSuffix(string, string2);
        }
        return null;
    }

    private static ResourceLocation[] getLocationsVariants(ResourceLocation resourceLocation, boolean bl) {
        ArrayList<ResourceLocation> arrayList = new ArrayList<ResourceLocation>();
        arrayList.add(resourceLocation);
        ResourceLocation resourceLocation2 = RandomEntities.getLocationRandom(resourceLocation, bl);
        if (resourceLocation2 == null) {
            return null;
        }
        for (int i = 1; i < arrayList.size() + 10; ++i) {
            int n = i + 1;
            ResourceLocation resourceLocation3 = RandomEntities.getLocationIndexed(resourceLocation2, n);
            if (!Config.hasResource(resourceLocation3)) continue;
            arrayList.add(resourceLocation3);
        }
        if (arrayList.size() <= 1) {
            return null;
        }
        ResourceLocation[] resourceLocationArray = arrayList.toArray(new ResourceLocation[arrayList.size()]);
        RandomEntities.dbg(resourceLocation.getPath() + ", variants: " + resourceLocationArray.length);
        return resourceLocationArray;
    }

    public static void update() {
        mapProperties.clear();
        active = false;
        if (Config.isRandomEntities()) {
            RandomEntities.initialize();
        }
    }

    private static void initialize() {
        renderGlobal = Config.getRenderGlobal();
        tileEntityRendererDispatcher = TileEntityRendererDispatcher.instance;
        String[] stringArray = new String[]{PREFIX_OPTIFINE_RANDOM, PREFIX_OPTIFINE_MOB};
        String[] stringArray2 = new String[]{SUFFIX_PNG, SUFFIX_PROPERTIES};
        String[] stringArray3 = ResUtils.collectFiles(stringArray, stringArray2);
        HashSet<String> hashSet = new HashSet<String>();
        for (int i = 0; i < stringArray3.length; ++i) {
            RandomEntityProperties randomEntityProperties;
            Object object = stringArray3[i];
            object = StrUtils.removeSuffix((String)object, stringArray2);
            object = StrUtils.trimTrailing((String)object, "0123456789");
            String string = RandomEntities.getPathBase((String)(object = (String)object + SUFFIX_PNG));
            if (hashSet.contains(string)) continue;
            hashSet.add(string);
            ResourceLocation resourceLocation = new ResourceLocation(string);
            if (!Config.hasResource(resourceLocation) || (randomEntityProperties = mapProperties.get(string)) != null) continue;
            randomEntityProperties = RandomEntities.makeProperties(resourceLocation, false);
            if (randomEntityProperties == null) {
                randomEntityProperties = RandomEntities.makeProperties(resourceLocation, true);
            }
            if (randomEntityProperties == null) continue;
            mapProperties.put(string, randomEntityProperties);
        }
        active = !mapProperties.isEmpty();
    }

    public static void dbg(String string) {
        Config.dbg("RandomEntities: " + string);
    }

    public static void warn(String string) {
        Config.warn("RandomEntities: " + string);
    }

    static {
        randomEntity = new RandomEntity();
        randomTileEntity = new RandomTileEntity();
        working = false;
        DEPENDANT_SUFFIXES = new String[]{"_armor", "_eyes", "_exploding", "_shooting", "_fur", "_eyes", "_invulnerable", "_angry", "_tame", "_collar"};
        HORSE_TEXTURES = (String[])ReflectorRaw.getFieldValue(null, HorseEntity.class, String[].class, 0);
        HORSE_TEXTURES_ABBR = (String[])ReflectorRaw.getFieldValue(null, HorseEntity.class, String[].class, 1);
    }
}

