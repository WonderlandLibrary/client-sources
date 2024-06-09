/*
 * Decompiled with CFR 0.145.
 */
package optifine;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.io.InputStream;
import java.nio.IntBuffer;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.ITickableTextureObject;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import optifine.BetterGrass;
import optifine.BetterSnow;
import optifine.Config;
import optifine.CustomColors;
import optifine.CustomItems;
import optifine.CustomSky;
import optifine.Lang;
import optifine.NaturalTextures;
import optifine.RandomMobs;
import optifine.SmartLeaves;
import optifine.TextureAnimations;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import shadersmod.client.MultiTexID;
import shadersmod.client.Shaders;

public class TextureUtils {
    public static final String texGrassTop = "grass_top";
    public static final String texStone = "stone";
    public static final String texDirt = "dirt";
    public static final String texCoarseDirt = "coarse_dirt";
    public static final String texGrassSide = "grass_side";
    public static final String texStoneslabSide = "stone_slab_side";
    public static final String texStoneslabTop = "stone_slab_top";
    public static final String texBedrock = "bedrock";
    public static final String texSand = "sand";
    public static final String texGravel = "gravel";
    public static final String texLogOak = "log_oak";
    public static final String texLogBigOak = "log_big_oak";
    public static final String texLogAcacia = "log_acacia";
    public static final String texLogSpruce = "log_spruce";
    public static final String texLogBirch = "log_birch";
    public static final String texLogJungle = "log_jungle";
    public static final String texLogOakTop = "log_oak_top";
    public static final String texLogBigOakTop = "log_big_oak_top";
    public static final String texLogAcaciaTop = "log_acacia_top";
    public static final String texLogSpruceTop = "log_spruce_top";
    public static final String texLogBirchTop = "log_birch_top";
    public static final String texLogJungleTop = "log_jungle_top";
    public static final String texLeavesOak = "leaves_oak";
    public static final String texLeavesBigOak = "leaves_big_oak";
    public static final String texLeavesAcacia = "leaves_acacia";
    public static final String texLeavesBirch = "leaves_birch";
    public static final String texLeavesSpuce = "leaves_spruce";
    public static final String texLeavesJungle = "leaves_jungle";
    public static final String texGoldOre = "gold_ore";
    public static final String texIronOre = "iron_ore";
    public static final String texCoalOre = "coal_ore";
    public static final String texObsidian = "obsidian";
    public static final String texGrassSideOverlay = "grass_side_overlay";
    public static final String texSnow = "snow";
    public static final String texGrassSideSnowed = "grass_side_snowed";
    public static final String texMyceliumSide = "mycelium_side";
    public static final String texMyceliumTop = "mycelium_top";
    public static final String texDiamondOre = "diamond_ore";
    public static final String texRedstoneOre = "redstone_ore";
    public static final String texLapisOre = "lapis_ore";
    public static final String texCactusSide = "cactus_side";
    public static final String texClay = "clay";
    public static final String texFarmlandWet = "farmland_wet";
    public static final String texFarmlandDry = "farmland_dry";
    public static final String texNetherrack = "netherrack";
    public static final String texSoulSand = "soul_sand";
    public static final String texGlowstone = "glowstone";
    public static final String texLeavesSpruce = "leaves_spruce";
    public static final String texLeavesSpruceOpaque = "leaves_spruce_opaque";
    public static final String texEndStone = "end_stone";
    public static final String texSandstoneTop = "sandstone_top";
    public static final String texSandstoneBottom = "sandstone_bottom";
    public static final String texRedstoneLampOff = "redstone_lamp_off";
    public static final String texRedstoneLampOn = "redstone_lamp_on";
    public static final String texWaterStill = "water_still";
    public static final String texWaterFlow = "water_flow";
    public static final String texLavaStill = "lava_still";
    public static final String texLavaFlow = "lava_flow";
    public static final String texFireLayer0 = "fire_layer_0";
    public static final String texFireLayer1 = "fire_layer_1";
    public static final String texPortal = "portal";
    public static final String texGlass = "glass";
    public static final String texGlassPaneTop = "glass_pane_top";
    public static final String texCompass = "compass";
    public static final String texClock = "clock";
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
    public static TextureAtlasSprite iconPortal;
    public static TextureAtlasSprite iconFireLayer0;
    public static TextureAtlasSprite iconFireLayer1;
    public static TextureAtlasSprite iconGlass;
    public static TextureAtlasSprite iconGlassPaneTop;
    public static TextureAtlasSprite iconCompass;
    public static TextureAtlasSprite iconClock;
    public static final String SPRITE_PREFIX_BLOCKS = "minecraft:blocks/";
    public static final String SPRITE_PREFIX_ITEMS = "minecraft:items/";
    private static IntBuffer staticBuffer;

    static {
        staticBuffer = GLAllocation.createDirectIntBuffer(256);
    }

    public static void update() {
        TextureMap mapBlocks = TextureUtils.getTextureMapBlocks();
        if (mapBlocks != null) {
            String prefix = SPRITE_PREFIX_BLOCKS;
            iconGrassTop = mapBlocks.getSpriteSafe(String.valueOf(prefix) + texGrassTop);
            iconGrassSide = mapBlocks.getSpriteSafe(String.valueOf(prefix) + texGrassSide);
            iconGrassSideOverlay = mapBlocks.getSpriteSafe(String.valueOf(prefix) + texGrassSideOverlay);
            iconSnow = mapBlocks.getSpriteSafe(String.valueOf(prefix) + texSnow);
            iconGrassSideSnowed = mapBlocks.getSpriteSafe(String.valueOf(prefix) + texGrassSideSnowed);
            iconMyceliumSide = mapBlocks.getSpriteSafe(String.valueOf(prefix) + texMyceliumSide);
            iconMyceliumTop = mapBlocks.getSpriteSafe(String.valueOf(prefix) + texMyceliumTop);
            iconWaterStill = mapBlocks.getSpriteSafe(String.valueOf(prefix) + texWaterStill);
            iconWaterFlow = mapBlocks.getSpriteSafe(String.valueOf(prefix) + texWaterFlow);
            iconLavaStill = mapBlocks.getSpriteSafe(String.valueOf(prefix) + texLavaStill);
            iconLavaFlow = mapBlocks.getSpriteSafe(String.valueOf(prefix) + texLavaFlow);
            iconFireLayer0 = mapBlocks.getSpriteSafe(String.valueOf(prefix) + texFireLayer0);
            iconFireLayer1 = mapBlocks.getSpriteSafe(String.valueOf(prefix) + texFireLayer1);
            iconPortal = mapBlocks.getSpriteSafe(String.valueOf(prefix) + texPortal);
            iconGlass = mapBlocks.getSpriteSafe(String.valueOf(prefix) + texGlass);
            iconGlassPaneTop = mapBlocks.getSpriteSafe(String.valueOf(prefix) + texGlassPaneTop);
            String prefixItems = SPRITE_PREFIX_ITEMS;
            iconCompass = mapBlocks.getSpriteSafe(String.valueOf(prefixItems) + texCompass);
            iconClock = mapBlocks.getSpriteSafe(String.valueOf(prefixItems) + texClock);
        }
    }

    public static BufferedImage fixTextureDimensions(String name, BufferedImage bi2) {
        int height;
        int width;
        if ((name.startsWith("/mob/zombie") || name.startsWith("/mob/pigzombie")) && (width = bi2.getWidth()) == (height = bi2.getHeight()) * 2) {
            BufferedImage scaledImage = new BufferedImage(width, height * 2, 2);
            Graphics2D gr2 = scaledImage.createGraphics();
            gr2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            gr2.drawImage(bi2, 0, 0, width, height, null);
            return scaledImage;
        }
        return bi2;
    }

    public static int ceilPowerOfTwo(int val) {
        int i2;
        for (i2 = 1; i2 < val; i2 *= 2) {
        }
        return i2;
    }

    public static int getPowerOfTwo(int val) {
        int i2 = 1;
        int po2 = 0;
        while (i2 < val) {
            i2 *= 2;
            ++po2;
        }
        return po2;
    }

    public static int twoToPower(int power) {
        int val = 1;
        for (int i2 = 0; i2 < power; ++i2) {
            val *= 2;
        }
        return val;
    }

    public static ITextureObject getTexture(ResourceLocation loc) {
        ITextureObject tex = Config.getTextureManager().getTexture(loc);
        if (tex != null) {
            return tex;
        }
        if (!Config.hasResource(loc)) {
            return null;
        }
        SimpleTexture tex1 = new SimpleTexture(loc);
        Config.getTextureManager().loadTexture(loc, tex1);
        return tex1;
    }

    public static void resourcesReloaded(IResourceManager rm2) {
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
            RandomMobs.resetTextures();
            CustomItems.updateModels();
            Shaders.resourcesReloaded();
            Lang.resourcesReloaded();
            Config.updateTexturePackClouds();
            SmartLeaves.updateLeavesModels();
            Config.getTextureManager().tick();
        }
    }

    public static TextureMap getTextureMapBlocks() {
        return Minecraft.getMinecraft().getTextureMapBlocks();
    }

    public static void registerResourceListener() {
        IResourceManager rm2 = Config.getResourceManager();
        if (rm2 instanceof IReloadableResourceManager) {
            IReloadableResourceManager tto = (IReloadableResourceManager)rm2;
            IResourceManagerReloadListener ttol = new IResourceManagerReloadListener(){

                @Override
                public void onResourceManagerReload(IResourceManager var1) {
                    TextureUtils.resourcesReloaded(var1);
                }
            };
            tto.registerReloadListener(ttol);
        }
        ITickableTextureObject tto1 = new ITickableTextureObject(){

            @Override
            public void tick() {
                TextureAnimations.updateCustomAnimations();
            }

            @Override
            public void loadTexture(IResourceManager var1) throws IOException {
            }

            @Override
            public int getGlTextureId() {
                return 0;
            }

            @Override
            public void func_174936_b(boolean p_174936_1, boolean p_174936_2) {
            }

            @Override
            public void func_174935_a() {
            }

            @Override
            public MultiTexID getMultiTexID() {
                return null;
            }
        };
        ResourceLocation ttol1 = new ResourceLocation("optifine/TickableTextures");
        Config.getTextureManager().loadTickableTexture(ttol1, tto1);
    }

    public static String fixResourcePath(String path, String basePath) {
        String strAssMc = "assets/minecraft/";
        if (path.startsWith(strAssMc)) {
            path = path.substring(strAssMc.length());
            return path;
        }
        if (path.startsWith("./")) {
            path = path.substring(2);
            if (!basePath.endsWith("/")) {
                basePath = String.valueOf(basePath) + "/";
            }
            path = String.valueOf(basePath) + path;
            return path;
        }
        if (path.startsWith("/~")) {
            path = path.substring(1);
        }
        String strMcpatcher = "mcpatcher/";
        if (path.startsWith("~/")) {
            path = path.substring(2);
            path = String.valueOf(strMcpatcher) + path;
            return path;
        }
        if (path.startsWith("/")) {
            path = String.valueOf(strMcpatcher) + path.substring(1);
            return path;
        }
        return path;
    }

    public static String getBasePath(String path) {
        int pos = path.lastIndexOf(47);
        return pos < 0 ? "" : path.substring(0, pos);
    }

    public static void applyAnisotropicLevel() {
        if (GLContext.getCapabilities().GL_EXT_texture_filter_anisotropic) {
            float maxLevel = GL11.glGetFloat(34047);
            float level = Config.getAnisotropicFilterLevel();
            level = Math.min(level, maxLevel);
            GL11.glTexParameterf(3553, 34046, level);
        }
    }

    public static void bindTexture(int glTexId) {
        GlStateManager.func_179144_i(glTexId);
    }

    public static boolean isPowerOfTwo(int x2) {
        int x22 = MathHelper.roundUpToPowerOfTwo(x2);
        return x22 == x2;
    }

    public static BufferedImage scaleToPowerOfTwo(BufferedImage bi2, int minSize) {
        if (bi2 == null) {
            return bi2;
        }
        int w2 = bi2.getWidth();
        int h2 = bi2.getHeight();
        int w22 = Math.max(w2, minSize);
        if ((w22 = MathHelper.roundUpToPowerOfTwo(w22)) == w2) {
            return bi2;
        }
        int h22 = h2 * w22 / w2;
        BufferedImage bi22 = new BufferedImage(w22, h22, 2);
        Graphics2D g2 = bi22.createGraphics();
        Object method = RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR;
        if (w22 % w2 != 0) {
            method = RenderingHints.VALUE_INTERPOLATION_BILINEAR;
        }
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, method);
        g2.drawImage(bi2, 0, 0, w22, h22, null);
        return bi22;
    }

    public static BufferedImage scaleMinTo(BufferedImage bi2, int minSize) {
        int w2;
        if (bi2 == null) {
            return bi2;
        }
        int w3 = bi2.getWidth();
        int h2 = bi2.getHeight();
        if (w3 >= minSize) {
            return bi2;
        }
        for (w2 = w3; w2 < minSize; w2 *= 2) {
        }
        int h22 = h2 * w2 / w3;
        BufferedImage bi22 = new BufferedImage(w2, h22, 2);
        Graphics2D g2 = bi22.createGraphics();
        Object method = RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR;
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, method);
        g2.drawImage(bi2, 0, 0, w2, h22, null);
        return bi22;
    }

    public static Dimension getImageSize(InputStream in2, String suffix) {
        Iterator<ImageReader> iter = ImageIO.getImageReadersBySuffix(suffix);
        while (iter.hasNext()) {
            Dimension var7;
            ImageReader reader = iter.next();
            try {
                try {
                    ImageInputStream e2 = ImageIO.createImageInputStream(in2);
                    reader.setInput(e2);
                    int width = reader.getWidth(reader.getMinIndex());
                    int height = reader.getHeight(reader.getMinIndex());
                    var7 = new Dimension(width, height);
                }
                catch (IOException var11) {
                    reader.dispose();
                    continue;
                }
            }
            catch (Throwable throwable) {
                reader.dispose();
                throw throwable;
            }
            reader.dispose();
            return var7;
        }
        return null;
    }

}

