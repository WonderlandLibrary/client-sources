package club.bluezenith.ui.guis;

import club.bluezenith.core.data.preferences.Preferences;
import club.bluezenith.util.client.MillisTimer;
import club.bluezenith.util.font.TFontRenderer;
import club.bluezenith.util.math.MathUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import java.awt.*;
import java.io.IOException;
import java.util.function.Consumer;

import static club.bluezenith.BlueZenith.getBlueZenith;
import static club.bluezenith.util.font.FontUtil.rubikMedium45;
import static club.bluezenith.util.font.FontUtil.rubikR32;
import static club.bluezenith.util.render.RenderUtil.blur;
import static club.bluezenith.util.render.RenderUtil.rect;

public class GuiConfirmDialog extends GuiScreen {

    private final Consumer<Boolean> confirmDialogCallback;
    private final boolean renderParentScreen;


    private String confirmText = "Yes", declineText = "No", title, description;

    //how long to wait until yes/no buttons can be pressed
    private long activationDelay, activationTime;
    private MillisTimer timer = new MillisTimer();

    private GuiButton yesButton, noButton;


    public GuiConfirmDialog(GuiScreen parentScreen, boolean renderParentScreen, Consumer<Boolean> confirmDialogCallback) {
        this(parentScreen, renderParentScreen, confirmDialogCallback, 0);
    }

    public GuiConfirmDialog(GuiScreen parentScreen, boolean renderParentScreen, Consumer<Boolean> confirmDialogCallback, long activationDelay) {
        this.parentScreen = parentScreen;
        this.renderParentScreen = renderParentScreen;
        this.confirmDialogCallback = confirmDialogCallback;
        this.activationDelay = activationDelay;
    }

    @Override
    public void initGui() {
        if(this.renderParentScreen)
            this.parentScreen.setWorldAndResolution(mc, width, height);

        final int halfWidth = width / 2, halfHeight = (height / 2) + 50;

        final int buttonsHeight = 20, buttonsMargin = 5, buttonsWidth = 150;

        this.buttonList.add(yesButton = new GuiButton(0, halfWidth - buttonsMargin - buttonsWidth, halfHeight - buttonsHeight /2, buttonsWidth, buttonsHeight, this.confirmText).onClick(() -> {
            mc.displayGuiScreen(parentScreen); //go back to previous screen
            confirmDialogCallback.accept(true); //this is the confirm button
        }));

        this.buttonList.add(noButton = new GuiButton(1, halfWidth + buttonsMargin, halfHeight - buttonsHeight /2, buttonsWidth, buttonsHeight, this.declineText).onClick(() -> {
            mc.displayGuiScreen(parentScreen); //go back to previous screen
            confirmDialogCallback.accept(false); //this is the decline button
        }));

        if(!Preferences.useBlurInMenus)
            this.buttonList.forEach(GuiButton::useOutline); //enable outline if blur is disabled, because without blur it's hard to see buttons on a dark bg.

        if(activationDelay > 0) {
            timer.reset();
            yesButton.enabled = false;
            noButton.enabled = false;
            activationTime = System.currentTimeMillis() + activationDelay;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if(this.title == null) {
            getBlueZenith().getNotificationPublisher().postError("GUI Screen", "This screen doesn't have a title! Closing...", 2500);
            mc.displayGuiScreen(parentScreen);
            return;
        }

        if(this.renderParentScreen) {
            parentScreen.drawScreen(-1, -1, partialTicks);
            if(Preferences.useBlurInMenus)
                blur(0, 0, width, height);
            else rect(0, 0, width, height, new Color(2, 2, 2, 200).getRGB());
        } else drawDefaultBackground();


        final TFontRenderer titleRenderer = rubikMedium45;
        final boolean hasDescription = this.description != null;

        final float halfWidth = width / 2F, halfHeight = height / 2F;

        float y;

        titleRenderer.drawString(this.title, 0.01F + halfWidth - titleRenderer.getStringWidthF(this.title) / 2F, y = (halfHeight - (hasDescription ? 30 : titleRenderer.FONT_HEIGHT)), -1);

        if(activationDelay > 0) {
            if(timer.hasTimeReached(activationDelay)) {
                yesButton.enabled = true;
                noButton.enabled = true;
            } else {
                final String message = String.format("Wait %s seconds.", MathUtil.round((activationTime - System.currentTimeMillis()) / 1000F));
                titleRenderer.drawString(message, 0.01F + halfWidth - titleRenderer.getStringWidthF(message) / 2F, y + titleRenderer.FONT_HEIGHT + 5, -1);
            }
        }

        if(hasDescription) {
            final TFontRenderer descriptionRenderer = rubikR32;
            descriptionRenderer.drawString(this.description, 0.01F + halfWidth - descriptionRenderer.getStringWidthF(this.description)/2F, halfHeight - descriptionRenderer.FONT_HEIGHT/2F, -1);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if(button.enabled)
           button.runClickCallback();
    }

    public GuiConfirmDialog setConfirmText(String confirmText) {
        this.confirmText = confirmText;
        return this;
    }

    public GuiConfirmDialog setDeclineText(String declineText) {
        this.declineText = declineText;
        return this;
    }

    public GuiConfirmDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public GuiConfirmDialog setDescription(String description) {
        this.description = description;
        return this;
    }
}
