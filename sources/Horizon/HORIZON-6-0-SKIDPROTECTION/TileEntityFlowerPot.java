package HORIZON-6-0-SKIDPROTECTION;

public class TileEntityFlowerPot extends TileEntity
{
    private Item_1028566121 Âµá€;
    private int Ó;
    private static final String à = "CL_00000356";
    
    public TileEntityFlowerPot() {
    }
    
    public TileEntityFlowerPot(final Item_1028566121 p_i45442_1_, final int p_i45442_2_) {
        this.Âµá€ = p_i45442_1_;
        this.Ó = p_i45442_2_;
    }
    
    @Override
    public void Â(final NBTTagCompound compound) {
        super.Â(compound);
        final ResourceLocation_1975012498 var2 = (ResourceLocation_1975012498)Item_1028566121.HorizonCode_Horizon_È.Â(this.Âµá€);
        compound.HorizonCode_Horizon_È("Item", (var2 == null) ? "" : var2.toString());
        compound.HorizonCode_Horizon_È("Data", this.Ó);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final NBTTagCompound compound) {
        super.HorizonCode_Horizon_È(compound);
        if (compound.Â("Item", 8)) {
            this.Âµá€ = Item_1028566121.HorizonCode_Horizon_È(compound.áˆºÑ¢Õ("Item"));
        }
        else {
            this.Âµá€ = Item_1028566121.HorizonCode_Horizon_È(compound.Ó("Item"));
        }
        this.Ó = compound.Ó("Data");
    }
    
    @Override
    public Packet £á() {
        final NBTTagCompound var1 = new NBTTagCompound();
        this.Â(var1);
        var1.Å("Item");
        var1.HorizonCode_Horizon_È("Item", Item_1028566121.HorizonCode_Horizon_È(this.Âµá€));
        return new S35PacketUpdateTileEntity(this.Â, 5, var1);
    }
    
    public void HorizonCode_Horizon_È(final Item_1028566121 p_145964_1_, final int p_145964_2_) {
        this.Âµá€ = p_145964_1_;
        this.Ó = p_145964_2_;
    }
    
    public Item_1028566121 HorizonCode_Horizon_È() {
        return this.Âµá€;
    }
    
    public int Â() {
        return this.Ó;
    }
}
