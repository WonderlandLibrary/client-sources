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

    public ExtendedStackTraceElement(StackTraceElement stackTraceElement, ExtendedClassInfo extraClassInfo) {
        this.stackTraceElement = stackTraceElement;
        this.extraClassInfo = extraClassInfo;
    }

    public ExtendedStackTraceElement(String declaringClass, String methodName, String fileName, int lineNumber, boolean exact, String location, String version) {
        this(new StackTraceElement(declaringClass, methodName, fileName, lineNumber), new ExtendedClassInfo(exact, location, version));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof ExtendedStackTraceElement)) {
            return false;
        }
        ExtendedStackTraceElement other = (ExtendedStackTraceElement)obj;
        if (this.extraClassInfo == null ? other.extraClassInfo != null : !this.extraClassInfo.equals(other.extraClassInfo)) {
            return false;
        }
        return !(this.stackTraceElement == null ? other.stackTraceElement != null : !this.stackTraceElement.equals(other.stackTraceElement));
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
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.extraClassInfo == null ? 0 : this.extraClassInfo.hashCode());
        result = 31 * result + (this.stackTraceElement == null ? 0 : this.stackTraceElement.hashCode());
        return result;
    }

    public boolean isNativeMethod() {
        return this.stackTraceElement.isNativeMethod();
    }

    void renderOn(StringBuilder output, TextRenderer textRenderer) {
        this.render(this.stackTraceElement, output, textRenderer);
        textRenderer.render(" ", output, "Text");
        this.extraClassInfo.renderOn(output, textRenderer);
    }

    private void render(StackTraceElement stElement, StringBuilder output, TextRenderer textRenderer) {
        String fileName = stElement.getFileName();
        int lineNumber = stElement.getLineNumber();
        textRenderer.render(this.getClassName(), output, "StackTraceElement.ClassName");
        textRenderer.render(".", output, "StackTraceElement.ClassMethodSeparator");
        textRenderer.render(stElement.getMethodName(), output, "StackTraceElement.MethodName");
        if (stElement.isNativeMethod()) {
            textRenderer.render("(Native Method)", output, "StackTraceElement.NativeMethod");
        } else if (fileName != null && lineNumber >= 0) {
            textRenderer.render("(", output, "StackTraceElement.Container");
            textRenderer.render(fileName, output, "StackTraceElement.FileName");
            textRenderer.render(":", output, "StackTraceElement.ContainerSeparator");
            textRenderer.render(Integer.toString(lineNumber), output, "StackTraceElement.LineNumber");
            textRenderer.render(")", output, "StackTraceElement.Container");
        } else if (fileName != null) {
            textRenderer.render("(", output, "StackTraceElement.Container");
            textRenderer.render(fileName, output, "StackTraceElement.FileName");
            textRenderer.render(")", output, "StackTraceElement.Container");
        } else {
            textRenderer.render("(", output, "StackTraceElement.Container");
            textRenderer.render("Unknown Source", output, "StackTraceElement.UnknownSource");
            textRenderer.render(")", output, "StackTraceElement.Container");
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        this.renderOn(sb, PlainTextRenderer.getInstance());
        return sb.toString();
    }
}

