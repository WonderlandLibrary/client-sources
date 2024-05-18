package HORIZON-6-0-SKIDPROTECTION;

public class DemoWorldManager extends ItemInWorldManager
{
    private boolean Ý;
    private boolean Ø­áŒŠá;
    private int Âµá€;
    private int Ó;
    private static final String à = "CL_00001429";
    
    public DemoWorldManager(final World worldIn) {
        super(worldIn);
    }
    
    @Override
    public void Ø­áŒŠá() {
        super.Ø­áŒŠá();
        ++this.Ó;
        final long var1 = this.HorizonCode_Horizon_È.Šáƒ();
        final long var2 = var1 / 24000L + 1L;
        if (!this.Ý && this.Ó > 20) {
            this.Ý = true;
            this.Â.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S2BPacketChangeGameState(5, 0.0f));
        }
        this.Ø­áŒŠá = (var1 > 120500L);
        if (this.Ø­áŒŠá) {
            ++this.Âµá€;
        }
        if (var1 % 24000L == 500L) {
            if (var2 <= 6L) {
                this.Â.HorizonCode_Horizon_È(new ChatComponentTranslation("demo.day." + var2, new Object[0]));
            }
        }
        else if (var2 == 1L) {
            if (var1 == 100L) {
                this.Â.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S2BPacketChangeGameState(5, 101.0f));
            }
            else if (var1 == 175L) {
                this.Â.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S2BPacketChangeGameState(5, 102.0f));
            }
            else if (var1 == 250L) {
                this.Â.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S2BPacketChangeGameState(5, 103.0f));
            }
        }
        else if (var2 == 5L && var1 % 24000L == 22000L) {
            this.Â.HorizonCode_Horizon_È(new ChatComponentTranslation("demo.day.warning", new Object[0]));
        }
    }
    
    private void Ó() {
        if (this.Âµá€ > 100) {
            this.Â.HorizonCode_Horizon_È(new ChatComponentTranslation("demo.reminder", new Object[0]));
            this.Âµá€ = 0;
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final BlockPos p_180784_1_, final EnumFacing p_180784_2_) {
        if (this.Ø­áŒŠá) {
            this.Ó();
        }
        else {
            super.HorizonCode_Horizon_È(p_180784_1_, p_180784_2_);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final BlockPos p_180785_1_) {
        if (!this.Ø­áŒŠá) {
            super.HorizonCode_Horizon_È(p_180785_1_);
        }
    }
    
    @Override
    public boolean Â(final BlockPos p_180237_1_) {
        return !this.Ø­áŒŠá && super.Â(p_180237_1_);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final EntityPlayer p_73085_1_, final World worldIn, final ItemStack p_73085_3_) {
        if (this.Ø­áŒŠá) {
            this.Ó();
            return false;
        }
        return super.HorizonCode_Horizon_È(p_73085_1_, worldIn, p_73085_3_);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final EntityPlayer p_180236_1_, final World worldIn, final ItemStack p_180236_3_, final BlockPos p_180236_4_, final EnumFacing p_180236_5_, final float p_180236_6_, final float p_180236_7_, final float p_180236_8_) {
        if (this.Ø­áŒŠá) {
            this.Ó();
            return false;
        }
        return super.HorizonCode_Horizon_È(p_180236_1_, worldIn, p_180236_3_, p_180236_4_, p_180236_5_, p_180236_6_, p_180236_7_, p_180236_8_);
    }
}
