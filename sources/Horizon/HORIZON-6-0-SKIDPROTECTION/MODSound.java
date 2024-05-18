package HORIZON-6-0-SKIDPROTECTION;

import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;
import java.io.IOException;
import java.io.InputStream;

public class MODSound extends AudioImpl
{
    private SoundStore HorizonCode_Horizon_È;
    
    public MODSound(final SoundStore store, final InputStream in) throws IOException {
        this.HorizonCode_Horizon_È = store;
    }
    
    @Override
    public int Â(final float pitch, final float gain, final boolean loop) {
        this.Â();
        this.HorizonCode_Horizon_È.Â(gain);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this);
        return this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(0);
    }
    
    private void Â() {
        AL10.alSourceStop(this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(0));
        final IntBuffer buffer = BufferUtils.createIntBuffer(1);
        for (int queued = AL10.alGetSourcei(this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(0), 4117); queued > 0; --queued) {
            AL10.alSourceUnqueueBuffers(this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(0), buffer);
        }
        AL10.alSourcei(this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(0), 4105, 0);
    }
    
    public void HorizonCode_Horizon_È() {
    }
    
    @Override
    public int HorizonCode_Horizon_È(final float pitch, final float gain, final boolean loop) {
        return -1;
    }
    
    @Override
    public void Ý() {
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È((MODSound)null);
    }
    
    @Override
    public float Ó() {
        throw new RuntimeException("Positioning on modules is not currently supported");
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final float position) {
        throw new RuntimeException("Positioning on modules is not currently supported");
    }
}
