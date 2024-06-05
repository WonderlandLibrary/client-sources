package ClassSub;

import java.awt.*;
import java.awt.datatransfer.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.*;
import org.lwjgl.input.*;
import java.io.*;

public final class Class179 extends GuiScreen
{
    private Class35 password;
    private final GuiScreen previousScreen;
    private Class82 thread;
    private GuiTextField username;
    
    
    public Class179(final GuiScreen previousScreen) {
        this.previousScreen = previousScreen;
    }
    
    protected void actionPerformed(final GuiButton guiButton) {
        try {
            switch (guiButton.id) {
                case 1: {
                    this.mc.displayGuiScreen(this.previousScreen);
                    break;
                }
                case 0: {
                    (this.thread = new Class82(new Class309(this.username.getText(), this.password.getText()))).start();
                    break;
                }
                case 2: {
                    final String s = (String)Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
                    if (s.contains(":")) {
                        final String[] array = s.split(":");
                        this.username.setText(array[0]);
                        this.password.setText(array[1]);
                        break;
                    }
                    break;
                }
            }
        }
        catch (Throwable t) {
            throw new RuntimeException();
        }
    }
    
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        Class246.drawRect(0.0f, 0.0f, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), 0);
        this.username.drawTextBox();
        this.password.drawTextBox();
        this.drawCenteredString(this.mc.fontRendererObj, "Alt Login", this.width / 2, 20, -1);
        this.drawCenteredString(this.mc.fontRendererObj, (this.thread == null) ? (EnumChatFormatting.GRAY + "Idle...") : this.thread.getStatus(), this.width / 2, 29, -1);
        if (this.username.getText().isEmpty()) {
            this.drawString(this.mc.fontRendererObj, "Username / E-Mail", this.width / 2 - 96, 66, -7829368);
        }
        if (this.password.getText().isEmpty()) {
            this.drawString(this.mc.fontRendererObj, "Password", this.width / 2 - 96, 106, -7829368);
        }
        super.drawScreen(n, n2, n3);
    }
    
    public void initGui() {
        final int n = this.height / 4 + 24;
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, n + 72 + 12, "Login"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, n + 72 + 12 + 24, "Back"));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 100, n + 72 + 12 + 48, "Import user:pass"));
        this.username = new GuiTextField(n, this.mc.fontRendererObj, this.width / 2 - 100, 60, 200, 20);
        this.password = new Class35(this.mc.fontRendererObj, this.width / 2 - 100, 100, 200, 20);
        this.username.setFocused(true);
        Keyboard.enableRepeatEvents(true);
    }
    
    protected void keyTyped(final char c, final int n) {
        try {
            super.keyTyped(c, n);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        if (c == '\t') {
            if (!this.username.isFocused() && !this.password.isFocused()) {
                this.username.setFocused(true);
            }
            else {
                this.username.setFocused(this.password.isFocused());
                this.password.setFocused(!this.username.isFocused());
            }
        }
        if (c == '\r') {
            this.actionPerformed(this.buttonList.get(0));
        }
        this.username.textboxKeyTyped(c, n);
        this.password.textboxKeyTyped(c, n);
    }
    
    protected void mouseClicked(final int n, final int n2, final int n3) {
        try {
            super.mouseClicked(n, n2, n3);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        this.username.mouseClicked(n, n2, n3);
        this.password.mouseClicked(n, n2, n3);
    }
    
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }
    
    public void updateScreen() {
        this.username.updateCursorCounter();
        this.password.updateCursorCounter();
    }
}
