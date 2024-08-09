/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.json;

import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XMLParserConfiguration;
import org.json.XMLTokener;
import org.json.XMLXsiTypeConverter;

public class XML {
    public static final Character AMP = Character.valueOf('&');
    public static final Character APOS = Character.valueOf('\'');
    public static final Character BANG = Character.valueOf('!');
    public static final Character EQ = Character.valueOf('=');
    public static final Character GT = Character.valueOf('>');
    public static final Character LT = Character.valueOf('<');
    public static final Character QUEST = Character.valueOf('?');
    public static final Character QUOT = Character.valueOf('\"');
    public static final Character SLASH = Character.valueOf('/');
    public static final String NULL_ATTR = "xsi:nil";
    public static final String TYPE_ATTR = "xsi:type";

    private static Iterable<Integer> codePointIterator(String string) {
        return new Iterable<Integer>(string){
            final String val$string;
            {
                this.val$string = string;
            }

            @Override
            public Iterator<Integer> iterator() {
                return new Iterator<Integer>(this){
                    private int nextIndex;
                    private int length;
                    final 1 this$0;
                    {
                        this.this$0 = var1_1;
                        this.nextIndex = 0;
                        this.length = this.this$0.val$string.length();
                    }

                    @Override
                    public boolean hasNext() {
                        return this.nextIndex < this.length;
                    }

                    @Override
                    public Integer next() {
                        int n = this.this$0.val$string.codePointAt(this.nextIndex);
                        this.nextIndex += Character.charCount(n);
                        return n;
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }

                    @Override
                    public Object next() {
                        return this.next();
                    }
                };
            }
        };
    }

    public static String escape(String string) {
        StringBuilder stringBuilder = new StringBuilder(string.length());
        block7: for (int n : XML.codePointIterator(string)) {
            switch (n) {
                case 38: {
                    stringBuilder.append("&amp;");
                    continue block7;
                }
                case 60: {
                    stringBuilder.append("&lt;");
                    continue block7;
                }
                case 62: {
                    stringBuilder.append("&gt;");
                    continue block7;
                }
                case 34: {
                    stringBuilder.append("&quot;");
                    continue block7;
                }
                case 39: {
                    stringBuilder.append("&apos;");
                    continue block7;
                }
            }
            if (XML.mustEscape(n)) {
                stringBuilder.append("&#x");
                stringBuilder.append(Integer.toHexString(n));
                stringBuilder.append(';');
                continue;
            }
            stringBuilder.appendCodePoint(n);
        }
        return stringBuilder.toString();
    }

    private static boolean mustEscape(int n) {
        return Character.isISOControl(n) && n != 9 && n != 10 && n != 13 || (n < 32 || n > 55295) && (n < 57344 || n > 65533) && (n < 65536 || n > 0x10FFFF);
    }

    public static String unescape(String string) {
        StringBuilder stringBuilder = new StringBuilder(string.length());
        int n = string.length();
        for (int i = 0; i < n; ++i) {
            char c = string.charAt(i);
            if (c == '&') {
                int n2 = string.indexOf(59, i);
                if (n2 > i) {
                    String string2 = string.substring(i + 1, n2);
                    stringBuilder.append(XMLTokener.unescapeEntity(string2));
                    i += string2.length() + 1;
                    continue;
                }
                stringBuilder.append(c);
                continue;
            }
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    public static void noSpace(String string) throws JSONException {
        int n = string.length();
        if (n == 0) {
            throw new JSONException("Empty string.");
        }
        for (int i = 0; i < n; ++i) {
            if (!Character.isWhitespace(string.charAt(i))) continue;
            throw new JSONException("'" + string + "' contains a space character.");
        }
    }

    private static boolean parse(XMLTokener xMLTokener, JSONObject jSONObject, String string, XMLParserConfiguration xMLParserConfiguration, int n) throws JSONException {
        String string2;
        JSONObject jSONObject2 = null;
        Object object = xMLTokener.nextToken();
        if (object == BANG) {
            char c = xMLTokener.next();
            if (c == '-') {
                if (xMLTokener.next() == '-') {
                    xMLTokener.skipPast("-->");
                    return true;
                }
                xMLTokener.back();
            } else if (c == '[') {
                object = xMLTokener.nextToken();
                if ("CDATA".equals(object) && xMLTokener.next() == '[') {
                    String string3 = xMLTokener.nextCDATA();
                    if (string3.length() > 0) {
                        jSONObject.accumulate(xMLParserConfiguration.getcDataTagName(), string3);
                    }
                    return true;
                }
                throw xMLTokener.syntaxError("Expected 'CDATA['");
            }
            int n2 = 1;
            do {
                if ((object = xMLTokener.nextMeta()) == null) {
                    throw xMLTokener.syntaxError("Missing '>' after '<!'.");
                }
                if (object == LT) {
                    ++n2;
                    continue;
                }
                if (object != GT) continue;
                --n2;
            } while (n2 > 0);
            return true;
        }
        if (object == QUEST) {
            xMLTokener.skipPast("?>");
            return true;
        }
        if (object == SLASH) {
            object = xMLTokener.nextToken();
            if (string == null) {
                throw xMLTokener.syntaxError("Mismatched close tag " + object);
            }
            if (!object.equals(string)) {
                throw xMLTokener.syntaxError("Mismatched " + string + " and " + object);
            }
            if (xMLTokener.nextToken() != GT) {
                throw xMLTokener.syntaxError("Misshaped close tag");
            }
            return false;
        }
        if (object instanceof Character) {
            throw xMLTokener.syntaxError("Misshaped tag");
        }
        String string4 = (String)object;
        object = null;
        jSONObject2 = new JSONObject();
        boolean bl = false;
        XMLXsiTypeConverter<?> xMLXsiTypeConverter = null;
        while (true) {
            if (object == null) {
                object = xMLTokener.nextToken();
            }
            if (!(object instanceof String)) break;
            string2 = (String)object;
            object = xMLTokener.nextToken();
            if (object == EQ) {
                object = xMLTokener.nextToken();
                if (!(object instanceof String)) {
                    throw xMLTokener.syntaxError("Missing value");
                }
                if (xMLParserConfiguration.isConvertNilAttributeToNull() && NULL_ATTR.equals(string2) && Boolean.parseBoolean((String)object)) {
                    bl = true;
                } else if (xMLParserConfiguration.getXsiTypeMap() != null && !xMLParserConfiguration.getXsiTypeMap().isEmpty() && TYPE_ATTR.equals(string2)) {
                    xMLXsiTypeConverter = xMLParserConfiguration.getXsiTypeMap().get(object);
                } else if (!bl) {
                    jSONObject2.accumulate(string2, xMLParserConfiguration.isKeepStrings() ? (String)object : XML.stringToValue((String)object));
                }
                object = null;
                continue;
            }
            jSONObject2.accumulate(string2, "");
        }
        if (object == SLASH) {
            if (xMLTokener.nextToken() != GT) {
                throw xMLTokener.syntaxError("Misshaped tag");
            }
            if (xMLParserConfiguration.getForceList().contains(string4)) {
                if (bl) {
                    jSONObject.append(string4, JSONObject.NULL);
                } else if (jSONObject2.length() > 0) {
                    jSONObject.append(string4, jSONObject2);
                } else {
                    jSONObject.put(string4, new JSONArray());
                }
            } else if (bl) {
                jSONObject.accumulate(string4, JSONObject.NULL);
            } else if (jSONObject2.length() > 0) {
                jSONObject.accumulate(string4, jSONObject2);
            } else {
                jSONObject.accumulate(string4, "");
            }
            return true;
        }
        if (object == GT) {
            while (true) {
                if ((object = xMLTokener.nextContent()) == null) {
                    if (string4 != null) {
                        throw xMLTokener.syntaxError("Unclosed tag " + string4);
                    }
                    return true;
                }
                if (object instanceof String) {
                    string2 = (String)object;
                    if (string2.length() <= 0) continue;
                    if (xMLXsiTypeConverter != null) {
                        jSONObject2.accumulate(xMLParserConfiguration.getcDataTagName(), XML.stringToValue(string2, xMLXsiTypeConverter));
                        continue;
                    }
                    jSONObject2.accumulate(xMLParserConfiguration.getcDataTagName(), xMLParserConfiguration.isKeepStrings() ? string2 : XML.stringToValue(string2));
                    continue;
                }
                if (object != LT) continue;
                if (n == xMLParserConfiguration.getMaxNestingDepth()) {
                    throw xMLTokener.syntaxError("Maximum nesting depth of " + xMLParserConfiguration.getMaxNestingDepth() + " reached");
                }
                if (XML.parse(xMLTokener, jSONObject2, string4, xMLParserConfiguration, n + 1)) break;
            }
            if (xMLParserConfiguration.getForceList().contains(string4)) {
                if (jSONObject2.length() == 0) {
                    jSONObject.put(string4, new JSONArray());
                } else if (jSONObject2.length() == 1 && jSONObject2.opt(xMLParserConfiguration.getcDataTagName()) != null) {
                    jSONObject.append(string4, jSONObject2.opt(xMLParserConfiguration.getcDataTagName()));
                } else {
                    jSONObject.append(string4, jSONObject2);
                }
            } else if (jSONObject2.length() == 0) {
                jSONObject.accumulate(string4, "");
            } else if (jSONObject2.length() == 1 && jSONObject2.opt(xMLParserConfiguration.getcDataTagName()) != null) {
                jSONObject.accumulate(string4, jSONObject2.opt(xMLParserConfiguration.getcDataTagName()));
            } else {
                jSONObject.accumulate(string4, jSONObject2);
            }
            return true;
        }
        throw xMLTokener.syntaxError("Misshaped tag");
    }

    public static Object stringToValue(String string, XMLXsiTypeConverter<?> xMLXsiTypeConverter) {
        if (xMLXsiTypeConverter != null) {
            return xMLXsiTypeConverter.convert(string);
        }
        return XML.stringToValue(string);
    }

    public static Object stringToValue(String string) {
        if ("".equals(string)) {
            return string;
        }
        if ("true".equalsIgnoreCase(string)) {
            return Boolean.TRUE;
        }
        if ("false".equalsIgnoreCase(string)) {
            return Boolean.FALSE;
        }
        if ("null".equalsIgnoreCase(string)) {
            return JSONObject.NULL;
        }
        char c = string.charAt(0);
        if (c >= '0' && c <= '9' || c == '-') {
            try {
                return XML.stringToNumber(string);
            } catch (Exception exception) {
                // empty catch block
            }
        }
        return string;
    }

    private static Number stringToNumber(String string) throws NumberFormatException {
        char c = string.charAt(0);
        if (c >= '0' && c <= '9' || c == '-') {
            BigInteger bigInteger;
            char c2;
            if (XML.isDecimalNotation(string)) {
                try {
                    BigDecimal bigDecimal = new BigDecimal(string);
                    if (c == '-' && BigDecimal.ZERO.compareTo(bigDecimal) == 0) {
                        return -0.0;
                    }
                    return bigDecimal;
                } catch (NumberFormatException numberFormatException) {
                    try {
                        Double d = Double.valueOf(string);
                        if (d.isNaN() || d.isInfinite()) {
                            throw new NumberFormatException("val [" + string + "] is not a valid number.");
                        }
                        return d;
                    } catch (NumberFormatException numberFormatException2) {
                        throw new NumberFormatException("val [" + string + "] is not a valid number.");
                    }
                }
            }
            if (c == '0' && string.length() > 1) {
                c2 = string.charAt(1);
                if (c2 >= '0' && c2 <= '9') {
                    throw new NumberFormatException("val [" + string + "] is not a valid number.");
                }
            } else if (c == '-' && string.length() > 2) {
                c2 = string.charAt(1);
                char c3 = string.charAt(2);
                if (c2 == '0' && c3 >= '0' && c3 <= '9') {
                    throw new NumberFormatException("val [" + string + "] is not a valid number.");
                }
            }
            if ((bigInteger = new BigInteger(string)).bitLength() <= 31) {
                return bigInteger.intValue();
            }
            if (bigInteger.bitLength() <= 63) {
                return bigInteger.longValue();
            }
            return bigInteger;
        }
        throw new NumberFormatException("val [" + string + "] is not a valid number.");
    }

    private static boolean isDecimalNotation(String string) {
        return string.indexOf(46) > -1 || string.indexOf(101) > -1 || string.indexOf(69) > -1 || "-0".equals(string);
    }

    public static JSONObject toJSONObject(String string) throws JSONException {
        return XML.toJSONObject(string, XMLParserConfiguration.ORIGINAL);
    }

    public static JSONObject toJSONObject(Reader reader) throws JSONException {
        return XML.toJSONObject(reader, XMLParserConfiguration.ORIGINAL);
    }

    public static JSONObject toJSONObject(Reader reader, boolean bl) throws JSONException {
        if (bl) {
            return XML.toJSONObject(reader, XMLParserConfiguration.KEEP_STRINGS);
        }
        return XML.toJSONObject(reader, XMLParserConfiguration.ORIGINAL);
    }

    public static JSONObject toJSONObject(Reader reader, XMLParserConfiguration xMLParserConfiguration) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        XMLTokener xMLTokener = new XMLTokener(reader);
        while (xMLTokener.more()) {
            xMLTokener.skipPast("<");
            if (!xMLTokener.more()) continue;
            XML.parse(xMLTokener, jSONObject, null, xMLParserConfiguration, 0);
        }
        return jSONObject;
    }

    public static JSONObject toJSONObject(String string, boolean bl) throws JSONException {
        return XML.toJSONObject((Reader)new StringReader(string), bl);
    }

    public static JSONObject toJSONObject(String string, XMLParserConfiguration xMLParserConfiguration) throws JSONException {
        return XML.toJSONObject((Reader)new StringReader(string), xMLParserConfiguration);
    }

    public static String toString(Object object) throws JSONException {
        return XML.toString(object, null, XMLParserConfiguration.ORIGINAL);
    }

    public static String toString(Object object, String string) {
        return XML.toString(object, string, XMLParserConfiguration.ORIGINAL);
    }

    public static String toString(Object object, String string, XMLParserConfiguration xMLParserConfiguration) throws JSONException {
        return XML.toString(object, string, xMLParserConfiguration, 0, 0);
    }

    private static String toString(Object object, String string, XMLParserConfiguration xMLParserConfiguration, int n, int n2) throws JSONException {
        String string2;
        StringBuilder stringBuilder = new StringBuilder();
        if (object instanceof JSONObject) {
            if (string != null) {
                stringBuilder.append(XML.indent(n2));
                stringBuilder.append('<');
                stringBuilder.append(string);
                stringBuilder.append('>');
                if (n > 0) {
                    stringBuilder.append("\n");
                    n2 += n;
                }
            }
            JSONObject jSONObject = (JSONObject)object;
            for (String string3 : jSONObject.keySet()) {
                Object object2;
                int n3;
                int n4;
                JSONArray jSONArray;
                Object object3 = jSONObject.opt(string3);
                if (object3 == null) {
                    object3 = "";
                } else if (object3.getClass().isArray()) {
                    object3 = new JSONArray(object3);
                }
                if (string3.equals(xMLParserConfiguration.getcDataTagName())) {
                    if (object3 instanceof JSONArray) {
                        jSONArray = (JSONArray)object3;
                        n4 = jSONArray.length();
                        for (n3 = 0; n3 < n4; ++n3) {
                            if (n3 > 0) {
                                stringBuilder.append('\n');
                            }
                            object2 = jSONArray.opt(n3);
                            stringBuilder.append(XML.escape(object2.toString()));
                        }
                        continue;
                    }
                    stringBuilder.append(XML.escape(object3.toString()));
                    continue;
                }
                if (object3 instanceof JSONArray) {
                    jSONArray = (JSONArray)object3;
                    n4 = jSONArray.length();
                    for (n3 = 0; n3 < n4; ++n3) {
                        object2 = jSONArray.opt(n3);
                        if (object2 instanceof JSONArray) {
                            stringBuilder.append('<');
                            stringBuilder.append(string3);
                            stringBuilder.append('>');
                            stringBuilder.append(XML.toString(object2, null, xMLParserConfiguration, n, n2));
                            stringBuilder.append("</");
                            stringBuilder.append(string3);
                            stringBuilder.append('>');
                            continue;
                        }
                        stringBuilder.append(XML.toString(object2, string3, xMLParserConfiguration, n, n2));
                    }
                    continue;
                }
                if ("".equals(object3)) {
                    stringBuilder.append(XML.indent(n2));
                    stringBuilder.append('<');
                    stringBuilder.append(string3);
                    stringBuilder.append("/>");
                    if (n <= 0) continue;
                    stringBuilder.append("\n");
                    continue;
                }
                stringBuilder.append(XML.toString(object3, string3, xMLParserConfiguration, n, n2));
            }
            if (string != null) {
                stringBuilder.append(XML.indent(n2 - n));
                stringBuilder.append("</");
                stringBuilder.append(string);
                stringBuilder.append('>');
                if (n > 0) {
                    stringBuilder.append("\n");
                }
            }
            return stringBuilder.toString();
        }
        if (object != null && (object instanceof JSONArray || object.getClass().isArray())) {
            JSONArray jSONArray = object.getClass().isArray() ? new JSONArray(object) : (JSONArray)object;
            int n5 = jSONArray.length();
            for (int i = 0; i < n5; ++i) {
                Object object4 = jSONArray.opt(i);
                stringBuilder.append(XML.toString(object4, string == null ? "array" : string, xMLParserConfiguration, n, n2));
            }
            return stringBuilder.toString();
        }
        String string4 = string2 = object == null ? "null" : XML.escape(object.toString());
        if (string == null) {
            return XML.indent(n2) + "\"" + string2 + "\"" + (n > 0 ? "\n" : "");
        }
        if (string2.length() == 0) {
            return XML.indent(n2) + "<" + string + "/>" + (n > 0 ? "\n" : "");
        }
        return XML.indent(n2) + "<" + string + ">" + string2 + "</" + string + ">" + (n > 0 ? "\n" : "");
    }

    public static String toString(Object object, int n) {
        return XML.toString(object, null, XMLParserConfiguration.ORIGINAL, n);
    }

    public static String toString(Object object, String string, int n) {
        return XML.toString(object, string, XMLParserConfiguration.ORIGINAL, n);
    }

    public static String toString(Object object, String string, XMLParserConfiguration xMLParserConfiguration, int n) throws JSONException {
        return XML.toString(object, string, xMLParserConfiguration, n, 0);
    }

    private static final String indent(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < n; ++i) {
            stringBuilder.append(' ');
        }
        return stringBuilder.toString();
    }
}

