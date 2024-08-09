/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.RuinedPortalStructure;

public class RuinedPortalFeature
implements IFeatureConfig {
    public static final Codec<RuinedPortalFeature> field_236627_a_ = ((MapCodec)RuinedPortalStructure.Location.field_236342_h_.fieldOf("portal_type")).xmap(RuinedPortalFeature::new, RuinedPortalFeature::lambda$static$0).codec();
    public final RuinedPortalStructure.Location field_236628_b_;

    public RuinedPortalFeature(RuinedPortalStructure.Location location) {
        this.field_236628_b_ = location;
    }

    private static RuinedPortalStructure.Location lambda$static$0(RuinedPortalFeature ruinedPortalFeature) {
        return ruinedPortalFeature.field_236628_b_;
    }
}

