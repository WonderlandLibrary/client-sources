/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.IDecoratable;
import net.minecraft.world.gen.feature.WorldDecoratingHelper;
import net.minecraft.world.gen.placement.DecoratedPlacementConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;

public class ConfiguredPlacement<DC extends IPlacementConfig>
implements IDecoratable<ConfiguredPlacement<?>> {
    public static final Codec<ConfiguredPlacement<?>> field_236952_a_ = Registry.DECORATOR.dispatch("type", ConfiguredPlacement::lambda$static$0, Placement::getCodec);
    private final Placement<DC> decorator;
    private final DC config;

    public ConfiguredPlacement(Placement<DC> placement, DC DC) {
        this.decorator = placement;
        this.config = DC;
    }

    public Stream<BlockPos> func_242876_a(WorldDecoratingHelper worldDecoratingHelper, Random random2, BlockPos blockPos) {
        return this.decorator.func_241857_a(worldDecoratingHelper, random2, this.config, blockPos);
    }

    public String toString() {
        return String.format("[%s %s]", Registry.DECORATOR.getKey(this.decorator), this.config);
    }

    @Override
    public ConfiguredPlacement<?> withPlacement(ConfiguredPlacement<?> configuredPlacement) {
        return new ConfiguredPlacement<DecoratedPlacementConfig>(Placement.field_242896_B, new DecoratedPlacementConfig(configuredPlacement, this));
    }

    public DC func_242877_b() {
        return this.config;
    }

    @Override
    public Object withPlacement(ConfiguredPlacement configuredPlacement) {
        return this.withPlacement(configuredPlacement);
    }

    private static Placement lambda$static$0(ConfiguredPlacement configuredPlacement) {
        return configuredPlacement.decorator;
    }
}

