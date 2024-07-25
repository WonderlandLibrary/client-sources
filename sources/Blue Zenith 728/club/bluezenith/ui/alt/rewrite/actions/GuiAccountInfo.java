package club.bluezenith.ui.alt.rewrite.actions;

import club.bluezenith.BlueZenith;
import club.bluezenith.core.data.alt.info.AccountInfo;
import club.bluezenith.core.data.alt.info.AccountType;
import club.bluezenith.core.data.preferences.Preferences;
import club.bluezenith.events.impl.DisconnectEvent;
import club.bluezenith.events.listeners.HypixelStatMeter;
import club.bluezenith.ui.alt.rewrite.AccountElement;
import club.bluezenith.util.font.TFontRenderer;
import club.bluezenith.util.player.PacketUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.moved.sussy.C00Handshake;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.util.Session;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Objects;

import static club.bluezenith.BlueZenith.getBlueZenith;
import static club.bluezenith.util.client.FileUtil.setClipboardContents;
import static club.bluezenith.util.font.FontUtil.rubikMedium37;
import static club.bluezenith.util.font.FontUtil.rubikMedium45;
import static club.bluezenith.util.render.RenderUtil.blur;
import static club.bluezenith.util.render.RenderUtil.rect;
import static java.lang.String.format;

public class GuiAccountInfo extends GuiScreen {
    private final AccountElement element;
    private final boolean renderParentScreen;
    private final String title;
    private String originalEmail;
    private String originalPassword;
    private GuiButton saveButton, favoriteButton;

    private GuiTextField emailField,
                         passwordField;

    public GuiAccountInfo(GuiScreen parentScreen, boolean renderParentScreen, AccountElement accountElement) {
        this.parentScreen = parentScreen;
        this.renderParentScreen = renderParentScreen;
        this.element = accountElement;
        final AccountInfo accountInfo = accountElement.getAccount();
        this.title = format("%s (%s)", accountInfo.getEffectiveUsername(), accountInfo.getAccountType().getTypeName());
    }

    @Override
    public void initGui() {
        if(renderParentScreen)
            this.parentScreen.setWorldAndResolution(mc, width, height);

        final int middleWidth = width / 2, middleHeight = height / 2 - 30,
                buttonWidth = 150, buttonHeight = 20, buttonMargin = 5, yOffset = middleHeight + 40;

        emailField = new GuiTextField(0, mc.fontRendererObj,
                middleWidth - buttonWidth - buttonMargin,
                yOffset - buttonHeight - buttonMargin,
                buttonWidth, buttonHeight).setName(element.getAccount().getAccountType() == AccountType.MOJANG ? "Email" : "Username");
        emailField.setMaxStringLength(1048);

        passwordField = new GuiTextField(1, mc.fontRendererObj,
                middleWidth + buttonMargin,
                yOffset - buttonHeight - buttonMargin,
                buttonWidth, buttonHeight).setName("Password");
        passwordField.setMaxStringLength(1048);

        this.buttonList.add(saveButton = new GuiButton(2,
                middleWidth - buttonWidth - buttonMargin,
                yOffset + buttonHeight/2 - buttonMargin - buttonMargin,
                150, 20,"Save changes")
                .onClick(() -> {
                    if(element.getAccount().isOffline()) {
                        getBlueZenith().getNotificationPublisher().postError(
                                "Account Manager",
                                "Cannot edit credentials of offline/microsoft accounts.",
                                2500);
                        return;
                    }
                    element.getAccount().setEmail(emailField.getText());
                    element.getAccount().setPassword(passwordField.getText().isEmpty() ? null : passwordField.getText());
                    getBlueZenith().getNotificationPublisher().postSuccess(
                            "Account Manager",
                            "Account credentials have been updated.",
                            2500
                    );
                }));
        saveButton.enabled = false;

        this.buttonList.add(new GuiButton(3,
                middleWidth + buttonMargin,
                yOffset + buttonHeight/2 - buttonMargin - buttonMargin,
                150, 20,"Copy credentials")
                .onClick(() -> {
                    final String toCopy = emailField.getText() + (element.getAccount().isOffline() ? "" : ":" + passwordField.getText());
                    setClipboardContents(toCopy);
                    getBlueZenith().getNotificationPublisher().postSuccess(
                            "Account Manager",
                            "Copied account credentials to the clipboard",
                            2500
                    );
                }));

        this.buttonList.add(favoriteButton = new GuiButton(4,
                middleWidth - buttonWidth - buttonMargin,
                yOffset + buttonHeight,
                150, 20,element.getAccount().isFavorite ? "Remove from favorites" : "Mark as favorite")
                .onClick(() -> {
                    final boolean isFav = element.getAccount().isFavorite;
                    element.getAccount().isFavorite = !isFav;
                    favoriteButton.displayString = !isFav ? "Remove from favorites" : "Mark as favorite";
                    getBlueZenith().getNotificationPublisher().postSuccess(
                            "Account Manager",
                            isFav ? "Removed account from favorites" : "Account marked as favorite",
                            2500
                    );
                }));

        this.buttonList.add(new GuiButton(3,
                middleWidth + buttonMargin,
                yOffset + buttonHeight,
                150, 20, "Back")
                .onClick(() -> mc.displayGuiScreen(null)));


        if(element.getAccount().getAccountType() != AccountType.OFFLINE) {
            this.buttonList.add(new GuiButton(0,
                    middleWidth - buttonWidth - buttonMargin,
                    yOffset + buttonHeight * 2 + buttonMargin,
                    buttonWidth * 2 + buttonMargin * 2, 20, "Check if the account is banned on Hypixel"
            ); //ban checker used to be here....

            if(element.getAccount().getAccountType() == AccountType.MICROSOFT) {
                this.buttonList.add(new GuiButton(-1,
                        middleWidth - buttonWidth - buttonMargin,
                        yOffset + buttonHeight * 3 + buttonMargin,
                        buttonWidth * 2 + buttonMargin * 2, 20, "Refresh Microsoft access token")
                        .onClick(() -> mc.displayGuiScreen(new GuiMicrosoftLogin(this, false, false, element.getAccount().getMsRefreshToken(), element.getAccount()))));
            }
        }

        emailField.setText(originalEmail = element.getAccount().isOffline() ? element.getAccount().getUsername() : element.getAccount().getEmail());
        passwordField.setText(originalPassword =
                element.getAccount().isOffline() ?
                        element.getAccount().getAccountType().equals(AccountType.MICROSOFT) ? "Microsoft account" : "Offline account"
                        : element.getAccount().getPassword());
        passwordField.setHideContent(!element.getAccount().isOffline());

        if(!Preferences.useBlurInMenus)
            this.buttonList.forEach(GuiButton::useOutline);

        Keyboard.enableRepeatEvents(true);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if(this.renderParentScreen) {
            parentScreen.drawScreen(-1, -1, partialTicks);
            if(Preferences.useBlurInMenus)
                blur(0, 0, width, height);
            else rect(0, 0, width, height, new Color(2, 2, 2, 200).getRGB());
        } else drawDefaultBackground();

        final TFontRenderer titleRenderer = rubikMedium45;

        final float halfWidth = width / 2F;
        final boolean isBanned = element.getAccount().isBanned();

        titleRenderer.drawString(this.title, 0.01F + halfWidth - titleRenderer.getStringWidthF(this.title)/2F, this.height/2F - (isBanned ? 60 : 50), -1);

        if(isBanned)
        rubikMedium37.drawString("Banned", 0.01F + halfWidth - rubikMedium37.getStringWidthF("Banned")/2F, this.height/2F - 40, -1);

        super.drawScreen(mouseX, mouseY, partialTicks);
        emailField.drawTextBox();
        passwordField.drawTextBox();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if(element.getAccount().isOffline()) return;
        emailField.mouseClicked(mouseX, mouseY, mouseButton);
        passwordField.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        if(element.getAccount().isOffline()) return;
        emailField.textboxKeyTyped(typedChar, keyCode);
        passwordField.textboxKeyTyped(typedChar, keyCode);
        saveButton.enabled = !Objects.equals(originalEmail, emailField.getText()) || !Objects.equals(originalPassword, passwordField.getText());
    }

    @Override
    public void updateScreen() {
        emailField.updateCursorCounter();
        passwordField.updateCursorCounter();
    }
}
