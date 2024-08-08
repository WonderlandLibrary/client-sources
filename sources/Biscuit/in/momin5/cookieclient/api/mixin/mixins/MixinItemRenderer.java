package in.momin5.cookieclient.api.mixin.mixins;

import in.momin5.cookieclient.CookieClient;
import in.momin5.cookieclient.api.event.events.RenderFirstPersonEvent;
import in.momin5.cookieclient.api.module.ModuleManager;
import in.momin5.cookieclient.client.modules.render.ViewModel;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public class MixinItemRenderer {
    @Inject(method = "transformSideFirstPerson", at = @At("HEAD"))
    public void transformSideFirstPerson(EnumHandSide hand, float p_187459_2_, CallbackInfo callbackInfo) {
        RenderFirstPersonEvent event = new RenderFirstPersonEvent(hand);
        CookieClient.EVENT_BUS.post(event);
    }

    @Inject(method = "transformEatFirstPerson", at = @At("HEAD"), cancellable = true)
    public void transformEatFirstPerson(float p_187454_1_, EnumHandSide hand, ItemStack stack, CallbackInfo callbackInfo) {
        RenderFirstPersonEvent event = new RenderFirstPersonEvent(hand);
        CookieClient.EVENT_BUS.post(event);

        ViewModel viewModel = (ViewModel) ModuleManager.getModule(ViewModel.class);

        if (viewModel.isEnabled() && viewModel.cancelEating.isEnabled()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method = "transformFirstPerson", at = @At("HEAD"))
    public void transformFirstPerson(EnumHandSide hand, float p_187453_2_, CallbackInfo callbackInfo) {
        RenderFirstPersonEvent event = new RenderFirstPersonEvent(hand);
        CookieClient.EVENT_BUS.post(event);
    }

}
