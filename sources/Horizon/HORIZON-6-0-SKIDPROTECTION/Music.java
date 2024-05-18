package HORIZON-6-0-SKIDPROTECTION;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class Music
{
    private static Music HorizonCode_Horizon_È;
    private Audio Â;
    private boolean Ý;
    private ArrayList Ø­áŒŠá;
    private float Âµá€;
    private float Ó;
    private float à;
    private int Ø;
    private int áŒŠÆ;
    private boolean áˆºÑ¢Õ;
    private boolean ÂµÈ;
    private float á;
    
    public static void HorizonCode_Horizon_È(final int delta) {
        if (Music.HorizonCode_Horizon_È != null) {
            SoundStore.Å().Âµá€(delta);
            if (!SoundStore.Å().£á()) {
                if (!Music.HorizonCode_Horizon_È.ÂµÈ) {
                    final Music oldMusic = Music.HorizonCode_Horizon_È;
                    Music.HorizonCode_Horizon_È = null;
                    oldMusic.áŒŠÆ();
                }
            }
            else {
                Music.HorizonCode_Horizon_È.Â(delta);
            }
        }
    }
    
    public Music(final String ref) throws SlickException {
        this(ref, false);
    }
    
    public Music(final URL ref) throws SlickException {
        this(ref, false);
    }
    
    public Music(final InputStream in, final String ref) throws SlickException {
        this.Ø­áŒŠá = new ArrayList();
        this.Âµá€ = 1.0f;
        this.á = -1.0f;
        SoundStore.Å().ÂµÈ();
        try {
            if (ref.toLowerCase().endsWith(".ogg")) {
                this.Â = SoundStore.Å().Ø­áŒŠá(in);
            }
            else if (ref.toLowerCase().endsWith(".wav")) {
                this.Â = SoundStore.Å().Ý(in);
            }
            else if (ref.toLowerCase().endsWith(".xm") || ref.toLowerCase().endsWith(".mod")) {
                this.Â = SoundStore.Å().HorizonCode_Horizon_È(in);
            }
            else {
                if (!ref.toLowerCase().endsWith(".aif") && !ref.toLowerCase().endsWith(".aiff")) {
                    throw new SlickException("Only .xm, .mod, .ogg, and .aif/f are currently supported.");
                }
                this.Â = SoundStore.Å().Â(in);
            }
        }
        catch (Exception e) {
            Log.HorizonCode_Horizon_È(e);
            throw new SlickException("Failed to load music: " + ref);
        }
    }
    
    public Music(final URL url, final boolean streamingHint) throws SlickException {
        this.Ø­áŒŠá = new ArrayList();
        this.Âµá€ = 1.0f;
        this.á = -1.0f;
        SoundStore.Å().ÂµÈ();
        final String ref = url.getFile();
        try {
            if (ref.toLowerCase().endsWith(".ogg")) {
                if (streamingHint) {
                    this.Â = SoundStore.Å().HorizonCode_Horizon_È(url);
                }
                else {
                    this.Â = SoundStore.Å().Ø­áŒŠá(url.openStream());
                }
            }
            else if (ref.toLowerCase().endsWith(".wav")) {
                this.Â = SoundStore.Å().Ý(url.openStream());
            }
            else if (ref.toLowerCase().endsWith(".xm") || ref.toLowerCase().endsWith(".mod")) {
                this.Â = SoundStore.Å().HorizonCode_Horizon_È(url.openStream());
            }
            else {
                if (!ref.toLowerCase().endsWith(".aif") && !ref.toLowerCase().endsWith(".aiff")) {
                    throw new SlickException("Only .xm, .mod, .ogg, and .aif/f are currently supported.");
                }
                this.Â = SoundStore.Å().Â(url.openStream());
            }
        }
        catch (Exception e) {
            Log.HorizonCode_Horizon_È(e);
            throw new SlickException("Failed to load sound: " + url);
        }
    }
    
    public Music(final String ref, final boolean streamingHint) throws SlickException {
        this.Ø­áŒŠá = new ArrayList();
        this.Âµá€ = 1.0f;
        this.á = -1.0f;
        SoundStore.Å().ÂµÈ();
        try {
            if (ref.toLowerCase().endsWith(".ogg")) {
                if (streamingHint) {
                    this.Â = SoundStore.Å().Ø­áŒŠá(ref);
                }
                else {
                    this.Â = SoundStore.Å().Âµá€(ref);
                }
            }
            else if (ref.toLowerCase().endsWith(".wav")) {
                this.Â = SoundStore.Å().Ý(ref);
            }
            else if (ref.toLowerCase().endsWith(".xm") || ref.toLowerCase().endsWith(".mod")) {
                this.Â = SoundStore.Å().HorizonCode_Horizon_È(ref);
            }
            else {
                if (!ref.toLowerCase().endsWith(".aif") && !ref.toLowerCase().endsWith(".aiff")) {
                    throw new SlickException("Only .xm, .mod, .ogg, and .aif/f are currently supported.");
                }
                this.Â = SoundStore.Å().Â(ref);
            }
        }
        catch (Exception e) {
            Log.HorizonCode_Horizon_È(e);
            throw new SlickException("Failed to load sound: " + ref);
        }
    }
    
    public void HorizonCode_Horizon_È(final MusicListener listener) {
        this.Ø­áŒŠá.add(listener);
    }
    
    public void Â(final MusicListener listener) {
        this.Ø­áŒŠá.remove(listener);
    }
    
    private void áŒŠÆ() {
        this.Ý = false;
        for (int i = 0; i < this.Ø­áŒŠá.size(); ++i) {
            this.Ø­áŒŠá.get(i).HorizonCode_Horizon_È(this);
        }
    }
    
    private void HorizonCode_Horizon_È(final Music newMusic) {
        this.Ý = false;
        for (int i = 0; i < this.Ø­áŒŠá.size(); ++i) {
            this.Ø­áŒŠá.get(i).HorizonCode_Horizon_È(this, newMusic);
        }
    }
    
    public void HorizonCode_Horizon_È() {
        this.Â(1.0f, 1.0f);
    }
    
    public void Â() {
        this.HorizonCode_Horizon_È(1.0f, 1.0f);
    }
    
    public void HorizonCode_Horizon_È(final float pitch, final float volume) {
        this.HorizonCode_Horizon_È(pitch, volume, false);
    }
    
    public void Â(final float pitch, final float volume) {
        this.HorizonCode_Horizon_È(pitch, volume, true);
    }
    
    private void HorizonCode_Horizon_È(final float pitch, float volume, final boolean loop) {
        if (Music.HorizonCode_Horizon_È != null) {
            Music.HorizonCode_Horizon_È.Ø­áŒŠá();
            Music.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this);
        }
        Music.HorizonCode_Horizon_È = this;
        if (volume < 0.0f) {
            volume = 0.0f;
        }
        if (volume > 1.0f) {
            volume = 1.0f;
        }
        this.Â.Â(pitch, volume, loop);
        this.Ý = true;
        this.HorizonCode_Horizon_È(volume);
        if (this.á != -1.0f) {
            this.Â(this.á);
        }
    }
    
    public void Ý() {
        this.Ý = false;
        AudioImpl.à();
    }
    
    public void Ø­áŒŠá() {
        this.Â.Ý();
    }
    
    public void Âµá€() {
        this.Ý = true;
        AudioImpl.Ø();
    }
    
    public boolean Ó() {
        return Music.HorizonCode_Horizon_È == this && this.Ý;
    }
    
    public void HorizonCode_Horizon_È(float volume) {
        if (volume > 1.0f) {
            volume = 1.0f;
        }
        else if (volume < 0.0f) {
            volume = 0.0f;
        }
        this.Âµá€ = volume;
        if (Music.HorizonCode_Horizon_È == this) {
            SoundStore.Å().Â(volume);
        }
    }
    
    public float à() {
        return this.Âµá€;
    }
    
    public void HorizonCode_Horizon_È(final int duration, final float endVolume, final boolean stopAfterFade) {
        this.áˆºÑ¢Õ = stopAfterFade;
        this.Ó = this.Âµá€;
        this.à = endVolume;
        this.áŒŠÆ = duration;
        this.Ø = duration;
    }
    
    void Â(final int delta) {
        if (!this.Ý) {
            return;
        }
        if (this.Ø > 0) {
            this.Ø -= delta;
            if (this.Ø < 0) {
                this.Ø = 0;
                if (this.áˆºÑ¢Õ) {
                    this.Ø­áŒŠá();
                    return;
                }
            }
            final float offset = (this.à - this.Ó) * (1.0f - this.Ø / this.áŒŠÆ);
            this.HorizonCode_Horizon_È(this.Ó + offset);
        }
    }
    
    public boolean Â(final float position) {
        if (this.Ý) {
            this.á = -1.0f;
            this.ÂµÈ = true;
            this.Ý = false;
            final boolean result = this.Â.HorizonCode_Horizon_È(position);
            this.Ý = true;
            this.ÂµÈ = false;
            return result;
        }
        this.á = position;
        return false;
    }
    
    public float Ø() {
        return this.Â.Ó();
    }
}
