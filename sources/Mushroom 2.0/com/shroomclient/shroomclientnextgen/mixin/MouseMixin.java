package com.shroomclient.shroomclientnextgen.mixin;

import com.shroomclient.shroomclientnextgen.util.MouseUtil;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class MouseMixin {

    @Shadow
    private boolean rightButtonClicked;

    @Inject(at = @At("TAIL"), method = "onMouseButton")
    public void onMouseButton_post(
        long window,
        int button,
        int action,
        int mods,
        CallbackInfo ci
    ) {
        MouseUtil.setRealRightClick(rightButtonClicked);
        rightButtonClicked = MouseUtil.getRightClick();
    }
}
