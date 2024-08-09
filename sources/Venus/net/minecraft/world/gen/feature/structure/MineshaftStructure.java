/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.structure;

import com.mojang.serialization.Codec;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.MineshaftConfig;
import net.minecraft.world.gen.feature.structure.MineshaftPieces;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class MineshaftStructure
extends Structure<MineshaftConfig> {
    public MineshaftStructure(Codec<MineshaftConfig> codec) {
        super(codec);
    }

    @Override
    protected boolean func_230363_a_(ChunkGenerator chunkGenerator, BiomeProvider biomeProvider, long l, SharedSeedRandom sharedSeedRandom, int n, int n2, Biome biome, ChunkPos chunkPos, MineshaftConfig mineshaftConfig) {
        sharedSeedRandom.setLargeFeatureSeed(l, n, n2);
        double d = mineshaftConfig.probability;
        return sharedSeedRandom.nextDouble() < d;
    }

    @Override
    public Structure.IStartFactory<MineshaftConfig> getStartFactory() {
        return Start::new;
    }

    @Override
    protected boolean func_230363_a_(ChunkGenerator chunkGenerator, BiomeProvider biomeProvider, long l, SharedSeedRandom sharedSeedRandom, int n, int n2, Biome biome, ChunkPos chunkPos, IFeatureConfig iFeatureConfig) {
        return this.func_230363_a_(chunkGenerator, biomeProvider, l, sharedSeedRandom, n, n2, biome, chunkPos, (MineshaftConfig)iFeatureConfig);
    }

    public static enum Type implements IStringSerializable
    {
        NORMAL("normal"),
        MESA("mesa");

        public static final Codec<Type> field_236324_c_;
        private static final Map<String, Type> BY_NAME;
        private final String name;

        private Type(String string2) {
            this.name = string2;
        }

        public String getName() {
            return this.name;
        }

        private static Type byName(String string) {
            return BY_NAME.get(string);
        }

        public static Type byId(int n) {
            return n >= 0 && n < Type.values().length ? Type.values()[n] : NORMAL;
        }

        @Override
        public String getString() {
            return this.name;
        }

        private static Type lambda$static$0(Type type) {
            return type;
        }

        static {
            field_236324_c_ = IStringSerializable.createEnumCodec(Type::values, Type::byName);
            BY_NAME = Arrays.stream(Type.values()).collect(Collectors.toMap(Type::getName, Type::lambda$static$0));
        }
    }

    public static class Start
    extends StructureStart<MineshaftConfig> {
        public Start(Structure<MineshaftConfig> structure, int n, int n2, MutableBoundingBox mutableBoundingBox, int n3, long l) {
            super(structure, n, n2, mutableBoundingBox, n3, l);
        }

        @Override
        public void func_230364_a_(DynamicRegistries dynamicRegistries, ChunkGenerator chunkGenerator, TemplateManager templateManager, int n, int n2, Biome biome, MineshaftConfig mineshaftConfig) {
            MineshaftPieces.Room room = new MineshaftPieces.Room(0, this.rand, (n << 4) + 2, (n2 << 4) + 2, mineshaftConfig.type);
            this.components.add(room);
            room.buildComponent(room, this.components, this.rand);
            this.recalculateStructureSize();
            if (mineshaftConfig.type == Type.MESA) {
                int n3 = -5;
                int n4 = chunkGenerator.func_230356_f_() - this.bounds.maxY + this.bounds.getYSize() / 2 - -5;
                this.bounds.offset(0, n4, 0);
                for (StructurePiece structurePiece : this.components) {
                    structurePiece.offset(0, n4, 0);
                }
            } else {
                this.func_214628_a(chunkGenerator.func_230356_f_(), this.rand, 10);
            }
        }

        @Override
        public void func_230364_a_(DynamicRegistries dynamicRegistries, ChunkGenerator chunkGenerator, TemplateManager templateManager, int n, int n2, Biome biome, IFeatureConfig iFeatureConfig) {
            this.func_230364_a_(dynamicRegistries, chunkGenerator, templateManager, n, n2, biome, (MineshaftConfig)iFeatureConfig);
        }
    }
}

