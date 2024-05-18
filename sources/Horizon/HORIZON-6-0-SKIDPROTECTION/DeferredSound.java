package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;
import java.io.InputStream;

public class DeferredSound extends AudioImpl implements DeferredResource
{
    public static final int HorizonCode_Horizon_È = 1;
    public static final int Â = 2;
    public static final int Ý = 3;
    public static final int Ø­áŒŠá = 4;
    private int Âµá€;
    private String Ó;
    private Audio à;
    private InputStream Ø;
    
    public DeferredSound(final String ref, final InputStream in, final int type) {
        this.Ó = ref;
        this.Âµá€ = type;
        if (ref.equals(in.toString())) {
            this.Ø = in;
        }
        LoadingList.HorizonCode_Horizon_È().HorizonCode_Horizon_È(this);
    }
    
    private void áŒŠÆ() {
        if (this.à == null) {
            throw new RuntimeException("Attempt to use deferred sound before loading");
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È() throws IOException {
        final boolean before = SoundStore.Å().Ý();
        SoundStore.Å().HorizonCode_Horizon_È(false);
        if (this.Ø != null) {
            switch (this.Âµá€) {
                case 1: {
                    this.à = SoundStore.Å().Ø­áŒŠá(this.Ø);
                    break;
                }
                case 2: {
                    this.à = SoundStore.Å().Ý(this.Ø);
                    break;
                }
                case 3: {
                    this.à = SoundStore.Å().HorizonCode_Horizon_È(this.Ø);
                    break;
                }
                case 4: {
                    this.à = SoundStore.Å().Â(this.Ø);
                    break;
                }
                default: {
                    Log.HorizonCode_Horizon_È("Unrecognised sound type: " + this.Âµá€);
                    break;
                }
            }
        }
        else {
            switch (this.Âµá€) {
                case 1: {
                    this.à = SoundStore.Å().Âµá€(this.Ó);
                    break;
                }
                case 2: {
                    this.à = SoundStore.Å().Ý(this.Ó);
                    break;
                }
                case 3: {
                    this.à = SoundStore.Å().HorizonCode_Horizon_È(this.Ó);
                    break;
                }
                case 4: {
                    this.à = SoundStore.Å().Â(this.Ó);
                    break;
                }
                default: {
                    Log.HorizonCode_Horizon_È("Unrecognised sound type: " + this.Âµá€);
                    break;
                }
            }
        }
        SoundStore.Å().HorizonCode_Horizon_È(before);
    }
    
    @Override
    public boolean Âµá€() {
        this.áŒŠÆ();
        return this.à.Âµá€();
    }
    
    @Override
    public int Â(final float pitch, final float gain, final boolean loop) {
        this.áŒŠÆ();
        return this.à.Â(pitch, gain, loop);
    }
    
    @Override
    public int HorizonCode_Horizon_È(final float pitch, final float gain, final boolean loop) {
        this.áŒŠÆ();
        return this.à.HorizonCode_Horizon_È(pitch, gain, loop);
    }
    
    @Override
    public int HorizonCode_Horizon_È(final float pitch, final float gain, final boolean loop, final float x, final float y, final float z) {
        this.áŒŠÆ();
        return this.à.HorizonCode_Horizon_È(pitch, gain, loop, x, y, z);
    }
    
    @Override
    public void Ý() {
        this.áŒŠÆ();
        this.à.Ý();
    }
    
    @Override
    public String Â() {
        return this.Ó;
    }
}
