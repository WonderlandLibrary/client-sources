package HORIZON-6-0-SKIDPROTECTION;

public final class EventBlockBoundingBox extends EventMain
{
    private final Block HorizonCode_Horizon_È;
    private AxisAlignedBB Â;
    private final int Ý;
    private final int Ø­áŒŠá;
    private final int Âµá€;
    
    public EventBlockBoundingBox(final AxisAlignedBB boundingBox, final Block block, final int x, final int y, final int z) {
        this.Â = boundingBox;
        this.HorizonCode_Horizon_È = block;
        this.Ý = x;
        this.Ø­áŒŠá = y;
        this.Âµá€ = z;
    }
    
    public Block HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public Block HorizonCode_Horizon_È(final double posX, final double d, final double posZ) {
        return this.HorizonCode_Horizon_È;
    }
    
    public AxisAlignedBB Â() {
        return this.Â;
    }
    
    public int Ý() {
        return this.Ý;
    }
    
    public int Ø­áŒŠá() {
        return this.Ø­áŒŠá;
    }
    
    public int Âµá€() {
        return this.Âµá€;
    }
    
    public void HorizonCode_Horizon_È(final AxisAlignedBB boundingBox) {
        this.Â = boundingBox;
    }
}
