/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.layer;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeRegistry;
import net.minecraft.world.gen.area.IAreaFactory;
import net.minecraft.world.gen.area.LazyArea;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Layer {
    private static final Logger LOGGER = LogManager.getLogger();
    private final LazyArea field_215742_b;

    public Layer(IAreaFactory<LazyArea> iAreaFactory) {
        this.field_215742_b = iAreaFactory.make();
    }

    public Biome func_242936_a(Registry<Biome> registry, int n, int n2) {
        int n3 = this.field_215742_b.getValue(n, n2);
        RegistryKey<Biome> registryKey = BiomeRegistry.getKeyFromID(n3);
        if (registryKey == null) {
            throw new IllegalStateException("Unknown biome id emitted by layers: " + n3);
        }
        Biome biome = registry.getValueForKey(registryKey);
        if (biome == null) {
            if (SharedConstants.developmentMode) {
                throw Util.pauseDevMode(new IllegalStateException("Unknown biome id: " + n3));
            }
            LOGGER.warn("Unknown biome id: ", (Object)n3);
            return registry.getValueForKey(BiomeRegistry.getKeyFromID(0));
        }
        return biome;
    }
}

