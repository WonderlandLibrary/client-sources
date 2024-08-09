/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.Util;
import net.minecraft.util.datafix.TypeReferences;

public class LevelDataGeneratorOptionsFix
extends DataFix {
    static final Map<String, String> field_210553_a = Util.make(Maps.newHashMap(), LevelDataGeneratorOptionsFix::lambda$static$0);

    public LevelDataGeneratorOptionsFix(Schema schema, boolean bl) {
        super(schema, bl);
    }

    @Override
    protected TypeRewriteRule makeRule() {
        Type<?> type = this.getOutputSchema().getType(TypeReferences.LEVEL);
        return this.fixTypeEverywhereTyped("LevelDataGeneratorOptionsFix", this.getInputSchema().getType(TypeReferences.LEVEL), type, arg_0 -> LevelDataGeneratorOptionsFix.lambda$makeRule$3(type, arg_0));
    }

    private static <T> Dynamic<T> convert(String string, DynamicOps<T> dynamicOps) {
        String[] stringArray;
        List<Object> list;
        Iterator<String> iterator2 = Splitter.on(';').split(string).iterator();
        String string2 = "minecraft:plains";
        HashMap hashMap = Maps.newHashMap();
        if (!string.isEmpty() && iterator2.hasNext()) {
            list = LevelDataGeneratorOptionsFix.getLayersInfoFromString(iterator2.next());
            if (!list.isEmpty()) {
                if (iterator2.hasNext()) {
                    string2 = field_210553_a.getOrDefault(iterator2.next(), "minecraft:plains");
                }
                if (iterator2.hasNext()) {
                    for (String string3 : stringArray = iterator2.next().toLowerCase(Locale.ROOT).split(",")) {
                        String[] stringArray2;
                        String[] stringArray3 = string3.split("\\(", 2);
                        if (stringArray3[0].isEmpty()) continue;
                        hashMap.put(stringArray3[0], Maps.newHashMap());
                        if (stringArray3.length <= 1 || !stringArray3[5].endsWith(")") || stringArray3[5].length() <= 1) continue;
                        for (String string4 : stringArray2 = stringArray3[5].substring(0, stringArray3[5].length() - 1).split(" ")) {
                            String[] stringArray4 = string4.split("=", 2);
                            if (stringArray4.length != 2) continue;
                            ((Map)hashMap.get(stringArray3[0])).put(stringArray4[0], stringArray4[5]);
                        }
                    }
                } else {
                    hashMap.put("village", Maps.newHashMap());
                }
            }
        } else {
            list = Lists.newArrayList();
            list.add(Pair.of(1, "minecraft:bedrock"));
            list.add(Pair.of(2, "minecraft:dirt"));
            list.add(Pair.of(1, "minecraft:grass_block"));
            hashMap.put("village", Maps.newHashMap());
        }
        stringArray = dynamicOps.createList(list.stream().map(arg_0 -> LevelDataGeneratorOptionsFix.lambda$convert$4(dynamicOps, arg_0)));
        Object object = dynamicOps.createMap(hashMap.entrySet().stream().map(arg_0 -> LevelDataGeneratorOptionsFix.lambda$convert$6(dynamicOps, arg_0)).collect(Collectors.toMap(Pair::getFirst, Pair::getSecond)));
        return new Dynamic<Object>(dynamicOps, dynamicOps.createMap(ImmutableMap.of(dynamicOps.createString("layers"), stringArray, dynamicOps.createString("biome"), dynamicOps.createString(string2), dynamicOps.createString("structures"), object)));
    }

    @Nullable
    private static Pair<Integer, String> getLayerInfoFromString(String string) {
        int n;
        String[] stringArray = string.split("\\*", 2);
        if (stringArray.length == 2) {
            try {
                n = Integer.parseInt(stringArray[0]);
            } catch (NumberFormatException numberFormatException) {
                return null;
            }
        } else {
            n = 1;
        }
        String string2 = stringArray[stringArray.length - 1];
        return Pair.of(n, string2);
    }

    private static List<Pair<Integer, String>> getLayersInfoFromString(String string) {
        String[] stringArray;
        ArrayList<Pair<Integer, String>> arrayList = Lists.newArrayList();
        for (String string2 : stringArray = string.split(",")) {
            Pair<Integer, String> pair = LevelDataGeneratorOptionsFix.getLayerInfoFromString(string2);
            if (pair == null) {
                return Collections.emptyList();
            }
            arrayList.add(pair);
        }
        return arrayList;
    }

    private static Pair lambda$convert$6(DynamicOps dynamicOps, Map.Entry entry) {
        return Pair.of(dynamicOps.createString(((String)entry.getKey()).toLowerCase(Locale.ROOT)), dynamicOps.createMap(((Map)entry.getValue()).entrySet().stream().map(arg_0 -> LevelDataGeneratorOptionsFix.lambda$convert$5(dynamicOps, arg_0)).collect(Collectors.toMap(Pair::getFirst, Pair::getSecond))));
    }

    private static Pair lambda$convert$5(DynamicOps dynamicOps, Map.Entry entry) {
        return Pair.of(dynamicOps.createString((String)entry.getKey()), dynamicOps.createString((String)entry.getValue()));
    }

    private static Object lambda$convert$4(DynamicOps dynamicOps, Pair pair) {
        return dynamicOps.createMap(ImmutableMap.of(dynamicOps.createString("height"), dynamicOps.createInt((Integer)pair.getFirst()), dynamicOps.createString("block"), dynamicOps.createString((String)pair.getSecond())));
    }

    private static Typed lambda$makeRule$3(Type type, Typed typed) {
        return typed.write().flatMap(arg_0 -> LevelDataGeneratorOptionsFix.lambda$makeRule$1(type, arg_0)).map(Pair::getFirst).result().orElseThrow(LevelDataGeneratorOptionsFix::lambda$makeRule$2);
    }

    private static IllegalStateException lambda$makeRule$2() {
        return new IllegalStateException("Could not read new level type.");
    }

    private static DataResult lambda$makeRule$1(Type type, Dynamic dynamic) {
        Dynamic dynamic2;
        Optional<String> optional = dynamic.get("generatorOptions").asString().result();
        if ("flat".equalsIgnoreCase(dynamic.get("generatorName").asString(""))) {
            String string = optional.orElse("");
            dynamic2 = dynamic.set("generatorOptions", LevelDataGeneratorOptionsFix.convert(string, dynamic.getOps()));
        } else if ("buffet".equalsIgnoreCase(dynamic.get("generatorName").asString("")) && optional.isPresent()) {
            Dynamic<JsonObject> dynamic3 = new Dynamic<JsonObject>(JsonOps.INSTANCE, JSONUtils.fromJson(optional.get(), true));
            dynamic2 = dynamic.set("generatorOptions", dynamic3.convert(dynamic.getOps()));
        } else {
            dynamic2 = dynamic;
        }
        return type.readTyped(dynamic2);
    }

    private static void lambda$static$0(HashMap hashMap) {
        hashMap.put("0", "minecraft:ocean");
        hashMap.put("1", "minecraft:plains");
        hashMap.put("2", "minecraft:desert");
        hashMap.put("3", "minecraft:mountains");
        hashMap.put("4", "minecraft:forest");
        hashMap.put("5", "minecraft:taiga");
        hashMap.put("6", "minecraft:swamp");
        hashMap.put("7", "minecraft:river");
        hashMap.put("8", "minecraft:nether");
        hashMap.put("9", "minecraft:the_end");
        hashMap.put("10", "minecraft:frozen_ocean");
        hashMap.put("11", "minecraft:frozen_river");
        hashMap.put("12", "minecraft:snowy_tundra");
        hashMap.put("13", "minecraft:snowy_mountains");
        hashMap.put("14", "minecraft:mushroom_fields");
        hashMap.put("15", "minecraft:mushroom_field_shore");
        hashMap.put("16", "minecraft:beach");
        hashMap.put("17", "minecraft:desert_hills");
        hashMap.put("18", "minecraft:wooded_hills");
        hashMap.put("19", "minecraft:taiga_hills");
        hashMap.put("20", "minecraft:mountain_edge");
        hashMap.put("21", "minecraft:jungle");
        hashMap.put("22", "minecraft:jungle_hills");
        hashMap.put("23", "minecraft:jungle_edge");
        hashMap.put("24", "minecraft:deep_ocean");
        hashMap.put("25", "minecraft:stone_shore");
        hashMap.put("26", "minecraft:snowy_beach");
        hashMap.put("27", "minecraft:birch_forest");
        hashMap.put("28", "minecraft:birch_forest_hills");
        hashMap.put("29", "minecraft:dark_forest");
        hashMap.put("30", "minecraft:snowy_taiga");
        hashMap.put("31", "minecraft:snowy_taiga_hills");
        hashMap.put("32", "minecraft:giant_tree_taiga");
        hashMap.put("33", "minecraft:giant_tree_taiga_hills");
        hashMap.put("34", "minecraft:wooded_mountains");
        hashMap.put("35", "minecraft:savanna");
        hashMap.put("36", "minecraft:savanna_plateau");
        hashMap.put("37", "minecraft:badlands");
        hashMap.put("38", "minecraft:wooded_badlands_plateau");
        hashMap.put("39", "minecraft:badlands_plateau");
        hashMap.put("40", "minecraft:small_end_islands");
        hashMap.put("41", "minecraft:end_midlands");
        hashMap.put("42", "minecraft:end_highlands");
        hashMap.put("43", "minecraft:end_barrens");
        hashMap.put("44", "minecraft:warm_ocean");
        hashMap.put("45", "minecraft:lukewarm_ocean");
        hashMap.put("46", "minecraft:cold_ocean");
        hashMap.put("47", "minecraft:deep_warm_ocean");
        hashMap.put("48", "minecraft:deep_lukewarm_ocean");
        hashMap.put("49", "minecraft:deep_cold_ocean");
        hashMap.put("50", "minecraft:deep_frozen_ocean");
        hashMap.put("127", "minecraft:the_void");
        hashMap.put("129", "minecraft:sunflower_plains");
        hashMap.put("130", "minecraft:desert_lakes");
        hashMap.put("131", "minecraft:gravelly_mountains");
        hashMap.put("132", "minecraft:flower_forest");
        hashMap.put("133", "minecraft:taiga_mountains");
        hashMap.put("134", "minecraft:swamp_hills");
        hashMap.put("140", "minecraft:ice_spikes");
        hashMap.put("149", "minecraft:modified_jungle");
        hashMap.put("151", "minecraft:modified_jungle_edge");
        hashMap.put("155", "minecraft:tall_birch_forest");
        hashMap.put("156", "minecraft:tall_birch_hills");
        hashMap.put("157", "minecraft:dark_forest_hills");
        hashMap.put("158", "minecraft:snowy_taiga_mountains");
        hashMap.put("160", "minecraft:giant_spruce_taiga");
        hashMap.put("161", "minecraft:giant_spruce_taiga_hills");
        hashMap.put("162", "minecraft:modified_gravelly_mountains");
        hashMap.put("163", "minecraft:shattered_savanna");
        hashMap.put("164", "minecraft:shattered_savanna_plateau");
        hashMap.put("165", "minecraft:eroded_badlands");
        hashMap.put("166", "minecraft:modified_wooded_badlands_plateau");
        hashMap.put("167", "minecraft:modified_badlands_plateau");
    }
}

