package net.minecraft.world.biome;

import net.minecraft.block.state.*;
import net.minecraft.world.gen.*;
import net.minecraft.world.chunk.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import org.apache.logging.log4j.*;
import com.google.common.collect.*;
import net.minecraft.world.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.util.*;

public abstract class BiomeGenBase
{
    public float maxHeight;
    public IBlockState fillerBlock;
    public static final BiomeGenBase mushroomIslandShore;
    public static final BiomeGenBase jungleHills;
    protected static final WorldGenDoublePlant DOUBLE_PLANT_GENERATOR;
    public BiomeDecorator theBiomeDecorator;
    protected WorldGenSwamp worldGeneratorSwamp;
    public static final BiomeGenBase hell;
    public static final BiomeGenBase frozenOcean;
    public static final BiomeGenBase ocean;
    private static final BiomeGenBase[] biomeList;
    public static final Set<BiomeGenBase> explorationBiomesList;
    public final int biomeID;
    public static final BiomeGenBase mesaPlateau;
    protected static final Height height_Shores;
    public static final BiomeGenBase plains;
    protected static final NoiseGeneratorPerlin GRASS_COLOR_NOISE;
    protected static final Height height_RockyWaters;
    protected List<SpawnListEntry> spawnableCaveCreatureList;
    public static final BiomeGenBase extremeHillsEdge;
    public static final BiomeGenBase birchForestHills;
    public static final BiomeGenBase mesaPlateau_F;
    public static final BiomeGenBase birchForest;
    protected static final Height height_LowPlains;
    public static final BiomeGenBase forestHills;
    private static final String[] I;
    public int color;
    public static final BiomeGenBase field_180279_ad;
    public int waterColorMultiplier;
    public float temperature;
    protected List<SpawnListEntry> spawnableMonsterList;
    protected boolean enableSnow;
    public static final BiomeGenBase deepOcean;
    protected static final Height height_LowHills;
    protected List<SpawnListEntry> spawnableWaterCreatureList;
    protected static final Height height_Default;
    protected static final Height height_Oceans;
    public static final BiomeGenBase iceMountains;
    protected WorldGenBigTree worldGeneratorBigTree;
    protected static final NoiseGeneratorPerlin temperatureNoise;
    public String biomeName;
    public static final BiomeGenBase mushroomIsland;
    public static final BiomeGenBase taigaHills;
    protected WorldGenTrees worldGeneratorTrees;
    public static final BiomeGenBase roofedForest;
    public int fillerBlockMetadata;
    public static final BiomeGenBase icePlains;
    public static final Map<String, BiomeGenBase> BIOME_ID_MAP;
    public static final BiomeGenBase swampland;
    protected static final Height height_DeepOceans;
    public IBlockState topBlock;
    protected static final Height height_MidPlains;
    protected static final Height height_PartiallySubmerged;
    public static final BiomeGenBase desert;
    public static final BiomeGenBase coldBeach;
    private static int[] $SWITCH_TABLE$net$minecraft$entity$EnumCreatureType;
    public static final BiomeGenBase stoneBeach;
    protected List<SpawnListEntry> spawnableCreatureList;
    public static final BiomeGenBase megaTaiga;
    public static final BiomeGenBase savanna;
    public static final BiomeGenBase sky;
    protected static final Height height_ShallowWaters;
    public static final BiomeGenBase coldTaiga;
    public int field_150609_ah;
    public static final BiomeGenBase beach;
    public static final BiomeGenBase taiga;
    public static final BiomeGenBase jungleEdge;
    public float minHeight;
    public static final BiomeGenBase river;
    public float rainfall;
    public static final BiomeGenBase mesa;
    public static final BiomeGenBase coldTaigaHills;
    public static final BiomeGenBase extremeHillsPlus;
    protected static final Height height_HighPlateaus;
    public static final BiomeGenBase jungle;
    public static final BiomeGenBase desertHills;
    public static final BiomeGenBase frozenRiver;
    public static final BiomeGenBase forest;
    private static final Logger logger;
    protected static final Height height_LowIslands;
    protected boolean enableRain;
    public static final BiomeGenBase megaTaigaHills;
    public static final BiomeGenBase savannaPlateau;
    public static final BiomeGenBase extremeHills;
    protected static final Height height_MidHills;
    
    public boolean isHighHumidity() {
        if (this.rainfall > 0.85f) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public boolean isEqualTo(final BiomeGenBase biomeGenBase) {
        int n;
        if (biomeGenBase == this) {
            n = " ".length();
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else if (biomeGenBase == null) {
            n = "".length();
            "".length();
            if (-1 >= 0) {
                throw null;
            }
        }
        else if (this.getBiomeClass() == biomeGenBase.getBiomeClass()) {
            n = " ".length();
            "".length();
            if (4 == 3) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    public static BiomeGenBase getBiomeFromBiomeList(final int n, final BiomeGenBase biomeGenBase) {
        if (n >= 0 && n <= BiomeGenBase.biomeList.length) {
            final BiomeGenBase biomeGenBase2 = BiomeGenBase.biomeList[n];
            BiomeGenBase biomeGenBase3;
            if (biomeGenBase2 == null) {
                biomeGenBase3 = biomeGenBase;
                "".length();
                if (3 < 3) {
                    throw null;
                }
            }
            else {
                biomeGenBase3 = biomeGenBase2;
            }
            return biomeGenBase3;
        }
        BiomeGenBase.logger.warn(BiomeGenBase.I[0xAA ^ 0x87] + n + BiomeGenBase.I[0x64 ^ 0x4A]);
        return BiomeGenBase.ocean;
    }
    
    public final float getFloatTemperature(final BlockPos blockPos) {
        if (blockPos.getY() > (0x7E ^ 0x3E)) {
            return this.temperature - ((float)(BiomeGenBase.temperatureNoise.func_151601_a(blockPos.getX() * 1.0 / 8.0, blockPos.getZ() * 1.0 / 8.0) * 4.0) + blockPos.getY() - 64.0f) * 0.05f / 30.0f;
        }
        return this.temperature;
    }
    
    public boolean canSpawnLightningBolt() {
        int n;
        if (this.isSnowyBiome()) {
            n = "".length();
            "".length();
            if (2 >= 4) {
                throw null;
            }
        }
        else {
            n = (this.enableRain ? 1 : 0);
        }
        return n != 0;
    }
    
    protected BiomeGenBase createMutation() {
        return this.createMutatedBiome(this.biomeID + (61 + 108 - 118 + 77));
    }
    
    public static BiomeGenBase[] getBiomeGenArray() {
        return BiomeGenBase.biomeList;
    }
    
    public final void generateBiomeTerrain(final World world, final Random random, final ChunkPrimer chunkPrimer, final int n, final int n2, final double n3) {
        final int func_181545_F = world.func_181545_F();
        IBlockState blockState = this.topBlock;
        IBlockState blockState2 = this.fillerBlock;
        int n4 = -" ".length();
        final int n5 = (int)(n3 / 3.0 + 3.0 + random.nextDouble() * 0.25);
        final int n6 = n & (0xB0 ^ 0xBF);
        final int n7 = n2 & (0x70 ^ 0x7F);
        final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        int i = 47 + 197 + 4 + 7;
        "".length();
        if (0 >= 3) {
            throw null;
        }
        while (i >= 0) {
            if (i <= random.nextInt(0xA6 ^ 0xA3)) {
                chunkPrimer.setBlockState(n7, i, n6, Blocks.bedrock.getDefaultState());
                "".length();
                if (0 >= 1) {
                    throw null;
                }
            }
            else {
                final IBlockState blockState3 = chunkPrimer.getBlockState(n7, i, n6);
                if (blockState3.getBlock().getMaterial() == Material.air) {
                    n4 = -" ".length();
                    "".length();
                    if (0 == 2) {
                        throw null;
                    }
                }
                else if (blockState3.getBlock() == Blocks.stone) {
                    if (n4 == -" ".length()) {
                        if (n5 <= 0) {
                            blockState = null;
                            blockState2 = Blocks.stone.getDefaultState();
                            "".length();
                            if (0 < 0) {
                                throw null;
                            }
                        }
                        else if (i >= func_181545_F - (0x7A ^ 0x7E) && i <= func_181545_F + " ".length()) {
                            blockState = this.topBlock;
                            blockState2 = this.fillerBlock;
                        }
                        if (i < func_181545_F && (blockState == null || blockState.getBlock().getMaterial() == Material.air)) {
                            if (this.getFloatTemperature(mutableBlockPos.func_181079_c(n, i, n2)) < 0.15f) {
                                blockState = Blocks.ice.getDefaultState();
                                "".length();
                                if (3 < 3) {
                                    throw null;
                                }
                            }
                            else {
                                blockState = Blocks.water.getDefaultState();
                            }
                        }
                        n4 = n5;
                        if (i >= func_181545_F - " ".length()) {
                            chunkPrimer.setBlockState(n7, i, n6, blockState);
                            "".length();
                            if (4 < 3) {
                                throw null;
                            }
                        }
                        else if (i < func_181545_F - (0x4F ^ 0x48) - n5) {
                            blockState = null;
                            blockState2 = Blocks.stone.getDefaultState();
                            chunkPrimer.setBlockState(n7, i, n6, Blocks.gravel.getDefaultState());
                            "".length();
                            if (2 >= 4) {
                                throw null;
                            }
                        }
                        else {
                            chunkPrimer.setBlockState(n7, i, n6, blockState2);
                            "".length();
                            if (3 == 2) {
                                throw null;
                            }
                        }
                    }
                    else if (n4 > 0) {
                        --n4;
                        chunkPrimer.setBlockState(n7, i, n6, blockState2);
                        if (n4 == 0 && blockState2.getBlock() == Blocks.sand) {
                            n4 = random.nextInt(0x76 ^ 0x72) + Math.max("".length(), i - (0x55 ^ 0x6A));
                            IBlockState blockState4;
                            if (blockState2.getValue(BlockSand.VARIANT) == BlockSand.EnumType.RED_SAND) {
                                blockState4 = Blocks.red_sandstone.getDefaultState();
                                "".length();
                                if (1 >= 4) {
                                    throw null;
                                }
                            }
                            else {
                                blockState4 = Blocks.sandstone.getDefaultState();
                            }
                            blockState2 = blockState4;
                        }
                    }
                }
            }
            --i;
        }
    }
    
    protected BiomeGenBase setEnableSnow() {
        this.enableSnow = (" ".length() != 0);
        return this;
    }
    
    public void decorate(final World world, final Random random, final BlockPos blockPos) {
        this.theBiomeDecorator.decorate(world, random, this, blockPos);
    }
    
    public void genTerrainBlocks(final World world, final Random random, final ChunkPrimer chunkPrimer, final int n, final int n2, final double n3) {
        this.generateBiomeTerrain(world, random, chunkPrimer, n, n2, n3);
    }
    
    protected BiomeGenBase(final int biomeID) {
        this.topBlock = Blocks.grass.getDefaultState();
        this.fillerBlock = Blocks.dirt.getDefaultState();
        this.fillerBlockMetadata = 479698 + 3947122 - 1932004 + 2674385;
        this.minHeight = BiomeGenBase.height_Default.rootHeight;
        this.maxHeight = BiomeGenBase.height_Default.variation;
        this.temperature = 0.5f;
        this.rainfall = 0.5f;
        this.waterColorMultiplier = 4570092 + 11429415 - 15971288 + 16748996;
        this.spawnableMonsterList = (List<SpawnListEntry>)Lists.newArrayList();
        this.spawnableCreatureList = (List<SpawnListEntry>)Lists.newArrayList();
        this.spawnableWaterCreatureList = (List<SpawnListEntry>)Lists.newArrayList();
        this.spawnableCaveCreatureList = (List<SpawnListEntry>)Lists.newArrayList();
        this.enableRain = (" ".length() != 0);
        this.worldGeneratorTrees = new WorldGenTrees("".length() != 0);
        this.worldGeneratorBigTree = new WorldGenBigTree("".length() != 0);
        this.worldGeneratorSwamp = new WorldGenSwamp();
        this.biomeID = biomeID;
        BiomeGenBase.biomeList[biomeID] = this;
        this.theBiomeDecorator = this.createBiomeDecorator();
        this.spawnableCreatureList.add(new SpawnListEntry(EntitySheep.class, 0xA5 ^ 0xA9, 0x86 ^ 0x82, 0x4C ^ 0x48));
        this.spawnableCreatureList.add(new SpawnListEntry(EntityRabbit.class, 0x62 ^ 0x68, "   ".length(), "   ".length()));
        this.spawnableCreatureList.add(new SpawnListEntry(EntityPig.class, 0x8C ^ 0x86, 0x27 ^ 0x23, 0x6A ^ 0x6E));
        this.spawnableCreatureList.add(new SpawnListEntry(EntityChicken.class, 0x7F ^ 0x75, 0x3C ^ 0x38, 0x16 ^ 0x12));
        this.spawnableCreatureList.add(new SpawnListEntry(EntityCow.class, 0x8 ^ 0x0, 0x76 ^ 0x72, 0xC ^ 0x8));
        this.spawnableMonsterList.add(new SpawnListEntry(EntitySpider.class, 0x72 ^ 0x16, 0x68 ^ 0x6C, 0x8 ^ 0xC));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityZombie.class, 0xF7 ^ 0x93, 0x30 ^ 0x34, 0xB ^ 0xF));
        this.spawnableMonsterList.add(new SpawnListEntry(EntitySkeleton.class, 0x34 ^ 0x50, 0x45 ^ 0x41, 0x6D ^ 0x69));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityCreeper.class, 0xC6 ^ 0xA2, 0x60 ^ 0x64, 0xB6 ^ 0xB2));
        this.spawnableMonsterList.add(new SpawnListEntry(EntitySlime.class, 0x39 ^ 0x5D, 0x61 ^ 0x65, 0x3F ^ 0x3B));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityEnderman.class, 0x6C ^ 0x66, " ".length(), 0xA6 ^ 0xA2));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityWitch.class, 0xB7 ^ 0xB2, " ".length(), " ".length()));
        this.spawnableWaterCreatureList.add(new SpawnListEntry(EntitySquid.class, 0xBA ^ 0xB0, 0x8D ^ 0x89, 0x90 ^ 0x94));
        this.spawnableCaveCreatureList.add(new SpawnListEntry(EntityBat.class, 0x7B ^ 0x71, 0x4C ^ 0x44, 0x9A ^ 0x92));
    }
    
    protected BiomeGenBase setColor(final int n) {
        this.func_150557_a(n, "".length() != 0);
        return this;
    }
    
    public static BiomeGenBase getBiome(final int n) {
        return getBiomeFromBiomeList(n, null);
    }
    
    public boolean isSnowyBiome() {
        return this.enableSnow;
    }
    
    protected BiomeDecorator createBiomeDecorator() {
        return new BiomeDecorator();
    }
    
    public float getSpawningChance() {
        return 0.1f;
    }
    
    public boolean getEnableSnow() {
        return this.isSnowyBiome();
    }
    
    public final float getFloatRainfall() {
        return this.rainfall;
    }
    
    public int getFoliageColorAtPos(final BlockPos blockPos) {
        return ColorizerFoliage.getFoliageColor(MathHelper.clamp_float(this.getFloatTemperature(blockPos), 0.0f, 1.0f), MathHelper.clamp_float(this.getFloatRainfall(), 0.0f, 1.0f));
    }
    
    public WorldGenAbstractTree genBigTreeChance(final Random random) {
        WorldGenAbstractTree worldGenAbstractTree;
        if (random.nextInt(0xB6 ^ 0xBC) == 0) {
            worldGenAbstractTree = this.worldGeneratorBigTree;
            "".length();
            if (4 < -1) {
                throw null;
            }
        }
        else {
            worldGenAbstractTree = this.worldGeneratorTrees;
        }
        return worldGenAbstractTree;
    }
    
    static {
        I();
        logger = LogManager.getLogger();
        height_Default = new Height(0.1f, 0.2f);
        height_ShallowWaters = new Height(-0.5f, 0.0f);
        height_Oceans = new Height(-1.0f, 0.1f);
        height_DeepOceans = new Height(-1.8f, 0.1f);
        height_LowPlains = new Height(0.125f, 0.05f);
        height_MidPlains = new Height(0.2f, 0.2f);
        height_LowHills = new Height(0.45f, 0.3f);
        height_HighPlateaus = new Height(1.5f, 0.025f);
        height_MidHills = new Height(1.0f, 0.5f);
        height_Shores = new Height(0.0f, 0.025f);
        height_RockyWaters = new Height(0.1f, 0.8f);
        height_LowIslands = new Height(0.2f, 0.3f);
        height_PartiallySubmerged = new Height(-0.2f, 0.1f);
        biomeList = new BiomeGenBase[11 + 163 - 36 + 118];
        explorationBiomesList = Sets.newHashSet();
        BIOME_ID_MAP = Maps.newHashMap();
        ocean = new BiomeGenOcean("".length()).setColor(0xE8 ^ 0x98).setBiomeName(BiomeGenBase.I["".length()]).setHeight(BiomeGenBase.height_Oceans);
        plains = new BiomeGenPlains(" ".length()).setColor(5058686 + 618058 - 387800 + 3997552).setBiomeName(BiomeGenBase.I[" ".length()]);
        desert = new BiomeGenDesert("  ".length()).setColor(10952580 + 8226149 - 16315993 + 13559176).setBiomeName(BiomeGenBase.I["  ".length()]).setDisableRain().setTemperatureRainfall(2.0f, 0.0f).setHeight(BiomeGenBase.height_LowPlains);
        extremeHills = new BiomeGenHills("   ".length(), "".length() != 0).setColor(3561128 + 521804 - 620458 + 2853654).setBiomeName(BiomeGenBase.I["   ".length()]).setHeight(BiomeGenBase.height_MidHills).setTemperatureRainfall(0.2f, 0.3f);
        forest = new BiomeGenForest(0x1F ^ 0x1B, "".length()).setColor(274082 + 72186 - 313867 + 321424).setBiomeName(BiomeGenBase.I[0xC3 ^ 0xC7]);
        taiga = new BiomeGenTaiga(0xB6 ^ 0xB3, "".length()).setColor(262124 + 552994 - 760377 + 692356).setBiomeName(BiomeGenBase.I[0xA7 ^ 0xA2]).setFillerBlockMetadata(1917132 + 2563070 - 1233857 + 1913128).setTemperatureRainfall(0.25f, 0.8f).setHeight(BiomeGenBase.height_MidPlains);
        swampland = new BiomeGenSwamp(0x25 ^ 0x23).setColor(301577 + 306212 - 132950 + 47835).setBiomeName(BiomeGenBase.I[0x8F ^ 0x89]).setFillerBlockMetadata(2501651 + 835932 + 1982199 + 3834594).setHeight(BiomeGenBase.height_PartiallySubmerged).setTemperatureRainfall(0.8f, 0.9f);
        river = new BiomeGenRiver(0x6C ^ 0x6B).setColor(103 + 218 - 242 + 176).setBiomeName(BiomeGenBase.I[0x88 ^ 0x8F]).setHeight(BiomeGenBase.height_ShallowWaters);
        hell = new BiomeGenHell(0x0 ^ 0x8).setColor(9096597 + 15595525 - 14518109 + 6537667).setBiomeName(BiomeGenBase.I[0x20 ^ 0x28]).setDisableRain().setTemperatureRainfall(2.0f, 0.0f);
        sky = new BiomeGenEnd(0x5A ^ 0x53).setColor(4869191 + 2011591 - 1784983 + 3325832).setBiomeName(BiomeGenBase.I[0x61 ^ 0x68]).setDisableRain();
        frozenOcean = new BiomeGenOcean(0x61 ^ 0x6B).setColor(1780301 + 6203171 - 7605525 + 9096261).setBiomeName(BiomeGenBase.I[0xB7 ^ 0xBD]).setEnableSnow().setHeight(BiomeGenBase.height_Oceans).setTemperatureRainfall(0.0f, 0.5f);
        frozenRiver = new BiomeGenRiver(0x9A ^ 0x91).setColor(5645274 + 3527433 - 1278992 + 2633260).setBiomeName(BiomeGenBase.I[0x10 ^ 0x1B]).setEnableSnow().setHeight(BiomeGenBase.height_ShallowWaters).setTemperatureRainfall(0.0f, 0.5f);
        icePlains = new BiomeGenSnow(0x8 ^ 0x4, "".length() != 0).setColor(7100229 + 6778712 + 2156724 + 741550).setBiomeName(BiomeGenBase.I[0xB5 ^ 0xB9]).setEnableSnow().setTemperatureRainfall(0.0f, 0.5f).setHeight(BiomeGenBase.height_LowPlains);
        iceMountains = new BiomeGenSnow(0x3F ^ 0x32, "".length() != 0).setColor(3635140 + 698627 - 1551493 + 7744606).setBiomeName(BiomeGenBase.I[0x8B ^ 0x86]).setEnableSnow().setHeight(BiomeGenBase.height_LowHills).setTemperatureRainfall(0.0f, 0.5f);
        mushroomIsland = new BiomeGenMushroomIsland(0x7A ^ 0x74).setColor(32327 + 4979219 - 1353024 + 13053413).setBiomeName(BiomeGenBase.I[0xB7 ^ 0xB9]).setTemperatureRainfall(0.9f, 1.0f).setHeight(BiomeGenBase.height_LowIslands);
        mushroomIslandShore = new BiomeGenMushroomIsland(0x3C ^ 0x33).setColor(5157571 + 1082025 + 2550182 + 1696237).setBiomeName(BiomeGenBase.I[0xCA ^ 0xC5]).setTemperatureRainfall(0.9f, 1.0f).setHeight(BiomeGenBase.height_Shores);
        beach = new BiomeGenBeach(0xAA ^ 0xBA).setColor(6307884 + 13012663 - 3334309 + 454679).setBiomeName(BiomeGenBase.I[0x20 ^ 0x30]).setTemperatureRainfall(0.8f, 0.4f).setHeight(BiomeGenBase.height_Shores);
        desertHills = new BiomeGenDesert(0xB7 ^ 0xA6).setColor(10595355 + 3496875 - 9640583 + 9335251).setBiomeName(BiomeGenBase.I[0x83 ^ 0x92]).setDisableRain().setTemperatureRainfall(2.0f, 0.0f).setHeight(BiomeGenBase.height_LowHills);
        forestHills = new BiomeGenForest(0x88 ^ 0x9A, "".length()).setColor(1020314 + 1161826 - 1037818 + 1105690).setBiomeName(BiomeGenBase.I[0x1A ^ 0x8]).setHeight(BiomeGenBase.height_LowHills);
        taigaHills = new BiomeGenTaiga(0x5E ^ 0x4D, "".length()).setColor(500990 + 1080282 - 444713 + 319876).setBiomeName(BiomeGenBase.I[0x4A ^ 0x59]).setFillerBlockMetadata(173038 + 1127307 + 2994407 + 864721).setTemperatureRainfall(0.25f, 0.8f).setHeight(BiomeGenBase.height_LowHills);
        extremeHillsEdge = new BiomeGenHills(0xA4 ^ 0xB0, " ".length() != 0).setColor(2492550 + 1053691 - 1437985 + 5393722).setBiomeName(BiomeGenBase.I[0x25 ^ 0x31]).setHeight(BiomeGenBase.height_MidHills.attenuate()).setTemperatureRainfall(0.2f, 0.3f);
        jungle = new BiomeGenJungle(0x54 ^ 0x41, "".length() != 0).setColor(1741879 + 543700 - 1641430 + 4826836).setBiomeName(BiomeGenBase.I[0x5C ^ 0x49]).setFillerBlockMetadata(1625463 + 1616714 - 479513 + 2708321).setTemperatureRainfall(0.95f, 0.9f);
        jungleHills = new BiomeGenJungle(0x45 ^ 0x53, "".length() != 0).setColor(1709327 + 404909 - 818946 + 1605195).setBiomeName(BiomeGenBase.I[0x92 ^ 0x84]).setFillerBlockMetadata(2367256 + 3444501 - 1011658 + 670886).setTemperatureRainfall(0.95f, 0.9f).setHeight(BiomeGenBase.height_LowHills);
        jungleEdge = new BiomeGenJungle(0xD5 ^ 0xC2, " ".length() != 0).setColor(4829598 + 5072674 - 6980297 + 3536160).setBiomeName(BiomeGenBase.I[0x33 ^ 0x24]).setFillerBlockMetadata(1031785 + 3134581 - 2485113 + 3789732).setTemperatureRainfall(0.95f, 0.8f);
        deepOcean = new BiomeGenOcean(0x68 ^ 0x70).setColor(0x1D ^ 0x2D).setBiomeName(BiomeGenBase.I[0x25 ^ 0x3D]).setHeight(BiomeGenBase.height_DeepOceans);
        stoneBeach = new BiomeGenStoneBeach(0x93 ^ 0x8A).setColor(2896229 + 9501403 - 12137429 + 10398233).setBiomeName(BiomeGenBase.I[0x6D ^ 0x74]).setTemperatureRainfall(0.2f, 0.3f).setHeight(BiomeGenBase.height_RockyWaters);
        coldBeach = new BiomeGenBeach(0xAC ^ 0xB6).setColor(16195769 + 9331165 - 16291654 + 7210352).setBiomeName(BiomeGenBase.I[0xC ^ 0x16]).setTemperatureRainfall(0.05f, 0.3f).setHeight(BiomeGenBase.height_Shores).setEnableSnow();
        birchForest = new BiomeGenForest(0x30 ^ 0x2B, "  ".length()).setBiomeName(BiomeGenBase.I[0x45 ^ 0x5E]).setColor(2282403 + 230038 + 306053 + 356998);
        birchForestHills = new BiomeGenForest(0x22 ^ 0x3E, "  ".length()).setBiomeName(BiomeGenBase.I[0xA8 ^ 0xB4]).setColor(1744655 + 571241 - 488711 + 228801).setHeight(BiomeGenBase.height_LowHills);
        roofedForest = new BiomeGenForest(0x8D ^ 0x90, "   ".length()).setColor(1621133 + 1379221 - 1988395 + 3203107).setBiomeName(BiomeGenBase.I[0x9F ^ 0x82]);
        coldTaiga = new BiomeGenTaiga(0xAE ^ 0xB0, "".length()).setColor(782028 + 2163670 - 994523 + 1281923).setBiomeName(BiomeGenBase.I[0xDA ^ 0xC4]).setFillerBlockMetadata(1759709 + 928953 - 474283 + 2945094).setEnableSnow().setTemperatureRainfall(-0.5f, 0.4f).setHeight(BiomeGenBase.height_MidPlains).func_150563_c(4755680 + 1044423 + 2168476 + 8808636);
        coldTaigaHills = new BiomeGenTaiga(0x79 ^ 0x66, "".length()).setColor(1642062 + 1519878 - 1344206 + 557744).setBiomeName(BiomeGenBase.I[0x4 ^ 0x1B]).setFillerBlockMetadata(1775455 + 2781199 - 3982603 + 4585422).setEnableSnow().setTemperatureRainfall(-0.5f, 0.4f).setHeight(BiomeGenBase.height_LowHills).func_150563_c(6183087 + 3765410 + 143780 + 6684938);
        megaTaiga = new BiomeGenTaiga(0x57 ^ 0x77, " ".length()).setColor(858605 + 969627 + 3896562 + 134103).setBiomeName(BiomeGenBase.I[0x3F ^ 0x1F]).setFillerBlockMetadata(4516878 + 1534814 - 4042772 + 3150553).setTemperatureRainfall(0.3f, 0.8f).setHeight(BiomeGenBase.height_MidPlains);
        megaTaigaHills = new BiomeGenTaiga(0x83 ^ 0xA2, " ".length()).setColor(4377328 + 117035 - 2589743 + 2637650).setBiomeName(BiomeGenBase.I[0x37 ^ 0x16]).setFillerBlockMetadata(1586489 + 1395332 + 671228 + 1506424).setTemperatureRainfall(0.3f, 0.8f).setHeight(BiomeGenBase.height_LowHills);
        extremeHillsPlus = new BiomeGenHills(0x39 ^ 0x1B, " ".length() != 0).setColor(2563891 + 1092683 - 902397 + 2517455).setBiomeName(BiomeGenBase.I[0xE0 ^ 0xC2]).setHeight(BiomeGenBase.height_MidHills).setTemperatureRainfall(0.2f, 0.3f);
        savanna = new BiomeGenSavanna(0x2E ^ 0xD).setColor(8610570 + 2669781 - 6624289 + 7775905).setBiomeName(BiomeGenBase.I[0x9E ^ 0xBD]).setTemperatureRainfall(1.2f, 0.0f).setDisableRain().setHeight(BiomeGenBase.height_LowPlains);
        savannaPlateau = new BiomeGenSavanna(0x41 ^ 0x65).setColor(3141504 + 837142 - 476840 + 7482998).setBiomeName(BiomeGenBase.I[0xE ^ 0x2A]).setTemperatureRainfall(1.0f, 0.0f).setDisableRain().setHeight(BiomeGenBase.height_HighPlateaus);
        mesa = new BiomeGenMesa(0x22 ^ 0x7, "".length() != 0, "".length() != 0).setColor(10910769 + 3093604 - 1088709 + 1323333).setBiomeName(BiomeGenBase.I[0x7E ^ 0x5B]);
        mesaPlateau_F = new BiomeGenMesa(0x77 ^ 0x51, "".length() != 0, " ".length() != 0).setColor(3214037 + 11216918 - 11508461 + 8650599).setBiomeName(BiomeGenBase.I[0xE6 ^ 0xC0]).setHeight(BiomeGenBase.height_HighPlateaus);
        mesaPlateau = new BiomeGenMesa(0x27 ^ 0x0, "".length() != 0, "".length() != 0).setColor(5720321 + 2773148 + 4280067 + 500677).setBiomeName(BiomeGenBase.I[0x98 ^ 0xBF]).setHeight(BiomeGenBase.height_HighPlateaus);
        field_180279_ad = BiomeGenBase.ocean;
        BiomeGenBase.plains.createMutation();
        BiomeGenBase.desert.createMutation();
        BiomeGenBase.forest.createMutation();
        BiomeGenBase.taiga.createMutation();
        BiomeGenBase.swampland.createMutation();
        BiomeGenBase.icePlains.createMutation();
        BiomeGenBase.jungle.createMutation();
        BiomeGenBase.jungleEdge.createMutation();
        BiomeGenBase.coldTaiga.createMutation();
        BiomeGenBase.savanna.createMutation();
        BiomeGenBase.savannaPlateau.createMutation();
        BiomeGenBase.mesa.createMutation();
        BiomeGenBase.mesaPlateau_F.createMutation();
        BiomeGenBase.mesaPlateau.createMutation();
        BiomeGenBase.birchForest.createMutation();
        BiomeGenBase.birchForestHills.createMutation();
        BiomeGenBase.roofedForest.createMutation();
        BiomeGenBase.megaTaiga.createMutation();
        BiomeGenBase.extremeHills.createMutation();
        BiomeGenBase.extremeHillsPlus.createMutation();
        BiomeGenBase.megaTaiga.createMutatedBiome(BiomeGenBase.megaTaigaHills.biomeID + (73 + 8 + 20 + 27)).setBiomeName(BiomeGenBase.I[0x22 ^ 0xA]);
        final BiomeGenBase[] biomeList2;
        final int length = (biomeList2 = BiomeGenBase.biomeList).length;
        int i = "".length();
        "".length();
        if (4 <= 1) {
            throw null;
        }
        while (i < length) {
            final BiomeGenBase biomeGenBase = biomeList2[i];
            if (biomeGenBase != null) {
                if (BiomeGenBase.BIOME_ID_MAP.containsKey(biomeGenBase.biomeName)) {
                    throw new Error(BiomeGenBase.I[0x54 ^ 0x7D] + biomeGenBase.biomeName + BiomeGenBase.I[0x5C ^ 0x76] + BiomeGenBase.BIOME_ID_MAP.get(biomeGenBase.biomeName).biomeID + BiomeGenBase.I[0x74 ^ 0x5F] + biomeGenBase.biomeID);
                }
                BiomeGenBase.BIOME_ID_MAP.put(biomeGenBase.biomeName, biomeGenBase);
                if (biomeGenBase.biomeID < 74 + 15 - 76 + 115) {
                    BiomeGenBase.explorationBiomesList.add(biomeGenBase);
                }
            }
            ++i;
        }
        BiomeGenBase.explorationBiomesList.remove(BiomeGenBase.hell);
        BiomeGenBase.explorationBiomesList.remove(BiomeGenBase.sky);
        BiomeGenBase.explorationBiomesList.remove(BiomeGenBase.frozenOcean);
        BiomeGenBase.explorationBiomesList.remove(BiomeGenBase.extremeHillsEdge);
        temperatureNoise = new NoiseGeneratorPerlin(new Random(1234L), " ".length());
        GRASS_COLOR_NOISE = new NoiseGeneratorPerlin(new Random(2345L), " ".length());
        DOUBLE_PLANT_GENERATOR = new WorldGenDoublePlant();
    }
    
    public int getSkyColorByTemp(float clamp_float) {
        clamp_float /= 3.0f;
        clamp_float = MathHelper.clamp_float(clamp_float, -1.0f, 1.0f);
        return MathHelper.func_181758_c(0.62222224f - clamp_float * 0.05f, 0.5f + clamp_float * 0.1f, 1.0f);
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (0 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    protected final BiomeGenBase setHeight(final Height height) {
        this.minHeight = height.rootHeight;
        this.maxHeight = height.variation;
        return this;
    }
    
    public int getGrassColorAtPos(final BlockPos blockPos) {
        return ColorizerGrass.getGrassColor(MathHelper.clamp_float(this.getFloatTemperature(blockPos), 0.0f, 1.0f), MathHelper.clamp_float(this.getFloatRainfall(), 0.0f, 1.0f));
    }
    
    protected BiomeGenBase func_150557_a(final int n, final boolean b) {
        this.color = n;
        if (b) {
            this.field_150609_ah = (n & 2595938 + 7374471 - 7860116 + 14601129) >> " ".length();
            "".length();
            if (3 <= 0) {
                throw null;
            }
        }
        else {
            this.field_150609_ah = n;
        }
        return this;
    }
    
    public WorldGenerator getRandomWorldGenForGrass(final Random random) {
        return new WorldGenTallGrass(BlockTallGrass.EnumType.GRASS);
    }
    
    protected BiomeGenBase createMutatedBiome(final int n) {
        return new BiomeGenMutated(n, this);
    }
    
    public Class<? extends BiomeGenBase> getBiomeClass() {
        return this.getClass();
    }
    
    public TempCategory getTempCategory() {
        TempCategory tempCategory;
        if (this.temperature < 0.2) {
            tempCategory = TempCategory.COLD;
            "".length();
            if (-1 == 1) {
                throw null;
            }
        }
        else if (this.temperature < 1.0) {
            tempCategory = TempCategory.MEDIUM;
            "".length();
            if (1 <= -1) {
                throw null;
            }
        }
        else {
            tempCategory = TempCategory.WARM;
        }
        return tempCategory;
    }
    
    protected BiomeGenBase setTemperatureRainfall(final float temperature, final float rainfall) {
        if (temperature > 0.1f && temperature < 0.2f) {
            throw new IllegalArgumentException(BiomeGenBase.I[0x97 ^ 0xBB]);
        }
        this.temperature = temperature;
        this.rainfall = rainfall;
        return this;
    }
    
    protected BiomeGenBase setBiomeName(final String biomeName) {
        this.biomeName = biomeName;
        return this;
    }
    
    protected BiomeGenBase setDisableRain() {
        this.enableRain = ("".length() != 0);
        return this;
    }
    
    protected BiomeGenBase func_150563_c(final int field_150609_ah) {
        this.field_150609_ah = field_150609_ah;
        return this;
    }
    
    public BlockFlower.EnumFlowerType pickRandomFlower(final Random random, final BlockPos blockPos) {
        BlockFlower.EnumFlowerType enumFlowerType;
        if (random.nextInt("   ".length()) > 0) {
            enumFlowerType = BlockFlower.EnumFlowerType.DANDELION;
            "".length();
            if (2 == 0) {
                throw null;
            }
        }
        else {
            enumFlowerType = BlockFlower.EnumFlowerType.POPPY;
        }
        return enumFlowerType;
    }
    
    public List<SpawnListEntry> getSpawnableList(final EnumCreatureType enumCreatureType) {
        switch ($SWITCH_TABLE$net$minecraft$entity$EnumCreatureType()[enumCreatureType.ordinal()]) {
            case 1: {
                return this.spawnableMonsterList;
            }
            case 2: {
                return this.spawnableCreatureList;
            }
            case 4: {
                return this.spawnableWaterCreatureList;
            }
            case 3: {
                return this.spawnableCaveCreatureList;
            }
            default: {
                return Collections.emptyList();
            }
        }
    }
    
    protected BiomeGenBase setFillerBlockMetadata(final int fillerBlockMetadata) {
        this.fillerBlockMetadata = fillerBlockMetadata;
        return this;
    }
    
    private static void I() {
        (I = new String[0xA1 ^ 0x8E])["".length()] = I("#\"#0\t", "lAFQg");
        BiomeGenBase.I[" ".length()] = I("\u0002\u0000+\u001d4!", "RlJtZ");
        BiomeGenBase.I["  ".length()] = I("\u0006$\u0018&'6", "BAkCU");
        BiomeGenBase.I["   ".length()] = I("#25\u001b\"\u000b/a!.\n&2", "fJAiG");
        BiomeGenBase.I[0x8D ^ 0x89] = I("\n\u000b%\f<8", "LdWiO");
        BiomeGenBase.I[0x77 ^ 0x72] = I("\u0003&\u0019>\u000f", "WGpYn");
        BiomeGenBase.I[0x93 ^ 0x95] = I("\u001293\";-/<+", "ANROK");
        BiomeGenBase.I[0x2F ^ 0x28] = I("\u0013\u001f\u001803", "AvnUA");
        BiomeGenBase.I[0xBF ^ 0xB7] = I("\u000e\u0017\n\u0014", "Frfxc");
        BiomeGenBase.I[0x6 ^ 0xF] = I("\u0004\f r\">\u0000", "PdERg");
        BiomeGenBase.I[0x45 ^ 0x4F] = I("\" \u001a<\u0015\n\u001d\u0016#\u0011\n", "dRuFp");
        BiomeGenBase.I[0x1E ^ 0x15] = I("6\b\u001f\u001b*\u001e(\u0019\u0017*\u0002", "pzpaO");
        BiomeGenBase.I[0x8A ^ 0x86] = I("\u0007\u0014#b\u0006\"\u0016/,%", "NwFBV");
        BiomeGenBase.I[0x41 ^ 0x4C] = I("*3\u0017q\u001b\f%\u001c%7\n>\u0001", "cPrQV");
        BiomeGenBase.I[0x53 ^ 0x5D] = I("\f \t#\u0006.:\u0017\u0002\u0007-4\u0014/", "AUzKt");
        BiomeGenBase.I[0x56 ^ 0x59] = I("\"\u000f2!:\u0000\u0015,\u0000;\u0003\u001b/-\u001b\u0007\u00153,", "ozAIH");
        BiomeGenBase.I[0xA0 ^ 0xB0] = I("\u000e&\u0019%:", "LCxFR");
        BiomeGenBase.I[0x57 ^ 0x46] = I("!\u0007\u001141\u0011*\u000b=/\u0016", "ebbQC");
        BiomeGenBase.I[0x5D ^ 0x4F] = I("\u0014\u001f1*\u001d&8*#\u0002!", "RpCOn");
        BiomeGenBase.I[0xA1 ^ 0xB2] = I("\u0006\u000f\u000b6\u0015\u001a\u0007\u000e=\u0007", "RnbQt");
        BiomeGenBase.I[0x6F ^ 0x7B] = I("\u0002\t\f\u0002<*\u0014X80+\u001d\u000bP\u001c#\u0016\u001d", "GqxpY");
        BiomeGenBase.I[0xBB ^ 0xAE] = I("0\u0010/2\u0007\u001f", "zeAUk");
        BiomeGenBase.I[0xB8 ^ 0xAE] = I("2\u0005\u0001\u001e\b\u001d8\u0006\u0015\b\u000b", "xpoyd");
        BiomeGenBase.I[0x92 ^ 0x85] = I("\f!+\u0005%#\u0011!\u0005,", "FTEbI");
        BiomeGenBase.I[0x9B ^ 0x83] = I("'<)3y,:)\"7", "cYLCY");
        BiomeGenBase.I[0x7D ^ 0x64] = I("*!\u0017<\fY\u0017\u001d3\n\u0011", "yUxRi");
        BiomeGenBase.I[0xDA ^ 0xC0] = I("\"\u0017\u0018\tV#\u001d\u0015\u000e\u001e", "axtmv");
        BiomeGenBase.I[0x80 ^ 0x9B] = I("\u0006\f\u00033\u000ed#\u001e\"\u00037\u0011", "DeqPf");
        BiomeGenBase.I[0x98 ^ 0x84] = I("0\u001d6%:R2+47\u0001\u0000d\u000e;\u001e\u00187", "rtDFR");
        BiomeGenBase.I[0x18 ^ 0x5] = I("\u0018\n(\u0016\u0010.E\u0001\u001f\u0007/\u00163", "JeGpu");
        BiomeGenBase.I[0xA7 ^ 0xB9] = I("5\u0017\u0018\u0017n\"\u0019\u001d\u0014/", "vxtsN");
        BiomeGenBase.I[0x6C ^ 0x73] = I("\u0001(\u0007\u0017M\u0016&\u0002\u0014\fb\u000f\u0002\u001f\u00011", "BGksm");
        BiomeGenBase.I[0x81 ^ 0xA1] = I("\u0018..\u000eE\u0001* \b\u0004", "UKIoe");
        BiomeGenBase.I[0x9C ^ 0xBD] = I(":\u0015\u0001\u0004A#\u0011\u000f\u0002\u0000W8\u000f\t\r\u0004", "wpfea");
        BiomeGenBase.I[0x1 ^ 0x23] = I("4\r\r\u001e\u0013\u001c\u0010Y$\u001f\u001d\u0019\nG", "quylv");
        BiomeGenBase.I[0x21 ^ 0x2] = I("6,\u0013\u0004\r\u000b,", "eMeec");
        BiomeGenBase.I[0x76 ^ 0x52] = I("\u0010\b\u0012\u0002)-\bD3+\"\u001d\u0001\u00022", "CidcG");
        BiomeGenBase.I[0x92 ^ 0xB7] = I("&\u00005\u0011", "keFpA");
        BiomeGenBase.I[0xBC ^ 0x9A] = I("\u0003\u0004\u001b\u0004B\u001e\r\t\u0011\u0007/\u0014H#", "Naheb");
        BiomeGenBase.I[0x7A ^ 0x5D] = I("</\u001a\nt!&\b\u001f1\u0010?", "qJikT");
        BiomeGenBase.I[0x4E ^ 0x66] = I("*\u000f'\u0004$\u0017\u000ec'*\u0011\r\"S\u0003\u0011\u0006/\u0000k5", "xjCsK");
        BiomeGenBase.I[0xA9 ^ 0x80] = I("6\u00058!\"TN", "tlWLG");
        BiomeGenBase.I[0x6D ^ 0x47] = I("{d\u00078Q=!\b\"\u001f< N*\u0002y&\u0001?\u0019y\r*k", "YDnKq");
        BiomeGenBase.I[0x4 ^ 0x2F] = I("B97#c", "bXYGC");
        BiomeGenBase.I[0x79 ^ 0x55] = I("\u001a*39\u001a/f7.\u0006#\"v,\f'63*\b>3$=\u001aj/8x\u001d\"#v*\b$!3xYdwvuIzhdx\u000b/%7-\u001a/f9>I9(9/", "JFVXi");
        BiomeGenBase.I[0x9 ^ 0x24] = I("\r\r,\u0019&o-\u0007T*<D,\u00017o\u000b%T! \u0011-\u00100uD", "OdCtC");
        BiomeGenBase.I[0x88 ^ 0xA6] = I("ej\f7\u0010(?\u0004&\u001f'-H&\u0019izHz9*/\t<_", "IJhRv");
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$entity$EnumCreatureType() {
        final int[] $switch_TABLE$net$minecraft$entity$EnumCreatureType = BiomeGenBase.$SWITCH_TABLE$net$minecraft$entity$EnumCreatureType;
        if ($switch_TABLE$net$minecraft$entity$EnumCreatureType != null) {
            return $switch_TABLE$net$minecraft$entity$EnumCreatureType;
        }
        final int[] $switch_TABLE$net$minecraft$entity$EnumCreatureType2 = new int[EnumCreatureType.values().length];
        try {
            $switch_TABLE$net$minecraft$entity$EnumCreatureType2[EnumCreatureType.AMBIENT.ordinal()] = "   ".length();
            "".length();
            if (0 == 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$entity$EnumCreatureType2[EnumCreatureType.CREATURE.ordinal()] = "  ".length();
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$entity$EnumCreatureType2[EnumCreatureType.MONSTER.ordinal()] = " ".length();
            "".length();
            if (4 < 1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            $switch_TABLE$net$minecraft$entity$EnumCreatureType2[EnumCreatureType.WATER_CREATURE.ordinal()] = (0x5A ^ 0x5E);
            "".length();
            if (4 == -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        return BiomeGenBase.$SWITCH_TABLE$net$minecraft$entity$EnumCreatureType = $switch_TABLE$net$minecraft$entity$EnumCreatureType2;
    }
    
    public final int getIntRainfall() {
        return (int)(this.rainfall * 65536.0f);
    }
    
    public static class SpawnListEntry extends WeightedRandom.Item
    {
        public int minGroupCount;
        public Class<? extends EntityLiving> entityClass;
        public int maxGroupCount;
        private static final String[] I;
        
        @Override
        public String toString() {
            return String.valueOf(this.entityClass.getSimpleName()) + SpawnListEntry.I["".length()] + this.minGroupCount + SpawnListEntry.I[" ".length()] + this.maxGroupCount + SpawnListEntry.I["  ".length()] + this.itemWeight;
        }
        
        public SpawnListEntry(final Class<? extends EntityLiving> entityClass, final int n, final int minGroupCount, final int maxGroupCount) {
            super(n);
            this.entityClass = entityClass;
            this.minGroupCount = minGroupCount;
            this.maxGroupCount = maxGroupCount;
        }
        
        static {
            I();
        }
        
        private static String I(final String s, final String s2) {
            final StringBuilder sb = new StringBuilder();
            final char[] charArray = s2.toCharArray();
            int length = "".length();
            final char[] charArray2 = s.toCharArray();
            final int length2 = charArray2.length;
            int i = "".length();
            while (i < length2) {
                sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                ++length;
                ++i;
                "".length();
                if (0 >= 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private static void I() {
            (I = new String["   ".length()])["".length()] = I("h_", "BwqPK");
            SpawnListEntry.I[" ".length()] = I("x", "UqDRb");
            SpawnListEntry.I["  ".length()] = I("Br", "kHsJg");
        }
    }
    
    public static class Height
    {
        public float variation;
        public float rootHeight;
        
        public Height attenuate() {
            return new Height(this.rootHeight * 0.8f, this.variation * 0.6f);
        }
        
        public Height(final float rootHeight, final float variation) {
            this.rootHeight = rootHeight;
            this.variation = variation;
        }
        
        private static String I(final String s, final String s2) {
            final StringBuilder sb = new StringBuilder();
            final char[] charArray = s2.toCharArray();
            int length = "".length();
            final char[] charArray2 = s.toCharArray();
            final int length2 = charArray2.length;
            int i = "".length();
            while (i < length2) {
                sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                ++length;
                ++i;
                "".length();
                if (0 == 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
    
    public enum TempCategory
    {
        MEDIUM(TempCategory.I["  ".length()], "  ".length()), 
        COLD(TempCategory.I[" ".length()], " ".length());
        
        private static final String[] I;
        
        OCEAN(TempCategory.I["".length()], "".length());
        
        private static final TempCategory[] ENUM$VALUES;
        
        WARM(TempCategory.I["   ".length()], "   ".length());
        
        private static void I() {
            (I = new String[0x36 ^ 0x32])["".length()] = I("-\u0015\u0017\u0014\u001c", "bVRUR");
            TempCategory.I[" ".length()] = I("\u0013>\u0004\u0014", "PqHPO");
            TempCategory.I["  ".length()] = I("\u000f\u0006\u001e+\u0006\u000f", "BCZbS");
            TempCategory.I["   ".length()] = I("\u0019\u0000\"4", "NApyS");
        }
        
        private TempCategory(final String s, final int n) {
        }
        
        static {
            I();
            final TempCategory[] enum$VALUES = new TempCategory[0x1B ^ 0x1F];
            enum$VALUES["".length()] = TempCategory.OCEAN;
            enum$VALUES[" ".length()] = TempCategory.COLD;
            enum$VALUES["  ".length()] = TempCategory.MEDIUM;
            enum$VALUES["   ".length()] = TempCategory.WARM;
            ENUM$VALUES = enum$VALUES;
        }
        
        private static String I(final String s, final String s2) {
            final StringBuilder sb = new StringBuilder();
            final char[] charArray = s2.toCharArray();
            int length = "".length();
            final char[] charArray2 = s.toCharArray();
            final int length2 = charArray2.length;
            int i = "".length();
            while (i < length2) {
                sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                ++length;
                ++i;
                "".length();
                if (3 < 3) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
}
