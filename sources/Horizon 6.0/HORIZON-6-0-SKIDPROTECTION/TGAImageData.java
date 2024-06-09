package HORIZON-6-0-SKIDPROTECTION;

import org.lwjgl.BufferUtils;
import java.nio.ByteOrder;
import java.io.DataInputStream;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.io.InputStream;

public class TGAImageData implements LoadableImageData
{
    private int HorizonCode_Horizon_È;
    private int Â;
    private int Ý;
    private int Ø­áŒŠá;
    private short Âµá€;
    
    private short HorizonCode_Horizon_È(final short signedShort) {
        final int input = signedShort & 0xFFFF;
        return (short)(input << 8 | (input & 0xFF00) >>> 8);
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return this.Âµá€;
    }
    
    @Override
    public int Ó() {
        return this.Ý;
    }
    
    @Override
    public int Â() {
        return this.Ø­áŒŠá;
    }
    
    @Override
    public int Âµá€() {
        return this.HorizonCode_Horizon_È;
    }
    
    @Override
    public int Ø­áŒŠá() {
        return this.Â;
    }
    
    @Override
    public ByteBuffer HorizonCode_Horizon_È(final InputStream fis) throws IOException {
        return this.HorizonCode_Horizon_È(fis, true, null);
    }
    
    @Override
    public ByteBuffer HorizonCode_Horizon_È(final InputStream fis, final boolean flipped, final int[] transparent) throws IOException {
        return this.HorizonCode_Horizon_È(fis, flipped, false, transparent);
    }
    
    @Override
    public ByteBuffer HorizonCode_Horizon_È(final InputStream fis, boolean flipped, boolean forceAlpha, final int[] transparent) throws IOException {
        if (transparent != null) {
            forceAlpha = true;
        }
        byte red = 0;
        byte green = 0;
        byte blue = 0;
        byte alpha = 0;
        final BufferedInputStream bis = new BufferedInputStream(fis, 100000);
        final DataInputStream dis = new DataInputStream(bis);
        final short idLength = (short)dis.read();
        final short colorMapType = (short)dis.read();
        final short imageType = (short)dis.read();
        final short cMapStart = this.HorizonCode_Horizon_È(dis.readShort());
        final short cMapLength = this.HorizonCode_Horizon_È(dis.readShort());
        final short cMapDepth = (short)dis.read();
        final short xOffset = this.HorizonCode_Horizon_È(dis.readShort());
        final short yOffset = this.HorizonCode_Horizon_È(dis.readShort());
        if (imageType != 2) {
            throw new IOException("Slick only supports uncompressed RGB(A) TGA images");
        }
        this.Ý = this.HorizonCode_Horizon_È(dis.readShort());
        this.Ø­áŒŠá = this.HorizonCode_Horizon_È(dis.readShort());
        this.Âµá€ = (short)dis.read();
        if (this.Âµá€ == 32) {
            forceAlpha = false;
        }
        this.HorizonCode_Horizon_È = this.HorizonCode_Horizon_È(this.Ý);
        this.Â = this.HorizonCode_Horizon_È(this.Ø­áŒŠá);
        final short imageDescriptor = (short)dis.read();
        if ((imageDescriptor & 0x20) == 0x0) {
            flipped = !flipped;
        }
        if (idLength > 0) {
            bis.skip(idLength);
        }
        byte[] rawData = null;
        if (this.Âµá€ == 32 || forceAlpha) {
            this.Âµá€ = 32;
            rawData = new byte[this.HorizonCode_Horizon_È * this.Â * 4];
        }
        else {
            if (this.Âµá€ != 24) {
                throw new RuntimeException("Only 24 and 32 bit TGAs are supported");
            }
            rawData = new byte[this.HorizonCode_Horizon_È * this.Â * 3];
        }
        if (this.Âµá€ == 24) {
            if (flipped) {
                for (int i = this.Ø­áŒŠá - 1; i >= 0; --i) {
                    for (int j = 0; j < this.Ý; ++j) {
                        blue = dis.readByte();
                        green = dis.readByte();
                        red = dis.readByte();
                        final int ofs = (j + i * this.HorizonCode_Horizon_È) * 3;
                        rawData[ofs] = red;
                        rawData[ofs + 1] = green;
                        rawData[ofs + 2] = blue;
                    }
                }
            }
            else {
                for (int i = 0; i < this.Ø­áŒŠá; ++i) {
                    for (int j = 0; j < this.Ý; ++j) {
                        blue = dis.readByte();
                        green = dis.readByte();
                        red = dis.readByte();
                        final int ofs = (j + i * this.HorizonCode_Horizon_È) * 3;
                        rawData[ofs] = red;
                        rawData[ofs + 1] = green;
                        rawData[ofs + 2] = blue;
                    }
                }
            }
        }
        else if (this.Âµá€ == 32) {
            if (flipped) {
                for (int i = this.Ø­áŒŠá - 1; i >= 0; --i) {
                    for (int j = 0; j < this.Ý; ++j) {
                        blue = dis.readByte();
                        green = dis.readByte();
                        red = dis.readByte();
                        if (forceAlpha) {
                            alpha = -1;
                        }
                        else {
                            alpha = dis.readByte();
                        }
                        final int ofs = (j + i * this.HorizonCode_Horizon_È) * 4;
                        rawData[ofs] = red;
                        rawData[ofs + 1] = green;
                        rawData[ofs + 2] = blue;
                        rawData[ofs + 3] = alpha;
                        if (alpha == 0) {
                            rawData[ofs + 2] = 0;
                            rawData[ofs] = (rawData[ofs + 1] = 0);
                        }
                    }
                }
            }
            else {
                for (int i = 0; i < this.Ø­áŒŠá; ++i) {
                    for (int j = 0; j < this.Ý; ++j) {
                        blue = dis.readByte();
                        green = dis.readByte();
                        red = dis.readByte();
                        if (forceAlpha) {
                            alpha = -1;
                        }
                        else {
                            alpha = dis.readByte();
                        }
                        final int ofs = (j + i * this.HorizonCode_Horizon_È) * 4;
                        if (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN) {
                            rawData[ofs] = red;
                            rawData[ofs + 1] = green;
                            rawData[ofs + 2] = blue;
                            rawData[ofs + 3] = alpha;
                        }
                        else {
                            rawData[ofs] = red;
                            rawData[ofs + 1] = green;
                            rawData[ofs + 2] = blue;
                            rawData[ofs + 3] = alpha;
                        }
                        if (alpha == 0) {
                            rawData[ofs + 2] = 0;
                            rawData[ofs] = (rawData[ofs + 1] = 0);
                        }
                    }
                }
            }
        }
        fis.close();
        if (transparent != null) {
            for (int i = 0; i < rawData.length; i += 4) {
                boolean match = true;
                for (int c = 0; c < 3; ++c) {
                    if (rawData[i + c] != transparent[c]) {
                        match = false;
                    }
                }
                if (match) {
                    rawData[i + 3] = 0;
                }
            }
        }
        final ByteBuffer scratch = BufferUtils.createByteBuffer(rawData.length);
        scratch.put(rawData);
        final int perPixel = this.Âµá€ / 8;
        if (this.Ø­áŒŠá < this.Â - 1) {
            final int topOffset = (this.Â - 1) * (this.HorizonCode_Horizon_È * perPixel);
            final int bottomOffset = (this.Ø­áŒŠá - 1) * (this.HorizonCode_Horizon_È * perPixel);
            for (int x = 0; x < this.HorizonCode_Horizon_È * perPixel; ++x) {
                scratch.put(topOffset + x, scratch.get(x));
                scratch.put(bottomOffset + this.HorizonCode_Horizon_È * perPixel + x, scratch.get(this.HorizonCode_Horizon_È * perPixel + x));
            }
        }
        if (this.Ý < this.HorizonCode_Horizon_È - 1) {
            for (int y = 0; y < this.Â; ++y) {
                for (int k = 0; k < perPixel; ++k) {
                    scratch.put((y + 1) * (this.HorizonCode_Horizon_È * perPixel) - perPixel + k, scratch.get(y * (this.HorizonCode_Horizon_È * perPixel) + k));
                    scratch.put(y * (this.HorizonCode_Horizon_È * perPixel) + this.Ý * perPixel + k, scratch.get(y * (this.HorizonCode_Horizon_È * perPixel) + (this.Ý - 1) * perPixel + k));
                }
            }
        }
        scratch.flip();
        return scratch;
    }
    
    private int HorizonCode_Horizon_È(final int fold) {
        int ret;
        for (ret = 2; ret < fold; ret *= 2) {}
        return ret;
    }
    
    @Override
    public ByteBuffer Ý() {
        throw new RuntimeException("TGAImageData doesn't store it's image.");
    }
    
    @Override
    public void HorizonCode_Horizon_È(final boolean edging) {
    }
}
