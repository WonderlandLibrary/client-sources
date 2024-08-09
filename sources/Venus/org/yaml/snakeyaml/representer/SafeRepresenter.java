/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.yaml.snakeyaml.representer;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Pattern;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.reader.StreamReader;
import org.yaml.snakeyaml.representer.BaseRepresenter;
import org.yaml.snakeyaml.representer.Represent;

class SafeRepresenter
extends BaseRepresenter {
    protected Map<Class<? extends Object>, Tag> classTags;
    protected TimeZone timeZone = null;
    protected DumperOptions.NonPrintableStyle nonPrintableStyle;
    private static final Pattern MULTILINE_PATTERN = Pattern.compile("\n|\u0085|\u2028|\u2029");

    public SafeRepresenter(DumperOptions dumperOptions) {
        if (dumperOptions == null) {
            throw new NullPointerException("DumperOptions must be provided.");
        }
        this.nullRepresenter = new RepresentNull(this);
        this.representers.put(String.class, new RepresentString(this));
        this.representers.put(Boolean.class, new RepresentBoolean(this));
        this.representers.put(Character.class, new RepresentString(this));
        this.representers.put(UUID.class, new RepresentUuid(this));
        this.representers.put(byte[].class, new RepresentByteArray(this));
        RepresentPrimitiveArray representPrimitiveArray = new RepresentPrimitiveArray(this);
        this.representers.put(short[].class, representPrimitiveArray);
        this.representers.put(int[].class, representPrimitiveArray);
        this.representers.put(long[].class, representPrimitiveArray);
        this.representers.put(float[].class, representPrimitiveArray);
        this.representers.put(double[].class, representPrimitiveArray);
        this.representers.put(char[].class, representPrimitiveArray);
        this.representers.put(boolean[].class, representPrimitiveArray);
        this.multiRepresenters.put(Number.class, new RepresentNumber(this));
        this.multiRepresenters.put(List.class, new RepresentList(this));
        this.multiRepresenters.put(Map.class, new RepresentMap(this));
        this.multiRepresenters.put(Set.class, new RepresentSet(this));
        this.multiRepresenters.put(Iterator.class, new RepresentIterator(this));
        this.multiRepresenters.put(new Object[0].getClass(), new RepresentArray(this));
        this.multiRepresenters.put(Date.class, new RepresentDate(this));
        this.multiRepresenters.put(Enum.class, new RepresentEnum(this));
        this.multiRepresenters.put(Calendar.class, new RepresentDate(this));
        this.classTags = new HashMap<Class<? extends Object>, Tag>();
        this.nonPrintableStyle = dumperOptions.getNonPrintableStyle();
        this.setDefaultScalarStyle(dumperOptions.getDefaultScalarStyle());
        this.setDefaultFlowStyle(dumperOptions.getDefaultFlowStyle());
    }

    protected Tag getTag(Class<?> clazz, Tag tag) {
        if (this.classTags.containsKey(clazz)) {
            return this.classTags.get(clazz);
        }
        return tag;
    }

    public Tag addClassTag(Class<? extends Object> clazz, Tag tag) {
        if (tag == null) {
            throw new NullPointerException("Tag must be provided.");
        }
        return this.classTags.put(clazz, tag);
    }

    public TimeZone getTimeZone() {
        return this.timeZone;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    static Pattern access$000() {
        return MULTILINE_PATTERN;
    }

    protected class RepresentUuid
    implements Represent {
        final SafeRepresenter this$0;

        protected RepresentUuid(SafeRepresenter safeRepresenter) {
            this.this$0 = safeRepresenter;
        }

        @Override
        public Node representData(Object object) {
            return this.this$0.representScalar(this.this$0.getTag(object.getClass(), new Tag(UUID.class)), object.toString());
        }
    }

    protected class RepresentByteArray
    implements Represent {
        final SafeRepresenter this$0;

        protected RepresentByteArray(SafeRepresenter safeRepresenter) {
            this.this$0 = safeRepresenter;
        }

        @Override
        public Node representData(Object object) {
            char[] cArray = Base64Coder.encode((byte[])object);
            return this.this$0.representScalar(Tag.BINARY, String.valueOf(cArray), DumperOptions.ScalarStyle.LITERAL);
        }
    }

    protected class RepresentEnum
    implements Represent {
        final SafeRepresenter this$0;

        protected RepresentEnum(SafeRepresenter safeRepresenter) {
            this.this$0 = safeRepresenter;
        }

        @Override
        public Node representData(Object object) {
            Tag tag = new Tag(object.getClass());
            return this.this$0.representScalar(this.this$0.getTag(object.getClass(), tag), ((Enum)object).name());
        }
    }

    protected class RepresentDate
    implements Represent {
        final SafeRepresenter this$0;

        protected RepresentDate(SafeRepresenter safeRepresenter) {
            this.this$0 = safeRepresenter;
        }

        @Override
        public Node representData(Object object) {
            int n;
            Calendar calendar;
            if (object instanceof Calendar) {
                calendar = (Calendar)object;
            } else {
                calendar = Calendar.getInstance(this.this$0.getTimeZone() == null ? TimeZone.getTimeZone("UTC") : this.this$0.timeZone);
                calendar.setTime((Date)object);
            }
            int n2 = calendar.get(1);
            int n3 = calendar.get(2) + 1;
            int n4 = calendar.get(5);
            int n5 = calendar.get(11);
            int n6 = calendar.get(12);
            int n7 = calendar.get(13);
            int n8 = calendar.get(14);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(n2));
            while (stringBuilder.length() < 4) {
                stringBuilder.insert(0, "0");
            }
            stringBuilder.append("-");
            if (n3 < 10) {
                stringBuilder.append("0");
            }
            stringBuilder.append(n3);
            stringBuilder.append("-");
            if (n4 < 10) {
                stringBuilder.append("0");
            }
            stringBuilder.append(n4);
            stringBuilder.append("T");
            if (n5 < 10) {
                stringBuilder.append("0");
            }
            stringBuilder.append(n5);
            stringBuilder.append(":");
            if (n6 < 10) {
                stringBuilder.append("0");
            }
            stringBuilder.append(n6);
            stringBuilder.append(":");
            if (n7 < 10) {
                stringBuilder.append("0");
            }
            stringBuilder.append(n7);
            if (n8 > 0) {
                if (n8 < 10) {
                    stringBuilder.append(".00");
                } else if (n8 < 100) {
                    stringBuilder.append(".0");
                } else {
                    stringBuilder.append(".");
                }
                stringBuilder.append(n8);
            }
            if ((n = calendar.getTimeZone().getOffset(calendar.getTime().getTime())) == 0) {
                stringBuilder.append('Z');
            } else {
                if (n < 0) {
                    stringBuilder.append('-');
                    n *= -1;
                } else {
                    stringBuilder.append('+');
                }
                int n9 = n / 60000;
                int n10 = n9 / 60;
                int n11 = n9 % 60;
                if (n10 < 10) {
                    stringBuilder.append('0');
                }
                stringBuilder.append(n10);
                stringBuilder.append(':');
                if (n11 < 10) {
                    stringBuilder.append('0');
                }
                stringBuilder.append(n11);
            }
            return this.this$0.representScalar(this.this$0.getTag(object.getClass(), Tag.TIMESTAMP), stringBuilder.toString(), DumperOptions.ScalarStyle.PLAIN);
        }
    }

    protected class RepresentSet
    implements Represent {
        final SafeRepresenter this$0;

        protected RepresentSet(SafeRepresenter safeRepresenter) {
            this.this$0 = safeRepresenter;
        }

        @Override
        public Node representData(Object object) {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            Set set = (Set)object;
            for (Object e : set) {
                linkedHashMap.put(e, null);
            }
            return this.this$0.representMapping(this.this$0.getTag(object.getClass(), Tag.SET), linkedHashMap, DumperOptions.FlowStyle.AUTO);
        }
    }

    protected class RepresentMap
    implements Represent {
        final SafeRepresenter this$0;

        protected RepresentMap(SafeRepresenter safeRepresenter) {
            this.this$0 = safeRepresenter;
        }

        @Override
        public Node representData(Object object) {
            return this.this$0.representMapping(this.this$0.getTag(object.getClass(), Tag.MAP), (Map)object, DumperOptions.FlowStyle.AUTO);
        }
    }

    protected class RepresentPrimitiveArray
    implements Represent {
        final SafeRepresenter this$0;

        protected RepresentPrimitiveArray(SafeRepresenter safeRepresenter) {
            this.this$0 = safeRepresenter;
        }

        @Override
        public Node representData(Object object) {
            Class<?> clazz = object.getClass().getComponentType();
            if (Byte.TYPE == clazz) {
                return this.this$0.representSequence(Tag.SEQ, this.asByteList(object), DumperOptions.FlowStyle.AUTO);
            }
            if (Short.TYPE == clazz) {
                return this.this$0.representSequence(Tag.SEQ, this.asShortList(object), DumperOptions.FlowStyle.AUTO);
            }
            if (Integer.TYPE == clazz) {
                return this.this$0.representSequence(Tag.SEQ, this.asIntList(object), DumperOptions.FlowStyle.AUTO);
            }
            if (Long.TYPE == clazz) {
                return this.this$0.representSequence(Tag.SEQ, this.asLongList(object), DumperOptions.FlowStyle.AUTO);
            }
            if (Float.TYPE == clazz) {
                return this.this$0.representSequence(Tag.SEQ, this.asFloatList(object), DumperOptions.FlowStyle.AUTO);
            }
            if (Double.TYPE == clazz) {
                return this.this$0.representSequence(Tag.SEQ, this.asDoubleList(object), DumperOptions.FlowStyle.AUTO);
            }
            if (Character.TYPE == clazz) {
                return this.this$0.representSequence(Tag.SEQ, this.asCharList(object), DumperOptions.FlowStyle.AUTO);
            }
            if (Boolean.TYPE == clazz) {
                return this.this$0.representSequence(Tag.SEQ, this.asBooleanList(object), DumperOptions.FlowStyle.AUTO);
            }
            throw new YAMLException("Unexpected primitive '" + clazz.getCanonicalName() + "'");
        }

        private List<Byte> asByteList(Object object) {
            byte[] byArray = (byte[])object;
            ArrayList<Byte> arrayList = new ArrayList<Byte>(byArray.length);
            for (int i = 0; i < byArray.length; ++i) {
                arrayList.add(byArray[i]);
            }
            return arrayList;
        }

        private List<Short> asShortList(Object object) {
            short[] sArray = (short[])object;
            ArrayList<Short> arrayList = new ArrayList<Short>(sArray.length);
            for (int i = 0; i < sArray.length; ++i) {
                arrayList.add(sArray[i]);
            }
            return arrayList;
        }

        private List<Integer> asIntList(Object object) {
            int[] nArray = (int[])object;
            ArrayList<Integer> arrayList = new ArrayList<Integer>(nArray.length);
            for (int i = 0; i < nArray.length; ++i) {
                arrayList.add(nArray[i]);
            }
            return arrayList;
        }

        private List<Long> asLongList(Object object) {
            long[] lArray = (long[])object;
            ArrayList<Long> arrayList = new ArrayList<Long>(lArray.length);
            for (int i = 0; i < lArray.length; ++i) {
                arrayList.add(lArray[i]);
            }
            return arrayList;
        }

        private List<Float> asFloatList(Object object) {
            float[] fArray = (float[])object;
            ArrayList<Float> arrayList = new ArrayList<Float>(fArray.length);
            for (int i = 0; i < fArray.length; ++i) {
                arrayList.add(Float.valueOf(fArray[i]));
            }
            return arrayList;
        }

        private List<Double> asDoubleList(Object object) {
            double[] dArray = (double[])object;
            ArrayList<Double> arrayList = new ArrayList<Double>(dArray.length);
            for (int i = 0; i < dArray.length; ++i) {
                arrayList.add(dArray[i]);
            }
            return arrayList;
        }

        private List<Character> asCharList(Object object) {
            char[] cArray = (char[])object;
            ArrayList<Character> arrayList = new ArrayList<Character>(cArray.length);
            for (int i = 0; i < cArray.length; ++i) {
                arrayList.add(Character.valueOf(cArray[i]));
            }
            return arrayList;
        }

        private List<Boolean> asBooleanList(Object object) {
            boolean[] blArray = (boolean[])object;
            ArrayList<Boolean> arrayList = new ArrayList<Boolean>(blArray.length);
            for (int i = 0; i < blArray.length; ++i) {
                arrayList.add(blArray[i]);
            }
            return arrayList;
        }
    }

    protected class RepresentArray
    implements Represent {
        final SafeRepresenter this$0;

        protected RepresentArray(SafeRepresenter safeRepresenter) {
            this.this$0 = safeRepresenter;
        }

        @Override
        public Node representData(Object object) {
            Object[] objectArray = (Object[])object;
            List<Object> list = Arrays.asList(objectArray);
            return this.this$0.representSequence(Tag.SEQ, list, DumperOptions.FlowStyle.AUTO);
        }
    }

    private static class IteratorWrapper
    implements Iterable<Object> {
        private final Iterator<Object> iter;

        public IteratorWrapper(Iterator<Object> iterator2) {
            this.iter = iterator2;
        }

        @Override
        public Iterator<Object> iterator() {
            return this.iter;
        }
    }

    protected class RepresentIterator
    implements Represent {
        final SafeRepresenter this$0;

        protected RepresentIterator(SafeRepresenter safeRepresenter) {
            this.this$0 = safeRepresenter;
        }

        @Override
        public Node representData(Object object) {
            Iterator iterator2 = (Iterator)object;
            return this.this$0.representSequence(this.this$0.getTag(object.getClass(), Tag.SEQ), new IteratorWrapper(iterator2), DumperOptions.FlowStyle.AUTO);
        }
    }

    protected class RepresentList
    implements Represent {
        final SafeRepresenter this$0;

        protected RepresentList(SafeRepresenter safeRepresenter) {
            this.this$0 = safeRepresenter;
        }

        @Override
        public Node representData(Object object) {
            return this.this$0.representSequence(this.this$0.getTag(object.getClass(), Tag.SEQ), (List)object, DumperOptions.FlowStyle.AUTO);
        }
    }

    protected class RepresentNumber
    implements Represent {
        final SafeRepresenter this$0;

        protected RepresentNumber(SafeRepresenter safeRepresenter) {
            this.this$0 = safeRepresenter;
        }

        @Override
        public Node representData(Object object) {
            String string;
            Tag tag;
            if (object instanceof Byte || object instanceof Short || object instanceof Integer || object instanceof Long || object instanceof BigInteger) {
                tag = Tag.INT;
                string = object.toString();
            } else {
                Number number = (Number)object;
                tag = Tag.FLOAT;
                string = number.equals(Double.NaN) ? ".NaN" : (number.equals(Double.POSITIVE_INFINITY) ? ".inf" : (number.equals(Double.NEGATIVE_INFINITY) ? "-.inf" : number.toString()));
            }
            return this.this$0.representScalar(this.this$0.getTag(object.getClass(), tag), string);
        }
    }

    protected class RepresentBoolean
    implements Represent {
        final SafeRepresenter this$0;

        protected RepresentBoolean(SafeRepresenter safeRepresenter) {
            this.this$0 = safeRepresenter;
        }

        @Override
        public Node representData(Object object) {
            String string = Boolean.TRUE.equals(object) ? "true" : "false";
            return this.this$0.representScalar(Tag.BOOL, string);
        }
    }

    protected class RepresentString
    implements Represent {
        final SafeRepresenter this$0;

        protected RepresentString(SafeRepresenter safeRepresenter) {
            this.this$0 = safeRepresenter;
        }

        @Override
        public Node representData(Object object) {
            Tag tag = Tag.STR;
            DumperOptions.ScalarStyle scalarStyle = this.this$0.defaultScalarStyle;
            String string = object.toString();
            if (this.this$0.nonPrintableStyle == DumperOptions.NonPrintableStyle.BINARY && !StreamReader.isPrintable(string)) {
                tag = Tag.BINARY;
                byte[] byArray = string.getBytes(StandardCharsets.UTF_8);
                String string2 = new String(byArray, StandardCharsets.UTF_8);
                if (!string2.equals(string)) {
                    throw new YAMLException("invalid string value has occurred");
                }
                char[] cArray = Base64Coder.encode(byArray);
                string = String.valueOf(cArray);
                scalarStyle = DumperOptions.ScalarStyle.LITERAL;
            }
            if (this.this$0.defaultScalarStyle == DumperOptions.ScalarStyle.PLAIN && SafeRepresenter.access$000().matcher(string).find()) {
                scalarStyle = DumperOptions.ScalarStyle.LITERAL;
            }
            return this.this$0.representScalar(tag, string, scalarStyle);
        }
    }

    protected class RepresentNull
    implements Represent {
        final SafeRepresenter this$0;

        protected RepresentNull(SafeRepresenter safeRepresenter) {
            this.this$0 = safeRepresenter;
        }

        @Override
        public Node representData(Object object) {
            return this.this$0.representScalar(Tag.NULL, "null");
        }
    }
}

