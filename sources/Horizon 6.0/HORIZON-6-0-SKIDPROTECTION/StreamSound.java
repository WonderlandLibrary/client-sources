package HORIZON-6-0-SKIDPROTECTION;

import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;
import java.io.IOException;

public class StreamSound extends AudioImpl
{
    private OpenALStreamPlayer HorizonCode_Horizon_È;
    
    public StreamSound(final OpenALStreamPlayer player) {
        this.HorizonCode_Horizon_È = player;
    }
    
    @Override
    public boolean Âµá€() {
        return SoundStore.Å().HorizonCode_Horizon_È(this.HorizonCode_Horizon_È);
    }
    
    @Override
    public int Â(final float pitch, final float gain, final boolean loop) {
        try {
            this.HorizonCode_Horizon_È();
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(pitch);
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(loop);
            SoundStore.Å().Â(this.HorizonCode_Horizon_È);
        }
        catch (IOException e) {
            Log.HorizonCode_Horizon_È("Failed to read OGG source: " + this.HorizonCode_Horizon_È.HorizonCode_Horizon_È());
        }
        return SoundStore.Å().HorizonCode_Horizon_È(0);
    }
    
    private void HorizonCode_Horizon_È() {
        final SoundStore store = SoundStore.Å();
        AL10.alSourceStop(store.HorizonCode_Horizon_È(0));
        final IntBuffer buffer = BufferUtils.createIntBuffer(1);
        for (int queued = AL10.alGetSourcei(store.HorizonCode_Horizon_È(0), 4117); queued > 0; --queued) {
            AL10.alSourceUnqueueBuffers(store.HorizonCode_Horizon_È(0), buffer);
        }
        AL10.alSourcei(store.HorizonCode_Horizon_È(0), 4105, 0);
    }
    
    @Override
    public int HorizonCode_Horizon_È(final float pitch, final float gain, final boolean loop, final float x, final float y, final float z) {
        return this.Â(pitch, gain, loop);
    }
    
    @Override
    public int HorizonCode_Horizon_È(final float pitch, final float gain, final boolean loop) {
        return this.Â(pitch, gain, loop);
    }
    
    @Override
    public void Ý() {
        SoundStore.Å().Â((OpenALStreamPlayer)null);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final float position) {
        return this.HorizonCode_Horizon_È.Â(position);
    }
    
    @Override
    public float Ó() {
        return this.HorizonCode_Horizon_È.Ø­áŒŠá();
    }
}
