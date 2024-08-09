/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3.builder;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.WeakHashMap;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.SystemUtils;

public abstract class ToStringStyle
implements Serializable {
    private static final long serialVersionUID = -2587890625525655916L;
    public static final ToStringStyle DEFAULT_STYLE = new DefaultToStringStyle();
    public static final ToStringStyle MULTI_LINE_STYLE = new MultiLineToStringStyle();
    public static final ToStringStyle NO_FIELD_NAMES_STYLE = new NoFieldNameToStringStyle();
    public static final ToStringStyle SHORT_PREFIX_STYLE = new ShortPrefixToStringStyle();
    public static final ToStringStyle SIMPLE_STYLE = new SimpleToStringStyle();
    public static final ToStringStyle NO_CLASS_NAME_STYLE = new NoClassNameToStringStyle();
    public static final ToStringStyle JSON_STYLE = new JsonToStringStyle();
    private static final ThreadLocal<WeakHashMap<Object, Object>> REGISTRY = new ThreadLocal();
    private boolean useFieldNames = true;
    private boolean useClassName = true;
    private boolean useShortClassName = false;
    private boolean useIdentityHashCode = true;
    private String contentStart = "[";
    private String contentEnd = "]";
    private String fieldNameValueSeparator = "=";
    private boolean fieldSeparatorAtStart = false;
    private boolean fieldSeparatorAtEnd = false;
    private String fieldSeparator = ",";
    private String arrayStart = "{";
    private String arraySeparator = ",";
    private boolean arrayContentDetail = true;
    private String arrayEnd = "}";
    private boolean defaultFullDetail = true;
    private String nullText = "<null>";
    private String sizeStartText = "<size=";
    private String sizeEndText = ">";
    private String summaryObjectStartText = "<";
    private String summaryObjectEndText = ">";

    static Map<Object, Object> getRegistry() {
        return REGISTRY.get();
    }

    static boolean isRegistered(Object object) {
        Map<Object, Object> map = ToStringStyle.getRegistry();
        return map != null && map.containsKey(object);
    }

    static void register(Object object) {
        if (object != null) {
            Map<Object, Object> map = ToStringStyle.getRegistry();
            if (map == null) {
                REGISTRY.set(new WeakHashMap());
            }
            ToStringStyle.getRegistry().put(object, null);
        }
    }

    static void unregister(Object object) {
        Map<Object, Object> map;
        if (object != null && (map = ToStringStyle.getRegistry()) != null) {
            map.remove(object);
            if (map.isEmpty()) {
                REGISTRY.remove();
            }
        }
    }

    protected ToStringStyle() {
    }

    public void appendSuper(StringBuffer stringBuffer, String string) {
        this.appendToString(stringBuffer, string);
    }

    public void appendToString(StringBuffer stringBuffer, String string) {
        int n;
        int n2;
        if (string != null && (n2 = string.indexOf(this.contentStart) + this.contentStart.length()) != (n = string.lastIndexOf(this.contentEnd)) && n2 >= 0 && n >= 0) {
            String string2 = string.substring(n2, n);
            if (this.fieldSeparatorAtStart) {
                this.removeLastFieldSeparator(stringBuffer);
            }
            stringBuffer.append(string2);
            this.appendFieldSeparator(stringBuffer);
        }
    }

    public void appendStart(StringBuffer stringBuffer, Object object) {
        if (object != null) {
            this.appendClassName(stringBuffer, object);
            this.appendIdentityHashCode(stringBuffer, object);
            this.appendContentStart(stringBuffer);
            if (this.fieldSeparatorAtStart) {
                this.appendFieldSeparator(stringBuffer);
            }
        }
    }

    public void appendEnd(StringBuffer stringBuffer, Object object) {
        if (!this.fieldSeparatorAtEnd) {
            this.removeLastFieldSeparator(stringBuffer);
        }
        this.appendContentEnd(stringBuffer);
        ToStringStyle.unregister(object);
    }

    protected void removeLastFieldSeparator(StringBuffer stringBuffer) {
        int n = stringBuffer.length();
        int n2 = this.fieldSeparator.length();
        if (n > 0 && n2 > 0 && n >= n2) {
            boolean bl = true;
            for (int i = 0; i < n2; ++i) {
                if (stringBuffer.charAt(n - 1 - i) == this.fieldSeparator.charAt(n2 - 1 - i)) continue;
                bl = false;
                break;
            }
            if (bl) {
                stringBuffer.setLength(n - n2);
            }
        }
    }

    public void append(StringBuffer stringBuffer, String string, Object object, Boolean bl) {
        this.appendFieldStart(stringBuffer, string);
        if (object == null) {
            this.appendNullText(stringBuffer, string);
        } else {
            this.appendInternal(stringBuffer, string, object, this.isFullDetail(bl));
        }
        this.appendFieldEnd(stringBuffer, string);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void appendInternal(StringBuffer stringBuffer, String string, Object object, boolean bl) {
        if (ToStringStyle.isRegistered(object) && !(object instanceof Number) && !(object instanceof Boolean) && !(object instanceof Character)) {
            this.appendCyclicObject(stringBuffer, string, object);
            return;
        }
        ToStringStyle.register(object);
        try {
            if (object instanceof Collection) {
                if (bl) {
                    this.appendDetail(stringBuffer, string, (Collection)object);
                } else {
                    this.appendSummarySize(stringBuffer, string, ((Collection)object).size());
                }
            } else if (object instanceof Map) {
                if (bl) {
                    this.appendDetail(stringBuffer, string, (Map)object);
                } else {
                    this.appendSummarySize(stringBuffer, string, ((Map)object).size());
                }
            } else if (object instanceof long[]) {
                if (bl) {
                    this.appendDetail(stringBuffer, string, (long[])object);
                } else {
                    this.appendSummary(stringBuffer, string, (long[])object);
                }
            } else if (object instanceof int[]) {
                if (bl) {
                    this.appendDetail(stringBuffer, string, (int[])object);
                } else {
                    this.appendSummary(stringBuffer, string, (int[])object);
                }
            } else if (object instanceof short[]) {
                if (bl) {
                    this.appendDetail(stringBuffer, string, (short[])object);
                } else {
                    this.appendSummary(stringBuffer, string, (short[])object);
                }
            } else if (object instanceof byte[]) {
                if (bl) {
                    this.appendDetail(stringBuffer, string, (byte[])object);
                } else {
                    this.appendSummary(stringBuffer, string, (byte[])object);
                }
            } else if (object instanceof char[]) {
                if (bl) {
                    this.appendDetail(stringBuffer, string, (char[])object);
                } else {
                    this.appendSummary(stringBuffer, string, (char[])object);
                }
            } else if (object instanceof double[]) {
                if (bl) {
                    this.appendDetail(stringBuffer, string, (double[])object);
                } else {
                    this.appendSummary(stringBuffer, string, (double[])object);
                }
            } else if (object instanceof float[]) {
                if (bl) {
                    this.appendDetail(stringBuffer, string, (float[])object);
                } else {
                    this.appendSummary(stringBuffer, string, (float[])object);
                }
            } else if (object instanceof boolean[]) {
                if (bl) {
                    this.appendDetail(stringBuffer, string, (boolean[])object);
                } else {
                    this.appendSummary(stringBuffer, string, (boolean[])object);
                }
            } else if (object.getClass().isArray()) {
                if (bl) {
                    this.appendDetail(stringBuffer, string, (Object[])object);
                } else {
                    this.appendSummary(stringBuffer, string, (Object[])object);
                }
            } else if (bl) {
                this.appendDetail(stringBuffer, string, object);
            } else {
                this.appendSummary(stringBuffer, string, object);
            }
        } finally {
            ToStringStyle.unregister(object);
        }
    }

    protected void appendCyclicObject(StringBuffer stringBuffer, String string, Object object) {
        ObjectUtils.identityToString(stringBuffer, object);
    }

    protected void appendDetail(StringBuffer stringBuffer, String string, Object object) {
        stringBuffer.append(object);
    }

    protected void appendDetail(StringBuffer stringBuffer, String string, Collection<?> collection) {
        stringBuffer.append(collection);
    }

    protected void appendDetail(StringBuffer stringBuffer, String string, Map<?, ?> map) {
        stringBuffer.append(map);
    }

    protected void appendSummary(StringBuffer stringBuffer, String string, Object object) {
        stringBuffer.append(this.summaryObjectStartText);
        stringBuffer.append(this.getShortClassName(object.getClass()));
        stringBuffer.append(this.summaryObjectEndText);
    }

    public void append(StringBuffer stringBuffer, String string, long l) {
        this.appendFieldStart(stringBuffer, string);
        this.appendDetail(stringBuffer, string, l);
        this.appendFieldEnd(stringBuffer, string);
    }

    protected void appendDetail(StringBuffer stringBuffer, String string, long l) {
        stringBuffer.append(l);
    }

    public void append(StringBuffer stringBuffer, String string, int n) {
        this.appendFieldStart(stringBuffer, string);
        this.appendDetail(stringBuffer, string, n);
        this.appendFieldEnd(stringBuffer, string);
    }

    protected void appendDetail(StringBuffer stringBuffer, String string, int n) {
        stringBuffer.append(n);
    }

    public void append(StringBuffer stringBuffer, String string, short s) {
        this.appendFieldStart(stringBuffer, string);
        this.appendDetail(stringBuffer, string, s);
        this.appendFieldEnd(stringBuffer, string);
    }

    protected void appendDetail(StringBuffer stringBuffer, String string, short s) {
        stringBuffer.append(s);
    }

    public void append(StringBuffer stringBuffer, String string, byte by) {
        this.appendFieldStart(stringBuffer, string);
        this.appendDetail(stringBuffer, string, by);
        this.appendFieldEnd(stringBuffer, string);
    }

    protected void appendDetail(StringBuffer stringBuffer, String string, byte by) {
        stringBuffer.append(by);
    }

    public void append(StringBuffer stringBuffer, String string, char c) {
        this.appendFieldStart(stringBuffer, string);
        this.appendDetail(stringBuffer, string, c);
        this.appendFieldEnd(stringBuffer, string);
    }

    protected void appendDetail(StringBuffer stringBuffer, String string, char c) {
        stringBuffer.append(c);
    }

    public void append(StringBuffer stringBuffer, String string, double d) {
        this.appendFieldStart(stringBuffer, string);
        this.appendDetail(stringBuffer, string, d);
        this.appendFieldEnd(stringBuffer, string);
    }

    protected void appendDetail(StringBuffer stringBuffer, String string, double d) {
        stringBuffer.append(d);
    }

    public void append(StringBuffer stringBuffer, String string, float f) {
        this.appendFieldStart(stringBuffer, string);
        this.appendDetail(stringBuffer, string, f);
        this.appendFieldEnd(stringBuffer, string);
    }

    protected void appendDetail(StringBuffer stringBuffer, String string, float f) {
        stringBuffer.append(f);
    }

    public void append(StringBuffer stringBuffer, String string, boolean bl) {
        this.appendFieldStart(stringBuffer, string);
        this.appendDetail(stringBuffer, string, bl);
        this.appendFieldEnd(stringBuffer, string);
    }

    protected void appendDetail(StringBuffer stringBuffer, String string, boolean bl) {
        stringBuffer.append(bl);
    }

    public void append(StringBuffer stringBuffer, String string, Object[] objectArray, Boolean bl) {
        this.appendFieldStart(stringBuffer, string);
        if (objectArray == null) {
            this.appendNullText(stringBuffer, string);
        } else if (this.isFullDetail(bl)) {
            this.appendDetail(stringBuffer, string, objectArray);
        } else {
            this.appendSummary(stringBuffer, string, objectArray);
        }
        this.appendFieldEnd(stringBuffer, string);
    }

    protected void appendDetail(StringBuffer stringBuffer, String string, Object[] objectArray) {
        stringBuffer.append(this.arrayStart);
        for (int i = 0; i < objectArray.length; ++i) {
            Object object = objectArray[i];
            if (i > 0) {
                stringBuffer.append(this.arraySeparator);
            }
            if (object == null) {
                this.appendNullText(stringBuffer, string);
                continue;
            }
            this.appendInternal(stringBuffer, string, object, this.arrayContentDetail);
        }
        stringBuffer.append(this.arrayEnd);
    }

    protected void reflectionAppendArrayDetail(StringBuffer stringBuffer, String string, Object object) {
        stringBuffer.append(this.arrayStart);
        int n = Array.getLength(object);
        for (int i = 0; i < n; ++i) {
            Object object2 = Array.get(object, i);
            if (i > 0) {
                stringBuffer.append(this.arraySeparator);
            }
            if (object2 == null) {
                this.appendNullText(stringBuffer, string);
                continue;
            }
            this.appendInternal(stringBuffer, string, object2, this.arrayContentDetail);
        }
        stringBuffer.append(this.arrayEnd);
    }

    protected void appendSummary(StringBuffer stringBuffer, String string, Object[] objectArray) {
        this.appendSummarySize(stringBuffer, string, objectArray.length);
    }

    public void append(StringBuffer stringBuffer, String string, long[] lArray, Boolean bl) {
        this.appendFieldStart(stringBuffer, string);
        if (lArray == null) {
            this.appendNullText(stringBuffer, string);
        } else if (this.isFullDetail(bl)) {
            this.appendDetail(stringBuffer, string, lArray);
        } else {
            this.appendSummary(stringBuffer, string, lArray);
        }
        this.appendFieldEnd(stringBuffer, string);
    }

    protected void appendDetail(StringBuffer stringBuffer, String string, long[] lArray) {
        stringBuffer.append(this.arrayStart);
        for (int i = 0; i < lArray.length; ++i) {
            if (i > 0) {
                stringBuffer.append(this.arraySeparator);
            }
            this.appendDetail(stringBuffer, string, lArray[i]);
        }
        stringBuffer.append(this.arrayEnd);
    }

    protected void appendSummary(StringBuffer stringBuffer, String string, long[] lArray) {
        this.appendSummarySize(stringBuffer, string, lArray.length);
    }

    public void append(StringBuffer stringBuffer, String string, int[] nArray, Boolean bl) {
        this.appendFieldStart(stringBuffer, string);
        if (nArray == null) {
            this.appendNullText(stringBuffer, string);
        } else if (this.isFullDetail(bl)) {
            this.appendDetail(stringBuffer, string, nArray);
        } else {
            this.appendSummary(stringBuffer, string, nArray);
        }
        this.appendFieldEnd(stringBuffer, string);
    }

    protected void appendDetail(StringBuffer stringBuffer, String string, int[] nArray) {
        stringBuffer.append(this.arrayStart);
        for (int i = 0; i < nArray.length; ++i) {
            if (i > 0) {
                stringBuffer.append(this.arraySeparator);
            }
            this.appendDetail(stringBuffer, string, nArray[i]);
        }
        stringBuffer.append(this.arrayEnd);
    }

    protected void appendSummary(StringBuffer stringBuffer, String string, int[] nArray) {
        this.appendSummarySize(stringBuffer, string, nArray.length);
    }

    public void append(StringBuffer stringBuffer, String string, short[] sArray, Boolean bl) {
        this.appendFieldStart(stringBuffer, string);
        if (sArray == null) {
            this.appendNullText(stringBuffer, string);
        } else if (this.isFullDetail(bl)) {
            this.appendDetail(stringBuffer, string, sArray);
        } else {
            this.appendSummary(stringBuffer, string, sArray);
        }
        this.appendFieldEnd(stringBuffer, string);
    }

    protected void appendDetail(StringBuffer stringBuffer, String string, short[] sArray) {
        stringBuffer.append(this.arrayStart);
        for (int i = 0; i < sArray.length; ++i) {
            if (i > 0) {
                stringBuffer.append(this.arraySeparator);
            }
            this.appendDetail(stringBuffer, string, sArray[i]);
        }
        stringBuffer.append(this.arrayEnd);
    }

    protected void appendSummary(StringBuffer stringBuffer, String string, short[] sArray) {
        this.appendSummarySize(stringBuffer, string, sArray.length);
    }

    public void append(StringBuffer stringBuffer, String string, byte[] byArray, Boolean bl) {
        this.appendFieldStart(stringBuffer, string);
        if (byArray == null) {
            this.appendNullText(stringBuffer, string);
        } else if (this.isFullDetail(bl)) {
            this.appendDetail(stringBuffer, string, byArray);
        } else {
            this.appendSummary(stringBuffer, string, byArray);
        }
        this.appendFieldEnd(stringBuffer, string);
    }

    protected void appendDetail(StringBuffer stringBuffer, String string, byte[] byArray) {
        stringBuffer.append(this.arrayStart);
        for (int i = 0; i < byArray.length; ++i) {
            if (i > 0) {
                stringBuffer.append(this.arraySeparator);
            }
            this.appendDetail(stringBuffer, string, byArray[i]);
        }
        stringBuffer.append(this.arrayEnd);
    }

    protected void appendSummary(StringBuffer stringBuffer, String string, byte[] byArray) {
        this.appendSummarySize(stringBuffer, string, byArray.length);
    }

    public void append(StringBuffer stringBuffer, String string, char[] cArray, Boolean bl) {
        this.appendFieldStart(stringBuffer, string);
        if (cArray == null) {
            this.appendNullText(stringBuffer, string);
        } else if (this.isFullDetail(bl)) {
            this.appendDetail(stringBuffer, string, cArray);
        } else {
            this.appendSummary(stringBuffer, string, cArray);
        }
        this.appendFieldEnd(stringBuffer, string);
    }

    protected void appendDetail(StringBuffer stringBuffer, String string, char[] cArray) {
        stringBuffer.append(this.arrayStart);
        for (int i = 0; i < cArray.length; ++i) {
            if (i > 0) {
                stringBuffer.append(this.arraySeparator);
            }
            this.appendDetail(stringBuffer, string, cArray[i]);
        }
        stringBuffer.append(this.arrayEnd);
    }

    protected void appendSummary(StringBuffer stringBuffer, String string, char[] cArray) {
        this.appendSummarySize(stringBuffer, string, cArray.length);
    }

    public void append(StringBuffer stringBuffer, String string, double[] dArray, Boolean bl) {
        this.appendFieldStart(stringBuffer, string);
        if (dArray == null) {
            this.appendNullText(stringBuffer, string);
        } else if (this.isFullDetail(bl)) {
            this.appendDetail(stringBuffer, string, dArray);
        } else {
            this.appendSummary(stringBuffer, string, dArray);
        }
        this.appendFieldEnd(stringBuffer, string);
    }

    protected void appendDetail(StringBuffer stringBuffer, String string, double[] dArray) {
        stringBuffer.append(this.arrayStart);
        for (int i = 0; i < dArray.length; ++i) {
            if (i > 0) {
                stringBuffer.append(this.arraySeparator);
            }
            this.appendDetail(stringBuffer, string, dArray[i]);
        }
        stringBuffer.append(this.arrayEnd);
    }

    protected void appendSummary(StringBuffer stringBuffer, String string, double[] dArray) {
        this.appendSummarySize(stringBuffer, string, dArray.length);
    }

    public void append(StringBuffer stringBuffer, String string, float[] fArray, Boolean bl) {
        this.appendFieldStart(stringBuffer, string);
        if (fArray == null) {
            this.appendNullText(stringBuffer, string);
        } else if (this.isFullDetail(bl)) {
            this.appendDetail(stringBuffer, string, fArray);
        } else {
            this.appendSummary(stringBuffer, string, fArray);
        }
        this.appendFieldEnd(stringBuffer, string);
    }

    protected void appendDetail(StringBuffer stringBuffer, String string, float[] fArray) {
        stringBuffer.append(this.arrayStart);
        for (int i = 0; i < fArray.length; ++i) {
            if (i > 0) {
                stringBuffer.append(this.arraySeparator);
            }
            this.appendDetail(stringBuffer, string, fArray[i]);
        }
        stringBuffer.append(this.arrayEnd);
    }

    protected void appendSummary(StringBuffer stringBuffer, String string, float[] fArray) {
        this.appendSummarySize(stringBuffer, string, fArray.length);
    }

    public void append(StringBuffer stringBuffer, String string, boolean[] blArray, Boolean bl) {
        this.appendFieldStart(stringBuffer, string);
        if (blArray == null) {
            this.appendNullText(stringBuffer, string);
        } else if (this.isFullDetail(bl)) {
            this.appendDetail(stringBuffer, string, blArray);
        } else {
            this.appendSummary(stringBuffer, string, blArray);
        }
        this.appendFieldEnd(stringBuffer, string);
    }

    protected void appendDetail(StringBuffer stringBuffer, String string, boolean[] blArray) {
        stringBuffer.append(this.arrayStart);
        for (int i = 0; i < blArray.length; ++i) {
            if (i > 0) {
                stringBuffer.append(this.arraySeparator);
            }
            this.appendDetail(stringBuffer, string, blArray[i]);
        }
        stringBuffer.append(this.arrayEnd);
    }

    protected void appendSummary(StringBuffer stringBuffer, String string, boolean[] blArray) {
        this.appendSummarySize(stringBuffer, string, blArray.length);
    }

    protected void appendClassName(StringBuffer stringBuffer, Object object) {
        if (this.useClassName && object != null) {
            ToStringStyle.register(object);
            if (this.useShortClassName) {
                stringBuffer.append(this.getShortClassName(object.getClass()));
            } else {
                stringBuffer.append(object.getClass().getName());
            }
        }
    }

    protected void appendIdentityHashCode(StringBuffer stringBuffer, Object object) {
        if (this.isUseIdentityHashCode() && object != null) {
            ToStringStyle.register(object);
            stringBuffer.append('@');
            stringBuffer.append(Integer.toHexString(System.identityHashCode(object)));
        }
    }

    protected void appendContentStart(StringBuffer stringBuffer) {
        stringBuffer.append(this.contentStart);
    }

    protected void appendContentEnd(StringBuffer stringBuffer) {
        stringBuffer.append(this.contentEnd);
    }

    protected void appendNullText(StringBuffer stringBuffer, String string) {
        stringBuffer.append(this.nullText);
    }

    protected void appendFieldSeparator(StringBuffer stringBuffer) {
        stringBuffer.append(this.fieldSeparator);
    }

    protected void appendFieldStart(StringBuffer stringBuffer, String string) {
        if (this.useFieldNames && string != null) {
            stringBuffer.append(string);
            stringBuffer.append(this.fieldNameValueSeparator);
        }
    }

    protected void appendFieldEnd(StringBuffer stringBuffer, String string) {
        this.appendFieldSeparator(stringBuffer);
    }

    protected void appendSummarySize(StringBuffer stringBuffer, String string, int n) {
        stringBuffer.append(this.sizeStartText);
        stringBuffer.append(n);
        stringBuffer.append(this.sizeEndText);
    }

    protected boolean isFullDetail(Boolean bl) {
        if (bl == null) {
            return this.defaultFullDetail;
        }
        return bl;
    }

    protected String getShortClassName(Class<?> clazz) {
        return ClassUtils.getShortClassName(clazz);
    }

    protected boolean isUseClassName() {
        return this.useClassName;
    }

    protected void setUseClassName(boolean bl) {
        this.useClassName = bl;
    }

    protected boolean isUseShortClassName() {
        return this.useShortClassName;
    }

    protected void setUseShortClassName(boolean bl) {
        this.useShortClassName = bl;
    }

    protected boolean isUseIdentityHashCode() {
        return this.useIdentityHashCode;
    }

    protected void setUseIdentityHashCode(boolean bl) {
        this.useIdentityHashCode = bl;
    }

    protected boolean isUseFieldNames() {
        return this.useFieldNames;
    }

    protected void setUseFieldNames(boolean bl) {
        this.useFieldNames = bl;
    }

    protected boolean isDefaultFullDetail() {
        return this.defaultFullDetail;
    }

    protected void setDefaultFullDetail(boolean bl) {
        this.defaultFullDetail = bl;
    }

    protected boolean isArrayContentDetail() {
        return this.arrayContentDetail;
    }

    protected void setArrayContentDetail(boolean bl) {
        this.arrayContentDetail = bl;
    }

    protected String getArrayStart() {
        return this.arrayStart;
    }

    protected void setArrayStart(String string) {
        if (string == null) {
            string = "";
        }
        this.arrayStart = string;
    }

    protected String getArrayEnd() {
        return this.arrayEnd;
    }

    protected void setArrayEnd(String string) {
        if (string == null) {
            string = "";
        }
        this.arrayEnd = string;
    }

    protected String getArraySeparator() {
        return this.arraySeparator;
    }

    protected void setArraySeparator(String string) {
        if (string == null) {
            string = "";
        }
        this.arraySeparator = string;
    }

    protected String getContentStart() {
        return this.contentStart;
    }

    protected void setContentStart(String string) {
        if (string == null) {
            string = "";
        }
        this.contentStart = string;
    }

    protected String getContentEnd() {
        return this.contentEnd;
    }

    protected void setContentEnd(String string) {
        if (string == null) {
            string = "";
        }
        this.contentEnd = string;
    }

    protected String getFieldNameValueSeparator() {
        return this.fieldNameValueSeparator;
    }

    protected void setFieldNameValueSeparator(String string) {
        if (string == null) {
            string = "";
        }
        this.fieldNameValueSeparator = string;
    }

    protected String getFieldSeparator() {
        return this.fieldSeparator;
    }

    protected void setFieldSeparator(String string) {
        if (string == null) {
            string = "";
        }
        this.fieldSeparator = string;
    }

    protected boolean isFieldSeparatorAtStart() {
        return this.fieldSeparatorAtStart;
    }

    protected void setFieldSeparatorAtStart(boolean bl) {
        this.fieldSeparatorAtStart = bl;
    }

    protected boolean isFieldSeparatorAtEnd() {
        return this.fieldSeparatorAtEnd;
    }

    protected void setFieldSeparatorAtEnd(boolean bl) {
        this.fieldSeparatorAtEnd = bl;
    }

    protected String getNullText() {
        return this.nullText;
    }

    protected void setNullText(String string) {
        if (string == null) {
            string = "";
        }
        this.nullText = string;
    }

    protected String getSizeStartText() {
        return this.sizeStartText;
    }

    protected void setSizeStartText(String string) {
        if (string == null) {
            string = "";
        }
        this.sizeStartText = string;
    }

    protected String getSizeEndText() {
        return this.sizeEndText;
    }

    protected void setSizeEndText(String string) {
        if (string == null) {
            string = "";
        }
        this.sizeEndText = string;
    }

    protected String getSummaryObjectStartText() {
        return this.summaryObjectStartText;
    }

    protected void setSummaryObjectStartText(String string) {
        if (string == null) {
            string = "";
        }
        this.summaryObjectStartText = string;
    }

    protected String getSummaryObjectEndText() {
        return this.summaryObjectEndText;
    }

    protected void setSummaryObjectEndText(String string) {
        if (string == null) {
            string = "";
        }
        this.summaryObjectEndText = string;
    }

    private static final class JsonToStringStyle
    extends ToStringStyle {
        private static final long serialVersionUID = 1L;
        private String FIELD_NAME_PREFIX = "\"";

        JsonToStringStyle() {
            this.setUseClassName(true);
            this.setUseIdentityHashCode(true);
            this.setContentStart("{");
            this.setContentEnd("}");
            this.setArrayStart("[");
            this.setArrayEnd("]");
            this.setFieldSeparator(",");
            this.setFieldNameValueSeparator(":");
            this.setNullText("null");
            this.setSummaryObjectStartText("\"<");
            this.setSummaryObjectEndText(">\"");
            this.setSizeStartText("\"<size=");
            this.setSizeEndText(">\"");
        }

        @Override
        public void append(StringBuffer stringBuffer, String string, Object[] objectArray, Boolean bl) {
            if (string == null) {
                throw new UnsupportedOperationException("Field names are mandatory when using JsonToStringStyle");
            }
            if (!this.isFullDetail(bl)) {
                throw new UnsupportedOperationException("FullDetail must be true when using JsonToStringStyle");
            }
            super.append(stringBuffer, string, objectArray, bl);
        }

        @Override
        public void append(StringBuffer stringBuffer, String string, long[] lArray, Boolean bl) {
            if (string == null) {
                throw new UnsupportedOperationException("Field names are mandatory when using JsonToStringStyle");
            }
            if (!this.isFullDetail(bl)) {
                throw new UnsupportedOperationException("FullDetail must be true when using JsonToStringStyle");
            }
            super.append(stringBuffer, string, lArray, bl);
        }

        @Override
        public void append(StringBuffer stringBuffer, String string, int[] nArray, Boolean bl) {
            if (string == null) {
                throw new UnsupportedOperationException("Field names are mandatory when using JsonToStringStyle");
            }
            if (!this.isFullDetail(bl)) {
                throw new UnsupportedOperationException("FullDetail must be true when using JsonToStringStyle");
            }
            super.append(stringBuffer, string, nArray, bl);
        }

        @Override
        public void append(StringBuffer stringBuffer, String string, short[] sArray, Boolean bl) {
            if (string == null) {
                throw new UnsupportedOperationException("Field names are mandatory when using JsonToStringStyle");
            }
            if (!this.isFullDetail(bl)) {
                throw new UnsupportedOperationException("FullDetail must be true when using JsonToStringStyle");
            }
            super.append(stringBuffer, string, sArray, bl);
        }

        @Override
        public void append(StringBuffer stringBuffer, String string, byte[] byArray, Boolean bl) {
            if (string == null) {
                throw new UnsupportedOperationException("Field names are mandatory when using JsonToStringStyle");
            }
            if (!this.isFullDetail(bl)) {
                throw new UnsupportedOperationException("FullDetail must be true when using JsonToStringStyle");
            }
            super.append(stringBuffer, string, byArray, bl);
        }

        @Override
        public void append(StringBuffer stringBuffer, String string, char[] cArray, Boolean bl) {
            if (string == null) {
                throw new UnsupportedOperationException("Field names are mandatory when using JsonToStringStyle");
            }
            if (!this.isFullDetail(bl)) {
                throw new UnsupportedOperationException("FullDetail must be true when using JsonToStringStyle");
            }
            super.append(stringBuffer, string, cArray, bl);
        }

        @Override
        public void append(StringBuffer stringBuffer, String string, double[] dArray, Boolean bl) {
            if (string == null) {
                throw new UnsupportedOperationException("Field names are mandatory when using JsonToStringStyle");
            }
            if (!this.isFullDetail(bl)) {
                throw new UnsupportedOperationException("FullDetail must be true when using JsonToStringStyle");
            }
            super.append(stringBuffer, string, dArray, bl);
        }

        @Override
        public void append(StringBuffer stringBuffer, String string, float[] fArray, Boolean bl) {
            if (string == null) {
                throw new UnsupportedOperationException("Field names are mandatory when using JsonToStringStyle");
            }
            if (!this.isFullDetail(bl)) {
                throw new UnsupportedOperationException("FullDetail must be true when using JsonToStringStyle");
            }
            super.append(stringBuffer, string, fArray, bl);
        }

        @Override
        public void append(StringBuffer stringBuffer, String string, boolean[] blArray, Boolean bl) {
            if (string == null) {
                throw new UnsupportedOperationException("Field names are mandatory when using JsonToStringStyle");
            }
            if (!this.isFullDetail(bl)) {
                throw new UnsupportedOperationException("FullDetail must be true when using JsonToStringStyle");
            }
            super.append(stringBuffer, string, blArray, bl);
        }

        @Override
        public void append(StringBuffer stringBuffer, String string, Object object, Boolean bl) {
            if (string == null) {
                throw new UnsupportedOperationException("Field names are mandatory when using JsonToStringStyle");
            }
            if (!this.isFullDetail(bl)) {
                throw new UnsupportedOperationException("FullDetail must be true when using JsonToStringStyle");
            }
            super.append(stringBuffer, string, object, bl);
        }

        @Override
        protected void appendDetail(StringBuffer stringBuffer, String string, char c) {
            this.appendValueAsString(stringBuffer, String.valueOf(c));
        }

        @Override
        protected void appendDetail(StringBuffer stringBuffer, String string, Object object) {
            if (object == null) {
                this.appendNullText(stringBuffer, string);
                return;
            }
            if (object instanceof String || object instanceof Character) {
                this.appendValueAsString(stringBuffer, object.toString());
                return;
            }
            if (object instanceof Number || object instanceof Boolean) {
                stringBuffer.append(object);
                return;
            }
            String string2 = object.toString();
            if (this.isJsonObject(string2) || this.isJsonArray(string2)) {
                stringBuffer.append(object);
                return;
            }
            this.appendDetail(stringBuffer, string, string2);
        }

        private boolean isJsonArray(String string) {
            return string.startsWith(this.getArrayStart()) && string.startsWith(this.getArrayEnd());
        }

        private boolean isJsonObject(String string) {
            return string.startsWith(this.getContentStart()) && string.endsWith(this.getContentEnd());
        }

        private void appendValueAsString(StringBuffer stringBuffer, String string) {
            stringBuffer.append("\"" + string + "\"");
        }

        @Override
        protected void appendFieldStart(StringBuffer stringBuffer, String string) {
            if (string == null) {
                throw new UnsupportedOperationException("Field names are mandatory when using JsonToStringStyle");
            }
            super.appendFieldStart(stringBuffer, this.FIELD_NAME_PREFIX + string + this.FIELD_NAME_PREFIX);
        }

        private Object readResolve() {
            return JSON_STYLE;
        }
    }

    private static final class NoClassNameToStringStyle
    extends ToStringStyle {
        private static final long serialVersionUID = 1L;

        NoClassNameToStringStyle() {
            this.setUseClassName(true);
            this.setUseIdentityHashCode(true);
        }

        private Object readResolve() {
            return NO_CLASS_NAME_STYLE;
        }
    }

    private static final class MultiLineToStringStyle
    extends ToStringStyle {
        private static final long serialVersionUID = 1L;

        MultiLineToStringStyle() {
            this.setContentStart("[");
            this.setFieldSeparator(SystemUtils.LINE_SEPARATOR + "  ");
            this.setFieldSeparatorAtStart(false);
            this.setContentEnd(SystemUtils.LINE_SEPARATOR + "]");
        }

        private Object readResolve() {
            return MULTI_LINE_STYLE;
        }
    }

    private static final class SimpleToStringStyle
    extends ToStringStyle {
        private static final long serialVersionUID = 1L;

        SimpleToStringStyle() {
            this.setUseClassName(true);
            this.setUseIdentityHashCode(true);
            this.setUseFieldNames(true);
            this.setContentStart("");
            this.setContentEnd("");
        }

        private Object readResolve() {
            return SIMPLE_STYLE;
        }
    }

    private static final class ShortPrefixToStringStyle
    extends ToStringStyle {
        private static final long serialVersionUID = 1L;

        ShortPrefixToStringStyle() {
            this.setUseShortClassName(false);
            this.setUseIdentityHashCode(true);
        }

        private Object readResolve() {
            return SHORT_PREFIX_STYLE;
        }
    }

    private static final class NoFieldNameToStringStyle
    extends ToStringStyle {
        private static final long serialVersionUID = 1L;

        NoFieldNameToStringStyle() {
            this.setUseFieldNames(true);
        }

        private Object readResolve() {
            return NO_FIELD_NAMES_STYLE;
        }
    }

    private static final class DefaultToStringStyle
    extends ToStringStyle {
        private static final long serialVersionUID = 1L;

        DefaultToStringStyle() {
        }

        private Object readResolve() {
            return DEFAULT_STYLE;
        }
    }
}

