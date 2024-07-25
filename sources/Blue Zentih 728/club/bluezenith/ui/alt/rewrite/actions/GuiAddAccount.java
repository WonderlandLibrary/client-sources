package club.bluezenith.ui.alt.rewrite.actions;

import club.bluezenith.core.data.alt.info.AccountInfo;
import club.bluezenith.core.data.preferences.Preferences;
import club.bluezenith.util.font.TFontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.IOException;
import java.util.function.Consumer;

import static club.bluezenith.BlueZenith.getBlueZenith;
import static club.bluezenith.util.client.ClientUtils.emailPattern;
import static club.bluezenith.util.font.FontUtil.rubikMedium45;
import static club.bluezenith.util.render.RenderUtil.blur;
import static club.bluezenith.util.render.RenderUtil.rect;
import static com.google.common.base.Strings.isNullOrEmpty;

public class GuiAddAccount extends GuiScreen {
    Consumer<AccountInfo> callback;

    private final boolean renderParentScreen, isDirect;
    private String title;

    private GuiTextField emailField, passwordField;
    private int titlePosition;

    public GuiAddAccount(GuiScreen parentScreen, boolean renderParentScreen, boolean isDirect, Consumer<AccountInfo> callback) {
        this.callback = callback;
        this.parentScreen = parentScreen;
        this.renderParentScreen = renderParentScreen;
        this.isDirect = isDirect;
    }

    @Override
    public void initGui() {
        if(this.renderParentScreen)
            this.parentScreen.setWorldAndResolution(mc, width, height);

        final int middleWidth = width / 2, middleHeight = height / 2 - 30,
                buttonWidth = 150, buttonHeight = 20, buttonMargin = 5, yOffset = middleHeight + 50;

        this.buttonList.add(new GuiButton(0,
                middleWidth - buttonMargin - buttonWidth,
                yOffset + buttonHeight + buttonMargin,
                buttonWidth * 2 + buttonMargin * 2, buttonHeight,
                "Microsoft account").onClick(() -> {
                    mc.displayGuiScreen(new GuiMicrosoftLogin(this, false, isDirect));
                }));

        this.buttonList.add(new GuiButton(0, middleWidth + buttonMargin, yOffset, buttonWidth, buttonHeight, "Discard")
                .onClick(() -> {
                    mc.displayGuiScreen(null); //this will close the screen, but I added a change to minecraft logic so that it'll show the parent screen instead.
                }));

        this.buttonList.add(new GuiButton(1, middleWidth - buttonMargin - buttonWidth, yOffset, buttonWidth, buttonHeight, "Proceed")
                .onClick(() -> {
                    final String email = emailField.getText();
                    final String password = passwordField.getText();

                    final boolean isOfflineAccount = isNullOrEmpty(password);

                    if(!isOfflineAccount && !emailPattern.matcher(email).find() || isOfflineAccount && isNullOrEmpty(email)) {
                        getBlueZenith().getNotificationPublisher().postError("Account Manager", "Invalid email/username provided!", 2500);
                        return;
                    }

                    if(!isOfflineAccount) //check if password was provided
                        this.callback.accept(new AccountInfo(email, password));
                    else if(email.length() <= 16 && email.length() >= 3 && email.matches("[A-z0-9]{3,16}"))
                        this.callback.accept(new AccountInfo(email)); //offline account, use only username
                    else {
                        getBlueZenith().getNotificationPublisher().postError("Account Manager", "Invalid username provided", 2500);
                        return;
                    }

                    getBlueZenith().getNotificationPublisher().postSuccess("Account Manager", "Account added!" + (isOfflineAccount ? " (Cracked)" : ""), 2500);
                    mc.displayGuiScreen(null);
                }));

        emailField = new GuiTextField(2, mc.fontRendererObj,
                middleWidth - buttonWidth - buttonMargin,
                titlePosition = yOffset - buttonHeight - buttonMargin,
                buttonWidth, buttonHeight).setName("Email/Username");
        emailField.setMaxStringLength(1048);
        passwordField = new GuiTextField(3, mc.fontRendererObj,
                middleWidth + buttonMargin,
                yOffset - buttonHeight - buttonMargin,
                buttonWidth, buttonHeight).setName("Password");
        passwordField.setMaxStringLength(1048);

        if(!Preferences.useBlurInMenus)
            this.buttonList.forEach(GuiButton::useOutline);

        titlePosition -= 40; //move title just a bit upwards
        Keyboard.enableRepeatEvents(true);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if(this.title == null) {
            getBlueZenith().getNotificationPublisher().postError("GUI Screen", "This screen doesn't have a title! Closing...", 2500);
            mc.displayGuiScreen(null);
            return;
        }

        if(this.renderParentScreen) {
            parentScreen.drawScreen(-1, -1, partialTicks);
            if(Preferences.useBlurInMenus)
                blur(0, 0, width, height);
            else rect(0, 0, width, height, new Color(2, 2, 2, 200).getRGB());
        } else drawDefaultBackground();

        final TFontRenderer titleRenderer = rubikMedium45;

        final float halfWidth = width / 2F;
        titleRenderer.drawString(this.title, 0.01F + halfWidth - titleRenderer.getStringWidthF(this.title)/2F, titlePosition, -1);

        super.drawScreen(mouseX, mouseY, partialTicks);
        emailField.drawTextBox();
        passwordField.drawTextBox();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        button.runClickCallback();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        emailField.mouseClicked(mouseX, mouseY, mouseButton);
        passwordField.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if(keyCode == Keyboard.KEY_RETURN) this.buttonList.get(1).runClickCallback(); //run "proceed" button click callback
        super.keyTyped(typedChar, keyCode);
        emailField.textboxKeyTyped(typedChar, keyCode);
        passwordField.textboxKeyTyped(typedChar, keyCode);
    }

    @Override
    public void updateScreen() {
        emailField.updateCursorCounter();
        passwordField.updateCursorCounter();
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    public GuiAddAccount setTitle(String title) {
        this.title = title;
        return this;
    }
}
