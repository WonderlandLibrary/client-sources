package me.valk.agway.gui.altmanager;

import java.io.File;
import java.io.IOException;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;

import me.valk.Vital;
import me.valk.utils.bind.FileUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GuiAddAlt extends GuiScreen
{
    private final GuiAltManager manager;
    private PasswordField password;
    private String status;
    private GuiTextField username;
    
    public GuiAddAlt(final GuiAltManager manager) {
        this.status = EnumChatFormatting.GRAY + "Idle...";
        this.manager = manager;
    }
    
    private static final File ALTS = FileUtils.getConfigFile("Alts");

	public static void saveAlts() {
		final List<String> fileContent = new ArrayList<String>();
		FileUtils.write(ALTS, fileContent, true);
		for (final Alt alt : AltManager.registry) {
			final String username = alt.getUsername();
			final String password = alt.getPassword();
			fileContent.add(String.format("%s:%s", username, password));
		}
		FileUtils.write(ALTS, fileContent, true);
	}

	public static void loadAlts() {
		try {
			final List<String> fileContent = FileUtils.read(ALTS);
			for (final String line : fileContent) {
				final String[] split = line.split(":");
				final String username = split[0];
				final String password = split[1];
				AltManager.registry.add(new Alt(username, password));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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
    	ScaledResolution scaledRes = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        this.mc.getTextureManager().bindTexture(new ResourceLocation("agway/bg.jpg"));
        Gui.drawScaledCustomSizeModalRect(0, 0, 0.0F, 0.0F, scaledRes.getScaledWidth(), scaledRes.getScaledHeight(), 
        scaledRes.getScaledWidth(), scaledRes.getScaledHeight(), scaledRes.getScaledWidth(), 
        scaledRes.getScaledHeight());
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
        this.username = new GuiTextField(this.eventButton, this.mc.fontRendererObj, this.width / 2 - 100, 60, 200, 20);
        this.password = new PasswordField(this.mc.fontRendererObj, this.width / 2 - 100, 100, 200, 20);
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
            GuiAddAlt.access$0(GuiAddAlt.this, EnumChatFormatting.GRAY + "Idle...");
        }
        
        private final void checkAndAddAlt(final String username, final String password) {
            final YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
            final YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication)service.createUserAuthentication(Agent.MINECRAFT);
            auth.setUsername(username);
            auth.setPassword(password);
            try {
                auth.logIn();
                Vital.getAltManager();
                AltManager.registry.add(new Alt(username, password, auth.getSelectedProfile().getName()));
                try {
                	saveAlts();
                }
                catch (Exception ex) {}
                GuiAddAlt.access$0(GuiAddAlt.this, "Alt added. (" + username + ")");
            }
            catch (AuthenticationException e) {
                GuiAddAlt.access$0(GuiAddAlt.this, EnumChatFormatting.RED + "Alt failed!");
                e.printStackTrace();
            }
        }
        
        @Override
        public void run() {
            if (this.password.equals("")) {
                Vital.getAltManager();
                AltManager.registry.add(new Alt(this.username, ""));
                GuiAddAlt.access$0(GuiAddAlt.this, EnumChatFormatting.GREEN + "Alt added. (" + this.username + " - offline name)");
                return;
            }
            GuiAddAlt.access$0(GuiAddAlt.this, EnumChatFormatting.AQUA + "Trying alt...");
            this.checkAndAddAlt(this.username, this.password);
        }
    }
}
