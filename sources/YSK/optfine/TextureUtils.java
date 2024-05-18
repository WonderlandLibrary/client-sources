package optfine;

import java.nio.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import org.lwjgl.opengl.*;
import java.awt.image.*;
import java.awt.*;
import javax.imageio.*;
import java.io.*;
import java.util.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.*;

public class TextureUtils
{
    public static final String texRedstoneLampOn;
    public static final String texLavaStill;
    public static final String texGrassTop;
    public static TextureAtlasSprite iconSnow;
    public static final String texLeavesSpuce;
    public static TextureAtlasSprite iconLavaStill;
    public static final String texEndStone;
    public static final String texGlowstone;
    public static final String texObsidian;
    public static final String texCoalOre;
    public static final String texSandstoneTop;
    public static final String texLogSpruce;
    public static final String texPortal;
    public static final String texMyceliumTop;
    public static final String texClay;
    public static final String texLogOakTop;
    public static final String texStoneslabSide;
    public static TextureAtlasSprite iconGrassSideOverlay;
    public static TextureAtlasSprite iconGrassTop;
    public static final String texCoarseDirt;
    public static final String texGrassSide;
    public static final String texLogJungleTop;
    public static final String texCactusSide;
    public static final String texGlass;
    public static TextureAtlasSprite iconFireLayer1;
    public static TextureAtlasSprite iconGlass;
    public static final String texRedstoneOre;
    public static final String texLapisOre;
    public static final String texMyceliumSide;
    public static TextureAtlasSprite iconGlassPaneTop;
    public static final String texLogBirch;
    public static final String texLogBirchTop;
    public static final String texGrassSideOverlay;
    public static final String texLogBigOakTop;
    public static final String texFireLayer1;
    public static final String texDiamondOre;
    public static TextureAtlasSprite iconMyceliumSide;
    public static final String texLogSpruceTop;
    public static final String texDirt;
    public static final String texLogJungle;
    public static TextureAtlasSprite iconGrassSide;
    public static final String texLeavesOak;
    public static final String texBedrock;
    public static final String texGlassPaneTop;
    public static final String texFarmlandWet;
    public static final String texGoldOre;
    public static final String texFarmlandDry;
    public static final String texLeavesSpruce;
    public static TextureAtlasSprite iconWaterStill;
    public static final String texSoulSand;
    public static TextureAtlasSprite iconMyceliumTop;
    public static final String texLeavesBirch;
    public static TextureAtlasSprite iconLavaFlow;
    private static final String[] I;
    private static IntBuffer staticBuffer;
    public static final String texSand;
    public static final String texStoneslabTop;
    public static final String texStone;
    public static final String texSandstoneBottom;
    public static final String texLogAcaciaTop;
    public static TextureAtlasSprite iconGrassSideSnowed;
    public static final String texGravel;
    public static TextureAtlasSprite iconWaterFlow;
    public static final String texWaterFlow;
    public static final String texLogOak;
    public static final String texLeavesBigOak;
    public static final String texLavaFlow;
    public static final String texSnow;
    public static final String texLeavesAcacia;
    public static final String SPRITE_LOCATION_PREFIX;
    public static final String texGrassSideSnowed;
    public static final String texIronOre;
    public static final String texLogAcacia;
    public static TextureAtlasSprite iconFireLayer0;
    public static final String texLogBigOak;
    public static final String texFireLayer0;
    public static final String texLeavesSpruceOpaque;
    public static final String texWaterStill;
    public static TextureAtlasSprite iconPortal;
    public static final String texLeavesJungle;
    public static final String texRedstoneLampOff;
    public static final String texNetherrack;
    
    public static ITextureObject getTexture(final ResourceLocation resourceLocation) {
        final ITextureObject texture = Config.getTextureManager().getTexture(resourceLocation);
        if (texture != null) {
            return texture;
        }
        if (!Config.hasResource(resourceLocation)) {
            return null;
        }
        final SimpleTexture simpleTexture = new SimpleTexture(resourceLocation);
        Config.getTextureManager().loadTexture(resourceLocation, simpleTexture);
        return simpleTexture;
    }
    
    public static ITextureObject getTexture(final String s) {
        return getTexture(new ResourceLocation(s));
    }
    
    public static void bindTexture(final int n) {
        GlStateManager.bindTexture(n);
    }
    
    public static boolean isPowerOfTwo(final int n) {
        if (MathHelper.roundUpToPowerOfTwo(n) == n) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public static int ceilPowerOfTwo(final int n) {
        int i = " ".length();
        "".length();
        if (3 < 1) {
            throw null;
        }
        while (i < n) {
            i *= "  ".length();
        }
        return i;
    }
    
    public static void update() {
        final TextureMap textureMapBlocks = getTextureMapBlocks();
        if (textureMapBlocks != null) {
            final String s = TextureUtils.I[0xCB ^ 0x8B];
            TextureUtils.iconGrassTop = textureMapBlocks.getSpriteSafe(String.valueOf(s) + TextureUtils.I[0x55 ^ 0x14]);
            TextureUtils.iconGrassSide = textureMapBlocks.getSpriteSafe(String.valueOf(s) + TextureUtils.I[0x4C ^ 0xE]);
            TextureUtils.iconGrassSideOverlay = textureMapBlocks.getSpriteSafe(String.valueOf(s) + TextureUtils.I[0xCD ^ 0x8E]);
            TextureUtils.iconSnow = textureMapBlocks.getSpriteSafe(String.valueOf(s) + TextureUtils.I[0x49 ^ 0xD]);
            TextureUtils.iconGrassSideSnowed = textureMapBlocks.getSpriteSafe(String.valueOf(s) + TextureUtils.I[0xE7 ^ 0xA2]);
            TextureUtils.iconMyceliumSide = textureMapBlocks.getSpriteSafe(String.valueOf(s) + TextureUtils.I[0x67 ^ 0x21]);
            TextureUtils.iconMyceliumTop = textureMapBlocks.getSpriteSafe(String.valueOf(s) + TextureUtils.I[0xF8 ^ 0xBF]);
            TextureUtils.iconWaterStill = textureMapBlocks.getSpriteSafe(String.valueOf(s) + TextureUtils.I[0xDD ^ 0x95]);
            TextureUtils.iconWaterFlow = textureMapBlocks.getSpriteSafe(String.valueOf(s) + TextureUtils.I[0x1 ^ 0x48]);
            TextureUtils.iconLavaStill = textureMapBlocks.getSpriteSafe(String.valueOf(s) + TextureUtils.I[0x7 ^ 0x4D]);
            TextureUtils.iconLavaFlow = textureMapBlocks.getSpriteSafe(String.valueOf(s) + TextureUtils.I[0x58 ^ 0x13]);
            TextureUtils.iconFireLayer0 = textureMapBlocks.getSpriteSafe(String.valueOf(s) + TextureUtils.I[0x79 ^ 0x35]);
            TextureUtils.iconFireLayer1 = textureMapBlocks.getSpriteSafe(String.valueOf(s) + TextureUtils.I[0xDE ^ 0x93]);
            TextureUtils.iconPortal = textureMapBlocks.getSpriteSafe(String.valueOf(s) + TextureUtils.I[0x27 ^ 0x69]);
            TextureUtils.iconGlass = textureMapBlocks.getSpriteSafe(String.valueOf(s) + TextureUtils.I[0x2 ^ 0x4D]);
            TextureUtils.iconGlassPaneTop = textureMapBlocks.getSpriteSafe(String.valueOf(s) + TextureUtils.I[0x15 ^ 0x45]);
        }
    }
    
    public static TextureMap getTextureMapBlocks() {
        return Minecraft.getMinecraft().getTextureMapBlocks();
    }
    
    public static void resourcesReloaded(final IResourceManager resourceManager) {
        if (getTextureMapBlocks() != null) {
            Config.dbg(TextureUtils.I[0xFD ^ 0xAE]);
            CustomSky.reset();
            TextureAnimations.reset();
            update();
            NaturalTextures.update();
            BetterGrass.update();
            BetterSnow.update();
            TextureAnimations.update();
            CustomColorizer.update();
            CustomSky.update();
            RandomMobs.resetTextures();
            Config.updateTexturePackClouds();
            Config.getTextureManager().tick();
        }
    }
    
    public static void applyAnisotropicLevel() {
        if (GLContext.getCapabilities().GL_EXT_texture_filter_anisotropic) {
            GL11.glTexParameterf(289 + 1295 - 486 + 2455, 8244 + 2537 - 10351 + 33616, Math.min(Config.getAnisotropicFilterLevel(), GL11.glGetFloat(33135 + 18413 - 23201 + 5700)));
        }
    }
    
    public static int getPowerOfTwo(final int n) {
        int i = " ".length();
        int length = "".length();
        "".length();
        if (3 == -1) {
            throw null;
        }
        while (i < n) {
            i *= "  ".length();
            ++length;
        }
        return length;
    }
    
    public static BufferedImage scaleToPowerOfTwo(final BufferedImage bufferedImage, final int n) {
        if (bufferedImage == null) {
            return bufferedImage;
        }
        final int width = bufferedImage.getWidth();
        final int height = bufferedImage.getHeight();
        final int roundUpToPowerOfTwo = MathHelper.roundUpToPowerOfTwo(Math.max(width, n));
        if (roundUpToPowerOfTwo == width) {
            return bufferedImage;
        }
        final int n2 = height * roundUpToPowerOfTwo / width;
        final BufferedImage bufferedImage2 = new BufferedImage(roundUpToPowerOfTwo, n2, "  ".length());
        final Graphics2D graphics = bufferedImage2.createGraphics();
        Object o = RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR;
        if (roundUpToPowerOfTwo % width != 0) {
            o = RenderingHints.VALUE_INTERPOLATION_BILINEAR;
        }
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, o);
        graphics.drawImage(bufferedImage, "".length(), "".length(), roundUpToPowerOfTwo, n2, null);
        return bufferedImage2;
    }
    
    public static Dimension getImageSize(final InputStream inputStream, final String s) {
        final Iterator<ImageReader> imageReadersBySuffix = ImageIO.getImageReadersBySuffix(s);
        while (imageReadersBySuffix.hasNext()) {
            final ImageReader imageReader = imageReadersBySuffix.next();
            Dimension dimension;
            try {
                imageReader.setInput(ImageIO.createImageInputStream(inputStream));
                dimension = new Dimension(imageReader.getWidth(imageReader.getMinIndex()), imageReader.getHeight(imageReader.getMinIndex()));
                "".length();
                if (2 != 2) {
                    throw null;
                }
            }
            catch (IOException ex) {
                imageReader.dispose();
                "".length();
                if (3 <= 2) {
                    throw null;
                }
                continue;
            }
            finally {
                imageReader.dispose();
            }
            imageReader.dispose();
            return dimension;
        }
        return null;
    }
    
    public static String fixResourcePath(String s, String string) {
        final String s2 = TextureUtils.I[0xF5 ^ 0xA0];
        if (s.startsWith(s2)) {
            s = s.substring(s2.length());
            return s;
        }
        if (s.startsWith(TextureUtils.I[0x35 ^ 0x63])) {
            s = s.substring("  ".length());
            if (!string.endsWith(TextureUtils.I[0xE3 ^ 0xB4])) {
                string = String.valueOf(string) + TextureUtils.I[0x1E ^ 0x46];
            }
            s = String.valueOf(string) + s;
            return s;
        }
        final String s3 = TextureUtils.I[0x50 ^ 0x9];
        if (s.startsWith(TextureUtils.I[0xED ^ 0xB7])) {
            s = s.substring("  ".length());
            s = String.valueOf(s3) + s;
            return s;
        }
        if (s.startsWith(TextureUtils.I[0x2E ^ 0x75])) {
            s = String.valueOf(s3) + s.substring(" ".length());
            return s;
        }
        return s;
    }
    
    public static void refreshBlockTextures() {
    }
    
    private static void I() {
        (I = new String[0xEB ^ 0xB6])["".length()] = I("\u0000\u0016,\u0004\u001c\u001f,/\u001b\u000b\u000f\u001b", "lsMry");
        TextureUtils.I[" ".length()] = I("\n\u0002\u001d\u0019\t\u001b\u0003\u0017#", "olyFz");
        TextureUtils.I["  ".length()] = I("\u0006\u00161\u000e;\u0003\u001e\t>8\u0001&\">)", "jyVQY");
        TextureUtils.I["   ".length()] = I("9\u00037\u0000", "JmXwv");
        TextureUtils.I[0xC1 ^ 0xC5] = I("\u0011\u0003\t6,\u0017\u0003\u0003", "xqfXs");
        TextureUtils.I[0xAF ^ 0xAA] = I(";\u0000\u001d$\u0011$\u0015\u0002)\"", "WakEN");
        TextureUtils.I[0x14 ^ 0x12] = I("+\u0005\u001e\u000e/!\b\u0007\u0018", "Gdhop");
        TextureUtils.I[0xA2 ^ 0xA5] = I("5\u0001.\u001a7\u0019\u0006-\u00150\u0019\u0006(\u00107", "FuAtR");
        TextureUtils.I[0x2F ^ 0x27] = I("\"?6-", "FVDYG");
        TextureUtils.I[0x6 ^ 0xF] = I("\u0005\u000b\u00006\u001c\t\u0016\u0004", "fdaZC");
        TextureUtils.I[0x43 ^ 0x49] = I("42\u0015\u0012\r)9\u0014>\u001642", "FWqay");
        TextureUtils.I[0x29 ^ 0x22] = I("7\u0019\u001e(\u0019(\u0011\u00169@8\u001c\u001f.\u0011)_", "ZppMz");
        TextureUtils.I[0xAA ^ 0xA6] = I("\u0014\u001f5\n\u0004\u0015\u0011", "vzQxk");
        TextureUtils.I[0x53 ^ 0x5E] = I("!$\u001e=\u0000, &\u0016\u0000=", "MKybo");
        TextureUtils.I[0x97 ^ 0x99] = I("%,\t?:/!7='$", "AEhRU");
        TextureUtils.I[0x75 ^ 0x7A] = I("\u0003/\u0014\u0011\u001b\t'\u001f\u00116:w", "eFftD");
        TextureUtils.I[0x71 ^ 0x61] = I("&*\u0011>\u0003#7\u0015\t", "JEvaa");
        TextureUtils.I[0x92 ^ 0x83] = I("(\u0003\u001f+-1\u0002\u001f\u0018\"", "DlxtG");
        TextureUtils.I[0x35 ^ 0x27] = I("\u0003*%5\t;6%(\u001f;2+6", "dFDFz");
        TextureUtils.I[0x73 ^ 0x60] = I("\u000f=(%*\u0010\u0007(0.\u00001(", "cXISO");
        TextureUtils.I[0x5D ^ 0x49] = I("+\u000b\f\u001258\b\r\u0000", "LgceF");
        TextureUtils.I[0xB1 ^ 0xA4] = I("\u00045\u0017\u0017\u0010\u0018(\u0005+\u0006", "hZpHc");
        TextureUtils.I[0x25 ^ 0x33] = I("\u0014\u001e-\u0019<,\u001f%\u000e*,\u0003:\u000f=\u001f\r5", "slLjO");
        TextureUtils.I[0x78 ^ 0x6F] = I("7;7<1=3<<\u001c\u000eb", "QREYn");
        TextureUtils.I[0x3 ^ 0x1B] = I("<\u001d\u0018\u0005\u0006 ", "Lrjqg");
        TextureUtils.I[0x38 ^ 0x21] = I("\u0002\u000140,\r\u000f0\u0006,1\u001a<\u001f", "nnSoM");
        TextureUtils.I[0xB0 ^ 0xAA] = I("5\t\u0006\u001c", "VegeH");
        TextureUtils.I[0x21 ^ 0x3A] = I("!\u0005*\u0005", "RdDak");
        TextureUtils.I[0x6A ^ 0x76] = I("\f\u0018\u001a\u001e\f\n\u001b\u0007", "cziwh");
        TextureUtils.I[0x79 ^ 0x64] = I("6\r\u00063\u00039\u0003\u0002\u0005\u0003", "Zbalb");
        TextureUtils.I[0x80 ^ 0x9E] = I("\t&1\f<\u0002", "nTPzY");
        TextureUtils.I[0x14 ^ 0xB] = I("\u00040\u000e>;<1\u0006)-", "cBoMH");
        TextureUtils.I[0x5B ^ 0x7B] = I("\u001f\n\u0013?\u001f7\u0018\u00133\u0001\u0004", "hkgZm");
        TextureUtils.I[0x67 ^ 0x46] = I("\u001d\u000f:\u001d<\u001a\u0001:\u001c\u0010\u001a\u0001$", "nnTyO");
        TextureUtils.I[0xE2 ^ 0xC0] = I("\u000f\u0005#92\n\r\u001b\t1\b", "cjDfP");
        TextureUtils.I[0x77 ^ 0x54] = I("\u001e\r \u0002\u0004\u00017+\u0001\u000f\u0015\u0004$", "rhAta");
        TextureUtils.I[0x51 ^ 0x75] = I("\t\u0016(,6\u000e\u0019>\u001e>\u001d\u000e", "owZAZ");
        TextureUtils.I[0xF ^ 0x2A] = I(": \t\u00000%\u001a\u001b\u0006'#&\r):&$\u0019\u00030", "VEhvU");
        TextureUtils.I[0x16 ^ 0x30] = I("\u001f;\u0007\u001d\"", "lOhsG");
        TextureUtils.I[0xA ^ 0x2D] = I("\u0005\u0014/=\u0014=\u0015'*\u0002=\u0015 !\u0010\u0007\u0002", "bfNNg");
        TextureUtils.I[0xE ^ 0x26] = I("\u001a\"(\u00127\n\u001c8\u000f&\u001c", "yCKfB");
        TextureUtils.I[0xEA ^ 0xC3] = I(">\n*2 3\u000e", "ReMmO");
        TextureUtils.I[0x31 ^ 0x1B] = I("\u001f7\u0006\u001e7\u001a*\u0002)\n\u00077\u0011", "sXaAU");
        TextureUtils.I[0xA1 ^ 0x8A] = I("\u001d! #+\u0015<)", "zNLGt");
        TextureUtils.I[0x5 ^ 0x29] = I("&\t\u001005\"\u0005\u001e\n*\"\u0014\u0016", "KpsUY");
        TextureUtils.I[0xB ^ 0x26] = I("\"\u00188&\u001e\"\u0016#.", "QwMJA");
        TextureUtils.I[0x4D ^ 0x63] = I("\u0002\f\n8.\u001f\u0007\u000b\u00146\u0011\u0004\u001e\u00145\u001e", "pinKZ");
        TextureUtils.I[0x57 ^ 0x78] = I("2\t+#\b5\u0006=\u0011\u00131\u001c", "ThYNd");
        TextureUtils.I[0x8E ^ 0xBE] = I("?\f>\u0006\u0000\"\u0007?*\u0018,\u0004**\u001b+\u000f", "MiZut");
        TextureUtils.I[0x32 ^ 0x3] = I("\u0005\u0010=%\u00006\u001e?)", "iqMLs");
        TextureUtils.I[0xBF ^ 0x8D] = I("\u001e\f4\u0013\"\u0019\u00024\u0012\u000e\u000f\u0002.\u0003>\u0000", "mmZwQ");
        TextureUtils.I[0xBE ^ 0x8D] = I("\u0015* 4\t\n\u001022\u001e\f,$", "yOABl");
        TextureUtils.I[0x2B ^ 0x1F] = I("\n\u001f3=\u0003\u000e\u0013=\u0007\u001b\b\u0016", "gfPXo");
        TextureUtils.I[0x2B ^ 0x1E] = I("\u001f>\u001d\t(\u0006?\u001d:',%\u0015&", "sQzVB");
        TextureUtils.I[0xAA ^ 0x9C] = I("\u00021\u0013>\u0003\u001d\u000b\u00018\u0014\u001b7\u0017", "nTrHf");
        TextureUtils.I[0x46 ^ 0x71] = I("0#5$\u00056\u00130?\u0004'", "SLTVv");
        TextureUtils.I[0x12 ^ 0x2A] = I("\u0018=!)+4:\"&,4=!7", "kINGN");
        TextureUtils.I[0xBD ^ 0x84] = I("\u0007,7&<\u0018\u0016912", "kIVPY");
        TextureUtils.I[0x9 ^ 0x33] = I("(\u0002*'7\u0010\u0004$$", "OpKTD");
        TextureUtils.I[0x5F ^ 0x64] = I("\u0019\t\u0006,\u000f\u0005\u001e\u0013'\u0001", "wlrDj");
        TextureUtils.I[0x9B ^ 0xA7] = I("$\u001b\u0013\u0002\u0015", "Cwrqf");
        TextureUtils.I[0x9 ^ 0x34] = I("\u0005\u001b84\u0014-\u001c >\u0011", "rzLQf");
        TextureUtils.I[0x51 ^ 0x6F] = I("\u001e.\t&\u0002\u00023\u001b\u001a\u0014-5\u0001\t", "rAnyq");
        TextureUtils.I[0x62 ^ 0x5D] = I("-\"\u00005\u00062\u0018\u0003*\u0004\u001e(\u0000(", "AGaCc");
        TextureUtils.I[0xF9 ^ 0xB9] = I("\u0003\u000e%0\u001b\u001c\u0006-!B\f\u000b$6\u0013\u001dH", "ngKUx");
        TextureUtils.I[0x4A ^ 0xB] = I("\u001d+\u0019\u00197%-\u0017\u001a", "zYxjD");
        TextureUtils.I[0x70 ^ 0x32] = I("\u001d7\u001b\u0018\u0002%6\u0013\u000f\u0014", "zEzkq");
        TextureUtils.I[0x46 ^ 0x5] = I("\u0017\u0006;\u0000%/\u00073\u00173/\u001b,\u0016$\u001c\u0015#", "ptZsV");
        TextureUtils.I[0xF5 ^ 0xB1] = I("%(-8", "VFBOU");
        TextureUtils.I[0x11 ^ 0x54] = I(".\u001f(\u0015=\u0016\u001e \u0002+\u0016\u001e'\t9,\t", "ImIfN");
        TextureUtils.I[0x2 ^ 0x44] = I(" ?7\t\"$393=$\"1", "MFTlN");
        TextureUtils.I[0x31 ^ 0x76] = I("\u00037\u0014,?\u0007;\u001a\u0016'\u0001>", "nNwIS");
        TextureUtils.I[0xD6 ^ 0x9E] = I("&,9?!\u000e>93?=", "QMMZS");
        TextureUtils.I[0x33 ^ 0x7A] = I("\u001f\t\u0004\u0012 7\u000e\u001c\u0018%", "hhpwR");
        TextureUtils.I[0x76 ^ 0x3C] = I("\u0019'\u0007\r\u0013\u00062\u0018\u0000 ", "uFqlL");
        TextureUtils.I[0x88 ^ 0xC3] = I("\u0001\u0013\u0006,\u0013\u000b\u001e\u001f:", "mrpML");
        TextureUtils.I[0x5D ^ 0x11] = I(" &&\u001c\f*.-\u001c!\u0019\u007f", "FOTyS");
        TextureUtils.I[0x6 ^ 0x4B] = I("(\n\u0011\u00173\"\u0002\u001a\u0017\u001e\u0011R", "Nccrl");
        TextureUtils.I[0xF3 ^ 0xBD] = I("*\u00019\u0011\n6", "ZnKek");
        TextureUtils.I[0x7A ^ 0x35] = I("\n\u00034\u0016 ", "moUeS");
        TextureUtils.I[0x1D ^ 0x4D] = I("7\u0005*\u00027\u000f\u0019*\u001f!\u000f\u001d$\u0001", "PiKqD");
        TextureUtils.I[0x5 ^ 0x54] = I("L7.3a\u00195,3'\u0006", "cZAQN");
        TextureUtils.I[0x7E ^ 0x2C] = I("j4\b\u0001A50\u0000\u0019\u0001(;\u000e\u0006", "EYgcn");
        TextureUtils.I[0x6D ^ 0x3E] = I("kP|w\u001d$\u001696+(\u00141w,4\t\"8\"a\u000e3/;4\b3$okP|", "AzVWO");
        TextureUtils.I[0xF8 ^ 0xAC] = I("?<&\u0013\r9\"7U?9/9\u001b\t<)\u0006\u001f\u0013$9 \u001f\u0018", "PLRzk");
        TextureUtils.I[0x4E ^ 0x1B] = I("3\u001b\u0004\u000e\u001c!G\u001a\u0002\u00067\u000b\u0005\n\u000e&G", "Rhwkh");
        TextureUtils.I[0xD3 ^ 0x85] = I("Gv", "iYYCY");
        TextureUtils.I[0xE9 ^ 0xBE] = I("j", "Evevs");
        TextureUtils.I[0xFC ^ 0xA4] = I("w", "XIjLf");
        TextureUtils.I[0xC ^ 0x55] = I("+\u0014#0\u0001%\u001f6#Z", "FwSQu");
        TextureUtils.I[0xD7 ^ 0x8D] = I("\u0010x", "nWRua");
        TextureUtils.I[0xD3 ^ 0x88] = I("Y", "vCcrB");
        TextureUtils.I[0xF4 ^ 0xA8] = I("", "znlXO");
    }
    
    public static BufferedImage fixTextureDimensions(final String s, final BufferedImage bufferedImage) {
        if (s.startsWith(TextureUtils.I[0x70 ^ 0x21]) || s.startsWith(TextureUtils.I[0xD8 ^ 0x8A])) {
            final int width = bufferedImage.getWidth();
            final int height = bufferedImage.getHeight();
            if (width == height * "  ".length()) {
                final BufferedImage bufferedImage2 = new BufferedImage(width, height * "  ".length(), "  ".length());
                final Graphics2D graphics = bufferedImage2.createGraphics();
                graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                graphics.drawImage(bufferedImage, "".length(), "".length(), width, height, null);
                return bufferedImage2;
            }
        }
        return bufferedImage;
    }
    
    public static void registerResourceListener() {
        final IResourceManager resourceManager = Config.getResourceManager();
        if (resourceManager instanceof IReloadableResourceManager) {
            ((IReloadableResourceManager)resourceManager).registerReloadListener(new IResourceManagerReloadListener() {
                @Override
                public void onResourceManagerReload(final IResourceManager resourceManager) {
                    TextureUtils.resourcesReloaded(resourceManager);
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
                        if (1 == 2) {
                            throw null;
                        }
                    }
                    return sb.toString();
                }
            });
        }
        Config.getTextureManager().loadTickableTexture(new ResourceLocation(TextureUtils.I[0xF7 ^ 0xA3]), new ITickableTextureObject() {
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
                    if (1 <= 0) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public void loadTexture(final IResourceManager resourceManager) throws IOException {
            }
            
            @Override
            public void tick() {
                TextureAnimations.updateCustomAnimations();
            }
            
            @Override
            public void restoreLastBlurMipmap() {
            }
            
            @Override
            public void setBlurMipmap(final boolean b, final boolean b2) {
            }
            
            @Override
            public int getGlTextureId() {
                return "".length();
            }
        });
    }
    
    static {
        I();
        texLeavesBirch = TextureUtils.I["".length()];
        texEndStone = TextureUtils.I[" ".length()];
        texLogBigOakTop = TextureUtils.I["  ".length()];
        texSnow = TextureUtils.I["   ".length()];
        texIronOre = TextureUtils.I[0x71 ^ 0x75];
        texLavaStill = TextureUtils.I[0xA7 ^ 0xA2];
        texLavaFlow = TextureUtils.I[0x7A ^ 0x7C];
        texStoneslabSide = TextureUtils.I[0x47 ^ 0x40];
        texDirt = TextureUtils.I[0x6A ^ 0x62];
        texCoalOre = TextureUtils.I[0x6 ^ 0xF];
        texRedstoneOre = TextureUtils.I[0x4 ^ 0xE];
        SPRITE_LOCATION_PREFIX = TextureUtils.I[0xAA ^ 0xA1];
        texBedrock = TextureUtils.I[0xD ^ 0x1];
        texLogOakTop = TextureUtils.I[0x4E ^ 0x43];
        texDiamondOre = TextureUtils.I[0x29 ^ 0x27];
        texFireLayer1 = TextureUtils.I[0x6D ^ 0x62];
        texLogBirch = TextureUtils.I[0x92 ^ 0x82];
        texLogJungle = TextureUtils.I[0x49 ^ 0x58];
        texGlassPaneTop = TextureUtils.I[0x7D ^ 0x6F];
        texLeavesAcacia = TextureUtils.I[0x63 ^ 0x70];
        texGlowstone = TextureUtils.I[0x79 ^ 0x6D];
        texLogSpruce = TextureUtils.I[0x91 ^ 0x84];
        texGrassSideOverlay = TextureUtils.I[0x50 ^ 0x46];
        texFireLayer0 = TextureUtils.I[0x47 ^ 0x50];
        texPortal = TextureUtils.I[0x90 ^ 0x88];
        texLogAcaciaTop = TextureUtils.I[0xA6 ^ 0xBF];
        texClay = TextureUtils.I[0x8D ^ 0x97];
        texSand = TextureUtils.I[0x4B ^ 0x50];
        texObsidian = TextureUtils.I[0xDC ^ 0xC0];
        texLogAcacia = TextureUtils.I[0xB4 ^ 0xA9];
        texGravel = TextureUtils.I[0xD8 ^ 0xC6];
        texGrassSide = TextureUtils.I[0x7E ^ 0x61];
        texWaterStill = TextureUtils.I[0x71 ^ 0x51];
        texSandstoneTop = TextureUtils.I[0x10 ^ 0x31];
        texLogBigOak = TextureUtils.I[0x5 ^ 0x27];
        texLeavesJungle = TextureUtils.I[0x32 ^ 0x11];
        texFarmlandDry = TextureUtils.I[0x39 ^ 0x1D];
        texLeavesSpruceOpaque = TextureUtils.I[0x15 ^ 0x30];
        texStone = TextureUtils.I[0x2B ^ 0xD];
        texGrassSideSnowed = TextureUtils.I[0xBA ^ 0x9D];
        texCactusSide = TextureUtils.I[0x15 ^ 0x3D];
        texLogOak = TextureUtils.I[0xAA ^ 0x83];
        texLogBirchTop = TextureUtils.I[0x41 ^ 0x6B];
        texGoldOre = TextureUtils.I[0xA8 ^ 0x83];
        texMyceliumSide = TextureUtils.I[0x32 ^ 0x1E];
        texSoulSand = TextureUtils.I[0x2 ^ 0x2F];
        texRedstoneLampOn = TextureUtils.I[0x64 ^ 0x4A];
        texFarmlandWet = TextureUtils.I[0x3A ^ 0x15];
        texRedstoneLampOff = TextureUtils.I[0x72 ^ 0x42];
        texLapisOre = TextureUtils.I[0x46 ^ 0x77];
        texSandstoneBottom = TextureUtils.I[0x3A ^ 0x8];
        texLeavesSpruce = TextureUtils.I[0x31 ^ 0x2];
        texMyceliumTop = TextureUtils.I[0x96 ^ 0xA2];
        texLogJungleTop = TextureUtils.I[0x24 ^ 0x11];
        texLeavesSpuce = TextureUtils.I[0x47 ^ 0x71];
        texCoarseDirt = TextureUtils.I[0x79 ^ 0x4E];
        texStoneslabTop = TextureUtils.I[0x7E ^ 0x46];
        texLeavesOak = TextureUtils.I[0x89 ^ 0xB0];
        texGrassTop = TextureUtils.I[0x27 ^ 0x1D];
        texNetherrack = TextureUtils.I[0xA9 ^ 0x92];
        texGlass = TextureUtils.I[0x3D ^ 0x1];
        texWaterFlow = TextureUtils.I[0x17 ^ 0x2A];
        texLogSpruceTop = TextureUtils.I[0x58 ^ 0x66];
        texLeavesBigOak = TextureUtils.I[0x21 ^ 0x1E];
        TextureUtils.staticBuffer = GLAllocation.createDirectIntBuffer(160 + 179 - 137 + 54);
    }
    
    public static int twoToPower(final int n) {
        int length = " ".length();
        int i = "".length();
        "".length();
        if (4 == -1) {
            throw null;
        }
        while (i < n) {
            length *= "  ".length();
            ++i;
        }
        return length;
    }
    
    public static String getBasePath(final String s) {
        final int lastIndex = s.lastIndexOf(0x8C ^ 0xA3);
        String substring;
        if (lastIndex < 0) {
            substring = TextureUtils.I[0x3B ^ 0x67];
            "".length();
            if (1 >= 2) {
                throw null;
            }
        }
        else {
            substring = s.substring("".length(), lastIndex);
        }
        return substring;
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
            if (-1 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
}
