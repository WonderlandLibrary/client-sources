/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3.builder;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.builder.Builder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ToStringBuilder
implements Builder<String> {
    private static volatile ToStringStyle defaultStyle = ToStringStyle.DEFAULT_STYLE;
    private final StringBuffer buffer;
    private final Object object;
    private final ToStringStyle style;

    public static ToStringStyle getDefaultStyle() {
        return defaultStyle;
    }

    public static void setDefaultStyle(ToStringStyle toStringStyle) {
        if (toStringStyle == null) {
            throw new IllegalArgumentException("The style must not be null");
        }
        defaultStyle = toStringStyle;
    }

    public static String reflectionToString(Object object) {
        return ReflectionToStringBuilder.toString(object);
    }

    public static String reflectionToString(Object object, ToStringStyle toStringStyle) {
        return ReflectionToStringBuilder.toString(object, toStringStyle);
    }

    public static String reflectionToString(Object object, ToStringStyle toStringStyle, boolean bl) {
        return ReflectionToStringBuilder.toString(object, toStringStyle, bl, false, null);
    }

    public static <T> String reflectionToString(T t, ToStringStyle toStringStyle, boolean bl, Class<? super T> clazz) {
        return ReflectionToStringBuilder.toString(t, toStringStyle, bl, false, clazz);
    }

    public ToStringBuilder(Object object) {
        this(object, null, null);
    }

    public ToStringBuilder(Object object, ToStringStyle toStringStyle) {
        this(object, toStringStyle, null);
    }

    public ToStringBuilder(Object object, ToStringStyle toStringStyle, StringBuffer stringBuffer) {
        if (toStringStyle == null) {
            toStringStyle = ToStringBuilder.getDefaultStyle();
        }
        if (stringBuffer == null) {
            stringBuffer = new StringBuffer(512);
        }
        this.buffer = stringBuffer;
        this.style = toStringStyle;
        this.object = object;
        toStringStyle.appendStart(stringBuffer, object);
    }

    public ToStringBuilder append(boolean bl) {
        this.style.append(this.buffer, null, bl);
        return this;
    }

    public ToStringBuilder append(boolean[] blArray) {
        this.style.append(this.buffer, (String)null, blArray, (Boolean)null);
        return this;
    }

    public ToStringBuilder append(byte by) {
        this.style.append(this.buffer, (String)null, by);
        return this;
    }

    public ToStringBuilder append(byte[] byArray) {
        this.style.append(this.buffer, (String)null, byArray, (Boolean)null);
        return this;
    }

    public ToStringBuilder append(char c) {
        this.style.append(this.buffer, (String)null, c);
        return this;
    }

    public ToStringBuilder append(char[] cArray) {
        this.style.append(this.buffer, (String)null, cArray, (Boolean)null);
        return this;
    }

    public ToStringBuilder append(double d) {
        this.style.append(this.buffer, null, d);
        return this;
    }

    public ToStringBuilder append(double[] dArray) {
        this.style.append(this.buffer, (String)null, dArray, (Boolean)null);
        return this;
    }

    public ToStringBuilder append(float f) {
        this.style.append(this.buffer, (String)null, f);
        return this;
    }

    public ToStringBuilder append(float[] fArray) {
        this.style.append(this.buffer, (String)null, fArray, (Boolean)null);
        return this;
    }

    public ToStringBuilder append(int n) {
        this.style.append(this.buffer, (String)null, n);
        return this;
    }

    public ToStringBuilder append(int[] nArray) {
        this.style.append(this.buffer, (String)null, nArray, (Boolean)null);
        return this;
    }

    public ToStringBuilder append(long l) {
        this.style.append(this.buffer, (String)null, l);
        return this;
    }

    public ToStringBuilder append(long[] lArray) {
        this.style.append(this.buffer, (String)null, lArray, (Boolean)null);
        return this;
    }

    public ToStringBuilder append(Object object) {
        this.style.append(this.buffer, null, object, null);
        return this;
    }

    public ToStringBuilder append(Object[] objectArray) {
        this.style.append(this.buffer, (String)null, objectArray, (Boolean)null);
        return this;
    }

    public ToStringBuilder append(short s) {
        this.style.append(this.buffer, (String)null, s);
        return this;
    }

    public ToStringBuilder append(short[] sArray) {
        this.style.append(this.buffer, (String)null, sArray, (Boolean)null);
        return this;
    }

    public ToStringBuilder append(String string, boolean bl) {
        this.style.append(this.buffer, string, bl);
        return this;
    }

    public ToStringBuilder append(String string, boolean[] blArray) {
        this.style.append(this.buffer, string, blArray, (Boolean)null);
        return this;
    }

    public ToStringBuilder append(String string, boolean[] blArray, boolean bl) {
        this.style.append(this.buffer, string, blArray, (Boolean)bl);
        return this;
    }

    public ToStringBuilder append(String string, byte by) {
        this.style.append(this.buffer, string, by);
        return this;
    }

    public ToStringBuilder append(String string, byte[] byArray) {
        this.style.append(this.buffer, string, byArray, (Boolean)null);
        return this;
    }

    public ToStringBuilder append(String string, byte[] byArray, boolean bl) {
        this.style.append(this.buffer, string, byArray, (Boolean)bl);
        return this;
    }

    public ToStringBuilder append(String string, char c) {
        this.style.append(this.buffer, string, c);
        return this;
    }

    public ToStringBuilder append(String string, char[] cArray) {
        this.style.append(this.buffer, string, cArray, (Boolean)null);
        return this;
    }

    public ToStringBuilder append(String string, char[] cArray, boolean bl) {
        this.style.append(this.buffer, string, cArray, (Boolean)bl);
        return this;
    }

    public ToStringBuilder append(String string, double d) {
        this.style.append(this.buffer, string, d);
        return this;
    }

    public ToStringBuilder append(String string, double[] dArray) {
        this.style.append(this.buffer, string, dArray, (Boolean)null);
        return this;
    }

    public ToStringBuilder append(String string, double[] dArray, boolean bl) {
        this.style.append(this.buffer, string, dArray, (Boolean)bl);
        return this;
    }

    public ToStringBuilder append(String string, float f) {
        this.style.append(this.buffer, string, f);
        return this;
    }

    public ToStringBuilder append(String string, float[] fArray) {
        this.style.append(this.buffer, string, fArray, (Boolean)null);
        return this;
    }

    public ToStringBuilder append(String string, float[] fArray, boolean bl) {
        this.style.append(this.buffer, string, fArray, (Boolean)bl);
        return this;
    }

    public ToStringBuilder append(String string, int n) {
        this.style.append(this.buffer, string, n);
        return this;
    }

    public ToStringBuilder append(String string, int[] nArray) {
        this.style.append(this.buffer, string, nArray, (Boolean)null);
        return this;
    }

    public ToStringBuilder append(String string, int[] nArray, boolean bl) {
        this.style.append(this.buffer, string, nArray, (Boolean)bl);
        return this;
    }

    public ToStringBuilder append(String string, long l) {
        this.style.append(this.buffer, string, l);
        return this;
    }

    public ToStringBuilder append(String string, long[] lArray) {
        this.style.append(this.buffer, string, lArray, (Boolean)null);
        return this;
    }

    public ToStringBuilder append(String string, long[] lArray, boolean bl) {
        this.style.append(this.buffer, string, lArray, (Boolean)bl);
        return this;
    }

    public ToStringBuilder append(String string, Object object) {
        this.style.append(this.buffer, string, object, null);
        return this;
    }

    public ToStringBuilder append(String string, Object object, boolean bl) {
        this.style.append(this.buffer, string, object, (Boolean)bl);
        return this;
    }

    public ToStringBuilder append(String string, Object[] objectArray) {
        this.style.append(this.buffer, string, objectArray, (Boolean)null);
        return this;
    }

    public ToStringBuilder append(String string, Object[] objectArray, boolean bl) {
        this.style.append(this.buffer, string, objectArray, (Boolean)bl);
        return this;
    }

    public ToStringBuilder append(String string, short s) {
        this.style.append(this.buffer, string, s);
        return this;
    }

    public ToStringBuilder append(String string, short[] sArray) {
        this.style.append(this.buffer, string, sArray, (Boolean)null);
        return this;
    }

    public ToStringBuilder append(String string, short[] sArray, boolean bl) {
        this.style.append(this.buffer, string, sArray, (Boolean)bl);
        return this;
    }

    public ToStringBuilder appendAsObjectToString(Object object) {
        ObjectUtils.identityToString(this.getStringBuffer(), object);
        return this;
    }

    public ToStringBuilder appendSuper(String string) {
        if (string != null) {
            this.style.appendSuper(this.buffer, string);
        }
        return this;
    }

    public ToStringBuilder appendToString(String string) {
        if (string != null) {
            this.style.appendToString(this.buffer, string);
        }
        return this;
    }

    public Object getObject() {
        return this.object;
    }

    public StringBuffer getStringBuffer() {
        return this.buffer;
    }

    public ToStringStyle getStyle() {
        return this.style;
    }

    public String toString() {
        if (this.getObject() == null) {
            this.getStringBuffer().append(this.getStyle().getNullText());
        } else {
            this.style.appendEnd(this.getStringBuffer(), this.getObject());
        }
        return this.getStringBuffer().toString();
    }

    @Override
    public String build() {
        return this.toString();
    }

    @Override
    public Object build() {
        return this.build();
    }
}

