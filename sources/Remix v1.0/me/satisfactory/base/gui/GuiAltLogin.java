package me.satisfactory.base.gui;

import me.satisfactory.base.utils.other.*;
import java.awt.*;
import java.awt.datatransfer.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.*;
import org.lwjgl.input.*;

public final class GuiAltLogin extends GuiScreen
{
    private final GuiScreen previousScreen;
    private GuiPasswordField password;
    private AltLogin thread;
    private GuiTextField username;
    private GuiTextField combined;
    
    public GuiAltLogin(final GuiScreen previousScreen) {
        this.previousScreen = previousScreen;
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) {
        switch (button.id) {
            case 1337: {
                try {
                    final Toolkit toolkit = Toolkit.getDefaultToolkit();
                    final Clipboard clipboard = toolkit.getSystemClipboard();
                    final String result = (String)clipboard.getData(DataFlavor.stringFlavor);
                    final String nm = result.split(":")[0];
                    final String pw = result.split(":")[1];
                    (this.thread = new AltLogin(nm.replaceAll(" ", "").replaceAll(System.getProperty("line.separator"), ""), pw.replaceAll(" ", "").replaceAll(System.getProperty("line.separator"), ""))).start();
                }
                catch (Exception e) {
                    this.thread.setStatus("Invalid Clipboard Text!");
                }
                break;
            }
            case 1: {
                GuiAltLogin.mc.displayGuiScreen(this.previousScreen);
                break;
            }
            case 0: {
                if (this.combined.getText().isEmpty()) {
                    this.thread = new AltLogin(this.username.getText(), this.password.getText());
                }
                else if (!this.combined.getText().isEmpty() && this.combined.getText().contains(":")) {
                    final String u = this.combined.getText().split(":")[0];
                    final String p = this.combined.getText().split(":")[1];
                    this.thread = new AltLogin(u.replaceAll(" ", ""), p.replaceAll(" ", ""));
                }
                else {
                    this.thread = new AltLogin(this.username.getText(), this.password.getText());
                }
                this.thread.start();
                break;
            }
        }
    }
    
    @Override
    public void drawScreen(final int x, final int y, final float z) {
        GuiAltLogin.mc.getTextureManager().bindTexture(new ResourceLocation("remix/mainmenu.png"));
        this.drawTexturedModalRect(0, 0, 0, 0, GuiAltLogin.width, GuiAltLogin.height);
        final ScaledResolution sr = new ScaledResolution(GuiAltLogin.mc, GuiAltLogin.mc.displayWidth, GuiAltLogin.mc.displayHeight);
        Gui.drawScaledCustomSizeModalRect(0.0, 0.0, 0.0f, 0.0f, GuiAltLogin.width, GuiAltLogin.height, GuiAltLogin.width, GuiAltLogin.height, (float)GuiAltLogin.width, (float)GuiAltLogin.height);
        Gui.drawRect(GuiAltLogin.width / 2 - 120, 0, GuiAltLogin.width / 2 + 120, GuiAltLogin.height, Integer.MIN_VALUE);
        this.username.drawTextBox();
        this.password.drawTextBox();
        this.combined.drawTextBox();
        Gui.drawCenteredString(GuiAltLogin.mc.fontRendererObj, "Alt Login", GuiAltLogin.width / 2, 20, -1);
        Gui.drawCenteredString(GuiAltLogin.mc.fontRendererObj, (this.thread == null) ? (EnumChatFormatting.GRAY + "Idle...") : this.thread.getStatus(), GuiAltLogin.width / 2, 29, -1);
        if (this.username.getText().isEmpty()) {
            this.drawString(GuiAltLogin.mc.fontRendererObj, "Username / E-Mail", GuiAltLogin.width / 2 - 96, 66, -7829368);
        }
        if (this.password.getText().isEmpty()) {
            this.drawString(GuiAltLogin.mc.fontRendererObj, "Password", GuiAltLogin.width / 2 - 96, 96, -7829368);
        }
        if (this.combined.getText().isEmpty()) {
            this.drawString(GuiAltLogin.mc.fontRendererObj, "Email:Password", GuiAltLogin.width / 2 - 96, 146, -7829368);
        }
        Gui.drawCenteredString(this.fontRendererObj, EnumChatFormatting.RED + "OR", GuiAltLogin.width / 2, 120, -1);
        super.drawScreen(x, y, z);
    }
    
    @Override
    public void initGui() {
        final int var3 = GuiAltLogin.height / 4 + 24;
        this.buttonList.add(new GuiButton(0, GuiAltLogin.width / 2 - 100, var3 + 72 + 12, "Login"));
        this.buttonList.add(new GuiButton(1337, GuiAltLogin.width / 2 - 100, var3 + 72 + 12 + 24, "Clipboard login"));
        this.buttonList.add(new GuiButton(1, GuiAltLogin.width / 2 - 100, var3 + 72 + 12 + 24 + 24, "Back"));
        this.username = new GuiTextField(var3, GuiAltLogin.mc.fontRendererObj, GuiAltLogin.width / 2 - 100, 60, 200, 20);
        this.password = new GuiPasswordField(0, this.fontRendererObj, GuiAltLogin.width / 2 - 100, 90, 200, 20);
        this.combined = new GuiTextField(var3, GuiAltLogin.mc.fontRendererObj, GuiAltLogin.width / 2 - 100, 140, 200, 20);
        this.username.setFocused(true);
        this.username.setMaxStringLength(200);
        this.password.setMaxStringLength(200);
        this.combined.setMaxStringLength(200);
        Keyboard.enableRepeatEvents(true);
    }
    
    @Override
    protected void keyTyped(final char character, final int key) {
        try {
            super.keyTyped(character, key);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if (character == '\t' && (this.username.isFocused() || this.combined.isFocused() || this.password.isFocused())) {
            this.username.setFocused(!this.username.isFocused());
            this.password.setFocused(!this.password.isFocused());
            this.combined.setFocused(!this.combined.isFocused());
        }
        if (character == '\r') {
            this.actionPerformed(this.buttonList.get(0));
        }
        this.username.textboxKeyTyped(character, key);
        this.password.textboxKeyTyped(character, key);
        this.combined.textboxKeyTyped(character, key);
    }
    
    @Override
    protected void mouseClicked(final int x, final int y, final int button) {
        try {
            super.mouseClicked(x, y, button);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        this.username.mouseClicked(x, y, button);
        this.password.mouseClicked(x, y, button);
        this.combined.mouseClicked(x, y, button);
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    public void updateScreen() {
        this.username.updateCursorCounter();
        this.password.updateCursorCounter();
        this.combined.updateCursorCounter();
    }
}
