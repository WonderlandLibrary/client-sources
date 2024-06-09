package host.kix.uzi.ui.alt.guiscreens;

import java.io.IOException;

import host.kix.uzi.Uzi;
import org.lwjgl.input.Keyboard;

import host.kix.uzi.ui.alt.Alt;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

public class GuiAddAlt extends GuiScreen {
    private GuiTextField username;
    private GuiMaskedTextField password;

    private GuiScreen parent;

    public GuiAddAlt(GuiScreen parent) {
        this.parent = parent;

    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, width / 2 - 100,
                height / 4 + 92 + 12, "Add"));
        this.buttonList.add(new GuiButton(1, width / 2 - 100,
                height / 4 + 116 + 12, "Back"));
        this.username = new GuiTextField(0, this.fontRendererObj, this.width / 2 - 100, 60, 200, 20);
        this.username.setMaxStringLength(Integer.MAX_VALUE);
        this.username.setFocused(true);
        this.password = new GuiMaskedTextField(this.fontRendererObj, this.width / 2 - 100, 100, 200, 20);
        this.password.func_146203_f(Integer.MAX_VALUE);
    }

    public void keyTyped(char character, int keyCode) {
        this.username.textboxKeyTyped(character, keyCode);
        this.password.textboxKeyTyped(character, keyCode);
        if (keyCode == Keyboard.KEY_TAB) {
            this.username.setFocused(!this.username.isFocused());
            this.password.setFocused(!this.password.isFocused());
        }
        if (keyCode == Keyboard.KEY_RETURN) {
            actionPerformed((GuiButton) this.buttonList.get(0));
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        username.mouseClicked(mouseX, mouseY, mouseButton);
        password.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        drawCenteredString(mc.fontRendererObj, "Add Alt", width / 2, 20, 0xFFFFFFFF);
        if (username.getText().isEmpty()) {
            drawString(mc.fontRendererObj, "Username / E-Mail", width / 2 - 96, 66, 0xFF888888);
        }
        if (password.getText().isEmpty()) {
            drawString(mc.fontRendererObj, "Password", width / 2 - 96, 106, 0xFF888888);
        }
        username.drawTextBox();
        password.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 0:
                if (!username.getText().isEmpty()) {
                    Alt alt = new Alt(
                            Uzi.getInstance().getAltManager()
                                    .resolveName(new Alt(username.getText(), password.getText())),
                            username.getText(), password.getText());
                    if (parent instanceof GuiAltManager) {
                        GuiAltManager manager = (GuiAltManager) parent;
                        if (manager.selected != null) {
                            Uzi.getInstance().getAltManager().getAlts().add(manager.index + 1, alt);
                        } else {
                            Uzi.getInstance().getAltManager().getAlts().add(alt);
                        }
                    } else {
                        Uzi.getInstance().getAltManager().getAlts().add(alt);
                    }
                }
                mc.displayGuiScreen(parent);
                break;
            case 1:
                mc.displayGuiScreen(parent);
                break;
        }
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    public void updateScreen() {
        username.updateCursorCounter();
        password.updateCursorCounter();
    }
}
