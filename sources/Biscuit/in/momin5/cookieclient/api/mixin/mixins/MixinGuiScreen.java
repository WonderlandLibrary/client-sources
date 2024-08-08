package in.momin5.cookieclient.api.mixin.mixins;

import in.momin5.cookieclient.CookieClient;
import in.momin5.cookieclient.api.event.events.TooltipRenderEvent;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(GuiScreen.class)
public abstract class MixinGuiScreen {
    @Shadow
    protected List<GuiButton> buttonList;

    @Shadow
    public int width;

    @Shadow
    public int height;

    @Shadow
    protected FontRenderer fontRenderer;

    @Inject(method = "renderToolTip", at = @At("HEAD"), cancellable = true)
    public void renderToolTip(ItemStack stack, int x, int y, CallbackInfo p_Info)
    {
        TooltipRenderEvent l_Event = new TooltipRenderEvent(stack, x, y);
        CookieClient.EVENT_BUS.post(l_Event);
        if (l_Event.isCancelled())
            p_Info.cancel();
    }
}
