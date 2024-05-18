package HORIZON-6-0-SKIDPROTECTION;

import java.security.AccessController;
import java.security.PrivilegedAction;

public class ImageDataFactory
{
    private static boolean HorizonCode_Horizon_È;
    private static boolean Â;
    private static final String Ý = "org.newdawn.slick.pngloader";
    
    static {
        ImageDataFactory.HorizonCode_Horizon_È = true;
        ImageDataFactory.Â = false;
    }
    
    private static void Â() {
        if (!ImageDataFactory.Â) {
            ImageDataFactory.Â = true;
            try {
                AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction() {
                    @Override
                    public Object run() {
                        final String val = System.getProperty("org.newdawn.slick.pngloader");
                        if ("false".equalsIgnoreCase(val)) {
                            ImageDataFactory.HorizonCode_Horizon_È(false);
                        }
                        Log.Ý("Use Java PNG Loader = " + ImageDataFactory.HorizonCode_Horizon_È);
                        return null;
                    }
                });
            }
            catch (Throwable t) {}
        }
    }
    
    public static LoadableImageData HorizonCode_Horizon_È(String ref) {
        Â();
        ref = ref.toLowerCase();
        if (ref.endsWith(".tga")) {
            return new TGAImageData();
        }
        if (ref.endsWith(".png")) {
            final CompositeImageData data = new CompositeImageData();
            if (ImageDataFactory.HorizonCode_Horizon_È) {
                data.HorizonCode_Horizon_È(new PNGImageData());
            }
            data.HorizonCode_Horizon_È(new ImageIOImageData());
            return data;
        }
        return new ImageIOImageData();
    }
    
    static /* synthetic */ void HorizonCode_Horizon_È(final boolean horizonCode_Horizon_È) {
        ImageDataFactory.HorizonCode_Horizon_È = horizonCode_Horizon_È;
    }
}
