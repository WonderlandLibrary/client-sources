package ClassSub;

import java.nio.*;
import java.util.*;
import java.io.*;
import java.util.zip.*;

public class Class27
{
    public static Class292 ALPHA;
    public static Class292 LUMINANCE;
    public static Class292 LUMINANCE_ALPHA;
    public static Class292 RGB;
    public static Class292 RGBA;
    public static Class292 BGRA;
    public static Class292 ABGR;
    private static final byte[] SIGNATURE;
    private static final int IHDR = 1229472850;
    private static final int PLTE = 1347179589;
    private static final int tRNS = 1951551059;
    private static final int IDAT = 1229209940;
    private static final int IEND = 1229278788;
    private static final byte COLOR_GREYSCALE = 0;
    private static final byte COLOR_TRUECOLOR = 2;
    private static final byte COLOR_INDEXED = 3;
    private static final byte COLOR_GREYALPHA = 4;
    private static final byte COLOR_TRUEALPHA = 6;
    private final InputStream input;
    private final CRC32 crc;
    private final byte[] buffer;
    private int chunkLength;
    private int chunkType;
    private int chunkRemaining;
    private int width;
    private int height;
    private int bitdepth;
    private int colorType;
    private int bytesPerPixel;
    private byte[] palette;
    private byte[] paletteA;
    private byte[] transPixel;
    
    
    public Class27(final InputStream input) throws IOException {
        this.input = input;
        this.crc = new CRC32();
        this.readFully(this.buffer = new byte[4096], 0, Class27.SIGNATURE.length);
        if (!checkSignature(this.buffer)) {
            throw new IOException("Not a valid PNG file");
        }
        this.openChunk(1229472850);
        this.readIHDR();
        this.closeChunk();
    Label_0132:
        while (true) {
            this.openChunk();
            switch (this.chunkType) {
                case 1229209940: {
                    break Label_0132;
                }
                case 1347179589: {
                    this.readPLTE();
                    break;
                }
                case 1951551059: {
                    this.readtRNS();
                    break;
                }
            }
            this.closeChunk();
        }
        if (this.colorType == 3 && this.palette == null) {
            throw new IOException("Missing PLTE chunk");
        }
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public boolean hasAlpha() {
        return this.colorType == 6 || this.paletteA != null || this.transPixel != null;
    }
    
    public boolean isRGB() {
        return this.colorType == 6 || this.colorType == 2 || this.colorType == 3;
    }
    
    public Class292 decideTextureFormat(final Class292 class292) {
        switch (this.colorType) {
            case 2: {
                if (class292 == Class27.ABGR || class292 == Class27.RGBA || class292 == Class27.BGRA || class292 == Class27.RGB) {
                    return class292;
                }
                return Class27.RGB;
            }
            case 6: {
                if (class292 == Class27.ABGR || class292 == Class27.RGBA || class292 == Class27.BGRA || class292 == Class27.RGB) {
                    return class292;
                }
                return Class27.RGBA;
            }
            case 0: {
                if (class292 == Class27.LUMINANCE || class292 == Class27.ALPHA) {
                    return class292;
                }
                return Class27.LUMINANCE;
            }
            case 4: {
                return Class27.LUMINANCE_ALPHA;
            }
            case 3: {
                if (class292 == Class27.ABGR || class292 == Class27.RGBA || class292 == Class27.BGRA) {
                    return class292;
                }
                return Class27.RGBA;
            }
            default: {
                throw new UnsupportedOperationException("Not yet implemented");
            }
        }
    }
    
    public void decode(final ByteBuffer byteBuffer, final int n, final Class292 class292) throws IOException {
        final int position = byteBuffer.position();
        final int n2 = (this.width * this.bitdepth + 7) / 8 * this.bytesPerPixel;
        byte[] array = new byte[n2 + 1];
        byte[] array2 = new byte[n2 + 1];
        byte[] array3 = (byte[])((this.bitdepth < 8) ? new byte[this.width + 1] : null);
        final Inflater inflater = new Inflater();
        try {
            for (int i = 0; i < this.height; ++i) {
                this.readChunkUnzip(inflater, array, 0, array.length);
                this.unfilter(array, array2);
                byteBuffer.position(position + i * n);
                switch (this.colorType) {
                    case 2: {
                        if (class292 == Class27.ABGR) {
                            this.copyRGBtoABGR(byteBuffer, array);
                            break;
                        }
                        if (class292 == Class27.RGBA) {
                            this.copyRGBtoRGBA(byteBuffer, array);
                            break;
                        }
                        if (class292 == Class27.BGRA) {
                            this.copyRGBtoBGRA(byteBuffer, array);
                            break;
                        }
                        if (class292 == Class27.RGB) {
                            this.copy(byteBuffer, array);
                            break;
                        }
                        throw new UnsupportedOperationException("Unsupported format for this image");
                    }
                    case 6: {
                        if (class292 == Class27.ABGR) {
                            this.copyRGBAtoABGR(byteBuffer, array);
                            break;
                        }
                        if (class292 == Class27.RGBA) {
                            this.copy(byteBuffer, array);
                            break;
                        }
                        if (class292 == Class27.BGRA) {
                            this.copyRGBAtoBGRA(byteBuffer, array);
                            break;
                        }
                        if (class292 == Class27.RGB) {
                            this.copyRGBAtoRGB(byteBuffer, array);
                            break;
                        }
                        throw new UnsupportedOperationException("Unsupported format for this image");
                    }
                    case 0: {
                        if (class292 == Class27.LUMINANCE || class292 == Class27.ALPHA) {
                            this.copy(byteBuffer, array);
                            break;
                        }
                        throw new UnsupportedOperationException("Unsupported format for this image");
                    }
                    case 4: {
                        if (class292 == Class27.LUMINANCE_ALPHA) {
                            this.copy(byteBuffer, array);
                            break;
                        }
                        throw new UnsupportedOperationException("Unsupported format for this image");
                    }
                    case 3: {
                        switch (this.bitdepth) {
                            case 8: {
                                array3 = array;
                                break;
                            }
                            case 4: {
                                this.expand4(array, array3);
                                break;
                            }
                            case 2: {
                                this.expand2(array, array3);
                                break;
                            }
                            case 1: {
                                this.expand1(array, array3);
                                break;
                            }
                            default: {
                                throw new UnsupportedOperationException("Unsupported bitdepth for this image");
                            }
                        }
                        if (class292 == Class27.ABGR) {
                            this.copyPALtoABGR(byteBuffer, array3);
                            break;
                        }
                        if (class292 == Class27.RGBA) {
                            this.copyPALtoRGBA(byteBuffer, array3);
                            break;
                        }
                        if (class292 == Class27.BGRA) {
                            this.copyPALtoBGRA(byteBuffer, array3);
                            break;
                        }
                        throw new UnsupportedOperationException("Unsupported format for this image");
                    }
                    default: {
                        throw new UnsupportedOperationException("Not yet implemented");
                    }
                }
                final byte[] array4 = array;
                array = array2;
                array2 = array4;
            }
        }
        finally {
            inflater.end();
        }
    }
    
    private void copy(final ByteBuffer byteBuffer, final byte[] array) {
        byteBuffer.put(array, 1, array.length - 1);
    }
    
    private void copyRGBtoABGR(final ByteBuffer byteBuffer, final byte[] array) {
        if (this.transPixel != null) {
            final byte b = this.transPixel[1];
            final byte b2 = this.transPixel[3];
            final byte b3 = this.transPixel[5];
            for (int i = 1; i < array.length; i += 3) {
                final byte b4 = array[i];
                final byte b5 = array[i + 1];
                final byte b6 = array[i + 2];
                byte b7 = -1;
                if (b4 == b && b5 == b2 && b6 == b3) {
                    b7 = 0;
                }
                byteBuffer.put(b7).put(b6).put(b5).put(b4);
            }
        }
        else {
            for (int j = 1; j < array.length; j += 3) {
                byteBuffer.put((byte)(-1)).put(array[j + 2]).put(array[j + 1]).put(array[j]);
            }
        }
    }
    
    private void copyRGBtoRGBA(final ByteBuffer byteBuffer, final byte[] array) {
        if (this.transPixel != null) {
            final byte b = this.transPixel[1];
            final byte b2 = this.transPixel[3];
            final byte b3 = this.transPixel[5];
            for (int i = 1; i < array.length; i += 3) {
                final byte b4 = array[i];
                final byte b5 = array[i + 1];
                final byte b6 = array[i + 2];
                byte b7 = -1;
                if (b4 == b && b5 == b2 && b6 == b3) {
                    b7 = 0;
                }
                byteBuffer.put(b4).put(b5).put(b6).put(b7);
            }
        }
        else {
            for (int j = 1; j < array.length; j += 3) {
                byteBuffer.put(array[j]).put(array[j + 1]).put(array[j + 2]).put((byte)(-1));
            }
        }
    }
    
    private void copyRGBtoBGRA(final ByteBuffer byteBuffer, final byte[] array) {
        if (this.transPixel != null) {
            final byte b = this.transPixel[1];
            final byte b2 = this.transPixel[3];
            final byte b3 = this.transPixel[5];
            for (int i = 1; i < array.length; i += 3) {
                final byte b4 = array[i];
                final byte b5 = array[i + 1];
                final byte b6 = array[i + 2];
                byte b7 = -1;
                if (b4 == b && b5 == b2 && b6 == b3) {
                    b7 = 0;
                }
                byteBuffer.put(b6).put(b5).put(b4).put(b7);
            }
        }
        else {
            for (int j = 1; j < array.length; j += 3) {
                byteBuffer.put(array[j + 2]).put(array[j + 1]).put(array[j]).put((byte)(-1));
            }
        }
    }
    
    private void copyRGBAtoABGR(final ByteBuffer byteBuffer, final byte[] array) {
        for (int i = 1; i < array.length; i += 4) {
            byteBuffer.put(array[i + 3]).put(array[i + 2]).put(array[i + 1]).put(array[i]);
        }
    }
    
    private void copyRGBAtoBGRA(final ByteBuffer byteBuffer, final byte[] array) {
        for (int i = 1; i < array.length; i += 4) {
            byteBuffer.put(array[i + 2]).put(array[i + 1]).put(array[i + 0]).put(array[i + 3]);
        }
    }
    
    private void copyRGBAtoRGB(final ByteBuffer byteBuffer, final byte[] array) {
        for (int i = 1; i < array.length; i += 4) {
            byteBuffer.put(array[i]).put(array[i + 1]).put(array[i + 2]);
        }
    }
    
    private void copyPALtoABGR(final ByteBuffer byteBuffer, final byte[] array) {
        if (this.paletteA != null) {
            for (int i = 1; i < array.length; ++i) {
                final int n = array[i] & 0xFF;
                byteBuffer.put(this.paletteA[n]).put(this.palette[n * 3 + 2]).put(this.palette[n * 3 + 1]).put(this.palette[n * 3 + 0]);
            }
        }
        else {
            for (int j = 1; j < array.length; ++j) {
                final int n2 = array[j] & 0xFF;
                byteBuffer.put((byte)(-1)).put(this.palette[n2 * 3 + 2]).put(this.palette[n2 * 3 + 1]).put(this.palette[n2 * 3 + 0]);
            }
        }
    }
    
    private void copyPALtoRGBA(final ByteBuffer byteBuffer, final byte[] array) {
        if (this.paletteA != null) {
            for (int i = 1; i < array.length; ++i) {
                final int n = array[i] & 0xFF;
                byteBuffer.put(this.palette[n * 3 + 0]).put(this.palette[n * 3 + 1]).put(this.palette[n * 3 + 2]).put(this.paletteA[n]);
            }
        }
        else {
            for (int j = 1; j < array.length; ++j) {
                final int n2 = array[j] & 0xFF;
                byteBuffer.put(this.palette[n2 * 3 + 0]).put(this.palette[n2 * 3 + 1]).put(this.palette[n2 * 3 + 2]).put((byte)(-1));
            }
        }
    }
    
    private void copyPALtoBGRA(final ByteBuffer byteBuffer, final byte[] array) {
        if (this.paletteA != null) {
            for (int i = 1; i < array.length; ++i) {
                final int n = array[i] & 0xFF;
                byteBuffer.put(this.palette[n * 3 + 2]).put(this.palette[n * 3 + 1]).put(this.palette[n * 3 + 0]).put(this.paletteA[n]);
            }
        }
        else {
            for (int j = 1; j < array.length; ++j) {
                final int n2 = array[j] & 0xFF;
                byteBuffer.put(this.palette[n2 * 3 + 2]).put(this.palette[n2 * 3 + 1]).put(this.palette[n2 * 3 + 0]).put((byte)(-1));
            }
        }
    }
    
    private void expand4(final byte[] array, final byte[] array2) {
        int i = 1;
        final int length = array2.length;
        while (i < length) {
            final int n = array[1 + (i >> 1)] & 0xFF;
            switch (length - i) {
                default: {
                    array2[i + 1] = (byte)(n & 0xF);
                }
                case 1: {
                    array2[i] = (byte)(n >> 4);
                    i += 2;
                    continue;
                }
            }
        }
    }
    
    private void expand2(final byte[] array, final byte[] array2) {
        int i = 1;
        final int length = array2.length;
        while (i < length) {
            final int n = array[1 + (i >> 2)] & 0xFF;
            switch (length - i) {
                default: {
                    array2[i + 3] = (byte)(n & 0x3);
                }
                case 3: {
                    array2[i + 2] = (byte)(n >> 2 & 0x3);
                }
                case 2: {
                    array2[i + 1] = (byte)(n >> 4 & 0x3);
                }
                case 1: {
                    array2[i] = (byte)(n >> 6);
                    i += 4;
                    continue;
                }
            }
        }
    }
    
    private void expand1(final byte[] array, final byte[] array2) {
        int i = 1;
        final int length = array2.length;
        while (i < length) {
            final int n = array[1 + (i >> 3)] & 0xFF;
            switch (length - i) {
                default: {
                    array2[i + 7] = (byte)(n & 0x1);
                }
                case 7: {
                    array2[i + 6] = (byte)(n >> 1 & 0x1);
                }
                case 6: {
                    array2[i + 5] = (byte)(n >> 2 & 0x1);
                }
                case 5: {
                    array2[i + 4] = (byte)(n >> 3 & 0x1);
                }
                case 4: {
                    array2[i + 3] = (byte)(n >> 4 & 0x1);
                }
                case 3: {
                    array2[i + 2] = (byte)(n >> 5 & 0x1);
                }
                case 2: {
                    array2[i + 1] = (byte)(n >> 6 & 0x1);
                }
                case 1: {
                    array2[i] = (byte)(n >> 7);
                    i += 8;
                    continue;
                }
            }
        }
    }
    
    private void unfilter(final byte[] array, final byte[] array2) throws IOException {
        switch (array[0]) {
            case 0: {
                break;
            }
            case 1: {
                this.unfilterSub(array);
                break;
            }
            case 2: {
                this.unfilterUp(array, array2);
                break;
            }
            case 3: {
                this.unfilterAverage(array, array2);
                break;
            }
            case 4: {
                this.unfilterPaeth(array, array2);
                break;
            }
            default: {
                throw new IOException("invalide filter type in scanline: " + array[0]);
            }
        }
    }
    
    private void unfilterSub(final byte[] array) {
        final int bytesPerPixel = this.bytesPerPixel;
        for (int i = bytesPerPixel + 1; i < array.length; ++i) {
            final int n = i;
            array[n] += array[i - bytesPerPixel];
        }
    }
    
    private void unfilterUp(final byte[] array, final byte[] array2) {
        final int bytesPerPixel = this.bytesPerPixel;
        for (int i = 1; i < array.length; ++i) {
            final int n = i;
            array[n] += array2[i];
        }
    }
    
    private void unfilterAverage(final byte[] array, final byte[] array2) {
        int bytesPerPixel;
        int i;
        for (bytesPerPixel = this.bytesPerPixel, i = 1; i <= bytesPerPixel; ++i) {
            final int n = i;
            array[n] += (byte)((array2[i] & 0xFF) >>> 1);
        }
        while (i < array.length) {
            final int n2 = i;
            array[n2] += (byte)((array2[i] & 0xFF) + (array[i - bytesPerPixel] & 0xFF) >>> 1);
            ++i;
        }
    }
    
    private void unfilterPaeth(final byte[] array, final byte[] array2) {
        int bytesPerPixel;
        int i;
        for (bytesPerPixel = this.bytesPerPixel, i = 1; i <= bytesPerPixel; ++i) {
            final int n = i;
            array[n] += array2[i];
        }
        while (i < array.length) {
            final int n2 = array[i - bytesPerPixel] & 0xFF;
            final int n3 = array2[i] & 0xFF;
            int n4 = array2[i - bytesPerPixel] & 0xFF;
            final int n5 = n2 + n3 - n4;
            int n6 = n5 - n2;
            if (n6 < 0) {
                n6 = -n6;
            }
            int n7 = n5 - n3;
            if (n7 < 0) {
                n7 = -n7;
            }
            int n8 = n5 - n4;
            if (n8 < 0) {
                n8 = -n8;
            }
            if (n6 <= n7 && n6 <= n8) {
                n4 = n2;
            }
            else if (n7 <= n8) {
                n4 = n3;
            }
            final int n9 = i;
            array[n9] += (byte)n4;
            ++i;
        }
    }
    
    private void readIHDR() throws IOException {
        this.checkChunkLength(13);
        this.readChunk(this.buffer, 0, 13);
        this.width = this.readInt(this.buffer, 0);
        this.height = this.readInt(this.buffer, 4);
        this.bitdepth = (this.buffer[8] & 0xFF);
        Label_0509: {
            switch (this.colorType = (this.buffer[9] & 0xFF)) {
                case 0: {
                    if (this.bitdepth != 8) {
                        throw new IOException("Unsupported bit depth: " + this.bitdepth);
                    }
                    this.bytesPerPixel = 1;
                    break;
                }
                case 4: {
                    if (this.bitdepth != 8) {
                        throw new IOException("Unsupported bit depth: " + this.bitdepth);
                    }
                    this.bytesPerPixel = 2;
                    break;
                }
                case 2: {
                    if (this.bitdepth != 8) {
                        throw new IOException("Unsupported bit depth: " + this.bitdepth);
                    }
                    this.bytesPerPixel = 3;
                    break;
                }
                case 6: {
                    if (this.bitdepth != 8) {
                        throw new IOException("Unsupported bit depth: " + this.bitdepth);
                    }
                    this.bytesPerPixel = 4;
                    break;
                }
                case 3: {
                    switch (this.bitdepth) {
                        case 1:
                        case 2:
                        case 4:
                        case 8: {
                            this.bytesPerPixel = 1;
                            break Label_0509;
                        }
                        default: {
                            throw new IOException("Unsupported bit depth: " + this.bitdepth);
                        }
                    }
                    break;
                }
                default: {
                    throw new IOException("unsupported color format: " + this.colorType);
                }
            }
        }
        if (this.buffer[10] != 0) {
            throw new IOException("unsupported compression method");
        }
        if (this.buffer[11] != 0) {
            throw new IOException("unsupported filtering method");
        }
        if (this.buffer[12] != 0) {
            throw new IOException("unsupported interlace method");
        }
    }
    
    private void readPLTE() throws IOException {
        final int n = this.chunkLength / 3;
        if (n < 1 || n > 256 || this.chunkLength % 3 != 0) {
            throw new IOException("PLTE chunk has wrong length");
        }
        this.readChunk(this.palette = new byte[n * 3], 0, this.palette.length);
    }
    
    private void readtRNS() throws IOException {
        switch (this.colorType) {
            case 0: {
                this.checkChunkLength(2);
                this.readChunk(this.transPixel = new byte[2], 0, 2);
                break;
            }
            case 2: {
                this.checkChunkLength(6);
                this.readChunk(this.transPixel = new byte[6], 0, 6);
                break;
            }
            case 3: {
                if (this.palette == null) {
                    throw new IOException("tRNS chunk without PLTE chunk");
                }
                Arrays.fill(this.paletteA = new byte[this.palette.length / 3], (byte)(-1));
                this.readChunk(this.paletteA, 0, this.paletteA.length);
                break;
            }
        }
    }
    
    private void closeChunk() throws IOException {
        if (this.chunkRemaining > 0) {
            this.skip(this.chunkRemaining + 4);
        }
        else {
            this.readFully(this.buffer, 0, 4);
            if ((int)this.crc.getValue() != this.readInt(this.buffer, 0)) {
                throw new IOException("Invalid CRC");
            }
        }
        this.chunkRemaining = 0;
        this.chunkLength = 0;
        this.chunkType = 0;
    }
    
    private void openChunk() throws IOException {
        this.readFully(this.buffer, 0, 8);
        this.chunkLength = this.readInt(this.buffer, 0);
        this.chunkType = this.readInt(this.buffer, 4);
        this.chunkRemaining = this.chunkLength;
        this.crc.reset();
        this.crc.update(this.buffer, 4, 4);
    }
    
    private void openChunk(final int n) throws IOException {
        this.openChunk();
        if (this.chunkType != n) {
            throw new IOException("Expected chunk: " + Integer.toHexString(n));
        }
    }
    
    private void checkChunkLength(final int n) throws IOException {
        if (this.chunkLength != n) {
            throw new IOException("Chunk has wrong size");
        }
    }
    
    private int readChunk(final byte[] array, final int n, int chunkRemaining) throws IOException {
        if (chunkRemaining > this.chunkRemaining) {
            chunkRemaining = this.chunkRemaining;
        }
        this.readFully(array, n, chunkRemaining);
        this.crc.update(array, n, chunkRemaining);
        this.chunkRemaining -= chunkRemaining;
        return chunkRemaining;
    }
    
    private void refillInflater(final Inflater inflater) throws IOException {
        while (this.chunkRemaining == 0) {
            this.closeChunk();
            this.openChunk(1229209940);
        }
        inflater.setInput(this.buffer, 0, this.readChunk(this.buffer, 0, this.buffer.length));
    }
    
    private void readChunkUnzip(final Inflater inflater, final byte[] array, int n, int i) throws IOException {
        Label_0004: {
            break Label_0004;
            try {
                do {
                    final int inflate = inflater.inflate(array, n, i);
                    if (inflate <= 0) {
                        if (inflater.finished()) {
                            throw new EOFException();
                        }
                        if (!inflater.needsInput()) {
                            throw new IOException("Can't inflate " + i + " bytes");
                        }
                        this.refillInflater(inflater);
                    }
                    else {
                        n += inflate;
                        i -= inflate;
                    }
                } while (i > 0);
            }
            catch (DataFormatException ex) {
                throw (IOException)new IOException("inflate error").initCause(ex);
            }
        }
    }
    
    private void readFully(final byte[] array, int n, int i) throws IOException {
        do {
            final int read = this.input.read(array, n, i);
            if (read < 0) {
                throw new EOFException();
            }
            n += read;
            i -= read;
        } while (i > 0);
    }
    
    private int readInt(final byte[] array, final int n) {
        return array[n] << 24 | (array[n + 1] & 0xFF) << 16 | (array[n + 2] & 0xFF) << 8 | (array[n + 3] & 0xFF);
    }
    
    private void skip(long n) throws IOException {
        while (n > 0L) {
            final long skip = this.input.skip(n);
            if (skip < 0L) {
                throw new EOFException();
            }
            n -= skip;
        }
    }
    
    private static boolean checkSignature(final byte[] array) {
        for (int i = 0; i < Class27.SIGNATURE.length; ++i) {
            if (array[i] != Class27.SIGNATURE[i]) {
                return false;
            }
        }
        return true;
    }
    
    static {
        Class27.ALPHA = new Class292(1, true, null);
        Class27.LUMINANCE = new Class292(1, false, null);
        Class27.LUMINANCE_ALPHA = new Class292(2, true, null);
        Class27.RGB = new Class292(3, false, null);
        Class27.RGBA = new Class292(4, true, null);
        Class27.BGRA = new Class292(4, true, null);
        Class27.ABGR = new Class292(4, true, null);
        SIGNATURE = new byte[] { -119, 80, 78, 71, 13, 10, 26, 10 };
    }
    
    public static class Class292
    {
        final int numComponents;
        final boolean hasAlpha;
        
        
        private Class292(final int numComponents, final boolean hasAlpha) {
            this.numComponents = numComponents;
            this.hasAlpha = hasAlpha;
        }
        
        public int getNumComponents() {
            return this.numComponents;
        }
        
        public boolean isHasAlpha() {
            return this.hasAlpha;
        }
        
        Class292(final int n, final boolean b, final Class52 class52) {
            this(n, b);
        }
    }
}
