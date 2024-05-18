// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.gui.screens.altmanager;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.io.InputStreamReader;
import java.net.URL;
import java.io.BufferedReader;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.Agent;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import java.net.Proxy;
import java.util.Random;
import com.klintos.twelve.gui.screens.altmanager.slot.Alt;
import net.minecraft.util.Session;
import com.klintos.twelve.gui.screens.mainmenu.MainMenu;
import org.lwjgl.input.Keyboard;
import com.klintos.twelve.utils.FileUtils;
import net.minecraft.client.Minecraft;
import com.klintos.twelve.gui.components.PasswordTextField;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.FontRenderer;
import com.klintos.twelve.gui.screens.altmanager.slot.AltSlot;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiAltManager extends GuiScreen
{
    private static String response;
    private GuiButton removeButton;
    private GuiButton loginButton;
    private GuiButton randomButton;
    private GuiButton directLoginButton;
    private GuiButton addButton;
    private GuiButton returnButton;
    public static AltSlot slot;
    private FontRenderer fontRendererObj;
    private static GuiTextField usernameTextField;
    private PasswordTextField passwordTextField;
    static boolean alreadyLogging;
    
    static {
        GuiAltManager.response = "§a" + Minecraft.getMinecraft().getSession().username;
    }
    
    public GuiAltManager() {
        this.fontRendererObj = Minecraft.getMinecraft().fontRendererObj;
        GuiAltManager.response = "§c" + Minecraft.getMinecraft().getSession().username;
        GuiAltManager.alreadyLogging = false;
        FileUtils.loadAltAccounts();
    }
    
    public void updateScreen() {
        GuiAltManager.usernameTextField.updateCursorCounter();
        this.passwordTextField.updateCursorCounter();
    }
    
    public void initGui() {
        AltSlot.altList.clear();
        FileUtils.loadAltAccounts();
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(this.directLoginButton = new GuiButton(0, this.width - 210, this.height - 25, 204, 20, "Direct Login"));
        this.buttonList.add(new GuiButton(1, 5, this.height - 25, 200, 20, "Back"));
        this.buttonList.add(this.addButton = new GuiButton(2, 5, this.height - 48, 98, 20, "Add"));
        this.buttonList.add(this.removeButton = new GuiButton(3, 107, this.height - 48, 98, 20, "Remove"));
        this.buttonList.add(this.loginButton = new GuiButton(4, 5, this.height - 93, 200, 20, "Login"));
        this.buttonList.add(this.randomButton = new GuiButton(5, 5, this.height - 71, 200, 20, "Random"));
        GuiAltManager.usernameTextField = new GuiTextField(0, this.fontRendererObj, this.width - 209, this.height - 83, 200, 20);
        this.passwordTextField = new PasswordTextField(this.fontRendererObj, this.width - 209, this.height - 50, 200, 20);
        (GuiAltManager.slot = new AltSlot(this.mc, this)).registerScrollButtons(7, 8);
    }
    
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }
    
    protected void actionPerformed(final GuiButton guibutton) {
        if (!guibutton.enabled) {
            return;
        }
        if (guibutton.id == 1) {
            this.mc.displayGuiScreen((GuiScreen)new MainMenu());
        }
        else if (guibutton.id == 0 && this.directLoginButton.enabled) {
            GuiAltManager.response = "§c§k0000000000";
            new Thread() {
                @Override
                public void run() {
                    if (!GuiAltManager.usernameTextField.getText().equals("")) {
                        if (GuiAltManager.this.passwordTextField.getText().length() > 0) {
                            final String s = GuiAltManager.usernameTextField.getText();
                            final String s2 = GuiAltManager.this.passwordTextField.getText();
                            try {
                                final String result = GuiAltManager.Login(s, s2).trim();
                                if (result == null || !result.contains(":")) {
                                    GuiAltManager.access$2("§4Unable to log in, " + result + ".");
                                    return;
                                }
                                final String[] values = result.split(":");
                                if (values.length <= 1) {
                                    return;
                                }
                                GuiAltManager.this.mc.session = new Session(values[2].trim(), values[4].trim(), values[3].trim(), "");
                                GuiAltManager.usernameTextField.setText("");
                                GuiAltManager.this.passwordTextField.setText("");
                                GuiAltManager.access$2("§a" + Minecraft.getMinecraft().getSession().username);
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            GuiAltManager.this.mc.session = new Session(GuiAltManager.usernameTextField.getText(), "", "", "");
                            GuiAltManager.usernameTextField.setText("");
                            GuiAltManager.this.passwordTextField.setText("");
                            GuiAltManager.access$2("§a" + Minecraft.getMinecraft().getSession().username);
                        }
                    }
                    else {
                        GuiAltManager.access$2("§4Username can't be empty.");
                    }
                }
            }.start();
        }
        else if (guibutton.id == 2 && this.addButton.enabled) {
            final String s = GuiAltManager.usernameTextField.getText();
            final String s2 = this.passwordTextField.getText();
            if (!s.equals("")) {
                if (!s2.equals("")) {
                    if (!AltSlot.altList.contains(AltSlot.altList.indexOf(new Alt(s, s2)))) {
                        AltSlot.addAlt(s, s2);
                        GuiAltManager.usernameTextField.setText("");
                        this.passwordTextField.setText("");
                        GuiAltManager.response = "§a" + Minecraft.getMinecraft().getSession().username;
                    }
                    else {
                        GuiAltManager.response = "§4List already contains this alt.";
                    }
                }
                else if (!AltSlot.altList.contains(AltSlot.altList.indexOf(new Alt(s)))) {
                    AltSlot.addAlt(s);
                    GuiAltManager.usernameTextField.setText("");
                    this.passwordTextField.setText("");
                    GuiAltManager.response = "§a" + Minecraft.getMinecraft().getSession().username;
                }
                else {
                    GuiAltManager.response = "§4List already contains this alt.";
                }
            }
            else {
                GuiAltManager.response = "§4Username can't be empty.";
            }
        }
        else if (guibutton.id == 3 && this.removeButton.enabled) {
            final String s = GuiAltManager.usernameTextField.getText();
            final String s2 = this.passwordTextField.getText();
            if (!AltSlot.altList.isEmpty()) {
                AltSlot.removeCurrentAlt();
            }
            else {
                GuiAltManager.response = "§4Cannot remove this, nothing to remove.";
            }
        }
        else if (guibutton.id == 4 && this.loginButton.enabled) {
            GuiAltManager.response = "§c§k0000000000";
            new Thread() {
                @Override
                public void run() {
                    if (AltSlot.getCurrentPassword().length() > 0) {
                        final String s = AltSlot.getCurrentUsername();
                        final String s2 = AltSlot.getCurrentPassword();
                        try {
                            final String result = GuiAltManager.Login(s, s2).trim();
                            if (result == null || !result.contains(":")) {
                                GuiAltManager.access$2("§4Unable to log in, " + result + ".");
                                return;
                            }
                            final String[] values = result.split(":");
                            if (values.length > 1) {
                                GuiAltManager.this.mc.session = new Session(values[2].trim(), values[4].trim(), values[3].trim(), "");
                            }
                            GuiAltManager.access$2("§a" + Minecraft.getMinecraft().getSession().username);
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        GuiAltManager.this.mc.session = new Session(GuiAltManager.usernameTextField.getText(), "", "", "");
                        GuiAltManager.access$2("§a" + Minecraft.getMinecraft().getSession().username);
                    }
                }
            }.start();
        }
        else if (guibutton.id == 5 && this.randomButton.enabled) {
            GuiAltManager.response = "§c§k0000000000";
            new Thread() {
                @Override
                public void run() {
                    final Random rand = new Random();
                    AltSlot.setCurrent(rand.nextInt(AltSlot.altList.size()));
                    if (AltSlot.getCurrentPassword().length() > 0) {
                        final String s = AltSlot.getCurrentUsername();
                        final String s2 = AltSlot.getCurrentPassword();
                        try {
                            final String result = GuiAltManager.Login(s, s2).trim();
                            if (result == null || !result.contains(":")) {
                                GuiAltManager.access$2("§4Unable to log in, " + result + ".");
                                return;
                            }
                            final String[] values = result.split(":");
                            if (values.length > 1) {
                                GuiAltManager.this.mc.session = new Session(values[2].trim(), values[4].trim(), values[3].trim(), "");
                            }
                            GuiAltManager.access$2("§a" + Minecraft.getMinecraft().getSession().username);
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        GuiAltManager.this.mc.session = new Session(AltSlot.getCurrentUsername(), "", "", "");
                        GuiAltManager.access$2("§a" + Minecraft.getMinecraft().getSession().username);
                    }
                }
            }.start();
        }
        else if (guibutton.id == 7) {
            FileUtils.saveAltAccounts();
        }
    }
    
    public static String Login(final String username, final String password) {
        if (username == null || username.length() <= 0 || password == null || password.length() <= 0) {
            return "Bad Request";
        }
        final YggdrasilAuthenticationService a = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
        final YggdrasilUserAuthentication b = (YggdrasilUserAuthentication)a.createUserAuthentication(Agent.MINECRAFT);
        b.setUsername(username);
        b.setPassword(password);
        try {
            b.logIn();
            return "swag:yolo:" + b.getSelectedProfile().getName() + ":" + b.getAuthenticatedToken() + ":" + b.getSelectedProfile().getId();
        }
        catch (AuthenticationException e) {
            e.printStackTrace();
            return "Bad Login";
        }
    }
    
    private BufferedReader read(final String url) throws Exception, FileNotFoundException {
        return new BufferedReader(new InputStreamReader(new URL(url).openStream()));
    }
    
    protected void keyTyped(final char par1, final int par2) {
        GuiAltManager.usernameTextField.textboxKeyTyped(par1, par2);
        this.passwordTextField.textboxKeyTyped(par1, par2);
        if (par1 == '\t') {
            if (!GuiAltManager.usernameTextField.isFocused() && !this.passwordTextField.isFocused()) {
                GuiAltManager.usernameTextField.setFocused(!GuiAltManager.usernameTextField.isFocused());
            }
            else {
                GuiAltManager.usernameTextField.setFocused(!GuiAltManager.usernameTextField.isFocused());
                this.passwordTextField.setFocused(!this.passwordTextField.isFocused());
            }
        }
        if (par1 == '\r') {
            this.actionPerformed((GuiButton) this.buttonList.get(0));
        }
    }
    
    protected void mouseClicked(final int par1, final int par2, final int par3) {
        try {
            final int var4 = par2 - GuiAltManager.slot.top - GuiAltManager.slot.headerPadding + (int)GuiAltManager.slot.amountScrolled - 4;
            final int slotID = var4 / GuiAltManager.slot.slotHeight;
            GuiAltManager.slot.elementClicked(slotID, false, par1, par2);
            super.mouseClicked(par1, par2, par3);
            GuiAltManager.usernameTextField.mouseClicked(par1, par2, par3);
            this.passwordTextField.mouseClicked(par1, par2, par3);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void drawScreen(final int i, final int j, final float f) {
        if (Keyboard.isKeyDown(1)) {
            this.mc.displayGuiScreen((GuiScreen)new MainMenu());
        }
        if (!GuiAltManager.alreadyLogging && Keyboard.isKeyDown(28)) {
            GuiAltManager.alreadyLogging = true;
            externalLogin();
        }
        this.drawDefaultBackground();
        this.removeButton.enabled = !AltSlot.altList.isEmpty();
        this.loginButton.enabled = !AltSlot.altList.isEmpty();
        this.randomButton.enabled = !AltSlot.altList.isEmpty();
        this.directLoginButton.enabled = !GuiAltManager.usernameTextField.getText().isEmpty();
        this.addButton.enabled = !GuiAltManager.usernameTextField.getText().isEmpty();
        GuiAltManager.slot.drawScreen(i, j, f);
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("Alt Manager", this.width / 2 - Minecraft.getMinecraft().fontRendererObj.getStringWidth("Alt Manager") / 2, 10, 16777215);
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("Username:", this.width - 210, this.height - 93, -1);
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("Password:", this.width - 210, this.height - 60, -1);
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("Accounts Loaded: §c" + AltSlot.altList.size(), this.width / 2 - Minecraft.getMinecraft().fontRendererObj.getStringWidth("Accounts Loaded: §c" + AltSlot.altList.size()) / 2, this.height - 95, 16777215);
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("Currently logged in as:", this.width / 2 - Minecraft.getMinecraft().fontRendererObj.getStringWidth("Currently logged in as:") / 2, this.height - 22, 16777215);
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(GuiAltManager.response, this.width / 2 - Minecraft.getMinecraft().fontRendererObj.getStringWidth(GuiAltManager.response) / 2, this.height - 12, 16777215);
        GuiAltManager.usernameTextField.drawTextBox();
        this.passwordTextField.drawTextBox();
        super.drawScreen(i, j, f);
    }
    
    public static void externalLogin() {
        GuiAltManager.response = "§c§k0000000000";
        new Thread() {
            @Override
            public void run() {
                if (AltSlot.getCurrentPassword().length() > 0) {
                    final String s = AltSlot.getCurrentUsername();
                    final String s2 = AltSlot.getCurrentPassword();
                    try {
                        final String result = GuiAltManager.Login(s, s2).trim();
                        if (result == null || !result.contains(":")) {
                            GuiAltManager.access$2("§4Unable to log in, " + result + ".");
                            return;
                        }
                        final String[] values = result.split(":");
                        if (values.length > 1) {
                            Minecraft.getMinecraft().session = (new Session(values[2].trim(), values[4].trim(), values[3].trim(), "mojang"));
                        }
                        GuiAltManager.access$2("§a" + Minecraft.getMinecraft().getSession().username);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    GuiAltManager.alreadyLogging = false;
                }
                else {
                    Minecraft.getMinecraft().session = (new Session(GuiAltManager.usernameTextField.getText(), "", "", ""));
                    GuiAltManager.access$2("§a" + Minecraft.getMinecraft().getSession().username);
                    GuiAltManager.alreadyLogging = false;
                }
            }
        }.start();
    }
    
    static /* synthetic */ void access$2(final String response) {
        GuiAltManager.response = response;
    }
}
