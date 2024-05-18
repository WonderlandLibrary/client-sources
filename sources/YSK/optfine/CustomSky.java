package optfine;

import net.minecraft.world.*;
import net.minecraft.util.*;
import java.util.*;
import java.io.*;
import net.minecraft.client.renderer.texture.*;

public class CustomSky
{
    private static CustomSkyLayer[][] worldSkyLayers;
    private static final String[] I;
    
    static {
        I();
        CustomSky.worldSkyLayers = null;
    }
    
    public static void renderSky(final World world, final TextureManager textureManager, final float n, final float n2) {
        if (CustomSky.worldSkyLayers != null && Config.getGameSettings().renderDistanceChunks >= (0x81 ^ 0x89)) {
            final int dimensionId = world.provider.getDimensionId();
            if (dimensionId >= 0 && dimensionId < CustomSky.worldSkyLayers.length) {
                final CustomSkyLayer[] array = CustomSky.worldSkyLayers[dimensionId];
                if (array != null) {
                    final int n3 = (int)(world.getWorldTime() % 24000L);
                    int i = "".length();
                    "".length();
                    if (2 < 2) {
                        throw null;
                    }
                    while (i < array.length) {
                        final CustomSkyLayer customSkyLayer = array[i];
                        if (customSkyLayer.isActive(n3)) {
                            customSkyLayer.render(n3, n, n2);
                        }
                        ++i;
                    }
                    Blender.clearBlend(n2);
                }
            }
        }
    }
    
    public static void reset() {
        CustomSky.worldSkyLayers = null;
    }
    
    private static CustomSkyLayer[][] readCustomSkies() {
        final CustomSkyLayer[][] array = new CustomSkyLayer[0x5 ^ 0xF]["".length()];
        final String s = CustomSky.I["".length()];
        int n = -" ".length();
        int i = "".length();
        "".length();
        if (0 >= 2) {
            throw null;
        }
        while (i < array.length) {
            final String string = String.valueOf(s) + i + CustomSky.I[" ".length()];
            final ArrayList<CustomSkyLayer> list = new ArrayList<CustomSkyLayer>();
            int j = " ".length();
            "".length();
            if (3 != 3) {
                throw null;
            }
            while (j < 659 + 431 - 377 + 287) {
                final String string2 = String.valueOf(string) + j + CustomSky.I["  ".length()];
                try {
                    final InputStream resourceStream = Config.getResourceStream(new ResourceLocation(string2));
                    if (resourceStream == null) {
                        "".length();
                        if (1 == 4) {
                            throw null;
                        }
                        break;
                    }
                    else {
                        final Properties properties = new Properties();
                        properties.load(resourceStream);
                        Config.dbg(CustomSky.I["   ".length()] + string2);
                        final CustomSkyLayer customSkyLayer = new CustomSkyLayer(properties, String.valueOf(string) + j + CustomSky.I[0x1 ^ 0x5]);
                        if (customSkyLayer.isValid(string2)) {
                            final ResourceLocation resourceLocation = new ResourceLocation(customSkyLayer.source);
                            final ITextureObject texture = TextureUtils.getTexture(resourceLocation);
                            if (texture == null) {
                                Config.log(CustomSky.I[0x1D ^ 0x18] + resourceLocation);
                                "".length();
                                if (1 >= 3) {
                                    throw null;
                                }
                            }
                            else {
                                customSkyLayer.textureId = texture.getGlTextureId();
                                list.add(customSkyLayer);
                                resourceStream.close();
                                "".length();
                                if (1 <= -1) {
                                    throw null;
                                }
                            }
                        }
                    }
                }
                catch (FileNotFoundException ex2) {
                    "".length();
                    if (4 != 4) {
                        throw null;
                    }
                    break;
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                }
                ++j;
            }
            if (list.size() > 0) {
                array[i] = list.toArray(new CustomSkyLayer[list.size()]);
                n = i;
            }
            ++i;
        }
        if (n < 0) {
            return null;
        }
        final CustomSkyLayer[][] array2 = new CustomSkyLayer[n + " ".length()]["".length()];
        int k = "".length();
        "".length();
        if (0 >= 2) {
            throw null;
        }
        while (k < array2.length) {
            array2[k] = array[k];
            ++k;
        }
        return array2;
    }
    
    public static boolean hasSkyLayers(final World world) {
        if (CustomSky.worldSkyLayers == null) {
            return "".length() != 0;
        }
        if (Config.getGameSettings().renderDistanceChunks < (0x37 ^ 0x3F)) {
            return "".length() != 0;
        }
        final int dimensionId = world.provider.getDimensionId();
        if (dimensionId >= 0 && dimensionId < CustomSky.worldSkyLayers.length) {
            final CustomSkyLayer[] array = CustomSky.worldSkyLayers[dimensionId];
            int n;
            if (array == null) {
                n = "".length();
                "".length();
                if (1 < 1) {
                    throw null;
                }
            }
            else if (array.length > 0) {
                n = " ".length();
                "".length();
                if (4 <= 3) {
                    throw null;
                }
            }
            else {
                n = "".length();
            }
            return n != 0;
        }
        return "".length() != 0;
    }
    
    private static void I() {
        (I = new String[0x1A ^ 0x1C])["".length()] = I("545 \u0004;? 3_+<<n\u00077%)%", "XWEAp");
        CustomSky.I[" ".length()] = I("x*;:", "WYPCJ");
        CustomSky.I["  ".length()] = I("C(\u0018\u00042\b*\u001e\u0002'\u001e", "mXjkB");
        CustomSky.I["   ".length()] = I("7\u0005&;-\u0019#>6b\u0004\u0002:?'\u0006\u0004<*1NP", "tpUOB");
        CustomSky.I[0x4D ^ 0x49] = I("@<+4", "nLESg");
        CustomSky.I[0x74 ^ 0x71] = I("98 \u0000\u001b\u0017\u001e8\rNZ\u00196\f\u0000\u000f?6T\u001a\u00159s\u0012\u001b\u000f#7NT", "zMStt");
    }
    
    public static void update() {
        reset();
        if (Config.isCustomSky()) {
            CustomSky.worldSkyLayers = readCustomSkies();
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
            if (2 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
}
