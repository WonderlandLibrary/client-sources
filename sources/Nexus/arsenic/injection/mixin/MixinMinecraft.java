package arsenic.injection.mixin;

import arsenic.event.impl.EventDisplayGuiScreen;
import arsenic.event.impl.EventGameLoop;
import arsenic.event.impl.EventKey;
import arsenic.event.impl.EventRunTick;
import arsenic.main.Nexus;
import arsenic.main.MinecraftAPI;
import arsenic.module.impl.combat.Aura;
import arsenic.module.impl.exploit.TickBase;
import arsenic.module.impl.player.FastPlace;
import arsenic.module.impl.movement.Scaffold;
import de.florianmichael.viaforge.ViaForge;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.achievement.GuiAchievement;
import net.minecraft.client.main.GameConfiguration;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.stream.IStream;
import net.minecraft.profiler.PlayerUsageSnooper;
import net.minecraft.profiler.Profiler;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.FrameTimer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Util;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.apache.logging.log4j.LogManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.util.Queue;
import java.util.Timer;
import java.util.concurrent.FutureTask;

@Mixin(priority = 1111, value = Minecraft.class)
public abstract class MixinMinecraft {
    @Shadow
    public GuiScreen currentScreen;
    @Shadow
    public boolean skipRenderWorld;

    @Shadow
    public WorldClient theWorld;

    @Shadow
    public EntityPlayerSP thePlayer;

    @Shadow
    public int displayWidth;

    @Shadow
    public int displayHeight;

    @Shadow
    public int rightClickDelayTimer;

    @Shadow
    public GameSettings gameSettings;
    @Shadow
    @Final
    public Profiler mcProfiler;

    @Shadow
    public abstract void runTick();

    @Shadow
    @Final
    private Queue<FutureTask<?>> scheduledTasks;

    @Shadow
    private boolean isGamePaused;
    @Shadow
    private Framebuffer framebufferMc;
    @Shadow
    public EntityRenderer entityRenderer;

    @Shadow
    public abstract void updateDisplay();

    @Final
    public FrameTimer frameTimer;
    long startNanoTime;

    @Shadow
    public abstract void shutdown();

    @Shadow
    int fpsCounter;

    @Shadow
    public abstract boolean isSingleplayer();

    @Shadow
    private IntegratedServer theIntegratedServer;
    @Shadow
    long debugUpdateTime = Minecraft.getSystemTime();
    @Shadow
    private static int debugFPS;
    @Shadow
    private PlayerUsageSnooper usageSnooper;

    @Shadow
    protected abstract void checkGLError(String p_checkGLError_1_);

    @Shadow
    private SoundHandler mcSoundHandler;

    @Shadow
    protected abstract void displayDebugInfo(long p_displayDebugInfo_1_);

    @Shadow
    long prevFrameTime = -1L;
    @Shadow
    public GuiAchievement guiAchievement;
    @Shadow
    private IStream stream;
    @Shadow
    public String debug = "";

    @Shadow
    public abstract boolean isFramerateLimitBelowMax();

    @Shadow
    public abstract int getLimitFramerate();

    @Shadow
    private static Minecraft theMinecraft;

    @ModifyArg(method = "runTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/settings/KeyBinding;setKeyBindState(IZ)V"), index = 0)
    public int getKeybind(int p_setKeyBindState_0_) {
        MinecraftAPI.KEY_CODE = p_setKeyBindState_0_;
        return p_setKeyBindState_0_;
    }

    @Inject(method = "runTick", at = @At(value = "HEAD"))
    public void runTick(CallbackInfo ci) {
        Nexus.getInstance().getEventManager().post(new EventGameLoop());
        MinecraftAPI.KEY_CODE = null;
    }

    @Redirect(method = "runTick", at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Keyboard;getEventKeyState()Z", ordinal = 2))
    public boolean redirectGetKeyState() {
        boolean state = Keyboard.getEventKeyState();
        if (state && MinecraftAPI.KEY_CODE != null && theMinecraft.currentScreen == null) {
            EventKey event = new EventKey(MinecraftAPI.KEY_CODE);
            Nexus.getInstance().getEventManager().post(event);
            MinecraftAPI.KEY_CODE = null;
        }
        return state;
    }

    /**
     * @me
     */

    @Overwrite
    private void runGameLoop() throws IOException {
        long i = System.nanoTime();
        this.mcProfiler.startSection("root");
        if (Display.isCreated() && Display.isCloseRequested()) {
            this.shutdown();
        }

        if (this.isGamePaused && this.theWorld != null) {
            float f = Minecraft.getMinecraft().timer.renderPartialTicks;
            Minecraft.getMinecraft().timer.updateTimer();
            Minecraft.getMinecraft().timer.renderPartialTicks = f;
        } else {
            Minecraft.getMinecraft().timer.updateTimer();
        }

        this.mcProfiler.startSection("scheduledExecutables");
        synchronized (this.scheduledTasks) {
            while (!this.scheduledTasks.isEmpty()) {
                Util.runTask((FutureTask) this.scheduledTasks.poll(), LogManager.getLogger());
            }
        }

        this.mcProfiler.endSection();
        long l = System.nanoTime();
        this.mcProfiler.startSection("tick");

        for (int j = 0; j < Minecraft.getMinecraft().timer.elapsedTicks; ++j) {
            if (Minecraft.getMinecraft().thePlayer != null) {
                boolean skip = false;

                if (j == 0) {
                    TickBase tickbase = Nexus.getNexus().getModuleManager().getModuleByClass(TickBase.class);

                    if (tickbase.isEnabled()) {
                        int extraTicks = tickbase.getExtraTicks();

                        if (extraTicks == -1) {
                            skip = true;
                        } else {
                            if (extraTicks > 0) {
                                for (int aa = 0; aa < extraTicks; aa++) {
                                    this.runTick();
                                }
                                tickbase.freezing = true;
                            }
                        }
                    }
                }

                if (!skip) {
                    this.runTick();
                }
            } else {
                this.runTick();
            }
        }

        this.mcProfiler.endStartSection("preRenderErrors");
        long i1 = System.nanoTime() - l;
        this.checkGLError("Pre render");
        this.mcProfiler.endStartSection("sound");
        this.mcSoundHandler.setListener(this.thePlayer, Minecraft.getMinecraft().timer.renderPartialTicks);
        this.mcProfiler.endSection();
        this.mcProfiler.startSection("render");
        GlStateManager.pushMatrix();
        GlStateManager.clear(16640);
        this.framebufferMc.bindFramebuffer(true);
        this.mcProfiler.startSection("display");
        GlStateManager.enableTexture2D();
        if (this.thePlayer != null && this.thePlayer.isEntityInsideOpaqueBlock()) {
            this.gameSettings.thirdPersonView = 0;
        }

        this.mcProfiler.endSection();
        if (!this.skipRenderWorld) {
            FMLCommonHandler.instance().onRenderTickStart(Minecraft.getMinecraft().timer.renderPartialTicks);
            this.mcProfiler.endStartSection("gameRenderer");
            this.entityRenderer.updateCameraAndRender(Minecraft.getMinecraft().timer.renderPartialTicks, i);
            this.mcProfiler.endSection();
            FMLCommonHandler.instance().onRenderTickEnd(Minecraft.getMinecraft().timer.renderPartialTicks);
        }

        this.mcProfiler.endSection();
        if (this.gameSettings.showDebugInfo && this.gameSettings.showDebugProfilerChart && !this.gameSettings.hideGUI) {
            if (!this.mcProfiler.profilingEnabled) {
                this.mcProfiler.clearProfiling();
            }

            this.mcProfiler.profilingEnabled = true;
            this.displayDebugInfo(i1);
        } else {
            this.mcProfiler.profilingEnabled = false;
            this.prevFrameTime = System.nanoTime();
        }

        this.guiAchievement.updateAchievementWindow();
        this.framebufferMc.unbindFramebuffer();
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        this.framebufferMc.framebufferRender(this.displayWidth, this.displayHeight);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        this.entityRenderer.renderStreamIndicator(Minecraft.getMinecraft().timer.renderPartialTicks);
        GlStateManager.popMatrix();
        this.mcProfiler.startSection("root");
        this.updateDisplay();
        Thread.yield();
        this.mcProfiler.startSection("stream");
        this.mcProfiler.startSection("update");
        this.stream.func_152935_j();
        this.mcProfiler.endStartSection("submit");
        this.stream.func_152922_k();
        this.mcProfiler.endSection();
        this.mcProfiler.endSection();
        this.checkGLError("Post render");
        ++this.fpsCounter;
        this.isGamePaused = this.isSingleplayer() && this.currentScreen != null && this.currentScreen.doesGuiPauseGame() && !this.theIntegratedServer.getPublic();
        long k = System.nanoTime();
        //  this.frameTimer.addFrame(k - this.startNanoTime);
        this.startNanoTime = k;

        while (Minecraft.getSystemTime() >= this.debugUpdateTime + 1000L) {
            debugFPS = this.fpsCounter;
            this.debug = String.format("%d fps (%d chunk update%s) T: %s%s%s%s%s", debugFPS, RenderChunk.renderChunksUpdated, RenderChunk.renderChunksUpdated != 1 ? "s" : "", (float) this.gameSettings.limitFramerate == GameSettings.Options.FRAMERATE_LIMIT.getValueMax() ? "inf" : this.gameSettings.limitFramerate, this.gameSettings.enableVsync ? " vsync" : "", this.gameSettings.fancyGraphics ? "" : " fast", this.gameSettings.clouds == 0 ? "" : (this.gameSettings.clouds == 1 ? " fast-clouds" : " fancy-clouds"), OpenGlHelper.useVbo() ? " vbo" : "");
            RenderChunk.renderChunksUpdated = 0;
            this.debugUpdateTime += 1000L;
            this.fpsCounter = 0;
            this.usageSnooper.addMemoryStatsToSnooper();
            if (!this.usageSnooper.isSnooperRunning()) {
                this.usageSnooper.startSnooper();
            }
        }

        if (this.isFramerateLimitBelowMax()) {
            this.mcProfiler.startSection("fpslimit_wait");
            Display.sync(this.getLimitFramerate());
            this.mcProfiler.endSection();
        }

        this.mcProfiler.endSection();
    }

    @Inject(method = "displayGuiScreen", at = @At(value = "RETURN"))
    public void displayGuiScreen(GuiScreen guiScreenIn, CallbackInfo ci) {
        EventDisplayGuiScreen event = new EventDisplayGuiScreen(guiScreenIn);
        Nexus.getNexus().getEventManager().post(event);
    }

    @Inject(method = "runTick", at = @At("HEAD"))
    public void onRunTick(CallbackInfo ci) {
        Nexus.getInstance().getEventManager().post(new EventRunTick());
    }

    @Inject(method = "rightClickMouse", at = @At("RETURN"))
    public void rightClickMouse(CallbackInfo ci) {
        FastPlace fastPlace = Nexus.getNexus().getModuleManager().getModuleByClass(FastPlace.class);
        Scaffold scaffoldTest = Nexus.getNexus().getModuleManager().getModuleByClass(Scaffold.class);
        if (!fastPlace.isEnabled() && !scaffoldTest.isEnabled()) {
            return;
        }

        rightClickDelayTimer = scaffoldTest.isEnabled() ? 0 : fastPlace.getTickDelay();

    }
}
