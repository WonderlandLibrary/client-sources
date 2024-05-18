package paulscode.sound;

import java.net.*;
import javax.sound.sampled.*;

public interface ICodec
{
    void reverseByteOrder(final boolean p0);
    
    boolean initialize(final URL p0);
    
    boolean initialized();
    
    SoundBuffer read();
    
    SoundBuffer readAll();
    
    boolean endOfStream();
    
    void cleanup();
    
    AudioFormat getAudioFormat();
}
