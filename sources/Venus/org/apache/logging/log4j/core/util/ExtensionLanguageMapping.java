/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.util;

import java.util.ArrayList;
import java.util.List;

public enum ExtensionLanguageMapping {
    JS("js", "JavaScript"),
    JAVASCRIPT("javascript", "JavaScript"),
    GVY("gvy", "Groovy"),
    GROOVY("groovy", "Groovy"),
    BSH("bsh", "beanshell"),
    BEANSHELL("beanshell", "beanshell"),
    JY("jy", "jython"),
    JYTHON("jython", "jython"),
    FTL("ftl", "freemarker"),
    FREEMARKER("freemarker", "freemarker"),
    VM("vm", "velocity"),
    VELOCITY("velocity", "velocity"),
    AWK("awk", "awk"),
    EJS("ejs", "ejs"),
    TCL("tcl", "tcl"),
    HS("hs", "jaskell"),
    JELLY("jelly", "jelly"),
    JEP("jep", "jep"),
    JEXL("jexl", "jexl"),
    JEXL2("jexl2", "jexl2"),
    RB("rb", "ruby"),
    RUBY("ruby", "ruby"),
    JUDO("judo", "judo"),
    JUDI("judi", "judo"),
    SCALA("scala", "scala"),
    CLJ("clj", "Clojure");

    private final String extension;
    private final String language;

    private ExtensionLanguageMapping(String string2, String string3) {
        this.extension = string2;
        this.language = string3;
    }

    public String getExtension() {
        return this.extension;
    }

    public String getLanguage() {
        return this.language;
    }

    public static ExtensionLanguageMapping getByExtension(String string) {
        for (ExtensionLanguageMapping extensionLanguageMapping : ExtensionLanguageMapping.values()) {
            if (!extensionLanguageMapping.extension.equals(string)) continue;
            return extensionLanguageMapping;
        }
        return null;
    }

    public static List<ExtensionLanguageMapping> getByLanguage(String string) {
        ArrayList<ExtensionLanguageMapping> arrayList = new ArrayList<ExtensionLanguageMapping>();
        for (ExtensionLanguageMapping extensionLanguageMapping : ExtensionLanguageMapping.values()) {
            if (!extensionLanguageMapping.language.equals(string)) continue;
            arrayList.add(extensionLanguageMapping);
        }
        return arrayList;
    }
}

