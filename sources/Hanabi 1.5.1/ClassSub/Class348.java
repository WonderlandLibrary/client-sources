package ClassSub;

import org.lwjgl.*;
import java.nio.*;

public class Class348 implements Class280
{
    private int width;
    private int height;
    private int texWidth;
    private int texHeight;
    private byte[] rawData;
    
    
    public Class348(final int width, final int height) {
        this.width = width;
        this.height = height;
        this.texWidth = this.get2Fold(width);
        this.texHeight = this.get2Fold(height);
        this.rawData = new byte[this.texWidth * this.texHeight * 4];
    }
    
    public byte[] getRGBA() {
        return this.rawData;
    }
    
    @Override
    public int getDepth() {
        return 32;
    }
    
    @Override
    public int getHeight() {
        return this.height;
    }
    
    @Override
    public int getTexHeight() {
        return this.texHeight;
    }
    
    @Override
    public int getTexWidth() {
        return this.texWidth;
    }
    
    @Override
    public int getWidth() {
        return this.width;
    }
    
    @Override
    public ByteBuffer getImageBufferData() {
        final ByteBuffer byteBuffer = BufferUtils.createByteBuffer(this.rawData.length);
        byteBuffer.put(this.rawData);
        byteBuffer.flip();
        return byteBuffer;
    }
    
    public void setRGBA(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        if (n < 0 || n >= this.width || n2 < 0 || n2 >= this.height) {
            throw new RuntimeException("Specified location: " + n + "," + n2 + " outside of image");
        }
        final int n7 = (n + n2 * this.texWidth) * 4;
        if (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN) {
            this.rawData[n7] = (byte)n5;
            this.rawData[n7 + 1] = (byte)n4;
            this.rawData[n7 + 2] = (byte)n3;
            this.rawData[n7 + 3] = (byte)n6;
        }
        else {
            this.rawData[n7] = (byte)n3;
            this.rawData[n7 + 1] = (byte)n4;
            this.rawData[n7 + 2] = (byte)n5;
            this.rawData[n7 + 3] = (byte)n6;
        }
    }
    
    public Class220 getImage() {
        return new Class220(this);
    }
    
    public Class220 getImage(final int n) {
        return new Class220(this, n);
    }
    
    private int get2Fold(final int n) {
        int i;
        for (i = 2; i < n; i *= 2) {}
        return i;
    }
}
