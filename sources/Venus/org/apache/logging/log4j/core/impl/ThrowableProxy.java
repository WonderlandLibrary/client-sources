/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.impl;

import java.io.Serializable;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import org.apache.logging.log4j.core.impl.ExtendedClassInfo;
import org.apache.logging.log4j.core.impl.ExtendedStackTraceElement;
import org.apache.logging.log4j.core.pattern.PlainTextRenderer;
import org.apache.logging.log4j.core.pattern.TextRenderer;
import org.apache.logging.log4j.core.util.Loader;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.LoaderUtil;
import org.apache.logging.log4j.util.ReflectionUtil;

public class ThrowableProxy
implements Serializable {
    private static final String TAB = "\t";
    private static final String CAUSED_BY_LABEL = "Caused by: ";
    private static final String SUPPRESSED_LABEL = "Suppressed: ";
    private static final String WRAPPED_BY_LABEL = "Wrapped by: ";
    private static final ThrowableProxy[] EMPTY_THROWABLE_PROXY_ARRAY = new ThrowableProxy[0];
    private static final char EOL = '\n';
    private static final String EOL_STR = String.valueOf('\n');
    private static final long serialVersionUID = -2752771578252251910L;
    private final ThrowableProxy causeProxy;
    private int commonElementCount;
    private final ExtendedStackTraceElement[] extendedStackTrace;
    private final String localizedMessage;
    private final String message;
    private final String name;
    private final ThrowableProxy[] suppressedProxies;
    private final transient Throwable throwable;

    private ThrowableProxy() {
        this.throwable = null;
        this.name = null;
        this.extendedStackTrace = null;
        this.causeProxy = null;
        this.message = null;
        this.localizedMessage = null;
        this.suppressedProxies = EMPTY_THROWABLE_PROXY_ARRAY;
    }

    public ThrowableProxy(Throwable throwable) {
        this(throwable, null);
    }

    private ThrowableProxy(Throwable throwable, Set<Throwable> set) {
        this.throwable = throwable;
        this.name = throwable.getClass().getName();
        this.message = throwable.getMessage();
        this.localizedMessage = throwable.getLocalizedMessage();
        HashMap<String, CacheEntry> hashMap = new HashMap<String, CacheEntry>();
        Stack<Class<?>> stack = ReflectionUtil.getCurrentStackTrace();
        this.extendedStackTrace = this.toExtendedStackTrace(stack, hashMap, null, throwable.getStackTrace());
        Throwable throwable2 = throwable.getCause();
        HashSet<Throwable> hashSet = new HashSet<Throwable>(1);
        this.causeProxy = throwable2 == null ? null : new ThrowableProxy(throwable, stack, hashMap, throwable2, set, hashSet);
        this.suppressedProxies = this.toSuppressedProxies(throwable, set);
    }

    private ThrowableProxy(Throwable throwable, Stack<Class<?>> stack, Map<String, CacheEntry> map, Throwable throwable2, Set<Throwable> set, Set<Throwable> set2) {
        set2.add(throwable2);
        this.throwable = throwable2;
        this.name = throwable2.getClass().getName();
        this.message = this.throwable.getMessage();
        this.localizedMessage = this.throwable.getLocalizedMessage();
        this.extendedStackTrace = this.toExtendedStackTrace(stack, map, throwable.getStackTrace(), throwable2.getStackTrace());
        Throwable throwable3 = throwable2.getCause();
        this.causeProxy = throwable3 == null || set2.contains(throwable3) ? null : new ThrowableProxy(throwable, stack, map, throwable3, set, set2);
        this.suppressedProxies = this.toSuppressedProxies(throwable2, set);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null) {
            return true;
        }
        if (this.getClass() != object.getClass()) {
            return true;
        }
        ThrowableProxy throwableProxy = (ThrowableProxy)object;
        if (this.causeProxy == null ? throwableProxy.causeProxy != null : !this.causeProxy.equals(throwableProxy.causeProxy)) {
            return true;
        }
        if (this.commonElementCount != throwableProxy.commonElementCount) {
            return true;
        }
        if (this.name == null ? throwableProxy.name != null : !this.name.equals(throwableProxy.name)) {
            return true;
        }
        if (!Arrays.equals(this.extendedStackTrace, throwableProxy.extendedStackTrace)) {
            return true;
        }
        return !Arrays.equals(this.suppressedProxies, throwableProxy.suppressedProxies);
    }

    private void formatCause(StringBuilder stringBuilder, String string, ThrowableProxy throwableProxy, List<String> list, TextRenderer textRenderer) {
        this.formatThrowableProxy(stringBuilder, string, CAUSED_BY_LABEL, throwableProxy, list, textRenderer);
    }

    private void formatThrowableProxy(StringBuilder stringBuilder, String string, String string2, ThrowableProxy throwableProxy, List<String> list, TextRenderer textRenderer) {
        if (throwableProxy == null) {
            return;
        }
        textRenderer.render(string, stringBuilder, "Prefix");
        textRenderer.render(string2, stringBuilder, "CauseLabel");
        throwableProxy.renderOn(stringBuilder, textRenderer);
        textRenderer.render(EOL_STR, stringBuilder, "Text");
        this.formatElements(stringBuilder, string, throwableProxy.commonElementCount, throwableProxy.getStackTrace(), throwableProxy.extendedStackTrace, list, textRenderer);
        this.formatSuppressed(stringBuilder, string + TAB, throwableProxy.suppressedProxies, list, textRenderer);
        this.formatCause(stringBuilder, string, throwableProxy.causeProxy, list, textRenderer);
    }

    void renderOn(StringBuilder stringBuilder, TextRenderer textRenderer) {
        String string = this.message;
        textRenderer.render(this.name, stringBuilder, "Name");
        if (string != null) {
            textRenderer.render(": ", stringBuilder, "NameMessageSeparator");
            textRenderer.render(string, stringBuilder, "Message");
        }
    }

    private void formatSuppressed(StringBuilder stringBuilder, String string, ThrowableProxy[] throwableProxyArray, List<String> list, TextRenderer textRenderer) {
        if (throwableProxyArray == null) {
            return;
        }
        for (ThrowableProxy throwableProxy : throwableProxyArray) {
            this.formatThrowableProxy(stringBuilder, string, SUPPRESSED_LABEL, throwableProxy, list, textRenderer);
        }
    }

    private void formatElements(StringBuilder stringBuilder, String string, int n, StackTraceElement[] stackTraceElementArray, ExtendedStackTraceElement[] extendedStackTraceElementArray, List<String> list, TextRenderer textRenderer) {
        if (list == null || list.isEmpty()) {
            for (ExtendedStackTraceElement extendedStackTraceElement : extendedStackTraceElementArray) {
                this.formatEntry(extendedStackTraceElement, stringBuilder, string, textRenderer);
            }
        } else {
            int n2 = 0;
            for (int i = 0; i < extendedStackTraceElementArray.length; ++i) {
                if (!this.ignoreElement(stackTraceElementArray[i], list)) {
                    if (n2 > 0) {
                        this.appendSuppressedCount(stringBuilder, string, n2, textRenderer);
                        n2 = 0;
                    }
                    this.formatEntry(extendedStackTraceElementArray[i], stringBuilder, string, textRenderer);
                    continue;
                }
                ++n2;
            }
            if (n2 > 0) {
                this.appendSuppressedCount(stringBuilder, string, n2, textRenderer);
            }
        }
        if (n != 0) {
            textRenderer.render(string, stringBuilder, "Prefix");
            textRenderer.render("\t... ", stringBuilder, "More");
            textRenderer.render(Integer.toString(n), stringBuilder, "More");
            textRenderer.render(" more", stringBuilder, "More");
            textRenderer.render(EOL_STR, stringBuilder, "Text");
        }
    }

    private void appendSuppressedCount(StringBuilder stringBuilder, String string, int n, TextRenderer textRenderer) {
        textRenderer.render(string, stringBuilder, "Prefix");
        if (n == 1) {
            textRenderer.render("\t... ", stringBuilder, "Suppressed");
        } else {
            textRenderer.render("\t... suppressed ", stringBuilder, "Suppressed");
            textRenderer.render(Integer.toString(n), stringBuilder, "Suppressed");
            textRenderer.render(" lines", stringBuilder, "Suppressed");
        }
        textRenderer.render(EOL_STR, stringBuilder, "Text");
    }

    private void formatEntry(ExtendedStackTraceElement extendedStackTraceElement, StringBuilder stringBuilder, String string, TextRenderer textRenderer) {
        textRenderer.render(string, stringBuilder, "Prefix");
        textRenderer.render("\tat ", stringBuilder, "At");
        extendedStackTraceElement.renderOn(stringBuilder, textRenderer);
        textRenderer.render(EOL_STR, stringBuilder, "Text");
    }

    public void formatWrapper(StringBuilder stringBuilder, ThrowableProxy throwableProxy) {
        this.formatWrapper(stringBuilder, throwableProxy, null, PlainTextRenderer.getInstance());
    }

    public void formatWrapper(StringBuilder stringBuilder, ThrowableProxy throwableProxy, List<String> list) {
        this.formatWrapper(stringBuilder, throwableProxy, list, PlainTextRenderer.getInstance());
    }

    public void formatWrapper(StringBuilder stringBuilder, ThrowableProxy throwableProxy, List<String> list, TextRenderer textRenderer) {
        Throwable throwable;
        Throwable throwable2 = throwable = throwableProxy.getCauseProxy() != null ? throwableProxy.getCauseProxy().getThrowable() : null;
        if (throwable != null) {
            this.formatWrapper(stringBuilder, throwableProxy.causeProxy, list, textRenderer);
            stringBuilder.append(WRAPPED_BY_LABEL);
        }
        throwableProxy.renderOn(stringBuilder, textRenderer);
        textRenderer.render(EOL_STR, stringBuilder, "Text");
        this.formatElements(stringBuilder, "", throwableProxy.commonElementCount, throwableProxy.getThrowable().getStackTrace(), throwableProxy.extendedStackTrace, list, textRenderer);
    }

    public ThrowableProxy getCauseProxy() {
        return this.causeProxy;
    }

    public String getCauseStackTraceAsString() {
        return this.getCauseStackTraceAsString(null, PlainTextRenderer.getInstance());
    }

    public String getCauseStackTraceAsString(List<String> list) {
        return this.getCauseStackTraceAsString(list, PlainTextRenderer.getInstance());
    }

    public String getCauseStackTraceAsString(List<String> list, TextRenderer textRenderer) {
        StringBuilder stringBuilder = new StringBuilder();
        if (this.causeProxy != null) {
            this.formatWrapper(stringBuilder, this.causeProxy, list, textRenderer);
            stringBuilder.append(WRAPPED_BY_LABEL);
        }
        this.renderOn(stringBuilder, textRenderer);
        textRenderer.render(EOL_STR, stringBuilder, "Text");
        this.formatElements(stringBuilder, "", 0, this.throwable.getStackTrace(), this.extendedStackTrace, list, textRenderer);
        return stringBuilder.toString();
    }

    public int getCommonElementCount() {
        return this.commonElementCount;
    }

    public ExtendedStackTraceElement[] getExtendedStackTrace() {
        return this.extendedStackTrace;
    }

    public String getExtendedStackTraceAsString() {
        return this.getExtendedStackTraceAsString(null, PlainTextRenderer.getInstance());
    }

    public String getExtendedStackTraceAsString(List<String> list) {
        return this.getExtendedStackTraceAsString(list, PlainTextRenderer.getInstance());
    }

    public String getExtendedStackTraceAsString(List<String> list, TextRenderer textRenderer) {
        StringBuilder stringBuilder = new StringBuilder(1024);
        textRenderer.render(this.name, stringBuilder, "Name");
        textRenderer.render(": ", stringBuilder, "NameMessageSeparator");
        textRenderer.render(this.message, stringBuilder, "Message");
        textRenderer.render(EOL_STR, stringBuilder, "Text");
        StackTraceElement[] stackTraceElementArray = this.throwable != null ? this.throwable.getStackTrace() : null;
        this.formatElements(stringBuilder, "", 0, stackTraceElementArray, this.extendedStackTrace, list, textRenderer);
        this.formatSuppressed(stringBuilder, TAB, this.suppressedProxies, list, textRenderer);
        this.formatCause(stringBuilder, "", this.causeProxy, list, textRenderer);
        return stringBuilder.toString();
    }

    public String getLocalizedMessage() {
        return this.localizedMessage;
    }

    public String getMessage() {
        return this.message;
    }

    public String getName() {
        return this.name;
    }

    public StackTraceElement[] getStackTrace() {
        return this.throwable == null ? null : this.throwable.getStackTrace();
    }

    public ThrowableProxy[] getSuppressedProxies() {
        return this.suppressedProxies;
    }

    public String getSuppressedStackTrace() {
        ThrowableProxy[] throwableProxyArray = this.getSuppressedProxies();
        if (throwableProxyArray == null || throwableProxyArray.length == 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder("Suppressed Stack Trace Elements:").append('\n');
        for (ThrowableProxy throwableProxy : throwableProxyArray) {
            stringBuilder.append(throwableProxy.getExtendedStackTraceAsString());
        }
        return stringBuilder.toString();
    }

    public Throwable getThrowable() {
        return this.throwable;
    }

    public int hashCode() {
        int n = 31;
        int n2 = 1;
        n2 = 31 * n2 + (this.causeProxy == null ? 0 : this.causeProxy.hashCode());
        n2 = 31 * n2 + this.commonElementCount;
        n2 = 31 * n2 + (this.extendedStackTrace == null ? 0 : Arrays.hashCode(this.extendedStackTrace));
        n2 = 31 * n2 + (this.suppressedProxies == null ? 0 : Arrays.hashCode(this.suppressedProxies));
        n2 = 31 * n2 + (this.name == null ? 0 : this.name.hashCode());
        return n2;
    }

    private boolean ignoreElement(StackTraceElement stackTraceElement, List<String> list) {
        if (list != null) {
            String string = stackTraceElement.getClassName();
            for (String string2 : list) {
                if (!string.startsWith(string2)) continue;
                return false;
            }
        }
        return true;
    }

    private Class<?> loadClass(ClassLoader classLoader, String string) {
        Class<?> clazz;
        if (classLoader != null) {
            try {
                clazz = classLoader.loadClass(string);
                if (clazz != null) {
                    return clazz;
                }
            } catch (Throwable throwable) {
                // empty catch block
            }
        }
        try {
            clazz = LoaderUtil.loadClass(string);
        } catch (ClassNotFoundException | NoClassDefFoundError throwable) {
            return this.loadClass(string);
        } catch (SecurityException securityException) {
            return null;
        }
        return clazz;
    }

    private Class<?> loadClass(String string) {
        try {
            return Loader.loadClass(string, this.getClass().getClassLoader());
        } catch (ClassNotFoundException | NoClassDefFoundError | SecurityException throwable) {
            return null;
        }
    }

    private CacheEntry toCacheEntry(StackTraceElement stackTraceElement, Class<?> clazz, boolean bl) {
        String string = "?";
        Object object = "?";
        ClassLoader classLoader = null;
        if (clazz != null) {
            Object object2;
            Object object3;
            try {
                object3 = clazz.getProtectionDomain().getCodeSource();
                if (object3 != null && (object2 = ((CodeSource)object3).getLocation()) != null) {
                    String string2 = ((URL)object2).toString().replace('\\', '/');
                    int n = string2.lastIndexOf("/");
                    if (n >= 0 && n == string2.length() - 1) {
                        n = string2.lastIndexOf("/", n - 1);
                        string = string2.substring(n + 1);
                    } else {
                        string = string2.substring(n + 1);
                    }
                }
            } catch (Exception exception) {
                // empty catch block
            }
            object3 = clazz.getPackage();
            if (object3 != null && (object2 = ((Package)object3).getImplementationVersion()) != null) {
                object = object2;
            }
            classLoader = clazz.getClassLoader();
        }
        return new CacheEntry(new ExtendedClassInfo(bl, string, (String)object), classLoader);
    }

    ExtendedStackTraceElement[] toExtendedStackTrace(Stack<Class<?>> stack, Map<String, CacheEntry> map, StackTraceElement[] stackTraceElementArray, StackTraceElement[] stackTraceElementArray2) {
        int n;
        if (stackTraceElementArray != null) {
            int n2;
            int n3 = stackTraceElementArray.length - 1;
            for (n2 = stackTraceElementArray2.length - 1; n3 >= 0 && n2 >= 0 && stackTraceElementArray[n3].equals(stackTraceElementArray2[n2]); --n3, --n2) {
            }
            this.commonElementCount = stackTraceElementArray2.length - 1 - n2;
            n = n2 + 1;
        } else {
            this.commonElementCount = 0;
            n = stackTraceElementArray2.length;
        }
        ExtendedStackTraceElement[] extendedStackTraceElementArray = new ExtendedStackTraceElement[n];
        Class<?> clazz = stack.isEmpty() ? null : stack.peek();
        ClassLoader classLoader = null;
        for (int i = n - 1; i >= 0; --i) {
            ExtendedClassInfo extendedClassInfo;
            CacheEntry cacheEntry;
            StackTraceElement stackTraceElement = stackTraceElementArray2[i];
            String string = stackTraceElement.getClassName();
            if (clazz != null && string.equals(clazz.getName())) {
                cacheEntry = this.toCacheEntry(stackTraceElement, clazz, true);
                extendedClassInfo = CacheEntry.access$000(cacheEntry);
                classLoader = CacheEntry.access$100(cacheEntry);
                stack.pop();
                clazz = stack.isEmpty() ? null : stack.peek();
            } else {
                CacheEntry cacheEntry2;
                cacheEntry = map.get(string);
                if (cacheEntry != null) {
                    cacheEntry2 = cacheEntry;
                    extendedClassInfo = CacheEntry.access$000(cacheEntry2);
                    if (CacheEntry.access$100(cacheEntry2) != null) {
                        classLoader = CacheEntry.access$100(cacheEntry2);
                    }
                } else {
                    cacheEntry2 = this.toCacheEntry(stackTraceElement, this.loadClass(classLoader, string), false);
                    extendedClassInfo = CacheEntry.access$000(cacheEntry2);
                    map.put(stackTraceElement.toString(), cacheEntry2);
                    if (CacheEntry.access$100(cacheEntry2) != null) {
                        classLoader = CacheEntry.access$100(cacheEntry2);
                    }
                }
            }
            extendedStackTraceElementArray[i] = new ExtendedStackTraceElement(stackTraceElement, extendedClassInfo);
        }
        return extendedStackTraceElementArray;
    }

    public String toString() {
        String string = this.message;
        return string != null ? this.name + ": " + string : this.name;
    }

    private ThrowableProxy[] toSuppressedProxies(Throwable throwable, Set<Throwable> set) {
        try {
            Throwable[] throwableArray = throwable.getSuppressed();
            if (throwableArray == null) {
                return EMPTY_THROWABLE_PROXY_ARRAY;
            }
            ArrayList<ThrowableProxy> arrayList = new ArrayList<ThrowableProxy>(throwableArray.length);
            if (set == null) {
                set = new HashSet<Throwable>(arrayList.size());
            }
            for (int i = 0; i < throwableArray.length; ++i) {
                Throwable throwable2 = throwableArray[i];
                if (set.contains(throwable2)) continue;
                set.add(throwable2);
                arrayList.add(new ThrowableProxy(throwable2, set));
            }
            return arrayList.toArray(new ThrowableProxy[arrayList.size()]);
        } catch (Exception exception) {
            StatusLogger.getLogger().error(exception);
            return null;
        }
    }

    static class CacheEntry {
        private final ExtendedClassInfo element;
        private final ClassLoader loader;

        public CacheEntry(ExtendedClassInfo extendedClassInfo, ClassLoader classLoader) {
            this.element = extendedClassInfo;
            this.loader = classLoader;
        }

        static ExtendedClassInfo access$000(CacheEntry cacheEntry) {
            return cacheEntry.element;
        }

        static ClassLoader access$100(CacheEntry cacheEntry) {
            return cacheEntry.loader;
        }
    }
}

