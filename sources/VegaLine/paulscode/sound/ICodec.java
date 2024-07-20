/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package paulscode.sound;

import java.net.URL;
import javax.sound.sampled.AudioFormat;
import paulscode.sound.SoundBuffer;

public interface ICodec {
    public void reverseByteOrder(boolean var1);

    public boolean initialize(URL var1);

    public boolean initialized();

    public SoundBuffer read();

    public SoundBuffer readAll();

    public boolean endOfStream();

    public void cleanup();

    public AudioFormat getAudioFormat();
}

