package HORIZON-6-0-SKIDPROTECTION;

public class SoundEventAccessor implements ISoundEventAccessor
{
    private final SoundPoolEntry HorizonCode_Horizon_È;
    private final int Â;
    private static final String Ý = "CL_00001153";
    
    SoundEventAccessor(final SoundPoolEntry entry, final int weight) {
        this.HorizonCode_Horizon_È = entry;
        this.Â = weight;
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return this.Â;
    }
    
    public SoundPoolEntry Ý() {
        return new SoundPoolEntry(this.HorizonCode_Horizon_È);
    }
}
