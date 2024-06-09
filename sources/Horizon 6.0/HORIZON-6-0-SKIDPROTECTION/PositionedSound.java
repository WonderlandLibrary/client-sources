package HORIZON-6-0-SKIDPROTECTION;

public abstract class PositionedSound implements ISound
{
    protected final ResourceLocation_1975012498 Â;
    protected float Ý;
    protected float Ø­áŒŠá;
    protected float Âµá€;
    protected float Ó;
    protected float à;
    protected boolean Ø;
    protected int áŒŠÆ;
    protected HorizonCode_Horizon_È áˆºÑ¢Õ;
    private static final String HorizonCode_Horizon_È = "CL_00001116";
    
    protected PositionedSound(final ResourceLocation_1975012498 soundResource) {
        this.Ý = 1.0f;
        this.Ø­áŒŠá = 1.0f;
        this.Ø = false;
        this.áŒŠÆ = 0;
        this.áˆºÑ¢Õ = ISound.HorizonCode_Horizon_È.Â;
        this.Â = soundResource;
    }
    
    @Override
    public ResourceLocation_1975012498 Â() {
        return this.Â;
    }
    
    @Override
    public boolean Ý() {
        return this.Ø;
    }
    
    @Override
    public int Ø­áŒŠá() {
        return this.áŒŠÆ;
    }
    
    @Override
    public float Âµá€() {
        return this.Ý;
    }
    
    @Override
    public float Ó() {
        return this.Ø­áŒŠá;
    }
    
    @Override
    public float à() {
        return this.Âµá€;
    }
    
    @Override
    public float Ø() {
        return this.Ó;
    }
    
    @Override
    public float áŒŠÆ() {
        return this.à;
    }
    
    @Override
    public HorizonCode_Horizon_È áˆºÑ¢Õ() {
        return this.áˆºÑ¢Õ;
    }
}
