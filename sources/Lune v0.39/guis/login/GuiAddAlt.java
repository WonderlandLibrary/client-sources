/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package me.superskidder.lune.guis.login;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import me.superskidder.lune.utils.client.FileUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.net.Proxy;

public class GuiAddAlt
extends GuiScreen {
    private final GuiAltManager manager;
    private GuiPasswordField password;
    private String status = "\u00a7eWaiting...";
    private GuiTextField username;
    private GuiTextField combined;

    public GuiAddAlt(GuiAltManager manager) {
        this.manager = manager;
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 0: {
                AddAltThread login;
                if (this.combined.getText().isEmpty()) {
                    login = new AddAltThread(this.username.getText(), this.password.getText());
                } else if (!this.combined.getText().isEmpty() && this.combined.getText().contains(":")) {
                    String u = this.combined.getText().split(":")[0];
                    String p = this.combined.getText().split(":")[1];
                    login = new AddAltThread(u.replaceAll(" ", ""), p.replaceAll(" ", ""));
                } else {
                    login = new AddAltThread(this.username.getText(), this.password.getText());
                }
                login.start();
                break;
            }
            case 1: {
                this.mc.displayGuiScreen(this.manager);
            }
        }
    }

    @Override
    public void drawScreen(int i, int j, float f) {
        this.drawDefaultBackground();
        mc.fontRendererObj.drawCenteredString("Add Alt", this.width / 2, 20, -1);
        this.username.drawTextBox();
        this.password.drawTextBox();
        this.combined.drawTextBox();
        if (this.username.getText().isEmpty()) {
            mc.fontRendererObj.drawStringWithShadow("Username / E-Mail", this.width / 2 - 96, 66.0f, -7829368);
        }
        if (this.password.getText().isEmpty()) {
            mc.fontRendererObj.drawStringWithShadow("Password", this.width / 2 - 96, 106.0f, -7829368);
        }
        if (this.combined.getText().isEmpty()) {
            mc.fontRendererObj.drawStringWithShadow("Email:Password", this.width / 2 - 96, 146.0f, -7829368);
        }
        mc.fontRendererObj.drawCenteredString(this.status, this.width / 2, 30, -1);
        super.drawScreen(i, j, f);
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents((boolean)true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 92 + 12, "Login"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 116 + 12, "Back"));
        this.username = new GuiTextField(1, this.mc.fontRendererObj, this.width / 2 - 100, 60, 200, 20);
        this.password = new GuiPasswordField(this.mc.fontRendererObj, this.width / 2 - 100, 100, 200, 20);
        this.combined = new GuiTextField(this.eventButton, this.mc.fontRendererObj, this.width / 2 - 100, 140, 200, 20);
        this.combined.setMaxStringLength(200);
    }

    @Override
    protected void keyTyped(char par1, int par2) {
        try {
            super.keyTyped(par1, par2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.username.textboxKeyTyped(par1, par2);
        this.password.textboxKeyTyped(par1, par2);
        this.combined.textboxKeyTyped(par1, par2);
        if (par1 == '\t' && (this.username.isFocused() || this.combined.isFocused() || this.password.isFocused())) {
            this.username.setFocused(!this.username.isFocused());
            this.password.setFocused(!this.password.isFocused());
            this.combined.setFocused(!this.combined.isFocused());
        }
        if (par1 == '\r') {
            this.actionPerformed((GuiButton)this.buttonList.get(0));
        }
    }

    @Override
    protected void mouseClicked(int par1, int par2, int par3) {
        try {
            super.mouseClicked(par1, par2, par3);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        this.username.mouseClicked(par1, par2, par3);
        this.password.mouseClicked(par1, par2, par3);
        this.combined.mouseClicked(par1, par2, par3);
    }

    static /* synthetic */ void access$0(GuiAddAlt guiAddAlt, String string) {
        guiAddAlt.status = string;
    }

    private class AddAltThread
    extends Thread {
        private final String password;
        private final String username;

        public AddAltThread(String username, String password) {
            this.username = username;
            this.password = password;
            GuiAddAlt.access$0(GuiAddAlt.this, "\u00a77Waiting...");
        }

        private final void checkAndAddAlt(String username, String password) {
            YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
            YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication)service.createUserAuthentication(Agent.MINECRAFT);
            auth.setUsername(username);
            auth.setPassword(password);
            try {
                auth.logIn();
                AltManager.getAlts().add(new Alt(username, password));
                FileUtil.saveAlts();
                GuiAddAlt.access$0(GuiAddAlt.this, "\u00a7aAlt added. (" + username + ")");
            }
            catch (AuthenticationException e) {
                GuiAddAlt.access$0(GuiAddAlt.this, "\u00a7cAlt failed!");
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            if (this.password.equals("")) {
                AltManager.getAlts().add(new Alt(this.username, ""));
                FileUtil.saveAlts();
                GuiAddAlt.access$0(GuiAddAlt.this, "\u00a7aAlt added. (" + this.username + " - offline name)");
                return;
            }
            GuiAddAlt.access$0(GuiAddAlt.this, "\u00a7eTrying alt...");
            this.checkAndAddAlt(this.username, this.password);
        }
    }

}

