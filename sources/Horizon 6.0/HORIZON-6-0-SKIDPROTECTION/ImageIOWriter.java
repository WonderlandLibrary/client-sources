package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;
import java.awt.image.WritableRaster;
import java.awt.image.ColorModel;
import java.awt.image.RenderedImage;
import javax.imageio.ImageIO;
import java.util.Hashtable;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.SampleModel;
import java.awt.image.Raster;
import java.awt.Point;
import java.awt.image.ComponentColorModel;
import java.awt.color.ColorSpace;
import java.awt.image.PixelInterleavedSampleModel;
import java.awt.image.DataBufferByte;
import java.nio.ByteBuffer;
import java.io.OutputStream;

public class ImageIOWriter implements ImageWriter
{
    @Override
    public void HorizonCode_Horizon_È(final Image image, final String format, final OutputStream output, final boolean hasAlpha) throws IOException {
        int len = 4 * image.ŒÏ() * image.Çªà¢();
        if (!hasAlpha) {
            len = 3 * image.ŒÏ() * image.Çªà¢();
        }
        final ByteBuffer out = ByteBuffer.allocate(len);
        for (int y = 0; y < image.Çªà¢(); ++y) {
            for (int x = 0; x < image.ŒÏ(); ++x) {
                final Color c = image.Â(x, y);
                out.put((byte)(c.£à * 255.0f));
                out.put((byte)(c.µà * 255.0f));
                out.put((byte)(c.ˆà * 255.0f));
                if (hasAlpha) {
                    out.put((byte)(c.¥Æ * 255.0f));
                }
            }
        }
        final DataBufferByte dataBuffer = new DataBufferByte(out.array(), len);
        PixelInterleavedSampleModel sampleModel;
        ColorModel cm;
        if (hasAlpha) {
            final int[] offsets = { 0, 1, 2, 3 };
            sampleModel = new PixelInterleavedSampleModel(0, image.ŒÏ(), image.Çªà¢(), 4, 4 * image.ŒÏ(), offsets);
            cm = new ComponentColorModel(ColorSpace.getInstance(1000), new int[] { 8, 8, 8, 8 }, true, false, 3, 0);
        }
        else {
            final int[] offsets = { 0, 1, 2 };
            sampleModel = new PixelInterleavedSampleModel(0, image.ŒÏ(), image.Çªà¢(), 3, 3 * image.ŒÏ(), offsets);
            cm = new ComponentColorModel(ColorSpace.getInstance(1000), new int[] { 8, 8, 8, 0 }, false, false, 1, 0);
        }
        final WritableRaster raster = Raster.createWritableRaster(sampleModel, dataBuffer, new Point(0, 0));
        final BufferedImage img = new BufferedImage(cm, raster, false, null);
        ImageIO.write(img, format, output);
    }
}
