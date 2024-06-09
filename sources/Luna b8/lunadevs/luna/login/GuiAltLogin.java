package lunadevs.luna.login;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import lunadevs.luna.generator.AltGen;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

public class GuiAltLogin
        extends GuiScreen {
    private GuiPasswordField password;
    private final GuiScreen previousScreen;
    private AltLoginThread thread;
    private GuiTextField username;

    public GuiAltLogin(GuiScreen previousScreen) {
        this.previousScreen = previousScreen;
    }

    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 2:
            AltGen.generate();
            this.thread = new AltLoginThread(AltGen.username, AltGen.password);
            this.thread.start();
            case 1:
                this.mc.displayGuiScreen(this.previousScreen);
                break;
            case 0:
                this.thread = new AltLoginThread(this.username.getText(), this.password.getText());
                this.thread.start();
        }
    }

    public void drawScreen(int x, int y, float z) {
        drawDefaultBackground();
        this.username.drawTextBox();
        this.password.drawTextBox();
        drawCenteredString(this.mc.fontRendererObj, "Alt Login", this.width / 2, 20,
                -1);
        drawCenteredString(this.mc.fontRendererObj, this.thread == null ? "\247aWaiting..." :
                this.thread.getStatus(), this.width / 2, 29, -1);
        if (this.username.getText().isEmpty()) {
            drawString(this.mc.fontRendererObj, "Username / E-Mail", this.width / 2 - 96,
                    66, -7829368);
        }
        if (this.password.getText().isEmpty()) {
            drawString(this.mc.fontRendererObj, "Password", this.width / 2 - 96, 106,
                    -7829368);
        }
        super.drawScreen(x, y, z);
    }

    public void initGui() {
        int var3 = this.height / 4 + 24;
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, var3 + 72 + 12, "Login"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, var3 + 72 + 12 + 24, "Back"));
        this.username = new GuiTextField(1, this.mc.fontRendererObj, this.width / 2 - 100, 60, 200, 20);
        this.password = new GuiPasswordField(this.mc.fontRendererObj, this.width / 2 - 100, 100, 200, 20);
        this.username.setFocused(true);
        Keyboard.enableRepeatEvents(true);
    }

    protected void keyTyped(char character, int key) {
        try {
            super.keyTyped(character, key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (character == '\t') {
            if ((!this.username.isFocused()) && (!this.password.isFocused())) {
                this.username.setFocused(true);
            } else {
                this.username.setFocused(this.password.isFocused());
                this.password.setFocused(!this.username.isFocused());
            }
        }
        if (character == '\r') {
            actionPerformed((GuiButton) this.buttonList.get(0));
        }
        this.username.textboxKeyTyped(character, key);
        this.password.textboxKeyTyped(character, key);
    }

    protected void mouseClicked(int x, int y, int button) {
        try {
            super.mouseClicked(x, y, button);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.username.mouseClicked(x, y, button);
        this.password.mouseClicked(x, y, button);
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    public void updateScreen() {
        this.username.updateCursorCounter();
        this.password.updateCursorCounter();
    }
}
