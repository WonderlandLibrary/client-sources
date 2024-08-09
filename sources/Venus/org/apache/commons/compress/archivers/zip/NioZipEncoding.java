/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.archivers.zip;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import org.apache.commons.compress.archivers.zip.ZipEncoding;
import org.apache.commons.compress.archivers.zip.ZipEncodingHelper;

class NioZipEncoding
implements ZipEncoding {
    private final Charset charset;

    public NioZipEncoding(Charset charset) {
        this.charset = charset;
    }

    public boolean canEncode(String string) {
        CharsetEncoder charsetEncoder = this.charset.newEncoder();
        charsetEncoder.onMalformedInput(CodingErrorAction.REPORT);
        charsetEncoder.onUnmappableCharacter(CodingErrorAction.REPORT);
        return charsetEncoder.canEncode(string);
    }

    public ByteBuffer encode(String string) {
        CharsetEncoder charsetEncoder = this.charset.newEncoder();
        charsetEncoder.onMalformedInput(CodingErrorAction.REPORT);
        charsetEncoder.onUnmappableCharacter(CodingErrorAction.REPORT);
        CharBuffer charBuffer = CharBuffer.wrap(string);
        ByteBuffer byteBuffer = ByteBuffer.allocate(string.length() + (string.length() + 1) / 2);
        while (charBuffer.remaining() > 0) {
            CoderResult coderResult = charsetEncoder.encode(charBuffer, byteBuffer, false);
            if (coderResult.isUnmappable() || coderResult.isMalformed()) {
                if (coderResult.length() * 6 > byteBuffer.remaining()) {
                    byteBuffer = ZipEncodingHelper.growBuffer(byteBuffer, byteBuffer.position() + coderResult.length() * 6);
                }
                for (int i = 0; i < coderResult.length(); ++i) {
                    ZipEncodingHelper.appendSurrogate(byteBuffer, charBuffer.get());
                }
                continue;
            }
            if (coderResult.isOverflow()) {
                byteBuffer = ZipEncodingHelper.growBuffer(byteBuffer, 0);
                continue;
            }
            if (!coderResult.isUnderflow()) continue;
            charsetEncoder.flush(byteBuffer);
            break;
        }
        byteBuffer.limit(byteBuffer.position());
        byteBuffer.rewind();
        return byteBuffer;
    }

    public String decode(byte[] byArray) throws IOException {
        return this.charset.newDecoder().onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT).decode(ByteBuffer.wrap(byArray)).toString();
    }
}

