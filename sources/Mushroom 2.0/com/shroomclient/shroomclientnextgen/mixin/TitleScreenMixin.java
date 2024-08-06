package com.shroomclient.shroomclientnextgen.mixin;

import com.shroomclient.shroomclientnextgen.util.RenderUtil;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = TitleScreen.class, priority = 9999)
public abstract class TitleScreenMixin extends Screen {

    protected TitleScreenMixin(Text title) {
        super(title);
    }

    // cancel init because it is EVIL
    @Inject(method = "init", at = @At("HEAD"), cancellable = true)
    public void TitleScreen(CallbackInfo ci) {
        ci.cancel();
    }

    private boolean lmbDown = false;
    private boolean lmbClicked = false;
    private boolean rmbDown = false;
    private boolean rmbClicked = false;
    private boolean mmbDown = false;
    private boolean mmbClicked = false;

    /**
     * @author scoliosis
     * @reason clicking
     */
    @Overwrite
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        switch (mouseButton) {
            case 0:
                lmbDown = true;
                lmbClicked = true;
                break;
            case 1:
                rmbDown = true;
                rmbClicked = true;
                break;
            case 2:
                mmbDown = true;
                mmbClicked = true;
                break;
        }

        return false;
    }

    @Override
    public boolean mouseReleased(
        double mouseX,
        double mouseY,
        int mouseButton
    ) {
        switch (mouseButton) {
            case 0:
                lmbDown = false;
                break;
            case 1:
                rmbDown = false;
                break;
            case 2:
                mmbDown = false;
                break;
        }

        return super.mouseReleased(mouseX, mouseY, mouseButton);
    }

    /**
     * @author Swig
     * @reason Custom main menu
     */
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    public void render(
        DrawContext context,
        int mouseX,
        int mouseY,
        float delta,
        CallbackInfo ci
    ) {
        ci.cancel();

        RenderUtil.setContext(context);

        RenderUtil.drawTitleScreen(mouseX, mouseY, lmbDown);
        lmbDown = false;

        context.getMatrices().translate(1000000, 0, 0);
    }
}
