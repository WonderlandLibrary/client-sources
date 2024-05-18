package HORIZON-6-0-SKIDPROTECTION;

public class EntityAIHarvestFarmland extends EntityAIMoveToBlock
{
    private final EntityVillager Ý;
    private boolean Ø­áŒŠá;
    private boolean Âµá€;
    private int Ó;
    private static final String à = "CL_00002253";
    
    public EntityAIHarvestFarmland(final EntityVillager p_i45889_1_, final double p_i45889_2_) {
        super(p_i45889_1_, p_i45889_2_, 16);
        this.Ý = p_i45889_1_;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        if (this.HorizonCode_Horizon_È <= 0) {
            if (!this.Ý.Ï­Ðƒà.Çªà¢().Â("mobGriefing")) {
                return false;
            }
            this.Ó = -1;
            this.Ø­áŒŠá = this.Ý.áˆºÉ();
            this.Âµá€ = this.Ý.ÐƒáˆºÄ();
        }
        return super.HorizonCode_Horizon_È();
    }
    
    @Override
    public boolean Â() {
        return this.Ó >= 0 && super.Â();
    }
    
    @Override
    public void Âµá€() {
        super.Âµá€();
    }
    
    @Override
    public void Ý() {
        super.Ý();
    }
    
    @Override
    public void Ø­áŒŠá() {
        super.Ø­áŒŠá();
        this.Ý.Ñ¢á().HorizonCode_Horizon_È(this.Â.HorizonCode_Horizon_È() + 0.5, this.Â.Â() + 1, this.Â.Ý() + 0.5, 10.0f, this.Ý.áˆºà());
        if (this.Ø()) {
            final World var1 = this.Ý.Ï­Ðƒà;
            final BlockPos var2 = this.Â.Ø­áŒŠá();
            final IBlockState var3 = var1.Â(var2);
            final Block var4 = var3.Ý();
            if (this.Ó == 0 && var4 instanceof BlockCrops && (int)var3.HorizonCode_Horizon_È(BlockCrops.Õ) == 7) {
                var1.Â(var2, true);
            }
            else if (this.Ó == 1 && var4 == Blocks.Â) {
                final InventoryBasic var5 = this.Ý.ÐƒÓ();
                int var6 = 0;
                while (var6 < var5.áŒŠÆ()) {
                    final ItemStack var7 = var5.á(var6);
                    boolean var8 = false;
                    if (var7 != null) {
                        if (var7.HorizonCode_Horizon_È() == Items.¥à) {
                            var1.HorizonCode_Horizon_È(var2, Blocks.Ï­Ï­Ï.¥à(), 3);
                            var8 = true;
                        }
                        else if (var7.HorizonCode_Horizon_È() == Items.ˆÂ) {
                            var1.HorizonCode_Horizon_È(var2, Blocks.Çªáˆºá.¥à(), 3);
                            var8 = true;
                        }
                        else if (var7.HorizonCode_Horizon_È() == Items.¥áŒŠà) {
                            var1.HorizonCode_Horizon_È(var2, Blocks.Ñ¢È.¥à(), 3);
                            var8 = true;
                        }
                    }
                    if (var8) {
                        final ItemStack itemStack = var7;
                        --itemStack.Â;
                        if (var7.Â <= 0) {
                            var5.Ý(var6, null);
                            break;
                        }
                        break;
                    }
                    else {
                        ++var6;
                    }
                }
            }
            this.Ó = -1;
            this.HorizonCode_Horizon_È = 10;
        }
    }
    
    @Override
    protected boolean HorizonCode_Horizon_È(final World worldIn, BlockPos p_179488_2_) {
        Block var3 = worldIn.Â(p_179488_2_).Ý();
        if (var3 == Blocks.£Â) {
            p_179488_2_ = p_179488_2_.Ø­áŒŠá();
            final IBlockState var4 = worldIn.Â(p_179488_2_);
            var3 = var4.Ý();
            if (var3 instanceof BlockCrops && (int)var4.HorizonCode_Horizon_È(BlockCrops.Õ) == 7 && this.Âµá€ && (this.Ó == 0 || this.Ó < 0)) {
                this.Ó = 0;
                return true;
            }
            if (var3 == Blocks.Â && this.Ø­áŒŠá && (this.Ó == 1 || this.Ó < 0)) {
                this.Ó = 1;
                return true;
            }
        }
        return false;
    }
}
