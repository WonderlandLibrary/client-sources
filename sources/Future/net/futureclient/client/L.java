package net.futureclient.client;

import java.util.Collection;
import java.util.Map;
import java.io.IOException;
import java.io.Writer;
import java.io.StringWriter;
import java.io.StringReader;
import java.io.Reader;

public class L
{
    public L() {
        super();
    }
    
    public static Object e(final Reader reader) {
        try {
            return new B().M(reader);
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    public static String e(final String s) {
        if (s == null) {
            return null;
        }
        final StringBuffer sb = new StringBuffer();
        M(s, sb);
        return sb.toString();
    }
    
    public static Object e(final String s) {
        return e(new StringReader(s));
    }
    
    public static void M(final String s, final StringBuffer sb) {
        final int length = s.length();
        int i = 0;
        int n = 0;
        while (i < length) {
            final char char1;
            switch (char1 = s.charAt(n)) {
                case '\"':
                    sb.append("\\\"");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                case '/':
                    sb.append("\\/");
                    break;
                default:
                    if ((char1 >= '\0' && char1 <= '\u001f') || (char1 >= '\u007f' && char1 <= '\u009f') || (char1 >= '\u2000' && char1 <= '\u20ff')) {
                        final String hexString = Integer.toHexString(char1);
                        sb.append("\\u");
                        int j;
                        int n2 = j = 0;
                        while (j < 4 - hexString.length()) {
                            ++n2;
                            sb.append('0');
                            j = n2;
                        }
                        sb.append(hexString.toUpperCase());
                        break;
                    }
                    sb.append(char1);
                    break;
            }
            i = ++n;
        }
    }
    
    public static Object M(final String s) throws f {
        return new B().M(s);
    }
    
    public static String M(final Object o) {
        final StringWriter stringWriter = new StringWriter();
        try {
            M(o, stringWriter);
            return stringWriter.toString();
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public static Object M(final Reader reader) throws IOException, f {
        return new B().M(reader);
    }
    
    public static void M(final Object o, final Writer writer) throws IOException {
        if (o == null) {
            writer.write("null");
            return;
        }
        if (o instanceof String) {
            writer.write(34);
            writer.write(e((String)o));
            writer.write(34);
            return;
        }
        if (o instanceof Double) {
            if (((Double)o).isInfinite() || ((Double)o).isNaN()) {
                writer.write("null");
                return;
            }
            writer.write(o.toString());
        }
        else if (o instanceof Float) {
            if (((Float)o).isInfinite() || ((Float)o).isNaN()) {
                writer.write("null");
                return;
            }
            writer.write(o.toString());
        }
        else {
            if (o instanceof Number) {
                writer.write(o.toString());
                return;
            }
            if (o instanceof Boolean) {
                writer.write(o.toString());
                return;
            }
            if (o instanceof k) {
                ((k)o).M(writer);
                return;
            }
            if (o instanceof C) {
                writer.write(((C)o).M());
                return;
            }
            if (o instanceof Map) {
                m.M((Map)o, writer);
                return;
            }
            if (o instanceof Collection) {
                e.M((Collection)o, writer);
                return;
            }
            if (o instanceof byte[]) {
                e.M((byte[])o, writer);
                return;
            }
            if (o instanceof short[]) {
                e.M((short[])o, writer);
                return;
            }
            if (o instanceof int[]) {
                e.M((int[])o, writer);
                return;
            }
            if (o instanceof long[]) {
                e.M((long[])o, writer);
                return;
            }
            if (o instanceof float[]) {
                e.M((float[])o, writer);
                return;
            }
            if (o instanceof double[]) {
                e.M((double[])o, writer);
                return;
            }
            if (o instanceof boolean[]) {
                e.M((boolean[])o, writer);
                return;
            }
            if (o instanceof char[]) {
                e.M((char[])o, writer);
                return;
            }
            if (o instanceof Object[]) {
                e.M((Object[])o, writer);
                return;
            }
            writer.write(o.toString());
        }
    }
    
    public static String M(String s) {
        final StackTraceElement stackTraceElement = new RuntimeException().getStackTrace()[1];
        final String string = new StringBuffer(stackTraceElement.getClassName()).append(stackTraceElement.getMethodName()).toString();
        final int n = string.length() - 1;
        final int n2 = 27;
        final int n3 = 101;
        final int length = (s = s).length();
        final char[] array = new char[length];
        int n4;
        int i = n4 = length - 1;
        final char[] array2 = array;
        final int n5 = n3;
        final int n6 = n2;
        int n7 = n;
        final int n8 = n;
        final String s2 = string;
        while (i >= 0) {
            final char[] array3 = array2;
            final int n9 = n6;
            final String s3 = s;
            final int n10 = n4--;
            array3[n10] = (char)(n9 ^ (s3.charAt(n10) ^ s2.charAt(n7)));
            if (n4 < 0) {
                break;
            }
            final char[] array4 = array2;
            final int n11 = n5;
            final String s4 = s;
            final int n12 = n4;
            final char c = (char)(n11 ^ (s4.charAt(n12) ^ s2.charAt(n7)));
            --n4;
            --n7;
            array4[n12] = c;
            if (n7 < 0) {
                n7 = n8;
            }
            i = n4;
        }
        return new String(array2);
    }
}
