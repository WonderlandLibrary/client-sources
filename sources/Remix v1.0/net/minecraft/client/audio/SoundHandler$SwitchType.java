package net.minecraft.client.audio;

static final class SwitchType
{
    static final int[] field_148765_a;
    
    static {
        field_148765_a = new int[SoundList.SoundEntry.Type.values().length];
        try {
            SwitchType.field_148765_a[SoundList.SoundEntry.Type.FILE.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            SwitchType.field_148765_a[SoundList.SoundEntry.Type.SOUND_EVENT.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
    }
}
