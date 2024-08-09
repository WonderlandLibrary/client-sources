/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.carver;

import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.carver.ICarverConfig;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;

public class ConfiguredCarvers {
    public static final ConfiguredCarver<ProbabilityConfig> field_243767_a = ConfiguredCarvers.func_243773_a("cave", WorldCarver.CAVE.func_242761_a(new ProbabilityConfig(0.14285715f)));
    public static final ConfiguredCarver<ProbabilityConfig> field_243768_b = ConfiguredCarvers.func_243773_a("canyon", WorldCarver.CANYON.func_242761_a(new ProbabilityConfig(0.02f)));
    public static final ConfiguredCarver<ProbabilityConfig> field_243769_c = ConfiguredCarvers.func_243773_a("ocean_cave", WorldCarver.CAVE.func_242761_a(new ProbabilityConfig(0.06666667f)));
    public static final ConfiguredCarver<ProbabilityConfig> field_243770_d = ConfiguredCarvers.func_243773_a("underwater_canyon", WorldCarver.UNDERWATER_CANYON.func_242761_a(new ProbabilityConfig(0.02f)));
    public static final ConfiguredCarver<ProbabilityConfig> field_243771_e = ConfiguredCarvers.func_243773_a("underwater_cave", WorldCarver.UNDERWATER_CAVE.func_242761_a(new ProbabilityConfig(0.06666667f)));
    public static final ConfiguredCarver<ProbabilityConfig> field_243772_f = ConfiguredCarvers.func_243773_a("nether_cave", WorldCarver.field_236240_b_.func_242761_a(new ProbabilityConfig(0.2f)));

    private static <WC extends ICarverConfig> ConfiguredCarver<WC> func_243773_a(String string, ConfiguredCarver<WC> configuredCarver) {
        return WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_CARVER, string, configuredCarver);
    }
}

