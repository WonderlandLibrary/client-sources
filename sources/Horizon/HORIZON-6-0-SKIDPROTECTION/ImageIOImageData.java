package HORIZON-6-0-SKIDPROTECTION;

import java.awt.image.WritableRaster;
import java.nio.ByteOrder;
import java.awt.image.DataBufferByte;
import java.awt.image.ImageObserver;
import java.awt.Image;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Hashtable;
import java.awt.Point;
import java.awt.image.Raster;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.io.InputStream;
import java.awt.image.ComponentColorModel;
import java.awt.color.ColorSpace;
import java.awt.image.ColorModel;

public class ImageIOImageData implements LoadableImageData
{
    private static final ColorModel HorizonCode_Horizon_È;
    private static final ColorModel Â;
    private int Ý;
    private int Ø­áŒŠá;
    private int Âµá€;
    private int Ó;
    private int à;
    private boolean Ø;
    
    static {
        HorizonCode_Horizon_È = new ComponentColorModel(ColorSpace.getInstance(1000), new int[] { 8, 8, 8, 8 }, true, false, 3, 0);
        Â = new ComponentColorModel(ColorSpace.getInstance(1000), new int[] { 8, 8, 8, 0 }, false, false, 1, 0);
    }
    
    public ImageIOImageData() {
        this.Ø = true;
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return this.Ý;
    }
    
    @Override
    public int Â() {
        return this.Ø­áŒŠá;
    }
    
    @Override
    public int Ø­áŒŠá() {
        return this.à;
    }
    
    @Override
    public int Âµá€() {
        return this.Ó;
    }
    
    @Override
    public int Ó() {
        return this.Âµá€;
    }
    
    @Override
    public ByteBuffer HorizonCode_Horizon_È(final InputStream fis) throws IOException {
        return this.HorizonCode_Horizon_È(fis, true, null);
    }
    
    @Override
    public ByteBuffer HorizonCode_Horizon_È(final InputStream fis, final boolean flipped, final int[] transparent) throws IOException {
        return this.HorizonCode_Horizon_È(fis, flipped, false, transparent);
    }
    
    @Override
    public ByteBuffer HorizonCode_Horizon_È(final InputStream fis, final boolean flipped, boolean forceAlpha, final int[] transparent) throws IOException {
        if (transparent != null) {
            forceAlpha = true;
        }
        final BufferedImage bufferedImage = ImageIO.read(fis);
        return this.HorizonCode_Horizon_È(bufferedImage, flipped, forceAlpha, transparent);
    }
    
    public ByteBuffer HorizonCode_Horizon_È(final BufferedImage image, final boolean flipped, final boolean forceAlpha, final int[] transparent) {
        ByteBuffer imageBuffer = null;
        int texWidth = 2;
        int texHeight = 2;
        while (texWidth < image.getWidth()) {
            texWidth *= 2;
        }
        while (texHeight < image.getHeight()) {
            texHeight *= 2;
        }
        this.Âµá€ = image.getWidth();
        this.Ø­áŒŠá = image.getHeight();
        this.à = texHeight;
        this.Ó = texWidth;
        final boolean useAlpha = image.getColorModel().hasAlpha() || forceAlpha;
        BufferedImage texImage;
        if (useAlpha) {
            this.Ý = 32;
            final WritableRaster raster = Raster.createInterleavedRaster(0, texWidth, texHeight, 4, null);
            texImage = new BufferedImage(ImageIOImageData.HorizonCode_Horizon_È, raster, false, new Hashtable<Object, Object>());
        }
        else {
            this.Ý = 24;
            final WritableRaster raster = Raster.createInterleavedRaster(0, texWidth, texHeight, 3, null);
            texImage = new BufferedImage(ImageIOImageData.Â, raster, false, new Hashtable<Object, Object>());
        }
        final Graphics2D g = (Graphics2D)texImage.getGraphics();
        if (useAlpha) {
            g.setColor(new Color(0.0f, 0.0f, 0.0f, 0.0f));
            g.fillRect(0, 0, texWidth, texHeight);
        }
        if (flipped) {
            g.scale(1.0, -1.0);
            g.drawImage(image, 0, -this.Ø­áŒŠá, null);
        }
        else {
            g.drawImage(image, 0, 0, null);
        }
        if (this.Ø) {
            if (this.Ø­áŒŠá < texHeight - 1) {
                this.HorizonCode_Horizon_È(texImage, 0, 0, this.Âµá€, 1, 0, texHeight - 1);
                this.HorizonCode_Horizon_È(texImage, 0, this.Ø­áŒŠá - 1, this.Âµá€, 1, 0, 1);
            }
            if (this.Âµá€ < texWidth - 1) {
                this.HorizonCode_Horizon_È(texImage, 0, 0, 1, this.Ø­áŒŠá, texWidth - 1, 0);
                this.HorizonCode_Horizon_È(texImage, this.Âµá€ - 1, 0, 1, this.Ø­áŒŠá, 1, 0);
            }
        }
        final byte[] data = ((DataBufferByte)texImage.getRaster().getDataBuffer()).getData();
        if (transparent != null) {
            for (int i = 0; i < data.length; i += 4) {
                boolean match = true;
                for (int c = 0; c < 3; ++c) {
                    final int value = (data[i + c] < 0) ? (256 + data[i + c]) : data[i + c];
                    if (value != transparent[c]) {
                        match = false;
                    }
                }
                if (match) {
                    data[i + 3] = 0;
                }
            }
        }
        imageBuffer = ByteBuffer.allocateDirect(data.length);
        imageBuffer.order(ByteOrder.nativeOrder());
        imageBuffer.put(data, 0, data.length);
        imageBuffer.flip();
        g.dispose();
        return imageBuffer;
    }
    
    @Override
    public ByteBuffer Ý() {
        throw new RuntimeException("ImageIOImageData doesn't store it's image.");
    }
    
    private void HorizonCode_Horizon_È(final BufferedImage image, final int x, final int y, final int width, final int height, final int dx, final int dy) {
        final Graphics2D g = (Graphics2D)image.getGraphics();
        g.drawImage(image.getSubimage(x, y, width, height), x + dx, y + dy, null);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final boolean edging) {
        this.Ø = edging;
    }
}
