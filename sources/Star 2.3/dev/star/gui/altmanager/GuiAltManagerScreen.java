package dev.star.gui.altmanager;

import com.google.common.base.Predicate;
import dev.star.Client;
import net.minecraft.client.gui.*;
import net.minecraft.client.resources.I18n;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class GuiAltManagerScreen extends GuiScreen {
    private final AltManagerUtils utils = new AltManagerUtils();
    private GuiTextField Username;
    private final Predicate<String> field_181032_r = p_apply_1_ -> {
        if (p_apply_1_.isEmpty()) {
            return true;
        } else {
            String[] astring = p_apply_1_.split(":");
            if (astring.length == 0) {
                return true;
            } else {
                try {
                    return true;
                } catch (IllegalArgumentException var4) {
                    return false;
                }
            }
        }
    };

    public GuiAltManagerScreen() {
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen() {
        this.Username.updateCursorCounter();
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(21, this.width / 2 - 100, this.height / 2 + 50, I18n.format("Custom Name Login (Cracked)")));
        this.buttonList.add(new GuiButton(22, this.width / 2 - 100, this.height / 2 + 74, I18n.format("Random Name Login (Cracked)")));
        this.buttonList.add(new GuiButton(23, this.width / 2 - 100, this.height / 2 + 96, I18n.format("Microsoft Login")));
        this.buttonList.add(new GuiButton(24, this.width / 2 - 100, this.height / 2 + 120, I18n.format("gui.cancel")));
        this.Username = new GuiTextField(1, this.fontRendererObj, this.width / 2 - 100, 206, 200, 20);
        this.Username.setMaxStringLength(128);
      //  this.Password.setText(this.serverData.serverIP);
        this.Username.setValidator(this.field_181032_r);
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        this.Username.textboxKeyTyped(typedChar, keyCode);

        if (keyCode == 15) {
            this.Username.setFocused(!this.Username.isFocused());
        }
    }

    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 21) {
            utils.loginWithString(Username.getText(), "", false);
        }

        if (button.id == 23) {
            utils.microsoftLoginAsync("email", "password");
        }

        if (button.id == 24) {
            this.mc.displayGuiScreen(new GuiMainMenu());
        }
    }

    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.Username.mouseClicked(mouseX, mouseY, mouseButton);
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        String username;
        if ( Client.INSTANCE.currentSessionAlt != null) {
            username = Client.INSTANCE.currentSessionAlt.username;
        } else {
            username = "Player";
        }
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, I18n.format("Logged in as " +  username), this.width / 2, 175, 16777215);
        this.drawString(this.fontRendererObj, I18n.format("Username"), this.width / 2 - 100, 194, 10526880);
        this.Username.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
