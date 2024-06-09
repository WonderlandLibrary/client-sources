package HORIZON-6-0-SKIDPROTECTION;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ImageOut
{
    private static final boolean Ø­áŒŠá = false;
    public static String HorizonCode_Horizon_È;
    public static String Â;
    public static String Ý;
    
    static {
        ImageOut.HorizonCode_Horizon_È = "tga";
        ImageOut.Â = "png";
        ImageOut.Ý = "jpg";
    }
    
    public static String[] HorizonCode_Horizon_È() {
        return ImageWriterFactory.HorizonCode_Horizon_È();
    }
    
    public static void HorizonCode_Horizon_È(final Image image, final String format, final OutputStream out) throws SlickException {
        HorizonCode_Horizon_È(image, format, out, false);
    }
    
    public static void HorizonCode_Horizon_È(final Image image, final String format, final OutputStream out, final boolean writeAlpha) throws SlickException {
        try {
            final ImageWriter writer = ImageWriterFactory.HorizonCode_Horizon_È(format);
            writer.HorizonCode_Horizon_È(image, format, out, writeAlpha);
        }
        catch (IOException e) {
            throw new SlickException("Unable to write out the image in format: " + format, e);
        }
    }
    
    public static void HorizonCode_Horizon_È(final Image image, final String dest) throws SlickException {
        HorizonCode_Horizon_È(image, dest, false);
    }
    
    public static void HorizonCode_Horizon_È(final Image image, final String dest, final boolean writeAlpha) throws SlickException {
        try {
            final int ext = dest.lastIndexOf(46);
            if (ext < 0) {
                throw new SlickException("Unable to determine format from: " + dest);
            }
            final String format = dest.substring(ext + 1);
            HorizonCode_Horizon_È(image, format, new FileOutputStream(dest), writeAlpha);
        }
        catch (IOException e) {
            throw new SlickException("Unable to write to the destination: " + dest, e);
        }
    }
    
    public static void HorizonCode_Horizon_È(final Image image, final String format, final String dest) throws SlickException {
        HorizonCode_Horizon_È(image, format, dest, false);
    }
    
    public static void HorizonCode_Horizon_È(final Image image, final String format, final String dest, final boolean writeAlpha) throws SlickException {
        try {
            HorizonCode_Horizon_È(image, format, new FileOutputStream(dest), writeAlpha);
        }
        catch (IOException e) {
            throw new SlickException("Unable to write to the destination: " + dest, e);
        }
    }
}
