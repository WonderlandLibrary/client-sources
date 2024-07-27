package dev.nexus.utils.ui.altmanager;

import dev.nexus.utils.client.ThemeUtil;
import dev.nexus.utils.render.DrawUtils;
import dev.nexus.utils.ui.mainmenu.CustomGuiButton;
import dev.nexus.utils.ui.mainmenu.MainMenu;
import me.liuli.elixir.account.MicrosoftAccount;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.util.Session;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;

public class AltManager extends GuiScreen {
    private static final int BUTTON_WIDTH = 200;
    private static final int BUTTON_HEIGHT = 20;
    private static final int BUTTON_SPACING = 5;
    private GuiTextField loginField;
    private String status = "Not logged in.";

    @Override
    public void initGui() {
        super.initGui();
        int buttonWidth = BUTTON_WIDTH;
        int buttonHeight = BUTTON_HEIGHT;
        int buttonSpacing = BUTTON_SPACING;
        int startX = (this.width - buttonWidth) / 2;
        int startY = (this.height - (buttonHeight * 5 + buttonSpacing * 4)) / 2;

        loginField = new GuiTextField(69, fontRendererObj, startX, startY - buttonHeight - buttonSpacing, buttonWidth, buttonHeight);
        loginField.setFocused(true);

        this.buttonList.clear();

        this.buttonList.add(new CustomGuiButton(0, startX, startY, buttonWidth, buttonHeight, "Login cracked", ThemeUtil.getMainColor()));
        this.buttonList.add(new CustomGuiButton(1, startX, startY + buttonHeight + buttonSpacing, buttonWidth, buttonHeight, "Copy URL", ThemeUtil.getMainColor()));
        this.buttonList.add(new CustomGuiButton(2, startX, startY + 2 * (buttonHeight + buttonSpacing), buttonWidth, buttonHeight, "Back", ThemeUtil.getMainColor()));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0:
                if (!loginField.getText().isEmpty()) {
                    mc.session = new Session(loginField.getText(), loginField.getText(), "0", "legacy");
                    status = "Logged in as " + mc.session.getUsername() + ".";
                }
                break;
            case 1:
                MicrosoftAccount.Companion.buildFromOpenBrowser(new MicrosoftAccount.OAuthHandler() {
                    @Override
                    public void authError(String error) {
                        status = error;
                    }

                    @Override
                    public void authResult(MicrosoftAccount account) {
                        mc.session = new Session(account.getSession().getUsername(), account.getSession().getUuid(), account.getSession().getToken(), "legacy");
                        status = "Logged in as " + mc.session.getUsername() + ".";
                    }

                    @Override
                    public void openUrl(String url) {
                        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(url), null);
                        status = "Link copied to clipboard.";
                    }
                }, MicrosoftAccount.AuthMethod.Companion.getAZURE_APP());
                break;
            case 2:
                Minecraft.getMinecraft().displayGuiScreen(new MainMenu());
                break;
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (loginField.isFocused()) {
            loginField.textboxKeyTyped(typedChar, keyCode);
        }
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        DrawUtils.drawRect(0, 0, this.width, this.height, new Color(27, 27, 27).getRGB());
        loginField.drawTextBox();

        int statusX = this.width / 2 - fontRendererObj.getStringWidth(status) / 2;
        int statusY = loginField.yPosition - 20;
        fontRendererObj.drawString(status, statusX, statusY, Color.WHITE.getRGB());
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
