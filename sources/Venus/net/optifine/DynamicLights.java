/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.monster.BlazeEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.MagmaCubeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.optifine.Config;
import net.optifine.DynamicLight;
import net.optifine.DynamicLightsMap;
import net.optifine.config.ConnectedParser;
import net.optifine.config.EntityTypeNameLocator;
import net.optifine.config.IObjectLocator;
import net.optifine.config.ItemLocator;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorForge;
import net.optifine.util.PropertiesOrdered;

public class DynamicLights {
    private static DynamicLightsMap mapDynamicLights = new DynamicLightsMap();
    private static Map<String, Integer> mapEntityLightLevels = new HashMap<String, Integer>();
    private static Map<Item, Integer> mapItemLightLevels = new HashMap<Item, Integer>();
    private static long timeUpdateMs = 0L;
    private static final double MAX_DIST = 7.5;
    private static final double MAX_DIST_SQ = 56.25;
    private static final int LIGHT_LEVEL_MAX = 15;
    private static final int LIGHT_LEVEL_FIRE = 15;
    private static final int LIGHT_LEVEL_BLAZE = 10;
    private static final int LIGHT_LEVEL_MAGMA_CUBE = 8;
    private static final int LIGHT_LEVEL_MAGMA_CUBE_CORE = 13;
    private static final int LIGHT_LEVEL_GLOWSTONE_DUST = 8;
    private static final int LIGHT_LEVEL_PRISMARINE_CRYSTALS = 8;
    private static final DataParameter<ItemStack> PARAMETER_ITEM_STACK = (DataParameter)Reflector.EntityItem_ITEM.getValue();
    private static boolean initialized;

    public static void entityAdded(Entity entity2, WorldRenderer worldRenderer) {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void entityRemoved(Entity entity2, WorldRenderer worldRenderer) {
        DynamicLightsMap dynamicLightsMap = mapDynamicLights;
        synchronized (dynamicLightsMap) {
            DynamicLight dynamicLight = mapDynamicLights.remove(entity2.getEntityId());
            if (dynamicLight != null) {
                dynamicLight.updateLitChunks(worldRenderer);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void update(WorldRenderer worldRenderer) {
        long l = System.currentTimeMillis();
        if (l >= timeUpdateMs + 50L) {
            timeUpdateMs = l;
            if (!initialized) {
                DynamicLights.initialize();
            }
            DynamicLightsMap dynamicLightsMap = mapDynamicLights;
            synchronized (dynamicLightsMap) {
                DynamicLights.updateMapDynamicLights(worldRenderer);
                if (mapDynamicLights.size() > 0) {
                    List<DynamicLight> list = mapDynamicLights.valueList();
                    for (int i = 0; i < list.size(); ++i) {
                        DynamicLight dynamicLight = list.get(i);
                        dynamicLight.update(worldRenderer);
                    }
                }
            }
        }
    }

    private static void initialize() {
        initialized = true;
        mapEntityLightLevels.clear();
        mapItemLightLevels.clear();
        String[] stringArray = ReflectorForge.getForgeModIds();
        for (int i = 0; i < stringArray.length; ++i) {
            String string = stringArray[i];
            try {
                ResourceLocation resourceLocation = new ResourceLocation(string, "optifine/dynamic_lights.properties");
                InputStream inputStream = Config.getResourceStream(resourceLocation);
                DynamicLights.loadModConfiguration(inputStream, resourceLocation.toString(), string);
                continue;
            } catch (IOException iOException) {
                // empty catch block
            }
        }
        if (mapEntityLightLevels.size() > 0) {
            Config.dbg("DynamicLights entities: " + mapEntityLightLevels.size());
        }
        if (mapItemLightLevels.size() > 0) {
            Config.dbg("DynamicLights items: " + mapItemLightLevels.size());
        }
    }

    private static void loadModConfiguration(InputStream inputStream, String string, String string2) {
        if (inputStream != null) {
            try {
                PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
                propertiesOrdered.load(inputStream);
                inputStream.close();
                Config.dbg("DynamicLights: Parsing " + string);
                ConnectedParser connectedParser = new ConnectedParser("DynamicLights");
                DynamicLights.loadModLightLevels(propertiesOrdered.getProperty("entities"), mapEntityLightLevels, new EntityTypeNameLocator(), connectedParser, string, string2);
                DynamicLights.loadModLightLevels(propertiesOrdered.getProperty("items"), mapItemLightLevels, new ItemLocator(), connectedParser, string, string2);
            } catch (IOException iOException) {
                Config.warn("DynamicLights: Error reading " + string);
            }
        }
    }

    private static <T> void loadModLightLevels(String string, Map<T, Integer> map, IObjectLocator<T> iObjectLocator, ConnectedParser connectedParser, String string2, String string3) {
        if (string != null) {
            String[] stringArray = Config.tokenize(string, " ");
            for (int i = 0; i < stringArray.length; ++i) {
                String string4 = stringArray[i];
                String[] stringArray2 = Config.tokenize(string4, ":");
                if (stringArray2.length != 2) {
                    connectedParser.warn("Invalid entry: " + string4 + ", in:" + string2);
                    continue;
                }
                String string5 = stringArray2[0];
                String string6 = stringArray2[5];
                String string7 = string3 + ":" + string5;
                ResourceLocation resourceLocation = new ResourceLocation(string7);
                T t = iObjectLocator.getObject(resourceLocation);
                if (t == null) {
                    connectedParser.warn("Object not found: " + string7);
                    continue;
                }
                int n = connectedParser.parseInt(string6, -1);
                if (n >= 0 && n <= 15) {
                    map.put(t, new Integer(n));
                    continue;
                }
                connectedParser.warn("Invalid light level: " + string4);
            }
        }
    }

    private static void updateMapDynamicLights(WorldRenderer worldRenderer) {
        ClientWorld clientWorld = worldRenderer.getWorld();
        if (clientWorld != null) {
            for (Entity entity2 : clientWorld.getAllEntities()) {
                DynamicLight dynamicLight;
                int n;
                int n2 = DynamicLights.getLightLevel(entity2);
                if (n2 > 0) {
                    n = entity2.getEntityId();
                    dynamicLight = mapDynamicLights.get(n);
                    if (dynamicLight != null) continue;
                    dynamicLight = new DynamicLight(entity2);
                    mapDynamicLights.put(n, dynamicLight);
                    continue;
                }
                n = entity2.getEntityId();
                dynamicLight = mapDynamicLights.remove(n);
                if (dynamicLight == null) continue;
                dynamicLight.updateLitChunks(worldRenderer);
            }
        }
    }

    public static int getCombinedLight(BlockPos blockPos, int n) {
        double d = DynamicLights.getLightLevel(blockPos);
        return DynamicLights.getCombinedLight(d, n);
    }

    public static int getCombinedLight(Entity entity2, int n) {
        double d = DynamicLights.getLightLevel(entity2.getPosition());
        if (entity2 == Config.getMinecraft().player) {
            double d2 = DynamicLights.getLightLevel(entity2);
            d = Math.max(d, d2);
        }
        return DynamicLights.getCombinedLight(d, n);
    }

    public static int getCombinedLight(double d, int n) {
        int n2;
        int n3;
        if (d > 0.0 && (n3 = (int)(d * 16.0)) > (n2 = n & 0xFF)) {
            n &= 0xFFFFFF00;
            n |= n3;
        }
        return n;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static double getLightLevel(BlockPos blockPos) {
        double d = 0.0;
        DynamicLightsMap dynamicLightsMap = mapDynamicLights;
        synchronized (dynamicLightsMap) {
            List<DynamicLight> list = mapDynamicLights.valueList();
            int n = list.size();
            for (int i = 0; i < n; ++i) {
                double d2;
                double d3;
                double d4;
                double d5;
                double d6;
                DynamicLight dynamicLight = list.get(i);
                int n2 = dynamicLight.getLastLightLevel();
                if (n2 <= 0) continue;
                double d7 = dynamicLight.getLastPosX();
                double d8 = dynamicLight.getLastPosY();
                double d9 = dynamicLight.getLastPosZ();
                double d10 = (double)blockPos.getX() - d7;
                double d11 = d10 * d10 + (d6 = (double)blockPos.getY() - d8) * d6 + (d5 = (double)blockPos.getZ() - d9) * d5;
                if (d11 > 56.25 || !((d4 = (d3 = 1.0 - (d2 = Math.sqrt(d11)) / 7.5) * (double)n2) > d)) continue;
                d = d4;
            }
        }
        return Config.limit(d, 0.0, 15.0);
    }

    public static int getLightLevel(ItemStack itemStack) {
        Object object;
        Block block;
        if (itemStack == null) {
            return 1;
        }
        Item item = itemStack.getItem();
        if (item instanceof BlockItem && (block = ((BlockItem)(object = (BlockItem)item)).getBlock()) != null) {
            return block.getDefaultState().getLightValue();
        }
        if (item == Items.LAVA_BUCKET) {
            return Blocks.LAVA.getDefaultState().getLightValue();
        }
        if (item != Items.BLAZE_ROD && item != Items.BLAZE_POWDER) {
            if (item == Items.GLOWSTONE_DUST) {
                return 1;
            }
            if (item == Items.PRISMARINE_CRYSTALS) {
                return 1;
            }
            if (item == Items.MAGMA_CREAM) {
                return 1;
            }
            if (item == Items.NETHER_STAR) {
                return Blocks.BEACON.getDefaultState().getLightValue() / 2;
            }
            if (!mapItemLightLevels.isEmpty() && (object = mapItemLightLevels.get(item)) != null) {
                return (Integer)object;
            }
            return 1;
        }
        return 1;
    }

    public static int getLightLevel(Entity entity2) {
        Object object;
        Object object2;
        if (entity2 == Config.getMinecraft().getRenderViewEntity() && !Config.isDynamicHandLight()) {
            return 1;
        }
        if (entity2 instanceof PlayerEntity && ((PlayerEntity)(object2 = (PlayerEntity)entity2)).isSpectator()) {
            return 1;
        }
        if (entity2.isBurning()) {
            return 0;
        }
        if (!mapEntityLightLevels.isEmpty() && (object = mapEntityLightLevels.get(object2 = EntityTypeNameLocator.getEntityTypeName(entity2))) != null) {
            return (Integer)object;
        }
        if (entity2 instanceof DamagingProjectileEntity) {
            return 0;
        }
        if (entity2 instanceof TNTEntity) {
            return 0;
        }
        if (entity2 instanceof BlazeEntity) {
            object2 = (BlazeEntity)entity2;
            return ((BlazeEntity)object2).isBurning() ? 15 : 10;
        }
        if (entity2 instanceof MagmaCubeEntity) {
            object2 = (MagmaCubeEntity)entity2;
            return (double)((MagmaCubeEntity)object2).squishFactor > 0.6 ? 13 : 8;
        }
        if (entity2 instanceof CreeperEntity && (double)((CreeperEntity)(object2 = (CreeperEntity)entity2)).getCreeperFlashIntensity(0.0f) > 0.001) {
            return 0;
        }
        if (entity2 instanceof LivingEntity) {
            object2 = (LivingEntity)entity2;
            object = ((LivingEntity)object2).getHeldItemMainhand();
            int n = DynamicLights.getLightLevel((ItemStack)object);
            ItemStack itemStack = ((LivingEntity)object2).getHeldItemOffhand();
            int n2 = DynamicLights.getLightLevel(itemStack);
            ItemStack itemStack2 = ((LivingEntity)object2).getItemStackFromSlot(EquipmentSlotType.HEAD);
            int n3 = DynamicLights.getLightLevel(itemStack2);
            int n4 = Math.max(n, n2);
            return Math.max(n4, n3);
        }
        if (entity2 instanceof ItemEntity) {
            object2 = (ItemEntity)entity2;
            object = DynamicLights.getItemStack((ItemEntity)object2);
            return DynamicLights.getLightLevel((ItemStack)object);
        }
        return 1;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void removeLights(WorldRenderer worldRenderer) {
        DynamicLightsMap dynamicLightsMap = mapDynamicLights;
        synchronized (dynamicLightsMap) {
            List<DynamicLight> list = mapDynamicLights.valueList();
            for (int i = 0; i < list.size(); ++i) {
                DynamicLight dynamicLight = list.get(i);
                dynamicLight.updateLitChunks(worldRenderer);
            }
            mapDynamicLights.clear();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void clear() {
        DynamicLightsMap dynamicLightsMap = mapDynamicLights;
        synchronized (dynamicLightsMap) {
            mapDynamicLights.clear();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static int getCount() {
        DynamicLightsMap dynamicLightsMap = mapDynamicLights;
        synchronized (dynamicLightsMap) {
            return mapDynamicLights.size();
        }
    }

    public static ItemStack getItemStack(ItemEntity itemEntity) {
        return itemEntity.getDataManager().get(PARAMETER_ITEM_STACK);
    }
}

