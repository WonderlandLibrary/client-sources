package com.mpatric.mp3agic;

import java.util.*;
import java.nio.charset.*;
import java.nio.*;

public class EncodedText
{
    public static final byte TEXT_ENCODING_ISO_8859_1 = 0;
    public static final byte TEXT_ENCODING_UTF_16 = 1;
    public static final byte TEXT_ENCODING_UTF_16BE = 2;
    public static final byte TEXT_ENCODING_UTF_8 = 3;
    public static final String CHARSET_ISO_8859_1 = "ISO-8859-1";
    public static final String CHARSET_UTF_16 = "UTF-16LE";
    public static final String CHARSET_UTF_16BE = "UTF-16BE";
    public static final String CHARSET_UTF_8 = "UTF-8";
    private static final String[] characterSets;
    private static final byte[] textEncodingFallback;
    private static final byte[][] boms;
    private static final byte[][] terminators;
    private byte[] value;
    private byte textEncoding;
    
    public EncodedText(final byte textEncoding, final byte[] value) {
        if (textEncoding == 1 && textEncodingForBytesFromBOM(value) == 2) {
            this.textEncoding = 2;
        }
        else {
            this.textEncoding = textEncoding;
        }
        this.value = value;
        this.stripBomAndTerminator();
    }
    
    public EncodedText(final String s) throws IllegalArgumentException {
        for (final byte textEncoding : EncodedText.textEncodingFallback) {
            this.textEncoding = textEncoding;
            this.value = stringToBytes(s, characterSetForTextEncoding(textEncoding));
            if (this.value != null && this.toString() != null) {
                this.stripBomAndTerminator();
                return;
            }
        }
        throw new IllegalArgumentException("Invalid string, could not find appropriate encoding");
    }
    
    public EncodedText(final String s, final byte b) throws IllegalArgumentException, CharacterCodingException {
        this(s);
        this.setTextEncoding(b, true);
    }
    
    public EncodedText(final byte textEncoding, final String s) {
        this.textEncoding = textEncoding;
        this.value = stringToBytes(s, characterSetForTextEncoding(textEncoding));
        this.stripBomAndTerminator();
    }
    
    public EncodedText(final byte[] array) {
        this(textEncodingForBytesFromBOM(array), array);
    }
    
    private static byte textEncodingForBytesFromBOM(final byte[] array) {
        if (array.length >= 2 && array[0] == -1 && array[1] == -2) {
            return 1;
        }
        if (array.length >= 2 && array[0] == -2 && array[1] == -1) {
            return 2;
        }
        if (array.length >= 3 && array[0] == -17 && array[1] == -69 && array[2] == -65) {
            return 3;
        }
        return 0;
    }
    
    private static String characterSetForTextEncoding(final byte b) {
        try {
            return EncodedText.characterSets[b];
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            throw new IllegalArgumentException("Invalid text encoding " + b);
        }
    }
    
    private void stripBomAndTerminator() {
        int n = 0;
        if (this.value.length >= 2 && ((this.value[0] == -2 && this.value[1] == -1) || (this.value[0] == -1 && this.value[1] == -2))) {
            n = 2;
        }
        else if (this.value.length >= 3 && this.value[0] == -17 && this.value[1] == -69 && this.value[2] == -65) {
            n = 3;
        }
        int length = 0;
        final byte[] array = EncodedText.terminators[this.textEncoding];
        if (this.value.length - n >= array.length) {
            boolean b = true;
            for (int i = 0; i < array.length; ++i) {
                if (this.value[this.value.length - array.length + i] != array[i]) {
                    b = false;
                    break;
                }
            }
            if (b) {
                length = array.length;
            }
        }
        if (n + length > 0) {
            final int n2 = this.value.length - n - length;
            final byte[] value = new byte[n2];
            if (n2 > 0) {
                System.arraycopy(this.value, n, value, 0, value.length);
            }
            this.value = value;
        }
    }
    
    public byte getTextEncoding() {
        return this.textEncoding;
    }
    
    public void setTextEncoding(final byte b) throws CharacterCodingException {
        this.setTextEncoding(b, true);
    }
    
    public void setTextEncoding(final byte textEncoding, final boolean b) throws CharacterCodingException {
        if (this.textEncoding != textEncoding) {
            final byte[] charBufferToBytes = charBufferToBytes(bytesToCharBuffer(this.value, characterSetForTextEncoding(this.textEncoding)), characterSetForTextEncoding(textEncoding));
            this.textEncoding = textEncoding;
            this.value = charBufferToBytes;
        }
    }
    
    public byte[] getTerminator() {
        return EncodedText.terminators[this.textEncoding];
    }
    
    public byte[] toBytes() {
        return this.toBytes(false, false);
    }
    
    public byte[] toBytes(final boolean b) {
        return this.toBytes(b, false);
    }
    
    public byte[] toBytes(final boolean b, final boolean b2) {
        characterSetForTextEncoding(this.textEncoding);
        final int n = this.value.length + (b ? EncodedText.boms[this.textEncoding].length : 0) + (b2 ? this.getTerminator().length : 0);
        if (n == this.value.length) {
            return this.value;
        }
        final byte[] array = new byte[n];
        int n2 = 0;
        if (b && EncodedText.boms[this.textEncoding].length > 0) {
            System.arraycopy(EncodedText.boms[this.textEncoding], 0, array, n2, EncodedText.boms[this.textEncoding].length);
            n2 += EncodedText.boms[this.textEncoding].length;
        }
        if (this.value.length > 0) {
            System.arraycopy(this.value, 0, array, n2, this.value.length);
            n2 += this.value.length;
        }
        if (b2) {
            final byte[] terminator = this.getTerminator();
            if (terminator.length > 0) {
                System.arraycopy(terminator, 0, array, n2, terminator.length);
            }
        }
        return array;
    }
    
    @Override
    public String toString() {
        try {
            return bytesToString(this.value, characterSetForTextEncoding(this.textEncoding));
        }
        catch (CharacterCodingException ex) {
            return null;
        }
    }
    
    public String getCharacterSet() {
        return characterSetForTextEncoding(this.textEncoding);
    }
    
    @Override
    public int hashCode() {
        return 31 * (31 * 1 + this.textEncoding) + Arrays.hashCode(this.value);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        final EncodedText encodedText = (EncodedText)o;
        return this.textEncoding == encodedText.textEncoding && Arrays.equals(this.value, encodedText.value);
    }
    
    private static String bytesToString(final byte[] array, final String s) throws CharacterCodingException {
        final String string = bytesToCharBuffer(array, s).toString();
        final int index = string.indexOf(0);
        if (index == -1) {
            return string;
        }
        return string.substring(0, index);
    }
    
    protected static CharBuffer bytesToCharBuffer(final byte[] array, final String s) throws CharacterCodingException {
        return Charset.forName(s).newDecoder().decode(ByteBuffer.wrap(array));
    }
    
    private static byte[] stringToBytes(final String s, final String s2) {
        try {
            return charBufferToBytes(CharBuffer.wrap(s), s2);
        }
        catch (CharacterCodingException ex) {
            return null;
        }
    }
    
    protected static byte[] charBufferToBytes(final CharBuffer charBuffer, final String s) throws CharacterCodingException {
        final ByteBuffer encode = Charset.forName(s).newEncoder().encode(charBuffer);
        return BufferTools.copyBuffer(encode.array(), 0, encode.limit());
    }
    
    static {
        characterSets = new String[] { "ISO-8859-1", "UTF-16LE", "UTF-16BE", "UTF-8" };
        textEncodingFallback = new byte[] { 0, 2, 1, 3 };
        boms = new byte[][] { new byte[0], { -1, -2 }, { -2, -1 }, new byte[0] };
        terminators = new byte[][] { { 0 }, { 0, 0 }, { 0, 0 }, { 0 } };
    }
}
