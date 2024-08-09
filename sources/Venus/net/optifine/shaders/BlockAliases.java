/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.state.Property;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.optifine.Config;
import net.optifine.ConnectedProperties;
import net.optifine.config.ConnectedParser;
import net.optifine.config.MatchBlock;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorForge;
import net.optifine.shaders.BlockAlias;
import net.optifine.shaders.IShaderPack;
import net.optifine.shaders.ShaderPackNone;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.config.MacroProcessor;
import net.optifine.util.BlockUtils;
import net.optifine.util.PropertiesOrdered;
import net.optifine.util.StrUtils;

public class BlockAliases {
    private static BlockAlias[][] blockAliases = null;
    private static boolean hasAliasMetadata = false;
    private static PropertiesOrdered blockLayerPropertes = null;
    private static boolean updateOnResourcesReloaded;
    private static List<List<BlockAlias>> legacyAliases;

    public static int getAliasBlockId(BlockState blockState) {
        int n;
        int n2 = blockState.getBlockId();
        BlockAlias blockAlias = BlockAliases.getBlockAlias(n2, n = blockState.getMetadata());
        return blockAlias != null ? blockAlias.getAliasBlockId() : -1;
    }

    public static boolean hasAliasMetadata() {
        return hasAliasMetadata;
    }

    public static int getAliasMetadata(BlockState blockState) {
        int n;
        if (!hasAliasMetadata) {
            return 1;
        }
        int n2 = blockState.getBlockId();
        BlockAlias blockAlias = BlockAliases.getBlockAlias(n2, n = blockState.getMetadata());
        return blockAlias != null ? blockAlias.getAliasMetadata() : 0;
    }

    public static BlockAlias getBlockAlias(int n, int n2) {
        if (blockAliases == null) {
            return null;
        }
        if (n >= 0 && n < blockAliases.length) {
            BlockAlias[] blockAliasArray = blockAliases[n];
            if (blockAliasArray == null) {
                return null;
            }
            for (int i = 0; i < blockAliasArray.length; ++i) {
                BlockAlias blockAlias = blockAliasArray[i];
                if (!blockAlias.matches(n, n2)) continue;
                return blockAlias;
            }
            return null;
        }
        return null;
    }

    public static BlockAlias[] getBlockAliases(int n) {
        if (blockAliases == null) {
            return null;
        }
        return n >= 0 && n < blockAliases.length ? blockAliases[n] : null;
    }

    public static void resourcesReloaded() {
        if (updateOnResourcesReloaded) {
            updateOnResourcesReloaded = false;
            BlockAliases.update(Shaders.getShaderPack());
        }
    }

    public static void update(IShaderPack iShaderPack) {
        BlockAliases.reset();
        if (iShaderPack != null && !(iShaderPack instanceof ShaderPackNone)) {
            if (Reflector.Loader_getActiveModList.exists() && Minecraft.getInstance().getResourceManager() == null) {
                Config.dbg("[Shaders] Delayed loading of block mappings after resources are loaded");
                updateOnResourcesReloaded = true;
            } else {
                List<List<BlockAlias>> list = new ArrayList<List<BlockAlias>>();
                String string = "/shaders/block.properties";
                InputStream inputStream = iShaderPack.getResourceAsStream(string);
                if (inputStream != null) {
                    BlockAliases.loadBlockAliases(inputStream, string, list);
                }
                BlockAliases.loadModBlockAliases(list);
                if (list.size() <= 0) {
                    list = BlockAliases.getLegacyAliases();
                    hasAliasMetadata = true;
                }
                blockAliases = BlockAliases.toBlockAliasArrays(list);
            }
        }
    }

    private static void loadModBlockAliases(List<List<BlockAlias>> list) {
        String[] stringArray = ReflectorForge.getForgeModIds();
        for (int i = 0; i < stringArray.length; ++i) {
            String string = stringArray[i];
            try {
                ResourceLocation resourceLocation = new ResourceLocation(string, "shaders/block.properties");
                InputStream inputStream = Config.getResourceStream(resourceLocation);
                BlockAliases.loadBlockAliases(inputStream, resourceLocation.toString(), list);
                continue;
            } catch (IOException iOException) {
                // empty catch block
            }
        }
    }

    private static void loadBlockAliases(InputStream inputStream, String string, List<List<BlockAlias>> list) {
        if (inputStream != null) {
            try {
                inputStream = MacroProcessor.process(inputStream, string, true);
                PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
                propertiesOrdered.load(inputStream);
                inputStream.close();
                Config.dbg("[Shaders] Parsing block mappings: " + string);
                ConnectedParser connectedParser = new ConnectedParser("Shaders");
                for (String string2 : ((Hashtable)propertiesOrdered).keySet()) {
                    String string3 = propertiesOrdered.getProperty(string2);
                    if (string2.startsWith("layer.")) {
                        if (blockLayerPropertes == null) {
                            blockLayerPropertes = new PropertiesOrdered();
                        }
                        blockLayerPropertes.put(string2, string3);
                        continue;
                    }
                    String string4 = "block.";
                    if (!string2.startsWith(string4)) {
                        Config.warn("[Shaders] Invalid block ID: " + string2);
                        continue;
                    }
                    String string5 = StrUtils.removePrefix(string2, string4);
                    int n = Config.parseInt(string5, -1);
                    if (n < 0) {
                        Config.warn("[Shaders] Invalid block ID: " + string2);
                        continue;
                    }
                    MatchBlock[] matchBlockArray = connectedParser.parseMatchBlocks(string3);
                    if (matchBlockArray != null && matchBlockArray.length >= 1) {
                        BlockAlias blockAlias = new BlockAlias(n, matchBlockArray);
                        BlockAliases.addToList(list, blockAlias);
                        continue;
                    }
                    Config.warn("[Shaders] Invalid block ID mapping: " + string2 + "=" + string3);
                }
            } catch (IOException iOException) {
                Config.warn("[Shaders] Error reading: " + string);
            }
        }
    }

    private static void addToList(List<List<BlockAlias>> list, BlockAlias blockAlias) {
        int[] nArray = blockAlias.getMatchBlockIds();
        for (int i = 0; i < nArray.length; ++i) {
            int n = nArray[i];
            while (n >= list.size()) {
                list.add(null);
            }
            List<BlockAlias> list2 = list.get(n);
            if (list2 == null) {
                list2 = new ArrayList<BlockAlias>();
                list.set(n, list2);
            }
            BlockAlias blockAlias2 = new BlockAlias(blockAlias.getAliasBlockId(), blockAlias.getMatchBlocks(n));
            list2.add(blockAlias2);
        }
    }

    private static BlockAlias[][] toBlockAliasArrays(List<List<BlockAlias>> list) {
        BlockAlias[][] blockAliasArray = new BlockAlias[list.size()][];
        for (int i = 0; i < blockAliasArray.length; ++i) {
            List<BlockAlias> list2 = list.get(i);
            if (list2 == null) continue;
            blockAliasArray[i] = list2.toArray(new BlockAlias[list2.size()]);
        }
        return blockAliasArray;
    }

    private static List<List<BlockAlias>> getLegacyAliases() {
        if (legacyAliases == null) {
            legacyAliases = BlockAliases.makeLegacyAliases();
        }
        return legacyAliases;
    }

    private static List<List<BlockAlias>> makeLegacyAliases() {
        try {
            String string = "flattening_ids.txt";
            Config.dbg("Using legacy block aliases: " + string);
            ArrayList<List<BlockAlias>> arrayList = new ArrayList<List<BlockAlias>>();
            ArrayList<String> arrayList2 = new ArrayList<String>();
            int n = 0;
            InputStream inputStream = Config.getOptiFineResourceStream("/" + string);
            if (inputStream == null) {
                return arrayList;
            }
            String[] stringArray = Config.readLines(inputStream);
            for (int i = 0; i < stringArray.length; ++i) {
                int n2 = i + 1;
                String string2 = stringArray[i];
                if (string2.trim().length() <= 0) continue;
                arrayList2.add(string2);
                if (string2.startsWith("#")) continue;
                if (string2.startsWith("alias")) {
                    String[] stringArray2 = Config.tokenize(string2, " ");
                    String string3 = stringArray2[0];
                    String string4 = stringArray2[5];
                    String string5 = "{Name:'" + string4 + "'";
                    List list = arrayList2.stream().filter(arg_0 -> BlockAliases.lambda$makeLegacyAliases$0(string5, arg_0)).collect(Collectors.toList());
                    if (list.size() <= 0) {
                        Config.warn("Block not processed: " + string2);
                        continue;
                    }
                    for (String string6 : list) {
                        String string7 = "{Name:'" + string3 + "'";
                        String string8 = string6.replace(string5, string7);
                        arrayList2.add(string8);
                        BlockAliases.addLegacyAlias(string8, n2, arrayList);
                        ++n;
                    }
                    continue;
                }
                BlockAliases.addLegacyAlias(string2, n2, arrayList);
                ++n;
            }
            Config.dbg("Legacy block aliases: " + n);
            return arrayList;
        } catch (IOException iOException) {
            Config.warn("Error loading legacy block aliases: " + iOException.getClass().getName() + ": " + iOException.getMessage());
            return new ArrayList<List<BlockAlias>>();
        }
    }

    /*
     * WARNING - void declaration
     */
    private static void addLegacyAlias(String string, int n, List<List<BlockAlias>> list) {
        String[] stringArray = Config.tokenize(string, " ");
        if (stringArray.length != 4) {
            Config.warn("Invalid flattening line: " + string);
        } else {
            String string2 = stringArray[0];
            String string3 = stringArray[5];
            int n2 = Config.parseInt(stringArray[5], Integer.MIN_VALUE);
            int n3 = Config.parseInt(stringArray[5], Integer.MIN_VALUE);
            if (n2 >= 0 && n3 >= 0) {
                try {
                    void var18_23;
                    Object object;
                    JsonParser jsonParser = new JsonParser();
                    JsonObject jsonObject = jsonParser.parse(string2).getAsJsonObject();
                    String string4 = jsonObject.get("Name").getAsString();
                    ResourceLocation resourceLocation = new ResourceLocation(string4);
                    Block block = BlockUtils.getBlock(resourceLocation);
                    if (block == null) {
                        Config.warn("Invalid block name (" + n + "): " + string4);
                        return;
                    }
                    BlockState blockState = block.getDefaultState();
                    Collection<Property> collection = blockState.getProperties();
                    LinkedHashMap<Property, Comparable> linkedHashMap = new LinkedHashMap<Property, Comparable>();
                    JsonObject jsonObject2 = (JsonObject)jsonObject.get("Properties");
                    if (jsonObject2 != null) {
                        for (Map.Entry<String, JsonElement> list22 : jsonObject2.entrySet()) {
                            object = list22.getKey();
                            String string5 = list22.getValue().getAsString();
                            Property property = ConnectedProperties.getProperty((String)object, collection);
                            if (property == null) {
                                Config.warn("Invalid property (" + n + "): " + (String)object);
                                continue;
                            }
                            Comparable comparable = ConnectedParser.parsePropertyValue(property, string5);
                            if (comparable == null) {
                                Config.warn("Invalid property value (" + n + "): " + string5);
                                continue;
                            }
                            linkedHashMap.put(property, comparable);
                        }
                    }
                    int n4 = blockState.getBlockId();
                    while (list.size() <= n4) {
                        list.add(null);
                    }
                    List<BlockAlias> list2 = list.get(n4);
                    if (list2 == null) {
                        ArrayList arrayList = new ArrayList(BlockUtils.getMetadataCount(block));
                        list.set(n4, arrayList);
                    }
                    object = BlockAliases.getMatchBlock(blockState.getBlock(), blockState.getBlockId(), linkedHashMap);
                    BlockAliases.addBlockAlias((List<BlockAlias>)var18_23, n2, n3, (MatchBlock)object);
                } catch (Exception exception) {
                    Config.warn("Error parsing: " + string);
                }
            } else {
                Config.warn("Invalid blockID or metadata (" + n + "): " + n2 + ":" + n3);
            }
        }
    }

    private static void addBlockAlias(List<BlockAlias> list, int n, int n2, MatchBlock matchBlock) {
        for (BlockAlias blockAlias : list) {
            if (blockAlias.getAliasBlockId() != n || blockAlias.getAliasMetadata() != n2) continue;
            MatchBlock[] matchBlockArray = blockAlias.getMatchBlocks();
            for (int i = 0; i < matchBlockArray.length; ++i) {
                MatchBlock matchBlock2 = matchBlockArray[i];
                if (matchBlock2.getBlockId() != matchBlock.getBlockId()) continue;
                matchBlock2.addMetadatas(matchBlock.getMetadatas());
                return;
            }
        }
        BlockAlias blockAlias = new BlockAlias(n, n2, new MatchBlock[]{matchBlock});
        list.add(blockAlias);
    }

    private static MatchBlock getMatchBlock(Block block, int n, Map<Property, Comparable> map) {
        ArrayList<BlockState> arrayList = new ArrayList<BlockState>();
        Set<Property> set = map.keySet();
        for (BlockState integerArray2 : BlockUtils.getBlockStates(block)) {
            boolean blockState = true;
            for (Property property : set) {
                Object t;
                if (!integerArray2.hasProperty(property)) {
                    blockState = false;
                    break;
                }
                Comparable comparable = map.get(property);
                if (comparable.equals(t = integerArray2.get(property))) continue;
                blockState = false;
                break;
            }
            if (!blockState) continue;
            arrayList.add(integerArray2);
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (BlockState blockState : arrayList) {
            linkedHashSet.add(blockState.getMetadata());
        }
        Integer[] integerArray = linkedHashSet.toArray(new Integer[linkedHashSet.size()]);
        int[] nArray = Config.toPrimitive(integerArray);
        MatchBlock matchBlock = new MatchBlock(n, nArray);
        return matchBlock;
    }

    private static void checkLegacyAliases() {
        for (ResourceLocation resourceLocation : Registry.BLOCK.keySet()) {
            Block block = Registry.BLOCK.getOrDefault(resourceLocation);
            int n = block.getDefaultState().getBlockId();
            BlockAlias[] blockAliasArray = BlockAliases.getBlockAliases(n);
            if (blockAliasArray == null) {
                Config.warn("Block has no alias: " + block);
                continue;
            }
            for (BlockState blockState : BlockUtils.getBlockStates(block)) {
                int n2 = blockState.getMetadata();
                BlockAlias blockAlias = BlockAliases.getBlockAlias(n, n2);
                if (blockAlias != null) continue;
                Config.warn("State has no alias: " + blockState);
            }
        }
    }

    public static PropertiesOrdered getBlockLayerPropertes() {
        return blockLayerPropertes;
    }

    public static void reset() {
        blockAliases = null;
        hasAliasMetadata = false;
        blockLayerPropertes = null;
    }

    public static int getRenderType(BlockState blockState) {
        if (hasAliasMetadata) {
            Block block = blockState.getBlock();
            if (block instanceof FlowingFluidBlock) {
                return 0;
            }
            BlockRenderType blockRenderType = blockState.getRenderType();
            return blockRenderType != BlockRenderType.ENTITYBLOCK_ANIMATED && blockRenderType != BlockRenderType.MODEL ? blockRenderType.ordinal() : blockRenderType.ordinal() + 1;
        }
        return blockState.getRenderType().ordinal();
    }

    private static boolean lambda$makeLegacyAliases$0(String string, String string2) {
        return string2.startsWith(string);
    }
}

