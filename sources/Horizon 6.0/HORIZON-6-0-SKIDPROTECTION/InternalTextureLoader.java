package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.nio.ByteOrder;
import java.nio.ByteBuffer;
import org.lwjgl.BufferUtils;
import java.io.BufferedInputStream;
import java.lang.ref.SoftReference;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.File;
import java.nio.IntBuffer;
import java.util.HashMap;

public class InternalTextureLoader
{
    protected static SGL HorizonCode_Horizon_È;
    private static final InternalTextureLoader Â;
    private HashMap Ý;
    private HashMap Ø­áŒŠá;
    private int Âµá€;
    private boolean Ó;
    private boolean à;
    
    static {
        InternalTextureLoader.HorizonCode_Horizon_È = Renderer.HorizonCode_Horizon_È();
        Â = new InternalTextureLoader();
    }
    
    public static InternalTextureLoader HorizonCode_Horizon_È() {
        return InternalTextureLoader.Â;
    }
    
    private InternalTextureLoader() {
        this.Ý = new HashMap();
        this.Ø­áŒŠá = new HashMap();
        this.Âµá€ = 6408;
    }
    
    public void HorizonCode_Horizon_È(final boolean holdTextureData) {
        this.à = holdTextureData;
    }
    
    public void Â(final boolean deferred) {
        this.Ó = deferred;
    }
    
    public boolean Â() {
        return this.Ó;
    }
    
    public void HorizonCode_Horizon_È(final String name) {
        this.Ý.remove(name);
        this.Ø­áŒŠá.remove(name);
    }
    
    public void Ý() {
        this.Ý.clear();
        this.Ø­áŒŠá.clear();
    }
    
    public void Ø­áŒŠá() {
        this.Âµá€ = 32859;
    }
    
    public static int Âµá€() {
        final IntBuffer tmp = Â(1);
        InternalTextureLoader.HorizonCode_Horizon_È.Â(tmp);
        return tmp.get(0);
    }
    
    public Texture HorizonCode_Horizon_È(final File source, final boolean flipped, final int filter) throws IOException {
        final String resourceName = source.getAbsolutePath();
        final InputStream in = new FileInputStream(source);
        return this.HorizonCode_Horizon_È(in, resourceName, flipped, filter, null);
    }
    
    public Texture HorizonCode_Horizon_È(final File source, final boolean flipped, final int filter, final int[] transparent) throws IOException {
        final String resourceName = source.getAbsolutePath();
        final InputStream in = new FileInputStream(source);
        return this.HorizonCode_Horizon_È(in, resourceName, flipped, filter, transparent);
    }
    
    public Texture HorizonCode_Horizon_È(final String resourceName, final boolean flipped, final int filter) throws IOException {
        final InputStream in = ResourceLoader.HorizonCode_Horizon_È(resourceName);
        return this.HorizonCode_Horizon_È(in, resourceName, flipped, filter, null);
    }
    
    public Texture HorizonCode_Horizon_È(final String resourceName, final boolean flipped, final int filter, final int[] transparent) throws IOException {
        final InputStream in = ResourceLoader.HorizonCode_Horizon_È(resourceName);
        return this.HorizonCode_Horizon_È(in, resourceName, flipped, filter, transparent);
    }
    
    public Texture HorizonCode_Horizon_È(final InputStream in, final String resourceName, final boolean flipped, final int filter) throws IOException {
        return this.HorizonCode_Horizon_È(in, resourceName, flipped, filter, null);
    }
    
    public TextureImpl HorizonCode_Horizon_È(final InputStream in, final String resourceName, final boolean flipped, final int filter, final int[] transparent) throws IOException {
        if (this.Ó) {
            return new DeferredTexture(in, resourceName, flipped, filter, transparent);
        }
        HashMap hash = this.Ý;
        if (filter == 9728) {
            hash = this.Ø­áŒŠá;
        }
        String resName = resourceName;
        if (transparent != null) {
            resName = String.valueOf(resName) + ":" + transparent[0] + ":" + transparent[1] + ":" + transparent[2];
        }
        resName = String.valueOf(resName) + ":" + flipped;
        if (this.à) {
            final TextureImpl tex = hash.get(resName);
            if (tex != null) {
                return tex;
            }
        }
        else {
            final SoftReference ref = hash.get(resName);
            if (ref != null) {
                final TextureImpl tex2 = ref.get();
                if (tex2 != null) {
                    return tex2;
                }
                hash.remove(resName);
            }
        }
        try {
            InternalTextureLoader.HorizonCode_Horizon_È.Ø();
        }
        catch (NullPointerException e) {
            throw new RuntimeException("Image based resources must be loaded as part of init() or the game loop. They cannot be loaded before initialisation.");
        }
        final TextureImpl tex = this.HorizonCode_Horizon_È(in, resourceName, 3553, filter, filter, flipped, transparent);
        tex.HorizonCode_Horizon_È(resName);
        if (this.à) {
            hash.put(resName, tex);
        }
        else {
            hash.put(resName, new SoftReference<TextureImpl>(tex));
        }
        return tex;
    }
    
    private TextureImpl HorizonCode_Horizon_È(final InputStream in, final String resourceName, final int target, final int magFilter, final int minFilter, final boolean flipped, final int[] transparent) throws IOException {
        final LoadableImageData imageData = ImageDataFactory.HorizonCode_Horizon_È(resourceName);
        final ByteBuffer textureBuffer = imageData.HorizonCode_Horizon_È(new BufferedInputStream(in), flipped, transparent);
        final int textureID = Âµá€();
        final TextureImpl texture = new TextureImpl(resourceName, target, textureID);
        InternalTextureLoader.HorizonCode_Horizon_È.Ý(target, textureID);
        final int width = imageData.Ó();
        final int height = imageData.Â();
        final boolean hasAlpha = imageData.HorizonCode_Horizon_È() == 32;
        texture.Ø­áŒŠá(imageData.Âµá€());
        texture.Â(imageData.Ø­áŒŠá());
        final int texWidth = texture.áˆºÑ¢Õ();
        final int texHeight = texture.à();
        final IntBuffer temp = BufferUtils.createIntBuffer(16);
        InternalTextureLoader.HorizonCode_Horizon_È.HorizonCode_Horizon_È(3379, temp);
        final int max = temp.get(0);
        if (texWidth > max || texHeight > max) {
            throw new IOException("Attempt to allocate a texture to big for the current hardware");
        }
        final int srcPixelFormat = hasAlpha ? 6408 : 6407;
        final int componentCount = hasAlpha ? 4 : 3;
        texture.Âµá€(width);
        texture.HorizonCode_Horizon_È(height);
        texture.HorizonCode_Horizon_È(hasAlpha);
        if (this.à) {
            texture.HorizonCode_Horizon_È(srcPixelFormat, componentCount, minFilter, magFilter, textureBuffer);
        }
        InternalTextureLoader.HorizonCode_Horizon_È.Â(target, 10241, minFilter);
        InternalTextureLoader.HorizonCode_Horizon_È.Â(target, 10240, magFilter);
        InternalTextureLoader.HorizonCode_Horizon_È.HorizonCode_Horizon_È(target, 0, this.Âµá€, HorizonCode_Horizon_È(width), HorizonCode_Horizon_È(height), 0, srcPixelFormat, 5121, textureBuffer);
        return texture;
    }
    
    public Texture HorizonCode_Horizon_È(final int width, final int height) throws IOException {
        return this.HorizonCode_Horizon_È(width, height, 9728);
    }
    
    public Texture HorizonCode_Horizon_È(final int width, final int height, final int filter) throws IOException {
        final ImageData ds = new EmptyImageData(width, height);
        return this.HorizonCode_Horizon_È(ds, filter);
    }
    
    public Texture HorizonCode_Horizon_È(final ImageData dataSource, final int filter) throws IOException {
        final int target = 3553;
        final ByteBuffer textureBuffer = dataSource.Ý();
        final int textureID = Âµá€();
        final TextureImpl texture = new TextureImpl("generated:" + dataSource, target, textureID);
        final boolean flipped = false;
        InternalTextureLoader.HorizonCode_Horizon_È.Ý(target, textureID);
        final int width = dataSource.Ó();
        final int height = dataSource.Â();
        final boolean hasAlpha = dataSource.HorizonCode_Horizon_È() == 32;
        texture.Ø­áŒŠá(dataSource.Âµá€());
        texture.Â(dataSource.Ø­áŒŠá());
        final int texWidth = texture.áˆºÑ¢Õ();
        final int texHeight = texture.à();
        final int srcPixelFormat = hasAlpha ? 6408 : 6407;
        final int componentCount = hasAlpha ? 4 : 3;
        texture.Âµá€(width);
        texture.HorizonCode_Horizon_È(height);
        texture.HorizonCode_Horizon_È(hasAlpha);
        final IntBuffer temp = BufferUtils.createIntBuffer(16);
        InternalTextureLoader.HorizonCode_Horizon_È.HorizonCode_Horizon_È(3379, temp);
        final int max = temp.get(0);
        if (texWidth > max || texHeight > max) {
            throw new IOException("Attempt to allocate a texture to big for the current hardware");
        }
        if (this.à) {
            texture.HorizonCode_Horizon_È(srcPixelFormat, componentCount, filter, filter, textureBuffer);
        }
        InternalTextureLoader.HorizonCode_Horizon_È.Â(target, 10241, filter);
        InternalTextureLoader.HorizonCode_Horizon_È.Â(target, 10240, filter);
        InternalTextureLoader.HorizonCode_Horizon_È.HorizonCode_Horizon_È(target, 0, this.Âµá€, HorizonCode_Horizon_È(width), HorizonCode_Horizon_È(height), 0, srcPixelFormat, 5121, textureBuffer);
        return texture;
    }
    
    public static int HorizonCode_Horizon_È(final int fold) {
        int ret;
        for (ret = 2; ret < fold; ret *= 2) {}
        return ret;
    }
    
    public static IntBuffer Â(final int size) {
        final ByteBuffer temp = ByteBuffer.allocateDirect(4 * size);
        temp.order(ByteOrder.nativeOrder());
        return temp.asIntBuffer();
    }
    
    public void Ó() {
        Iterator texs = this.Ý.values().iterator();
        while (texs.hasNext()) {
            texs.next().ˆà();
        }
        texs = this.Ø­áŒŠá.values().iterator();
        while (texs.hasNext()) {
            texs.next().ˆà();
        }
    }
    
    public int HorizonCode_Horizon_È(final TextureImpl texture, final int srcPixelFormat, final int componentCount, final int minFilter, final int magFilter, final ByteBuffer textureBuffer) {
        final int target = 3553;
        final int textureID = Âµá€();
        InternalTextureLoader.HorizonCode_Horizon_È.Ý(target, textureID);
        InternalTextureLoader.HorizonCode_Horizon_È.Â(target, 10241, minFilter);
        InternalTextureLoader.HorizonCode_Horizon_È.Â(target, 10240, magFilter);
        InternalTextureLoader.HorizonCode_Horizon_È.HorizonCode_Horizon_È(target, 0, this.Âµá€, texture.áˆºÑ¢Õ(), texture.à(), 0, srcPixelFormat, 5121, textureBuffer);
        return textureID;
    }
}
