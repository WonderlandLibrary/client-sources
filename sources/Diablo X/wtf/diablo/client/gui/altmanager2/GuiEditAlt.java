package wtf.diablo.client.gui.altmanager2;

import net.minecraft.client.gui.*;
import org.lwjgl.input.Keyboard;
import wtf.diablo.client.gui.altmanager2.impl.AltManagerButton;
import wtf.diablo.client.gui.altmanager2.login.Alt;
import wtf.diablo.client.util.mc.alt.AltUtil;

import java.io.IOException;

public class GuiEditAlt extends GuiScreen {

    private final Alt alt;
    private final int altIndex;
    private final GuiScreen parentScreen;
    private GuiTextField usernameTextbox;
    private GuiTextField passwordTextbox;

    public GuiEditAlt(GuiScreen parentScreen, int altIndex) {
        this.alt = AltUtil.getAltList().get(altIndex);
        this.altIndex = altIndex;
        this.parentScreen = parentScreen;
    }


    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        Keyboard.enableRepeatEvents(false);
    }

    @SuppressWarnings("DuplicatedCode")
    public void initGui() {
        this.buttonList.clear();
        Keyboard.enableRepeatEvents(true);
        final ScaledResolution scaledResolution = new ScaledResolution(mc);
        int midX = scaledResolution.getScaledWidth() / 2;
        int midY = scaledResolution.getScaledHeight() / 2;
        String username = alt.getEmail();
        String password = alt.getPassword();

        if (usernameTextbox != null) {
            username = usernameTextbox.getText();
        }
        if (passwordTextbox != null) {
            password = passwordTextbox.getText();
        }

        usernameTextbox = new GuiTextField(20, mc.fontRendererObj, midX - 100, midY - 60, 200, 20);
        usernameTextbox.setMaxStringLength(127);
        usernameTextbox.setText(username);
        passwordTextbox = new GuiTextField(20, mc.fontRendererObj, midX - 100, midY - 30, 200, 20);
        passwordTextbox.setMaxStringLength(127);
        passwordTextbox.setText(password);
        this.buttonList.add(new AltManagerButton(1, midX - 50, midY, 100, 20, "Done"));
        this.buttonList.add(new AltManagerButton(2, midX - 50, midY + 25, 100, 20, "Back"));
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        Gui.drawRect(0, 0, this.width, this.height, 0xFF151515);
        super.drawScreen(mouseX, mouseY, partialTicks);
        usernameTextbox.drawTextBox();
        passwordTextbox.drawTextBox();
    }

    public void updateScreen() {
        passwordTextbox.updateCursorCounter();
        usernameTextbox.updateCursorCounter();
        super.updateScreen();
    }

    protected void keyTyped(char typedChar, int keyCode) {
        if (passwordTextbox.isFocused()) {
            passwordTextbox.textboxKeyTyped(typedChar, keyCode);
        } else if (usernameTextbox.isFocused()) {
            usernameTextbox.textboxKeyTyped(typedChar, keyCode);
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
                String username = usernameTextbox.getText();
                String password = passwordTextbox.getText();
                if (username.length() > 0) {
                    AltUtil.getAltList().set(altIndex, new Alt(username, password, alt.getAccountType()));
                    mc.displayGuiScreen(parentScreen);
                }
                break;
            case 2:
                mc.displayGuiScreen(parentScreen);
                break;
        }
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        this.usernameTextbox.mouseClicked(mouseX, mouseY, mouseButton);
        this.passwordTextbox.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
}
