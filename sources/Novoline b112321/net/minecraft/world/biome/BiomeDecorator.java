package net.minecraft.world.biome;

import java.util.Random;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockFlower$EnumFlowerType;
import net.minecraft.block.BlockStone;
import net.minecraft.block.BlockStone$EnumType;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.ChunkProviderSettings;
import net.minecraft.world.gen.ChunkProviderSettings$Factory;
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
   protected World currentWorld;
   protected Random randomGenerator;
   protected BlockPos field_180294_c;
   protected ChunkProviderSettings chunkProviderSettings;
   protected WorldGenerator clayGen = new WorldGenClay(4);
   protected WorldGenerator sandGen = new WorldGenSand(Blocks.sand, 7);
   protected WorldGenerator gravelAsSandGen = new WorldGenSand(Blocks.gravel, 6);
   protected WorldGenerator dirtGen;
   protected WorldGenerator gravelGen;
   protected WorldGenerator graniteGen;
   protected WorldGenerator dioriteGen;
   protected WorldGenerator andesiteGen;
   protected WorldGenerator coalGen;
   protected WorldGenerator ironGen;
   protected WorldGenerator goldGen;
   protected WorldGenerator redstoneGen;
   protected WorldGenerator diamondGen;
   protected WorldGenerator lapisGen;
   protected WorldGenFlowers yellowFlowerGen = new WorldGenFlowers(Blocks.yellow_flower, BlockFlower$EnumFlowerType.DANDELION);
   protected WorldGenerator mushroomBrownGen = new GeneratorBushFeature(Blocks.brown_mushroom);
   protected WorldGenerator mushroomRedGen = new GeneratorBushFeature(Blocks.red_mushroom);
   protected WorldGenerator bigMushroomGen = new WorldGenBigMushroom();
   protected WorldGenerator reedGen = new WorldGenReed();
   protected WorldGenerator cactusGen = new WorldGenCactus();
   protected WorldGenerator waterlilyGen = new WorldGenWaterlily();
   protected int waterlilyPerChunk;
   protected int treesPerChunk;
   protected int flowersPerChunk = 2;
   protected int grassPerChunk = 1;
   protected int deadBushPerChunk;
   protected int mushroomsPerChunk;
   protected int reedsPerChunk;
   protected int cactiPerChunk;
   protected int sandPerChunk = 1;
   protected int sandPerChunk2 = 3;
   protected int clayPerChunk = 1;
   protected int bigMushroomsPerChunk;
   public boolean generateLakes = true;

   public void decorate(World var1, Random var2, BiomeGenBase var3, BlockPos var4) {
      if(this.currentWorld != null) {
         throw new RuntimeException("Already decorating");
      } else {
         this.currentWorld = var1;
         String var5 = var1.getWorldInfo().getGeneratorOptions();
         this.chunkProviderSettings = ChunkProviderSettings$Factory.jsonToFactory(var5).func_177864_b();
         this.randomGenerator = var2;
         this.field_180294_c = var4;
         this.dirtGen = new WorldGenMinable(Blocks.dirt.getDefaultState(), this.chunkProviderSettings.dirtSize);
         this.gravelGen = new WorldGenMinable(Blocks.gravel.getDefaultState(), this.chunkProviderSettings.gravelSize);
         this.graniteGen = new WorldGenMinable(Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone$EnumType.GRANITE), this.chunkProviderSettings.graniteSize);
         this.dioriteGen = new WorldGenMinable(Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone$EnumType.DIORITE), this.chunkProviderSettings.dioriteSize);
         this.andesiteGen = new WorldGenMinable(Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone$EnumType.ANDESITE), this.chunkProviderSettings.andesiteSize);
         this.coalGen = new WorldGenMinable(Blocks.coal_ore.getDefaultState(), this.chunkProviderSettings.coalSize);
         this.ironGen = new WorldGenMinable(Blocks.iron_ore.getDefaultState(), this.chunkProviderSettings.ironSize);
         this.goldGen = new WorldGenMinable(Blocks.gold_ore.getDefaultState(), this.chunkProviderSettings.goldSize);
         this.redstoneGen = new WorldGenMinable(Blocks.redstone_ore.getDefaultState(), this.chunkProviderSettings.redstoneSize);
         this.diamondGen = new WorldGenMinable(Blocks.diamond_ore.getDefaultState(), this.chunkProviderSettings.diamondSize);
         this.lapisGen = new WorldGenMinable(Blocks.lapis_ore.getDefaultState(), this.chunkProviderSettings.lapisSize);
         this.genDecorations(var3);
         this.currentWorld = null;
         this.randomGenerator = null;
      }
   }

   protected void genDecorations(BiomeGenBase var1) {
      this.generateOres();

      for(int var2 = 0; var2 < this.sandPerChunk2; ++var2) {
         int var3 = this.randomGenerator.nextInt(16) + 8;
         int var4 = this.randomGenerator.nextInt(16) + 8;
         this.sandGen.generate(this.currentWorld, this.randomGenerator, this.currentWorld.getTopSolidOrLiquidBlock(this.field_180294_c.a(var3, 0, var4)));
      }

      for(int var11 = 0; var11 < this.clayPerChunk; ++var11) {
         int var14 = this.randomGenerator.nextInt(16) + 8;
         int var31 = this.randomGenerator.nextInt(16) + 8;
         this.clayGen.generate(this.currentWorld, this.randomGenerator, this.currentWorld.getTopSolidOrLiquidBlock(this.field_180294_c.a(var14, 0, var31)));
      }

      for(int var12 = 0; var12 < this.sandPerChunk; ++var12) {
         int var15 = this.randomGenerator.nextInt(16) + 8;
         int var32 = this.randomGenerator.nextInt(16) + 8;
         this.gravelAsSandGen.generate(this.currentWorld, this.randomGenerator, this.currentWorld.getTopSolidOrLiquidBlock(this.field_180294_c.a(var15, 0, var32)));
      }

      int var13 = this.treesPerChunk;
      if(this.randomGenerator.nextInt(10) == 0) {
         ++var13;
      }

      for(int var16 = 0; var16 < var13; ++var16) {
         int var33 = this.randomGenerator.nextInt(16) + 8;
         int var5 = this.randomGenerator.nextInt(16) + 8;
         WorldGenAbstractTree var6 = var1.genBigTreeChance(this.randomGenerator);
         var6.func_175904_e();
         BlockPos var7 = this.currentWorld.getHeight(this.field_180294_c.a(var33, 0, var5));
         if(var6.generate(this.currentWorld, this.randomGenerator, var7)) {
            var6.func_180711_a(this.currentWorld, this.randomGenerator, var7);
         }
      }

      for(int var17 = 0; var17 < this.bigMushroomsPerChunk; ++var17) {
         int var34 = this.randomGenerator.nextInt(16) + 8;
         int var49 = this.randomGenerator.nextInt(16) + 8;
         this.bigMushroomGen.generate(this.currentWorld, this.randomGenerator, this.currentWorld.getHeight(this.field_180294_c.a(var34, 0, var49)));
      }

      for(int var18 = 0; var18 < this.flowersPerChunk; ++var18) {
         int var35 = this.randomGenerator.nextInt(16) + 8;
         int var50 = this.randomGenerator.nextInt(16) + 8;
         int var64 = this.currentWorld.getHeight(this.field_180294_c.a(var35, 0, var50)).getY() + 32;
         int var78 = this.randomGenerator.nextInt(var64);
         BlockPos var8 = this.field_180294_c.a(var35, var78, var50);
         BlockFlower$EnumFlowerType var9 = var1.pickRandomFlower(this.randomGenerator, var8);
         BlockFlower var10 = var9.getBlockType().getBlock();
         if(var10.getMaterial() != Material.air) {
            this.yellowFlowerGen.setGeneratedBlock(var10, var9);
            this.yellowFlowerGen.generate(this.currentWorld, this.randomGenerator, var8);
         }
      }

      for(int var19 = 0; var19 < this.grassPerChunk; ++var19) {
         int var36 = this.randomGenerator.nextInt(16) + 8;
         int var51 = this.randomGenerator.nextInt(16) + 8;
         int var65 = this.currentWorld.getHeight(this.field_180294_c.a(var36, 0, var51)).getY() * 2;
         int var79 = this.randomGenerator.nextInt(var65);
         var1.getRandomWorldGenForGrass(this.randomGenerator).generate(this.currentWorld, this.randomGenerator, this.field_180294_c.a(var36, var79, var51));
      }

      for(int var20 = 0; var20 < this.deadBushPerChunk; ++var20) {
         int var37 = this.randomGenerator.nextInt(16) + 8;
         int var52 = this.randomGenerator.nextInt(16) + 8;
         int var66 = this.currentWorld.getHeight(this.field_180294_c.a(var37, 0, var52)).getY() * 2;
         int var80 = this.randomGenerator.nextInt(var66);
         (new WorldGenDeadBush()).generate(this.currentWorld, this.randomGenerator, this.field_180294_c.a(var37, var80, var52));
      }

      for(int var21 = 0; var21 < this.waterlilyPerChunk; ++var21) {
         int var38 = this.randomGenerator.nextInt(16) + 8;
         int var53 = this.randomGenerator.nextInt(16) + 8;
         int var67 = this.currentWorld.getHeight(this.field_180294_c.a(var38, 0, var53)).getY() * 2;
         int var81 = this.randomGenerator.nextInt(var67);

         BlockPos var88;
         BlockPos var91;
         for(var88 = this.field_180294_c.a(var38, var81, var53); var88.getY() > 0; var88 = var91) {
            var91 = var88.down();
            if(!this.currentWorld.isAirBlock(var91)) {
               break;
            }
         }

         this.waterlilyGen.generate(this.currentWorld, this.randomGenerator, var88);
      }

      for(int var22 = 0; var22 < this.mushroomsPerChunk; ++var22) {
         if(this.randomGenerator.nextInt(4) == 0) {
            int var39 = this.randomGenerator.nextInt(16) + 8;
            int var54 = this.randomGenerator.nextInt(16) + 8;
            BlockPos var68 = this.currentWorld.getHeight(this.field_180294_c.a(var39, 0, var54));
            this.mushroomBrownGen.generate(this.currentWorld, this.randomGenerator, var68);
         }

         if(this.randomGenerator.nextInt(8) == 0) {
            int var40 = this.randomGenerator.nextInt(16) + 8;
            int var55 = this.randomGenerator.nextInt(16) + 8;
            int var69 = this.currentWorld.getHeight(this.field_180294_c.a(var40, 0, var55)).getY() * 2;
            int var82 = this.randomGenerator.nextInt(var69);
            BlockPos var89 = this.field_180294_c.a(var40, var82, var55);
            this.mushroomRedGen.generate(this.currentWorld, this.randomGenerator, var89);
         }
      }

      if(this.randomGenerator.nextInt(4) == 0) {
         int var23 = this.randomGenerator.nextInt(16) + 8;
         int var41 = this.randomGenerator.nextInt(16) + 8;
         int var56 = this.currentWorld.getHeight(this.field_180294_c.a(var23, 0, var41)).getY() * 2;
         int var70 = this.randomGenerator.nextInt(var56);
         this.mushroomBrownGen.generate(this.currentWorld, this.randomGenerator, this.field_180294_c.a(var23, var70, var41));
      }

      if(this.randomGenerator.nextInt(8) == 0) {
         int var24 = this.randomGenerator.nextInt(16) + 8;
         int var42 = this.randomGenerator.nextInt(16) + 8;
         int var57 = this.currentWorld.getHeight(this.field_180294_c.a(var24, 0, var42)).getY() * 2;
         int var71 = this.randomGenerator.nextInt(var57);
         this.mushroomRedGen.generate(this.currentWorld, this.randomGenerator, this.field_180294_c.a(var24, var71, var42));
      }

      for(int var25 = 0; var25 < this.reedsPerChunk; ++var25) {
         int var43 = this.randomGenerator.nextInt(16) + 8;
         int var58 = this.randomGenerator.nextInt(16) + 8;
         int var72 = this.currentWorld.getHeight(this.field_180294_c.a(var43, 0, var58)).getY() * 2;
         int var83 = this.randomGenerator.nextInt(var72);
         this.reedGen.generate(this.currentWorld, this.randomGenerator, this.field_180294_c.a(var43, var83, var58));
      }

      for(int var26 = 0; var26 < 10; ++var26) {
         int var44 = this.randomGenerator.nextInt(16) + 8;
         int var59 = this.randomGenerator.nextInt(16) + 8;
         int var73 = this.currentWorld.getHeight(this.field_180294_c.a(var44, 0, var59)).getY() * 2;
         int var84 = this.randomGenerator.nextInt(var73);
         this.reedGen.generate(this.currentWorld, this.randomGenerator, this.field_180294_c.a(var44, var84, var59));
      }

      if(this.randomGenerator.nextInt(32) == 0) {
         int var27 = this.randomGenerator.nextInt(16) + 8;
         int var45 = this.randomGenerator.nextInt(16) + 8;
         int var60 = this.currentWorld.getHeight(this.field_180294_c.a(var27, 0, var45)).getY() * 2;
         int var74 = this.randomGenerator.nextInt(var60);
         (new WorldGenPumpkin()).generate(this.currentWorld, this.randomGenerator, this.field_180294_c.a(var27, var74, var45));
      }

      for(int var28 = 0; var28 < this.cactiPerChunk; ++var28) {
         int var46 = this.randomGenerator.nextInt(16) + 8;
         int var61 = this.randomGenerator.nextInt(16) + 8;
         int var75 = this.currentWorld.getHeight(this.field_180294_c.a(var46, 0, var61)).getY() * 2;
         int var85 = this.randomGenerator.nextInt(var75);
         this.cactusGen.generate(this.currentWorld, this.randomGenerator, this.field_180294_c.a(var46, var85, var61));
      }

      if(this.generateLakes) {
         for(int var29 = 0; var29 < 50; ++var29) {
            int var47 = this.randomGenerator.nextInt(16) + 8;
            int var62 = this.randomGenerator.nextInt(16) + 8;
            int var76 = this.randomGenerator.nextInt(248) + 8;
            int var86 = this.randomGenerator.nextInt(var76);
            BlockPos var90 = this.field_180294_c.a(var47, var86, var62);
            (new WorldGenLiquids(Blocks.flowing_water)).generate(this.currentWorld, this.randomGenerator, var90);
         }

         for(int var30 = 0; var30 < 20; ++var30) {
            int var48 = this.randomGenerator.nextInt(16) + 8;
            int var63 = this.randomGenerator.nextInt(16) + 8;
            int var77 = this.randomGenerator.nextInt(this.randomGenerator.nextInt(this.randomGenerator.nextInt(240) + 8) + 8);
            BlockPos var87 = this.field_180294_c.a(var48, var77, var63);
            (new WorldGenLiquids(Blocks.flowing_lava)).generate(this.currentWorld, this.randomGenerator, var87);
         }
      }

   }

   protected void genStandardOre1(int var1, WorldGenerator var2, int var3, int var4) {
      if(var4 < var3) {
         int var5 = var3;
         var3 = var4;
         var4 = var5;
      } else if(var4 == var3) {
         if(var3 < 255) {
            ++var4;
         } else {
            --var3;
         }
      }

      for(int var7 = 0; var7 < var1; ++var7) {
         BlockPos var6 = this.field_180294_c.a(this.randomGenerator.nextInt(16), this.randomGenerator.nextInt(var4 - var3) + var3, this.randomGenerator.nextInt(16));
         var2.generate(this.currentWorld, this.randomGenerator, var6);
      }

   }

   protected void genStandardOre2(int var1, WorldGenerator var2, int var3, int var4) {
      for(int var5 = 0; var5 < var1; ++var5) {
         BlockPos var6 = this.field_180294_c.a(this.randomGenerator.nextInt(16), this.randomGenerator.nextInt(var4) + this.randomGenerator.nextInt(var4) + var3 - var4, this.randomGenerator.nextInt(16));
         var2.generate(this.currentWorld, this.randomGenerator, var6);
      }

   }

   protected void generateOres() {
      this.genStandardOre1(this.chunkProviderSettings.dirtCount, this.dirtGen, this.chunkProviderSettings.dirtMinHeight, this.chunkProviderSettings.dirtMaxHeight);
      this.genStandardOre1(this.chunkProviderSettings.gravelCount, this.gravelGen, this.chunkProviderSettings.gravelMinHeight, this.chunkProviderSettings.gravelMaxHeight);
      this.genStandardOre1(this.chunkProviderSettings.dioriteCount, this.dioriteGen, this.chunkProviderSettings.dioriteMinHeight, this.chunkProviderSettings.dioriteMaxHeight);
      this.genStandardOre1(this.chunkProviderSettings.graniteCount, this.graniteGen, this.chunkProviderSettings.graniteMinHeight, this.chunkProviderSettings.graniteMaxHeight);
      this.genStandardOre1(this.chunkProviderSettings.andesiteCount, this.andesiteGen, this.chunkProviderSettings.andesiteMinHeight, this.chunkProviderSettings.andesiteMaxHeight);
      this.genStandardOre1(this.chunkProviderSettings.coalCount, this.coalGen, this.chunkProviderSettings.coalMinHeight, this.chunkProviderSettings.coalMaxHeight);
      this.genStandardOre1(this.chunkProviderSettings.ironCount, this.ironGen, this.chunkProviderSettings.ironMinHeight, this.chunkProviderSettings.ironMaxHeight);
      this.genStandardOre1(this.chunkProviderSettings.goldCount, this.goldGen, this.chunkProviderSettings.goldMinHeight, this.chunkProviderSettings.goldMaxHeight);
      this.genStandardOre1(this.chunkProviderSettings.redstoneCount, this.redstoneGen, this.chunkProviderSettings.redstoneMinHeight, this.chunkProviderSettings.redstoneMaxHeight);
      this.genStandardOre1(this.chunkProviderSettings.diamondCount, this.diamondGen, this.chunkProviderSettings.diamondMinHeight, this.chunkProviderSettings.diamondMaxHeight);
      this.genStandardOre2(this.chunkProviderSettings.lapisCount, this.lapisGen, this.chunkProviderSettings.lapisCenterHeight, this.chunkProviderSettings.lapisSpread);
   }

   private static RuntimeException a(RuntimeException var0) {
      return var0;
   }
}
