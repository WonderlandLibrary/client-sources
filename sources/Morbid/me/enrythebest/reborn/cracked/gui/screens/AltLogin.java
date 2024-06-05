package me.enrythebest.reborn.cracked.gui.screens;

import org.lwjgl.input.*;
import me.enrythebest.reborn.cracked.util.file.*;
import net.minecraft.src.*;
import java.net.*;
import java.io.*;

public class AltLogin extends GuiScreen
{
    private GuiScreen parentScreen;
    private GuiTextField usernameTextField;
    private PasswordField passwordTextField;
    private String error;
    
    public AltLogin(final GuiScreen var1) {
        this.parentScreen = var1;
    }
    
    @Override
    public void updateScreen() {
        this.usernameTextField.updateCursorCounter();
        this.passwordTextField.updateCursorCounter();
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    protected void actionPerformed(final GuiButton var1) {
        if (var1.enabled) {
            if (var1.id == 1) {
                this.mc.displayGuiScreen(this.parentScreen);
            }
            else if (var1.id == 2) {
                if (PreviousAccount.password == null || PreviousAccount.username == null) {
                    return;
                }
                final String var2 = PreviousAccount.username;
                final String var3 = PreviousAccount.password;
                PreviousAccount.saveAccount(var2, var3);
                try {
                    final String var4 = this.Login(var2, var3).trim();
                    if (var4 == null || !var4.contains(":")) {
                        this.error = var4;
                        return;
                    }
                    final String[] var5 = var4.split(":");
                    if (var5.length > 1) {
                        this.mc.session = new Session(var5[2], var5[3]);
                    }
                    this.mc.displayGuiScreen(this.parentScreen);
                }
                catch (Exception var6) {
                    var6.printStackTrace();
                }
            }
            else if (var1.id == 0) {
                if (this.passwordTextField.getText().length() > 0) {
                    final String var2 = this.usernameTextField.getText();
                    final String var3 = this.passwordTextField.getText();
                    PreviousAccount.saveAccount(var2, var3);
                    try {
                        final String var4 = this.Login(var2, var3).trim();
                        if (var4 == null || !var4.contains(":")) {
                            this.error = var4;
                            return;
                        }
                        final String[] var5 = var4.split(":");
                        if (var5.length > 1) {
                            this.mc.session = new Session(var5[2], var5[3]);
                        }
                        this.mc.displayGuiScreen(this.parentScreen);
                    }
                    catch (Exception var7) {
                        var7.printStackTrace();
                    }
                }
                else {
                    this.mc.session = new Session(this.usernameTextField.getText(), "");
                }
                this.mc.displayGuiScreen(this.parentScreen);
            }
        }
    }
    
    @Override
    protected void keyTyped(final char var1, final int var2) {
        this.usernameTextField.textboxKeyTyped(var1, var2);
        this.passwordTextField.textboxKeyTyped(var1, var2);
        if (var1 == '\t') {
            if (this.usernameTextField.isFocused()) {
                this.usernameTextField.setFocused(false);
                this.passwordTextField.setFocused(true);
            }
            else {
                this.usernameTextField.setFocused(true);
                this.passwordTextField.setFocused(false);
            }
        }
        if (var1 == '\r') {
            this.actionPerformed(this.buttonList.get(0));
        }
    }
    
    @Override
    protected void mouseClicked(final int var1, final int var2, final int var3) {
        super.mouseClicked(var1, var2, var3);
        this.usernameTextField.mouseClicked(var1, var2, var3);
        this.passwordTextField.mouseClicked(var1, var2, var3);
    }
    
    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96, "Login"));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 4 + 120, "Previous alt"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 144, "Cancel"));
        this.usernameTextField = new GuiTextField(this.fontRenderer, this.width / 2 - 100, 76, 200, 20);
        this.passwordTextField = new PasswordField(this.fontRenderer, this.width / 2 - 100, 116, 200, 20);
        this.usernameTextField.setMaxStringLength(64);
    }
    
    @Override
    public void drawScreen(final int var1, final int var2, final float var3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, "Change Account", this.width / 2, this.height / 4 - 60 + 20, 16777215);
        this.drawString(this.fontRenderer, "Username", this.width / 2 - 100, 63, 10526880);
        this.drawString(this.fontRenderer, "Password", this.width / 2 - 100, 104, 10526880);
        this.usernameTextField.drawTextBox();
        this.passwordTextField.drawTextBox();
        if (this.error != null) {
            this.drawCenteredString(this.fontRenderer, "§c Login Failed:" + this.error, this.width / 2, this.height / 4 + 72 + 12, 16777215);
        }
        super.drawScreen(var1, var2, var3);
    }
    
    public String Login(final String var1, final String var2) {
        String var3 = "";
        final String var4 = "http://login.minecraft.net/?user=" + var1 + "&password=" + var2 + "&version=13";
        try {
            final BufferedReader var5 = this.read(var4);
            String var6 = "";
            while ((var6 = var5.readLine()) != null) {
                var3 = var6;
            }
            var5.close();
        }
        catch (Exception var7) {
            var7.printStackTrace();
        }
        return var3;
    }
    
    private BufferedReader read(final String var1) throws Exception, FileNotFoundException {
        return new BufferedReader(new InputStreamReader(new URL(var1).openStream()));
    }
}
