// 
// Decompiled by the rizzer xd
// 

package dev.lvstrng.argon.mixin;

import dev.lvstrng.argon.Argon;
import dev.lvstrng.argon.interfaces.IKeybinding;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({KeyBinding.class})
public abstract class KeyBindingMixin implements IKeybinding {
    @Shadow
    private InputUtil.Key boundKey;

    @Override
    public boolean isPressed() {
        return InputUtil.isKeyPressed(Argon.mc.getWindow().getHandle(), this.boundKey.getCode());
    }

    @Override
    public void gwQI() {
        //this.method_23481(this.gwQH());
    }

}
