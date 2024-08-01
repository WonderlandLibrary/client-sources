package wtf.diablo.client.gui.altmanager2;

import net.minecraft.client.gui.*;
import org.lwjgl.input.Keyboard;
import wtf.diablo.client.gui.altmanager2.impl.AltManagerButton;
import wtf.diablo.client.gui.altmanager2.login.AccountType;
import wtf.diablo.client.gui.altmanager2.login.Alt;
import wtf.diablo.client.util.mc.alt.AltUtil;

import java.io.IOException;
import java.util.Arrays;

public class GuiAddAlt extends GuiScreen {

    private final GuiScreen parentScreen;
    private GuiTextField usernameTextbox;
    private GuiTextField passwordTextbox;
    private boolean isFullAlt = false;
    private AccountType currentAccountType = AccountType.MICROSOFT;

    public GuiAddAlt(GuiScreen parentScreen) {
        this.parentScreen = parentScreen;
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        Keyboard.enableRepeatEvents(false);
    }

    public void initGui() {
        //noinspection DuplicatedCode
        this.buttonList.clear();
        Keyboard.enableRepeatEvents(true);
        final ScaledResolution scaledResolution = new ScaledResolution(mc);

        int midX = scaledResolution.getScaledWidth() / 2;
        int midY = scaledResolution.getScaledHeight() / 2;
        String username = "";
        String password = "";
        if (usernameTextbox != null) {
            username = usernameTextbox.getText();
        }
        if (passwordTextbox != null && !isFullAlt) {
            password = passwordTextbox.getText();
        }
        usernameTextbox = new GuiTextField(20, mc.fontRendererObj, midX - 100, midY - 60, 200, 20);
        usernameTextbox.setMaxStringLength(127);
        usernameTextbox.setText(username);
        passwordTextbox = new GuiTextField(20, mc.fontRendererObj, midX - 100, midY - 30, 200, 20);
        passwordTextbox.setMaxStringLength(127);
        passwordTextbox.setText(password);
        this.buttonList.add(new AltManagerButton(1, midX - 65, midY, 130, 20, "Add Alt"));
        this.buttonList.add(new AltManagerButton(5, midX - 65, midY + 25, 130, 20, "Account Type: " + this.currentAccountType));
        this.buttonList.add(new AltManagerButton(2, midX - 65, midY + 50, 130, 20, "Back"));
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        Gui.drawRect(0, 0, this.width, this.height, 0xFF151515);
        super.drawScreen(mouseX, mouseY, partialTicks);
        usernameTextbox.drawTextBox();
        if (!isFullAlt) {
            passwordTextbox.drawHiddenTextBox();
        }
    }

    public void updateScreen() {
        if (!isFullAlt) {
            passwordTextbox.updateCursorCounter();
        }
        usernameTextbox.updateCursorCounter();
        super.updateScreen();
    }

    protected void keyTyped(char typedChar, int keyCode) {

        //noinspection DuplicatedCode
        if (keyCode == Keyboard.KEY_ESCAPE) {
            mc.displayGuiScreen(parentScreen);
        }

        if (passwordTextbox.isFocused()) {
            passwordTextbox.textboxKeyTyped(typedChar, keyCode);
        } else if (usernameTextbox.isFocused()) {
            usernameTextbox.textboxKeyTyped(typedChar, keyCode);
            if (usernameTextbox.getText().contains(":")) {
                if (!isFullAlt) {
                    isFullAlt = true;
                    initGui();
                    usernameTextbox.setFocused(true);
                }
            } else {
                if (isFullAlt) {
                    isFullAlt = false;
                    initGui();
                    usernameTextbox.setFocused(true);
                }
            }
        } else {
            if (!passwordTextbox.textboxKeyTyped(typedChar, keyCode) || !usernameTextbox.textboxKeyTyped(typedChar, keyCode)) {
                if (keyCode == 1) {
                    mc.displayGuiScreen(parentScreen);
                }
            }
        }
    }

    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 1:
                String username;
                String password;
                //noinspection DuplicatedCode
                if (isFullAlt) {
                    String text = usernameTextbox.getText();
                    text = text.replaceAll("[\\s+]", "");
                    String[] details = text.split(":");
                    username = details[0];
                    password = details.length == 1 ? "" : details[1];
                } else {
                    username = usernameTextbox.getText();
                    password = passwordTextbox.getText();
                }
                if (username.length() > 0) {
                    Alt thisAlt = new Alt(username, password, currentAccountType);
                    AltUtil.getAltList().add(thisAlt);
                    mc.displayGuiScreen(parentScreen);
                }
                break;
            case 2:
                mc.displayGuiScreen(parentScreen);
                break;
            case 5:
                int index = Arrays.asList(AccountType.values()).indexOf(currentAccountType) + 1;
                if (index >= AccountType.values().length) index = 0;
                currentAccountType = AccountType.values()[index];
                button.displayString = "Account Type: " + currentAccountType.name();
                break;
        }
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        this.usernameTextbox.mouseClicked(mouseX, mouseY, mouseButton);
        this.passwordTextbox.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
}
