package com.shroomclient.shroomclientnextgen.mixin;

import com.mojang.authlib.GameProfile;
import com.shroomclient.shroomclientnextgen.ShroomClient;
import com.shroomclient.shroomclientnextgen.auth.SessionManager;
import com.shroomclient.shroomclientnextgen.events.Bus;
import com.shroomclient.shroomclientnextgen.events.impl.ClientTickEvent;
import com.shroomclient.shroomclientnextgen.events.impl.ScreenResolutionChangeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.WorldLoadEvent;
import com.shroomclient.shroomclientnextgen.modules.impl.client.ClickGUI;
import com.shroomclient.shroomclientnextgen.modules.impl.player.FastPlace;
import com.shroomclient.shroomclientnextgen.util.C;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.session.Session;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Mutable
    @Shadow
    @Final
    private Session session;

    @Inject(at = @At("HEAD"), method = "tick")
    public void onTick(CallbackInfo ci) {
        Bus.post(new ClientTickEvent());
    }

    /**
     * @author scoliosis
     * @reason sets minecraft window title
     */
    @Overwrite
    private String getWindowTitle() {
        return "Mushroom Client " + ShroomClient.version;
    }

    private void applySession() {
        if (SessionManager.sessionOverride != null) {
            session = SessionManager.sessionOverride;
        }
    }

    @Inject(at = @At("HEAD"), method = "joinWorld")
    public void onJoinWorld(ClientWorld world, CallbackInfo ci) {
        System.out.println("new world loaded");
        Bus.post(new WorldLoadEvent(world));
    }

    @Inject(at = @At("HEAD"), method = "getSession")
    public void getSession(CallbackInfoReturnable<Session> cir) {
        applySession();
    }

    // This is the only method that directly references the session field instead of using getSession()
    @Inject(at = @At("HEAD"), method = "getGameProfile")
    public void getProfileProperties(CallbackInfoReturnable<GameProfile> cir) {
        applySession();
    }

    @Inject(
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gl/Framebuffer;resize(IIZ)V",
            ordinal = 0
        ),
        method = "onResolutionChanged"
    )
    public void onResolutionChanged(CallbackInfo i) {
        System.out.println("screen resize!!");

        // update scale stuff, i need these everywhere
        ClickGUI.scaleSizeW = 0.0010416667f * C.mc.getWindow().getScaledWidth();
        ClickGUI.scaleSizeH = 0.002016129f * C.mc.getWindow().getScaledHeight();

        Bus.post(new ScreenResolutionChangeEvent());
    }

    @Shadow
    private int itemUseCooldown;

    @Inject(
        method = "doItemUse",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/client/MinecraftClient;itemUseCooldown:I",
            shift = At.Shift.AFTER
        )
    )
    private void hookItemUseCooldown(CallbackInfo callbackInfo) {
        itemUseCooldown = FastPlace.fastPlaceTicks();
    }
}
