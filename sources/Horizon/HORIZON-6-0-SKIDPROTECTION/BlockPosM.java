package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.collect.AbstractIterator;
import java.util.Iterator;

public class BlockPosM extends BlockPos
{
    private int Â;
    private int Ý;
    private int Ø­áŒŠá;
    private int Ó;
    private BlockPosM[] à;
    private boolean Ø;
    
    public BlockPosM(final int x, final int y, final int z) {
        this(x, y, z, 0);
    }
    
    public BlockPosM(final double xIn, final double yIn, final double zIn) {
        this(MathHelper.Ý(xIn), MathHelper.Ý(yIn), MathHelper.Ý(zIn));
    }
    
    public BlockPosM(final int x, final int y, final int z, final int level) {
        super(0, 0, 0);
        this.Â = x;
        this.Ý = y;
        this.Ø­áŒŠá = z;
        this.Ó = level;
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
    
    public void HorizonCode_Horizon_È(final int x, final int y, final int z) {
        this.Â = x;
        this.Ý = y;
        this.Ø­áŒŠá = z;
        this.Ø = true;
    }
    
    public void HorizonCode_Horizon_È(final double xIn, final double yIn, final double zIn) {
        this.HorizonCode_Horizon_È(MathHelper.Ý(xIn), MathHelper.Ý(yIn), MathHelper.Ý(zIn));
    }
    
    @Override
    public BlockPos HorizonCode_Horizon_È(final EnumFacing facing) {
        if (this.Ó <= 0) {
            return super.HorizonCode_Horizon_È(facing, 1);
        }
        if (this.à == null) {
            this.à = new BlockPosM[EnumFacing.à.length];
        }
        if (this.Ø) {
            this.ÂµÈ();
        }
        final int index = facing.Â();
        BlockPosM bpm = this.à[index];
        if (bpm == null) {
            final int nx = this.Â + facing.Ø();
            final int ny = this.Ý + facing.áŒŠÆ();
            final int nz = this.Ø­áŒŠá + facing.áˆºÑ¢Õ();
            bpm = new BlockPosM(nx, ny, nz, this.Ó - 1);
            this.à[index] = bpm;
        }
        return bpm;
    }
    
    @Override
    public BlockPos HorizonCode_Horizon_È(final EnumFacing facing, final int n) {
        return (n == 1) ? this.HorizonCode_Horizon_È(facing) : super.HorizonCode_Horizon_È(facing, 1);
    }
    
    private void ÂµÈ() {
        for (int i = 0; i < 6; ++i) {
            final BlockPosM bpm = this.à[i];
            if (bpm != null) {
                final EnumFacing facing = EnumFacing.à[i];
                final int nx = this.Â + facing.Ø();
                final int ny = this.Ý + facing.áŒŠÆ();
                final int nz = this.Ø­áŒŠá + facing.áˆºÑ¢Õ();
                bpm.HorizonCode_Horizon_È(nx, ny, nz);
            }
        }
        this.Ø = false;
    }
    
    public static Iterable HorizonCode_Horizon_È(final BlockPos from, final BlockPos to) {
        final BlockPos posFrom = new BlockPos(Math.min(from.HorizonCode_Horizon_È(), to.HorizonCode_Horizon_È()), Math.min(from.Â(), to.Â()), Math.min(from.Ý(), to.Ý()));
        final BlockPos posTo = new BlockPos(Math.max(from.HorizonCode_Horizon_È(), to.HorizonCode_Horizon_È()), Math.max(from.Â(), to.Â()), Math.max(from.Ý(), to.Ý()));
        return new Iterable() {
            @Override
            public Iterator iterator() {
                return (Iterator)new AbstractIterator() {
                    private BlockPosM Â = null;
                    
                    protected BlockPosM HorizonCode_Horizon_È() {
                        if (this.Â == null) {
                            return this.Â = new BlockPosM(posFrom.HorizonCode_Horizon_È(), posFrom.Â(), posFrom.Ý(), 3);
                        }
                        if (this.Â.equals(posTo)) {
                            return (BlockPosM)this.endOfData();
                        }
                        int bx = this.Â.HorizonCode_Horizon_È();
                        int by = this.Â.Â();
                        int bz = this.Â.Ý();
                        if (bx < posTo.HorizonCode_Horizon_È()) {
                            ++bx;
                        }
                        else if (by < posTo.Â()) {
                            bx = posFrom.HorizonCode_Horizon_È();
                            ++by;
                        }
                        else if (bz < posTo.Ý()) {
                            bx = posFrom.HorizonCode_Horizon_È();
                            by = posFrom.Â();
                            ++bz;
                        }
                        this.Â.HorizonCode_Horizon_È(bx, by, bz);
                        return this.Â;
                    }
                    
                    protected Object computeNext() {
                        return this.HorizonCode_Horizon_È();
                    }
                };
            }
        };
    }
}
