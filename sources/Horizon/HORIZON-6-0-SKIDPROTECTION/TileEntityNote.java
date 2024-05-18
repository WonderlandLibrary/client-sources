package HORIZON-6-0-SKIDPROTECTION;

public class TileEntityNote extends TileEntity
{
    public byte Âµá€;
    public boolean Ó;
    private static final String à = "CL_00000362";
    
    @Override
    public void Â(final NBTTagCompound compound) {
        super.Â(compound);
        compound.HorizonCode_Horizon_È("note", this.Âµá€);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final NBTTagCompound compound) {
        super.HorizonCode_Horizon_È(compound);
        this.Âµá€ = compound.Ø­áŒŠá("note");
        this.Âµá€ = (byte)MathHelper.HorizonCode_Horizon_È(this.Âµá€, 0, 24);
    }
    
    public void HorizonCode_Horizon_È() {
        this.Âµá€ = (byte)((this.Âµá€ + 1) % 25);
        this.ŠÄ();
    }
    
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos p_175108_2_) {
        if (worldIn.Â(p_175108_2_.Ø­áŒŠá()).Ý().Ó() == Material.HorizonCode_Horizon_È) {
            final Material var3 = worldIn.Â(p_175108_2_.Âµá€()).Ý().Ó();
            byte var4 = 0;
            if (var3 == Material.Âµá€) {
                var4 = 1;
            }
            if (var3 == Material.£à) {
                var4 = 2;
            }
            if (var3 == Material.¥Æ) {
                var4 = 3;
            }
            if (var3 == Material.Ø­áŒŠá) {
                var4 = 4;
            }
            worldIn.Ý(p_175108_2_, Blocks.Çªà¢, var4, this.Âµá€);
        }
    }
}
