// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.altmanager;

import com.mojang.authlib.exceptions.AuthenticationException;
import net.andrewsnetwork.icarus.utilities.Alt;
import net.andrewsnetwork.icarus.Icarus;
import com.mojang.authlib.Agent;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import java.net.Proxy;
import java.io.IOException;
import org.lwjgl.input.Keyboard;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.andrewsnetwork.icarus.utilities.GuiPasswordField;
import net.minecraft.client.gui.GuiScreen;

public class GuiAddAlt extends GuiScreen
{
    private final GuiAltManager manager;
    private GuiPasswordField password;
    private String status;
    private GuiTextField username;
    
    public GuiAddAlt(final GuiAltManager manager) {
        this.status = "§7Waiting...";
        this.manager = manager;
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) {
        switch (button.id) {
            case 0: {
                final AddAltThread login = new AddAltThread(this.username.getText(), this.password.getText());
                login.start();
                break;
            }
            case 1: {
                this.mc.displayGuiScreen(this.manager);
                break;
            }
        }
    }
    
    @Override
    public void drawScreen(final int i, final int j, final float f) {
        this.drawDefaultBackground();
        this.username.drawTextBox();
        this.password.drawTextBox();
        this.drawCenteredString(this.fontRendererObj, "Add Alt", this.width / 2, 20, -1);
        if (this.username.getText().isEmpty()) {
            this.drawString(this.mc.fontRendererObj, "Username / E-Mail", this.width / 2 - 96, 66, -7829368);
        }
        if (this.password.getText().isEmpty()) {
            this.drawString(this.mc.fontRendererObj, "Password", this.width / 2 - 96, 106, -7829368);
        }
        this.drawCenteredString(this.fontRendererObj, this.status, this.width / 2, 30, -1);
        super.drawScreen(i, j, f);
    }
    
    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 92 + 12, "Login"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 116 + 12, "Back"));
        this.username = new GuiTextField(1, this.mc.fontRendererObj, this.width / 2 - 100, 60, 200, 20);
        this.password = new GuiPasswordField(this.mc.fontRendererObj, this.width / 2 - 100, 100, 200, 20);
    }
    
    @Override
    protected void keyTyped(final char par1, final int par2) {
        this.username.textboxKeyTyped(par1, par2);
        this.password.textboxKeyTyped(par1, par2);
        if (par1 == '\t' && (this.username.isFocused() || this.password.isFocused())) {
            this.username.setFocused(!this.username.isFocused());
            this.password.setFocused(!this.password.isFocused());
        }
        if (par1 == '\r') {
            this.actionPerformed((GuiButton) this.buttonList.get(0));
        }
    }
    
    @Override
    protected void mouseClicked(final int par1, final int par2, final int par3) {
        try {
            super.mouseClicked(par1, par2, par3);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        this.username.mouseClicked(par1, par2, par3);
        this.password.mouseClicked(par1, par2, par3);
    }
    
    static /* synthetic */ void access$0(final GuiAddAlt guiAddAlt, final String status) {
        guiAddAlt.status = status;
    }
    
    private class AddAltThread extends Thread
    {
        private final String password;
        private final String username;
        
        public AddAltThread(final String username, final String password) {
            this.username = username;
            this.password = password;
            GuiAddAlt.access$0(GuiAddAlt.this, "§7Waiting...");
        }
        
        private final void checkAndAddAlt(final String username, final String password) {
            final YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
            final YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication)service.createUserAuthentication(Agent.MINECRAFT);
            auth.setUsername(username);
            auth.setPassword(password);
            try {
                auth.logIn();
                Icarus.getAltManager().getAlts().add(new Alt(username, password));
                Icarus.getFileManager().getFileByName("alts").saveFile();
                GuiAddAlt.access$0(GuiAddAlt.this, "§aAlt added. (" + username + ")");
            }
            catch (AuthenticationException e) {
                GuiAddAlt.access$0(GuiAddAlt.this, "§cAlt failed!");
                e.printStackTrace();
            }
        }
        
        @Override
        public void run() {
            if (this.password.equals("")) {
                Icarus.getAltManager().getAlts().add(new Alt(this.username, ""));
                Icarus.getFileManager().getFileByName("alts").saveFile();
                GuiAddAlt.access$0(GuiAddAlt.this, "§aAlt added. (" + this.username + " - offline name)");
                return;
            }
            GuiAddAlt.access$0(GuiAddAlt.this, "§eTrying alt...");
            this.checkAndAddAlt(this.username, this.password);
        }
    }
}
