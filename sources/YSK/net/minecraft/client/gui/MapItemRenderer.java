package net.minecraft.client.gui;

import net.minecraft.world.storage.*;
import com.google.common.collect.*;
import java.util.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.block.material.*;

public class MapItemRenderer
{
    private final Map<String, Instance> loadedMaps;
    private static final String[] I;
    private final TextureManager textureManager;
    private static final ResourceLocation mapIcons;
    
    static TextureManager access$0(final MapItemRenderer mapItemRenderer) {
        return mapItemRenderer.textureManager;
    }
    
    public void updateMapTexture(final MapData mapData) {
        Instance.access$0(this.getMapRendererInstance(mapData));
    }
    
    public MapItemRenderer(final TextureManager textureManager) {
        this.loadedMaps = (Map<String, Instance>)Maps.newHashMap();
        this.textureManager = textureManager;
    }
    
    public void renderMap(final MapData mapData, final boolean b) {
        Instance.access$1(this.getMapRendererInstance(mapData), b);
    }
    
    static ResourceLocation access$1() {
        return MapItemRenderer.mapIcons;
    }
    
    static {
        I();
        mapIcons = new ResourceLocation(MapItemRenderer.I["".length()]);
    }
    
    private Instance getMapRendererInstance(final MapData mapData) {
        Instance instance = this.loadedMaps.get(mapData.mapName);
        if (instance == null) {
            instance = new Instance(mapData, null);
            this.loadedMaps.put(mapData.mapName, instance);
        }
        return instance;
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("%\r\u0000$\u0019#\r\u000b\u007f\u00010\u0018W=\r!7\u00113\u0003?\u001bV \u00026", "QhxPl");
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
            if (2 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void clearLoadedMaps() {
        final Iterator<Instance> iterator = this.loadedMaps.values().iterator();
        "".length();
        if (2 == 0) {
            throw null;
        }
        while (iterator.hasNext()) {
            this.textureManager.deleteTexture(Instance.access$3(iterator.next()));
        }
        this.loadedMaps.clear();
    }
    
    class Instance
    {
        private final MapData mapData;
        private static final String[] I;
        private final int[] mapTextureData;
        private final DynamicTexture mapTexture;
        final MapItemRenderer this$0;
        private final ResourceLocation location;
        
        static void access$0(final Instance instance) {
            instance.updateMapTexture();
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
                if (2 < 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private void render(final boolean b) {
            final int length = "".length();
            final int length2 = "".length();
            final Tessellator instance = Tessellator.getInstance();
            final WorldRenderer worldRenderer = instance.getWorldRenderer();
            final float n = 0.0f;
            MapItemRenderer.access$0(this.this$0).bindTexture(this.location);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(" ".length(), 691 + 68 - 310 + 322, "".length(), " ".length());
            GlStateManager.disableAlpha();
            worldRenderer.begin(0x4A ^ 0x4D, DefaultVertexFormats.POSITION_TEX);
            worldRenderer.pos(length + "".length() + n, length2 + (84 + 109 - 92 + 27) - n, -0.009999999776482582).tex(0.0, 1.0).endVertex();
            worldRenderer.pos(length + (112 + 100 - 146 + 62) - n, length2 + (67 + 118 - 113 + 56) - n, -0.009999999776482582).tex(1.0, 1.0).endVertex();
            worldRenderer.pos(length + (57 + 77 - 119 + 113) - n, length2 + "".length() + n, -0.009999999776482582).tex(1.0, 0.0).endVertex();
            worldRenderer.pos(length + "".length() + n, length2 + "".length() + n, -0.009999999776482582).tex(0.0, 0.0).endVertex();
            instance.draw();
            GlStateManager.enableAlpha();
            GlStateManager.disableBlend();
            MapItemRenderer.access$0(this.this$0).bindTexture(MapItemRenderer.access$1());
            int length3 = "".length();
            final Iterator<Vec4b> iterator = this.mapData.mapDecorations.values().iterator();
            "".length();
            if (3 <= 2) {
                throw null;
            }
            while (iterator.hasNext()) {
                final Vec4b vec4b = iterator.next();
                if (!b || vec4b.func_176110_a() == " ".length()) {
                    GlStateManager.pushMatrix();
                    GlStateManager.translate(length + vec4b.func_176112_b() / 2.0f + 64.0f, length2 + vec4b.func_176113_c() / 2.0f + 64.0f, -0.02f);
                    GlStateManager.rotate(vec4b.func_176111_d() * (183 + 28 + 136 + 13) / 16.0f, 0.0f, 0.0f, 1.0f);
                    GlStateManager.scale(4.0f, 4.0f, 3.0f);
                    GlStateManager.translate(-0.125f, 0.125f, 0.0f);
                    final byte func_176110_a = vec4b.func_176110_a();
                    final float n2 = (func_176110_a % (0x95 ^ 0x91) + "".length()) / 4.0f;
                    final float n3 = (func_176110_a / (0x42 ^ 0x46) + "".length()) / 4.0f;
                    final float n4 = (func_176110_a % (0x1E ^ 0x1A) + " ".length()) / 4.0f;
                    final float n5 = (func_176110_a / (0x32 ^ 0x36) + " ".length()) / 4.0f;
                    worldRenderer.begin(0x84 ^ 0x83, DefaultVertexFormats.POSITION_TEX);
                    worldRenderer.pos(-1.0, 1.0, length3 * -0.001f).tex(n2, n3).endVertex();
                    worldRenderer.pos(1.0, 1.0, length3 * -0.001f).tex(n4, n3).endVertex();
                    worldRenderer.pos(1.0, -1.0, length3 * -0.001f).tex(n4, n5).endVertex();
                    worldRenderer.pos(-1.0, -1.0, length3 * -0.001f).tex(n2, n5).endVertex();
                    instance.draw();
                    GlStateManager.popMatrix();
                    ++length3;
                }
            }
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0f, 0.0f, -0.04f);
            GlStateManager.scale(1.0f, 1.0f, 1.0f);
            GlStateManager.popMatrix();
        }
        
        static void access$1(final Instance instance, final boolean b) {
            instance.render(b);
        }
        
        private static void I() {
            (I = new String[" ".length()])["".length()] = I("*\u00179~", "GvIQv");
        }
        
        private void updateMapTexture() {
            int i = "".length();
            "".length();
            if (0 < 0) {
                throw null;
            }
            while (i < 4941 + 2593 - 2420 + 11270) {
                final int n = this.mapData.colors[i] & 138 + 110 - 120 + 127;
                if (n / (0x7D ^ 0x79) == 0) {
                    this.mapTextureData[i] = (i + i / (109 + 6 - 39 + 52) & " ".length()) * (0x4 ^ 0xC) + (0xAC ^ 0xBC) << (0xAE ^ 0xB6);
                    "".length();
                    if (-1 >= 4) {
                        throw null;
                    }
                }
                else {
                    this.mapTextureData[i] = MapColor.mapColorArray[n / (0x1A ^ 0x1E)].func_151643_b(n & "   ".length());
                }
                ++i;
            }
            this.mapTexture.updateDynamicTexture();
        }
        
        static ResourceLocation access$3(final Instance instance) {
            return instance.location;
        }
        
        Instance(final MapItemRenderer mapItemRenderer, final MapData mapData, final Instance instance) {
            this(mapItemRenderer, mapData);
        }
        
        static {
            I();
        }
        
        private Instance(final MapItemRenderer this$0, final MapData mapData) {
            this.this$0 = this$0;
            this.mapData = mapData;
            this.mapTexture = new DynamicTexture(105 + 120 - 97 + 0, 56 + 71 - 41 + 42);
            this.mapTextureData = this.mapTexture.getTextureData();
            this.location = MapItemRenderer.access$0(this$0).getDynamicTextureLocation(Instance.I["".length()] + mapData.mapName, this.mapTexture);
            int i = "".length();
            "".length();
            if (-1 >= 2) {
                throw null;
            }
            while (i < this.mapTextureData.length) {
                this.mapTextureData[i] = "".length();
                ++i;
            }
        }
    }
}
