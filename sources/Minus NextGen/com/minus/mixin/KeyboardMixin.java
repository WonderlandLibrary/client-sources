package com.minus.mixin;

import com.minus.Minus;
import com.minus.event.events.client.EventOnKey;
import com.minus.utils.MinecraftInterface;
import net.minecraft.client.Keyboard;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class KeyboardMixin implements MinecraftInterface {
	@Inject(method = "onKey", at = @At(value = "HEAD"))
	public void callKeyPressEventThingFr(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
		if(action == GLFW.GLFW_PRESS && mc.currentScreen == null) {
			Minus.instance.getEventBus().post(new EventOnKey(key, action));
		}
	}
}