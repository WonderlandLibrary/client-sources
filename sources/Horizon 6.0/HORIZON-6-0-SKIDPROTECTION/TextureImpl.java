package HORIZON-6-0-SKIDPROTECTION;

import org.lwjgl.BufferUtils;
import java.nio.ByteOrder;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class TextureImpl implements Texture
{
    protected static SGL HorizonCode_Horizon_È;
    static Texture Â;
    private int Ý;
    private int Ø­áŒŠá;
    private int Âµá€;
    private int Ó;
    private int à;
    private int Ø;
    private float áŒŠÆ;
    private float áˆºÑ¢Õ;
    private boolean ÂµÈ;
    private String á;
    private String ˆÏ­;
    private HorizonCode_Horizon_È £á;
    
    static {
        TextureImpl.HorizonCode_Horizon_È = Renderer.HorizonCode_Horizon_È();
    }
    
    public static Texture Å() {
        return TextureImpl.Â;
    }
    
    protected TextureImpl() {
    }
    
    public TextureImpl(final String ref, final int target, final int textureID) {
        this.Ý = target;
        this.á = ref;
        this.Ø­áŒŠá = textureID;
        TextureImpl.Â = this;
    }
    
    public void HorizonCode_Horizon_È(final String cacheName) {
        this.ˆÏ­ = cacheName;
    }
    
    @Override
    public boolean £á() {
        return this.ÂµÈ;
    }
    
    @Override
    public String áŒŠÆ() {
        return this.á;
    }
    
    public void HorizonCode_Horizon_È(final boolean alpha) {
        this.ÂµÈ = alpha;
    }
    
    public static void £à() {
        TextureImpl.Â = null;
        TextureImpl.HorizonCode_Horizon_È.Ø­áŒŠá(3553);
    }
    
    public static void µà() {
        TextureImpl.Â = null;
    }
    
    @Override
    public void Ý() {
        if (TextureImpl.Â != this) {
            TextureImpl.Â = this;
            TextureImpl.HorizonCode_Horizon_È.Âµá€(3553);
            TextureImpl.HorizonCode_Horizon_È.Ý(this.Ý, this.Ø­áŒŠá);
        }
    }
    
    public void HorizonCode_Horizon_È(final int height) {
        this.Âµá€ = height;
        this.HorizonCode_Horizon_È();
    }
    
    public void Âµá€(final int width) {
        this.Ó = width;
        this.Â();
    }
    
    @Override
    public int Âµá€() {
        return this.Âµá€;
    }
    
    @Override
    public int Ó() {
        return this.Ó;
    }
    
    @Override
    public float Ø­áŒŠá() {
        return this.áˆºÑ¢Õ;
    }
    
    @Override
    public float ÂµÈ() {
        return this.áŒŠÆ;
    }
    
    @Override
    public int à() {
        return this.Ø;
    }
    
    @Override
    public int áˆºÑ¢Õ() {
        return this.à;
    }
    
    public void Â(final int texHeight) {
        this.Ø = texHeight;
        this.HorizonCode_Horizon_È();
    }
    
    public void Ø­áŒŠá(final int texWidth) {
        this.à = texWidth;
        this.Â();
    }
    
    private void HorizonCode_Horizon_È() {
        if (this.Ø != 0) {
            this.áˆºÑ¢Õ = this.Âµá€ / this.Ø;
        }
    }
    
    private void Â() {
        if (this.à != 0) {
            this.áŒŠÆ = this.Ó / this.à;
        }
    }
    
    @Override
    public void á() {
        final IntBuffer texBuf = this.à(1);
        texBuf.put(this.Ø­áŒŠá);
        texBuf.flip();
        TextureImpl.HorizonCode_Horizon_È.HorizonCode_Horizon_È(texBuf);
        if (TextureImpl.Â == this) {
            £à();
        }
        if (this.ˆÏ­ != null) {
            InternalTextureLoader.HorizonCode_Horizon_È().HorizonCode_Horizon_È(this.ˆÏ­);
        }
        else {
            InternalTextureLoader.HorizonCode_Horizon_È().HorizonCode_Horizon_È(this.á);
        }
    }
    
    @Override
    public int Ø() {
        return this.Ø­áŒŠá;
    }
    
    public void Ý(final int textureID) {
        this.Ø­áŒŠá = textureID;
    }
    
    protected IntBuffer à(final int size) {
        final ByteBuffer temp = ByteBuffer.allocateDirect(4 * size);
        temp.order(ByteOrder.nativeOrder());
        return temp.asIntBuffer();
    }
    
    @Override
    public byte[] ˆÏ­() {
        final ByteBuffer buffer = BufferUtils.createByteBuffer((this.£á() ? 4 : 3) * this.à * this.Ø);
        this.Ý();
        TextureImpl.HorizonCode_Horizon_È.HorizonCode_Horizon_È(3553, 0, this.£á() ? 6408 : 6407, 5121, buffer);
        final byte[] data = new byte[buffer.limit()];
        buffer.get(data);
        buffer.clear();
        return data;
    }
    
    @Override
    public void Ó(final int textureFilter) {
        this.Ý();
        TextureImpl.HorizonCode_Horizon_È.Â(this.Ý, 10241, textureFilter);
        TextureImpl.HorizonCode_Horizon_È.Â(this.Ý, 10240, textureFilter);
    }
    
    public void HorizonCode_Horizon_È(final int srcPixelFormat, final int componentCount, final int minFilter, final int magFilter, final ByteBuffer textureBuffer) {
        TextureImpl.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.£á = new HorizonCode_Horizon_È((HorizonCode_Horizon_È)null), srcPixelFormat);
        TextureImpl.HorizonCode_Horizon_È.Â(this.£á, componentCount);
        TextureImpl.HorizonCode_Horizon_È.Ý(this.£á, minFilter);
        TextureImpl.HorizonCode_Horizon_È.Ø­áŒŠá(this.£á, magFilter);
        TextureImpl.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.£á, textureBuffer);
    }
    
    public void ˆà() {
        if (this.£á != null) {
            this.Ø­áŒŠá = this.£á.HorizonCode_Horizon_È();
        }
    }
    
    private class HorizonCode_Horizon_È
    {
        private int Â;
        private int Ý;
        private int Ø­áŒŠá;
        private int Âµá€;
        private ByteBuffer Ó;
        
        public int HorizonCode_Horizon_È() {
            Log.HorizonCode_Horizon_È("Reloading texture: " + TextureImpl.this.á);
            return InternalTextureLoader.HorizonCode_Horizon_È().HorizonCode_Horizon_È(TextureImpl.this, this.Â, this.Ý, this.Ø­áŒŠá, this.Âµá€, this.Ó);
        }
        
        static /* synthetic */ void HorizonCode_Horizon_È(final HorizonCode_Horizon_È horizonCode_Horizon_È, final int â) {
            horizonCode_Horizon_È.Â = â;
        }
        
        static /* synthetic */ void Â(final HorizonCode_Horizon_È horizonCode_Horizon_È, final int ý) {
            horizonCode_Horizon_È.Ý = ý;
        }
        
        static /* synthetic */ void Ý(final HorizonCode_Horizon_È horizonCode_Horizon_È, final int ø­áŒŠá) {
            horizonCode_Horizon_È.Ø­áŒŠá = ø­áŒŠá;
        }
        
        static /* synthetic */ void Ø­áŒŠá(final HorizonCode_Horizon_È horizonCode_Horizon_È, final int âµá€) {
            horizonCode_Horizon_È.Âµá€ = âµá€;
        }
        
        static /* synthetic */ void HorizonCode_Horizon_È(final HorizonCode_Horizon_È horizonCode_Horizon_È, final ByteBuffer ó) {
            horizonCode_Horizon_È.Ó = ó;
        }
    }
}
