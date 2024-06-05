package ClassSub;

import java.nio.*;

private class Class72
{
    private int srcPixelFormat;
    private int componentCount;
    private int minFilter;
    private int magFilter;
    private ByteBuffer textureBuffer;
    final Class237 this$0;
    
    
    private Class72(final Class237 this$0) {
        this.this$0 = this$0;
    }
    
    public int reload() {
        Class301.error("Reloading texture: " + Class237.access$600(this.this$0));
        return Class144.get().reload(this.this$0, this.srcPixelFormat, this.componentCount, this.minFilter, this.magFilter, this.textureBuffer);
    }
    
    Class72(final Class237 class237, final Class57 class238) {
        this(class237);
    }
    
    static int access$102(final Class72 class72, final int srcPixelFormat) {
        return class72.srcPixelFormat = srcPixelFormat;
    }
    
    static int access$202(final Class72 class72, final int componentCount) {
        return class72.componentCount = componentCount;
    }
    
    static int access$302(final Class72 class72, final int minFilter) {
        return class72.minFilter = minFilter;
    }
    
    static int access$402(final Class72 class72, final int magFilter) {
        return class72.magFilter = magFilter;
    }
    
    static ByteBuffer access$502(final Class72 class72, final ByteBuffer textureBuffer) {
        return class72.textureBuffer = textureBuffer;
    }
}
