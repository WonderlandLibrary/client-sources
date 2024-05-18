package net.minecraft.client.renderer.texture;

import net.minecraft.client.resources.*;
import com.google.common.collect.*;
import java.util.concurrent.*;
import net.minecraft.util.*;
import net.minecraft.crash.*;
import java.awt.image.*;
import net.minecraft.client.resources.data.*;
import java.util.*;
import optfine.*;
import java.io.*;

public class TextureAtlasSprite
{
    protected int width;
    protected List framesTextureData;
    protected int originY;
    public int glSpriteTextureId;
    public float baseU;
    private static final String[] I;
    private float minV;
    public TextureAtlasSprite spriteSingle;
    protected int height;
    private static String locationNameCompass;
    private final String iconName;
    private int indexInMap;
    public int mipmapLevels;
    private float maxU;
    protected int frameCounter;
    public float baseV;
    protected int tickCounter;
    private float maxV;
    protected int originX;
    protected int[][] interpolatedFrameData;
    public boolean isSpriteSingle;
    private static String locationNameClock;
    public int sheetWidth;
    private float minU;
    public int sheetHeight;
    protected boolean rotated;
    private AnimationMetadataSection animationMetadata;
    private static final String __OBFID;
    
    public int getOriginY() {
        return this.originY;
    }
    
    protected static TextureAtlasSprite makeAtlasSprite(final ResourceLocation resourceLocation) {
        final String string = resourceLocation.toString();
        TextureAtlasSprite textureAtlasSprite;
        if (TextureAtlasSprite.locationNameClock.equals(string)) {
            textureAtlasSprite = new TextureClock(string);
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else if (TextureAtlasSprite.locationNameCompass.equals(string)) {
            textureAtlasSprite = new TextureCompass(string);
            "".length();
            if (0 >= 1) {
                throw null;
            }
        }
        else {
            textureAtlasSprite = new TextureAtlasSprite(string);
        }
        return textureAtlasSprite;
    }
    
    public boolean hasAnimationMetadata() {
        if (this.animationMetadata != null) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public float getInterpolatedV(final double n) {
        return this.minV + (this.maxV - this.minV) * ((float)n / 16.0f);
    }
    
    public float getMinU() {
        return this.minU;
    }
    
    public boolean load(final IResourceManager resourceManager, final ResourceLocation resourceLocation) {
        return " ".length() != 0;
    }
    
    public int[][] getFrameTextureData(final int n) {
        return this.framesTextureData.get(n);
    }
    
    protected TextureAtlasSprite(final String iconName) {
        this.framesTextureData = Lists.newArrayList();
        this.indexInMap = -" ".length();
        this.glSpriteTextureId = -" ".length();
        this.spriteSingle = null;
        this.isSpriteSingle = ("".length() != 0);
        this.mipmapLevels = "".length();
        this.iconName = iconName;
        if (Config.isMultiTexture()) {
            this.spriteSingle = new TextureAtlasSprite(this);
        }
    }
    
    public void setIndexInMap(final int indexInMap) {
        this.indexInMap = indexInMap;
    }
    
    public void generateMipmaps(final int n) {
        final ArrayList arrayList = Lists.newArrayList();
        int i = "".length();
        "".length();
        if (true != true) {
            throw null;
        }
        while (i < this.framesTextureData.size()) {
            final int[][] array = this.framesTextureData.get(i);
            if (array != null) {
                try {
                    arrayList.add(TextureUtil.generateMipmapData(n, this.width, array));
                    "".length();
                    if (1 == 2) {
                        throw null;
                    }
                }
                catch (Throwable t) {
                    final CrashReport crashReport = CrashReport.makeCrashReport(t, TextureAtlasSprite.I[0xA5 ^ 0xA2]);
                    final CrashReportCategory category = crashReport.makeCategory(TextureAtlasSprite.I[0x3 ^ 0xB]);
                    category.addCrashSection(TextureAtlasSprite.I[0x19 ^ 0x10], i);
                    category.addCrashSectionCallable(TextureAtlasSprite.I[0xBB ^ 0xB1], new Callable(this, array) {
                        private final int[][] val$aint;
                        private static final String[] I;
                        private static final String __OBFID;
                        final TextureAtlasSprite this$0;
                        
                        @Override
                        public String call() throws Exception {
                            final StringBuilder sb = new StringBuilder();
                            final int[][] val$aint;
                            final int length = (val$aint = this.val$aint).length;
                            int i = "".length();
                            "".length();
                            if (2 <= -1) {
                                throw null;
                            }
                            while (i < length) {
                                final int[] array = val$aint[i];
                                if (sb.length() > 0) {
                                    sb.append(TextureAtlasSprite$1.I["".length()]);
                                }
                                final StringBuilder sb2 = sb;
                                Serializable value;
                                if (array == null) {
                                    value = TextureAtlasSprite$1.I[" ".length()];
                                    "".length();
                                    if (3 < -1) {
                                        throw null;
                                    }
                                }
                                else {
                                    value = array.length;
                                }
                                sb2.append(value);
                                ++i;
                            }
                            return sb.toString();
                        }
                        
                        static {
                            I();
                            __OBFID = TextureAtlasSprite$1.I["  ".length()];
                        }
                        
                        private static void I() {
                            (I = new String["   ".length()])["".length()] = I("Xs", "tSmRm");
                            TextureAtlasSprite$1.I[" ".length()] = I("?8?<", "QMSPn");
                            TextureAtlasSprite$1.I["  ".length()] = I("\u0012\r\n|~aqd|xb", "QAULN");
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
                                if (2 == -1) {
                                    throw null;
                                }
                            }
                            return sb.toString();
                        }
                        
                        @Override
                        public Object call() throws Exception {
                            return this.call();
                        }
                    });
                    throw new ReportedException(crashReport);
                }
            }
            ++i;
        }
        this.setFramesTextureData(arrayList);
        if (this.spriteSingle != null) {
            this.spriteSingle.generateMipmaps(n);
        }
    }
    
    public void copyFrom(final TextureAtlasSprite textureAtlasSprite) {
        this.originX = textureAtlasSprite.originX;
        this.originY = textureAtlasSprite.originY;
        this.width = textureAtlasSprite.width;
        this.height = textureAtlasSprite.height;
        this.rotated = textureAtlasSprite.rotated;
        this.minU = textureAtlasSprite.minU;
        this.maxU = textureAtlasSprite.maxU;
        this.minV = textureAtlasSprite.minV;
        this.maxV = textureAtlasSprite.maxV;
        if (this.spriteSingle != null) {
            this.spriteSingle.initSprite(this.width, this.height, "".length(), "".length(), "".length() != 0);
        }
    }
    
    public void deleteSpriteTexture() {
        if (this.glSpriteTextureId >= 0) {
            TextureUtil.deleteTexture(this.glSpriteTextureId);
            this.glSpriteTextureId = -" ".length();
        }
    }
    
    public void loadSprite(final BufferedImage[] array, final AnimationMetadataSection animationMetadata) throws IOException {
        this.resetSprite();
        final int width = array["".length()].getWidth();
        final int height = array["".length()].getHeight();
        this.width = width;
        this.height = height;
        final int[][] array2 = new int[array.length][];
        int i = "".length();
        "".length();
        if (true != true) {
            throw null;
        }
        while (i < array.length) {
            final BufferedImage bufferedImage = array[i];
            if (bufferedImage != null) {
                if (i > 0 && (bufferedImage.getWidth() != width >> i || bufferedImage.getHeight() != height >> i)) {
                    final String s = TextureAtlasSprite.I["   ".length()];
                    final Object[] array3 = new Object[0x6 ^ 0x3];
                    array3["".length()] = i;
                    array3[" ".length()] = bufferedImage.getWidth();
                    array3["  ".length()] = bufferedImage.getHeight();
                    array3["   ".length()] = width >> i;
                    array3[0x86 ^ 0x82] = height >> i;
                    throw new RuntimeException(String.format(s, array3));
                }
                array2[i] = new int[bufferedImage.getWidth() * bufferedImage.getHeight()];
                bufferedImage.getRGB("".length(), "".length(), bufferedImage.getWidth(), bufferedImage.getHeight(), array2[i], "".length(), bufferedImage.getWidth());
            }
            ++i;
        }
        if (animationMetadata == null) {
            if (height != width) {
                throw new RuntimeException(TextureAtlasSprite.I[0x7E ^ 0x7A]);
            }
            this.framesTextureData.add(array2);
            "".length();
            if (0 >= 1) {
                throw null;
            }
        }
        else {
            final int n = height / width;
            final int n2 = width;
            final int n3 = width;
            this.height = this.width;
            if (animationMetadata.getFrameCount() > 0) {
                final Iterator<Integer> iterator = animationMetadata.getFrameIndexSet().iterator();
                "".length();
                if (2 < -1) {
                    throw null;
                }
                while (iterator.hasNext()) {
                    final int intValue = iterator.next();
                    if (intValue >= n) {
                        throw new RuntimeException(TextureAtlasSprite.I[0x5F ^ 0x5A] + intValue);
                    }
                    this.allocateFrameTextureData(intValue);
                    this.framesTextureData.set(intValue, getFrameTextureData(array2, n2, n3, intValue));
                }
                this.animationMetadata = animationMetadata;
                "".length();
                if (2 != 2) {
                    throw null;
                }
            }
            else {
                final ArrayList arrayList = Lists.newArrayList();
                int j = "".length();
                "".length();
                if (0 == 3) {
                    throw null;
                }
                while (j < n) {
                    this.framesTextureData.add(getFrameTextureData(array2, n2, n3, j));
                    arrayList.add(new AnimationFrame(j, -" ".length()));
                    ++j;
                }
                this.animationMetadata = new AnimationMetadataSection(arrayList, this.width, this.height, animationMetadata.getFrameTime(), animationMetadata.isInterpolate());
            }
        }
        int k = "".length();
        "".length();
        if (-1 < -1) {
            throw null;
        }
        while (k < this.framesTextureData.size()) {
            final int[][] array4 = this.framesTextureData.get(k);
            if (array4 != null && !this.iconName.startsWith(TextureAtlasSprite.I[0x8E ^ 0x88])) {
                int l = "".length();
                "".length();
                if (4 < -1) {
                    throw null;
                }
                while (l < array4.length) {
                    this.fixTransparentColor(array4[l]);
                    ++l;
                }
            }
            ++k;
        }
        if (this.spriteSingle != null) {
            this.spriteSingle.loadSprite(array, animationMetadata);
        }
    }
    
    public double getSpriteV16(final float n) {
        return (n - this.minV) / (this.maxV - this.minV) * 16.0f;
    }
    
    public int getIconWidth() {
        return this.width;
    }
    
    private TextureAtlasSprite(final TextureAtlasSprite textureAtlasSprite) {
        this.framesTextureData = Lists.newArrayList();
        this.indexInMap = -" ".length();
        this.glSpriteTextureId = -" ".length();
        this.spriteSingle = null;
        this.isSpriteSingle = ("".length() != 0);
        this.mipmapLevels = "".length();
        this.iconName = textureAtlasSprite.iconName;
        this.isSpriteSingle = (" ".length() != 0);
    }
    
    public static void setLocationNameClock(final String locationNameClock) {
        TextureAtlasSprite.locationNameClock = locationNameClock;
    }
    
    private static int[][] getFrameTextureData(final int[][] array, final int n, final int n2, final int n3) {
        final int[][] array2 = new int[array.length][];
        int i = "".length();
        "".length();
        if (2 >= 3) {
            throw null;
        }
        while (i < array.length) {
            final int[] array3 = array[i];
            if (array3 != null) {
                array2[i] = new int[(n >> i) * (n2 >> i)];
                System.arraycopy(array3, n3 * array2[i].length, array2[i], "".length(), array2[i].length);
            }
            ++i;
        }
        return array2;
    }
    
    public void clearFramesTextureData() {
        this.framesTextureData.clear();
        if (this.spriteSingle != null) {
            this.spriteSingle.clearFramesTextureData();
        }
    }
    
    public int getIconHeight() {
        return this.height;
    }
    
    public void setFramesTextureData(final List list) {
        this.framesTextureData = list;
        if (this.spriteSingle != null) {
            this.spriteSingle.setFramesTextureData(list);
        }
    }
    
    public static void setLocationNameCompass(final String locationNameCompass) {
        TextureAtlasSprite.locationNameCompass = locationNameCompass;
    }
    
    static {
        I();
        __OBFID = TextureAtlasSprite.I["".length()];
        TextureAtlasSprite.locationNameClock = TextureAtlasSprite.I[" ".length()];
        TextureAtlasSprite.locationNameCompass = TextureAtlasSprite.I["  ".length()];
    }
    
    private void updateAnimationInterpolated() {
        final double n = 1.0 - this.tickCounter / this.animationMetadata.getFrameTimeSingle(this.frameCounter);
        final int frameIndex = this.animationMetadata.getFrameIndex(this.frameCounter);
        int n2;
        if (this.animationMetadata.getFrameCount() == 0) {
            n2 = this.framesTextureData.size();
            "".length();
            if (4 < 2) {
                throw null;
            }
        }
        else {
            n2 = this.animationMetadata.getFrameCount();
        }
        final int frameIndex2 = this.animationMetadata.getFrameIndex((this.frameCounter + " ".length()) % n2);
        if (frameIndex != frameIndex2 && frameIndex2 >= 0 && frameIndex2 < this.framesTextureData.size()) {
            final int[][] array = this.framesTextureData.get(frameIndex);
            final int[][] array2 = this.framesTextureData.get(frameIndex2);
            if (this.interpolatedFrameData == null || this.interpolatedFrameData.length != array.length) {
                this.interpolatedFrameData = new int[array.length][];
            }
            int i = "".length();
            "".length();
            if (3 < 3) {
                throw null;
            }
            while (i < array.length) {
                if (this.interpolatedFrameData[i] == null) {
                    this.interpolatedFrameData[i] = new int[array[i].length];
                }
                if (i < array2.length && array2[i].length == array[i].length) {
                    int j = "".length();
                    "".length();
                    if (2 == 3) {
                        throw null;
                    }
                    while (j < array[i].length) {
                        final int n3 = array[i][j];
                        final int n4 = array2[i][j];
                        this.interpolatedFrameData[i][j] = ((n3 & -(13136181 + 417346 - 7971572 + 11195261)) | (int)(((n3 & 4629491 + 11478553 - 7200176 + 7803812) >> (0x1 ^ 0x11)) * n + ((n4 & 1987745 + 4507112 - 483486 + 10700309) >> (0x82 ^ 0x92)) * (1.0 - n)) << (0x65 ^ 0x75) | (int)(((n3 & 25654 + 65047 - 33250 + 7829) >> (0x2 ^ 0xA)) * n + ((n4 & 14091 + 8995 - 1461 + 43655) >> (0x24 ^ 0x2C)) * (1.0 - n)) << (0x2A ^ 0x22) | (int)((n3 & 116 + 101 - 166 + 204) * n + (n4 & 125 + 229 - 200 + 101) * (1.0 - n)));
                        ++j;
                    }
                }
                ++i;
            }
            TextureUtil.uploadTextureMipmap(this.interpolatedFrameData, this.width, this.height, this.originX, this.originY, "".length() != 0, "".length() != 0);
        }
    }
    
    public float getInterpolatedU(final double n) {
        return this.minU + (this.maxU - this.minU) * (float)n / 16.0f;
    }
    
    public boolean hasCustomLoader(final IResourceManager resourceManager, final ResourceLocation resourceLocation) {
        return "".length() != 0;
    }
    
    public float getMaxV() {
        return this.maxV;
    }
    
    private void resetSprite() {
        this.animationMetadata = null;
        this.setFramesTextureData(Lists.newArrayList());
        this.frameCounter = "".length();
        this.tickCounter = "".length();
        if (this.spriteSingle != null) {
            this.spriteSingle.resetSprite();
        }
    }
    
    public int getOriginX() {
        return this.originX;
    }
    
    public void initSprite(final int n, final int n2, final int originX, final int originY, final boolean rotated) {
        this.originX = originX;
        this.originY = originY;
        this.rotated = rotated;
        final float n3 = (float)(0.009999999776482582 / n);
        final float n4 = (float)(0.009999999776482582 / n2);
        this.minU = originX / n + n3;
        this.maxU = (originX + this.width) / n - n3;
        this.minV = originY / n2 + n4;
        this.maxV = (originY + this.height) / n2 - n4;
        this.baseU = Math.min(this.minU, this.maxU);
        this.baseV = Math.min(this.minV, this.maxV);
        if (this.spriteSingle != null) {
            this.spriteSingle.initSprite(this.width, this.height, "".length(), "".length(), "".length() != 0);
        }
    }
    
    private void allocateFrameTextureData(final int n) {
        if (this.framesTextureData.size() <= n) {
            int i = this.framesTextureData.size();
            "".length();
            if (-1 >= 0) {
                throw null;
            }
            while (i <= n) {
                this.framesTextureData.add(null);
                ++i;
            }
        }
        if (this.spriteSingle != null) {
            this.spriteSingle.allocateFrameTextureData(n);
        }
    }
    
    private void fixTransparentColor(final int[] array) {
        if (array != null) {
            long n = 0L;
            long n2 = 0L;
            long n3 = 0L;
            long n4 = 0L;
            int i = "".length();
            "".length();
            if (false) {
                throw null;
            }
            while (i < array.length) {
                final int n5 = array[i];
                if ((n5 >> (0x53 ^ 0x4B) & 225 + 195 - 198 + 33) >= (0x86 ^ 0x96)) {
                    final int n6 = n5 >> (0x3E ^ 0x2E) & 76 + 11 + 159 + 9;
                    final int n7 = n5 >> (0x66 ^ 0x6E) & 17 + 5 + 77 + 156;
                    final int n8 = n5 & 49 + 102 + 29 + 75;
                    n += n6;
                    n2 += n7;
                    n3 += n8;
                    ++n4;
                }
                ++i;
            }
            if (n4 > 0L) {
                final int n9 = (int)(n / n4) << (0x8 ^ 0x18) | (int)(n2 / n4) << (0x2F ^ 0x27) | (int)(n3 / n4);
                int j = "".length();
                "".length();
                if (2 >= 4) {
                    throw null;
                }
                while (j < array.length) {
                    if ((array[j] >> (0x70 ^ 0x68) & 114 + 63 + 22 + 56) <= (0xAE ^ 0xBE)) {
                        array[j] = n9;
                    }
                    ++j;
                }
            }
        }
    }
    
    @Override
    public String toString() {
        return TextureAtlasSprite.I[0x56 ^ 0x5D] + this.iconName + (char)(0xA9 ^ 0x8E) + TextureAtlasSprite.I[0x3B ^ 0x37] + this.framesTextureData.size() + TextureAtlasSprite.I[0x1 ^ 0xC] + this.rotated + TextureAtlasSprite.I[0x68 ^ 0x66] + this.originX + TextureAtlasSprite.I[0xCD ^ 0xC2] + this.originY + TextureAtlasSprite.I[0x78 ^ 0x68] + this.height + TextureAtlasSprite.I[0x3C ^ 0x2D] + this.width + TextureAtlasSprite.I[0x72 ^ 0x60] + this.minU + TextureAtlasSprite.I[0xAB ^ 0xB8] + this.maxU + TextureAtlasSprite.I[0x78 ^ 0x6C] + this.minV + TextureAtlasSprite.I[0x2A ^ 0x3F] + this.maxV + (char)(0x1F ^ 0x62);
    }
    
    public int getFrameCount() {
        return this.framesTextureData.size();
    }
    
    public void setIconWidth(final int width) {
        this.width = width;
        if (this.spriteSingle != null) {
            this.spriteSingle.setIconWidth(this.width);
        }
    }
    
    public void setIconHeight(final int height) {
        this.height = height;
        if (this.spriteSingle != null) {
            this.spriteSingle.setIconHeight(this.height);
        }
    }
    
    public double getSpriteU16(final float n) {
        return (n - this.minU) / (this.maxU - this.minU) * 16.0f;
    }
    
    public float toSingleU(float n) {
        n -= this.baseU;
        n *= this.sheetWidth / this.width;
        return n;
    }
    
    public void updateAnimation() {
        this.tickCounter += " ".length();
        if (this.tickCounter >= this.animationMetadata.getFrameTimeSingle(this.frameCounter)) {
            final int frameIndex = this.animationMetadata.getFrameIndex(this.frameCounter);
            int n;
            if (this.animationMetadata.getFrameCount() == 0) {
                n = this.framesTextureData.size();
                "".length();
                if (1 >= 2) {
                    throw null;
                }
            }
            else {
                n = this.animationMetadata.getFrameCount();
            }
            this.frameCounter = (this.frameCounter + " ".length()) % n;
            this.tickCounter = "".length();
            final int frameIndex2 = this.animationMetadata.getFrameIndex(this.frameCounter);
            final int length = "".length();
            final boolean isSpriteSingle = this.isSpriteSingle;
            if (frameIndex != frameIndex2 && frameIndex2 >= 0 && frameIndex2 < this.framesTextureData.size()) {
                TextureUtil.uploadTextureMipmap(this.framesTextureData.get(frameIndex2), this.width, this.height, this.originX, this.originY, length != 0, isSpriteSingle);
                "".length();
                if (0 >= 1) {
                    throw null;
                }
            }
        }
        else if (this.animationMetadata.isInterpolate()) {
            this.updateAnimationInterpolated();
        }
    }
    
    public int getIndexInMap() {
        return this.indexInMap;
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
            if (2 == 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public float getMinV() {
        return this.minV;
    }
    
    public float getMaxU() {
        return this.maxU;
    }
    
    public float toSingleV(float n) {
        n -= this.baseV;
        n *= this.sheetHeight / this.height;
        return n;
    }
    
    public void bindSpriteTexture() {
        if (this.glSpriteTextureId < 0) {
            TextureUtil.allocateTextureImpl(this.glSpriteTextureId = TextureUtil.glGenTextures(), this.mipmapLevels, this.width, this.height);
            TextureUtils.applyAnisotropicLevel();
        }
        TextureUtils.bindTexture(this.glSpriteTextureId);
    }
    
    public String getIconName() {
        return this.iconName;
    }
    
    private static void I() {
        (I = new String[0x69 ^ 0x7F])["".length()] = I("$9:CFWETC@U", "guesv");
        TextureAtlasSprite.I[" ".length()] = I("\r\u0018\u00076.\u0006\u0003A96\u0000\u000e\u0005", "omnZZ");
        TextureAtlasSprite.I["  ".length()] = I("\u0007\u00189\u001f\u001b\f\u0003\u007f\u0010\u0000\b\u001d1\u0000\u001c", "emPso");
        TextureAtlasSprite.I["   ".length()] = I("$\u00072\u0001!\u0014I'\fm\u001d\u00062\u0007m\u001c\u0000#\u000f(\u0007\f?YmT\r\u007fC$\u001c\b4\u0006m\u0018\u001as\u0010$\u000b\fiCh\u0015\u0011v\u0007aQ\f+\u0013(\u0012\u001d6\u0007mT\r+F)", "qiScM");
        TextureAtlasSprite.I[0x29 ^ 0x2D] = I("\u001a(\b\u0000+\u0016z\u0006\u0018>\u001d9\u0013K<\u0019.\u000e\u0004n\u00194\u0003K \u0017.G\n X;\t\u0002#\u0019.\u000e\u0004 ", "xZgkN");
        TextureAtlasSprite.I[0x7B ^ 0x7E] = I("\u0018,>\u0016\u0002\u0018&h\u0011\u001c\u0010/-\u001e\u0000\u0015'0W", "qBHwn");
        TextureAtlasSprite.I[0x75 ^ 0x73] = I("'\u001f\u0004\u000f\u00008\u0017\f\u001eY(\u001a\u0005\t\b9Y\u0006\u000f\u0002<\u0013\u00195", "Jvjjc");
        TextureAtlasSprite.I[0x91 ^ 0x96] = I("\t3%\b\u0017/\"\"\u0003\u0002n;\"\u001d\b/&8M\u0003!$k\u000b\u0017/;.", "NVKme");
        TextureAtlasSprite.I[0xBB ^ 0xB3] = I("> \u0010)\u000bX0\u0014-\u0000\u001fr\u00180\u000b\n3\u0005!\n", "xRqDn");
        TextureAtlasSprite.I[0x82 ^ 0x8B] = I("6\u001a\u0012 \rP\u0001\u001d)\r\b", "phsMh");
        TextureAtlasSprite.I[0x3D ^ 0x37] = I("\"(/%\u0000D)'2\u0000\u0017", "dZNHe");
        TextureAtlasSprite.I[0x4 ^ 0xF] = I("\f1\u001f!\u0012*1&!\u000b9'4%\u00151 \u0002.\t99\u0002h@", "XTgUg");
        TextureAtlasSprite.I[0x26 ^ 0x2A] = I("Xu\u0000\u000b;\u00190%\u0016/\u001a![", "tUfyZ");
        TextureAtlasSprite.I[0x28 ^ 0x25] = I("mx\b\u001c= ,\u001f\u0017t", "AXzsI");
        TextureAtlasSprite.I[0x32 ^ 0x3C] = I("UI*o", "yiRRs");
        TextureAtlasSprite.I[0xD ^ 0x2] = I("hI)h", "DiPUE");
        TextureAtlasSprite.I[0x9E ^ 0x8E] = I("GT\r\u00120\f\u001c\u0011J", "ktewY");
        TextureAtlasSprite.I[0x80 ^ 0x91] = I("Yr\u0003!%\u0001:I", "uRtHA");
        TextureAtlasSprite.I[0xB8 ^ 0xAA] = I("Fc\u0010au", "jCeQH");
        TextureAtlasSprite.I[0x69 ^ 0x7A] = I("GJ8eg", "kjMTZ");
        TextureAtlasSprite.I[0x4C ^ 0x58] = I("bn\u001bxL", "NNmHq");
        TextureAtlasSprite.I[0x25 ^ 0x30] = I("Mc\u0018pP", "aCnAm");
    }
}
