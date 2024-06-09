// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.gui.login;

import java.io.IOException;
import org.lwjgl.input.Keyboard;
import net.minecraft.util.EnumChatFormatting;
import xyz.niggfaclient.font.Fonts;
import xyz.niggfaclient.utils.render.RenderUtils;
import xyz.niggfaclient.utils.other.MathUtils;
import java.util.Random;
import java.awt.datatransfer.DataFlavor;
import java.awt.Toolkit;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.GuiScreen;

public class GuiAltLogin extends GuiScreen
{
    private final GuiScreen previousScreen;
    private PasswordField password;
    private AltLoginThread thread;
    private GuiTextField username;
    
    public GuiAltLogin(final GuiScreen previousScreen) {
        this.previousScreen = previousScreen;
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) {
        switch (button.id) {
            case 1: {
                this.mc.displayGuiScreen(this.previousScreen);
                break;
            }
            case 0: {
                try {
                    (this.thread = new AltLoginThread(this.username.getText(), this.password.getText())).start();
                }
                catch (Throwable var4) {
                    var4.printStackTrace();
                }
                break;
            }
            case 2: {
                if (this.username == null) {
                    if (this.password == null) {
                        break;
                    }
                }
                String data;
                try {
                    data = (String)Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
                }
                catch (Exception ignored) {
                    break;
                }
                if (data.contains(":")) {
                    final String[] credentials = data.split(":");
                    this.username.setText(credentials[0]);
                    this.password.setText(credentials[1]);
                }
                break;
            }
            case 3: {
                final String chars = "abcdefghijklmnopqrstuvwxyz1234567890";
                final StringBuilder salt = new StringBuilder();
                final Random rnd = new Random();
                while (salt.length() < MathUtils.getRandomInRange(8, 16)) {
                    final int index = (int)(rnd.nextFloat() * chars.length());
                    salt.append(chars.charAt(index));
                }
                (this.thread = new AltLoginThread(salt.toString(), "")).start();
                break;
            }
        }
    }
    
    @Override
    public void drawScreen(final int x2, final int y2, final float z2) {
        RenderUtils.drawGradient((float)this.width, (float)this.height);
        super.drawScreen(x2, y2, z2);
        this.username.drawTextBox();
        this.password.drawTextBox();
        Fonts.sf18.drawCenteredString("Alt Login", (float)(this.width / 2), 20.0f, -1);
        Fonts.sf18.drawCenteredString((this.thread == null) ? (EnumChatFormatting.YELLOW + "Idle...") : this.thread.getStatus(), (float)(this.width / 2), 29.0f, -1);
        if (this.username.getText().isEmpty()) {
            Fonts.sf18.drawString("Username / E-Mail", (float)(this.width / 2 - 96), 67.0f, -7829368);
        }
        if (this.password.getText().isEmpty()) {
            Fonts.sf18.drawString("Password", (float)(this.width / 2 - 96), 107.0f, -7829368);
        }
    }
    
    @Override
    public void initGui() {
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 92 + 12, "Login"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 116 + 12, "Back"));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 4 + 116 + 36, "Import user:pass"));
        this.buttonList.add(new GuiButton(3, this.width / 2 - 100, this.height / 4 + 116 + 60, "Generate Random Account"));
        this.username = new GuiTextField(this.height / 4 + 24, this.mc.fontRendererObj, this.width / 2 - 100, 60, 200, 20);
        this.password = new PasswordField(this.mc.fontRendererObj, this.width / 2 - 100, 100, 200, 20);
        this.username.setFocused(true);
        Keyboard.enableRepeatEvents(true);
    }
    
    @Override
    protected void keyTyped(final char character, final int key) {
        try {
            super.keyTyped(character, key);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        if (character == '\t') {
            if (!this.username.isFocused() && !this.password.isFocused()) {
                this.username.setFocused(true);
            }
            else {
                this.username.setFocused(this.password.isFocused());
                this.password.setFocused(!this.username.isFocused());
            }
        }
        if (character == '\r') {
            this.actionPerformed(this.buttonList.get(0));
        }
        this.username.textboxKeyTyped(character, key);
        this.password.textboxKeyTyped(character, key);
    }
    
    @Override
    protected void mouseClicked(final int x2, final int y2, final int button) {
        try {
            super.mouseClicked(x2, y2, button);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        this.username.mouseClicked(x2, y2, button);
        this.password.mouseClicked(x2, y2, button);
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    public void updateScreen() {
        this.username.updateCursorCounter();
        this.password.updateCursorCounter();
    }
}
