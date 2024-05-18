/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.gui.click.component;

import me.thekirkayt.client.command.Command;
import me.thekirkayt.client.command.CommandManager;
import me.thekirkayt.client.gui.click.component.Component;
import me.thekirkayt.utils.ClientUtils;
import me.thekirkayt.utils.minecraft.FontRenderer;
import me.thekirkayt.utils.minecraft.GuiTextField;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;

public class Console
extends Component {
    private GuiTextField textField = new GuiTextField(-69, ClientUtils.clientFont(), 0, 0, 0, 0);
    private boolean autoCompleteNext;

    public Console() {
        super(null, 0.0, 0.0, 0.0, 0.0);
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        ScaledResolution scaledRes = new ScaledResolution(ClientUtils.mc(), ClientUtils.mc().displayWidth, ClientUtils.mc().displayHeight);
        this.textField.xPosition = (int)(scaledRes.getScaledWidth_double() / 4.0);
        this.textField.width = (int)(scaledRes.getScaledWidth_double() / 2.0);
        this.textField.yPosition = 2;
        this.textField.height = 18;
        this.textField.drawTextBox();
    }

    @Override
    public void click(int mouseX, int mouseY, int button) {
    }

    @Override
    public void drag(int mouseX, int mouseY, int button) {
    }

    @Override
    public void release(int mouseX, int mouseY, int button) {
    }

    @Override
    public void keyPress(int keyInt, char keyChar) {
    }

    public boolean keyType(int keyInt, char keyChar) {
        if (this.textField.isFocused() && 1 == keyInt) {
            this.textField.setText("");
            this.textField.setFocused(false);
            return true;
        }
        if (!Keyboard.isKeyDown((int)42) && !Keyboard.isKeyDown((int)54) && 53 == keyInt) {
            this.textField.setText("");
            this.textField.setFocused(true);
        } else if (28 == keyInt) {
            String[] args = this.textField.getText().split(" ");
            Command commandFromMessage = CommandManager.getCommandFromMessage(this.textField.getText());
            commandFromMessage.runCommand(args);
            this.textField.setText("");
        } else if (this.textField.isFocused()) {
            this.autoCompleteNext = false;
            this.textField.textboxKeyTyped(keyChar, keyInt);
        }
        return false;
    }
}

