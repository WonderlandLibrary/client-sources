package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;

public class BigImage extends Image
{
    protected static SGL HorizonCode_Horizon_È;
    private static Image Ñ¢á;
    private Image[][] ŒÏ;
    private int Çªà¢;
    private int Ê;
    private int ÇŽÉ;
    private int ˆá;
    
    static {
        BigImage.HorizonCode_Horizon_È = Renderer.HorizonCode_Horizon_È();
    }
    
    public static final int HorizonCode_Horizon_È() {
        final IntBuffer buffer = BufferUtils.createIntBuffer(16);
        BigImage.HorizonCode_Horizon_È.HorizonCode_Horizon_È(3379, buffer);
        return buffer.get(0);
    }
    
    private BigImage() {
        this.Ø­à = true;
    }
    
    public BigImage(final String ref) throws SlickException {
        this(ref, 2);
    }
    
    public BigImage(final String ref, final int filter) throws SlickException {
        this.HorizonCode_Horizon_È(ref, filter, HorizonCode_Horizon_È());
    }
    
    public BigImage(final String ref, final int filter, final int tileSize) throws SlickException {
        this.HorizonCode_Horizon_È(ref, filter, tileSize);
    }
    
    public BigImage(final LoadableImageData data, final ByteBuffer imageBuffer, final int filter) {
        this.HorizonCode_Horizon_È(data, imageBuffer, filter, HorizonCode_Horizon_È());
    }
    
    public BigImage(final LoadableImageData data, final ByteBuffer imageBuffer, final int filter, final int tileSize) {
        this.HorizonCode_Horizon_È(data, imageBuffer, filter, tileSize);
    }
    
    public Image HorizonCode_Horizon_È(final int x, final int y) {
        return this.ŒÏ[x][y];
    }
    
    private void HorizonCode_Horizon_È(final String ref, final int filter, final int tileSize) throws SlickException {
        try {
            final LoadableImageData data = ImageDataFactory.HorizonCode_Horizon_È(ref);
            final ByteBuffer imageBuffer = data.HorizonCode_Horizon_È(ResourceLoader.HorizonCode_Horizon_È(ref), false, null);
            this.HorizonCode_Horizon_È(data, imageBuffer, filter, tileSize);
        }
        catch (IOException e) {
            throw new SlickException("Failed to load: " + ref, e);
        }
    }
    
    private void HorizonCode_Horizon_È(final LoadableImageData data, final ByteBuffer imageBuffer, final int filter, final int tileSize) {
        final int dataWidth = data.Âµá€();
        final int dataHeight = data.Ø­áŒŠá();
        final int ó = data.Ó();
        this.ÂµÈ = ó;
        this.ÇŽÉ = ó;
        final int â = data.Â();
        this.á = â;
        this.ˆá = â;
        if (dataWidth <= tileSize && dataHeight <= tileSize) {
            this.ŒÏ = new Image[1][1];
            final ImageData tempData = new ImageData() {
                @Override
                public int HorizonCode_Horizon_È() {
                    return data.HorizonCode_Horizon_È();
                }
                
                @Override
                public int Â() {
                    return dataHeight;
                }
                
                @Override
                public ByteBuffer Ý() {
                    return imageBuffer;
                }
                
                @Override
                public int Ø­áŒŠá() {
                    return dataHeight;
                }
                
                @Override
                public int Âµá€() {
                    return dataWidth;
                }
                
                @Override
                public int Ó() {
                    return dataWidth;
                }
            };
            this.ŒÏ[0][0] = new Image(tempData, filter);
            this.Çªà¢ = 1;
            this.Ê = 1;
            this.Ø­à = true;
            return;
        }
        this.Çªà¢ = (this.ÇŽÉ - 1) / tileSize + 1;
        this.Ê = (this.ˆá - 1) / tileSize + 1;
        this.ŒÏ = new Image[this.Çªà¢][this.Ê];
        final int components = data.HorizonCode_Horizon_È() / 8;
        for (int x = 0; x < this.Çªà¢; ++x) {
            for (int y = 0; y < this.Ê; ++y) {
                final int finalX = (x + 1) * tileSize;
                final int finalY = (y + 1) * tileSize;
                final int imageWidth = Math.min(this.ÇŽÉ - x * tileSize, tileSize);
                final int imageHeight = Math.min(this.ˆá - y * tileSize, tileSize);
                final ByteBuffer subBuffer = BufferUtils.createByteBuffer(tileSize * tileSize * components);
                final int xo = x * tileSize * components;
                final byte[] byteData = new byte[tileSize * components];
                for (int i = 0; i < tileSize; ++i) {
                    final int yo = (y * tileSize + i) * dataWidth * components;
                    imageBuffer.position(yo + xo);
                    imageBuffer.get(byteData, 0, tileSize * components);
                    subBuffer.put(byteData);
                }
                subBuffer.flip();
                final ImageData imgData = new ImageData() {
                    @Override
                    public int HorizonCode_Horizon_È() {
                        return data.HorizonCode_Horizon_È();
                    }
                    
                    @Override
                    public int Â() {
                        return imageHeight;
                    }
                    
                    @Override
                    public int Ó() {
                        return imageWidth;
                    }
                    
                    @Override
                    public ByteBuffer Ý() {
                        return subBuffer;
                    }
                    
                    @Override
                    public int Ø­áŒŠá() {
                        return tileSize;
                    }
                    
                    @Override
                    public int Âµá€() {
                        return tileSize;
                    }
                };
                this.ŒÏ[x][y] = new Image(imgData, filter);
            }
        }
        this.Ø­à = true;
    }
    
    @Override
    public void Â() {
        throw new OperationNotSupportedException("Can't bind big images yet");
    }
    
    @Override
    public Image Ý() {
        throw new OperationNotSupportedException("Can't copy big images yet");
    }
    
    @Override
    public void Ø­áŒŠá() {
        this.HorizonCode_Horizon_È(0.0f, 0.0f);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float x, final float y, final Color filter) {
        this.HorizonCode_Horizon_È(x, y, this.ÂµÈ, this.á, filter);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float x, final float y, final float scale, final Color filter) {
        this.HorizonCode_Horizon_È(x, y, this.ÂµÈ * scale, this.á * scale, filter);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float x, final float y, final float width, final float height, final Color filter) {
        final float sx = width / this.ÇŽÉ;
        final float sy = height / this.ˆá;
        BigImage.HorizonCode_Horizon_È.Â(x, y, 0.0f);
        BigImage.HorizonCode_Horizon_È.HorizonCode_Horizon_È(sx, sy, 1.0f);
        float xp = 0.0f;
        float yp = 0.0f;
        for (int tx = 0; tx < this.Çªà¢; ++tx) {
            yp = 0.0f;
            for (int ty = 0; ty < this.Ê; ++ty) {
                final Image image = this.ŒÏ[tx][ty];
                image.HorizonCode_Horizon_È(xp, yp, image.ŒÏ(), image.Çªà¢(), filter);
                yp += image.Çªà¢();
                if (ty == this.Ê - 1) {
                    xp += image.ŒÏ();
                }
            }
        }
        BigImage.HorizonCode_Horizon_È.HorizonCode_Horizon_È(1.0f / sx, 1.0f / sy, 1.0f);
        BigImage.HorizonCode_Horizon_È.Â(-x, -y, 0.0f);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float x, final float y, final float x2, final float y2, final float srcx, final float srcy, final float srcx2, final float srcy2) {
        final int srcwidth = (int)(srcx2 - srcx);
        final int srcheight = (int)(srcy2 - srcy);
        final Image subImage = this.HorizonCode_Horizon_È((int)srcx, (int)srcy, srcwidth, srcheight);
        final int width = (int)(x2 - x);
        final int height = (int)(y2 - y);
        subImage.HorizonCode_Horizon_È(x, y, width, height);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float x, final float y, final float srcx, final float srcy, final float srcx2, final float srcy2) {
        final int srcwidth = (int)(srcx2 - srcx);
        final int srcheight = (int)(srcy2 - srcy);
        this.HorizonCode_Horizon_È(x, y, srcwidth, srcheight, srcx, srcy, srcx2, srcy2);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float x, final float y, final float width, final float height) {
        this.HorizonCode_Horizon_È(x, y, width, height, Color.Ý);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float x, final float y, final float scale) {
        this.HorizonCode_Horizon_È(x, y, scale, Color.Ý);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float x, final float y) {
        this.HorizonCode_Horizon_È(x, y, Color.Ý);
    }
    
    @Override
    public void Â(final float x, final float y, final float width, final float height) {
        final float sx = width / this.ÇŽÉ;
        final float sy = height / this.ˆá;
        float xp = 0.0f;
        float yp = 0.0f;
        for (int tx = 0; tx < this.Çªà¢; ++tx) {
            yp = 0.0f;
            for (int ty = 0; ty < this.Ê; ++ty) {
                final Image image = this.ŒÏ[tx][ty];
                if (BigImage.Ñ¢á == null || image.áŒŠÆ() != BigImage.Ñ¢á.áŒŠÆ()) {
                    if (BigImage.Ñ¢á != null) {
                        BigImage.Ñ¢á.Âµá€();
                    }
                    (BigImage.Ñ¢á = image).Ó();
                }
                image.Â(xp + x, yp + y, image.ŒÏ(), image.Çªà¢());
                yp += image.Çªà¢();
                if (ty == this.Ê - 1) {
                    xp += image.ŒÏ();
                }
            }
        }
    }
    
    @Override
    public void Ý(final float x, final float y, final float width, final float height) {
        final float sx = width / this.ÇŽÉ;
        final float sy = height / this.ˆá;
        BigImage.HorizonCode_Horizon_È.Â(x, y, 0.0f);
        BigImage.HorizonCode_Horizon_È.HorizonCode_Horizon_È(sx, sy, 1.0f);
        float xp = 0.0f;
        float yp = 0.0f;
        for (int tx = 0; tx < this.Çªà¢; ++tx) {
            yp = 0.0f;
            for (int ty = 0; ty < this.Ê; ++ty) {
                final Image image = this.ŒÏ[tx][ty];
                image.Ý(xp, yp, image.ŒÏ(), image.Çªà¢());
                yp += image.Çªà¢();
                if (ty == this.Ê - 1) {
                    xp += image.ŒÏ();
                }
            }
        }
        BigImage.HorizonCode_Horizon_È.HorizonCode_Horizon_È(1.0f / sx, 1.0f / sy, 1.0f);
        BigImage.HorizonCode_Horizon_È.Â(-x, -y, 0.0f);
    }
    
    @Override
    public void Â(final float x, final float y) {
        this.Ý(x, y, this.ÂµÈ, this.á);
    }
    
    @Override
    public void Âµá€() {
        if (BigImage.Ñ¢á != null) {
            BigImage.Ñ¢á.Âµá€();
        }
        BigImage.Ñ¢á = null;
    }
    
    @Override
    public void Ó() {
    }
    
    @Override
    public void à() {
        throw new OperationNotSupportedException("Doesn't make sense for tiled operations");
    }
    
    @Override
    public Color Â(final int x, final int y) {
        throw new OperationNotSupportedException("Can't use big images as buffers");
    }
    
    @Override
    public Image HorizonCode_Horizon_È(final boolean flipHorizontal, final boolean flipVertical) {
        final BigImage image = new BigImage();
        image.ŒÏ = this.ŒÏ;
        image.Çªà¢ = this.Çªà¢;
        image.Ê = this.Ê;
        image.ÂµÈ = this.ÂµÈ;
        image.á = this.á;
        image.ÇŽÉ = this.ÇŽÉ;
        image.ˆá = this.ˆá;
        if (flipHorizontal) {
            final Image[][] images = image.ŒÏ;
            image.ŒÏ = new Image[this.Çªà¢][this.Ê];
            for (int x = 0; x < this.Çªà¢; ++x) {
                for (int y = 0; y < this.Ê; ++y) {
                    image.ŒÏ[x][y] = images[this.Çªà¢ - 1 - x][y].HorizonCode_Horizon_È(true, false);
                }
            }
        }
        if (flipVertical) {
            final Image[][] images = image.ŒÏ;
            image.ŒÏ = new Image[this.Çªà¢][this.Ê];
            for (int x = 0; x < this.Çªà¢; ++x) {
                for (int y = 0; y < this.Ê; ++y) {
                    image.ŒÏ[x][y] = images[x][this.Ê - 1 - y].HorizonCode_Horizon_È(false, true);
                }
            }
        }
        return image;
    }
    
    @Override
    public Graphics Ø() throws SlickException {
        throw new OperationNotSupportedException("Can't use big images as offscreen buffers");
    }
    
    @Override
    public Image HorizonCode_Horizon_È(final float scale) {
        return this.Ý((int)(scale * this.ÂµÈ), (int)(scale * this.á));
    }
    
    @Override
    public Image Ý(final int width, final int height) {
        final BigImage image = new BigImage();
        image.ŒÏ = this.ŒÏ;
        image.Çªà¢ = this.Çªà¢;
        image.Ê = this.Ê;
        image.ÂµÈ = width;
        image.á = height;
        image.ÇŽÉ = this.ÇŽÉ;
        image.ˆá = this.ˆá;
        return image;
    }
    
    @Override
    public Image HorizonCode_Horizon_È(final int x, final int y, final int width, final int height) {
        final BigImage image = new BigImage();
        image.ÂµÈ = width;
        image.á = height;
        image.ÇŽÉ = width;
        image.ˆá = height;
        image.ŒÏ = new Image[this.Çªà¢][this.Ê];
        float xp = 0.0f;
        float yp = 0.0f;
        final int x2 = x + width;
        final int y2 = y + height;
        int startx = 0;
        int starty = 0;
        boolean foundStart = false;
        for (int xt = 0; xt < this.Çªà¢; ++xt) {
            yp = 0.0f;
            starty = 0;
            foundStart = false;
            for (int yt = 0; yt < this.Ê; ++yt) {
                final Image current = this.ŒÏ[xt][yt];
                final int xp2 = (int)(xp + current.ŒÏ());
                final int yp2 = (int)(yp + current.Çªà¢());
                final int targetX1 = (int)Math.max(x, xp);
                final int targetY1 = (int)Math.max(y, yp);
                final int targetX2 = Math.min(x2, xp2);
                final int targetY2 = Math.min(y2, yp2);
                final int targetWidth = targetX2 - targetX1;
                final int targetHeight = targetY2 - targetY1;
                if (targetWidth > 0 && targetHeight > 0) {
                    final Image subImage = current.HorizonCode_Horizon_È((int)(targetX1 - xp), (int)(targetY1 - yp), targetX2 - targetX1, targetY2 - targetY1);
                    foundStart = true;
                    image.ŒÏ[startx][starty] = subImage;
                    ++starty;
                    image.Ê = Math.max(image.Ê, starty);
                }
                yp += current.Çªà¢();
                if (yt == this.Ê - 1) {
                    xp += current.ŒÏ();
                }
            }
            if (foundStart) {
                ++startx;
                final BigImage bigImage = image;
                ++bigImage.Çªà¢;
            }
        }
        return image;
    }
    
    @Override
    public Texture áŒŠÆ() {
        throw new OperationNotSupportedException("Can't use big images as offscreen buffers");
    }
    
    @Override
    protected void áˆºÑ¢Õ() {
        throw new OperationNotSupportedException("Can't use big images as offscreen buffers");
    }
    
    @Override
    protected void ÂµÈ() {
        throw new OperationNotSupportedException("Can't use big images as offscreen buffers");
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Texture texture) {
        throw new OperationNotSupportedException("Can't use big images as offscreen buffers");
    }
    
    public Image Ø­áŒŠá(final int offsetX, final int offsetY) {
        return this.ŒÏ[offsetX][offsetY];
    }
    
    public int á() {
        return this.Çªà¢;
    }
    
    public int ˆÏ­() {
        return this.Ê;
    }
    
    @Override
    public String toString() {
        return "[BIG IMAGE]";
    }
    
    @Override
    public void £á() throws SlickException {
        for (int tx = 0; tx < this.Çªà¢; ++tx) {
            for (int ty = 0; ty < this.Ê; ++ty) {
                final Image image = this.ŒÏ[tx][ty];
                image.£á();
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float x, final float y, final float x2, final float y2, final float srcx, final float srcy, final float srcx2, final float srcy2, final Color filter) {
        final int srcwidth = (int)(srcx2 - srcx);
        final int srcheight = (int)(srcy2 - srcy);
        final Image subImage = this.HorizonCode_Horizon_È((int)srcx, (int)srcy, srcwidth, srcheight);
        final int width = (int)(x2 - x);
        final int height = (int)(y2 - y);
        subImage.HorizonCode_Horizon_È(x, y, width, height, filter);
    }
    
    @Override
    public void Ý(final float x, final float y) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void Â(final float x, final float y, final float x2, final float y2, final float srcx, final float srcy, final float srcx2, final float srcy2, final Color filter) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void Â(final float x, final float y, final float x2, final float y2, final float srcx, final float srcy, final float srcx2, final float srcy2) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void Â(final float x, final float y, final float width, final float height, final Color col) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void Ø­áŒŠá(final float x, final float y, final float hshear, final float vshear) {
        throw new UnsupportedOperationException();
    }
}
