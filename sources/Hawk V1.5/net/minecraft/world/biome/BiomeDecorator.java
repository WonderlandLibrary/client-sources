package net.minecraft.world.biome;

import java.util.Random;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockStone;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkProviderSettings;
import net.minecraft.world.gen.GeneratorBushFeature;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigMushroom;
import net.minecraft.world.gen.feature.WorldGenCactus;
import net.minecraft.world.gen.feature.WorldGenClay;
import net.minecraft.world.gen.feature.WorldGenDeadBush;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraft.world.gen.feature.WorldGenLiquids;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenPumpkin;
import net.minecraft.world.gen.feature.WorldGenReed;
import net.minecraft.world.gen.feature.WorldGenSand;
import net.minecraft.world.gen.feature.WorldGenWaterlily;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeDecorator {
   protected WorldGenerator waterlilyGen;
   protected int flowersPerChunk;
   protected WorldGenerator mushroomRedGen;
   protected int sandPerChunk2;
   protected int sandPerChunk;
   protected int waterlilyPerChunk;
   protected WorldGenerator ironGen;
   protected int grassPerChunk;
   protected WorldGenerator field_180299_p;
   protected WorldGenFlowers yellowFlowerGen;
   protected int bigMushroomsPerChunk;
   protected WorldGenerator bigMushroomGen;
   private static final String __OBFID = "CL_00000164";
   protected WorldGenerator clayGen = new WorldGenClay(4);
   protected Random randomGenerator;
   protected World currentWorld;
   protected int mushroomsPerChunk;
   protected int clayPerChunk;
   protected WorldGenerator field_180298_q;
   protected int deadBushPerChunk;
   protected WorldGenerator cactusGen;
   protected WorldGenerator field_180297_k;
   protected WorldGenerator goldGen;
   protected WorldGenerator lapisGen;
   protected WorldGenerator field_180296_j;
   public boolean generateLakes;
   protected WorldGenerator sandGen;
   protected BlockPos field_180294_c;
   protected WorldGenerator gravelGen;
   protected int treesPerChunk;
   protected int cactiPerChunk;
   protected WorldGenerator mushroomBrownGen;
   protected ChunkProviderSettings field_180293_d;
   protected int reedsPerChunk;
   protected WorldGenerator coalGen;
   protected WorldGenerator field_180295_l;
   protected WorldGenerator dirtGen;
   protected WorldGenerator gravelAsSandGen;
   protected WorldGenerator reedGen;

   protected void genStandardOre2(int var1, WorldGenerator var2, int var3, int var4) {
      for(int var5 = 0; var5 < var1; ++var5) {
         BlockPos var6 = this.field_180294_c.add(this.randomGenerator.nextInt(16), this.randomGenerator.nextInt(var4) + this.randomGenerator.nextInt(var4) + var3 - var4, this.randomGenerator.nextInt(16));
         var2.generate(this.currentWorld, this.randomGenerator, var6);
      }

   }

   public BiomeDecorator() {
      this.sandGen = new WorldGenSand(Blocks.sand, 7);
      this.gravelAsSandGen = new WorldGenSand(Blocks.gravel, 6);
      this.yellowFlowerGen = new WorldGenFlowers(Blocks.yellow_flower, BlockFlower.EnumFlowerType.DANDELION);
      this.mushroomBrownGen = new GeneratorBushFeature(Blocks.brown_mushroom);
      this.mushroomRedGen = new GeneratorBushFeature(Blocks.red_mushroom);
      this.bigMushroomGen = new WorldGenBigMushroom();
      this.reedGen = new WorldGenReed();
      this.cactusGen = new WorldGenCactus();
      this.waterlilyGen = new WorldGenWaterlily();
      this.flowersPerChunk = 2;
      this.grassPerChunk = 1;
      this.sandPerChunk = 1;
      this.sandPerChunk2 = 3;
      this.clayPerChunk = 1;
      this.generateLakes = true;
   }

   public void func_180292_a(World var1, Random var2, BiomeGenBase var3, BlockPos var4) {
      if (this.currentWorld != null) {
         throw new RuntimeException("Already decorating");
      } else {
         this.currentWorld = var1;
         String var5 = var1.getWorldInfo().getGeneratorOptions();
         if (var5 != null) {
            this.field_180293_d = ChunkProviderSettings.Factory.func_177865_a(var5).func_177864_b();
         } else {
            this.field_180293_d = ChunkProviderSettings.Factory.func_177865_a("").func_177864_b();
         }

         this.randomGenerator = var2;
         this.field_180294_c = var4;
         this.dirtGen = new WorldGenMinable(Blocks.dirt.getDefaultState(), this.field_180293_d.field_177789_I);
         this.gravelGen = new WorldGenMinable(Blocks.gravel.getDefaultState(), this.field_180293_d.field_177785_M);
         this.field_180296_j = new WorldGenMinable(Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT_PROP, BlockStone.EnumType.GRANITE), this.field_180293_d.field_177796_Q);
         this.field_180297_k = new WorldGenMinable(Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT_PROP, BlockStone.EnumType.DIORITE), this.field_180293_d.field_177792_U);
         this.field_180295_l = new WorldGenMinable(Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT_PROP, BlockStone.EnumType.ANDESITE), this.field_180293_d.field_177800_Y);
         this.coalGen = new WorldGenMinable(Blocks.coal_ore.getDefaultState(), this.field_180293_d.field_177844_ac);
         this.ironGen = new WorldGenMinable(Blocks.iron_ore.getDefaultState(), this.field_180293_d.field_177848_ag);
         this.goldGen = new WorldGenMinable(Blocks.gold_ore.getDefaultState(), this.field_180293_d.field_177828_ak);
         this.field_180299_p = new WorldGenMinable(Blocks.redstone_ore.getDefaultState(), this.field_180293_d.field_177836_ao);
         this.field_180298_q = new WorldGenMinable(Blocks.diamond_ore.getDefaultState(), this.field_180293_d.field_177814_as);
         this.lapisGen = new WorldGenMinable(Blocks.lapis_ore.getDefaultState(), this.field_180293_d.field_177822_aw);
         this.genDecorations(var3);
         this.currentWorld = null;
         this.randomGenerator = null;
      }
   }

   protected void genDecorations(BiomeGenBase var1) {
      this.generateOres();

      int var2;
      int var3;
      int var4;
      for(var2 = 0; var2 < this.sandPerChunk2; ++var2) {
         var3 = this.randomGenerator.nextInt(16) + 8;
         var4 = this.randomGenerator.nextInt(16) + 8;
         this.sandGen.generate(this.currentWorld, this.randomGenerator, this.currentWorld.func_175672_r(this.field_180294_c.add(var3, 0, var4)));
      }

      for(var2 = 0; var2 < this.clayPerChunk; ++var2) {
         var3 = this.randomGenerator.nextInt(16) + 8;
         var4 = this.randomGenerator.nextInt(16) + 8;
         this.clayGen.generate(this.currentWorld, this.randomGenerator, this.currentWorld.func_175672_r(this.field_180294_c.add(var3, 0, var4)));
      }

      for(var2 = 0; var2 < this.sandPerChunk; ++var2) {
         var3 = this.randomGenerator.nextInt(16) + 8;
         var4 = this.randomGenerator.nextInt(16) + 8;
         this.gravelAsSandGen.generate(this.currentWorld, this.randomGenerator, this.currentWorld.func_175672_r(this.field_180294_c.add(var3, 0, var4)));
      }

      var2 = this.treesPerChunk;
      if (this.randomGenerator.nextInt(10) == 0) {
         ++var2;
      }

      int var5;
      BlockPos var6;
      for(var3 = 0; var3 < var2; ++var3) {
         var4 = this.randomGenerator.nextInt(16) + 8;
         var5 = this.randomGenerator.nextInt(16) + 8;
         WorldGenAbstractTree var7 = var1.genBigTreeChance(this.randomGenerator);
         var7.func_175904_e();
         var6 = this.currentWorld.getHorizon(this.field_180294_c.add(var4, 0, var5));
         if (var7.generate(this.currentWorld, this.randomGenerator, var6)) {
            var7.func_180711_a(this.currentWorld, this.randomGenerator, var6);
         }
      }

      for(var3 = 0; var3 < this.bigMushroomsPerChunk; ++var3) {
         var4 = this.randomGenerator.nextInt(16) + 8;
         var5 = this.randomGenerator.nextInt(16) + 8;
         this.bigMushroomGen.generate(this.currentWorld, this.randomGenerator, this.currentWorld.getHorizon(this.field_180294_c.add(var4, 0, var5)));
      }

      int var10;
      for(var3 = 0; var3 < this.flowersPerChunk; ++var3) {
         var4 = this.randomGenerator.nextInt(16) + 8;
         var5 = this.randomGenerator.nextInt(16) + 8;
         var10 = this.randomGenerator.nextInt(this.currentWorld.getHorizon(this.field_180294_c.add(var4, 0, var5)).getY() + 32);
         var6 = this.field_180294_c.add(var4, var10, var5);
         BlockFlower.EnumFlowerType var8 = var1.pickRandomFlower(this.randomGenerator, var6);
         BlockFlower var9 = var8.func_176964_a().func_180346_a();
         if (var9.getMaterial() != Material.air) {
            this.yellowFlowerGen.setGeneratedBlock(var9, var8);
            this.yellowFlowerGen.generate(this.currentWorld, this.randomGenerator, var6);
         }
      }

      for(var3 = 0; var3 < this.grassPerChunk; ++var3) {
         var4 = this.randomGenerator.nextInt(16) + 8;
         var5 = this.randomGenerator.nextInt(16) + 8;
         var10 = this.randomGenerator.nextInt(this.currentWorld.getHorizon(this.field_180294_c.add(var4, 0, var5)).getY() * 2);
         var1.getRandomWorldGenForGrass(this.randomGenerator).generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(var4, var10, var5));
      }

      for(var3 = 0; var3 < this.deadBushPerChunk; ++var3) {
         var4 = this.randomGenerator.nextInt(16) + 8;
         var5 = this.randomGenerator.nextInt(16) + 8;
         var10 = this.randomGenerator.nextInt(this.currentWorld.getHorizon(this.field_180294_c.add(var4, 0, var5)).getY() * 2);
         (new WorldGenDeadBush()).generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(var4, var10, var5));
      }

      BlockPos var11;
      for(var3 = 0; var3 < this.waterlilyPerChunk; ++var3) {
         var4 = this.randomGenerator.nextInt(16) + 8;
         var5 = this.randomGenerator.nextInt(16) + 8;
         var10 = this.randomGenerator.nextInt(this.currentWorld.getHorizon(this.field_180294_c.add(var4, 0, var5)).getY() * 2);

         for(var6 = this.field_180294_c.add(var4, var10, var5); var6.getY() > 0; var6 = var11) {
            var11 = var6.offsetDown();
            if (!this.currentWorld.isAirBlock(var11)) {
               break;
            }
         }

         this.waterlilyGen.generate(this.currentWorld, this.randomGenerator, var6);
      }

      for(var3 = 0; var3 < this.mushroomsPerChunk; ++var3) {
         if (this.randomGenerator.nextInt(4) == 0) {
            var4 = this.randomGenerator.nextInt(16) + 8;
            var5 = this.randomGenerator.nextInt(16) + 8;
            var11 = this.currentWorld.getHorizon(this.field_180294_c.add(var4, 0, var5));
            this.mushroomBrownGen.generate(this.currentWorld, this.randomGenerator, var11);
         }

         if (this.randomGenerator.nextInt(8) == 0) {
            var4 = this.randomGenerator.nextInt(16) + 8;
            var5 = this.randomGenerator.nextInt(16) + 8;
            var10 = this.randomGenerator.nextInt(this.currentWorld.getHorizon(this.field_180294_c.add(var4, 0, var5)).getY() * 2);
            var6 = this.field_180294_c.add(var4, var10, var5);
            this.mushroomRedGen.generate(this.currentWorld, this.randomGenerator, var6);
         }
      }

      if (this.randomGenerator.nextInt(4) == 0) {
         var3 = this.randomGenerator.nextInt(16) + 8;
         var4 = this.randomGenerator.nextInt(16) + 8;
         var5 = this.randomGenerator.nextInt(this.currentWorld.getHorizon(this.field_180294_c.add(var3, 0, var4)).getY() * 2);
         this.mushroomBrownGen.generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(var3, var5, var4));
      }

      if (this.randomGenerator.nextInt(8) == 0) {
         var3 = this.randomGenerator.nextInt(16) + 8;
         var4 = this.randomGenerator.nextInt(16) + 8;
         var5 = this.randomGenerator.nextInt(this.currentWorld.getHorizon(this.field_180294_c.add(var3, 0, var4)).getY() * 2);
         this.mushroomRedGen.generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(var3, var5, var4));
      }

      for(var3 = 0; var3 < this.reedsPerChunk; ++var3) {
         var4 = this.randomGenerator.nextInt(16) + 8;
         var5 = this.randomGenerator.nextInt(16) + 8;
         var10 = this.randomGenerator.nextInt(this.currentWorld.getHorizon(this.field_180294_c.add(var4, 0, var5)).getY() * 2);
         this.reedGen.generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(var4, var10, var5));
      }

      for(var3 = 0; var3 < 10; ++var3) {
         var4 = this.randomGenerator.nextInt(16) + 8;
         var5 = this.randomGenerator.nextInt(16) + 8;
         var10 = this.randomGenerator.nextInt(this.currentWorld.getHorizon(this.field_180294_c.add(var4, 0, var5)).getY() * 2);
         this.reedGen.generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(var4, var10, var5));
      }

      if (this.randomGenerator.nextInt(32) == 0) {
         var3 = this.randomGenerator.nextInt(16) + 8;
         var4 = this.randomGenerator.nextInt(16) + 8;
         var5 = this.randomGenerator.nextInt(this.currentWorld.getHorizon(this.field_180294_c.add(var3, 0, var4)).getY() * 2);
         (new WorldGenPumpkin()).generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(var3, var5, var4));
      }

      for(var3 = 0; var3 < this.cactiPerChunk; ++var3) {
         var4 = this.randomGenerator.nextInt(16) + 8;
         var5 = this.randomGenerator.nextInt(16) + 8;
         var10 = this.randomGenerator.nextInt(this.currentWorld.getHorizon(this.field_180294_c.add(var4, 0, var5)).getY() * 2);
         this.cactusGen.generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(var4, var10, var5));
      }

      if (this.generateLakes) {
         for(var3 = 0; var3 < 50; ++var3) {
            var11 = this.field_180294_c.add(this.randomGenerator.nextInt(16) + 8, this.randomGenerator.nextInt(this.randomGenerator.nextInt(248) + 8), this.randomGenerator.nextInt(16) + 8);
            (new WorldGenLiquids(Blocks.flowing_water)).generate(this.currentWorld, this.randomGenerator, var11);
         }

         for(var3 = 0; var3 < 20; ++var3) {
            var11 = this.field_180294_c.add(this.randomGenerator.nextInt(16) + 8, this.randomGenerator.nextInt(this.randomGenerator.nextInt(this.randomGenerator.nextInt(240) + 8) + 8), this.randomGenerator.nextInt(16) + 8);
            (new WorldGenLiquids(Blocks.flowing_lava)).generate(this.currentWorld, this.randomGenerator, var11);
         }
      }

   }

   protected void generateOres() {
      this.genStandardOre1(this.field_180293_d.field_177790_J, this.dirtGen, this.field_180293_d.field_177791_K, this.field_180293_d.field_177784_L);
      this.genStandardOre1(this.field_180293_d.field_177786_N, this.gravelGen, this.field_180293_d.field_177787_O, this.field_180293_d.field_177797_P);
      this.genStandardOre1(this.field_180293_d.field_177795_V, this.field_180297_k, this.field_180293_d.field_177794_W, this.field_180293_d.field_177801_X);
      this.genStandardOre1(this.field_180293_d.field_177799_R, this.field_180296_j, this.field_180293_d.field_177798_S, this.field_180293_d.field_177793_T);
      this.genStandardOre1(this.field_180293_d.field_177802_Z, this.field_180295_l, this.field_180293_d.field_177846_aa, this.field_180293_d.field_177847_ab);
      this.genStandardOre1(this.field_180293_d.field_177845_ad, this.coalGen, this.field_180293_d.field_177851_ae, this.field_180293_d.field_177853_af);
      this.genStandardOre1(this.field_180293_d.field_177849_ah, this.ironGen, this.field_180293_d.field_177832_ai, this.field_180293_d.field_177834_aj);
      this.genStandardOre1(this.field_180293_d.field_177830_al, this.goldGen, this.field_180293_d.field_177840_am, this.field_180293_d.field_177842_an);
      this.genStandardOre1(this.field_180293_d.field_177838_ap, this.field_180299_p, this.field_180293_d.field_177818_aq, this.field_180293_d.field_177816_ar);
      this.genStandardOre1(this.field_180293_d.field_177812_at, this.field_180298_q, this.field_180293_d.field_177826_au, this.field_180293_d.field_177824_av);
      this.genStandardOre2(this.field_180293_d.field_177820_ax, this.lapisGen, this.field_180293_d.field_177807_ay, this.field_180293_d.field_177805_az);
   }

   protected void genStandardOre1(int var1, WorldGenerator var2, int var3, int var4) {
      int var5;
      if (var4 < var3) {
         var5 = var3;
         var3 = var4;
         var4 = var5;
      } else if (var4 == var3) {
         if (var3 < 255) {
            ++var4;
         } else {
            --var3;
         }
      }

      for(var5 = 0; var5 < var1; ++var5) {
         BlockPos var6 = this.field_180294_c.add(this.randomGenerator.nextInt(16), this.randomGenerator.nextInt(var4 - var3) + var3, this.randomGenerator.nextInt(16));
         var2.generate(this.currentWorld, this.randomGenerator, var6);
      }

   }
}
