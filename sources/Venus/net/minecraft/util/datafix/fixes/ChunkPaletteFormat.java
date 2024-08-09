/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicLike;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntListIterator;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.util.ArbitraryBitLengthIntArray;
import net.minecraft.util.IntIdentityHashBiMap;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.datafix.fixes.BlockStateFlatteningMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChunkPaletteFormat
extends DataFix {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final BitSet VIRTUAL = new BitSet(256);
    private static final BitSet FIX = new BitSet(256);
    private static final Dynamic<?> PUMPKIN = BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:pumpkin'}");
    private static final Dynamic<?> SNOWY_PODZOL = BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:podzol',Properties:{snowy:'true'}}");
    private static final Dynamic<?> SNOWY_GRASS = BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:grass_block',Properties:{snowy:'true'}}");
    private static final Dynamic<?> SNOWY_MYCELIUM = BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:mycelium',Properties:{snowy:'true'}}");
    private static final Dynamic<?> UPPER_SUNFLOWER = BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:sunflower',Properties:{half:'upper'}}");
    private static final Dynamic<?> UPPER_LILAC = BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:lilac',Properties:{half:'upper'}}");
    private static final Dynamic<?> UPPER_TALL_GRASS = BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:tall_grass',Properties:{half:'upper'}}");
    private static final Dynamic<?> UPPER_LARGE_FERN = BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:large_fern',Properties:{half:'upper'}}");
    private static final Dynamic<?> UPPER_ROSE_BUSH = BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:rose_bush',Properties:{half:'upper'}}");
    private static final Dynamic<?> UPPER_PEONY = BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:peony',Properties:{half:'upper'}}");
    private static final Map<String, Dynamic<?>> FLOWER_POT_MAP = DataFixUtils.make(Maps.newHashMap(), ChunkPaletteFormat::lambda$static$0);
    private static final Map<String, Dynamic<?>> SKULL_MAP = DataFixUtils.make(Maps.newHashMap(), ChunkPaletteFormat::lambda$static$1);
    private static final Map<String, Dynamic<?>> DOOR_MAP = DataFixUtils.make(Maps.newHashMap(), ChunkPaletteFormat::lambda$static$2);
    private static final Map<String, Dynamic<?>> NOTE_BLOCK_MAP = DataFixUtils.make(Maps.newHashMap(), ChunkPaletteFormat::lambda$static$3);
    private static final Int2ObjectMap<String> DYE_COLOR_MAP = DataFixUtils.make(new Int2ObjectOpenHashMap(), ChunkPaletteFormat::lambda$static$4);
    private static final Map<String, Dynamic<?>> BED_BLOCK_MAP = DataFixUtils.make(Maps.newHashMap(), ChunkPaletteFormat::lambda$static$5);
    private static final Map<String, Dynamic<?>> BANNER_BLOCK_MAP = DataFixUtils.make(Maps.newHashMap(), ChunkPaletteFormat::lambda$static$6);
    private static final Dynamic<?> AIR = BlockStateFlatteningMap.getFixedNBTForID(0);

    public ChunkPaletteFormat(Schema schema, boolean bl) {
        super(schema, bl);
    }

    private static void mapSkull(Map<String, Dynamic<?>> map, int n, String string, String string2) {
        map.put(n + "north", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "_wall_" + string2 + "',Properties:{facing:'north'}}"));
        map.put(n + "east", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "_wall_" + string2 + "',Properties:{facing:'east'}}"));
        map.put(n + "south", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "_wall_" + string2 + "',Properties:{facing:'south'}}"));
        map.put(n + "west", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "_wall_" + string2 + "',Properties:{facing:'west'}}"));
        for (int i = 0; i < 16; ++i) {
            map.put("" + n + i, BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "_" + string2 + "',Properties:{rotation:'" + i + "'}}"));
        }
    }

    private static void mapDoor(Map<String, Dynamic<?>> map, String string, int n) {
        map.put("minecraft:" + string + "eastlowerleftfalsefalse", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "',Properties:{facing:'east',half:'lower',hinge:'left',open:'false',powered:'false'}}"));
        map.put("minecraft:" + string + "eastlowerleftfalsetrue", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "',Properties:{facing:'east',half:'lower',hinge:'left',open:'false',powered:'true'}}"));
        map.put("minecraft:" + string + "eastlowerlefttruefalse", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "',Properties:{facing:'east',half:'lower',hinge:'left',open:'true',powered:'false'}}"));
        map.put("minecraft:" + string + "eastlowerlefttruetrue", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "',Properties:{facing:'east',half:'lower',hinge:'left',open:'true',powered:'true'}}"));
        map.put("minecraft:" + string + "eastlowerrightfalsefalse", BlockStateFlatteningMap.getFixedNBTForID(n));
        map.put("minecraft:" + string + "eastlowerrightfalsetrue", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "',Properties:{facing:'east',half:'lower',hinge:'right',open:'false',powered:'true'}}"));
        map.put("minecraft:" + string + "eastlowerrighttruefalse", BlockStateFlatteningMap.getFixedNBTForID(n + 4));
        map.put("minecraft:" + string + "eastlowerrighttruetrue", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "',Properties:{facing:'east',half:'lower',hinge:'right',open:'true',powered:'true'}}"));
        map.put("minecraft:" + string + "eastupperleftfalsefalse", BlockStateFlatteningMap.getFixedNBTForID(n + 8));
        map.put("minecraft:" + string + "eastupperleftfalsetrue", BlockStateFlatteningMap.getFixedNBTForID(n + 10));
        map.put("minecraft:" + string + "eastupperlefttruefalse", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "',Properties:{facing:'east',half:'upper',hinge:'left',open:'true',powered:'false'}}"));
        map.put("minecraft:" + string + "eastupperlefttruetrue", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "',Properties:{facing:'east',half:'upper',hinge:'left',open:'true',powered:'true'}}"));
        map.put("minecraft:" + string + "eastupperrightfalsefalse", BlockStateFlatteningMap.getFixedNBTForID(n + 9));
        map.put("minecraft:" + string + "eastupperrightfalsetrue", BlockStateFlatteningMap.getFixedNBTForID(n + 11));
        map.put("minecraft:" + string + "eastupperrighttruefalse", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "',Properties:{facing:'east',half:'upper',hinge:'right',open:'true',powered:'false'}}"));
        map.put("minecraft:" + string + "eastupperrighttruetrue", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "',Properties:{facing:'east',half:'upper',hinge:'right',open:'true',powered:'true'}}"));
        map.put("minecraft:" + string + "northlowerleftfalsefalse", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "',Properties:{facing:'north',half:'lower',hinge:'left',open:'false',powered:'false'}}"));
        map.put("minecraft:" + string + "northlowerleftfalsetrue", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "',Properties:{facing:'north',half:'lower',hinge:'left',open:'false',powered:'true'}}"));
        map.put("minecraft:" + string + "northlowerlefttruefalse", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "',Properties:{facing:'north',half:'lower',hinge:'left',open:'true',powered:'false'}}"));
        map.put("minecraft:" + string + "northlowerlefttruetrue", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "',Properties:{facing:'north',half:'lower',hinge:'left',open:'true',powered:'true'}}"));
        map.put("minecraft:" + string + "northlowerrightfalsefalse", BlockStateFlatteningMap.getFixedNBTForID(n + 3));
        map.put("minecraft:" + string + "northlowerrightfalsetrue", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "',Properties:{facing:'north',half:'lower',hinge:'right',open:'false',powered:'true'}}"));
        map.put("minecraft:" + string + "northlowerrighttruefalse", BlockStateFlatteningMap.getFixedNBTForID(n + 7));
        map.put("minecraft:" + string + "northlowerrighttruetrue", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "',Properties:{facing:'north',half:'lower',hinge:'right',open:'true',powered:'true'}}"));
        map.put("minecraft:" + string + "northupperleftfalsefalse", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "',Properties:{facing:'north',half:'upper',hinge:'left',open:'false',powered:'false'}}"));
        map.put("minecraft:" + string + "northupperleftfalsetrue", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "',Properties:{facing:'north',half:'upper',hinge:'left',open:'false',powered:'true'}}"));
        map.put("minecraft:" + string + "northupperlefttruefalse", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "',Properties:{facing:'north',half:'upper',hinge:'left',open:'true',powered:'false'}}"));
        map.put("minecraft:" + string + "northupperlefttruetrue", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "',Properties:{facing:'north',half:'upper',hinge:'left',open:'true',powered:'true'}}"));
        map.put("minecraft:" + string + "northupperrightfalsefalse", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "',Properties:{facing:'north',half:'upper',hinge:'right',open:'false',powered:'false'}}"));
        map.put("minecraft:" + string + "northupperrightfalsetrue", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "',Properties:{facing:'north',half:'upper',hinge:'right',open:'false',powered:'true'}}"));
        map.put("minecraft:" + string + "northupperrighttruefalse", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "',Properties:{facing:'north',half:'upper',hinge:'right',open:'true',powered:'false'}}"));
        map.put("minecraft:" + string + "northupperrighttruetrue", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "',Properties:{facing:'north',half:'upper',hinge:'right',open:'true',powered:'true'}}"));
        map.put("minecraft:" + string + "southlowerleftfalsefalse", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "',Properties:{facing:'south',half:'lower',hinge:'left',open:'false',powered:'false'}}"));
        map.put("minecraft:" + string + "southlowerleftfalsetrue", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "',Properties:{facing:'south',half:'lower',hinge:'left',open:'false',powered:'true'}}"));
        map.put("minecraft:" + string + "southlowerlefttruefalse", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "',Properties:{facing:'south',half:'lower',hinge:'left',open:'true',powered:'false'}}"));
        map.put("minecraft:" + string + "southlowerlefttruetrue", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "',Properties:{facing:'south',half:'lower',hinge:'left',open:'true',powered:'true'}}"));
        map.put("minecraft:" + string + "southlowerrightfalsefalse", BlockStateFlatteningMap.getFixedNBTForID(n + 1));
        map.put("minecraft:" + string + "southlowerrightfalsetrue", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "',Properties:{facing:'south',half:'lower',hinge:'right',open:'false',powered:'true'}}"));
        map.put("minecraft:" + string + "southlowerrighttruefalse", BlockStateFlatteningMap.getFixedNBTForID(n + 5));
        map.put("minecraft:" + string + "southlowerrighttruetrue", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "',Properties:{facing:'south',half:'lower',hinge:'right',open:'true',powered:'true'}}"));
        map.put("minecraft:" + string + "southupperleftfalsefalse", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "',Properties:{facing:'south',half:'upper',hinge:'left',open:'false',powered:'false'}}"));
        map.put("minecraft:" + string + "southupperleftfalsetrue", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "',Properties:{facing:'south',half:'upper',hinge:'left',open:'false',powered:'true'}}"));
        map.put("minecraft:" + string + "southupperlefttruefalse", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "',Properties:{facing:'south',half:'upper',hinge:'left',open:'true',powered:'false'}}"));
        map.put("minecraft:" + string + "southupperlefttruetrue", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "',Properties:{facing:'south',half:'upper',hinge:'left',open:'true',powered:'true'}}"));
        map.put("minecraft:" + string + "southupperrightfalsefalse", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "',Properties:{facing:'south',half:'upper',hinge:'right',open:'false',powered:'false'}}"));
        map.put("minecraft:" + string + "southupperrightfalsetrue", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "',Properties:{facing:'south',half:'upper',hinge:'right',open:'false',powered:'true'}}"));
        map.put("minecraft:" + string + "southupperrighttruefalse", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "',Properties:{facing:'south',half:'upper',hinge:'right',open:'true',powered:'false'}}"));
        map.put("minecraft:" + string + "southupperrighttruetrue", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "',Properties:{facing:'south',half:'upper',hinge:'right',open:'true',powered:'true'}}"));
        map.put("minecraft:" + string + "westlowerleftfalsefalse", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "',Properties:{facing:'west',half:'lower',hinge:'left',open:'false',powered:'false'}}"));
        map.put("minecraft:" + string + "westlowerleftfalsetrue", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "',Properties:{facing:'west',half:'lower',hinge:'left',open:'false',powered:'true'}}"));
        map.put("minecraft:" + string + "westlowerlefttruefalse", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "',Properties:{facing:'west',half:'lower',hinge:'left',open:'true',powered:'false'}}"));
        map.put("minecraft:" + string + "westlowerlefttruetrue", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "',Properties:{facing:'west',half:'lower',hinge:'left',open:'true',powered:'true'}}"));
        map.put("minecraft:" + string + "westlowerrightfalsefalse", BlockStateFlatteningMap.getFixedNBTForID(n + 2));
        map.put("minecraft:" + string + "westlowerrightfalsetrue", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "',Properties:{facing:'west',half:'lower',hinge:'right',open:'false',powered:'true'}}"));
        map.put("minecraft:" + string + "westlowerrighttruefalse", BlockStateFlatteningMap.getFixedNBTForID(n + 6));
        map.put("minecraft:" + string + "westlowerrighttruetrue", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "',Properties:{facing:'west',half:'lower',hinge:'right',open:'true',powered:'true'}}"));
        map.put("minecraft:" + string + "westupperleftfalsefalse", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "',Properties:{facing:'west',half:'upper',hinge:'left',open:'false',powered:'false'}}"));
        map.put("minecraft:" + string + "westupperleftfalsetrue", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "',Properties:{facing:'west',half:'upper',hinge:'left',open:'false',powered:'true'}}"));
        map.put("minecraft:" + string + "westupperlefttruefalse", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "',Properties:{facing:'west',half:'upper',hinge:'left',open:'true',powered:'false'}}"));
        map.put("minecraft:" + string + "westupperlefttruetrue", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "',Properties:{facing:'west',half:'upper',hinge:'left',open:'true',powered:'true'}}"));
        map.put("minecraft:" + string + "westupperrightfalsefalse", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "',Properties:{facing:'west',half:'upper',hinge:'right',open:'false',powered:'false'}}"));
        map.put("minecraft:" + string + "westupperrightfalsetrue", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "',Properties:{facing:'west',half:'upper',hinge:'right',open:'false',powered:'true'}}"));
        map.put("minecraft:" + string + "westupperrighttruefalse", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "',Properties:{facing:'west',half:'upper',hinge:'right',open:'true',powered:'false'}}"));
        map.put("minecraft:" + string + "westupperrighttruetrue", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "',Properties:{facing:'west',half:'upper',hinge:'right',open:'true',powered:'true'}}"));
    }

    private static void addBeds(Map<String, Dynamic<?>> map, int n, String string) {
        map.put("southfalsefoot" + n, BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "_bed',Properties:{facing:'south',occupied:'false',part:'foot'}}"));
        map.put("westfalsefoot" + n, BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "_bed',Properties:{facing:'west',occupied:'false',part:'foot'}}"));
        map.put("northfalsefoot" + n, BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "_bed',Properties:{facing:'north',occupied:'false',part:'foot'}}"));
        map.put("eastfalsefoot" + n, BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "_bed',Properties:{facing:'east',occupied:'false',part:'foot'}}"));
        map.put("southfalsehead" + n, BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "_bed',Properties:{facing:'south',occupied:'false',part:'head'}}"));
        map.put("westfalsehead" + n, BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "_bed',Properties:{facing:'west',occupied:'false',part:'head'}}"));
        map.put("northfalsehead" + n, BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "_bed',Properties:{facing:'north',occupied:'false',part:'head'}}"));
        map.put("eastfalsehead" + n, BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "_bed',Properties:{facing:'east',occupied:'false',part:'head'}}"));
        map.put("southtruehead" + n, BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "_bed',Properties:{facing:'south',occupied:'true',part:'head'}}"));
        map.put("westtruehead" + n, BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "_bed',Properties:{facing:'west',occupied:'true',part:'head'}}"));
        map.put("northtruehead" + n, BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "_bed',Properties:{facing:'north',occupied:'true',part:'head'}}"));
        map.put("easttruehead" + n, BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "_bed',Properties:{facing:'east',occupied:'true',part:'head'}}"));
    }

    private static void addBanners(Map<String, Dynamic<?>> map, int n, String string) {
        for (int i = 0; i < 16; ++i) {
            map.put(i + "_" + n, BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "_banner',Properties:{rotation:'" + i + "'}}"));
        }
        map.put("north_" + n, BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "_wall_banner',Properties:{facing:'north'}}"));
        map.put("south_" + n, BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "_wall_banner',Properties:{facing:'south'}}"));
        map.put("west_" + n, BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "_wall_banner',Properties:{facing:'west'}}"));
        map.put("east_" + n, BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:" + string + "_wall_banner',Properties:{facing:'east'}}"));
    }

    public static String getName(Dynamic<?> dynamic) {
        return dynamic.get("Name").asString("");
    }

    public static String getProperty(Dynamic<?> dynamic, String string) {
        return dynamic.get("Properties").get(string).asString("");
    }

    public static int idFor(IntIdentityHashBiMap<Dynamic<?>> intIdentityHashBiMap, Dynamic<?> dynamic) {
        int n = intIdentityHashBiMap.getId(dynamic);
        if (n == -1) {
            n = intIdentityHashBiMap.add(dynamic);
        }
        return n;
    }

    private Dynamic<?> fix(Dynamic<?> dynamic) {
        Optional<Dynamic<?>> optional = dynamic.get("Level").result();
        return optional.isPresent() && optional.get().get("Sections").asStreamOpt().result().isPresent() ? dynamic.set("Level", new UpgradeChunk(optional.get()).write()) : dynamic;
    }

    @Override
    public TypeRewriteRule makeRule() {
        Type<?> type = this.getInputSchema().getType(TypeReferences.CHUNK);
        Type<?> type2 = this.getOutputSchema().getType(TypeReferences.CHUNK);
        return this.writeFixAndRead("ChunkPalettedStorageFix", type, type2, this::fix);
    }

    public static int getSideMask(boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        int n = 0;
        if (bl3) {
            n = bl2 ? (n |= 2) : (bl ? (n |= 0x80) : (n |= 1));
        } else if (bl4) {
            n = bl ? (n |= 0x20) : (bl2 ? (n |= 8) : (n |= 0x10));
        } else if (bl2) {
            n |= 4;
        } else if (bl) {
            n |= 0x40;
        }
        return n;
    }

    private static void lambda$static$6(HashMap hashMap) {
        for (Int2ObjectMap.Entry entry : DYE_COLOR_MAP.int2ObjectEntrySet()) {
            if (Objects.equals(entry.getValue(), "white")) continue;
            ChunkPaletteFormat.addBanners(hashMap, 15 - entry.getIntKey(), (String)entry.getValue());
        }
    }

    private static void lambda$static$5(HashMap hashMap) {
        for (Int2ObjectMap.Entry entry : DYE_COLOR_MAP.int2ObjectEntrySet()) {
            if (Objects.equals(entry.getValue(), "red")) continue;
            ChunkPaletteFormat.addBeds(hashMap, entry.getIntKey(), (String)entry.getValue());
        }
    }

    private static void lambda$static$4(Int2ObjectOpenHashMap int2ObjectOpenHashMap) {
        int2ObjectOpenHashMap.put(0, "white");
        int2ObjectOpenHashMap.put(1, "orange");
        int2ObjectOpenHashMap.put(2, "magenta");
        int2ObjectOpenHashMap.put(3, "light_blue");
        int2ObjectOpenHashMap.put(4, "yellow");
        int2ObjectOpenHashMap.put(5, "lime");
        int2ObjectOpenHashMap.put(6, "pink");
        int2ObjectOpenHashMap.put(7, "gray");
        int2ObjectOpenHashMap.put(8, "light_gray");
        int2ObjectOpenHashMap.put(9, "cyan");
        int2ObjectOpenHashMap.put(10, "purple");
        int2ObjectOpenHashMap.put(11, "blue");
        int2ObjectOpenHashMap.put(12, "brown");
        int2ObjectOpenHashMap.put(13, "green");
        int2ObjectOpenHashMap.put(14, "red");
        int2ObjectOpenHashMap.put(15, "black");
    }

    private static void lambda$static$3(HashMap hashMap) {
        for (int i = 0; i < 26; ++i) {
            hashMap.put("true" + i, BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:note_block',Properties:{powered:'true',note:'" + i + "'}}"));
            hashMap.put("false" + i, BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:note_block',Properties:{powered:'false',note:'" + i + "'}}"));
        }
    }

    private static void lambda$static$2(HashMap hashMap) {
        ChunkPaletteFormat.mapDoor(hashMap, "oak_door", 1024);
        ChunkPaletteFormat.mapDoor(hashMap, "iron_door", 1136);
        ChunkPaletteFormat.mapDoor(hashMap, "spruce_door", 3088);
        ChunkPaletteFormat.mapDoor(hashMap, "birch_door", 3104);
        ChunkPaletteFormat.mapDoor(hashMap, "jungle_door", 3120);
        ChunkPaletteFormat.mapDoor(hashMap, "acacia_door", 3136);
        ChunkPaletteFormat.mapDoor(hashMap, "dark_oak_door", 3152);
    }

    private static void lambda$static$1(HashMap hashMap) {
        ChunkPaletteFormat.mapSkull(hashMap, 0, "skeleton", "skull");
        ChunkPaletteFormat.mapSkull(hashMap, 1, "wither_skeleton", "skull");
        ChunkPaletteFormat.mapSkull(hashMap, 2, "zombie", "head");
        ChunkPaletteFormat.mapSkull(hashMap, 3, "player", "head");
        ChunkPaletteFormat.mapSkull(hashMap, 4, "creeper", "head");
        ChunkPaletteFormat.mapSkull(hashMap, 5, "dragon", "head");
    }

    private static void lambda$static$0(HashMap hashMap) {
        hashMap.put("minecraft:air0", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:flower_pot'}"));
        hashMap.put("minecraft:red_flower0", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:potted_poppy'}"));
        hashMap.put("minecraft:red_flower1", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:potted_blue_orchid'}"));
        hashMap.put("minecraft:red_flower2", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:potted_allium'}"));
        hashMap.put("minecraft:red_flower3", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:potted_azure_bluet'}"));
        hashMap.put("minecraft:red_flower4", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:potted_red_tulip'}"));
        hashMap.put("minecraft:red_flower5", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:potted_orange_tulip'}"));
        hashMap.put("minecraft:red_flower6", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:potted_white_tulip'}"));
        hashMap.put("minecraft:red_flower7", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:potted_pink_tulip'}"));
        hashMap.put("minecraft:red_flower8", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:potted_oxeye_daisy'}"));
        hashMap.put("minecraft:yellow_flower0", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:potted_dandelion'}"));
        hashMap.put("minecraft:sapling0", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:potted_oak_sapling'}"));
        hashMap.put("minecraft:sapling1", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:potted_spruce_sapling'}"));
        hashMap.put("minecraft:sapling2", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:potted_birch_sapling'}"));
        hashMap.put("minecraft:sapling3", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:potted_jungle_sapling'}"));
        hashMap.put("minecraft:sapling4", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:potted_acacia_sapling'}"));
        hashMap.put("minecraft:sapling5", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:potted_dark_oak_sapling'}"));
        hashMap.put("minecraft:red_mushroom0", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:potted_red_mushroom'}"));
        hashMap.put("minecraft:brown_mushroom0", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:potted_brown_mushroom'}"));
        hashMap.put("minecraft:deadbush0", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:potted_dead_bush'}"));
        hashMap.put("minecraft:tallgrass2", BlockStateFlatteningMap.makeDynamic("{Name:'minecraft:potted_fern'}"));
        hashMap.put("minecraft:cactus0", BlockStateFlatteningMap.getFixedNBTForID(2240));
    }

    static {
        FIX.set(2);
        FIX.set(3);
        FIX.set(110);
        FIX.set(140);
        FIX.set(144);
        FIX.set(25);
        FIX.set(86);
        FIX.set(26);
        FIX.set(176);
        FIX.set(177);
        FIX.set(175);
        FIX.set(64);
        FIX.set(71);
        FIX.set(193);
        FIX.set(194);
        FIX.set(195);
        FIX.set(196);
        FIX.set(197);
        VIRTUAL.set(54);
        VIRTUAL.set(146);
        VIRTUAL.set(25);
        VIRTUAL.set(26);
        VIRTUAL.set(51);
        VIRTUAL.set(53);
        VIRTUAL.set(67);
        VIRTUAL.set(108);
        VIRTUAL.set(109);
        VIRTUAL.set(114);
        VIRTUAL.set(128);
        VIRTUAL.set(134);
        VIRTUAL.set(135);
        VIRTUAL.set(136);
        VIRTUAL.set(156);
        VIRTUAL.set(163);
        VIRTUAL.set(164);
        VIRTUAL.set(180);
        VIRTUAL.set(203);
        VIRTUAL.set(55);
        VIRTUAL.set(85);
        VIRTUAL.set(113);
        VIRTUAL.set(188);
        VIRTUAL.set(189);
        VIRTUAL.set(190);
        VIRTUAL.set(191);
        VIRTUAL.set(192);
        VIRTUAL.set(93);
        VIRTUAL.set(94);
        VIRTUAL.set(101);
        VIRTUAL.set(102);
        VIRTUAL.set(160);
        VIRTUAL.set(106);
        VIRTUAL.set(107);
        VIRTUAL.set(183);
        VIRTUAL.set(184);
        VIRTUAL.set(185);
        VIRTUAL.set(186);
        VIRTUAL.set(187);
        VIRTUAL.set(132);
        VIRTUAL.set(139);
        VIRTUAL.set(199);
    }

    static final class UpgradeChunk {
        private int sides;
        private final Section[] sections = new Section[16];
        private final Dynamic<?> level;
        private final int x;
        private final int z;
        private final Int2ObjectMap<Dynamic<?>> tileEntities = new Int2ObjectLinkedOpenHashMap(16);

        public UpgradeChunk(Dynamic<?> dynamic) {
            this.level = dynamic;
            this.x = dynamic.get("xPos").asInt(0) << 4;
            this.z = dynamic.get("zPos").asInt(0) << 4;
            dynamic.get("TileEntities").asStreamOpt().result().ifPresent(this::lambda$new$1);
            boolean bl = dynamic.get("convertedFromAlphaFormat").asBoolean(true);
            dynamic.get("Sections").asStreamOpt().result().ifPresent(this::lambda$new$3);
            for (Section section : this.sections) {
                if (section == null) continue;
                block14: for (Map.Entry entry : section.toFix.entrySet()) {
                    int n = section.y << 12;
                    switch ((Integer)entry.getKey()) {
                        case 2: {
                            Dynamic<?> dynamic2;
                            Dynamic<?> dynamic3;
                            int n2;
                            IntListIterator intListIterator = ((IntList)entry.getValue()).iterator();
                            while (intListIterator.hasNext()) {
                                n2 = (Integer)intListIterator.next();
                                dynamic3 = this.getBlock(n2 |= n);
                                if (!"minecraft:grass_block".equals(ChunkPaletteFormat.getName(dynamic3)) || !"minecraft:snow".equals(dynamic2 = ChunkPaletteFormat.getName(this.getBlock(UpgradeChunk.relative(n2, Direction.UP)))) && !"minecraft:snow_layer".equals(dynamic2)) continue;
                                this.setBlock(n2, SNOWY_GRASS);
                            }
                            continue block14;
                        }
                        case 3: {
                            Dynamic<?> dynamic2;
                            Dynamic<?> dynamic3;
                            int n2;
                            IntListIterator intListIterator = ((IntList)entry.getValue()).iterator();
                            while (intListIterator.hasNext()) {
                                n2 = (Integer)intListIterator.next();
                                dynamic3 = this.getBlock(n2 |= n);
                                if (!"minecraft:podzol".equals(ChunkPaletteFormat.getName(dynamic3)) || !"minecraft:snow".equals(dynamic2 = ChunkPaletteFormat.getName(this.getBlock(UpgradeChunk.relative(n2, Direction.UP)))) && !"minecraft:snow_layer".equals(dynamic2)) continue;
                                this.setBlock(n2, SNOWY_PODZOL);
                            }
                            continue block14;
                        }
                        case 25: {
                            Dynamic<?> dynamic2;
                            Dynamic<?> dynamic3;
                            int n2;
                            IntListIterator intListIterator = ((IntList)entry.getValue()).iterator();
                            while (intListIterator.hasNext()) {
                                n2 = (Integer)intListIterator.next();
                                dynamic3 = this.removeTileEntity(n2 |= n);
                                if (dynamic3 == null) continue;
                                dynamic2 = Boolean.toString(dynamic3.get("powered").asBoolean(true)) + (byte)Math.min(Math.max(dynamic3.get("note").asInt(0), 0), 24);
                                this.setBlock(n2, NOTE_BLOCK_MAP.getOrDefault(dynamic2, NOTE_BLOCK_MAP.get("false0")));
                            }
                            continue block14;
                        }
                        case 26: {
                            Object object;
                            Dynamic<?> dynamic2;
                            Dynamic<?> dynamic3;
                            int n2;
                            IntListIterator intListIterator = ((IntList)entry.getValue()).iterator();
                            while (intListIterator.hasNext()) {
                                int n3;
                                n2 = (Integer)intListIterator.next();
                                dynamic3 = this.getTileEntity(n2 |= n);
                                dynamic2 = this.getBlock(n2);
                                if (dynamic3 == null || (n3 = dynamic3.get("color").asInt(0)) == 14 || n3 < 0 || n3 >= 16 || !BED_BLOCK_MAP.containsKey(object = ChunkPaletteFormat.getProperty(dynamic2, "facing") + ChunkPaletteFormat.getProperty(dynamic2, "occupied") + ChunkPaletteFormat.getProperty(dynamic2, "part") + n3)) continue;
                                this.setBlock(n2, BED_BLOCK_MAP.get(object));
                            }
                            continue block14;
                        }
                        case 64: 
                        case 71: 
                        case 193: 
                        case 194: 
                        case 195: 
                        case 196: 
                        case 197: {
                            Object object;
                            Dynamic<?> dynamic2;
                            Dynamic<?> dynamic3;
                            int n2;
                            IntListIterator intListIterator = ((IntList)entry.getValue()).iterator();
                            while (intListIterator.hasNext()) {
                                n2 = (Integer)intListIterator.next();
                                dynamic3 = this.getBlock(n2 |= n);
                                if (!ChunkPaletteFormat.getName(dynamic3).endsWith("_door") || !"lower".equals(ChunkPaletteFormat.getProperty(dynamic2 = this.getBlock(n2), "half"))) continue;
                                int n4 = UpgradeChunk.relative(n2, Direction.UP);
                                object = this.getBlock(n4);
                                String string = ChunkPaletteFormat.getName(dynamic2);
                                if (!string.equals(ChunkPaletteFormat.getName(object))) continue;
                                String string2 = ChunkPaletteFormat.getProperty(dynamic2, "facing");
                                String string3 = ChunkPaletteFormat.getProperty(dynamic2, "open");
                                String string4 = bl ? "left" : ChunkPaletteFormat.getProperty(object, "hinge");
                                String string5 = bl ? "false" : ChunkPaletteFormat.getProperty(object, "powered");
                                this.setBlock(n2, DOOR_MAP.get(string + string2 + "lower" + string4 + string3 + string5));
                                this.setBlock(n4, DOOR_MAP.get(string + string2 + "upper" + string4 + string3 + string5));
                            }
                            continue block14;
                        }
                        case 86: {
                            Dynamic<?> dynamic2;
                            Dynamic<?> dynamic3;
                            int n2;
                            IntListIterator intListIterator = ((IntList)entry.getValue()).iterator();
                            while (intListIterator.hasNext()) {
                                n2 = (Integer)intListIterator.next();
                                dynamic3 = this.getBlock(n2 |= n);
                                if (!"minecraft:carved_pumpkin".equals(ChunkPaletteFormat.getName(dynamic3)) || !"minecraft:grass_block".equals(dynamic2 = ChunkPaletteFormat.getName(this.getBlock(UpgradeChunk.relative(n2, Direction.DOWN)))) && !"minecraft:dirt".equals(dynamic2)) continue;
                                this.setBlock(n2, PUMPKIN);
                            }
                            continue block14;
                        }
                        case 110: {
                            Dynamic<?> dynamic2;
                            Dynamic<?> dynamic3;
                            int n2;
                            IntListIterator intListIterator = ((IntList)entry.getValue()).iterator();
                            while (intListIterator.hasNext()) {
                                n2 = (Integer)intListIterator.next();
                                dynamic3 = this.getBlock(n2 |= n);
                                if (!"minecraft:mycelium".equals(ChunkPaletteFormat.getName(dynamic3)) || !"minecraft:snow".equals(dynamic2 = ChunkPaletteFormat.getName(this.getBlock(UpgradeChunk.relative(n2, Direction.UP)))) && !"minecraft:snow_layer".equals(dynamic2)) continue;
                                this.setBlock(n2, SNOWY_MYCELIUM);
                            }
                            continue block14;
                        }
                        case 140: {
                            Dynamic<?> dynamic2;
                            Dynamic<?> dynamic3;
                            int n2;
                            IntListIterator intListIterator = ((IntList)entry.getValue()).iterator();
                            while (intListIterator.hasNext()) {
                                n2 = (Integer)intListIterator.next();
                                dynamic3 = this.removeTileEntity(n2 |= n);
                                if (dynamic3 == null) continue;
                                dynamic2 = dynamic3.get("Item").asString("") + dynamic3.get("Data").asInt(0);
                                this.setBlock(n2, FLOWER_POT_MAP.getOrDefault(dynamic2, FLOWER_POT_MAP.get("minecraft:air0")));
                            }
                            continue block14;
                        }
                        case 144: {
                            Object object;
                            Dynamic<?> dynamic2;
                            Dynamic<?> dynamic3;
                            int n2;
                            IntListIterator intListIterator = ((IntList)entry.getValue()).iterator();
                            while (intListIterator.hasNext()) {
                                n2 = (Integer)intListIterator.next();
                                dynamic3 = this.getTileEntity(n2 |= n);
                                if (dynamic3 == null) continue;
                                dynamic2 = String.valueOf(dynamic3.get("SkullType").asInt(0));
                                String string = ChunkPaletteFormat.getProperty(this.getBlock(n2), "facing");
                                object = !"up".equals(string) && !"down".equals(string) ? (String)((Object)dynamic2) + string : (String)((Object)dynamic2) + String.valueOf(dynamic3.get("Rot").asInt(0));
                                dynamic3.remove("SkullType");
                                dynamic3.remove("facing");
                                dynamic3.remove("Rot");
                                this.setBlock(n2, SKULL_MAP.getOrDefault(object, SKULL_MAP.get("0north")));
                            }
                            continue block14;
                        }
                        case 175: {
                            Dynamic<?> dynamic2;
                            Dynamic<?> dynamic3;
                            int n2;
                            IntListIterator intListIterator = ((IntList)entry.getValue()).iterator();
                            while (intListIterator.hasNext()) {
                                n2 = (Integer)intListIterator.next();
                                dynamic3 = this.getBlock(n2 |= n);
                                if (!"upper".equals(ChunkPaletteFormat.getProperty(dynamic3, "half"))) continue;
                                dynamic2 = this.getBlock(UpgradeChunk.relative(n2, Direction.DOWN));
                                String string = ChunkPaletteFormat.getName(dynamic2);
                                if ("minecraft:sunflower".equals(string)) {
                                    this.setBlock(n2, UPPER_SUNFLOWER);
                                    continue;
                                }
                                if ("minecraft:lilac".equals(string)) {
                                    this.setBlock(n2, UPPER_LILAC);
                                    continue;
                                }
                                if ("minecraft:tall_grass".equals(string)) {
                                    this.setBlock(n2, UPPER_TALL_GRASS);
                                    continue;
                                }
                                if ("minecraft:large_fern".equals(string)) {
                                    this.setBlock(n2, UPPER_LARGE_FERN);
                                    continue;
                                }
                                if ("minecraft:rose_bush".equals(string)) {
                                    this.setBlock(n2, UPPER_ROSE_BUSH);
                                    continue;
                                }
                                if (!"minecraft:peony".equals(string)) continue;
                                this.setBlock(n2, UPPER_PEONY);
                            }
                            continue block14;
                        }
                        case 176: 
                        case 177: {
                            Object object;
                            Dynamic<?> dynamic2;
                            Dynamic<?> dynamic3;
                            int n2;
                            IntListIterator intListIterator = ((IntList)entry.getValue()).iterator();
                            while (intListIterator.hasNext()) {
                                int n5;
                                n2 = (Integer)intListIterator.next();
                                dynamic3 = this.getTileEntity(n2 |= n);
                                dynamic2 = this.getBlock(n2);
                                if (dynamic3 == null || (n5 = dynamic3.get("Base").asInt(0)) == 15 || n5 < 0 || n5 >= 16 || !BANNER_BLOCK_MAP.containsKey(object = ChunkPaletteFormat.getProperty(dynamic2, (Integer)entry.getKey() == 176 ? "rotation" : "facing") + "_" + n5)) continue;
                                this.setBlock(n2, BANNER_BLOCK_MAP.get(object));
                            }
                            break;
                        }
                    }
                }
            }
        }

        @Nullable
        private Dynamic<?> getTileEntity(int n) {
            return (Dynamic)this.tileEntities.get(n);
        }

        @Nullable
        private Dynamic<?> removeTileEntity(int n) {
            return (Dynamic)this.tileEntities.remove(n);
        }

        public static int relative(int n, Direction direction) {
            switch (direction.getAxis()) {
                case X: {
                    int n2 = (n & 0xF) + direction.getAxisDirection().getStep();
                    return n2 >= 0 && n2 <= 15 ? n & 0xFFFFFFF0 | n2 : -1;
                }
                case Y: {
                    int n3 = (n >> 8) + direction.getAxisDirection().getStep();
                    return n3 >= 0 && n3 <= 255 ? n & 0xFF | n3 << 8 : -1;
                }
                case Z: {
                    int n4 = (n >> 4 & 0xF) + direction.getAxisDirection().getStep();
                    return n4 >= 0 && n4 <= 15 ? n & 0xFFFFFF0F | n4 << 4 : -1;
                }
            }
            return 1;
        }

        private void setBlock(int n, Dynamic<?> dynamic) {
            Section section;
            if (n >= 0 && n <= 65535 && (section = this.getSection(n)) != null) {
                section.setBlock(n & 0xFFF, dynamic);
            }
        }

        @Nullable
        private Section getSection(int n) {
            int n2 = n >> 12;
            return n2 < this.sections.length ? this.sections[n2] : null;
        }

        public Dynamic<?> getBlock(int n) {
            if (n >= 0 && n <= 65535) {
                Section section = this.getSection(n);
                return section == null ? AIR : section.getBlock(n & 0xFFF);
            }
            return AIR;
        }

        public Dynamic<?> write() {
            Dynamic<Object> dynamic = this.level;
            dynamic = this.tileEntities.isEmpty() ? dynamic.remove("TileEntities") : dynamic.set("TileEntities", dynamic.createList(this.tileEntities.values().stream()));
            Dynamic dynamic2 = dynamic.emptyMap();
            ArrayList<Dynamic<?>> arrayList = Lists.newArrayList();
            for (Section section : this.sections) {
                if (section == null) continue;
                arrayList.add(section.write());
                dynamic2 = dynamic2.set(String.valueOf(section.y), dynamic2.createIntList(Arrays.stream(section.update.toIntArray())));
            }
            Object object = dynamic.emptyMap();
            object = ((Dynamic)object).set("Sides", ((DynamicLike)object).createByte((byte)this.sides));
            object = ((Dynamic)object).set("Indices", dynamic2);
            return dynamic.set("UpgradeData", (Dynamic<?>)object).set("Sections", ((DynamicLike)object).createList(arrayList.stream()));
        }

        private void lambda$new$3(Stream stream) {
            stream.forEach(this::lambda$new$2);
        }

        private void lambda$new$2(Dynamic dynamic) {
            Section section = new Section(dynamic);
            this.sides = section.upgrade(this.sides);
            this.sections[section.y] = section;
        }

        private void lambda$new$1(Stream stream) {
            stream.forEach(this::lambda$new$0);
        }

        private void lambda$new$0(Dynamic dynamic) {
            int n;
            int n2 = dynamic.get("x").asInt(0) - this.x & 0xF;
            int n3 = dynamic.get("y").asInt(0);
            int n4 = n3 << 8 | (n = dynamic.get("z").asInt(0) - this.z & 0xF) << 4 | n2;
            if (this.tileEntities.put(n4, (Dynamic<?>)dynamic) != null) {
                LOGGER.warn("In chunk: {}x{} found a duplicate block entity at position: [{}, {}, {}]", (Object)this.x, (Object)this.z, (Object)n2, (Object)n3, (Object)n);
            }
        }
    }

    static class Section {
        private final IntIdentityHashBiMap<Dynamic<?>> palette = new IntIdentityHashBiMap(32);
        private final List<Dynamic<?>> listTag;
        private final Dynamic<?> section;
        private final boolean hasData;
        private final Int2ObjectMap<IntList> toFix = new Int2ObjectLinkedOpenHashMap<IntList>();
        private final IntList update = new IntArrayList();
        public final int y;
        private final Set<Dynamic<?>> seen = Sets.newIdentityHashSet();
        private final int[] buffer = new int[4096];

        public Section(Dynamic<?> dynamic) {
            this.listTag = Lists.newArrayList();
            this.section = dynamic;
            this.y = dynamic.get("Y").asInt(0);
            this.hasData = dynamic.get("Blocks").result().isPresent();
        }

        public Dynamic<?> getBlock(int n) {
            if (n >= 0 && n <= 4095) {
                Dynamic<?> dynamic = this.palette.getByValue(this.buffer[n]);
                return dynamic == null ? AIR : dynamic;
            }
            return AIR;
        }

        public void setBlock(int n, Dynamic<?> dynamic) {
            if (this.seen.add(dynamic)) {
                this.listTag.add("%%FILTER_ME%%".equals(ChunkPaletteFormat.getName(dynamic)) ? AIR : dynamic);
            }
            this.buffer[n] = ChunkPaletteFormat.idFor(this.palette, dynamic);
        }

        public int upgrade(int n) {
            if (!this.hasData) {
                return n;
            }
            ByteBuffer byteBuffer = this.section.get("Blocks").asByteBufferOpt().result().get();
            NibbleArray nibbleArray = this.section.get("Data").asByteBufferOpt().map(Section::lambda$upgrade$0).result().orElseGet(NibbleArray::new);
            NibbleArray nibbleArray2 = this.section.get("Add").asByteBufferOpt().map(Section::lambda$upgrade$1).result().orElseGet(NibbleArray::new);
            this.seen.add(AIR);
            ChunkPaletteFormat.idFor(this.palette, AIR);
            this.listTag.add(AIR);
            for (int i = 0; i < 4096; ++i) {
                int n2 = i & 0xF;
                int n3 = i >> 8 & 0xF;
                int n4 = i >> 4 & 0xF;
                int n5 = nibbleArray2.get(n2, n3, n4) << 12 | (byteBuffer.get(i) & 0xFF) << 4 | nibbleArray.get(n2, n3, n4);
                if (FIX.get(n5 >> 4)) {
                    this.addFix(n5 >> 4, i);
                }
                if (VIRTUAL.get(n5 >> 4)) {
                    int n6 = ChunkPaletteFormat.getSideMask(n2 == 0, n2 == 15, n4 == 0, n4 == 15);
                    if (n6 == 0) {
                        this.update.add(i);
                    } else {
                        n |= n6;
                    }
                }
                this.setBlock(i, BlockStateFlatteningMap.getFixedNBTForID(n5));
            }
            return n;
        }

        private void addFix(int n, int n2) {
            IntList intList = (IntList)this.toFix.get(n);
            if (intList == null) {
                intList = new IntArrayList();
                this.toFix.put(n, intList);
            }
            intList.add(n2);
        }

        public Dynamic<?> write() {
            Dynamic<Object> dynamic = this.section;
            if (!this.hasData) {
                return dynamic;
            }
            dynamic = dynamic.set("Palette", dynamic.createList(this.listTag.stream()));
            int n = Math.max(4, DataFixUtils.ceillog2(this.seen.size()));
            ArbitraryBitLengthIntArray arbitraryBitLengthIntArray = new ArbitraryBitLengthIntArray(n, 4096);
            for (int i = 0; i < this.buffer.length; ++i) {
                arbitraryBitLengthIntArray.func_233049_a_(i, this.buffer[i]);
            }
            dynamic = dynamic.set("BlockStates", dynamic.createLongList(Arrays.stream(arbitraryBitLengthIntArray.func_233047_a_())));
            dynamic = dynamic.remove("Blocks");
            dynamic = dynamic.remove("Data");
            return dynamic.remove("Add");
        }

        private static NibbleArray lambda$upgrade$1(ByteBuffer byteBuffer) {
            return new NibbleArray(DataFixUtils.toArray(byteBuffer));
        }

        private static NibbleArray lambda$upgrade$0(ByteBuffer byteBuffer) {
            return new NibbleArray(DataFixUtils.toArray(byteBuffer));
        }
    }

    static class NibbleArray {
        private final byte[] data;

        public NibbleArray() {
            this.data = new byte[2048];
        }

        public NibbleArray(byte[] byArray) {
            this.data = byArray;
            if (byArray.length != 2048) {
                throw new IllegalArgumentException("ChunkNibbleArrays should be 2048 bytes not: " + byArray.length);
            }
        }

        public int get(int n, int n2, int n3) {
            int n4 = this.getPosition(n2 << 8 | n3 << 4 | n);
            return this.isFirst(n2 << 8 | n3 << 4 | n) ? this.data[n4] & 0xF : this.data[n4] >> 4 & 0xF;
        }

        private boolean isFirst(int n) {
            return (n & 1) == 0;
        }

        private int getPosition(int n) {
            return n >> 1;
        }
    }

    public static enum Direction {
        DOWN(Offset.NEGATIVE, Axis.Y),
        UP(Offset.POSITIVE, Axis.Y),
        NORTH(Offset.NEGATIVE, Axis.Z),
        SOUTH(Offset.POSITIVE, Axis.Z),
        WEST(Offset.NEGATIVE, Axis.X),
        EAST(Offset.POSITIVE, Axis.X);

        private final Axis axis;
        private final Offset axisDirection;

        private Direction(Offset offset, Axis axis) {
            this.axis = axis;
            this.axisDirection = offset;
        }

        public Offset getAxisDirection() {
            return this.axisDirection;
        }

        public Axis getAxis() {
            return this.axis;
        }

        public static enum Axis {
            X,
            Y,
            Z;

        }

        public static enum Offset {
            POSITIVE(1),
            NEGATIVE(-1);

            private final int step;

            private Offset(int n2) {
                this.step = n2;
            }

            public int getStep() {
                return this.step;
            }
        }
    }
}

