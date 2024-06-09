package HORIZON-6-0-SKIDPROTECTION;

import java.awt.image.ImageObserver;
import java.awt.Image;
import java.awt.Graphics2D;
import java.nio.ByteBuffer;
import java.io.IOException;
import java.awt.image.BufferedImage;

public class BufferedImageUtil
{
    public static Texture HorizonCode_Horizon_È(final String resourceName, final BufferedImage resourceImage) throws IOException {
        final Texture tex = HorizonCode_Horizon_È(resourceName, resourceImage, 3553, 6408, 9729, 9729);
        return tex;
    }
    
    public static Texture HorizonCode_Horizon_È(final String resourceName, final BufferedImage resourceImage, final int filter) throws IOException {
        final Texture tex = HorizonCode_Horizon_È(resourceName, resourceImage, 3553, 6408, filter, filter);
        return tex;
    }
    
    public static Texture HorizonCode_Horizon_È(final String resourceName, final BufferedImage resourceimage, final int target, final int dstPixelFormat, final int minFilter, final int magFilter) throws IOException {
        final ImageIOImageData data = new ImageIOImageData();
        int srcPixelFormat = 0;
        final int textureID = InternalTextureLoader.Âµá€();
        final TextureImpl texture = new TextureImpl(resourceName, target, textureID);
        Renderer.HorizonCode_Horizon_È().Âµá€(3553);
        Renderer.HorizonCode_Horizon_È().Ý(target, textureID);
        texture.Âµá€(resourceimage.getWidth());
        texture.HorizonCode_Horizon_È(resourceimage.getHeight());
        if (resourceimage.getColorModel().hasAlpha()) {
            srcPixelFormat = 6408;
        }
        else {
            srcPixelFormat = 6407;
        }
        final ByteBuffer textureBuffer = data.HorizonCode_Horizon_È(resourceimage, false, false, null);
        texture.Â(data.Ø­áŒŠá());
        texture.Ø­áŒŠá(data.Âµá€());
        texture.HorizonCode_Horizon_È(data.HorizonCode_Horizon_È() == 32);
        if (target == 3553) {
            Renderer.HorizonCode_Horizon_È().Â(target, 10241, minFilter);
            Renderer.HorizonCode_Horizon_È().Â(target, 10240, magFilter);
            if (Renderer.HorizonCode_Horizon_È().áŒŠÆ()) {
                Renderer.HorizonCode_Horizon_È().Â(3553, 10242, 34627);
                Renderer.HorizonCode_Horizon_È().Â(3553, 10243, 34627);
            }
            else {
                Renderer.HorizonCode_Horizon_È().Â(3553, 10242, 10496);
                Renderer.HorizonCode_Horizon_È().Â(3553, 10243, 10496);
            }
        }
        Renderer.HorizonCode_Horizon_È().HorizonCode_Horizon_È(target, 0, dstPixelFormat, texture.áˆºÑ¢Õ(), texture.à(), 0, srcPixelFormat, 5121, textureBuffer);
        return texture;
    }
    
    private static void HorizonCode_Horizon_È(final BufferedImage image, final int x, final int y, final int width, final int height, final int dx, final int dy) {
        final Graphics2D g = (Graphics2D)image.getGraphics();
        g.drawImage(image.getSubimage(x, y, width, height), x + dx, y + dy, null);
    }
}
