package HORIZON-6-0-SKIDPROTECTION;

import java.io.File;

public class AnvilSaveHandler extends SaveHandler
{
    private static final String HorizonCode_Horizon_È = "CL_00000581";
    
    public AnvilSaveHandler(final File p_i2142_1_, final String p_i2142_2_, final boolean p_i2142_3_) {
        super(p_i2142_1_, p_i2142_2_, p_i2142_3_);
    }
    
    @Override
    public IChunkLoader HorizonCode_Horizon_È(final WorldProvider p_75763_1_) {
        final File var2 = this.Ó();
        if (p_75763_1_ instanceof WorldProviderHell) {
            final File var3 = new File(var2, "DIM-1");
            var3.mkdirs();
            return new AnvilChunkLoader(var3);
        }
        if (p_75763_1_ instanceof WorldProviderEnd) {
            final File var3 = new File(var2, "DIM1");
            var3.mkdirs();
            return new AnvilChunkLoader(var3);
        }
        return new AnvilChunkLoader(var2);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final WorldInfo p_75755_1_, final NBTTagCompound p_75755_2_) {
        p_75755_1_.Ø­áŒŠá(19133);
        super.HorizonCode_Horizon_È(p_75755_1_, p_75755_2_);
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        try {
            ThreadedFileIOBase.HorizonCode_Horizon_È().Â();
        }
        catch (InterruptedException var2) {
            var2.printStackTrace();
        }
        RegionFileCache.HorizonCode_Horizon_È();
    }
}
