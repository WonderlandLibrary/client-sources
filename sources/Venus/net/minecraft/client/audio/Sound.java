/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.audio;

import net.minecraft.client.audio.ISoundEventAccessor;
import net.minecraft.client.audio.SoundEngine;
import net.minecraft.util.ResourceLocation;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Sound
implements ISoundEventAccessor<Sound> {
    private final ResourceLocation name;
    private final float volume;
    private final float pitch;
    private final int weight;
    private final Type type;
    private final boolean streaming;
    private final boolean preload;
    private final int attenuationDistance;

    public Sound(String string, float f, float f2, int n, Type type, boolean bl, boolean bl2, int n2) {
        this.name = new ResourceLocation(string);
        this.volume = f;
        this.pitch = f2;
        this.weight = n;
        this.type = type;
        this.streaming = bl;
        this.preload = bl2;
        this.attenuationDistance = n2;
    }

    public ResourceLocation getSoundLocation() {
        return this.name;
    }

    public ResourceLocation getSoundAsOggLocation() {
        return new ResourceLocation(this.name.getNamespace(), "sounds/" + this.name.getPath() + ".ogg");
    }

    public float getVolume() {
        return this.volume;
    }

    public float getPitch() {
        return this.pitch;
    }

    @Override
    public int getWeight() {
        return this.weight;
    }

    @Override
    public Sound cloneEntry() {
        return this;
    }

    @Override
    public void enqueuePreload(SoundEngine soundEngine) {
        if (this.preload) {
            soundEngine.enqueuePreload(this);
        }
    }

    public Type getType() {
        return this.type;
    }

    public boolean isStreaming() {
        return this.streaming;
    }

    public boolean shouldPreload() {
        return this.preload;
    }

    public int getAttenuationDistance() {
        return this.attenuationDistance;
    }

    public String toString() {
        return "Sound[" + this.name + "]";
    }

    @Override
    public Object cloneEntry() {
        return this.cloneEntry();
    }

    public static enum Type {
        FILE("file"),
        SOUND_EVENT("event");

        private final String name;

        private Type(String string2) {
            this.name = string2;
        }

        public static Type getByName(String string) {
            for (Type type : Type.values()) {
                if (!type.name.equals(string)) continue;
                return type;
            }
            return null;
        }
    }
}

