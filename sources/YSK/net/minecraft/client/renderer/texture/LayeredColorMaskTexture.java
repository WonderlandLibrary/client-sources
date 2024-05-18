package net.minecraft.client.renderer.texture;

import java.util.*;
import net.minecraft.item.*;
import net.minecraft.client.resources.*;
import java.awt.*;
import java.awt.image.*;
import net.minecraft.util.*;
import java.io.*;
import net.minecraft.block.material.*;
import org.apache.logging.log4j.*;

public class LayeredColorMaskTexture extends AbstractTexture
{
    private final List<String> field_174949_h;
    private final ResourceLocation textureLocation;
    private static final Logger LOG;
    private static final String[] I;
    private final List<EnumDyeColor> field_174950_i;
    
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
            if (-1 == 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void loadTexture(final IResourceManager resourceManager) throws IOException {
        this.deleteGlTexture();
        BufferedImage bufferedImage2 = null;
        Label_0482: {
            try {
                final BufferedImage bufferedImage = TextureUtil.readBufferedImage(resourceManager.getResource(this.textureLocation).getInputStream());
                int type = bufferedImage.getType();
                if (type == 0) {
                    type = (0xA ^ 0xC);
                }
                bufferedImage2 = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), type);
                bufferedImage2.getGraphics().drawImage(bufferedImage, "".length(), "".length(), null);
                int length = "".length();
                "".length();
                if (2 >= 3) {
                    throw null;
                }
                Block_16: {
                    while (length < (0x25 ^ 0x34) && length < this.field_174949_h.size()) {
                        if (length >= this.field_174950_i.size()) {
                            break Block_16;
                        }
                        final String s = this.field_174949_h.get(length);
                        final MapColor mapColor = this.field_174950_i.get(length).getMapColor();
                        if (s != null) {
                            final BufferedImage bufferedImage3 = TextureUtil.readBufferedImage(resourceManager.getResource(new ResourceLocation(s)).getInputStream());
                            if (bufferedImage3.getWidth() == bufferedImage2.getWidth() && bufferedImage3.getHeight() == bufferedImage2.getHeight() && bufferedImage3.getType() == (0x5B ^ 0x5D)) {
                                int i = "".length();
                                "".length();
                                if (4 != 4) {
                                    throw null;
                                }
                                while (i < bufferedImage3.getHeight()) {
                                    int j = "".length();
                                    "".length();
                                    if (3 >= 4) {
                                        throw null;
                                    }
                                    while (j < bufferedImage3.getWidth()) {
                                        final int rgb = bufferedImage3.getRGB(j, i);
                                        if ((rgb & -(6930411 + 10017962 - 12741478 + 12570321)) != 0x0) {
                                            bufferedImage3.setRGB(j, i, ((rgb & 4499524 + 4399059 + 1132797 + 6680300) << (0x34 ^ 0x3C) & -(2757238 + 1977100 - 3869038 + 15911916)) | (MathHelper.func_180188_d(bufferedImage.getRGB(j, i), mapColor.colorValue) & 5799411 + 541407 - 5495822 + 15932219));
                                        }
                                        ++j;
                                    }
                                    ++i;
                                }
                                bufferedImage2.getGraphics().drawImage(bufferedImage3, "".length(), "".length(), null);
                            }
                        }
                        ++length;
                    }
                    break Label_0482;
                }
                "".length();
                if (1 < 1) {
                    throw null;
                }
            }
            catch (IOException ex) {
                LayeredColorMaskTexture.LOG.error(LayeredColorMaskTexture.I["".length()], (Throwable)ex);
                return;
            }
        }
        TextureUtil.uploadTextureImage(this.getGlTextureId(), bufferedImage2);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0006 \u0006\t0+h\u0007E8*.\u0017E8$6\u0016\u00171!o\u001a\b5\"*", "EOseT");
    }
    
    public LayeredColorMaskTexture(final ResourceLocation textureLocation, final List<String> field_174949_h, final List<EnumDyeColor> field_174950_i) {
        this.textureLocation = textureLocation;
        this.field_174949_h = field_174949_h;
        this.field_174950_i = field_174950_i;
    }
    
    static {
        I();
        LOG = LogManager.getLogger();
    }
}
