/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.biome;

import net.minecraft.client.audio.BackgroundMusicTracks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.MoodSoundAmbience;
import net.minecraft.world.biome.ParticleEffectAmbience;
import net.minecraft.world.biome.SoundAdditionsAmbience;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.carver.ConfiguredCarvers;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.OceanRuinConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class BiomeMaker {
    private static int getSkyColorWithTemperatureModifier(float f) {
        float f2 = f / 3.0f;
        f2 = MathHelper.clamp(f2, -1.0f, 1.0f);
        return MathHelper.hsvToRGB(0.62222224f - f2 * 0.05f, 0.5f + f2 * 0.1f, 1.0f);
    }

    public static Biome makeGiantTaigaBiome(float f, float f2, float f3, boolean bl) {
        MobSpawnInfo.Builder builder = new MobSpawnInfo.Builder();
        DefaultBiomeFeatures.withPassiveMobs(builder);
        builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.WOLF, 8, 4, 4));
        builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.RABBIT, 4, 2, 3));
        builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.FOX, 8, 2, 4));
        if (bl) {
            DefaultBiomeFeatures.withBatsAndHostiles(builder);
        } else {
            DefaultBiomeFeatures.withBats(builder);
            DefaultBiomeFeatures.withHostileMobs(builder, 100, 25, 100);
        }
        BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder().withSurfaceBuilder(ConfiguredSurfaceBuilders.field_244177_i);
        DefaultBiomeFeatures.withStrongholdAndMineshaft(builder2);
        builder2.withStructure(StructureFeatures.field_244159_y);
        DefaultBiomeFeatures.withCavesAndCanyons(builder2);
        DefaultBiomeFeatures.withLavaAndWaterLakes(builder2);
        DefaultBiomeFeatures.withMonsterRoom(builder2);
        DefaultBiomeFeatures.withForestRocks(builder2);
        DefaultBiomeFeatures.withLargeFern(builder2);
        DefaultBiomeFeatures.withCommonOverworldBlocks(builder2);
        DefaultBiomeFeatures.withOverworldOres(builder2);
        DefaultBiomeFeatures.withDisks(builder2);
        builder2.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, bl ? Features.TREES_GIANT_SPRUCE : Features.TREES_GIANT);
        DefaultBiomeFeatures.withDefaultFlowers(builder2);
        DefaultBiomeFeatures.withGiantTaigaGrassVegetation(builder2);
        DefaultBiomeFeatures.withNormalMushroomGeneration(builder2);
        DefaultBiomeFeatures.withSugarCaneAndPumpkins(builder2);
        DefaultBiomeFeatures.withLavaAndWaterSprings(builder2);
        DefaultBiomeFeatures.withSparseBerries(builder2);
        DefaultBiomeFeatures.withFrozenTopLayer(builder2);
        return new Biome.Builder().precipitation(Biome.RainType.RAIN).category(Biome.Category.TAIGA).depth(f).scale(f2).temperature(f3).downfall(0.8f).setEffects(new BiomeAmbience.Builder().setWaterColor(4159204).setWaterFogColor(329011).setFogColor(12638463).withSkyColor(BiomeMaker.getSkyColorWithTemperatureModifier(f3)).setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build()).withMobSpawnSettings(builder.copy()).withGenerationSettings(builder2.build()).build();
    }

    public static Biome makeBirchForestBiome(float f, float f2, boolean bl) {
        MobSpawnInfo.Builder builder = new MobSpawnInfo.Builder();
        DefaultBiomeFeatures.withPassiveMobs(builder);
        DefaultBiomeFeatures.withBatsAndHostiles(builder);
        BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder().withSurfaceBuilder(ConfiguredSurfaceBuilders.field_244178_j);
        DefaultBiomeFeatures.withStrongholdAndMineshaft(builder2);
        builder2.withStructure(StructureFeatures.field_244159_y);
        DefaultBiomeFeatures.withCavesAndCanyons(builder2);
        DefaultBiomeFeatures.withLavaAndWaterLakes(builder2);
        DefaultBiomeFeatures.withMonsterRoom(builder2);
        DefaultBiomeFeatures.withAllForestFlowerGeneration(builder2);
        DefaultBiomeFeatures.withCommonOverworldBlocks(builder2);
        DefaultBiomeFeatures.withOverworldOres(builder2);
        DefaultBiomeFeatures.withDisks(builder2);
        if (bl) {
            DefaultBiomeFeatures.withTallBirches(builder2);
        } else {
            DefaultBiomeFeatures.withBirchTrees(builder2);
        }
        DefaultBiomeFeatures.withDefaultFlowers(builder2);
        DefaultBiomeFeatures.withForestGrass(builder2);
        DefaultBiomeFeatures.withNormalMushroomGeneration(builder2);
        DefaultBiomeFeatures.withSugarCaneAndPumpkins(builder2);
        DefaultBiomeFeatures.withLavaAndWaterSprings(builder2);
        DefaultBiomeFeatures.withFrozenTopLayer(builder2);
        return new Biome.Builder().precipitation(Biome.RainType.RAIN).category(Biome.Category.FOREST).depth(f).scale(f2).temperature(0.6f).downfall(0.6f).setEffects(new BiomeAmbience.Builder().setWaterColor(4159204).setWaterFogColor(329011).setFogColor(12638463).withSkyColor(BiomeMaker.getSkyColorWithTemperatureModifier(0.6f)).setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build()).withMobSpawnSettings(builder.copy()).withGenerationSettings(builder2.build()).build();
    }

    public static Biome makeJungleBiome() {
        return BiomeMaker.makeGenericJungleBiome(0.1f, 0.2f, 40, 2, 3);
    }

    public static Biome makeJungleEdgeBiome() {
        MobSpawnInfo.Builder builder = new MobSpawnInfo.Builder();
        DefaultBiomeFeatures.withSpawnsWithExtraChickens(builder);
        return BiomeMaker.makeTropicalBiome(0.1f, 0.2f, 0.8f, false, true, false, builder);
    }

    public static Biome makeModifiedJungleEdgeBiome() {
        MobSpawnInfo.Builder builder = new MobSpawnInfo.Builder();
        DefaultBiomeFeatures.withSpawnsWithExtraChickens(builder);
        return BiomeMaker.makeTropicalBiome(0.2f, 0.4f, 0.8f, false, true, true, builder);
    }

    public static Biome makeModifiedJungleBiome() {
        MobSpawnInfo.Builder builder = new MobSpawnInfo.Builder();
        DefaultBiomeFeatures.withSpawnsWithExtraChickens(builder);
        builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.PARROT, 10, 1, 1)).withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.OCELOT, 2, 1, 1));
        return BiomeMaker.makeTropicalBiome(0.2f, 0.4f, 0.9f, false, false, true, builder);
    }

    public static Biome makeJungleHillsBiome() {
        return BiomeMaker.makeGenericJungleBiome(0.45f, 0.3f, 10, 1, 1);
    }

    public static Biome makeBambooJungleBiome() {
        return BiomeMaker.makeGenericBambooBiome(0.1f, 0.2f, 40, 2);
    }

    public static Biome makeBambooJungleHillsBiome() {
        return BiomeMaker.makeGenericBambooBiome(0.45f, 0.3f, 10, 1);
    }

    private static Biome makeGenericJungleBiome(float f, float f2, int n, int n2, int n3) {
        MobSpawnInfo.Builder builder = new MobSpawnInfo.Builder();
        DefaultBiomeFeatures.withSpawnsWithExtraChickens(builder);
        builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.PARROT, n, 1, n2)).withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.OCELOT, 2, 1, n3)).withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.PANDA, 1, 1, 2));
        builder.isValidSpawnBiomeForPlayer();
        return BiomeMaker.makeTropicalBiome(f, f2, 0.9f, false, false, false, builder);
    }

    private static Biome makeGenericBambooBiome(float f, float f2, int n, int n2) {
        MobSpawnInfo.Builder builder = new MobSpawnInfo.Builder();
        DefaultBiomeFeatures.withSpawnsWithExtraChickens(builder);
        builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.PARROT, n, 1, n2)).withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.PANDA, 80, 1, 2)).withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.OCELOT, 2, 1, 1));
        return BiomeMaker.makeTropicalBiome(f, f2, 0.9f, true, false, false, builder);
    }

    private static Biome makeTropicalBiome(float f, float f2, float f3, boolean bl, boolean bl2, boolean bl3, MobSpawnInfo.Builder builder) {
        BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder().withSurfaceBuilder(ConfiguredSurfaceBuilders.field_244178_j);
        if (!bl2 && !bl3) {
            builder2.withStructure(StructureFeatures.field_244139_e);
        }
        DefaultBiomeFeatures.withStrongholdAndMineshaft(builder2);
        builder2.withStructure(StructureFeatures.field_244130_A);
        DefaultBiomeFeatures.withCavesAndCanyons(builder2);
        DefaultBiomeFeatures.withLavaAndWaterLakes(builder2);
        DefaultBiomeFeatures.withMonsterRoom(builder2);
        DefaultBiomeFeatures.withCommonOverworldBlocks(builder2);
        DefaultBiomeFeatures.withOverworldOres(builder2);
        DefaultBiomeFeatures.withDisks(builder2);
        if (bl) {
            DefaultBiomeFeatures.withBambooVegetation(builder2);
        } else {
            if (!bl2 && !bl3) {
                DefaultBiomeFeatures.withLightBambooVegetation(builder2);
            }
            if (bl2) {
                DefaultBiomeFeatures.withJungleEdgeTrees(builder2);
            } else {
                DefaultBiomeFeatures.withJungleTrees(builder2);
            }
        }
        DefaultBiomeFeatures.withWarmFlowers(builder2);
        DefaultBiomeFeatures.withJungleGrass(builder2);
        DefaultBiomeFeatures.withNormalMushroomGeneration(builder2);
        DefaultBiomeFeatures.withSugarCaneAndPumpkins(builder2);
        DefaultBiomeFeatures.withLavaAndWaterSprings(builder2);
        DefaultBiomeFeatures.withMelonPatchesAndVines(builder2);
        DefaultBiomeFeatures.withFrozenTopLayer(builder2);
        return new Biome.Builder().precipitation(Biome.RainType.RAIN).category(Biome.Category.JUNGLE).depth(f).scale(f2).temperature(0.95f).downfall(f3).setEffects(new BiomeAmbience.Builder().setWaterColor(4159204).setWaterFogColor(329011).setFogColor(12638463).withSkyColor(BiomeMaker.getSkyColorWithTemperatureModifier(0.95f)).setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build()).withMobSpawnSettings(builder.copy()).withGenerationSettings(builder2.build()).build();
    }

    public static Biome makeMountainBiome(float f, float f2, ConfiguredSurfaceBuilder<SurfaceBuilderConfig> configuredSurfaceBuilder, boolean bl) {
        MobSpawnInfo.Builder builder = new MobSpawnInfo.Builder();
        DefaultBiomeFeatures.withPassiveMobs(builder);
        builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.LLAMA, 5, 4, 6));
        DefaultBiomeFeatures.withBatsAndHostiles(builder);
        BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder().withSurfaceBuilder(configuredSurfaceBuilder);
        DefaultBiomeFeatures.withStrongholdAndMineshaft(builder2);
        builder2.withStructure(StructureFeatures.field_244132_C);
        DefaultBiomeFeatures.withCavesAndCanyons(builder2);
        DefaultBiomeFeatures.withLavaAndWaterLakes(builder2);
        DefaultBiomeFeatures.withMonsterRoom(builder2);
        DefaultBiomeFeatures.withCommonOverworldBlocks(builder2);
        DefaultBiomeFeatures.withOverworldOres(builder2);
        DefaultBiomeFeatures.withDisks(builder2);
        if (bl) {
            DefaultBiomeFeatures.withMountainEdgeTrees(builder2);
        } else {
            DefaultBiomeFeatures.withMountainTrees(builder2);
        }
        DefaultBiomeFeatures.withDefaultFlowers(builder2);
        DefaultBiomeFeatures.withBadlandsGrass(builder2);
        DefaultBiomeFeatures.withNormalMushroomGeneration(builder2);
        DefaultBiomeFeatures.withSugarCaneAndPumpkins(builder2);
        DefaultBiomeFeatures.withLavaAndWaterSprings(builder2);
        DefaultBiomeFeatures.withEmeraldOre(builder2);
        DefaultBiomeFeatures.withInfestedStone(builder2);
        DefaultBiomeFeatures.withFrozenTopLayer(builder2);
        return new Biome.Builder().precipitation(Biome.RainType.RAIN).category(Biome.Category.EXTREME_HILLS).depth(f).scale(f2).temperature(0.2f).downfall(0.3f).setEffects(new BiomeAmbience.Builder().setWaterColor(4159204).setWaterFogColor(329011).setFogColor(12638463).withSkyColor(BiomeMaker.getSkyColorWithTemperatureModifier(0.2f)).setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build()).withMobSpawnSettings(builder.copy()).withGenerationSettings(builder2.build()).build();
    }

    public static Biome makeDesertBiome(float f, float f2, boolean bl, boolean bl2, boolean bl3) {
        MobSpawnInfo.Builder builder = new MobSpawnInfo.Builder();
        DefaultBiomeFeatures.withDesertMobs(builder);
        BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder().withSurfaceBuilder(ConfiguredSurfaceBuilders.field_244172_d);
        if (bl) {
            builder2.withStructure(StructureFeatures.field_244155_u);
            builder2.withStructure(StructureFeatures.field_244135_a);
        }
        if (bl2) {
            builder2.withStructure(StructureFeatures.field_244140_f);
        }
        if (bl3) {
            DefaultBiomeFeatures.withFossils(builder2);
        }
        DefaultBiomeFeatures.withStrongholdAndMineshaft(builder2);
        builder2.withStructure(StructureFeatures.field_244160_z);
        DefaultBiomeFeatures.withCavesAndCanyons(builder2);
        DefaultBiomeFeatures.withLavaLakes(builder2);
        DefaultBiomeFeatures.withMonsterRoom(builder2);
        DefaultBiomeFeatures.withCommonOverworldBlocks(builder2);
        DefaultBiomeFeatures.withOverworldOres(builder2);
        DefaultBiomeFeatures.withDisks(builder2);
        DefaultBiomeFeatures.withDefaultFlowers(builder2);
        DefaultBiomeFeatures.withBadlandsGrass(builder2);
        DefaultBiomeFeatures.withDesertDeadBushes(builder2);
        DefaultBiomeFeatures.withNormalMushroomGeneration(builder2);
        DefaultBiomeFeatures.withDesertVegetation(builder2);
        DefaultBiomeFeatures.withLavaAndWaterSprings(builder2);
        DefaultBiomeFeatures.withDesertWells(builder2);
        DefaultBiomeFeatures.withFrozenTopLayer(builder2);
        return new Biome.Builder().precipitation(Biome.RainType.NONE).category(Biome.Category.DESERT).depth(f).scale(f2).temperature(2.0f).downfall(0.0f).setEffects(new BiomeAmbience.Builder().setWaterColor(4159204).setWaterFogColor(329011).setFogColor(12638463).withSkyColor(BiomeMaker.getSkyColorWithTemperatureModifier(2.0f)).setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build()).withMobSpawnSettings(builder.copy()).withGenerationSettings(builder2.build()).build();
    }

    public static Biome makePlainsBiome(boolean bl) {
        MobSpawnInfo.Builder builder = new MobSpawnInfo.Builder();
        DefaultBiomeFeatures.withSpawnsWithHorseAndDonkey(builder);
        if (!bl) {
            builder.isValidSpawnBiomeForPlayer();
        }
        BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder().withSurfaceBuilder(ConfiguredSurfaceBuilders.field_244178_j);
        if (!bl) {
            builder2.withStructure(StructureFeatures.field_244154_t).withStructure(StructureFeatures.field_244135_a);
        }
        DefaultBiomeFeatures.withStrongholdAndMineshaft(builder2);
        builder2.withStructure(StructureFeatures.field_244159_y);
        DefaultBiomeFeatures.withCavesAndCanyons(builder2);
        DefaultBiomeFeatures.withLavaAndWaterLakes(builder2);
        DefaultBiomeFeatures.withMonsterRoom(builder2);
        DefaultBiomeFeatures.withNoiseTallGrass(builder2);
        if (bl) {
            builder2.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.PATCH_SUNFLOWER);
        }
        DefaultBiomeFeatures.withCommonOverworldBlocks(builder2);
        DefaultBiomeFeatures.withOverworldOres(builder2);
        DefaultBiomeFeatures.withDisks(builder2);
        DefaultBiomeFeatures.withPlainGrassVegetation(builder2);
        if (bl) {
            builder2.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.PATCH_SUGAR_CANE);
        }
        DefaultBiomeFeatures.withNormalMushroomGeneration(builder2);
        if (bl) {
            builder2.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.PATCH_PUMPKIN);
        } else {
            DefaultBiomeFeatures.withSugarCaneAndPumpkins(builder2);
        }
        DefaultBiomeFeatures.withLavaAndWaterSprings(builder2);
        DefaultBiomeFeatures.withFrozenTopLayer(builder2);
        return new Biome.Builder().precipitation(Biome.RainType.RAIN).category(Biome.Category.PLAINS).depth(0.125f).scale(0.05f).temperature(0.8f).downfall(0.4f).setEffects(new BiomeAmbience.Builder().setWaterColor(4159204).setWaterFogColor(329011).setFogColor(12638463).withSkyColor(BiomeMaker.getSkyColorWithTemperatureModifier(0.8f)).setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build()).withMobSpawnSettings(builder.copy()).withGenerationSettings(builder2.build()).build();
    }

    private static Biome makeEndBiome(BiomeGenerationSettings.Builder builder) {
        MobSpawnInfo.Builder builder2 = new MobSpawnInfo.Builder();
        DefaultBiomeFeatures.withEndermen(builder2);
        return new Biome.Builder().precipitation(Biome.RainType.NONE).category(Biome.Category.THEEND).depth(0.1f).scale(0.2f).temperature(0.5f).downfall(0.5f).setEffects(new BiomeAmbience.Builder().setWaterColor(4159204).setWaterFogColor(329011).setFogColor(0xA080A0).withSkyColor(0).setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build()).withMobSpawnSettings(builder2.copy()).withGenerationSettings(builder.build()).build();
    }

    public static Biome makeEndBarrensBiome() {
        BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder().withSurfaceBuilder(ConfiguredSurfaceBuilders.field_244173_e);
        return BiomeMaker.makeEndBiome(builder);
    }

    public static Biome makeTheEndBiome() {
        BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder().withSurfaceBuilder(ConfiguredSurfaceBuilders.field_244173_e).withFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Features.END_SPIKE);
        return BiomeMaker.makeEndBiome(builder);
    }

    public static Biome makeEndMidlandsBiome() {
        BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder().withSurfaceBuilder(ConfiguredSurfaceBuilders.field_244173_e).withStructure(StructureFeatures.field_244151_q);
        return BiomeMaker.makeEndBiome(builder);
    }

    public static Biome makeEndHighlandsBiome() {
        BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder().withSurfaceBuilder(ConfiguredSurfaceBuilders.field_244173_e).withStructure(StructureFeatures.field_244151_q).withFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Features.END_GATEWAY).withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.CHORUS_PLANT);
        return BiomeMaker.makeEndBiome(builder);
    }

    public static Biome makeSmallEndIslandsBiome() {
        BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder().withSurfaceBuilder(ConfiguredSurfaceBuilders.field_244173_e).withFeature(GenerationStage.Decoration.RAW_GENERATION, Features.END_ISLAND_DECORATED);
        return BiomeMaker.makeEndBiome(builder);
    }

    public static Biome makeMushroomBiome(float f, float f2) {
        MobSpawnInfo.Builder builder = new MobSpawnInfo.Builder();
        DefaultBiomeFeatures.withMooshroomsAndBats(builder);
        BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder().withSurfaceBuilder(ConfiguredSurfaceBuilders.field_244182_n);
        DefaultBiomeFeatures.withStrongholdAndMineshaft(builder2);
        builder2.withStructure(StructureFeatures.field_244159_y);
        DefaultBiomeFeatures.withCavesAndCanyons(builder2);
        DefaultBiomeFeatures.withLavaAndWaterLakes(builder2);
        DefaultBiomeFeatures.withMonsterRoom(builder2);
        DefaultBiomeFeatures.withCommonOverworldBlocks(builder2);
        DefaultBiomeFeatures.withOverworldOres(builder2);
        DefaultBiomeFeatures.withDisks(builder2);
        DefaultBiomeFeatures.withMushroomBiomeVegetation(builder2);
        DefaultBiomeFeatures.withNormalMushroomGeneration(builder2);
        DefaultBiomeFeatures.withSugarCaneAndPumpkins(builder2);
        DefaultBiomeFeatures.withLavaAndWaterSprings(builder2);
        DefaultBiomeFeatures.withFrozenTopLayer(builder2);
        return new Biome.Builder().precipitation(Biome.RainType.RAIN).category(Biome.Category.MUSHROOM).depth(f).scale(f2).temperature(0.9f).downfall(1.0f).setEffects(new BiomeAmbience.Builder().setWaterColor(4159204).setWaterFogColor(329011).setFogColor(12638463).withSkyColor(BiomeMaker.getSkyColorWithTemperatureModifier(0.9f)).setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build()).withMobSpawnSettings(builder.copy()).withGenerationSettings(builder2.build()).build();
    }

    private static Biome makeGenericSavannaBiome(float f, float f2, float f3, boolean bl, boolean bl2, MobSpawnInfo.Builder builder) {
        BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder().withSurfaceBuilder(bl2 ? ConfiguredSurfaceBuilders.field_244186_r : ConfiguredSurfaceBuilders.field_244178_j);
        if (!bl && !bl2) {
            builder2.withStructure(StructureFeatures.field_244156_v).withStructure(StructureFeatures.field_244135_a);
        }
        DefaultBiomeFeatures.withStrongholdAndMineshaft(builder2);
        builder2.withStructure(bl ? StructureFeatures.field_244132_C : StructureFeatures.field_244159_y);
        DefaultBiomeFeatures.withCavesAndCanyons(builder2);
        DefaultBiomeFeatures.withLavaAndWaterLakes(builder2);
        DefaultBiomeFeatures.withMonsterRoom(builder2);
        if (!bl2) {
            DefaultBiomeFeatures.withTallGrass(builder2);
        }
        DefaultBiomeFeatures.withCommonOverworldBlocks(builder2);
        DefaultBiomeFeatures.withOverworldOres(builder2);
        DefaultBiomeFeatures.withDisks(builder2);
        if (bl2) {
            DefaultBiomeFeatures.withShatteredSavannaTrees(builder2);
            DefaultBiomeFeatures.withDefaultFlowers(builder2);
            DefaultBiomeFeatures.withNormalGrassPatch(builder2);
        } else {
            DefaultBiomeFeatures.withSavannaTrees(builder2);
            DefaultBiomeFeatures.withWarmFlowers(builder2);
            DefaultBiomeFeatures.withSavannaGrass(builder2);
        }
        DefaultBiomeFeatures.withNormalMushroomGeneration(builder2);
        DefaultBiomeFeatures.withSugarCaneAndPumpkins(builder2);
        DefaultBiomeFeatures.withLavaAndWaterSprings(builder2);
        DefaultBiomeFeatures.withFrozenTopLayer(builder2);
        return new Biome.Builder().precipitation(Biome.RainType.NONE).category(Biome.Category.SAVANNA).depth(f).scale(f2).temperature(f3).downfall(0.0f).setEffects(new BiomeAmbience.Builder().setWaterColor(4159204).setWaterFogColor(329011).setFogColor(12638463).withSkyColor(BiomeMaker.getSkyColorWithTemperatureModifier(f3)).setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build()).withMobSpawnSettings(builder.copy()).withGenerationSettings(builder2.build()).build();
    }

    public static Biome makeGenericSavannaBiome(float f, float f2, float f3, boolean bl, boolean bl2) {
        MobSpawnInfo.Builder builder = BiomeMaker.getSpawnsWithHorseAndDonkey();
        return BiomeMaker.makeGenericSavannaBiome(f, f2, f3, bl, bl2, builder);
    }

    private static MobSpawnInfo.Builder getSpawnsWithHorseAndDonkey() {
        MobSpawnInfo.Builder builder = new MobSpawnInfo.Builder();
        DefaultBiomeFeatures.withPassiveMobs(builder);
        builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.HORSE, 1, 2, 6)).withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.DONKEY, 1, 1, 1));
        DefaultBiomeFeatures.withBatsAndHostiles(builder);
        return builder;
    }

    public static Biome makeSavannaPlateauBiome() {
        MobSpawnInfo.Builder builder = BiomeMaker.getSpawnsWithHorseAndDonkey();
        builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.LLAMA, 8, 4, 4));
        return BiomeMaker.makeGenericSavannaBiome(1.5f, 0.025f, 1.0f, true, false, builder);
    }

    private static Biome makeGenericBadlandsBiome(ConfiguredSurfaceBuilder<SurfaceBuilderConfig> configuredSurfaceBuilder, float f, float f2, boolean bl, boolean bl2) {
        MobSpawnInfo.Builder builder = new MobSpawnInfo.Builder();
        DefaultBiomeFeatures.withBatsAndHostiles(builder);
        BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder().withSurfaceBuilder(configuredSurfaceBuilder);
        DefaultBiomeFeatures.withBadlandsStructures(builder2);
        builder2.withStructure(bl ? StructureFeatures.field_244132_C : StructureFeatures.field_244159_y);
        DefaultBiomeFeatures.withCavesAndCanyons(builder2);
        DefaultBiomeFeatures.withLavaAndWaterLakes(builder2);
        DefaultBiomeFeatures.withMonsterRoom(builder2);
        DefaultBiomeFeatures.withCommonOverworldBlocks(builder2);
        DefaultBiomeFeatures.withOverworldOres(builder2);
        DefaultBiomeFeatures.withExtraGoldOre(builder2);
        DefaultBiomeFeatures.withDisks(builder2);
        if (bl2) {
            DefaultBiomeFeatures.withBadlandsOakTrees(builder2);
        }
        DefaultBiomeFeatures.withBadlandsGrassAndBush(builder2);
        DefaultBiomeFeatures.withNormalMushroomGeneration(builder2);
        DefaultBiomeFeatures.withBadlandsVegetation(builder2);
        DefaultBiomeFeatures.withLavaAndWaterSprings(builder2);
        DefaultBiomeFeatures.withFrozenTopLayer(builder2);
        return new Biome.Builder().precipitation(Biome.RainType.NONE).category(Biome.Category.MESA).depth(f).scale(f2).temperature(2.0f).downfall(0.0f).setEffects(new BiomeAmbience.Builder().setWaterColor(4159204).setWaterFogColor(329011).setFogColor(12638463).withSkyColor(BiomeMaker.getSkyColorWithTemperatureModifier(2.0f)).withFoliageColor(10387789).withGrassColor(9470285).setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build()).withMobSpawnSettings(builder.copy()).withGenerationSettings(builder2.build()).build();
    }

    public static Biome makeBadlandsBiome(float f, float f2, boolean bl) {
        return BiomeMaker.makeGenericBadlandsBiome(ConfiguredSurfaceBuilders.field_244169_a, f, f2, bl, false);
    }

    public static Biome makeWoodedBadlandsPlateauBiome(float f, float f2) {
        return BiomeMaker.makeGenericBadlandsBiome(ConfiguredSurfaceBuilders.field_244191_w, f, f2, true, true);
    }

    public static Biome makeErodedBadlandsBiome() {
        return BiomeMaker.makeGenericBadlandsBiome(ConfiguredSurfaceBuilders.field_244174_f, 0.1f, 0.2f, true, false);
    }

    private static Biome makeGenericOceanBiome(MobSpawnInfo.Builder builder, int n, int n2, boolean bl, BiomeGenerationSettings.Builder builder2) {
        return new Biome.Builder().precipitation(Biome.RainType.RAIN).category(Biome.Category.OCEAN).depth(bl ? -1.8f : -1.0f).scale(0.1f).temperature(0.5f).downfall(0.5f).setEffects(new BiomeAmbience.Builder().setWaterColor(n).setWaterFogColor(n2).setFogColor(12638463).withSkyColor(BiomeMaker.getSkyColorWithTemperatureModifier(0.5f)).setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build()).withMobSpawnSettings(builder.copy()).withGenerationSettings(builder2.build()).build();
    }

    private static BiomeGenerationSettings.Builder getOceanGenerationSettingsBuilder(ConfiguredSurfaceBuilder<SurfaceBuilderConfig> configuredSurfaceBuilder, boolean bl, boolean bl2, boolean bl3) {
        StructureFeature<OceanRuinConfig, ? extends Structure<OceanRuinConfig>> structureFeature;
        BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder().withSurfaceBuilder(configuredSurfaceBuilder);
        StructureFeature<OceanRuinConfig, ? extends Structure<OceanRuinConfig>> structureFeature2 = structureFeature = bl2 ? StructureFeatures.field_244148_n : StructureFeatures.field_244147_m;
        if (bl3) {
            if (bl) {
                builder.withStructure(StructureFeatures.field_244146_l);
            }
            DefaultBiomeFeatures.withOceanStructures(builder);
            builder.withStructure(structureFeature);
        } else {
            builder.withStructure(structureFeature);
            if (bl) {
                builder.withStructure(StructureFeatures.field_244146_l);
            }
            DefaultBiomeFeatures.withOceanStructures(builder);
        }
        builder.withStructure(StructureFeatures.field_244133_D);
        DefaultBiomeFeatures.withOceanCavesAndCanyons(builder);
        DefaultBiomeFeatures.withLavaAndWaterLakes(builder);
        DefaultBiomeFeatures.withMonsterRoom(builder);
        DefaultBiomeFeatures.withCommonOverworldBlocks(builder);
        DefaultBiomeFeatures.withOverworldOres(builder);
        DefaultBiomeFeatures.withDisks(builder);
        DefaultBiomeFeatures.withTreesInWater(builder);
        DefaultBiomeFeatures.withDefaultFlowers(builder);
        DefaultBiomeFeatures.withBadlandsGrass(builder);
        DefaultBiomeFeatures.withNormalMushroomGeneration(builder);
        DefaultBiomeFeatures.withSugarCaneAndPumpkins(builder);
        DefaultBiomeFeatures.withLavaAndWaterSprings(builder);
        return builder;
    }

    public static Biome makeColdOceanBiome(boolean bl) {
        MobSpawnInfo.Builder builder = new MobSpawnInfo.Builder();
        DefaultBiomeFeatures.withOceanMobs(builder, 3, 4, 15);
        builder.withSpawner(EntityClassification.WATER_AMBIENT, new MobSpawnInfo.Spawners(EntityType.SALMON, 15, 1, 5));
        boolean bl2 = !bl;
        BiomeGenerationSettings.Builder builder2 = BiomeMaker.getOceanGenerationSettingsBuilder(ConfiguredSurfaceBuilders.field_244178_j, bl, false, bl2);
        builder2.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, bl ? Features.SEAGRASS_DEEP_COLD : Features.SEAGRASS_COLD);
        DefaultBiomeFeatures.withSimpleSeagrass(builder2);
        DefaultBiomeFeatures.withColdKelp(builder2);
        DefaultBiomeFeatures.withFrozenTopLayer(builder2);
        return BiomeMaker.makeGenericOceanBiome(builder, 4020182, 329011, bl, builder2);
    }

    public static Biome makeOceanBiome(boolean bl) {
        MobSpawnInfo.Builder builder = new MobSpawnInfo.Builder();
        DefaultBiomeFeatures.withOceanMobs(builder, 1, 4, 10);
        builder.withSpawner(EntityClassification.WATER_CREATURE, new MobSpawnInfo.Spawners(EntityType.DOLPHIN, 1, 1, 2));
        BiomeGenerationSettings.Builder builder2 = BiomeMaker.getOceanGenerationSettingsBuilder(ConfiguredSurfaceBuilders.field_244178_j, bl, false, true);
        builder2.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, bl ? Features.SEAGRASS_DEEP : Features.SEAGRASS_NORMAL);
        DefaultBiomeFeatures.withSimpleSeagrass(builder2);
        DefaultBiomeFeatures.withColdKelp(builder2);
        DefaultBiomeFeatures.withFrozenTopLayer(builder2);
        return BiomeMaker.makeGenericOceanBiome(builder, 4159204, 329011, bl, builder2);
    }

    public static Biome makeLukewarmOceanBiome(boolean bl) {
        MobSpawnInfo.Builder builder = new MobSpawnInfo.Builder();
        if (bl) {
            DefaultBiomeFeatures.withOceanMobs(builder, 8, 4, 8);
        } else {
            DefaultBiomeFeatures.withOceanMobs(builder, 10, 2, 15);
        }
        builder.withSpawner(EntityClassification.WATER_AMBIENT, new MobSpawnInfo.Spawners(EntityType.PUFFERFISH, 5, 1, 3)).withSpawner(EntityClassification.WATER_AMBIENT, new MobSpawnInfo.Spawners(EntityType.TROPICAL_FISH, 25, 8, 8)).withSpawner(EntityClassification.WATER_CREATURE, new MobSpawnInfo.Spawners(EntityType.DOLPHIN, 2, 1, 2));
        BiomeGenerationSettings.Builder builder2 = BiomeMaker.getOceanGenerationSettingsBuilder(ConfiguredSurfaceBuilders.field_244185_q, bl, true, false);
        builder2.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, bl ? Features.SEAGRASS_DEEP_WARM : Features.SEAGRASS_WARM);
        if (bl) {
            DefaultBiomeFeatures.withSimpleSeagrass(builder2);
        }
        DefaultBiomeFeatures.withWarmKelp(builder2);
        DefaultBiomeFeatures.withFrozenTopLayer(builder2);
        return BiomeMaker.makeGenericOceanBiome(builder, 4566514, 267827, bl, builder2);
    }

    public static Biome makeWarmOceanBiome() {
        MobSpawnInfo.Builder builder = new MobSpawnInfo.Builder().withSpawner(EntityClassification.WATER_AMBIENT, new MobSpawnInfo.Spawners(EntityType.PUFFERFISH, 15, 1, 3));
        DefaultBiomeFeatures.withWarmOceanMobs(builder, 10, 4);
        BiomeGenerationSettings.Builder builder2 = BiomeMaker.getOceanGenerationSettingsBuilder(ConfiguredSurfaceBuilders.field_244176_h, false, true, false).withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.WARM_OCEAN_VEGETATION).withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.SEAGRASS_WARM).withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.SEA_PICKLE);
        DefaultBiomeFeatures.withFrozenTopLayer(builder2);
        return BiomeMaker.makeGenericOceanBiome(builder, 4445678, 270131, false, builder2);
    }

    public static Biome makeDeepWarmOceanBiome() {
        MobSpawnInfo.Builder builder = new MobSpawnInfo.Builder();
        DefaultBiomeFeatures.withWarmOceanMobs(builder, 5, 1);
        builder.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.DROWNED, 5, 1, 1));
        BiomeGenerationSettings.Builder builder2 = BiomeMaker.getOceanGenerationSettingsBuilder(ConfiguredSurfaceBuilders.field_244176_h, true, true, false).withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.SEAGRASS_DEEP_WARM);
        DefaultBiomeFeatures.withSimpleSeagrass(builder2);
        DefaultBiomeFeatures.withFrozenTopLayer(builder2);
        return BiomeMaker.makeGenericOceanBiome(builder, 4445678, 270131, true, builder2);
    }

    public static Biome makeFrozenOceanBiome(boolean bl) {
        MobSpawnInfo.Builder builder = new MobSpawnInfo.Builder().withSpawner(EntityClassification.WATER_CREATURE, new MobSpawnInfo.Spawners(EntityType.SQUID, 1, 1, 4)).withSpawner(EntityClassification.WATER_AMBIENT, new MobSpawnInfo.Spawners(EntityType.SALMON, 15, 1, 5)).withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.POLAR_BEAR, 1, 1, 2));
        DefaultBiomeFeatures.withBatsAndHostiles(builder);
        builder.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.DROWNED, 5, 1, 1));
        float f = bl ? 0.5f : 0.0f;
        BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder().withSurfaceBuilder(ConfiguredSurfaceBuilders.field_244175_g);
        builder2.withStructure(StructureFeatures.field_244147_m);
        if (bl) {
            builder2.withStructure(StructureFeatures.field_244146_l);
        }
        DefaultBiomeFeatures.withOceanStructures(builder2);
        builder2.withStructure(StructureFeatures.field_244133_D);
        DefaultBiomeFeatures.withOceanCavesAndCanyons(builder2);
        DefaultBiomeFeatures.withLavaAndWaterLakes(builder2);
        DefaultBiomeFeatures.withIcebergs(builder2);
        DefaultBiomeFeatures.withMonsterRoom(builder2);
        DefaultBiomeFeatures.withBlueIce(builder2);
        DefaultBiomeFeatures.withCommonOverworldBlocks(builder2);
        DefaultBiomeFeatures.withOverworldOres(builder2);
        DefaultBiomeFeatures.withDisks(builder2);
        DefaultBiomeFeatures.withTreesInWater(builder2);
        DefaultBiomeFeatures.withDefaultFlowers(builder2);
        DefaultBiomeFeatures.withBadlandsGrass(builder2);
        DefaultBiomeFeatures.withNormalMushroomGeneration(builder2);
        DefaultBiomeFeatures.withSugarCaneAndPumpkins(builder2);
        DefaultBiomeFeatures.withLavaAndWaterSprings(builder2);
        DefaultBiomeFeatures.withFrozenTopLayer(builder2);
        return new Biome.Builder().precipitation(bl ? Biome.RainType.RAIN : Biome.RainType.SNOW).category(Biome.Category.OCEAN).depth(bl ? -1.8f : -1.0f).scale(0.1f).temperature(f).withTemperatureModifier(Biome.TemperatureModifier.FROZEN).downfall(0.5f).setEffects(new BiomeAmbience.Builder().setWaterColor(3750089).setWaterFogColor(329011).setFogColor(12638463).withSkyColor(BiomeMaker.getSkyColorWithTemperatureModifier(f)).setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build()).withMobSpawnSettings(builder.copy()).withGenerationSettings(builder2.build()).build();
    }

    private static Biome makeGenericForestBiome(float f, float f2, boolean bl, MobSpawnInfo.Builder builder) {
        BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder().withSurfaceBuilder(ConfiguredSurfaceBuilders.field_244178_j);
        DefaultBiomeFeatures.withStrongholdAndMineshaft(builder2);
        builder2.withStructure(StructureFeatures.field_244159_y);
        DefaultBiomeFeatures.withCavesAndCanyons(builder2);
        DefaultBiomeFeatures.withLavaAndWaterLakes(builder2);
        DefaultBiomeFeatures.withMonsterRoom(builder2);
        if (bl) {
            builder2.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.FOREST_FLOWER_VEGETATION_COMMON);
        } else {
            DefaultBiomeFeatures.withAllForestFlowerGeneration(builder2);
        }
        DefaultBiomeFeatures.withCommonOverworldBlocks(builder2);
        DefaultBiomeFeatures.withOverworldOres(builder2);
        DefaultBiomeFeatures.withDisks(builder2);
        if (bl) {
            builder2.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.FOREST_FLOWER_TREES);
            builder2.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.FLOWER_FOREST);
            DefaultBiomeFeatures.withBadlandsGrass(builder2);
        } else {
            DefaultBiomeFeatures.withForestBirchTrees(builder2);
            DefaultBiomeFeatures.withDefaultFlowers(builder2);
            DefaultBiomeFeatures.withForestGrass(builder2);
        }
        DefaultBiomeFeatures.withNormalMushroomGeneration(builder2);
        DefaultBiomeFeatures.withSugarCaneAndPumpkins(builder2);
        DefaultBiomeFeatures.withLavaAndWaterSprings(builder2);
        DefaultBiomeFeatures.withFrozenTopLayer(builder2);
        return new Biome.Builder().precipitation(Biome.RainType.RAIN).category(Biome.Category.FOREST).depth(f).scale(f2).temperature(0.7f).downfall(0.8f).setEffects(new BiomeAmbience.Builder().setWaterColor(4159204).setWaterFogColor(329011).setFogColor(12638463).withSkyColor(BiomeMaker.getSkyColorWithTemperatureModifier(0.7f)).setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build()).withMobSpawnSettings(builder.copy()).withGenerationSettings(builder2.build()).build();
    }

    private static MobSpawnInfo.Builder getStandardMobSpawnBuilder() {
        MobSpawnInfo.Builder builder = new MobSpawnInfo.Builder();
        DefaultBiomeFeatures.withPassiveMobs(builder);
        DefaultBiomeFeatures.withBatsAndHostiles(builder);
        return builder;
    }

    public static Biome makeForestBiome(float f, float f2) {
        MobSpawnInfo.Builder builder = BiomeMaker.getStandardMobSpawnBuilder().withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.WOLF, 5, 4, 4)).isValidSpawnBiomeForPlayer();
        return BiomeMaker.makeGenericForestBiome(f, f2, false, builder);
    }

    public static Biome makeFlowerForestBiome() {
        MobSpawnInfo.Builder builder = BiomeMaker.getStandardMobSpawnBuilder().withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.RABBIT, 4, 2, 3));
        return BiomeMaker.makeGenericForestBiome(0.1f, 0.4f, true, builder);
    }

    public static Biome makeTaigaBiome(float f, float f2, boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        MobSpawnInfo.Builder builder = new MobSpawnInfo.Builder();
        DefaultBiomeFeatures.withPassiveMobs(builder);
        builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.WOLF, 8, 4, 4)).withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.RABBIT, 4, 2, 3)).withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.FOX, 8, 2, 4));
        if (!bl && !bl2) {
            builder.isValidSpawnBiomeForPlayer();
        }
        DefaultBiomeFeatures.withBatsAndHostiles(builder);
        float f3 = bl ? -0.5f : 0.25f;
        BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder().withSurfaceBuilder(ConfiguredSurfaceBuilders.field_244178_j);
        if (bl3) {
            builder2.withStructure(StructureFeatures.field_244158_x);
            builder2.withStructure(StructureFeatures.field_244135_a);
        }
        if (bl4) {
            builder2.withStructure(StructureFeatures.field_244141_g);
        }
        DefaultBiomeFeatures.withStrongholdAndMineshaft(builder2);
        builder2.withStructure(bl2 ? StructureFeatures.field_244132_C : StructureFeatures.field_244159_y);
        DefaultBiomeFeatures.withCavesAndCanyons(builder2);
        DefaultBiomeFeatures.withLavaAndWaterLakes(builder2);
        DefaultBiomeFeatures.withMonsterRoom(builder2);
        DefaultBiomeFeatures.withLargeFern(builder2);
        DefaultBiomeFeatures.withCommonOverworldBlocks(builder2);
        DefaultBiomeFeatures.withOverworldOres(builder2);
        DefaultBiomeFeatures.withDisks(builder2);
        DefaultBiomeFeatures.withTaigaVegetation(builder2);
        DefaultBiomeFeatures.withDefaultFlowers(builder2);
        DefaultBiomeFeatures.withTaigaGrassVegetation(builder2);
        DefaultBiomeFeatures.withNormalMushroomGeneration(builder2);
        DefaultBiomeFeatures.withSugarCaneAndPumpkins(builder2);
        DefaultBiomeFeatures.withLavaAndWaterSprings(builder2);
        if (bl) {
            DefaultBiomeFeatures.withChanceBerries(builder2);
        } else {
            DefaultBiomeFeatures.withSparseBerries(builder2);
        }
        DefaultBiomeFeatures.withFrozenTopLayer(builder2);
        return new Biome.Builder().precipitation(bl ? Biome.RainType.SNOW : Biome.RainType.RAIN).category(Biome.Category.TAIGA).depth(f).scale(f2).temperature(f3).downfall(bl ? 0.4f : 0.8f).setEffects(new BiomeAmbience.Builder().setWaterColor(bl ? 4020182 : 4159204).setWaterFogColor(329011).setFogColor(12638463).withSkyColor(BiomeMaker.getSkyColorWithTemperatureModifier(f3)).setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build()).withMobSpawnSettings(builder.copy()).withGenerationSettings(builder2.build()).build();
    }

    public static Biome makeDarkForestBiome(float f, float f2, boolean bl) {
        MobSpawnInfo.Builder builder = new MobSpawnInfo.Builder();
        DefaultBiomeFeatures.withPassiveMobs(builder);
        DefaultBiomeFeatures.withBatsAndHostiles(builder);
        BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder().withSurfaceBuilder(ConfiguredSurfaceBuilders.field_244178_j);
        builder2.withStructure(StructureFeatures.field_244138_d);
        DefaultBiomeFeatures.withStrongholdAndMineshaft(builder2);
        builder2.withStructure(StructureFeatures.field_244159_y);
        DefaultBiomeFeatures.withCavesAndCanyons(builder2);
        DefaultBiomeFeatures.withLavaAndWaterLakes(builder2);
        DefaultBiomeFeatures.withMonsterRoom(builder2);
        builder2.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, bl ? Features.DARK_FOREST_VEGETATION_RED : Features.DARK_FOREST_VEGETATION_BROWN);
        DefaultBiomeFeatures.withAllForestFlowerGeneration(builder2);
        DefaultBiomeFeatures.withCommonOverworldBlocks(builder2);
        DefaultBiomeFeatures.withOverworldOres(builder2);
        DefaultBiomeFeatures.withDisks(builder2);
        DefaultBiomeFeatures.withDefaultFlowers(builder2);
        DefaultBiomeFeatures.withForestGrass(builder2);
        DefaultBiomeFeatures.withNormalMushroomGeneration(builder2);
        DefaultBiomeFeatures.withSugarCaneAndPumpkins(builder2);
        DefaultBiomeFeatures.withLavaAndWaterSprings(builder2);
        DefaultBiomeFeatures.withFrozenTopLayer(builder2);
        return new Biome.Builder().precipitation(Biome.RainType.RAIN).category(Biome.Category.FOREST).depth(f).scale(f2).temperature(0.7f).downfall(0.8f).setEffects(new BiomeAmbience.Builder().setWaterColor(4159204).setWaterFogColor(329011).setFogColor(12638463).withSkyColor(BiomeMaker.getSkyColorWithTemperatureModifier(0.7f)).withGrassColorModifier(BiomeAmbience.GrassColorModifier.DARK_FOREST).setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build()).withMobSpawnSettings(builder.copy()).withGenerationSettings(builder2.build()).build();
    }

    public static Biome makeGenericSwampBiome(float f, float f2, boolean bl) {
        MobSpawnInfo.Builder builder = new MobSpawnInfo.Builder();
        DefaultBiomeFeatures.withPassiveMobs(builder);
        DefaultBiomeFeatures.withBatsAndHostiles(builder);
        builder.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.SLIME, 1, 1, 1));
        BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder().withSurfaceBuilder(ConfiguredSurfaceBuilders.field_244189_u);
        if (!bl) {
            builder2.withStructure(StructureFeatures.field_244144_j);
        }
        builder2.withStructure(StructureFeatures.field_244136_b);
        builder2.withStructure(StructureFeatures.field_244131_B);
        DefaultBiomeFeatures.withCavesAndCanyons(builder2);
        if (!bl) {
            DefaultBiomeFeatures.withFossils(builder2);
        }
        DefaultBiomeFeatures.withLavaAndWaterLakes(builder2);
        DefaultBiomeFeatures.withMonsterRoom(builder2);
        DefaultBiomeFeatures.withCommonOverworldBlocks(builder2);
        DefaultBiomeFeatures.withOverworldOres(builder2);
        DefaultBiomeFeatures.withClayDisks(builder2);
        DefaultBiomeFeatures.withSwampVegetation(builder2);
        DefaultBiomeFeatures.withNormalMushroomGeneration(builder2);
        DefaultBiomeFeatures.withSwampSugarcaneAndPumpkin(builder2);
        DefaultBiomeFeatures.withLavaAndWaterSprings(builder2);
        if (bl) {
            DefaultBiomeFeatures.withFossils(builder2);
        } else {
            builder2.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.SEAGRASS_SWAMP);
        }
        DefaultBiomeFeatures.withFrozenTopLayer(builder2);
        return new Biome.Builder().precipitation(Biome.RainType.RAIN).category(Biome.Category.SWAMP).depth(f).scale(f2).temperature(0.8f).downfall(0.9f).setEffects(new BiomeAmbience.Builder().setWaterColor(6388580).setWaterFogColor(2302743).setFogColor(12638463).withSkyColor(BiomeMaker.getSkyColorWithTemperatureModifier(0.8f)).withFoliageColor(6975545).withGrassColorModifier(BiomeAmbience.GrassColorModifier.SWAMP).setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build()).withMobSpawnSettings(builder.copy()).withGenerationSettings(builder2.build()).build();
    }

    public static Biome makeSnowyBiome(float f, float f2, boolean bl, boolean bl2) {
        MobSpawnInfo.Builder builder = new MobSpawnInfo.Builder().withCreatureSpawnProbability(0.07f);
        DefaultBiomeFeatures.withSnowyBiomeMobs(builder);
        BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder().withSurfaceBuilder(bl ? ConfiguredSurfaceBuilders.field_244180_l : ConfiguredSurfaceBuilders.field_244178_j);
        if (!bl && !bl2) {
            builder2.withStructure(StructureFeatures.field_244157_w).withStructure(StructureFeatures.field_244141_g);
        }
        DefaultBiomeFeatures.withStrongholdAndMineshaft(builder2);
        if (!bl && !bl2) {
            builder2.withStructure(StructureFeatures.field_244135_a);
        }
        builder2.withStructure(bl2 ? StructureFeatures.field_244132_C : StructureFeatures.field_244159_y);
        DefaultBiomeFeatures.withCavesAndCanyons(builder2);
        DefaultBiomeFeatures.withLavaAndWaterLakes(builder2);
        DefaultBiomeFeatures.withMonsterRoom(builder2);
        if (bl) {
            builder2.withFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Features.ICE_SPIKE);
            builder2.withFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Features.ICE_PATCH);
        }
        DefaultBiomeFeatures.withCommonOverworldBlocks(builder2);
        DefaultBiomeFeatures.withOverworldOres(builder2);
        DefaultBiomeFeatures.withDisks(builder2);
        DefaultBiomeFeatures.withSnowySpruces(builder2);
        DefaultBiomeFeatures.withDefaultFlowers(builder2);
        DefaultBiomeFeatures.withBadlandsGrass(builder2);
        DefaultBiomeFeatures.withNormalMushroomGeneration(builder2);
        DefaultBiomeFeatures.withSugarCaneAndPumpkins(builder2);
        DefaultBiomeFeatures.withLavaAndWaterSprings(builder2);
        DefaultBiomeFeatures.withFrozenTopLayer(builder2);
        return new Biome.Builder().precipitation(Biome.RainType.SNOW).category(Biome.Category.ICY).depth(f).scale(f2).temperature(0.0f).downfall(0.5f).setEffects(new BiomeAmbience.Builder().setWaterColor(4159204).setWaterFogColor(329011).setFogColor(12638463).withSkyColor(BiomeMaker.getSkyColorWithTemperatureModifier(0.0f)).setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build()).withMobSpawnSettings(builder.copy()).withGenerationSettings(builder2.build()).build();
    }

    public static Biome makeRiverBiome(float f, float f2, float f3, int n, boolean bl) {
        MobSpawnInfo.Builder builder = new MobSpawnInfo.Builder().withSpawner(EntityClassification.WATER_CREATURE, new MobSpawnInfo.Spawners(EntityType.SQUID, 2, 1, 4)).withSpawner(EntityClassification.WATER_AMBIENT, new MobSpawnInfo.Spawners(EntityType.SALMON, 5, 1, 5));
        DefaultBiomeFeatures.withBatsAndHostiles(builder);
        builder.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.DROWNED, bl ? 1 : 100, 1, 1));
        BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder().withSurfaceBuilder(ConfiguredSurfaceBuilders.field_244178_j);
        builder2.withStructure(StructureFeatures.field_244136_b);
        builder2.withStructure(StructureFeatures.field_244159_y);
        DefaultBiomeFeatures.withCavesAndCanyons(builder2);
        DefaultBiomeFeatures.withLavaAndWaterLakes(builder2);
        DefaultBiomeFeatures.withMonsterRoom(builder2);
        DefaultBiomeFeatures.withCommonOverworldBlocks(builder2);
        DefaultBiomeFeatures.withOverworldOres(builder2);
        DefaultBiomeFeatures.withDisks(builder2);
        DefaultBiomeFeatures.withTreesInWater(builder2);
        DefaultBiomeFeatures.withDefaultFlowers(builder2);
        DefaultBiomeFeatures.withBadlandsGrass(builder2);
        DefaultBiomeFeatures.withNormalMushroomGeneration(builder2);
        DefaultBiomeFeatures.withSugarCaneAndPumpkins(builder2);
        DefaultBiomeFeatures.withLavaAndWaterSprings(builder2);
        if (!bl) {
            builder2.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.SEAGRASS_RIVER);
        }
        DefaultBiomeFeatures.withFrozenTopLayer(builder2);
        return new Biome.Builder().precipitation(bl ? Biome.RainType.SNOW : Biome.RainType.RAIN).category(Biome.Category.RIVER).depth(f).scale(f2).temperature(f3).downfall(0.5f).setEffects(new BiomeAmbience.Builder().setWaterColor(n).setWaterFogColor(329011).setFogColor(12638463).withSkyColor(BiomeMaker.getSkyColorWithTemperatureModifier(f3)).setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build()).withMobSpawnSettings(builder.copy()).withGenerationSettings(builder2.build()).build();
    }

    public static Biome makeGenericBeachBiome(float f, float f2, float f3, float f4, int n, boolean bl, boolean bl2) {
        MobSpawnInfo.Builder builder = new MobSpawnInfo.Builder();
        if (!bl2 && !bl) {
            builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.TURTLE, 5, 2, 5));
        }
        DefaultBiomeFeatures.withBatsAndHostiles(builder);
        BiomeGenerationSettings.Builder builder2 = new BiomeGenerationSettings.Builder().withSurfaceBuilder(bl2 ? ConfiguredSurfaceBuilders.field_244188_t : ConfiguredSurfaceBuilders.field_244172_d);
        if (bl2) {
            DefaultBiomeFeatures.withStrongholdAndMineshaft(builder2);
        } else {
            builder2.withStructure(StructureFeatures.field_244136_b);
            builder2.withStructure(StructureFeatures.field_244152_r);
            builder2.withStructure(StructureFeatures.field_244143_i);
        }
        builder2.withStructure(bl2 ? StructureFeatures.field_244132_C : StructureFeatures.field_244159_y);
        DefaultBiomeFeatures.withCavesAndCanyons(builder2);
        DefaultBiomeFeatures.withLavaAndWaterLakes(builder2);
        DefaultBiomeFeatures.withMonsterRoom(builder2);
        DefaultBiomeFeatures.withCommonOverworldBlocks(builder2);
        DefaultBiomeFeatures.withOverworldOres(builder2);
        DefaultBiomeFeatures.withDisks(builder2);
        DefaultBiomeFeatures.withDefaultFlowers(builder2);
        DefaultBiomeFeatures.withBadlandsGrass(builder2);
        DefaultBiomeFeatures.withNormalMushroomGeneration(builder2);
        DefaultBiomeFeatures.withSugarCaneAndPumpkins(builder2);
        DefaultBiomeFeatures.withLavaAndWaterSprings(builder2);
        DefaultBiomeFeatures.withFrozenTopLayer(builder2);
        return new Biome.Builder().precipitation(bl ? Biome.RainType.SNOW : Biome.RainType.RAIN).category(bl2 ? Biome.Category.NONE : Biome.Category.BEACH).depth(f).scale(f2).temperature(f3).downfall(f4).setEffects(new BiomeAmbience.Builder().setWaterColor(n).setWaterFogColor(329011).setFogColor(12638463).withSkyColor(BiomeMaker.getSkyColorWithTemperatureModifier(f3)).setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build()).withMobSpawnSettings(builder.copy()).withGenerationSettings(builder2.build()).build();
    }

    public static Biome makeVoidBiome() {
        BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder().withSurfaceBuilder(ConfiguredSurfaceBuilders.field_244184_p);
        builder.withFeature(GenerationStage.Decoration.TOP_LAYER_MODIFICATION, Features.VOID_START_PLATFORM);
        return new Biome.Builder().precipitation(Biome.RainType.NONE).category(Biome.Category.NONE).depth(0.1f).scale(0.2f).temperature(0.5f).downfall(0.5f).setEffects(new BiomeAmbience.Builder().setWaterColor(4159204).setWaterFogColor(329011).setFogColor(12638463).withSkyColor(BiomeMaker.getSkyColorWithTemperatureModifier(0.5f)).setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build()).withMobSpawnSettings(MobSpawnInfo.EMPTY).withGenerationSettings(builder.build()).build();
    }

    public static Biome makeNetherWastesBiome() {
        MobSpawnInfo mobSpawnInfo = new MobSpawnInfo.Builder().withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.GHAST, 50, 4, 4)).withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.ZOMBIFIED_PIGLIN, 100, 4, 4)).withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.MAGMA_CUBE, 2, 4, 4)).withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.ENDERMAN, 1, 4, 4)).withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.PIGLIN, 15, 4, 4)).withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.STRIDER, 60, 1, 2)).copy();
        BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder().withSurfaceBuilder(ConfiguredSurfaceBuilders.field_244183_o).withStructure(StructureFeatures.field_244134_E).withStructure(StructureFeatures.field_244149_o).withStructure(StructureFeatures.field_244153_s).withCarver(GenerationStage.Carving.AIR, ConfiguredCarvers.field_243772_f).withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.SPRING_LAVA);
        DefaultBiomeFeatures.withNormalMushroomGeneration(builder);
        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.SPRING_OPEN).withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.PATCH_FIRE).withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.PATCH_SOUL_FIRE).withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.GLOWSTONE_EXTRA).withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.GLOWSTONE).withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.BROWN_MUSHROOM_NETHER).withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.RED_MUSHROOM_NETHER).withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.ORE_MAGMA).withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.SPRING_CLOSED);
        DefaultBiomeFeatures.withCommonNetherBlocks(builder);
        return new Biome.Builder().precipitation(Biome.RainType.NONE).category(Biome.Category.NETHER).depth(0.1f).scale(0.2f).temperature(2.0f).downfall(0.0f).setEffects(new BiomeAmbience.Builder().setWaterColor(4159204).setWaterFogColor(329011).setFogColor(0x330808).withSkyColor(BiomeMaker.getSkyColorWithTemperatureModifier(2.0f)).setAmbientSound(SoundEvents.AMBIENT_NETHER_WASTES_LOOP).setMoodSound(new MoodSoundAmbience(SoundEvents.AMBIENT_NETHER_WASTES_MOOD, 6000, 8, 2.0)).setAdditionsSound(new SoundAdditionsAmbience(SoundEvents.AMBIENT_NETHER_WASTES_ADDITIONS, 0.0111)).setMusic(BackgroundMusicTracks.getDefaultBackgroundMusicSelector(SoundEvents.MUSIC_NETHER_NETHER_WASTES)).build()).withMobSpawnSettings(mobSpawnInfo).withGenerationSettings(builder.build()).build();
    }

    public static Biome makeSoulSandValleyBiome() {
        double d = 0.7;
        double d2 = 0.15;
        MobSpawnInfo mobSpawnInfo = new MobSpawnInfo.Builder().withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.SKELETON, 20, 5, 5)).withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.GHAST, 50, 4, 4)).withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.ENDERMAN, 1, 4, 4)).withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.STRIDER, 60, 1, 2)).withSpawnCost(EntityType.SKELETON, 0.7, 0.15).withSpawnCost(EntityType.GHAST, 0.7, 0.15).withSpawnCost(EntityType.ENDERMAN, 0.7, 0.15).withSpawnCost(EntityType.STRIDER, 0.7, 0.15).copy();
        BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder().withSurfaceBuilder(ConfiguredSurfaceBuilders.field_244187_s).withStructure(StructureFeatures.field_244149_o).withStructure(StructureFeatures.field_244150_p).withStructure(StructureFeatures.field_244134_E).withStructure(StructureFeatures.field_244153_s).withCarver(GenerationStage.Carving.AIR, ConfiguredCarvers.field_243772_f).withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.SPRING_LAVA).withFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Features.BASALT_PILLAR).withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.SPRING_OPEN).withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.GLOWSTONE_EXTRA).withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.GLOWSTONE).withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.PATCH_CRIMSON_ROOTS).withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.PATCH_FIRE).withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.PATCH_SOUL_FIRE).withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.ORE_MAGMA).withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.SPRING_CLOSED).withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.ORE_SOUL_SAND);
        DefaultBiomeFeatures.withCommonNetherBlocks(builder);
        return new Biome.Builder().precipitation(Biome.RainType.NONE).category(Biome.Category.NETHER).depth(0.1f).scale(0.2f).temperature(2.0f).downfall(0.0f).setEffects(new BiomeAmbience.Builder().setWaterColor(4159204).setWaterFogColor(329011).setFogColor(1787717).withSkyColor(BiomeMaker.getSkyColorWithTemperatureModifier(2.0f)).setParticle(new ParticleEffectAmbience(ParticleTypes.ASH, 0.00625f)).setAmbientSound(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_LOOP).setMoodSound(new MoodSoundAmbience(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_MOOD, 6000, 8, 2.0)).setAdditionsSound(new SoundAdditionsAmbience(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_ADDITIONS, 0.0111)).setMusic(BackgroundMusicTracks.getDefaultBackgroundMusicSelector(SoundEvents.MUSIC_NETHER_SOUL_SAND_VALLEY)).build()).withMobSpawnSettings(mobSpawnInfo).withGenerationSettings(builder.build()).build();
    }

    public static Biome makeBasaltDeltasBiome() {
        MobSpawnInfo mobSpawnInfo = new MobSpawnInfo.Builder().withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.GHAST, 40, 1, 1)).withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.MAGMA_CUBE, 100, 2, 5)).withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.STRIDER, 60, 1, 2)).copy();
        BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder().withSurfaceBuilder(ConfiguredSurfaceBuilders.field_244170_b).withStructure(StructureFeatures.field_244134_E).withCarver(GenerationStage.Carving.AIR, ConfiguredCarvers.field_243772_f).withStructure(StructureFeatures.field_244149_o).withFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Features.DELTA).withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.SPRING_LAVA_DOUBLE).withFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Features.SMALL_BASALT_COLUMNS).withFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Features.LARGE_BASALT_COLUMNS).withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.BASALT_BLOBS).withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.BLACKSTONE_BLOBS).withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.SPRING_DELTA).withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.PATCH_FIRE).withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.PATCH_SOUL_FIRE).withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.GLOWSTONE_EXTRA).withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.GLOWSTONE).withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.BROWN_MUSHROOM_NETHER).withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.RED_MUSHROOM_NETHER).withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.ORE_MAGMA).withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.SPRING_CLOSED_DOUBLE).withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.ORE_GOLD_DELTAS).withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.ORE_QUARTZ_DELTAS);
        DefaultBiomeFeatures.withDebrisOre(builder);
        return new Biome.Builder().precipitation(Biome.RainType.NONE).category(Biome.Category.NETHER).depth(0.1f).scale(0.2f).temperature(2.0f).downfall(0.0f).setEffects(new BiomeAmbience.Builder().setWaterColor(4159204).setWaterFogColor(4341314).setFogColor(6840176).withSkyColor(BiomeMaker.getSkyColorWithTemperatureModifier(2.0f)).setParticle(new ParticleEffectAmbience(ParticleTypes.WHITE_ASH, 0.118093334f)).setAmbientSound(SoundEvents.AMBIENT_BASALT_DELTAS_LOOP).setMoodSound(new MoodSoundAmbience(SoundEvents.AMBIENT_BASALT_DELTAS_MOOD, 6000, 8, 2.0)).setAdditionsSound(new SoundAdditionsAmbience(SoundEvents.AMBIENT_BASALT_DELTAS_ADDITIONS, 0.0111)).setMusic(BackgroundMusicTracks.getDefaultBackgroundMusicSelector(SoundEvents.MUSIC_NETHER_BASALT_DELTAS)).build()).withMobSpawnSettings(mobSpawnInfo).withGenerationSettings(builder.build()).build();
    }

    public static Biome makeCrimsonForestBiome() {
        MobSpawnInfo mobSpawnInfo = new MobSpawnInfo.Builder().withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.ZOMBIFIED_PIGLIN, 1, 2, 4)).withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.HOGLIN, 9, 3, 4)).withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.PIGLIN, 5, 3, 4)).withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.STRIDER, 60, 1, 2)).copy();
        BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder().withSurfaceBuilder(ConfiguredSurfaceBuilders.field_244171_c).withStructure(StructureFeatures.field_244134_E).withCarver(GenerationStage.Carving.AIR, ConfiguredCarvers.field_243772_f).withStructure(StructureFeatures.field_244149_o).withStructure(StructureFeatures.field_244153_s).withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.SPRING_LAVA);
        DefaultBiomeFeatures.withNormalMushroomGeneration(builder);
        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.SPRING_OPEN).withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.PATCH_FIRE).withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.GLOWSTONE_EXTRA).withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.GLOWSTONE).withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.ORE_MAGMA).withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.SPRING_CLOSED).withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.WEEPING_VINES).withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.CRIMSON_FUNGI).withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.CRIMSON_FOREST_VEGETATION);
        DefaultBiomeFeatures.withCommonNetherBlocks(builder);
        return new Biome.Builder().precipitation(Biome.RainType.NONE).category(Biome.Category.NETHER).depth(0.1f).scale(0.2f).temperature(2.0f).downfall(0.0f).setEffects(new BiomeAmbience.Builder().setWaterColor(4159204).setWaterFogColor(329011).setFogColor(0x330303).withSkyColor(BiomeMaker.getSkyColorWithTemperatureModifier(2.0f)).setParticle(new ParticleEffectAmbience(ParticleTypes.CRIMSON_SPORE, 0.025f)).setAmbientSound(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP).setMoodSound(new MoodSoundAmbience(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD, 6000, 8, 2.0)).setAdditionsSound(new SoundAdditionsAmbience(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS, 0.0111)).setMusic(BackgroundMusicTracks.getDefaultBackgroundMusicSelector(SoundEvents.MUSIC_NETHER_CRIMSON_FOREST)).build()).withMobSpawnSettings(mobSpawnInfo).withGenerationSettings(builder.build()).build();
    }

    public static Biome makeWarpedForestBiome() {
        MobSpawnInfo mobSpawnInfo = new MobSpawnInfo.Builder().withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.ENDERMAN, 1, 4, 4)).withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.STRIDER, 60, 1, 2)).withSpawnCost(EntityType.ENDERMAN, 1.0, 0.12).copy();
        BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder().withSurfaceBuilder(ConfiguredSurfaceBuilders.field_244190_v).withStructure(StructureFeatures.field_244149_o).withStructure(StructureFeatures.field_244153_s).withStructure(StructureFeatures.field_244134_E).withCarver(GenerationStage.Carving.AIR, ConfiguredCarvers.field_243772_f).withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.SPRING_LAVA);
        DefaultBiomeFeatures.withNormalMushroomGeneration(builder);
        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.SPRING_OPEN).withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.PATCH_FIRE).withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.PATCH_SOUL_FIRE).withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.GLOWSTONE_EXTRA).withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.GLOWSTONE).withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.ORE_MAGMA).withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.SPRING_CLOSED).withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.WARPED_FUNGI).withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.WARPED_FOREST_VEGETATION).withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.NETHER_SPROUTS).withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.TWISTING_VINES);
        DefaultBiomeFeatures.withCommonNetherBlocks(builder);
        return new Biome.Builder().precipitation(Biome.RainType.NONE).category(Biome.Category.NETHER).depth(0.1f).scale(0.2f).temperature(2.0f).downfall(0.0f).setEffects(new BiomeAmbience.Builder().setWaterColor(4159204).setWaterFogColor(329011).setFogColor(1705242).withSkyColor(BiomeMaker.getSkyColorWithTemperatureModifier(2.0f)).setParticle(new ParticleEffectAmbience(ParticleTypes.WARPED_SPORE, 0.01428f)).setAmbientSound(SoundEvents.AMBIENT_WARPED_FOREST_LOOP).setMoodSound(new MoodSoundAmbience(SoundEvents.AMBIENT_WARPED_FOREST_MOOD, 6000, 8, 2.0)).setAdditionsSound(new SoundAdditionsAmbience(SoundEvents.AMBIENT_WARPED_FOREST_ADDITIONS, 0.0111)).setMusic(BackgroundMusicTracks.getDefaultBackgroundMusicSelector(SoundEvents.MUSIC_NETHER_WARPED_FOREST)).build()).withMobSpawnSettings(mobSpawnInfo).withGenerationSettings(builder.build()).build();
    }
}

