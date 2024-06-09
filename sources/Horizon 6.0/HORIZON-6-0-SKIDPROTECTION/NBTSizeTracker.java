package HORIZON-6-0-SKIDPROTECTION;

public class NBTSizeTracker
{
    public static final NBTSizeTracker HorizonCode_Horizon_È;
    private final long Â;
    private long Ý;
    private static final String Ø­áŒŠá = "CL_00001903";
    
    static {
        HorizonCode_Horizon_È = new NBTSizeTracker() {
            private static final String Â = "CL_00001902";
            
            @Override
            public void HorizonCode_Horizon_È(final long bits) {
            }
        };
    }
    
    public NBTSizeTracker(final long max) {
        this.Â = max;
    }
    
    public void HorizonCode_Horizon_È(final long bits) {
        this.Ý += bits / 8L;
        if (this.Ý > this.Â) {
            throw new RuntimeException("Tried to read NBT tag that was too big; tried to allocate: " + this.Ý + "bytes where max allowed: " + this.Â);
        }
    }
}
