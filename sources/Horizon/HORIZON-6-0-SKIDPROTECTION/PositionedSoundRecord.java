package HORIZON-6-0-SKIDPROTECTION;

public class PositionedSoundRecord extends PositionedSound
{
    private static final String HorizonCode_Horizon_È = "CL_00001120";
    
    public static PositionedSoundRecord HorizonCode_Horizon_È(final ResourceLocation_1975012498 soundResource, final float pitch) {
        return new PositionedSoundRecord(soundResource, 0.25f, pitch, false, 0, ISound.HorizonCode_Horizon_È.HorizonCode_Horizon_È, 0.0f, 0.0f, 0.0f);
    }
    
    public static PositionedSoundRecord HorizonCode_Horizon_È(final ResourceLocation_1975012498 soundResource) {
        return new PositionedSoundRecord(soundResource, 1.0f, 1.0f, false, 0, ISound.HorizonCode_Horizon_È.HorizonCode_Horizon_È, 0.0f, 0.0f, 0.0f);
    }
    
    public static PositionedSoundRecord HorizonCode_Horizon_È(final ResourceLocation_1975012498 soundResource, final float xPosition, final float yPosition, final float zPosition) {
        return new PositionedSoundRecord(soundResource, 4.0f, 1.0f, false, 0, ISound.HorizonCode_Horizon_È.Â, xPosition, yPosition, zPosition);
    }
    
    public PositionedSoundRecord(final ResourceLocation_1975012498 soundResource, final float volume, final float pitch, final float xPosition, final float yPosition, final float zPosition) {
        this(soundResource, volume, pitch, false, 0, ISound.HorizonCode_Horizon_È.Â, xPosition, yPosition, zPosition);
    }
    
    private PositionedSoundRecord(final ResourceLocation_1975012498 soundResource, final float volume, final float pitch, final boolean repeat, final int repeatDelay, final ISound.HorizonCode_Horizon_È attenuationType, final float xPosition, final float yPosition, final float zPosition) {
        super(soundResource);
        this.Ý = volume;
        this.Ø­áŒŠá = pitch;
        this.Âµá€ = xPosition;
        this.Ó = yPosition;
        this.à = zPosition;
        this.Ø = repeat;
        this.áŒŠÆ = repeatDelay;
        this.áˆºÑ¢Õ = attenuationType;
    }
}
