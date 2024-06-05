package net.minecraft.world.biome;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.awt.Color;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockTallGrass;
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
import net.minecraft.util.MathHelper;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.World;
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
   public static final BiomeGenBase savanna;
   public IBlockState topBlock;
   protected static final BiomeGenBase.Height height_MidHills = new BiomeGenBase.Height(1.0F, 0.5F);
   public int waterColorMultiplier;
   protected static final BiomeGenBase.Height height_LowPlains = new BiomeGenBase.Height(0.125F, 0.05F);
   public float rainfall;
   public static final BiomeGenBase roofedForest;
   public static final BiomeGenBase sky;
   protected static final BiomeGenBase.Height height_ShallowWaters = new BiomeGenBase.Height(-0.5F, 0.0F);
   public static final BiomeGenBase stoneBeach;
   public static final BiomeGenBase desertHills;
   public int color;
   protected boolean enableSnow;
   protected List spawnableMonsterList;
   protected static final BiomeGenBase.Height height_LowHills = new BiomeGenBase.Height(0.45F, 0.3F);
   private static final String __OBFID = "CL_00000158";
   protected boolean enableRain;
   public static final BiomeGenBase ocean;
   public static final BiomeGenBase frozenOcean;
   protected static final BiomeGenBase.Height height_Oceans = new BiomeGenBase.Height(-1.0F, 0.1F);
   public static final BiomeGenBase taiga;
   public int fillerBlockMetadata;
   public static final BiomeGenBase forestHills;
   protected static final BiomeGenBase.Height height_RockyWaters = new BiomeGenBase.Height(0.1F, 0.8F);
   public BiomeDecorator theBiomeDecorator;
   public int field_150609_ah;
   protected List spawnableCreatureList;
   protected List spawnableCaveCreatureList;
   protected static final WorldGenDoublePlant field_180280_ag;
   public static final BiomeGenBase birchForest;
   public static final BiomeGenBase mushroomIsland;
   public static final BiomeGenBase birchForestHills;
   public String biomeName;
   public static final BiomeGenBase extremeHills;
   public IBlockState fillerBlock;
   public float maxHeight;
   protected WorldGenBigTree worldGeneratorBigTree;
   protected WorldGenTrees worldGeneratorTrees;
   protected static final BiomeGenBase.Height height_MidPlains = new BiomeGenBase.Height(0.2F, 0.2F);
   public static final BiomeGenBase mesa;
   protected static final BiomeGenBase.Height height_PartiallySubmerged = new BiomeGenBase.Height(-0.2F, 0.1F);
   protected static final BiomeGenBase.Height height_DeepOceans = new BiomeGenBase.Height(-1.8F, 0.1F);
   public static final BiomeGenBase iceMountains;
   public static final BiomeGenBase beach;
   public static final BiomeGenBase field_180279_ad;
   protected List spawnableWaterCreatureList;
   public static final BiomeGenBase taigaHills;
   public static final BiomeGenBase icePlains;
   public static final BiomeGenBase megaTaigaHills;
   public float temperature;
   public static final BiomeGenBase coldTaigaHills;
   protected static final BiomeGenBase.Height height_Shores = new BiomeGenBase.Height(0.0F, 0.025F);
   public final int biomeID;
   protected static final BiomeGenBase.Height height_Default = new BiomeGenBase.Height(0.1F, 0.2F);
   protected static final BiomeGenBase.Height height_HighPlateaus = new BiomeGenBase.Height(1.5F, 0.025F);
   public static final BiomeGenBase mesaPlateau;
   public float minHeight;
   public static final BiomeGenBase desert;
   public static final BiomeGenBase megaTaiga;
   protected static final NoiseGeneratorPerlin field_180281_af;
   private static final BiomeGenBase[] biomeList = new BiomeGenBase[256];
   public static final Set explorationBiomesList = Sets.newHashSet();
   public static final BiomeGenBase mesaPlateau_F;
   public static final BiomeGenBase plains;
   public static final BiomeGenBase extremeHillsPlus;
   public static final BiomeGenBase jungleHills;
   public static final BiomeGenBase mushroomIslandShore;
   protected static final BiomeGenBase.Height height_LowIslands = new BiomeGenBase.Height(0.2F, 0.3F);
   public static final BiomeGenBase forest;
   public static final BiomeGenBase deepOcean;
   private static final Logger logger = LogManager.getLogger();
   public static final BiomeGenBase frozenRiver;
   public static final BiomeGenBase coldTaiga;
   public static final Map field_180278_o = Maps.newHashMap();
   public static final BiomeGenBase hell;
   protected WorldGenSwamp worldGeneratorSwamp;
   public static final BiomeGenBase swampland;
   protected static final NoiseGeneratorPerlin temperatureNoise;
   public static final BiomeGenBase savannaPlateau;
   public static final BiomeGenBase jungleEdge;
   public static final BiomeGenBase extremeHillsEdge;
   public static final BiomeGenBase coldBeach;
   public static final BiomeGenBase river;
   public static final BiomeGenBase jungle;

   public final int getIntRainfall() {
      return (int)(this.rainfall * 65536.0F);
   }

   public final float getFloatRainfall() {
      return this.rainfall;
   }

   protected BiomeGenBase setColor(int var1) {
      this.func_150557_a(var1, false);
      return this;
   }

   protected BiomeGenBase(int var1) {
      this.topBlock = Blocks.grass.getDefaultState();
      this.fillerBlock = Blocks.dirt.getDefaultState();
      this.fillerBlockMetadata = 5169201;
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
      this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntitySheep.class, 12, 4, 4));
      this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityRabbit.class, 10, 3, 3));
      this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityPig.class, 10, 4, 4));
      this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityChicken.class, 10, 4, 4));
      this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityCow.class, 8, 4, 4));
      this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntitySpider.class, 100, 4, 4));
      this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityZombie.class, 100, 4, 4));
      this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntitySkeleton.class, 100, 4, 4));
      this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityCreeper.class, 100, 4, 4));
      this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntitySlime.class, 100, 4, 4));
      this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityEnderman.class, 10, 1, 4));
      this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityWitch.class, 5, 1, 1));
      this.spawnableWaterCreatureList.add(new BiomeGenBase.SpawnListEntry(EntitySquid.class, 10, 4, 4));
      this.spawnableCaveCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityBat.class, 10, 8, 8));
   }

   protected BiomeGenBase func_150563_c(int var1) {
      this.field_150609_ah = var1;
      return this;
   }

   public void func_180624_a(World var1, Random var2, BlockPos var3) {
      this.theBiomeDecorator.func_180292_a(var1, var2, this, var3);
   }

   public final void func_180628_b(World var1, Random var2, ChunkPrimer var3, int var4, int var5, double var6) {
      boolean var8 = true;
      IBlockState var9 = this.topBlock;
      IBlockState var10 = this.fillerBlock;
      int var11 = -1;
      int var12 = (int)(var6 / 3.0D + 3.0D + var2.nextDouble() * 0.25D);
      int var13 = var4 & 15;
      int var14 = var5 & 15;

      for(int var15 = 255; var15 >= 0; --var15) {
         if (var15 <= var2.nextInt(5)) {
            var3.setBlockState(var14, var15, var13, Blocks.bedrock.getDefaultState());
         } else {
            IBlockState var16 = var3.getBlockState(var14, var15, var13);
            if (var16.getBlock().getMaterial() == Material.air) {
               var11 = -1;
            } else if (var16.getBlock() == Blocks.stone) {
               if (var11 == -1) {
                  if (var12 <= 0) {
                     var9 = null;
                     var10 = Blocks.stone.getDefaultState();
                  } else if (var15 >= 59 && var15 <= 64) {
                     var9 = this.topBlock;
                     var10 = this.fillerBlock;
                  }

                  if (var15 < 63 && (var9 == null || var9.getBlock().getMaterial() == Material.air)) {
                     if (this.func_180626_a(new BlockPos(var4, var15, var5)) < 0.15F) {
                        var9 = Blocks.ice.getDefaultState();
                     } else {
                        var9 = Blocks.water.getDefaultState();
                     }
                  }

                  var11 = var12;
                  if (var15 >= 62) {
                     var3.setBlockState(var14, var15, var13, var9);
                  } else if (var15 < 56 - var12) {
                     var9 = null;
                     var10 = Blocks.stone.getDefaultState();
                     var3.setBlockState(var14, var15, var13, Blocks.gravel.getDefaultState());
                  } else {
                     var3.setBlockState(var14, var15, var13, var10);
                  }
               } else if (var11 > 0) {
                  --var11;
                  var3.setBlockState(var14, var15, var13, var10);
                  if (var11 == 0 && var10.getBlock() == Blocks.sand) {
                     var11 = var2.nextInt(4) + Math.max(0, var15 - 63);
                     var10 = var10.getValue(BlockSand.VARIANT_PROP) == BlockSand.EnumType.RED_SAND ? Blocks.red_sandstone.getDefaultState() : Blocks.sandstone.getDefaultState();
                  }
               }
            }
         }
      }

   }

   public float getSpawningChance() {
      return 0.1F;
   }

   public final float func_180626_a(BlockPos var1) {
      if (var1.getY() > 64) {
         float var2 = (float)(temperatureNoise.func_151601_a((double)var1.getX() * 1.0D / 8.0D, (double)var1.getZ() * 1.0D / 8.0D) * 4.0D);
         return this.temperature - (var2 + (float)var1.getY() - 64.0F) * 0.05F / 30.0F;
      } else {
         return this.temperature;
      }
   }

   public boolean canSpawnLightningBolt() {
      return this.isSnowyBiome() ? false : this.enableRain;
   }

   public boolean getEnableSnow() {
      return this.isSnowyBiome();
   }

   protected BiomeDecorator createBiomeDecorator() {
      return new BiomeDecorator();
   }

   protected BiomeGenBase createMutation() {
      return this.createMutatedBiome(this.biomeID + 128);
   }

   public int getSkyColorByTemp(float var1) {
      var1 /= 3.0F;
      var1 = MathHelper.clamp_float(var1, -1.0F, 1.0F);
      return Color.getHSBColor(0.62222224F - var1 * 0.05F, 0.5F + var1 * 0.1F, 1.0F).getRGB();
   }

   public int func_180625_c(BlockPos var1) {
      double var2 = (double)MathHelper.clamp_float(this.func_180626_a(var1), 0.0F, 1.0F);
      double var4 = (double)MathHelper.clamp_float(this.getFloatRainfall(), 0.0F, 1.0F);
      return ColorizerFoliage.getFoliageColor(var2, var4);
   }

   protected BiomeGenBase setTemperatureRainfall(float var1, float var2) {
      if (var1 > 0.1F && var1 < 0.2F) {
         throw new IllegalArgumentException("Please avoid temperatures in the range 0.1 - 0.2 because of snow");
      } else {
         this.temperature = var1;
         this.rainfall = var2;
         return this;
      }
   }

   public int func_180627_b(BlockPos var1) {
      double var2 = (double)MathHelper.clamp_float(this.func_180626_a(var1), 0.0F, 1.0F);
      double var4 = (double)MathHelper.clamp_float(this.getFloatRainfall(), 0.0F, 1.0F);
      return ColorizerGrass.getGrassColor(var2, var4);
   }

   public BlockFlower.EnumFlowerType pickRandomFlower(Random var1, BlockPos var2) {
      return var1.nextInt(3) > 0 ? BlockFlower.EnumFlowerType.DANDELION : BlockFlower.EnumFlowerType.POPPY;
   }

   public static BiomeGenBase[] getBiomeGenArray() {
      return biomeList;
   }

   protected BiomeGenBase setBiomeName(String var1) {
      this.biomeName = var1;
      return this;
   }

   public static BiomeGenBase getBiome(int var0) {
      return getBiomeFromBiomeList(var0, (BiomeGenBase)null);
   }

   public Class getBiomeClass() {
      return this.getClass();
   }

   protected BiomeGenBase setFillerBlockMetadata(int var1) {
      this.fillerBlockMetadata = var1;
      return this;
   }

   public static BiomeGenBase getBiomeFromBiomeList(int var0, BiomeGenBase var1) {
      if (var0 >= 0 && var0 <= biomeList.length) {
         BiomeGenBase var2 = biomeList[var0];
         return var2 == null ? var1 : var2;
      } else {
         logger.warn(String.valueOf((new StringBuilder("Biome ID is out of bounds: ")).append(var0).append(", defaulting to 0 (Ocean)")));
         return ocean;
      }
   }

   protected BiomeGenBase func_150557_a(int var1, boolean var2) {
      this.color = var1;
      if (var2) {
         this.field_150609_ah = (var1 & 16711422) >> 1;
      } else {
         this.field_150609_ah = var1;
      }

      return this;
   }

   public WorldGenAbstractTree genBigTreeChance(Random var1) {
      return (WorldGenAbstractTree)(var1.nextInt(10) == 0 ? this.worldGeneratorBigTree : this.worldGeneratorTrees);
   }

   public void genTerrainBlocks(World var1, Random var2, ChunkPrimer var3, int var4, int var5, double var6) {
      this.func_180628_b(var1, var2, var3, var4, var5, var6);
   }

   public boolean isSnowyBiome() {
      return this.enableSnow;
   }

   public BiomeGenBase.TempCategory getTempCategory() {
      return (double)this.temperature < 0.2D ? BiomeGenBase.TempCategory.COLD : ((double)this.temperature < 1.0D ? BiomeGenBase.TempCategory.MEDIUM : BiomeGenBase.TempCategory.WARM);
   }

   public List getSpawnableList(EnumCreatureType var1) {
      switch(var1) {
      case MONSTER:
         return this.spawnableMonsterList;
      case CREATURE:
         return this.spawnableCreatureList;
      case WATER_CREATURE:
         return this.spawnableWaterCreatureList;
      case AMBIENT:
         return this.spawnableCaveCreatureList;
      default:
         return Collections.emptyList();
      }
   }

   public boolean isEqualTo(BiomeGenBase var1) {
      return var1 == this ? true : (var1 == null ? false : this.getBiomeClass() == var1.getBiomeClass());
   }

   protected BiomeGenBase createMutatedBiome(int var1) {
      return new BiomeGenMutated(var1, this);
   }

   static {
      ocean = (new BiomeGenOcean(0)).setColor(112).setBiomeName("Ocean").setHeight(height_Oceans);
      plains = (new BiomeGenPlains(1)).setColor(9286496).setBiomeName("Plains");
      desert = (new BiomeGenDesert(2)).setColor(16421912).setBiomeName("Desert").setDisableRain().setTemperatureRainfall(2.0F, 0.0F).setHeight(height_LowPlains);
      extremeHills = (new BiomeGenHills(3, false)).setColor(6316128).setBiomeName("Extreme Hills").setHeight(height_MidHills).setTemperatureRainfall(0.2F, 0.3F);
      forest = (new BiomeGenForest(4, 0)).setColor(353825).setBiomeName("Forest");
      taiga = (new BiomeGenTaiga(5, 0)).setColor(747097).setBiomeName("Taiga").setFillerBlockMetadata(5159473).setTemperatureRainfall(0.25F, 0.8F).setHeight(height_MidPlains);
      swampland = (new BiomeGenSwamp(6)).setColor(522674).setBiomeName("Swampland").setFillerBlockMetadata(9154376).setHeight(height_PartiallySubmerged).setTemperatureRainfall(0.8F, 0.9F);
      river = (new BiomeGenRiver(7)).setColor(255).setBiomeName("River").setHeight(height_ShallowWaters);
      hell = (new BiomeGenHell(8)).setColor(16711680).setBiomeName("Hell").setDisableRain().setTemperatureRainfall(2.0F, 0.0F);
      sky = (new BiomeGenEnd(9)).setColor(8421631).setBiomeName("The End").setDisableRain();
      frozenOcean = (new BiomeGenOcean(10)).setColor(9474208).setBiomeName("FrozenOcean").setEnableSnow().setHeight(height_Oceans).setTemperatureRainfall(0.0F, 0.5F);
      frozenRiver = (new BiomeGenRiver(11)).setColor(10526975).setBiomeName("FrozenRiver").setEnableSnow().setHeight(height_ShallowWaters).setTemperatureRainfall(0.0F, 0.5F);
      icePlains = (new BiomeGenSnow(12, false)).setColor(16777215).setBiomeName("Ice Plains").setEnableSnow().setTemperatureRainfall(0.0F, 0.5F).setHeight(height_LowPlains);
      iceMountains = (new BiomeGenSnow(13, false)).setColor(10526880).setBiomeName("Ice Mountains").setEnableSnow().setHeight(height_LowHills).setTemperatureRainfall(0.0F, 0.5F);
      mushroomIsland = (new BiomeGenMushroomIsland(14)).setColor(16711935).setBiomeName("MushroomIsland").setTemperatureRainfall(0.9F, 1.0F).setHeight(height_LowIslands);
      mushroomIslandShore = (new BiomeGenMushroomIsland(15)).setColor(10486015).setBiomeName("MushroomIslandShore").setTemperatureRainfall(0.9F, 1.0F).setHeight(height_Shores);
      beach = (new BiomeGenBeach(16)).setColor(16440917).setBiomeName("Beach").setTemperatureRainfall(0.8F, 0.4F).setHeight(height_Shores);
      desertHills = (new BiomeGenDesert(17)).setColor(13786898).setBiomeName("DesertHills").setDisableRain().setTemperatureRainfall(2.0F, 0.0F).setHeight(height_LowHills);
      forestHills = (new BiomeGenForest(18, 0)).setColor(2250012).setBiomeName("ForestHills").setHeight(height_LowHills);
      taigaHills = (new BiomeGenTaiga(19, 0)).setColor(1456435).setBiomeName("TaigaHills").setFillerBlockMetadata(5159473).setTemperatureRainfall(0.25F, 0.8F).setHeight(height_LowHills);
      extremeHillsEdge = (new BiomeGenHills(20, true)).setColor(7501978).setBiomeName("Extreme Hills Edge").setHeight(height_MidHills.attenuate()).setTemperatureRainfall(0.2F, 0.3F);
      jungle = (new BiomeGenJungle(21, false)).setColor(5470985).setBiomeName("Jungle").setFillerBlockMetadata(5470985).setTemperatureRainfall(0.95F, 0.9F);
      jungleHills = (new BiomeGenJungle(22, false)).setColor(2900485).setBiomeName("JungleHills").setFillerBlockMetadata(5470985).setTemperatureRainfall(0.95F, 0.9F).setHeight(height_LowHills);
      jungleEdge = (new BiomeGenJungle(23, true)).setColor(6458135).setBiomeName("JungleEdge").setFillerBlockMetadata(5470985).setTemperatureRainfall(0.95F, 0.8F);
      deepOcean = (new BiomeGenOcean(24)).setColor(48).setBiomeName("Deep Ocean").setHeight(height_DeepOceans);
      stoneBeach = (new BiomeGenStoneBeach(25)).setColor(10658436).setBiomeName("Stone Beach").setTemperatureRainfall(0.2F, 0.3F).setHeight(height_RockyWaters);
      coldBeach = (new BiomeGenBeach(26)).setColor(16445632).setBiomeName("Cold Beach").setTemperatureRainfall(0.05F, 0.3F).setHeight(height_Shores).setEnableSnow();
      birchForest = (new BiomeGenForest(27, 2)).setBiomeName("Birch Forest").setColor(3175492);
      birchForestHills = (new BiomeGenForest(28, 2)).setBiomeName("Birch Forest Hills").setColor(2055986).setHeight(height_LowHills);
      roofedForest = (new BiomeGenForest(29, 3)).setColor(4215066).setBiomeName("Roofed Forest");
      coldTaiga = (new BiomeGenTaiga(30, 0)).setColor(3233098).setBiomeName("Cold Taiga").setFillerBlockMetadata(5159473).setEnableSnow().setTemperatureRainfall(-0.5F, 0.4F).setHeight(height_MidPlains).func_150563_c(16777215);
      coldTaigaHills = (new BiomeGenTaiga(31, 0)).setColor(2375478).setBiomeName("Cold Taiga Hills").setFillerBlockMetadata(5159473).setEnableSnow().setTemperatureRainfall(-0.5F, 0.4F).setHeight(height_LowHills).func_150563_c(16777215);
      megaTaiga = (new BiomeGenTaiga(32, 1)).setColor(5858897).setBiomeName("Mega Taiga").setFillerBlockMetadata(5159473).setTemperatureRainfall(0.3F, 0.8F).setHeight(height_MidPlains);
      megaTaigaHills = (new BiomeGenTaiga(33, 1)).setColor(4542270).setBiomeName("Mega Taiga Hills").setFillerBlockMetadata(5159473).setTemperatureRainfall(0.3F, 0.8F).setHeight(height_LowHills);
      extremeHillsPlus = (new BiomeGenHills(34, true)).setColor(5271632).setBiomeName("Extreme Hills+").setHeight(height_MidHills).setTemperatureRainfall(0.2F, 0.3F);
      savanna = (new BiomeGenSavanna(35)).setColor(12431967).setBiomeName("Savanna").setTemperatureRainfall(1.2F, 0.0F).setDisableRain().setHeight(height_LowPlains);
      savannaPlateau = (new BiomeGenSavanna(36)).setColor(10984804).setBiomeName("Savanna Plateau").setTemperatureRainfall(1.0F, 0.0F).setDisableRain().setHeight(height_HighPlateaus);
      mesa = (new BiomeGenMesa(37, false, false)).setColor(14238997).setBiomeName("Mesa");
      mesaPlateau_F = (new BiomeGenMesa(38, false, true)).setColor(11573093).setBiomeName("Mesa Plateau F").setHeight(height_HighPlateaus);
      mesaPlateau = (new BiomeGenMesa(39, false, false)).setColor(13274213).setBiomeName("Mesa Plateau").setHeight(height_HighPlateaus);
      field_180279_ad = ocean;
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
      BiomeGenBase[] var0 = biomeList;
      int var1 = var0.length;

      for(int var2 = 0; var2 < var1; ++var2) {
         BiomeGenBase var3 = var0[var2];
         if (var3 != null) {
            if (field_180278_o.containsKey(var3.biomeName)) {
               throw new Error(String.valueOf((new StringBuilder("Biome \"")).append(var3.biomeName).append("\" is defined as both ID ").append(((BiomeGenBase)field_180278_o.get(var3.biomeName)).biomeID).append(" and ").append(var3.biomeID)));
            }

            field_180278_o.put(var3.biomeName, var3);
            if (var3.biomeID < 128) {
               explorationBiomesList.add(var3);
            }
         }
      }

      explorationBiomesList.remove(hell);
      explorationBiomesList.remove(sky);
      explorationBiomesList.remove(frozenOcean);
      explorationBiomesList.remove(extremeHillsEdge);
      temperatureNoise = new NoiseGeneratorPerlin(new Random(1234L), 1);
      field_180281_af = new NoiseGeneratorPerlin(new Random(2345L), 1);
      field_180280_ag = new WorldGenDoublePlant();
   }

   public boolean isHighHumidity() {
      return this.rainfall > 0.85F;
   }

   protected BiomeGenBase setEnableSnow() {
      this.enableSnow = true;
      return this;
   }

   protected BiomeGenBase setDisableRain() {
      this.enableRain = false;
      return this;
   }

   protected final BiomeGenBase setHeight(BiomeGenBase.Height var1) {
      this.minHeight = var1.rootHeight;
      this.maxHeight = var1.variation;
      return this;
   }

   public WorldGenerator getRandomWorldGenForGrass(Random var1) {
      return new WorldGenTallGrass(BlockTallGrass.EnumType.GRASS);
   }

   static final class SwitchEnumCreatureType {
      static final int[] field_180275_a = new int[EnumCreatureType.values().length];
      private static final String __OBFID = "CL_00002150";

      static {
         try {
            field_180275_a[EnumCreatureType.MONSTER.ordinal()] = 1;
         } catch (NoSuchFieldError var4) {
         }

         try {
            field_180275_a[EnumCreatureType.CREATURE.ordinal()] = 2;
         } catch (NoSuchFieldError var3) {
         }

         try {
            field_180275_a[EnumCreatureType.WATER_CREATURE.ordinal()] = 3;
         } catch (NoSuchFieldError var2) {
         }

         try {
            field_180275_a[EnumCreatureType.AMBIENT.ordinal()] = 4;
         } catch (NoSuchFieldError var1) {
         }

      }
   }

   public static class Height {
      public float variation;
      public float rootHeight;
      private static final String __OBFID = "CL_00000159";

      public Height(float var1, float var2) {
         this.rootHeight = var1;
         this.variation = var2;
      }

      public BiomeGenBase.Height attenuate() {
         return new BiomeGenBase.Height(this.rootHeight * 0.8F, this.variation * 0.6F);
      }
   }

   public static class SpawnListEntry extends WeightedRandom.Item {
      public int maxGroupCount;
      public Class entityClass;
      public int minGroupCount;
      private static final String __OBFID = "CL_00000161";

      public SpawnListEntry(Class var1, int var2, int var3, int var4) {
         super(var2);
         this.entityClass = var1;
         this.minGroupCount = var3;
         this.maxGroupCount = var4;
      }

      public String toString() {
         return String.valueOf((new StringBuilder(String.valueOf(this.entityClass.getSimpleName()))).append("*(").append(this.minGroupCount).append("-").append(this.maxGroupCount).append("):").append(this.itemWeight));
      }
   }

   public static enum TempCategory {
      COLD("COLD", 1),
      WARM("WARM", 3),
      OCEAN("OCEAN", 0);

      private static final BiomeGenBase.TempCategory[] $VALUES = new BiomeGenBase.TempCategory[]{OCEAN, COLD, MEDIUM, WARM};
      private static final String __OBFID = "CL_00000160";
      MEDIUM("MEDIUM", 2);

      private static final BiomeGenBase.TempCategory[] ENUM$VALUES = new BiomeGenBase.TempCategory[]{OCEAN, COLD, MEDIUM, WARM};

      private TempCategory(String var3, int var4) {
      }
   }
}
