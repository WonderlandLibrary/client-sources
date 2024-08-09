/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.FirstPersonRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.tileentity.EndPortalTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.math.vector.Vector4f;
import net.minecraft.world.chunk.Chunk;
import net.optifine.reflect.Reflector;
import net.optifine.render.GlBlendState;
import net.optifine.render.GlCullState;
import net.optifine.render.ICamera;
import net.optifine.render.RenderTypes;
import net.optifine.shaders.ClippingHelperDummy;
import net.optifine.shaders.DrawBuffers;
import net.optifine.shaders.GlState;
import net.optifine.shaders.RenderStage;
import net.optifine.shaders.Shaders;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class ShadersRender {
    private static final ResourceLocation END_PORTAL_TEXTURE = new ResourceLocation("textures/entity/end_portal.png");

    public static void setFrustrumPosition(ICamera iCamera, double d, double d2, double d3) {
        iCamera.setCameraPosition(d, d2, d3);
    }

    public static void beginTerrainSolid() {
        if (Shaders.isRenderingWorld) {
            Shaders.fogEnabled = true;
            Shaders.useProgram(Shaders.ProgramTerrain);
            Shaders.setRenderStage(RenderStage.TERRAIN_SOLID);
        }
    }

    public static void beginTerrainCutoutMipped() {
        if (Shaders.isRenderingWorld) {
            Shaders.useProgram(Shaders.ProgramTerrain);
            Shaders.setRenderStage(RenderStage.TERRAIN_CUTOUT_MIPPED);
        }
    }

    public static void beginTerrainCutout() {
        if (Shaders.isRenderingWorld) {
            Shaders.useProgram(Shaders.ProgramTerrain);
            Shaders.setRenderStage(RenderStage.TERRAIN_CUTOUT);
        }
    }

    public static void endTerrain() {
        if (Shaders.isRenderingWorld) {
            Shaders.useProgram(Shaders.ProgramTexturedLit);
            Shaders.setRenderStage(RenderStage.NONE);
        }
    }

    public static void beginTranslucent() {
        if (Shaders.isRenderingWorld) {
            Shaders.useProgram(Shaders.ProgramWater);
            Shaders.setRenderStage(RenderStage.TERRAIN_TRANSLUCENT);
        }
    }

    public static void endTranslucent() {
        if (Shaders.isRenderingWorld) {
            Shaders.useProgram(Shaders.ProgramTexturedLit);
            Shaders.setRenderStage(RenderStage.NONE);
        }
    }

    public static void beginTripwire() {
        if (Shaders.isRenderingWorld) {
            Shaders.setRenderStage(RenderStage.TRIPWIRE);
        }
    }

    public static void endTripwire() {
        if (Shaders.isRenderingWorld) {
            Shaders.setRenderStage(RenderStage.NONE);
        }
    }

    public static void renderHand0(GameRenderer gameRenderer, MatrixStack matrixStack, ActiveRenderInfo activeRenderInfo, float f) {
        if (!Shaders.isShadowPass) {
            boolean bl = Shaders.isItemToRenderMainTranslucent();
            boolean bl2 = Shaders.isItemToRenderOffTranslucent();
            if (!bl || !bl2) {
                Shaders.readCenterDepth();
                Shaders.beginHand(matrixStack, false);
                GL30.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                Shaders.setSkipRenderHands(bl, bl2);
                gameRenderer.renderHand(matrixStack, activeRenderInfo, f, true, false, true);
                Shaders.endHand(matrixStack);
                Shaders.setHandsRendered(!bl, !bl2);
                Shaders.setSkipRenderHands(false, false);
            }
        }
    }

    public static void renderHand1(GameRenderer gameRenderer, MatrixStack matrixStack, ActiveRenderInfo activeRenderInfo, float f) {
        if (!Shaders.isShadowPass && !Shaders.isBothHandsRendered()) {
            Shaders.readCenterDepth();
            GlStateManager.enableBlend();
            Shaders.beginHand(matrixStack, true);
            GL30.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            Shaders.setSkipRenderHands(Shaders.isHandRenderedMain(), Shaders.isHandRenderedOff());
            gameRenderer.renderHand(matrixStack, activeRenderInfo, f, true, false, false);
            Shaders.endHand(matrixStack);
            Shaders.setHandsRendered(true, true);
            Shaders.setSkipRenderHands(false, false);
        }
    }

    public static void renderItemFP(FirstPersonRenderer firstPersonRenderer, float f, MatrixStack matrixStack, IRenderTypeBuffer.Impl impl, ClientPlayerEntity clientPlayerEntity, int n, boolean bl) {
        Minecraft.getInstance().worldRenderer.renderedEntity = clientPlayerEntity;
        GlStateManager.depthMask(true);
        if (bl) {
            GlStateManager.depthFunc(519);
            matrixStack.push();
            DrawBuffers drawBuffers = GlState.getDrawBuffers();
            GlState.setDrawBuffers(Shaders.drawBuffersNone);
            Shaders.renderItemKeepDepthMask = true;
            firstPersonRenderer.renderItemInFirstPerson(f, matrixStack, impl, clientPlayerEntity, n);
            Shaders.renderItemKeepDepthMask = false;
            GlState.setDrawBuffers(drawBuffers);
            matrixStack.pop();
        }
        GlStateManager.depthFunc(515);
        GL30.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        firstPersonRenderer.renderItemInFirstPerson(f, matrixStack, impl, clientPlayerEntity, n);
        Minecraft.getInstance().worldRenderer.renderedEntity = null;
    }

    public static void renderFPOverlay(GameRenderer gameRenderer, MatrixStack matrixStack, ActiveRenderInfo activeRenderInfo, float f) {
        if (!Shaders.isShadowPass) {
            Shaders.beginFPOverlay();
            gameRenderer.renderHand(matrixStack, activeRenderInfo, f, false, true, true);
            Shaders.endFPOverlay();
        }
    }

    public static void beginBlockDamage() {
        if (Shaders.isRenderingWorld) {
            Shaders.useProgram(Shaders.ProgramDamagedBlock);
            Shaders.setRenderStage(RenderStage.DESTROY);
            if (Shaders.ProgramDamagedBlock.getId() == Shaders.ProgramTerrain.getId()) {
                GlState.setDrawBuffers(Shaders.drawBuffersColorAtt[0]);
                GlStateManager.depthMask(false);
            }
        }
    }

    public static void endBlockDamage() {
        if (Shaders.isRenderingWorld) {
            GlStateManager.depthMask(true);
            Shaders.useProgram(Shaders.ProgramTexturedLit);
            Shaders.setRenderStage(RenderStage.NONE);
        }
    }

    public static void beginOutline() {
        if (Shaders.isRenderingWorld) {
            Shaders.useProgram(Shaders.ProgramBasic);
            Shaders.setRenderStage(RenderStage.OUTLINE);
        }
    }

    public static void endOutline() {
        if (Shaders.isRenderingWorld) {
            Shaders.useProgram(Shaders.ProgramTexturedLit);
            Shaders.setRenderStage(RenderStage.NONE);
        }
    }

    public static void beginDebug() {
        if (Shaders.isRenderingWorld) {
            Shaders.setRenderStage(RenderStage.DEBUG);
        }
    }

    public static void endDebug() {
        if (Shaders.isRenderingWorld) {
            Shaders.setRenderStage(RenderStage.NONE);
        }
    }

    public static void renderShadowMap(GameRenderer gameRenderer, ActiveRenderInfo activeRenderInfo, int n, float f, long l) {
        if (Shaders.hasShadowMap) {
            Object object;
            Object object2;
            Object object3;
            Minecraft minecraft = Minecraft.getInstance();
            minecraft.getProfiler().endStartSection("shadow pass");
            WorldRenderer worldRenderer = minecraft.worldRenderer;
            Shaders.isShadowPass = true;
            Shaders.updateProjectionMatrix();
            Shaders.checkGLError("pre shadow");
            GL30.glMatrixMode(5889);
            GL11.glPushMatrix();
            GL30.glMatrixMode(5888);
            GL11.glPushMatrix();
            minecraft.getProfiler().endStartSection("shadow clear");
            Shaders.sfb.bindFramebuffer();
            Shaders.checkGLError("shadow bind sfb");
            minecraft.getProfiler().endStartSection("shadow camera");
            ShadersRender.updateActiveRenderInfo(activeRenderInfo, minecraft, f);
            MatrixStack matrixStack = new MatrixStack();
            Shaders.setCameraShadow(matrixStack, activeRenderInfo, f);
            Shaders.checkGLError("shadow camera");
            Shaders.dispatchComputes(Shaders.dfb, Shaders.ProgramShadow.getComputePrograms());
            Shaders.useProgram(Shaders.ProgramShadow);
            Shaders.sfb.setDrawBuffers();
            Shaders.checkGLError("shadow drawbuffers");
            GL30.glReadBuffer(0);
            Shaders.checkGLError("shadow readbuffer");
            Shaders.sfb.setDepthTexture();
            Shaders.sfb.setColorTextures(false);
            Shaders.checkFramebufferStatus("shadow fb");
            GL30.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
            GL30.glClear(256);
            for (int i = 0; i < Shaders.usedShadowColorBuffers; ++i) {
                if (!Shaders.shadowBuffersClear[i]) continue;
                object3 = Shaders.shadowBuffersClearColor[i];
                if (object3 != null) {
                    GL30.glClearColor(((Vector4f)object3).getX(), ((Vector4f)object3).getY(), ((Vector4f)object3).getZ(), ((Vector4f)object3).getW());
                } else {
                    GL30.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
                }
                GlState.setDrawBuffers(Shaders.drawBuffersColorAtt[i]);
                GL30.glClear(16384);
            }
            Shaders.sfb.setDrawBuffers();
            Shaders.checkGLError("shadow clear");
            minecraft.getProfiler().endStartSection("shadow frustum");
            ClippingHelperDummy clippingHelperDummy = new ClippingHelperDummy();
            minecraft.getProfiler().endStartSection("shadow culling");
            object3 = activeRenderInfo.getProjectedView();
            clippingHelperDummy.setCameraPosition(((Vector3d)object3).x, ((Vector3d)object3).y, ((Vector3d)object3).z);
            GlStateManager.shadeModel(7425);
            GlStateManager.enableDepthTest();
            GlStateManager.depthFunc(515);
            GlStateManager.depthMask(true);
            GlStateManager.colorMask(true, true, true, true);
            GlStateManager.lockCull(new GlCullState(false));
            GlStateManager.lockBlend(new GlBlendState(false));
            minecraft.getProfiler().endStartSection("shadow prepareterrain");
            minecraft.getTextureManager().bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
            minecraft.getProfiler().endStartSection("shadow setupterrain");
            int n2 = minecraft.worldRenderer.getNextFrameCount();
            worldRenderer.setupTerrain(activeRenderInfo, clippingHelperDummy, false, n2, minecraft.player.isSpectator());
            minecraft.getProfiler().endStartSection("shadow updatechunks");
            minecraft.getProfiler().endStartSection("shadow terrain");
            double d = ((Vector3d)object3).getX();
            double d2 = ((Vector3d)object3).getY();
            double d3 = ((Vector3d)object3).getZ();
            GlStateManager.matrixMode(5888);
            GlStateManager.pushMatrix();
            if (Shaders.isRenderShadowTerrain()) {
                GlStateManager.disableAlphaTest();
                worldRenderer.renderBlockLayer(RenderTypes.SOLID, matrixStack, d, d2, d3);
                Shaders.checkGLError("shadow terrain solid");
                GlStateManager.enableAlphaTest();
                worldRenderer.renderBlockLayer(RenderTypes.CUTOUT_MIPPED, matrixStack, d, d2, d3);
                Shaders.checkGLError("shadow terrain cutoutmipped");
                minecraft.getTextureManager().getTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE).setBlurMipmapDirect(false, true);
                worldRenderer.renderBlockLayer(RenderTypes.CUTOUT, matrixStack, d, d2, d3);
                minecraft.getTextureManager().getTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE).restoreLastBlurMipmap();
                Shaders.checkGLError("shadow terrain cutout");
            }
            GlStateManager.shadeModel(7424);
            GlStateManager.alphaFunc(516, 0.1f);
            GlStateManager.matrixMode(5888);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            minecraft.getProfiler().endStartSection("shadow entities");
            WorldRenderer worldRenderer2 = minecraft.worldRenderer;
            EntityRendererManager entityRendererManager = minecraft.getRenderManager();
            IRenderTypeBuffer.Impl impl = worldRenderer2.getRenderTypeTextures().getBufferSource();
            boolean bl = Shaders.isShadowPass && !minecraft.player.isSpectator();
            Iterator<WorldRenderer.LocalRenderInformationContainer> iterator2 = (Shaders.isRenderShadowEntities() ? worldRenderer2.getRenderInfosEntities() : Collections.EMPTY_LIST).iterator();
            while (iterator2.hasNext()) {
                Object object4 = object2 = iterator2.next();
                ChunkRenderDispatcher.ChunkRender chunkRender = ((WorldRenderer.LocalRenderInformationContainer)object4).renderChunk;
                object = chunkRender.getChunk();
                for (Entity entity2 : ((Chunk)object).getEntityLists()[chunkRender.getPosition().getY() / 16]) {
                    if (!entityRendererManager.shouldRender(entity2, clippingHelperDummy, d, d2, d3) && !entity2.isRidingOrBeingRiddenBy(minecraft.player) || entity2 == activeRenderInfo.getRenderViewEntity() && !bl && !activeRenderInfo.isThirdPerson() && (!(activeRenderInfo.getRenderViewEntity() instanceof LivingEntity) || !((LivingEntity)activeRenderInfo.getRenderViewEntity()).isSleeping()) || entity2 instanceof ClientPlayerEntity && activeRenderInfo.getRenderViewEntity() != entity2) continue;
                    worldRenderer2.renderedEntity = entity2;
                    Shaders.nextEntity(entity2);
                    worldRenderer2.renderEntity(entity2, d, d2, d3, f, matrixStack, impl);
                    worldRenderer2.renderedEntity = null;
                }
            }
            worldRenderer2.checkMatrixStack(matrixStack);
            impl.finish(RenderType.getEntitySolid(AtlasTexture.LOCATION_BLOCKS_TEXTURE));
            impl.finish(RenderType.getEntityCutout(AtlasTexture.LOCATION_BLOCKS_TEXTURE));
            impl.finish(RenderType.getEntityCutoutNoCull(AtlasTexture.LOCATION_BLOCKS_TEXTURE));
            impl.finish(RenderType.getEntitySmoothCutout(AtlasTexture.LOCATION_BLOCKS_TEXTURE));
            Shaders.endEntities();
            Shaders.beginBlockEntities();
            SignTileEntityRenderer.updateTextRenderDistance();
            boolean bl2 = Reflector.IForgeTileEntity_getRenderBoundingBox.exists();
            object2 = clippingHelperDummy;
            for (Object e : Shaders.isRenderShadowBlockEntities() ? worldRenderer2.getRenderInfosTileEntities() : Collections.EMPTY_LIST) {
                object = (WorldRenderer.LocalRenderInformationContainer)e;
                List<TileEntity> list = ((WorldRenderer.LocalRenderInformationContainer)object).renderChunk.getCompiledChunk().getTileEntities();
                if (list.isEmpty()) continue;
                for (TileEntity tileEntity : list) {
                    AxisAlignedBB axisAlignedBB;
                    if (bl2 && (axisAlignedBB = (AxisAlignedBB)Reflector.call(tileEntity, Reflector.IForgeTileEntity_getRenderBoundingBox, new Object[0])) != null && !((ClippingHelper)object2).isBoundingBoxInFrustum(axisAlignedBB)) continue;
                    Shaders.nextBlockEntity(tileEntity);
                    BlockPos blockPos = tileEntity.getPos();
                    matrixStack.push();
                    matrixStack.translate((double)blockPos.getX() - d, (double)blockPos.getY() - d2, (double)blockPos.getZ() - d3);
                    TileEntityRendererDispatcher.instance.renderTileEntity(tileEntity, f, matrixStack, impl);
                    matrixStack.pop();
                }
            }
            worldRenderer2.checkMatrixStack(matrixStack);
            impl.finish(RenderType.getSolid());
            impl.finish(Atlases.getSolidBlockType());
            impl.finish(Atlases.getCutoutBlockType());
            impl.finish(Atlases.getBedType());
            impl.finish(Atlases.getShulkerBoxType());
            impl.finish(Atlases.getSignType());
            impl.finish(Atlases.getChestType());
            impl.finish();
            Shaders.endBlockEntities();
            Shaders.checkGLError("shadow entities");
            GlStateManager.matrixMode(5888);
            GlStateManager.popMatrix();
            GlStateManager.depthMask(true);
            GlStateManager.disableBlend();
            GlStateManager.unlockCull();
            GlStateManager.enableCull();
            GlStateManager.blendFuncSeparate(770, 771, 1, 0);
            GlStateManager.alphaFunc(516, 0.1f);
            if (Shaders.usedShadowDepthBuffers >= 2) {
                GlStateManager.activeTexture(33989);
                Shaders.checkGLError("pre copy shadow depth");
                GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, Shaders.shadowMapWidth, Shaders.shadowMapHeight);
                Shaders.checkGLError("copy shadow depth");
                GlStateManager.activeTexture(33984);
            }
            GlStateManager.disableBlend();
            GlStateManager.depthMask(true);
            minecraft.getTextureManager().bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
            GlStateManager.shadeModel(7425);
            Shaders.checkGLError("shadow pre-translucent");
            Shaders.sfb.setDrawBuffers();
            Shaders.checkGLError("shadow drawbuffers pre-translucent");
            Shaders.checkFramebufferStatus("shadow pre-translucent");
            if (Shaders.isRenderShadowTranslucent()) {
                minecraft.getProfiler().endStartSection("shadow translucent");
                worldRenderer.renderBlockLayer(RenderTypes.TRANSLUCENT, matrixStack, d, d2, d3);
                Shaders.checkGLError("shadow translucent");
            }
            GlStateManager.unlockBlend();
            GlStateManager.shadeModel(7424);
            GlStateManager.depthMask(true);
            GlStateManager.enableCull();
            GlStateManager.disableBlend();
            GL30.glFlush();
            Shaders.checkGLError("shadow flush");
            Shaders.isShadowPass = false;
            minecraft.getProfiler().endStartSection("shadow postprocess");
            if (Shaders.hasGlGenMipmap) {
                Shaders.sfb.generateDepthMipmaps(Shaders.shadowMipmapEnabled);
                Shaders.sfb.generateColorMipmaps(true, Shaders.shadowColorMipmapEnabled);
            }
            Shaders.checkGLError("shadow postprocess");
            if (Shaders.hasShadowcompPrograms) {
                Shaders.renderShadowComposites();
            }
            Shaders.dfb.bindFramebuffer();
            GL11.glViewport(0, 0, Shaders.renderWidth, Shaders.renderHeight);
            GlState.setDrawBuffers(null);
            minecraft.getTextureManager().bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
            Shaders.useProgram(Shaders.ProgramTerrain);
            GL30.glMatrixMode(5888);
            GL11.glPopMatrix();
            GL30.glMatrixMode(5889);
            GL11.glPopMatrix();
            GL30.glMatrixMode(5888);
            Shaders.checkGLError("shadow end");
        }
    }

    public static void updateActiveRenderInfo(ActiveRenderInfo activeRenderInfo, Minecraft minecraft, float f) {
        activeRenderInfo.update(minecraft.world, minecraft.getRenderViewEntity() == null ? minecraft.player : minecraft.getRenderViewEntity(), !minecraft.gameSettings.getPointOfView().func_243192_a(), minecraft.gameSettings.getPointOfView().func_243193_b(), f);
    }

    public static void preRenderChunkLayer(RenderType renderType) {
        if (renderType == RenderTypes.SOLID) {
            ShadersRender.beginTerrainSolid();
        }
        if (renderType == RenderTypes.CUTOUT_MIPPED) {
            ShadersRender.beginTerrainCutoutMipped();
        }
        if (renderType == RenderTypes.CUTOUT) {
            ShadersRender.beginTerrainCutout();
        }
        if (renderType == RenderTypes.TRANSLUCENT) {
            ShadersRender.beginTranslucent();
        }
        if (renderType == RenderType.getTripwire()) {
            ShadersRender.beginTripwire();
        }
        if (Shaders.isRenderBackFace(renderType)) {
            GlStateManager.disableCull();
        }
        if (GLX.useVbo()) {
            GL20.glEnableVertexAttribArray(Shaders.midBlockAttrib);
            GL20.glEnableVertexAttribArray(Shaders.midTexCoordAttrib);
            GL20.glEnableVertexAttribArray(Shaders.tangentAttrib);
            GL20.glEnableVertexAttribArray(Shaders.entityAttrib);
        }
    }

    public static void postRenderChunkLayer(RenderType renderType) {
        if (GLX.useVbo()) {
            GL20.glDisableVertexAttribArray(Shaders.midBlockAttrib);
            GL20.glDisableVertexAttribArray(Shaders.midTexCoordAttrib);
            GL20.glDisableVertexAttribArray(Shaders.tangentAttrib);
            GL20.glDisableVertexAttribArray(Shaders.entityAttrib);
        }
        if (Shaders.isRenderBackFace(renderType)) {
            GlStateManager.enableCull();
        }
    }

    public static void preRender(RenderType renderType, BufferBuilder bufferBuilder) {
        if (Shaders.isRenderingWorld && !Shaders.isShadowPass) {
            if (renderType.isGlint()) {
                ShadersRender.renderEnchantedGlintBegin();
            } else if (renderType.getName().equals("eyes")) {
                Shaders.beginSpiderEyes();
            } else if (renderType.getName().equals("crumbling")) {
                ShadersRender.beginBlockDamage();
            } else if (renderType == RenderType.LINES) {
                Shaders.beginLeash();
            }
        }
    }

    public static void postRender(RenderType renderType, BufferBuilder bufferBuilder) {
        if (Shaders.isRenderingWorld && !Shaders.isShadowPass) {
            if (renderType.isGlint()) {
                ShadersRender.renderEnchantedGlintEnd();
            } else if (renderType.getName().equals("eyes")) {
                Shaders.endSpiderEyes();
            } else if (renderType.getName().equals("crumbling")) {
                ShadersRender.endBlockDamage();
            } else if (renderType == RenderType.LINES) {
                Shaders.endLeash();
            }
        }
    }

    public static void setupArrayPointersVbo() {
        int n = 18;
        GL20.glVertexAttribPointer(Shaders.midBlockAttrib, 3, 5120, false, 72, 32L);
        GL20.glVertexAttribPointer(Shaders.midTexCoordAttrib, 2, 5126, false, 72, 36L);
        GL20.glVertexAttribPointer(Shaders.tangentAttrib, 4, 5122, false, 72, 44L);
        GL20.glVertexAttribPointer(Shaders.entityAttrib, 3, 5122, false, 72, 52L);
    }

    public static void beaconBeamBegin() {
        Shaders.useProgram(Shaders.ProgramBeaconBeam);
    }

    public static void beaconBeamStartQuad1() {
    }

    public static void beaconBeamStartQuad2() {
    }

    public static void beaconBeamDraw1() {
    }

    public static void beaconBeamDraw2() {
        GlStateManager.disableBlend();
    }

    public static void renderEnchantedGlintBegin() {
        Shaders.useProgram(Shaders.ProgramArmorGlint);
    }

    public static void renderEnchantedGlintEnd() {
        if (Shaders.isRenderingWorld) {
            if (Shaders.isRenderingFirstPersonHand() && Shaders.isRenderBothHands()) {
                Shaders.useProgram(Shaders.ProgramHand);
            } else {
                Shaders.useProgram(Shaders.ProgramEntities);
            }
        } else {
            Shaders.useProgram(Shaders.ProgramNone);
        }
    }

    public static boolean renderEndPortal(EndPortalTileEntity endPortalTileEntity, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, int n2) {
        float f3;
        float f4;
        float f5;
        float f6;
        float f7;
        float f8;
        Vector3i vector3i;
        if (!Shaders.isShadowPass && Shaders.activeProgram.getId() == 0) {
            return true;
        }
        GlStateManager.disableLighting();
        MatrixStack.Entry entry = matrixStack.getLast();
        Matrix4f matrix4f = entry.getMatrix();
        Matrix3f matrix3f = entry.getNormal();
        IVertexBuilder iVertexBuilder = iRenderTypeBuffer.getBuffer(RenderType.getEntitySolid(END_PORTAL_TEXTURE));
        float f9 = 0.5f;
        float f10 = f9 * 0.15f;
        float f11 = f9 * 0.3f;
        float f12 = f9 * 0.4f;
        float f13 = 0.0f;
        float f14 = 0.2f;
        float f15 = (float)(System.currentTimeMillis() % 100000L) / 100000.0f;
        float f16 = 0.0f;
        float f17 = 0.0f;
        float f18 = 0.0f;
        if (endPortalTileEntity.shouldRenderFace(Direction.SOUTH)) {
            vector3i = Direction.SOUTH.getDirectionVec();
            f8 = vector3i.getX();
            f7 = vector3i.getY();
            f6 = vector3i.getZ();
            f5 = matrix3f.getTransformX(f8, f7, f6);
            f4 = matrix3f.getTransformY(f8, f7, f6);
            f3 = matrix3f.getTransformZ(f8, f7, f6);
            iVertexBuilder.pos(matrix4f, f16, f17, f18 + 1.0f).color(f10, f11, f12, 1.0f).tex(f13 + f15, f13 + f15).overlay(n2).lightmap(n).normal(f5, f4, f3).endVertex();
            iVertexBuilder.pos(matrix4f, f16 + 1.0f, f17, f18 + 1.0f).color(f10, f11, f12, 1.0f).tex(f13 + f15, f14 + f15).overlay(n2).lightmap(n).normal(f5, f4, f3).endVertex();
            iVertexBuilder.pos(matrix4f, f16 + 1.0f, f17 + 1.0f, f18 + 1.0f).color(f10, f11, f12, 1.0f).tex(f14 + f15, f14 + f15).overlay(n2).lightmap(n).normal(f5, f4, f3).endVertex();
            iVertexBuilder.pos(matrix4f, f16, f17 + 1.0f, f18 + 1.0f).color(f10, f11, f12, 1.0f).tex(f14 + f15, f13 + f15).overlay(n2).lightmap(n).normal(f5, f4, f3).endVertex();
        }
        if (endPortalTileEntity.shouldRenderFace(Direction.NORTH)) {
            vector3i = Direction.NORTH.getDirectionVec();
            f8 = vector3i.getX();
            f7 = vector3i.getY();
            f6 = vector3i.getZ();
            f5 = matrix3f.getTransformX(f8, f7, f6);
            f4 = matrix3f.getTransformY(f8, f7, f6);
            f3 = matrix3f.getTransformZ(f8, f7, f6);
            iVertexBuilder.pos(matrix4f, f16, f17 + 1.0f, f18).color(f10, f11, f12, 1.0f).tex(f14 + f15, f14 + f15).overlay(n2).lightmap(n).normal(f5, f4, f3).endVertex();
            iVertexBuilder.pos(matrix4f, f16 + 1.0f, f17 + 1.0f, f18).color(f10, f11, f12, 1.0f).tex(f14 + f15, f13 + f15).overlay(n2).lightmap(n).normal(f5, f4, f3).endVertex();
            iVertexBuilder.pos(matrix4f, f16 + 1.0f, f17, f18).color(f10, f11, f12, 1.0f).tex(f13 + f15, f13 + f15).overlay(n2).lightmap(n).normal(f5, f4, f3).endVertex();
            iVertexBuilder.pos(matrix4f, f16, f17, f18).color(f10, f11, f12, 1.0f).tex(f13 + f15, f14 + f15).overlay(n2).lightmap(n).normal(f5, f4, f3).endVertex();
        }
        if (endPortalTileEntity.shouldRenderFace(Direction.EAST)) {
            vector3i = Direction.EAST.getDirectionVec();
            f8 = vector3i.getX();
            f7 = vector3i.getY();
            f6 = vector3i.getZ();
            f5 = matrix3f.getTransformX(f8, f7, f6);
            f4 = matrix3f.getTransformY(f8, f7, f6);
            f3 = matrix3f.getTransformZ(f8, f7, f6);
            iVertexBuilder.pos(matrix4f, f16 + 1.0f, f17 + 1.0f, f18).color(f10, f11, f12, 1.0f).tex(f14 + f15, f14 + f15).overlay(n2).lightmap(n).normal(f5, f4, f3).endVertex();
            iVertexBuilder.pos(matrix4f, f16 + 1.0f, f17 + 1.0f, f18 + 1.0f).color(f10, f11, f12, 1.0f).tex(f14 + f15, f13 + f15).overlay(n2).lightmap(n).normal(f5, f4, f3).endVertex();
            iVertexBuilder.pos(matrix4f, f16 + 1.0f, f17, f18 + 1.0f).color(f10, f11, f12, 1.0f).tex(f13 + f15, f13 + f15).overlay(n2).lightmap(n).normal(f5, f4, f3).endVertex();
            iVertexBuilder.pos(matrix4f, f16 + 1.0f, f17, f18).color(f10, f11, f12, 1.0f).tex(f13 + f15, f14 + f15).overlay(n2).lightmap(n).normal(f5, f4, f3).endVertex();
        }
        if (endPortalTileEntity.shouldRenderFace(Direction.WEST)) {
            vector3i = Direction.WEST.getDirectionVec();
            f8 = vector3i.getX();
            f7 = vector3i.getY();
            f6 = vector3i.getZ();
            f5 = matrix3f.getTransformX(f8, f7, f6);
            f4 = matrix3f.getTransformY(f8, f7, f6);
            f3 = matrix3f.getTransformZ(f8, f7, f6);
            iVertexBuilder.pos(matrix4f, f16, f17, f18).color(f10, f11, f12, 1.0f).tex(f13 + f15, f13 + f15).overlay(n2).lightmap(n).normal(f5, f4, f3).endVertex();
            iVertexBuilder.pos(matrix4f, f16, f17, f18 + 1.0f).color(f10, f11, f12, 1.0f).tex(f13 + f15, f14 + f15).overlay(n2).lightmap(n).normal(f5, f4, f3).endVertex();
            iVertexBuilder.pos(matrix4f, f16, f17 + 1.0f, f18 + 1.0f).color(f10, f11, f12, 1.0f).tex(f14 + f15, f14 + f15).overlay(n2).lightmap(n).normal(f5, f4, f3).endVertex();
            iVertexBuilder.pos(matrix4f, f16, f17 + 1.0f, f18).color(f10, f11, f12, 1.0f).tex(f14 + f15, f13 + f15).overlay(n2).lightmap(n).normal(f5, f4, f3).endVertex();
        }
        if (endPortalTileEntity.shouldRenderFace(Direction.DOWN)) {
            vector3i = Direction.DOWN.getDirectionVec();
            f8 = vector3i.getX();
            f7 = vector3i.getY();
            f6 = vector3i.getZ();
            f5 = matrix3f.getTransformX(f8, f7, f6);
            f4 = matrix3f.getTransformY(f8, f7, f6);
            f3 = matrix3f.getTransformZ(f8, f7, f6);
            iVertexBuilder.pos(matrix4f, f16, f17, f18).color(f10, f11, f12, 1.0f).tex(f13 + f15, f13 + f15).overlay(n2).lightmap(n).normal(f5, f4, f3).endVertex();
            iVertexBuilder.pos(matrix4f, f16 + 1.0f, f17, f18).color(f10, f11, f12, 1.0f).tex(f13 + f15, f14 + f15).overlay(n2).lightmap(n).normal(f5, f4, f3).endVertex();
            iVertexBuilder.pos(matrix4f, f16 + 1.0f, f17, f18 + 1.0f).color(f10, f11, f12, 1.0f).tex(f14 + f15, f14 + f15).overlay(n2).lightmap(n).normal(f5, f4, f3).endVertex();
            iVertexBuilder.pos(matrix4f, f16, f17, f18 + 1.0f).color(f10, f11, f12, 1.0f).tex(f14 + f15, f13 + f15).overlay(n2).lightmap(n).normal(f5, f4, f3).endVertex();
        }
        if (endPortalTileEntity.shouldRenderFace(Direction.UP)) {
            vector3i = Direction.UP.getDirectionVec();
            f8 = vector3i.getX();
            f7 = vector3i.getY();
            f6 = vector3i.getZ();
            f5 = matrix3f.getTransformX(f8, f7, f6);
            f4 = matrix3f.getTransformY(f8, f7, f6);
            f3 = matrix3f.getTransformZ(f8, f7, f6);
            iVertexBuilder.pos(matrix4f, f16, f17 + f2, f18 + 1.0f).color(f10, f11, f12, 1.0f).tex(f13 + f15, f13 + f15).overlay(n2).lightmap(n).normal(f5, f4, f3).endVertex();
            iVertexBuilder.pos(matrix4f, f16 + 1.0f, f17 + f2, f18 + 1.0f).color(f10, f11, f12, 1.0f).tex(f13 + f15, f14 + f15).overlay(n2).lightmap(n).normal(f5, f4, f3).endVertex();
            iVertexBuilder.pos(matrix4f, f16 + 1.0f, f17 + f2, f18).color(f10, f11, f12, 1.0f).tex(f14 + f15, f14 + f15).overlay(n2).lightmap(n).normal(f5, f4, f3).endVertex();
            iVertexBuilder.pos(matrix4f, f16, f17 + f2, f18).color(f10, f11, f12, 1.0f).tex(f14 + f15, f13 + f15).overlay(n2).lightmap(n).normal(f5, f4, f3).endVertex();
        }
        GlStateManager.enableLighting();
        return false;
    }
}

