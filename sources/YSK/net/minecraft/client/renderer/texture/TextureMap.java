package net.minecraft.client.renderer.texture;

import java.awt.image.*;
import org.apache.logging.log4j.*;
import optfine.*;
import net.minecraft.client.*;
import net.minecraft.client.resources.data.*;
import java.util.concurrent.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.resources.*;
import net.minecraft.crash.*;
import java.util.*;
import java.io.*;
import java.awt.*;
import com.google.common.collect.*;

public class TextureMap extends AbstractTexture implements ITickableTextureObject
{
    private static final Logger logger;
    private final List listAnimatedSprites;
    public static final ResourceLocation locationBlocksTexture;
    private final Map mapUploadedSprites;
    private static final String __OBFID;
    private final String basePath;
    private final IIconCreator iconCreator;
    private int mipmapLevels;
    private final TextureAtlasSprite missingImage;
    private final Map mapRegisteredSprites;
    public static final ResourceLocation LOCATION_MISSING_TEXTURE;
    private static final String[] I;
    
    @Override
    public void loadTexture(final IResourceManager resourceManager) throws IOException {
        if (this.iconCreator != null) {
            this.loadSprites(resourceManager, this.iconCreator);
        }
    }
    
    private static void I() {
        (I = new String[0xEE ^ 0xC3])["".length()] = I(".\u001f+J}]cEJxU", "mStzM");
        TextureMap.I[" ".length()] = I("*9<5\")7!)", "GPOFK");
        TextureMap.I["  ".length()] = I("#\u0011,\r?%\u0011'V+#\u00185\ne5\u0018;\u001a!$Z$\u0017-", "WtTyJ");
        TextureMap.I["   ".length()] = I("\u0017#5;\f\u0014-('", "zJFHe");
        TextureMap.I[0x7E ^ 0x7A] = I("\u001b*%:7&c92 3/&mv", "VCUWV");
        TextureMap.I[0x7A ^ 0x7F] = I("\u000b\u0000($-2\u0010<$14\u0010~p", "FuDPD");
        TextureMap.I[0xD ^ 0xB] = I("\u001b-\u0017\u001f=,n\u0018\u001c6h>\u0019\u0004=:n\u0019\u0015xztV", "HNvsX");
        TextureMap.I[0x45 ^ 0x42] = I("@R", "lrOcF");
        TextureMap.I[0x9A ^ 0x92] = I("ob_e", "OOaEe");
        TextureMap.I[0x20 ^ 0x29] = I("\u001b,\f#\u001d\u001d,", "oItWh");
        TextureMap.I[0x69 ^ 0x63] = I("\u0007#\n4=7m\u001f9q>\"\n2q75\u001f$0r \u0002&=7;\u000e:\"~m\u00189$ .\u000e{%75\u001f##7m\u0002%q<\"\u001fv!=:\u000e$q=+K\"&=", "RMkVQ");
        TextureMap.I[0x30 ^ 0x3B] = I("\u0001?%\u0012/1q0\u001fc8>%\u0014c984\u001c&\"4(P8)q\"\u0002,9kd\u000b>", "TQDpC");
        TextureMap.I[0x70 ^ 0x7C] = I("\u0005\u0005\u000b\u0001\u0002\u0010\u0002\r\u0002", "dkblc");
        TextureMap.I[0x67 ^ 0x6A] = I("\u001d\u0018%7=-V0:q8\u00176&4h\u001b!!0,\u001704q.\u0004+8q", "HvDUQ");
        TextureMap.I[0x57 ^ 0x59] = I("\r\u001038\u0000x\u000e3%\u00141\r=v\u0013=\u001b.#\u0015=Oz#\t9\u000163G,\fz:\b9\u0007z", "XcZVg");
        TextureMap.I[0x84 ^ 0x8B] = I("Df", "hFRDx");
        TextureMap.I[0x15 ^ 0x5] = I("\u001e(/%\u00168(w*\u001ej:>%\u000bj>>+\u0006j6*)\u00187m;8\u000e#9$q\u000e#=w=\u0006<(;q\u00058\":q\u00187m#>C10", "JMWQc");
        TextureMap.I[0x32 ^ 0x23] = I("#$Sx\u0005*6\u0019(\b6>I5\b(5\f.\u00044y\u000f*\u000e5y\u0012%A,6I#\u001cty\u000b=\u00029,\u001a=A7?I5\b60\u0004-\fx)\u0006/\u0004*y\u0006>A,.\u0006bA#$", "XYiXa");
        TextureMap.I[0x46 ^ 0x54] = I("4\u0001*\t\u0014\u001c\u001f=E\u0000\u001c\u00017\u0004\u001d", "uqZem");
        TextureMap.I[0x72 ^ 0x61] = I("\u0011;\u0011*\u0019'k\u0001&\u0004,,C.\u00042&\u00023\u001d'/", "BKcCm");
        TextureMap.I[0x9E ^ 0x8A] = I("1\u001a \r\u0004\u0007J<\u0005\u001d\u0007", "bjRdp");
        TextureMap.I[0x15 ^ 0x0] = I("\u0011\u0011'\u000e>'A&\u000e0'", "BaUgJ");
        TextureMap.I[0xBD ^ 0xAB] = I(":'\u001f!>\fw\u000b:+\u00042\u001e", "iWmHJ");
        TextureMap.I[0x3A ^ 0x2D] = I("\u0006\u0001\u0014\t\t;H\b\u0001\u001e.\u0004\u0017", "Khddh");
        TextureMap.I[0xB3 ^ 0xAB] = I("/\u0016\u000f\n0\t\u0000PK?\u0011\u001c\u0011\u0016d\u0017\u0019G\n0\u0000\u0005\u0019", "ldjkD");
        TextureMap.I[0x3B ^ 0x22] = I("$\u0019\u0002\u0002-\u001f\u0004\u0005\u0011n\u0003\b\u0013\u0002;\u0005\bK\u0017:\u001b\f\u0018", "wmkvN");
        TextureMap.I[0x3B ^ 0x21] = I("!73-2\u00077k;\"\u001c<,y4\u0001;?:/\u00106k-(\u00127?1\"\u0007", "uRKYG");
        TextureMap.I[0x18 ^ 0x3] = I("4&8\u0007\u0001U\"5\u0012\u001a", "uRTfr");
        TextureMap.I[0x5D ^ 0x41] = I("&4 #&\u0010", "uDRJR");
        TextureMap.I[0x79 ^ 0x64] = I("6\u0007.\u0010= \u001e,\u0000\u001b +9\u0005", "EfXui");
        TextureMap.I[0xD9 ^ 0xC7] = I("#\u000760", "WuCUH");
        TextureMap.I[0xA7 ^ 0xB8] = I("x", "WKPYE");
        TextureMap.I[0x38 ^ 0x18] = I("\u0019", "FHCkL");
        TextureMap.I[0x1D ^ 0x3C] = I("\\&7\u0011", "rVYvT");
        TextureMap.I[0xB6 ^ 0x94] = I("<(\u001b%\u0007!", "QAkHf");
        TextureMap.I[0x1B ^ 0x38] = I("K\u001d'#", "emIDm");
        TextureMap.I[0xC ^ 0x28] = I("J\u0011ur8J\u0011", "obZWK");
        TextureMap.I[0x7D ^ 0x58] = I("G\u001e6>", "inXYS");
        TextureMap.I[0x4E ^ 0x68] = I("B\u0011c%\u000b\u0017\u000f-8\u0011HG?fG\u0003G?", "gbLHb");
        TextureMap.I[0x6E ^ 0x49] = I("k\t\u0004\u0000", "EyjgX");
        TextureMap.I[0x8D ^ 0xA5] = I(".\u001b+\"&\u000b\u001b&c1\u0003\u001a&,&B\u0016-c<\u0017\u0018$b", "btHCR");
        TextureMap.I[0x60 ^ 0x49] = I(":$56\u001f4/ %D", "WGEWk");
        TextureMap.I[0x14 ^ 0x3E] = I("\u000b\u001a\u0019\u000f,\r\u0004\bI", "djmfJ");
        TextureMap.I[0xB4 ^ 0x9F] = I(" \u0018(&\u001a\u0016H)&\u0014\u0016Rz", "shZOn");
        TextureMap.I[0x93 ^ 0xBF] = I("3= ", "CSGuV");
    }
    
    public int getCountRegisteredSprites() {
        return this.mapRegisteredSprites.size();
    }
    
    private int[] getMissingImageData(final int n) {
        final BufferedImage bufferedImage = new BufferedImage(0xBC ^ 0xAC, 0x98 ^ 0x88, "  ".length());
        bufferedImage.setRGB("".length(), "".length(), 0x6 ^ 0x16, 0xA0 ^ 0xB0, TextureUtil.missingTextureData, "".length(), 0xBA ^ 0xAA);
        final BufferedImage scaleToPowerOfTwo = TextureUtils.scaleToPowerOfTwo(bufferedImage, n);
        final int[] array = new int[n * n];
        scaleToPowerOfTwo.getRGB("".length(), "".length(), n, n, array, "".length(), n);
        return array;
    }
    
    static {
        I();
        __OBFID = TextureMap.I["".length()];
        logger = LogManager.getLogger();
        LOCATION_MISSING_TEXTURE = new ResourceLocation(TextureMap.I[" ".length()]);
        locationBlocksTexture = new ResourceLocation(TextureMap.I["  ".length()]);
    }
    
    private boolean isAbsoluteLocation(final ResourceLocation resourceLocation) {
        return this.isAbsoluteLocationPath(resourceLocation.getResourcePath());
    }
    
    public TextureMap(final String s) {
        this(s, null);
    }
    
    public boolean setTextureEntry(final String s, final TextureAtlasSprite textureAtlasSprite) {
        if (!this.mapRegisteredSprites.containsKey(s)) {
            this.mapRegisteredSprites.put(s, textureAtlasSprite);
            if (textureAtlasSprite.getIndexInMap() < 0) {
                textureAtlasSprite.setIndexInMap(this.mapRegisteredSprites.size());
            }
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public TextureAtlasSprite registerSprite(final ResourceLocation resourceLocation) {
        if (resourceLocation == null) {
            throw new IllegalArgumentException(TextureMap.I[0x68 ^ 0x40]);
        }
        TextureAtlasSprite atlasSprite = this.mapRegisteredSprites.get(resourceLocation.toString());
        if (atlasSprite == null && Reflector.ModLoader_getCustomAnimationLogic.exists()) {
            final ReflectorMethod modLoader_getCustomAnimationLogic = Reflector.ModLoader_getCustomAnimationLogic;
            final Object[] array = new Object[" ".length()];
            array["".length()] = resourceLocation;
            atlasSprite = (TextureAtlasSprite)Reflector.call(modLoader_getCustomAnimationLogic, array);
        }
        if (atlasSprite == null) {
            atlasSprite = TextureAtlasSprite.makeAtlasSprite(resourceLocation);
            this.mapRegisteredSprites.put(resourceLocation.toString(), atlasSprite);
            if (atlasSprite instanceof TextureAtlasSprite && atlasSprite.getIndexInMap() < 0) {
                atlasSprite.setIndexInMap(this.mapRegisteredSprites.size());
            }
        }
        return atlasSprite;
    }
    
    public void loadTextureAtlas(final IResourceManager resourceManager) {
        Config.dbg(TextureMap.I[0xAC ^ 0xA9] + Config.isMultiTexture());
        if (Config.isMultiTexture()) {
            final Iterator<TextureAtlasSprite> iterator = this.mapUploadedSprites.values().iterator();
            "".length();
            if (-1 >= 1) {
                throw null;
            }
            while (iterator.hasNext()) {
                iterator.next().deleteSpriteTexture();
            }
        }
        ConnectedTextures.updateIcons(this);
        final int glMaximumTextureSize = Minecraft.getGLMaximumTextureSize();
        final Stitcher stitcher = new Stitcher(glMaximumTextureSize, glMaximumTextureSize, " ".length() != 0, "".length(), this.mipmapLevels);
        this.mapUploadedSprites.clear();
        this.listAnimatedSprites.clear();
        int n = 861009773 + 1434760755 - 451415322 + 303128441;
        final ReflectorMethod forgeHooksClient_onTextureStitchedPre = Reflector.ForgeHooksClient_onTextureStitchedPre;
        final Object[] array = new Object[" ".length()];
        array["".length()] = this;
        Reflector.callVoid(forgeHooksClient_onTextureStitchedPre, array);
        final int minSpriteSize = this.getMinSpriteSize();
        int n2 = " ".length() << this.mipmapLevels;
        final Iterator<Map.Entry<K, TextureAtlasSprite>> iterator2 = this.mapRegisteredSprites.entrySet().iterator();
        "".length();
        if (4 <= -1) {
            throw null;
        }
        while (iterator2.hasNext()) {
            final TextureAtlasSprite textureAtlasSprite = iterator2.next().getValue();
            final ResourceLocation resourceLocation = new ResourceLocation(textureAtlasSprite.getIconName());
            final ResourceLocation completeResourceLocation = this.completeResourceLocation(resourceLocation, "".length());
            if (!textureAtlasSprite.hasCustomLoader(resourceManager, resourceLocation)) {
                try {
                    final IResource resource = resourceManager.getResource(completeResourceLocation);
                    final BufferedImage[] array2 = new BufferedImage[" ".length() + this.mipmapLevels];
                    array2["".length()] = TextureUtil.readBufferedImage(resource.getInputStream());
                    if (this.mipmapLevels > 0 && array2 != null) {
                        final int width = array2["".length()].getWidth();
                        array2["".length()] = TextureUtils.scaleToPowerOfTwo(array2["".length()], minSpriteSize);
                        final int width2 = array2["".length()].getWidth();
                        if (!TextureUtils.isPowerOfTwo(width)) {
                            Config.log(TextureMap.I[0x26 ^ 0x20] + textureAtlasSprite.getIconName() + TextureMap.I[0x62 ^ 0x65] + width + TextureMap.I[0x21 ^ 0x29] + width2);
                        }
                    }
                    final TextureMetadataSection textureMetadataSection = resource.getMetadata(TextureMap.I[0xAC ^ 0xA5]);
                    if (textureMetadataSection != null) {
                        final List<Integer> listMipmaps = textureMetadataSection.getListMipmaps();
                        if (!listMipmaps.isEmpty()) {
                            final int width3 = array2["".length()].getWidth();
                            final int height = array2["".length()].getHeight();
                            if (MathHelper.roundUpToPowerOfTwo(width3) != width3 || MathHelper.roundUpToPowerOfTwo(height) != height) {
                                throw new RuntimeException(TextureMap.I[0x8D ^ 0x87]);
                            }
                        }
                        final Iterator<Integer> iterator3 = listMipmaps.iterator();
                        "".length();
                        if (4 != 4) {
                            throw null;
                        }
                        while (iterator3.hasNext()) {
                            final int intValue = iterator3.next();
                            if (intValue > 0 && intValue < array2.length - " ".length() && array2[intValue] == null) {
                                final ResourceLocation completeResourceLocation2 = this.completeResourceLocation(resourceLocation, intValue);
                                try {
                                    array2[intValue] = TextureUtil.readBufferedImage(resourceManager.getResource(completeResourceLocation2).getInputStream());
                                    "".length();
                                    if (false) {
                                        throw null;
                                    }
                                    continue;
                                }
                                catch (IOException ex) {
                                    final Logger logger = TextureMap.logger;
                                    final String s = TextureMap.I[0x9 ^ 0x2];
                                    final Object[] array3 = new Object["   ".length()];
                                    array3["".length()] = intValue;
                                    array3[" ".length()] = completeResourceLocation2;
                                    array3["  ".length()] = ex;
                                    logger.error(s, array3);
                                }
                            }
                        }
                    }
                    textureAtlasSprite.loadSprite(array2, resource.getMetadata(TextureMap.I[0x8B ^ 0x87]));
                    "".length();
                    if (2 < 0) {
                        throw null;
                    }
                }
                catch (RuntimeException ex2) {
                    TextureMap.logger.error(TextureMap.I[0x4C ^ 0x41] + completeResourceLocation, (Throwable)ex2);
                    "".length();
                    if (2 <= 1) {
                        throw null;
                    }
                    continue;
                }
                catch (IOException ex3) {
                    TextureMap.logger.error(TextureMap.I[0x10 ^ 0x1E] + completeResourceLocation + TextureMap.I[0x10 ^ 0x1F] + ex3.getClass().getName());
                    "".length();
                    if (3 == 4) {
                        throw null;
                    }
                    continue;
                }
                n = Math.min(n, Math.min(textureAtlasSprite.getIconWidth(), textureAtlasSprite.getIconHeight()));
                final int min = Math.min(Integer.lowestOneBit(textureAtlasSprite.getIconWidth()), Integer.lowestOneBit(textureAtlasSprite.getIconHeight()));
                if (min < n2) {
                    final Logger logger2 = TextureMap.logger;
                    final String s2 = TextureMap.I[0x40 ^ 0x50];
                    final Object[] array4 = new Object[0x9A ^ 0x9F];
                    array4["".length()] = completeResourceLocation;
                    array4[" ".length()] = textureAtlasSprite.getIconWidth();
                    array4["  ".length()] = textureAtlasSprite.getIconHeight();
                    array4["   ".length()] = MathHelper.calculateLogBaseTwo(n2);
                    array4[0xB ^ 0xF] = MathHelper.calculateLogBaseTwo(min);
                    logger2.warn(s2, array4);
                    n2 = min;
                }
                stitcher.addSprite(textureAtlasSprite);
                "".length();
                if (3 < 0) {
                    throw null;
                }
                continue;
            }
            else {
                if (textureAtlasSprite.load(resourceManager, resourceLocation)) {
                    continue;
                }
                n = Math.min(n, Math.min(textureAtlasSprite.getIconWidth(), textureAtlasSprite.getIconHeight()));
                stitcher.addSprite(textureAtlasSprite);
            }
        }
        final int min2 = Math.min(n, n2);
        int mipmapLevels = MathHelper.calculateLogBaseTwo(min2);
        if (mipmapLevels < 0) {
            mipmapLevels = "".length();
        }
        if (mipmapLevels < this.mipmapLevels) {
            final Logger logger3 = TextureMap.logger;
            final String s3 = TextureMap.I[0x33 ^ 0x22];
            final Object[] array5 = new Object[0x18 ^ 0x1C];
            array5["".length()] = this.basePath;
            array5[" ".length()] = this.mipmapLevels;
            array5["  ".length()] = mipmapLevels;
            array5["   ".length()] = min2;
            logger3.info(s3, array5);
            this.mipmapLevels = mipmapLevels;
        }
        final Iterator<TextureAtlasSprite> iterator4 = this.mapRegisteredSprites.values().iterator();
        "".length();
        if (3 <= 2) {
            throw null;
        }
        while (iterator4.hasNext()) {
            final TextureAtlasSprite textureAtlasSprite2 = iterator4.next();
            try {
                textureAtlasSprite2.generateMipmaps(this.mipmapLevels);
                "".length();
                if (1 == 4) {
                    throw null;
                }
                continue;
            }
            catch (Throwable t) {
                final CrashReport crashReport = CrashReport.makeCrashReport(t, TextureMap.I[0x4D ^ 0x5F]);
                final CrashReportCategory category = crashReport.makeCategory(TextureMap.I[0x94 ^ 0x87]);
                category.addCrashSectionCallable(TextureMap.I[0x71 ^ 0x65], new Callable(this, textureAtlasSprite2) {
                    private final TextureAtlasSprite val$textureatlassprite2;
                    final TextureMap this$0;
                    private static final String __OBFID;
                    private static final String[] I;
                    
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
                    
                    @Override
                    public Object call() throws Exception {
                        return this.call();
                    }
                    
                    static {
                        I();
                        __OBFID = TextureMap$1.I["".length()];
                    }
                    
                    @Override
                    public String call() throws Exception {
                        return this.val$textureatlassprite2.getIconName();
                    }
                    
                    private static void I() {
                        (I = new String[" ".length()])["".length()] = I("(\u000e\u000eiI[r`iLR", "kBQYy");
                    }
                });
                category.addCrashSectionCallable(TextureMap.I[0x30 ^ 0x25], new Callable(this, textureAtlasSprite2) {
                    final TextureMap this$0;
                    private static final String[] I;
                    private final TextureAtlasSprite val$textureatlassprite2;
                    private static final String __OBFID;
                    
                    private static void I() {
                        (I = new String["  ".length()])["".length()] = I("u3M", "UKmQi");
                        TextureMap$2.I[" ".length()] = I("\u0002\u0004\u001bEFqxuE@q", "AHDuv");
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
                            if (2 <= -1) {
                                throw null;
                            }
                        }
                        return sb.toString();
                    }
                    
                    @Override
                    public Object call() throws Exception {
                        return this.call();
                    }
                    
                    @Override
                    public String call() throws Exception {
                        return String.valueOf(this.val$textureatlassprite2.getIconWidth()) + TextureMap$2.I["".length()] + this.val$textureatlassprite2.getIconHeight();
                    }
                    
                    static {
                        I();
                        __OBFID = TextureMap$2.I[" ".length()];
                    }
                });
                category.addCrashSectionCallable(TextureMap.I[0x99 ^ 0x8F], new Callable(this, textureAtlasSprite2) {
                    private static final String __OBFID;
                    private final TextureAtlasSprite val$textureatlassprite2;
                    final TextureMap this$0;
                    private static final String[] I;
                    
                    @Override
                    public Object call() throws Exception {
                        return this.call();
                    }
                    
                    static {
                        I();
                        __OBFID = TextureMap$3.I[" ".length()];
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
                            if (3 < 0) {
                                throw null;
                            }
                        }
                        return sb.toString();
                    }
                    
                    @Override
                    public String call() throws Exception {
                        return String.valueOf(this.val$textureatlassprite2.getFrameCount()) + TextureMap$3.I["".length()];
                    }
                    
                    private static void I() {
                        (I = new String["  ".length()])["".length()] = I("s\u0011(\b\n6\u0004", "SwZig");
                        TextureMap$3.I[" ".length()] = I("\f\u000b\f[\\\u007fwb[Z~", "OGSkl");
                    }
                });
                category.addCrashSection(TextureMap.I[0x1E ^ 0x9], this.mipmapLevels);
                throw new ReportedException(crashReport);
            }
        }
        this.missingImage.generateMipmaps(this.mipmapLevels);
        stitcher.addSprite(this.missingImage);
        try {
            stitcher.doStitch();
            "".length();
            if (1 <= -1) {
                throw null;
            }
        }
        catch (StitcherException ex4) {
            throw ex4;
        }
        final Logger logger4 = TextureMap.logger;
        final String s4 = TextureMap.I[0x35 ^ 0x2D];
        final Object[] array6 = new Object["   ".length()];
        array6["".length()] = stitcher.getCurrentWidth();
        array6[" ".length()] = stitcher.getCurrentHeight();
        array6["  ".length()] = this.basePath;
        logger4.info(s4, array6);
        TextureUtil.allocateTextureImpl(this.getGlTextureId(), this.mipmapLevels, stitcher.getCurrentWidth(), stitcher.getCurrentHeight());
        final HashMap hashMap = Maps.newHashMap(this.mapRegisteredSprites);
        final Iterator iterator5 = stitcher.getStichSlots().iterator();
        "".length();
        if (true != true) {
            throw null;
        }
        while (iterator5.hasNext()) {
            final TextureAtlasSprite textureAtlasSprite3 = iterator5.next();
            final String iconName = textureAtlasSprite3.getIconName();
            hashMap.remove(iconName);
            this.mapUploadedSprites.put(iconName, textureAtlasSprite3);
            try {
                TextureUtil.uploadTextureMipmap(textureAtlasSprite3.getFrameTextureData("".length()), textureAtlasSprite3.getIconWidth(), textureAtlasSprite3.getIconHeight(), textureAtlasSprite3.getOriginX(), textureAtlasSprite3.getOriginY(), "".length() != 0, "".length() != 0);
                "".length();
                if (-1 >= 1) {
                    throw null;
                }
            }
            catch (Throwable t2) {
                final CrashReport crashReport2 = CrashReport.makeCrashReport(t2, TextureMap.I[0x84 ^ 0x9D]);
                final CrashReportCategory category2 = crashReport2.makeCategory(TextureMap.I[0x42 ^ 0x58]);
                category2.addCrashSection(TextureMap.I[0x9B ^ 0x80], this.basePath);
                category2.addCrashSection(TextureMap.I[0x74 ^ 0x68], textureAtlasSprite3);
                throw new ReportedException(crashReport2);
            }
            if (textureAtlasSprite3.hasAnimationMetadata()) {
                this.listAnimatedSprites.add(textureAtlasSprite3);
            }
        }
        final Iterator<TextureAtlasSprite> iterator6 = hashMap.values().iterator();
        "".length();
        if (false) {
            throw null;
        }
        while (iterator6.hasNext()) {
            iterator6.next().copyFrom(this.missingImage);
        }
        if (Config.isMultiTexture()) {
            final int currentWidth = stitcher.getCurrentWidth();
            final int currentHeight = stitcher.getCurrentHeight();
            final Iterator iterator7 = stitcher.getStichSlots().iterator();
            "".length();
            if (4 != 4) {
                throw null;
            }
            while (iterator7.hasNext()) {
                final TextureAtlasSprite textureAtlasSprite4 = iterator7.next();
                textureAtlasSprite4.sheetWidth = currentWidth;
                textureAtlasSprite4.sheetHeight = currentHeight;
                textureAtlasSprite4.mipmapLevels = this.mipmapLevels;
                final TextureAtlasSprite spriteSingle = textureAtlasSprite4.spriteSingle;
                if (spriteSingle != null) {
                    spriteSingle.sheetWidth = currentWidth;
                    spriteSingle.sheetHeight = currentHeight;
                    spriteSingle.mipmapLevels = this.mipmapLevels;
                    textureAtlasSprite4.bindSpriteTexture();
                    TextureUtil.uploadTextureMipmap(spriteSingle.getFrameTextureData("".length()), spriteSingle.getIconWidth(), spriteSingle.getIconHeight(), spriteSingle.getOriginX(), spriteSingle.getOriginY(), "".length() != 0, " ".length() != 0);
                }
            }
            Config.getMinecraft().getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        }
        final ReflectorMethod forgeHooksClient_onTextureStitchedPost = Reflector.ForgeHooksClient_onTextureStitchedPost;
        final Object[] array7 = new Object[" ".length()];
        array7["".length()] = this;
        Reflector.callVoid(forgeHooksClient_onTextureStitchedPost, array7);
        if (Config.equals(System.getProperty(TextureMap.I[0xB0 ^ 0xAD]), TextureMap.I[0x17 ^ 0x9])) {
            TextureUtil.saveGlTexture(this.basePath.replaceAll(TextureMap.I[0xF ^ 0x10], TextureMap.I[0xAC ^ 0x8C]), this.getGlTextureId(), this.mipmapLevels, stitcher.getCurrentWidth(), stitcher.getCurrentHeight());
        }
    }
    
    @Override
    public void tick() {
        this.updateAnimations();
    }
    
    private int detectMinimumSpriteSize(final Map map, final IResourceManager resourceManager, final int n) {
        final HashMap<Object, Integer> hashMap = new HashMap<Object, Integer>();
        final Iterator<Map.Entry<K, TextureAtlasSprite>> iterator = map.entrySet().iterator();
        "".length();
        if (1 <= 0) {
            throw null;
        }
        while (iterator.hasNext()) {
            final TextureAtlasSprite textureAtlasSprite = iterator.next().getValue();
            final ResourceLocation resourceLocation = new ResourceLocation(textureAtlasSprite.getIconName());
            final ResourceLocation completeResourceLocation = this.completeResourceLocation(resourceLocation, "".length());
            if (!textureAtlasSprite.hasCustomLoader(resourceManager, resourceLocation)) {
                try {
                    final IResource resource = resourceManager.getResource(completeResourceLocation);
                    if (resource == null) {
                        continue;
                    }
                    final InputStream inputStream = resource.getInputStream();
                    if (inputStream == null) {
                        continue;
                    }
                    final Dimension imageSize = TextureUtils.getImageSize(inputStream, TextureMap.I[0xE9 ^ 0xC5]);
                    if (imageSize == null) {
                        continue;
                    }
                    final int roundUpToPowerOfTwo = MathHelper.roundUpToPowerOfTwo(imageSize.width);
                    if (!hashMap.containsKey(roundUpToPowerOfTwo)) {
                        hashMap.put(roundUpToPowerOfTwo, " ".length());
                        "".length();
                        if (3 <= 1) {
                            throw null;
                        }
                        continue;
                    }
                    else {
                        hashMap.put(roundUpToPowerOfTwo, hashMap.get(roundUpToPowerOfTwo) + " ".length());
                        "".length();
                        if (false) {
                            throw null;
                        }
                        continue;
                    }
                }
                catch (Exception ex) {}
            }
        }
        int length = "".length();
        final TreeSet set = new TreeSet<Integer>(hashMap.keySet());
        final Iterator<Integer> iterator2 = set.iterator();
        "".length();
        if (2 < 0) {
            throw null;
        }
        while (iterator2.hasNext()) {
            length += hashMap.get((int)iterator2.next());
        }
        int n2 = 0xAC ^ 0xBC;
        int length2 = "".length();
        final int n3 = length * n / (0x3B ^ 0x5F);
        final Iterator<Integer> iterator3 = (Iterator<Integer>)set.iterator();
        "".length();
        if (-1 >= 4) {
            throw null;
        }
        while (iterator3.hasNext()) {
            final int intValue = iterator3.next();
            length2 += hashMap.get(intValue);
            if (intValue > n2) {
                n2 = intValue;
            }
            if (length2 > n3) {
                return n2;
            }
        }
        return n2;
    }
    
    private boolean isTerrainAnimationActive(final TextureAtlasSprite textureAtlasSprite) {
        boolean b;
        if (textureAtlasSprite != TextureUtils.iconWaterStill && textureAtlasSprite != TextureUtils.iconWaterFlow) {
            if (textureAtlasSprite != TextureUtils.iconLavaStill && textureAtlasSprite != TextureUtils.iconLavaFlow) {
                if (textureAtlasSprite != TextureUtils.iconFireLayer0 && textureAtlasSprite != TextureUtils.iconFireLayer1) {
                    if (textureAtlasSprite == TextureUtils.iconPortal) {
                        b = Config.isAnimatedPortal();
                        "".length();
                        if (-1 >= 1) {
                            throw null;
                        }
                    }
                    else {
                        b = Config.isAnimatedTerrain();
                        "".length();
                        if (1 < 1) {
                            throw null;
                        }
                    }
                }
                else {
                    b = Config.isAnimatedFire();
                    "".length();
                    if (2 == 1) {
                        throw null;
                    }
                }
            }
            else {
                b = Config.isAnimatedLava();
                "".length();
                if (3 >= 4) {
                    throw null;
                }
            }
        }
        else {
            b = Config.isAnimatedWater();
        }
        return b;
    }
    
    public TextureAtlasSprite getTextureExtry(final String s) {
        return this.mapRegisteredSprites.get(new ResourceLocation(s).toString());
    }
    
    public void setMipmapLevels(final int mipmapLevels) {
        this.mipmapLevels = mipmapLevels;
    }
    
    public void loadSprites(final IResourceManager resourceManager, final IIconCreator iconCreator) {
        this.mapRegisteredSprites.clear();
        iconCreator.registerSprites(this);
        if (this.mipmapLevels >= (0x9E ^ 0x9A)) {
            this.mipmapLevels = this.detectMaxMipmapLevel(this.mapRegisteredSprites, resourceManager);
            Config.log(TextureMap.I[0x16 ^ 0x12] + this.mipmapLevels);
        }
        this.initMissingImage();
        this.deleteGlTexture();
        this.loadTextureAtlas(resourceManager);
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
            if (2 == 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void updateAnimations() {
        TextureUtil.bindTexture(this.getGlTextureId());
        final Iterator<TextureAtlasSprite> iterator = this.listAnimatedSprites.iterator();
        "".length();
        if (4 == 0) {
            throw null;
        }
        while (iterator.hasNext()) {
            final TextureAtlasSprite textureAtlasSprite = iterator.next();
            if (this.isTerrainAnimationActive(textureAtlasSprite)) {
                textureAtlasSprite.updateAnimation();
            }
        }
        if (Config.isMultiTexture()) {
            final Iterator<TextureAtlasSprite> iterator2 = this.listAnimatedSprites.iterator();
            "".length();
            if (2 < 1) {
                throw null;
            }
            while (iterator2.hasNext()) {
                final TextureAtlasSprite textureAtlasSprite2 = iterator2.next();
                if (this.isTerrainAnimationActive(textureAtlasSprite2)) {
                    final TextureAtlasSprite spriteSingle = textureAtlasSprite2.spriteSingle;
                    if (spriteSingle == null) {
                        continue;
                    }
                    textureAtlasSprite2.bindSpriteTexture();
                    spriteSingle.updateAnimation();
                }
            }
            TextureUtil.bindTexture(this.getGlTextureId());
        }
    }
    
    private int detectMaxMipmapLevel(final Map map, final IResourceManager resourceManager) {
        int detectMinimumSpriteSize = this.detectMinimumSpriteSize(map, resourceManager, 0x5A ^ 0x4E);
        if (detectMinimumSpriteSize < (0xAF ^ 0xBF)) {
            detectMinimumSpriteSize = (0x53 ^ 0x43);
        }
        final int roundUpToPowerOfTwo = MathHelper.roundUpToPowerOfTwo(detectMinimumSpriteSize);
        if (roundUpToPowerOfTwo > (0xAF ^ 0xBF)) {
            Config.log(TextureMap.I[0x52 ^ 0x79] + roundUpToPowerOfTwo);
        }
        int calculateLogBaseTwo = MathHelper.calculateLogBaseTwo(roundUpToPowerOfTwo);
        if (calculateLogBaseTwo < (0xB5 ^ 0xB1)) {
            calculateLogBaseTwo = (0x9B ^ 0x9F);
        }
        return calculateLogBaseTwo;
    }
    
    private boolean isAbsoluteLocationPath(final String s) {
        final String lowerCase = s.toLowerCase();
        if (!lowerCase.startsWith(TextureMap.I[0x17 ^ 0x3E]) && !lowerCase.startsWith(TextureMap.I[0xA5 ^ 0x8F])) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    private ResourceLocation completeResourceLocation(final ResourceLocation resourceLocation, final int n) {
        ResourceLocation resourceLocation2;
        if (this.isAbsoluteLocation(resourceLocation)) {
            if (n == 0) {
                resourceLocation2 = new ResourceLocation(resourceLocation.getResourceDomain(), String.valueOf(resourceLocation.getResourcePath()) + TextureMap.I[0x65 ^ 0x44]);
                "".length();
                if (4 <= -1) {
                    throw null;
                }
            }
            else {
                resourceLocation2 = new ResourceLocation(resourceLocation.getResourceDomain(), String.valueOf(resourceLocation.getResourcePath()) + TextureMap.I[0x67 ^ 0x45] + n + TextureMap.I[0x99 ^ 0xBA]);
                "".length();
                if (4 == 0) {
                    throw null;
                }
            }
        }
        else if (n == 0) {
            final String resourceDomain;
            final String s;
            final Object[] array;
            resourceLocation2 = new ResourceLocation(resourceDomain, String.format(s, array));
            resourceDomain = resourceLocation.getResourceDomain();
            s = TextureMap.I[0x1E ^ 0x3A];
            array = new Object["   ".length()];
            array["".length()] = this.basePath;
            array[" ".length()] = resourceLocation.getResourcePath();
            array["  ".length()] = TextureMap.I[0x87 ^ 0xA2];
            "".length();
            if (1 >= 4) {
                throw null;
            }
        }
        else {
            final String resourceDomain2;
            final String s2;
            final Object[] array2;
            resourceLocation2 = new ResourceLocation(resourceDomain2, String.format(s2, array2));
            resourceDomain2 = resourceLocation.getResourceDomain();
            s2 = TextureMap.I[0x31 ^ 0x17];
            array2 = new Object[0x3F ^ 0x3B];
            array2["".length()] = this.basePath;
            array2[" ".length()] = resourceLocation.getResourcePath();
            array2["  ".length()] = n;
            array2["   ".length()] = TextureMap.I[0x3E ^ 0x19];
        }
        return resourceLocation2;
    }
    
    public TextureMap(final String basePath, final IIconCreator iconCreator) {
        this.listAnimatedSprites = Lists.newArrayList();
        this.mapRegisteredSprites = Maps.newHashMap();
        this.mapUploadedSprites = Maps.newHashMap();
        this.missingImage = new TextureAtlasSprite(TextureMap.I["   ".length()]);
        this.basePath = basePath;
        this.iconCreator = iconCreator;
    }
    
    public TextureAtlasSprite getSpriteSafe(final String s) {
        return this.mapRegisteredSprites.get(new ResourceLocation(s).toString());
    }
    
    public TextureAtlasSprite getAtlasSprite(final String s) {
        TextureAtlasSprite missingImage = this.mapUploadedSprites.get(s);
        if (missingImage == null) {
            missingImage = this.missingImage;
        }
        return missingImage;
    }
    
    public TextureAtlasSprite getMissingSprite() {
        return this.missingImage;
    }
    
    private void initMissingImage() {
        final int minSpriteSize = this.getMinSpriteSize();
        final int[] missingImageData = this.getMissingImageData(minSpriteSize);
        this.missingImage.setIconWidth(minSpriteSize);
        this.missingImage.setIconHeight(minSpriteSize);
        final int[][] array = new int[this.mipmapLevels + " ".length()][];
        array["".length()] = missingImageData;
        final TextureAtlasSprite missingImage = this.missingImage;
        final int[][][] array2 = new int[" ".length()][][];
        array2["".length()] = array;
        missingImage.setFramesTextureData(Lists.newArrayList((Object[])array2));
        this.missingImage.setIndexInMap("".length());
    }
    
    private int getMinSpriteSize() {
        int n = " ".length() << this.mipmapLevels;
        if (n < (0x72 ^ 0x62)) {
            n = (0x7 ^ 0x17);
        }
        return n;
    }
}
