package net.silentclient.client.mixin.mixins;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import net.silentclient.client.cosmetics.StaticResourceLocation;
import net.silentclient.client.event.impl.EventText;
import net.silentclient.client.mixin.ducks.FontRendererExt;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = FontRenderer.class, priority = 1100)
public abstract class FontRendererMixin implements FontRendererExt {
    @Override
    public StaticResourceLocation silent$getFontTextures() {
        return new StaticResourceLocation(this.locationFontTexture);
    }

    @ModifyVariable(method = "renderString", at = @At("HEAD"), ordinal = 0)
    public String renderString(String text) {
        if(text == null) {
            return text;
        }

        EventText event = new EventText(text);
        event.call();

        return event.getOutputText();
    }

    @ModifyVariable(method = "getStringWidth", at = @At("HEAD"), ordinal = 0)
    private String getStringWidth(String text) {
        if(text == null) {
            return text;
        }

        EventText event = new EventText(text);
        event.call();

        return event.getOutputText();
    }

    @Shadow
    protected abstract void resetStyles();

    @Shadow @Final private ResourceLocation locationFontTexture;

    @Inject(method = "drawString(Ljava/lang/String;FFIZ)I",
            at = @At(
                    value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;renderString(Ljava/lang/String;FFIZ)I",
                    ordinal = 0, shift = At.Shift.AFTER
            )
    )
    private void silent$resetStyle(CallbackInfoReturnable<Integer> ci) {
        this.resetStyles();
    }
}
