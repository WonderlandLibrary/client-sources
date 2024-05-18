package paulscode.sound;

import javax.sound.sampled.*;

public class SoundBuffer
{
    public byte[] audioData;
    public AudioFormat audioFormat;
    
    public SoundBuffer(final byte[] audioData, final AudioFormat audioFormat) {
        this.audioData = audioData;
        this.audioFormat = audioFormat;
    }
    
    public void cleanup() {
        this.audioData = null;
        this.audioFormat = null;
    }
    
    public void trimData(final int n) {
        if (this.audioData == null || n == 0) {
            this.audioData = null;
        }
        else if (this.audioData.length > n) {
            final byte[] audioData = new byte[n];
            System.arraycopy(this.audioData, 0, audioData, 0, n);
            this.audioData = audioData;
        }
    }
}
