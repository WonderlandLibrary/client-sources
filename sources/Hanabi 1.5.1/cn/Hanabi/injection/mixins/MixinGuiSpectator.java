package cn.Hanabi.injection.mixins;

import net.minecraftforge.fml.relauncher.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import cn.Hanabi.events.*;
import com.darkmagician6.eventapi.*;
import com.darkmagician6.eventapi.events.*;
import org.spongepowered.asm.mixin.injection.*;

@SideOnly(Side.CLIENT)
@Mixin({ GuiSpectator.class })
public class MixinGuiSpectator
{
    @Inject(method = { "renderTooltip" }, at = { @At("RETURN") })
    private void renderTooltip(final ScaledResolution sr, final float partialTicks, final CallbackInfo ci) {
        EventManager.call(new EventRender2D(partialTicks));
    }
}
