/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package digital.rbq.alt.gui;

import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;
import digital.rbq.alt.Alt;
import digital.rbq.alt.AltLoginService;
import digital.rbq.alt.gui.GuiDirectLogin;

public final class GuiAltManager
extends GuiScreen {
    private final GuiScreen previousScreen;
    private AltLoginService thread;
    private Alt selected;

    public GuiAltManager(GuiScreen previousScreen) {
        this.previousScreen = previousScreen;
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 1: {
                this.mc.displayGuiScreen(this.previousScreen);
                break;
            }
            case 0: {
                this.mc.displayGuiScreen(new GuiDirectLogin(this.previousScreen));
                break;
            }
            case 2: {
                if (this.selected == null) break;
            }
        }
    }

    @Override
    public void drawScreen(int x, int y, float z) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.mc.fontRendererObj, "Alt Manager", this.width / 2, 20, -1);
        this.drawCenteredString(this.mc.fontRendererObj, this.thread == null ? (Object)((Object)EnumChatFormatting.GRAY) + "Waiting..." : this.thread.getStatus(), this.width / 2, 29, -1);
        super.drawScreen(x, y, z);
    }

    @Override
    public void initGui() {
        int var3 = this.height - 24;
        this.buttonList.add(new GuiButton(0, this.width / 2 + 102, var3, 100, 20, "Direct Login"));
        this.buttonList.add(new GuiButton(2, this.width / 2, var3, 100, 20, "Login"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 102, var3, 100, 20, "Back"));
        Keyboard.enableRepeatEvents((boolean)true);
    }

    @Override
    protected void keyTyped(char character, int key) {
        try {
            super.keyTyped(character, key);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void mouseClicked(int x, int y, int button) {
        try {
            super.mouseClicked(x, y, button);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents((boolean)false);
    }

    @Override
    public void updateScreen() {
    }
}

