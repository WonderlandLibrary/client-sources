/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.codec.language;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;
import org.apache.commons.codec.language.SoundexUtils;

public class Soundex
implements StringEncoder {
    public static final char SILENT_MARKER = '-';
    public static final String US_ENGLISH_MAPPING_STRING = "01230120022455012623010202";
    private static final char[] US_ENGLISH_MAPPING = "01230120022455012623010202".toCharArray();
    public static final Soundex US_ENGLISH = new Soundex();
    public static final Soundex US_ENGLISH_SIMPLIFIED = new Soundex("01230120022455012623010202", false);
    public static final Soundex US_ENGLISH_GENEALOGY = new Soundex("-123-12--22455-12623-1-2-2");
    @Deprecated
    private int maxLength = 4;
    private final char[] soundexMapping;
    private final boolean specialCaseHW;

    public Soundex() {
        this.soundexMapping = US_ENGLISH_MAPPING;
        this.specialCaseHW = true;
    }

    public Soundex(char[] cArray) {
        this.soundexMapping = new char[cArray.length];
        System.arraycopy(cArray, 0, this.soundexMapping, 0, cArray.length);
        this.specialCaseHW = !this.hasMarker(this.soundexMapping);
    }

    private boolean hasMarker(char[] cArray) {
        for (char c : cArray) {
            if (c != '-') continue;
            return false;
        }
        return true;
    }

    public Soundex(String string) {
        this.soundexMapping = string.toCharArray();
        this.specialCaseHW = !this.hasMarker(this.soundexMapping);
    }

    public Soundex(String string, boolean bl) {
        this.soundexMapping = string.toCharArray();
        this.specialCaseHW = bl;
    }

    public int difference(String string, String string2) throws EncoderException {
        return SoundexUtils.difference(this, string, string2);
    }

    @Override
    public Object encode(Object object) throws EncoderException {
        if (!(object instanceof String)) {
            throw new EncoderException("Parameter supplied to Soundex encode is not of type java.lang.String");
        }
        return this.soundex((String)object);
    }

    @Override
    public String encode(String string) {
        return this.soundex(string);
    }

    @Deprecated
    public int getMaxLength() {
        return this.maxLength;
    }

    private char map(char c) {
        int n = c - 65;
        if (n < 0 || n >= this.soundexMapping.length) {
            throw new IllegalArgumentException("The character is not mapped: " + c + " (index=" + n + ")");
        }
        return this.soundexMapping[n];
    }

    @Deprecated
    public void setMaxLength(int n) {
        this.maxLength = n;
    }

    public String soundex(String string) {
        if (string == null) {
            return null;
        }
        if ((string = SoundexUtils.clean(string)).length() == 0) {
            return string;
        }
        char[] cArray = new char[]{'0', '0', '0', '0'};
        int n = 0;
        char c = string.charAt(0);
        cArray[n++] = c;
        char c2 = this.map(c);
        for (int i = 1; i < string.length() && n < cArray.length; ++i) {
            char c3;
            char c4 = string.charAt(i);
            if (this.specialCaseHW && (c4 == 'H' || c4 == 'W') || (c3 = this.map(c4)) == '-') continue;
            if (c3 != '0' && c3 != c2) {
                cArray[n++] = c3;
            }
            c2 = c3;
        }
        return new String(cArray);
    }
}

