package HORIZON-6-0-SKIDPROTECTION;

import org.lwjgl.openal.OpenALException;
import java.io.IOException;
import org.lwjgl.openal.AL10;
import org.lwjgl.BufferUtils;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class OpenALStreamPlayer
{
    public static final int HorizonCode_Horizon_È = 3;
    private static final int Â = 81920;
    private byte[] Ý;
    private IntBuffer Ø­áŒŠá;
    private ByteBuffer Âµá€;
    private IntBuffer Ó;
    private int à;
    private int Ø;
    private boolean áŒŠÆ;
    private boolean áˆºÑ¢Õ;
    private AudioInputStream ÂµÈ;
    private String á;
    private URL ˆÏ­;
    private float £á;
    private float Å;
    
    public OpenALStreamPlayer(final int source, final String ref) {
        this.Ý = new byte[81920];
        this.Âµá€ = BufferUtils.createByteBuffer(81920);
        this.Ó = BufferUtils.createIntBuffer(1);
        this.áˆºÑ¢Õ = true;
        this.à = source;
        this.á = ref;
        AL10.alGenBuffers(this.Ø­áŒŠá = BufferUtils.createIntBuffer(3));
    }
    
    public OpenALStreamPlayer(final int source, final URL url) {
        this.Ý = new byte[81920];
        this.Âµá€ = BufferUtils.createByteBuffer(81920);
        this.Ó = BufferUtils.createIntBuffer(1);
        this.áˆºÑ¢Õ = true;
        this.à = source;
        this.ˆÏ­ = url;
        AL10.alGenBuffers(this.Ø­áŒŠá = BufferUtils.createIntBuffer(3));
    }
    
    private void Âµá€() throws IOException {
        if (this.ÂµÈ != null) {
            this.ÂµÈ.close();
        }
        OggInputStream audio;
        if (this.ˆÏ­ != null) {
            audio = new OggInputStream(this.ˆÏ­.openStream());
        }
        else {
            audio = new OggInputStream(ResourceLoader.HorizonCode_Horizon_È(this.á));
        }
        this.ÂµÈ = audio;
        this.Å = 0.0f;
    }
    
    public String HorizonCode_Horizon_È() {
        return (this.ˆÏ­ == null) ? this.á : this.ˆÏ­.toString();
    }
    
    private void Ó() {
        final IntBuffer buffer = BufferUtils.createIntBuffer(1);
        for (int queued = AL10.alGetSourcei(this.à, 4117); queued > 0; --queued) {
            AL10.alSourceUnqueueBuffers(this.à, buffer);
        }
    }
    
    public void HorizonCode_Horizon_È(final boolean loop) throws IOException {
        this.áŒŠÆ = loop;
        this.Âµá€();
        this.áˆºÑ¢Õ = false;
        AL10.alSourceStop(this.à);
        this.Ó();
        this.à();
    }
    
    public void HorizonCode_Horizon_È(final float pitch) {
        this.£á = pitch;
    }
    
    public boolean Â() {
        return this.áˆºÑ¢Õ;
    }
    
    public void Ý() {
        if (this.áˆºÑ¢Õ) {
            return;
        }
        final float sampleRate = this.ÂµÈ.Â();
        float sampleSize;
        if (this.ÂµÈ.HorizonCode_Horizon_È() > 1) {
            sampleSize = 4.0f;
        }
        else {
            sampleSize = 2.0f;
        }
        for (int processed = AL10.alGetSourcei(this.à, 4118); processed > 0; --processed) {
            this.Ó.clear();
            AL10.alSourceUnqueueBuffers(this.à, this.Ó);
            final int bufferIndex = this.Ó.get(0);
            final float bufferLength = AL10.alGetBufferi(bufferIndex, 8196) / sampleSize / sampleRate;
            this.Å += bufferLength;
            if (this.HorizonCode_Horizon_È(bufferIndex)) {
                AL10.alSourceQueueBuffers(this.à, this.Ó);
            }
            else {
                --this.Ø;
                if (this.Ø == 0) {
                    this.áˆºÑ¢Õ = true;
                }
            }
        }
        final int state = AL10.alGetSourcei(this.à, 4112);
        if (state != 4114) {
            AL10.alSourcePlay(this.à);
        }
    }
    
    public boolean HorizonCode_Horizon_È(final int bufferId) {
        try {
            final int count = this.ÂµÈ.read(this.Ý);
            if (count != -1) {
                this.Âµá€.clear();
                this.Âµá€.put(this.Ý, 0, count);
                this.Âµá€.flip();
                final int format = (this.ÂµÈ.HorizonCode_Horizon_È() > 1) ? 4355 : 4353;
                try {
                    AL10.alBufferData(bufferId, format, this.Âµá€, this.ÂµÈ.Â());
                    return true;
                }
                catch (OpenALException e) {
                    Log.HorizonCode_Horizon_È("Failed to loop buffer: " + bufferId + " " + format + " " + count + " " + this.ÂµÈ.Â(), (Throwable)e);
                    return false;
                }
            }
            if (!this.áŒŠÆ) {
                this.áˆºÑ¢Õ = true;
                return false;
            }
            this.Âµá€();
            this.HorizonCode_Horizon_È(bufferId);
            return true;
        }
        catch (IOException e2) {
            Log.HorizonCode_Horizon_È(e2);
            return false;
        }
    }
    
    public boolean Â(final float position) {
        try {
            if (this.Ø­áŒŠá() > position) {
                this.Âµá€();
            }
            final float sampleRate = this.ÂµÈ.Â();
            float sampleSize;
            if (this.ÂµÈ.HorizonCode_Horizon_È() > 1) {
                sampleSize = 4.0f;
            }
            else {
                sampleSize = 2.0f;
            }
            while (this.Å < position) {
                final int count = this.ÂµÈ.read(this.Ý);
                if (count == -1) {
                    if (this.áŒŠÆ) {
                        this.Âµá€();
                    }
                    else {
                        this.áˆºÑ¢Õ = true;
                    }
                    return false;
                }
                final float bufferLength = count / sampleSize / sampleRate;
                this.Å += bufferLength;
            }
            this.à();
            return true;
        }
        catch (IOException e) {
            Log.HorizonCode_Horizon_È(e);
            return false;
        }
    }
    
    private void à() {
        AL10.alSourcei(this.à, 4103, 0);
        AL10.alSourcef(this.à, 4099, this.£á);
        this.Ø = 3;
        for (int i = 0; i < 3; ++i) {
            this.HorizonCode_Horizon_È(this.Ø­áŒŠá.get(i));
        }
        AL10.alSourceQueueBuffers(this.à, this.Ø­áŒŠá);
        AL10.alSourcePlay(this.à);
    }
    
    public float Ø­áŒŠá() {
        return this.Å + AL10.alGetSourcef(this.à, 4132);
    }
}
