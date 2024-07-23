package io.github.liticane.monoxide.ui.screens.mainmenu.monoxide.altmanager;

import io.github.liticane.monoxide.ui.screens.mainmenu.monoxide.AtaniMainMenu;
import io.github.liticane.monoxide.ui.screens.mainmenu.monoxide.altmanager.auth.SessionManager;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.Session;
import org.lwjglx.input.Keyboard;

import java.io.IOException;

public class GuiCrackedLogin extends GuiScreen {
    private GuiTextField usernameField = null;

    public void initGui() {
        buttonList.add(new GuiButton(
                2, width / 2 - 102, height / 2 + 31, 100, 20, "Login"
        ));
        buttonList.add(new GuiButton(
                3, width / 2 + 2, height / 2 + 31, 100, 20, "Cancel"
        ));


        usernameField = new GuiTextField(
                0, fontRendererObj, width / 2 - 75, height / 2 - 10, 150, 20
        );
        usernameField.setFocused(true);
        usernameField.setMaxStringLength(16);
    }

    @Override
    public void updateScreen() {
        usernameField.updateCursorCounter();
        drawCenteredString(
                fontRendererObj, "Cracked Login", width / 2, height / 2 - 40, -1
        );
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float renderPartialTicks) {
        usernameField.drawTextBox();
        super.drawScreen(mouseX, mouseY, renderPartialTicks);

        drawCenteredString(
                fontRendererObj, "Cracked Login", width / 2, height / 2 - 40, -1
        );

    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        usernameField.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        switch (keyCode) {
            case Keyboard.KEY_ESCAPE: {
                this.mc.displayGuiScreen(new GuiAccountManager(this));
            }
            break;
            case Keyboard.KEY_RETURN: {
                if (usernameField != null) {
                    if (!usernameField.getText().contains(" ") && usernameField.getText() != "") {
                        final Session session = new Session(usernameField.getText(), "", "", Session.Type.LEGACY.toString());
                        SessionManager.setSession(session);
                        this.mc.displayGuiScreen(new GuiAccountManager(getPreviousScreen()));
                    }
                }
            }
            break;
            default: {
                usernameField.textboxKeyTyped(typedChar, keyCode);
            }
        }
    }


    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 2) {
            if (usernameField != null) {
                if (!usernameField.getText().contains(" ") && usernameField.getText() != "") {
                    final Session session = new Session(usernameField.getText(), "", "", Session.Type.LEGACY.toString());
                    SessionManager.setSession(session);
                    this.mc.displayGuiScreen(new GuiAccountManager(getPreviousScreen()));
                }
            }
        } else if (button.id == 3) {
            this.mc.displayGuiScreen(new GuiAccountManager(getPreviousScreen()));
        }
    }
    private final AtaniMainMenu screen = new AtaniMainMenu();
    private GuiScreen getPreviousScreen() {
        return screen;
    }
}