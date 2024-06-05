package optifine;

import java.nio.*;
import java.awt.image.*;
import net.minecraft.client.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.renderer.texture.*;
import shadersmod.client.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.*;
import java.awt.*;
import javax.imageio.*;
import java.io.*;
import java.util.*;
import javax.imageio.stream.*;
import net.minecraft.client.renderer.*;

public class TextureUtils
{
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
    public static final String SPRITE_PREFIX_BLOCKS = "minecraft:blocks/";
    public static final String SPRITE_PREFIX_ITEMS = "minecraft:items/";
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
    private static IntBuffer staticBuffer;
    
    public static void update() {
        final TextureMap mapBlocks = getTextureMapBlocks();
        if (mapBlocks != null) {
            final String prefix = "minecraft:blocks/";
            TextureUtils.iconGrassTop = mapBlocks.getSpriteSafe(prefix + "grass_top");
            TextureUtils.iconGrassSide = mapBlocks.getSpriteSafe(prefix + "grass_side");
            TextureUtils.iconGrassSideOverlay = mapBlocks.getSpriteSafe(prefix + "grass_side_overlay");
            TextureUtils.iconSnow = mapBlocks.getSpriteSafe(prefix + "snow");
            TextureUtils.iconGrassSideSnowed = mapBlocks.getSpriteSafe(prefix + "grass_side_snowed");
            TextureUtils.iconMyceliumSide = mapBlocks.getSpriteSafe(prefix + "mycelium_side");
            TextureUtils.iconMyceliumTop = mapBlocks.getSpriteSafe(prefix + "mycelium_top");
            TextureUtils.iconWaterStill = mapBlocks.getSpriteSafe(prefix + "water_still");
            TextureUtils.iconWaterFlow = mapBlocks.getSpriteSafe(prefix + "water_flow");
            TextureUtils.iconLavaStill = mapBlocks.getSpriteSafe(prefix + "lava_still");
            TextureUtils.iconLavaFlow = mapBlocks.getSpriteSafe(prefix + "lava_flow");
            TextureUtils.iconFireLayer0 = mapBlocks.getSpriteSafe(prefix + "fire_layer_0");
            TextureUtils.iconFireLayer1 = mapBlocks.getSpriteSafe(prefix + "fire_layer_1");
            TextureUtils.iconPortal = mapBlocks.getSpriteSafe(prefix + "portal");
            TextureUtils.iconGlass = mapBlocks.getSpriteSafe(prefix + "glass");
            TextureUtils.iconGlassPaneTop = mapBlocks.getSpriteSafe(prefix + "glass_pane_top");
            final String prefixItems = "minecraft:items/";
            TextureUtils.iconCompass = mapBlocks.getSpriteSafe(prefixItems + "compass");
            TextureUtils.iconClock = mapBlocks.getSpriteSafe(prefixItems + "clock");
        }
    }
    
    public static BufferedImage fixTextureDimensions(final String name, final BufferedImage bi) {
        if (name.startsWith("/mob/zombie") || name.startsWith("/mob/pigzombie")) {
            final int width = bi.getWidth();
            final int height = bi.getHeight();
            if (width == height * 2) {
                final BufferedImage scaledImage = new BufferedImage(width, height * 2, 2);
                final Graphics2D gr = scaledImage.createGraphics();
                gr.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                gr.drawImage(bi, 0, 0, width, height, null);
                return scaledImage;
            }
        }
        return bi;
    }
    
    public static int ceilPowerOfTwo(final int val) {
        int i;
        for (i = 1; i < val; i *= 2) {}
        return i;
    }
    
    public static int getPowerOfTwo(final int val) {
        int i;
        int po2;
        for (i = 1, po2 = 0; i < val; i *= 2, ++po2) {}
        return po2;
    }
    
    public static int twoToPower(final int power) {
        int val = 1;
        for (int i = 0; i < power; ++i) {
            val *= 2;
        }
        return val;
    }
    
    public static ITextureObject getTexture(final ResourceLocation loc) {
        final ITextureObject tex = Config.getTextureManager().getTexture(loc);
        if (tex != null) {
            return tex;
        }
        if (!Config.hasResource(loc)) {
            return null;
        }
        final SimpleTexture tex2 = new SimpleTexture(loc);
        Config.getTextureManager().loadTexture(loc, tex2);
        return tex2;
    }
    
    public static void resourcesReloaded(final IResourceManager rm) {
        if (getTextureMapBlocks() != null) {
            Config.dbg("*** Reloading custom textures ***");
            CustomSky.reset();
            TextureAnimations.reset();
            update();
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
        final IResourceManager rm = Config.getResourceManager();
        if (rm instanceof IReloadableResourceManager) {
            final IReloadableResourceManager tto = (IReloadableResourceManager)rm;
            final IResourceManagerReloadListener ttol = new IResourceManagerReloadListener() {
                @Override
                public void onResourceManagerReload(final IResourceManager var1) {
                    TextureUtils.resourcesReloaded(var1);
                }
            };
            tto.registerReloadListener(ttol);
        }
        final ITickableTextureObject tto2 = new ITickableTextureObject() {
            @Override
            public void tick() {
                TextureAnimations.updateCustomAnimations();
            }
            
            @Override
            public void loadTexture(final IResourceManager var1) {
            }
            
            @Override
            public int getGlTextureId() {
                return 0;
            }
            
            @Override
            public void func_174936_b(final boolean p_174936_1, final boolean p_174936_2) {
            }
            
            @Override
            public void func_174935_a() {
            }
            
            @Override
            public MultiTexID getMultiTexID() {
                return null;
            }
        };
        final ResourceLocation ttol2 = new ResourceLocation("optifine/TickableTextures");
        Config.getTextureManager().loadTickableTexture(ttol2, tto2);
    }
    
    public static String fixResourcePath(String path, String basePath) {
        final String strAssMc = "assets/minecraft/";
        if (path.startsWith(strAssMc)) {
            path = path.substring(strAssMc.length());
            return path;
        }
        if (path.startsWith("./")) {
            path = path.substring(2);
            if (!basePath.endsWith("/")) {
                basePath += "/";
            }
            path = basePath + path;
            return path;
        }
        if (path.startsWith("/~")) {
            path = path.substring(1);
        }
        final String strMcpatcher = "mcpatcher/";
        if (path.startsWith("~/")) {
            path = path.substring(2);
            path = strMcpatcher + path;
            return path;
        }
        if (path.startsWith("/")) {
            path = strMcpatcher + path.substring(1);
            return path;
        }
        return path;
    }
    
    public static String getBasePath(final String path) {
        final int pos = path.lastIndexOf(47);
        return (pos < 0) ? "" : path.substring(0, pos);
    }
    
    public static void applyAnisotropicLevel() {
        if (GLContext.getCapabilities().GL_EXT_texture_filter_anisotropic) {
            final float maxLevel = GL11.glGetFloat(34047);
            float level = (float)Config.getAnisotropicFilterLevel();
            level = Math.min(level, maxLevel);
            GL11.glTexParameterf(3553, 34046, level);
        }
    }
    
    public static void bindTexture(final int glTexId) {
        GlStateManager.func_179144_i(glTexId);
    }
    
    public static boolean isPowerOfTwo(final int x) {
        final int x2 = MathHelper.roundUpToPowerOfTwo(x);
        return x2 == x;
    }
    
    public static BufferedImage scaleToPowerOfTwo(final BufferedImage bi, final int minSize) {
        if (bi == null) {
            return bi;
        }
        final int w = bi.getWidth();
        final int h = bi.getHeight();
        int w2 = Math.max(w, minSize);
        w2 = MathHelper.roundUpToPowerOfTwo(w2);
        if (w2 == w) {
            return bi;
        }
        final int h2 = h * w2 / w;
        final BufferedImage bi2 = new BufferedImage(w2, h2, 2);
        final Graphics2D g2 = bi2.createGraphics();
        Object method = RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR;
        if (w2 % w != 0) {
            method = RenderingHints.VALUE_INTERPOLATION_BILINEAR;
        }
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, method);
        g2.drawImage(bi, 0, 0, w2, h2, null);
        return bi2;
    }
    
    public static BufferedImage scaleMinTo(final BufferedImage bi, final int minSize) {
        if (bi == null) {
            return bi;
        }
        final int w = bi.getWidth();
        final int h = bi.getHeight();
        if (w >= minSize) {
            return bi;
        }
        int w2;
        for (w2 = w; w2 < minSize; w2 *= 2) {}
        final int h2 = h * w2 / w;
        final BufferedImage bi2 = new BufferedImage(w2, h2, 2);
        final Graphics2D g2 = bi2.createGraphics();
        final Object method = RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR;
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, method);
        g2.drawImage(bi, 0, 0, w2, h2, null);
        return bi2;
    }
    
    public static Dimension getImageSize(final InputStream in, final String suffix) {
        final Iterator iter = ImageIO.getImageReadersBySuffix(suffix);
        while (iter.hasNext()) {
            final ImageReader reader = iter.next();
            Dimension var7;
            try {
                final ImageInputStream e = ImageIO.createImageInputStream(in);
                reader.setInput(e);
                final int width = reader.getWidth(reader.getMinIndex());
                final int height = reader.getHeight(reader.getMinIndex());
                var7 = new Dimension(width, height);
            }
            catch (IOException var8) {
                continue;
            }
            finally {
                reader.dispose();
            }
            return var7;
        }
        return null;
    }
    
    static {
        TextureUtils.staticBuffer = GLAllocation.createDirectIntBuffer(256);
    }
}
