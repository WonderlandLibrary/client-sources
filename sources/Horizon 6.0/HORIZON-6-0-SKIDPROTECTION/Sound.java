package HORIZON-6-0-SKIDPROTECTION;

import java.net.URL;
import java.io.InputStream;

public class Sound
{
    private Audio HorizonCode_Horizon_È;
    
    public Sound(final InputStream in, final String ref) throws SlickException {
        SoundStore.Å().ÂµÈ();
        try {
            if (ref.toLowerCase().endsWith(".ogg")) {
                this.HorizonCode_Horizon_È = SoundStore.Å().Ø­áŒŠá(in);
            }
            else if (ref.toLowerCase().endsWith(".wav")) {
                this.HorizonCode_Horizon_È = SoundStore.Å().Ý(in);
            }
            else if (ref.toLowerCase().endsWith(".aif")) {
                this.HorizonCode_Horizon_È = SoundStore.Å().Â(in);
            }
            else {
                if (!ref.toLowerCase().endsWith(".xm") && !ref.toLowerCase().endsWith(".mod")) {
                    throw new SlickException("Only .xm, .mod, .aif, .wav and .ogg are currently supported.");
                }
                this.HorizonCode_Horizon_È = SoundStore.Å().HorizonCode_Horizon_È(in);
            }
        }
        catch (Exception e) {
            Log.HorizonCode_Horizon_È(e);
            throw new SlickException("Failed to load sound: " + ref);
        }
    }
    
    public Sound(final URL url) throws SlickException {
        SoundStore.Å().ÂµÈ();
        final String ref = url.getFile();
        try {
            if (ref.toLowerCase().endsWith(".ogg")) {
                this.HorizonCode_Horizon_È = SoundStore.Å().Ø­áŒŠá(url.openStream());
            }
            else if (ref.toLowerCase().endsWith(".wav")) {
                this.HorizonCode_Horizon_È = SoundStore.Å().Ý(url.openStream());
            }
            else if (ref.toLowerCase().endsWith(".aif")) {
                this.HorizonCode_Horizon_È = SoundStore.Å().Â(url.openStream());
            }
            else {
                if (!ref.toLowerCase().endsWith(".xm") && !ref.toLowerCase().endsWith(".mod")) {
                    throw new SlickException("Only .xm, .mod, .aif, .wav and .ogg are currently supported.");
                }
                this.HorizonCode_Horizon_È = SoundStore.Å().HorizonCode_Horizon_È(url.openStream());
            }
        }
        catch (Exception e) {
            Log.HorizonCode_Horizon_È(e);
            throw new SlickException("Failed to load sound: " + ref);
        }
    }
    
    public Sound(final String ref) throws SlickException {
        SoundStore.Å().ÂµÈ();
        try {
            if (ref.toLowerCase().endsWith(".ogg")) {
                this.HorizonCode_Horizon_È = SoundStore.Å().Âµá€(ref);
            }
            else if (ref.toLowerCase().endsWith(".wav")) {
                this.HorizonCode_Horizon_È = SoundStore.Å().Ý(ref);
            }
            else if (ref.toLowerCase().endsWith(".aif")) {
                this.HorizonCode_Horizon_È = SoundStore.Å().Â(ref);
            }
            else {
                if (!ref.toLowerCase().endsWith(".xm") && !ref.toLowerCase().endsWith(".mod")) {
                    throw new SlickException("Only .xm, .mod, .aif, .wav and .ogg are currently supported.");
                }
                this.HorizonCode_Horizon_È = SoundStore.Å().HorizonCode_Horizon_È(ref);
            }
        }
        catch (Exception e) {
            Log.HorizonCode_Horizon_È(e);
            throw new SlickException("Failed to load sound: " + ref);
        }
    }
    
    public void HorizonCode_Horizon_È() {
        this.HorizonCode_Horizon_È(1.0f, 1.0f);
    }
    
    public void HorizonCode_Horizon_È(final float pitch, final float volume) {
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(pitch, volume * SoundStore.Å().Ø(), false);
    }
    
    public void HorizonCode_Horizon_È(final float x, final float y, final float z) {
        this.HorizonCode_Horizon_È(1.0f, 1.0f, x, y, z);
    }
    
    public void HorizonCode_Horizon_È(final float pitch, final float volume, final float x, final float y, final float z) {
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(pitch, volume * SoundStore.Å().Ø(), false, x, y, z);
    }
    
    public void Â() {
        this.Â(1.0f, 1.0f);
    }
    
    public void Â(final float pitch, final float volume) {
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(pitch, volume * SoundStore.Å().Ø(), true);
    }
    
    public boolean Ý() {
        return this.HorizonCode_Horizon_È.Âµá€();
    }
    
    public void Ø­áŒŠá() {
        this.HorizonCode_Horizon_È.Ý();
    }
}
