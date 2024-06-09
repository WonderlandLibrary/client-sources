package HORIZON-6-0-SKIDPROTECTION;

public abstract class TileEntityLockable extends TileEntity implements ILockableContainer, IInteractionObject
{
    private LockCode Âµá€;
    private static final String Ó = "CL_00002040";
    
    public TileEntityLockable() {
        this.Âµá€ = LockCode.HorizonCode_Horizon_È;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final NBTTagCompound compound) {
        super.HorizonCode_Horizon_È(compound);
        this.Âµá€ = LockCode.Â(compound);
    }
    
    @Override
    public void Â(final NBTTagCompound compound) {
        super.Â(compound);
        if (this.Âµá€ != null) {
            this.Âµá€.HorizonCode_Horizon_È(compound);
        }
    }
    
    @Override
    public boolean Ó() {
        return this.Âµá€ != null && !this.Âµá€.HorizonCode_Horizon_È();
    }
    
    @Override
    public LockCode x_() {
        return this.Âµá€;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final LockCode code) {
        this.Âµá€ = code;
    }
    
    @Override
    public IChatComponent Ý() {
        return this.j_() ? new ChatComponentText(this.v_()) : new ChatComponentTranslation(this.v_(), new Object[0]);
    }
}
