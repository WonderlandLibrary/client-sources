package HORIZON-6-0-SKIDPROTECTION;

public class TileEntityComparator extends TileEntity
{
    private int Âµá€;
    private static final String Ó = "CL_00000349";
    
    @Override
    public void Â(final NBTTagCompound compound) {
        super.Â(compound);
        compound.HorizonCode_Horizon_È("OutputSignal", this.Âµá€);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final NBTTagCompound compound) {
        super.HorizonCode_Horizon_È(compound);
        this.Âµá€ = compound.Ó("OutputSignal");
    }
    
    public int HorizonCode_Horizon_È() {
        return this.Âµá€;
    }
    
    public void HorizonCode_Horizon_È(final int p_145995_1_) {
        this.Âµá€ = p_145995_1_;
    }
}
