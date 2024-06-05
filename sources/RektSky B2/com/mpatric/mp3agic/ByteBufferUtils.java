package com.mpatric.mp3agic;

import java.nio.*;

public class ByteBufferUtils
{
    public static String extractNullTerminatedString(final ByteBuffer byteBuffer) {
        final int position = byteBuffer.position();
        final byte[] array = new byte[byteBuffer.remaining()];
        byteBuffer.get(array);
        final String s = new String(array);
        final String substring = s.substring(0, s.indexOf(0));
        byteBuffer.position(position + substring.length() + 1);
        return substring;
    }
}
