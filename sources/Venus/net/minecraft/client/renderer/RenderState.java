/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.optifine.util.CompareUtils;

public abstract class RenderState {
    protected final String name;
    private final Runnable setupTask;
    private final Runnable clearTask;
    protected static final TransparencyState NO_TRANSPARENCY = new TransparencyState("no_transparency", RenderState::lambda$static$0, RenderState::lambda$static$1);
    protected static final TransparencyState ADDITIVE_TRANSPARENCY = new TransparencyState("additive_transparency", RenderState::lambda$static$2, RenderState::lambda$static$3);
    protected static final TransparencyState LIGHTNING_TRANSPARENCY = new TransparencyState("lightning_transparency", RenderState::lambda$static$4, RenderState::lambda$static$5);
    protected static final TransparencyState GLINT_TRANSPARENCY = new TransparencyState("glint_transparency", RenderState::lambda$static$6, RenderState::lambda$static$7);
    protected static final TransparencyState CRUMBLING_TRANSPARENCY = new TransparencyState("crumbling_transparency", RenderState::lambda$static$8, RenderState::lambda$static$9);
    protected static final TransparencyState TRANSLUCENT_TRANSPARENCY = new TransparencyState("translucent_transparency", RenderState::lambda$static$10, RenderState::lambda$static$11);
    protected static final AlphaState ZERO_ALPHA = new AlphaState(0.0f);
    protected static final AlphaState DEFAULT_ALPHA = new AlphaState(0.003921569f);
    protected static final AlphaState HALF_ALPHA = new AlphaState(0.5f);
    protected static final AlphaState CUTOUT_MIPPED_ALPHA = new AlphaState(0.1f);
    protected static final ShadeModelState SHADE_DISABLED = new ShadeModelState(false);
    protected static final ShadeModelState SHADE_ENABLED = new ShadeModelState(true);
    protected static final TextureState BLOCK_SHEET_MIPPED = new TextureState(AtlasTexture.LOCATION_BLOCKS_TEXTURE, false, true);
    protected static final TextureState BLOCK_SHEET = new TextureState(AtlasTexture.LOCATION_BLOCKS_TEXTURE, false, false);
    protected static final TextureState NO_TEXTURE = new TextureState();
    protected static final TexturingState DEFAULT_TEXTURING = new TexturingState("default_texturing", RenderState::lambda$static$12, RenderState::lambda$static$13);
    protected static final TexturingState OUTLINE_TEXTURING = new TexturingState("outline_texturing", RenderState::lambda$static$14, RenderState::lambda$static$15);
    protected static final TexturingState GLINT_TEXTURING = new TexturingState("glint_texturing", RenderState::lambda$static$16, RenderState::lambda$static$17);
    protected static final TexturingState ENTITY_GLINT_TEXTURING = new TexturingState("entity_glint_texturing", RenderState::lambda$static$18, RenderState::lambda$static$19);
    protected static final LightmapState LIGHTMAP_ENABLED = new LightmapState(true);
    protected static final LightmapState LIGHTMAP_DISABLED = new LightmapState(false);
    protected static final OverlayState OVERLAY_ENABLED = new OverlayState(true);
    protected static final OverlayState OVERLAY_DISABLED = new OverlayState(false);
    protected static final DiffuseLightingState DIFFUSE_LIGHTING_ENABLED = new DiffuseLightingState(true);
    protected static final DiffuseLightingState DIFFUSE_LIGHTING_DISABLED = new DiffuseLightingState(false);
    protected static final CullState CULL_ENABLED = new CullState(true);
    protected static final CullState CULL_DISABLED = new CullState(false);
    protected static final DepthTestState DEPTH_ALWAYS = new DepthTestState("always", 519);
    protected static final DepthTestState DEPTH_EQUAL = new DepthTestState("==", 514);
    protected static final DepthTestState DEPTH_LEQUAL = new DepthTestState("<=", 515);
    protected static final WriteMaskState COLOR_DEPTH_WRITE = new WriteMaskState(true, true);
    protected static final WriteMaskState COLOR_WRITE = new WriteMaskState(true, false);
    protected static final WriteMaskState DEPTH_WRITE = new WriteMaskState(false, true);
    protected static final LayerState NO_LAYERING = new LayerState("no_layering", RenderState::lambda$static$20, RenderState::lambda$static$21);
    protected static final LayerState POLYGON_OFFSET_LAYERING = new LayerState("polygon_offset_layering", RenderState::lambda$static$22, RenderState::lambda$static$23);
    protected static final LayerState field_239235_M_ = new LayerState("view_offset_z_layering", RenderState::lambda$static$24, RenderSystem::popMatrix);
    protected static final FogState NO_FOG = new FogState("no_fog", RenderState::lambda$static$25, RenderState::lambda$static$26);
    protected static final FogState FOG = new FogState("fog", RenderState::lambda$static$27, RenderState::lambda$static$28);
    protected static final FogState BLACK_FOG = new FogState("black_fog", RenderState::lambda$static$29, RenderState::lambda$static$30);
    protected static final TargetState MAIN_TARGET = new TargetState("main_target", RenderState::lambda$static$31, RenderState::lambda$static$32);
    protected static final TargetState OUTLINE_TARGET = new TargetState("outline_target", RenderState::lambda$static$33, RenderState::lambda$static$34);
    protected static final TargetState field_239236_S_ = new TargetState("translucent_target", RenderState::lambda$static$35, RenderState::lambda$static$36);
    protected static final TargetState field_239237_T_ = new TargetState("particles_target", RenderState::lambda$static$37, RenderState::lambda$static$38);
    protected static final TargetState field_239238_U_ = new TargetState("weather_target", RenderState::lambda$static$39, RenderState::lambda$static$40);
    protected static final TargetState field_239239_V_ = new TargetState("clouds_target", RenderState::lambda$static$41, RenderState::lambda$static$42);
    protected static final TargetState field_241712_U_ = new TargetState("item_entity_target", RenderState::lambda$static$43, RenderState::lambda$static$44);
    protected static final LineState DEFAULT_LINE = new LineState(OptionalDouble.of(1.0));

    public RenderState(String string, Runnable runnable, Runnable runnable2) {
        this.name = string;
        this.setupTask = runnable;
        this.clearTask = runnable2;
    }

    public void setupRenderState() {
        this.setupTask.run();
    }

    public void clearRenderState() {
        this.clearTask.run();
    }

    public boolean equals(@Nullable Object object) {
        if (this == object) {
            return false;
        }
        if (object != null && this.getClass() == object.getClass()) {
            RenderState renderState = (RenderState)object;
            return this.name.equals(renderState.name);
        }
        return true;
    }

    public int hashCode() {
        return this.name.hashCode();
    }

    public String toString() {
        return this.name;
    }

    private static void setupGlintTexturing(float f) {
        RenderSystem.matrixMode(5890);
        RenderSystem.pushMatrix();
        RenderSystem.loadIdentity();
        long l = Util.milliTime() * 8L;
        float f2 = (float)(l % 110000L) / 110000.0f;
        float f3 = (float)(l % 30000L) / 30000.0f;
        RenderSystem.translatef(-f2, f3, 0.0f);
        RenderSystem.rotatef(10.0f, 0.0f, 0.0f, 1.0f);
        RenderSystem.scalef(f, f, f);
        RenderSystem.matrixMode(5888);
    }

    public String getName() {
        return this.name;
    }

    private static void lambda$static$44() {
        if (Minecraft.isFabulousGraphicsEnabled()) {
            Minecraft.getInstance().getFramebuffer().bindFramebuffer(true);
        }
    }

    private static void lambda$static$43() {
        if (Minecraft.isFabulousGraphicsEnabled()) {
            Minecraft.getInstance().worldRenderer.func_239229_r_().bindFramebuffer(true);
        }
    }

    private static void lambda$static$42() {
        if (Minecraft.isFabulousGraphicsEnabled()) {
            Minecraft.getInstance().getFramebuffer().bindFramebuffer(true);
        }
    }

    private static void lambda$static$41() {
        if (Minecraft.isFabulousGraphicsEnabled()) {
            Minecraft.getInstance().worldRenderer.func_239232_u_().bindFramebuffer(true);
        }
    }

    private static void lambda$static$40() {
        if (Minecraft.isFabulousGraphicsEnabled()) {
            Minecraft.getInstance().getFramebuffer().bindFramebuffer(true);
        }
    }

    private static void lambda$static$39() {
        if (Minecraft.isFabulousGraphicsEnabled()) {
            Minecraft.getInstance().worldRenderer.func_239231_t_().bindFramebuffer(true);
        }
    }

    private static void lambda$static$38() {
        if (Minecraft.isFabulousGraphicsEnabled()) {
            Minecraft.getInstance().getFramebuffer().bindFramebuffer(true);
        }
    }

    private static void lambda$static$37() {
        if (Minecraft.isFabulousGraphicsEnabled()) {
            Minecraft.getInstance().worldRenderer.func_239230_s_().bindFramebuffer(true);
        }
    }

    private static void lambda$static$36() {
        if (Minecraft.isFabulousGraphicsEnabled()) {
            Minecraft.getInstance().getFramebuffer().bindFramebuffer(true);
        }
    }

    private static void lambda$static$35() {
        if (Minecraft.isFabulousGraphicsEnabled()) {
            Minecraft.getInstance().worldRenderer.func_239228_q_().bindFramebuffer(true);
        }
    }

    private static void lambda$static$34() {
        Minecraft.getInstance().getFramebuffer().bindFramebuffer(true);
    }

    private static void lambda$static$33() {
        Minecraft.getInstance().worldRenderer.getEntityOutlineFramebuffer().bindFramebuffer(true);
    }

    private static void lambda$static$32() {
    }

    private static void lambda$static$31() {
    }

    private static void lambda$static$30() {
        FogRenderer.applyFog();
        RenderSystem.disableFog();
    }

    private static void lambda$static$29() {
        RenderSystem.fog(2918, 0.0f, 0.0f, 0.0f, 1.0f);
        RenderSystem.enableFog();
    }

    private static void lambda$static$28() {
        RenderSystem.disableFog();
    }

    private static void lambda$static$27() {
        FogRenderer.applyFog();
        RenderSystem.enableFog();
    }

    private static void lambda$static$26() {
    }

    private static void lambda$static$25() {
    }

    private static void lambda$static$24() {
        RenderSystem.pushMatrix();
        RenderSystem.scalef(0.99975586f, 0.99975586f, 0.99975586f);
    }

    private static void lambda$static$23() {
        RenderSystem.polygonOffset(0.0f, 0.0f);
        RenderSystem.disablePolygonOffset();
    }

    private static void lambda$static$22() {
        RenderSystem.polygonOffset(-1.0f, -10.0f);
        RenderSystem.enablePolygonOffset();
    }

    private static void lambda$static$21() {
    }

    private static void lambda$static$20() {
    }

    private static void lambda$static$19() {
        RenderSystem.matrixMode(5890);
        RenderSystem.popMatrix();
        RenderSystem.matrixMode(5888);
    }

    private static void lambda$static$18() {
        RenderState.setupGlintTexturing(0.16f);
    }

    private static void lambda$static$17() {
        RenderSystem.matrixMode(5890);
        RenderSystem.popMatrix();
        RenderSystem.matrixMode(5888);
    }

    private static void lambda$static$16() {
        RenderState.setupGlintTexturing(8.0f);
    }

    private static void lambda$static$15() {
        RenderSystem.teardownOutline();
    }

    private static void lambda$static$14() {
        RenderSystem.setupOutline();
    }

    private static void lambda$static$13() {
    }

    private static void lambda$static$12() {
    }

    private static void lambda$static$11() {
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
    }

    private static void lambda$static$10() {
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    }

    private static void lambda$static$9() {
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
    }

    private static void lambda$static$8() {
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.DST_COLOR, GlStateManager.DestFactor.SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
    }

    private static void lambda$static$7() {
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
    }

    private static void lambda$static$6() {
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
    }

    private static void lambda$static$5() {
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
    }

    private static void lambda$static$4() {
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
    }

    private static void lambda$static$3() {
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
    }

    private static void lambda$static$2() {
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
    }

    private static void lambda$static$1() {
    }

    private static void lambda$static$0() {
        RenderSystem.disableBlend();
    }

    public static class TransparencyState
    extends RenderState {
        public TransparencyState(String string, Runnable runnable, Runnable runnable2) {
            super(string, runnable, runnable2);
        }
    }

    public static class AlphaState
    extends RenderState {
        private final float ref;

        public AlphaState(float f) {
            super("alpha", () -> AlphaState.lambda$new$0(f), AlphaState::lambda$new$1);
            this.ref = f;
        }

        @Override
        public boolean equals(@Nullable Object object) {
            if (this == object) {
                return false;
            }
            if (object != null && this.getClass() == object.getClass()) {
                if (!super.equals(object)) {
                    return true;
                }
                return this.ref == ((AlphaState)object).ref;
            }
            return true;
        }

        @Override
        public int hashCode() {
            return CompareUtils.hash(super.hashCode(), this.ref);
        }

        @Override
        public String toString() {
            return this.name + "[" + this.ref + "]";
        }

        private static void lambda$new$1() {
            RenderSystem.disableAlphaTest();
            RenderSystem.defaultAlphaFunc();
        }

        private static void lambda$new$0(float f) {
            if (f > 0.0f) {
                RenderSystem.enableAlphaTest();
                RenderSystem.alphaFunc(516, f);
            } else {
                RenderSystem.disableAlphaTest();
            }
        }
    }

    public static class ShadeModelState
    extends RenderState {
        private final boolean smooth;

        public ShadeModelState(boolean bl) {
            super("shade_model", () -> ShadeModelState.lambda$new$0(bl), ShadeModelState::lambda$new$1);
            this.smooth = bl;
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            if (object != null && this.getClass() == object.getClass()) {
                ShadeModelState shadeModelState = (ShadeModelState)object;
                return this.smooth == shadeModelState.smooth;
            }
            return true;
        }

        @Override
        public int hashCode() {
            return Boolean.hashCode(this.smooth);
        }

        @Override
        public String toString() {
            return this.name + "[" + (this.smooth ? "smooth" : "flat") + "]";
        }

        private static void lambda$new$1() {
            RenderSystem.shadeModel(7424);
        }

        private static void lambda$new$0(boolean bl) {
            RenderSystem.shadeModel(bl ? 7425 : 7424);
        }
    }

    public static class TextureState
    extends RenderState {
        private final Optional<ResourceLocation> texture;
        private final boolean blur;
        private final boolean mipmap;

        public TextureState(ResourceLocation resourceLocation, boolean bl, boolean bl2) {
            super("texture", () -> TextureState.lambda$new$0(resourceLocation, bl, bl2), TextureState::lambda$new$1);
            this.texture = Optional.of(resourceLocation);
            this.blur = bl;
            this.mipmap = bl2;
        }

        public TextureState() {
            super("texture", TextureState::lambda$new$2, TextureState::lambda$new$3);
            this.texture = Optional.empty();
            this.blur = false;
            this.mipmap = false;
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            if (object != null && this.getClass() == object.getClass()) {
                TextureState textureState = (TextureState)object;
                return this.texture.equals(textureState.texture) && this.blur == textureState.blur && this.mipmap == textureState.mipmap;
            }
            return true;
        }

        @Override
        public int hashCode() {
            return this.texture.hashCode();
        }

        @Override
        public String toString() {
            return this.name + "[" + this.texture + "(blur=" + this.blur + ", mipmap=" + this.mipmap + ")]";
        }

        protected Optional<ResourceLocation> texture() {
            return this.texture;
        }

        public boolean isBlur() {
            return this.blur;
        }

        public boolean isMipmap() {
            return this.mipmap;
        }

        private static void lambda$new$3() {
            RenderSystem.enableTexture();
        }

        private static void lambda$new$2() {
            RenderSystem.disableTexture();
        }

        private static void lambda$new$1() {
        }

        private static void lambda$new$0(ResourceLocation resourceLocation, boolean bl, boolean bl2) {
            RenderSystem.enableTexture();
            TextureManager textureManager = Minecraft.getInstance().getTextureManager();
            textureManager.bindTexture(resourceLocation);
            textureManager.getBoundTexture().setBlurMipmapDirect(bl, bl2);
        }
    }

    public static class TexturingState
    extends RenderState {
        public TexturingState(String string, Runnable runnable, Runnable runnable2) {
            super(string, runnable, runnable2);
        }
    }

    public static class LightmapState
    extends BooleanState {
        public LightmapState(boolean bl) {
            super("lightmap", () -> LightmapState.lambda$new$0(bl), () -> LightmapState.lambda$new$1(bl), bl);
        }

        @Override
        public String toString() {
            return super.toString();
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public boolean equals(Object object) {
            return super.equals(object);
        }

        private static void lambda$new$1(boolean bl) {
            if (bl) {
                Minecraft.getInstance().gameRenderer.getLightTexture().disableLightmap();
            }
        }

        private static void lambda$new$0(boolean bl) {
            if (bl) {
                Minecraft.getInstance().gameRenderer.getLightTexture().enableLightmap();
            }
        }
    }

    public static class OverlayState
    extends BooleanState {
        public OverlayState(boolean bl) {
            super("overlay", () -> OverlayState.lambda$new$0(bl), () -> OverlayState.lambda$new$1(bl), bl);
        }

        @Override
        public String toString() {
            return super.toString();
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public boolean equals(Object object) {
            return super.equals(object);
        }

        private static void lambda$new$1(boolean bl) {
            if (bl) {
                Minecraft.getInstance().gameRenderer.getOverlayTexture().teardownOverlayColor();
            }
        }

        private static void lambda$new$0(boolean bl) {
            if (bl) {
                Minecraft.getInstance().gameRenderer.getOverlayTexture().setupOverlayColor();
            }
        }
    }

    public static class DiffuseLightingState
    extends BooleanState {
        public DiffuseLightingState(boolean bl) {
            super("diffuse_lighting", () -> DiffuseLightingState.lambda$new$0(bl), () -> DiffuseLightingState.lambda$new$1(bl), bl);
        }

        @Override
        public String toString() {
            return super.toString();
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public boolean equals(Object object) {
            return super.equals(object);
        }

        private static void lambda$new$1(boolean bl) {
            if (bl) {
                RenderHelper.disableStandardItemLighting();
            }
        }

        private static void lambda$new$0(boolean bl) {
            if (bl) {
                RenderHelper.enableStandardItemLighting();
            }
        }
    }

    public static class CullState
    extends BooleanState {
        public CullState(boolean bl) {
            super("cull", () -> CullState.lambda$new$0(bl), () -> CullState.lambda$new$1(bl), bl);
        }

        @Override
        public String toString() {
            return super.toString();
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public boolean equals(Object object) {
            return super.equals(object);
        }

        private static void lambda$new$1(boolean bl) {
            if (!bl) {
                RenderSystem.enableCull();
            }
        }

        private static void lambda$new$0(boolean bl) {
            if (!bl) {
                RenderSystem.disableCull();
            }
        }
    }

    public static class DepthTestState
    extends RenderState {
        private final String field_239256_X_;
        private final int func;

        public DepthTestState(String string, int n) {
            super("depth_test", () -> DepthTestState.lambda$new$0(n), () -> DepthTestState.lambda$new$1(n));
            this.field_239256_X_ = string;
            this.func = n;
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            if (object != null && this.getClass() == object.getClass()) {
                DepthTestState depthTestState = (DepthTestState)object;
                return this.func == depthTestState.func;
            }
            return true;
        }

        @Override
        public int hashCode() {
            return Integer.hashCode(this.func);
        }

        @Override
        public String toString() {
            return this.name + "[" + this.field_239256_X_ + "]";
        }

        private static void lambda$new$1(int n) {
            if (n != 519) {
                RenderSystem.disableDepthTest();
                RenderSystem.depthFunc(515);
            }
        }

        private static void lambda$new$0(int n) {
            if (n != 519) {
                RenderSystem.enableDepthTest();
                RenderSystem.depthFunc(n);
            }
        }
    }

    public static class WriteMaskState
    extends RenderState {
        private final boolean colorMask;
        private final boolean depthMask;

        public WriteMaskState(boolean bl, boolean bl2) {
            super("write_mask_state", () -> WriteMaskState.lambda$new$0(bl2, bl), () -> WriteMaskState.lambda$new$1(bl2, bl));
            this.colorMask = bl;
            this.depthMask = bl2;
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            if (object != null && this.getClass() == object.getClass()) {
                WriteMaskState writeMaskState = (WriteMaskState)object;
                return this.colorMask == writeMaskState.colorMask && this.depthMask == writeMaskState.depthMask;
            }
            return true;
        }

        @Override
        public int hashCode() {
            return CompareUtils.hash(this.colorMask, this.depthMask);
        }

        @Override
        public String toString() {
            return this.name + "[writeColor=" + this.colorMask + ", writeDepth=" + this.depthMask + "]";
        }

        private static void lambda$new$1(boolean bl, boolean bl2) {
            if (!bl) {
                RenderSystem.depthMask(true);
            }
            if (!bl2) {
                RenderSystem.colorMask(true, true, true, true);
            }
        }

        private static void lambda$new$0(boolean bl, boolean bl2) {
            if (!bl) {
                RenderSystem.depthMask(bl);
            }
            if (!bl2) {
                RenderSystem.colorMask(bl2, bl2, bl2, bl2);
            }
        }
    }

    public static class LayerState
    extends RenderState {
        public LayerState(String string, Runnable runnable, Runnable runnable2) {
            super(string, runnable, runnable2);
        }
    }

    public static class FogState
    extends RenderState {
        public FogState(String string, Runnable runnable, Runnable runnable2) {
            super(string, runnable, runnable2);
        }
    }

    public static class TargetState
    extends RenderState {
        public TargetState(String string, Runnable runnable, Runnable runnable2) {
            super(string, runnable, runnable2);
        }
    }

    public static class LineState
    extends RenderState {
        private final OptionalDouble width;

        public LineState(OptionalDouble optionalDouble) {
            super("line_width", () -> LineState.lambda$new$0(optionalDouble), () -> LineState.lambda$new$1(optionalDouble));
            this.width = optionalDouble;
        }

        @Override
        public boolean equals(@Nullable Object object) {
            if (this == object) {
                return false;
            }
            if (object != null && this.getClass() == object.getClass()) {
                return !super.equals(object) ? false : Objects.equals(this.width, ((LineState)object).width);
            }
            return true;
        }

        @Override
        public int hashCode() {
            return CompareUtils.hash(super.hashCode(), (Object)this.width);
        }

        @Override
        public String toString() {
            return this.name + "[" + (Serializable)(this.width.isPresent() ? Double.valueOf(this.width.getAsDouble()) : "window_scale") + "]";
        }

        private static void lambda$new$1(OptionalDouble optionalDouble) {
            if (!Objects.equals(optionalDouble, OptionalDouble.of(1.0))) {
                RenderSystem.lineWidth(1.0f);
            }
        }

        private static void lambda$new$0(OptionalDouble optionalDouble) {
            if (!Objects.equals(optionalDouble, OptionalDouble.of(1.0))) {
                if (optionalDouble.isPresent()) {
                    RenderSystem.lineWidth((float)optionalDouble.getAsDouble());
                } else {
                    RenderSystem.lineWidth(Math.max(2.5f, (float)Minecraft.getInstance().getMainWindow().getFramebufferWidth() / 1920.0f * 2.5f));
                }
            }
        }
    }

    public static final class PortalTexturingState
    extends TexturingState {
        private final int iteration;

        public PortalTexturingState(int n) {
            super("portal_texturing", () -> PortalTexturingState.lambda$new$0(n), PortalTexturingState::lambda$new$1);
            this.iteration = n;
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            if (object != null && this.getClass() == object.getClass()) {
                PortalTexturingState portalTexturingState = (PortalTexturingState)object;
                return this.iteration == portalTexturingState.iteration;
            }
            return true;
        }

        @Override
        public int hashCode() {
            return Integer.hashCode(this.iteration);
        }

        private static void lambda$new$1() {
            RenderSystem.matrixMode(5890);
            RenderSystem.popMatrix();
            RenderSystem.matrixMode(5888);
            RenderSystem.clearTexGen();
        }

        private static void lambda$new$0(int n) {
            RenderSystem.matrixMode(5890);
            RenderSystem.pushMatrix();
            RenderSystem.loadIdentity();
            RenderSystem.translatef(0.5f, 0.5f, 0.0f);
            RenderSystem.scalef(0.5f, 0.5f, 1.0f);
            RenderSystem.translatef(17.0f / (float)n, (2.0f + (float)n / 1.5f) * ((float)(Util.milliTime() % 800000L) / 800000.0f), 0.0f);
            RenderSystem.rotatef(((float)(n * n) * 4321.0f + (float)n * 9.0f) * 2.0f, 0.0f, 0.0f, 1.0f);
            RenderSystem.scalef(4.5f - (float)n / 4.0f, 4.5f - (float)n / 4.0f, 1.0f);
            RenderSystem.mulTextureByProjModelView();
            RenderSystem.matrixMode(5888);
            RenderSystem.setupEndPortalTexGen();
        }
    }

    public static final class OffsetTexturingState
    extends TexturingState {
        private final float offsetU;
        private final float offsetV;

        public OffsetTexturingState(float f, float f2) {
            super("offset_texturing", () -> OffsetTexturingState.lambda$new$0(f, f2), OffsetTexturingState::lambda$new$1);
            this.offsetU = f;
            this.offsetV = f2;
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            if (object != null && this.getClass() == object.getClass()) {
                OffsetTexturingState offsetTexturingState = (OffsetTexturingState)object;
                return Float.compare(offsetTexturingState.offsetU, this.offsetU) == 0 && Float.compare(offsetTexturingState.offsetV, this.offsetV) == 0;
            }
            return true;
        }

        @Override
        public int hashCode() {
            return CompareUtils.hash(this.offsetU, this.offsetV);
        }

        private static void lambda$new$1() {
            RenderSystem.matrixMode(5890);
            RenderSystem.popMatrix();
            RenderSystem.matrixMode(5888);
        }

        private static void lambda$new$0(float f, float f2) {
            RenderSystem.matrixMode(5890);
            RenderSystem.pushMatrix();
            RenderSystem.loadIdentity();
            RenderSystem.translatef(f, f2, 0.0f);
            RenderSystem.matrixMode(5888);
        }
    }

    static class BooleanState
    extends RenderState {
        private final boolean enabled;

        public BooleanState(String string, Runnable runnable, Runnable runnable2, boolean bl) {
            super(string, runnable, runnable2);
            this.enabled = bl;
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            if (object != null && this.getClass() == object.getClass()) {
                BooleanState booleanState = (BooleanState)object;
                return this.enabled == booleanState.enabled;
            }
            return true;
        }

        @Override
        public int hashCode() {
            return Boolean.hashCode(this.enabled);
        }

        @Override
        public String toString() {
            return this.name + "[" + this.enabled + "]";
        }
    }
}

