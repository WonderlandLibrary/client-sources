package net.minecraft.src;

import java.util.*;

public class BiomeDecorator
{
    protected World currentWorld;
    protected Random randomGenerator;
    protected int chunk_X;
    protected int chunk_Z;
    protected BiomeGenBase biome;
    protected WorldGenerator clayGen;
    protected WorldGenerator sandGen;
    protected WorldGenerator gravelAsSandGen;
    protected WorldGenerator dirtGen;
    protected WorldGenerator gravelGen;
    protected WorldGenerator coalGen;
    protected WorldGenerator ironGen;
    protected WorldGenerator goldGen;
    protected WorldGenerator redstoneGen;
    protected WorldGenerator diamondGen;
    protected WorldGenerator lapisGen;
    protected WorldGenerator plantYellowGen;
    protected WorldGenerator plantRedGen;
    protected WorldGenerator mushroomBrownGen;
    protected WorldGenerator mushroomRedGen;
    protected WorldGenerator bigMushroomGen;
    protected WorldGenerator reedGen;
    protected WorldGenerator cactusGen;
    protected WorldGenerator waterlilyGen;
    protected int waterlilyPerChunk;
    protected int treesPerChunk;
    protected int flowersPerChunk;
    protected int grassPerChunk;
    protected int deadBushPerChunk;
    protected int mushroomsPerChunk;
    protected int reedsPerChunk;
    protected int cactiPerChunk;
    protected int sandPerChunk;
    protected int sandPerChunk2;
    protected int clayPerChunk;
    protected int bigMushroomsPerChunk;
    public boolean generateLakes;
    
    public BiomeDecorator(final BiomeGenBase par1BiomeGenBase) {
        this.clayGen = new WorldGenClay(4);
        this.sandGen = new WorldGenSand(7, Block.sand.blockID);
        this.gravelAsSandGen = new WorldGenSand(6, Block.gravel.blockID);
        this.dirtGen = new WorldGenMinable(Block.dirt.blockID, 32);
        this.gravelGen = new WorldGenMinable(Block.gravel.blockID, 32);
        this.coalGen = new WorldGenMinable(Block.oreCoal.blockID, 16);
        this.ironGen = new WorldGenMinable(Block.oreIron.blockID, 8);
        this.goldGen = new WorldGenMinable(Block.oreGold.blockID, 8);
        this.redstoneGen = new WorldGenMinable(Block.oreRedstone.blockID, 7);
        this.diamondGen = new WorldGenMinable(Block.oreDiamond.blockID, 7);
        this.lapisGen = new WorldGenMinable(Block.oreLapis.blockID, 6);
        this.plantYellowGen = new WorldGenFlowers(Block.plantYellow.blockID);
        this.plantRedGen = new WorldGenFlowers(Block.plantRed.blockID);
        this.mushroomBrownGen = new WorldGenFlowers(Block.mushroomBrown.blockID);
        this.mushroomRedGen = new WorldGenFlowers(Block.mushroomRed.blockID);
        this.bigMushroomGen = new WorldGenBigMushroom();
        this.reedGen = new WorldGenReed();
        this.cactusGen = new WorldGenCactus();
        this.waterlilyGen = new WorldGenWaterlily();
        this.waterlilyPerChunk = 0;
        this.treesPerChunk = 0;
        this.flowersPerChunk = 2;
        this.grassPerChunk = 1;
        this.deadBushPerChunk = 0;
        this.mushroomsPerChunk = 0;
        this.reedsPerChunk = 0;
        this.cactiPerChunk = 0;
        this.sandPerChunk = 1;
        this.sandPerChunk2 = 3;
        this.clayPerChunk = 1;
        this.bigMushroomsPerChunk = 0;
        this.generateLakes = true;
        this.biome = par1BiomeGenBase;
    }
    
    public void decorate(final World par1World, final Random par2Random, final int par3, final int par4) {
        if (this.currentWorld != null) {
            throw new RuntimeException("Already decorating!!");
        }
        this.currentWorld = par1World;
        this.randomGenerator = par2Random;
        this.chunk_X = par3;
        this.chunk_Z = par4;
        this.decorate();
        this.currentWorld = null;
        this.randomGenerator = null;
    }
    
    protected void decorate() {
        this.generateOres();
        for (int var1 = 0; var1 < this.sandPerChunk2; ++var1) {
            final int var2 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            final int var3 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            this.sandGen.generate(this.currentWorld, this.randomGenerator, var2, this.currentWorld.getTopSolidOrLiquidBlock(var2, var3), var3);
        }
        for (int var1 = 0; var1 < this.clayPerChunk; ++var1) {
            final int var2 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            final int var3 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            this.clayGen.generate(this.currentWorld, this.randomGenerator, var2, this.currentWorld.getTopSolidOrLiquidBlock(var2, var3), var3);
        }
        for (int var1 = 0; var1 < this.sandPerChunk; ++var1) {
            final int var2 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            final int var3 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            this.sandGen.generate(this.currentWorld, this.randomGenerator, var2, this.currentWorld.getTopSolidOrLiquidBlock(var2, var3), var3);
        }
        int var1 = this.treesPerChunk;
        if (this.randomGenerator.nextInt(10) == 0) {
            ++var1;
        }
        for (int var2 = 0; var2 < var1; ++var2) {
            final int var3 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            final int var4 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            final WorldGenerator var5 = this.biome.getRandomWorldGenForTrees(this.randomGenerator);
            var5.setScale(1.0, 1.0, 1.0);
            var5.generate(this.currentWorld, this.randomGenerator, var3, this.currentWorld.getHeightValue(var3, var4), var4);
        }
        for (int var2 = 0; var2 < this.bigMushroomsPerChunk; ++var2) {
            final int var3 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            final int var4 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            this.bigMushroomGen.generate(this.currentWorld, this.randomGenerator, var3, this.currentWorld.getHeightValue(var3, var4), var4);
        }
        for (int var2 = 0; var2 < this.flowersPerChunk; ++var2) {
            int var3 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            int var4 = this.randomGenerator.nextInt(128);
            int var6 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            this.plantYellowGen.generate(this.currentWorld, this.randomGenerator, var3, var4, var6);
            if (this.randomGenerator.nextInt(4) == 0) {
                var3 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
                var4 = this.randomGenerator.nextInt(128);
                var6 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
                this.plantRedGen.generate(this.currentWorld, this.randomGenerator, var3, var4, var6);
            }
        }
        for (int var2 = 0; var2 < this.grassPerChunk; ++var2) {
            final int var3 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            final int var4 = this.randomGenerator.nextInt(128);
            final int var6 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            final WorldGenerator var7 = this.biome.getRandomWorldGenForGrass(this.randomGenerator);
            var7.generate(this.currentWorld, this.randomGenerator, var3, var4, var6);
        }
        for (int var2 = 0; var2 < this.deadBushPerChunk; ++var2) {
            final int var3 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            final int var4 = this.randomGenerator.nextInt(128);
            final int var6 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            new WorldGenDeadBush(Block.deadBush.blockID).generate(this.currentWorld, this.randomGenerator, var3, var4, var6);
        }
        for (int var2 = 0; var2 < this.waterlilyPerChunk; ++var2) {
            int var3;
            int var4;
            int var6;
            for (var3 = this.chunk_X + this.randomGenerator.nextInt(16) + 8, var4 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8, var6 = this.randomGenerator.nextInt(128); var6 > 0 && this.currentWorld.getBlockId(var3, var6 - 1, var4) == 0; --var6) {}
            this.waterlilyGen.generate(this.currentWorld, this.randomGenerator, var3, var6, var4);
        }
        for (int var2 = 0; var2 < this.mushroomsPerChunk; ++var2) {
            if (this.randomGenerator.nextInt(4) == 0) {
                final int var3 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
                final int var4 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
                final int var6 = this.currentWorld.getHeightValue(var3, var4);
                this.mushroomBrownGen.generate(this.currentWorld, this.randomGenerator, var3, var6, var4);
            }
            if (this.randomGenerator.nextInt(8) == 0) {
                final int var3 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
                final int var4 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
                final int var6 = this.randomGenerator.nextInt(128);
                this.mushroomRedGen.generate(this.currentWorld, this.randomGenerator, var3, var6, var4);
            }
        }
        if (this.randomGenerator.nextInt(4) == 0) {
            final int var2 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            final int var3 = this.randomGenerator.nextInt(128);
            final int var4 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            this.mushroomBrownGen.generate(this.currentWorld, this.randomGenerator, var2, var3, var4);
        }
        if (this.randomGenerator.nextInt(8) == 0) {
            final int var2 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            final int var3 = this.randomGenerator.nextInt(128);
            final int var4 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            this.mushroomRedGen.generate(this.currentWorld, this.randomGenerator, var2, var3, var4);
        }
        for (int var2 = 0; var2 < this.reedsPerChunk; ++var2) {
            final int var3 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            final int var4 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            final int var6 = this.randomGenerator.nextInt(128);
            this.reedGen.generate(this.currentWorld, this.randomGenerator, var3, var6, var4);
        }
        for (int var2 = 0; var2 < 10; ++var2) {
            final int var3 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            final int var4 = this.randomGenerator.nextInt(128);
            final int var6 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            this.reedGen.generate(this.currentWorld, this.randomGenerator, var3, var4, var6);
        }
        if (this.randomGenerator.nextInt(32) == 0) {
            final int var2 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            final int var3 = this.randomGenerator.nextInt(128);
            final int var4 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            new WorldGenPumpkin().generate(this.currentWorld, this.randomGenerator, var2, var3, var4);
        }
        for (int var2 = 0; var2 < this.cactiPerChunk; ++var2) {
            final int var3 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            final int var4 = this.randomGenerator.nextInt(128);
            final int var6 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            this.cactusGen.generate(this.currentWorld, this.randomGenerator, var3, var4, var6);
        }
        if (this.generateLakes) {
            for (int var2 = 0; var2 < 50; ++var2) {
                final int var3 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
                final int var4 = this.randomGenerator.nextInt(this.randomGenerator.nextInt(120) + 8);
                final int var6 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
                new WorldGenLiquids(Block.waterMoving.blockID).generate(this.currentWorld, this.randomGenerator, var3, var4, var6);
            }
            for (int var2 = 0; var2 < 20; ++var2) {
                final int var3 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
                final int var4 = this.randomGenerator.nextInt(this.randomGenerator.nextInt(this.randomGenerator.nextInt(112) + 8) + 8);
                final int var6 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
                new WorldGenLiquids(Block.lavaMoving.blockID).generate(this.currentWorld, this.randomGenerator, var3, var4, var6);
            }
        }
    }
    
    protected void genStandardOre1(final int par1, final WorldGenerator par2WorldGenerator, final int par3, final int par4) {
        for (int var5 = 0; var5 < par1; ++var5) {
            final int var6 = this.chunk_X + this.randomGenerator.nextInt(16);
            final int var7 = this.randomGenerator.nextInt(par4 - par3) + par3;
            final int var8 = this.chunk_Z + this.randomGenerator.nextInt(16);
            par2WorldGenerator.generate(this.currentWorld, this.randomGenerator, var6, var7, var8);
        }
    }
    
    protected void genStandardOre2(final int par1, final WorldGenerator par2WorldGenerator, final int par3, final int par4) {
        for (int var5 = 0; var5 < par1; ++var5) {
            final int var6 = this.chunk_X + this.randomGenerator.nextInt(16);
            final int var7 = this.randomGenerator.nextInt(par4) + this.randomGenerator.nextInt(par4) + (par3 - par4);
            final int var8 = this.chunk_Z + this.randomGenerator.nextInt(16);
            par2WorldGenerator.generate(this.currentWorld, this.randomGenerator, var6, var7, var8);
        }
    }
    
    protected void generateOres() {
        this.genStandardOre1(20, this.dirtGen, 0, 128);
        this.genStandardOre1(10, this.gravelGen, 0, 128);
        this.genStandardOre1(20, this.coalGen, 0, 128);
        this.genStandardOre1(20, this.ironGen, 0, 64);
        this.genStandardOre1(2, this.goldGen, 0, 32);
        this.genStandardOre1(8, this.redstoneGen, 0, 16);
        this.genStandardOre1(1, this.diamondGen, 0, 16);
        this.genStandardOre2(1, this.lapisGen, 16, 16);
    }
}
