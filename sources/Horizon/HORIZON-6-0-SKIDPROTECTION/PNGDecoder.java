package HORIZON-6-0-SKIDPROTECTION;

import java.util.zip.DataFormatException;
import java.io.EOFException;
import java.util.Arrays;
import java.util.zip.Inflater;
import java.nio.ByteBuffer;
import java.io.IOException;
import java.util.zip.CRC32;
import java.io.InputStream;

public class PNGDecoder
{
    public static HorizonCode_Horizon_È HorizonCode_Horizon_È;
    public static HorizonCode_Horizon_È Â;
    public static HorizonCode_Horizon_È Ý;
    public static HorizonCode_Horizon_È Ø­áŒŠá;
    public static HorizonCode_Horizon_È Âµá€;
    public static HorizonCode_Horizon_È Ó;
    public static HorizonCode_Horizon_È à;
    private static final byte[] Ø;
    private static final int áŒŠÆ = 1229472850;
    private static final int áˆºÑ¢Õ = 1347179589;
    private static final int ÂµÈ = 1951551059;
    private static final int á = 1229209940;
    private static final int ˆÏ­ = 1229278788;
    private static final byte £á = 0;
    private static final byte Å = 2;
    private static final byte £à = 3;
    private static final byte µà = 4;
    private static final byte ˆà = 6;
    private final InputStream ¥Æ;
    private final CRC32 Ø­à;
    private final byte[] µÕ;
    private int Æ;
    private int Šáƒ;
    private int Ï­Ðƒà;
    private int áŒŠà;
    private int ŠÄ;
    private int Ñ¢á;
    private int ŒÏ;
    private int Çªà¢;
    private byte[] Ê;
    private byte[] ÇŽÉ;
    private byte[] ˆá;
    
    static {
        PNGDecoder.HorizonCode_Horizon_È = new HorizonCode_Horizon_È(1, true, null);
        PNGDecoder.Â = new HorizonCode_Horizon_È(1, false, null);
        PNGDecoder.Ý = new HorizonCode_Horizon_È(2, true, null);
        PNGDecoder.Ø­áŒŠá = new HorizonCode_Horizon_È(3, false, null);
        PNGDecoder.Âµá€ = new HorizonCode_Horizon_È(4, true, null);
        PNGDecoder.Ó = new HorizonCode_Horizon_È(4, true, null);
        PNGDecoder.à = new HorizonCode_Horizon_È(4, true, null);
        Ø = new byte[] { -119, 80, 78, 71, 13, 10, 26, 10 };
    }
    
    public PNGDecoder(final InputStream input) throws IOException {
        this.¥Æ = input;
        this.Ø­à = new CRC32();
        this.Â(this.µÕ = new byte[4096], 0, PNGDecoder.Ø.length);
        if (!Â(this.µÕ)) {
            throw new IOException("Not a valid PNG file");
        }
        this.HorizonCode_Horizon_È(1229472850);
        this.Âµá€();
        this.Ø();
    Label_0120:
        while (true) {
            this.áŒŠÆ();
            switch (this.Šáƒ) {
                case 1229209940: {
                    break Label_0120;
                }
                case 1347179589: {
                    this.Ó();
                    break;
                }
                case 1951551059: {
                    this.à();
                    break;
                }
            }
            this.Ø();
        }
        if (this.ŒÏ == 3 && this.Ê == null) {
            throw new IOException("Missing PLTE chunk");
        }
    }
    
    public int HorizonCode_Horizon_È() {
        return this.ŠÄ;
    }
    
    public int Â() {
        return this.áŒŠà;
    }
    
    public boolean Ý() {
        return this.ŒÏ == 6 || this.ÇŽÉ != null || this.ˆá != null;
    }
    
    public boolean Ø­áŒŠá() {
        return this.ŒÏ == 6 || this.ŒÏ == 2 || this.ŒÏ == 3;
    }
    
    public HorizonCode_Horizon_È HorizonCode_Horizon_È(final HorizonCode_Horizon_È fmt) {
        switch (this.ŒÏ) {
            case 2: {
                if (fmt == PNGDecoder.à || fmt == PNGDecoder.Âµá€ || fmt == PNGDecoder.Ó || fmt == PNGDecoder.Ø­áŒŠá) {
                    return fmt;
                }
                return PNGDecoder.Ø­áŒŠá;
            }
            case 6: {
                if (fmt == PNGDecoder.à || fmt == PNGDecoder.Âµá€ || fmt == PNGDecoder.Ó || fmt == PNGDecoder.Ø­áŒŠá) {
                    return fmt;
                }
                return PNGDecoder.Âµá€;
            }
            case 0: {
                if (fmt == PNGDecoder.Â || fmt == PNGDecoder.HorizonCode_Horizon_È) {
                    return fmt;
                }
                return PNGDecoder.Â;
            }
            case 4: {
                return PNGDecoder.Ý;
            }
            case 3: {
                if (fmt == PNGDecoder.à || fmt == PNGDecoder.Âµá€ || fmt == PNGDecoder.Ó) {
                    return fmt;
                }
                return PNGDecoder.Âµá€;
            }
            default: {
                throw new UnsupportedOperationException("Not yet implemented");
            }
        }
    }
    
    public void HorizonCode_Horizon_È(final ByteBuffer buffer, final int stride, final HorizonCode_Horizon_È fmt) throws IOException {
        final int offset = buffer.position();
        final int lineSize = (this.áŒŠà * this.Ñ¢á + 7) / 8 * this.Çªà¢;
        byte[] curLine = new byte[lineSize + 1];
        byte[] prevLine = new byte[lineSize + 1];
        byte[] palLine = (byte[])((this.Ñ¢á < 8) ? new byte[this.áŒŠà + 1] : null);
        final Inflater inflater = new Inflater();
        try {
            for (int y = 0; y < this.ŠÄ; ++y) {
                this.HorizonCode_Horizon_È(inflater, curLine, 0, curLine.length);
                this.Ø­áŒŠá(curLine, prevLine);
                buffer.position(offset + y * stride);
                switch (this.ŒÏ) {
                    case 2: {
                        if (fmt == PNGDecoder.à) {
                            this.Â(buffer, curLine);
                            break;
                        }
                        if (fmt == PNGDecoder.Âµá€) {
                            this.Ý(buffer, curLine);
                            break;
                        }
                        if (fmt == PNGDecoder.Ó) {
                            this.Ø­áŒŠá(buffer, curLine);
                            break;
                        }
                        if (fmt == PNGDecoder.Ø­áŒŠá) {
                            this.HorizonCode_Horizon_È(buffer, curLine);
                            break;
                        }
                        throw new UnsupportedOperationException("Unsupported format for this image");
                    }
                    case 6: {
                        if (fmt == PNGDecoder.à) {
                            this.Âµá€(buffer, curLine);
                            break;
                        }
                        if (fmt == PNGDecoder.Âµá€) {
                            this.HorizonCode_Horizon_È(buffer, curLine);
                            break;
                        }
                        if (fmt == PNGDecoder.Ó) {
                            this.Ó(buffer, curLine);
                            break;
                        }
                        if (fmt == PNGDecoder.Ø­áŒŠá) {
                            this.à(buffer, curLine);
                            break;
                        }
                        throw new UnsupportedOperationException("Unsupported format for this image");
                    }
                    case 0: {
                        if (fmt == PNGDecoder.Â || fmt == PNGDecoder.HorizonCode_Horizon_È) {
                            this.HorizonCode_Horizon_È(buffer, curLine);
                            break;
                        }
                        throw new UnsupportedOperationException("Unsupported format for this image");
                    }
                    case 4: {
                        if (fmt == PNGDecoder.Ý) {
                            this.HorizonCode_Horizon_È(buffer, curLine);
                            break;
                        }
                        throw new UnsupportedOperationException("Unsupported format for this image");
                    }
                    case 3: {
                        switch (this.Ñ¢á) {
                            case 8: {
                                palLine = curLine;
                                break;
                            }
                            case 4: {
                                this.HorizonCode_Horizon_È(curLine, palLine);
                                break;
                            }
                            case 2: {
                                this.Â(curLine, palLine);
                                break;
                            }
                            case 1: {
                                this.Ý(curLine, palLine);
                                break;
                            }
                            default: {
                                throw new UnsupportedOperationException("Unsupported bitdepth for this image");
                            }
                        }
                        if (fmt == PNGDecoder.à) {
                            this.Ø(buffer, palLine);
                            break;
                        }
                        if (fmt == PNGDecoder.Âµá€) {
                            this.áŒŠÆ(buffer, palLine);
                            break;
                        }
                        if (fmt == PNGDecoder.Ó) {
                            this.áˆºÑ¢Õ(buffer, palLine);
                            break;
                        }
                        throw new UnsupportedOperationException("Unsupported format for this image");
                    }
                    default: {
                        throw new UnsupportedOperationException("Not yet implemented");
                    }
                }
                final byte[] tmp = curLine;
                curLine = prevLine;
                prevLine = tmp;
            }
        }
        finally {
            inflater.end();
        }
        inflater.end();
    }
    
    private void HorizonCode_Horizon_È(final ByteBuffer buffer, final byte[] curLine) {
        buffer.put(curLine, 1, curLine.length - 1);
    }
    
    private void Â(final ByteBuffer buffer, final byte[] curLine) {
        if (this.ˆá != null) {
            final byte tr = this.ˆá[1];
            final byte tg = this.ˆá[3];
            final byte tb = this.ˆá[5];
            for (int i = 1, n = curLine.length; i < n; i += 3) {
                final byte r = curLine[i];
                final byte g = curLine[i + 1];
                final byte b = curLine[i + 2];
                byte a = -1;
                if (r == tr && g == tg && b == tb) {
                    a = 0;
                }
                buffer.put(a).put(b).put(g).put(r);
            }
        }
        else {
            for (int j = 1, n2 = curLine.length; j < n2; j += 3) {
                buffer.put((byte)(-1)).put(curLine[j + 2]).put(curLine[j + 1]).put(curLine[j]);
            }
        }
    }
    
    private void Ý(final ByteBuffer buffer, final byte[] curLine) {
        if (this.ˆá != null) {
            final byte tr = this.ˆá[1];
            final byte tg = this.ˆá[3];
            final byte tb = this.ˆá[5];
            for (int i = 1, n = curLine.length; i < n; i += 3) {
                final byte r = curLine[i];
                final byte g = curLine[i + 1];
                final byte b = curLine[i + 2];
                byte a = -1;
                if (r == tr && g == tg && b == tb) {
                    a = 0;
                }
                buffer.put(r).put(g).put(b).put(a);
            }
        }
        else {
            for (int j = 1, n2 = curLine.length; j < n2; j += 3) {
                buffer.put(curLine[j]).put(curLine[j + 1]).put(curLine[j + 2]).put((byte)(-1));
            }
        }
    }
    
    private void Ø­áŒŠá(final ByteBuffer buffer, final byte[] curLine) {
        if (this.ˆá != null) {
            final byte tr = this.ˆá[1];
            final byte tg = this.ˆá[3];
            final byte tb = this.ˆá[5];
            for (int i = 1, n = curLine.length; i < n; i += 3) {
                final byte r = curLine[i];
                final byte g = curLine[i + 1];
                final byte b = curLine[i + 2];
                byte a = -1;
                if (r == tr && g == tg && b == tb) {
                    a = 0;
                }
                buffer.put(b).put(g).put(r).put(a);
            }
        }
        else {
            for (int j = 1, n2 = curLine.length; j < n2; j += 3) {
                buffer.put(curLine[j + 2]).put(curLine[j + 1]).put(curLine[j]).put((byte)(-1));
            }
        }
    }
    
    private void Âµá€(final ByteBuffer buffer, final byte[] curLine) {
        for (int i = 1, n = curLine.length; i < n; i += 4) {
            buffer.put(curLine[i + 3]).put(curLine[i + 2]).put(curLine[i + 1]).put(curLine[i]);
        }
    }
    
    private void Ó(final ByteBuffer buffer, final byte[] curLine) {
        for (int i = 1, n = curLine.length; i < n; i += 4) {
            buffer.put(curLine[i + 2]).put(curLine[i + 1]).put(curLine[i + 0]).put(curLine[i + 3]);
        }
    }
    
    private void à(final ByteBuffer buffer, final byte[] curLine) {
        for (int i = 1, n = curLine.length; i < n; i += 4) {
            buffer.put(curLine[i]).put(curLine[i + 1]).put(curLine[i + 2]);
        }
    }
    
    private void Ø(final ByteBuffer buffer, final byte[] curLine) {
        if (this.ÇŽÉ != null) {
            for (int i = 1, n = curLine.length; i < n; ++i) {
                final int idx = curLine[i] & 0xFF;
                final byte r = this.Ê[idx * 3 + 0];
                final byte g = this.Ê[idx * 3 + 1];
                final byte b = this.Ê[idx * 3 + 2];
                final byte a = this.ÇŽÉ[idx];
                buffer.put(a).put(b).put(g).put(r);
            }
        }
        else {
            for (int i = 1, n = curLine.length; i < n; ++i) {
                final int idx = curLine[i] & 0xFF;
                final byte r = this.Ê[idx * 3 + 0];
                final byte g = this.Ê[idx * 3 + 1];
                final byte b = this.Ê[idx * 3 + 2];
                final byte a = -1;
                buffer.put(a).put(b).put(g).put(r);
            }
        }
    }
    
    private void áŒŠÆ(final ByteBuffer buffer, final byte[] curLine) {
        if (this.ÇŽÉ != null) {
            for (int i = 1, n = curLine.length; i < n; ++i) {
                final int idx = curLine[i] & 0xFF;
                final byte r = this.Ê[idx * 3 + 0];
                final byte g = this.Ê[idx * 3 + 1];
                final byte b = this.Ê[idx * 3 + 2];
                final byte a = this.ÇŽÉ[idx];
                buffer.put(r).put(g).put(b).put(a);
            }
        }
        else {
            for (int i = 1, n = curLine.length; i < n; ++i) {
                final int idx = curLine[i] & 0xFF;
                final byte r = this.Ê[idx * 3 + 0];
                final byte g = this.Ê[idx * 3 + 1];
                final byte b = this.Ê[idx * 3 + 2];
                final byte a = -1;
                buffer.put(r).put(g).put(b).put(a);
            }
        }
    }
    
    private void áˆºÑ¢Õ(final ByteBuffer buffer, final byte[] curLine) {
        if (this.ÇŽÉ != null) {
            for (int i = 1, n = curLine.length; i < n; ++i) {
                final int idx = curLine[i] & 0xFF;
                final byte r = this.Ê[idx * 3 + 0];
                final byte g = this.Ê[idx * 3 + 1];
                final byte b = this.Ê[idx * 3 + 2];
                final byte a = this.ÇŽÉ[idx];
                buffer.put(b).put(g).put(r).put(a);
            }
        }
        else {
            for (int i = 1, n = curLine.length; i < n; ++i) {
                final int idx = curLine[i] & 0xFF;
                final byte r = this.Ê[idx * 3 + 0];
                final byte g = this.Ê[idx * 3 + 1];
                final byte b = this.Ê[idx * 3 + 2];
                final byte a = -1;
                buffer.put(b).put(g).put(r).put(a);
            }
        }
    }
    
    private void HorizonCode_Horizon_È(final byte[] src, final byte[] dst) {
        int i = 1;
        final int n = dst.length;
        while (i < n) {
            final int val = src[1 + (i >> 1)] & 0xFF;
            switch (n - i) {
                default: {
                    dst[i + 1] = (byte)(val & 0xF);
                }
                case 1: {
                    dst[i] = (byte)(val >> 4);
                    i += 2;
                    continue;
                }
            }
        }
    }
    
    private void Â(final byte[] src, final byte[] dst) {
        int i = 1;
        final int n = dst.length;
        while (i < n) {
            final int val = src[1 + (i >> 2)] & 0xFF;
            switch (n - i) {
                default: {
                    dst[i + 3] = (byte)(val & 0x3);
                }
                case 3: {
                    dst[i + 2] = (byte)(val >> 2 & 0x3);
                }
                case 2: {
                    dst[i + 1] = (byte)(val >> 4 & 0x3);
                }
                case 1: {
                    dst[i] = (byte)(val >> 6);
                    i += 4;
                    continue;
                }
            }
        }
    }
    
    private void Ý(final byte[] src, final byte[] dst) {
        int i = 1;
        final int n = dst.length;
        while (i < n) {
            final int val = src[1 + (i >> 3)] & 0xFF;
            switch (n - i) {
                default: {
                    dst[i + 7] = (byte)(val & 0x1);
                }
                case 7: {
                    dst[i + 6] = (byte)(val >> 1 & 0x1);
                }
                case 6: {
                    dst[i + 5] = (byte)(val >> 2 & 0x1);
                }
                case 5: {
                    dst[i + 4] = (byte)(val >> 3 & 0x1);
                }
                case 4: {
                    dst[i + 3] = (byte)(val >> 4 & 0x1);
                }
                case 3: {
                    dst[i + 2] = (byte)(val >> 5 & 0x1);
                }
                case 2: {
                    dst[i + 1] = (byte)(val >> 6 & 0x1);
                }
                case 1: {
                    dst[i] = (byte)(val >> 7);
                    i += 8;
                    continue;
                }
            }
        }
    }
    
    private void Ø­áŒŠá(final byte[] curLine, final byte[] prevLine) throws IOException {
        switch (curLine[0]) {
            case 0: {
                break;
            }
            case 1: {
                this.HorizonCode_Horizon_È(curLine);
                break;
            }
            case 2: {
                this.Âµá€(curLine, prevLine);
                break;
            }
            case 3: {
                this.Ó(curLine, prevLine);
                break;
            }
            case 4: {
                this.à(curLine, prevLine);
                break;
            }
            default: {
                throw new IOException("invalide filter type in scanline: " + curLine[0]);
            }
        }
    }
    
    private void HorizonCode_Horizon_È(final byte[] curLine) {
        final int bpp = this.Çªà¢;
        for (int i = bpp + 1, n = curLine.length; i < n; ++i) {
            final int n2 = i;
            curLine[n2] += curLine[i - bpp];
        }
    }
    
    private void Âµá€(final byte[] curLine, final byte[] prevLine) {
        final int bpp = this.Çªà¢;
        for (int i = 1, n = curLine.length; i < n; ++i) {
            final int n2 = i;
            curLine[n2] += prevLine[i];
        }
    }
    
    private void Ó(final byte[] curLine, final byte[] prevLine) {
        int bpp;
        int i;
        for (bpp = this.Çªà¢, i = 1; i <= bpp; ++i) {
            final int n2 = i;
            curLine[n2] += (byte)((prevLine[i] & 0xFF) >>> 1);
        }
        for (int n = curLine.length; i < n; ++i) {
            final int n3 = i;
            curLine[n3] += (byte)((prevLine[i] & 0xFF) + (curLine[i - bpp] & 0xFF) >>> 1);
        }
    }
    
    private void à(final byte[] curLine, final byte[] prevLine) {
        int bpp;
        int i;
        for (bpp = this.Çªà¢, i = 1; i <= bpp; ++i) {
            final int n2 = i;
            curLine[n2] += prevLine[i];
        }
        for (int n = curLine.length; i < n; ++i) {
            final int a = curLine[i - bpp] & 0xFF;
            final int b = prevLine[i] & 0xFF;
            int c = prevLine[i - bpp] & 0xFF;
            final int p = a + b - c;
            int pa = p - a;
            if (pa < 0) {
                pa = -pa;
            }
            int pb = p - b;
            if (pb < 0) {
                pb = -pb;
            }
            int pc = p - c;
            if (pc < 0) {
                pc = -pc;
            }
            if (pa <= pb && pa <= pc) {
                c = a;
            }
            else if (pb <= pc) {
                c = b;
            }
            final int n3 = i;
            curLine[n3] += (byte)c;
        }
    }
    
    private void Âµá€() throws IOException {
        this.Â(13);
        this.HorizonCode_Horizon_È(this.µÕ, 0, 13);
        this.áŒŠà = this.HorizonCode_Horizon_È(this.µÕ, 0);
        this.ŠÄ = this.HorizonCode_Horizon_È(this.µÕ, 4);
        this.Ñ¢á = (this.µÕ[8] & 0xFF);
        Label_0416: {
            switch (this.ŒÏ = (this.µÕ[9] & 0xFF)) {
                case 0: {
                    if (this.Ñ¢á != 8) {
                        throw new IOException("Unsupported bit depth: " + this.Ñ¢á);
                    }
                    this.Çªà¢ = 1;
                    break;
                }
                case 4: {
                    if (this.Ñ¢á != 8) {
                        throw new IOException("Unsupported bit depth: " + this.Ñ¢á);
                    }
                    this.Çªà¢ = 2;
                    break;
                }
                case 2: {
                    if (this.Ñ¢á != 8) {
                        throw new IOException("Unsupported bit depth: " + this.Ñ¢á);
                    }
                    this.Çªà¢ = 3;
                    break;
                }
                case 6: {
                    if (this.Ñ¢á != 8) {
                        throw new IOException("Unsupported bit depth: " + this.Ñ¢á);
                    }
                    this.Çªà¢ = 4;
                    break;
                }
                case 3: {
                    switch (this.Ñ¢á) {
                        case 1:
                        case 2:
                        case 4:
                        case 8: {
                            this.Çªà¢ = 1;
                            break Label_0416;
                        }
                        default: {
                            throw new IOException("Unsupported bit depth: " + this.Ñ¢á);
                        }
                    }
                    break;
                }
                default: {
                    throw new IOException("unsupported color format: " + this.ŒÏ);
                }
            }
        }
        if (this.µÕ[10] != 0) {
            throw new IOException("unsupported compression method");
        }
        if (this.µÕ[11] != 0) {
            throw new IOException("unsupported filtering method");
        }
        if (this.µÕ[12] != 0) {
            throw new IOException("unsupported interlace method");
        }
    }
    
    private void Ó() throws IOException {
        final int paletteEntries = this.Æ / 3;
        if (paletteEntries < 1 || paletteEntries > 256 || this.Æ % 3 != 0) {
            throw new IOException("PLTE chunk has wrong length");
        }
        this.HorizonCode_Horizon_È(this.Ê = new byte[paletteEntries * 3], 0, this.Ê.length);
    }
    
    private void à() throws IOException {
        switch (this.ŒÏ) {
            case 0: {
                this.Â(2);
                this.HorizonCode_Horizon_È(this.ˆá = new byte[2], 0, 2);
                break;
            }
            case 2: {
                this.Â(6);
                this.HorizonCode_Horizon_È(this.ˆá = new byte[6], 0, 6);
                break;
            }
            case 3: {
                if (this.Ê == null) {
                    throw new IOException("tRNS chunk without PLTE chunk");
                }
                Arrays.fill(this.ÇŽÉ = new byte[this.Ê.length / 3], (byte)(-1));
                this.HorizonCode_Horizon_È(this.ÇŽÉ, 0, this.ÇŽÉ.length);
                break;
            }
        }
    }
    
    private void Ø() throws IOException {
        if (this.Ï­Ðƒà > 0) {
            this.HorizonCode_Horizon_È((long)(this.Ï­Ðƒà + 4));
        }
        else {
            this.Â(this.µÕ, 0, 4);
            final int expectedCrc = this.HorizonCode_Horizon_È(this.µÕ, 0);
            final int computedCrc = (int)this.Ø­à.getValue();
            if (computedCrc != expectedCrc) {
                throw new IOException("Invalid CRC");
            }
        }
        this.Ï­Ðƒà = 0;
        this.Æ = 0;
        this.Šáƒ = 0;
    }
    
    private void áŒŠÆ() throws IOException {
        this.Â(this.µÕ, 0, 8);
        this.Æ = this.HorizonCode_Horizon_È(this.µÕ, 0);
        this.Šáƒ = this.HorizonCode_Horizon_È(this.µÕ, 4);
        this.Ï­Ðƒà = this.Æ;
        this.Ø­à.reset();
        this.Ø­à.update(this.µÕ, 4, 4);
    }
    
    private void HorizonCode_Horizon_È(final int expected) throws IOException {
        this.áŒŠÆ();
        if (this.Šáƒ != expected) {
            throw new IOException("Expected chunk: " + Integer.toHexString(expected));
        }
    }
    
    private void Â(final int expected) throws IOException {
        if (this.Æ != expected) {
            throw new IOException("Chunk has wrong size");
        }
    }
    
    private int HorizonCode_Horizon_È(final byte[] buffer, final int offset, int length) throws IOException {
        if (length > this.Ï­Ðƒà) {
            length = this.Ï­Ðƒà;
        }
        this.Â(buffer, offset, length);
        this.Ø­à.update(buffer, offset, length);
        this.Ï­Ðƒà -= length;
        return length;
    }
    
    private void HorizonCode_Horizon_È(final Inflater inflater) throws IOException {
        while (this.Ï­Ðƒà == 0) {
            this.Ø();
            this.HorizonCode_Horizon_È(1229209940);
        }
        final int read = this.HorizonCode_Horizon_È(this.µÕ, 0, this.µÕ.length);
        inflater.setInput(this.µÕ, 0, read);
    }
    
    private void HorizonCode_Horizon_È(final Inflater inflater, final byte[] buffer, int offset, int length) throws IOException {
        try {
            do {
                final int read = inflater.inflate(buffer, offset, length);
                if (read <= 0) {
                    if (inflater.finished()) {
                        throw new EOFException();
                    }
                    if (!inflater.needsInput()) {
                        throw new IOException("Can't inflate " + length + " bytes");
                    }
                    this.HorizonCode_Horizon_È(inflater);
                }
                else {
                    offset += read;
                    length -= read;
                }
            } while (length > 0);
        }
        catch (DataFormatException ex) {
            throw (IOException)new IOException("inflate error").initCause(ex);
        }
    }
    
    private void Â(final byte[] buffer, int offset, int length) throws IOException {
        do {
            final int read = this.¥Æ.read(buffer, offset, length);
            if (read < 0) {
                throw new EOFException();
            }
            offset += read;
            length -= read;
        } while (length > 0);
    }
    
    private int HorizonCode_Horizon_È(final byte[] buffer, final int offset) {
        return buffer[offset] << 24 | (buffer[offset + 1] & 0xFF) << 16 | (buffer[offset + 2] & 0xFF) << 8 | (buffer[offset + 3] & 0xFF);
    }
    
    private void HorizonCode_Horizon_È(long amount) throws IOException {
        while (amount > 0L) {
            final long skipped = this.¥Æ.skip(amount);
            if (skipped < 0L) {
                throw new EOFException();
            }
            amount -= skipped;
        }
    }
    
    private static boolean Â(final byte[] buffer) {
        for (int i = 0; i < PNGDecoder.Ø.length; ++i) {
            if (buffer[i] != PNGDecoder.Ø[i]) {
                return false;
            }
        }
        return true;
    }
    
    public static class HorizonCode_Horizon_È
    {
        final int HorizonCode_Horizon_È;
        final boolean Â;
        
        private HorizonCode_Horizon_È(final int numComponents, final boolean hasAlpha) {
            this.HorizonCode_Horizon_È = numComponents;
            this.Â = hasAlpha;
        }
        
        public int HorizonCode_Horizon_È() {
            return this.HorizonCode_Horizon_È;
        }
        
        public boolean Â() {
            return this.Â;
        }
    }
}
