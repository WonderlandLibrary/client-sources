package HORIZON-6-0-SKIDPROTECTION;

import java.nio.ByteOrder;
import org.lwjgl.BufferUtils;
import java.nio.ByteBuffer;

public class ImageBuffer implements ImageData
{
    private int HorizonCode_Horizon_È;
    private int Â;
    private int Ý;
    private int Ø­áŒŠá;
    private byte[] Âµá€;
    
    public ImageBuffer(final int width, final int height) {
        this.HorizonCode_Horizon_È = width;
        this.Â = height;
        this.Ý = this.Â(width);
        this.Ø­áŒŠá = this.Â(height);
        this.Âµá€ = new byte[this.Ý * this.Ø­áŒŠá * 4];
    }
    
    public byte[] à() {
        return this.Âµá€;
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 32;
    }
    
    @Override
    public int Â() {
        return this.Â;
    }
    
    @Override
    public int Ø­áŒŠá() {
        return this.Ø­áŒŠá;
    }
    
    @Override
    public int Âµá€() {
        return this.Ý;
    }
    
    @Override
    public int Ó() {
        return this.HorizonCode_Horizon_È;
    }
    
    @Override
    public ByteBuffer Ý() {
        final ByteBuffer scratch = BufferUtils.createByteBuffer(this.Âµá€.length);
        scratch.put(this.Âµá€);
        scratch.flip();
        return scratch;
    }
    
    public void HorizonCode_Horizon_È(final int x, final int y, final int r, final int g, final int b, final int a) {
        if (x < 0 || x >= this.HorizonCode_Horizon_È || y < 0 || y >= this.Â) {
            throw new RuntimeException("Specified location: " + x + "," + y + " outside of image");
        }
        final int ofs = (x + y * this.Ý) * 4;
        if (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN) {
            this.Âµá€[ofs] = (byte)b;
            this.Âµá€[ofs + 1] = (byte)g;
            this.Âµá€[ofs + 2] = (byte)r;
            this.Âµá€[ofs + 3] = (byte)a;
        }
        else {
            this.Âµá€[ofs] = (byte)r;
            this.Âµá€[ofs + 1] = (byte)g;
            this.Âµá€[ofs + 2] = (byte)b;
            this.Âµá€[ofs + 3] = (byte)a;
        }
    }
    
    public Image Ø() {
        return new Image(this);
    }
    
    public Image HorizonCode_Horizon_È(final int filter) {
        return new Image(this, filter);
    }
    
    private int Â(final int fold) {
        int ret;
        for (ret = 2; ret < fold; ret *= 2) {}
        return ret;
    }
}
