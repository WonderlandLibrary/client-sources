package club.bluezenith.ui.alt.rewrite.actions;

import club.bluezenith.core.data.alt.info.AccountInfo;
import club.bluezenith.core.data.preferences.Preferences;
import club.bluezenith.util.client.OAuthServer;
import club.bluezenith.ui.alt.rewrite.actions.microsoft.MicrosoftAuthenticator;
import club.bluezenith.util.font.TFontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import java.awt.*;

import static club.bluezenith.BlueZenith.getBlueZenith;
import static club.bluezenith.util.font.FontUtil.inter35;
import static club.bluezenith.util.font.FontUtil.rubikMedium45;
import static club.bluezenith.util.render.RenderUtil.blur;
import static club.bluezenith.util.render.RenderUtil.rect;

public class GuiMicrosoftLogin extends GuiScreen {

    private final boolean renderParentScreen, isDirect;
    private OAuthServer server;
    private String refreshToken;
    private String message = "Login to the web page we've opened in your browser.";
    private AccountInfo refreshingAccount;

    public GuiMicrosoftLogin(GuiScreen parent, boolean renderParentScreen, boolean isDirect, String refreshToken, AccountInfo refreshingAccount) {
        this.parentScreen = parent;
        this.renderParentScreen = renderParentScreen;
        this.refreshToken = refreshToken;

        if(refreshToken != null)
            message = "Refreshing the access token, hold on..";
        this.refreshingAccount = refreshingAccount;
        this.isDirect = isDirect;
    }

    public GuiMicrosoftLogin(GuiScreen parent, boolean renderParentScreen, boolean isDirect) {
        this(parent, renderParentScreen, isDirect, null, null);
    }

    @Override
    public void initGui() {
        if(renderParentScreen) {
            parentScreen.setWorldAndResolution(mc, width, height);
        }

        this.buttonList.add(new GuiButton(0,
                width / 2 - 150,
                height / 2 + 20,
                300, 20,
                "Back").onClick(() -> mc.displayGuiScreen(getBlueZenith().getNewAltManagerGUI())));

        if(server != null) return; //todo replace dark rect backgrounds with kinda light backgrounds for better visibility
        try {
            new MicrosoftAuthenticator(server = new OAuthServer(), refreshToken, refreshingAccount, isDirect).start();
        } catch (Exception exception) {
            mc.displayGuiScreen(null);
            getBlueZenith().getNotificationPublisher().postError("Microsoft authenticator", "Couldn't start the local server.\nSee logs for more info.", 2500);
            exception.printStackTrace();
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if(this.renderParentScreen) {
            this.parentScreen.drawScreen(-1, -1, partialTicks);
            if(Preferences.useBlurInMenus)
                blur(0, 0, width, height);
            else rect(0, 0, width, height, new Color(2, 2, 2, 200).getRGB());
        } else drawDefaultBackground();

        TFontRenderer titleRenderer = rubikMedium45;

        final float halfWidth = width / 2F;
        final String title = "Microsoft login";

        titleRenderer.drawString(title, 0.01F + halfWidth - titleRenderer.getStringWidthF(title)/2F, height / 2F - 25, -1);

        titleRenderer = inter35;
        titleRenderer.drawString(this.message, 0.01F + halfWidth - titleRenderer.getStringWidthF(this.message)/2F, height / 2F, -1);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public void completed(String message) {
        this.message = message;
    }

    @Override
    public void onGuiClosed() {
        server.stop();
    }
}
