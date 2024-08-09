/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.storage.MapData;
import net.minecraft.world.storage.MapDecoration;

public class MapItemRenderer
implements AutoCloseable {
    private static final ResourceLocation TEXTURE_MAP_ICONS = new ResourceLocation("textures/map/map_icons.png");
    private static final RenderType field_228085_d_ = RenderType.getText(TEXTURE_MAP_ICONS);
    private final TextureManager textureManager;
    private final Map<String, Instance> loadedMaps = Maps.newHashMap();

    public MapItemRenderer(TextureManager textureManager) {
        this.textureManager = textureManager;
    }

    public void updateMapTexture(MapData mapData) {
        this.getMapRendererInstance(mapData).updateMapTexture();
    }

    public void renderMap(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, MapData mapData, boolean bl, int n) {
        this.getMapRendererInstance(mapData).func_228089_a_(matrixStack, iRenderTypeBuffer, bl, n);
    }

    private Instance getMapRendererInstance(MapData mapData) {
        Instance instance = this.loadedMaps.get(mapData.getName());
        if (instance == null) {
            instance = new Instance(this, mapData);
            this.loadedMaps.put(mapData.getName(), instance);
        }
        return instance;
    }

    @Nullable
    public Instance getMapInstanceIfExists(String string) {
        return this.loadedMaps.get(string);
    }

    public void clearLoadedMaps() {
        for (Instance instance : this.loadedMaps.values()) {
            instance.close();
        }
        this.loadedMaps.clear();
    }

    @Nullable
    public MapData getData(@Nullable Instance instance) {
        return instance != null ? instance.mapData : null;
    }

    @Override
    public void close() {
        this.clearLoadedMaps();
    }

    class Instance
    implements AutoCloseable {
        private final MapData mapData;
        private final DynamicTexture mapTexture;
        private final RenderType field_228088_d_;
        final MapItemRenderer this$0;

        private Instance(MapItemRenderer mapItemRenderer, MapData mapData) {
            this.this$0 = mapItemRenderer;
            this.mapData = mapData;
            this.mapTexture = new DynamicTexture(128, 128, true);
            ResourceLocation resourceLocation = mapItemRenderer.textureManager.getDynamicTextureLocation("map/" + mapData.getName(), this.mapTexture);
            this.field_228088_d_ = RenderType.getText(resourceLocation);
        }

        private void updateMapTexture() {
            for (int i = 0; i < 128; ++i) {
                for (int j = 0; j < 128; ++j) {
                    int n = j + i * 128;
                    int n2 = this.mapData.colors[n] & 0xFF;
                    if (n2 / 4 == 0) {
                        this.mapTexture.getTextureData().setPixelRGBA(j, i, 0);
                        continue;
                    }
                    if (MaterialColor.COLORS == null) {
                        this.mapTexture.getTextureData().setPixelRGBA(j, i, MaterialColor.COLORS[n2 / 4].getMapColor(n2 & 3));
                        continue;
                    }
                    this.mapTexture.getTextureData().setPixelRGBA(j, i, MaterialColor.TEST[MathHelper.clamp(n2 / 4, 0, MaterialColor.TEST.length - 1)].getMapColor(n2 & 3));
                }
            }
            this.mapTexture.updateDynamicTexture();
        }

        private void func_228089_a_(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, boolean bl, int n) {
            boolean bl2 = false;
            boolean bl3 = false;
            float f = 0.0f;
            Matrix4f matrix4f = matrixStack.getLast().getMatrix();
            IVertexBuilder iVertexBuilder = iRenderTypeBuffer.getBuffer(this.field_228088_d_);
            iVertexBuilder.pos(matrix4f, 0.0f, 128.0f, -0.01f).color(255, 255, 255, 255).tex(0.0f, 1.0f).lightmap(n).endVertex();
            iVertexBuilder.pos(matrix4f, 128.0f, 128.0f, -0.01f).color(255, 255, 255, 255).tex(1.0f, 1.0f).lightmap(n).endVertex();
            iVertexBuilder.pos(matrix4f, 128.0f, 0.0f, -0.01f).color(255, 255, 255, 255).tex(1.0f, 0.0f).lightmap(n).endVertex();
            iVertexBuilder.pos(matrix4f, 0.0f, 0.0f, -0.01f).color(255, 255, 255, 255).tex(0.0f, 0.0f).lightmap(n).endVertex();
            int n2 = 0;
            for (MapDecoration mapDecoration : this.mapData.mapDecorations.values()) {
                if (bl && !mapDecoration.renderOnFrame()) continue;
                matrixStack.push();
                matrixStack.translate(0.0f + (float)mapDecoration.getX() / 2.0f + 64.0f, 0.0f + (float)mapDecoration.getY() / 2.0f + 64.0f, -0.02f);
                matrixStack.rotate(Vector3f.ZP.rotationDegrees((float)(mapDecoration.getRotation() * 360) / 16.0f));
                matrixStack.scale(4.0f, 4.0f, 3.0f);
                matrixStack.translate(-0.125, 0.125, 0.0);
                byte by = mapDecoration.getImage();
                float f2 = (float)(by % 16 + 0) / 16.0f;
                float f3 = (float)(by / 16 + 0) / 16.0f;
                float f4 = (float)(by % 16 + 1) / 16.0f;
                float f5 = (float)(by / 16 + 1) / 16.0f;
                Matrix4f matrix4f2 = matrixStack.getLast().getMatrix();
                float f6 = -0.001f;
                IVertexBuilder iVertexBuilder2 = iRenderTypeBuffer.getBuffer(field_228085_d_);
                iVertexBuilder2.pos(matrix4f2, -1.0f, 1.0f, (float)n2 * -0.001f).color(255, 255, 255, 255).tex(f2, f3).lightmap(n).endVertex();
                iVertexBuilder2.pos(matrix4f2, 1.0f, 1.0f, (float)n2 * -0.001f).color(255, 255, 255, 255).tex(f4, f3).lightmap(n).endVertex();
                iVertexBuilder2.pos(matrix4f2, 1.0f, -1.0f, (float)n2 * -0.001f).color(255, 255, 255, 255).tex(f4, f5).lightmap(n).endVertex();
                iVertexBuilder2.pos(matrix4f2, -1.0f, -1.0f, (float)n2 * -0.001f).color(255, 255, 255, 255).tex(f2, f5).lightmap(n).endVertex();
                matrixStack.pop();
                if (mapDecoration.getCustomName() != null) {
                    FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;
                    ITextComponent iTextComponent = mapDecoration.getCustomName();
                    float f7 = fontRenderer.getStringPropertyWidth(iTextComponent);
                    float f8 = MathHelper.clamp(25.0f / f7, 0.0f, 0.6666667f);
                    matrixStack.push();
                    matrixStack.translate(0.0f + (float)mapDecoration.getX() / 2.0f + 64.0f - f7 * f8 / 2.0f, 0.0f + (float)mapDecoration.getY() / 2.0f + 64.0f + 4.0f, -0.025f);
                    matrixStack.scale(f8, f8, 1.0f);
                    matrixStack.translate(0.0, 0.0, -0.1f);
                    fontRenderer.func_243247_a(iTextComponent, 0.0f, 0.0f, -1, false, matrixStack.getLast().getMatrix(), iRenderTypeBuffer, false, Integer.MIN_VALUE, n);
                    matrixStack.pop();
                }
                ++n2;
            }
        }

        @Override
        public void close() {
            this.mapTexture.close();
        }
    }
}

