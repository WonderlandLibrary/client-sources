package HORIZON-6-0-SKIDPROTECTION;

import javax.imageio.ImageIO;
import java.util.HashMap;

public class ImageWriterFactory
{
    private static HashMap HorizonCode_Horizon_È;
    
    static {
        ImageWriterFactory.HorizonCode_Horizon_È = new HashMap();
        final String[] formats = ImageIO.getWriterFormatNames();
        final ImageIOWriter writer = new ImageIOWriter();
        for (int i = 0; i < formats.length; ++i) {
            HorizonCode_Horizon_È(formats[i], writer);
        }
        final TGAWriter tga = new TGAWriter();
        HorizonCode_Horizon_È("tga", tga);
    }
    
    public static void HorizonCode_Horizon_È(final String format, final ImageWriter writer) {
        ImageWriterFactory.HorizonCode_Horizon_È.put(format, writer);
    }
    
    public static String[] HorizonCode_Horizon_È() {
        return (String[])ImageWriterFactory.HorizonCode_Horizon_È.keySet().toArray(new String[0]);
    }
    
    public static ImageWriter HorizonCode_Horizon_È(final String format) throws SlickException {
        ImageWriter writer = ImageWriterFactory.HorizonCode_Horizon_È.get(format);
        if (writer != null) {
            return writer;
        }
        writer = ImageWriterFactory.HorizonCode_Horizon_È.get(format.toLowerCase());
        if (writer != null) {
            return writer;
        }
        writer = ImageWriterFactory.HorizonCode_Horizon_È.get(format.toUpperCase());
        if (writer != null) {
            return writer;
        }
        throw new SlickException("No image writer available for: " + format);
    }
}
