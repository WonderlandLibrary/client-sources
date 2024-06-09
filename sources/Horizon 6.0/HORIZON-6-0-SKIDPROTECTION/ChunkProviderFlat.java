package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.Map;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;

public class ChunkProviderFlat implements IChunkProvider
{
    private World HorizonCode_Horizon_È;
    private Random Â;
    private final IBlockState[] Ý;
    private final FlatGeneratorInfo Ø­áŒŠá;
    private final List Âµá€;
    private final boolean Ó;
    private final boolean à;
    private WorldGenLakes Ø;
    private WorldGenLakes áŒŠÆ;
    private static final String áˆºÑ¢Õ = "CL_00000391";
    
    public ChunkProviderFlat(final World worldIn, final long p_i2004_2_, final boolean p_i2004_4_, final String p_i2004_5_) {
        this.Ý = new IBlockState[256];
        this.Âµá€ = Lists.newArrayList();
        this.HorizonCode_Horizon_È = worldIn;
        this.Â = new Random(p_i2004_2_);
        this.Ø­áŒŠá = FlatGeneratorInfo.HorizonCode_Horizon_È(p_i2004_5_);
        if (p_i2004_4_) {
            final Map var6 = this.Ø­áŒŠá.Â();
            if (var6.containsKey("village")) {
                final Map var7 = var6.get("village");
                if (!var7.containsKey("size")) {
                    var7.put("size", "1");
                }
                this.Âµá€.add(new MapGenVillage(var7));
            }
            if (var6.containsKey("biome_1")) {
                this.Âµá€.add(new MapGenScatteredFeature(var6.get("biome_1")));
            }
            if (var6.containsKey("mineshaft")) {
                this.Âµá€.add(new MapGenMineshaft(var6.get("mineshaft")));
            }
            if (var6.containsKey("stronghold")) {
                this.Âµá€.add(new MapGenStronghold(var6.get("stronghold")));
            }
            if (var6.containsKey("oceanmonument")) {
                this.Âµá€.add(new StructureOceanMonument(var6.get("oceanmonument")));
            }
        }
        if (this.Ø­áŒŠá.Â().containsKey("lake")) {
            this.Ø = new WorldGenLakes(Blocks.ÂµÈ);
        }
        if (this.Ø­áŒŠá.Â().containsKey("lava_lake")) {
            this.áŒŠÆ = new WorldGenLakes(Blocks.ˆÏ­);
        }
        this.à = this.Ø­áŒŠá.Â().containsKey("dungeon");
        boolean var8 = true;
        for (final FlatLayerInfo var10 : this.Ø­áŒŠá.Ý()) {
            for (int var11 = var10.Ý(); var11 < var10.Ý() + var10.HorizonCode_Horizon_È(); ++var11) {
                final IBlockState var12 = var10.Â();
                if (var12.Ý() != Blocks.Â) {
                    var8 = false;
                    this.Ý[var11] = var12;
                }
            }
        }
        this.Ó = (!var8 && this.Ø­áŒŠá.Â().containsKey("decoration"));
    }
    
    @Override
    public Chunk Ø­áŒŠá(final int p_73154_1_, final int p_73154_2_) {
        final ChunkPrimer var3 = new ChunkPrimer();
        for (int var4 = 0; var4 < this.Ý.length; ++var4) {
            final IBlockState var5 = this.Ý[var4];
            if (var5 != null) {
                for (int var6 = 0; var6 < 16; ++var6) {
                    for (int var7 = 0; var7 < 16; ++var7) {
                        var3.HorizonCode_Horizon_È(var6, var4, var7, var5);
                    }
                }
            }
        }
        for (final MapGenBase var9 : this.Âµá€) {
            var9.HorizonCode_Horizon_È(this, this.HorizonCode_Horizon_È, p_73154_1_, p_73154_2_, var3);
        }
        final Chunk var10 = new Chunk(this.HorizonCode_Horizon_È, var3, p_73154_1_, p_73154_2_);
        final BiomeGenBase[] var11 = this.HorizonCode_Horizon_È.áŒŠÆ().Â(null, p_73154_1_ * 16, p_73154_2_ * 16, 16, 16);
        final byte[] var12 = var10.ÂµÈ();
        for (int var7 = 0; var7 < var12.length; ++var7) {
            var12[var7] = (byte)var11[var7].ÇªÔ;
        }
        var10.Ø­áŒŠá();
        return var10;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final int p_73149_1_, final int p_73149_2_) {
        return true;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final IChunkProvider p_73153_1_, final int p_73153_2_, final int p_73153_3_) {
        final int var4 = p_73153_2_ * 16;
        final int var5 = p_73153_3_ * 16;
        final BlockPos var6 = new BlockPos(var4, 0, var5);
        final BiomeGenBase var7 = this.HorizonCode_Horizon_È.Ý(new BlockPos(var4 + 16, 0, var5 + 16));
        boolean var8 = false;
        this.Â.setSeed(this.HorizonCode_Horizon_È.Æ());
        final long var9 = this.Â.nextLong() / 2L * 2L + 1L;
        final long var10 = this.Â.nextLong() / 2L * 2L + 1L;
        this.Â.setSeed(p_73153_2_ * var9 + p_73153_3_ * var10 ^ this.HorizonCode_Horizon_È.Æ());
        final ChunkCoordIntPair var11 = new ChunkCoordIntPair(p_73153_2_, p_73153_3_);
        for (final MapGenStructure var13 : this.Âµá€) {
            final boolean var14 = var13.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, this.Â, var11);
            if (var13 instanceof MapGenVillage) {
                var8 |= var14;
            }
        }
        if (this.Ø != null && !var8 && this.Â.nextInt(4) == 0) {
            this.Ø.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, this.Â, var6.Â(this.Â.nextInt(16) + 8, this.Â.nextInt(256), this.Â.nextInt(16) + 8));
        }
        if (this.áŒŠÆ != null && !var8 && this.Â.nextInt(8) == 0) {
            final BlockPos var15 = var6.Â(this.Â.nextInt(16) + 8, this.Â.nextInt(this.Â.nextInt(248) + 8), this.Â.nextInt(16) + 8);
            if (var15.Â() < 63 || this.Â.nextInt(10) == 0) {
                this.áŒŠÆ.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, this.Â, var15);
            }
        }
        if (this.à) {
            for (int var16 = 0; var16 < 8; ++var16) {
                new WorldGenDungeons().HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, this.Â, var6.Â(this.Â.nextInt(16) + 8, this.Â.nextInt(256), this.Â.nextInt(16) + 8));
            }
        }
        if (this.Ó) {
            var7.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, this.Â, new BlockPos(var4, 0, var5));
        }
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final IChunkProvider p_177460_1_, final Chunk p_177460_2_, final int p_177460_3_, final int p_177460_4_) {
        return false;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final boolean p_73151_1_, final IProgressUpdate p_73151_2_) {
        return true;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
    }
    
    @Override
    public boolean Â() {
        return false;
    }
    
    @Override
    public boolean Ý() {
        return true;
    }
    
    @Override
    public String Ø­áŒŠá() {
        return "FlatLevelSource";
    }
    
    @Override
    public List HorizonCode_Horizon_È(final EnumCreatureType p_177458_1_, final BlockPos p_177458_2_) {
        final BiomeGenBase var3 = this.HorizonCode_Horizon_È.Ý(p_177458_2_);
        return var3.HorizonCode_Horizon_È(p_177458_1_);
    }
    
    @Override
    public BlockPos HorizonCode_Horizon_È(final World worldIn, final String p_180513_2_, final BlockPos p_180513_3_) {
        if ("Stronghold".equals(p_180513_2_)) {
            for (final MapGenStructure var5 : this.Âµá€) {
                if (var5 instanceof MapGenStronghold) {
                    return var5.Â(worldIn, p_180513_3_);
                }
            }
        }
        return null;
    }
    
    @Override
    public int Âµá€() {
        return 0;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Chunk p_180514_1_, final int p_180514_2_, final int p_180514_3_) {
        for (final MapGenStructure var5 : this.Âµá€) {
            var5.HorizonCode_Horizon_È(this, this.HorizonCode_Horizon_È, p_180514_2_, p_180514_3_, null);
        }
    }
    
    @Override
    public Chunk HorizonCode_Horizon_È(final BlockPos p_177459_1_) {
        return this.Ø­áŒŠá(p_177459_1_.HorizonCode_Horizon_È() >> 4, p_177459_1_.Ý() >> 4);
    }
}
