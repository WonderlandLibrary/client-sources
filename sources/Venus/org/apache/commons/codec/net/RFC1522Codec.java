/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.codec.net;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.binary.StringUtils;

abstract class RFC1522Codec {
    protected static final char SEP = '?';
    protected static final String POSTFIX = "?=";
    protected static final String PREFIX = "=?";

    RFC1522Codec() {
    }

    protected String encodeText(String string, Charset charset) throws EncoderException {
        if (string == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(PREFIX);
        stringBuilder.append(charset);
        stringBuilder.append('?');
        stringBuilder.append(this.getEncoding());
        stringBuilder.append('?');
        byte[] byArray = this.doEncoding(string.getBytes(charset));
        stringBuilder.append(StringUtils.newStringUsAscii(byArray));
        stringBuilder.append(POSTFIX);
        return stringBuilder.toString();
    }

    protected String encodeText(String string, String string2) throws EncoderException, UnsupportedEncodingException {
        if (string == null) {
            return null;
        }
        return this.encodeText(string, Charset.forName(string2));
    }

    protected String decodeText(String string) throws DecoderException, UnsupportedEncodingException {
        if (string == null) {
            return null;
        }
        if (!string.startsWith(PREFIX) || !string.endsWith(POSTFIX)) {
            throw new DecoderException("RFC 1522 violation: malformed encoded content");
        }
        int n = string.length() - 2;
        int n2 = 2;
        int n3 = string.indexOf(63, n2);
        if (n3 == n) {
            throw new DecoderException("RFC 1522 violation: charset token not found");
        }
        String string2 = string.substring(n2, n3);
        if (string2.equals("")) {
            throw new DecoderException("RFC 1522 violation: charset not specified");
        }
        n2 = n3 + 1;
        if ((n3 = string.indexOf(63, n2)) == n) {
            throw new DecoderException("RFC 1522 violation: encoding token not found");
        }
        String string3 = string.substring(n2, n3);
        if (!this.getEncoding().equalsIgnoreCase(string3)) {
            throw new DecoderException("This codec cannot decode " + string3 + " encoded content");
        }
        n2 = n3 + 1;
        n3 = string.indexOf(63, n2);
        byte[] byArray = StringUtils.getBytesUsAscii(string.substring(n2, n3));
        byArray = this.doDecoding(byArray);
        return new String(byArray, string2);
    }

    protected abstract String getEncoding();

    protected abstract byte[] doEncoding(byte[] var1) throws EncoderException;

    protected abstract byte[] doDecoding(byte[] var1) throws DecoderException;
}

