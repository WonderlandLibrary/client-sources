/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.impl;

import java.io.Serializable;
import org.apache.logging.log4j.core.impl.ExtendedClassInfo;
import org.apache.logging.log4j.core.pattern.PlainTextRenderer;
import org.apache.logging.log4j.core.pattern.TextRenderer;

public final class ExtendedStackTraceElement
implements Serializable {
    private static final long serialVersionUID = -2171069569241280505L;
    private final ExtendedClassInfo extraClassInfo;
    private final StackTraceElement stackTraceElement;

    public ExtendedStackTraceElement(StackTraceElement stackTraceElement, ExtendedClassInfo extendedClassInfo) {
        this.stackTraceElement = stackTraceElement;
        this.extraClassInfo = extendedClassInfo;
    }

    public ExtendedStackTraceElement(String string, String string2, String string3, int n, boolean bl, String string4, String string5) {
        this(new StackTraceElement(string, string2, string3, n), new ExtendedClassInfo(bl, string4, string5));
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null) {
            return true;
        }
        if (!(object instanceof ExtendedStackTraceElement)) {
            return true;
        }
        ExtendedStackTraceElement extendedStackTraceElement = (ExtendedStackTraceElement)object;
        if (this.extraClassInfo == null ? extendedStackTraceElement.extraClassInfo != null : !this.extraClassInfo.equals(extendedStackTraceElement.extraClassInfo)) {
            return true;
        }
        return this.stackTraceElement == null ? extendedStackTraceElement.stackTraceElement != null : !this.stackTraceElement.equals(extendedStackTraceElement.stackTraceElement);
    }

    public String getClassName() {
        return this.stackTraceElement.getClassName();
    }

    public boolean getExact() {
        return this.extraClassInfo.getExact();
    }

    public ExtendedClassInfo getExtraClassInfo() {
        return this.extraClassInfo;
    }

    public String getFileName() {
        return this.stackTraceElement.getFileName();
    }

    public int getLineNumber() {
        return this.stackTraceElement.getLineNumber();
    }

    public String getLocation() {
        return this.extraClassInfo.getLocation();
    }

    public String getMethodName() {
        return this.stackTraceElement.getMethodName();
    }

    public StackTraceElement getStackTraceElement() {
        return this.stackTraceElement;
    }

    public String getVersion() {
        return this.extraClassInfo.getVersion();
    }

    public int hashCode() {
        int n = 31;
        int n2 = 1;
        n2 = 31 * n2 + (this.extraClassInfo == null ? 0 : this.extraClassInfo.hashCode());
        n2 = 31 * n2 + (this.stackTraceElement == null ? 0 : this.stackTraceElement.hashCode());
        return n2;
    }

    public boolean isNativeMethod() {
        return this.stackTraceElement.isNativeMethod();
    }

    void renderOn(StringBuilder stringBuilder, TextRenderer textRenderer) {
        this.render(this.stackTraceElement, stringBuilder, textRenderer);
        textRenderer.render(" ", stringBuilder, "Text");
        this.extraClassInfo.renderOn(stringBuilder, textRenderer);
    }

    private void render(StackTraceElement stackTraceElement, StringBuilder stringBuilder, TextRenderer textRenderer) {
        String string = stackTraceElement.getFileName();
        int n = stackTraceElement.getLineNumber();
        textRenderer.render(this.getClassName(), stringBuilder, "StackTraceElement.ClassName");
        textRenderer.render(".", stringBuilder, "StackTraceElement.ClassMethodSeparator");
        textRenderer.render(stackTraceElement.getMethodName(), stringBuilder, "StackTraceElement.MethodName");
        if (stackTraceElement.isNativeMethod()) {
            textRenderer.render("(Native Method)", stringBuilder, "StackTraceElement.NativeMethod");
        } else if (string != null && n >= 0) {
            textRenderer.render("(", stringBuilder, "StackTraceElement.Container");
            textRenderer.render(string, stringBuilder, "StackTraceElement.FileName");
            textRenderer.render(":", stringBuilder, "StackTraceElement.ContainerSeparator");
            textRenderer.render(Integer.toString(n), stringBuilder, "StackTraceElement.LineNumber");
            textRenderer.render(")", stringBuilder, "StackTraceElement.Container");
        } else if (string != null) {
            textRenderer.render("(", stringBuilder, "StackTraceElement.Container");
            textRenderer.render(string, stringBuilder, "StackTraceElement.FileName");
            textRenderer.render(")", stringBuilder, "StackTraceElement.Container");
        } else {
            textRenderer.render("(", stringBuilder, "StackTraceElement.Container");
            textRenderer.render("Unknown Source", stringBuilder, "StackTraceElement.UnknownSource");
            textRenderer.render(")", stringBuilder, "StackTraceElement.Container");
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        this.renderOn(stringBuilder, PlainTextRenderer.getInstance());
        return stringBuilder.toString();
    }
}

