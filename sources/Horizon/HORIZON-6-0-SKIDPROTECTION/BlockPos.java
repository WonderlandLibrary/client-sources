package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.collect.AbstractIterator;
import java.util.Iterator;

public class BlockPos extends Vec3i
{
    public static final BlockPos HorizonCode_Horizon_È;
    private static final int Â;
    private static final int Ý;
    private static final int Ø­áŒŠá;
    private static final int Ó;
    private static final int à;
    private static final long Ø;
    private static final long áŒŠÆ;
    private static final long áˆºÑ¢Õ;
    private static final String ÂµÈ = "CL_00002334";
    
    static {
        HorizonCode_Horizon_È = new BlockPos(0, 0, 0);
        Â = 1 + MathHelper.Ý(MathHelper.Â(30000000));
        Ý = BlockPos.Â;
        Ø­áŒŠá = 64 - BlockPos.Â - BlockPos.Ý;
        Ó = 0 + BlockPos.Ý;
        à = BlockPos.Ó + BlockPos.Ø­áŒŠá;
        Ø = (1L << BlockPos.Â) - 1L;
        áŒŠÆ = (1L << BlockPos.Ø­áŒŠá) - 1L;
        áˆºÑ¢Õ = (1L << BlockPos.Ý) - 1L;
    }
    
    public BlockPos(final int x, final int y, final int z) {
        super(x, y, z);
    }
    
    public BlockPos(final double x, final double y, final double z) {
        super(x, y, z);
    }
    
    public BlockPos(final Entity p_i46032_1_) {
        this(p_i46032_1_.ŒÏ, p_i46032_1_.Çªà¢, p_i46032_1_.Ê);
    }
    
    public BlockPos(final Vec3 p_i46033_1_) {
        this(p_i46033_1_.HorizonCode_Horizon_È, p_i46033_1_.Â, p_i46033_1_.Ý);
    }
    
    public BlockPos(final Vec3i p_i46034_1_) {
        this(p_i46034_1_.HorizonCode_Horizon_È(), p_i46034_1_.Â(), p_i46034_1_.Ý());
    }
    
    public BlockPos Â(final double x, final double y, final double z) {
        return new BlockPos(this.HorizonCode_Horizon_È() + x, this.Â() + y, this.Ý() + z);
    }
    
    public BlockPos Â(final int x, final int y, final int z) {
        return new BlockPos(this.HorizonCode_Horizon_È() + x, this.Â() + y, this.Ý() + z);
    }
    
    public BlockPos HorizonCode_Horizon_È(final Vec3i vec) {
        return new BlockPos(this.HorizonCode_Horizon_È() + vec.HorizonCode_Horizon_È(), this.Â() + vec.Â(), this.Ý() + vec.Ý());
    }
    
    public BlockPos Â(final Vec3i vec) {
        return new BlockPos(this.HorizonCode_Horizon_È() - vec.HorizonCode_Horizon_È(), this.Â() - vec.Â(), this.Ý() - vec.Ý());
    }
    
    public BlockPos HorizonCode_Horizon_È(final int factor) {
        return new BlockPos(this.HorizonCode_Horizon_È() * factor, this.Â() * factor, this.Ý() * factor);
    }
    
    public BlockPos Ø­áŒŠá() {
        return this.Â(1);
    }
    
    public BlockPos Â(final int n) {
        return this.HorizonCode_Horizon_È(EnumFacing.Â, n);
    }
    
    public BlockPos Âµá€() {
        return this.Ý(1);
    }
    
    public BlockPos Ý(final int n) {
        return this.HorizonCode_Horizon_È(EnumFacing.HorizonCode_Horizon_È, n);
    }
    
    public BlockPos Ó() {
        return this.Ø­áŒŠá(1);
    }
    
    public BlockPos Ø­áŒŠá(final int n) {
        return this.HorizonCode_Horizon_È(EnumFacing.Ý, n);
    }
    
    public BlockPos à() {
        return this.Âµá€(1);
    }
    
    public BlockPos Âµá€(final int n) {
        return this.HorizonCode_Horizon_È(EnumFacing.Ø­áŒŠá, n);
    }
    
    public BlockPos Ø() {
        return this.Ó(1);
    }
    
    public BlockPos Ó(final int n) {
        return this.HorizonCode_Horizon_È(EnumFacing.Âµá€, n);
    }
    
    public BlockPos áŒŠÆ() {
        return this.à(1);
    }
    
    public BlockPos à(final int n) {
        return this.HorizonCode_Horizon_È(EnumFacing.Ó, n);
    }
    
    public BlockPos HorizonCode_Horizon_È(final EnumFacing facing) {
        return this.HorizonCode_Horizon_È(facing, 1);
    }
    
    public BlockPos HorizonCode_Horizon_È(final EnumFacing facing, final int n) {
        return new BlockPos(this.HorizonCode_Horizon_È() + facing.Ø() * n, this.Â() + facing.áŒŠÆ() * n, this.Ý() + facing.áˆºÑ¢Õ() * n);
    }
    
    public BlockPos Ý(final Vec3i vec) {
        return new BlockPos(this.Â() * vec.Ý() - this.Ý() * vec.Â(), this.Ý() * vec.HorizonCode_Horizon_È() - this.HorizonCode_Horizon_È() * vec.Ý(), this.HorizonCode_Horizon_È() * vec.Â() - this.Â() * vec.HorizonCode_Horizon_È());
    }
    
    public long áˆºÑ¢Õ() {
        return (this.HorizonCode_Horizon_È() & BlockPos.Ø) << BlockPos.à | (this.Â() & BlockPos.áŒŠÆ) << BlockPos.Ó | (this.Ý() & BlockPos.áˆºÑ¢Õ) << 0;
    }
    
    public static BlockPos HorizonCode_Horizon_È(final long serialized) {
        final int var2 = (int)(serialized << 64 - BlockPos.à - BlockPos.Â >> 64 - BlockPos.Â);
        final int var3 = (int)(serialized << 64 - BlockPos.Ó - BlockPos.Ø­áŒŠá >> 64 - BlockPos.Ø­áŒŠá);
        final int var4 = (int)(serialized << 64 - BlockPos.Ý >> 64 - BlockPos.Ý);
        return new BlockPos(var2, var3, var4);
    }
    
    public static Iterable Â(final BlockPos from, final BlockPos to) {
        final BlockPos var2 = new BlockPos(Math.min(from.HorizonCode_Horizon_È(), to.HorizonCode_Horizon_È()), Math.min(from.Â(), to.Â()), Math.min(from.Ý(), to.Ý()));
        final BlockPos var3 = new BlockPos(Math.max(from.HorizonCode_Horizon_È(), to.HorizonCode_Horizon_È()), Math.max(from.Â(), to.Â()), Math.max(from.Ý(), to.Ý()));
        return new Iterable() {
            private static final String HorizonCode_Horizon_È = "CL_00002333";
            
            @Override
            public Iterator iterator() {
                return (Iterator)new AbstractIterator() {
                    private BlockPos Â = null;
                    private static final String Ý = "CL_00002332";
                    
                    protected BlockPos HorizonCode_Horizon_È() {
                        if (this.Â == null) {
                            return this.Â = var2;
                        }
                        if (this.Â.equals(var3)) {
                            return (BlockPos)this.endOfData();
                        }
                        int var1 = this.Â.HorizonCode_Horizon_È();
                        int var2x = this.Â.Â();
                        int var3x = this.Â.Ý();
                        if (var1 < var3.HorizonCode_Horizon_È()) {
                            ++var1;
                        }
                        else if (var2x < var3.Â()) {
                            var1 = var2.HorizonCode_Horizon_È();
                            ++var2x;
                        }
                        else if (var3x < var3.Ý()) {
                            var1 = var2.HorizonCode_Horizon_È();
                            var2x = var2.Â();
                            ++var3x;
                        }
                        return this.Â = new BlockPos(var1, var2x, var3x);
                    }
                    
                    protected Object computeNext() {
                        return this.HorizonCode_Horizon_È();
                    }
                };
            }
        };
    }
    
    public static Iterable Ý(final BlockPos from, final BlockPos to) {
        final BlockPos var2 = new BlockPos(Math.min(from.HorizonCode_Horizon_È(), to.HorizonCode_Horizon_È()), Math.min(from.Â(), to.Â()), Math.min(from.Ý(), to.Ý()));
        final BlockPos var3 = new BlockPos(Math.max(from.HorizonCode_Horizon_È(), to.HorizonCode_Horizon_È()), Math.max(from.Â(), to.Â()), Math.max(from.Ý(), to.Ý()));
        return new Iterable() {
            private static final String HorizonCode_Horizon_È = "CL_00002331";
            
            @Override
            public Iterator iterator() {
                return (Iterator)new AbstractIterator() {
                    private HorizonCode_Horizon_È Â = null;
                    private static final String Ý = "CL_00002330";
                    
                    protected HorizonCode_Horizon_È HorizonCode_Horizon_È() {
                        if (this.Â == null) {
                            return this.Â = new HorizonCode_Horizon_È(var2.HorizonCode_Horizon_È(), var2.Â(), var2.Ý(), null);
                        }
                        if (this.Â.equals(var3)) {
                            return (HorizonCode_Horizon_È)this.endOfData();
                        }
                        int var1 = this.Â.HorizonCode_Horizon_È();
                        int var2xx = this.Â.Â();
                        int var3x = this.Â.Ý();
                        if (var1 < var3.HorizonCode_Horizon_È()) {
                            ++var1;
                        }
                        else if (var2xx < var3.Â()) {
                            var1 = var2.HorizonCode_Horizon_È();
                            ++var2xx;
                        }
                        else if (var3x < var3.Ý()) {
                            var1 = var2.HorizonCode_Horizon_È();
                            var2xx = var2.Â();
                            ++var3x;
                        }
                        this.Â.Â = var1;
                        this.Â.Ý = var2xx;
                        this.Â.Ø­áŒŠá = var3x;
                        return this.Â;
                    }
                    
                    protected Object computeNext() {
                        return this.HorizonCode_Horizon_È();
                    }
                };
            }
        };
    }
    
    @Override
    public Vec3i Ø­áŒŠá(final Vec3i vec) {
        return this.Ý(vec);
    }
    
    public static final class HorizonCode_Horizon_È extends BlockPos
    {
        public int Â;
        public int Ý;
        public int Ø­áŒŠá;
        private static final String Ó = "CL_00002329";
        
        private HorizonCode_Horizon_È(final int x_, final int y_, final int z_) {
            super(0, 0, 0);
            this.Â = x_;
            this.Ý = y_;
            this.Ø­áŒŠá = z_;
        }
        
        @Override
        public int HorizonCode_Horizon_È() {
            return this.Â;
        }
        
        @Override
        public int Â() {
            return this.Ý;
        }
        
        @Override
        public int Ý() {
            return this.Ø­áŒŠá;
        }
        
        @Override
        public Vec3i Ø­áŒŠá(final Vec3i vec) {
            return super.Ý(vec);
        }
        
        HorizonCode_Horizon_È(final int p_i46025_1_, final int p_i46025_2_, final int p_i46025_3_, final Object p_i46025_4_) {
            this(p_i46025_1_, p_i46025_2_, p_i46025_3_);
        }
    }
}
