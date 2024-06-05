package cn.Hanabi.injection.mixins;

import cn.Hanabi.injection.interfaces.*;
import net.minecraft.client.settings.*;
import org.spongepowered.asm.mixin.*;

@Mixin({ KeyBinding.class })
public class MixinKeyBinding implements IKeyBinding
{
    @Shadow
    private boolean pressed;
    
    @Override
    public boolean getPress() {
        return this.pressed;
    }
    
    @Override
    public void setPress(final Boolean b) {
        this.pressed = b;
    }
}
