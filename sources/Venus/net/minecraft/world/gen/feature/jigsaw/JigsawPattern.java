/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.jigsaw;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKeyCodec;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.jigsaw.JigsawPiece;
import net.minecraft.world.gen.feature.template.GravityStructureProcessor;
import net.minecraft.world.gen.feature.template.StructureProcessor;
import net.minecraft.world.gen.feature.template.TemplateManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JigsawPattern {
    private static final Logger field_236853_d_ = LogManager.getLogger();
    public static final Codec<JigsawPattern> field_236852_a_ = RecordCodecBuilder.create(JigsawPattern::lambda$static$1);
    public static final Codec<Supplier<JigsawPattern>> field_244392_b_ = RegistryKeyCodec.create(Registry.JIGSAW_POOL_KEY, field_236852_a_);
    private final ResourceLocation name;
    private final List<Pair<JigsawPiece, Integer>> rawTemplates;
    private final List<JigsawPiece> jigsawPieces;
    private final ResourceLocation fallback;
    private int maxSize = Integer.MIN_VALUE;

    public JigsawPattern(ResourceLocation resourceLocation, ResourceLocation resourceLocation2, List<Pair<JigsawPiece, Integer>> list) {
        this.name = resourceLocation;
        this.rawTemplates = list;
        this.jigsawPieces = Lists.newArrayList();
        for (Pair<JigsawPiece, Integer> pair : list) {
            JigsawPiece jigsawPiece = pair.getFirst();
            for (int i = 0; i < pair.getSecond(); ++i) {
                this.jigsawPieces.add(jigsawPiece);
            }
        }
        this.fallback = resourceLocation2;
    }

    public JigsawPattern(ResourceLocation resourceLocation, ResourceLocation resourceLocation2, List<Pair<Function<PlacementBehaviour, ? extends JigsawPiece>, Integer>> list, PlacementBehaviour placementBehaviour) {
        this.name = resourceLocation;
        this.rawTemplates = Lists.newArrayList();
        this.jigsawPieces = Lists.newArrayList();
        for (Pair<Function<PlacementBehaviour, ? extends JigsawPiece>, Integer> pair : list) {
            JigsawPiece jigsawPiece = pair.getFirst().apply(placementBehaviour);
            this.rawTemplates.add(Pair.of(jigsawPiece, pair.getSecond()));
            for (int i = 0; i < pair.getSecond(); ++i) {
                this.jigsawPieces.add(jigsawPiece);
            }
        }
        this.fallback = resourceLocation2;
    }

    public int getMaxSize(TemplateManager templateManager) {
        if (this.maxSize == Integer.MIN_VALUE) {
            this.maxSize = this.jigsawPieces.stream().mapToInt(arg_0 -> JigsawPattern.lambda$getMaxSize$2(templateManager, arg_0)).max().orElse(0);
        }
        return this.maxSize;
    }

    public ResourceLocation getFallback() {
        return this.fallback;
    }

    public JigsawPiece getRandomPiece(Random random2) {
        return this.jigsawPieces.get(random2.nextInt(this.jigsawPieces.size()));
    }

    public List<JigsawPiece> getShuffledPieces(Random random2) {
        return ImmutableList.copyOf(ObjectArrays.shuffle(this.jigsawPieces.toArray(new JigsawPiece[0]), random2));
    }

    public ResourceLocation getName() {
        return this.name;
    }

    public int getNumberOfPieces() {
        return this.jigsawPieces.size();
    }

    private static int lambda$getMaxSize$2(TemplateManager templateManager, JigsawPiece jigsawPiece) {
        return jigsawPiece.getBoundingBox(templateManager, BlockPos.ZERO, Rotation.NONE).getYSize();
    }

    private static App lambda$static$1(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)ResourceLocation.CODEC.fieldOf("name")).forGetter(JigsawPattern::getName), ((MapCodec)ResourceLocation.CODEC.fieldOf("fallback")).forGetter(JigsawPattern::getFallback), ((MapCodec)Codec.mapPair(JigsawPiece.field_236847_e_.fieldOf("element"), Codec.INT.fieldOf("weight")).codec().listOf().promotePartial((Consumer)Util.func_240982_a_("Pool element: ", field_236853_d_::error)).fieldOf("elements")).forGetter(JigsawPattern::lambda$static$0)).apply(instance, JigsawPattern::new);
    }

    private static List lambda$static$0(JigsawPattern jigsawPattern) {
        return jigsawPattern.rawTemplates;
    }

    public static enum PlacementBehaviour implements IStringSerializable
    {
        TERRAIN_MATCHING("terrain_matching", ImmutableList.of(new GravityStructureProcessor(Heightmap.Type.WORLD_SURFACE_WG, -1))),
        RIGID("rigid", ImmutableList.of());

        public static final Codec<PlacementBehaviour> field_236858_c_;
        private static final Map<String, PlacementBehaviour> BEHAVIOURS;
        private final String name;
        private final ImmutableList<StructureProcessor> structureProcessors;

        private PlacementBehaviour(String string2, ImmutableList<StructureProcessor> immutableList) {
            this.name = string2;
            this.structureProcessors = immutableList;
        }

        public String getName() {
            return this.name;
        }

        public static PlacementBehaviour getBehaviour(String string) {
            return BEHAVIOURS.get(string);
        }

        public ImmutableList<StructureProcessor> getStructureProcessors() {
            return this.structureProcessors;
        }

        @Override
        public String getString() {
            return this.name;
        }

        private static PlacementBehaviour lambda$static$0(PlacementBehaviour placementBehaviour) {
            return placementBehaviour;
        }

        static {
            field_236858_c_ = IStringSerializable.createEnumCodec(PlacementBehaviour::values, PlacementBehaviour::getBehaviour);
            BEHAVIOURS = Arrays.stream(PlacementBehaviour.values()).collect(Collectors.toMap(PlacementBehaviour::getName, PlacementBehaviour::lambda$static$0));
        }
    }
}

