package HORIZON-6-0-SKIDPROTECTION;

public class EventBoundingBox extends Event
{
    private Block HorizonCode_Horizon_È;
    private int Â;
    private int Ý;
    private int Ø­áŒŠá;
    private AxisAlignedBB Âµá€;
    
    public Event HorizonCode_Horizon_È(final Block b, final BlockPos pos, final AxisAlignedBB bb) {
        this.HorizonCode_Horizon_È = b;
        this.Â = pos.HorizonCode_Horizon_È();
        this.Ý = pos.Â();
        this.Ø­áŒŠá = pos.Ý();
        this.Âµá€ = bb;
        return super.Â();
    }
    
    public Block Ý() {
        return this.HorizonCode_Horizon_È;
    }
    
    public int Ø­áŒŠá() {
        return this.Â;
    }
    
    public int Âµá€() {
        return this.Ý;
    }
    
    public int Ó() {
        return this.Ø­áŒŠá;
    }
    
    public AxisAlignedBB à() {
        return this.Âµá€;
    }
    
    public void HorizonCode_Horizon_È(final AxisAlignedBB boundingBox) {
        this.Âµá€ = boundingBox;
    }
}
