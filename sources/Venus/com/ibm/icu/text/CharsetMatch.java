/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetRecognizer;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class CharsetMatch
implements Comparable<CharsetMatch> {
    private int fConfidence;
    private byte[] fRawInput = null;
    private int fRawLength;
    private InputStream fInputStream = null;
    private String fCharsetName;
    private String fLang;

    public Reader getReader() {
        InputStream inputStream = this.fInputStream;
        if (inputStream == null) {
            inputStream = new ByteArrayInputStream(this.fRawInput, 0, this.fRawLength);
        }
        try {
            inputStream.reset();
            return new InputStreamReader(inputStream, this.getName());
        } catch (IOException iOException) {
            return null;
        }
    }

    public String getString() throws IOException {
        return this.getString(-1);
    }

    public String getString(int n) throws IOException {
        int n2;
        String string = null;
        if (this.fInputStream != null) {
            StringBuilder stringBuilder = new StringBuilder();
            char[] cArray = new char[1024];
            Reader reader = this.getReader();
            int n3 = n < 0 ? Integer.MAX_VALUE : n;
            int n4 = 0;
            while ((n4 = reader.read(cArray, 0, Math.min(n3, 1024))) >= 0) {
                stringBuilder.append(cArray, 0, n4);
                n3 -= n4;
            }
            reader.close();
            return stringBuilder.toString();
        }
        String string2 = this.getName();
        int n5 = n2 = string2.indexOf("_rtl") < 0 ? string2.indexOf("_ltr") : string2.indexOf("_rtl");
        if (n2 > 0) {
            string2 = string2.substring(0, n2);
        }
        string = new String(this.fRawInput, string2);
        return string;
    }

    public int getConfidence() {
        return this.fConfidence;
    }

    public String getName() {
        return this.fCharsetName;
    }

    public String getLanguage() {
        return this.fLang;
    }

    @Override
    public int compareTo(CharsetMatch charsetMatch) {
        int n = 0;
        if (this.fConfidence > charsetMatch.fConfidence) {
            n = 1;
        } else if (this.fConfidence < charsetMatch.fConfidence) {
            n = -1;
        }
        return n;
    }

    CharsetMatch(CharsetDetector charsetDetector, CharsetRecognizer charsetRecognizer, int n) {
        this.fConfidence = n;
        if (charsetDetector.fInputStream == null) {
            this.fRawInput = charsetDetector.fRawInput;
            this.fRawLength = charsetDetector.fRawLength;
        }
        this.fInputStream = charsetDetector.fInputStream;
        this.fCharsetName = charsetRecognizer.getName();
        this.fLang = charsetRecognizer.getLanguage();
    }

    CharsetMatch(CharsetDetector charsetDetector, CharsetRecognizer charsetRecognizer, int n, String string, String string2) {
        this.fConfidence = n;
        if (charsetDetector.fInputStream == null) {
            this.fRawInput = charsetDetector.fRawInput;
            this.fRawLength = charsetDetector.fRawLength;
        }
        this.fInputStream = charsetDetector.fInputStream;
        this.fCharsetName = string;
        this.fLang = string2;
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((CharsetMatch)object);
    }
}

