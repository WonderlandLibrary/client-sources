package ac.sapphire.client.mixin.client;

import ac.sapphire.client.Sapphire;
import ac.sapphire.client.event.events.game.GameQuitEvent;
import ac.sapphire.client.event.events.game.GameStartEvent;
import ac.sapphire.client.event.events.input.KeyboardInputEvent;
import ac.sapphire.client.event.events.input.MouseInputEvent;
import ac.sapphire.client.event.events.misc.PostTickEvent;
import ac.sapphire.client.event.events.misc.PreTickEvent;
import ac.sapphire.client.module.impl.combat.autoclicker.AutoClickerModule;
import ac.sapphire.client.module.impl.player.FastPlaceModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.item.ItemBlock;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
class MinecraftMixin {
    @Shadow
    public EntityPlayerSP thePlayer;
    @Shadow
    private int leftClickCounter;
    @Shadow
    private int rightClickDelayTimer;

    @Shadow public GameSettings gameSettings;

    @Inject(method = "startGame", at = @At("RETURN"))
    void startGame(CallbackInfo ci) {
        Sapphire.INSTANCE.getEventBus().post(GameStartEvent.INSTANCE);
    }

    @Inject(method = "shutdownMinecraftApplet", at = @At("HEAD"))
    void shutdownMinecraftApplet(CallbackInfo ci) {
        Sapphire.INSTANCE.getEventBus().post(GameQuitEvent.INSTANCE);
    }

    @Inject(method = "runTick", at = @At(value = "HEAD"))
    void runTick(CallbackInfo ci) {
        AutoClickerModule mod = Sapphire.INSTANCE.getModuleManager().get(AutoClickerModule.class);
        if (mod.isToggled() && mod.getRemoveHitCooldown()) {
            leftClickCounter = 0;
        }

        if (thePlayer != null && thePlayer.getHeldItem() != null && thePlayer.getHeldItem().getItem() instanceof ItemBlock && !gameSettings.keyBindAttack.isKeyDown() && Sapphire.INSTANCE.getModuleManager().get(FastPlaceModule.class).isToggled()) {
            rightClickDelayTimer = 0;
        }

        Sapphire.INSTANCE.getEventBus().post(PreTickEvent.INSTANCE);
    }

    @Inject(method = "runTick", at = @At(value = "RETURN"))
    void runTickReturn(CallbackInfo ci) {
        Sapphire.INSTANCE.getEventBus().post(PostTickEvent.INSTANCE);
    }

    @Inject(method = "runTick", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/client/settings/KeyBinding;setKeyBindState(IZ)V", ordinal = 0))
    void runTickMouse(CallbackInfo ci) {
        int button = Mouse.getEventButton(); // -1 = nothing
        boolean state = Mouse.getEventButtonState(); // false = released
        if (button == -1 || state) {
            return;
        }

        Sapphire.INSTANCE.getEventBus().post(new MouseInputEvent(button));
    }

    @Inject(method = "runTick", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/client/settings/KeyBinding;setKeyBindState(IZ)V", ordinal = 1))
    void runTickKeyboard(CallbackInfo ci) {
        int key = Keyboard.getEventKey(); // -1 = nothing
        boolean state = Keyboard.getEventKeyState(); // false = released
        if (key == -1 || state) {
            return;
        }

        Sapphire.INSTANCE.getEventBus().post(new KeyboardInputEvent(key));
    }
}