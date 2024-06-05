package net.minecraft.client.audio;

import net.minecraft.util.*;
import com.google.common.collect.*;
import java.util.*;

public class SoundEventAccessorComposite implements ISoundEventAccessor
{
    private final List soundPool;
    private final Random rnd;
    private final ResourceLocation soundLocation;
    private final SoundCategory category;
    private double eventPitch;
    private double eventVolume;
    
    public SoundEventAccessorComposite(final ResourceLocation soundLocation, final double pitch, final double volume, final SoundCategory category) {
        this.soundPool = Lists.newArrayList();
        this.rnd = new Random();
        this.soundLocation = soundLocation;
        this.eventVolume = volume;
        this.eventPitch = pitch;
        this.category = category;
    }
    
    @Override
    public int getWeight() {
        int var1 = 0;
        for (final ISoundEventAccessor var3 : this.soundPool) {
            var1 += var3.getWeight();
        }
        return var1;
    }
    
    public SoundPoolEntry cloneEntry1() {
        final int var1 = this.getWeight();
        if (!this.soundPool.isEmpty() && var1 != 0) {
            int var2 = this.rnd.nextInt(var1);
            for (final ISoundEventAccessor var4 : this.soundPool) {
                var2 -= var4.getWeight();
                if (var2 < 0) {
                    final SoundPoolEntry var5 = (SoundPoolEntry)var4.cloneEntry();
                    var5.setPitch(var5.getPitch() * this.eventPitch);
                    var5.setVolume(var5.getVolume() * this.eventVolume);
                    return var5;
                }
            }
            return SoundHandler.missing_sound;
        }
        return SoundHandler.missing_sound;
    }
    
    public void addSoundToEventPool(final ISoundEventAccessor p_148727_1_) {
        this.soundPool.add(p_148727_1_);
    }
    
    public ResourceLocation getSoundEventLocation() {
        return this.soundLocation;
    }
    
    public SoundCategory getSoundCategory() {
        return this.category;
    }
    
    @Override
    public Object cloneEntry() {
        return this.cloneEntry1();
    }
}
