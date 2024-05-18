package net.minecraft.client.renderer.texture;

import net.minecraft.util.*;
import net.minecraft.client.resources.data.*;
import net.minecraft.client.resources.*;
import java.awt.image.*;
import java.io.*;
import org.apache.logging.log4j.*;

public class SimpleTexture extends AbstractTexture
{
    protected final ResourceLocation textureLocation;
    private static final Logger logger;
    private static final String[] I;
    
    @Override
    public void loadTexture(final IResourceManager resourceManager) throws IOException {
        this.deleteGlTexture();
        InputStream inputStream = null;
        try {
            final IResource resource = resourceManager.getResource(this.textureLocation);
            inputStream = resource.getInputStream();
            final BufferedImage bufferedImage = TextureUtil.readBufferedImage(inputStream);
            int n = "".length();
            int n2 = "".length();
            if (resource.hasMetadata()) {
                try {
                    final TextureMetadataSection textureMetadataSection = resource.getMetadata(SimpleTexture.I["".length()]);
                    if (textureMetadataSection != null) {
                        n = (textureMetadataSection.getTextureBlur() ? 1 : 0);
                        n2 = (textureMetadataSection.getTextureClamp() ? 1 : 0);
                        "".length();
                        if (4 < 4) {
                            throw null;
                        }
                    }
                }
                catch (RuntimeException ex) {
                    SimpleTexture.logger.warn(SimpleTexture.I[" ".length()] + this.textureLocation, (Throwable)ex);
                }
            }
            TextureUtil.uploadTextureImageAllocate(this.getGlTextureId(), bufferedImage, n != 0, n2 != 0);
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        if (inputStream != null) {
            inputStream.close();
        }
    }
    
    static {
        I();
        logger = LogManager.getLogger();
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (4 <= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public SimpleTexture(final ResourceLocation textureLocation) {
        this.textureLocation = textureLocation;
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("<\u0002,;-:\u0002", "HgTOX");
        SimpleTexture.I[" ".length()] = I("\u0017\u0002#-.5C8$*5\n$&k<\u0006> /0\u0017+a$7Yj", "QcJAK");
    }
}
