package optfine;

import net.minecraft.util.*;
import net.minecraft.client.resources.*;
import java.io.*;
import java.util.zip.*;
import java.util.*;
import java.awt.image.*;
import java.awt.*;
import javax.imageio.*;
import net.minecraft.client.settings.*;

public class TextureAnimations
{
    private static final String[] I;
    private static TextureAnimation[] textureAnimations;
    
    public static byte[] getCustomTextureData(final String s, final int n) {
        byte[] array = loadImage(s, n);
        if (array == null) {
            array = loadImage(TextureAnimations.I[0x3E ^ 0x29] + s, n);
        }
        return array;
    }
    
    public static void update() {
        TextureAnimations.textureAnimations = null;
        TextureAnimations.textureAnimations = getTextureAnimations(Config.getResourcePacks());
        if (Config.isAnimatedTextures()) {
            updateAnimations();
        }
    }
    
    public static TextureAnimation makeTextureAnimation(final Properties properties, final ResourceLocation resourceLocation) {
        final String property = properties.getProperty(TextureAnimations.I[0x5B ^ 0x5F]);
        final String property2 = properties.getProperty(TextureAnimations.I[0x15 ^ 0x10]);
        final int int1 = Config.parseInt(properties.getProperty(TextureAnimations.I[0x78 ^ 0x7E]), -" ".length());
        final int int2 = Config.parseInt(properties.getProperty(TextureAnimations.I[0x25 ^ 0x22]), -" ".length());
        final int int3 = Config.parseInt(properties.getProperty(TextureAnimations.I[0x3B ^ 0x33]), -" ".length());
        final int int4 = Config.parseInt(properties.getProperty(TextureAnimations.I[0x13 ^ 0x1A]), -" ".length());
        if (property == null || property2 == null) {
            Config.warn(TextureAnimations.I[0x1F ^ 0x12]);
            return null;
        }
        if (int1 < 0 || int2 < 0 || int3 < 0 || int4 < 0) {
            Config.warn(TextureAnimations.I[0x4B ^ 0x47]);
            return null;
        }
        final String basePath = TextureUtils.getBasePath(resourceLocation.getResourcePath());
        final String fixResourcePath = TextureUtils.fixResourcePath(property, basePath);
        final String fixResourcePath2 = TextureUtils.fixResourcePath(property2, basePath);
        final byte[] customTextureData = getCustomTextureData(fixResourcePath, int3);
        if (customTextureData == null) {
            Config.warn(TextureAnimations.I[0x3E ^ 0x34] + fixResourcePath2);
            return null;
        }
        final ResourceLocation resourceLocation2 = new ResourceLocation(fixResourcePath2);
        if (!Config.hasResource(resourceLocation2)) {
            Config.warn(TextureAnimations.I[0x0 ^ 0xB] + fixResourcePath2);
            return null;
        }
        return new TextureAnimation(fixResourcePath, customTextureData, fixResourcePath2, resourceLocation2, int1, int2, int3, int4, properties, " ".length());
    }
    
    public static TextureAnimation[] getTextureAnimations(final IResourcePack resourcePack) {
        if (!(resourcePack instanceof AbstractResourcePack)) {
            return null;
        }
        final File resourcePackFile = ResourceUtils.getResourcePackFile((AbstractResourcePack)resourcePack);
        if (resourcePackFile == null) {
            return null;
        }
        if (!resourcePackFile.exists()) {
            return null;
        }
        String[] array;
        if (resourcePackFile.isFile()) {
            array = getAnimationPropertiesZip(resourcePackFile);
            "".length();
            if (-1 >= 2) {
                throw null;
            }
        }
        else {
            array = getAnimationPropertiesDir(resourcePackFile);
        }
        if (array == null) {
            return null;
        }
        final ArrayList<TextureAnimation> list = new ArrayList<TextureAnimation>();
        int i = "".length();
        "".length();
        if (3 != 3) {
            throw null;
        }
        while (i < array.length) {
            final String s = array[i];
            Config.dbg(TextureAnimations.I["".length()] + s);
            try {
                final ResourceLocation resourceLocation = new ResourceLocation(s);
                final InputStream inputStream = resourcePack.getInputStream(resourceLocation);
                final Properties properties = new Properties();
                properties.load(inputStream);
                final TextureAnimation textureAnimation = makeTextureAnimation(properties, resourceLocation);
                if (textureAnimation != null) {
                    if (Config.getDefiningResourcePack(new ResourceLocation(textureAnimation.getDstTex())) != resourcePack) {
                        Config.dbg(TextureAnimations.I[" ".length()] + s + TextureAnimations.I["  ".length()]);
                        "".length();
                        if (1 < -1) {
                            throw null;
                        }
                    }
                    else {
                        list.add(textureAnimation);
                        "".length();
                        if (4 < 2) {
                            throw null;
                        }
                    }
                }
            }
            catch (FileNotFoundException ex) {
                Config.warn(TextureAnimations.I["   ".length()] + ex.getMessage());
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
            catch (IOException ex2) {
                ex2.printStackTrace();
            }
            ++i;
        }
        return list.toArray(new TextureAnimation[list.size()]);
    }
    
    public static String[] getAnimationPropertiesDir(final File file) {
        final File file2 = new File(file, TextureAnimations.I[0x57 ^ 0x59]);
        if (!file2.exists()) {
            return null;
        }
        if (!file2.isDirectory()) {
            return null;
        }
        final File[] listFiles = file2.listFiles();
        if (listFiles == null) {
            return null;
        }
        final ArrayList<String> list = new ArrayList<String>();
        int i = "".length();
        "".length();
        if (3 < 1) {
            throw null;
        }
        while (i < listFiles.length) {
            final File file3 = listFiles[i];
            final String name = file3.getName();
            if (!name.startsWith(TextureAnimations.I[0x4A ^ 0x45]) && name.endsWith(TextureAnimations.I[0x77 ^ 0x67]) && file3.isFile() && file3.canRead()) {
                Config.dbg(TextureAnimations.I[0x65 ^ 0x74] + file3.getName());
                list.add(TextureAnimations.I[0x8A ^ 0x98] + name);
            }
            ++i;
        }
        return list.toArray(new String[list.size()]);
    }
    
    static {
        I();
        TextureAnimations.textureAnimations = null;
    }
    
    public static String[] getAnimationPropertiesZip(final File file) {
        try {
            final Enumeration<? extends ZipEntry> entries = new ZipFile(file).entries();
            final ArrayList<String> list = new ArrayList<String>();
            "".length();
            if (1 <= 0) {
                throw null;
            }
            while (entries.hasMoreElements()) {
                final String name = ((ZipEntry)entries.nextElement()).getName();
                if (name.startsWith(TextureAnimations.I[0x64 ^ 0x77]) && !name.startsWith(TextureAnimations.I[0x72 ^ 0x66]) && name.endsWith(TextureAnimations.I[0x22 ^ 0x37])) {
                    list.add(name.substring(TextureAnimations.I[0x76 ^ 0x60].length()));
                }
            }
            return list.toArray(new String[list.size()]);
        }
        catch (IOException ex) {
            ex.printStackTrace();
            return null;
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
            if (4 == 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static void updateCustomAnimations() {
        if (TextureAnimations.textureAnimations != null && Config.isAnimatedTextures()) {
            updateAnimations();
        }
    }
    
    public static void reset() {
        TextureAnimations.textureAnimations = null;
    }
    
    public static TextureAnimation[] getTextureAnimations(final IResourcePack[] array) {
        final ArrayList list = new ArrayList();
        int i = "".length();
        "".length();
        if (2 != 2) {
            throw null;
        }
        while (i < array.length) {
            final TextureAnimation[] textureAnimations = getTextureAnimations(array[i]);
            if (textureAnimations != null) {
                list.addAll(Arrays.asList(textureAnimations));
            }
            ++i;
        }
        return (TextureAnimation[])list.toArray(new TextureAnimation[list.size()]);
    }
    
    public static BufferedImage scaleBufferedImage(final BufferedImage bufferedImage, final int n, final int n2) {
        final BufferedImage bufferedImage2 = new BufferedImage(n, n2, "  ".length());
        final Graphics2D graphics = bufferedImage2.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics.drawImage(bufferedImage, "".length(), "".length(), n, n2, null);
        return bufferedImage2;
    }
    
    private static BufferedImage readTextureImage(final InputStream inputStream) throws IOException {
        final BufferedImage read = ImageIO.read(inputStream);
        inputStream.close();
        return read;
    }
    
    public static void updateAnimations() {
        if (TextureAnimations.textureAnimations != null) {
            int i = "".length();
            "".length();
            if (4 != 4) {
                throw null;
            }
            while (i < TextureAnimations.textureAnimations.length) {
                TextureAnimations.textureAnimations[i].updateTexture();
                ++i;
            }
        }
    }
    
    private static byte[] loadImage(final String s, final int n) {
        final GameSettings gameSettings = Config.getGameSettings();
        try {
            final InputStream resourceStream = Config.getResourceStream(new ResourceLocation(s));
            if (resourceStream == null) {
                return null;
            }
            BufferedImage bufferedImage = readTextureImage(resourceStream);
            if (bufferedImage == null) {
                return null;
            }
            if (n > 0 && bufferedImage.getWidth() != n) {
                bufferedImage = scaleBufferedImage(bufferedImage, n, n * (bufferedImage.getHeight() / bufferedImage.getWidth()));
            }
            final int width = bufferedImage.getWidth();
            final int height = bufferedImage.getHeight();
            final int[] array = new int[width * height];
            final byte[] array2 = new byte[width * height * (0x0 ^ 0x4)];
            bufferedImage.getRGB("".length(), "".length(), width, height, array, "".length(), width);
            int i = "".length();
            "".length();
            if (3 != 3) {
                throw null;
            }
            while (i < array.length) {
                final int n2 = array[i] >> (0x91 ^ 0x89) & 198 + 31 - 59 + 85;
                int n3 = array[i] >> (0x8 ^ 0x18) & 231 + 200 - 366 + 190;
                int n4 = array[i] >> (0xBB ^ 0xB3) & 200 + 119 - 111 + 47;
                int n5 = array[i] & 7 + 141 - 48 + 155;
                if (gameSettings != null && gameSettings.anaglyph) {
                    final int n6 = (n3 * (0x3B ^ 0x25) + n4 * (0x15 ^ 0x2E) + n5 * (0x18 ^ 0x13)) / (0x6D ^ 0x9);
                    final int n7 = (n3 * (0x5A ^ 0x44) + n4 * (0x1C ^ 0x5A)) / (0xA ^ 0x6E);
                    final int n8 = (n3 * (0xA2 ^ 0xBC) + n5 * (0x14 ^ 0x52)) / (0xEC ^ 0x88);
                    n3 = n6;
                    n4 = n7;
                    n5 = n8;
                }
                array2[i * (0x4F ^ 0x4B) + "".length()] = (byte)n3;
                array2[i * (0x9E ^ 0x9A) + " ".length()] = (byte)n4;
                array2[i * (0x44 ^ 0x40) + "  ".length()] = (byte)n5;
                array2[i * (0x3 ^ 0x7) + "   ".length()] = (byte)n2;
                ++i;
            }
            return array2;
        }
        catch (FileNotFoundException ex2) {
            return null;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    private static void I() {
        (I = new String[0x1F ^ 0x7])["".length()] = I("\u001b )\u001f#= q\n8&(0\u001f? +kK", "OEQkV");
        TextureAnimations.I[" ".length()] = I("4\u0002,\u00163\u0002\r\u007fF", "giEfC");
        TextureAnimations.I["  ".length()] = I("ay\"\u0014\u0004*<\"U\u0002(!\"\u0000\u0004(y8\u001a\u0002m59\u0014\u0012(=v\u0013\u0004\"4v\u0006\u0017 <v\u0007\u0013>6#\u0007\u0015(y&\u0014\u0015&", "MYVuv");
        TextureAnimations.I["   ".length()] = I("\u00151#\u0017O=7;R\t<-!\u0016Us", "SXOro");
        TextureAnimations.I[0x8F ^ 0x8B] = I("\u0002\u00009(", "drVEs");
        TextureAnimations.I[0xA6 ^ 0xA3] = I("07", "DXoiV");
        TextureAnimations.I[0x71 ^ 0x77] = I("\u0000", "xVIwp");
        TextureAnimations.I[0x48 ^ 0x4F] = I(";", "BIBoy");
        TextureAnimations.I[0xCB ^ 0xC3] = I("?", "HjVvx");
        TextureAnimations.I[0x72 ^ 0x7B] = I("!", "InWku");
        TextureAnimations.I[0x84 ^ 0x8E] = I("><\u000b\u00160\u0018<2\f,\u00078\u0007\u000b*\u0004cS1*\u001f+\u0010\u0007e\u001e<\u000b\u00160\u0018<S\f*\u001ey\u0015\r0\u0004=IB", "jYsbE");
        TextureAnimations.I[0x9 ^ 0x2] = I("!\u0002\u0016\r\u0005\u0007\u0002/\u0017\u0019\u0018\u0006\u001a\u0010\u001f\u001b]N-\u0011\u0007\u0000\u000b\rP\u0001\u0002\u0016\r\u0005\u0007\u0002N\u0017\u001f\u0001G\b\u0016\u0005\u001b\u0003TY", "ugnyp");
        TextureAnimations.I[0x70 ^ 0x7C] = I("\u001f\t<-\u00119\t\u00057\r&\r00\u000b%Vd\u0010\n=\r(0\u0000k\u000f+6\u0016/\u0005*8\u0010.\u001f", "KlDYd");
        TextureAnimations.I[0xB2 ^ 0xBF] = I("</\u001c\u00133\u001a/%\t/\u0005+\u0010\u000e)\u0006pD4)\u001d8\u0007\u0002f\u00078D\u0013'\u001a-\u0001\u0013f\u001c/\u001c\u00133\u001a/D\t)\u001cj\u0017\u0017#\u000b#\u0002\u000e#\f", "hJdgF");
        TextureAnimations.I[0x53 ^ 0x5D] = I("\t),*", "hGEGF");
        TextureAnimations.I[0x4C ^ 0x43] = I("'\u001b\n\u001b()1", "DnyoG");
        TextureAnimations.I[0x3F ^ 0x2F] = I("f5&\u0017\u0000-7 \u0011\u0015;", "HETxp");
        TextureAnimations.I[0xB6 ^ 0xA7] = I("\u0002\u00011 >$\u0001\b:\";\u0005==$8^i5%?\tf", "VdITK");
        TextureAnimations.I[0x58 ^ 0x4A] = I("j\u001b\u00171\u0003j", "EzyXn");
        TextureAnimations.I[0xB3 ^ 0xA0] = I("\u0005\n4\u000f-\u0017V*\u00037\u0001\u001a5\u000b?\u0010V*\t)\u0005\r$\u0002<\u0016V&\u00040\tV", "dyGjY");
        TextureAnimations.I[0x72 ^ 0x66] = I("\u0002\u001b\n=.\u0010G\u001414\u0006\u000b\u000b9<\u0017G\u0014;*\u0002\u001c\u001a0?\u0011G\u001863\u000eG\u001a-)\u0017\u0007\u0014\u0007", "chyXZ");
        TextureAnimations.I[0x63 ^ 0x76] = I("X\u0013\u001f=\u001c\u0013\u0011\u0019;\t\u0005", "vcmRl");
        TextureAnimations.I[0x29 ^ 0x3F] = I("\u00152\t\u0000\u0010\u0007n\u0017\f\n\u0011\"\b\u0004\u0002\u0000n", "tAzed");
        TextureAnimations.I[0x85 ^ 0x92] = I("f\u0019#:\u001c", "IxMSq");
    }
}
