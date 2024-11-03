package net.silentclient.client.mixin.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.*;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.stream.IStream;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import net.minecraft.util.Timer;
import net.minecraft.world.WorldSettings;
import net.silentclient.client.Client;
import net.silentclient.client.event.impl.*;
import net.silentclient.client.gui.SilentScreen;
import net.silentclient.client.gui.lite.LiteMainMenu;
import net.silentclient.client.gui.multiplayer.SilentMultiplayerGui;
import net.silentclient.client.gui.resourcepacks.SilentResourcePacksGui;
import net.silentclient.client.gui.silentmainmenu.MainMenuConcept;
import net.silentclient.client.gui.util.BackgroundPanorama;
import net.silentclient.client.hooks.MinecraftHook;
import net.silentclient.client.mixin.ducks.GuiMultiplayerExt;
import net.silentclient.client.mixin.ducks.MinecraftExt;
import net.silentclient.client.mods.player.ZoomMod;
import net.silentclient.client.mods.render.AnimationsMod;
import net.silentclient.client.mods.settings.FPSBoostMod;
import net.silentclient.client.mods.settings.GeneralMod;
import org.apache.commons.lang3.SystemUtils;
import org.apache.logging.log4j.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.io.IOException;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin implements MinecraftExt {
    @Inject(method = "startGame", at = @At("HEAD"))
    public void initClient(CallbackInfo callbackInfo) throws IOException {
        Client.getInstance().init();
    }

    @Inject(method = "startGame", at = @At("TAIL"))
    public void startClient(CallbackInfo callbackInfo) throws Throwable {
        Client.getInstance().setiMetadataSerializer(this.metadataSerializer_);
        Client.getInstance().start();
    }

    @Redirect(method = "startGame", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;displayGuiScreen(Lnet/minecraft/client/gui/GuiScreen;)V"))
    public void mainMenuToNews(Minecraft instance, GuiScreen i) {
        if(Client.backgroundPanorama == null) {
            Client.backgroundPanorama = new BackgroundPanorama(Minecraft.getMinecraft());
        }
    }

    @Override
    public void setSession(Object session) {
        this.session = (Session) session;
    }

    @Inject(method = "shutdownMinecraftApplet", at = @At("HEAD"))
    public void shutdownClient(CallbackInfo callbackInfo) throws Throwable {
        Client.getInstance().shutdown();
    }

    @Redirect(method = "createDisplay", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/Display;setTitle(Ljava/lang/String;)V"))
    public void setWindowTitle(String newTitle) {
        Client.getInstance().updateWindowTitle();
    }

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;info(Ljava/lang/String;)V", ordinal = 1))
    public void cancelLoggingToken(Logger instance, String s) {

    }

    @Inject(method = "runTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/profiler/Profiler;endSection()V", ordinal = 1, shift = At.Shift.BEFORE))
    public void tickEvent(CallbackInfo callbackInfo) {
        new ClientTickEvent().call();
    }

    @Redirect(method = "runTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/settings/KeyBinding;setKeyBindState(IZ)V"))
    public void keyEvent(int keybinding, boolean keyCode) {
        KeyBinding.setKeyBindState(keybinding, keyCode);
        KeyEvent event = new KeyEvent(keybinding);

        event.call();
    }

    @Shadow abstract void displayGuiScreen(GuiScreen screen);

    @Shadow private static int debugFPS;

    @Shadow public abstract TextureManager getTextureManager();

    @Shadow @Final private IMetadataSerializer metadataSerializer_;

    @Shadow private boolean fullscreen;

    @Shadow public int displayWidth;

    @Shadow public int displayHeight;

    @Shadow public WorldClient theWorld;

    @Shadow public EntityRenderer entityRenderer;

    @Shadow @Final public File mcDataDir;

    @Shadow private Framebuffer framebufferMc;

    @Shadow public GuiScreen currentScreen;

    @Shadow public GameSettings gameSettings;

    @Mutable
    @Shadow @Final private Session session;

    @Shadow private Timer timer;

    @Shadow public EntityPlayerSP thePlayer;

    @Inject(method = "displayGuiScreen", at = @At("HEAD"), cancellable = true)
    public void displayGuiScreenInject(GuiScreen guiScreenIn, CallbackInfo ci) {
        if(guiScreenIn instanceof SilentScreen) {
            Client.logger.info("Opening menu: " + guiScreenIn.getClass().toString());
        }
        if(Client.backgroundPanorama == null) {
            Client.backgroundPanorama = new BackgroundPanorama(Minecraft.getMinecraft());
        }
        if((guiScreenIn instanceof GuiMainMenu || (guiScreenIn == null && this.theWorld == null)) && Client.getInstance().getGlobalSettings() != null) {
            displayGuiScreen(Client.getInstance().getGlobalSettings().isLite() ? new LiteMainMenu() : new MainMenuConcept());
            ci.cancel();
            return;
        }
        if(guiScreenIn instanceof GuiScreenResourcePacks) {
            displayGuiScreen(new SilentResourcePacksGui(this.currentScreen));
            ci.cancel();
            return;
        }
        if(guiScreenIn instanceof GuiMultiplayer) {
            displayGuiScreen(new SilentMultiplayerGui((GuiScreen) ((GuiMultiplayerExt) guiScreenIn).silent$getParentScreen()));
            ci.cancel();
            return;
        }
        if(guiScreenIn instanceof GuiIngameMenu) {
            displayGuiScreen(new net.silentclient.client.gui.minecraft.GuiIngameMenu());
            ci.cancel();
            return;
        }
    }

    /**
     * @author kirillsaint
     * @reason custom splash screen
     */
    @Overwrite
    public void drawSplashScreen(TextureManager textureManagerInstance) throws LWJGLException {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        int scaleFactor = scaledResolution.getScaleFactor();
        Framebuffer framebuffer = new Framebuffer(scaledResolution.getScaledWidth() * scaleFactor, scaledResolution.getScaledHeight() * scaleFactor, true);
        framebuffer.bindFramebuffer(false);
        GlStateManager.matrixMode(GL11.GL_PROJECTION);
        GlStateManager.loadIdentity();
        GlStateManager.ortho(0.0D, (double) scaledResolution.getScaledWidth(), (double) scaledResolution.getScaledHeight(), 0.0D, 1000.0D, 3000.0D);
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        GlStateManager.loadIdentity();
        GlStateManager.translate(0.0F, 0.0F, -2000.0F);
        GlStateManager.disableLighting();
        GlStateManager.disableFog();
        GlStateManager.disableDepth();
        GlStateManager.enableTexture2D();
        textureManagerInstance.bindTexture(new ResourceLocation("silentclient/splash.png"));
        GlStateManager.resetColor();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        Gui.drawScaledCustomSizeModalRect(0, 0, 0, 0, 1920, 1080, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), 1920, 1080);
        framebuffer.unbindFramebuffer();
        framebuffer.framebufferRender(scaledResolution.getScaledWidth() * scaleFactor, scaledResolution.getScaledHeight() * scaleFactor);
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(516, 0.1F);
        Minecraft.getMinecraft().updateDisplay();
    }

    @Inject(method = "clickMouse", at = @At("HEAD"))
    public void callClickLeftMouseEvent(CallbackInfo ci) {
        new EventClickMouse(0).call();
    }

    @Inject(method = "rightClickMouse", at = @At("HEAD"), cancellable = true)
    public void rightClickMouse(CallbackInfo ci) {
        if (AnimationsMod.getSettingBoolean("Punching During Usage") &&
                Minecraft.getMinecraft().playerController.getIsHittingBlock() &&
                Minecraft.getMinecraft().thePlayer.getHeldItem() != null &&
                (Minecraft.getMinecraft().thePlayer.getHeldItem().getItemUseAction() != EnumAction.NONE ||
                        Minecraft.getMinecraft().thePlayer.getHeldItem().getItem() instanceof ItemBlock)) {
            // This sends packets to the server saying we stopped breaking.
            // Simply making getIsHittingBlock return false will make the server
            // think we are still breaking the block while right clicking.
            // Which is bad. Obviously.
            Minecraft.getMinecraft().playerController.resetBlockRemoving();
            ci.cancel();
        }
    }

    @Inject(method = "runTick", at=@At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;rightClickMouse()V", ordinal = 0))
    public void rightClickEvent(CallbackInfo ci) {
        new EventClickMouse(1).call();
    }

    /**
     * @author kirillsaint
     * @reason call event
     */
    @Overwrite
    public static int getDebugFPS()
    {
        EventDebugFps event = new EventDebugFps(debugFPS);
        event.call();
        return event.getFps();
    }

    @Inject(method = "runTick", at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Mouse;getEventDWheel()I"))
    public void scroll(CallbackInfo ci) {
        int dWheel = Mouse.getEventDWheel();

        EventScrollMouse event = new EventScrollMouse(dWheel);
        event.call();
    }

    @Redirect(method = "runTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/InventoryPlayer;changeCurrentItem(I)V"))
    public void cancelInventoryScroll(InventoryPlayer instance, int direction) {
        if(!Client.getInstance().getSettingsManager().getSettingByClass(GeneralMod.class, "Disable Scroll Wheel").getValBoolean()) {
            instance.changeCurrentItem(direction);
        }
    }

    @Redirect(method = "runTick", at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Mouse;getEventDWheel()I"))
    public int cancelScroll() {
        if(!Client.getInstance().getModInstances().getZoomMod().isEnabled() || !Client.getInstance().getModInstances().getZoomMod().isActive() || !Client.getInstance().getSettingsManager().getSettingByClass(ZoomMod.class, "Scroll").getValBoolean()) {
            return Mouse.getEventDWheel();
        }
        return 0;
    }

    @Inject(method = "setInitialDisplayMode", at = @At(value = "HEAD"), cancellable = true)
    private void setInitialDisplayMode(CallbackInfo ci) throws LWJGLException {
        MinecraftHook.displayFix(ci, fullscreen, displayWidth, displayHeight);
    }

    @Inject(method = "toggleFullscreen", at = @At(value = "INVOKE", remap = false, target = "Lorg/lwjgl/opengl/Display;setVSyncEnabled(Z)V", shift = At.Shift.AFTER))
    private void toggleFullscreen(CallbackInfo ci) throws LWJGLException {
        MinecraftHook.fullScreenFix(fullscreen, displayWidth, displayHeight);
    }

    @Inject(method = "checkGLError", at = @At("HEAD"), cancellable = true)
    public void cancelCheckGLError(String message, CallbackInfo ci) {
        if(Client.getInstance().getSettingsManager() != null && !Client.getInstance().getSettingsManager().getSettingByClass(FPSBoostMod.class, "Check glError").getValBoolean()) {
            ci.cancel();
        }
    }

    @Inject(method = "loadWorld(Lnet/minecraft/client/multiplayer/WorldClient;Ljava/lang/String;)V", at = @At("HEAD"))
    private void clearLoadedMaps(WorldClient worldClientIn, String loadingMessage, CallbackInfo ci) {
        if (worldClientIn != this.theWorld) {
            this.entityRenderer.getMapItemRenderer().clearLoadedMaps();
        }
    }

    @Redirect(
            method = "loadWorld(Lnet/minecraft/client/multiplayer/WorldClient;Ljava/lang/String;)V",
            at = @At(value = "INVOKE", target = "Ljava/lang/System;gc()V")
    )
    private void optimizedWorldSwapping() {
    }

    //#if MC==10809
    @Redirect(
            method = "runGameLoop",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/stream/IStream;func_152935_j()V")
    )
    private void skipTwitchCode1(IStream instance) {
        // No-op
    }

    @Redirect(
            method = "runGameLoop",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/stream/IStream;func_152922_k()V")
    )
    private void skipTwitchCode2(IStream instance) {
        // No-op
    }
    //#endif

    //#if MC==10809
    @Inject(method = "toggleFullscreen", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/Display;setFullscreen(Z)V", remap = false))
    private void silent$resolveScreenState(CallbackInfo ci) {
        if (!this.fullscreen && SystemUtils.IS_OS_WINDOWS) {
            Display.setResizable(false);
            Display.setResizable(true);
        }
    }
    @Redirect(method = "dispatchKeypresses", at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Keyboard;getEventCharacter()C", remap = false))
    private char silent$resolveForeignKeyboards() {
        return (char) (Keyboard.getEventCharacter() + 256);
    }
    //#endif

    @Redirect(method = "dispatchKeypresses", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiNewChat;printChatMessage(Lnet/minecraft/util/IChatComponent;)V", ordinal = 1))
    public void cancelChatMessage(GuiNewChat instance, IChatComponent chatComponent) {

    }

    @Inject(method = "launchIntegratedServer", at = @At("HEAD"))
    public void event(String folderName, String worldName, WorldSettings worldSettingsIn, CallbackInfo ci) {
        new SingleplayerJoinEvent().call();
    }

    /**
     * @author kirillsaint
     * @reason FPS Limiter
     */
    @Overwrite
    public int getLimitFramerate()
    {
        if (this.theWorld == null && this.currentScreen != null) return this.currentScreen instanceof SilentScreen ? ((SilentScreen) this.currentScreen).getFpsLimit() : 30;

        if(!Display.isActive() && Client.getInstance().getSettingsManager().getSettingByClass(FPSBoostMod.class, "Limit Unfocused FPS").getValBoolean()) {
            return Client.getInstance().getSettingsManager().getSettingByClass(FPSBoostMod.class, "Unfocused FPS Limit").getValInt();
        }

        return this.gameSettings.limitFramerate;
    }

    @Override
    public Object getTimer() {
        return this.timer;
    }
}
