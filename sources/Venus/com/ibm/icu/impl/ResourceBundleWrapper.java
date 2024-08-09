/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.impl.CacheBase;
import com.ibm.icu.impl.ClassLoaderUtil;
import com.ibm.icu.impl.ICUDebug;
import com.ibm.icu.impl.SoftCache;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public final class ResourceBundleWrapper
extends UResourceBundle {
    private ResourceBundle bundle = null;
    private String localeID = null;
    private String baseName = null;
    private List<String> keys = null;
    private static CacheBase<String, ResourceBundleWrapper, Loader> BUNDLE_CACHE = new SoftCache<String, ResourceBundleWrapper, Loader>(){

        @Override
        protected ResourceBundleWrapper createInstance(String string, Loader loader) {
            return loader.load();
        }

        @Override
        protected Object createInstance(Object object, Object object2) {
            return this.createInstance((String)object, (Loader)object2);
        }
    };
    private static final boolean DEBUG = ICUDebug.enabled("resourceBundleWrapper");

    private ResourceBundleWrapper(ResourceBundle resourceBundle) {
        this.bundle = resourceBundle;
    }

    @Override
    protected Object handleGetObject(String string) {
        Object object = null;
        for (ResourceBundleWrapper resourceBundleWrapper = this; resourceBundleWrapper != null; resourceBundleWrapper = (ResourceBundleWrapper)resourceBundleWrapper.getParent()) {
            try {
                object = resourceBundleWrapper.bundle.getObject(string);
                break;
            } catch (MissingResourceException missingResourceException) {
                continue;
            }
        }
        if (object == null) {
            throw new MissingResourceException("Can't find resource for bundle " + this.baseName + ", key " + string, this.getClass().getName(), string);
        }
        return object;
    }

    @Override
    public Enumeration<String> getKeys() {
        return Collections.enumeration(this.keys);
    }

    private void initKeysVector() {
        this.keys = new ArrayList<String>();
        for (ResourceBundleWrapper resourceBundleWrapper = this; resourceBundleWrapper != null; resourceBundleWrapper = (ResourceBundleWrapper)resourceBundleWrapper.getParent()) {
            Enumeration<String> enumeration = resourceBundleWrapper.bundle.getKeys();
            while (enumeration.hasMoreElements()) {
                String string = enumeration.nextElement();
                if (this.keys.contains(string)) continue;
                this.keys.add(string);
            }
        }
    }

    @Override
    protected String getLocaleID() {
        return this.localeID;
    }

    @Override
    protected String getBaseName() {
        return this.bundle.getClass().getName().replace('.', '/');
    }

    @Override
    public ULocale getULocale() {
        return new ULocale(this.localeID);
    }

    @Override
    public UResourceBundle getParent() {
        return (UResourceBundle)this.parent;
    }

    public static ResourceBundleWrapper getBundleInstance(String string, String string2, ClassLoader classLoader, boolean bl) {
        ResourceBundleWrapper resourceBundleWrapper;
        if (classLoader == null) {
            classLoader = ClassLoaderUtil.getClassLoader();
        }
        if ((resourceBundleWrapper = bl ? ResourceBundleWrapper.instantiateBundle(string, string2, null, classLoader, bl) : ResourceBundleWrapper.instantiateBundle(string, string2, ULocale.getDefault().getBaseName(), classLoader, bl)) == null) {
            String string3 = "_";
            if (string.indexOf(47) >= 0) {
                string3 = "/";
            }
            throw new MissingResourceException("Could not find the bundle " + string + string3 + string2, "", "");
        }
        return resourceBundleWrapper;
    }

    private static boolean localeIDStartsWithLangSubtag(String string, String string2) {
        return string.startsWith(string2) && (string.length() == string2.length() || string.charAt(string2.length()) == '_');
    }

    private static ResourceBundleWrapper instantiateBundle(String string, String string2, String string3, ClassLoader classLoader, boolean bl) {
        String string4 = string2.isEmpty() ? string : string + '_' + string2;
        String string5 = bl ? string4 : string4 + '#' + string3;
        return BUNDLE_CACHE.getInstance(string5, new Loader(string2, string, string3, classLoader, bl, string4){
            final String val$localeID;
            final String val$baseName;
            final String val$defaultID;
            final ClassLoader val$root;
            final boolean val$disableFallback;
            final String val$name;
            {
                this.val$localeID = string;
                this.val$baseName = string2;
                this.val$defaultID = string3;
                this.val$root = classLoader;
                this.val$disableFallback = bl;
                this.val$name = string4;
                super(null);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public ResourceBundleWrapper load() {
                Object object;
                block33: {
                    Object object2;
                    Object object3;
                    boolean bl;
                    boolean bl2;
                    ResourceBundleWrapper resourceBundleWrapper;
                    block31: {
                        resourceBundleWrapper = null;
                        int n = this.val$localeID.lastIndexOf(95);
                        bl2 = false;
                        bl = false;
                        if (n != -1) {
                            object = this.val$localeID.substring(0, n);
                            resourceBundleWrapper = ResourceBundleWrapper.access$100(this.val$baseName, (String)object, this.val$defaultID, this.val$root, this.val$disableFallback);
                        } else if (!this.val$localeID.isEmpty()) {
                            resourceBundleWrapper = ResourceBundleWrapper.access$100(this.val$baseName, "", this.val$defaultID, this.val$root, this.val$disableFallback);
                            bl = true;
                        }
                        object = null;
                        try {
                            object3 = this.val$root.loadClass(this.val$name).asSubclass(ResourceBundle.class);
                            object2 = ((Class)object3).newInstance();
                            object = new ResourceBundleWrapper((ResourceBundle)object2, null);
                            if (resourceBundleWrapper != null) {
                                ResourceBundleWrapper.access$300((ResourceBundleWrapper)object, resourceBundleWrapper);
                            }
                            ResourceBundleWrapper.access$402((ResourceBundleWrapper)object, this.val$baseName);
                            ResourceBundleWrapper.access$502((ResourceBundleWrapper)object, this.val$localeID);
                        } catch (ClassNotFoundException classNotFoundException) {
                            bl2 = true;
                        } catch (NoClassDefFoundError noClassDefFoundError) {
                            bl2 = true;
                        } catch (Exception exception) {
                            if (ResourceBundleWrapper.access$600()) {
                                System.out.println("failure");
                            }
                            if (!ResourceBundleWrapper.access$600()) break block31;
                            System.out.println(exception);
                        }
                    }
                    if (bl2) {
                        try {
                            object3 = this.val$name.replace('.', '/') + ".properties";
                            object2 = AccessController.doPrivileged(new PrivilegedAction<InputStream>(this, (String)object3){
                                final String val$resName;
                                final 2 this$0;
                                {
                                    this.this$0 = var1_1;
                                    this.val$resName = string;
                                }

                                @Override
                                public InputStream run() {
                                    return this.this$0.val$root.getResourceAsStream(this.val$resName);
                                }

                                @Override
                                public Object run() {
                                    return this.run();
                                }
                            });
                            if (object2 != null) {
                                object2 = new BufferedInputStream((InputStream)object2);
                                try {
                                    object = new ResourceBundleWrapper(new PropertyResourceBundle((InputStream)object2), null);
                                    if (resourceBundleWrapper != null) {
                                        ResourceBundleWrapper.access$700((ResourceBundleWrapper)object, resourceBundleWrapper);
                                    }
                                    ResourceBundleWrapper.access$402((ResourceBundleWrapper)object, this.val$baseName);
                                    ResourceBundleWrapper.access$502((ResourceBundleWrapper)object, this.val$localeID);
                                } catch (Exception exception) {
                                } finally {
                                    try {
                                        ((InputStream)object2).close();
                                    } catch (Exception exception) {}
                                }
                            }
                            if (!(object != null || this.val$disableFallback || this.val$localeID.isEmpty() || this.val$localeID.indexOf(95) >= 0 || ResourceBundleWrapper.access$800(this.val$defaultID, this.val$localeID))) {
                                object = ResourceBundleWrapper.access$100(this.val$baseName, this.val$defaultID, this.val$defaultID, this.val$root, this.val$disableFallback);
                            }
                            if (!(object != null || bl && this.val$disableFallback)) {
                                object = resourceBundleWrapper;
                            }
                        } catch (Exception exception) {
                            if (ResourceBundleWrapper.access$600()) {
                                System.out.println("failure");
                            }
                            if (!ResourceBundleWrapper.access$600()) break block33;
                            System.out.println(exception);
                        }
                    }
                }
                if (object != null) {
                    ResourceBundleWrapper.access$900((ResourceBundleWrapper)object);
                } else if (ResourceBundleWrapper.access$600()) {
                    System.out.println("Returning null for " + this.val$baseName + "_" + this.val$localeID);
                }
                return object;
            }
        });
    }

    static ResourceBundleWrapper access$100(String string, String string2, String string3, ClassLoader classLoader, boolean bl) {
        return ResourceBundleWrapper.instantiateBundle(string, string2, string3, classLoader, bl);
    }

    ResourceBundleWrapper(ResourceBundle resourceBundle, 1 var2_2) {
        this(resourceBundle);
    }

    static void access$300(ResourceBundleWrapper resourceBundleWrapper, ResourceBundle resourceBundle) {
        resourceBundleWrapper.setParent(resourceBundle);
    }

    static String access$402(ResourceBundleWrapper resourceBundleWrapper, String string) {
        resourceBundleWrapper.baseName = string;
        return resourceBundleWrapper.baseName;
    }

    static String access$502(ResourceBundleWrapper resourceBundleWrapper, String string) {
        resourceBundleWrapper.localeID = string;
        return resourceBundleWrapper.localeID;
    }

    static boolean access$600() {
        return DEBUG;
    }

    static void access$700(ResourceBundleWrapper resourceBundleWrapper, ResourceBundle resourceBundle) {
        resourceBundleWrapper.setParent(resourceBundle);
    }

    static boolean access$800(String string, String string2) {
        return ResourceBundleWrapper.localeIDStartsWithLangSubtag(string, string2);
    }

    static void access$900(ResourceBundleWrapper resourceBundleWrapper) {
        resourceBundleWrapper.initKeysVector();
    }

    private static abstract class Loader {
        private Loader() {
        }

        abstract ResourceBundleWrapper load();

        Loader(1 var1_1) {
            this();
        }
    }
}

