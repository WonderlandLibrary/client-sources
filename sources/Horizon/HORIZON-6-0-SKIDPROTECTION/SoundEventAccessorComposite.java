package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import com.google.common.collect.Lists;
import java.util.Random;
import java.util.List;

public class SoundEventAccessorComposite implements ISoundEventAccessor
{
    private final List HorizonCode_Horizon_È;
    private final Random Â;
    private final ResourceLocation_1975012498 Ý;
    private final SoundCategory Ø­áŒŠá;
    private double Âµá€;
    private double Ó;
    private static final String à = "CL_00001146";
    
    public SoundEventAccessorComposite(final ResourceLocation_1975012498 soundLocation, final double pitch, final double volume, final SoundCategory category) {
        this.HorizonCode_Horizon_È = Lists.newArrayList();
        this.Â = new Random();
        this.Ý = soundLocation;
        this.Ó = volume;
        this.Âµá€ = pitch;
        this.Ø­áŒŠá = category;
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        int var1 = 0;
        for (final ISoundEventAccessor var3 : this.HorizonCode_Horizon_È) {
            var1 += var3.HorizonCode_Horizon_È();
        }
        return var1;
    }
    
    public SoundPoolEntry Ý() {
        final int var1 = this.HorizonCode_Horizon_È();
        if (!this.HorizonCode_Horizon_È.isEmpty() && var1 != 0) {
            int var2 = this.Â.nextInt(var1);
            for (final ISoundEventAccessor var4 : this.HorizonCode_Horizon_È) {
                var2 -= var4.HorizonCode_Horizon_È();
                if (var2 < 0) {
                    final SoundPoolEntry var5 = (SoundPoolEntry)var4.Â();
                    var5.HorizonCode_Horizon_È(var5.Â() * this.Âµá€);
                    var5.Â(var5.Ý() * this.Ó);
                    return var5;
                }
            }
            return SoundHandler.HorizonCode_Horizon_È;
        }
        return SoundHandler.HorizonCode_Horizon_È;
    }
    
    public void HorizonCode_Horizon_È(final ISoundEventAccessor p_148727_1_) {
        this.HorizonCode_Horizon_È.add(p_148727_1_);
    }
    
    public ResourceLocation_1975012498 Ø­áŒŠá() {
        return this.Ý;
    }
    
    public SoundCategory Âµá€() {
        return this.Ø­áŒŠá;
    }
}
