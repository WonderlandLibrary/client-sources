package HORIZON-6-0-SKIDPROTECTION;

public class PositionImpl implements IPosition
{
    protected final double HorizonCode_Horizon_È;
    protected final double Â;
    protected final double Ý;
    private static final String Ø­áŒŠá = "CL_00001208";
    
    public PositionImpl(final double xCoord, final double yCoord, final double zCoord) {
        this.HorizonCode_Horizon_È = xCoord;
        this.Â = yCoord;
        this.Ý = zCoord;
    }
    
    @Override
    public double Â() {
        return this.HorizonCode_Horizon_È;
    }
    
    @Override
    public double Ý() {
        return this.Â;
    }
    
    @Override
    public double Ø­áŒŠá() {
        return this.Ý;
    }
}
