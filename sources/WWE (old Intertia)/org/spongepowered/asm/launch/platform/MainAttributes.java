package org.spongepowered.asm.launch.platform;

import java.net.*;
import java.io.*;
import java.util.jar.*;
import java.util.*;

final class MainAttributes
{
    private static final Map<URI, MainAttributes> instances;
    protected final Attributes attributes;
    
    private MainAttributes() {
        super();
        this.attributes = new Attributes();
    }
    
    private MainAttributes(final File file) {
        super();
        this.attributes = getAttributes(file);
    }
    
    public final String get(final String s) {
        if (this.attributes != null) {
            return this.attributes.getValue(s);
        }
        return null;
    }
    
    private static Attributes getAttributes(final File file) {
        if (file == null) {
            return null;
        }
        JarFile jarFile = null;
        try {
            jarFile = new JarFile(file);
            final Manifest manifest = jarFile.getManifest();
            if (manifest != null) {
                return manifest.getMainAttributes();
            }
        }
        catch (IOException ex) {}
        finally {
            try {
                if (jarFile != null) {
                    jarFile.close();
                }
            }
            catch (IOException ex2) {}
        }
        return new Attributes();
    }
    
    public static MainAttributes of(final File file) {
        return of(file.toURI());
    }
    
    public static MainAttributes of(final URI uri) {
        MainAttributes mainAttributes = MainAttributes.instances.get(uri);
        if (mainAttributes == null) {
            mainAttributes = new MainAttributes(new File(uri));
            MainAttributes.instances.put(uri, mainAttributes);
        }
        return mainAttributes;
    }
    
    static {
        instances = new HashMap<URI, MainAttributes>();
    }
}
