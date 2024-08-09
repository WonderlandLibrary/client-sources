/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.trunkplacer;

import com.mojang.serialization.Codec;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.trunkplacer.AbstractTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.DarkOakTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.FancyTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.ForkyTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.GiantTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.MegaJungleTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;

public class TrunkPlacerType<P extends AbstractTrunkPlacer> {
    public static final TrunkPlacerType<StraightTrunkPlacer> STRAIGHT_TRUNK_PLACER = TrunkPlacerType.register("straight_trunk_placer", StraightTrunkPlacer.field_236903_a_);
    public static final TrunkPlacerType<ForkyTrunkPlacer> FORKING_TRUNK_PLACER = TrunkPlacerType.register("forking_trunk_placer", ForkyTrunkPlacer.field_236896_a_);
    public static final TrunkPlacerType<GiantTrunkPlacer> GIANT_TRUNK_PLACER = TrunkPlacerType.register("giant_trunk_placer", GiantTrunkPlacer.field_236898_a_);
    public static final TrunkPlacerType<MegaJungleTrunkPlacer> MEGA_TRUNK_PLACER = TrunkPlacerType.register("mega_jungle_trunk_placer", MegaJungleTrunkPlacer.field_236901_b_);
    public static final TrunkPlacerType<DarkOakTrunkPlacer> DARK_OAK_TRUNK_PLACER = TrunkPlacerType.register("dark_oak_trunk_placer", DarkOakTrunkPlacer.field_236882_a_);
    public static final TrunkPlacerType<FancyTrunkPlacer> FANCY_TRUNK_PLACER = TrunkPlacerType.register("fancy_trunk_placer", FancyTrunkPlacer.field_236884_a_);
    private final Codec<P> field_236926_g_;

    private static <P extends AbstractTrunkPlacer> TrunkPlacerType<P> register(String string, Codec<P> codec) {
        return Registry.register(Registry.TRUNK_REPLACER, string, new TrunkPlacerType<P>(codec));
    }

    private TrunkPlacerType(Codec<P> codec) {
        this.field_236926_g_ = codec;
    }

    public Codec<P> func_236927_a_() {
        return this.field_236926_g_;
    }
}

