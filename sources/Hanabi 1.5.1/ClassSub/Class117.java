package ClassSub;

import java.nio.*;
import org.lwjgl.*;

public class Class117 implements Class257
{
    private int width;
    private int height;
    
    
    public Class117(final int width, final int height) {
        this.width = width;
        this.height = height;
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
    public ByteBuffer getImageBufferData() {
        return BufferUtils.createByteBuffer(this.getTexWidth() * this.getTexHeight() * 4);
    }
    
    @Override
    public int getTexHeight() {
        return Class144.get2Fold(this.height);
    }
    
    @Override
    public int getTexWidth() {
        return Class144.get2Fold(this.width);
    }
    
    @Override
    public int getWidth() {
        return this.width;
    }
}
