package HORIZON-6-0-SKIDPROTECTION;

import org.lwjgl.BufferUtils;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class PNGImageData implements LoadableImageData
{
    private int HorizonCode_Horizon_È;
    private int Â;
    private int Ý;
    private int Ø­áŒŠá;
    private PNGDecoder Âµá€;
    private int Ó;
    private ByteBuffer à;
    
    @Override
    public int HorizonCode_Horizon_È() {
        return this.Ó;
    }
    
    @Override
    public ByteBuffer Ý() {
        return this.à;
    }
    
    @Override
    public int Ø­áŒŠá() {
        return this.Ý;
    }
    
    @Override
    public int Âµá€() {
        return this.Ø­áŒŠá;
    }
    
    @Override
    public ByteBuffer HorizonCode_Horizon_È(final InputStream fis) throws IOException {
        return this.HorizonCode_Horizon_È(fis, false, null);
    }
    
    @Override
    public ByteBuffer HorizonCode_Horizon_È(final InputStream fis, final boolean flipped, final int[] transparent) throws IOException {
        return this.HorizonCode_Horizon_È(fis, flipped, false, transparent);
    }
    
    @Override
    public ByteBuffer HorizonCode_Horizon_È(final InputStream fis, final boolean flipped, boolean forceAlpha, final int[] transparent) throws IOException {
        if (transparent != null) {
            forceAlpha = true;
            throw new IOException("Transparent color not support in custom PNG Decoder");
        }
        final PNGDecoder decoder = new PNGDecoder(fis);
        if (!decoder.Ø­áŒŠá()) {
            throw new IOException("Only RGB formatted images are supported by the PNGLoader");
        }
        this.HorizonCode_Horizon_È = decoder.Â();
        this.Â = decoder.HorizonCode_Horizon_È();
        this.Ø­áŒŠá = this.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È);
        this.Ý = this.HorizonCode_Horizon_È(this.Â);
        final int perPixel = decoder.Ý() ? 4 : 3;
        this.Ó = (decoder.Ý() ? 32 : 24);
        decoder.HorizonCode_Horizon_È(this.à = BufferUtils.createByteBuffer(this.Ø­áŒŠá * this.Ý * perPixel), this.Ø­áŒŠá * perPixel, (perPixel == 4) ? PNGDecoder.Âµá€ : PNGDecoder.Ø­áŒŠá);
        if (this.Â < this.Ý - 1) {
            final int topOffset = (this.Ý - 1) * (this.Ø­áŒŠá * perPixel);
            final int bottomOffset = (this.Â - 1) * (this.Ø­áŒŠá * perPixel);
            for (int x = 0; x < this.Ø­áŒŠá; ++x) {
                for (int i = 0; i < perPixel; ++i) {
                    this.à.put(topOffset + x + i, this.à.get(x + i));
                    this.à.put(bottomOffset + this.Ø­áŒŠá * perPixel + x + i, this.à.get(bottomOffset + x + i));
                }
            }
        }
        if (this.HorizonCode_Horizon_È < this.Ø­áŒŠá - 1) {
            for (int y = 0; y < this.Ý; ++y) {
                for (int j = 0; j < perPixel; ++j) {
                    this.à.put((y + 1) * (this.Ø­áŒŠá * perPixel) - perPixel + j, this.à.get(y * (this.Ø­áŒŠá * perPixel) + j));
                    this.à.put(y * (this.Ø­áŒŠá * perPixel) + this.HorizonCode_Horizon_È * perPixel + j, this.à.get(y * (this.Ø­áŒŠá * perPixel) + (this.HorizonCode_Horizon_È - 1) * perPixel + j));
                }
            }
        }
        if (!decoder.Ý() && forceAlpha) {
            final ByteBuffer temp = BufferUtils.createByteBuffer(this.Ø­áŒŠá * this.Ý * 4);
            for (int x2 = 0; x2 < this.Ø­áŒŠá; ++x2) {
                for (int y2 = 0; y2 < this.Ý; ++y2) {
                    final int srcOffset = y2 * 3 + x2 * this.Ý * 3;
                    final int dstOffset = y2 * 4 + x2 * this.Ý * 4;
                    temp.put(dstOffset, this.à.get(srcOffset));
                    temp.put(dstOffset + 1, this.à.get(srcOffset + 1));
                    temp.put(dstOffset + 2, this.à.get(srcOffset + 2));
                    if (x2 < this.Â() && y2 < this.Ó()) {
                        temp.put(dstOffset + 3, (byte)(-1));
                    }
                    else {
                        temp.put(dstOffset + 3, (byte)0);
                    }
                }
            }
            this.Ó = 32;
            this.à = temp;
        }
        if (transparent != null) {
            for (int k = 0; k < this.Ø­áŒŠá * this.Ý * 4; k += 4) {
                boolean match = true;
                for (int c = 0; c < 3; ++c) {
                    if (this.HorizonCode_Horizon_È(this.à.get(k + c)) != transparent[c]) {
                        match = false;
                    }
                }
                if (match) {
                    this.à.put(k + 3, (byte)0);
                }
            }
        }
        this.à.position(0);
        return this.à;
    }
    
    private int HorizonCode_Horizon_È(final byte b) {
        if (b < 0) {
            return 256 + b;
        }
        return b;
    }
    
    private int HorizonCode_Horizon_È(final int fold) {
        int ret;
        for (ret = 2; ret < fold; ret *= 2) {}
        return ret;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final boolean edging) {
    }
    
    @Override
    public int Ó() {
        return this.HorizonCode_Horizon_È;
    }
    
    @Override
    public int Â() {
        return this.Â;
    }
}
