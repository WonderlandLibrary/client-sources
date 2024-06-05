package com.ibm.icu.impl;

import java.security.*;
import java.util.*;
import java.io.*;

public class ICUConfig
{
    public static final String CONFIG_PROPS_FILE = "/com/ibm/icu/ICUConfig.properties";
    private static final Properties CONFIG_PROPS;
    
    public static String get(final String name) {
        return get(name, null);
    }
    
    public static String get(final String name, final String def) {
        String val = null;
        final String fname = name;
        if (System.getSecurityManager() != null) {
            try {
                val = AccessController.doPrivileged((PrivilegedAction<String>)new PrivilegedAction<String>() {
                    @Override
                    public String run() {
                        return System.getProperty(fname);
                    }
                });
            }
            catch (AccessControlException ex) {}
        }
        else {
            val = System.getProperty(name);
        }
        if (val == null) {
            val = ICUConfig.CONFIG_PROPS.getProperty(name, def);
        }
        return val;
    }
    
    static {
        CONFIG_PROPS = new Properties();
        try {
            final InputStream is = ICUData.getStream("/com/ibm/icu/ICUConfig.properties");
            if (is != null) {
                try {
                    ICUConfig.CONFIG_PROPS.load(is);
                }
                finally {
                    is.close();
                }
            }
        }
        catch (MissingResourceException ex) {}
        catch (IOException ex2) {}
    }
}
