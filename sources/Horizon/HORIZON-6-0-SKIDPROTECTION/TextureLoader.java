package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;
import java.io.InputStream;

public class TextureLoader
{
    public static Texture HorizonCode_Horizon_È(final String format, final InputStream in) throws IOException {
        return HorizonCode_Horizon_È(format, in, false, 9729);
    }
    
    public static Texture HorizonCode_Horizon_È(final String format, final InputStream in, final boolean flipped) throws IOException {
        return HorizonCode_Horizon_È(format, in, flipped, 9729);
    }
    
    public static Texture HorizonCode_Horizon_È(final String format, final InputStream in, final int filter) throws IOException {
        return HorizonCode_Horizon_È(format, in, false, filter);
    }
    
    public static Texture HorizonCode_Horizon_È(final String format, final InputStream in, final boolean flipped, final int filter) throws IOException {
        return InternalTextureLoader.HorizonCode_Horizon_È().HorizonCode_Horizon_È(in, String.valueOf(in.toString()) + "." + format, flipped, filter);
    }
}
