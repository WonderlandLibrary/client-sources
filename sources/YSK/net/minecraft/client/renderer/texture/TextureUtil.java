package net.minecraft.client.renderer.texture;

import java.nio.*;
import net.minecraft.client.*;
import org.lwjgl.opengl.*;
import org.lwjgl.*;
import javax.imageio.*;
import java.awt.image.*;
import org.apache.commons.io.*;
import java.io.*;
import net.minecraft.client.resources.*;
import net.minecraft.util.*;
import org.apache.logging.log4j.*;
import net.minecraft.client.renderer.*;
import optfine.*;

public class TextureUtil
{
    public static final int[] missingTextureData;
    private static final Logger logger;
    public static final DynamicTexture missingTexture;
    private static final int[] mipmapBuffer;
    private static final String __OBFID;
    private static final String[] I;
    private static final IntBuffer dataBuffer;
    
    private static void setTextureBlurred(final boolean b) {
        setTextureBlurMipmap(b, "".length() != 0);
    }
    
    static void bindTexture(final int n) {
        GlStateManager.bindTexture(n);
    }
    
    private static void copyToBufferPos(final int[] array, final int n, final int n2) {
        int[] updateAnaglyph = array;
        if (Minecraft.getMinecraft().gameSettings.anaglyph) {
            updateAnaglyph = updateAnaglyph(array);
        }
        TextureUtil.dataBuffer.clear();
        TextureUtil.dataBuffer.put(updateAnaglyph, n, n2);
        TextureUtil.dataBuffer.position("".length()).limit(n2);
    }
    
    private static void setTextureClamped(final boolean b) {
        if (b) {
            GL11.glTexParameteri(201 + 2148 - 1734 + 2938, 8045 + 613 - 1139 + 2723, 9005 + 18907 - 6685 + 11844);
            GL11.glTexParameteri(1029 + 2318 - 1391 + 1597, 2865 + 2977 + 3112 + 1289, 3616 + 12496 + 12197 + 4762);
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else {
            GL11.glTexParameteri(1983 + 824 - 255 + 1001, 3732 + 4980 - 8550 + 10080, 958 + 5036 - 5512 + 10015);
            GL11.glTexParameteri(612 + 451 - 872 + 3362, 1143 + 3900 + 4753 + 447, 1920 + 5008 - 3962 + 7531);
        }
    }
    
    public static int glGenTextures() {
        return GlStateManager.generateTexture();
    }
    
    public static void allocateTextureImpl(final int n, final int n2, final int n3, final int n4) {
        deleteTexture(n);
        bindTexture(n);
        if (n2 >= 0) {
            GL11.glTexParameteri(2855 + 2761 - 3421 + 1358, 5971 + 2440 - 6451 + 31125, n2);
            GL11.glTexParameterf(82 + 2004 - 933 + 2400, 473 + 9511 + 2798 + 20300, 0.0f);
            GL11.glTexParameterf(538 + 886 + 417 + 1712, 28741 + 15159 - 43786 + 32969, (float)n2);
            GL11.glTexParameterf(1895 + 105 + 830 + 723, 466 + 24782 - 5051 + 13852, 0.0f);
        }
        int i = "".length();
        "".length();
        if (2 <= -1) {
            throw null;
        }
        while (i <= n2) {
            GL11.glTexImage2D(17 + 2277 - 2202 + 3461, i, 3822 + 98 - 1224 + 3712, n3 >> i, n4 >> i, "".length(), 31892 + 10195 - 12776 + 3682, 1421 + 23870 - 9902 + 18250, (IntBuffer)null);
            ++i;
        }
    }
    
    public static void saveGlTexture(final String s, final int n, final int n2, final int n3, final int n4) {
        bindTexture(n);
        GL11.glPixelStorei(2623 + 1315 - 2591 + 1986, " ".length());
        GL11.glPixelStorei(474 + 1516 + 185 + 1142, " ".length());
        int i = "".length();
        "".length();
        if (4 <= 2) {
            throw null;
        }
        while (i <= n2) {
            final File file = new File(String.valueOf(s) + TextureUtil.I[" ".length()] + i + TextureUtil.I["  ".length()]);
            final int n5 = n3 >> i;
            final int n6 = n4 >> i;
            final int n7 = n5 * n6;
            final IntBuffer intBuffer = BufferUtils.createIntBuffer(n7);
            final int[] array = new int[n7];
            GL11.glGetTexImage(2567 + 1547 - 3767 + 3206, i, 31531 + 21913 - 29704 + 9253, 26626 + 22460 - 24237 + 8790, intBuffer);
            intBuffer.get(array);
            final BufferedImage bufferedImage = new BufferedImage(n5, n6, "  ".length());
            bufferedImage.setRGB("".length(), "".length(), n5, n6, array, "".length(), n5);
            try {
                ImageIO.write(bufferedImage, TextureUtil.I["   ".length()], file);
                final Logger logger = TextureUtil.logger;
                final String s2 = TextureUtil.I[0x19 ^ 0x1D];
                final Object[] array2 = new Object[" ".length()];
                array2["".length()] = file.getAbsolutePath();
                logger.debug(s2, array2);
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
            catch (Exception ex) {
                TextureUtil.logger.debug(TextureUtil.I[0x43 ^ 0x46], (Throwable)ex);
            }
            ++i;
        }
    }
    
    public static void allocateTexture(final int n, final int n2, final int n3) {
        allocateTextureImpl(n, "".length(), n2, n3);
    }
    
    public static BufferedImage readBufferedImage(final InputStream inputStream) throws IOException {
        BufferedImage read;
        try {
            read = ImageIO.read(inputStream);
            "".length();
            if (2 < 2) {
                throw null;
            }
        }
        finally {
            IOUtils.closeQuietly(inputStream);
        }
        IOUtils.closeQuietly(inputStream);
        return read;
    }
    
    private static void uploadTextureImageSubImpl(final BufferedImage bufferedImage, final int n, final int n2, final boolean textureBlurred, final boolean textureClamped) {
        final int width = bufferedImage.getWidth();
        final int height = bufferedImage.getHeight();
        final int n3 = (3816479 + 2565833 - 3904523 + 1716515) / width;
        final int[] array = new int[n3 * width];
        setTextureBlurred(textureBlurred);
        setTextureClamped(textureClamped);
        int i = "".length();
        "".length();
        if (4 <= -1) {
            throw null;
        }
        while (i < width * height) {
            final int n4 = i / width;
            final int min = Math.min(n3, height - n4);
            final int n5 = width * min;
            bufferedImage.getRGB("".length(), n4, width, min, array, "".length(), width);
            copyToBuffer(array, n5);
            GL11.glTexSubImage2D(806 + 334 - 831 + 3244, "".length(), n, n2 + n4, width, min, 25479 + 32005 - 26663 + 2172, 12639 + 7280 - 9904 + 23624, TextureUtil.dataBuffer);
            i += width * n3;
        }
    }
    
    public static int anaglyphColor(final int n) {
        final int n2 = n >> (0x6E ^ 0x76) & 59 + 245 - 208 + 159;
        final int n3 = n >> (0xB2 ^ 0xA2) & 199 + 29 + 8 + 19;
        final int n4 = n >> (0x9F ^ 0x97) & 161 + 3 - 79 + 170;
        final int n5 = n & 105 + 22 - 5 + 133;
        return n2 << (0x8D ^ 0x95) | (n3 * (0x34 ^ 0x2A) + n4 * (0xD ^ 0x36) + n5 * (0x80 ^ 0x8B)) / (0x18 ^ 0x7C) << (0x4D ^ 0x5D) | (n3 * (0xD ^ 0x13) + n4 * (0xF6 ^ 0xB0)) / (0x1B ^ 0x7F) << (0xCE ^ 0xC6) | (n3 * (0x4F ^ 0x51) + n5 * (0x87 ^ 0xC1)) / (0x64 ^ 0x0);
    }
    
    public static void deleteTexture(final int n) {
        GlStateManager.deleteTexture(n);
    }
    
    private static void uploadTextureSub(final int n, final int[] array, final int n2, final int n3, final int n4, final int n5, final boolean b, final boolean textureClamped, final boolean b2) {
        final int n6 = (3770035 + 3352166 - 5776086 + 2848189) / n2;
        setTextureBlurMipmap(b, b2);
        setTextureClamped(textureClamped);
        int i = "".length();
        "".length();
        if (4 != 4) {
            throw null;
        }
        while (i < n2 * n3) {
            final int n7 = i / n2;
            final int min = Math.min(n6, n3 - n7);
            copyToBufferPos(array, i, n2 * min);
            GL11.glTexSubImage2D(2599 + 1574 - 641 + 21, n, n4, n5 + n7, n2, min, 15488 + 26255 - 22546 + 13796, 13218 + 11927 - 18608 + 27102, TextureUtil.dataBuffer);
            i += n2 * min;
        }
    }
    
    public static int uploadTextureImage(final int n, final BufferedImage bufferedImage) {
        return uploadTextureImageAllocate(n, bufferedImage, "".length() != 0, "".length() != 0);
    }
    
    private static int blendColorComponent(final int n, final int n2, final int n3, final int n4, final int n5) {
        return (int)((float)Math.pow(((float)Math.pow((n >> n5 & 30 + 1 + 73 + 151) / 255.0f, 2.2) + (float)Math.pow((n2 >> n5 & 172 + 21 - 151 + 213) / 255.0f, 2.2) + (float)Math.pow((n3 >> n5 & 53 + 58 + 19 + 125) / 255.0f, 2.2) + (float)Math.pow((n4 >> n5 & 38 + 140 - 52 + 129) / 255.0f, 2.2)) * 0.25, 0.45454545454545453) * 255.0);
    }
    
    public static void processPixelValues(final int[] array, final int n, final int n2) {
        final int[] array2 = new int[n];
        final int n3 = n2 / "  ".length();
        int i = "".length();
        "".length();
        if (0 == 3) {
            throw null;
        }
        while (i < n3) {
            System.arraycopy(array, i * n, array2, "".length(), n);
            System.arraycopy(array, (n2 - " ".length() - i) * n, array, i * n, n);
            System.arraycopy(array2, "".length(), array, (n2 - " ".length() - i) * n, n);
            ++i;
        }
    }
    
    public static int[] readImageData(final IResourceManager resourceManager, final ResourceLocation resourceLocation) throws IOException {
        final BufferedImage bufferedImage = readBufferedImage(resourceManager.getResource(resourceLocation).getInputStream());
        final int width = bufferedImage.getWidth();
        final int height = bufferedImage.getHeight();
        final int[] array = new int[width * height];
        bufferedImage.getRGB("".length(), "".length(), width, height, array, "".length(), width);
        return array;
    }
    
    public static int uploadTextureImageSub(final int n, final BufferedImage bufferedImage, final int n2, final int n3, final boolean b, final boolean b2) {
        bindTexture(n);
        uploadTextureImageSubImpl(bufferedImage, n2, n3, b, b2);
        return n;
    }
    
    public static int uploadTextureImageAllocate(final int n, final BufferedImage bufferedImage, final boolean b, final boolean b2) {
        allocateTexture(n, bufferedImage.getWidth(), bufferedImage.getHeight());
        return uploadTextureImageSub(n, bufferedImage, "".length(), "".length(), b, b2);
    }
    
    public static void uploadTextureMipmap(final int[][] array, final int n, final int n2, final int n3, final int n4, final boolean b, final boolean b2) {
        int i = "".length();
        "".length();
        if (3 == 4) {
            throw null;
        }
        while (i < array.length) {
            final int[] array2 = array[i];
            final int n5 = i;
            final int[] array3 = array2;
            final int n6 = n >> i;
            final int n7 = n2 >> i;
            final int n8 = n3 >> i;
            final int n9 = n4 >> i;
            int n10;
            if (array.length > " ".length()) {
                n10 = " ".length();
                "".length();
                if (2 <= 1) {
                    throw null;
                }
            }
            else {
                n10 = "".length();
            }
            uploadTextureSub(n5, array3, n6, n7, n8, n9, b, b2, n10 != 0);
            ++i;
        }
    }
    
    private static void setTextureBlurMipmap(final boolean b, final boolean b2) {
        if (b) {
            final int n = 1619 + 1119 - 1879 + 2694;
            final int n2 = 5124 + 2356 - 2297 + 5058;
            int n3;
            if (b2) {
                n3 = 9247 + 3000 - 2736 + 476;
                "".length();
                if (0 >= 2) {
                    throw null;
                }
            }
            else {
                n3 = 7971 + 429 - 6552 + 7881;
            }
            GL11.glTexParameteri(n, n2, n3);
            GL11.glTexParameteri(2511 + 2944 - 3563 + 1661, 5752 + 7098 - 2830 + 220, 7428 + 749 - 362 + 1914);
            "".length();
            if (2 == 1) {
                throw null;
            }
        }
        else {
            final int mipmapType = Config.getMipmapType();
            final int n4 = 79 + 633 + 2680 + 161;
            final int n5 = 9992 + 8977 - 9616 + 888;
            int n6;
            if (b2) {
                n6 = mipmapType;
                "".length();
                if (3 == 2) {
                    throw null;
                }
            }
            else {
                n6 = 4445 + 7876 - 7318 + 4725;
            }
            GL11.glTexParameteri(n4, n5, n6);
            GL11.glTexParameteri(2630 + 224 - 2240 + 2939, 4432 + 356 + 5135 + 317, 1453 + 4920 - 5485 + 8840);
        }
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
            if (0 >= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static int[] updateAnaglyph(final int[] array) {
        final int[] array2 = new int[array.length];
        int i = "".length();
        "".length();
        if (-1 >= 3) {
            throw null;
        }
        while (i < array.length) {
            array2[i] = anaglyphColor(array[i]);
            ++i;
        }
        return array2;
    }
    
    static {
        I();
        __OBFID = TextureUtil.I["".length()];
        logger = LogManager.getLogger();
        dataBuffer = GLAllocation.createDirectIntBuffer(170876 + 1606966 - 1067712 + 3484174);
        missingTexture = new DynamicTexture(0x1F ^ 0xF, 0x45 ^ 0x55);
        missingTextureData = TextureUtil.missingTexture.getTextureData();
        final int[] array = new int[0x5E ^ 0x56];
        array["".length()] = -(157500 + 395397 - 349093 + 320236);
        array[" ".length()] = -(53741 + 28712 + 280673 + 160914);
        array["  ".length()] = -(333530 + 432198 - 528352 + 286664);
        array["   ".length()] = -(11274 + 462633 - 331612 + 381745);
        array[0x50 ^ 0x54] = -(404775 + 298342 - 544039 + 364962);
        array[0x7C ^ 0x79] = -(131549 + 181469 + 77233 + 133789);
        array[0x1 ^ 0x7] = -(381427 + 45329 - 68658 + 165942);
        array[0x8 ^ 0xF] = -(20809 + 370156 - 370852 + 503927);
        final int[] array2 = array;
        final int[] array3 = new int[0xB6 ^ 0xBE];
        array3["".length()] = -(5645226 + 1131317 - 3659287 + 13659960);
        array3[" ".length()] = -(7791084 + 7298634 - 14055498 + 15742996);
        array3["  ".length()] = -(3578426 + 14010181 - 17525031 + 16713640);
        array3["   ".length()] = -(5606141 + 13058643 - 7677999 + 5790431);
        array3[0x53 ^ 0x57] = -(4216081 + 14882308 - 7232132 + 4910959);
        array3[0x4B ^ 0x4E] = -(4658233 + 4283537 - 771296 + 8606742);
        array3[0xC4 ^ 0xC2] = -(7369766 + 3564356 + 5804683 + 38411);
        array3[0x3C ^ 0x3B] = -(5367566 + 14404704 - 11554120 + 8559066);
        final int[] array4 = array3;
        final int length = array2.length;
        int i = "".length();
        "".length();
        if (1 >= 4) {
            throw null;
        }
        while (i < (0x98 ^ 0x88)) {
            int[] array5;
            if (i < length) {
                array5 = array2;
                "".length();
                if (0 >= 3) {
                    throw null;
                }
            }
            else {
                array5 = array4;
            }
            System.arraycopy(array5, "".length(), TextureUtil.missingTextureData, (0x11 ^ 0x1) * i, length);
            int[] array6;
            if (i < length) {
                array6 = array4;
                "".length();
                if (2 <= 0) {
                    throw null;
                }
            }
            else {
                array6 = array2;
            }
            System.arraycopy(array6, "".length(), TextureUtil.missingTextureData, (0x53 ^ 0x43) * i + length, length);
            ++i;
        }
        TextureUtil.missingTexture.updateDynamicTexture();
        mipmapBuffer = new int[0x69 ^ 0x6D];
    }
    
    private static void copyToBuffer(final int[] array, final int n) {
        copyToBufferPos(array, "".length(), n);
    }
    
    private static void I() {
        (I = new String[0x93 ^ 0x95])["".length()] = I("\u0017-3gtdQ]grc", "TalWD");
        TextureUtil.I[" ".length()] = I("*", "ucSHO");
        TextureUtil.I["  ".length()] = I("V3:\u001d", "xCTzB");
        TextureUtil.I["   ".length()] = I("\u001e\f\u0015", "nbrUY");
        TextureUtil.I[0x6E ^ 0x6A] = I("! =,&\u0010=)c$\n?m7;^x6>", "dXMCT");
        TextureUtil.I[0x1C ^ 0x19] = I("\u0013\u000450\u0019#J =U1\u0018=&\u0010|J", "FjTRu");
    }
    
    public static void uploadTexture(final int n, final int[] array, final int n2, final int n3) {
        bindTexture(n);
        uploadTextureSub("".length(), array, n2, n3, "".length(), "".length(), "".length() != 0, "".length() != 0, "".length() != 0);
    }
    
    public static int[][] generateMipmapData(final int n, final int n2, final int[][] array) {
        final int[][] array2 = new int[n + " ".length()][];
        array2["".length()] = array["".length()];
        if (n > 0) {
            int n3 = "".length();
            int i = "".length();
            "".length();
            if (1 >= 2) {
                throw null;
            }
            while (i < array.length) {
                if (array["".length()][i] >> (0x12 ^ 0xA) == 0) {
                    n3 = " ".length();
                    "".length();
                    if (-1 == 2) {
                        throw null;
                    }
                    break;
                }
                else {
                    ++i;
                }
            }
            int j = " ".length();
            "".length();
            if (-1 != -1) {
                throw null;
            }
            while (j <= n) {
                if (array[j] != null) {
                    array2[j] = array[j];
                    "".length();
                    if (!true) {
                        throw null;
                    }
                }
                else {
                    final int[] array3 = array2[j - " ".length()];
                    final int[] array4 = new int[array3.length >> "  ".length()];
                    final int n4 = n2 >> j;
                    final int n5 = array4.length / n4;
                    final int n6 = n4 << " ".length();
                    int k = "".length();
                    "".length();
                    if (3 <= 2) {
                        throw null;
                    }
                    while (k < n4) {
                        int l = "".length();
                        "".length();
                        if (false) {
                            throw null;
                        }
                        while (l < n5) {
                            final int n7 = "  ".length() * (k + l * n6);
                            array4[k + l * n4] = blendColors(array3[n7 + "".length()], array3[n7 + " ".length()], array3[n7 + "".length() + n6], array3[n7 + " ".length() + n6], n3 != 0);
                            ++l;
                        }
                        ++k;
                    }
                    array2[j] = array4;
                }
                ++j;
            }
        }
        return array2;
    }
    
    private static int blendColors(final int n, final int n2, final int n3, final int n4, final boolean b) {
        return Mipmaps.alphaBlend(n, n2, n3, n4);
    }
}
