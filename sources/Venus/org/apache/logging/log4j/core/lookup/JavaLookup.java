/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.lookup;

import java.util.Locale;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.lookup.AbstractLookup;
import org.apache.logging.log4j.core.lookup.SystemPropertiesLookup;
import org.apache.logging.log4j.util.Strings;

@Plugin(name="java", category="Lookup")
public class JavaLookup
extends AbstractLookup {
    private final SystemPropertiesLookup spLookup = new SystemPropertiesLookup();

    public String getHardware() {
        return "processors: " + Runtime.getRuntime().availableProcessors() + ", architecture: " + this.getSystemProperty("os.arch") + this.getSystemProperty("-", "sun.arch.data.model") + this.getSystemProperty(", instruction sets: ", "sun.cpu.isalist");
    }

    public String getLocale() {
        return "default locale: " + Locale.getDefault() + ", platform encoding: " + this.getSystemProperty("file.encoding");
    }

    public String getOperatingSystem() {
        return this.getSystemProperty("os.name") + " " + this.getSystemProperty("os.version") + this.getSystemProperty(" ", "sun.os.patch.level") + ", architecture: " + this.getSystemProperty("os.arch") + this.getSystemProperty("-", "sun.arch.data.model");
    }

    public String getRuntime() {
        return this.getSystemProperty("java.runtime.name") + " (build " + this.getSystemProperty("java.runtime.version") + ") from " + this.getSystemProperty("java.vendor");
    }

    private String getSystemProperty(String string) {
        return this.spLookup.lookup(string);
    }

    private String getSystemProperty(String string, String string2) {
        String string3 = this.getSystemProperty(string2);
        if (Strings.isEmpty(string3)) {
            return "";
        }
        return string + string3;
    }

    public String getVirtualMachine() {
        return this.getSystemProperty("java.vm.name") + " (build " + this.getSystemProperty("java.vm.version") + ", " + this.getSystemProperty("java.vm.info") + ")";
    }

    @Override
    public String lookup(LogEvent logEvent, String string) {
        switch (string) {
            case "version": {
                return "Java version " + this.getSystemProperty("java.version");
            }
            case "runtime": {
                return this.getRuntime();
            }
            case "vm": {
                return this.getVirtualMachine();
            }
            case "os": {
                return this.getOperatingSystem();
            }
            case "hw": {
                return this.getHardware();
            }
            case "locale": {
                return this.getLocale();
            }
        }
        throw new IllegalArgumentException(string);
    }
}

