package net.minecraft.src;

final class StepSoundSand extends StepSound
{
    StepSoundSand(final String par1Str, final float par2, final float par3) {
        super(par1Str, par2, par3);
    }
    
    @Override
    public String getBreakSound() {
        return "dig.wood";
    }
}
