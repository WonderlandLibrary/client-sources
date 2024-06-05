package ClassSub;

import java.io.*;
import javax.imageio.*;
import java.util.*;
import java.awt.*;
import java.nio.*;
import java.awt.color.*;
import java.awt.image.*;

public class Class293 implements Class96
{
    private static final ColorModel glAlphaColorModel;
    private static final ColorModel glColorModel;
    private int depth;
    private int height;
    private int width;
    private int texWidth;
    private int texHeight;
    private boolean edging;
    
    
    public Class293() {
        this.edging = true;
    }
    
    @Override
    public int getDepth() {
        return this.depth;
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
    public ByteBuffer loadImage(final InputStream inputStream) throws IOException {
        return this.loadImage(inputStream, true, null);
    }
    
    @Override
    public ByteBuffer loadImage(final InputStream inputStream, final boolean b, final int[] array) throws IOException {
        return this.loadImage(inputStream, b, false, array);
    }
    
    @Override
    public ByteBuffer loadImage(final InputStream inputStream, final boolean b, boolean b2, final int[] array) throws IOException {
        if (array != null) {
            b2 = true;
        }
        return this.imageToByteBuffer(ImageIO.read(inputStream), b, b2, array);
    }
    
    public ByteBuffer imageToByteBuffer(final BufferedImage bufferedImage, final boolean b, final boolean b2, final int[] array) {
        int i = 2;
        int j = 2;
        while (i < bufferedImage.getWidth()) {
            i *= 2;
        }
        while (j < bufferedImage.getHeight()) {
            j *= 2;
        }
        this.width = bufferedImage.getWidth();
        this.height = bufferedImage.getHeight();
        this.texHeight = j;
        this.texWidth = i;
        final boolean b3 = bufferedImage.getColorModel().hasAlpha() || b2;
        BufferedImage bufferedImage2;
        if (b3) {
            this.depth = 32;
            bufferedImage2 = new BufferedImage(Class293.glAlphaColorModel, Raster.createInterleavedRaster(0, i, j, 4, null), false, new Hashtable<Object, Object>());
        }
        else {
            this.depth = 24;
            bufferedImage2 = new BufferedImage(Class293.glColorModel, Raster.createInterleavedRaster(0, i, j, 3, null), false, new Hashtable<Object, Object>());
        }
        final Graphics2D graphics2D = (Graphics2D)bufferedImage2.getGraphics();
        if (b3) {
            graphics2D.setColor(new Color(0.0f, 0.0f, 0.0f, 0.0f));
            graphics2D.fillRect(0, 0, i, j);
        }
        if (b) {
            graphics2D.scale(1.0, -1.0);
            graphics2D.drawImage(bufferedImage, 0, -this.height, null);
        }
        else {
            graphics2D.drawImage(bufferedImage, 0, 0, null);
        }
        if (this.edging) {
            if (this.height < j - 1) {
                this.copyArea(bufferedImage2, 0, 0, this.width, 1, 0, j - 1);
                this.copyArea(bufferedImage2, 0, this.height - 1, this.width, 1, 0, 1);
            }
            if (this.width < i - 1) {
                this.copyArea(bufferedImage2, 0, 0, 1, this.height, i - 1, 0);
                this.copyArea(bufferedImage2, this.width - 1, 0, 1, this.height, 1, 0);
            }
        }
        final byte[] array2 = ((DataBufferByte)bufferedImage2.getRaster().getDataBuffer()).getData();
        if (array != null) {
            for (int k = 0; k < array2.length; k += 4) {
                boolean b4 = true;
                for (int l = 0; l < 3; ++l) {
                    if (((array2[k + l] < 0) ? (256 + array2[k + l]) : array2[k + l]) != array[l]) {
                        b4 = false;
                    }
                }
                if (b4) {
                    array2[k + 3] = 0;
                }
            }
        }
        final ByteBuffer allocateDirect = ByteBuffer.allocateDirect(array2.length);
        allocateDirect.order(ByteOrder.nativeOrder());
        allocateDirect.put(array2, 0, array2.length);
        allocateDirect.flip();
        graphics2D.dispose();
        return allocateDirect;
    }
    
    @Override
    public ByteBuffer getImageBufferData() {
        throw new RuntimeException("ImageIOImageData doesn't store it's image.");
    }
    
    private void copyArea(final BufferedImage bufferedImage, final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        ((Graphics2D)bufferedImage.getGraphics()).drawImage(bufferedImage.getSubimage(n, n2, n3, n4), n + n5, n2 + n6, null);
    }
    
    @Override
    public void configureEdging(final boolean edging) {
        this.edging = edging;
    }
    
    static {
        glAlphaColorModel = new ComponentColorModel(ColorSpace.getInstance(1000), new int[] { 8, 8, 8, 8 }, true, false, 3, 0);
        glColorModel = new ComponentColorModel(ColorSpace.getInstance(1000), new int[] { 8, 8, 8, 0 }, false, false, 1, 0);
    }
}
