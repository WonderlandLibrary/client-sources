package ClassSub;

import net.minecraft.util.*;
import java.awt.*;
import java.awt.datatransfer.*;
import net.minecraft.client.gui.*;
import org.lwjgl.input.*;
import java.io.*;
import java.net.*;
import com.mojang.authlib.yggdrasil.*;
import com.mojang.authlib.*;
import cn.Hanabi.*;
import com.mojang.authlib.exceptions.*;

public class Class24 extends GuiScreen
{
    private final Class7 manager;
    private Class35 password;
    private String status;
    private GuiTextField username;
    
    
    public Class24(final Class7 manager) {
        this.status = EnumChatFormatting.GRAY + "Idle...";
        this.manager = manager;
    }
    
    protected void actionPerformed(final GuiButton guiButton) {
        switch (guiButton.id) {
            case 0: {
                new Class50(this.username.getText(), this.password.getText()).start();
                break;
            }
            case 1: {
                this.mc.displayGuiScreen((GuiScreen)this.manager);
                break;
            }
            case 2: {
                String s;
                try {
                    s = (String)Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
                }
                catch (Exception ex) {
                    break;
                }
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
    
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        Class246.drawRect(0.0f, 0.0f, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), 0);
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
        super.drawScreen(n, n2, n3);
    }
    
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 92 + 12, "Login"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 116 + 12, "Back"));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 4 + 116 + 36, "Import user:pass"));
        this.username = new GuiTextField(99999, this.mc.fontRendererObj, this.width / 2 - 100, 60, 200, 20);
        this.password = new Class35(this.mc.fontRendererObj, this.width / 2 - 100, 100, 200, 20);
    }
    
    protected void keyTyped(final char c, final int n) {
        this.username.textboxKeyTyped(c, n);
        this.password.textboxKeyTyped(c, n);
        if (c == '\t' && (this.username.isFocused() || this.password.isFocused())) {
            this.username.setFocused(!this.username.isFocused());
            this.password.setFocused(!this.password.isFocused());
        }
        if (c == '\r') {
            this.actionPerformed(this.buttonList.get(0));
        }
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
    
    static void access$0(final Class24 class24, final String status) {
        class24.status = status;
    }
    
    private class Class50 extends Thread
    {
        private final String password;
        private final String username;
        final Class24 this$0;
        
        
        public Class50(final Class24 this$0, final String username, final String password) {
            this.this$0 = this$0;
            this.username = username;
            this.password = password;
            Class24.access$0(this$0, EnumChatFormatting.GRAY + "Idle...");
        }
        
        private final void checkAndAddAlt(final String username, final String password) {
            final YggdrasilUserAuthentication yggdrasilUserAuthentication = (YggdrasilUserAuthentication)new YggdrasilAuthenticationService(Proxy.NO_PROXY, "").createUserAuthentication(Agent.MINECRAFT);
            yggdrasilUserAuthentication.setUsername(username);
            yggdrasilUserAuthentication.setPassword(password);
            try {
                yggdrasilUserAuthentication.logIn();
                Class206.registry.add(new Class309(username, password, yggdrasilUserAuthentication.getSelectedProfile().getName()));
                try {
                    Hanabi.INSTANCE.altFileMgr.getFile(Class85.class).saveFile();
                }
                catch (Exception ex2) {}
                Class24.access$0(this.this$0, "Alt added. (" + username + ")");
            }
            catch (AuthenticationException ex) {
                Class24.access$0(this.this$0, EnumChatFormatting.RED + "Alt failed!");
                ex.printStackTrace();
            }
        }
        
        @Override
        public void run() {
            if (this.password.equals("")) {
                Class206.registry.add(new Class309(this.username, ""));
                Class24.access$0(this.this$0, EnumChatFormatting.GREEN + "Alt added. (" + this.username + " - offline name)");
                return;
            }
            Class24.access$0(this.this$0, EnumChatFormatting.AQUA + "Trying alt...");
            this.checkAndAddAlt(this.username, this.password);
        }
    }
}
