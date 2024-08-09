/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.audio;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.client.audio.ISoundEventAccessor;
import net.minecraft.client.audio.Sound;
import net.minecraft.client.audio.SoundEngine;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class SoundEventAccessor
implements ISoundEventAccessor<Sound> {
    private final List<ISoundEventAccessor<Sound>> accessorList = Lists.newArrayList();
    private final Random rnd = new Random();
    private final ResourceLocation location;
    @Nullable
    private final ITextComponent subtitle;

    public SoundEventAccessor(ResourceLocation resourceLocation, @Nullable String string) {
        this.location = resourceLocation;
        this.subtitle = string == null ? null : new TranslationTextComponent(string);
    }

    @Override
    public int getWeight() {
        int n = 0;
        for (ISoundEventAccessor<Sound> iSoundEventAccessor : this.accessorList) {
            n += iSoundEventAccessor.getWeight();
        }
        return n;
    }

    @Override
    public Sound cloneEntry() {
        int n = this.getWeight();
        if (!this.accessorList.isEmpty() && n != 0) {
            int n2 = this.rnd.nextInt(n);
            for (ISoundEventAccessor<Sound> iSoundEventAccessor : this.accessorList) {
                if ((n2 -= iSoundEventAccessor.getWeight()) >= 0) continue;
                return iSoundEventAccessor.cloneEntry();
            }
            return SoundHandler.MISSING_SOUND;
        }
        return SoundHandler.MISSING_SOUND;
    }

    public void addSound(ISoundEventAccessor<Sound> iSoundEventAccessor) {
        this.accessorList.add(iSoundEventAccessor);
    }

    @Nullable
    public ITextComponent getSubtitle() {
        return this.subtitle;
    }

    @Override
    public void enqueuePreload(SoundEngine soundEngine) {
        for (ISoundEventAccessor<Sound> iSoundEventAccessor : this.accessorList) {
            iSoundEventAccessor.enqueuePreload(soundEngine);
        }
    }

    @Override
    public Object cloneEntry() {
        return this.cloneEntry();
    }
}

