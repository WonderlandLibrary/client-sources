/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.util;

import com.mojang.blaze3d.platform.GlStateManager;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import net.minecraft.client.renderer.entity.layers.MooshroomMushroomLayer;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.MissingTextureSprite;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.Texture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.ReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourceManagerReloadListener;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.optifine.BetterGrass;
import net.optifine.BetterSnow;
import net.optifine.Config;
import net.optifine.ConnectedTextures;
import net.optifine.CustomBlockLayers;
import net.optifine.CustomColors;
import net.optifine.CustomGuis;
import net.optifine.CustomItems;
import net.optifine.CustomLoadingScreens;
import net.optifine.CustomPanorama;
import net.optifine.CustomSky;
import net.optifine.EmissiveTextures;
import net.optifine.Lang;
import net.optifine.NaturalTextures;
import net.optifine.RandomEntities;
import net.optifine.SmartLeaves;
import net.optifine.TextureAnimations;
import net.optifine.entity.model.CustomEntityModels;
import net.optifine.reflect.ReflectorForge;
import net.optifine.shaders.MultiTexID;
import net.optifine.shaders.Shaders;
import net.optifine.util.StrUtils;
import net.optifine.util.TickableTexture;
import org.apache.commons.io.IOUtils;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class TextureUtils {
    private static final String texGrassTop = "grass_block_top";
    private static final String texGrassSide = "grass_block_side";
    private static final String texGrassSideOverlay = "grass_block_side_overlay";
    private static final String texSnow = "snow";
    private static final String texGrassSideSnowed = "grass_block_snow";
    private static final String texMyceliumSide = "mycelium_side";
    private static final String texMyceliumTop = "mycelium_top";
    private static final String texWaterStill = "water_still";
    private static final String texWaterFlow = "water_flow";
    private static final String texLavaStill = "lava_still";
    private static final String texLavaFlow = "lava_flow";
    private static final String texFireLayer0 = "fire_0";
    private static final String texFireLayer1 = "fire_1";
    private static final String texSoulFireLayer0 = "soul_fire_0";
    private static final String texSoulFireLayer1 = "soul_fire_1";
    private static final String texCampFire = "campfire_fire";
    private static final String texCampFireLogLit = "campfire_log_lit";
    private static final String texSoulCampFire = "soul_campfire_fire";
    private static final String texSoulCampFireLogLit = "soul_campfire_log_lit";
    private static final String texPortal = "nether_portal";
    private static final String texGlass = "glass";
    private static final String texGlassPaneTop = "glass_pane_top";
    public static TextureAtlasSprite iconGrassTop;
    public static TextureAtlasSprite iconGrassSide;
    public static TextureAtlasSprite iconGrassSideOverlay;
    public static TextureAtlasSprite iconSnow;
    public static TextureAtlasSprite iconGrassSideSnowed;
    public static TextureAtlasSprite iconMyceliumSide;
    public static TextureAtlasSprite iconMyceliumTop;
    public static TextureAtlasSprite iconWaterStill;
    public static TextureAtlasSprite iconWaterFlow;
    public static TextureAtlasSprite iconLavaStill;
    public static TextureAtlasSprite iconLavaFlow;
    public static TextureAtlasSprite iconFireLayer0;
    public static TextureAtlasSprite iconFireLayer1;
    public static TextureAtlasSprite iconSoulFireLayer0;
    public static TextureAtlasSprite iconSoulFireLayer1;
    public static TextureAtlasSprite iconCampFire;
    public static TextureAtlasSprite iconCampFireLogLit;
    public static TextureAtlasSprite iconSoulCampFire;
    public static TextureAtlasSprite iconSoulCampFireLogLit;
    public static TextureAtlasSprite iconPortal;
    public static TextureAtlasSprite iconGlass;
    public static TextureAtlasSprite iconGlassPaneTop;
    public static final String SPRITE_PREFIX_BLOCKS = "minecraft:block/";
    public static final String SPRITE_PREFIX_ITEMS = "minecraft:item/";
    public static final ResourceLocation LOCATION_SPRITE_EMPTY;
    public static final ResourceLocation LOCATION_TEXTURE_EMPTY;
    private static IntBuffer staticBuffer;
    private static int glMaximumTextureSize;
    private static Map<Integer, String> mapTextureAllocations;

    public static void update() {
        AtlasTexture atlasTexture = TextureUtils.getTextureMapBlocks();
        if (atlasTexture != null) {
            String string = SPRITE_PREFIX_BLOCKS;
            iconGrassTop = TextureUtils.getSpriteCheck(atlasTexture, string + texGrassTop);
            iconGrassSide = TextureUtils.getSpriteCheck(atlasTexture, string + texGrassSide);
            iconGrassSideOverlay = TextureUtils.getSpriteCheck(atlasTexture, string + texGrassSideOverlay);
            iconSnow = TextureUtils.getSpriteCheck(atlasTexture, string + texSnow);
            iconGrassSideSnowed = TextureUtils.getSpriteCheck(atlasTexture, string + texGrassSideSnowed);
            iconMyceliumSide = TextureUtils.getSpriteCheck(atlasTexture, string + texMyceliumSide);
            iconMyceliumTop = TextureUtils.getSpriteCheck(atlasTexture, string + texMyceliumTop);
            iconWaterStill = TextureUtils.getSpriteCheck(atlasTexture, string + texWaterStill);
            iconWaterFlow = TextureUtils.getSpriteCheck(atlasTexture, string + texWaterFlow);
            iconLavaStill = TextureUtils.getSpriteCheck(atlasTexture, string + texLavaStill);
            iconLavaFlow = TextureUtils.getSpriteCheck(atlasTexture, string + texLavaFlow);
            iconFireLayer0 = TextureUtils.getSpriteCheck(atlasTexture, string + texFireLayer0);
            iconFireLayer1 = TextureUtils.getSpriteCheck(atlasTexture, string + texFireLayer1);
            iconSoulFireLayer0 = TextureUtils.getSpriteCheck(atlasTexture, string + texSoulFireLayer0);
            iconSoulFireLayer1 = TextureUtils.getSpriteCheck(atlasTexture, string + texSoulFireLayer1);
            iconCampFire = TextureUtils.getSpriteCheck(atlasTexture, string + texCampFire);
            iconCampFireLogLit = TextureUtils.getSpriteCheck(atlasTexture, string + texCampFireLogLit);
            iconSoulCampFire = TextureUtils.getSpriteCheck(atlasTexture, string + texSoulCampFire);
            iconSoulCampFireLogLit = TextureUtils.getSpriteCheck(atlasTexture, string + texSoulCampFireLogLit);
            iconPortal = TextureUtils.getSpriteCheck(atlasTexture, string + texPortal);
            iconGlass = TextureUtils.getSpriteCheck(atlasTexture, string + texGlass);
            iconGlassPaneTop = TextureUtils.getSpriteCheck(atlasTexture, string + texGlassPaneTop);
            String string2 = SPRITE_PREFIX_ITEMS;
        }
    }

    public static TextureAtlasSprite getSpriteCheck(AtlasTexture atlasTexture, String string) {
        TextureAtlasSprite textureAtlasSprite = atlasTexture.getUploadedSprite(string);
        if (textureAtlasSprite == null || textureAtlasSprite instanceof MissingTextureSprite) {
            Config.warn("Sprite not found: " + string);
        }
        return textureAtlasSprite;
    }

    public static BufferedImage fixTextureDimensions(String string, BufferedImage bufferedImage) {
        int n;
        int n2;
        if ((string.startsWith("/mob/zombie") || string.startsWith("/mob/pigzombie")) && (n2 = bufferedImage.getWidth()) == (n = bufferedImage.getHeight()) * 2) {
            BufferedImage bufferedImage2 = new BufferedImage(n2, n * 2, 2);
            Graphics2D graphics2D = bufferedImage2.createGraphics();
            graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            graphics2D.drawImage(bufferedImage, 0, 0, n2, n, null);
            return bufferedImage2;
        }
        return bufferedImage;
    }

    public static int ceilPowerOfTwo(int n) {
        int n2;
        for (n2 = 1; n2 < n; n2 *= 2) {
        }
        return n2;
    }

    public static int getPowerOfTwo(int n) {
        int n2 = 1;
        int n3 = 0;
        while (n2 < n) {
            n2 *= 2;
            ++n3;
        }
        return n3;
    }

    public static int twoToPower(int n) {
        int n2 = 1;
        for (int i = 0; i < n; ++i) {
            n2 *= 2;
        }
        return n2;
    }

    public static Texture getTexture(ResourceLocation resourceLocation) {
        Texture texture = Config.getTextureManager().getTexture(resourceLocation);
        if (texture != null) {
            return texture;
        }
        if (!Config.hasResource(resourceLocation)) {
            return null;
        }
        texture = new SimpleTexture(resourceLocation);
        Config.getTextureManager().loadTexture(resourceLocation, texture);
        return texture;
    }

    public static void resourcesReloaded(IResourceManager iResourceManager) {
        if (TextureUtils.getTextureMapBlocks() != null) {
            Config.dbg("*** Reloading custom textures ***");
            CustomSky.reset();
            TextureAnimations.reset();
            TextureUtils.update();
            NaturalTextures.update();
            BetterGrass.update();
            BetterSnow.update();
            TextureAnimations.update();
            CustomColors.update();
            CustomSky.update();
            RandomEntities.update();
            CustomItems.updateModels();
            CustomEntityModels.update();
            Shaders.resourcesReloaded();
            Lang.resourcesReloaded();
            Config.updateTexturePackClouds();
            SmartLeaves.updateLeavesModels();
            CustomPanorama.update();
            CustomGuis.update();
            MooshroomMushroomLayer.update();
            CustomLoadingScreens.update();
            CustomBlockLayers.update();
            Config.getTextureManager().tick();
            Config.dbg("Disable Forge light pipeline");
            ReflectorForge.setForgeLightPipelineEnabled(false);
        }
    }

    public static AtlasTexture getTextureMapBlocks() {
        return Config.getTextureMap();
    }

    public static void registerResourceListener() {
        IResourceManager iResourceManager = Config.getResourceManager();
        if (iResourceManager instanceof IReloadableResourceManager) {
            IReloadableResourceManager iReloadableResourceManager = (IReloadableResourceManager)iResourceManager;
            ReloadListener reloadListener = new ReloadListener(){

                protected Object prepare(IResourceManager iResourceManager, IProfiler iProfiler) {
                    return null;
                }

                protected void apply(Object object, IResourceManager iResourceManager, IProfiler iProfiler) {
                }
            };
            iReloadableResourceManager.addReloadListener(reloadListener);
            IResourceManagerReloadListener iResourceManagerReloadListener = new IResourceManagerReloadListener(){

                @Override
                public void onResourceManagerReload(IResourceManager iResourceManager) {
                    TextureUtils.resourcesReloaded(iResourceManager);
                }
            };
            iReloadableResourceManager.addReloadListener(iResourceManagerReloadListener);
        }
    }

    public static void registerTickableTextures() {
        TickableTexture tickableTexture = new TickableTexture(){

            @Override
            public void tick() {
                TextureAnimations.updateAnimations();
            }

            @Override
            public void loadTexture(IResourceManager iResourceManager) throws IOException {
            }

            @Override
            public int getGlTextureId() {
                return 1;
            }

            @Override
            public void restoreLastBlurMipmap() {
            }

            @Override
            public MultiTexID getMultiTexID() {
                return null;
            }
        };
        ResourceLocation resourceLocation = new ResourceLocation("optifine/tickable_textures");
        Config.getTextureManager().loadTexture(resourceLocation, tickableTexture);
    }

    public static void registerCustomModels(ModelBakery modelBakery) {
        CustomItems.update();
        CustomItems.loadModels(modelBakery);
    }

    public static void registerCustomSprites(AtlasTexture atlasTexture) {
        if (atlasTexture.getTextureLocation().equals(AtlasTexture.LOCATION_BLOCKS_TEXTURE)) {
            ConnectedTextures.updateIcons(atlasTexture);
            CustomItems.updateIcons(atlasTexture);
            BetterGrass.updateIcons(atlasTexture);
        }
    }

    public static void refreshCustomSprites(AtlasTexture atlasTexture) {
        if (atlasTexture.getTextureLocation().equals(AtlasTexture.LOCATION_BLOCKS_TEXTURE)) {
            ConnectedTextures.refreshIcons(atlasTexture);
            CustomItems.refreshIcons(atlasTexture);
            BetterGrass.refreshIcons(atlasTexture);
        }
        EmissiveTextures.refreshIcons(atlasTexture);
    }

    public static ResourceLocation fixResourceLocation(ResourceLocation resourceLocation, String string) {
        if (!resourceLocation.getNamespace().equals("minecraft")) {
            return resourceLocation;
        }
        String string2 = resourceLocation.getPath();
        String string3 = TextureUtils.fixResourcePath(string2, string);
        if (string3 != string2) {
            resourceLocation = new ResourceLocation(resourceLocation.getNamespace(), string3);
        }
        return resourceLocation;
    }

    public static String fixResourcePath(String string, String object) {
        String string2 = "assets/minecraft/";
        if (string.startsWith(string2)) {
            return string.substring(string2.length());
        }
        if (string.startsWith("./")) {
            string = string.substring(2);
            if (!((String)object).endsWith("/")) {
                object = (String)object + "/";
            }
            return (String)object + string;
        }
        if (string.startsWith("/~")) {
            string = string.substring(1);
        }
        String string3 = "optifine/";
        if (string.startsWith("~/")) {
            string = string.substring(2);
            return string3 + string;
        }
        return string.startsWith("/") ? string3 + string.substring(1) : string;
    }

    public static String getBasePath(String string) {
        int n = string.lastIndexOf(47);
        return n < 0 ? "" : string.substring(0, n);
    }

    public static void applyAnisotropicLevel() {
        if (GL.getCapabilities().GL_EXT_texture_filter_anisotropic) {
            float f = GL11.glGetFloat(34047);
            float f2 = Config.getAnisotropicFilterLevel();
            f2 = Math.min(f2, f);
            GL11.glTexParameterf(3553, 34046, f2);
        }
    }

    public static void bindTexture(int n) {
        GlStateManager.bindTexture(n);
    }

    public static boolean isPowerOfTwo(int n) {
        int n2 = MathHelper.smallestEncompassingPowerOfTwo(n);
        return n2 == n;
    }

    public static NativeImage scaleImage(NativeImage nativeImage, int n) {
        BufferedImage bufferedImage = TextureUtils.toBufferedImage(nativeImage);
        BufferedImage bufferedImage2 = TextureUtils.scaleImage(bufferedImage, n);
        return TextureUtils.toNativeImage(bufferedImage2);
    }

    public static BufferedImage toBufferedImage(NativeImage nativeImage) {
        int n = nativeImage.getWidth();
        int n2 = nativeImage.getHeight();
        int[] nArray = new int[n * n2];
        nativeImage.getBufferRGBA().get(nArray);
        BufferedImage bufferedImage = new BufferedImage(n, n2, 2);
        bufferedImage.setRGB(0, 0, n, n2, nArray, 0, n);
        return bufferedImage;
    }

    public static NativeImage toNativeImage(BufferedImage bufferedImage) {
        int n = bufferedImage.getWidth();
        int n2 = bufferedImage.getHeight();
        int[] nArray = new int[n * n2];
        bufferedImage.getRGB(0, 0, n, n2, nArray, 0, n);
        NativeImage nativeImage = new NativeImage(n, n2, false);
        nativeImage.getBufferRGBA().put(nArray);
        return nativeImage;
    }

    public static BufferedImage scaleImage(BufferedImage bufferedImage, int n) {
        int n2 = bufferedImage.getWidth();
        int n3 = bufferedImage.getHeight();
        int n4 = n3 * n / n2;
        BufferedImage bufferedImage2 = new BufferedImage(n, n4, 2);
        Graphics2D graphics2D = bufferedImage2.createGraphics();
        Object object = RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR;
        if (n < n2 || n % n2 != 0) {
            object = RenderingHints.VALUE_INTERPOLATION_BILINEAR;
        }
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, object);
        graphics2D.drawImage(bufferedImage, 0, 0, n, n4, null);
        return bufferedImage2;
    }

    public static int scaleToGrid(int n, int n2) {
        int n3;
        if (n == n2) {
            return n;
        }
        for (n3 = n / n2 * n2; n3 < n; n3 += n2) {
        }
        return n3;
    }

    public static int scaleToMin(int n, int n2) {
        int n3;
        if (n >= n2) {
            return n;
        }
        for (n3 = n2 / n * n; n3 < n2; n3 += n) {
        }
        return n3;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Dimension getImageSize(InputStream inputStream, String string) {
        Iterator<ImageReader> iterator2 = ImageIO.getImageReadersBySuffix(string);
        while (iterator2.hasNext()) {
            Dimension dimension;
            ImageReader imageReader = iterator2.next();
            try {
                ImageInputStream imageInputStream = ImageIO.createImageInputStream(inputStream);
                imageReader.setInput(imageInputStream);
                int n = imageReader.getWidth(imageReader.getMinIndex());
                int n2 = imageReader.getHeight(imageReader.getMinIndex());
                dimension = new Dimension(n, n2);
            } catch (IOException iOException) {}
            continue;
            finally {
                imageReader.dispose();
                continue;
            }
            return dimension;
        }
        return null;
    }

    public static void dbgMipmaps(TextureAtlasSprite textureAtlasSprite) {
        NativeImage[] nativeImageArray = textureAtlasSprite.getMipmapImages();
        for (int i = 0; i < nativeImageArray.length; ++i) {
            NativeImage nativeImage = nativeImageArray[i];
            if (nativeImage == null) {
                Config.dbg(i + ": " + nativeImage);
                continue;
            }
            Config.dbg(i + ": " + nativeImage.getWidth() * nativeImage.getHeight());
        }
    }

    public static void saveGlTexture(String string, int n, int n2, int n3, int n4) {
        Object object;
        int n5;
        TextureUtils.bindTexture(n);
        GL11.glPixelStorei(3333, 1);
        GL11.glPixelStorei(3317, 1);
        string = StrUtils.removeSuffix(string, ".png");
        File file = new File(string);
        File file2 = file.getParentFile();
        if (file2 != null) {
            file2.mkdirs();
        }
        for (n5 = 0; n5 < 16; ++n5) {
            object = string + "_" + n5 + ".png";
            File file3 = new File((String)object);
            file3.delete();
        }
        for (n5 = 0; n5 <= n2; ++n5) {
            object = new File(string + "_" + n5 + ".png");
            int n6 = n3 >> n5;
            int n7 = n4 >> n5;
            int n8 = n6 * n7;
            IntBuffer intBuffer = BufferUtils.createIntBuffer(n8);
            int[] nArray = new int[n8];
            GL11.glGetTexImage(3553, n5, 32993, 33639, intBuffer);
            intBuffer.get(nArray);
            BufferedImage bufferedImage = new BufferedImage(n6, n7, 2);
            bufferedImage.setRGB(0, 0, n6, n7, nArray, 0, n6);
            try {
                ImageIO.write((RenderedImage)bufferedImage, "png", (File)object);
                Config.dbg("Exported: " + (File)object);
                continue;
            } catch (Exception exception) {
                Config.warn("Error writing: " + (File)object);
                Config.warn(exception.getClass().getName() + ": " + exception.getMessage());
            }
        }
    }

    public static int getGLMaximumTextureSize() {
        if (glMaximumTextureSize < 0) {
            glMaximumTextureSize = TextureUtils.detectGLMaximumTextureSize();
        }
        return glMaximumTextureSize;
    }

    private static int detectGLMaximumTextureSize() {
        for (int i = 65536; i > 0; i >>= 1) {
            GlStateManager.texImage2D(32868, 0, 6408, i, i, 0, 6408, 5121, null);
            int n = GL11.glGetError();
            int n2 = GlStateManager.getTexLevelParameter(32868, 0, 4096);
            if (n2 == 0) continue;
            return i;
        }
        return 1;
    }

    public static BufferedImage readBufferedImage(InputStream inputStream) throws IOException {
        BufferedImage bufferedImage;
        if (inputStream == null) {
            return null;
        }
        try {
            BufferedImage bufferedImage2;
            bufferedImage = bufferedImage2 = ImageIO.read(inputStream);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        return bufferedImage;
    }

    public static int toAbgr(int n) {
        int n2 = n >> 24 & 0xFF;
        int n3 = n >> 16 & 0xFF;
        int n4 = n >> 8 & 0xFF;
        int n5 = n >> 0 & 0xFF;
        return n2 << 24 | n5 << 16 | n4 << 8 | n3;
    }

    public static void resetDataUnpacking() {
        GlStateManager.pixelStore(3314, 0);
        GlStateManager.pixelStore(3316, 0);
        GlStateManager.pixelStore(3315, 0);
        GlStateManager.pixelStore(3317, 4);
    }

    public static String getStackTrace(Throwable throwable) {
        CharArrayWriter charArrayWriter = new CharArrayWriter();
        throwable.printStackTrace(new PrintWriter(charArrayWriter));
        return charArrayWriter.toString();
    }

    public static void debugTextureGenerated(int n) {
        mapTextureAllocations.put(n, TextureUtils.getStackTrace(new Throwable("StackTrace")));
        Config.dbg("Textures: " + mapTextureAllocations.size());
    }

    public static void debugTextureDeleted(int n) {
        mapTextureAllocations.remove(n);
        Config.dbg("Textures: " + mapTextureAllocations.size());
    }

    static {
        LOCATION_SPRITE_EMPTY = new ResourceLocation("optifine/ctm/default/empty");
        LOCATION_TEXTURE_EMPTY = new ResourceLocation("optifine/ctm/default/empty.png");
        staticBuffer = Config.createDirectIntBuffer(256);
        glMaximumTextureSize = -1;
        mapTextureAllocations = new HashMap<Integer, String>();
    }
}

