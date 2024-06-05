package net.minecraft.world.biome;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import net.nA;
import net.minecraft.block.BlockFlower$EnumFlowerType;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSand$EnumType;
import net.minecraft.block.BlockTallGrass$EnumType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BlockPos$MutableBlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase$1;
import net.minecraft.world.biome.BiomeGenBase$Height;
import net.minecraft.world.biome.BiomeGenBase$TempCategory;
import net.minecraft.world.biome.BiomeGenBeach;
import net.minecraft.world.biome.BiomeGenDesert;
import net.minecraft.world.biome.BiomeGenEnd;
import net.minecraft.world.biome.BiomeGenForest;
import net.minecraft.world.biome.BiomeGenHell;
import net.minecraft.world.biome.BiomeGenHills;
import net.minecraft.world.biome.BiomeGenJungle;
import net.minecraft.world.biome.BiomeGenMesa;
import net.minecraft.world.biome.BiomeGenMushroomIsland;
import net.minecraft.world.biome.BiomeGenMutated;
import net.minecraft.world.biome.BiomeGenOcean;
import net.minecraft.world.biome.BiomeGenPlains;
import net.minecraft.world.biome.BiomeGenRiver;
import net.minecraft.world.biome.BiomeGenSavanna;
import net.minecraft.world.biome.BiomeGenSnow;
import net.minecraft.world.biome.BiomeGenStoneBeach;
import net.minecraft.world.biome.BiomeGenSwamp;
import net.minecraft.world.biome.BiomeGenTaiga;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenDoublePlant;
import net.minecraft.world.gen.feature.WorldGenSwamp;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.feature.WorldGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class BiomeGenBase {
   private static final Logger LOGGER = LogManager.getLogger();
   protected static final BiomeGenBase$Height height_Default = new BiomeGenBase$Height(0.1F, 0.2F);
   protected static final BiomeGenBase$Height height_ShallowWaters = new BiomeGenBase$Height(-0.5F, 0.0F);
   protected static final BiomeGenBase$Height height_Oceans = new BiomeGenBase$Height(-1.0F, 0.1F);
   protected static final BiomeGenBase$Height height_DeepOceans = new BiomeGenBase$Height(-1.8F, 0.1F);
   protected static final BiomeGenBase$Height height_LowPlains = new BiomeGenBase$Height(0.125F, 0.05F);
   protected static final BiomeGenBase$Height height_MidPlains = new BiomeGenBase$Height(0.2F, 0.2F);
   protected static final BiomeGenBase$Height height_LowHills = new BiomeGenBase$Height(0.45F, 0.3F);
   protected static final BiomeGenBase$Height height_HighPlateaus = new BiomeGenBase$Height(1.5F, 0.025F);
   protected static final BiomeGenBase$Height height_MidHills = new BiomeGenBase$Height(1.0F, 0.5F);
   protected static final BiomeGenBase$Height height_Shores = new BiomeGenBase$Height(0.0F, 0.025F);
   protected static final BiomeGenBase$Height height_RockyWaters = new BiomeGenBase$Height(0.1F, 0.8F);
   protected static final BiomeGenBase$Height height_LowIslands = new BiomeGenBase$Height(0.2F, 0.3F);
   protected static final BiomeGenBase$Height height_PartiallySubmerged = new BiomeGenBase$Height(-0.2F, 0.1F);
   private static final BiomeGenBase[] biomeList = new BiomeGenBase[256];
   public static final Set explorationBiomesList = Sets.newHashSet();
   public static final Map BIOME_ID_MAP = Maps.newHashMap();
   public static final BiomeGenBase ocean = (new BiomeGenOcean(0)).setColor(112).setBiomeName("Ocean").setHeight(height_Oceans);
   public static final BiomeGenBase plains = (new BiomeGenPlains(1)).setColor(9286496).setBiomeName("Plains");
   public static final BiomeGenBase desert = (new BiomeGenDesert(2)).setColor(16421912).setBiomeName("Desert").setDisableRain().setTemperatureRainfall(2.0F, 0.0F).setHeight(height_LowPlains);
   public static final BiomeGenBase extremeHills = (new BiomeGenHills(3, false)).setColor(6316128).setBiomeName("Extreme Hills").setHeight(height_MidHills).setTemperatureRainfall(0.2F, 0.3F);
   public static final BiomeGenBase forest = (new BiomeGenForest(4, 0)).setColor(353825).setBiomeName("Forest");
   public static final BiomeGenBase taiga = (new BiomeGenTaiga(5, 0)).setColor(747097).setBiomeName("Taiga").setFillerBlockMetadata(5159473).setTemperatureRainfall(0.25F, 0.8F).setHeight(height_MidPlains);
   public static final BiomeGenBase swampland = (new BiomeGenSwamp(6)).setColor(522674).setBiomeName("Swampland").setFillerBlockMetadata(9154376).setHeight(height_PartiallySubmerged).setTemperatureRainfall(0.8F, 0.9F);
   public static final BiomeGenBase river = (new BiomeGenRiver(7)).setColor(255).setBiomeName("River").setHeight(height_ShallowWaters);
   public static final BiomeGenBase hell = (new BiomeGenHell(8)).setColor(16711680).setBiomeName("Hell").setDisableRain().setTemperatureRainfall(2.0F, 0.0F);
   public static final BiomeGenBase sky = (new BiomeGenEnd(9)).setColor(8421631).setBiomeName("The End").setDisableRain();
   public static final BiomeGenBase frozenOcean = (new BiomeGenOcean(10)).setColor(9474208).setBiomeName("FrozenOcean").setEnableSnow().setHeight(height_Oceans).setTemperatureRainfall(0.0F, 0.5F);
   public static final BiomeGenBase frozenRiver = (new BiomeGenRiver(11)).setColor(10526975).setBiomeName("FrozenRiver").setEnableSnow().setHeight(height_ShallowWaters).setTemperatureRainfall(0.0F, 0.5F);
   public static final BiomeGenBase icePlains = (new BiomeGenSnow(12, false)).setColor(16777215).setBiomeName("Ice Plains").setEnableSnow().setTemperatureRainfall(0.0F, 0.5F).setHeight(height_LowPlains);
   public static final BiomeGenBase iceMountains = (new BiomeGenSnow(13, false)).setColor(10526880).setBiomeName("Ice Mountains").setEnableSnow().setHeight(height_LowHills).setTemperatureRainfall(0.0F, 0.5F);
   public static final BiomeGenBase mushroomIsland = (new BiomeGenMushroomIsland(14)).setColor(16711935).setBiomeName("MushroomIsland").setTemperatureRainfall(0.9F, 1.0F).setHeight(height_LowIslands);
   public static final BiomeGenBase mushroomIslandShore = (new BiomeGenMushroomIsland(15)).setColor(10486015).setBiomeName("MushroomIslandShore").setTemperatureRainfall(0.9F, 1.0F).setHeight(height_Shores);
   public static final BiomeGenBase beach = (new BiomeGenBeach(16)).setColor(16440917).setBiomeName("Beach").setTemperatureRainfall(0.8F, 0.4F).setHeight(height_Shores);
   public static final BiomeGenBase desertHills = (new BiomeGenDesert(17)).setColor(13786898).setBiomeName("DesertHills").setDisableRain().setTemperatureRainfall(2.0F, 0.0F).setHeight(height_LowHills);
   public static final BiomeGenBase forestHills = (new BiomeGenForest(18, 0)).setColor(2250012).setBiomeName("ForestHills").setHeight(height_LowHills);
   public static final BiomeGenBase taigaHills = (new BiomeGenTaiga(19, 0)).setColor(1456435).setBiomeName("TaigaHills").setFillerBlockMetadata(5159473).setTemperatureRainfall(0.25F, 0.8F).setHeight(height_LowHills);
   public static final BiomeGenBase extremeHillsEdge = (new BiomeGenHills(20, true)).setColor(7501978).setBiomeName("Extreme Hills Edge").setHeight(height_MidHills.attenuate()).setTemperatureRainfall(0.2F, 0.3F);
   public static final BiomeGenBase jungle = (new BiomeGenJungle(21, false)).setColor(5470985).setBiomeName("Jungle").setFillerBlockMetadata(5470985).setTemperatureRainfall(0.95F, 0.9F);
   public static final BiomeGenBase jungleHills = (new BiomeGenJungle(22, false)).setColor(2900485).setBiomeName("JungleHills").setFillerBlockMetadata(5470985).setTemperatureRainfall(0.95F, 0.9F).setHeight(height_LowHills);
   public static final BiomeGenBase jungleEdge = (new BiomeGenJungle(23, true)).setColor(6458135).setBiomeName("JungleEdge").setFillerBlockMetadata(5470985).setTemperatureRainfall(0.95F, 0.8F);
   public static final BiomeGenBase deepOcean = (new BiomeGenOcean(24)).setColor(48).setBiomeName("Deep Ocean").setHeight(height_DeepOceans);
   public static final BiomeGenBase stoneBeach = (new BiomeGenStoneBeach(25)).setColor(10658436).setBiomeName("Stone Beach").setTemperatureRainfall(0.2F, 0.3F).setHeight(height_RockyWaters);
   public static final BiomeGenBase coldBeach = (new BiomeGenBeach(26)).setColor(16445632).setBiomeName("Cold Beach").setTemperatureRainfall(0.05F, 0.3F).setHeight(height_Shores).setEnableSnow();
   public static final BiomeGenBase birchForest = (new BiomeGenForest(27, 2)).setBiomeName("Birch Forest").setColor(3175492);
   public static final BiomeGenBase birchForestHills = (new BiomeGenForest(28, 2)).setBiomeName("Birch Forest Hills").setColor(2055986).setHeight(height_LowHills);
   public static final BiomeGenBase roofedForest = (new BiomeGenForest(29, 3)).setColor(4215066).setBiomeName("Roofed Forest");
   public static final BiomeGenBase coldTaiga = (new BiomeGenTaiga(30, 0)).setColor(3233098).setBiomeName("Cold Taiga").setFillerBlockMetadata(5159473).setEnableSnow().setTemperatureRainfall(-0.5F, 0.4F).setHeight(height_MidPlains).func_150563_c(16777215);
   public static final BiomeGenBase coldTaigaHills = (new BiomeGenTaiga(31, 0)).setColor(2375478).setBiomeName("Cold Taiga Hills").setFillerBlockMetadata(5159473).setEnableSnow().setTemperatureRainfall(-0.5F, 0.4F).setHeight(height_LowHills).func_150563_c(16777215);
   public static final BiomeGenBase megaTaiga = (new BiomeGenTaiga(32, 1)).setColor(5858897).setBiomeName("Mega Taiga").setFillerBlockMetadata(5159473).setTemperatureRainfall(0.3F, 0.8F).setHeight(height_MidPlains);
   public static final BiomeGenBase megaTaigaHills = (new BiomeGenTaiga(33, 1)).setColor(4542270).setBiomeName("Mega Taiga Hills").setFillerBlockMetadata(5159473).setTemperatureRainfall(0.3F, 0.8F).setHeight(height_LowHills);
   public static final BiomeGenBase extremeHillsPlus = (new BiomeGenHills(34, true)).setColor(5271632).setBiomeName("Extreme Hills+").setHeight(height_MidHills).setTemperatureRainfall(0.2F, 0.3F);
   public static final BiomeGenBase savanna = (new BiomeGenSavanna(35)).setColor(12431967).setBiomeName("Savanna").setTemperatureRainfall(1.2F, 0.0F).setDisableRain().setHeight(height_LowPlains);
   public static final BiomeGenBase savannaPlateau = (new BiomeGenSavanna(36)).setColor(10984804).setBiomeName("Savanna Plateau").setTemperatureRainfall(1.0F, 0.0F).setDisableRain().setHeight(height_HighPlateaus);
   public static final BiomeGenBase mesa = (new BiomeGenMesa(37, false, false)).setColor(14238997).setBiomeName("Mesa");
   public static final BiomeGenBase mesaPlateau_F = (new BiomeGenMesa(38, false, true)).setColor(11573093).setBiomeName("Mesa Plateau F").setHeight(height_HighPlateaus);
   public static final BiomeGenBase mesaPlateau = (new BiomeGenMesa(39, false, false)).setColor(13274213).setBiomeName("Mesa Plateau").setHeight(height_HighPlateaus);
   public static final BiomeGenBase field_180279_ad = ocean;
   protected static final NoiseGeneratorPerlin temperatureNoise;
   protected static final NoiseGeneratorPerlin GRASS_COLOR_NOISE;
   protected static final WorldGenDoublePlant DOUBLE_PLANT_GENERATOR;
   public String biomeName;
   public int color;
   public int field_150609_ah;
   public IBlockState topBlock = Blocks.grass.getDefaultState();
   public IBlockState fillerBlock = Blocks.dirt.getDefaultState();
   public int fillerBlockMetadata = 5169201;
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
   protected boolean enableSnow;
   protected boolean enableRain;
   public final int biomeID;
   protected WorldGenTrees worldGeneratorTrees;
   protected WorldGenBigTree worldGeneratorBigTree;
   protected WorldGenSwamp worldGeneratorSwamp;

   protected BiomeGenBase(int var1) {
      this.minHeight = height_Default.rootHeight;
      this.maxHeight = height_Default.variation;
      this.temperature = 0.5F;
      this.rainfall = 0.5F;
      this.waterColorMultiplier = 16777215;
      this.spawnableMonsterList = Lists.newArrayList();
      this.spawnableCreatureList = Lists.newArrayList();
      this.spawnableWaterCreatureList = Lists.newArrayList();
      this.spawnableCaveCreatureList = Lists.newArrayList();
      this.enableRain = true;
      this.worldGeneratorTrees = new WorldGenTrees(false);
      this.worldGeneratorBigTree = new WorldGenBigTree(false);
      this.worldGeneratorSwamp = new WorldGenSwamp();
      this.biomeID = var1;
      biomeList[var1] = this;
      this.theBiomeDecorator = this.createBiomeDecorator();
      this.spawnableCreatureList.add(new nA(EntitySheep.class, 12, 4, 4));
      this.spawnableCreatureList.add(new nA(EntityRabbit.class, 10, 3, 3));
      this.spawnableCreatureList.add(new nA(EntityPig.class, 10, 4, 4));
      this.spawnableCreatureList.add(new nA(EntityChicken.class, 10, 4, 4));
      this.spawnableCreatureList.add(new nA(EntityCow.class, 8, 4, 4));
      this.spawnableMonsterList.add(new nA(EntitySpider.class, 100, 4, 4));
      this.spawnableMonsterList.add(new nA(EntityZombie.class, 100, 4, 4));
      this.spawnableMonsterList.add(new nA(EntitySkeleton.class, 100, 4, 4));
      this.spawnableMonsterList.add(new nA(EntityCreeper.class, 100, 4, 4));
      this.spawnableMonsterList.add(new nA(EntitySlime.class, 100, 4, 4));
      this.spawnableMonsterList.add(new nA(EntityEnderman.class, 10, 1, 4));
      this.spawnableMonsterList.add(new nA(EntityWitch.class, 5, 1, 1));
      this.spawnableWaterCreatureList.add(new nA(EntitySquid.class, 10, 4, 4));
      this.spawnableCaveCreatureList.add(new nA(EntityBat.class, 10, 8, 8));
   }

   protected BiomeDecorator createBiomeDecorator() {
      return new BiomeDecorator();
   }

   protected BiomeGenBase setTemperatureRainfall(float var1, float var2) {
      if(var1 > 0.1F && var1 < 0.2F) {
         throw new IllegalArgumentException("Please avoid temperatures in the range 0.1 - 0.2 because of snow");
      } else {
         this.temperature = var1;
         this.rainfall = var2;
         return this;
      }
   }

   protected final BiomeGenBase setHeight(BiomeGenBase$Height var1) {
      this.minHeight = var1.rootHeight;
      this.maxHeight = var1.variation;
      return this;
   }

   protected BiomeGenBase setDisableRain() {
      this.enableRain = false;
      return this;
   }

   public WorldGenAbstractTree genBigTreeChance(Random var1) {
      return (WorldGenAbstractTree)(var1.nextInt(10) == 0?this.worldGeneratorBigTree:this.worldGeneratorTrees);
   }

   public WorldGenerator getRandomWorldGenForGrass(Random var1) {
      return new WorldGenTallGrass(BlockTallGrass$EnumType.GRASS);
   }

   public BlockFlower$EnumFlowerType pickRandomFlower(Random var1, BlockPos var2) {
      return var1.nextInt(3) > 0?BlockFlower$EnumFlowerType.DANDELION:BlockFlower$EnumFlowerType.POPPY;
   }

   public BiomeGenBase setEnableSnow() {
      this.enableSnow = true;
      return this;
   }

   protected BiomeGenBase setBiomeName(String var1) {
      this.biomeName = var1;
      return this;
   }

   protected BiomeGenBase setFillerBlockMetadata(int var1) {
      this.fillerBlockMetadata = var1;
      return this;
   }

   protected BiomeGenBase setColor(int var1) {
      this.func_150557_a(var1, false);
      return this;
   }

   protected BiomeGenBase func_150563_c(int var1) {
      this.field_150609_ah = var1;
      return this;
   }

   protected BiomeGenBase func_150557_a(int var1, boolean var2) {
      this.color = var1;
      this.field_150609_ah = (var1 & 16711422) >> 1;
      return this;
   }

   public int getSkyColorByTemp(float var1) {
      var1 = var1 / 3.0F;
      var1 = MathHelper.clamp_float(var1, -1.0F, 1.0F);
      return MathHelper.func_181758_c(0.62222224F - var1 * 0.05F, 0.5F + var1 * 0.1F, 1.0F);
   }

   public List getSpawnableList(EnumCreatureType var1) {
      switch(BiomeGenBase$1.$SwitchMap$net$minecraft$entity$EnumCreatureType[var1.ordinal()]) {
      case 1:
         return this.spawnableMonsterList;
      case 2:
         return this.spawnableCreatureList;
      case 3:
         return this.spawnableWaterCreatureList;
      case 4:
         return this.spawnableCaveCreatureList;
      default:
         return Collections.emptyList();
      }
   }

   public boolean getEnableSnow() {
      return this.isSnowyBiome();
   }

   public boolean canSpawnLightningBolt() {
      return !this.isSnowyBiome() && this.enableRain;
   }

   public boolean isHighHumidity() {
      return this.rainfall > 0.85F;
   }

   public float getSpawningChance() {
      return 0.1F;
   }

   public final int getIntRainfall() {
      return (int)(this.rainfall * 65536.0F);
   }

   public final float getFloatRainfall() {
      return this.rainfall;
   }

   public final float getFloatTemperature(BlockPos var1) {
      if(var1.getY() > 64) {
         float var2 = (float)(temperatureNoise.func_151601_a((double)var1.getX() * 1.0D / 8.0D, (double)var1.getZ() * 1.0D / 8.0D) * 4.0D);
         return this.temperature - (var2 + (float)var1.getY() - 64.0F) * 0.05F / 30.0F;
      } else {
         return this.temperature;
      }
   }

   public void decorate(World var1, Random var2, BlockPos var3) {
      this.theBiomeDecorator.decorate(var1, var2, this, var3);
   }

   public int getGrassColorAtPos(BlockPos var1) {
      double var2 = (double)MathHelper.clamp_float(this.getFloatTemperature(var1), 0.0F, 1.0F);
      double var4 = (double)MathHelper.clamp_float(this.getFloatRainfall(), 0.0F, 1.0F);
      return ColorizerGrass.getGrassColor(var2, var4);
   }

   public int getFoliageColorAtPos(BlockPos var1) {
      double var2 = (double)MathHelper.clamp_float(this.getFloatTemperature(var1), 0.0F, 1.0F);
      double var4 = (double)MathHelper.clamp_float(this.getFloatRainfall(), 0.0F, 1.0F);
      return ColorizerFoliage.getFoliageColor(var2, var4);
   }

   public boolean isSnowyBiome() {
      return this.enableSnow;
   }

   public void genTerrainBlocks(World var1, Random var2, ChunkPrimer var3, int var4, int var5, double var6) {
      this.generateBiomeTerrain(var1, var2, var3, var4, var5, var6);
   }

   public final void generateBiomeTerrain(World var1, Random var2, ChunkPrimer var3, int var4, int var5, double var6) {
      int var8 = var1.func_181545_F();
      IBlockState var9 = this.topBlock;
      IBlockState var10 = this.fillerBlock;
      int var11 = -1;
      int var12 = (int)(var6 / 3.0D + 3.0D + var2.nextDouble() * 0.25D);
      int var13 = var4 & 15;
      int var14 = var5 & 15;
      BlockPos$MutableBlockPos var15 = new BlockPos$MutableBlockPos();
      int var16 = 255;

      while(true) {
         if(var16 <= var2.nextInt(5)) {
            var3.setBlockState(var14, var16, var13, Blocks.bedrock.getDefaultState());
         } else {
            IBlockState var17 = var3.getBlockState(var14, var16, var13);
            if(var17.getBlock().getMaterial() == Material.air) {
               var11 = -1;
            } else if(var17.getBlock() == Blocks.stone) {
               if(var11 == -1) {
                  var9 = null;
                  var10 = Blocks.stone.getDefaultState();
                  if(var16 < var8 && var9.getBlock().getMaterial() == Material.air) {
                     if(this.getFloatTemperature(var15.func_181079_c(var4, var16, var5)) < 0.15F) {
                        var9 = Blocks.ice.getDefaultState();
                     } else {
                        var9 = Blocks.water.getDefaultState();
                     }
                  }

                  var11 = var12;
                  if(var16 >= var8 - 1) {
                     var3.setBlockState(var14, var16, var13, var9);
                  } else if(var16 < var8 - 7 - var12) {
                     var9 = null;
                     var10 = Blocks.stone.getDefaultState();
                     var3.setBlockState(var14, var16, var13, Blocks.gravel.getDefaultState());
                  } else {
                     var3.setBlockState(var14, var16, var13, var10);
                  }
               } else {
                  --var11;
                  var3.setBlockState(var14, var16, var13, var10);
                  if(var10.getBlock() == Blocks.sand) {
                     var11 = var2.nextInt(4) + Math.max(0, var16 - 63);
                     var10 = var10.getValue(BlockSand.VARIANT) == BlockSand$EnumType.RED_SAND?Blocks.red_sandstone.getDefaultState():Blocks.sandstone.getDefaultState();
                  }
               }
            }
         }

         --var16;
      }
   }

   protected BiomeGenBase createMutation() {
      return this.createMutatedBiome(this.biomeID + 128);
   }

   protected BiomeGenBase createMutatedBiome(int var1) {
      return new BiomeGenMutated(var1, this);
   }

   public Class getBiomeClass() {
      return this.getClass();
   }

   public boolean isEqualTo(BiomeGenBase var1) {
      return var1 == this || this.getBiomeClass() == var1.getBiomeClass();
   }

   public BiomeGenBase$TempCategory getTempCategory() {
      return (double)this.temperature < 0.2D?BiomeGenBase$TempCategory.COLD:((double)this.temperature < 1.0D?BiomeGenBase$TempCategory.MEDIUM:BiomeGenBase$TempCategory.WARM);
   }

   public static BiomeGenBase[] getBiomeGenArray() {
      return biomeList;
   }

   public static BiomeGenBase getBiome(int var0) {
      return getBiomeFromBiomeList(var0, (BiomeGenBase)null);
   }

   public static BiomeGenBase getBiomeFromBiomeList(int var0, BiomeGenBase var1) {
      if(var0 <= biomeList.length) {
         BiomeGenBase var2 = biomeList[var0];
         return var1;
      } else {
         LOGGER.warn("Biome ID is out of bounds: {}, defaulting to 0 (Ocean)", new Object[]{Integer.valueOf(var0)});
         return ocean;
      }
   }

   static {
      plains.createMutation();
      desert.createMutation();
      forest.createMutation();
      taiga.createMutation();
      swampland.createMutation();
      icePlains.createMutation();
      jungle.createMutation();
      jungleEdge.createMutation();
      coldTaiga.createMutation();
      savanna.createMutation();
      savannaPlateau.createMutation();
      mesa.createMutation();
      mesaPlateau_F.createMutation();
      mesaPlateau.createMutation();
      birchForest.createMutation();
      birchForestHills.createMutation();
      roofedForest.createMutation();
      megaTaiga.createMutation();
      extremeHills.createMutation();
      extremeHillsPlus.createMutation();
      megaTaiga.createMutatedBiome(megaTaigaHills.biomeID + 128).setBiomeName("Redwood Taiga Hills M");

      for(BiomeGenBase var10 : biomeList) {
         if(BIOME_ID_MAP.containsKey(var10.biomeName)) {
            throw new Error("Biome \"" + var10.biomeName + "\" is defined as both ID " + ((BiomeGenBase)BIOME_ID_MAP.get(var10.biomeName)).biomeID + " and " + var10.biomeID);
         }

         BIOME_ID_MAP.put(var10.biomeName, var10);
         if(var10.biomeID < 128) {
            explorationBiomesList.add(var10);
         }
      }

      explorationBiomesList.remove(hell);
      explorationBiomesList.remove(sky);
      explorationBiomesList.remove(frozenOcean);
      explorationBiomesList.remove(extremeHillsEdge);
      temperatureNoise = new NoiseGeneratorPerlin(new Random(1234L), 1);
      GRASS_COLOR_NOISE = new NoiseGeneratorPerlin(new Random(2345L), 1);
      DOUBLE_PLANT_GENERATOR = new WorldGenDoublePlant();
   }

   private static IllegalArgumentException a(IllegalArgumentException var0) {
      return var0;
   }
}
