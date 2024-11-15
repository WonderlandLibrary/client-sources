// 
// Decompiled by the rizzer xd
// 

package dev.lvstrng.argon.mixin;

import dev.lvstrng.argon.Argon;
import dev.lvstrng.argon.modules.Module;
import dev.lvstrng.argon.modules.impl.SelfDestruct;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Keyboard.class})
public class KeyboardMixin {
    @Final
    private MinecraftClient mc;

    @Inject(method = {"onKey"}, at = {@At("HEAD")})
    private void onPress(final long window, final int key, final int scancode, final int action, final int modifiers, final CallbackInfo ci) {
        if (window == this.mc.getWindow().getHandle() && !SelfDestruct.isSelfDestructed) {
            for (Module class37 : Argon.INSTANCE.getModuleManager().getModules()) {
                if (key == class37.getKeybind() && action == 1) {
                    class37.method100();
                }
            }
        }
    }
}
