/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.impl.ICUData;
import java.io.IOException;
import java.io.InputStream;
import java.security.AccessControlException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.MissingResourceException;
import java.util.Properties;

public class ICUConfig {
    public static final String CONFIG_PROPS_FILE = "/com/ibm/icu/ICUConfig.properties";
    private static final Properties CONFIG_PROPS;

    public static String get(String string) {
        return ICUConfig.get(string, null);
    }

    public static String get(String string, String string2) {
        String string3 = null;
        String string4 = string;
        if (System.getSecurityManager() != null) {
            try {
                string3 = AccessController.doPrivileged(new PrivilegedAction<String>(string4){
                    final String val$fname;
                    {
                        this.val$fname = string;
                    }

                    @Override
                    public String run() {
                        return System.getProperty(this.val$fname);
                    }

                    @Override
                    public Object run() {
                        return this.run();
                    }
                });
            } catch (AccessControlException accessControlException) {}
        } else {
            string3 = System.getProperty(string);
        }
        if (string3 == null) {
            string3 = CONFIG_PROPS.getProperty(string, string2);
        }
        return string3;
    }

    static {
        block6: {
            CONFIG_PROPS = new Properties();
            try {
                InputStream inputStream = ICUData.getStream(CONFIG_PROPS_FILE);
                if (inputStream == null) break block6;
                try {
                    CONFIG_PROPS.load(inputStream);
                } finally {
                    inputStream.close();
                }
            } catch (MissingResourceException missingResourceException) {
            } catch (IOException iOException) {
                // empty catch block
            }
        }
    }
}

