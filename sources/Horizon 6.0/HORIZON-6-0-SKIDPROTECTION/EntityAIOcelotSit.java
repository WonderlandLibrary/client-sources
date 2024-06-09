package HORIZON-6-0-SKIDPROTECTION;

public class EntityAIOcelotSit extends EntityAIMoveToBlock
{
    private final EntityOcelot Ý;
    private static final String Ø­áŒŠá = "CL_00001601";
    
    public EntityAIOcelotSit(final EntityOcelot p_i45315_1_, final double p_i45315_2_) {
        super(p_i45315_1_, p_i45315_2_, 8);
        this.Ý = p_i45315_1_;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        return this.Ý.ÐƒÓ() && !this.Ý.áˆºÕ() && super.HorizonCode_Horizon_È();
    }
    
    @Override
    public boolean Â() {
        return super.Â();
    }
    
    @Override
    public void Âµá€() {
        super.Âµá€();
        this.Ý.ÐƒáˆºÄ().HorizonCode_Horizon_È(false);
    }
    
    @Override
    public void Ý() {
        super.Ý();
        this.Ý.£á(false);
    }
    
    @Override
    public void Ø­áŒŠá() {
        super.Ø­áŒŠá();
        this.Ý.ÐƒáˆºÄ().HorizonCode_Horizon_È(false);
        if (!this.Ø()) {
            this.Ý.£á(false);
        }
        else if (!this.Ý.áˆºÕ()) {
            this.Ý.£á(true);
        }
    }
    
    @Override
    protected boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos p_179488_2_) {
        if (!worldIn.Ø­áŒŠá(p_179488_2_.Ø­áŒŠá())) {
            return false;
        }
        final IBlockState var3 = worldIn.Â(p_179488_2_);
        final Block var4 = var3.Ý();
        if (var4 == Blocks.ˆáƒ) {
            final TileEntity var5 = worldIn.HorizonCode_Horizon_È(p_179488_2_);
            if (var5 instanceof TileEntityChest && ((TileEntityChest)var5).á < 1) {
                return true;
            }
        }
        else {
            if (var4 == Blocks.ˆÐƒØ­à) {
                return true;
            }
            if (var4 == Blocks.Ê && var3.HorizonCode_Horizon_È(BlockBed.Õ) != BlockBed.HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
                return true;
            }
        }
        return false;
    }
}
