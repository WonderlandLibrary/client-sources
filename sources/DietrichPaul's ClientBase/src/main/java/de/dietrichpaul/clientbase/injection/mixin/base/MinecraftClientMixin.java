/*
 * This file is part of Clientbase - https://github.com/DietrichPaul/Clientbase
 * by DietrichPaul, FlorianMichael and contributors
 *
 * To the extent possible under law, the person who associated CC0 with
 * Clientbase has waived all copyright and related or neighboring rights
 * to Clientbase.
 *
 * You should have received a copy of the CC0 legalcode along with this
 * work.  If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */
package de.dietrichpaul.clientbase.injection.mixin.base;

import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.util.render.OpenGL;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = MinecraftClient.class, priority = Integer.MIN_VALUE)
public class MinecraftClientMixin {

    @Inject(method = "<init>", at = @At(value = "FIELD", target = "Lnet/minecraft/client/MinecraftClient;inGameHud:Lnet/minecraft/client/gui/hud/InGameHud;", shift = At.Shift.AFTER, ordinal = 0))
    public void onStart(RunArgs args, CallbackInfo ci) {
        ClientBase.INSTANCE.init();
    }

    @Inject(method = "stop", at = @At("HEAD"))
    public void onStop(CallbackInfo ci) {
        ClientBase.INSTANCE.stop();
    }

    @Inject(method = "render", at = @At("HEAD"))
    public void onRender(boolean tick, CallbackInfo ci) {
        OpenGL.drawFrame();
    }
}
