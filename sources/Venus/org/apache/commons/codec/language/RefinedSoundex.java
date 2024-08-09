/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.codec.language;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;
import org.apache.commons.codec.language.SoundexUtils;

public class RefinedSoundex
implements StringEncoder {
    public static final String US_ENGLISH_MAPPING_STRING = "01360240043788015936020505";
    private static final char[] US_ENGLISH_MAPPING = "01360240043788015936020505".toCharArray();
    private final char[] soundexMapping;
    public static final RefinedSoundex US_ENGLISH = new RefinedSoundex();

    public RefinedSoundex() {
        this.soundexMapping = US_ENGLISH_MAPPING;
    }

    public RefinedSoundex(char[] cArray) {
        this.soundexMapping = new char[cArray.length];
        System.arraycopy(cArray, 0, this.soundexMapping, 0, cArray.length);
    }

    public RefinedSoundex(String string) {
        this.soundexMapping = string.toCharArray();
    }

    public int difference(String string, String string2) throws EncoderException {
        return SoundexUtils.difference(this, string, string2);
    }

    @Override
    public Object encode(Object object) throws EncoderException {
        if (!(object instanceof String)) {
            throw new EncoderException("Parameter supplied to RefinedSoundex encode is not of type java.lang.String");
        }
        return this.soundex((String)object);
    }

    @Override
    public String encode(String string) {
        return this.soundex(string);
    }

    char getMappingCode(char c) {
        if (!Character.isLetter(c)) {
            return '\u0001';
        }
        return this.soundexMapping[Character.toUpperCase(c) - 65];
    }

    public String soundex(String string) {
        if (string == null) {
            return null;
        }
        if ((string = SoundexUtils.clean(string)).length() == 0) {
            return string;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string.charAt(0));
        char c = '*';
        for (int i = 0; i < string.length(); ++i) {
            char c2 = this.getMappingCode(string.charAt(i));
            if (c2 == c) continue;
            if (c2 != '\u0000') {
                stringBuilder.append(c2);
            }
            c = c2;
        }
        return stringBuilder.toString();
    }
}

