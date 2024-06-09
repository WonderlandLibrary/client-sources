package exhibition.management.spotify;

import com.google.common.base.Charsets;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.google.common.cache.Weigher;
import com.google.common.collect.Maps;
import exhibition.management.spotify.ResourceLocation;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.base64.Base64;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.optifine.TextureUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.Validate;
import org.lwjgl.opengl.GL11;

public class Base64Renderer {
    private static Cache<String, Base64Renderer> CACHE = CacheBuilder.newBuilder().expireAfterAccess(5L, TimeUnit.MINUTES).maximumWeight(1000000L).weigher((Weigher)new Weigher<String, Base64Renderer>(){

        public int weigh(String s, Base64Renderer base64Renderer) {
            String base64String = base64Renderer.getBase64String();
            return base64String == null ? 0 : base64String.length();
        }
    }).removalListener((RemovalListener)new RemovalListener<String, Base64Renderer>(){

        public void onRemoval(RemovalNotification<String, Base64Renderer> removalNotification) {
            Base64Renderer renderer = (Base64Renderer)removalNotification.getValue();
            if (renderer != null) {
                renderer.reset();
            }
        }
    }).build();
    private String base64String;
    private ITextureObject dynamicImage;
    private net.minecraft.util.ResourceLocation resourceLocation;
    private net.minecraft.util.ResourceLocation fallbackResource;
    private boolean interpolateLinear = false;
    private int x;
    private int y;
    private int width;
    private int height;
    private ThreadDownloadImageData albumCover;
    private final Map<net.minecraft.util.ResourceLocation, DynamicTexture> dynamicTextures = Maps.newHashMap();

    public Base64Renderer() {
        this(new net.minecraft.util.ResourceLocation("textures/skin.png"));
    }

    public Base64Renderer(net.minecraft.util.ResourceLocation fallbackResource) {
        this(fallbackResource, 64, 64);
    }

    public Base64Renderer(int width, int height) {
        this(new net.minecraft.util.ResourceLocation("textures/skin.png"), width, height);
    }

    public Base64Renderer(net.minecraft.util.ResourceLocation fallbackResource, int width, int height) {
        this.fallbackResource = fallbackResource;
        this.width = width;
        this.height = height;
    }

    public void renderImage() {
        this.renderImage(this.x, this.y, this.width, this.height);
    }

    public void renderImage(int x, int y, int width, int height) {
        this.renderImage(x, y, width, height, 1.0f, 1.0f, 1.0f, 1.0f);
    }

    public void renderImage(int x, int y, int width, int height, float r, float g, float b, float a) {
        if (this.dynamicImage == null) {
            if (this.fallbackResource != null) {
                this.render(x, y, width, height, this.fallbackResource, r, g, b, a);
            }
        } else {
            this.render(x, y, width, height, this.resourceLocation, r, b, g, a);
        }
    }

    private void render(int x, int y, int width, int height, net.minecraft.util.ResourceLocation resource, float r, float g, float b, float a) {
        this.bindTexture(resource);
        GlStateManager.color(r, g, b, a);
        GlStateManager.disableBlend();
        if (this.interpolateLinear) {
            GL11.glTexParameteri((int)3553, (int)10240, (int)9729);
            GL11.glTexParameteri((int)3553, (int)10241, (int)9986);
        }
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, width, height, width, height);
        if (this.interpolateLinear) {
            GL11.glTexParameteri((int)3553, (int)10240, (int)9728);
            GL11.glTexParameteri((int)3553, (int)10241, (int)9984);
        }
    }

    public String getBase64String() {
        return this.base64String;
    }

    public void setBase64String(String base64String, String resourceLocation) {
        this.base64String = base64String;
        this.resourceLocation = new ResourceLocation("exhi", resourceLocation);
        this.dynamicImage = (ITextureObject)this.getTexture(this.resourceLocation);
        this.prepareImage();
    }

    public Object getTexture(Object resourceLocation) {
        if (resourceLocation instanceof net.minecraft.util.ResourceLocation && ((net.minecraft.util.ResourceLocation)resourceLocation).getResourceDomain().equals("exhi") && this.dynamicTextures.containsKey(resourceLocation)) {
            return this.dynamicTextures.get(resourceLocation);
        }
        return TextureUtils.getTexture((net.minecraft.util.ResourceLocation)resourceLocation);
    }

    public void setBase64String(String base64String, net.minecraft.util.ResourceLocation resourceLocation) {
        this.base64String = base64String;
        this.resourceLocation = resourceLocation;
        this.dynamicImage = (ITextureObject)this.getTexture(this.resourceLocation);
        this.prepareImage();
    }

    public void reset() {
        this.delete(this.resourceLocation);
        this.base64String = null;
        this.resourceLocation = null;
        this.dynamicImage = null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void prepareImage() {
        BufferedImage localBufferedImage;
        if (this.base64String == null) {
            this.delete(this.resourceLocation);
            this.dynamicImage = null;
            return;
        }
        ByteBuf localByteBuf1 = Unpooled.copiedBuffer((CharSequence)this.base64String, (Charset)Charsets.UTF_8);
        ByteBuf localByteBuf2 = null;
        try {
            localByteBuf2 = Base64.decode((ByteBuf)localByteBuf1);
            localBufferedImage = Base64Renderer.read((InputStream)new ByteBufInputStream(localByteBuf2));
            Validate.validState((boolean)(localBufferedImage.getWidth() == this.width), (String)("Must be " + this.width + " pixels wide"), (Object[])new Object[0]);
            Validate.validState((boolean)(localBufferedImage.getHeight() == this.height), (String)("Must be " + this.height + " pixels high"), (Object[])new Object[0]);
        }
        catch (Exception e) {
            System.out.println("Could not prepare base64 renderer image");
            e.printStackTrace();
            this.delete(this.resourceLocation);
            this.dynamicImage = null;
            return;
        }
        finally {
            localByteBuf1.release();
            if (localByteBuf2 != null) {
                localByteBuf2.release();
            }
        }
        if (this.dynamicImage == null) {
            this.dynamicImage = (ITextureObject)this.createDynamicImage(this.resourceLocation, this.width, this.height);
        }
        this.fillDynamicImage(this.dynamicImage, localBufferedImage);
    }

    public void fillDynamicImage(Object dynamicImage, BufferedImage image) {
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), ((DynamicTexture)dynamicImage).getTextureData(), 0, image.getWidth());
        ((DynamicTexture)dynamicImage).updateDynamicTexture();
    }

    public Object createDynamicImage(Object resourceLocation, int width, int height) {
        DynamicTexture dynamicImage = new DynamicTexture(width, height);
        if (((net.minecraft.util.ResourceLocation)resourceLocation).getResourceDomain().equals("exhi")) {
            this.dynamicTextures.put((net.minecraft.util.ResourceLocation)resourceLocation, dynamicImage);
        } else {
            Minecraft.getMinecraft().getTextureManager().loadTexture((net.minecraft.util.ResourceLocation)resourceLocation, dynamicImage);
        }
        return dynamicImage;
    }

    public void bindTexture(Object resourceLocation) {
        if (resourceLocation instanceof net.minecraft.util.ResourceLocation && ((net.minecraft.util.ResourceLocation)resourceLocation).getResourceDomain().equals("exhi") && this.dynamicTextures.containsKey(resourceLocation)) {
            GlStateManager.func_179144_i(this.dynamicTextures.get(resourceLocation).getGlTextureId());
        } else {
            Minecraft.getMinecraft().getTextureManager().bindTexture((net.minecraft.util.ResourceLocation)resourceLocation);
        }
    }

    public void deleteTexture(Object resourceLocation) {
        if (resourceLocation instanceof net.minecraft.util.ResourceLocation && ((net.minecraft.util.ResourceLocation)resourceLocation).getResourceDomain().equals("exhi") && this.dynamicTextures.containsKey(resourceLocation)) {
            this.dynamicTextures.remove(resourceLocation);
            Minecraft.getMinecraft().getTextureManager().deleteTexture((net.minecraft.util.ResourceLocation)resourceLocation);
        }
    }

    private static BufferedImage read(InputStream byteBuf) throws IOException {
        try {
            BufferedImage bufferedImage = ImageIO.read(byteBuf);
            return bufferedImage;
        }
        finally {
            IOUtils.closeQuietly((InputStream)byteBuf);
        }
    }

    private void delete(net.minecraft.util.ResourceLocation resource) {
        this.deleteTexture(resource);
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidthAndHeight(int size) {
        this.width = size;
        this.height = size;
    }

    public boolean isInterpolateLinear() {
        return this.interpolateLinear;
    }

    public void setInterpolateLinear(boolean interpolateLinear) {
        this.interpolateLinear = interpolateLinear;
    }

    public static Base64Renderer getRenderer(BufferedImage icon, String id) {
        Base64Renderer renderer = (Base64Renderer)CACHE.getIfPresent(id);
        if (renderer != null) {
            return renderer;
        }
        Base64Renderer finalRenderer = new Base64Renderer(null, icon.getWidth(), icon.getHeight());
        CACHE.put(id, finalRenderer);
        try {
            ByteBuf decodedBuffer = Unpooled.buffer();
            ImageIO.write((RenderedImage)icon, "PNG", (OutputStream)new ByteBufOutputStream(decodedBuffer));
            ByteBuf encodedBuffer = Base64.encode((ByteBuf)decodedBuffer);
            String imageDataString = encodedBuffer.toString(Charsets.UTF_8);
            finalRenderer.setBase64String(imageDataString, id);
        }
        catch (Exception e) {
            System.out.println("Could not load icon " + id + e);
        }
        return finalRenderer;
    }

}

