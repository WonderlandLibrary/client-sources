package HORIZON-6-0-SKIDPROTECTION;

public class ChunkLoader
{
    private static final String HorizonCode_Horizon_È = "CL_00000379";
    
    public static HorizonCode_Horizon_È HorizonCode_Horizon_È(final NBTTagCompound nbt) {
        final int var1 = nbt.Ó("xPos");
        final int var2 = nbt.Ó("zPos");
        final HorizonCode_Horizon_È var3 = new HorizonCode_Horizon_È(var1, var2);
        var3.à = nbt.ÂµÈ("Blocks");
        var3.Ó = new NibbleArrayReader(nbt.ÂµÈ("Data"), 7);
        var3.Âµá€ = new NibbleArrayReader(nbt.ÂµÈ("SkyLight"), 7);
        var3.Ø­áŒŠá = new NibbleArrayReader(nbt.ÂµÈ("BlockLight"), 7);
        var3.Ý = nbt.ÂµÈ("HeightMap");
        var3.Â = nbt.£á("TerrainPopulated");
        var3.Ø = nbt.Ý("Entities", 10);
        var3.áŒŠÆ = nbt.Ý("TileEntities", 10);
        var3.áˆºÑ¢Õ = nbt.Ý("TileTicks", 10);
        try {
            var3.HorizonCode_Horizon_È = nbt.à("LastUpdate");
        }
        catch (ClassCastException var4) {
            var3.HorizonCode_Horizon_È = nbt.Ó("LastUpdate");
        }
        return var3;
    }
    
    public static void HorizonCode_Horizon_È(final HorizonCode_Horizon_È p_76690_0_, final NBTTagCompound p_76690_1_, final WorldChunkManager p_76690_2_) {
        p_76690_1_.HorizonCode_Horizon_È("xPos", p_76690_0_.ÂµÈ);
        p_76690_1_.HorizonCode_Horizon_È("zPos", p_76690_0_.á);
        p_76690_1_.HorizonCode_Horizon_È("LastUpdate", p_76690_0_.HorizonCode_Horizon_È);
        final int[] var3 = new int[p_76690_0_.Ý.length];
        for (int var4 = 0; var4 < p_76690_0_.Ý.length; ++var4) {
            var3[var4] = p_76690_0_.Ý[var4];
        }
        p_76690_1_.HorizonCode_Horizon_È("HeightMap", var3);
        p_76690_1_.HorizonCode_Horizon_È("TerrainPopulated", p_76690_0_.Â);
        final NBTTagList var5 = new NBTTagList();
        for (int var6 = 0; var6 < 8; ++var6) {
            boolean var7 = true;
            for (int var8 = 0; var8 < 16 && var7; ++var8) {
                for (int var9 = 0; var9 < 16 && var7; ++var9) {
                    for (int var10 = 0; var10 < 16; ++var10) {
                        final int var11 = var8 << 11 | var10 << 7 | var9 + (var6 << 4);
                        final byte var12 = p_76690_0_.à[var11];
                        if (var12 != 0) {
                            var7 = false;
                            break;
                        }
                    }
                }
            }
            if (!var7) {
                final byte[] var13 = new byte[4096];
                final NibbleArray var14 = new NibbleArray();
                final NibbleArray var15 = new NibbleArray();
                final NibbleArray var16 = new NibbleArray();
                for (int var17 = 0; var17 < 16; ++var17) {
                    for (int var18 = 0; var18 < 16; ++var18) {
                        for (int var19 = 0; var19 < 16; ++var19) {
                            final int var20 = var17 << 11 | var19 << 7 | var18 + (var6 << 4);
                            final byte var21 = p_76690_0_.à[var20];
                            var13[var18 << 8 | var19 << 4 | var17] = (byte)(var21 & 0xFF);
                            var14.HorizonCode_Horizon_È(var17, var18, var19, p_76690_0_.Ó.HorizonCode_Horizon_È(var17, var18 + (var6 << 4), var19));
                            var15.HorizonCode_Horizon_È(var17, var18, var19, p_76690_0_.Âµá€.HorizonCode_Horizon_È(var17, var18 + (var6 << 4), var19));
                            var16.HorizonCode_Horizon_È(var17, var18, var19, p_76690_0_.Ø­áŒŠá.HorizonCode_Horizon_È(var17, var18 + (var6 << 4), var19));
                        }
                    }
                }
                final NBTTagCompound var22 = new NBTTagCompound();
                var22.HorizonCode_Horizon_È("Y", (byte)(var6 & 0xFF));
                var22.HorizonCode_Horizon_È("Blocks", var13);
                var22.HorizonCode_Horizon_È("Data", var14.HorizonCode_Horizon_È());
                var22.HorizonCode_Horizon_È("SkyLight", var15.HorizonCode_Horizon_È());
                var22.HorizonCode_Horizon_È("BlockLight", var16.HorizonCode_Horizon_È());
                var5.HorizonCode_Horizon_È(var22);
            }
        }
        p_76690_1_.HorizonCode_Horizon_È("Sections", var5);
        final byte[] var23 = new byte[256];
        for (int var24 = 0; var24 < 16; ++var24) {
            for (int var8 = 0; var8 < 16; ++var8) {
                var23[var8 << 4 | var24] = (byte)(p_76690_2_.HorizonCode_Horizon_È(new BlockPos(p_76690_0_.ÂµÈ << 4 | var24, 0, p_76690_0_.á << 4 | var8), BiomeGenBase.ÇªÓ).ÇªÔ & 0xFF);
            }
        }
        p_76690_1_.HorizonCode_Horizon_È("Biomes", var23);
        p_76690_1_.HorizonCode_Horizon_È("Entities", p_76690_0_.Ø);
        p_76690_1_.HorizonCode_Horizon_È("TileEntities", p_76690_0_.áŒŠÆ);
        if (p_76690_0_.áˆºÑ¢Õ != null) {
            p_76690_1_.HorizonCode_Horizon_È("TileTicks", p_76690_0_.áˆºÑ¢Õ);
        }
    }
    
    public static class HorizonCode_Horizon_È
    {
        public long HorizonCode_Horizon_È;
        public boolean Â;
        public byte[] Ý;
        public NibbleArrayReader Ø­áŒŠá;
        public NibbleArrayReader Âµá€;
        public NibbleArrayReader Ó;
        public byte[] à;
        public NBTTagList Ø;
        public NBTTagList áŒŠÆ;
        public NBTTagList áˆºÑ¢Õ;
        public final int ÂµÈ;
        public final int á;
        private static final String ˆÏ­ = "CL_00000380";
        
        public HorizonCode_Horizon_È(final int p_i1999_1_, final int p_i1999_2_) {
            this.ÂµÈ = p_i1999_1_;
            this.á = p_i1999_2_;
        }
    }
}
