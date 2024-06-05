package net.minecraft.src;

import java.util.*;
import java.awt.*;

public abstract class BiomeGenBase
{
    public static final BiomeGenBase[] biomeList;
    public static final BiomeGenBase ocean;
    public static final BiomeGenBase plains;
    public static final BiomeGenBase desert;
    public static final BiomeGenBase extremeHills;
    public static final BiomeGenBase forest;
    public static final BiomeGenBase taiga;
    public static final BiomeGenBase swampland;
    public static final BiomeGenBase river;
    public static final BiomeGenBase hell;
    public static final BiomeGenBase sky;
    public static final BiomeGenBase frozenOcean;
    public static final BiomeGenBase frozenRiver;
    public static final BiomeGenBase icePlains;
    public static final BiomeGenBase iceMountains;
    public static final BiomeGenBase mushroomIsland;
    public static final BiomeGenBase mushroomIslandShore;
    public static final BiomeGenBase beach;
    public static final BiomeGenBase desertHills;
    public static final BiomeGenBase forestHills;
    public static final BiomeGenBase taigaHills;
    public static final BiomeGenBase extremeHillsEdge;
    public static final BiomeGenBase jungle;
    public static final BiomeGenBase jungleHills;
    public String biomeName;
    public int color;
    public byte topBlock;
    public byte fillerBlock;
    public int field_76754_C;
    public float minHeight;
    public float maxHeight;
    public float temperature;
    public float rainfall;
    public int waterColorMultiplier;
    public BiomeDecorator theBiomeDecorator;
    protected List spawnableMonsterList;
    protected List spawnableCreatureList;
    protected List spawnableWaterCreatureList;
    protected List spawnableCaveCreatureList;
    private boolean enableSnow;
    private boolean enableRain;
    public final int biomeID;
    protected WorldGenTrees worldGeneratorTrees;
    protected WorldGenBigTree worldGeneratorBigTree;
    protected WorldGenForest worldGeneratorForest;
    protected WorldGenSwamp worldGeneratorSwamp;
    
    static {
        biomeList = new BiomeGenBase[256];
        ocean = new BiomeGenOcean(0).setColor(112).setBiomeName("Ocean").setMinMaxHeight(-1.0f, 0.4f);
        plains = new BiomeGenPlains(1).setColor(9286496).setBiomeName("Plains").setTemperatureRainfall(0.8f, 0.4f);
        desert = new BiomeGenDesert(2).setColor(16421912).setBiomeName("Desert").setDisableRain().setTemperatureRainfall(2.0f, 0.0f).setMinMaxHeight(0.1f, 0.2f);
        extremeHills = new BiomeGenHills(3).setColor(6316128).setBiomeName("Extreme Hills").setMinMaxHeight(0.3f, 1.5f).setTemperatureRainfall(0.2f, 0.3f);
        forest = new BiomeGenForest(4).setColor(353825).setBiomeName("Forest").func_76733_a(5159473).setTemperatureRainfall(0.7f, 0.8f);
        taiga = new BiomeGenTaiga(5).setColor(747097).setBiomeName("Taiga").func_76733_a(5159473).setEnableSnow().setTemperatureRainfall(0.05f, 0.8f).setMinMaxHeight(0.1f, 0.4f);
        swampland = new BiomeGenSwamp(6).setColor(522674).setBiomeName("Swampland").func_76733_a(9154376).setMinMaxHeight(-0.2f, 0.1f).setTemperatureRainfall(0.8f, 0.9f);
        river = new BiomeGenRiver(7).setColor(255).setBiomeName("River").setMinMaxHeight(-0.5f, 0.0f);
        hell = new BiomeGenHell(8).setColor(16711680).setBiomeName("Hell").setDisableRain().setTemperatureRainfall(2.0f, 0.0f);
        sky = new BiomeGenEnd(9).setColor(8421631).setBiomeName("Sky").setDisableRain();
        frozenOcean = new BiomeGenOcean(10).setColor(9474208).setBiomeName("FrozenOcean").setEnableSnow().setMinMaxHeight(-1.0f, 0.5f).setTemperatureRainfall(0.0f, 0.5f);
        frozenRiver = new BiomeGenRiver(11).setColor(10526975).setBiomeName("FrozenRiver").setEnableSnow().setMinMaxHeight(-0.5f, 0.0f).setTemperatureRainfall(0.0f, 0.5f);
        icePlains = new BiomeGenSnow(12).setColor(16777215).setBiomeName("Ice Plains").setEnableSnow().setTemperatureRainfall(0.0f, 0.5f);
        iceMountains = new BiomeGenSnow(13).setColor(10526880).setBiomeName("Ice Mountains").setEnableSnow().setMinMaxHeight(0.3f, 1.3f).setTemperatureRainfall(0.0f, 0.5f);
        mushroomIsland = new BiomeGenMushroomIsland(14).setColor(16711935).setBiomeName("MushroomIsland").setTemperatureRainfall(0.9f, 1.0f).setMinMaxHeight(0.2f, 1.0f);
        mushroomIslandShore = new BiomeGenMushroomIsland(15).setColor(10486015).setBiomeName("MushroomIslandShore").setTemperatureRainfall(0.9f, 1.0f).setMinMaxHeight(-1.0f, 0.1f);
        beach = new BiomeGenBeach(16).setColor(16440917).setBiomeName("Beach").setTemperatureRainfall(0.8f, 0.4f).setMinMaxHeight(0.0f, 0.1f);
        desertHills = new BiomeGenDesert(17).setColor(13786898).setBiomeName("DesertHills").setDisableRain().setTemperatureRainfall(2.0f, 0.0f).setMinMaxHeight(0.3f, 0.8f);
        forestHills = new BiomeGenForest(18).setColor(2250012).setBiomeName("ForestHills").func_76733_a(5159473).setTemperatureRainfall(0.7f, 0.8f).setMinMaxHeight(0.3f, 0.7f);
        taigaHills = new BiomeGenTaiga(19).setColor(1456435).setBiomeName("TaigaHills").setEnableSnow().func_76733_a(5159473).setTemperatureRainfall(0.05f, 0.8f).setMinMaxHeight(0.3f, 0.8f);
        extremeHillsEdge = new BiomeGenHills(20).setColor(7501978).setBiomeName("Extreme Hills Edge").setMinMaxHeight(0.2f, 0.8f).setTemperatureRainfall(0.2f, 0.3f);
        jungle = new BiomeGenJungle(21).setColor(5470985).setBiomeName("Jungle").func_76733_a(5470985).setTemperatureRainfall(1.2f, 0.9f).setMinMaxHeight(0.2f, 0.4f);
        jungleHills = new BiomeGenJungle(22).setColor(2900485).setBiomeName("JungleHills").func_76733_a(5470985).setTemperatureRainfall(1.2f, 0.9f).setMinMaxHeight(1.8f, 0.5f);
    }
    
    protected BiomeGenBase(final int par1) {
        this.topBlock = (byte)Block.grass.blockID;
        this.fillerBlock = (byte)Block.dirt.blockID;
        this.field_76754_C = 5169201;
        this.minHeight = 0.1f;
        this.maxHeight = 0.3f;
        this.temperature = 0.5f;
        this.rainfall = 0.5f;
        this.waterColorMultiplier = 16777215;
        this.spawnableMonsterList = new ArrayList();
        this.spawnableCreatureList = new ArrayList();
        this.spawnableWaterCreatureList = new ArrayList();
        this.spawnableCaveCreatureList = new ArrayList();
        this.enableRain = true;
        this.worldGeneratorTrees = new WorldGenTrees(false);
        this.worldGeneratorBigTree = new WorldGenBigTree(false);
        this.worldGeneratorForest = new WorldGenForest(false);
        this.worldGeneratorSwamp = new WorldGenSwamp();
        this.biomeID = par1;
        BiomeGenBase.biomeList[par1] = this;
        this.theBiomeDecorator = this.createBiomeDecorator();
        this.spawnableCreatureList.add(new SpawnListEntry(EntitySheep.class, 12, 4, 4));
        this.spawnableCreatureList.add(new SpawnListEntry(EntityPig.class, 10, 4, 4));
        this.spawnableCreatureList.add(new SpawnListEntry(EntityChicken.class, 10, 4, 4));
        this.spawnableCreatureList.add(new SpawnListEntry(EntityCow.class, 8, 4, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntitySpider.class, 10, 4, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityZombie.class, 10, 4, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntitySkeleton.class, 10, 4, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityCreeper.class, 10, 4, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntitySlime.class, 10, 4, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityEnderman.class, 1, 1, 4));
        this.spawnableWaterCreatureList.add(new SpawnListEntry(EntitySquid.class, 10, 4, 4));
        this.spawnableCaveCreatureList.add(new SpawnListEntry(EntityBat.class, 10, 8, 8));
    }
    
    protected BiomeDecorator createBiomeDecorator() {
        return new BiomeDecorator(this);
    }
    
    private BiomeGenBase setTemperatureRainfall(final float par1, final float par2) {
        if (par1 > 0.1f && par1 < 0.2f) {
            throw new IllegalArgumentException("Please avoid temperatures in the range 0.1 - 0.2 because of snow");
        }
        this.temperature = par1;
        this.rainfall = par2;
        return this;
    }
    
    private BiomeGenBase setMinMaxHeight(final float par1, final float par2) {
        this.minHeight = par1;
        this.maxHeight = par2;
        return this;
    }
    
    private BiomeGenBase setDisableRain() {
        this.enableRain = false;
        return this;
    }
    
    public WorldGenerator getRandomWorldGenForTrees(final Random par1Random) {
        return (par1Random.nextInt(10) == 0) ? this.worldGeneratorBigTree : this.worldGeneratorTrees;
    }
    
    public WorldGenerator getRandomWorldGenForGrass(final Random par1Random) {
        return new WorldGenTallGrass(Block.tallGrass.blockID, 1);
    }
    
    protected BiomeGenBase setEnableSnow() {
        this.enableSnow = true;
        return this;
    }
    
    protected BiomeGenBase setBiomeName(final String par1Str) {
        this.biomeName = par1Str;
        return this;
    }
    
    protected BiomeGenBase func_76733_a(final int par1) {
        this.field_76754_C = par1;
        return this;
    }
    
    protected BiomeGenBase setColor(final int par1) {
        this.color = par1;
        return this;
    }
    
    public int getSkyColorByTemp(float par1) {
        par1 /= 3.0f;
        if (par1 < -1.0f) {
            par1 = -1.0f;
        }
        if (par1 > 1.0f) {
            par1 = 1.0f;
        }
        return Color.getHSBColor(0.62222224f - par1 * 0.05f, 0.5f + par1 * 0.1f, 1.0f).getRGB();
    }
    
    public List getSpawnableList(final EnumCreatureType par1EnumCreatureType) {
        return (par1EnumCreatureType == EnumCreatureType.monster) ? this.spawnableMonsterList : ((par1EnumCreatureType == EnumCreatureType.creature) ? this.spawnableCreatureList : ((par1EnumCreatureType == EnumCreatureType.waterCreature) ? this.spawnableWaterCreatureList : ((par1EnumCreatureType == EnumCreatureType.ambient) ? this.spawnableCaveCreatureList : null)));
    }
    
    public boolean getEnableSnow() {
        return this.enableSnow;
    }
    
    public boolean canSpawnLightningBolt() {
        return !this.enableSnow && this.enableRain;
    }
    
    public boolean isHighHumidity() {
        return this.rainfall > 0.85f;
    }
    
    public float getSpawningChance() {
        return 0.1f;
    }
    
    public final int getIntRainfall() {
        return (int)(this.rainfall * 65536.0f);
    }
    
    public final int getIntTemperature() {
        return (int)(this.temperature * 65536.0f);
    }
    
    public final float getFloatRainfall() {
        return this.rainfall;
    }
    
    public final float getFloatTemperature() {
        return this.temperature;
    }
    
    public void decorate(final World par1World, final Random par2Random, final int par3, final int par4) {
        this.theBiomeDecorator.decorate(par1World, par2Random, par3, par4);
    }
    
    public int getBiomeGrassColor() {
        final double var1 = MathHelper.clamp_float(this.getFloatTemperature(), 0.0f, 1.0f);
        final double var2 = MathHelper.clamp_float(this.getFloatRainfall(), 0.0f, 1.0f);
        return ColorizerGrass.getGrassColor(var1, var2);
    }
    
    public int getBiomeFoliageColor() {
        final double var1 = MathHelper.clamp_float(this.getFloatTemperature(), 0.0f, 1.0f);
        final double var2 = MathHelper.clamp_float(this.getFloatRainfall(), 0.0f, 1.0f);
        return ColorizerFoliage.getFoliageColor(var1, var2);
    }
}
