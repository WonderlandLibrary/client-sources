package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.Callable;
import com.google.common.collect.Maps;
import java.util.Map;

public abstract class MapGenStructure extends MapGenBase
{
    private MapGenStructureData Âµá€;
    protected Map Ø­áŒŠá;
    private static final String Ó = "CL_00000505";
    private LongHashMap à;
    
    public MapGenStructure() {
        this.Ø­áŒŠá = Maps.newHashMap();
        this.à = new LongHashMap();
    }
    
    public abstract String HorizonCode_Horizon_È();
    
    @Override
    protected final void HorizonCode_Horizon_È(final World worldIn, final int p_180701_2_, final int p_180701_3_, final int p_180701_4_, final int p_180701_5_, final ChunkPrimer p_180701_6_) {
        this.HorizonCode_Horizon_È(worldIn);
        if (!this.à.Â(ChunkCoordIntPair.HorizonCode_Horizon_È(p_180701_2_, p_180701_3_))) {
            this.Â.nextInt();
            try {
                if (this.HorizonCode_Horizon_È(p_180701_2_, p_180701_3_)) {
                    final StructureStart var10 = this.Â(p_180701_2_, p_180701_3_);
                    this.Ø­áŒŠá.put(ChunkCoordIntPair.HorizonCode_Horizon_È(p_180701_2_, p_180701_3_), var10);
                    this.à.HorizonCode_Horizon_È(ChunkCoordIntPair.HorizonCode_Horizon_È(p_180701_2_, p_180701_3_), var10);
                    this.HorizonCode_Horizon_È(p_180701_2_, p_180701_3_, var10);
                }
            }
            catch (Throwable var12) {
                final CrashReport var11 = CrashReport.HorizonCode_Horizon_È(var12, "Exception preparing structure feature");
                final CrashReportCategory var13 = var11.HorizonCode_Horizon_È("Feature being prepared");
                var13.HorizonCode_Horizon_È("Is feature chunk", new Callable() {
                    private static final String Â = "CL_00000506";
                    
                    public String HorizonCode_Horizon_È() {
                        return MapGenStructure.this.HorizonCode_Horizon_È(p_180701_2_, p_180701_3_) ? "True" : "False";
                    }
                });
                var13.HorizonCode_Horizon_È("Chunk location", String.format("%d,%d", p_180701_2_, p_180701_3_));
                var13.HorizonCode_Horizon_È("Chunk pos hash", new Callable() {
                    private static final String Â = "CL_00000507";
                    
                    public String HorizonCode_Horizon_È() {
                        return String.valueOf(ChunkCoordIntPair.HorizonCode_Horizon_È(p_180701_2_, p_180701_3_));
                    }
                });
                var13.HorizonCode_Horizon_È("Structure type", new Callable() {
                    private static final String Â = "CL_00000508";
                    
                    public String HorizonCode_Horizon_È() {
                        return MapGenStructure.this.getClass().getCanonicalName();
                    }
                });
                throw new ReportedException(var11);
            }
        }
    }
    
    public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_175794_2_, final ChunkCoordIntPair p_175794_3_) {
        this.HorizonCode_Horizon_È(worldIn);
        final int var4 = (p_175794_3_.HorizonCode_Horizon_È << 4) + 8;
        final int var5 = (p_175794_3_.Â << 4) + 8;
        boolean var6 = false;
        for (final StructureStart var8 : this.Ø­áŒŠá.values()) {
            if (var8.HorizonCode_Horizon_È() && var8.HorizonCode_Horizon_È(p_175794_3_) && var8.Â().HorizonCode_Horizon_È(var4, var5, var4 + 15, var5 + 15)) {
                var8.HorizonCode_Horizon_È(worldIn, p_175794_2_, new StructureBoundingBox(var4, var5, var4 + 15, var5 + 15));
                var8.Â(p_175794_3_);
                var6 = true;
                this.HorizonCode_Horizon_È(var8.Âµá€(), var8.Ó(), var8);
            }
        }
        return var6;
    }
    
    public boolean Â(final BlockPos p_175795_1_) {
        this.HorizonCode_Horizon_È(this.Ý);
        return this.Ý(p_175795_1_) != null;
    }
    
    protected StructureStart Ý(final BlockPos p_175797_1_) {
        for (final StructureStart var3 : this.Ø­áŒŠá.values()) {
            if (var3.HorizonCode_Horizon_È() && var3.Â().HorizonCode_Horizon_È(p_175797_1_)) {
                for (final StructureComponent var5 : var3.Ý()) {
                    if (var5.Â().HorizonCode_Horizon_È(p_175797_1_)) {
                        return var3;
                    }
                }
            }
        }
        return null;
    }
    
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos p_175796_2_) {
        this.HorizonCode_Horizon_È(worldIn);
        for (final StructureStart var4 : this.Ø­áŒŠá.values()) {
            if (var4.HorizonCode_Horizon_È() && var4.Â().HorizonCode_Horizon_È(p_175796_2_)) {
                return true;
            }
        }
        return false;
    }
    
    public BlockPos Â(final World worldIn, final BlockPos p_180706_2_) {
        this.HorizonCode_Horizon_È(this.Ý = worldIn);
        this.Â.setSeed(worldIn.Æ());
        final long var3 = this.Â.nextLong();
        final long var4 = this.Â.nextLong();
        final long var5 = (p_180706_2_.HorizonCode_Horizon_È() >> 4) * var3;
        final long var6 = (p_180706_2_.Ý() >> 4) * var4;
        this.Â.setSeed(var5 ^ var6 ^ worldIn.Æ());
        this.HorizonCode_Horizon_È(worldIn, p_180706_2_.HorizonCode_Horizon_È() >> 4, p_180706_2_.Ý() >> 4, 0, 0, null);
        double var7 = Double.MAX_VALUE;
        BlockPos var8 = null;
        for (final StructureStart var10 : this.Ø­áŒŠá.values()) {
            if (var10.HorizonCode_Horizon_È()) {
                final StructureComponent var11 = var10.Ý().get(0);
                final BlockPos var12 = var11.Ø­áŒŠá();
                final double var13 = var12.Ó(p_180706_2_);
                if (var13 >= var7) {
                    continue;
                }
                var7 = var13;
                var8 = var12;
            }
        }
        if (var8 != null) {
            return var8;
        }
        final List var14 = this.Â();
        if (var14 != null) {
            BlockPos var15 = null;
            for (final BlockPos var12 : var14) {
                final double var13 = var12.Ó(p_180706_2_);
                if (var13 < var7) {
                    var7 = var13;
                    var15 = var12;
                }
            }
            return var15;
        }
        return null;
    }
    
    protected List Â() {
        return null;
    }
    
    private void HorizonCode_Horizon_È(final World worldIn) {
        if (this.Âµá€ == null) {
            this.Âµá€ = (MapGenStructureData)worldIn.HorizonCode_Horizon_È(MapGenStructureData.class, this.HorizonCode_Horizon_È());
            if (this.Âµá€ == null) {
                this.Âµá€ = new MapGenStructureData(this.HorizonCode_Horizon_È());
                worldIn.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È(), this.Âµá€);
            }
            else {
                final NBTTagCompound var2 = this.Âµá€.HorizonCode_Horizon_È();
                for (final String var4 : var2.Âµá€()) {
                    final NBTBase var5 = var2.HorizonCode_Horizon_È(var4);
                    if (var5.HorizonCode_Horizon_È() == 10) {
                        final NBTTagCompound var6 = (NBTTagCompound)var5;
                        if (!var6.Ý("ChunkX") || !var6.Ý("ChunkZ")) {
                            continue;
                        }
                        final int var7 = var6.Ó("ChunkX");
                        final int var8 = var6.Ó("ChunkZ");
                        final StructureStart var9 = MapGenStructureIO.HorizonCode_Horizon_È(var6, worldIn);
                        if (var9 == null) {
                            continue;
                        }
                        this.Ø­áŒŠá.put(ChunkCoordIntPair.HorizonCode_Horizon_È(var7, var8), var9);
                        this.à.HorizonCode_Horizon_È(ChunkCoordIntPair.HorizonCode_Horizon_È(var7, var8), var9);
                    }
                }
            }
        }
    }
    
    private void HorizonCode_Horizon_È(final int p_143026_1_, final int p_143026_2_, final StructureStart p_143026_3_) {
        this.Âµá€.HorizonCode_Horizon_È(p_143026_3_.HorizonCode_Horizon_È(p_143026_1_, p_143026_2_), p_143026_1_, p_143026_2_);
        this.Âµá€.Ø­áŒŠá();
    }
    
    protected abstract boolean HorizonCode_Horizon_È(final int p0, final int p1);
    
    protected abstract StructureStart Â(final int p0, final int p1);
}
