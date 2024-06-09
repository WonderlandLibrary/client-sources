package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.nio.IntBuffer;

public class TextureUtils
{
    public static final String HorizonCode_Horizon_È = "grass_top";
    public static final String Â = "stone";
    public static final String Ý = "dirt";
    public static final String Ø­áŒŠá = "grass_side";
    public static final String Âµá€ = "stone_slab_side";
    public static final String Ó = "stone_slab_top";
    public static final String à = "bedrock";
    public static final String Ø = "sand";
    public static final String áŒŠÆ = "gravel";
    public static final String áˆºÑ¢Õ = "log_oak";
    public static final String ÂµÈ = "log_oak_top";
    public static final String á = "gold_ore";
    public static final String ˆÏ­ = "iron_ore";
    public static final String £á = "coal_ore";
    public static final String Å = "obsidian";
    public static final String £à = "grass_side_overlay";
    public static final String µà = "snow";
    public static final String ˆà = "grass_side_snowed";
    public static final String ¥Æ = "mycelium_side";
    public static final String Ø­à = "mycelium_top";
    public static final String µÕ = "diamond_ore";
    public static final String Æ = "redstone_ore";
    public static final String Šáƒ = "lapis_ore";
    public static final String Ï­Ðƒà = "leaves_oak";
    public static final String áŒŠà = "leaves_oak_opaque";
    public static final String ŠÄ = "leaves_jungle";
    public static final String Ñ¢á = "leaves_jungle_opaque";
    public static final String ŒÏ = "cactus_side";
    public static final String Çªà¢ = "clay";
    public static final String Ê = "farmland_wet";
    public static final String ÇŽÉ = "farmland_dry";
    public static final String ˆá = "netherrack";
    public static final String ÇŽÕ = "soul_sand";
    public static final String É = "glowstone";
    public static final String áƒ = "log_spruce";
    public static final String á€ = "log_birch";
    public static final String Õ = "leaves_spruce";
    public static final String à¢ = "leaves_spruce_opaque";
    public static final String ŠÂµà = "log_jungle";
    public static final String ¥à = "end_stone";
    public static final String Âµà = "sandstone_top";
    public static final String Ç = "sandstone_bottom";
    public static final String È = "redstone_lamp_off";
    public static final String áŠ = "redstone_lamp_on";
    public static final String ˆáŠ = "water_still";
    public static final String áŒŠ = "water_flow";
    public static final String £ÂµÄ = "lava_still";
    public static final String Ø­Âµ = "lava_flow";
    public static final String Ä = "fire_layer_0";
    public static final String Ñ¢Â = "fire_layer_1";
    public static final String Ï­à = "portal";
    public static final String áˆºáˆºÈ = "glass";
    public static final String ÇŽá€ = "glass_pane_top";
    public static TextureAtlasSprite Ï;
    public static TextureAtlasSprite Ô;
    public static TextureAtlasSprite ÇªÓ;
    public static TextureAtlasSprite áˆºÏ;
    public static TextureAtlasSprite ˆáƒ;
    public static TextureAtlasSprite Œ;
    public static TextureAtlasSprite £Ï;
    public static TextureAtlasSprite Ø­á;
    public static TextureAtlasSprite ˆÉ;
    public static TextureAtlasSprite Ï­Ï­Ï;
    public static TextureAtlasSprite £Â;
    public static TextureAtlasSprite £Ó;
    public static TextureAtlasSprite ˆÐƒØ­à;
    public static TextureAtlasSprite £Õ;
    public static TextureAtlasSprite Ï­Ô;
    public static TextureAtlasSprite Œà;
    public static final String Ðƒá = "minecraft:blocks/";
    private static IntBuffer ˆÏ;
    
    static {
        TextureUtils.ˆÏ = GLAllocation.Ø­áŒŠá(256);
    }
    
    public static void HorizonCode_Horizon_È() {
        final TextureMap mapBlocks = Ý();
        if (mapBlocks != null) {
            final String prefix = "minecraft:blocks/";
            TextureUtils.Ï = mapBlocks.Ý(String.valueOf(prefix) + "grass_top");
            TextureUtils.Ô = mapBlocks.Ý(String.valueOf(prefix) + "grass_side");
            TextureUtils.ÇªÓ = mapBlocks.Ý(String.valueOf(prefix) + "grass_side_overlay");
            TextureUtils.áˆºÏ = mapBlocks.Ý(String.valueOf(prefix) + "snow");
            TextureUtils.ˆáƒ = mapBlocks.Ý(String.valueOf(prefix) + "grass_side_snowed");
            TextureUtils.Œ = mapBlocks.Ý(String.valueOf(prefix) + "mycelium_side");
            TextureUtils.£Ï = mapBlocks.Ý(String.valueOf(prefix) + "mycelium_top");
            TextureUtils.Ø­á = mapBlocks.Ý(String.valueOf(prefix) + "water_still");
            TextureUtils.ˆÉ = mapBlocks.Ý(String.valueOf(prefix) + "water_flow");
            TextureUtils.Ï­Ï­Ï = mapBlocks.Ý(String.valueOf(prefix) + "lava_still");
            TextureUtils.£Â = mapBlocks.Ý(String.valueOf(prefix) + "lava_flow");
            TextureUtils.ˆÐƒØ­à = mapBlocks.Ý(String.valueOf(prefix) + "fire_layer_0");
            TextureUtils.£Õ = mapBlocks.Ý(String.valueOf(prefix) + "fire_layer_1");
            TextureUtils.£Ó = mapBlocks.Ý(String.valueOf(prefix) + "portal");
            TextureUtils.Ï­Ô = mapBlocks.Ý(String.valueOf(prefix) + "glass");
            TextureUtils.Œà = mapBlocks.Ý(String.valueOf(prefix) + "glass_pane_top");
        }
    }
    
    public static BufferedImage HorizonCode_Horizon_È(final String name, final BufferedImage bi) {
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
    
    public static int HorizonCode_Horizon_È(final int val) {
        int i;
        for (i = 1; i < val; i *= 2) {}
        return i;
    }
    
    public static int Â(final int val) {
        int i;
        int po2;
        for (i = 1, po2 = 0; i < val; i *= 2, ++po2) {}
        return po2;
    }
    
    public static int Ý(final int power) {
        int val = 1;
        for (int i = 0; i < power; ++i) {
            val *= 2;
        }
        return val;
    }
    
    public static void Â() {
    }
    
    public static ITextureObject HorizonCode_Horizon_È(final String path) {
        return HorizonCode_Horizon_È(new ResourceLocation_1975012498(path));
    }
    
    public static ITextureObject HorizonCode_Horizon_È(final ResourceLocation_1975012498 loc) {
        final ITextureObject tex = Config.áŠ().Â(loc);
        if (tex != null) {
            return tex;
        }
        if (!Config.Ý(loc)) {
            return null;
        }
        final SimpleTexture tex2 = new SimpleTexture(loc);
        Config.áŠ().HorizonCode_Horizon_È(loc, tex2);
        return tex2;
    }
    
    public static void HorizonCode_Horizon_È(final IResourceManager rm) {
        if (Ý() != null) {
            Config.HorizonCode_Horizon_È("*** Reloading custom textures ***");
            CustomSky.HorizonCode_Horizon_È();
            TextureAnimations.HorizonCode_Horizon_È();
            HorizonCode_Horizon_È();
            NaturalTextures.HorizonCode_Horizon_È();
            BetterGrass.HorizonCode_Horizon_È();
            BetterSnow.HorizonCode_Horizon_È();
            TextureAnimations.Â();
            CustomColorizer.HorizonCode_Horizon_È();
            CustomSky.Â();
            RandomMobs.HorizonCode_Horizon_È();
            Config.Ï­Ðƒà();
            Config.áŠ().Â();
        }
    }
    
    public static TextureMap Ý() {
        return Minecraft.áŒŠà().áŠ();
    }
    
    public static void Ø­áŒŠá() {
        final IResourceManager rm = Config.ˆáŠ();
        if (rm instanceof IReloadableResourceManager) {
            final IReloadableResourceManager tto = (IReloadableResourceManager)rm;
            final IResourceManagerReloadListener ttol = new IResourceManagerReloadListener() {
                @Override
                public void HorizonCode_Horizon_È(final IResourceManager var1) {
                    TextureUtils.HorizonCode_Horizon_È(var1);
                }
            };
            tto.HorizonCode_Horizon_È(ttol);
        }
        final ITickableTextureObject tto2 = new ITickableTextureObject() {
            @Override
            public void Â() {
                TextureAnimations.Ý();
            }
            
            @Override
            public void HorizonCode_Horizon_È(final IResourceManager var1) throws IOException {
            }
            
            @Override
            public int HorizonCode_Horizon_È() {
                return 0;
            }
            
            @Override
            public void Â(final boolean p_174936_1, final boolean p_174936_2) {
            }
            
            @Override
            public void Ø­áŒŠá() {
            }
        };
        final ResourceLocation_1975012498 ttol2 = new ResourceLocation_1975012498("optifine/TickableTextures");
        Config.áŠ().HorizonCode_Horizon_È(ttol2, tto2);
    }
    
    public static String HorizonCode_Horizon_È(String path, String basePath) {
        final String strAssMc = "assets/minecraft/";
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
        final String strMcpatcher = "mcpatcher/";
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
    
    public static String Â(final String path) {
        final int pos = path.lastIndexOf(47);
        return (pos < 0) ? "" : path.substring(0, pos);
    }
}
