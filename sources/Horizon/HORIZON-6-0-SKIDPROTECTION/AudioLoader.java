package HORIZON-6-0-SKIDPROTECTION;

import java.net.URL;
import java.io.IOException;
import java.io.InputStream;

public class AudioLoader
{
    private static final String HorizonCode_Horizon_È = "AIF";
    private static final String Â = "WAV";
    private static final String Ý = "OGG";
    private static final String Ø­áŒŠá = "MOD";
    private static final String Âµá€ = "XM";
    private static boolean Ó;
    
    static {
        AudioLoader.Ó = false;
    }
    
    private static void Â() {
        if (!AudioLoader.Ó) {
            SoundStore.Å().ÂµÈ();
            AudioLoader.Ó = true;
        }
    }
    
    public static Audio HorizonCode_Horizon_È(final String format, final InputStream in) throws IOException {
        Â();
        if (format.equals("AIF")) {
            return SoundStore.Å().Â(in);
        }
        if (format.equals("WAV")) {
            return SoundStore.Å().Ý(in);
        }
        if (format.equals("OGG")) {
            return SoundStore.Å().Ø­áŒŠá(in);
        }
        throw new IOException("Unsupported format for non-streaming Audio: " + format);
    }
    
    public static Audio HorizonCode_Horizon_È(final String format, final URL url) throws IOException {
        Â();
        if (format.equals("OGG")) {
            return SoundStore.Å().HorizonCode_Horizon_È(url);
        }
        if (format.equals("MOD")) {
            return SoundStore.Å().HorizonCode_Horizon_È(url.openStream());
        }
        if (format.equals("XM")) {
            return SoundStore.Å().HorizonCode_Horizon_È(url.openStream());
        }
        throw new IOException("Unsupported format for streaming Audio: " + format);
    }
    
    public static void HorizonCode_Horizon_È() {
        Â();
        SoundStore.Å().Âµá€(0);
    }
}
