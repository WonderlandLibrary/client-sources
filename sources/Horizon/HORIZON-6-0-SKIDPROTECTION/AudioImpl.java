package HORIZON-6-0-SKIDPROTECTION;

import org.lwjgl.openal.AL10;

public class AudioImpl implements Audio
{
    private SoundStore HorizonCode_Horizon_È;
    private int Â;
    private int Ý;
    private float Ø­áŒŠá;
    
    AudioImpl(final SoundStore store, final int buffer) {
        this.Ý = -1;
        this.HorizonCode_Horizon_È = store;
        this.Â = buffer;
        final int bytes = AL10.alGetBufferi(buffer, 8196);
        final int bits = AL10.alGetBufferi(buffer, 8194);
        final int channels = AL10.alGetBufferi(buffer, 8195);
        final int freq = AL10.alGetBufferi(buffer, 8193);
        final int samples = bytes / (bits / 8);
        this.Ø­áŒŠá = samples / freq / channels;
    }
    
    @Override
    public int Ø­áŒŠá() {
        return this.Â;
    }
    
    protected AudioImpl() {
        this.Ý = -1;
    }
    
    @Override
    public void Ý() {
        if (this.Ý != -1) {
            this.HorizonCode_Horizon_È.Ý(this.Ý);
            this.Ý = -1;
        }
    }
    
    @Override
    public boolean Âµá€() {
        return this.Ý != -1 && this.HorizonCode_Horizon_È.Ø­áŒŠá(this.Ý);
    }
    
    @Override
    public int HorizonCode_Horizon_È(final float pitch, final float gain, final boolean loop) {
        this.Ý = this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.Â, pitch, gain, loop);
        return this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.Ý);
    }
    
    @Override
    public int HorizonCode_Horizon_È(final float pitch, final float gain, final boolean loop, final float x, final float y, final float z) {
        this.Ý = this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.Â, pitch, gain, loop, x, y, z);
        return this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.Ý);
    }
    
    @Override
    public int Â(final float pitch, final float gain, final boolean loop) {
        this.HorizonCode_Horizon_È.Â(this.Â, pitch, gain, loop);
        this.Ý = 0;
        return this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(0);
    }
    
    public static void à() {
        SoundStore.Å().á();
    }
    
    public static void Ø() {
        SoundStore.Å().ˆÏ­();
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(float position) {
        position %= this.Ø­áŒŠá;
        AL10.alSourcef(this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.Ý), 4132, position);
        return AL10.alGetError() == 0;
    }
    
    @Override
    public float Ó() {
        return AL10.alGetSourcef(this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.Ý), 4132);
    }
}
