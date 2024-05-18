package net.minecraft.client.renderer.texture;

import com.google.common.collect.*;
import net.minecraft.client.resources.*;
import net.minecraft.util.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import org.apache.logging.log4j.*;

public class LayeredTexture extends AbstractTexture
{
    private static final String[] I;
    private static final Logger logger;
    public final List<String> layeredTextureNames;
    
    public LayeredTexture(final String... array) {
        this.layeredTextureNames = (List<String>)Lists.newArrayList((Object[])array);
    }
    
    @Override
    public void loadTexture(final IResourceManager resourceManager) throws IOException {
        this.deleteGlTexture();
        BufferedImage bufferedImage = null;
        try {
            final Iterator<String> iterator = this.layeredTextureNames.iterator();
            "".length();
            if (1 >= 3) {
                throw null;
            }
            while (iterator.hasNext()) {
                final String s = iterator.next();
                if (s != null) {
                    final BufferedImage bufferedImage2 = TextureUtil.readBufferedImage(resourceManager.getResource(new ResourceLocation(s)).getInputStream());
                    if (bufferedImage == null) {
                        bufferedImage = new BufferedImage(bufferedImage2.getWidth(), bufferedImage2.getHeight(), "  ".length());
                    }
                    bufferedImage.getGraphics().drawImage(bufferedImage2, "".length(), "".length(), null);
                }
            }
            "".length();
            if (2 >= 3) {
                throw null;
            }
        }
        catch (IOException ex) {
            LayeredTexture.logger.error(LayeredTexture.I["".length()], (Throwable)ex);
            return;
        }
        TextureUtil.uploadTextureImage(this.getGlTextureId(), bufferedImage);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0017\u001c!\u0004\u000b:T H\u0003;\u00120H\u00035\n1\u001a\n0S=\u0005\u000e3\u0016", "TsTho");
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
            if (2 != 2) {
                throw null;
            }
        }
        return sb.toString();
    }
}
