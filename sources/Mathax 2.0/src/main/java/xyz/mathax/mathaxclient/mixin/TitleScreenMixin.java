package xyz.mathax.mathaxclient.mixin;

import xyz.mathax.mathaxclient.MatHax;
import xyz.mathax.mathaxclient.gui.renderer.OverlayRenderer;
import xyz.mathax.mathaxclient.systems.config.Config;
import xyz.mathax.mathaxclient.utils.network.Executor;
import xyz.mathax.mathaxclient.utils.network.versions.Version;
import xyz.mathax.mathaxclient.utils.network.versions.Versions;
import xyz.mathax.mathaxclient.gui.prompts.OkPrompt;
import xyz.mathax.mathaxclient.gui.prompts.YesNoPrompt;
import xyz.mathax.mathaxclient.utils.render.LoggedProxyText;
import xyz.mathax.mathaxclient.utils.render.TitleScreenCredits;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {
    private final OverlayRenderer RENDERER = new OverlayRenderer();

    private static boolean firstTimeOpen = true;

    public TitleScreenMixin(Text title) {
        super(title);
    }

    // Update checker

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/TitleScreen;drawStringWithShadow(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)V", ordinal = 0))
    private void onRenderTitleScreen(MatrixStack matrixStack, int mouseX, int mouseY, float delta, CallbackInfo info) {
        if (firstTimeOpen) {
            firstTimeOpen = false;

            Executor.execute(() -> {
                MatHax.LOG.info("Checking for update...");

                Version version = Versions.get();
                Version latestVersion = Versions.getLatest();
                if (latestVersion == null) {
                    MatHax.LOG.info("Could not check for update!");
                    return;
                }

                if (latestVersion.isHigherThan(version)) {
                    MatHax.LOG.info("There is a new version available, {}! You are using {}!", Versions.getStylized(true), Versions.getStylized());
                    YesNoPrompt.create()
                            .title("New Update")
                            .message("A new version of %s for %s is available.", MatHax.NAME, Versions.getMinecraft())
                            .message("Your version: %s", Versions.getStylized())
                            .message("Latest version: %s", Versions.getStylized(true))
                            .message("Do you want to update?")
                            .onYes(() -> Util.getOperatingSystem().open("https://mathaxclient.xyz/download/"))
                            .onNo(() -> OkPrompt.create()
                                    .title("Are you sure?")
                                    .message("Using old versions of %s is not recommended", MatHax.NAME)
                                    .message("and could report in issues.")
                                    .id("new-update-no")
                                    .onOk(this::close)
                                    .show())
                            .id("new-update")
                            .show();
                } else {
                    MatHax.LOG.info("You are using the latest version, {}!", Versions.getStylized());
                }
            });
        }
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void onRender(MatrixStack matrixStack, int mouseX, int mouseY, float delta, CallbackInfo info) {
        LoggedProxyText.render(matrixStack);

        if (Config.get().titleScreenCreditsAndSplashesSetting.get()) {
            TitleScreenCredits.render(matrixStack);
        }
    }
}
