package HORIZON-6-0-SKIDPROTECTION;

import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import java.awt.Image;
import java.awt.RenderingHints;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.io.InputStream;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Properties;
import java.util.Collection;
import java.util.Arrays;
import java.util.ArrayList;

public class TextureAnimations
{
    private static TextureAnimation[] HorizonCode_Horizon_È;
    
    static {
        TextureAnimations.HorizonCode_Horizon_È = null;
    }
    
    public static void HorizonCode_Horizon_È() {
        TextureAnimations.HorizonCode_Horizon_È = null;
    }
    
    public static void Â() {
        final IResourcePack[] rps = Config.áŒŠ();
        TextureAnimations.HorizonCode_Horizon_È = HorizonCode_Horizon_È(rps);
        Ø­áŒŠá();
    }
    
    public static void Ý() {
        if (TextureAnimations.HorizonCode_Horizon_È != null && Config.Ðƒá()) {
            Ø­áŒŠá();
        }
    }
    
    public static void Ø­áŒŠá() {
        if (TextureAnimations.HorizonCode_Horizon_È != null) {
            for (int i = 0; i < TextureAnimations.HorizonCode_Horizon_È.length; ++i) {
                final TextureAnimation anim = TextureAnimations.HorizonCode_Horizon_È[i];
                anim.Ø­áŒŠá();
            }
        }
    }
    
    public static TextureAnimation[] HorizonCode_Horizon_È(final IResourcePack[] rps) {
        final ArrayList list = new ArrayList();
        for (int anims = 0; anims < rps.length; ++anims) {
            final IResourcePack rp = rps[anims];
            final TextureAnimation[] tas = HorizonCode_Horizon_È(rp);
            if (tas != null) {
                list.addAll(Arrays.asList(tas));
            }
        }
        final TextureAnimation[] var5 = list.toArray(new TextureAnimation[list.size()]);
        return var5;
    }
    
    public static TextureAnimation[] HorizonCode_Horizon_È(final IResourcePack rp) {
        if (!(rp instanceof AbstractResourcePack)) {
            return null;
        }
        final AbstractResourcePack arp = (AbstractResourcePack)rp;
        final File tpFile = ResourceUtils.HorizonCode_Horizon_È(arp);
        if (tpFile == null) {
            return null;
        }
        if (!tpFile.exists()) {
            return null;
        }
        String[] animPropNames = null;
        if (tpFile.isFile()) {
            animPropNames = Â(tpFile);
        }
        else {
            animPropNames = HorizonCode_Horizon_È(tpFile);
        }
        if (animPropNames == null) {
            return null;
        }
        final ArrayList list = new ArrayList();
        for (int anims = 0; anims < animPropNames.length; ++anims) {
            final String propName = animPropNames[anims];
            Config.HorizonCode_Horizon_È("Texture animation: " + propName);
            try {
                final ResourceLocation_1975012498 e = new ResourceLocation_1975012498(propName);
                final InputStream in = rp.HorizonCode_Horizon_È(e);
                final Properties props = new Properties();
                props.load(in);
                final TextureAnimation anim = HorizonCode_Horizon_È(props, e);
                if (anim != null) {
                    final ResourceLocation_1975012498 locDstTex = new ResourceLocation_1975012498(anim.Ó());
                    if (Config.Âµá€(locDstTex) != rp) {
                        Config.HorizonCode_Horizon_È("Skipped: " + propName + ", target texture not loaded from same resource pack");
                    }
                    else {
                        list.add(anim);
                    }
                }
            }
            catch (FileNotFoundException var12) {
                Config.Â("File not found: " + var12.getMessage());
            }
            catch (IOException var13) {
                var13.printStackTrace();
            }
        }
        final TextureAnimation[] var14 = list.toArray(new TextureAnimation[list.size()]);
        return var14;
    }
    
    public static TextureAnimation HorizonCode_Horizon_È(final Properties props, final ResourceLocation_1975012498 propLoc) {
        String texFrom = props.getProperty("from");
        String texTo = props.getProperty("to");
        final int x = Config.HorizonCode_Horizon_È(props.getProperty("x"), -1);
        final int y = Config.HorizonCode_Horizon_È(props.getProperty("y"), -1);
        final int width = Config.HorizonCode_Horizon_È(props.getProperty("w"), -1);
        final int height = Config.HorizonCode_Horizon_È(props.getProperty("h"), -1);
        if (texFrom == null || texTo == null) {
            Config.Â("TextureAnimation: Source or target texture not specified");
            return null;
        }
        if (x < 0 || y < 0 || width < 0 || height < 0) {
            Config.Â("TextureAnimation: Invalid coordinates");
            return null;
        }
        final String basePath = TextureUtils.Â(propLoc.Â());
        texFrom = TextureUtils.HorizonCode_Horizon_È(texFrom, basePath);
        texTo = TextureUtils.HorizonCode_Horizon_È(texTo, basePath);
        final byte[] imageBytes = HorizonCode_Horizon_È(texFrom, width);
        if (imageBytes == null) {
            Config.Â("TextureAnimation: Source texture not found: " + texTo);
            return null;
        }
        final ResourceLocation_1975012498 locTexTo = new ResourceLocation_1975012498(texTo);
        if (!Config.Ý(locTexTo)) {
            Config.Â("TextureAnimation: Target texture not found: " + texTo);
            return null;
        }
        final ITextureObject destTex = TextureUtils.HorizonCode_Horizon_È(locTexTo);
        if (destTex == null) {
            Config.Â("TextureAnimation: Target texture not found: " + locTexTo);
            return null;
        }
        final int destTexId = destTex.HorizonCode_Horizon_È();
        final TextureAnimation anim = new TextureAnimation(texFrom, imageBytes, texTo, destTexId, x, y, width, height, props, 1);
        return anim;
    }
    
    public static String[] HorizonCode_Horizon_È(final File tpDir) {
        final File dirAnim = new File(tpDir, "anim");
        if (!dirAnim.exists()) {
            return null;
        }
        if (!dirAnim.isDirectory()) {
            return null;
        }
        final File[] propFiles = dirAnim.listFiles();
        if (propFiles == null) {
            return null;
        }
        final ArrayList list = new ArrayList();
        for (int props = 0; props < propFiles.length; ++props) {
            final File file = propFiles[props];
            final String name = file.getName();
            if (!name.startsWith("custom_") && name.endsWith(".properties") && file.isFile() && file.canRead()) {
                Config.HorizonCode_Horizon_È("TextureAnimation: anim/" + file.getName());
                list.add("/anim/" + name);
            }
        }
        final String[] var7 = list.toArray(new String[list.size()]);
        return var7;
    }
    
    public static String[] Â(final File tpFile) {
        try {
            final ZipFile e = new ZipFile(tpFile);
            final Enumeration en = e.entries();
            final ArrayList list = new ArrayList();
            while (en.hasMoreElements()) {
                final ZipEntry props = en.nextElement();
                String name = props.getName();
                if (name.startsWith("assets/minecraft/mcpatcher/anim/") && !name.startsWith("assets/minecraft/mcpatcher/anim/custom_") && name.endsWith(".properties")) {
                    final String assetsMcStr = "assets/minecraft/";
                    name = name.substring(assetsMcStr.length());
                    list.add(name);
                }
            }
            final String[] props2 = list.toArray(new String[list.size()]);
            return props2;
        }
        catch (IOException var7) {
            var7.printStackTrace();
            return null;
        }
    }
    
    public static byte[] HorizonCode_Horizon_È(final String imagePath, final int tileWidth) {
        byte[] imageBytes = Â(imagePath, tileWidth);
        if (imageBytes == null) {
            imageBytes = Â("/anim" + imagePath, tileWidth);
        }
        return imageBytes;
    }
    
    private static byte[] Â(final String name, final int targetWidth) {
        final GameSettings options = Config.ÇªØ­();
        try {
            final ResourceLocation_1975012498 e = new ResourceLocation_1975012498(name);
            final InputStream in = Config.HorizonCode_Horizon_È(e);
            if (in == null) {
                return null;
            }
            BufferedImage image = HorizonCode_Horizon_È(in);
            if (image == null) {
                return null;
            }
            if (targetWidth > 0 && image.getWidth() != targetWidth) {
                final double width = image.getHeight() / image.getWidth();
                final int ai = (int)(targetWidth * width);
                image = HorizonCode_Horizon_È(image, targetWidth, ai);
            }
            final int var20 = image.getWidth();
            final int height = image.getHeight();
            final int[] var21 = new int[var20 * height];
            final byte[] byteBuf = new byte[var20 * height * 4];
            image.getRGB(0, 0, var20, height, var21, 0, var20);
            for (int l = 0; l < var21.length; ++l) {
                final int alpha = var21[l] >> 24 & 0xFF;
                int red = var21[l] >> 16 & 0xFF;
                int green = var21[l] >> 8 & 0xFF;
                int blue = var21[l] & 0xFF;
                if (options != null && options.Âµá€) {
                    final int j3 = (red * 30 + green * 59 + blue * 11) / 100;
                    final int l2 = (red * 30 + green * 70) / 100;
                    final int j4 = (red * 30 + blue * 70) / 100;
                    red = j3;
                    green = l2;
                    blue = j4;
                }
                byteBuf[l * 4 + 0] = (byte)red;
                byteBuf[l * 4 + 1] = (byte)green;
                byteBuf[l * 4 + 2] = (byte)blue;
                byteBuf[l * 4 + 3] = (byte)alpha;
            }
            return byteBuf;
        }
        catch (FileNotFoundException var23) {
            return null;
        }
        catch (Exception var22) {
            var22.printStackTrace();
            return null;
        }
    }
    
    private static BufferedImage HorizonCode_Horizon_È(final InputStream par1InputStream) throws IOException {
        final BufferedImage var2 = ImageIO.read(par1InputStream);
        par1InputStream.close();
        return var2;
    }
    
    public static BufferedImage HorizonCode_Horizon_È(final BufferedImage image, final int width, final int height) {
        final BufferedImage scaledImage = new BufferedImage(width, height, 2);
        final Graphics2D gr = scaledImage.createGraphics();
        gr.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        gr.drawImage(image, 0, 0, width, height, null);
        return scaledImage;
    }
}
