/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.config;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.state.Property;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.DefaultedRegistry;
import net.minecraft.util.registry.Registry;
import net.optifine.Config;
import net.optifine.ConnectedProperties;
import net.optifine.config.BiomeId;
import net.optifine.config.INameGetter;
import net.optifine.config.MatchBlock;
import net.optifine.config.MatchProfession;
import net.optifine.config.NbtTagValue;
import net.optifine.config.RangeInt;
import net.optifine.config.RangeListInt;
import net.optifine.config.Weather;
import net.optifine.util.BiomeUtils;
import net.optifine.util.BlockUtils;
import net.optifine.util.EntityTypeUtils;
import net.optifine.util.ItemUtils;

public class ConnectedParser {
    private String context = null;
    public static final MatchProfession[] PROFESSIONS_INVALID = new MatchProfession[0];
    public static final DyeColor[] DYE_COLORS_INVALID = new DyeColor[0];
    private static Map<ResourceLocation, BiomeId> MAP_BIOMES_COMPACT = null;
    private static final INameGetter<Enum> NAME_GETTER_ENUM = new INameGetter<Enum>(){

        @Override
        public String getName(Enum enum_) {
            return enum_.name();
        }

        @Override
        public String getName(Object object) {
            return this.getName((Enum)object);
        }
    };
    private static final INameGetter<DyeColor> NAME_GETTER_DYE_COLOR = new INameGetter<DyeColor>(){

        @Override
        public String getName(DyeColor dyeColor) {
            return dyeColor.getString();
        }

        @Override
        public String getName(Object object) {
            return this.getName((DyeColor)object);
        }
    };

    public ConnectedParser(String string) {
        this.context = string;
    }

    public String parseName(String string) {
        int n;
        String string2 = string;
        int n2 = string.lastIndexOf(47);
        if (n2 >= 0) {
            string2 = string.substring(n2 + 1);
        }
        if ((n = string2.lastIndexOf(46)) >= 0) {
            string2 = string2.substring(0, n);
        }
        return string2;
    }

    public String parseBasePath(String string) {
        int n = string.lastIndexOf(47);
        return n < 0 ? "" : string.substring(0, n);
    }

    public MatchBlock[] parseMatchBlocks(String string) {
        if (string == null) {
            return null;
        }
        ArrayList<MatchBlock> arrayList = new ArrayList<MatchBlock>();
        String[] stringArray = Config.tokenize(string, " ");
        for (int i = 0; i < stringArray.length; ++i) {
            String string2 = stringArray[i];
            MatchBlock[] matchBlockArray = this.parseMatchBlock(string2);
            if (matchBlockArray == null) continue;
            arrayList.addAll(Arrays.asList(matchBlockArray));
        }
        return arrayList.toArray(new MatchBlock[arrayList.size()]);
    }

    public BlockState parseBlockState(String string, BlockState blockState) {
        MatchBlock[] matchBlockArray = this.parseMatchBlock(string);
        if (matchBlockArray == null) {
            return blockState;
        }
        if (matchBlockArray.length != 1) {
            return blockState;
        }
        MatchBlock matchBlock = matchBlockArray[0];
        int n = matchBlock.getBlockId();
        Block block = Registry.BLOCK.getByValue(n);
        return block.getDefaultState();
    }

    public MatchBlock[] parseMatchBlock(String string) {
        if (string == null) {
            return null;
        }
        if ((string = string.trim()).length() <= 0) {
            return null;
        }
        String[] stringArray = Config.tokenize(string, ":");
        String string2 = "minecraft";
        int n = 0;
        if (stringArray.length > 1 && this.isFullBlockName(stringArray)) {
            string2 = stringArray[0];
            n = 1;
        } else {
            string2 = "minecraft";
            n = 0;
        }
        String string3 = stringArray[n];
        String[] stringArray2 = Arrays.copyOfRange(stringArray, n + 1, stringArray.length);
        Block[] blockArray = this.parseBlockPart(string2, string3);
        if (blockArray == null) {
            return null;
        }
        MatchBlock[] matchBlockArray = new MatchBlock[blockArray.length];
        for (int i = 0; i < blockArray.length; ++i) {
            MatchBlock matchBlock;
            Block block = blockArray[i];
            int n2 = Registry.BLOCK.getId(block);
            int[] nArray = null;
            if (stringArray2.length > 0 && (nArray = this.parseBlockMetadatas(block, stringArray2)) == null) {
                return null;
            }
            matchBlockArray[i] = matchBlock = new MatchBlock(n2, nArray);
        }
        return matchBlockArray;
    }

    public boolean isFullBlockName(String[] stringArray) {
        if (stringArray.length <= 1) {
            return true;
        }
        String string = stringArray[0];
        if (string.length() < 1) {
            return true;
        }
        return !string.contains("=");
    }

    public boolean startsWithDigit(String string) {
        if (string == null) {
            return true;
        }
        if (string.length() < 1) {
            return true;
        }
        char c = string.charAt(0);
        return Character.isDigit(c);
    }

    public Block[] parseBlockPart(String string, String string2) {
        String string3 = string + ":" + string2;
        ResourceLocation resourceLocation = new ResourceLocation(string3);
        Block block = BlockUtils.getBlock(resourceLocation);
        if (block == null) {
            this.warn("Block not found for name: " + string3);
            return null;
        }
        return new Block[]{block};
    }

    public int[] parseBlockMetadatas(Block block, String[] stringArray) {
        Object object;
        if (stringArray.length <= 0) {
            return null;
        }
        BlockState blockState = block.getDefaultState();
        Collection<Property> collection = blockState.getProperties();
        HashMap<Property, List<Comparable>> hashMap = new HashMap<Property, List<Comparable>>();
        for (int i = 0; i < stringArray.length; ++i) {
            String string = stringArray[i];
            if (string.length() <= 0) continue;
            String[] stringArray2 = Config.tokenize(string, "=");
            if (stringArray2.length != 2) {
                this.warn("Invalid block property: " + string);
                return null;
            }
            object = stringArray2[0];
            String string2 = stringArray2[5];
            Property property = ConnectedProperties.getProperty((String)object, collection);
            if (property == null) {
                this.warn("Property not found: " + (String)object + ", block: " + block);
                return null;
            }
            ArrayList<Comparable> arrayList = (ArrayList<Comparable>)hashMap.get(object);
            if (arrayList == null) {
                arrayList = new ArrayList<Comparable>();
                hashMap.put(property, arrayList);
            }
            String[] stringArray3 = Config.tokenize(string2, ",");
            for (int j = 0; j < stringArray3.length; ++j) {
                String string3 = stringArray3[j];
                Comparable comparable = ConnectedParser.parsePropertyValue(property, string3);
                if (comparable == null) {
                    this.warn("Property value not found: " + string3 + ", property: " + (String)object + ", block: " + block);
                    return null;
                }
                arrayList.add(comparable);
            }
        }
        if (hashMap.isEmpty()) {
            return null;
        }
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        int n = BlockUtils.getMetadataCount(block);
        for (int i = 0; i < n; ++i) {
            try {
                object = BlockUtils.getBlockState(block, i);
                if (!this.matchState((BlockState)object, hashMap)) continue;
                arrayList.add(i);
                continue;
            } catch (IllegalArgumentException illegalArgumentException) {
                // empty catch block
            }
        }
        if (arrayList.size() == n) {
            return null;
        }
        int[] nArray = new int[arrayList.size()];
        for (int i = 0; i < nArray.length; ++i) {
            nArray[i] = (Integer)arrayList.get(i);
        }
        return nArray;
    }

    public static Comparable parsePropertyValue(Property property, String string) {
        Class clazz = property.getValueClass();
        Comparable comparable = ConnectedParser.parseValue(string, clazz);
        if (comparable == null) {
            Collection collection = property.getAllowedValues();
            comparable = ConnectedParser.getPropertyValue(string, collection);
        }
        return comparable;
    }

    public static Comparable getPropertyValue(String string, Collection collection) {
        for (Comparable comparable : (Set)collection) {
            if (!ConnectedParser.getValueName(comparable).equals(string)) continue;
            return comparable;
        }
        return null;
    }

    private static Object getValueName(Comparable comparable) {
        if (comparable instanceof IStringSerializable) {
            IStringSerializable iStringSerializable = (IStringSerializable)((Object)comparable);
            return iStringSerializable.getString();
        }
        return comparable.toString();
    }

    public static Comparable parseValue(String string, Class clazz) {
        if (clazz == String.class) {
            return string;
        }
        if (clazz == Boolean.class) {
            return Boolean.valueOf(string);
        }
        if (clazz == Float.class) {
            return Float.valueOf(string);
        }
        if (clazz == Double.class) {
            return Double.valueOf(string);
        }
        if (clazz == Integer.class) {
            return Integer.valueOf(string);
        }
        return clazz == Long.class ? Long.valueOf(string) : null;
    }

    public boolean matchState(BlockState blockState, Map<Property, List<Comparable>> map) {
        for (Property property : map.keySet()) {
            List<Comparable> list = map.get(property);
            Object t = blockState.get(property);
            if (t == null) {
                return true;
            }
            if (list.contains(t)) continue;
            return true;
        }
        return false;
    }

    public BiomeId[] parseBiomes(String string) {
        if (string == null) {
            return null;
        }
        string = string.trim();
        boolean bl = false;
        if (string.startsWith("!")) {
            bl = true;
            string = string.substring(1);
        }
        String[] stringArray = Config.tokenize(string, " ");
        List<BiomeId> list = new ArrayList<BiomeId>();
        for (int i = 0; i < stringArray.length; ++i) {
            String string2 = stringArray[i];
            BiomeId biomeId = this.getBiomeId(string2);
            if (biomeId == null) {
                this.warn("Biome not found: " + (String)string2);
                continue;
            }
            list.add(biomeId);
        }
        if (bl) {
            HashSet<ResourceLocation> hashSet = new HashSet<ResourceLocation>(BiomeUtils.getLocations());
            for (BiomeId biomeId : list) {
                hashSet.remove(biomeId.getResourceLocation());
            }
            list = BiomeUtils.getBiomeIds(hashSet);
        }
        return list.toArray(new BiomeId[list.size()]);
    }

    public BiomeId getBiomeId(String string) {
        ResourceLocation resourceLocation = new ResourceLocation(string = string.toLowerCase());
        BiomeId biomeId = BiomeUtils.getBiomeId(resourceLocation);
        if (biomeId != null) {
            return biomeId;
        }
        String string2 = string.replace(" ", "").replace("_", "");
        ResourceLocation resourceLocation2 = new ResourceLocation(string2);
        if (MAP_BIOMES_COMPACT == null) {
            MAP_BIOMES_COMPACT = new HashMap<ResourceLocation, BiomeId>();
            for (ResourceLocation resourceLocation3 : BiomeUtils.getLocations()) {
                BiomeId biomeId2 = BiomeUtils.getBiomeId(resourceLocation3);
                if (biomeId2 == null) continue;
                String string3 = resourceLocation3.getPath().replace(" ", "").replace("_", "").toLowerCase();
                ResourceLocation resourceLocation4 = new ResourceLocation(resourceLocation3.getNamespace(), string3);
                MAP_BIOMES_COMPACT.put(resourceLocation4, biomeId2);
            }
        }
        return (biomeId = MAP_BIOMES_COMPACT.get(resourceLocation2)) != null ? biomeId : null;
    }

    public int parseInt(String string, int n) {
        if (string == null) {
            return n;
        }
        int n2 = Config.parseInt(string = string.trim(), -1);
        if (n2 < 0) {
            this.warn("Invalid number: " + string);
            return n;
        }
        return n2;
    }

    public int[] parseIntList(String string) {
        if (string == null) {
            return null;
        }
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        String[] stringArray = Config.tokenize(string, " ,");
        for (int i = 0; i < stringArray.length; ++i) {
            String string2 = stringArray[i];
            if (string2.contains("-")) {
                String[] stringArray2 = Config.tokenize(string2, "-");
                if (stringArray2.length != 2) {
                    this.warn("Invalid interval: " + string2 + ", when parsing: " + string);
                    continue;
                }
                int n = Config.parseInt(stringArray2[0], -1);
                int n2 = Config.parseInt(stringArray2[5], -1);
                if (n >= 0 && n2 >= 0 && n <= n2) {
                    for (int j = n; j <= n2; ++j) {
                        arrayList.add(j);
                    }
                    continue;
                }
                this.warn("Invalid interval: " + string2 + ", when parsing: " + string);
                continue;
            }
            int n = Config.parseInt(string2, -1);
            if (n < 0) {
                this.warn("Invalid number: " + string2 + ", when parsing: " + string);
                continue;
            }
            arrayList.add(n);
        }
        int[] nArray = new int[arrayList.size()];
        for (int i = 0; i < nArray.length; ++i) {
            nArray[i] = (Integer)arrayList.get(i);
        }
        return nArray;
    }

    public boolean[] parseFaces(String string, boolean[] blArray) {
        if (string == null) {
            return blArray;
        }
        EnumSet<Direction> enumSet = EnumSet.allOf(Direction.class);
        String[] stringArray = Config.tokenize(string, " ,");
        for (int i = 0; i < stringArray.length; ++i) {
            String string2 = stringArray[i];
            if (string2.equals("sides")) {
                enumSet.add(Direction.NORTH);
                enumSet.add(Direction.SOUTH);
                enumSet.add(Direction.WEST);
                enumSet.add(Direction.EAST);
                continue;
            }
            if (string2.equals("all")) {
                enumSet.addAll(Arrays.asList(Direction.VALUES));
                continue;
            }
            Direction direction = this.parseFace(string2);
            if (direction == null) continue;
            enumSet.add(direction);
        }
        boolean[] blArray2 = new boolean[Direction.VALUES.length];
        for (int i = 0; i < blArray2.length; ++i) {
            blArray2[i] = enumSet.contains(Direction.VALUES[i]);
        }
        return blArray2;
    }

    public Direction parseFace(String string) {
        if (!(string = string.toLowerCase()).equals("bottom") && !string.equals("down")) {
            if (!string.equals("top") && !string.equals("up")) {
                if (string.equals("north")) {
                    return Direction.NORTH;
                }
                if (string.equals("south")) {
                    return Direction.SOUTH;
                }
                if (string.equals("east")) {
                    return Direction.EAST;
                }
                if (string.equals("west")) {
                    return Direction.WEST;
                }
                Config.warn("Unknown face: " + string);
                return null;
            }
            return Direction.UP;
        }
        return Direction.DOWN;
    }

    public void dbg(String string) {
        Config.dbg(this.context + ": " + string);
    }

    public void warn(String string) {
        Config.warn(this.context + ": " + string);
    }

    public RangeListInt parseRangeListInt(String string) {
        if (string == null) {
            return null;
        }
        RangeListInt rangeListInt = new RangeListInt();
        String[] stringArray = Config.tokenize(string, " ,");
        for (int i = 0; i < stringArray.length; ++i) {
            String string2 = stringArray[i];
            RangeInt rangeInt = this.parseRangeInt(string2);
            if (rangeInt == null) {
                return null;
            }
            rangeListInt.addRange(rangeInt);
        }
        return rangeListInt;
    }

    private RangeInt parseRangeInt(String string) {
        if (string == null) {
            return null;
        }
        if (string.indexOf(45) >= 0) {
            String[] stringArray = Config.tokenize(string, "-");
            if (stringArray.length != 2) {
                this.warn("Invalid range: " + string);
                return null;
            }
            int n = Config.parseInt(stringArray[0], -1);
            int n2 = Config.parseInt(stringArray[5], -1);
            if (n >= 0 && n2 >= 0) {
                return new RangeInt(n, n2);
            }
            this.warn("Invalid range: " + string);
            return null;
        }
        int n = Config.parseInt(string, -1);
        if (n < 0) {
            this.warn("Invalid integer: " + string);
            return null;
        }
        return new RangeInt(n, n);
    }

    public boolean parseBoolean(String string, boolean bl) {
        if (string == null) {
            return bl;
        }
        String string2 = string.toLowerCase().trim();
        if (string2.equals("true")) {
            return false;
        }
        if (string2.equals("false")) {
            return true;
        }
        this.warn("Invalid boolean: " + string);
        return bl;
    }

    public Boolean parseBooleanObject(String string) {
        if (string == null) {
            return null;
        }
        String string2 = string.toLowerCase().trim();
        if (string2.equals("true")) {
            return Boolean.TRUE;
        }
        if (string2.equals("false")) {
            return Boolean.FALSE;
        }
        this.warn("Invalid boolean: " + string);
        return null;
    }

    public static int parseColor(String string, int n) {
        if (string == null) {
            return n;
        }
        string = string.trim();
        try {
            return Integer.parseInt(string, 16) & 0xFFFFFF;
        } catch (NumberFormatException numberFormatException) {
            return n;
        }
    }

    public static int parseColor4(String string, int n) {
        if (string == null) {
            return n;
        }
        string = string.trim();
        try {
            return (int)(Long.parseLong(string, 16) & 0xFFFFFFFFFFFFFFFFL);
        } catch (NumberFormatException numberFormatException) {
            return n;
        }
    }

    public RenderType parseBlockRenderLayer(String string, RenderType renderType) {
        if (string == null) {
            return renderType;
        }
        string = string.toLowerCase().trim();
        RenderType[] renderTypeArray = RenderType.CHUNK_RENDER_TYPES;
        for (int i = 0; i < renderTypeArray.length; ++i) {
            RenderType renderType2 = renderTypeArray[i];
            if (!string.equals(renderType2.getName().toLowerCase())) continue;
            return renderType2;
        }
        return renderType;
    }

    public <T> T parseObject(String string, T[] TArray, INameGetter iNameGetter, String string2) {
        if (string == null) {
            return null;
        }
        String string3 = string.toLowerCase().trim();
        for (int i = 0; i < TArray.length; ++i) {
            T t = TArray[i];
            String string4 = iNameGetter.getName(t);
            if (string4 == null || !string4.toLowerCase().equals(string3)) continue;
            return t;
        }
        this.warn("Invalid " + string2 + ": " + string);
        return null;
    }

    public <T> T[] parseObjects(String string, T[] TArray, INameGetter iNameGetter, String string2, T[] TArray2) {
        if (string == null) {
            return null;
        }
        string = string.toLowerCase().trim();
        String[] stringArray = Config.tokenize(string, " ");
        Object[] objectArray = (Object[])Array.newInstance(TArray.getClass().getComponentType(), stringArray.length);
        for (int i = 0; i < stringArray.length; ++i) {
            String string3 = stringArray[i];
            T t = this.parseObject(string3, TArray, iNameGetter, string2);
            if (t == null) {
                return TArray2;
            }
            objectArray[i] = t;
        }
        return objectArray;
    }

    public Enum parseEnum(String string, Enum[] enumArray, String string2) {
        return this.parseObject(string, enumArray, NAME_GETTER_ENUM, string2);
    }

    public Enum[] parseEnums(String string, Enum[] enumArray, String string2, Enum[] enumArray2) {
        return this.parseObjects(string, enumArray, NAME_GETTER_ENUM, string2, enumArray2);
    }

    public DyeColor[] parseDyeColors(String string, String string2, DyeColor[] dyeColorArray) {
        return this.parseObjects(string, DyeColor.values(), NAME_GETTER_DYE_COLOR, string2, dyeColorArray);
    }

    public Weather[] parseWeather(String string, String string2, Weather[] weatherArray) {
        return this.parseObjects(string, Weather.values(), NAME_GETTER_ENUM, string2, weatherArray);
    }

    public NbtTagValue parseNbtTagValue(String string, String string2) {
        return string != null && string2 != null ? new NbtTagValue(string, string2) : null;
    }

    public MatchProfession[] parseProfessions(String string) {
        if (string == null) {
            return null;
        }
        ArrayList<MatchProfession> arrayList = new ArrayList<MatchProfession>();
        String[] stringArray = Config.tokenize(string, " ");
        for (int i = 0; i < stringArray.length; ++i) {
            String string2 = stringArray[i];
            MatchProfession matchProfession = this.parseProfession(string2);
            if (matchProfession == null) {
                this.warn("Invalid profession: " + string2);
                return PROFESSIONS_INVALID;
            }
            arrayList.add(matchProfession);
        }
        return arrayList.isEmpty() ? null : arrayList.toArray(new MatchProfession[arrayList.size()]);
    }

    private MatchProfession parseProfession(String string) {
        Object object;
        Object object2;
        Object object3 = string;
        Object object4 = null;
        int n = string.lastIndexOf(58);
        if (n >= 0) {
            object2 = string.substring(0, n);
            object = string.substring(n + 1);
            if (((String)object).isEmpty() || ((String)object).matches("[0-9].*")) {
                object3 = object2;
                object4 = object;
            }
        }
        if ((object2 = this.parseVillagerProfession((String)object3)) == null) {
            return null;
        }
        object = this.parseIntList((String)object4);
        return new MatchProfession((VillagerProfession)object2, (int[])object);
    }

    private VillagerProfession parseVillagerProfession(String string) {
        if (string == null) {
            return null;
        }
        DefaultedRegistry<VillagerProfession> defaultedRegistry = Registry.VILLAGER_PROFESSION;
        ResourceLocation resourceLocation = new ResourceLocation(string = string.toLowerCase());
        return !((Registry)defaultedRegistry).containsKey(resourceLocation) ? null : (VillagerProfession)((Registry)defaultedRegistry).getOrDefault(resourceLocation);
    }

    public int[] parseItems(String string) {
        string = string.trim();
        TreeSet<Integer> treeSet = new TreeSet<Integer>();
        String[] stringArray = Config.tokenize(string, " ");
        for (int i = 0; i < stringArray.length; ++i) {
            String string2 = stringArray[i];
            ResourceLocation resourceLocation = new ResourceLocation(string2);
            Item item = ItemUtils.getItem(resourceLocation);
            if (item == null) {
                this.warn("Item not found: " + string2);
                continue;
            }
            int n = ItemUtils.getId(item);
            if (n < 0) {
                this.warn("Item has no ID: " + item + ", name: " + string2);
                continue;
            }
            treeSet.add(new Integer(n));
        }
        Integer[] integerArray = treeSet.toArray(new Integer[treeSet.size()]);
        return Config.toPrimitive(integerArray);
    }

    public int[] parseEntities(String string) {
        string = string.trim();
        TreeSet<Integer> treeSet = new TreeSet<Integer>();
        String[] stringArray = Config.tokenize(string, " ");
        for (int i = 0; i < stringArray.length; ++i) {
            String string2 = stringArray[i];
            ResourceLocation resourceLocation = new ResourceLocation(string2);
            EntityType entityType = EntityTypeUtils.getEntityType(resourceLocation);
            if (entityType == null) {
                this.warn("Entity not found: " + string2);
                continue;
            }
            int n = Registry.ENTITY_TYPE.getId(entityType);
            if (n < 0) {
                this.warn("Entity has no ID: " + entityType + ", name: " + string2);
                continue;
            }
            treeSet.add(new Integer(n));
        }
        Integer[] integerArray = treeSet.toArray(new Integer[treeSet.size()]);
        return Config.toPrimitive(integerArray);
    }
}

