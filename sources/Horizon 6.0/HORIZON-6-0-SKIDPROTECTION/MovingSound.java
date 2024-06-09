package HORIZON-6-0-SKIDPROTECTION;

public abstract class MovingSound extends PositionedSound implements ITickableSound
{
    protected boolean HorizonCode_Horizon_È;
    private static final String ÂµÈ = "CL_00001117";
    
    protected MovingSound(final ResourceLocation_1975012498 location) {
        super(location);
        this.HorizonCode_Horizon_È = false;
    }
    
    @Override
    public boolean ÂµÈ() {
        return this.HorizonCode_Horizon_È;
    }
}
