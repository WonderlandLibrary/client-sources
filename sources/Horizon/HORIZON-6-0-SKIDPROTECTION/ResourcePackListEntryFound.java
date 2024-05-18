package HORIZON-6-0-SKIDPROTECTION;

public class ResourcePackListEntryFound extends ResourcePackListEntry
{
    private final ResourcePackRepository.HorizonCode_Horizon_È Ý;
    private static final String Ø­áŒŠá = "CL_00000823";
    
    public ResourcePackListEntryFound(final GuiScreenResourcePacks p_i45053_1_, final ResourcePackRepository.HorizonCode_Horizon_È p_i45053_2_) {
        super(p_i45053_1_);
        this.Ý = p_i45053_2_;
    }
    
    @Override
    protected void Ý() {
        this.Ý.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È.¥à());
    }
    
    @Override
    protected String HorizonCode_Horizon_È() {
        return this.Ý.Âµá€();
    }
    
    @Override
    protected String Â() {
        return this.Ý.Ø­áŒŠá();
    }
    
    public ResourcePackRepository.HorizonCode_Horizon_È áŒŠÆ() {
        return this.Ý;
    }
}
