package com.leafclient.leaf.mixin;

import com.leafclient.leaf.Leaf;
import com.leafclient.leaf.event.client.ClientLoadedEvent;
import com.leafclient.leaf.event.game.input.KeyboardTickEvent;
import com.leafclient.leaf.extension.ExtensionMinecraft;
import com.leafclient.leaf.management.event.EventManager;
import com.leafclient.leaf.utils.client.keyboard.Key;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.Session;
import net.minecraft.util.Timer;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft implements ExtensionMinecraft {

    @Shadow @Final @Mutable private Session session;

    @Shadow @Final private Timer timer;

    @Shadow @Nullable public GuiScreen currentScreen;

    @NotNull
    @Override
    public Session getMutableSession() {
        return session;
    }

    @Override
    public void setMutableSession(@NotNull Session session) {
        this.session = session;
    }

    @NotNull
    @Override
    public Timer getTimer() {
        return timer;
    }

    @Inject(
            method = "runTickKeyboard",
            at = @At("TAIL")
    )
    private void inject$runTickKeyboard(CallbackInfo info) {
        EventManager.INSTANCE.publish(new KeyboardTickEvent());
    }

    /**
     * Injects client's loading event
     */
    @Inject(
            method = "init",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/Minecraft;ingameGUI:Lnet/minecraft/client/gui/GuiIngame;"
            )
    )
    private void inject$init(CallbackInfo info) {
        Leaf leaf = Leaf.INSTANCE;
        EventManager.INSTANCE.publish(new ClientLoadedEvent());
    }

    /**
     * Injects client loaded event
     */
    @Inject(method = "init", at = @At("RETURN"))
    private void inject$initEnd(CallbackInfo info) {
        Leaf.INSTANCE.getContributors();
    }

    @Inject(
            method = "runTickKeyboard",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/settings/KeyBinding;setKeyBindState(IZ)V",
                    shift = At.Shift.BEFORE
            )
    )
    private void inject$keyboardEvents(CallbackInfo info) {
        if(currentScreen == null) {
            final int keyCode = Keyboard.getEventKey();
            if(keyCode >= 0 || (keyCode >= -100 && keyCode <= -85)) {
                boolean pressed = Keyboard.getEventKeyState();
                if(pressed) {
                    if(Key.Companion.press(new Key(keyCode)))
                        info.cancel();
                } else {
                    if(Key.Companion.release(new Key(keyCode)))
                        info.cancel();
                }
            }
        }
    }

}
