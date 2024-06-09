/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.gui;

import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.UserAuthentication;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import java.io.IOException;
import java.net.Proxy;
import java.util.List;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.Session;
import org.lwjgl.input.Keyboard;

public class AltLogin
extends GuiScreen {
    public GuiTextField email;
    public GuiTextField pw;
    public GuiScreen alterScreen;
    private Object GuiButton;
    public String status;
    public String name;

    public AltLogin(GuiScreen alt2) {
        this.alterScreen = alt2;
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 96 + 50, "Login"));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 4 + 96 + 73, "Back"));
        this.buttonList.add(new GuiButton(3, this.width / 2 - 100, this.height / 4 + 96 + 96, "Clipboard"));
        this.email = new GuiTextField(3, this.fontRendererObj, this.width / 2 - 100, 140, 200, 20);
        this.pw = new GuiTextField(4, this.fontRendererObj, this.width / 2 - 100, 180, 200, 20);
        this.email.setMaxStringLength(50);
        this.pw.setMaxStringLength(50);
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    public void updateScreen() {
        this.email.updateCursorCounter();
        this.pw.updateCursorCounter();
    }

    @Override
    protected void mouseClicked(int x2, int y2, int m2) {
        this.email.mouseClicked(x2, y2, m2);
        this.pw.mouseClicked(x2, y2, m2);
        try {
            super.mouseClicked(x2, y2, m2);
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override
    protected void keyTyped(char c2, int i2) throws IOException {
        this.email.textboxKeyTyped(c2, i2);
        this.pw.textboxKeyTyped(c2, i2);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawBackground(1);
        AltLogin.drawString(this.fontRendererObj, "Email:", this.width / 2 - 100, 130, -1);
        AltLogin.drawString(this.fontRendererObj, "Password:", this.width / 2 - 100, 170, -1);
        AltLogin.drawString(this.fontRendererObj, "\u00a73Logged in with \u00a7a" + Minecraft.session.getUsername(), this.width / 2 - 75, 80, -1);
        this.email.drawTextBox();
        this.pw.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 3) {
            String Copy = AltLogin.getClipboardString();
            String[] Emails = Copy.split(":");
            String Email1 = Emails[0];
            String Email2 = Emails[1];
            this.email.writeText(Email1);
            this.pw.writeText(Email2);
            if (!this.email.getText().trim().isEmpty()) {
                YggdrasilUserAuthentication a2 = (YggdrasilUserAuthentication)new YggdrasilAuthenticationService(Proxy.NO_PROXY, "").createUserAuthentication(Agent.MINECRAFT);
                a2.setUsername(this.email.getText().trim());
                a2.setPassword(this.pw.getText().trim());
                this.status = "1";
                String name = "1";
                name = a2.getUserID();
                try {
                    a2.logIn();
                    Minecraft.session = new Session(a2.getSelectedProfile().getName(), a2.getSelectedProfile().getId().toString(), a2.getAuthenticatedToken(), "mojang");
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
        }
        if (button.id == 2) {
            this.mc.displayGuiScreen(this.alterScreen);
        } else if (this.pw.getText().trim().isEmpty()) {
            if (this.email.getText().trim().isEmpty()) {
                Minecraft.session = new Session(this.email.getText().trim(), "-", "-", "Legacy");
                this.status = "Eingeloggt!";
            } else {
                this.status = "Bitte gebe etwas in das Email Feld ein";
            }
        } else if (!this.email.getText().trim().isEmpty()) {
            YggdrasilUserAuthentication a3 = (YggdrasilUserAuthentication)new YggdrasilAuthenticationService(Proxy.NO_PROXY, "").createUserAuthentication(Agent.MINECRAFT);
            a3.setUsername(this.email.getText().trim());
            a3.setPassword(this.pw.getText().trim());
            try {
                a3.logIn();
                Minecraft.session = new Session(a3.getSelectedProfile().getName(), a3.getSelectedProfile().getId().toString(), a3.getAuthenticatedToken(), "mojang");
            }
            catch (Exception Emails) {
                // empty catch block
            }
        }
    }
}

