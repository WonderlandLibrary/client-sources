package optfine;

import java.io.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.block.model.*;
import java.util.*;

public class NaturalTextures
{
    private static NaturalProperties[] propertiesByIndex;
    
    public static void update() {
        NaturalTextures.propertiesByIndex = new NaturalProperties[0];
        if (Config.isNaturalTextures()) {
            final String s = "optifine/natural.properties";
            try {
                final ResourceLocation resourcelocation = new ResourceLocation(s);
                if (!Config.hasResource(resourcelocation)) {
                    Config.dbg("NaturalTextures: configuration \"" + s + "\" not found");
                    NaturalTextures.propertiesByIndex = makeDefaultProperties();
                    return;
                }
                final InputStream inputstream = Config.getResourceStream(resourcelocation);
                final ArrayList arraylist = new ArrayList(256);
                final String s2 = Config.readInputStream(inputstream);
                inputstream.close();
                final String[] astring = Config.tokenize(s2, "\n\r");
                Config.dbg("Natural Textures: Parsing configuration \"" + s + "\"");
                final TextureMap texturemap = TextureUtils.getTextureMapBlocks();
                for (int i = 0; i < astring.length; ++i) {
                    final String s3 = astring[i].trim();
                    if (!s3.startsWith("#")) {
                        final String[] astring2 = Config.tokenize(s3, "=");
                        if (astring2.length != 2) {
                            Config.warn("Natural Textures: Invalid \"" + s + "\" line: " + s3);
                        }
                        else {
                            final String s4 = astring2[0].trim();
                            final String s5 = astring2[1].trim();
                            final TextureAtlasSprite textureatlassprite = texturemap.getSpriteSafe("minecraft:blocks/" + s4);
                            if (textureatlassprite == null) {
                                Config.warn("Natural Textures: Texture not found: \"" + s + "\" line: " + s3);
                            }
                            else {
                                final int j = textureatlassprite.getIndexInMap();
                                if (j < 0) {
                                    Config.warn("Natural Textures: Invalid \"" + s + "\" line: " + s3);
                                }
                                else {
                                    final NaturalProperties naturalproperties = new NaturalProperties(s5);
                                    if (naturalproperties.isValid()) {
                                        while (arraylist.size() <= j) {
                                            arraylist.add(null);
                                        }
                                        arraylist.set(j, naturalproperties);
                                        Config.dbg("NaturalTextures: " + s4 + " = " + s5);
                                    }
                                }
                            }
                        }
                    }
                }
                NaturalTextures.propertiesByIndex = arraylist.toArray(new NaturalProperties[arraylist.size()]);
            }
            catch (FileNotFoundException var16) {
                Config.warn("NaturalTextures: configuration \"" + s + "\" not found");
                NaturalTextures.propertiesByIndex = makeDefaultProperties();
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
    
    public static BakedQuad getNaturalTexture(final BlockPos p_getNaturalTexture_0_, final BakedQuad p_getNaturalTexture_1_) {
        final TextureAtlasSprite textureatlassprite = p_getNaturalTexture_1_.getSprite();
        if (textureatlassprite == null) {
            return p_getNaturalTexture_1_;
        }
        final NaturalProperties naturalproperties = getNaturalProperties(textureatlassprite);
        if (naturalproperties == null) {
            return p_getNaturalTexture_1_;
        }
        final int i = ConnectedTextures.getSide(p_getNaturalTexture_1_.getFace());
        final int j = Config.getRandom(p_getNaturalTexture_0_, i);
        int k = 0;
        boolean flag = false;
        if (naturalproperties.rotation > 1) {
            k = (j & 0x3);
        }
        if (naturalproperties.rotation == 2) {
            k = k / 2 * 2;
        }
        if (naturalproperties.flip) {
            flag = ((j & 0x4) != 0x0);
        }
        return naturalproperties.getQuad(p_getNaturalTexture_1_, k, flag);
    }
    
    public static NaturalProperties getNaturalProperties(final TextureAtlasSprite p_getNaturalProperties_0_) {
        if (!(p_getNaturalProperties_0_ instanceof TextureAtlasSprite)) {
            return null;
        }
        final int i = p_getNaturalProperties_0_.getIndexInMap();
        if (i >= 0 && i < NaturalTextures.propertiesByIndex.length) {
            final NaturalProperties naturalproperties = NaturalTextures.propertiesByIndex[i];
            return naturalproperties;
        }
        return null;
    }
    
    private static NaturalProperties[] makeDefaultProperties() {
        Config.dbg("NaturalTextures: Creating default configuration.");
        final List list = new ArrayList();
        setIconProperties(list, "coarse_dirt", "4F");
        setIconProperties(list, "grass_side", "F");
        setIconProperties(list, "grass_side_overlay", "F");
        setIconProperties(list, "stone_slab_top", "F");
        setIconProperties(list, "gravel", "2");
        setIconProperties(list, "log_oak", "2F");
        setIconProperties(list, "log_spruce", "2F");
        setIconProperties(list, "log_birch", "F");
        setIconProperties(list, "log_jungle", "2F");
        setIconProperties(list, "log_acacia", "2F");
        setIconProperties(list, "log_big_oak", "2F");
        setIconProperties(list, "log_oak_top", "4F");
        setIconProperties(list, "log_spruce_top", "4F");
        setIconProperties(list, "log_birch_top", "4F");
        setIconProperties(list, "log_jungle_top", "4F");
        setIconProperties(list, "log_acacia_top", "4F");
        setIconProperties(list, "log_big_oak_top", "4F");
        setIconProperties(list, "leaves_oak", "2F");
        setIconProperties(list, "leaves_spruce", "2F");
        setIconProperties(list, "leaves_birch", "2F");
        setIconProperties(list, "leaves_jungle", "2");
        setIconProperties(list, "leaves_big_oak", "2F");
        setIconProperties(list, "leaves_acacia", "2F");
        setIconProperties(list, "gold_ore", "2F");
        setIconProperties(list, "iron_ore", "2F");
        setIconProperties(list, "coal_ore", "2F");
        setIconProperties(list, "diamond_ore", "2F");
        setIconProperties(list, "redstone_ore", "2F");
        setIconProperties(list, "lapis_ore", "2F");
        setIconProperties(list, "obsidian", "4F");
        setIconProperties(list, "snow", "4F");
        setIconProperties(list, "grass_side_snowed", "F");
        setIconProperties(list, "cactus_side", "2F");
        setIconProperties(list, "clay", "4F");
        setIconProperties(list, "mycelium_side", "F");
        setIconProperties(list, "mycelium_top", "4F");
        setIconProperties(list, "farmland_wet", "2F");
        setIconProperties(list, "farmland_dry", "2F");
        setIconProperties(list, "netherrack", "4F");
        setIconProperties(list, "soul_sand", "4F");
        setIconProperties(list, "glowstone", "4");
        setIconProperties(list, "end_stone", "4");
        setIconProperties(list, "sandstone_top", "4");
        setIconProperties(list, "sandstone_bottom", "4F");
        setIconProperties(list, "redstone_lamp_on", "4F");
        setIconProperties(list, "redstone_lamp_off", "4F");
        final NaturalProperties[] anaturalproperties = list.toArray(new NaturalProperties[list.size()]);
        return anaturalproperties;
    }
    
    private static void setIconProperties(final List p_setIconProperties_0_, final String p_setIconProperties_1_, final String p_setIconProperties_2_) {
        final TextureMap texturemap = TextureUtils.getTextureMapBlocks();
        final TextureAtlasSprite textureatlassprite = texturemap.getSpriteSafe("minecraft:blocks/" + p_setIconProperties_1_);
        if (textureatlassprite == null) {
            Config.warn("*** NaturalProperties: Icon not found: " + p_setIconProperties_1_ + " ***");
        }
        else if (!(textureatlassprite instanceof TextureAtlasSprite)) {
            Config.warn("*** NaturalProperties: Icon is not IconStitched: " + p_setIconProperties_1_ + ": " + textureatlassprite.getClass().getName() + " ***");
        }
        else {
            final int i = textureatlassprite.getIndexInMap();
            if (i < 0) {
                Config.warn("*** NaturalProperties: Invalid index for icon: " + p_setIconProperties_1_ + ": " + i + " ***");
            }
            else if (Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/" + p_setIconProperties_1_ + ".png"))) {
                while (i >= p_setIconProperties_0_.size()) {
                    p_setIconProperties_0_.add(null);
                }
                final NaturalProperties naturalproperties = new NaturalProperties(p_setIconProperties_2_);
                p_setIconProperties_0_.set(i, naturalproperties);
                Config.dbg("NaturalTextures: " + p_setIconProperties_1_ + " = " + p_setIconProperties_2_);
            }
        }
    }
    
    static {
        NaturalTextures.propertiesByIndex = new NaturalProperties[0];
    }
}
