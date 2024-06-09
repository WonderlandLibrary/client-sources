/*
 * Decompiled with CFR 0_122.
 */
package winter.utils.other.alts;

import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.UserAuthentication;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;
import winter.Client;
import winter.utils.other.alts.Alt;
import winter.utils.other.alts.AltManager;
import winter.utils.other.alts.GuiAltManager;
import winter.utils.other.alts.files.AltFileManager;
import winter.utils.other.alts.files.Alts;
import winter.utils.other.alts.files.CustomFile;

public class GuiAddAltUP
extends GuiScreen {
    private final GuiAltManager manager;
    private String status = (Object)((Object)EnumChatFormatting.GRAY) + "Idle...";
    private GuiTextField account;

    public GuiAddAltUP(GuiAltManager manager) {
        this.manager = manager;
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 0: {
                AddAltThread login = new AddAltThread(this.account.getText());
                login.start();
                break;
            }
            case 1: {
                this.mc.displayGuiScreen(this.manager);
            }
        }
    }

    @Override
    public void drawScreen(int i2, int j2, float f2) {
        this.drawDefaultBackground();
        this.account.drawTextBox();
        this.drawCenteredString(this.fontRendererObj, "Add Alt", this.width / 2, 20, -1);
        if (this.account.getText().isEmpty()) {
            this.drawString(this.mc.fontRendererObj, "Username:Pass", this.width / 2 - 96, 66, -7829368);
        }
        this.drawCenteredString(this.fontRendererObj, this.status, this.width / 2, 30, -1);
        super.drawScreen(i2, j2, f2);
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 92 + 12, "Login"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 116 + 12, "Back"));
        this.account = new GuiTextField(this.eventButton, this.mc.fontRendererObj, this.width / 2 - 100, 60, 200, 20);
    }

    @Override
    protected void keyTyped(char par1, int par2) {
        this.account.textboxKeyTyped(par1, par2);
        if (par1 == '\t' && this.account.isFocused()) {
            this.account.setFocused(!this.account.isFocused());
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
        catch (IOException e2) {
            e2.printStackTrace();
        }
        this.account.mouseClicked(par1, par2, par3);
    }

    static void access$0(GuiAddAltUP guiAddAlt, String status) {
        guiAddAlt.status = status;
    }

    private class AddAltThread
    extends Thread {
        private final String account;
        private String username;
        private String password;

        public AddAltThread(String account) {
            this.account = account;
            this.username = "";
            this.password = "";
            GuiAddAltUP.access$0(GuiAddAltUP.this, (Object)((Object)EnumChatFormatting.GRAY) + "Idle...");
        }

        private final void checkAndAddAlt(String username, String password) {
            YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
            YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication)service.createUserAuthentication(Agent.MINECRAFT);
            auth.setUsername(username);
            auth.setPassword(password);
            try {
                auth.logIn();
                AltManager altManager = Client.altManager;
                AltManager.registry.add(new Alt(username, password, auth.getSelectedProfile().getName()));
                Client.altFiles.getFile(Alts.class).saveFile();
                GuiAddAltUP.access$0(GuiAddAltUP.this, "Alt added. (" + username + ")");
            }
            catch (AuthenticationException e2) {
                GuiAddAltUP.access$0(GuiAddAltUP.this, (Object)((Object)EnumChatFormatting.RED) + "Alt failed!");
                e2.printStackTrace();
            }
        }

        @Override
        public void run() {
            int i2 = this.account.indexOf(":");
            if (i2 >= 0) {
                this.username = this.account.substring(0, i2).trim();
                this.password = this.account.substring(i2 + 1).trim();
                System.out.println(String.valueOf(this.username) + ", " + this.password);
            }
            if (this.password.equals("")) {
                AltManager altManager = Client.altManager;
                AltManager.registry.add(new Alt(this.username, ""));
                GuiAddAltUP.access$0(GuiAddAltUP.this, (Object)((Object)EnumChatFormatting.GREEN) + "Alt added. (" + this.username + " - offline name)");
                return;
            }
            GuiAddAltUP.access$0(GuiAddAltUP.this, (Object)((Object)EnumChatFormatting.YELLOW) + "Trying alt...");
            this.checkAndAddAlt(this.username, this.password);
        }
    }

}

