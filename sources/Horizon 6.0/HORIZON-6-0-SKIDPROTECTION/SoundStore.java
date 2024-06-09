package HORIZON-6-0-SKIDPROTECTION;

import org.lwjgl.Sys;
import java.net.URL;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.IOException;
import org.lwjgl.openal.OpenALException;
import java.security.AccessController;
import org.lwjgl.openal.AL;
import java.security.PrivilegedAction;
import org.lwjgl.openal.AL10;
import org.lwjgl.BufferUtils;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;

public class SoundStore
{
    private static SoundStore HorizonCode_Horizon_È;
    private boolean Â;
    private boolean Ý;
    private boolean Ø­áŒŠá;
    private int Âµá€;
    private HashMap Ó;
    private int à;
    private IntBuffer Ø;
    private int áŒŠÆ;
    private boolean áˆºÑ¢Õ;
    private MODSound ÂµÈ;
    private OpenALStreamPlayer á;
    private float ˆÏ­;
    private float £á;
    private float Å;
    private boolean £à;
    private boolean µà;
    private FloatBuffer ˆà;
    private FloatBuffer ¥Æ;
    private int Ø­à;
    
    static {
        SoundStore.HorizonCode_Horizon_È = new SoundStore();
    }
    
    private SoundStore() {
        this.Ó = new HashMap();
        this.à = -1;
        this.áˆºÑ¢Õ = false;
        this.ˆÏ­ = 1.0f;
        this.£á = 1.0f;
        this.Å = 1.0f;
        this.ˆà = BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f });
        this.¥Æ = BufferUtils.createFloatBuffer(3);
        this.Ø­à = 64;
    }
    
    public void HorizonCode_Horizon_È() {
        SoundStore.HorizonCode_Horizon_È = new SoundStore();
    }
    
    public void Â() {
        this.áˆºÑ¢Õ = true;
    }
    
    public void HorizonCode_Horizon_È(final boolean deferred) {
        this.µà = deferred;
    }
    
    public boolean Ý() {
        return this.µà;
    }
    
    public void Â(final boolean music) {
        if (this.Ø­áŒŠá) {
            this.Ý = music;
            if (music) {
                this.ˆÏ­();
                this.HorizonCode_Horizon_È(this.ˆÏ­);
            }
            else {
                this.á();
            }
        }
    }
    
    public boolean Ø­áŒŠá() {
        return this.Ý;
    }
    
    public void HorizonCode_Horizon_È(float volume) {
        if (volume < 0.0f) {
            volume = 0.0f;
        }
        if (volume > 1.0f) {
            volume = 1.0f;
        }
        this.ˆÏ­ = volume;
        if (this.Ø­áŒŠá) {
            AL10.alSourcef(this.Ø.get(0), 4106, this.Å * this.ˆÏ­);
        }
    }
    
    public float Âµá€() {
        return this.Å;
    }
    
    public void Â(float volume) {
        if (volume < 0.0f) {
            volume = 0.0f;
        }
        if (volume > 1.0f) {
            volume = 1.0f;
        }
        if (this.Ø­áŒŠá) {
            this.Å = volume;
            AL10.alSourcef(this.Ø.get(0), 4106, this.Å * this.ˆÏ­);
        }
    }
    
    public void Ý(float volume) {
        if (volume < 0.0f) {
            volume = 0.0f;
        }
        this.£á = volume;
    }
    
    public boolean Ó() {
        return this.Ø­áŒŠá;
    }
    
    public boolean à() {
        return this.Ý;
    }
    
    public float Ø() {
        return this.£á;
    }
    
    public float áŒŠÆ() {
        return this.ˆÏ­;
    }
    
    public int HorizonCode_Horizon_È(final int index) {
        if (!this.Ø­áŒŠá) {
            return -1;
        }
        if (index < 0) {
            return -1;
        }
        return this.Ø.get(index);
    }
    
    public void Ý(final boolean sounds) {
        if (this.Ø­áŒŠá) {
            this.Â = sounds;
        }
    }
    
    public boolean áˆºÑ¢Õ() {
        return this.Â;
    }
    
    public void Â(final int max) {
        this.Ø­à = max;
    }
    
    public void ÂµÈ() {
        if (this.áˆºÑ¢Õ) {
            return;
        }
        Log.Ý("Initialising sounds..");
        this.áˆºÑ¢Õ = true;
        AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction() {
            @Override
            public Object run() {
                try {
                    AL.create();
                    SoundStore.HorizonCode_Horizon_È(SoundStore.this, true);
                    SoundStore.Â(SoundStore.this, true);
                    SoundStore.Ý(SoundStore.this, true);
                    Log.Ý("- Sound works");
                }
                catch (Exception e) {
                    Log.HorizonCode_Horizon_È("Sound initialisation failure.");
                    Log.HorizonCode_Horizon_È(e);
                    SoundStore.HorizonCode_Horizon_È(SoundStore.this, false);
                    SoundStore.Â(SoundStore.this, false);
                    SoundStore.Ý(SoundStore.this, false);
                }
                return null;
            }
        });
        if (this.Ø­áŒŠá) {
            this.Âµá€ = 0;
            this.Ø = BufferUtils.createIntBuffer(this.Ø­à);
            while (AL10.alGetError() == 0) {
                final IntBuffer temp = BufferUtils.createIntBuffer(1);
                try {
                    AL10.alGenSources(temp);
                    if (AL10.alGetError() != 0) {
                        continue;
                    }
                    ++this.Âµá€;
                    this.Ø.put(temp.get(0));
                    if (this.Âµá€ <= this.Ø­à - 1) {
                        continue;
                    }
                }
                catch (OpenALException e) {}
                break;
            }
            Log.Ý("- " + this.Âµá€ + " OpenAL source available");
            if (AL10.alGetError() != 0) {
                this.Â = false;
                this.Ý = false;
                this.Ø­áŒŠá = false;
                Log.HorizonCode_Horizon_È("- AL init failed");
            }
            else {
                final FloatBuffer listenerOri = BufferUtils.createFloatBuffer(6).put(new float[] { 0.0f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f });
                final FloatBuffer listenerVel = BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f });
                final FloatBuffer listenerPos = BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f });
                listenerPos.flip();
                listenerVel.flip();
                listenerOri.flip();
                AL10.alListener(4100, listenerPos);
                AL10.alListener(4102, listenerVel);
                AL10.alListener(4111, listenerOri);
                Log.Ý("- Sounds source generated");
            }
        }
    }
    
    void Ý(final int index) {
        AL10.alSourceStop(this.Ø.get(index));
    }
    
    int HorizonCode_Horizon_È(final int buffer, final float pitch, final float gain, final boolean loop) {
        return this.HorizonCode_Horizon_È(buffer, pitch, gain, loop, 0.0f, 0.0f, 0.0f);
    }
    
    int HorizonCode_Horizon_È(final int buffer, final float pitch, float gain, final boolean loop, final float x, final float y, final float z) {
        gain *= this.£á;
        if (gain == 0.0f) {
            gain = 0.001f;
        }
        if (!this.Ø­áŒŠá || !this.Â) {
            return -1;
        }
        final int nextSource = this.µà();
        if (nextSource == -1) {
            return -1;
        }
        AL10.alSourceStop(this.Ø.get(nextSource));
        AL10.alSourcei(this.Ø.get(nextSource), 4105, buffer);
        AL10.alSourcef(this.Ø.get(nextSource), 4099, pitch);
        AL10.alSourcef(this.Ø.get(nextSource), 4106, gain);
        AL10.alSourcei(this.Ø.get(nextSource), 4103, (int)(loop ? 1 : 0));
        this.¥Æ.clear();
        this.ˆà.clear();
        this.ˆà.put(new float[] { 0.0f, 0.0f, 0.0f });
        this.¥Æ.put(new float[] { x, y, z });
        this.¥Æ.flip();
        this.ˆà.flip();
        AL10.alSource(this.Ø.get(nextSource), 4100, this.¥Æ);
        AL10.alSource(this.Ø.get(nextSource), 4102, this.ˆà);
        AL10.alSourcePlay(this.Ø.get(nextSource));
        return nextSource;
    }
    
    boolean Ø­áŒŠá(final int index) {
        final int state = AL10.alGetSourcei(this.Ø.get(index), 4112);
        return state == 4114;
    }
    
    private int µà() {
        for (int i = 1; i < this.Âµá€ - 1; ++i) {
            final int state = AL10.alGetSourcei(this.Ø.get(i), 4112);
            if (state != 4114 && state != 4115) {
                return i;
            }
        }
        return -1;
    }
    
    void Â(final int buffer, final float pitch, final float gain, final boolean loop) {
        this.£à = false;
        this.HorizonCode_Horizon_È((MODSound)null);
        if (this.Ø­áŒŠá) {
            if (this.à != -1) {
                AL10.alSourceStop(this.Ø.get(0));
            }
            this.ˆà();
            AL10.alSourcei(this.Ø.get(0), 4105, buffer);
            AL10.alSourcef(this.Ø.get(0), 4099, pitch);
            AL10.alSourcei(this.Ø.get(0), 4103, (int)(loop ? 1 : 0));
            this.à = this.Ø.get(0);
            if (!this.Ý) {
                this.á();
            }
            else {
                AL10.alSourcePlay(this.Ø.get(0));
            }
        }
    }
    
    private int ˆà() {
        return this.Ø.get(0);
    }
    
    public void Ø­áŒŠá(final float pitch) {
        if (this.Ø­áŒŠá) {
            AL10.alSourcef(this.Ø.get(0), 4099, pitch);
        }
    }
    
    public void á() {
        if (this.Ø­áŒŠá && this.à != -1) {
            this.£à = true;
            AL10.alSourcePause(this.à);
        }
    }
    
    public void ˆÏ­() {
        if (this.Ý && this.Ø­áŒŠá && this.à != -1) {
            this.£à = false;
            AL10.alSourcePlay(this.à);
        }
    }
    
    boolean HorizonCode_Horizon_È(final OpenALStreamPlayer player) {
        return this.á == player;
    }
    
    public Audio HorizonCode_Horizon_È(final String ref) throws IOException {
        return this.HorizonCode_Horizon_È(ref, ResourceLoader.HorizonCode_Horizon_È(ref));
    }
    
    public Audio HorizonCode_Horizon_È(final InputStream in) throws IOException {
        return this.HorizonCode_Horizon_È(in.toString(), in);
    }
    
    public Audio HorizonCode_Horizon_È(final String ref, final InputStream in) throws IOException {
        if (!this.Ø­áŒŠá) {
            return new NullAudio();
        }
        if (!this.áˆºÑ¢Õ) {
            throw new RuntimeException("Can't load sounds until SoundStore is init(). Use the container init() method.");
        }
        if (this.µà) {
            return new DeferredSound(ref, in, 3);
        }
        return new MODSound(this, in);
    }
    
    public Audio Â(final String ref) throws IOException {
        return this.Â(ref, ResourceLoader.HorizonCode_Horizon_È(ref));
    }
    
    public Audio Â(final InputStream in) throws IOException {
        return this.Â(in.toString(), in);
    }
    
    public Audio Â(final String ref, InputStream in) throws IOException {
        in = new BufferedInputStream(in);
        if (!this.Ø­áŒŠá) {
            return new NullAudio();
        }
        if (!this.áˆºÑ¢Õ) {
            throw new RuntimeException("Can't load sounds until SoundStore is init(). Use the container init() method.");
        }
        if (this.µà) {
            return new DeferredSound(ref, in, 4);
        }
        int buffer = -1;
        if (this.Ó.get(ref) != null) {
            buffer = this.Ó.get(ref);
        }
        else {
            try {
                final IntBuffer buf = BufferUtils.createIntBuffer(1);
                final AiffData data = AiffData.HorizonCode_Horizon_È(in);
                AL10.alGenBuffers(buf);
                AL10.alBufferData(buf.get(0), data.Â, data.HorizonCode_Horizon_È, data.Ý);
                this.Ó.put(ref, new Integer(buf.get(0)));
                buffer = buf.get(0);
            }
            catch (Exception e) {
                Log.HorizonCode_Horizon_È(e);
                final IOException x = new IOException("Failed to load: " + ref);
                x.initCause(e);
                throw x;
            }
        }
        if (buffer == -1) {
            throw new IOException("Unable to load: " + ref);
        }
        return new AudioImpl(this, buffer);
    }
    
    public Audio Ý(final String ref) throws IOException {
        return this.Ý(ref, ResourceLoader.HorizonCode_Horizon_È(ref));
    }
    
    public Audio Ý(final InputStream in) throws IOException {
        return this.Ý(in.toString(), in);
    }
    
    public Audio Ý(final String ref, final InputStream in) throws IOException {
        if (!this.Ø­áŒŠá) {
            return new NullAudio();
        }
        if (!this.áˆºÑ¢Õ) {
            throw new RuntimeException("Can't load sounds until SoundStore is init(). Use the container init() method.");
        }
        if (this.µà) {
            return new DeferredSound(ref, in, 2);
        }
        int buffer = -1;
        if (this.Ó.get(ref) != null) {
            buffer = this.Ó.get(ref);
        }
        else {
            try {
                final IntBuffer buf = BufferUtils.createIntBuffer(1);
                final WaveData data = WaveData.HorizonCode_Horizon_È(in);
                AL10.alGenBuffers(buf);
                AL10.alBufferData(buf.get(0), data.Â, data.HorizonCode_Horizon_È, data.Ý);
                this.Ó.put(ref, new Integer(buf.get(0)));
                buffer = buf.get(0);
            }
            catch (Exception e) {
                Log.HorizonCode_Horizon_È(e);
                final IOException x = new IOException("Failed to load: " + ref);
                x.initCause(e);
                throw x;
            }
        }
        if (buffer == -1) {
            throw new IOException("Unable to load: " + ref);
        }
        return new AudioImpl(this, buffer);
    }
    
    public Audio Ø­áŒŠá(final String ref) throws IOException {
        if (!this.Ø­áŒŠá) {
            return new NullAudio();
        }
        this.HorizonCode_Horizon_È((MODSound)null);
        this.Â((OpenALStreamPlayer)null);
        if (this.à != -1) {
            AL10.alSourceStop(this.Ø.get(0));
        }
        this.ˆà();
        this.à = this.Ø.get(0);
        return new StreamSound(new OpenALStreamPlayer(this.à, ref));
    }
    
    public Audio HorizonCode_Horizon_È(final URL ref) throws IOException {
        if (!this.Ø­áŒŠá) {
            return new NullAudio();
        }
        this.HorizonCode_Horizon_È((MODSound)null);
        this.Â((OpenALStreamPlayer)null);
        if (this.à != -1) {
            AL10.alSourceStop(this.Ø.get(0));
        }
        this.ˆà();
        this.à = this.Ø.get(0);
        return new StreamSound(new OpenALStreamPlayer(this.à, ref));
    }
    
    public Audio Âµá€(final String ref) throws IOException {
        return this.Ø­áŒŠá(ref, ResourceLoader.HorizonCode_Horizon_È(ref));
    }
    
    public Audio Ø­áŒŠá(final InputStream in) throws IOException {
        return this.Ø­áŒŠá(in.toString(), in);
    }
    
    public Audio Ø­áŒŠá(final String ref, final InputStream in) throws IOException {
        if (!this.Ø­áŒŠá) {
            return new NullAudio();
        }
        if (!this.áˆºÑ¢Õ) {
            throw new RuntimeException("Can't load sounds until SoundStore is init(). Use the container init() method.");
        }
        if (this.µà) {
            return new DeferredSound(ref, in, 1);
        }
        int buffer = -1;
        if (this.Ó.get(ref) != null) {
            buffer = this.Ó.get(ref);
        }
        else {
            try {
                final IntBuffer buf = BufferUtils.createIntBuffer(1);
                final OggDecoder decoder = new OggDecoder();
                final OggData ogg = decoder.HorizonCode_Horizon_È(in);
                AL10.alGenBuffers(buf);
                AL10.alBufferData(buf.get(0), (ogg.Ý > 1) ? 4355 : 4353, ogg.HorizonCode_Horizon_È, ogg.Â);
                this.Ó.put(ref, new Integer(buf.get(0)));
                buffer = buf.get(0);
            }
            catch (Exception e) {
                Log.HorizonCode_Horizon_È(e);
                Sys.alert("Error", "Failed to load: " + ref + " - " + e.getMessage());
                throw new IOException("Unable to load: " + ref);
            }
        }
        if (buffer == -1) {
            throw new IOException("Unable to load: " + ref);
        }
        return new AudioImpl(this, buffer);
    }
    
    void HorizonCode_Horizon_È(final MODSound sound) {
        if (!this.Ø­áŒŠá) {
            return;
        }
        this.à = this.Ø.get(0);
        this.Ý(0);
        if ((this.ÂµÈ = sound) != null) {
            this.á = null;
        }
        this.£à = false;
    }
    
    void Â(final OpenALStreamPlayer stream) {
        if (!this.Ø­áŒŠá) {
            return;
        }
        this.à = this.Ø.get(0);
        if ((this.á = stream) != null) {
            this.ÂµÈ = null;
        }
        this.£à = false;
    }
    
    public void Âµá€(final int delta) {
        if (!this.Ø­áŒŠá) {
            return;
        }
        if (this.£à) {
            return;
        }
        if (this.Ý) {
            if (this.ÂµÈ != null) {
                try {
                    this.ÂµÈ.HorizonCode_Horizon_È();
                }
                catch (OpenALException e) {
                    Log.HorizonCode_Horizon_È("Error with OpenGL MOD Player on this this platform");
                    Log.HorizonCode_Horizon_È((Throwable)e);
                    this.ÂµÈ = null;
                }
            }
            if (this.á != null) {
                try {
                    this.á.Ý();
                }
                catch (OpenALException e) {
                    Log.HorizonCode_Horizon_È("Error with OpenGL Streaming Player on this this platform");
                    Log.HorizonCode_Horizon_È((Throwable)e);
                    this.ÂµÈ = null;
                }
            }
        }
    }
    
    public boolean £á() {
        if (!this.Ø­áŒŠá) {
            return false;
        }
        final int state = AL10.alGetSourcei(this.Ø.get(0), 4112);
        return state == 4114 || state == 4115;
    }
    
    public static SoundStore Å() {
        return SoundStore.HorizonCode_Horizon_È;
    }
    
    public void Ó(final int id) {
        AL10.alSourceStop(id);
    }
    
    public int £à() {
        return this.Âµá€;
    }
    
    static /* synthetic */ void HorizonCode_Horizon_È(final SoundStore soundStore, final boolean ø­áŒŠá) {
        soundStore.Ø­áŒŠá = ø­áŒŠá;
    }
    
    static /* synthetic */ void Â(final SoundStore soundStore, final boolean â) {
        soundStore.Â = â;
    }
    
    static /* synthetic */ void Ý(final SoundStore soundStore, final boolean ý) {
        soundStore.Ý = ý;
    }
}
