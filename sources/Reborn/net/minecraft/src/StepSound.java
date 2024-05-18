package net.minecraft.src;

public class StepSound
{
    public final String stepSoundName;
    public final float stepSoundVolume;
    public final float stepSoundPitch;
    
    public StepSound(final String par1Str, final float par2, final float par3) {
        this.stepSoundName = par1Str;
        this.stepSoundVolume = par2;
        this.stepSoundPitch = par3;
    }
    
    public float getVolume() {
        return this.stepSoundVolume;
    }
    
    public float getPitch() {
        return this.stepSoundPitch;
    }
    
    public String getBreakSound() {
        return "dig." + this.stepSoundName;
    }
    
    public String getStepSound() {
        return "step." + this.stepSoundName;
    }
    
    public String getPlaceSound() {
        return this.getBreakSound();
    }
}
