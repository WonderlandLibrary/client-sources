/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer;

import com.google.gson.JsonSyntaxException;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import javax.annotation.Nullable;
import mpp.venusfr.events.EventCancelOverlay;
import mpp.venusfr.functions.api.FunctionRegistry;
import mpp.venusfr.functions.impl.combat.NoEntityTrace;
import mpp.venusfr.functions.impl.misc.BetterMinecraft;
import mpp.venusfr.functions.impl.render.GlassHand;
import mpp.venusfr.functions.impl.render.Snow;
import mpp.venusfr.ui.mainmenu.MainScreen;
import mpp.venusfr.venusfr;
import net.minecraft.block.BlockState;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.gui.MapItemRenderer;
import net.minecraft.client.gui.ResourceLoadProgressGui;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.DownloadTerrainScreen;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.FirstPersonRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.OverlayRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderTypeBuffers;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemFrameEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effects;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourceManagerReloadListener;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.CachedBlockInfo;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ScreenShotHelper;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraftforge.resource.IResourceType;
import net.minecraftforge.resource.VanillaResourceType;
import net.optifine.Config;
import net.optifine.GlErrors;
import net.optifine.Lagometer;
import net.optifine.RandomEntities;
import net.optifine.gui.GuiChatOF;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorResolver;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.ShadersRender;
import net.optifine.util.MemoryMonitor;
import net.optifine.util.TimedEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.hogoshi.Animation;
import ru.hogoshi.util.Easings;

public class GameRenderer
implements IResourceManagerReloadListener,
AutoCloseable {
    private static final ResourceLocation field_243496_c = new ResourceLocation("textures/misc/nausea.png");
    private static final Logger LOGGER = LogManager.getLogger();
    private final Minecraft mc;
    private final IResourceManager resourceManager;
    private final Random random = new Random();
    private float farPlaneDistance;
    public final FirstPersonRenderer itemRenderer;
    private final MapItemRenderer mapItemRenderer;
    private final RenderTypeBuffers renderTypeBuffers;
    private int rendererUpdateCount;
    private float fovModifierHand;
    private float fovModifierHandPrev;
    private float bossColorModifier;
    private float bossColorModifierPrev;
    private final boolean renderHand = true;
    private final boolean drawBlockOutline = true;
    private long timeWorldIcon;
    private long prevFrameTime = Util.milliTime();
    private final LightTexture lightmapTexture;
    private final OverlayTexture overlayTexture = new OverlayTexture();
    private boolean debugView;
    private final float cameraZoom = 1.0f;
    private float cameraYaw;
    private float cameraPitch;
    @Nullable
    private ItemStack itemActivationItem;
    private int itemActivationTicks;
    private float itemActivationOffX;
    private float itemActivationOffY;
    @Nullable
    private ShaderGroup shaderGroup;
    private static final ResourceLocation[] SHADERS_TEXTURES = new ResourceLocation[]{new ResourceLocation("shaders/post/notch.json"), new ResourceLocation("shaders/post/fxaa.json"), new ResourceLocation("shaders/post/art.json"), new ResourceLocation("shaders/post/bumpy.json"), new ResourceLocation("shaders/post/blobs2.json"), new ResourceLocation("shaders/post/pencil.json"), new ResourceLocation("shaders/post/color_convolve.json"), new ResourceLocation("shaders/post/deconverge.json"), new ResourceLocation("shaders/post/flip.json"), new ResourceLocation("shaders/post/invert.json"), new ResourceLocation("shaders/post/ntsc.json"), new ResourceLocation("shaders/post/outline.json"), new ResourceLocation("shaders/post/phosphor.json"), new ResourceLocation("shaders/post/scan_pincushion.json"), new ResourceLocation("shaders/post/sobel.json"), new ResourceLocation("shaders/post/bits.json"), new ResourceLocation("shaders/post/desaturate.json"), new ResourceLocation("shaders/post/green.json"), new ResourceLocation("shaders/post/blur.json"), new ResourceLocation("shaders/post/wobble.json"), new ResourceLocation("shaders/post/blobs.json"), new ResourceLocation("shaders/post/antialias.json"), new ResourceLocation("shaders/post/creeper.json"), new ResourceLocation("shaders/post/spider.json")};
    public static final int SHADER_COUNT = SHADERS_TEXTURES.length;
    private int shaderIndex = SHADER_COUNT;
    private boolean useShader;
    private final ActiveRenderInfo activeRender = new ActiveRenderInfo();
    private boolean initialized = false;
    private World updatedWorld = null;
    private float clipDistance = 128.0f;
    private long lastServerTime = 0L;
    private int lastServerTicks = 0;
    private int serverWaitTime = 0;
    private int serverWaitTimeCurrent = 0;
    private float avgServerTimeDiff = 0.0f;
    private float avgServerTickDiff = 0.0f;
    private final ShaderGroup[] fxaaShaders = new ShaderGroup[10];
    private boolean guiLoadingVisible = false;
    private final Animation zoomAnim = new Animation().setToValue(1.0);
    private boolean lastFlag = false;

    public GameRenderer(Minecraft minecraft, IResourceManager iResourceManager, RenderTypeBuffers renderTypeBuffers) {
        this.mc = minecraft;
        this.resourceManager = iResourceManager;
        this.itemRenderer = minecraft.getFirstPersonRenderer();
        this.mapItemRenderer = new MapItemRenderer(minecraft.getTextureManager());
        this.lightmapTexture = new LightTexture(this, minecraft);
        this.renderTypeBuffers = renderTypeBuffers;
        this.shaderGroup = null;
    }

    public void setupOverlayRendering(int n) {
        MainWindow mainWindow = Minecraft.getInstance().getMainWindow();
        mainWindow.setGuiScale(n);
        GlStateManager.clear(256);
        GlStateManager.matrixMode(5889);
        GlStateManager.loadIdentity();
        GlStateManager.ortho(0.0, mainWindow.getScaledWidth(), mainWindow.getScaledHeight(), 0.0, 1000.0, 3000.0);
        GlStateManager.matrixMode(5888);
        GlStateManager.loadIdentity();
        GlStateManager.translatef(0.0f, 0.0f, -2000.0f);
    }

    public void setupOverlayRendering() {
        MainWindow mainWindow = Minecraft.getInstance().getMainWindow();
        int n = mainWindow.calcGuiScale(Minecraft.getInstance().gameSettings.guiScale, Minecraft.getInstance().getForceUnicodeFont());
        mainWindow.setGuiScale(n);
        GlStateManager.clear(256);
        GlStateManager.matrixMode(5889);
        GlStateManager.loadIdentity();
        GlStateManager.ortho(0.0, mainWindow.getScaledWidth(), mainWindow.getScaledHeight(), 0.0, 1000.0, 3000.0);
        GlStateManager.matrixMode(5888);
        GlStateManager.loadIdentity();
        GlStateManager.translatef(0.0f, 0.0f, -2000.0f);
    }

    @Override
    public void close() {
        this.lightmapTexture.close();
        this.mapItemRenderer.close();
        this.overlayTexture.close();
        this.stopUseShader();
    }

    public void stopUseShader() {
        if (this.shaderGroup != null) {
            this.shaderGroup.close();
        }
        this.shaderGroup = null;
        this.shaderIndex = SHADER_COUNT;
    }

    public void switchUseShader() {
        this.useShader = !this.useShader;
    }

    public void loadEntityShader(@Nullable Entity entity2) {
        if (this.shaderGroup != null) {
            this.shaderGroup.close();
        }
        this.shaderGroup = null;
        if (entity2 instanceof CreeperEntity) {
            this.loadShader(new ResourceLocation("shaders/post/creeper.json"));
        } else if (entity2 instanceof SpiderEntity) {
            this.loadShader(new ResourceLocation("shaders/post/spider.json"));
        } else if (entity2 instanceof EndermanEntity) {
            this.loadShader(new ResourceLocation("shaders/post/invert.json"));
        } else if (Reflector.ForgeHooksClient_loadEntityShader.exists()) {
            Reflector.call(Reflector.ForgeHooksClient_loadEntityShader, entity2, this);
        }
    }

    private void loadShader(ResourceLocation resourceLocation) {
        if (GLX.isUsingFBOs()) {
            if (this.shaderGroup != null) {
                this.shaderGroup.close();
            }
            try {
                this.shaderGroup = new ShaderGroup(this.mc.getTextureManager(), this.resourceManager, this.mc.getFramebuffer(), resourceLocation);
                this.shaderGroup.createBindFramebuffers(this.mc.getMainWindow().getFramebufferWidth(), this.mc.getMainWindow().getFramebufferHeight());
                this.useShader = true;
            } catch (IOException iOException) {
                LOGGER.warn("Failed to load shader: {}", (Object)resourceLocation, (Object)iOException);
                this.shaderIndex = SHADER_COUNT;
                this.useShader = false;
            } catch (JsonSyntaxException jsonSyntaxException) {
                LOGGER.warn("Failed to parse shader: {}", (Object)resourceLocation, (Object)jsonSyntaxException);
                this.shaderIndex = SHADER_COUNT;
                this.useShader = false;
            }
        }
    }

    @Override
    public void onResourceManagerReload(IResourceManager iResourceManager) {
        if (this.shaderGroup != null) {
            this.shaderGroup.close();
        }
        this.shaderGroup = null;
        if (this.shaderIndex == SHADER_COUNT) {
            this.loadEntityShader(this.mc.getRenderViewEntity());
        } else {
            this.loadShader(SHADERS_TEXTURES[this.shaderIndex]);
        }
    }

    public void tick() {
        this.updateFovModifierHand();
        this.lightmapTexture.tick();
        if (this.mc.getRenderViewEntity() == null) {
            this.mc.setRenderViewEntity(this.mc.player);
        }
        this.activeRender.interpolateHeight();
        ++this.rendererUpdateCount;
        this.itemRenderer.tick();
        this.mc.worldRenderer.addRainParticles(this.activeRender);
        this.bossColorModifierPrev = this.bossColorModifier;
        if (this.mc.ingameGUI.getBossOverlay().shouldDarkenSky()) {
            this.bossColorModifier += 0.05f;
            if (this.bossColorModifier > 1.0f) {
                this.bossColorModifier = 1.0f;
            }
        } else if (this.bossColorModifier > 0.0f) {
            this.bossColorModifier -= 0.0125f;
        }
        if (this.itemActivationTicks > 0) {
            --this.itemActivationTicks;
            if (this.itemActivationTicks == 0) {
                this.itemActivationItem = null;
            }
        }
    }

    @Nullable
    public ShaderGroup getShaderGroup() {
        return this.shaderGroup;
    }

    public void updateShaderGroupSize(int n, int n2) {
        if (this.shaderGroup != null) {
            this.shaderGroup.createBindFramebuffers(n, n2);
        }
        this.mc.worldRenderer.createBindEntityOutlineFbs(n, n2);
    }

    public void getMouseOver(float f) {
        Entity entity2 = this.mc.getRenderViewEntity();
        if (entity2 != null && this.mc.world != null) {
            FunctionRegistry functionRegistry;
            NoEntityTrace noEntityTrace;
            this.mc.getProfiler().startSection("pick");
            this.mc.pointedEntity = null;
            double d = this.mc.playerController.getBlockReachDistance();
            this.mc.objectMouseOver = entity2.pick(d, f, true);
            Vector3d vector3d = entity2.getEyePosition(f);
            boolean bl = false;
            int n = 3;
            double d2 = d;
            if (this.mc.playerController.extendedReach()) {
                d = d2 = 6.0;
            } else if (d > 3.0) {
                bl = true;
            }
            d2 *= d2;
            if (this.mc.objectMouseOver != null) {
                d2 = this.mc.objectMouseOver.getHitVec().squareDistanceTo(vector3d);
            }
            if (!(noEntityTrace = (functionRegistry = venusfr.getInstance().getFunctionRegistry()).getNoEntityTrace()).isState()) {
                Vector3d vector3d2 = entity2.getLook(1.0f);
                Vector3d vector3d3 = vector3d.add(vector3d2.x * d, vector3d2.y * d, vector3d2.z * d);
                float f2 = 1.0f;
                AxisAlignedBB axisAlignedBB = entity2.getBoundingBox().expand(vector3d2.scale(d)).grow(1.0, 1.0, 1.0);
                EntityRayTraceResult entityRayTraceResult = ProjectileHelper.rayTraceEntities(entity2, vector3d, vector3d3, axisAlignedBB, GameRenderer::lambda$getMouseOver$0, d2);
                if (entityRayTraceResult != null) {
                    Entity entity3 = entityRayTraceResult.getEntity();
                    Vector3d vector3d4 = entityRayTraceResult.getHitVec();
                    double d3 = vector3d.squareDistanceTo(vector3d4);
                    if (bl && d3 > 9.0) {
                        this.mc.objectMouseOver = BlockRayTraceResult.createMiss(vector3d4, Direction.getFacingFromVector(vector3d2.x, vector3d2.y, vector3d2.z), new BlockPos(vector3d4));
                    } else if (d3 < d2 || this.mc.objectMouseOver == null) {
                        this.mc.objectMouseOver = entityRayTraceResult;
                        if (entity3 instanceof LivingEntity || entity3 instanceof ItemFrameEntity) {
                            this.mc.pointedEntity = entity3;
                        }
                    }
                }
            }
            this.mc.getProfiler().endSection();
        }
    }

    private void updateFovModifierHand() {
        float f = 1.0f;
        if (this.mc.getRenderViewEntity() instanceof AbstractClientPlayerEntity) {
            AbstractClientPlayerEntity abstractClientPlayerEntity = (AbstractClientPlayerEntity)this.mc.getRenderViewEntity();
            f = abstractClientPlayerEntity.getFovModifier();
        }
        this.fovModifierHandPrev = this.fovModifierHand;
        this.fovModifierHand += (f - this.fovModifierHand) * 0.5f;
        if (this.fovModifierHand > 1.5f) {
            this.fovModifierHand = 1.5f;
        }
        if (this.fovModifierHand < 0.1f) {
            this.fovModifierHand = 0.1f;
        }
    }

    public double getFOVModifier(ActiveRenderInfo activeRenderInfo, float f, boolean bl) {
        FluidState fluidState;
        FunctionRegistry functionRegistry;
        BetterMinecraft betterMinecraft;
        boolean bl2;
        if (this.debugView) {
            return 90.0;
        }
        double d = 70.0;
        if (bl) {
            d = this.mc.gameSettings.fov;
            if (Config.isDynamicFov()) {
                d *= (double)MathHelper.lerp(f, this.fovModifierHandPrev, this.fovModifierHand);
            }
        }
        boolean bl3 = false;
        if (this.mc.currentScreen == null) {
            bl3 = this.mc.gameSettings.ofKeyBindZoom.isKeyDown();
        }
        boolean bl4 = bl2 = (betterMinecraft = (functionRegistry = venusfr.getInstance().getFunctionRegistry()).getBetterMinecraft()).isState() && (Boolean)betterMinecraft.smoothCamera.get() != false;
        if (bl2) {
            if (this.lastFlag != bl3) {
                this.zoomAnim.animate(bl3 ? 4.0 : 1.0, 0.5, Easings.QUINT_OUT);
            }
            this.lastFlag = bl3;
            this.zoomAnim.update();
            d /= this.zoomAnim.getValue();
        }
        if (bl3) {
            if (!Config.zoomMode) {
                Config.zoomMode = true;
                Config.zoomSmoothCamera = this.mc.gameSettings.smoothCamera;
                this.mc.gameSettings.smoothCamera = true;
                this.mc.worldRenderer.setDisplayListEntitiesDirty();
            }
            if (!bl2 && Config.zoomMode) {
                d /= 4.0;
            }
        } else if (Config.zoomMode) {
            Config.zoomMode = false;
            this.mc.gameSettings.smoothCamera = Config.zoomSmoothCamera;
            this.mc.worldRenderer.setDisplayListEntitiesDirty();
        }
        if (activeRenderInfo.getRenderViewEntity() instanceof LivingEntity && ((LivingEntity)activeRenderInfo.getRenderViewEntity()).getShouldBeDead()) {
            float f2 = Math.min((float)((LivingEntity)activeRenderInfo.getRenderViewEntity()).deathTime + f, 20.0f);
            d /= (double)((1.0f - 500.0f / (f2 + 500.0f)) * 2.0f + 1.0f);
        }
        if (!(fluidState = activeRenderInfo.getFluidState()).isEmpty()) {
            d = d * 60.0 / 70.0;
        }
        return Reflector.ForgeHooksClient_getFOVModifier.exists() ? Reflector.callDouble(Reflector.ForgeHooksClient_getFOVModifier, this, activeRenderInfo, Float.valueOf(f), d) : d;
    }

    private void hurtCameraEffect(MatrixStack matrixStack, float f) {
        EventCancelOverlay eventCancelOverlay = new EventCancelOverlay(EventCancelOverlay.Overlays.HURT);
        venusfr.getInstance().getEventBus().post(eventCancelOverlay);
        if (eventCancelOverlay.isCancel()) {
            eventCancelOverlay.open();
            return;
        }
        if (this.mc.getRenderViewEntity() instanceof LivingEntity) {
            float f2;
            LivingEntity livingEntity = (LivingEntity)this.mc.getRenderViewEntity();
            float f3 = (float)livingEntity.hurtTime - f;
            if (livingEntity.getShouldBeDead()) {
                f2 = Math.min((float)livingEntity.deathTime + f, 20.0f);
                matrixStack.rotate(Vector3f.ZP.rotationDegrees(40.0f - 8000.0f / (f2 + 200.0f)));
            }
            if (f3 < 0.0f) {
                return;
            }
            f3 /= (float)livingEntity.maxHurtTime;
            f3 = MathHelper.sin(f3 * f3 * f3 * f3 * (float)Math.PI);
            f2 = livingEntity.attackedAtYaw;
            matrixStack.rotate(Vector3f.YP.rotationDegrees(-f2));
            matrixStack.rotate(Vector3f.ZP.rotationDegrees(-f3 * 14.0f));
            matrixStack.rotate(Vector3f.YP.rotationDegrees(f2));
        }
    }

    private void applyBobbing(MatrixStack matrixStack, float f) {
        if (this.mc.getRenderViewEntity() instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity)this.mc.getRenderViewEntity();
            float f2 = playerEntity.distanceWalkedModified - playerEntity.prevDistanceWalkedModified;
            float f3 = -(playerEntity.distanceWalkedModified + f2 * f);
            float f4 = MathHelper.lerp(f, playerEntity.prevCameraYaw, playerEntity.cameraYaw);
            matrixStack.translate(MathHelper.sin(f3 * (float)Math.PI) * f4 * 0.5f, -Math.abs(MathHelper.cos(f3 * (float)Math.PI) * f4), 0.0);
            matrixStack.rotate(Vector3f.ZP.rotationDegrees(MathHelper.sin(f3 * (float)Math.PI) * f4 * 3.0f));
            matrixStack.rotate(Vector3f.XP.rotationDegrees(Math.abs(MathHelper.cos(f3 * (float)Math.PI - 0.2f) * f4) * 5.0f));
        }
    }

    private void renderHand(MatrixStack matrixStack, ActiveRenderInfo activeRenderInfo, float f) {
        this.renderHand(matrixStack, activeRenderInfo, f, true, true, true);
    }

    public void renderHand(MatrixStack matrixStack, ActiveRenderInfo activeRenderInfo, float f, boolean bl, boolean bl2, boolean bl3) {
        FunctionRegistry functionRegistry = venusfr.getInstance().getFunctionRegistry();
        GlassHand glassHand = functionRegistry.getGlassHand();
        if (!this.debugView) {
            Shaders.beginRenderFirstPersonHand(bl3);
            this.resetProjectionMatrix(this.getProjectionMatrix(activeRenderInfo, f, true));
            MatrixStack.Entry entry = matrixStack.getLast();
            entry.getMatrix().setIdentity();
            entry.getNormal().setIdentity();
            boolean bl4 = false;
            if (bl) {
                matrixStack.push();
                this.hurtCameraEffect(matrixStack, f);
                if (this.mc.gameSettings.viewBobbing) {
                    this.applyBobbing(matrixStack, f);
                }
                boolean bl5 = bl4 = this.mc.getRenderViewEntity() instanceof LivingEntity && ((LivingEntity)this.mc.getRenderViewEntity()).isSleeping();
                if (this.mc.gameSettings.getPointOfView().func_243192_a() && !bl4 && !this.mc.gameSettings.hideGUI && this.mc.playerController.getCurrentGameType() != GameType.SPECTATOR) {
                    this.lightmapTexture.enableLightmap();
                    if (Config.isShaders()) {
                        ShadersRender.renderItemFP(this.itemRenderer, f, matrixStack, this.renderTypeBuffers.getBufferSource(), this.mc.player, this.mc.getRenderManager().getPackedLight(this.mc.player, f), bl3);
                    } else if (glassHand.isState()) {
                        glassHand.hands.setup();
                        this.itemRenderer.renderItemInFirstPerson(f, matrixStack, this.renderTypeBuffers.getBufferSource(), this.mc.player, this.mc.getRenderManager().getPackedLight(this.mc.player, f));
                        glassHand.hands.unbindFramebuffer();
                        this.mc.getFramebuffer().bindFramebuffer(false);
                    } else {
                        this.itemRenderer.renderItemInFirstPerson(f, matrixStack, this.renderTypeBuffers.getBufferSource(), this.mc.player, this.mc.getRenderManager().getPackedLight(this.mc.player, f));
                    }
                    this.lightmapTexture.disableLightmap();
                }
                matrixStack.pop();
            }
            Shaders.endRenderFirstPersonHand();
            if (!bl2) {
                return;
            }
            this.lightmapTexture.disableLightmap();
            if (this.mc.gameSettings.getPointOfView().func_243192_a() && !bl4) {
                OverlayRenderer.renderOverlays(this.mc, matrixStack);
                this.hurtCameraEffect(matrixStack, f);
            }
            if (this.mc.gameSettings.viewBobbing) {
                this.applyBobbing(matrixStack, f);
            }
        }
    }

    public void resetProjectionMatrix(Matrix4f matrix4f) {
        RenderSystem.matrixMode(5889);
        RenderSystem.loadIdentity();
        RenderSystem.multMatrix(matrix4f);
        RenderSystem.matrixMode(5888);
    }

    public Matrix4f getProjectionMatrix(ActiveRenderInfo activeRenderInfo, float f, boolean bl) {
        MatrixStack matrixStack = new MatrixStack();
        matrixStack.getLast().getMatrix().setIdentity();
        if (Config.isShaders() && Shaders.isRenderingFirstPersonHand()) {
            Shaders.applyHandDepth(matrixStack);
        }
        this.clipDistance = this.farPlaneDistance * 2.0f;
        if (this.clipDistance < 173.0f) {
            this.clipDistance = 173.0f;
        }
        if (this.cameraZoom != 1.0f) {
            matrixStack.translate(this.cameraYaw, -this.cameraPitch, 0.0);
            matrixStack.scale(this.cameraZoom, this.cameraZoom, 1.0f);
        }
        matrixStack.getLast().getMatrix().mul(Matrix4f.perspective(this.getFOVModifier(activeRenderInfo, f, bl), (float)this.mc.getMainWindow().getFramebufferWidth() / (float)this.mc.getMainWindow().getFramebufferHeight(), 0.05f, this.clipDistance));
        return matrixStack.getLast().getMatrix();
    }

    public static float getNightVisionBrightness(LivingEntity livingEntity, float f) {
        int n = livingEntity.getActivePotionEffect(Effects.NIGHT_VISION).getDuration();
        return n > 200 ? 1.0f : 0.7f + MathHelper.sin(((float)n - f) * (float)Math.PI * 0.2f) * 0.3f;
    }

    public void updateCameraAndRender(float f, long l, boolean bl) {
        this.frameInit();
        if (!(this.mc.isGameFocused() || !this.mc.gameSettings.pauseOnLostFocus || this.mc.gameSettings.touchscreen && this.mc.mouseHelper.isRightDown())) {
            if (Util.milliTime() - this.prevFrameTime > 500L) {
                this.mc.displayInGameMenu(true);
            }
        } else {
            this.prevFrameTime = Util.milliTime();
        }
        if (!this.mc.skipRenderWorld) {
            int n = (int)(this.mc.mouseHelper.getMouseX() * (double)this.mc.getMainWindow().getScaledWidth() / (double)this.mc.getMainWindow().getWidth());
            int n2 = (int)(this.mc.mouseHelper.getMouseY() * (double)this.mc.getMainWindow().getScaledHeight() / (double)this.mc.getMainWindow().getHeight());
            if (bl && this.mc.world != null && !Config.isReloadingResources()) {
                this.mc.getProfiler().startSection("level");
                this.renderWorld(f, l, new MatrixStack());
                if (this.mc.isSingleplayer() && this.timeWorldIcon < Util.milliTime() - 1000L) {
                    this.timeWorldIcon = Util.milliTime();
                    if (!this.mc.getIntegratedServer().isWorldIconSet()) {
                        this.createWorldIcon();
                    }
                }
                this.mc.worldRenderer.renderEntityOutlineFramebuffer();
                if (this.shaderGroup != null && this.useShader) {
                    RenderSystem.disableBlend();
                    RenderSystem.disableDepthTest();
                    RenderSystem.disableAlphaTest();
                    RenderSystem.enableTexture();
                    RenderSystem.matrixMode(5890);
                    RenderSystem.pushMatrix();
                    RenderSystem.loadIdentity();
                    this.shaderGroup.render(f);
                    RenderSystem.popMatrix();
                    RenderSystem.enableTexture();
                }
                this.mc.getFramebuffer().bindFramebuffer(false);
            } else {
                RenderSystem.viewport(0, 0, this.mc.getMainWindow().getFramebufferWidth(), this.mc.getMainWindow().getFramebufferHeight());
            }
            MainWindow mainWindow = this.mc.getMainWindow();
            RenderSystem.clear(256, Minecraft.IS_RUNNING_ON_MAC);
            RenderSystem.matrixMode(5889);
            RenderSystem.loadIdentity();
            RenderSystem.ortho(0.0, (double)mainWindow.getFramebufferWidth() / mainWindow.getGuiScaleFactor(), (double)mainWindow.getFramebufferHeight() / mainWindow.getGuiScaleFactor(), 0.0, 1000.0, 3000.0);
            RenderSystem.matrixMode(5888);
            RenderSystem.loadIdentity();
            RenderSystem.translatef(0.0f, 0.0f, -2000.0f);
            RenderHelper.setupGui3DDiffuseLighting();
            MatrixStack matrixStack = new MatrixStack();
            if (this.lightmapTexture.isCustom()) {
                this.lightmapTexture.setAllowed(true);
            }
            if (bl && this.mc.world != null) {
                float f2;
                this.mc.getProfiler().endStartSection("gui");
                if (this.mc.player != null && (f2 = MathHelper.lerp(f, this.mc.player.prevTimeInPortal, this.mc.player.timeInPortal)) > 0.0f && this.mc.player.isPotionActive(Effects.NAUSEA) && this.mc.gameSettings.screenEffectScale < 1.0f) {
                    this.func_243497_c(f2 * (1.0f - this.mc.gameSettings.screenEffectScale));
                }
                if (!this.mc.gameSettings.hideGUI || this.mc.currentScreen != null) {
                    RenderSystem.defaultAlphaFunc();
                    this.renderItemActivation(this.mc.getMainWindow().getScaledWidth(), this.mc.getMainWindow().getScaledHeight(), f);
                    this.mc.ingameGUI.renderIngameGui(matrixStack, f);
                    if (this.mc.gameSettings.ofShowFps && !this.mc.gameSettings.showDebugInfo) {
                        Config.drawFps(matrixStack);
                    }
                    if (this.mc.gameSettings.showDebugInfo) {
                        Lagometer.showLagometer(matrixStack, (int)this.mc.getMainWindow().getGuiScaleFactor());
                    }
                    RenderSystem.clear(256, Minecraft.IS_RUNNING_ON_MAC);
                }
                this.mc.getProfiler().endSection();
            }
            if (this.guiLoadingVisible != (this.mc.loadingGui != null)) {
                if (this.mc.loadingGui != null) {
                    ResourceLoadProgressGui.loadLogoTexture(this.mc);
                    if (this.mc.loadingGui instanceof ResourceLoadProgressGui) {
                        ResourceLoadProgressGui resourceLoadProgressGui = (ResourceLoadProgressGui)this.mc.loadingGui;
                        resourceLoadProgressGui.update();
                    }
                }
                boolean bl2 = this.guiLoadingVisible = this.mc.loadingGui != null;
            }
            if (this.mc.loadingGui != null) {
                try {
                    this.mc.loadingGui.render(matrixStack, n, n2, this.mc.getTickLength());
                } catch (Throwable throwable) {
                    CrashReport crashReport = CrashReport.makeCrashReport(throwable, "Rendering overlay");
                    CrashReportCategory crashReportCategory = crashReport.makeCategory("Overlay render details");
                    crashReportCategory.addDetail("Overlay name", this::lambda$updateCameraAndRender$1);
                    throw new ReportedException(crashReport);
                }
            }
            if (this.mc.currentScreen != null) {
                try {
                    if (Reflector.ForgeHooksClient_drawScreen.exists()) {
                        Reflector.callVoid(Reflector.ForgeHooksClient_drawScreen, this.mc.currentScreen, matrixStack, n, n2, Float.valueOf(this.mc.getTickLength()));
                    } else {
                        this.mc.currentScreen.render(matrixStack, n, n2, this.mc.getTickLength());
                    }
                } catch (Throwable throwable) {
                    CrashReport crashReport = CrashReport.makeCrashReport(throwable, "Rendering screen");
                    CrashReportCategory crashReportCategory = crashReport.makeCategory("Screen render details");
                    crashReportCategory.addDetail("Screen name", this::lambda$updateCameraAndRender$2);
                    crashReportCategory.addDetail("Mouse location", () -> this.lambda$updateCameraAndRender$3(n, n2));
                    crashReportCategory.addDetail("Screen size", this::lambda$updateCameraAndRender$4);
                    throw new ReportedException(crashReport);
                }
            }
            this.lightmapTexture.setAllowed(false);
        }
        this.frameFinish();
        this.waitForServerThread();
        MemoryMonitor.update();
        Lagometer.updateLagometer();
        if (this.mc.gameSettings.ofProfiler) {
            this.mc.gameSettings.showDebugProfilerChart = true;
        }
    }

    private void createWorldIcon() {
        if (this.mc.worldRenderer.getRenderedChunks() > 10 && this.mc.worldRenderer.hasNoChunkUpdates() && !this.mc.getIntegratedServer().isWorldIconSet()) {
            NativeImage nativeImage = ScreenShotHelper.createScreenshot(this.mc.getMainWindow().getFramebufferWidth(), this.mc.getMainWindow().getFramebufferHeight(), this.mc.getFramebuffer());
            Util.getRenderingService().execute(() -> this.lambda$createWorldIcon$5(nativeImage));
        }
    }

    private boolean isDrawBlockOutline() {
        boolean bl;
        Objects.requireNonNull(this);
        Entity entity2 = this.mc.getRenderViewEntity();
        boolean bl2 = bl = entity2 instanceof PlayerEntity && !this.mc.gameSettings.hideGUI;
        if (bl && !((PlayerEntity)entity2).abilities.allowEdit) {
            ItemStack itemStack = ((LivingEntity)entity2).getHeldItemMainhand();
            RayTraceResult rayTraceResult = this.mc.objectMouseOver;
            if (rayTraceResult != null && rayTraceResult.getType() == RayTraceResult.Type.BLOCK) {
                BlockPos blockPos = ((BlockRayTraceResult)rayTraceResult).getPos();
                BlockState blockState = this.mc.world.getBlockState(blockPos);
                if (this.mc.playerController.getCurrentGameType() == GameType.SPECTATOR) {
                    bl = blockState.getContainer(this.mc.world, blockPos) != null;
                } else {
                    CachedBlockInfo cachedBlockInfo = new CachedBlockInfo(this.mc.world, blockPos, false);
                    bl = !itemStack.isEmpty() && (itemStack.canDestroy(this.mc.world.getTags(), cachedBlockInfo) || itemStack.canPlaceOn(this.mc.world.getTags(), cachedBlockInfo));
                }
            }
        }
        return bl;
    }

    public void renderWorld(float f, long l, MatrixStack matrixStack) {
        float f2;
        float f3;
        this.lightmapTexture.updateLightmap(f);
        if (this.mc.getRenderViewEntity() == null) {
            this.mc.setRenderViewEntity(this.mc.player);
        }
        this.getMouseOver(f);
        if (Config.isShaders()) {
            Shaders.beginRender(this.mc, this.activeRender, f, l);
        }
        this.mc.getProfiler().startSection("center");
        boolean bl = Config.isShaders();
        if (bl) {
            Shaders.beginRenderPass(f, l);
        }
        boolean bl2 = this.isDrawBlockOutline();
        this.mc.getProfiler().endStartSection("camera");
        ActiveRenderInfo activeRenderInfo = this.activeRender;
        this.farPlaneDistance = this.mc.gameSettings.renderDistanceChunks * 16;
        if (Config.isFogFancy()) {
            this.farPlaneDistance *= 0.95f;
        }
        if (Config.isFogFast()) {
            this.farPlaneDistance *= 0.83f;
        }
        MatrixStack matrixStack2 = new MatrixStack();
        matrixStack2.getLast().getMatrix().mul(this.getProjectionMatrix(activeRenderInfo, f, false));
        MatrixStack matrixStack3 = matrixStack2;
        if (Shaders.isEffectsModelView()) {
            matrixStack2 = matrixStack;
        }
        this.hurtCameraEffect(matrixStack2, f);
        if (this.mc.gameSettings.viewBobbing) {
            this.applyBobbing(matrixStack2, f);
        }
        if ((f3 = MathHelper.lerp(f, this.mc.player.prevTimeInPortal, this.mc.player.timeInPortal) * this.mc.gameSettings.screenEffectScale * this.mc.gameSettings.screenEffectScale) > 0.0f) {
            int n = this.mc.player.isPotionActive(Effects.NAUSEA) ? 7 : 20;
            float f4 = 5.0f / (f3 * f3 + 5.0f) - f3 * 0.04f;
            f4 *= f4;
            Vector3f vector3f = new Vector3f(0.0f, MathHelper.SQRT_2 / 2.0f, MathHelper.SQRT_2 / 2.0f);
            matrixStack2.rotate(vector3f.rotationDegrees(((float)this.rendererUpdateCount + f) * (float)n));
            matrixStack2.scale(1.0f / f4, 1.0f, 1.0f);
            f2 = -((float)this.rendererUpdateCount + f) * (float)n;
            matrixStack2.rotate(vector3f.rotationDegrees(f2));
        }
        if (Shaders.isEffectsModelView()) {
            matrixStack2 = matrixStack3;
        }
        Matrix4f matrix4f = matrixStack2.getLast().getMatrix();
        this.resetProjectionMatrix(matrix4f);
        activeRenderInfo.update(this.mc.world, this.mc.getRenderViewEntity() == null ? this.mc.player : this.mc.getRenderViewEntity(), !this.mc.gameSettings.getPointOfView().func_243192_a(), this.mc.gameSettings.getPointOfView().func_243193_b(), f);
        if (Reflector.ForgeHooksClient_onCameraSetup.exists()) {
            Object object = Reflector.ForgeHooksClient_onCameraSetup.call(this, activeRenderInfo, Float.valueOf(f));
            float f5 = Reflector.callFloat(object, Reflector.EntityViewRenderEvent_CameraSetup_getYaw, new Object[0]);
            f2 = Reflector.callFloat(object, Reflector.EntityViewRenderEvent_CameraSetup_getPitch, new Object[0]);
            float f6 = Reflector.callFloat(object, Reflector.EntityViewRenderEvent_CameraSetup_getRoll, new Object[0]);
            activeRenderInfo.setAnglesInternal(f5, f2);
            matrixStack.rotate(Vector3f.ZP.rotationDegrees(f6));
        }
        matrixStack.rotate(Vector3f.XP.rotationDegrees(activeRenderInfo.getPitch()));
        matrixStack.rotate(Vector3f.YP.rotationDegrees(activeRenderInfo.getYaw() + 180.0f));
        this.mc.worldRenderer.updateCameraAndRender(matrixStack, f, l, bl2, activeRenderInfo, this, this.lightmapTexture, matrix4f);
        if (Reflector.ForgeHooksClient_dispatchRenderLast.exists()) {
            this.mc.getProfiler().endStartSection("forge_render_last");
            Reflector.callVoid(Reflector.ForgeHooksClient_dispatchRenderLast, this.mc.worldRenderer, matrixStack, Float.valueOf(f), matrix4f, l);
        }
        this.mc.getProfiler().endStartSection("hand");
        Objects.requireNonNull(this);
        if (!Shaders.isShadowPass) {
            if (bl) {
                ShadersRender.renderHand1(this, matrixStack, activeRenderInfo, f);
                Shaders.renderCompositeFinal();
            }
            RenderSystem.clear(256, Minecraft.IS_RUNNING_ON_MAC);
            if (bl) {
                ShadersRender.renderFPOverlay(this, matrixStack, activeRenderInfo, f);
            } else {
                this.renderHand(matrixStack, activeRenderInfo, f);
            }
        }
        if (bl) {
            Shaders.endRender();
        }
        if (venusfr.getInstance().getFunctionRegistry().getSnow().isState()) {
            Snow.onPreRender3D(matrixStack);
        }
        this.mc.getProfiler().endSection();
    }

    public void resetData() {
        this.itemActivationItem = null;
        this.mapItemRenderer.clearLoadedMaps();
        this.activeRender.clear();
    }

    public MapItemRenderer getMapItemRenderer() {
        return this.mapItemRenderer;
    }

    private void waitForServerThread() {
        this.serverWaitTimeCurrent = 0;
        if (Config.isSmoothWorld() && Config.isSingleProcessor()) {
            IntegratedServer integratedServer;
            if (this.mc.isIntegratedServerRunning() && (integratedServer = this.mc.getIntegratedServer()) != null) {
                boolean bl = this.mc.isGamePaused();
                if (!bl && !(this.mc.currentScreen instanceof DownloadTerrainScreen)) {
                    if (this.serverWaitTime > 0) {
                        Lagometer.timerServer.start();
                        Config.sleep(this.serverWaitTime);
                        Lagometer.timerServer.end();
                        this.serverWaitTimeCurrent = this.serverWaitTime;
                    }
                    long l = System.nanoTime() / 1000000L;
                    if (this.lastServerTime != 0L && this.lastServerTicks != 0) {
                        long l2 = l - this.lastServerTime;
                        if (l2 < 0L) {
                            this.lastServerTime = l;
                            l2 = 0L;
                        }
                        if (l2 >= 50L) {
                            this.lastServerTime = l;
                            int n = integratedServer.getTickCounter();
                            int n2 = n - this.lastServerTicks;
                            if (n2 < 0) {
                                this.lastServerTicks = n;
                                n2 = 0;
                            }
                            if (n2 < 1 && this.serverWaitTime < 100) {
                                this.serverWaitTime += 2;
                            }
                            if (n2 > 1 && this.serverWaitTime > 0) {
                                --this.serverWaitTime;
                            }
                            this.lastServerTicks = n;
                        }
                    } else {
                        this.lastServerTime = l;
                        this.lastServerTicks = integratedServer.getTickCounter();
                        this.avgServerTickDiff = 1.0f;
                        this.avgServerTimeDiff = 50.0f;
                    }
                } else {
                    if (this.mc.currentScreen instanceof DownloadTerrainScreen) {
                        Config.sleep(20L);
                    }
                    this.lastServerTime = 0L;
                    this.lastServerTicks = 0;
                }
            }
        } else {
            this.lastServerTime = 0L;
            this.lastServerTicks = 0;
        }
    }

    private void frameInit() {
        ClientWorld clientWorld;
        Config.frameStart();
        GlErrors.frameStart();
        if (!this.initialized) {
            ReflectorResolver.resolve();
            if (Config.getBitsOs() == 64 && Config.getBitsJre() == 32) {
                Config.setNotify64BitJava(true);
            }
            this.initialized = true;
        }
        if ((clientWorld = this.mc.world) != null) {
            Object object;
            if (Config.getNewRelease() != null) {
                object = "HD_U".replace("HD_U", "HD Ultra").replace("L", "Light");
                String string = (String)object + " " + Config.getNewRelease();
                StringTextComponent stringTextComponent = new StringTextComponent(I18n.format("of.message.newVersion", "\u00a7n" + string + "\u00a7r"));
                stringTextComponent.setStyle(Style.EMPTY.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://optifine.net/downloads")));
                this.mc.ingameGUI.getChatGUI().printChatMessage(stringTextComponent);
                Config.setNewRelease(null);
            }
            if (Config.isNotify64BitJava()) {
                Config.setNotify64BitJava(false);
                object = new StringTextComponent(I18n.format("of.message.java64Bit", new Object[0]));
                this.mc.ingameGUI.getChatGUI().printChatMessage((ITextComponent)object);
            }
        }
        if (this.mc.currentScreen instanceof MainScreen) {
            this.updateMainMenu((MainScreen)this.mc.currentScreen);
        }
        if (this.updatedWorld != clientWorld) {
            RandomEntities.worldChanged(this.updatedWorld, clientWorld);
            Config.updateThreadPriorities();
            this.lastServerTime = 0L;
            this.lastServerTicks = 0;
            this.updatedWorld = clientWorld;
        }
        if (!this.setFxaaShader(Shaders.configAntialiasingLevel)) {
            Shaders.configAntialiasingLevel = 0;
        }
        if (this.mc.currentScreen != null && this.mc.currentScreen.getClass() == ChatScreen.class) {
            this.mc.displayGuiScreen(new GuiChatOF((ChatScreen)this.mc.currentScreen));
        }
    }

    private void frameFinish() {
        int n;
        if (this.mc.world != null && Config.isShowGlErrors() && TimedEvent.isActive("CheckGlErrorFrameFinish", 10000L) && (n = GlStateManager.getError()) != 0 && GlErrors.isEnabled(n)) {
            String string = Config.getGlErrorString(n);
            StringTextComponent stringTextComponent = new StringTextComponent(I18n.format("of.message.openglError", n, string));
            this.mc.ingameGUI.getChatGUI().printChatMessage(stringTextComponent);
        }
    }

    private void updateMainMenu(MainScreen mainScreen) {
    }

    public boolean setFxaaShader(int n) {
        if (!GLX.isUsingFBOs()) {
            return true;
        }
        if (this.shaderGroup != null && this.shaderGroup != this.fxaaShaders[2] && this.shaderGroup != this.fxaaShaders[4]) {
            return false;
        }
        if (n != 2 && n != 4) {
            if (this.shaderGroup == null) {
                return false;
            }
            this.shaderGroup.close();
            this.shaderGroup = null;
            return false;
        }
        if (this.shaderGroup != null && this.shaderGroup == this.fxaaShaders[n]) {
            return false;
        }
        if (this.mc.world == null) {
            return false;
        }
        this.loadShader(new ResourceLocation("shaders/post/fxaa_of_" + n + "x.json"));
        this.fxaaShaders[n] = this.shaderGroup;
        return this.useShader;
    }

    public IResourceType getResourceType() {
        return VanillaResourceType.SHADERS;
    }

    public void displayItemActivation(ItemStack itemStack) {
        this.itemActivationItem = itemStack;
        this.itemActivationTicks = 40;
        this.itemActivationOffX = this.random.nextFloat() * 2.0f - 1.0f;
        this.itemActivationOffY = this.random.nextFloat() * 2.0f - 1.0f;
    }

    private void renderItemActivation(int n, int n2, float f) {
        if (this.itemActivationItem != null && this.itemActivationTicks > 0) {
            int n3 = 40 - this.itemActivationTicks;
            float f2 = ((float)n3 + f) / 40.0f;
            float f3 = f2 * f2;
            float f4 = f2 * f3;
            float f5 = 10.25f * f4 * f3 - 24.95f * f3 * f3 + 25.5f * f4 - 13.8f * f3 + 4.0f * f2;
            float f6 = f5 * (float)Math.PI;
            float f7 = this.itemActivationOffX * (float)(n / 4);
            float f8 = this.itemActivationOffY * (float)(n2 / 4);
            RenderSystem.enableAlphaTest();
            RenderSystem.pushMatrix();
            RenderSystem.pushLightingAttributes();
            RenderSystem.enableDepthTest();
            RenderSystem.disableCull();
            MatrixStack matrixStack = new MatrixStack();
            matrixStack.push();
            matrixStack.translate((float)(n / 2) + f7 * MathHelper.abs(MathHelper.sin(f6 * 2.0f)), (float)(n2 / 2) + f8 * MathHelper.abs(MathHelper.sin(f6 * 2.0f)), -50.0);
            float f9 = 50.0f + 175.0f * MathHelper.sin(f6);
            matrixStack.scale(f9, -f9, f9);
            matrixStack.rotate(Vector3f.YP.rotationDegrees(900.0f * MathHelper.abs(MathHelper.sin(f6))));
            matrixStack.rotate(Vector3f.XP.rotationDegrees(6.0f * MathHelper.cos(f2 * 8.0f)));
            matrixStack.rotate(Vector3f.ZP.rotationDegrees(6.0f * MathHelper.cos(f2 * 8.0f)));
            IRenderTypeBuffer.Impl impl = this.renderTypeBuffers.getBufferSource();
            this.mc.getItemRenderer().renderItem(this.itemActivationItem, ItemCameraTransforms.TransformType.FIXED, 0xF000F0, OverlayTexture.NO_OVERLAY, matrixStack, impl);
            matrixStack.pop();
            impl.finish();
            RenderSystem.popAttributes();
            RenderSystem.popMatrix();
            RenderSystem.enableCull();
            RenderSystem.disableDepthTest();
        }
    }

    private void func_243497_c(float f) {
        int n = this.mc.getMainWindow().getScaledWidth();
        int n2 = this.mc.getMainWindow().getScaledHeight();
        double d = MathHelper.lerp((double)f, 2.0, 1.0);
        float f2 = 0.2f * f;
        float f3 = 0.4f * f;
        float f4 = 0.2f * f;
        double d2 = (double)n * d;
        double d3 = (double)n2 * d;
        double d4 = ((double)n - d2) / 2.0;
        double d5 = ((double)n2 - d3) / 2.0;
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
        RenderSystem.color4f(f2, f3, f4, 1.0f);
        this.mc.getTextureManager().bindTexture(field_243496_c);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferBuilder.pos(d4, d5 + d3, -90.0).tex(0.0f, 1.0f).endVertex();
        bufferBuilder.pos(d4 + d2, d5 + d3, -90.0).tex(1.0f, 1.0f).endVertex();
        bufferBuilder.pos(d4 + d2, d5, -90.0).tex(1.0f, 0.0f).endVertex();
        bufferBuilder.pos(d4, d5, -90.0).tex(0.0f, 0.0f).endVertex();
        tessellator.draw();
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
    }

    public float getBossColorModifier(float f) {
        return MathHelper.lerp(f, this.bossColorModifierPrev, this.bossColorModifier);
    }

    public float getFarPlaneDistance() {
        return this.farPlaneDistance;
    }

    public ActiveRenderInfo getActiveRenderInfo() {
        return this.activeRender;
    }

    public LightTexture getLightTexture() {
        return this.lightmapTexture;
    }

    public OverlayTexture getOverlayTexture() {
        return this.overlayTexture;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void lambda$createWorldIcon$5(NativeImage nativeImage) {
        int n = nativeImage.getWidth();
        int n2 = nativeImage.getHeight();
        int n3 = 0;
        int n4 = 0;
        if (n > n2) {
            n3 = (n - n2) / 2;
            n = n2;
        } else {
            n4 = (n2 - n) / 2;
            n2 = n;
        }
        try (NativeImage nativeImage2 = new NativeImage(64, 64, false);){
            nativeImage.resizeSubRectTo(n3, n4, n, n2, nativeImage2);
            nativeImage2.write(this.mc.getIntegratedServer().getWorldIconFile());
        } catch (IOException iOException) {
            LOGGER.warn("Couldn't save auto screenshot", (Throwable)iOException);
        } finally {
            nativeImage.close();
        }
    }

    private String lambda$updateCameraAndRender$4() throws Exception {
        return String.format(Locale.ROOT, "Scaled: (%d, %d). Absolute: (%d, %d). Scale factor of %f", this.mc.getMainWindow().getScaledWidth(), this.mc.getMainWindow().getScaledHeight(), this.mc.getMainWindow().getFramebufferWidth(), this.mc.getMainWindow().getFramebufferHeight(), this.mc.getMainWindow().getGuiScaleFactor());
    }

    private String lambda$updateCameraAndRender$3(int n, int n2) throws Exception {
        return String.format(Locale.ROOT, "Scaled: (%d, %d). Absolute: (%f, %f)", n, n2, this.mc.mouseHelper.getMouseX(), this.mc.mouseHelper.getMouseY());
    }

    private String lambda$updateCameraAndRender$2() throws Exception {
        return this.mc.currentScreen.getClass().getCanonicalName();
    }

    private String lambda$updateCameraAndRender$1() throws Exception {
        return this.mc.loadingGui.getClass().getCanonicalName();
    }

    private static boolean lambda$getMouseOver$0(Entity entity2) {
        return !entity2.isSpectator() && entity2.canBeCollidedWith();
    }
}

