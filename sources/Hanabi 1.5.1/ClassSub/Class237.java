package ClassSub;

import java.nio.*;
import org.lwjgl.*;

public class Class237 implements Class282
{
    protected static Class311 GL;
    static Class282 lastBind;
    private int target;
    private int textureID;
    private int height;
    private int width;
    private int texWidth;
    private int texHeight;
    private float widthRatio;
    private float heightRatio;
    private boolean alpha;
    private String ref;
    private String cacheName;
    private Class72 reloadData;
    
    
    public static Class282 getLastBind() {
        return Class237.lastBind;
    }
    
    protected Class237() {
    }
    
    public Class237(final String ref, final int target, final int textureID) {
        this.target = target;
        this.ref = ref;
        this.textureID = textureID;
        Class237.lastBind = this;
    }
    
    public void setCacheName(final String cacheName) {
        this.cacheName = cacheName;
    }
    
    @Override
    public boolean hasAlpha() {
        return this.alpha;
    }
    
    @Override
    public String getTextureRef() {
        return this.ref;
    }
    
    public void setAlpha(final boolean alpha) {
        this.alpha = alpha;
    }
    
    public static void bindNone() {
        Class237.lastBind = null;
        Class237.GL.glDisable(3553);
    }
    
    public static void unbind() {
        Class237.lastBind = null;
    }
    
    @Override
    public void bind() {
        if (Class237.lastBind != this) {
            Class237.lastBind = this;
            Class237.GL.glEnable(3553);
            Class237.GL.glBindTexture(this.target, this.textureID);
        }
    }
    
    public void setHeight(final int height) {
        this.height = height;
        this.setHeight();
    }
    
    public void setWidth(final int width) {
        this.width = width;
        this.setWidth();
    }
    
    @Override
    public int getImageHeight() {
        return this.height;
    }
    
    @Override
    public int getImageWidth() {
        return this.width;
    }
    
    @Override
    public float getHeight() {
        return this.heightRatio;
    }
    
    @Override
    public float getWidth() {
        return this.widthRatio;
    }
    
    @Override
    public int getTextureHeight() {
        return this.texHeight;
    }
    
    @Override
    public int getTextureWidth() {
        return this.texWidth;
    }
    
    public void setTextureHeight(final int texHeight) {
        this.texHeight = texHeight;
        this.setHeight();
    }
    
    public void setTextureWidth(final int texWidth) {
        this.texWidth = texWidth;
        this.setWidth();
    }
    
    private void setHeight() {
        if (this.texHeight != 0) {
            this.heightRatio = this.height / this.texHeight;
        }
    }
    
    private void setWidth() {
        if (this.texWidth != 0) {
            this.widthRatio = this.width / this.texWidth;
        }
    }
    
    @Override
    public void release() {
        final IntBuffer intBuffer = this.createIntBuffer(1);
        intBuffer.put(this.textureID);
        intBuffer.flip();
        Class237.GL.glDeleteTextures(intBuffer);
        if (Class237.lastBind == this) {
            bindNone();
        }
        if (this.cacheName != null) {
            Class144.get().clear(this.cacheName);
        }
        else {
            Class144.get().clear(this.ref);
        }
    }
    
    @Override
    public int getTextureID() {
        return this.textureID;
    }
    
    public void setTextureID(final int textureID) {
        this.textureID = textureID;
    }
    
    protected IntBuffer createIntBuffer(final int n) {
        final ByteBuffer allocateDirect = ByteBuffer.allocateDirect(4 * n);
        allocateDirect.order(ByteOrder.nativeOrder());
        return allocateDirect.asIntBuffer();
    }
    
    @Override
    public byte[] getTextureData() {
        final ByteBuffer byteBuffer = BufferUtils.createByteBuffer((this.hasAlpha() ? 4 : 3) * this.texWidth * this.texHeight);
        this.bind();
        Class237.GL.glGetTexImage(3553, 0, this.hasAlpha() ? 6408 : 6407, 5121, byteBuffer);
        final byte[] array = new byte[byteBuffer.limit()];
        byteBuffer.get(array);
        byteBuffer.clear();
        return array;
    }
    
    @Override
    public void setTextureFilter(final int n) {
        this.bind();
        Class237.GL.glTexParameteri(this.target, 10241, n);
        Class237.GL.glTexParameteri(this.target, 10240, n);
    }
    
    public void setTextureData(final int n, final int n2, final int n3, final int n4, final ByteBuffer byteBuffer) {
        Class72.access$102(this.reloadData = new Class72(null), n);
        Class72.access$202(this.reloadData, n2);
        Class72.access$302(this.reloadData, n3);
        Class72.access$402(this.reloadData, n4);
        Class72.access$502(this.reloadData, byteBuffer);
    }
    
    public void reload() {
        if (this.reloadData != null) {
            this.textureID = this.reloadData.reload();
        }
    }
    
    static String access$600(final Class237 class237) {
        return class237.ref;
    }
    
    static {
        Class237.GL = Class197.get();
    }
    
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
}
