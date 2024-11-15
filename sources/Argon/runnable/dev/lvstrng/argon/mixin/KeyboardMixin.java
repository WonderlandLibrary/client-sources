// 
// Decompiled by the rizzer xd
// 

package dev.lvstrng.argon.mixin;

import dev.lvstrng.argon.Argon;
import dev.lvstrng.argon.modules.Module;
import dev.lvstrng.argon.modules.impl.SelfDestruct;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Keyboard.class})
public class KeyboardMixin {

    @Inject(method = {"onKey"}, at = {@At("HEAD")})
    private void onPress(final long window, final int key, final int scancode, final int action, final int modifiers, final CallbackInfo ci) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc != null && window == mc.getWindow().getHandle() && !SelfDestruct.isSelfDestructed) {
            for (Module class37 : Argon.INSTANCE.getModuleManager().getModules()) {
                if (key == class37.getKeybind() && action == GLFW.GLFW_PRESS) {
                    class37.method100();
                }
            }
        }
    }
}
