/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.foliageplacer;

import com.mojang.serialization.Codec;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.foliageplacer.AcaciaFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.BushFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.DarkOakFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FancyFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.foliageplacer.JungleFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.MegaPineFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.PineFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.SpruceFoliagePlacer;

public class FoliagePlacerType<P extends FoliagePlacer> {
    public static final FoliagePlacerType<BlobFoliagePlacer> BLOB = FoliagePlacerType.func_236773_a_("blob_foliage_placer", BlobFoliagePlacer.field_236738_a_);
    public static final FoliagePlacerType<SpruceFoliagePlacer> SPRUCE = FoliagePlacerType.func_236773_a_("spruce_foliage_placer", SpruceFoliagePlacer.field_236790_a_);
    public static final FoliagePlacerType<PineFoliagePlacer> PINE = FoliagePlacerType.func_236773_a_("pine_foliage_placer", PineFoliagePlacer.field_236784_a_);
    public static final FoliagePlacerType<AcaciaFoliagePlacer> ACACIA = FoliagePlacerType.func_236773_a_("acacia_foliage_placer", AcaciaFoliagePlacer.field_236736_a_);
    public static final FoliagePlacerType<BushFoliagePlacer> field_236766_e_ = FoliagePlacerType.func_236773_a_("bush_foliage_placer", BushFoliagePlacer.field_236743_c_);
    public static final FoliagePlacerType<FancyFoliagePlacer> field_236767_f_ = FoliagePlacerType.func_236773_a_("fancy_foliage_placer", FancyFoliagePlacer.field_236747_c_);
    public static final FoliagePlacerType<JungleFoliagePlacer> field_236768_g_ = FoliagePlacerType.func_236773_a_("jungle_foliage_placer", JungleFoliagePlacer.field_236774_a_);
    public static final FoliagePlacerType<MegaPineFoliagePlacer> field_236769_h_ = FoliagePlacerType.func_236773_a_("mega_pine_foliage_placer", MegaPineFoliagePlacer.field_236778_a_);
    public static final FoliagePlacerType<DarkOakFoliagePlacer> field_236770_i_ = FoliagePlacerType.func_236773_a_("dark_oak_foliage_placer", DarkOakFoliagePlacer.field_236745_a_);
    private final Codec<P> field_236771_j_;

    private static <P extends FoliagePlacer> FoliagePlacerType<P> func_236773_a_(String string, Codec<P> codec) {
        return Registry.register(Registry.FOLIAGE_PLACER_TYPE, string, new FoliagePlacerType<P>(codec));
    }

    private FoliagePlacerType(Codec<P> codec) {
        this.field_236771_j_ = codec;
    }

    public Codec<P> func_236772_a_() {
        return this.field_236771_j_;
    }
}

