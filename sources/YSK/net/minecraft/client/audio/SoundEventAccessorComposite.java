package net.minecraft.client.audio;

import net.minecraft.util.*;
import com.google.common.collect.*;
import java.util.*;

public class SoundEventAccessorComposite implements ISoundEventAccessor<SoundPoolEntry>
{
    private final Random rnd;
    private final ResourceLocation soundLocation;
    private final SoundCategory category;
    private final List<ISoundEventAccessor<SoundPoolEntry>> soundPool;
    private double eventPitch;
    private double eventVolume;
    
    public void addSoundToEventPool(final ISoundEventAccessor<SoundPoolEntry> soundEventAccessor) {
        this.soundPool.add(soundEventAccessor);
    }
    
    @Override
    public Object cloneEntry() {
        return this.cloneEntry();
    }
    
    public ResourceLocation getSoundEventLocation() {
        return this.soundLocation;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (3 < 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public SoundEventAccessorComposite(final ResourceLocation soundLocation, final double eventPitch, final double eventVolume, final SoundCategory category) {
        this.soundPool = (List<ISoundEventAccessor<SoundPoolEntry>>)Lists.newArrayList();
        this.rnd = new Random();
        this.soundLocation = soundLocation;
        this.eventVolume = eventVolume;
        this.eventPitch = eventPitch;
        this.category = category;
    }
    
    @Override
    public SoundPoolEntry cloneEntry() {
        final int weight = this.getWeight();
        if (this.soundPool.isEmpty() || weight == 0) {
            return SoundHandler.missing_sound;
        }
        int nextInt = this.rnd.nextInt(weight);
        final Iterator<ISoundEventAccessor<SoundPoolEntry>> iterator = this.soundPool.iterator();
        "".length();
        if (3 != 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            final ISoundEventAccessor<SoundPoolEntry> soundEventAccessor = iterator.next();
            nextInt -= soundEventAccessor.getWeight();
            if (nextInt < 0) {
                final SoundPoolEntry soundPoolEntry = soundEventAccessor.cloneEntry();
                soundPoolEntry.setPitch(soundPoolEntry.getPitch() * this.eventPitch);
                soundPoolEntry.setVolume(soundPoolEntry.getVolume() * this.eventVolume);
                return soundPoolEntry;
            }
        }
        return SoundHandler.missing_sound;
    }
    
    @Override
    public int getWeight() {
        int length = "".length();
        final Iterator<ISoundEventAccessor<SoundPoolEntry>> iterator = this.soundPool.iterator();
        "".length();
        if (3 != 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            length += iterator.next().getWeight();
        }
        return length;
    }
    
    public SoundCategory getSoundCategory() {
        return this.category;
    }
}
