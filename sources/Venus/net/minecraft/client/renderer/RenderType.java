/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer;

import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.objects.ObjectOpenCustomHashSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.tileentity.EndPortalTileEntityRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.optifine.Config;
import net.optifine.EmissiveTextures;
import net.optifine.RandomEntities;
import net.optifine.SmartAnimations;
import net.optifine.render.RenderStateManager;
import net.optifine.render.RenderUtils;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.ShadersRender;
import net.optifine.util.CompareUtils;
import net.optifine.util.CompoundKey;

public abstract class RenderType
extends RenderState {
    private static final RenderType SOLID = RenderType.makeType("solid", DefaultVertexFormats.BLOCK, 7, 0x200000, true, false, State.getBuilder().shadeModel(SHADE_ENABLED).lightmap(LIGHTMAP_ENABLED).texture(BLOCK_SHEET_MIPPED).build(false));
    private static final RenderType CUTOUT_MIPPED = RenderType.makeType("cutout_mipped", DefaultVertexFormats.BLOCK, 7, 131072, true, false, State.getBuilder().shadeModel(SHADE_ENABLED).lightmap(LIGHTMAP_ENABLED).texture(BLOCK_SHEET_MIPPED).alpha(CUTOUT_MIPPED_ALPHA).build(false));
    private static final RenderType CUTOUT = RenderType.makeType("cutout", DefaultVertexFormats.BLOCK, 7, 131072, true, false, State.getBuilder().shadeModel(SHADE_ENABLED).lightmap(LIGHTMAP_ENABLED).texture(BLOCK_SHEET).alpha(CUTOUT_MIPPED_ALPHA).build(false));
    private static final RenderType TRANSLUCENT = RenderType.makeType("translucent", DefaultVertexFormats.BLOCK, 7, 262144, true, true, RenderType.getTranslucentState());
    private static final RenderType TRANSLUCENT_MOVING__BLOCK = RenderType.makeType("translucent_moving_block", DefaultVertexFormats.BLOCK, 7, 262144, false, true, RenderType.getItemEntityState());
    private static final RenderType TRANSLUCENT_NO_CRUMBLING = RenderType.makeType("translucent_no_crumbling", DefaultVertexFormats.BLOCK, 7, 262144, false, true, RenderType.getTranslucentState());
    private static final RenderType LEASH = RenderType.makeType("leash", DefaultVertexFormats.POSITION_COLOR_LIGHTMAP, 7, 256, State.getBuilder().texture(NO_TEXTURE).cull(CULL_DISABLED).lightmap(LIGHTMAP_ENABLED).build(true));
    private static final RenderType WATER_MASK = RenderType.makeType("water_mask", DefaultVertexFormats.POSITION, 7, 256, State.getBuilder().texture(NO_TEXTURE).writeMask(DEPTH_WRITE).build(true));
    private static final RenderType ARMOR_GLINT = RenderType.makeType("armor_glint", DefaultVertexFormats.POSITION_TEX, 7, 256, State.getBuilder().texture(new RenderState.TextureState(ItemRenderer.RES_ITEM_GLINT, true, false)).writeMask(COLOR_WRITE).cull(CULL_DISABLED).depthTest(DEPTH_EQUAL).transparency(GLINT_TRANSPARENCY).texturing(GLINT_TEXTURING).layer(field_239235_M_).build(true));
    private static final RenderType ARMOR_ENTITY_GLINT = RenderType.makeType("armor_entity_glint", DefaultVertexFormats.POSITION_TEX, 7, 256, State.getBuilder().texture(new RenderState.TextureState(ItemRenderer.RES_ITEM_GLINT, true, false)).writeMask(COLOR_WRITE).cull(CULL_DISABLED).depthTest(DEPTH_EQUAL).transparency(GLINT_TRANSPARENCY).texturing(ENTITY_GLINT_TEXTURING).layer(field_239235_M_).build(true));
    private static final RenderType GLINT_TRANSLUCENT = RenderType.makeType("glint_translucent", DefaultVertexFormats.POSITION_TEX, 7, 256, State.getBuilder().texture(new RenderState.TextureState(ItemRenderer.RES_ITEM_GLINT, true, false)).writeMask(COLOR_WRITE).cull(CULL_DISABLED).depthTest(DEPTH_EQUAL).transparency(GLINT_TRANSPARENCY).texturing(GLINT_TEXTURING).target(field_241712_U_).build(true));
    private static final RenderType GLINT = RenderType.makeType("glint", DefaultVertexFormats.POSITION_TEX, 7, 256, State.getBuilder().texture(new RenderState.TextureState(ItemRenderer.RES_ITEM_GLINT, true, false)).writeMask(COLOR_WRITE).cull(CULL_DISABLED).depthTest(DEPTH_EQUAL).transparency(GLINT_TRANSPARENCY).texturing(GLINT_TEXTURING).build(true));
    private static final RenderType GLINT_DIRECT = RenderType.makeType("glint_direct", DefaultVertexFormats.POSITION_TEX, 7, 256, State.getBuilder().texture(new RenderState.TextureState(ItemRenderer.RES_ITEM_GLINT, true, false)).writeMask(COLOR_WRITE).cull(CULL_DISABLED).depthTest(DEPTH_EQUAL).transparency(GLINT_TRANSPARENCY).texturing(GLINT_TEXTURING).build(true));
    private static final RenderType ENTITY_GLINT = RenderType.makeType("entity_glint", DefaultVertexFormats.POSITION_TEX, 7, 256, State.getBuilder().texture(new RenderState.TextureState(ItemRenderer.RES_ITEM_GLINT, true, false)).writeMask(COLOR_WRITE).cull(CULL_DISABLED).depthTest(DEPTH_EQUAL).transparency(GLINT_TRANSPARENCY).target(field_241712_U_).texturing(ENTITY_GLINT_TEXTURING).build(true));
    private static final RenderType ENTITY_GLINT_DIRECT = RenderType.makeType("entity_glint_direct", DefaultVertexFormats.POSITION_TEX, 7, 256, State.getBuilder().texture(new RenderState.TextureState(ItemRenderer.RES_ITEM_GLINT, true, false)).writeMask(COLOR_WRITE).cull(CULL_DISABLED).depthTest(DEPTH_EQUAL).transparency(GLINT_TRANSPARENCY).texturing(ENTITY_GLINT_TEXTURING).build(true));
    private static final RenderType LIGHTNING = RenderType.makeType("lightning", DefaultVertexFormats.POSITION_COLOR, 7, 256, false, true, State.getBuilder().writeMask(COLOR_DEPTH_WRITE).transparency(LIGHTNING_TRANSPARENCY).target(field_239238_U_).shadeModel(SHADE_ENABLED).build(true));
    private static final RenderType TRIPWIRE = RenderType.makeType("tripwire", DefaultVertexFormats.BLOCK, 7, 262144, true, true, RenderType.getWeatherState());
    public static final Type LINES = RenderType.makeType("lines", DefaultVertexFormats.POSITION_COLOR, 1, 256, State.getBuilder().line(new RenderState.LineState(OptionalDouble.empty())).layer(field_239235_M_).transparency(TRANSLUCENT_TRANSPARENCY).target(field_241712_U_).writeMask(COLOR_DEPTH_WRITE).build(true));
    private final VertexFormat vertexFormat;
    private final int drawMode;
    private final int bufferSize;
    private final boolean useDelegate;
    private final boolean needsSorting;
    private final Optional<RenderType> renderType;
    private int id = -1;
    public static final RenderType[] CHUNK_RENDER_TYPES = RenderType.getChunkRenderTypesArray();
    private static Map<CompoundKey, RenderType> RENDER_TYPES;

    public int ordinal() {
        return this.id;
    }

    public boolean isNeedsSorting() {
        return this.needsSorting;
    }

    private static RenderType[] getChunkRenderTypesArray() {
        RenderType[] renderTypeArray = RenderType.getBlockRenderTypes().toArray(new RenderType[0]);
        int n = 0;
        while (n < renderTypeArray.length) {
            RenderType renderType = renderTypeArray[n];
            renderType.id = n++;
        }
        return renderTypeArray;
    }

    public static RenderType getSolid() {
        return SOLID;
    }

    public static RenderType getCutoutMipped() {
        return CUTOUT_MIPPED;
    }

    public static RenderType getCutout() {
        return CUTOUT;
    }

    private static State getTranslucentState() {
        return State.getBuilder().shadeModel(SHADE_ENABLED).lightmap(LIGHTMAP_ENABLED).texture(BLOCK_SHEET_MIPPED).transparency(TRANSLUCENT_TRANSPARENCY).target(field_239236_S_).build(false);
    }

    public static RenderType getTranslucent() {
        return TRANSLUCENT;
    }

    private static State getItemEntityState() {
        return State.getBuilder().shadeModel(SHADE_ENABLED).lightmap(LIGHTMAP_ENABLED).texture(BLOCK_SHEET_MIPPED).transparency(TRANSLUCENT_TRANSPARENCY).target(field_241712_U_).build(false);
    }

    public static RenderType getTranslucentMovingBlock() {
        return TRANSLUCENT_MOVING__BLOCK;
    }

    public static RenderType getTranslucentNoCrumbling() {
        return TRANSLUCENT_NO_CRUMBLING;
    }

    public static RenderType getArmorCutoutNoCull(ResourceLocation resourceLocation) {
        ResourceLocation resourceLocation2 = resourceLocation = RenderType.getCustomTexture(resourceLocation);
        return RenderType.getRenderType("armor_cutout_no_cull", resourceLocation, () -> RenderType.lambda$getArmorCutoutNoCull$0(resourceLocation2));
    }

    public static RenderType getEntitySolid(ResourceLocation resourceLocation) {
        ResourceLocation resourceLocation2 = resourceLocation = RenderType.getCustomTexture(resourceLocation);
        return RenderType.getRenderType("entity_solid", resourceLocation, () -> RenderType.lambda$getEntitySolid$1(resourceLocation2));
    }

    public static RenderType getEntityCutout(ResourceLocation resourceLocation) {
        ResourceLocation resourceLocation2 = resourceLocation = RenderType.getCustomTexture(resourceLocation);
        return RenderType.getRenderType("entity_cutout", resourceLocation, () -> RenderType.lambda$getEntityCutout$2(resourceLocation2));
    }

    public static RenderType getEntityCutoutNoCull(ResourceLocation resourceLocation, boolean bl) {
        ResourceLocation resourceLocation2 = resourceLocation = RenderType.getCustomTexture(resourceLocation);
        return RenderType.getRenderType("entity_cutout_no_cull", resourceLocation, bl, () -> RenderType.lambda$getEntityCutoutNoCull$3(resourceLocation2, bl));
    }

    public static RenderType getEntityCutoutNoCull(ResourceLocation resourceLocation) {
        return RenderType.getEntityCutoutNoCull(resourceLocation, true);
    }

    public static RenderType getEntityCutoutNoCullZOffset(ResourceLocation resourceLocation, boolean bl) {
        ResourceLocation resourceLocation2 = resourceLocation = RenderType.getCustomTexture(resourceLocation);
        return RenderType.getRenderType("entity_cutout_no_cull_z_offset", resourceLocation, bl, () -> RenderType.lambda$getEntityCutoutNoCullZOffset$4(resourceLocation2, bl));
    }

    public static RenderType getEntityCutoutNoCullZOffset(ResourceLocation resourceLocation) {
        return RenderType.getEntityCutoutNoCullZOffset(resourceLocation, true);
    }

    public static RenderType getItemEntityTranslucentCull(ResourceLocation resourceLocation) {
        ResourceLocation resourceLocation2 = resourceLocation = RenderType.getCustomTexture(resourceLocation);
        return RenderType.getRenderType("item_entity_translucent_cull", resourceLocation, () -> RenderType.lambda$getItemEntityTranslucentCull$5(resourceLocation2));
    }

    public static RenderType getEntityTranslucentCull(ResourceLocation resourceLocation) {
        ResourceLocation resourceLocation2 = resourceLocation = RenderType.getCustomTexture(resourceLocation);
        return RenderType.getRenderType("entity_translucent_cull", resourceLocation, () -> RenderType.lambda$getEntityTranslucentCull$6(resourceLocation2));
    }

    public static RenderType getEntityTranslucent(ResourceLocation resourceLocation, boolean bl) {
        ResourceLocation resourceLocation2 = resourceLocation = RenderType.getCustomTexture(resourceLocation);
        return RenderType.getRenderType("entity_translucent", resourceLocation, bl, () -> RenderType.lambda$getEntityTranslucent$7(resourceLocation2, bl));
    }

    public static RenderType getEntityTranslucent(ResourceLocation resourceLocation) {
        return RenderType.getEntityTranslucent(resourceLocation, true);
    }

    public static RenderType getEntitySmoothCutout(ResourceLocation resourceLocation) {
        ResourceLocation resourceLocation2 = resourceLocation = RenderType.getCustomTexture(resourceLocation);
        return RenderType.getRenderType("entity_smooth_cutout", resourceLocation, () -> RenderType.lambda$getEntitySmoothCutout$8(resourceLocation2));
    }

    public static RenderType getBeaconBeam(ResourceLocation resourceLocation, boolean bl) {
        ResourceLocation resourceLocation2 = resourceLocation = RenderType.getCustomTexture(resourceLocation);
        return RenderType.getRenderType("beacon_beam", resourceLocation, bl, () -> RenderType.lambda$getBeaconBeam$9(resourceLocation2, bl));
    }

    public static RenderType getEntityDecal(ResourceLocation resourceLocation) {
        ResourceLocation resourceLocation2 = resourceLocation = RenderType.getCustomTexture(resourceLocation);
        return RenderType.getRenderType("entity_decal", resourceLocation, () -> RenderType.lambda$getEntityDecal$10(resourceLocation2));
    }

    public static RenderType getEntityNoOutline(ResourceLocation resourceLocation) {
        ResourceLocation resourceLocation2 = resourceLocation = RenderType.getCustomTexture(resourceLocation);
        return RenderType.getRenderType("entity_no_outline", resourceLocation, () -> RenderType.lambda$getEntityNoOutline$11(resourceLocation2));
    }

    public static RenderType getEntityShadow(ResourceLocation resourceLocation) {
        ResourceLocation resourceLocation2 = resourceLocation = RenderType.getCustomTexture(resourceLocation);
        return RenderType.getRenderType("entity_shadow", resourceLocation, () -> RenderType.lambda$getEntityShadow$12(resourceLocation2));
    }

    public static RenderType getEntityAlpha(ResourceLocation resourceLocation, float f) {
        ResourceLocation resourceLocation2 = resourceLocation = RenderType.getCustomTexture(resourceLocation);
        return RenderType.getRenderType("entity_alpha", resourceLocation, f, () -> RenderType.lambda$getEntityAlpha$13(resourceLocation2, f));
    }

    public static RenderType getEyes(ResourceLocation resourceLocation) {
        ResourceLocation resourceLocation2 = resourceLocation = RenderType.getCustomTexture(resourceLocation);
        return RenderType.getRenderType("eyes", resourceLocation, () -> RenderType.lambda$getEyes$14(resourceLocation2));
    }

    public static RenderType getEnergySwirl(ResourceLocation resourceLocation, float f, float f2) {
        ResourceLocation resourceLocation2 = resourceLocation = RenderType.getCustomTexture(resourceLocation);
        return RenderType.getRenderType("energy_swirl", resourceLocation, f, f2, () -> RenderType.lambda$getEnergySwirl$15(resourceLocation2, f, f2));
    }

    public static RenderType getLeash() {
        return LEASH;
    }

    public static RenderType getWaterMask() {
        return WATER_MASK;
    }

    public static RenderType getOutline(ResourceLocation resourceLocation) {
        return RenderType.getOutline(resourceLocation, CULL_DISABLED);
    }

    public static RenderType getOutline(ResourceLocation resourceLocation, RenderState.CullState cullState) {
        return RenderType.getRenderType("outline", resourceLocation, cullState == CULL_ENABLED, () -> RenderType.lambda$getOutline$16(resourceLocation, cullState));
    }

    public static RenderType getArmorGlint() {
        return ARMOR_GLINT;
    }

    public static RenderType getArmorEntityGlint() {
        return ARMOR_ENTITY_GLINT;
    }

    public static RenderType getGlintTranslucent() {
        return GLINT_TRANSLUCENT;
    }

    public static RenderType getGlint() {
        return GLINT;
    }

    public static RenderType getGlintDirect() {
        return GLINT_DIRECT;
    }

    public static RenderType getEntityGlint() {
        return ENTITY_GLINT;
    }

    public static RenderType getEntityGlintDirect() {
        return ENTITY_GLINT_DIRECT;
    }

    public static RenderType getCrumbling(ResourceLocation resourceLocation) {
        return RenderType.getRenderType("crumbling", resourceLocation, () -> RenderType.lambda$getCrumbling$17(resourceLocation));
    }

    public static RenderType getText(ResourceLocation resourceLocation) {
        return RenderType.getRenderType("text", resourceLocation, () -> RenderType.lambda$getText$18(resourceLocation));
    }

    public static RenderType getTextSeeThrough(ResourceLocation resourceLocation) {
        return RenderType.getRenderType("text_see_through", resourceLocation, () -> RenderType.lambda$getTextSeeThrough$19(resourceLocation));
    }

    public static RenderType getLightning() {
        return LIGHTNING;
    }

    private static State getWeatherState() {
        return State.getBuilder().shadeModel(SHADE_ENABLED).lightmap(LIGHTMAP_ENABLED).texture(BLOCK_SHEET_MIPPED).transparency(TRANSLUCENT_TRANSPARENCY).target(field_239238_U_).build(false);
    }

    public static RenderType getTripwire() {
        return TRIPWIRE;
    }

    public static RenderType getEndPortal(int n) {
        return RenderType.getRenderType("end_portal", n, () -> RenderType.lambda$getEndPortal$20(n));
    }

    public static RenderType getLines() {
        return LINES;
    }

    public RenderType(String string, VertexFormat vertexFormat, int n, int n2, boolean bl, boolean bl2, Runnable runnable, Runnable runnable2) {
        super(string, runnable, runnable2);
        this.vertexFormat = vertexFormat;
        this.drawMode = n;
        this.bufferSize = n2;
        this.useDelegate = bl;
        this.needsSorting = bl2;
        this.renderType = Optional.of(this);
    }

    public static Type makeType(String string, VertexFormat vertexFormat, int n, int n2, State state) {
        return RenderType.makeType(string, vertexFormat, n, n2, false, false, state);
    }

    public static Type makeType(String string, VertexFormat vertexFormat, int n, int n2, boolean bl, boolean bl2, State state) {
        return Type.getOrCreate(string, vertexFormat, n, n2, bl, bl2, state);
    }

    public void finish(BufferBuilder bufferBuilder, int n, int n2, int n3) {
        if (bufferBuilder.isDrawing()) {
            if (this.needsSorting) {
                bufferBuilder.sortVertexData(n, n2, n3);
            }
            if (bufferBuilder.animatedSprites != null) {
                SmartAnimations.spritesRendered(bufferBuilder.animatedSprites);
            }
            bufferBuilder.finishDrawing();
            this.setupRenderState();
            if (Config.isShaders()) {
                RenderUtils.setFlushRenderBuffers(false);
                Shaders.pushProgram();
                ShadersRender.preRender(this, bufferBuilder);
            }
            WorldVertexBufferUploader.draw(bufferBuilder);
            if (Config.isShaders()) {
                ShadersRender.postRender(this, bufferBuilder);
                Shaders.popProgram();
                RenderUtils.setFlushRenderBuffers(true);
            }
            this.clearRenderState();
        }
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static List<RenderType> getBlockRenderTypes() {
        return ImmutableList.of(RenderType.getSolid(), RenderType.getCutoutMipped(), RenderType.getCutout(), RenderType.getTranslucent(), RenderType.getTripwire());
    }

    public int getBufferSize() {
        return this.bufferSize;
    }

    public VertexFormat getVertexFormat() {
        return this.vertexFormat;
    }

    public int getDrawMode() {
        return this.drawMode;
    }

    public Optional<RenderType> getOutline() {
        return Optional.empty();
    }

    public boolean isColoredOutlineBuffer() {
        return true;
    }

    public boolean isUseDelegate() {
        return this.useDelegate;
    }

    public Optional<RenderType> getRenderType() {
        return this.renderType;
    }

    private static RenderType getRenderType(String string, ResourceLocation resourceLocation, Supplier<RenderType> supplier) {
        CompoundKey compoundKey = new CompoundKey(string, resourceLocation);
        return RenderType.getRenderType(compoundKey, supplier);
    }

    private static RenderType getRenderType(String string, ResourceLocation resourceLocation, boolean bl, Supplier<RenderType> supplier) {
        CompoundKey compoundKey = new CompoundKey(string, resourceLocation, bl);
        return RenderType.getRenderType(compoundKey, supplier);
    }

    private static RenderType getRenderType(String string, ResourceLocation resourceLocation, float f, Supplier<RenderType> supplier) {
        CompoundKey compoundKey = new CompoundKey(string, resourceLocation, Float.valueOf(f));
        return RenderType.getRenderType(compoundKey, supplier);
    }

    private static RenderType getRenderType(String string, ResourceLocation resourceLocation, float f, float f2, Supplier<RenderType> supplier) {
        CompoundKey compoundKey = new CompoundKey(string, resourceLocation, Float.valueOf(f), Float.valueOf(f2));
        return RenderType.getRenderType(compoundKey, supplier);
    }

    private static RenderType getRenderType(String string, int n, Supplier<RenderType> supplier) {
        CompoundKey compoundKey = new CompoundKey(string, n);
        return RenderType.getRenderType(compoundKey, supplier);
    }

    private static RenderType getRenderType(CompoundKey compoundKey, Supplier<RenderType> supplier) {
        RenderType renderType;
        if (RENDER_TYPES == null) {
            RENDER_TYPES = new HashMap<CompoundKey, RenderType>();
        }
        if ((renderType = RENDER_TYPES.get(compoundKey)) != null) {
            return renderType;
        }
        renderType = supplier.get();
        RENDER_TYPES.put(compoundKey, renderType);
        return renderType;
    }

    public static ResourceLocation getCustomTexture(ResourceLocation resourceLocation) {
        if (Config.isRandomEntities()) {
            resourceLocation = RandomEntities.getTextureLocation(resourceLocation);
        }
        if (EmissiveTextures.isActive()) {
            resourceLocation = EmissiveTextures.getEmissiveTexture(resourceLocation);
        }
        return resourceLocation;
    }

    public boolean isEntitySolid() {
        return this.getName().equals("entity_solid");
    }

    public static int getCountRenderStates() {
        return RenderType.LINES.renderState.renderStates.size();
    }

    public ResourceLocation getTextureLocation() {
        return null;
    }

    public boolean isGlint() {
        return this.getTextureLocation() == ItemRenderer.RES_ITEM_GLINT;
    }

    private static RenderType lambda$getEndPortal$20(int n) {
        RenderState.TextureState textureState;
        RenderState.TransparencyState transparencyState;
        if (n <= 1) {
            transparencyState = TRANSLUCENT_TRANSPARENCY;
            textureState = new RenderState.TextureState(EndPortalTileEntityRenderer.END_SKY_TEXTURE, false, false);
        } else {
            transparencyState = ADDITIVE_TRANSPARENCY;
            textureState = new RenderState.TextureState(EndPortalTileEntityRenderer.END_PORTAL_TEXTURE, false, false);
        }
        return RenderType.makeType("end_portal", DefaultVertexFormats.POSITION_COLOR, 7, 256, false, true, State.getBuilder().transparency(transparencyState).texture(textureState).texturing(new RenderState.PortalTexturingState(n)).fog(BLACK_FOG).build(true));
    }

    private static RenderType lambda$getTextSeeThrough$19(ResourceLocation resourceLocation) {
        return RenderType.makeType("text_see_through", DefaultVertexFormats.POSITION_COLOR_TEX_LIGHTMAP, 7, 256, false, false, State.getBuilder().texture(new RenderState.TextureState(resourceLocation, false, false)).alpha(DEFAULT_ALPHA).transparency(TRANSLUCENT_TRANSPARENCY).lightmap(LIGHTMAP_ENABLED).depthTest(DEPTH_ALWAYS).writeMask(COLOR_WRITE).build(true));
    }

    private static RenderType lambda$getText$18(ResourceLocation resourceLocation) {
        return RenderType.makeType("text", DefaultVertexFormats.POSITION_COLOR_TEX_LIGHTMAP, 7, 256, false, false, State.getBuilder().texture(new RenderState.TextureState(resourceLocation, false, false)).alpha(DEFAULT_ALPHA).transparency(TRANSLUCENT_TRANSPARENCY).lightmap(LIGHTMAP_ENABLED).build(true));
    }

    private static RenderType lambda$getCrumbling$17(ResourceLocation resourceLocation) {
        RenderState.TextureState textureState = new RenderState.TextureState(resourceLocation, false, false);
        return RenderType.makeType("crumbling", DefaultVertexFormats.BLOCK, 7, 256, false, true, State.getBuilder().texture(textureState).alpha(DEFAULT_ALPHA).transparency(CRUMBLING_TRANSPARENCY).writeMask(COLOR_WRITE).layer(POLYGON_OFFSET_LAYERING).build(true));
    }

    private static RenderType lambda$getOutline$16(ResourceLocation resourceLocation, RenderState.CullState cullState) {
        return RenderType.makeType("outline", DefaultVertexFormats.POSITION_COLOR_TEX, 7, 256, State.getBuilder().texture(new RenderState.TextureState(resourceLocation, false, false)).cull(cullState).depthTest(DEPTH_ALWAYS).alpha(DEFAULT_ALPHA).texturing(OUTLINE_TEXTURING).fog(NO_FOG).target(OUTLINE_TARGET).func_230173_a_(OutlineState.IS_OUTLINE));
    }

    private static RenderType lambda$getEnergySwirl$15(ResourceLocation resourceLocation, float f, float f2) {
        return RenderType.makeType("energy_swirl", DefaultVertexFormats.ENTITY, 7, 256, false, true, State.getBuilder().texture(new RenderState.TextureState(resourceLocation, false, false)).texturing(new RenderState.OffsetTexturingState(f, f2)).fog(BLACK_FOG).transparency(ADDITIVE_TRANSPARENCY).diffuseLighting(DIFFUSE_LIGHTING_ENABLED).alpha(DEFAULT_ALPHA).cull(CULL_DISABLED).lightmap(LIGHTMAP_ENABLED).overlay(OVERLAY_ENABLED).build(true));
    }

    private static RenderType lambda$getEyes$14(ResourceLocation resourceLocation) {
        RenderState.TextureState textureState = new RenderState.TextureState(resourceLocation, false, false);
        return RenderType.makeType("eyes", DefaultVertexFormats.ENTITY, 7, 256, false, true, State.getBuilder().texture(textureState).transparency(ADDITIVE_TRANSPARENCY).writeMask(COLOR_WRITE).fog(BLACK_FOG).build(true));
    }

    private static RenderType lambda$getEntityAlpha$13(ResourceLocation resourceLocation, float f) {
        State state = State.getBuilder().texture(new RenderState.TextureState(resourceLocation, false, false)).alpha(new RenderState.AlphaState(f)).cull(CULL_DISABLED).build(false);
        return RenderType.makeType("entity_alpha", DefaultVertexFormats.ENTITY, 7, 256, state);
    }

    private static RenderType lambda$getEntityShadow$12(ResourceLocation resourceLocation) {
        State state = State.getBuilder().texture(new RenderState.TextureState(resourceLocation, false, false)).transparency(TRANSLUCENT_TRANSPARENCY).diffuseLighting(DIFFUSE_LIGHTING_ENABLED).alpha(DEFAULT_ALPHA).cull(CULL_ENABLED).lightmap(LIGHTMAP_ENABLED).overlay(OVERLAY_ENABLED).writeMask(COLOR_WRITE).depthTest(DEPTH_LEQUAL).layer(field_239235_M_).build(true);
        return RenderType.makeType("entity_shadow", DefaultVertexFormats.ENTITY, 7, 256, false, false, state);
    }

    private static RenderType lambda$getEntityNoOutline$11(ResourceLocation resourceLocation) {
        State state = State.getBuilder().texture(new RenderState.TextureState(resourceLocation, false, false)).transparency(TRANSLUCENT_TRANSPARENCY).diffuseLighting(DIFFUSE_LIGHTING_ENABLED).alpha(DEFAULT_ALPHA).cull(CULL_DISABLED).lightmap(LIGHTMAP_ENABLED).overlay(OVERLAY_ENABLED).writeMask(COLOR_WRITE).build(true);
        return RenderType.makeType("entity_no_outline", DefaultVertexFormats.ENTITY, 7, 256, false, true, state);
    }

    private static RenderType lambda$getEntityDecal$10(ResourceLocation resourceLocation) {
        State state = State.getBuilder().texture(new RenderState.TextureState(resourceLocation, false, false)).diffuseLighting(DIFFUSE_LIGHTING_ENABLED).alpha(DEFAULT_ALPHA).depthTest(DEPTH_EQUAL).cull(CULL_DISABLED).lightmap(LIGHTMAP_ENABLED).overlay(OVERLAY_ENABLED).build(true);
        return RenderType.makeType("entity_decal", DefaultVertexFormats.ENTITY, 7, 256, state);
    }

    private static RenderType lambda$getBeaconBeam$9(ResourceLocation resourceLocation, boolean bl) {
        State state = State.getBuilder().texture(new RenderState.TextureState(resourceLocation, false, false)).transparency(bl ? TRANSLUCENT_TRANSPARENCY : NO_TRANSPARENCY).writeMask(bl ? COLOR_WRITE : COLOR_DEPTH_WRITE).fog(NO_FOG).build(true);
        return RenderType.makeType("beacon_beam", DefaultVertexFormats.BLOCK, 7, 256, false, true, state);
    }

    private static RenderType lambda$getEntitySmoothCutout$8(ResourceLocation resourceLocation) {
        State state = State.getBuilder().texture(new RenderState.TextureState(resourceLocation, false, false)).alpha(HALF_ALPHA).diffuseLighting(DIFFUSE_LIGHTING_ENABLED).shadeModel(SHADE_ENABLED).cull(CULL_DISABLED).lightmap(LIGHTMAP_ENABLED).build(false);
        return RenderType.makeType("entity_smooth_cutout", DefaultVertexFormats.ENTITY, 7, 256, state);
    }

    private static RenderType lambda$getEntityTranslucent$7(ResourceLocation resourceLocation, boolean bl) {
        State state = State.getBuilder().texture(new RenderState.TextureState(resourceLocation, false, false)).transparency(TRANSLUCENT_TRANSPARENCY).diffuseLighting(DIFFUSE_LIGHTING_ENABLED).alpha(DEFAULT_ALPHA).cull(CULL_DISABLED).lightmap(LIGHTMAP_ENABLED).overlay(OVERLAY_ENABLED).build(bl);
        return RenderType.makeType("entity_translucent", DefaultVertexFormats.ENTITY, 7, 256, true, true, state);
    }

    private static RenderType lambda$getEntityTranslucentCull$6(ResourceLocation resourceLocation) {
        State state = State.getBuilder().texture(new RenderState.TextureState(resourceLocation, false, false)).transparency(TRANSLUCENT_TRANSPARENCY).diffuseLighting(DIFFUSE_LIGHTING_ENABLED).alpha(DEFAULT_ALPHA).lightmap(LIGHTMAP_ENABLED).overlay(OVERLAY_ENABLED).build(false);
        return RenderType.makeType("entity_translucent_cull", DefaultVertexFormats.ENTITY, 7, 256, true, true, state);
    }

    private static RenderType lambda$getItemEntityTranslucentCull$5(ResourceLocation resourceLocation) {
        State state = State.getBuilder().texture(new RenderState.TextureState(resourceLocation, false, false)).transparency(TRANSLUCENT_TRANSPARENCY).target(field_241712_U_).diffuseLighting(DIFFUSE_LIGHTING_ENABLED).alpha(DEFAULT_ALPHA).lightmap(LIGHTMAP_ENABLED).overlay(OVERLAY_ENABLED).writeMask(RenderState.COLOR_DEPTH_WRITE).build(false);
        return RenderType.makeType("item_entity_translucent_cull", DefaultVertexFormats.ENTITY, 7, 256, true, true, state);
    }

    private static RenderType lambda$getEntityCutoutNoCullZOffset$4(ResourceLocation resourceLocation, boolean bl) {
        State state = State.getBuilder().texture(new RenderState.TextureState(resourceLocation, false, false)).transparency(NO_TRANSPARENCY).diffuseLighting(DIFFUSE_LIGHTING_ENABLED).alpha(DEFAULT_ALPHA).cull(CULL_DISABLED).lightmap(LIGHTMAP_ENABLED).overlay(OVERLAY_ENABLED).layer(field_239235_M_).build(bl);
        return RenderType.makeType("entity_cutout_no_cull_z_offset", DefaultVertexFormats.ENTITY, 7, 256, true, false, state);
    }

    private static RenderType lambda$getEntityCutoutNoCull$3(ResourceLocation resourceLocation, boolean bl) {
        State state = State.getBuilder().texture(new RenderState.TextureState(resourceLocation, false, false)).transparency(NO_TRANSPARENCY).diffuseLighting(DIFFUSE_LIGHTING_ENABLED).alpha(DEFAULT_ALPHA).cull(CULL_DISABLED).lightmap(LIGHTMAP_ENABLED).overlay(OVERLAY_ENABLED).build(bl);
        return RenderType.makeType("entity_cutout_no_cull", DefaultVertexFormats.ENTITY, 7, 256, true, false, state);
    }

    private static RenderType lambda$getEntityCutout$2(ResourceLocation resourceLocation) {
        State state = State.getBuilder().texture(new RenderState.TextureState(resourceLocation, false, false)).transparency(NO_TRANSPARENCY).diffuseLighting(DIFFUSE_LIGHTING_ENABLED).alpha(DEFAULT_ALPHA).lightmap(LIGHTMAP_ENABLED).overlay(OVERLAY_ENABLED).build(false);
        return RenderType.makeType("entity_cutout", DefaultVertexFormats.ENTITY, 7, 256, true, false, state);
    }

    private static RenderType lambda$getEntitySolid$1(ResourceLocation resourceLocation) {
        State state = State.getBuilder().texture(new RenderState.TextureState(resourceLocation, false, false)).transparency(NO_TRANSPARENCY).diffuseLighting(DIFFUSE_LIGHTING_ENABLED).lightmap(LIGHTMAP_ENABLED).overlay(OVERLAY_ENABLED).build(false);
        return RenderType.makeType("entity_solid", DefaultVertexFormats.ENTITY, 7, 256, true, false, state);
    }

    private static RenderType lambda$getArmorCutoutNoCull$0(ResourceLocation resourceLocation) {
        State state = State.getBuilder().texture(new RenderState.TextureState(resourceLocation, false, false)).transparency(NO_TRANSPARENCY).diffuseLighting(DIFFUSE_LIGHTING_ENABLED).alpha(DEFAULT_ALPHA).cull(CULL_DISABLED).lightmap(LIGHTMAP_ENABLED).overlay(OVERLAY_ENABLED).layer(field_239235_M_).build(false);
        return RenderType.makeType("armor_cutout_no_cull", DefaultVertexFormats.ENTITY, 7, 256, true, false, state);
    }

    public static final class State {
        private final RenderState.TextureState texture;
        private final RenderState.TransparencyState transparency;
        private final RenderState.DiffuseLightingState diffuseLighting;
        private final RenderState.ShadeModelState shadowModel;
        private final RenderState.AlphaState alpha;
        private final RenderState.DepthTestState depthTest;
        private final RenderState.CullState cull;
        private final RenderState.LightmapState lightmap;
        private final RenderState.OverlayState overlay;
        private final RenderState.FogState fog;
        private final RenderState.LayerState layer;
        private final RenderState.TargetState target;
        private final RenderState.TexturingState texturing;
        private final RenderState.WriteMaskState writeMask;
        private final RenderState.LineState line;
        private final OutlineState field_230171_p_;
        private final ImmutableList<RenderState> renderStates;

        private State(RenderState.TextureState textureState, RenderState.TransparencyState transparencyState, RenderState.DiffuseLightingState diffuseLightingState, RenderState.ShadeModelState shadeModelState, RenderState.AlphaState alphaState, RenderState.DepthTestState depthTestState, RenderState.CullState cullState, RenderState.LightmapState lightmapState, RenderState.OverlayState overlayState, RenderState.FogState fogState, RenderState.LayerState layerState, RenderState.TargetState targetState, RenderState.TexturingState texturingState, RenderState.WriteMaskState writeMaskState, RenderState.LineState lineState, OutlineState outlineState) {
            this.texture = textureState;
            this.transparency = transparencyState;
            this.diffuseLighting = diffuseLightingState;
            this.shadowModel = shadeModelState;
            this.alpha = alphaState;
            this.depthTest = depthTestState;
            this.cull = cullState;
            this.lightmap = lightmapState;
            this.overlay = overlayState;
            this.fog = fogState;
            this.layer = layerState;
            this.target = targetState;
            this.texturing = texturingState;
            this.writeMask = writeMaskState;
            this.line = lineState;
            this.field_230171_p_ = outlineState;
            this.renderStates = ImmutableList.of(this.texture, this.transparency, this.diffuseLighting, this.shadowModel, this.alpha, this.depthTest, this.cull, this.lightmap, this.overlay, this.fog, this.layer, this.target, this.texturing, this.writeMask, this.line);
        }

        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            if (object != null && this.getClass() == object.getClass()) {
                State state = (State)object;
                return this.field_230171_p_ == state.field_230171_p_ && this.renderStates.equals(state.renderStates);
            }
            return true;
        }

        public int hashCode() {
            return CompareUtils.hash(this.renderStates, (Object)this.field_230171_p_);
        }

        public String toString() {
            return "CompositeState[" + this.renderStates + ", outlineProperty=" + this.field_230171_p_ + "]";
        }

        public static Builder getBuilder() {
            return new Builder();
        }

        public Builder getCopyBuilder() {
            Builder builder = new Builder();
            builder.texture(this.texture);
            builder.transparency(this.transparency);
            builder.diffuseLighting(this.diffuseLighting);
            builder.shadeModel(this.shadowModel);
            builder.alpha(this.alpha);
            builder.depthTest(this.depthTest);
            builder.cull(this.cull);
            builder.lightmap(this.lightmap);
            builder.overlay(this.overlay);
            builder.fog(this.fog);
            builder.layer(this.layer);
            builder.target(this.target);
            builder.texturing(this.texturing);
            builder.writeMask(this.writeMask);
            builder.line(this.line);
            return builder;
        }

        public static class Builder {
            private RenderState.TextureState texture = RenderState.NO_TEXTURE;
            private RenderState.TransparencyState transparency = RenderState.NO_TRANSPARENCY;
            private RenderState.DiffuseLightingState diffuseLighting = RenderState.DIFFUSE_LIGHTING_DISABLED;
            private RenderState.ShadeModelState shadeModel = RenderState.SHADE_DISABLED;
            private RenderState.AlphaState alpha = RenderState.ZERO_ALPHA;
            private RenderState.DepthTestState depthTest = RenderState.DEPTH_LEQUAL;
            private RenderState.CullState cull = RenderState.CULL_ENABLED;
            private RenderState.LightmapState lightmap = RenderState.LIGHTMAP_DISABLED;
            private RenderState.OverlayState overlay = RenderState.OVERLAY_DISABLED;
            private RenderState.FogState fog = RenderState.FOG;
            private RenderState.LayerState layer = RenderState.NO_LAYERING;
            private RenderState.TargetState target = RenderState.MAIN_TARGET;
            private RenderState.TexturingState texturing = RenderState.DEFAULT_TEXTURING;
            private RenderState.WriteMaskState writeMask = RenderState.COLOR_DEPTH_WRITE;
            private RenderState.LineState line = RenderState.DEFAULT_LINE;

            private Builder() {
            }

            public Builder texture(RenderState.TextureState textureState) {
                this.texture = textureState;
                return this;
            }

            public Builder transparency(RenderState.TransparencyState transparencyState) {
                this.transparency = transparencyState;
                return this;
            }

            public Builder diffuseLighting(RenderState.DiffuseLightingState diffuseLightingState) {
                this.diffuseLighting = diffuseLightingState;
                return this;
            }

            public Builder shadeModel(RenderState.ShadeModelState shadeModelState) {
                this.shadeModel = shadeModelState;
                return this;
            }

            public Builder alpha(RenderState.AlphaState alphaState) {
                this.alpha = alphaState;
                return this;
            }

            public Builder depthTest(RenderState.DepthTestState depthTestState) {
                this.depthTest = depthTestState;
                return this;
            }

            public Builder cull(RenderState.CullState cullState) {
                this.cull = cullState;
                return this;
            }

            public Builder lightmap(RenderState.LightmapState lightmapState) {
                this.lightmap = lightmapState;
                return this;
            }

            public Builder overlay(RenderState.OverlayState overlayState) {
                this.overlay = overlayState;
                return this;
            }

            public Builder fog(RenderState.FogState fogState) {
                this.fog = fogState;
                return this;
            }

            public Builder layer(RenderState.LayerState layerState) {
                this.layer = layerState;
                return this;
            }

            public Builder target(RenderState.TargetState targetState) {
                this.target = targetState;
                return this;
            }

            public Builder texturing(RenderState.TexturingState texturingState) {
                this.texturing = texturingState;
                return this;
            }

            public Builder writeMask(RenderState.WriteMaskState writeMaskState) {
                this.writeMask = writeMaskState;
                return this;
            }

            public Builder line(RenderState.LineState lineState) {
                this.line = lineState;
                return this;
            }

            public State build(boolean bl) {
                return this.func_230173_a_(bl ? OutlineState.AFFECTS_OUTLINE : OutlineState.NONE);
            }

            public State func_230173_a_(OutlineState outlineState) {
                return new State(this.texture, this.transparency, this.diffuseLighting, this.shadeModel, this.alpha, this.depthTest, this.cull, this.lightmap, this.overlay, this.fog, this.layer, this.target, this.texturing, this.writeMask, this.line, outlineState);
            }
        }
    }

    static final class Type
    extends RenderType {
        private static final ObjectOpenCustomHashSet<Type> TYPES = new ObjectOpenCustomHashSet<Type>(EqualityStrategy.INSTANCE);
        private final State renderState;
        private final int hashCode;
        private final Optional<RenderType> outlineRenderType;
        private final boolean field_230170_V_;
        private Map<ResourceLocation, Type> mapTextured = new HashMap<ResourceLocation, Type>();

        private Type(String string, VertexFormat vertexFormat, int n, int n2, boolean bl, boolean bl2, State state) {
            super(string, vertexFormat, n, n2, bl, bl2, () -> Type.lambda$new$0(state), () -> Type.lambda$new$1(state));
            this.renderState = state;
            this.outlineRenderType = state.field_230171_p_ == OutlineState.AFFECTS_OUTLINE ? state.texture.texture().map(arg_0 -> Type.lambda$new$2(state, arg_0)) : Optional.empty();
            this.field_230170_V_ = state.field_230171_p_ == OutlineState.IS_OUTLINE;
            this.hashCode = CompareUtils.hash(super.hashCode(), (Object)state);
        }

        private static Type getOrCreate(String string, VertexFormat vertexFormat, int n, int n2, boolean bl, boolean bl2, State state) {
            return TYPES.addOrGet(new Type(string, vertexFormat, n, n2, bl, bl2, state));
        }

        @Override
        public Optional<RenderType> getOutline() {
            return this.outlineRenderType;
        }

        @Override
        public boolean isColoredOutlineBuffer() {
            return this.field_230170_V_;
        }

        @Override
        public boolean equals(@Nullable Object object) {
            return this == object;
        }

        @Override
        public int hashCode() {
            return this.hashCode;
        }

        @Override
        public String toString() {
            return this.name + ":RenderType[" + this.renderState + ", ]";
        }

        public Type getTextured(ResourceLocation resourceLocation) {
            if (resourceLocation == null) {
                return this;
            }
            Optional<ResourceLocation> optional = this.renderState.texture.texture();
            if (!optional.isPresent()) {
                return this;
            }
            ResourceLocation resourceLocation2 = optional.get();
            if (resourceLocation2 == null) {
                return this;
            }
            if (resourceLocation.equals(resourceLocation2)) {
                return this;
            }
            Type type = this.mapTextured.get(resourceLocation);
            if (type == null) {
                State.Builder builder = this.renderState.getCopyBuilder();
                builder.texture(new RenderState.TextureState(resourceLocation, this.renderState.texture.isBlur(), this.renderState.texture.isMipmap()));
                State state = builder.build(this.field_230170_V_);
                type = Type.makeType(this.name, this.getVertexFormat(), this.getDrawMode(), this.getBufferSize(), this.isUseDelegate(), this.isNeedsSorting(), state);
                this.mapTextured.put(resourceLocation, type);
            }
            return type;
        }

        @Override
        public ResourceLocation getTextureLocation() {
            Optional<ResourceLocation> optional = this.renderState.texture.texture();
            return !optional.isPresent() ? null : optional.get();
        }

        private static RenderType lambda$new$2(State state, ResourceLocation resourceLocation) {
            return Type.getOutline(resourceLocation, state.cull);
        }

        private static void lambda$new$1(State state) {
            RenderStateManager.clearRenderStates(state.renderStates);
        }

        private static void lambda$new$0(State state) {
            RenderStateManager.setupRenderStates(state.renderStates);
        }

        static enum EqualityStrategy implements Hash.Strategy<Type>
        {
            INSTANCE;


            @Override
            public int hashCode(@Nullable Type type) {
                return type == null ? 0 : type.hashCode;
            }

            @Override
            public boolean equals(@Nullable Type type, @Nullable Type type2) {
                if (type == type2) {
                    return false;
                }
                return type != null && type2 != null ? Objects.equals(type.renderState, type2.renderState) : false;
            }

            @Override
            public boolean equals(@Nullable Object object, @Nullable Object object2) {
                return this.equals((Type)object, (Type)object2);
            }

            @Override
            public int hashCode(@Nullable Object object) {
                return this.hashCode((Type)object);
            }
        }
    }

    static enum OutlineState {
        NONE("none"),
        IS_OUTLINE("is_outline"),
        AFFECTS_OUTLINE("affects_outline");

        private final String name;

        private OutlineState(String string2) {
            this.name = string2;
        }

        public String toString() {
            return this.name;
        }
    }
}

