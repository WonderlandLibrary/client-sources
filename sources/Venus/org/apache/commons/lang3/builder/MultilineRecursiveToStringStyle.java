/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3.builder;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.builder.RecursiveToStringStyle;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class MultilineRecursiveToStringStyle
extends RecursiveToStringStyle {
    private static final long serialVersionUID = 1L;
    private int indent = 2;
    private int spaces = 2;

    public MultilineRecursiveToStringStyle() {
        this.resetIndent();
    }

    private void resetIndent() {
        this.setArrayStart("{" + SystemUtils.LINE_SEPARATOR + this.spacer(this.spaces));
        this.setArraySeparator("," + SystemUtils.LINE_SEPARATOR + this.spacer(this.spaces));
        this.setArrayEnd(SystemUtils.LINE_SEPARATOR + this.spacer(this.spaces - this.indent) + "}");
        this.setContentStart("[" + SystemUtils.LINE_SEPARATOR + this.spacer(this.spaces));
        this.setFieldSeparator("," + SystemUtils.LINE_SEPARATOR + this.spacer(this.spaces));
        this.setContentEnd(SystemUtils.LINE_SEPARATOR + this.spacer(this.spaces - this.indent) + "]");
    }

    private StringBuilder spacer(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < n; ++i) {
            stringBuilder.append(" ");
        }
        return stringBuilder;
    }

    @Override
    public void appendDetail(StringBuffer stringBuffer, String string, Object object) {
        if (!ClassUtils.isPrimitiveWrapper(object.getClass()) && !String.class.equals(object.getClass()) && this.accept(object.getClass())) {
            this.spaces += this.indent;
            this.resetIndent();
            stringBuffer.append(ReflectionToStringBuilder.toString(object, this));
            this.spaces -= this.indent;
            this.resetIndent();
        } else {
            super.appendDetail(stringBuffer, string, object);
        }
    }

    @Override
    protected void appendDetail(StringBuffer stringBuffer, String string, Object[] objectArray) {
        this.spaces += this.indent;
        this.resetIndent();
        super.appendDetail(stringBuffer, string, objectArray);
        this.spaces -= this.indent;
        this.resetIndent();
    }

    @Override
    protected void reflectionAppendArrayDetail(StringBuffer stringBuffer, String string, Object object) {
        this.spaces += this.indent;
        this.resetIndent();
        super.appendDetail(stringBuffer, string, object);
        this.spaces -= this.indent;
        this.resetIndent();
    }

    @Override
    protected void appendDetail(StringBuffer stringBuffer, String string, long[] lArray) {
        this.spaces += this.indent;
        this.resetIndent();
        super.appendDetail(stringBuffer, string, lArray);
        this.spaces -= this.indent;
        this.resetIndent();
    }

    @Override
    protected void appendDetail(StringBuffer stringBuffer, String string, int[] nArray) {
        this.spaces += this.indent;
        this.resetIndent();
        super.appendDetail(stringBuffer, string, nArray);
        this.spaces -= this.indent;
        this.resetIndent();
    }

    @Override
    protected void appendDetail(StringBuffer stringBuffer, String string, short[] sArray) {
        this.spaces += this.indent;
        this.resetIndent();
        super.appendDetail(stringBuffer, string, sArray);
        this.spaces -= this.indent;
        this.resetIndent();
    }

    @Override
    protected void appendDetail(StringBuffer stringBuffer, String string, byte[] byArray) {
        this.spaces += this.indent;
        this.resetIndent();
        super.appendDetail(stringBuffer, string, byArray);
        this.spaces -= this.indent;
        this.resetIndent();
    }

    @Override
    protected void appendDetail(StringBuffer stringBuffer, String string, char[] cArray) {
        this.spaces += this.indent;
        this.resetIndent();
        super.appendDetail(stringBuffer, string, cArray);
        this.spaces -= this.indent;
        this.resetIndent();
    }

    @Override
    protected void appendDetail(StringBuffer stringBuffer, String string, double[] dArray) {
        this.spaces += this.indent;
        this.resetIndent();
        super.appendDetail(stringBuffer, string, dArray);
        this.spaces -= this.indent;
        this.resetIndent();
    }

    @Override
    protected void appendDetail(StringBuffer stringBuffer, String string, float[] fArray) {
        this.spaces += this.indent;
        this.resetIndent();
        super.appendDetail(stringBuffer, string, fArray);
        this.spaces -= this.indent;
        this.resetIndent();
    }

    @Override
    protected void appendDetail(StringBuffer stringBuffer, String string, boolean[] blArray) {
        this.spaces += this.indent;
        this.resetIndent();
        super.appendDetail(stringBuffer, string, blArray);
        this.spaces -= this.indent;
        this.resetIndent();
    }
}

