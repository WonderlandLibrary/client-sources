/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.audio;

import java.nio.ByteBuffer;
import java.util.OptionalInt;
import javax.annotation.Nullable;
import javax.sound.sampled.AudioFormat;
import net.minecraft.client.audio.ALUtils;
import org.lwjgl.openal.AL10;

public class AudioStreamBuffer {
    @Nullable
    private ByteBuffer inputBuffer;
    private final AudioFormat audioFormat;
    private boolean hasBuffer;
    private int buffer;

    public AudioStreamBuffer(ByteBuffer byteBuffer, AudioFormat audioFormat) {
        this.inputBuffer = byteBuffer;
        this.audioFormat = audioFormat;
    }

    OptionalInt getBuffer() {
        if (!this.hasBuffer) {
            if (this.inputBuffer == null) {
                return OptionalInt.empty();
            }
            int n = ALUtils.getFormat(this.audioFormat);
            int[] nArray = new int[1];
            AL10.alGenBuffers(nArray);
            if (ALUtils.checkALError("Creating buffer")) {
                return OptionalInt.empty();
            }
            AL10.alBufferData(nArray[0], n, this.inputBuffer, (int)this.audioFormat.getSampleRate());
            if (ALUtils.checkALError("Assigning buffer data")) {
                return OptionalInt.empty();
            }
            this.buffer = nArray[0];
            this.hasBuffer = true;
            this.inputBuffer = null;
        }
        return OptionalInt.of(this.buffer);
    }

    public void deleteBuffer() {
        if (this.hasBuffer) {
            AL10.alDeleteBuffers(new int[]{this.buffer});
            if (ALUtils.checkALError("Deleting stream buffers")) {
                return;
            }
        }
        this.hasBuffer = false;
    }

    public OptionalInt getUntrackedBuffer() {
        OptionalInt optionalInt = this.getBuffer();
        this.hasBuffer = false;
        return optionalInt;
    }
}

