/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.impl.ClassLoaderUtil;
import com.ibm.icu.impl.ICUDebug;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public abstract class URLHandler {
    public static final String PROPNAME = "urlhandler.props";
    private static final Map<String, Method> handlers;
    private static final boolean DEBUG;

    public static URLHandler get(URL uRL) {
        block9: {
            Method method;
            if (uRL == null) {
                return null;
            }
            String string = uRL.getProtocol();
            if (handlers != null && (method = handlers.get(string)) != null) {
                try {
                    URLHandler uRLHandler = (URLHandler)method.invoke(null, uRL);
                    if (uRLHandler != null) {
                        return uRLHandler;
                    }
                } catch (IllegalAccessException illegalAccessException) {
                    if (DEBUG) {
                        System.err.println(illegalAccessException);
                    }
                } catch (IllegalArgumentException illegalArgumentException) {
                    if (DEBUG) {
                        System.err.println(illegalArgumentException);
                    }
                } catch (InvocationTargetException invocationTargetException) {
                    if (!DEBUG) break block9;
                    System.err.println(invocationTargetException);
                }
            }
        }
        return URLHandler.getDefault(uRL);
    }

    protected static URLHandler getDefault(URL uRL) {
        URLHandler uRLHandler = null;
        String string = uRL.getProtocol();
        try {
            if (string.equals("file")) {
                uRLHandler = new FileURLHandler(uRL);
            } else if (string.equals("jar") || string.equals("wsjar")) {
                uRLHandler = new JarURLHandler(uRL);
            }
        } catch (Exception exception) {
            // empty catch block
        }
        return uRLHandler;
    }

    public void guide(URLVisitor uRLVisitor, boolean bl) {
        this.guide(uRLVisitor, bl, false);
    }

    public abstract void guide(URLVisitor var1, boolean var2, boolean var3);

    static boolean access$000() {
        return DEBUG;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static {
        HashMap<String, Method> hashMap;
        block23: {
            DEBUG = ICUDebug.enabled("URLHandler");
            hashMap = null;
            BufferedReader bufferedReader = null;
            try {
                ClassLoader classLoader = ClassLoaderUtil.getClassLoader(URLHandler.class);
                InputStream inputStream = classLoader.getResourceAsStream(PROPNAME);
                if (inputStream == null) break block23;
                Class[] classArray = new Class[]{URL.class};
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String string = bufferedReader.readLine();
                while (string != null) {
                    block24: {
                        if ((string = string.trim()).length() != 0 && string.charAt(0) != '#') {
                            int n = string.indexOf(61);
                            if (n == -1) {
                                if (!DEBUG) break;
                                System.err.println("bad urlhandler line: '" + string + "'");
                                break;
                            }
                            String string2 = string.substring(0, n).trim();
                            String string3 = string.substring(n + 1).trim();
                            try {
                                Class<?> clazz = Class.forName(string3);
                                Method method = clazz.getDeclaredMethod("get", classArray);
                                if (hashMap == null) {
                                    hashMap = new HashMap<String, Method>();
                                }
                                hashMap.put(string2, method);
                            } catch (ClassNotFoundException classNotFoundException) {
                                if (DEBUG) {
                                    System.err.println(classNotFoundException);
                                }
                            } catch (NoSuchMethodException noSuchMethodException) {
                                if (DEBUG) {
                                    System.err.println(noSuchMethodException);
                                }
                            } catch (SecurityException securityException) {
                                if (!DEBUG) break block24;
                                System.err.println(securityException);
                            }
                        }
                    }
                    string = bufferedReader.readLine();
                }
                bufferedReader.close();
            } catch (Throwable throwable) {
                if (DEBUG) {
                    System.err.println(throwable);
                }
            } finally {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException iOException) {}
                }
            }
        }
        handlers = hashMap;
    }

    public static interface URLVisitor {
        public void visit(String var1);
    }

    private static class JarURLHandler
    extends URLHandler {
        JarFile jarFile;
        String prefix;

        JarURLHandler(URL uRL) {
            try {
                Object object;
                int n;
                String string;
                this.prefix = uRL.getPath();
                int n2 = this.prefix.lastIndexOf("!/");
                if (n2 >= 0) {
                    this.prefix = this.prefix.substring(n2 + 2);
                }
                if (!(string = uRL.getProtocol()).equals("jar") && (n = ((String)(object = uRL.toString())).indexOf(":")) != -1) {
                    uRL = new URL("jar" + ((String)object).substring(n));
                }
                object = (JarURLConnection)uRL.openConnection();
                this.jarFile = ((JarURLConnection)object).getJarFile();
            } catch (Exception exception) {
                if (URLHandler.access$000()) {
                    System.err.println("icurb jar error: " + exception);
                }
                throw new IllegalArgumentException("jar error: " + exception.getMessage());
            }
        }

        @Override
        public void guide(URLVisitor uRLVisitor, boolean bl, boolean bl2) {
            block4: {
                try {
                    Enumeration<JarEntry> enumeration = this.jarFile.entries();
                    while (enumeration.hasMoreElements()) {
                        int n;
                        String string;
                        JarEntry jarEntry = enumeration.nextElement();
                        if (jarEntry.isDirectory() || !(string = jarEntry.getName()).startsWith(this.prefix) || (n = (string = string.substring(this.prefix.length())).lastIndexOf(47)) > 0 && !bl) continue;
                        if (bl2 && n != -1) {
                            string = string.substring(n + 1);
                        }
                        uRLVisitor.visit(string);
                    }
                } catch (Exception exception) {
                    if (!URLHandler.access$000()) break block4;
                    System.err.println("icurb jar error: " + exception);
                }
            }
        }
    }

    private static class FileURLHandler
    extends URLHandler {
        File file;

        FileURLHandler(URL uRL) {
            try {
                this.file = new File(uRL.toURI());
            } catch (URISyntaxException uRISyntaxException) {
                // empty catch block
            }
            if (this.file == null || !this.file.exists()) {
                if (URLHandler.access$000()) {
                    System.err.println("file does not exist - " + uRL.toString());
                }
                throw new IllegalArgumentException();
            }
        }

        @Override
        public void guide(URLVisitor uRLVisitor, boolean bl, boolean bl2) {
            if (this.file.isDirectory()) {
                this.process(uRLVisitor, bl, bl2, "/", this.file.listFiles());
            } else {
                uRLVisitor.visit(this.file.getName());
            }
        }

        private void process(URLVisitor uRLVisitor, boolean bl, boolean bl2, String string, File[] fileArray) {
            if (fileArray != null) {
                for (int i = 0; i < fileArray.length; ++i) {
                    File file = fileArray[i];
                    if (file.isDirectory()) {
                        if (!bl) continue;
                        this.process(uRLVisitor, bl, bl2, string + file.getName() + '/', file.listFiles());
                        continue;
                    }
                    uRLVisitor.visit(bl2 ? file.getName() : string + file.getName());
                }
            }
        }
    }
}

