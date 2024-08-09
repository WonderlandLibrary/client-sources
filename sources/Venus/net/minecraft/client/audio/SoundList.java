/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.audio;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.audio.Sound;

public class SoundList {
    private final List<Sound> sounds;
    private final boolean replaceExisting;
    private final String subtitle;

    public SoundList(List<Sound> list, boolean bl, String string) {
        this.sounds = list;
        this.replaceExisting = bl;
        this.subtitle = string;
    }

    public List<Sound> getSounds() {
        return this.sounds;
    }

    public boolean canReplaceExisting() {
        return this.replaceExisting;
    }

    @Nullable
    public String getSubtitle() {
        return this.subtitle;
    }
}

