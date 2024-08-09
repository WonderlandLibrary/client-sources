/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.layer;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import java.util.function.LongFunction;
import net.minecraft.util.Util;
import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.LazyAreaLayerContext;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.area.IAreaFactory;
import net.minecraft.world.gen.area.LazyArea;
import net.minecraft.world.gen.layer.AddBambooForestLayer;
import net.minecraft.world.gen.layer.AddIslandLayer;
import net.minecraft.world.gen.layer.AddMushroomIslandLayer;
import net.minecraft.world.gen.layer.AddSnowLayer;
import net.minecraft.world.gen.layer.BiomeLayer;
import net.minecraft.world.gen.layer.DeepOceanLayer;
import net.minecraft.world.gen.layer.EdgeBiomeLayer;
import net.minecraft.world.gen.layer.EdgeLayer;
import net.minecraft.world.gen.layer.HillsLayer;
import net.minecraft.world.gen.layer.IslandLayer;
import net.minecraft.world.gen.layer.Layer;
import net.minecraft.world.gen.layer.MixOceansLayer;
import net.minecraft.world.gen.layer.MixRiverLayer;
import net.minecraft.world.gen.layer.OceanLayer;
import net.minecraft.world.gen.layer.RareBiomeLayer;
import net.minecraft.world.gen.layer.RemoveTooMuchOceanLayer;
import net.minecraft.world.gen.layer.RiverLayer;
import net.minecraft.world.gen.layer.ShoreLayer;
import net.minecraft.world.gen.layer.SmoothLayer;
import net.minecraft.world.gen.layer.StartRiverLayer;
import net.minecraft.world.gen.layer.ZoomLayer;
import net.minecraft.world.gen.layer.traits.IAreaTransformer1;

public class LayerUtil {
    private static final Int2IntMap field_242937_a = Util.make(new Int2IntOpenHashMap(), LayerUtil::lambda$static$0);

    private static <T extends IArea, C extends IExtendedNoiseRandom<T>> IAreaFactory<T> repeat(long l, IAreaTransformer1 iAreaTransformer1, IAreaFactory<T> iAreaFactory, int n, LongFunction<C> longFunction) {
        IAreaFactory<T> iAreaFactory2 = iAreaFactory;
        for (int i = 0; i < n; ++i) {
            iAreaFactory2 = iAreaTransformer1.apply((IExtendedNoiseRandom)longFunction.apply(l + (long)i), iAreaFactory2);
        }
        return iAreaFactory2;
    }

    private static <T extends IArea, C extends IExtendedNoiseRandom<T>> IAreaFactory<T> func_237216_a_(boolean bl, int n, int n2, LongFunction<C> longFunction) {
        IAreaFactory iAreaFactory = IslandLayer.INSTANCE.apply((IExtendedNoiseRandom)longFunction.apply(1L));
        iAreaFactory = ZoomLayer.FUZZY.apply((IExtendedNoiseRandom)longFunction.apply(2000L), iAreaFactory);
        iAreaFactory = AddIslandLayer.INSTANCE.apply((IExtendedNoiseRandom)longFunction.apply(1L), iAreaFactory);
        iAreaFactory = ZoomLayer.NORMAL.apply((IExtendedNoiseRandom)longFunction.apply(2001L), iAreaFactory);
        iAreaFactory = AddIslandLayer.INSTANCE.apply((IExtendedNoiseRandom)longFunction.apply(2L), iAreaFactory);
        iAreaFactory = AddIslandLayer.INSTANCE.apply((IExtendedNoiseRandom)longFunction.apply(50L), iAreaFactory);
        iAreaFactory = AddIslandLayer.INSTANCE.apply((IExtendedNoiseRandom)longFunction.apply(70L), iAreaFactory);
        iAreaFactory = RemoveTooMuchOceanLayer.INSTANCE.apply((IExtendedNoiseRandom)longFunction.apply(2L), iAreaFactory);
        IAreaFactory iAreaFactory2 = OceanLayer.INSTANCE.apply((IExtendedNoiseRandom)longFunction.apply(2L));
        iAreaFactory2 = LayerUtil.repeat(2001L, ZoomLayer.NORMAL, iAreaFactory2, 6, longFunction);
        iAreaFactory = AddSnowLayer.INSTANCE.apply((IExtendedNoiseRandom)longFunction.apply(2L), iAreaFactory);
        iAreaFactory = AddIslandLayer.INSTANCE.apply((IExtendedNoiseRandom)longFunction.apply(3L), iAreaFactory);
        iAreaFactory = EdgeLayer.CoolWarm.INSTANCE.apply((IExtendedNoiseRandom)longFunction.apply(2L), iAreaFactory);
        iAreaFactory = EdgeLayer.HeatIce.INSTANCE.apply((IExtendedNoiseRandom)longFunction.apply(2L), iAreaFactory);
        iAreaFactory = EdgeLayer.Special.INSTANCE.apply((IExtendedNoiseRandom)longFunction.apply(3L), iAreaFactory);
        iAreaFactory = ZoomLayer.NORMAL.apply((IExtendedNoiseRandom)longFunction.apply(2002L), iAreaFactory);
        iAreaFactory = ZoomLayer.NORMAL.apply((IExtendedNoiseRandom)longFunction.apply(2003L), iAreaFactory);
        iAreaFactory = AddIslandLayer.INSTANCE.apply((IExtendedNoiseRandom)longFunction.apply(4L), iAreaFactory);
        iAreaFactory = AddMushroomIslandLayer.INSTANCE.apply((IExtendedNoiseRandom)longFunction.apply(5L), iAreaFactory);
        iAreaFactory = DeepOceanLayer.INSTANCE.apply((IExtendedNoiseRandom)longFunction.apply(4L), iAreaFactory);
        iAreaFactory = LayerUtil.repeat(1000L, ZoomLayer.NORMAL, iAreaFactory, 0, longFunction);
        IAreaFactory iAreaFactory3 = LayerUtil.repeat(1000L, ZoomLayer.NORMAL, iAreaFactory, 0, longFunction);
        iAreaFactory3 = StartRiverLayer.INSTANCE.apply((IExtendedNoiseRandom)longFunction.apply(100L), iAreaFactory3);
        IAreaFactory iAreaFactory4 = new BiomeLayer(bl).apply((IExtendedNoiseRandom)longFunction.apply(200L), iAreaFactory);
        iAreaFactory4 = AddBambooForestLayer.INSTANCE.apply((IExtendedNoiseRandom)longFunction.apply(1001L), iAreaFactory4);
        iAreaFactory4 = LayerUtil.repeat(1000L, ZoomLayer.NORMAL, iAreaFactory4, 2, longFunction);
        iAreaFactory4 = EdgeBiomeLayer.INSTANCE.apply((IExtendedNoiseRandom)longFunction.apply(1000L), iAreaFactory4);
        IAreaFactory iAreaFactory5 = LayerUtil.repeat(1000L, ZoomLayer.NORMAL, iAreaFactory3, 2, longFunction);
        iAreaFactory4 = HillsLayer.INSTANCE.apply((IExtendedNoiseRandom)longFunction.apply(1000L), iAreaFactory4, iAreaFactory5);
        iAreaFactory3 = LayerUtil.repeat(1000L, ZoomLayer.NORMAL, iAreaFactory3, 2, longFunction);
        iAreaFactory3 = LayerUtil.repeat(1000L, ZoomLayer.NORMAL, iAreaFactory3, n2, longFunction);
        iAreaFactory3 = RiverLayer.INSTANCE.apply((IExtendedNoiseRandom)longFunction.apply(1L), iAreaFactory3);
        iAreaFactory3 = SmoothLayer.INSTANCE.apply((IExtendedNoiseRandom)longFunction.apply(1000L), iAreaFactory3);
        iAreaFactory4 = RareBiomeLayer.INSTANCE.apply((IExtendedNoiseRandom)longFunction.apply(1001L), iAreaFactory4);
        for (int i = 0; i < n; ++i) {
            iAreaFactory4 = ZoomLayer.NORMAL.apply((IExtendedNoiseRandom)longFunction.apply(1000 + i), iAreaFactory4);
            if (i == 0) {
                iAreaFactory4 = AddIslandLayer.INSTANCE.apply((IExtendedNoiseRandom)longFunction.apply(3L), iAreaFactory4);
            }
            if (i != 1 && n != 1) continue;
            iAreaFactory4 = ShoreLayer.INSTANCE.apply((IExtendedNoiseRandom)longFunction.apply(1000L), iAreaFactory4);
        }
        iAreaFactory4 = SmoothLayer.INSTANCE.apply((IExtendedNoiseRandom)longFunction.apply(1000L), iAreaFactory4);
        iAreaFactory4 = MixRiverLayer.INSTANCE.apply((IExtendedNoiseRandom)longFunction.apply(100L), iAreaFactory4, iAreaFactory3);
        return MixOceansLayer.INSTANCE.apply((IExtendedNoiseRandom)longFunction.apply(100L), iAreaFactory4, iAreaFactory2);
    }

    public static Layer func_237215_a_(long l, boolean bl, int n, int n2) {
        int n3 = 25;
        IAreaFactory<LazyArea> iAreaFactory = LayerUtil.func_237216_a_(bl, n, n2, arg_0 -> LayerUtil.lambda$func_237215_a_$1(l, arg_0));
        return new Layer(iAreaFactory);
    }

    public static boolean areBiomesSimilar(int n, int n2) {
        if (n == n2) {
            return false;
        }
        return field_242937_a.get(n) == field_242937_a.get(n2);
    }

    private static void func_242939_a(Int2IntOpenHashMap int2IntOpenHashMap, Type type, int n) {
        int2IntOpenHashMap.put(n, type.ordinal());
    }

    protected static boolean isOcean(int n) {
        return n == 44 || n == 45 || n == 0 || n == 46 || n == 10 || n == 47 || n == 48 || n == 24 || n == 49 || n == 50;
    }

    protected static boolean isShallowOcean(int n) {
        return n == 44 || n == 45 || n == 0 || n == 46 || n == 10;
    }

    private static LazyAreaLayerContext lambda$func_237215_a_$1(long l, long l2) {
        return new LazyAreaLayerContext(25, l, l2);
    }

    private static void lambda$static$0(Int2IntOpenHashMap int2IntOpenHashMap) {
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.BEACH, 16);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.BEACH, 26);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.DESERT, 2);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.DESERT, 17);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.DESERT, 130);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.EXTREME_HILLS, 131);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.EXTREME_HILLS, 162);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.EXTREME_HILLS, 20);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.EXTREME_HILLS, 3);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.EXTREME_HILLS, 34);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.FOREST, 27);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.FOREST, 28);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.FOREST, 29);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.FOREST, 157);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.FOREST, 132);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.FOREST, 4);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.FOREST, 155);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.FOREST, 156);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.FOREST, 18);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.ICY, 140);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.ICY, 13);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.ICY, 12);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.JUNGLE, 168);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.JUNGLE, 169);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.JUNGLE, 21);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.JUNGLE, 23);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.JUNGLE, 22);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.JUNGLE, 149);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.JUNGLE, 151);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.MESA, 37);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.MESA, 165);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.MESA, 167);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.MESA, 166);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.BADLANDS_PLATEAU, 39);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.BADLANDS_PLATEAU, 38);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.MUSHROOM, 14);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.MUSHROOM, 15);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.NONE, 25);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.OCEAN, 46);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.OCEAN, 49);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.OCEAN, 50);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.OCEAN, 48);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.OCEAN, 24);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.OCEAN, 47);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.OCEAN, 10);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.OCEAN, 45);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.OCEAN, 0);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.OCEAN, 44);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.PLAINS, 1);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.PLAINS, 129);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.RIVER, 11);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.RIVER, 7);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.SAVANNA, 35);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.SAVANNA, 36);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.SAVANNA, 163);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.SAVANNA, 164);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.SWAMP, 6);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.SWAMP, 134);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.TAIGA, 160);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.TAIGA, 161);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.TAIGA, 32);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.TAIGA, 33);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.TAIGA, 30);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.TAIGA, 31);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.TAIGA, 158);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.TAIGA, 5);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.TAIGA, 19);
        LayerUtil.func_242939_a(int2IntOpenHashMap, Type.TAIGA, 133);
    }

    static enum Type {
        NONE,
        TAIGA,
        EXTREME_HILLS,
        JUNGLE,
        MESA,
        BADLANDS_PLATEAU,
        PLAINS,
        SAVANNA,
        ICY,
        BEACH,
        FOREST,
        OCEAN,
        DESERT,
        RIVER,
        SWAMP,
        MUSHROOM;

    }
}

