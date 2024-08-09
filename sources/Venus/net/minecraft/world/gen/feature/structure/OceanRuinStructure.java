/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.structure;

import com.mojang.serialization.Codec;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.OceanRuinConfig;
import net.minecraft.world.gen.feature.structure.OceanRuinPieces;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class OceanRuinStructure
extends Structure<OceanRuinConfig> {
    public OceanRuinStructure(Codec<OceanRuinConfig> codec) {
        super(codec);
    }

    @Override
    public Structure.IStartFactory<OceanRuinConfig> getStartFactory() {
        return Start::new;
    }

    public static enum Type implements IStringSerializable
    {
        WARM("warm"),
        COLD("cold");

        public static final Codec<Type> field_236998_c_;
        private static final Map<String, Type> BY_NAME;
        private final String name;

        private Type(String string2) {
            this.name = string2;
        }

        public String getName() {
            return this.name;
        }

        @Nullable
        public static Type getType(String string) {
            return BY_NAME.get(string);
        }

        @Override
        public String getString() {
            return this.name;
        }

        private static Type lambda$static$0(Type type) {
            return type;
        }

        static {
            field_236998_c_ = IStringSerializable.createEnumCodec(Type::values, Type::getType);
            BY_NAME = Arrays.stream(Type.values()).collect(Collectors.toMap(Type::getName, Type::lambda$static$0));
        }
    }

    public static class Start
    extends StructureStart<OceanRuinConfig> {
        public Start(Structure<OceanRuinConfig> structure, int n, int n2, MutableBoundingBox mutableBoundingBox, int n3, long l) {
            super(structure, n, n2, mutableBoundingBox, n3, l);
        }

        @Override
        public void func_230364_a_(DynamicRegistries dynamicRegistries, ChunkGenerator chunkGenerator, TemplateManager templateManager, int n, int n2, Biome biome, OceanRuinConfig oceanRuinConfig) {
            int n3 = n * 16;
            int n4 = n2 * 16;
            BlockPos blockPos = new BlockPos(n3, 90, n4);
            Rotation rotation = Rotation.randomRotation(this.rand);
            OceanRuinPieces.func_204041_a(templateManager, blockPos, rotation, this.components, this.rand, oceanRuinConfig);
            this.recalculateStructureSize();
        }

        @Override
        public void func_230364_a_(DynamicRegistries dynamicRegistries, ChunkGenerator chunkGenerator, TemplateManager templateManager, int n, int n2, Biome biome, IFeatureConfig iFeatureConfig) {
            this.func_230364_a_(dynamicRegistries, chunkGenerator, templateManager, n, n2, biome, (OceanRuinConfig)iFeatureConfig);
        }
    }
}

