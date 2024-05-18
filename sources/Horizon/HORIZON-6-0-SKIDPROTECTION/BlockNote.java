package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.collect.Lists;
import java.util.List;

public class BlockNote extends BlockContainer
{
    private static final List Õ;
    private static final String à¢ = "CL_00000278";
    
    static {
        Õ = Lists.newArrayList((Object[])new String[] { "harp", "bd", "snare", "hat", "bassattack" });
    }
    
    public BlockNote() {
        super(Material.Ø­áŒŠá);
        this.HorizonCode_Horizon_È(CreativeTabs.Ø­áŒŠá);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Block neighborBlock) {
        final boolean var5 = worldIn.áŒŠà(pos);
        final TileEntity var6 = worldIn.HorizonCode_Horizon_È(pos);
        if (var6 instanceof TileEntityNote) {
            final TileEntityNote var7 = (TileEntityNote)var6;
            if (var7.Ó != var5) {
                if (var5) {
                    var7.HorizonCode_Horizon_È(worldIn, pos);
                }
                var7.Ó = var5;
            }
        }
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (worldIn.ŠÄ) {
            return true;
        }
        final TileEntity var9 = worldIn.HorizonCode_Horizon_È(pos);
        if (var9 instanceof TileEntityNote) {
            final TileEntityNote var10 = (TileEntityNote)var9;
            var10.HorizonCode_Horizon_È();
            var10.HorizonCode_Horizon_È(worldIn, pos);
        }
        return true;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final EntityPlayer playerIn) {
        if (!worldIn.ŠÄ) {
            final TileEntity var4 = worldIn.HorizonCode_Horizon_È(pos);
            if (var4 instanceof TileEntityNote) {
                ((TileEntityNote)var4).HorizonCode_Horizon_È(worldIn, pos);
            }
        }
    }
    
    @Override
    public TileEntity HorizonCode_Horizon_È(final World worldIn, final int meta) {
        return new TileEntityNote();
    }
    
    private String Âµá€(int p_176433_1_) {
        if (p_176433_1_ < 0 || p_176433_1_ >= BlockNote.Õ.size()) {
            p_176433_1_ = 0;
        }
        return BlockNote.Õ.get(p_176433_1_);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final int eventID, final int eventParam) {
        final float var6 = (float)Math.pow(2.0, (eventParam - 12) / 12.0);
        worldIn.HorizonCode_Horizon_È(pos.HorizonCode_Horizon_È() + 0.5, pos.Â() + 0.5, pos.Ý() + 0.5, "note." + this.Âµá€(eventID), 3.0f, var6);
        worldIn.HorizonCode_Horizon_È(EnumParticleTypes.Ï­Ðƒà, pos.HorizonCode_Horizon_È() + 0.5, pos.Â() + 1.2, pos.Ý() + 0.5, eventParam / 24.0, 0.0, 0.0, new int[0]);
        return true;
    }
    
    @Override
    public int ÂµÈ() {
        return 3;
    }
}
