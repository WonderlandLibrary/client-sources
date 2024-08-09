/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicLike;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.OptionalDynamic;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.util.datafix.TypeReferences;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.mutable.MutableInt;

public class WorldGenSettings
extends DataFix {
    private static final ImmutableMap<String, StructureSeparationSettingsCodec> field_233422_a_ = ImmutableMap.builder().put("minecraft:village", new StructureSeparationSettingsCodec(32, 8, 10387312)).put("minecraft:desert_pyramid", new StructureSeparationSettingsCodec(32, 8, 14357617)).put("minecraft:igloo", new StructureSeparationSettingsCodec(32, 8, 14357618)).put("minecraft:jungle_pyramid", new StructureSeparationSettingsCodec(32, 8, 14357619)).put("minecraft:swamp_hut", new StructureSeparationSettingsCodec(32, 8, 14357620)).put("minecraft:pillager_outpost", new StructureSeparationSettingsCodec(32, 8, 165745296)).put("minecraft:monument", new StructureSeparationSettingsCodec(32, 5, 10387313)).put("minecraft:endcity", new StructureSeparationSettingsCodec(20, 11, 10387313)).put("minecraft:mansion", new StructureSeparationSettingsCodec(80, 20, 10387319)).build();

    public WorldGenSettings(Schema schema) {
        super(schema, true);
    }

    @Override
    protected TypeRewriteRule makeRule() {
        return this.fixTypeEverywhereTyped("WorldGenSettings building", this.getInputSchema().getType(TypeReferences.WORLD_GEN_SETTINGS), WorldGenSettings::lambda$makeRule$0);
    }

    private static <T> Dynamic<T> func_233423_a_(long l, DynamicLike<T> dynamicLike, Dynamic<T> dynamic, Dynamic<T> dynamic2) {
        return dynamicLike.createMap(ImmutableMap.of(dynamicLike.createString("type"), dynamicLike.createString("minecraft:noise"), dynamicLike.createString("biome_source"), dynamic2, dynamicLike.createString("seed"), dynamicLike.createLong(l), dynamicLike.createString("settings"), dynamic));
    }

    private static <T> Dynamic<T> func_233427_a_(Dynamic<T> dynamic, long l, boolean bl, boolean bl2) {
        ImmutableMap.Builder builder = ImmutableMap.builder().put(dynamic.createString("type"), dynamic.createString("minecraft:vanilla_layered")).put(dynamic.createString("seed"), dynamic.createLong(l)).put(dynamic.createString("large_biomes"), dynamic.createBoolean(bl2));
        if (bl) {
            builder.put(dynamic.createString("legacy_biome_init_layer"), dynamic.createBoolean(bl));
        }
        return dynamic.createMap(builder.build());
    }

    private static <T> Dynamic<T> func_233426_a_(Dynamic<T> dynamic) {
        Object object;
        Dynamic<T> dynamic2;
        DynamicOps dynamicOps = dynamic.getOps();
        long l = dynamic.get("RandomSeed").asLong(0L);
        Optional<String> optional = dynamic.get("generatorName").asString().map(WorldGenSettings::lambda$func_233426_a_$1).result();
        Optional optional2 = dynamic.get("legacy_custom_options").asString().result().map(Optional::of).orElseGet(() -> WorldGenSettings.lambda$func_233426_a_$2(optional, dynamic));
        boolean bl = false;
        if (optional.equals(Optional.of("customized"))) {
            dynamic2 = WorldGenSettings.func_241322_a_(dynamic, l);
        } else if (!optional.isPresent()) {
            dynamic2 = WorldGenSettings.func_241322_a_(dynamic, l);
        } else {
            switch (optional.get()) {
                case "flat": {
                    object = dynamic.get("generatorOptions");
                    Map map = WorldGenSettings.func_233430_a_(dynamicOps, object);
                    dynamic2 = dynamic.createMap(ImmutableMap.of(dynamic.createString("type"), dynamic.createString("minecraft:flat"), dynamic.createString("settings"), dynamic.createMap(ImmutableMap.of(dynamic.createString("structures"), dynamic.createMap(map), dynamic.createString("layers"), ((OptionalDynamic)object).get("layers").result().orElseGet(() -> WorldGenSettings.lambda$func_233426_a_$3(dynamic)), dynamic.createString("biome"), dynamic.createString(((OptionalDynamic)object).get("biome").asString("minecraft:plains"))))));
                    break;
                }
                case "debug_all_block_states": {
                    dynamic2 = dynamic.createMap(ImmutableMap.of(dynamic.createString("type"), dynamic.createString("minecraft:debug")));
                    break;
                }
                case "buffet": {
                    Dynamic dynamic3;
                    Dynamic dynamic4;
                    OptionalDynamic<T> optionalDynamic = dynamic.get("generatorOptions");
                    OptionalDynamic<T> optionalDynamic2 = optionalDynamic.get("chunk_generator");
                    Optional<String> optional3 = optionalDynamic2.get("type").asString().result();
                    if (Objects.equals(optional3, Optional.of("minecraft:caves"))) {
                        dynamic4 = dynamic.createString("minecraft:caves");
                        bl = true;
                    } else {
                        dynamic4 = Objects.equals(optional3, Optional.of("minecraft:floating_islands")) ? dynamic.createString("minecraft:floating_islands") : dynamic.createString("minecraft:overworld");
                    }
                    Dynamic dynamic5 = optionalDynamic.get("biome_source").result().orElseGet(() -> WorldGenSettings.lambda$func_233426_a_$4(dynamic));
                    if (dynamic5.get("type").asString().result().equals(Optional.of("minecraft:fixed"))) {
                        String string = dynamic5.get("options").get("biomes").asStream().findFirst().flatMap(WorldGenSettings::lambda$func_233426_a_$5).orElse("minecraft:ocean");
                        dynamic3 = dynamic5.remove("options").set("biome", dynamic.createString(string));
                    } else {
                        dynamic3 = dynamic5;
                    }
                    dynamic2 = WorldGenSettings.func_233423_a_(l, dynamic, dynamic4, dynamic3);
                    break;
                }
                default: {
                    boolean bl2 = optional.get().equals("default");
                    boolean bl3 = optional.get().equals("default_1_1") || bl2 && dynamic.get("generatorVersion").asInt(0) == 0;
                    boolean bl4 = optional.get().equals("amplified");
                    boolean bl5 = optional.get().equals("largebiomes");
                    dynamic2 = WorldGenSettings.func_233423_a_(l, dynamic, dynamic.createString(bl4 ? "minecraft:amplified" : "minecraft:overworld"), WorldGenSettings.func_233427_a_(dynamic, l, bl3, bl5));
                }
            }
        }
        boolean bl6 = dynamic.get("MapFeatures").asBoolean(false);
        boolean bl7 = dynamic.get("BonusChest").asBoolean(true);
        object = ImmutableMap.builder();
        ((ImmutableMap.Builder)object).put(dynamicOps.createString("seed"), dynamicOps.createLong(l));
        ((ImmutableMap.Builder)object).put(dynamicOps.createString("generate_features"), dynamicOps.createBoolean(bl6));
        ((ImmutableMap.Builder)object).put(dynamicOps.createString("bonus_chest"), dynamicOps.createBoolean(bl7));
        ((ImmutableMap.Builder)object).put(dynamicOps.createString("dimensions"), WorldGenSettings.func_241323_a_(dynamic, l, dynamic2, bl));
        optional2.ifPresent(arg_0 -> WorldGenSettings.lambda$func_233426_a_$6((ImmutableMap.Builder)object, dynamicOps, arg_0));
        return new Dynamic(dynamicOps, dynamicOps.createMap(((ImmutableMap.Builder)object).build()));
    }

    protected static <T> Dynamic<T> func_241322_a_(Dynamic<T> dynamic, long l) {
        return WorldGenSettings.func_233423_a_(l, dynamic, dynamic.createString("minecraft:overworld"), WorldGenSettings.func_233427_a_(dynamic, l, false, false));
    }

    protected static <T> T func_241323_a_(Dynamic<T> dynamic, long l, Dynamic<T> dynamic2, boolean bl) {
        DynamicOps dynamicOps = dynamic.getOps();
        return dynamicOps.createMap(ImmutableMap.of(dynamicOps.createString("minecraft:overworld"), dynamicOps.createMap(ImmutableMap.of(dynamicOps.createString("type"), dynamicOps.createString("minecraft:overworld" + (bl ? "_caves" : "")), dynamicOps.createString("generator"), dynamic2.getValue())), dynamicOps.createString("minecraft:the_nether"), dynamicOps.createMap(ImmutableMap.of(dynamicOps.createString("type"), dynamicOps.createString("minecraft:the_nether"), dynamicOps.createString("generator"), WorldGenSettings.func_233423_a_(l, dynamic, dynamic.createString("minecraft:nether"), dynamic.createMap(ImmutableMap.of(dynamic.createString("type"), dynamic.createString("minecraft:multi_noise"), dynamic.createString("seed"), dynamic.createLong(l), dynamic.createString("preset"), dynamic.createString("minecraft:nether")))).getValue())), dynamicOps.createString("minecraft:the_end"), dynamicOps.createMap(ImmutableMap.of(dynamicOps.createString("type"), dynamicOps.createString("minecraft:the_end"), dynamicOps.createString("generator"), WorldGenSettings.func_233423_a_(l, dynamic, dynamic.createString("minecraft:end"), dynamic.createMap(ImmutableMap.of(dynamic.createString("type"), dynamic.createString("minecraft:the_end"), dynamic.createString("seed"), dynamic.createLong(l)))).getValue()))));
    }

    private static <T> Map<Dynamic<T>, Dynamic<T>> func_233430_a_(DynamicOps<T> dynamicOps, OptionalDynamic<T> optionalDynamic) {
        MutableInt mutableInt = new MutableInt(32);
        MutableInt mutableInt2 = new MutableInt(3);
        MutableInt mutableInt3 = new MutableInt(128);
        MutableBoolean mutableBoolean = new MutableBoolean(false);
        HashMap<String, StructureSeparationSettingsCodec> hashMap = Maps.newHashMap();
        if (!optionalDynamic.result().isPresent()) {
            mutableBoolean.setTrue();
            hashMap.put("minecraft:village", field_233422_a_.get("minecraft:village"));
        }
        optionalDynamic.get("structures").flatMap(Dynamic::getMapValues).result().ifPresent(arg_0 -> WorldGenSettings.lambda$func_233430_a_$10(mutableBoolean, mutableInt, mutableInt2, mutableInt3, hashMap, arg_0));
        ImmutableMap.Builder builder = ImmutableMap.builder();
        builder.put(optionalDynamic.createString("structures"), optionalDynamic.createMap(hashMap.entrySet().stream().collect(Collectors.toMap(arg_0 -> WorldGenSettings.lambda$func_233430_a_$11(optionalDynamic, arg_0), arg_0 -> WorldGenSettings.lambda$func_233430_a_$12(dynamicOps, arg_0)))));
        if (mutableBoolean.isTrue()) {
            builder.put(optionalDynamic.createString("stronghold"), optionalDynamic.createMap(ImmutableMap.of(optionalDynamic.createString("distance"), optionalDynamic.createInt(mutableInt.getValue()), optionalDynamic.createString("spread"), optionalDynamic.createInt(mutableInt2.getValue()), optionalDynamic.createString("count"), optionalDynamic.createInt(mutableInt3.getValue()))));
        }
        return builder.build();
    }

    private static int func_233434_a_(String string, int n) {
        return NumberUtils.toInt(string, n);
    }

    private static int func_233435_a_(String string, int n, int n2) {
        return Math.max(n2, WorldGenSettings.func_233434_a_(string, n));
    }

    private static void func_233436_a_(Map<String, StructureSeparationSettingsCodec> map, String string, String string2, int n) {
        StructureSeparationSettingsCodec structureSeparationSettingsCodec = map.getOrDefault(string, field_233422_a_.get(string));
        int n2 = WorldGenSettings.func_233435_a_(string2, structureSeparationSettingsCodec.field_233443_b_, n);
        map.put(string, new StructureSeparationSettingsCodec(n2, structureSeparationSettingsCodec.field_233444_c_, structureSeparationSettingsCodec.field_233445_d_));
    }

    private static Dynamic lambda$func_233430_a_$12(DynamicOps dynamicOps, Map.Entry entry) {
        return ((StructureSeparationSettingsCodec)entry.getValue()).func_233447_a_(dynamicOps);
    }

    private static Dynamic lambda$func_233430_a_$11(OptionalDynamic optionalDynamic, Map.Entry entry) {
        return optionalDynamic.createString((String)entry.getKey());
    }

    private static void lambda$func_233430_a_$10(MutableBoolean mutableBoolean, MutableInt mutableInt, MutableInt mutableInt2, MutableInt mutableInt3, Map map, Map map2) {
        map2.forEach((arg_0, arg_1) -> WorldGenSettings.lambda$func_233430_a_$9(mutableBoolean, mutableInt, mutableInt2, mutableInt3, map, arg_0, arg_1));
    }

    private static void lambda$func_233430_a_$9(MutableBoolean mutableBoolean, MutableInt mutableInt, MutableInt mutableInt2, MutableInt mutableInt3, Map map, Dynamic dynamic, Dynamic dynamic2) {
        dynamic2.getMapValues().result().ifPresent(arg_0 -> WorldGenSettings.lambda$func_233430_a_$8(dynamic, mutableBoolean, mutableInt, mutableInt2, mutableInt3, map, arg_0));
    }

    private static void lambda$func_233430_a_$8(Dynamic dynamic, MutableBoolean mutableBoolean, MutableInt mutableInt, MutableInt mutableInt2, MutableInt mutableInt3, Map map, Map map2) {
        map2.forEach((arg_0, arg_1) -> WorldGenSettings.lambda$func_233430_a_$7(dynamic, mutableBoolean, mutableInt, mutableInt2, mutableInt3, map, arg_0, arg_1));
    }

    private static void lambda$func_233430_a_$7(Dynamic dynamic, MutableBoolean mutableBoolean, MutableInt mutableInt, MutableInt mutableInt2, MutableInt mutableInt3, Map map, Dynamic dynamic2, Dynamic dynamic3) {
        String string = dynamic.asString("");
        String string2 = dynamic2.asString("");
        String string3 = dynamic3.asString("");
        if ("stronghold".equals(string)) {
            mutableBoolean.setTrue();
            int n = -1;
            switch (string2.hashCode()) {
                case -895684237: {
                    if (!string2.equals("spread")) break;
                    n = 1;
                    break;
                }
                case 94851343: {
                    if (!string2.equals("count")) break;
                    n = 2;
                    break;
                }
                case 288459765: {
                    if (!string2.equals("distance")) break;
                    n = 0;
                }
            }
            switch (n) {
                case 0: {
                    mutableInt.setValue(WorldGenSettings.func_233435_a_(string3, mutableInt.getValue(), 1));
                    return;
                }
                case 1: {
                    mutableInt2.setValue(WorldGenSettings.func_233435_a_(string3, mutableInt2.getValue(), 1));
                    return;
                }
                case 2: {
                    mutableInt3.setValue(WorldGenSettings.func_233435_a_(string3, mutableInt3.getValue(), 1));
                    return;
                }
            }
        } else {
            int n = -1;
            switch (string2.hashCode()) {
                case -2116852922: {
                    if (!string2.equals("separation")) break;
                    n = 1;
                    break;
                }
                case -2012158909: {
                    if (!string2.equals("spacing")) break;
                    n = 2;
                    break;
                }
                case 288459765: {
                    if (!string2.equals("distance")) break;
                    n = 0;
                }
            }
            switch (n) {
                case 0: {
                    int n2 = -1;
                    switch (string.hashCode()) {
                        case -1606796090: {
                            if (!string.equals("endcity")) break;
                            n2 = 2;
                            break;
                        }
                        case -107033518: {
                            if (!string.equals("biome_1")) break;
                            n2 = 1;
                            break;
                        }
                        case 460367020: {
                            if (!string.equals("village")) break;
                            n2 = 0;
                            break;
                        }
                        case 835798799: {
                            if (!string.equals("mansion")) break;
                            n2 = 3;
                        }
                    }
                    switch (n2) {
                        case 0: {
                            WorldGenSettings.func_233436_a_(map, "minecraft:village", string3, 9);
                            return;
                        }
                        case 1: {
                            WorldGenSettings.func_233436_a_(map, "minecraft:desert_pyramid", string3, 9);
                            WorldGenSettings.func_233436_a_(map, "minecraft:igloo", string3, 9);
                            WorldGenSettings.func_233436_a_(map, "minecraft:jungle_pyramid", string3, 9);
                            WorldGenSettings.func_233436_a_(map, "minecraft:swamp_hut", string3, 9);
                            WorldGenSettings.func_233436_a_(map, "minecraft:pillager_outpost", string3, 9);
                            return;
                        }
                        case 2: {
                            WorldGenSettings.func_233436_a_(map, "minecraft:endcity", string3, 1);
                            return;
                        }
                        case 3: {
                            WorldGenSettings.func_233436_a_(map, "minecraft:mansion", string3, 1);
                            return;
                        }
                    }
                    return;
                }
                case 1: {
                    if ("oceanmonument".equals(string)) {
                        StructureSeparationSettingsCodec structureSeparationSettingsCodec = map.getOrDefault("minecraft:monument", field_233422_a_.get("minecraft:monument"));
                        int n3 = WorldGenSettings.func_233435_a_(string3, structureSeparationSettingsCodec.field_233444_c_, 1);
                        map.put("minecraft:monument", new StructureSeparationSettingsCodec(n3, structureSeparationSettingsCodec.field_233444_c_, structureSeparationSettingsCodec.field_233445_d_));
                    }
                    return;
                }
                case 2: {
                    if ("oceanmonument".equals(string)) {
                        WorldGenSettings.func_233436_a_(map, "minecraft:monument", string3, 1);
                    }
                    return;
                }
            }
        }
    }

    private static void lambda$func_233426_a_$6(ImmutableMap.Builder builder, DynamicOps dynamicOps, String string) {
        builder.put(dynamicOps.createString("legacy_custom_options"), dynamicOps.createString(string));
    }

    private static Optional lambda$func_233426_a_$5(Dynamic dynamic) {
        return dynamic.asString().result();
    }

    private static Dynamic lambda$func_233426_a_$4(Dynamic dynamic) {
        return dynamic.createMap(ImmutableMap.of(dynamic.createString("type"), dynamic.createString("minecraft:fixed")));
    }

    private static Dynamic lambda$func_233426_a_$3(Dynamic dynamic) {
        return dynamic.createList(Stream.of(dynamic.createMap(ImmutableMap.of(dynamic.createString("height"), dynamic.createInt(1), dynamic.createString("block"), dynamic.createString("minecraft:bedrock"))), dynamic.createMap(ImmutableMap.of(dynamic.createString("height"), dynamic.createInt(2), dynamic.createString("block"), dynamic.createString("minecraft:dirt"))), dynamic.createMap(ImmutableMap.of(dynamic.createString("height"), dynamic.createInt(1), dynamic.createString("block"), dynamic.createString("minecraft:grass_block")))));
    }

    private static Optional lambda$func_233426_a_$2(Optional optional, Dynamic dynamic) {
        return optional.equals(Optional.of("customized")) ? dynamic.get("generatorOptions").asString().result() : Optional.empty();
    }

    private static String lambda$func_233426_a_$1(String string) {
        return string.toLowerCase(Locale.ROOT);
    }

    private static Typed lambda$makeRule$0(Typed typed) {
        return typed.update(DSL.remainderFinder(), WorldGenSettings::func_233426_a_);
    }

    static final class StructureSeparationSettingsCodec {
        public static final Codec<StructureSeparationSettingsCodec> field_233442_a_ = RecordCodecBuilder.create(StructureSeparationSettingsCodec::lambda$static$3);
        private final int field_233443_b_;
        private final int field_233444_c_;
        private final int field_233445_d_;

        public StructureSeparationSettingsCodec(int n, int n2, int n3) {
            this.field_233443_b_ = n;
            this.field_233444_c_ = n2;
            this.field_233445_d_ = n3;
        }

        public <T> Dynamic<T> func_233447_a_(DynamicOps<T> dynamicOps) {
            return new Dynamic<T>(dynamicOps, field_233442_a_.encodeStart(dynamicOps, this).result().orElse(dynamicOps.emptyMap()));
        }

        private static App lambda$static$3(RecordCodecBuilder.Instance instance) {
            return instance.group(((MapCodec)Codec.INT.fieldOf("spacing")).forGetter(StructureSeparationSettingsCodec::lambda$static$0), ((MapCodec)Codec.INT.fieldOf("separation")).forGetter(StructureSeparationSettingsCodec::lambda$static$1), ((MapCodec)Codec.INT.fieldOf("salt")).forGetter(StructureSeparationSettingsCodec::lambda$static$2)).apply(instance, StructureSeparationSettingsCodec::new);
        }

        private static Integer lambda$static$2(StructureSeparationSettingsCodec structureSeparationSettingsCodec) {
            return structureSeparationSettingsCodec.field_233445_d_;
        }

        private static Integer lambda$static$1(StructureSeparationSettingsCodec structureSeparationSettingsCodec) {
            return structureSeparationSettingsCodec.field_233444_c_;
        }

        private static Integer lambda$static$0(StructureSeparationSettingsCodec structureSeparationSettingsCodec) {
            return structureSeparationSettingsCodec.field_233443_b_;
        }
    }
}

