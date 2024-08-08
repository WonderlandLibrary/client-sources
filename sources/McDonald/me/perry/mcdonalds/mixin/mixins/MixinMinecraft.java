// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.mixin.mixins;

import net.minecraft.client.multiplayer.PlayerControllerMP;
import me.perry.mcdonalds.features.modules.player.MultiTask;
import net.minecraft.client.entity.EntityPlayerSP;
import me.perry.mcdonalds.McDonalds;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.common.MinecraftForge;
import me.perry.mcdonalds.event.events.KeyEvent;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.minecraft.crash.CrashReport;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ Minecraft.class })
public abstract class MixinMinecraft
{
    @Inject(method = { "shutdownMinecraftApplet" }, at = { @At("HEAD") })
    private void stopClient(final CallbackInfo callbackInfo) {
        this.unload();
    }
    
    @Redirect(method = { "run" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;displayCrashReport(Lnet/minecraft/crash/CrashReport;)V"))
    public void displayCrashReport(final Minecraft minecraft, final CrashReport crashReport) {
        this.unload();
    }
    
    @Inject(method = { "runTickKeyboard" }, at = { @At(value = "INVOKE", remap = false, target = "Lorg/lwjgl/input/Keyboard;getEventKey()I", ordinal = 0, shift = At.Shift.BEFORE) })
    private void onKeyboard(final CallbackInfo callbackInfo) {
        final int n;
        final int i = n = ((Keyboard.getEventKey() == 0) ? (Keyboard.getEventCharacter() + '\u0100') : Keyboard.getEventKey());
        if (Keyboard.getEventKeyState()) {
            final KeyEvent event = new KeyEvent(i);
            MinecraftForge.EVENT_BUS.post((Event)event);
        }
    }
    
    private void unload() {
        McDonalds.LOGGER.info("Initiated client shutdown.");
        McDonalds.onUnload();
        McDonalds.LOGGER.info("Finished client shutdown.");
    }
    
    @Redirect(method = { "sendClickBlockToController" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;isHandActive()Z"))
    private boolean isHandActiveWrapper(final EntityPlayerSP playerSP) {
        return !MultiTask.getInstance().isOn() && playerSP.isHandActive();
    }
    
    @Redirect(method = { "rightClickMouse" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;getIsHittingBlock()Z", ordinal = 0))
    private boolean isHittingBlockHook(final PlayerControllerMP playerControllerMP) {
        return !MultiTask.getInstance().isOn() && playerControllerMP.getIsHittingBlock();
    }
}
