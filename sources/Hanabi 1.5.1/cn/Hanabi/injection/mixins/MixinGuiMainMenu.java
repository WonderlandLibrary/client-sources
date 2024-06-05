package cn.Hanabi.injection.mixins;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.client.*;
import ClassSub.*;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ GuiMainMenu.class })
public class MixinGuiMainMenu
{
    @Inject(method = { "initGui" }, at = { @At("HEAD") }, cancellable = true)
    public void onInit(final CallbackInfo ci) {
        Minecraft.getMinecraft().displayGuiScreen((GuiScreen)new Class151());
        ci.cancel();
    }
}
