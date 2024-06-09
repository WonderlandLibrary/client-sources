package HORIZON-6-0-SKIDPROTECTION;

import java.net.URL;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.File;
import java.util.ArrayList;

public class ResourceLoader
{
    private static ArrayList HorizonCode_Horizon_È;
    
    static {
        (ResourceLoader.HorizonCode_Horizon_È = new ArrayList()).add(new ClasspathLocation());
        ResourceLoader.HorizonCode_Horizon_È.add(new FileSystemLocation(new File(".")));
    }
    
    public static void HorizonCode_Horizon_È(final ResourceLocation location) {
        ResourceLoader.HorizonCode_Horizon_È.add(location);
    }
    
    public static void Â(final ResourceLocation location) {
        ResourceLoader.HorizonCode_Horizon_È.remove(location);
    }
    
    public static void HorizonCode_Horizon_È() {
        ResourceLoader.HorizonCode_Horizon_È.clear();
    }
    
    public static InputStream HorizonCode_Horizon_È(final String ref) {
        InputStream in = null;
        for (int i = 0; i < ResourceLoader.HorizonCode_Horizon_È.size(); ++i) {
            final ResourceLocation location = ResourceLoader.HorizonCode_Horizon_È.get(i);
            in = location.Â(ref);
            if (in != null) {
                break;
            }
        }
        if (in == null) {
            throw new RuntimeException("Resource not found: " + ref);
        }
        return new BufferedInputStream(in);
    }
    
    public static boolean Â(final String ref) {
        URL url = null;
        for (int i = 0; i < ResourceLoader.HorizonCode_Horizon_È.size(); ++i) {
            final ResourceLocation location = ResourceLoader.HorizonCode_Horizon_È.get(i);
            url = location.HorizonCode_Horizon_È(ref);
            if (url != null) {
                return true;
            }
        }
        return false;
    }
    
    public static URL Ý(final String ref) {
        URL url = null;
        for (int i = 0; i < ResourceLoader.HorizonCode_Horizon_È.size(); ++i) {
            final ResourceLocation location = ResourceLoader.HorizonCode_Horizon_È.get(i);
            url = location.HorizonCode_Horizon_È(ref);
            if (url != null) {
                break;
            }
        }
        if (url == null) {
            throw new RuntimeException("Resource not found: " + ref);
        }
        return url;
    }
}
