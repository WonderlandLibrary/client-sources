/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.message;

import org.apache.http.HeaderElement;
import org.apache.http.NameValuePair;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.message.HeaderValueFormatter;
import org.apache.http.util.Args;
import org.apache.http.util.CharArrayBuffer;

@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class BasicHeaderValueFormatter
implements HeaderValueFormatter {
    @Deprecated
    public static final BasicHeaderValueFormatter DEFAULT = new BasicHeaderValueFormatter();
    public static final BasicHeaderValueFormatter INSTANCE = new BasicHeaderValueFormatter();
    public static final String SEPARATORS = " ;,:@()<>\\\"/[]?={}\t";
    public static final String UNSAFE_CHARS = "\"\\";

    public static String formatElements(HeaderElement[] headerElementArray, boolean bl, HeaderValueFormatter headerValueFormatter) {
        return (headerValueFormatter != null ? headerValueFormatter : INSTANCE).formatElements(null, headerElementArray, bl).toString();
    }

    @Override
    public CharArrayBuffer formatElements(CharArrayBuffer charArrayBuffer, HeaderElement[] headerElementArray, boolean bl) {
        Args.notNull(headerElementArray, "Header element array");
        int n = this.estimateElementsLen(headerElementArray);
        CharArrayBuffer charArrayBuffer2 = charArrayBuffer;
        if (charArrayBuffer2 == null) {
            charArrayBuffer2 = new CharArrayBuffer(n);
        } else {
            charArrayBuffer2.ensureCapacity(n);
        }
        for (int i = 0; i < headerElementArray.length; ++i) {
            if (i > 0) {
                charArrayBuffer2.append(", ");
            }
            this.formatHeaderElement(charArrayBuffer2, headerElementArray[i], bl);
        }
        return charArrayBuffer2;
    }

    protected int estimateElementsLen(HeaderElement[] headerElementArray) {
        if (headerElementArray == null || headerElementArray.length < 1) {
            return 1;
        }
        int n = (headerElementArray.length - 1) * 2;
        for (HeaderElement headerElement : headerElementArray) {
            n += this.estimateHeaderElementLen(headerElement);
        }
        return n;
    }

    public static String formatHeaderElement(HeaderElement headerElement, boolean bl, HeaderValueFormatter headerValueFormatter) {
        return (headerValueFormatter != null ? headerValueFormatter : INSTANCE).formatHeaderElement(null, headerElement, bl).toString();
    }

    @Override
    public CharArrayBuffer formatHeaderElement(CharArrayBuffer charArrayBuffer, HeaderElement headerElement, boolean bl) {
        int n;
        Args.notNull(headerElement, "Header element");
        int n2 = this.estimateHeaderElementLen(headerElement);
        CharArrayBuffer charArrayBuffer2 = charArrayBuffer;
        if (charArrayBuffer2 == null) {
            charArrayBuffer2 = new CharArrayBuffer(n2);
        } else {
            charArrayBuffer2.ensureCapacity(n2);
        }
        charArrayBuffer2.append(headerElement.getName());
        String string = headerElement.getValue();
        if (string != null) {
            charArrayBuffer2.append('=');
            this.doFormatValue(charArrayBuffer2, string, bl);
        }
        if ((n = headerElement.getParameterCount()) > 0) {
            for (int i = 0; i < n; ++i) {
                charArrayBuffer2.append("; ");
                this.formatNameValuePair(charArrayBuffer2, headerElement.getParameter(i), bl);
            }
        }
        return charArrayBuffer2;
    }

    protected int estimateHeaderElementLen(HeaderElement headerElement) {
        int n;
        if (headerElement == null) {
            return 1;
        }
        int n2 = headerElement.getName().length();
        String string = headerElement.getValue();
        if (string != null) {
            n2 += 3 + string.length();
        }
        if ((n = headerElement.getParameterCount()) > 0) {
            for (int i = 0; i < n; ++i) {
                n2 += 2 + this.estimateNameValuePairLen(headerElement.getParameter(i));
            }
        }
        return n2;
    }

    public static String formatParameters(NameValuePair[] nameValuePairArray, boolean bl, HeaderValueFormatter headerValueFormatter) {
        return (headerValueFormatter != null ? headerValueFormatter : INSTANCE).formatParameters(null, nameValuePairArray, bl).toString();
    }

    @Override
    public CharArrayBuffer formatParameters(CharArrayBuffer charArrayBuffer, NameValuePair[] nameValuePairArray, boolean bl) {
        Args.notNull(nameValuePairArray, "Header parameter array");
        int n = this.estimateParametersLen(nameValuePairArray);
        CharArrayBuffer charArrayBuffer2 = charArrayBuffer;
        if (charArrayBuffer2 == null) {
            charArrayBuffer2 = new CharArrayBuffer(n);
        } else {
            charArrayBuffer2.ensureCapacity(n);
        }
        for (int i = 0; i < nameValuePairArray.length; ++i) {
            if (i > 0) {
                charArrayBuffer2.append("; ");
            }
            this.formatNameValuePair(charArrayBuffer2, nameValuePairArray[i], bl);
        }
        return charArrayBuffer2;
    }

    protected int estimateParametersLen(NameValuePair[] nameValuePairArray) {
        if (nameValuePairArray == null || nameValuePairArray.length < 1) {
            return 1;
        }
        int n = (nameValuePairArray.length - 1) * 2;
        for (NameValuePair nameValuePair : nameValuePairArray) {
            n += this.estimateNameValuePairLen(nameValuePair);
        }
        return n;
    }

    public static String formatNameValuePair(NameValuePair nameValuePair, boolean bl, HeaderValueFormatter headerValueFormatter) {
        return (headerValueFormatter != null ? headerValueFormatter : INSTANCE).formatNameValuePair(null, nameValuePair, bl).toString();
    }

    @Override
    public CharArrayBuffer formatNameValuePair(CharArrayBuffer charArrayBuffer, NameValuePair nameValuePair, boolean bl) {
        Args.notNull(nameValuePair, "Name / value pair");
        int n = this.estimateNameValuePairLen(nameValuePair);
        CharArrayBuffer charArrayBuffer2 = charArrayBuffer;
        if (charArrayBuffer2 == null) {
            charArrayBuffer2 = new CharArrayBuffer(n);
        } else {
            charArrayBuffer2.ensureCapacity(n);
        }
        charArrayBuffer2.append(nameValuePair.getName());
        String string = nameValuePair.getValue();
        if (string != null) {
            charArrayBuffer2.append('=');
            this.doFormatValue(charArrayBuffer2, string, bl);
        }
        return charArrayBuffer2;
    }

    protected int estimateNameValuePairLen(NameValuePair nameValuePair) {
        if (nameValuePair == null) {
            return 1;
        }
        int n = nameValuePair.getName().length();
        String string = nameValuePair.getValue();
        if (string != null) {
            n += 3 + string.length();
        }
        return n;
    }

    protected void doFormatValue(CharArrayBuffer charArrayBuffer, String string, boolean bl) {
        int n;
        boolean bl2 = bl;
        if (!bl2) {
            for (n = 0; n < string.length() && !bl2; ++n) {
                bl2 = this.isSeparator(string.charAt(n));
            }
        }
        if (bl2) {
            charArrayBuffer.append('\"');
        }
        for (n = 0; n < string.length(); ++n) {
            char c = string.charAt(n);
            if (this.isUnsafe(c)) {
                charArrayBuffer.append('\\');
            }
            charArrayBuffer.append(c);
        }
        if (bl2) {
            charArrayBuffer.append('\"');
        }
    }

    protected boolean isSeparator(char c) {
        return SEPARATORS.indexOf(c) >= 0;
    }

    protected boolean isUnsafe(char c) {
        return UNSAFE_CHARS.indexOf(c) >= 0;
    }
}

