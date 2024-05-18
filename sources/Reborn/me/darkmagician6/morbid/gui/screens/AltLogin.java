package me.darkmagician6.morbid.gui.screens;

import org.lwjgl.input.*;
import me.darkmagician6.morbid.util.file.*;
import java.net.*;
import java.io.*;

public class AltLogin extends axr
{
    private axr parentScreen;
    private aws usernameTextField;
    private PasswordField passwordTextField;
    private String error;
    
    public AltLogin(final axr guiscreen) {
        this.parentScreen = guiscreen;
    }
    
    @Override
    public void c() {
        this.usernameTextField.a();
        this.passwordTextField.updateCursorCounter();
    }
    
    @Override
    public void b() {
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    protected void a(final awg guibutton) {
        if (!guibutton.g) {
            return;
        }
        if (guibutton.f == 1) {
            this.g.a(this.parentScreen);
        }
        else if (guibutton.f == 2) {
            if (PreviousAccount.password == null || PreviousAccount.username == null) {
                return;
            }
            final String s = PreviousAccount.username;
            final String s2 = PreviousAccount.password;
            PreviousAccount.saveAccount(s, s2);
            try {
                final String result = this.Login(s, s2).trim();
                if (result == null || !result.contains(":")) {
                    this.error = result;
                    return;
                }
                final String[] values = result.split(":");
                if (values.length > 1) {
                    this.g.k = new awf(values[2], values[3]);
                }
                this.g.a(this.parentScreen);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (guibutton.f == 0) {
            if (this.passwordTextField.getText().length() > 0) {
                final String s = this.usernameTextField.b();
                final String s2 = this.passwordTextField.getText();
                PreviousAccount.saveAccount(s, s2);
                try {
                    final String result = this.Login(s, s2).trim();
                    if (result == null || !result.contains(":")) {
                        this.error = result;
                        return;
                    }
                    final String[] values = result.split(":");
                    if (values.length > 1) {
                        this.g.k = new awf(values[2], values[3]);
                    }
                    this.g.a(this.parentScreen);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                this.g.k = new awf(this.usernameTextField.b(), "");
            }
            this.g.a(this.parentScreen);
        }
    }
    
    @Override
    protected void a(final char c, final int i) {
        this.usernameTextField.a(c, i);
        this.passwordTextField.textboxKeyTyped(c, i);
        if (c == '\t') {
            if (this.usernameTextField.l()) {
                this.usernameTextField.b(false);
                this.passwordTextField.setFocused(true);
            }
            else {
                this.usernameTextField.b(true);
                this.passwordTextField.setFocused(false);
            }
        }
        if (c == '\r') {
            this.a(this.k.get(0));
        }
    }
    
    @Override
    protected void a(final int i, final int j, final int k) {
        super.a(i, j, k);
        this.usernameTextField.a(i, j, k);
        this.passwordTextField.mouseClicked(i, j, k);
    }
    
    @Override
    public void A_() {
        Keyboard.enableRepeatEvents(true);
        this.k.clear();
        this.k.add(new awg(0, this.h / 2 - 100, this.i / 4 + 96, "Login"));
        this.k.add(new awg(2, this.h / 2 - 100, this.i / 4 + 120, "Previous alt"));
        this.k.add(new awg(1, this.h / 2 - 100, this.i / 4 + 144, "Cancel"));
        this.usernameTextField = new aws(this.m, this.h / 2 - 100, 76, 200, 20);
        this.passwordTextField = new PasswordField(this.m, this.h / 2 - 100, 116, 200, 20);
        this.usernameTextField.f(64);
    }
    
    @Override
    public void a(final int i, final int j, final float f) {
        this.e();
        this.a(this.m, "Change Account", this.h / 2, this.i / 4 - 60 + 20, 16777215);
        this.b(this.m, "Username", this.h / 2 - 100, 63, 10526880);
        this.b(this.m, "Password", this.h / 2 - 100, 104, 10526880);
        this.usernameTextField.f();
        this.passwordTextField.drawTextBox();
        if (this.error != null) {
            this.a(this.m, "§c Login Failed:" + this.error, this.h / 2, this.i / 4 + 72 + 12, 16777215);
        }
        super.a(i, j, f);
    }
    
    public String Login(final String username, final String password) {
        String resultText = "";
        final String loginpage = "http://login.minecraft.net/?user=" + username + "&password=" + password + "&version=13";
        try {
            final BufferedReader pageReader = this.read(loginpage);
            String s = "";
            while ((s = pageReader.readLine()) != null) {
                resultText = s;
            }
            pageReader.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return resultText;
    }
    
    private BufferedReader read(final String url) throws Exception, FileNotFoundException {
        return new BufferedReader(new InputStreamReader(new URL(url).openStream()));
    }
}
