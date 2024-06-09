package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class NaturalTextures
{
    private static NaturalProperties[] HorizonCode_Horizon_È;
    
    static {
        NaturalTextures.HorizonCode_Horizon_È = new NaturalProperties[0];
    }
    
    public static void HorizonCode_Horizon_È() {
        NaturalTextures.HorizonCode_Horizon_È = new NaturalProperties[0];
        if (Config.Û()) {
            final String fileName = "optifine/natural.properties";
            try {
                final ResourceLocation_1975012498 e = new ResourceLocation_1975012498(fileName);
                if (!Config.Ý(e)) {
                    Config.HorizonCode_Horizon_È("NaturalTextures: configuration \"" + fileName + "\" not found");
                    NaturalTextures.HorizonCode_Horizon_È = Â();
                    return;
                }
                final InputStream in = Config.HorizonCode_Horizon_È(e);
                final ArrayList list = new ArrayList(256);
                final String configStr = Config.HorizonCode_Horizon_È(in);
                in.close();
                final String[] configLines = Config.HorizonCode_Horizon_È(configStr, "\n\r");
                Config.HorizonCode_Horizon_È("Natural Textures: Parsing configuration \"" + fileName + "\"");
                final TextureMap textureMapBlocks = TextureUtils.Ý();
                for (int i = 0; i < configLines.length; ++i) {
                    final String line = configLines[i].trim();
                    if (!line.startsWith("#")) {
                        final String[] strs = Config.HorizonCode_Horizon_È(line, "=");
                        if (strs.length != 2) {
                            Config.Â("Natural Textures: Invalid \"" + fileName + "\" line: " + line);
                        }
                        else {
                            final String key = strs[0].trim();
                            final String type = strs[1].trim();
                            final TextureAtlasSprite ts = textureMapBlocks.Ý("minecraft:blocks/" + key);
                            if (ts == null) {
                                Config.Â("Natural Textures: Texture not found: \"" + fileName + "\" line: " + line);
                            }
                            else {
                                final int tileNum = ts.£á();
                                if (tileNum < 0) {
                                    Config.Â("Natural Textures: Invalid \"" + fileName + "\" line: " + line);
                                }
                                else {
                                    final NaturalProperties props = new NaturalProperties(type);
                                    if (props.HorizonCode_Horizon_È()) {
                                        while (list.size() <= tileNum) {
                                            list.add(null);
                                        }
                                        list.set(tileNum, props);
                                        Config.HorizonCode_Horizon_È("NaturalTextures: " + key + " = " + type);
                                    }
                                }
                            }
                        }
                    }
                }
                NaturalTextures.HorizonCode_Horizon_È = list.toArray(new NaturalProperties[list.size()]);
            }
            catch (FileNotFoundException var18) {
                Config.Â("NaturalTextures: configuration \"" + fileName + "\" not found");
                NaturalTextures.HorizonCode_Horizon_È = Â();
            }
            catch (Exception var17) {
                var17.printStackTrace();
            }
        }
    }
    
    public static NaturalProperties HorizonCode_Horizon_È(final TextureAtlasSprite icon) {
        if (!(icon instanceof TextureAtlasSprite)) {
            return null;
        }
        final int tileNum = icon.£á();
        if (tileNum >= 0 && tileNum < NaturalTextures.HorizonCode_Horizon_È.length) {
            final NaturalProperties props = NaturalTextures.HorizonCode_Horizon_È[tileNum];
            return props;
        }
        return null;
    }
    
    private static NaturalProperties[] Â() {
        Config.HorizonCode_Horizon_È("NaturalTextures: Creating default configuration.");
        final ArrayList propsList = new ArrayList();
        HorizonCode_Horizon_È(propsList, "grass_top", "4F");
        HorizonCode_Horizon_È(propsList, "stone", "2F");
        HorizonCode_Horizon_È(propsList, "dirt", "4F");
        HorizonCode_Horizon_È(propsList, "grass_side", "F");
        HorizonCode_Horizon_È(propsList, "grass_side_overlay", "F");
        HorizonCode_Horizon_È(propsList, "stone_slab_top", "F");
        HorizonCode_Horizon_È(propsList, "bedrock", "2F");
        HorizonCode_Horizon_È(propsList, "sand", "4F");
        HorizonCode_Horizon_È(propsList, "gravel", "2");
        HorizonCode_Horizon_È(propsList, "log_oak", "2F");
        HorizonCode_Horizon_È(propsList, "log_oak_top", "4F");
        HorizonCode_Horizon_È(propsList, "gold_ore", "2F");
        HorizonCode_Horizon_È(propsList, "iron_ore", "2F");
        HorizonCode_Horizon_È(propsList, "coal_ore", "2F");
        HorizonCode_Horizon_È(propsList, "diamond_ore", "2F");
        HorizonCode_Horizon_È(propsList, "redstone_ore", "2F");
        HorizonCode_Horizon_È(propsList, "lapis_ore", "2F");
        HorizonCode_Horizon_È(propsList, "obsidian", "4F");
        HorizonCode_Horizon_È(propsList, "leaves_oak", "2F");
        HorizonCode_Horizon_È(propsList, "leaves_jungle", "2");
        HorizonCode_Horizon_È(propsList, "snow", "4F");
        HorizonCode_Horizon_È(propsList, "grass_side_snowed", "F");
        HorizonCode_Horizon_È(propsList, "cactus_side", "2F");
        HorizonCode_Horizon_È(propsList, "clay", "4F");
        HorizonCode_Horizon_È(propsList, "mycelium_side", "F");
        HorizonCode_Horizon_È(propsList, "mycelium_top", "4F");
        HorizonCode_Horizon_È(propsList, "farmland_wet", "2F");
        HorizonCode_Horizon_È(propsList, "farmland_dry", "2F");
        HorizonCode_Horizon_È(propsList, "netherrack", "4F");
        HorizonCode_Horizon_È(propsList, "soul_sand", "4F");
        HorizonCode_Horizon_È(propsList, "glowstone", "4");
        HorizonCode_Horizon_È(propsList, "log_spruce", "2F");
        HorizonCode_Horizon_È(propsList, "log_birch", "F");
        HorizonCode_Horizon_È(propsList, "leaves_spruce", "2F");
        HorizonCode_Horizon_È(propsList, "log_jungle", "2F");
        HorizonCode_Horizon_È(propsList, "end_stone", "4");
        HorizonCode_Horizon_È(propsList, "sandstone_top", "4");
        HorizonCode_Horizon_È(propsList, "sandstone_bottom", "4F");
        HorizonCode_Horizon_È(propsList, "redstone_lamp_on", "4F");
        final NaturalProperties[] terrainProps = propsList.toArray(new NaturalProperties[propsList.size()]);
        return terrainProps;
    }
    
    private static void HorizonCode_Horizon_È(final List propsList, final String iconName, final String propStr) {
        final TextureMap terrainMap = TextureUtils.Ý();
        final TextureAtlasSprite icon = terrainMap.Ý("minecraft:blocks/" + iconName);
        if (icon == null) {
            Config.Â("*** NaturalProperties: Icon not found: " + iconName + " ***");
        }
        else if (!(icon instanceof TextureAtlasSprite)) {
            Config.Â("*** NaturalProperties: Icon is not IconStitched: " + iconName + ": " + icon.getClass().getName() + " ***");
        }
        else {
            final int index = icon.£á();
            if (index < 0) {
                Config.Â("*** NaturalProperties: Invalid index for icon: " + iconName + ": " + index + " ***");
            }
            else if (Config.Ø­áŒŠá(new ResourceLocation_1975012498("textures/blocks/" + iconName + ".png"))) {
                while (index >= propsList.size()) {
                    propsList.add(null);
                }
                final NaturalProperties props = new NaturalProperties(propStr);
                propsList.set(index, props);
                Config.HorizonCode_Horizon_È("NaturalTextures: " + iconName + " = " + propStr);
            }
        }
    }
}
