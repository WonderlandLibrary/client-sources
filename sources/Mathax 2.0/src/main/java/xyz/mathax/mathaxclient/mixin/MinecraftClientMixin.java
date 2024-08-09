package xyz.mathax.mathaxclient.mixin;

import com.mojang.authlib.minecraft.UserApiService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import xyz.mathax.mathaxclient.MatHax;
import xyz.mathax.mathaxclient.events.entity.player.ItemUseCrosshairTargetEvent;
import xyz.mathax.mathaxclient.events.game.GameLeftEvent;
import xyz.mathax.mathaxclient.events.game.OpenScreenEvent;
import xyz.mathax.mathaxclient.events.game.ResourcePacksReloadedEvent;
import xyz.mathax.mathaxclient.events.game.WindowResizedEvent;
import xyz.mathax.mathaxclient.events.world.TickEvent;
import xyz.mathax.mathaxclient.gui.WidgetScreen;
import xyz.mathax.mathaxclient.mixininterface.IMinecraftClient;
import xyz.mathax.mathaxclient.systems.config.Config;
import xyz.mathax.mathaxclient.systems.modules.Modules;
import xyz.mathax.mathaxclient.systems.modules.render.UnfocusedCPU;
import xyz.mathax.mathaxclient.utils.Utils;
import xyz.mathax.mathaxclient.utils.misc.MatHaxIdentifier;
import xyz.mathax.mathaxclient.utils.network.versions.Versions;
import xyz.mathax.mathaxclient.utils.window.Icon;
import xyz.mathax.mathaxclient.utils.window.Title;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.RunArgs;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.util.Window;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.profiler.Profiler;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.CompletableFuture;

@Mixin(value = MinecraftClient.class, priority = 1001)
public abstract class MinecraftClientMixin implements IMinecraftClient {
    private static boolean firstTitleUpdate = true;
    private static boolean isCustom = true;

    @Unique
    private boolean doItemUseCalled;

    @Unique
    private boolean rightClick;

    @Unique
    private long lastTime;

    @Unique
    private boolean firstFrame;

    @Shadow
    public ClientWorld world;

    @Shadow
    @Final
    public Mouse mouse;

    @Shadow
    @Final
    private Window window;

    @Shadow
    public Screen currentScreen;

    @Shadow
    protected abstract void doItemUse();

    @Shadow
    public abstract Profiler getProfiler();

    @Shadow
    public abstract boolean isWindowFocused();

    @Shadow
    @Nullable
    public ClientPlayerInteractionManager interactionManager;

    @Shadow
    @Final
    private String versionType;

    @Shadow
    protected abstract UserApiService createUserApiService(YggdrasilAuthenticationService authService, RunArgs runArgs);

    @Shadow
    public abstract void scheduleStop();

    @Shadow
    public abstract ResourceManager getResourceManager();

    @Shadow
    public abstract boolean forcesUnicodeFont();

    @Shadow
    @Final
    public GameOptions options;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(CallbackInfo info) {
        MatHax.INSTANCE.onInitializeClient();
        firstFrame = true;
    }

    @Inject(at = @At("HEAD"), method = "tick")
    private void onPreTick(CallbackInfo info) {
        doItemUseCalled = false;

        getProfiler().push(MatHax.ID + "_pre_update");
        MatHax.EVENT_BUS.post(TickEvent.Pre.get());
        getProfiler().pop();

        if (rightClick && !doItemUseCalled && interactionManager != null) {
            doItemUse();
        }

        rightClick = false;
    }

    @Inject(at = @At("TAIL"), method = "tick")
    private void onTick(CallbackInfo info) {
        getProfiler().push(MatHax.ID + "_post_update");
        MatHax.EVENT_BUS.post(TickEvent.Post.get());
        getProfiler().pop();
    }

    @Inject(method = "doItemUse", at = @At("HEAD"))
    private void onDoItemUse(CallbackInfo info) {
        doItemUseCalled = true;
    }

    @Inject(method = "disconnect(Lnet/minecraft/client/gui/screen/Screen;)V", at = @At("HEAD"))
    private void onDisconnect(Screen screen, CallbackInfo info) {
        if (world != null) {
            MatHax.EVENT_BUS.post(GameLeftEvent.get());
        }
    }

    @Inject(method = "setScreen", at = @At("HEAD"), cancellable = true)
    private void onSetScreen(Screen screen, CallbackInfo info) {
        if (screen instanceof WidgetScreen) {
            screen.mouseMoved(mouse.getX() * window.getScaleFactor(), mouse.getY() * window.getScaleFactor());
        }

        OpenScreenEvent event = OpenScreenEvent.get(screen);
        MatHax.EVENT_BUS.post(event);

        if (event.isCancelled()) {
            info.cancel();
        }
    }

    @Redirect(method = "doItemUse", at = @At(value = "FIELD", target = "Lnet/minecraft/client/MinecraftClient;crosshairTarget:Lnet/minecraft/util/hit/HitResult;", ordinal = 1))
    private HitResult doItemUseMinecraftClientCrosshairTargetProxy(MinecraftClient client) {
        return MatHax.EVENT_BUS.post(ItemUseCrosshairTargetEvent.get(client.crosshairTarget)).target;
    }

    @Inject(method = "reloadResources(Z)Ljava/util/concurrent/CompletableFuture;", at = @At("RETURN"), cancellable = true)
    private void onReloadResourcesNewCompletableFuture(boolean force, CallbackInfoReturnable<CompletableFuture<Void>> cir) {
        cir.setReturnValue(cir.getReturnValue().thenRun(() -> MatHax.EVENT_BUS.post(ResourcePacksReloadedEvent.get())));
    }

    @ModifyArg(method = "updateWindowTitle", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/Window;setTitle(Ljava/lang/String;)V"))
    private String setTitle(String original) {
        if (Config.get() == null && firstTitleUpdate) {
            firstTitleUpdate = false;
            Title.setTitle(original, false);
            isCustom = false;
        }

        if (Config.get() != null) {
            if (Config.get().customWindowTitleAndIconSetting.get() && !isCustom) {
                Icon.setIcon(new MatHaxIdentifier("icons/64.png"), new MatHaxIdentifier("icons/128.png"));
                Title.setTitle(MatHax.NAME + " " + Versions.getStylized() + " - Minecraft " + versionType + " " + Versions.getMinecraft(), true);
                isCustom = true;
            } else if (!Config.get().customWindowTitleAndIconSetting.get() && isCustom) {
                Icon.setMinecraft();
                Title.setTitle(original, false);
                isCustom = false;
            }
        }

        return Title.getCurrentTitle();
    }

    @Inject(method = "onResolutionChanged", at = @At("TAIL"))
    private void onResolutionChanged(CallbackInfo info) {
        MatHax.EVENT_BUS.post(WindowResizedEvent.get());
    }

    @Inject(method = "getFramerateLimit", at = @At("HEAD"), cancellable = true)
    private void onGetFramerateLimit(CallbackInfoReturnable<Integer> infoReturnable) {
        if (Modules.get().isEnabled(UnfocusedCPU.class) && !isWindowFocused()) {
            infoReturnable.setReturnValue(Math.min(Modules.get().get(UnfocusedCPU.class).fpsSetting.get(), this.options.getMaxFps().getValue()));
        }
    }

    @Inject(method = "render", at = @At("HEAD"))
    private void onRender(CallbackInfo info) {
        long time = Utils.getCurrentTimeMillis();

        if (firstFrame) {
            lastTime = time;
            firstFrame = false;
        }

        Utils.frameTime = (time - lastTime) / 1000.0;
        lastTime = time;
    }

    @Override
    public void rightClick() {
        rightClick = true;
    }
}
