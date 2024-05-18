package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;
import com.google.common.base.Predicate;
import java.util.List;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.Iterator;
import java.util.Arrays;
import com.google.common.collect.Queues;
import com.google.common.collect.Maps;
import org.apache.logging.log4j.LogManager;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Map;
import org.apache.logging.log4j.Logger;

public class Chunk
{
    private static final Logger Ý;
    private final ExtendedBlockStorage[] Ø­áŒŠá;
    private final byte[] Âµá€;
    private final int[] Ó;
    private final boolean[] à;
    private boolean Ø;
    private final World áŒŠÆ;
    private final int[] áˆºÑ¢Õ;
    public final int HorizonCode_Horizon_È;
    public final int Â;
    private boolean ÂµÈ;
    private final Map á;
    private final ClassInheratanceMultiMap[] ˆÏ­;
    private boolean £á;
    private boolean Å;
    private boolean £à;
    private boolean µà;
    private boolean ˆà;
    private long ¥Æ;
    private int Ø­à;
    private long µÕ;
    private int Æ;
    private ConcurrentLinkedQueue Šáƒ;
    private static final String Ï­Ðƒà = "CL_00000373";
    
    static {
        Ý = LogManager.getLogger();
    }
    
    public Chunk(final World worldIn, final int x, final int z) {
        this.Ø­áŒŠá = new ExtendedBlockStorage[16];
        this.Âµá€ = new byte[256];
        this.Ó = new int[256];
        this.à = new boolean[256];
        this.á = Maps.newHashMap();
        this.Æ = 4096;
        this.Šáƒ = Queues.newConcurrentLinkedQueue();
        this.ˆÏ­ = new ClassInheratanceMultiMap[16];
        this.áŒŠÆ = worldIn;
        this.HorizonCode_Horizon_È = x;
        this.Â = z;
        this.áˆºÑ¢Õ = new int[256];
        for (int var4 = 0; var4 < this.ˆÏ­.length; ++var4) {
            this.ˆÏ­[var4] = new ClassInheratanceMultiMap(Entity.class);
        }
        Arrays.fill(this.Ó, -999);
        Arrays.fill(this.Âµá€, (byte)(-1));
    }
    
    public Chunk(final World worldIn, final ChunkPrimer primer, final int x, final int z) {
        this(worldIn, x, z);
        final short var5 = 256;
        final boolean var6 = !worldIn.£à.Å();
        for (int var7 = 0; var7 < 16; ++var7) {
            for (int var8 = 0; var8 < 16; ++var8) {
                for (int var9 = 0; var9 < var5; ++var9) {
                    final int var10 = var7 * var5 * 16 | var8 * var5 | var9;
                    final IBlockState var11 = primer.HorizonCode_Horizon_È(var10);
                    if (var11.Ý().Ó() != Material.HorizonCode_Horizon_È) {
                        final int var12 = var9 >> 4;
                        if (this.Ø­áŒŠá[var12] == null) {
                            this.Ø­áŒŠá[var12] = new ExtendedBlockStorage(var12 << 4, var6);
                        }
                        this.Ø­áŒŠá[var12].HorizonCode_Horizon_È(var7, var9 & 0xF, var8, var11);
                    }
                }
            }
        }
    }
    
    public boolean HorizonCode_Horizon_È(final int x, final int z) {
        return x == this.HorizonCode_Horizon_È && z == this.Â;
    }
    
    public int HorizonCode_Horizon_È(final BlockPos pos) {
        return this.Â(pos.HorizonCode_Horizon_È() & 0xF, pos.Ý() & 0xF);
    }
    
    public int Â(final int x, final int z) {
        return this.áˆºÑ¢Õ[z << 4 | x];
    }
    
    public int HorizonCode_Horizon_È() {
        for (int var1 = this.Ø­áŒŠá.length - 1; var1 >= 0; --var1) {
            if (this.Ø­áŒŠá[var1] != null) {
                return this.Ø­áŒŠá[var1].Ý();
            }
        }
        return 0;
    }
    
    public ExtendedBlockStorage[] Â() {
        return this.Ø­áŒŠá;
    }
    
    protected void Ý() {
        final int var1 = this.HorizonCode_Horizon_È();
        this.Ø­à = Integer.MAX_VALUE;
        for (int var2 = 0; var2 < 16; ++var2) {
            for (int var3 = 0; var3 < 16; ++var3) {
                this.Ó[var2 + (var3 << 4)] = -999;
                int var4 = var1 + 16;
                while (var4 > 0) {
                    final Block var5 = this.Âµá€(var2, var4 - 1, var3);
                    if (var5.Â() == 0) {
                        --var4;
                    }
                    else {
                        if ((this.áˆºÑ¢Õ[var3 << 4 | var2] = var4) < this.Ø­à) {
                            this.Ø­à = var4;
                            break;
                        }
                        break;
                    }
                }
            }
        }
        this.µà = true;
    }
    
    public void Ø­áŒŠá() {
        final int var1 = this.HorizonCode_Horizon_È();
        this.Ø­à = Integer.MAX_VALUE;
        for (int var2 = 0; var2 < 16; ++var2) {
            for (int var3 = 0; var3 < 16; ++var3) {
                this.Ó[var2 + (var3 << 4)] = -999;
                int var4 = var1 + 16;
                while (var4 > 0) {
                    if (this.Ø­áŒŠá(var2, var4 - 1, var3) == 0) {
                        --var4;
                    }
                    else {
                        if ((this.áˆºÑ¢Õ[var3 << 4 | var2] = var4) < this.Ø­à) {
                            this.Ø­à = var4;
                            break;
                        }
                        break;
                    }
                }
                if (!this.áŒŠÆ.£à.Å()) {
                    var4 = 15;
                    int var5 = var1 + 16 - 1;
                    do {
                        int var6 = this.Ø­áŒŠá(var2, var5, var3);
                        if (var6 == 0 && var4 != 15) {
                            var6 = 1;
                        }
                        var4 -= var6;
                        if (var4 > 0) {
                            final ExtendedBlockStorage var7 = this.Ø­áŒŠá[var5 >> 4];
                            if (var7 == null) {
                                continue;
                            }
                            var7.HorizonCode_Horizon_È(var2, var5 & 0xF, var3, var4);
                            this.áŒŠÆ.Å(new BlockPos((this.HorizonCode_Horizon_È << 4) + var2, var5, (this.Â << 4) + var3));
                        }
                    } while (--var5 > 0 && var4 > 0);
                }
            }
        }
        this.µà = true;
    }
    
    private void Ø­áŒŠá(final int x, final int z) {
        this.à[x + z * 16] = true;
        this.ÂµÈ = true;
    }
    
    private void Ø(final boolean p_150803_1_) {
        this.áŒŠÆ.Ï­Ðƒà.HorizonCode_Horizon_È("recheckGaps");
        if (this.áŒŠÆ.Â(new BlockPos(this.HorizonCode_Horizon_È * 16 + 8, 0, this.Â * 16 + 8), 16)) {
            for (int var2 = 0; var2 < 16; ++var2) {
                for (int var3 = 0; var3 < 16; ++var3) {
                    if (this.à[var2 + var3 * 16]) {
                        this.à[var2 + var3 * 16] = false;
                        final int var4 = this.Â(var2, var3);
                        final int var5 = this.HorizonCode_Horizon_È * 16 + var2;
                        final int var6 = this.Â * 16 + var3;
                        int var7 = Integer.MAX_VALUE;
                        for (final EnumFacing var9 : EnumFacing.Ý.HorizonCode_Horizon_È) {
                            var7 = Math.min(var7, this.áŒŠÆ.Â(var5 + var9.Ø(), var6 + var9.áˆºÑ¢Õ()));
                        }
                        this.Â(var5, var6, var7);
                        for (final EnumFacing var9 : EnumFacing.Ý.HorizonCode_Horizon_È) {
                            this.Â(var5 + var9.Ø(), var6 + var9.áˆºÑ¢Õ(), var4);
                        }
                        if (p_150803_1_) {
                            this.áŒŠÆ.Ï­Ðƒà.Â();
                            return;
                        }
                    }
                }
            }
            this.ÂµÈ = false;
        }
        this.áŒŠÆ.Ï­Ðƒà.Â();
    }
    
    private void Â(final int x, final int p_76599_2_, final int z) {
        final int var4 = this.áŒŠÆ.£á(new BlockPos(x, 0, p_76599_2_)).Â();
        if (var4 > z) {
            this.HorizonCode_Horizon_È(x, p_76599_2_, z, var4 + 1);
        }
        else if (var4 < z) {
            this.HorizonCode_Horizon_È(x, p_76599_2_, var4, z + 1);
        }
    }
    
    private void HorizonCode_Horizon_È(final int x, final int z, final int startY, final int endY) {
        if (endY > startY && this.áŒŠÆ.Â(new BlockPos(x, 0, z), 16)) {
            for (int var5 = startY; var5 < endY; ++var5) {
                this.áŒŠÆ.Ý(EnumSkyBlock.HorizonCode_Horizon_È, new BlockPos(x, var5, z));
            }
            this.µà = true;
        }
    }
    
    private void Ý(final int x, final int y, final int z) {
        int var5;
        final int var4 = var5 = (this.áˆºÑ¢Õ[z << 4 | x] & 0xFF);
        if (y > var4) {
            var5 = y;
        }
        while (var5 > 0 && this.Ø­áŒŠá(x, var5 - 1, z) == 0) {
            --var5;
        }
        if (var5 != var4) {
            this.áŒŠÆ.HorizonCode_Horizon_È(x + this.HorizonCode_Horizon_È * 16, z + this.Â * 16, var5, var4);
            this.áˆºÑ¢Õ[z << 4 | x] = var5;
            final int var6 = this.HorizonCode_Horizon_È * 16 + x;
            final int var7 = this.Â * 16 + z;
            if (!this.áŒŠÆ.£à.Å()) {
                if (var5 < var4) {
                    for (int var8 = var5; var8 < var4; ++var8) {
                        final ExtendedBlockStorage var9 = this.Ø­áŒŠá[var8 >> 4];
                        if (var9 != null) {
                            var9.HorizonCode_Horizon_È(x, var8 & 0xF, z, 15);
                            this.áŒŠÆ.Å(new BlockPos((this.HorizonCode_Horizon_È << 4) + x, var8, (this.Â << 4) + z));
                        }
                    }
                }
                else {
                    for (int var8 = var4; var8 < var5; ++var8) {
                        final ExtendedBlockStorage var9 = this.Ø­áŒŠá[var8 >> 4];
                        if (var9 != null) {
                            var9.HorizonCode_Horizon_È(x, var8 & 0xF, z, 0);
                            this.áŒŠÆ.Å(new BlockPos((this.HorizonCode_Horizon_È << 4) + x, var8, (this.Â << 4) + z));
                        }
                    }
                }
                int var8 = 15;
                while (var5 > 0 && var8 > 0) {
                    --var5;
                    int var10 = this.Ø­áŒŠá(x, var5, z);
                    if (var10 == 0) {
                        var10 = 1;
                    }
                    var8 -= var10;
                    if (var8 < 0) {
                        var8 = 0;
                    }
                    final ExtendedBlockStorage var11 = this.Ø­áŒŠá[var5 >> 4];
                    if (var11 != null) {
                        var11.HorizonCode_Horizon_È(x, var5 & 0xF, z, var8);
                    }
                }
            }
            int var8 = this.áˆºÑ¢Õ[z << 4 | x];
            int var10;
            int var12;
            if ((var12 = var8) < (var10 = var4)) {
                var10 = var8;
                var12 = var4;
            }
            if (var8 < this.Ø­à) {
                this.Ø­à = var8;
            }
            if (!this.áŒŠÆ.£à.Å()) {
                for (final EnumFacing var14 : EnumFacing.Ý.HorizonCode_Horizon_È) {
                    this.HorizonCode_Horizon_È(var6 + var14.Ø(), var7 + var14.áˆºÑ¢Õ(), var10, var12);
                }
                this.HorizonCode_Horizon_È(var6, var7, var10, var12);
            }
            this.µà = true;
        }
    }
    
    public int Â(final BlockPos pos) {
        return this.Ý(pos).Â();
    }
    
    private int Ø­áŒŠá(final int p_150808_1_, final int p_150808_2_, final int p_150808_3_) {
        return this.Âµá€(p_150808_1_, p_150808_2_, p_150808_3_).Â();
    }
    
    private Block Âµá€(final int x, final int y, final int z) {
        Block var4 = Blocks.Â;
        if (y >= 0 && y >> 4 < this.Ø­áŒŠá.length) {
            final ExtendedBlockStorage var5 = this.Ø­áŒŠá[y >> 4];
            if (var5 != null) {
                try {
                    var4 = var5.Â(x, y & 0xF, z);
                }
                catch (Throwable var7) {
                    final CrashReport var6 = CrashReport.HorizonCode_Horizon_È(var7, "Getting block");
                    throw new ReportedException(var6);
                }
            }
        }
        return var4;
    }
    
    public Block HorizonCode_Horizon_È(final int x, final int y, final int z) {
        try {
            return this.Âµá€(x & 0xF, y, z & 0xF);
        }
        catch (ReportedException var6) {
            final CrashReportCategory var5 = var6.HorizonCode_Horizon_È().HorizonCode_Horizon_È("Block being got");
            var5.HorizonCode_Horizon_È("Location", new Callable() {
                private static final String Â = "CL_00000374";
                
                public String HorizonCode_Horizon_È() {
                    return CrashReportCategory.HorizonCode_Horizon_È(new BlockPos(Chunk.this.HorizonCode_Horizon_È * 16 + x, y, Chunk.this.Â * 16 + z));
                }
            });
            throw var6;
        }
    }
    
    public Block Ý(final BlockPos pos) {
        try {
            return this.Âµá€(pos.HorizonCode_Horizon_È() & 0xF, pos.Â(), pos.Ý() & 0xF);
        }
        catch (ReportedException var4) {
            final CrashReportCategory var3 = var4.HorizonCode_Horizon_È().HorizonCode_Horizon_È("Block being got");
            var3.HorizonCode_Horizon_È("Location", new Callable() {
                private static final String Â = "CL_00002011";
                
                public String HorizonCode_Horizon_È() {
                    return CrashReportCategory.HorizonCode_Horizon_È(pos);
                }
                
                @Override
                public Object call() {
                    return this.HorizonCode_Horizon_È();
                }
            });
            throw var4;
        }
    }
    
    public IBlockState Ø­áŒŠá(final BlockPos pos) {
        if (this.áŒŠÆ.s_() == WorldType.Ø) {
            IBlockState var7 = null;
            if (pos.Â() == 60) {
                var7 = Blocks.¥ÇªÅ.¥à();
            }
            if (pos.Â() == 70) {
                var7 = ChunkProviderDebug.Â(pos.HorizonCode_Horizon_È(), pos.Ý());
            }
            return (var7 == null) ? Blocks.Â.¥à() : var7;
        }
        try {
            if (pos.Â() >= 0 && pos.Â() >> 4 < this.Ø­áŒŠá.length) {
                final ExtendedBlockStorage var8 = this.Ø­áŒŠá[pos.Â() >> 4];
                if (var8 != null) {
                    final int var9 = pos.HorizonCode_Horizon_È() & 0xF;
                    final int var10 = pos.Â() & 0xF;
                    final int var11 = pos.Ý() & 0xF;
                    return var8.HorizonCode_Horizon_È(var9, var10, var11);
                }
            }
            return Blocks.Â.¥à();
        }
        catch (Throwable var13) {
            final CrashReport var12 = CrashReport.HorizonCode_Horizon_È(var13, "Getting block state");
            final CrashReportCategory var14 = var12.HorizonCode_Horizon_È("Block being got");
            var14.HorizonCode_Horizon_È("Location", new Callable() {
                private static final String Â = "CL_00002010";
                
                public String HorizonCode_Horizon_È() {
                    return CrashReportCategory.HorizonCode_Horizon_È(pos);
                }
                
                @Override
                public Object call() {
                    return this.HorizonCode_Horizon_È();
                }
            });
            throw new ReportedException(var12);
        }
    }
    
    private int Ó(final int p_76628_1_, final int p_76628_2_, final int p_76628_3_) {
        if (p_76628_2_ >> 4 >= this.Ø­áŒŠá.length) {
            return 0;
        }
        final ExtendedBlockStorage var4 = this.Ø­áŒŠá[p_76628_2_ >> 4];
        return (var4 != null) ? var4.Ý(p_76628_1_, p_76628_2_ & 0xF, p_76628_3_) : 0;
    }
    
    public int Âµá€(final BlockPos pos) {
        return this.Ó(pos.HorizonCode_Horizon_È() & 0xF, pos.Â(), pos.Ý() & 0xF);
    }
    
    public IBlockState HorizonCode_Horizon_È(final BlockPos p_177436_1_, final IBlockState p_177436_2_) {
        final int var3 = p_177436_1_.HorizonCode_Horizon_È() & 0xF;
        final int var4 = p_177436_1_.Â();
        final int var5 = p_177436_1_.Ý() & 0xF;
        final int var6 = var5 << 4 | var3;
        if (var4 >= this.Ó[var6] - 1) {
            this.Ó[var6] = -999;
        }
        final int var7 = this.áˆºÑ¢Õ[var6];
        final IBlockState var8 = this.Ø­áŒŠá(p_177436_1_);
        if (var8 == p_177436_2_) {
            return null;
        }
        final Block var9 = p_177436_2_.Ý();
        final Block var10 = var8.Ý();
        ExtendedBlockStorage var11 = this.Ø­áŒŠá[var4 >> 4];
        boolean var12 = false;
        if (var11 == null) {
            if (var9 == Blocks.Â) {
                return null;
            }
            final ExtendedBlockStorage[] ø­áŒŠá = this.Ø­áŒŠá;
            final int n = var4 >> 4;
            final ExtendedBlockStorage extendedBlockStorage = new ExtendedBlockStorage(var4 >> 4 << 4, !this.áŒŠÆ.£à.Å());
            ø­áŒŠá[n] = extendedBlockStorage;
            var11 = extendedBlockStorage;
            var12 = (var4 >= var7);
        }
        var11.HorizonCode_Horizon_È(var3, var4 & 0xF, var5, p_177436_2_);
        if (var10 != var9) {
            if (!this.áŒŠÆ.ŠÄ) {
                var10.Ø­áŒŠá(this.áŒŠÆ, p_177436_1_, var8);
            }
            else if (var10 instanceof ITileEntityProvider) {
                this.áŒŠÆ.¥Æ(p_177436_1_);
            }
        }
        if (var11.Â(var3, var4 & 0xF, var5) != var9) {
            return null;
        }
        if (var12) {
            this.Ø­áŒŠá();
        }
        else {
            final int var13 = var9.Â();
            final int var14 = var10.Â();
            if (var13 > 0) {
                if (var4 >= var7) {
                    this.Ý(var3, var4 + 1, var5);
                }
            }
            else if (var4 == var7 - 1) {
                this.Ý(var3, var4, var5);
            }
            if (var13 != var14 && (var13 < var14 || this.HorizonCode_Horizon_È(EnumSkyBlock.HorizonCode_Horizon_È, p_177436_1_) > 0 || this.HorizonCode_Horizon_È(EnumSkyBlock.Â, p_177436_1_) > 0)) {
                this.Ø­áŒŠá(var3, var5);
            }
        }
        if (var10 instanceof ITileEntityProvider) {
            final TileEntity var15 = this.HorizonCode_Horizon_È(p_177436_1_, Chunk.HorizonCode_Horizon_È.Ý);
            if (var15 != null) {
                var15.ˆà();
            }
        }
        if (!this.áŒŠÆ.ŠÄ && var10 != var9) {
            var9.Ý(this.áŒŠÆ, p_177436_1_, p_177436_2_);
        }
        if (var9 instanceof ITileEntityProvider) {
            TileEntity var15 = this.HorizonCode_Horizon_È(p_177436_1_, Chunk.HorizonCode_Horizon_È.Ý);
            if (var15 == null) {
                var15 = ((ITileEntityProvider)var9).HorizonCode_Horizon_È(this.áŒŠÆ, var9.Ý(p_177436_2_));
                this.áŒŠÆ.HorizonCode_Horizon_È(p_177436_1_, var15);
            }
            if (var15 != null) {
                var15.ˆà();
            }
        }
        this.µà = true;
        return var8;
    }
    
    public int HorizonCode_Horizon_È(final EnumSkyBlock p_177413_1_, final BlockPos p_177413_2_) {
        final int var3 = p_177413_2_.HorizonCode_Horizon_È() & 0xF;
        final int var4 = p_177413_2_.Â();
        final int var5 = p_177413_2_.Ý() & 0xF;
        final ExtendedBlockStorage var6 = this.Ø­áŒŠá[var4 >> 4];
        return (var6 == null) ? (this.Ó(p_177413_2_) ? p_177413_1_.Ý : 0) : ((p_177413_1_ == EnumSkyBlock.HorizonCode_Horizon_È) ? (this.áŒŠÆ.£à.Å() ? 0 : var6.Ø­áŒŠá(var3, var4 & 0xF, var5)) : ((p_177413_1_ == EnumSkyBlock.Â) ? var6.Âµá€(var3, var4 & 0xF, var5) : p_177413_1_.Ý));
    }
    
    public void HorizonCode_Horizon_È(final EnumSkyBlock p_177431_1_, final BlockPos p_177431_2_, final int p_177431_3_) {
        final int var4 = p_177431_2_.HorizonCode_Horizon_È() & 0xF;
        final int var5 = p_177431_2_.Â();
        final int var6 = p_177431_2_.Ý() & 0xF;
        ExtendedBlockStorage var7 = this.Ø­áŒŠá[var5 >> 4];
        if (var7 == null) {
            final ExtendedBlockStorage[] ø­áŒŠá = this.Ø­áŒŠá;
            final int n = var5 >> 4;
            final ExtendedBlockStorage extendedBlockStorage = new ExtendedBlockStorage(var5 >> 4 << 4, !this.áŒŠÆ.£à.Å());
            ø­áŒŠá[n] = extendedBlockStorage;
            var7 = extendedBlockStorage;
            this.Ø­áŒŠá();
        }
        this.µà = true;
        if (p_177431_1_ == EnumSkyBlock.HorizonCode_Horizon_È) {
            if (!this.áŒŠÆ.£à.Å()) {
                var7.HorizonCode_Horizon_È(var4, var5 & 0xF, var6, p_177431_3_);
            }
        }
        else if (p_177431_1_ == EnumSkyBlock.Â) {
            var7.Â(var4, var5 & 0xF, var6, p_177431_3_);
        }
    }
    
    public int HorizonCode_Horizon_È(final BlockPos p_177443_1_, final int p_177443_2_) {
        final int var3 = p_177443_1_.HorizonCode_Horizon_È() & 0xF;
        final int var4 = p_177443_1_.Â();
        final int var5 = p_177443_1_.Ý() & 0xF;
        final ExtendedBlockStorage var6 = this.Ø­áŒŠá[var4 >> 4];
        if (var6 == null) {
            return (!this.áŒŠÆ.£à.Å() && p_177443_2_ < EnumSkyBlock.HorizonCode_Horizon_È.Ý) ? (EnumSkyBlock.HorizonCode_Horizon_È.Ý - p_177443_2_) : 0;
        }
        int var7 = this.áŒŠÆ.£à.Å() ? 0 : var6.Ø­áŒŠá(var3, var4 & 0xF, var5);
        var7 -= p_177443_2_;
        final int var8 = var6.Âµá€(var3, var4 & 0xF, var5);
        if (var8 > var7) {
            var7 = var8;
        }
        return var7;
    }
    
    public void HorizonCode_Horizon_È(final Entity entityIn) {
        this.ˆà = true;
        final int var2 = MathHelper.Ý(entityIn.ŒÏ / 16.0);
        final int var3 = MathHelper.Ý(entityIn.Ê / 16.0);
        if (var2 != this.HorizonCode_Horizon_È || var3 != this.Â) {
            Chunk.Ý.warn("Wrong location! (" + var2 + ", " + var3 + ") should be (" + this.HorizonCode_Horizon_È + ", " + this.Â + "), " + entityIn, new Object[] { entityIn });
            entityIn.á€();
        }
        int var4 = MathHelper.Ý(entityIn.Çªà¢ / 16.0);
        if (var4 < 0) {
            var4 = 0;
        }
        if (var4 >= this.ˆÏ­.length) {
            var4 = this.ˆÏ­.length - 1;
        }
        entityIn.ˆÐƒØ­à = true;
        entityIn.£Õ = this.HorizonCode_Horizon_È;
        entityIn.Ï­Ô = var4;
        entityIn.Œà = this.Â;
        this.ˆÏ­[var4].add(entityIn);
    }
    
    public void Â(final Entity p_76622_1_) {
        this.HorizonCode_Horizon_È(p_76622_1_, p_76622_1_.Ï­Ô);
    }
    
    public void HorizonCode_Horizon_È(final Entity p_76608_1_, int p_76608_2_) {
        if (p_76608_2_ < 0) {
            p_76608_2_ = 0;
        }
        if (p_76608_2_ >= this.ˆÏ­.length) {
            p_76608_2_ = this.ˆÏ­.length - 1;
        }
        this.ˆÏ­[p_76608_2_].remove(p_76608_1_);
    }
    
    public boolean Ó(final BlockPos pos) {
        final int var2 = pos.HorizonCode_Horizon_È() & 0xF;
        final int var3 = pos.Â();
        final int var4 = pos.Ý() & 0xF;
        return var3 >= this.áˆºÑ¢Õ[var4 << 4 | var2];
    }
    
    private TileEntity áŒŠÆ(final BlockPos pos) {
        final Block var2 = this.Ý(pos);
        return var2.£á() ? ((ITileEntityProvider)var2).HorizonCode_Horizon_È(this.áŒŠÆ, this.Âµá€(pos)) : null;
    }
    
    public TileEntity HorizonCode_Horizon_È(final BlockPos p_177424_1_, final HorizonCode_Horizon_È p_177424_2_) {
        TileEntity var3 = this.á.get(p_177424_1_);
        if (var3 == null) {
            if (p_177424_2_ == Chunk.HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
                var3 = this.áŒŠÆ(p_177424_1_);
                this.áŒŠÆ.HorizonCode_Horizon_È(p_177424_1_, var3);
            }
            else if (p_177424_2_ == Chunk.HorizonCode_Horizon_È.Â) {
                this.Šáƒ.add(p_177424_1_);
            }
        }
        else if (var3.Å()) {
            this.á.remove(p_177424_1_);
            return null;
        }
        return var3;
    }
    
    public void HorizonCode_Horizon_È(final TileEntity tileEntityIn) {
        this.HorizonCode_Horizon_È(tileEntityIn.á(), tileEntityIn);
        if (this.Ø) {
            this.áŒŠÆ.HorizonCode_Horizon_È(tileEntityIn);
        }
    }
    
    public void HorizonCode_Horizon_È(final BlockPos pos, final TileEntity tileEntityIn) {
        tileEntityIn.HorizonCode_Horizon_È(this.áŒŠÆ);
        tileEntityIn.HorizonCode_Horizon_È(pos);
        if (this.Ý(pos) instanceof ITileEntityProvider) {
            if (this.á.containsKey(pos)) {
                this.á.get(pos).£à();
            }
            tileEntityIn.µà();
            this.á.put(pos, tileEntityIn);
        }
    }
    
    public void à(final BlockPos pos) {
        if (this.Ø) {
            final TileEntity var2 = this.á.remove(pos);
            if (var2 != null) {
                var2.£à();
            }
        }
    }
    
    public void Âµá€() {
        this.Ø = true;
        this.áŒŠÆ.HorizonCode_Horizon_È(this.á.values());
        for (int var1 = 0; var1 < this.ˆÏ­.length; ++var1) {
            for (final Entity var3 : this.ˆÏ­[var1]) {
                var3.ˆáƒ();
            }
            this.áŒŠÆ.Â(this.ˆÏ­[var1]);
        }
    }
    
    public void Ó() {
        this.Ø = false;
        for (final TileEntity var2 : this.á.values()) {
            this.áŒŠÆ.Â(var2);
        }
        for (int var3 = 0; var3 < this.ˆÏ­.length; ++var3) {
            this.áŒŠÆ.Ý(this.ˆÏ­[var3]);
        }
    }
    
    public void à() {
        this.µà = true;
    }
    
    public void HorizonCode_Horizon_È(final Entity p_177414_1_, final AxisAlignedBB p_177414_2_, final List p_177414_3_, final Predicate p_177414_4_) {
        int var5 = MathHelper.Ý((p_177414_2_.Â - 2.0) / 16.0);
        int var6 = MathHelper.Ý((p_177414_2_.Âµá€ + 2.0) / 16.0);
        var5 = MathHelper.HorizonCode_Horizon_È(var5, 0, this.ˆÏ­.length - 1);
        var6 = MathHelper.HorizonCode_Horizon_È(var6, 0, this.ˆÏ­.length - 1);
        for (int var7 = var5; var7 <= var6; ++var7) {
            for (Entity var9 : this.ˆÏ­[var7]) {
                if (var9 != p_177414_1_ && var9.£É().Â(p_177414_2_) && (p_177414_4_ == null || p_177414_4_.apply((Object)var9))) {
                    p_177414_3_.add(var9);
                    final Entity[] var10 = var9.ÇªÔ();
                    if (var10 == null) {
                        continue;
                    }
                    for (int var11 = 0; var11 < var10.length; ++var11) {
                        var9 = var10[var11];
                        if (var9 != p_177414_1_ && var9.£É().Â(p_177414_2_) && (p_177414_4_ == null || p_177414_4_.apply((Object)var9))) {
                            p_177414_3_.add(var9);
                        }
                    }
                }
            }
        }
    }
    
    public void HorizonCode_Horizon_È(final Class p_177430_1_, final AxisAlignedBB p_177430_2_, final List p_177430_3_, final Predicate p_177430_4_) {
        int var5 = MathHelper.Ý((p_177430_2_.Â - 2.0) / 16.0);
        int var6 = MathHelper.Ý((p_177430_2_.Âµá€ + 2.0) / 16.0);
        var5 = MathHelper.HorizonCode_Horizon_È(var5, 0, this.ˆÏ­.length - 1);
        var6 = MathHelper.HorizonCode_Horizon_È(var6, 0, this.ˆÏ­.length - 1);
        for (int var7 = var5; var7 <= var6; ++var7) {
            for (final Entity var9 : this.ˆÏ­[var7].Â(p_177430_1_)) {
                if (var9.£É().Â(p_177430_2_) && (p_177430_4_ == null || p_177430_4_.apply((Object)var9))) {
                    p_177430_3_.add(var9);
                }
            }
        }
    }
    
    public boolean HorizonCode_Horizon_È(final boolean p_76601_1_) {
        if (p_76601_1_) {
            if ((this.ˆà && this.áŒŠÆ.Šáƒ() != this.¥Æ) || this.µà) {
                return true;
            }
        }
        else if (this.ˆà && this.áŒŠÆ.Šáƒ() >= this.¥Æ + 600L) {
            return true;
        }
        return this.µà;
    }
    
    public Random HorizonCode_Horizon_È(final long seed) {
        return new Random(this.áŒŠÆ.Æ() + this.HorizonCode_Horizon_È * this.HorizonCode_Horizon_È * 4987142 + this.HorizonCode_Horizon_È * 5947611 + this.Â * this.Â * 4392871L + this.Â * 389711 ^ seed);
    }
    
    public boolean Ø() {
        return false;
    }
    
    public void HorizonCode_Horizon_È(final IChunkProvider p_76624_1_, final IChunkProvider p_76624_2_, final int p_76624_3_, final int p_76624_4_) {
        final boolean var5 = p_76624_1_.HorizonCode_Horizon_È(p_76624_3_, p_76624_4_ - 1);
        final boolean var6 = p_76624_1_.HorizonCode_Horizon_È(p_76624_3_ + 1, p_76624_4_);
        final boolean var7 = p_76624_1_.HorizonCode_Horizon_È(p_76624_3_, p_76624_4_ + 1);
        final boolean var8 = p_76624_1_.HorizonCode_Horizon_È(p_76624_3_ - 1, p_76624_4_);
        final boolean var9 = p_76624_1_.HorizonCode_Horizon_È(p_76624_3_ - 1, p_76624_4_ - 1);
        final boolean var10 = p_76624_1_.HorizonCode_Horizon_È(p_76624_3_ + 1, p_76624_4_ + 1);
        final boolean var11 = p_76624_1_.HorizonCode_Horizon_È(p_76624_3_ - 1, p_76624_4_ + 1);
        final boolean var12 = p_76624_1_.HorizonCode_Horizon_È(p_76624_3_ + 1, p_76624_4_ - 1);
        if (var6 && var7 && var10) {
            if (!this.£á) {
                p_76624_1_.HorizonCode_Horizon_È(p_76624_2_, p_76624_3_, p_76624_4_);
            }
            else {
                p_76624_1_.HorizonCode_Horizon_È(p_76624_2_, this, p_76624_3_, p_76624_4_);
            }
        }
        if (var8 && var7 && var11) {
            final Chunk var13 = p_76624_1_.Ø­áŒŠá(p_76624_3_ - 1, p_76624_4_);
            if (!var13.£á) {
                p_76624_1_.HorizonCode_Horizon_È(p_76624_2_, p_76624_3_ - 1, p_76624_4_);
            }
            else {
                p_76624_1_.HorizonCode_Horizon_È(p_76624_2_, var13, p_76624_3_ - 1, p_76624_4_);
            }
        }
        if (var5 && var6 && var12) {
            final Chunk var13 = p_76624_1_.Ø­áŒŠá(p_76624_3_, p_76624_4_ - 1);
            if (!var13.£á) {
                p_76624_1_.HorizonCode_Horizon_È(p_76624_2_, p_76624_3_, p_76624_4_ - 1);
            }
            else {
                p_76624_1_.HorizonCode_Horizon_È(p_76624_2_, var13, p_76624_3_, p_76624_4_ - 1);
            }
        }
        if (var9 && var5 && var8) {
            final Chunk var13 = p_76624_1_.Ø­áŒŠá(p_76624_3_ - 1, p_76624_4_ - 1);
            if (!var13.£á) {
                p_76624_1_.HorizonCode_Horizon_È(p_76624_2_, p_76624_3_ - 1, p_76624_4_ - 1);
            }
            else {
                p_76624_1_.HorizonCode_Horizon_È(p_76624_2_, var13, p_76624_3_ - 1, p_76624_4_ - 1);
            }
        }
    }
    
    public BlockPos Ø(final BlockPos p_177440_1_) {
        final int var2 = p_177440_1_.HorizonCode_Horizon_È() & 0xF;
        final int var3 = p_177440_1_.Ý() & 0xF;
        final int var4 = var2 | var3 << 4;
        BlockPos var5 = new BlockPos(p_177440_1_.HorizonCode_Horizon_È(), this.Ó[var4], p_177440_1_.Ý());
        if (var5.Â() == -999) {
            final int var6 = this.HorizonCode_Horizon_È() + 15;
            var5 = new BlockPos(p_177440_1_.HorizonCode_Horizon_È(), var6, p_177440_1_.Ý());
            int var7 = -1;
            while (var5.Â() > 0 && var7 == -1) {
                final Block var8 = this.Ý(var5);
                final Material var9 = var8.Ó();
                if (!var9.Ø­áŒŠá() && !var9.HorizonCode_Horizon_È()) {
                    var5 = var5.Âµá€();
                }
                else {
                    var7 = var5.Â() + 1;
                }
            }
            this.Ó[var4] = var7;
        }
        return new BlockPos(p_177440_1_.HorizonCode_Horizon_È(), this.Ó[var4], p_177440_1_.Ý());
    }
    
    public void Â(final boolean p_150804_1_) {
        if (this.ÂµÈ && !this.áŒŠÆ.£à.Å() && !p_150804_1_) {
            this.Ø(this.áŒŠÆ.ŠÄ);
        }
        this.£à = true;
        if (!this.Å && this.£á) {
            this.£á();
        }
        while (!this.Šáƒ.isEmpty()) {
            final BlockPos var2 = this.Šáƒ.poll();
            if (this.HorizonCode_Horizon_È(var2, Chunk.HorizonCode_Horizon_È.Ý) == null && this.Ý(var2).£á()) {
                final TileEntity var3 = this.áŒŠÆ(var2);
                this.áŒŠÆ.HorizonCode_Horizon_È(var2, var3);
                this.áŒŠÆ.Â(var2, var2);
            }
        }
    }
    
    public boolean áŒŠÆ() {
        return this.£à && this.£á && this.Å;
    }
    
    public ChunkCoordIntPair áˆºÑ¢Õ() {
        return new ChunkCoordIntPair(this.HorizonCode_Horizon_È, this.Â);
    }
    
    public boolean Ý(int p_76606_1_, int p_76606_2_) {
        if (p_76606_1_ < 0) {
            p_76606_1_ = 0;
        }
        if (p_76606_2_ >= 256) {
            p_76606_2_ = 255;
        }
        for (int var3 = p_76606_1_; var3 <= p_76606_2_; var3 += 16) {
            final ExtendedBlockStorage var4 = this.Ø­áŒŠá[var3 >> 4];
            if (var4 != null && !var4.HorizonCode_Horizon_È()) {
                return false;
            }
        }
        return true;
    }
    
    public void HorizonCode_Horizon_È(final ExtendedBlockStorage[] newStorageArrays) {
        if (this.Ø­áŒŠá.length != newStorageArrays.length) {
            Chunk.Ý.warn("Could not set level chunk sections, array length is " + newStorageArrays.length + " instead of " + this.Ø­áŒŠá.length);
        }
        else {
            for (int var2 = 0; var2 < this.Ø­áŒŠá.length; ++var2) {
                this.Ø­áŒŠá[var2] = newStorageArrays[var2];
            }
        }
    }
    
    public void HorizonCode_Horizon_È(final byte[] p_177439_1_, final int p_177439_2_, final boolean p_177439_3_) {
        int var4 = 0;
        final boolean var5 = !this.áŒŠÆ.£à.Å();
        for (int var6 = 0; var6 < this.Ø­áŒŠá.length; ++var6) {
            if ((p_177439_2_ & 1 << var6) != 0x0) {
                if (this.Ø­áŒŠá[var6] == null) {
                    this.Ø­áŒŠá[var6] = new ExtendedBlockStorage(var6 << 4, var5);
                }
                final char[] var7 = this.Ø­áŒŠá[var6].Âµá€();
                for (int var8 = 0; var8 < var7.length; ++var8) {
                    var7[var8] = (char)((p_177439_1_[var4 + 1] & 0xFF) << 8 | (p_177439_1_[var4] & 0xFF));
                    var4 += 2;
                }
            }
            else if (p_177439_3_ && this.Ø­áŒŠá[var6] != null) {
                this.Ø­áŒŠá[var6] = null;
            }
        }
        for (int var6 = 0; var6 < this.Ø­áŒŠá.length; ++var6) {
            if ((p_177439_2_ & 1 << var6) != 0x0 && this.Ø­áŒŠá[var6] != null) {
                final NibbleArray var9 = this.Ø­áŒŠá[var6].Ó();
                System.arraycopy(p_177439_1_, var4, var9.HorizonCode_Horizon_È(), 0, var9.HorizonCode_Horizon_È().length);
                var4 += var9.HorizonCode_Horizon_È().length;
            }
        }
        if (var5) {
            for (int var6 = 0; var6 < this.Ø­áŒŠá.length; ++var6) {
                if ((p_177439_2_ & 1 << var6) != 0x0 && this.Ø­áŒŠá[var6] != null) {
                    final NibbleArray var9 = this.Ø­áŒŠá[var6].à();
                    System.arraycopy(p_177439_1_, var4, var9.HorizonCode_Horizon_È(), 0, var9.HorizonCode_Horizon_È().length);
                    var4 += var9.HorizonCode_Horizon_È().length;
                }
            }
        }
        if (p_177439_3_) {
            System.arraycopy(p_177439_1_, var4, this.Âµá€, 0, this.Âµá€.length);
            final int n = var4 + this.Âµá€.length;
        }
        for (int var6 = 0; var6 < this.Ø­áŒŠá.length; ++var6) {
            if (this.Ø­áŒŠá[var6] != null && (p_177439_2_ & 1 << var6) != 0x0) {
                this.Ø­áŒŠá[var6].Ø­áŒŠá();
            }
        }
        this.Å = true;
        this.£á = true;
        this.Ý();
        for (final TileEntity var11 : this.á.values()) {
            var11.ˆà();
        }
    }
    
    public BiomeGenBase HorizonCode_Horizon_È(final BlockPos pos, final WorldChunkManager chunkManager) {
        final int var3 = pos.HorizonCode_Horizon_È() & 0xF;
        final int var4 = pos.Ý() & 0xF;
        int var5 = this.Âµá€[var4 << 4 | var3] & 0xFF;
        if (var5 == 255) {
            final BiomeGenBase var6 = chunkManager.HorizonCode_Horizon_È(pos, BiomeGenBase.µà);
            var5 = var6.ÇªÔ;
            this.Âµá€[var4 << 4 | var3] = (byte)(var5 & 0xFF);
        }
        final BiomeGenBase var6 = BiomeGenBase.Âµá€(var5);
        return (var6 == null) ? BiomeGenBase.µà : var6;
    }
    
    public byte[] ÂµÈ() {
        return this.Âµá€;
    }
    
    public void HorizonCode_Horizon_È(final byte[] biomeArray) {
        if (this.Âµá€.length != biomeArray.length) {
            Chunk.Ý.warn("Could not set level chunk biomes, array length is " + biomeArray.length + " instead of " + this.Âµá€.length);
        }
        else {
            for (int var2 = 0; var2 < this.Âµá€.length; ++var2) {
                this.Âµá€[var2] = biomeArray[var2];
            }
        }
    }
    
    public void á() {
        this.Æ = 0;
    }
    
    public void ˆÏ­() {
        final BlockPos var1 = new BlockPos(this.HorizonCode_Horizon_È << 4, 0, this.Â << 4);
        for (int var2 = 0; var2 < 8; ++var2) {
            if (this.Æ >= 4096) {
                return;
            }
            final int var3 = this.Æ % 16;
            final int var4 = this.Æ / 16 % 16;
            final int var5 = this.Æ / 256;
            ++this.Æ;
            for (int var6 = 0; var6 < 16; ++var6) {
                final BlockPos var7 = var1.Â(var4, (var3 << 4) + var6, var5);
                final boolean var8 = var6 == 0 || var6 == 15 || var4 == 0 || var4 == 15 || var5 == 0 || var5 == 15;
                if ((this.Ø­áŒŠá[var3] == null && var8) || (this.Ø­áŒŠá[var3] != null && this.Ø­áŒŠá[var3].Â(var4, var6, var5).Ó() == Material.HorizonCode_Horizon_È)) {
                    for (final EnumFacing var12 : EnumFacing.values()) {
                        final BlockPos var13 = var7.HorizonCode_Horizon_È(var12);
                        if (this.áŒŠÆ.Â(var13).Ý().Ø­áŒŠá() > 0) {
                            this.áŒŠÆ.Šáƒ(var13);
                        }
                    }
                    this.áŒŠÆ.Šáƒ(var7);
                }
            }
        }
    }
    
    public void £á() {
        this.£á = true;
        this.Å = true;
        final BlockPos var1 = new BlockPos(this.HorizonCode_Horizon_È << 4, 0, this.Â << 4);
        if (!this.áŒŠÆ.£à.Å()) {
            if (this.áŒŠÆ.HorizonCode_Horizon_È(var1.Â(-1, 0, -1), var1.Â(16, 63, 16))) {
            Label_0116:
                for (int var2 = 0; var2 < 16; ++var2) {
                    for (int var3 = 0; var3 < 16; ++var3) {
                        if (!this.Âµá€(var2, var3)) {
                            this.Å = false;
                            break Label_0116;
                        }
                    }
                }
                if (this.Å) {
                    for (final EnumFacing var5 : EnumFacing.Ý.HorizonCode_Horizon_È) {
                        final int var6 = (var5.Ø­áŒŠá() == EnumFacing.Â.HorizonCode_Horizon_È) ? 16 : 1;
                        this.áŒŠÆ.à(var1.HorizonCode_Horizon_È(var5, var6)).HorizonCode_Horizon_È(var5.Âµá€());
                    }
                    this.Ï­Ðƒà();
                }
            }
            else {
                this.Å = false;
            }
        }
    }
    
    private void Ï­Ðƒà() {
        for (int var1 = 0; var1 < this.à.length; ++var1) {
            this.à[var1] = true;
        }
        this.Ø(false);
    }
    
    private void HorizonCode_Horizon_È(final EnumFacing p_180700_1_) {
        if (this.£á) {
            if (p_180700_1_ == EnumFacing.Ó) {
                for (int var2 = 0; var2 < 16; ++var2) {
                    this.Âµá€(15, var2);
                }
            }
            else if (p_180700_1_ == EnumFacing.Âµá€) {
                for (int var2 = 0; var2 < 16; ++var2) {
                    this.Âµá€(0, var2);
                }
            }
            else if (p_180700_1_ == EnumFacing.Ø­áŒŠá) {
                for (int var2 = 0; var2 < 16; ++var2) {
                    this.Âµá€(var2, 15);
                }
            }
            else if (p_180700_1_ == EnumFacing.Ý) {
                for (int var2 = 0; var2 < 16; ++var2) {
                    this.Âµá€(var2, 0);
                }
            }
        }
    }
    
    private boolean Âµá€(final int p_150811_1_, final int p_150811_2_) {
        final BlockPos var3 = new BlockPos(this.HorizonCode_Horizon_È << 4, 0, this.Â << 4);
        final int var4 = this.HorizonCode_Horizon_È();
        boolean var5 = false;
        boolean var6 = false;
        int var7 = var4 + 16 - 1;
        while (true) {
            if (var7 <= 63) {
                if (var7 <= 0) {
                    break;
                }
                if (var6) {
                    break;
                }
            }
            final BlockPos var8 = var3.Â(p_150811_1_, var7, p_150811_2_);
            final int var9 = this.Â(var8);
            if (var9 == 255 && var7 < 63) {
                var6 = true;
            }
            if (!var5 && var9 > 0) {
                var5 = true;
            }
            else if (var5 && var9 == 0 && !this.áŒŠÆ.Šáƒ(var8)) {
                return false;
            }
            --var7;
        }
        while (var7 > 0) {
            final BlockPos var8 = var3.Â(p_150811_1_, var7, p_150811_2_);
            if (this.Ý(var8).Ø­áŒŠá() > 0) {
                this.áŒŠÆ.Šáƒ(var8);
            }
            --var7;
        }
        return true;
    }
    
    public boolean Å() {
        return this.Ø;
    }
    
    public void Ý(final boolean p_177417_1_) {
        this.Ø = p_177417_1_;
    }
    
    public World £à() {
        return this.áŒŠÆ;
    }
    
    public int[] µà() {
        return this.áˆºÑ¢Õ;
    }
    
    public void HorizonCode_Horizon_È(final int[] newHeightMap) {
        if (this.áˆºÑ¢Õ.length != newHeightMap.length) {
            Chunk.Ý.warn("Could not set level chunk heightmap, array length is " + newHeightMap.length + " instead of " + this.áˆºÑ¢Õ.length);
        }
        else {
            for (int var2 = 0; var2 < this.áˆºÑ¢Õ.length; ++var2) {
                this.áˆºÑ¢Õ[var2] = newHeightMap[var2];
            }
        }
    }
    
    public Map ˆà() {
        return this.á;
    }
    
    public ClassInheratanceMultiMap[] ¥Æ() {
        return this.ˆÏ­;
    }
    
    public boolean Ø­à() {
        return this.£á;
    }
    
    public void Ø­áŒŠá(final boolean terrainPopulated) {
        this.£á = terrainPopulated;
    }
    
    public boolean µÕ() {
        return this.Å;
    }
    
    public void Âµá€(final boolean lightPopulated) {
        this.Å = lightPopulated;
    }
    
    public void Ó(final boolean modified) {
        this.µà = modified;
    }
    
    public void à(final boolean hasEntitiesIn) {
        this.ˆà = hasEntitiesIn;
    }
    
    public void Â(final long saveTime) {
        this.¥Æ = saveTime;
    }
    
    public int Æ() {
        return this.Ø­à;
    }
    
    public long Šáƒ() {
        return this.µÕ;
    }
    
    public void Ý(final long newInhabitedTime) {
        this.µÕ = newInhabitedTime;
    }
    
    public enum HorizonCode_Horizon_È
    {
        HorizonCode_Horizon_È("IMMEDIATE", 0, "IMMEDIATE", 0), 
        Â("QUEUED", 1, "QUEUED", 1), 
        Ý("CHECK", 2, "CHECK", 2);
        
        private static final HorizonCode_Horizon_È[] Ø­áŒŠá;
        private static final String Âµá€ = "CL_00002009";
        
        static {
            Ó = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý };
            Ø­áŒŠá = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý };
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45642_1_, final int p_i45642_2_) {
        }
    }
}
