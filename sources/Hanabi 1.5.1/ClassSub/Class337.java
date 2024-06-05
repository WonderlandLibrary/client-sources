package ClassSub;

import java.util.*;
import java.net.*;
import java.io.*;

public class Class337
{
    private static ArrayList locations;
    
    
    public static void addResourceLocation(final Class217 class217) {
        Class337.locations.add(class217);
    }
    
    public static void removeResourceLocation(final Class217 class217) {
        Class337.locations.remove(class217);
    }
    
    public static void removeAllResourceLocations() {
        Class337.locations.clear();
    }
    
    public static InputStream getResourceAsStream(final String s) {
        InputStream resourceAsStream = null;
        for (int i = 0; i < Class337.locations.size(); ++i) {
            resourceAsStream = ((Class217)Class337.locations.get(i)).getResourceAsStream(s);
            if (resourceAsStream != null) {
                break;
            }
        }
        if (resourceAsStream == null) {
            throw new RuntimeException("Resource not found: " + s);
        }
        return new BufferedInputStream(resourceAsStream);
    }
    
    public static boolean resourceExists(final String s) {
        for (int i = 0; i < Class337.locations.size(); ++i) {
            if (((Class217)Class337.locations.get(i)).getResource(s) != null) {
                return true;
            }
        }
        return false;
    }
    
    public static URL getResource(final String s) {
        URL resource = null;
        for (int i = 0; i < Class337.locations.size(); ++i) {
            resource = ((Class217)Class337.locations.get(i)).getResource(s);
            if (resource != null) {
                break;
            }
        }
        if (resource == null) {
            throw new RuntimeException("Resource not found: " + s);
        }
        return resource;
    }
    
    static {
        (Class337.locations = new ArrayList()).add(new Class44());
        Class337.locations.add(new Class102(new File(".")));
    }
}
