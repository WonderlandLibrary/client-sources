package ClassSub;

import java.lang.ref.*;
import java.io.*;
import org.lwjgl.*;
import java.nio.*;
import java.util.*;

public class Class144
{
    protected static Class311 GL;
    private static final Class144 loader;
    private HashMap texturesLinear;
    private HashMap texturesNearest;
    private int dstPixelFormat;
    private boolean deferred;
    private boolean holdTextureData;
    
    
    public static Class144 get() {
        return Class144.loader;
    }
    
    private Class144() {
        this.texturesLinear = new HashMap();
        this.texturesNearest = new HashMap();
        this.dstPixelFormat = 6408;
    }
    
    public void setHoldTextureData(final boolean holdTextureData) {
        this.holdTextureData = holdTextureData;
    }
    
    public void setDeferredLoading(final boolean deferred) {
        this.deferred = deferred;
    }
    
    public boolean isDeferredLoading() {
        return this.deferred;
    }
    
    public void clear(final String s) {
        this.texturesLinear.remove(s);
        this.texturesNearest.remove(s);
    }
    
    public void clear() {
        this.texturesLinear.clear();
        this.texturesNearest.clear();
    }
    
    public void set16BitMode() {
        this.dstPixelFormat = 32859;
    }
    
    public static int createTextureID() {
        final IntBuffer intBuffer = createIntBuffer(1);
        Class144.GL.glGenTextures(intBuffer);
        return intBuffer.get(0);
    }
    
    public Class282 getTexture(final File file, final boolean b, final int n) throws IOException {
        return this.getTexture(new FileInputStream(file), file.getAbsolutePath(), b, n, null);
    }
    
    public Class282 getTexture(final File file, final boolean b, final int n, final int[] array) throws IOException {
        return this.getTexture(new FileInputStream(file), file.getAbsolutePath(), b, n, array);
    }
    
    public Class282 getTexture(final String s, final boolean b, final int n) throws IOException {
        return this.getTexture(Class337.getResourceAsStream(s), s, b, n, null);
    }
    
    public Class282 getTexture(final String s, final boolean b, final int n, final int[] array) throws IOException {
        return this.getTexture(Class337.getResourceAsStream(s), s, b, n, array);
    }
    
    public Class282 getTexture(final InputStream inputStream, final String s, final boolean b, final int n) throws IOException {
        return this.getTexture(inputStream, s, b, n, null);
    }
    
    public Class237 getTexture(final InputStream inputStream, final String s, final boolean b, final int n, final int[] array) throws IOException {
        if (this.deferred) {
            return new Class291(inputStream, s, b, n, array);
        }
        HashMap hashMap = this.texturesLinear;
        if (n == 9728) {
            hashMap = this.texturesNearest;
        }
        String string = s;
        if (array != null) {
            string = string + ":" + array[0] + ":" + array[1] + ":" + array[2];
        }
        final String string2 = string + ":" + b;
        if (this.holdTextureData) {
            final Class237 class237 = hashMap.get(string2);
            if (class237 != null) {
                return class237;
            }
        }
        else {
            final SoftReference softReference = (SoftReference)hashMap.get(string2);
            if (softReference != null) {
                final Class237 class238 = softReference.get();
                if (class238 != null) {
                    return class238;
                }
                hashMap.remove(string2);
            }
        }
        try {
            Class144.GL.glGetError();
        }
        catch (NullPointerException ex) {
            throw new RuntimeException("Image based resources must be loaded as part of init() or the game loop. They cannot be loaded before initialisation.");
        }
        final Class237 texture = this.getTexture(inputStream, s, 3553, n, n, b, array);
        texture.setCacheName(string2);
        if (this.holdTextureData) {
            hashMap.put(string2, texture);
        }
        else {
            hashMap.put(string2, new SoftReference(texture));
        }
        return texture;
    }
    
    private Class237 getTexture(final InputStream inputStream, final String s, final int n, final int n2, final int n3, final boolean b, final int[] array) throws IOException {
        final Class96 imageData = Class89.getImageDataFor(s);
        final ByteBuffer loadImage = imageData.loadImage(new BufferedInputStream(inputStream), b, array);
        final int textureID = createTextureID();
        final Class237 class237 = new Class237(s, n, textureID);
        Class144.GL.glBindTexture(n, textureID);
        final int width = imageData.getWidth();
        final int height = imageData.getHeight();
        final boolean alpha = imageData.getDepth() == 32;
        class237.setTextureWidth(imageData.getTexWidth());
        class237.setTextureHeight(imageData.getTexHeight());
        final int textureWidth = class237.getTextureWidth();
        final int textureHeight = class237.getTextureHeight();
        final IntBuffer intBuffer = BufferUtils.createIntBuffer(16);
        Class144.GL.glGetInteger(3379, intBuffer);
        final int value = intBuffer.get(0);
        if (textureWidth > value || textureHeight > value) {
            throw new IOException("Attempt to allocate a texture to big for the current hardware");
        }
        final int n4 = alpha ? 6408 : 6407;
        final int n5 = alpha ? 4 : 3;
        class237.setWidth(width);
        class237.setHeight(height);
        class237.setAlpha(alpha);
        if (this.holdTextureData) {
            class237.setTextureData(n4, n5, n3, n2, loadImage);
        }
        final Class311 gl = Class144.GL;
        final Class311 gl2 = Class144.GL;
        gl.glTexParameteri(n, 10241, n3);
        final Class311 gl3 = Class144.GL;
        final Class311 gl4 = Class144.GL;
        gl3.glTexParameteri(n, 10240, n2);
        Class144.GL.glTexImage2D(n, 0, this.dstPixelFormat, get2Fold(width), get2Fold(height), 0, n4, 5121, loadImage);
        return class237;
    }
    
    public Class282 createTexture(final int n, final int n2) throws IOException {
        return this.createTexture(n, n2, 9728);
    }
    
    public Class282 createTexture(final int n, final int n2, final int n3) throws IOException {
        return this.getTexture(new Class117(n, n2), n3);
    }
    
    public Class282 getTexture(final Class257 class257, final int n) throws IOException {
        final int n2 = 3553;
        final ByteBuffer imageBufferData = class257.getImageBufferData();
        final int textureID = createTextureID();
        final Class237 class258 = new Class237("generated:" + class257, n2, textureID);
        Class144.GL.glBindTexture(n2, textureID);
        final int width = class257.getWidth();
        final int height = class257.getHeight();
        final boolean alpha = class257.getDepth() == 32;
        class258.setTextureWidth(class257.getTexWidth());
        class258.setTextureHeight(class257.getTexHeight());
        final int textureWidth = class258.getTextureWidth();
        final int textureHeight = class258.getTextureHeight();
        final int n3 = alpha ? 6408 : 6407;
        final int n4 = alpha ? 4 : 3;
        class258.setWidth(width);
        class258.setHeight(height);
        class258.setAlpha(alpha);
        final IntBuffer intBuffer = BufferUtils.createIntBuffer(16);
        Class144.GL.glGetInteger(3379, intBuffer);
        final int value = intBuffer.get(0);
        if (textureWidth > value || textureHeight > value) {
            throw new IOException("Attempt to allocate a texture to big for the current hardware");
        }
        if (this.holdTextureData) {
            class258.setTextureData(n3, n4, n, n, imageBufferData);
        }
        Class144.GL.glTexParameteri(n2, 10241, n);
        Class144.GL.glTexParameteri(n2, 10240, n);
        Class144.GL.glTexImage2D(n2, 0, this.dstPixelFormat, get2Fold(width), get2Fold(height), 0, n3, 5121, imageBufferData);
        return class258;
    }
    
    public static int get2Fold(final int n) {
        int i;
        for (i = 2; i < n; i *= 2) {}
        return i;
    }
    
    public static IntBuffer createIntBuffer(final int n) {
        final ByteBuffer allocateDirect = ByteBuffer.allocateDirect(4 * n);
        allocateDirect.order(ByteOrder.nativeOrder());
        return allocateDirect.asIntBuffer();
    }
    
    public void reload() {
        final Iterator<Class237> iterator = this.texturesLinear.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().reload();
        }
        final Iterator<Class237> iterator2 = this.texturesNearest.values().iterator();
        while (iterator2.hasNext()) {
            iterator2.next().reload();
        }
    }
    
    public int reload(final Class237 class237, final int n, final int n2, final int n3, final int n4, final ByteBuffer byteBuffer) {
        final int n5 = 3553;
        final int textureID = createTextureID();
        Class144.GL.glBindTexture(n5, textureID);
        Class144.GL.glTexParameteri(n5, 10241, n3);
        Class144.GL.glTexParameteri(n5, 10240, n4);
        Class144.GL.glTexImage2D(n5, 0, this.dstPixelFormat, class237.getTextureWidth(), class237.getTextureHeight(), 0, n, 5121, byteBuffer);
        return textureID;
    }
    
    static {
        Class144.GL = Class197.get();
        loader = new Class144();
    }
}
