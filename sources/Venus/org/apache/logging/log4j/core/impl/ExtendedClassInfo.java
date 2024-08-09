/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.impl;

import java.io.Serializable;
import org.apache.logging.log4j.core.pattern.PlainTextRenderer;
import org.apache.logging.log4j.core.pattern.TextRenderer;

public final class ExtendedClassInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    private final boolean exact;
    private final String location;
    private final String version;

    public ExtendedClassInfo(boolean bl, String string, String string2) {
        this.exact = bl;
        this.location = string;
        this.version = string2;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null) {
            return true;
        }
        if (!(object instanceof ExtendedClassInfo)) {
            return true;
        }
        ExtendedClassInfo extendedClassInfo = (ExtendedClassInfo)object;
        if (this.exact != extendedClassInfo.exact) {
            return true;
        }
        if (this.location == null ? extendedClassInfo.location != null : !this.location.equals(extendedClassInfo.location)) {
            return true;
        }
        return this.version == null ? extendedClassInfo.version != null : !this.version.equals(extendedClassInfo.version);
    }

    public boolean getExact() {
        return this.exact;
    }

    public String getLocation() {
        return this.location;
    }

    public String getVersion() {
        return this.version;
    }

    public int hashCode() {
        int n = 31;
        int n2 = 1;
        n2 = 31 * n2 + (this.exact ? 1231 : 1237);
        n2 = 31 * n2 + (this.location == null ? 0 : this.location.hashCode());
        n2 = 31 * n2 + (this.version == null ? 0 : this.version.hashCode());
        return n2;
    }

    public void renderOn(StringBuilder stringBuilder, TextRenderer textRenderer) {
        if (!this.exact) {
            textRenderer.render("~", stringBuilder, "ExtraClassInfo.Inexact");
        }
        textRenderer.render("[", stringBuilder, "ExtraClassInfo.Container");
        textRenderer.render(this.location, stringBuilder, "ExtraClassInfo.Location");
        textRenderer.render(":", stringBuilder, "ExtraClassInfo.ContainerSeparator");
        textRenderer.render(this.version, stringBuilder, "ExtraClassInfo.Version");
        textRenderer.render("]", stringBuilder, "ExtraClassInfo.Container");
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        this.renderOn(stringBuilder, PlainTextRenderer.getInstance());
        return stringBuilder.toString();
    }
}

