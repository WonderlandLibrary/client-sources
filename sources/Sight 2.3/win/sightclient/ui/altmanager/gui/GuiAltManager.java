package win.sightclient.ui.altmanager.gui;

import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.Proxy;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import com.thealtening.auth.service.AlteningServiceType;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Session;
import win.sightclient.Sight;
import win.sightclient.ui.altmanager.Alt;
import win.sightclient.ui.altmanager.AltManager;
import win.sightclient.utils.Utils;
import win.sightclient.utils.FileUtils;

public class GuiAltManager extends GuiScreen {

	private String status = "";
	private GuiTextField email;
	private GuiTextField pass;
	
	@Override
	public void initGui() {
		this.refresh();
		Keyboard.enableRepeatEvents(true);
	}
	
	private void refresh() {
		this.buttonList.clear();
		this.offset = 0;
		
		for (int i = 0; i < AltManager.getAlts().size(); i++) {
			this.buttonList.add(new AltButton(this.width / 2 - 100, ((i + 1) * 21) + 20, i, this));
		}
		
		email = new GuiTextField(0, mc.fontRendererObj, 5, this.height - 96, 100, 20);
		pass = new GuiTextField(0, mc.fontRendererObj, 5, this.height - 72, 100, 20);
		this.buttonList.add(new GuiButton(69, 3, this.height - 49, 51, 20, "Add"));
		this.buttonList.add(new GuiButton(692, 56, this.height - 49, 51, 20, "Log In"));
		this.buttonList.add(new GuiButton(6969, this.width - 105, this.height - 28, 100, 20, "TheAltening"));
		this.buttonList.add(new GuiButton(696969, this.width - 105, this.height - 50, 100, 20, "S-X Alts"));
		this.buttonList.add(new GuiButton(6969694, this.width - 105, this.height - 72, 100, 20, "Hydrogen"));
		this.buttonList.add(new GuiButton(69696969, 3, this.height - 28, 104, 20, "Import User:Pass"));
		this.buttonList.add(new GuiButton(1, 2, 30, 75, 20, "Remove"));
		this.buttonList.add(new GuiButton(2, 2, 55, 75, 20, "Import"));
		this.buttonList.add(new GuiButton(3, 2, 80, 75, 20, "Clear"));
	}
	
	private int offset;
	
	@Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (Mouse.hasWheel()) {
            final int wheel = Mouse.getDWheel();
            if (wheel < 0) {
                this.offset += 21;
                if (this.offset < 0) {
                    this.offset = 0;
                }
            }
            else if (wheel > 0) {
                this.offset -= 21;
                if (this.offset < 0) {
                    this.offset = 0;
                }
            }
        }
		this.drawDefaultBackground();
		mc.fontRendererObj.drawStringWithShadow(Minecraft.getMinecraft().getSession().getUsername(), 10, 10, new Color(100, 100, 100).getRGB());
		Gui.drawRect(this.width / 2 - 125, 0, this.width / 2 + 125, this.height, new Color(255, 255, 255, 35).getRGB());
		mc.fontRendererObj.drawStringWithShadow("Alt Manager", this.width / 2 - (mc.fontRendererObj.getStringWidth("Alt Manager") / 2), 12, -1);
		mc.fontRendererObj.drawStringWithShadow(this.status, this.width / 2 - (mc.fontRendererObj.getStringWidth(this.status) / 2), 23, -1);
        for (int i = 0; i < this.buttonList.size(); ++i) {
        	if (this.buttonList.get(i) instanceof AltButton) {
        		AltButton ab = (AltButton)this.buttonList.get(i);
        		ab.yPosition = ab.getOrigY() - offset;
        	}
            ((GuiButton)this.buttonList.get(i)).drawButton(this.mc, mouseX, mouseY);
        }
		email.drawTextBox();
		pass.drawTextBox();
		if (this.email.getText().isEmpty()) {
			mc.fontRendererObj.drawString("Email", 10, this.height - 90, new Color(100, 100, 100).getRGB());
		}
		
		if (this.pass.getText().isEmpty()) {
			mc.fontRendererObj.drawString("Password", 10, this.height - 66, new Color(100, 100, 100).getRGB());
		}
    }
	
	public void login(final Alt alt) {
		if (alt == null) {
			return;
		}
		Thread login = new Thread(new Runnable() {

			@Override
			public void run() {
				Sight.instance.serviceSwitcher.switchToService(AlteningServiceType.MOJANG);
				status = EnumChatFormatting.YELLOW + "Logging in...";
		        YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
		        YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication)service.createUserAuthentication(Agent.MINECRAFT);
		        auth.setUsername(alt.getUser());
		        auth.setPassword(alt.getPass());
		        try {
		            auth.logIn();
		            Minecraft.getMinecraft().session = new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(), auth.getAuthenticatedToken(), "mojang");
		            status = EnumChatFormatting.GREEN + "Logged in as " + auth.getSelectedProfile().getName() + ".";
		            Sight.instance.connection.printUsername();
		        }
		        catch (AuthenticationException localAuthenticationException) {
		            localAuthenticationException.printStackTrace();
		            status = EnumChatFormatting.RED + "Login failed.";
		        }
			}
			
		});
		login.start();
	}
	
    @Override
    protected void keyTyped(final char character, final int key) throws IOException {
        try {
            super.keyTyped(character, key);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        if (character == '\t' && (this.email.isFocused() || this.pass.isFocused())) {
            this.email.setFocused(!this.email.isFocused());
            this.pass.setFocused(!this.pass.isFocused());
        }
        if (character == '\r') {
            this.actionPerformed(this.buttonList.get(0));
        }
        this.email.textboxKeyTyped(character, key);
        this.pass.textboxKeyTyped(character, key);
    }
    
    public boolean isAltInArea(final int y) {
        return y <= this.height && y > 20;
    }
	
	public void setSelected(int val) {
		for (GuiButton g : this.buttonList) {
			if (g instanceof AltButton) {
				AltButton ab = (AltButton)g;
				ab.setSelected(false);
			}
		}
		
		try {
			((AltButton)this.buttonList.get(val)).setSelected(true);
		} catch (Exception e) {}
	}
	
    @Override
    protected void mouseClicked(final int x, final int y, final int button) {
        try {
            super.mouseClicked(x, y, button);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        this.email.mouseClicked(x, y, button);
        this.pass.mouseClicked(x, y, button);
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    public void updateScreen() {
        this.email.updateCursorCounter();
        this.pass.updateCursorCounter();
    }
    
    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
    	if (button.id == 69 && !this.email.getText().isEmpty() && !this.pass.getText().isEmpty()) {
    		try {
    			String email = this.email.getText();
    			String pass = this.pass.getText();
    			AltManager.addAlt(new Alt(email, pass));
    			Sight.instance.fileManager.saveAltsFile();
    			this.refresh();
    		} catch (Exception e) {}
    	} else if (button.id == 1) {
    		ArrayList<GuiButton> buttons = new ArrayList<GuiButton>(this.buttonList);
    		for (GuiButton g : buttons) {
    			if (g instanceof AltButton) {
    				AltButton ab = (AltButton)g;
    				if (ab.isSelected()) {
    					AltManager.removeAlt(ab.getAlt());
    				}
    			}
    			this.refresh();
    		}
    		this.status = "";
    	} else if (button.id == 6969) {
    		mc.displayGuiScreen(new GuiTheAltening(this));
    	} else if (button.id == 696969) {
    		mc.displayGuiScreen(new GuiSXAlts(this));
    	} else if (button.id == 2) {
        	Thread fileChoose = new Thread(new Runnable() {

				@Override
				public void run() {
	            	JFileChooser fc = new JFileChooser("Import Alts");
	            	int returnVal = fc.showOpenDialog(null);
	            	
	                if (returnVal == JFileChooser.APPROVE_OPTION) {
	                    File file = fc.getSelectedFile();
	                    if (file.exists()) {
	                    	ArrayList<String> lines = FileUtils.getLines(file);
	                    	for (String str : lines) {
	                    		if (str.contains(":") && !str.startsWith(":") && !str.endsWith(":")) {
	                    			AltManager.addAlt(new Alt(str.split(":")[0], str.substring(str.indexOf(":") + 1)));
	                    		}
	                    	}
	                    	refresh();
	                    }
	                }
				}
        		
        	});
        	fileChoose.start();
    	} else if (button.id == 3) {
    		AltManager.getAlts().clear();
    		this.refresh();
    		Sight.instance.fileManager.saveDataFile();
    	} else if (button.id == 69696969) {
        	try {
            	if (Utils.getLatestPaste() != null) {
	                String result = Utils.getLatestPaste();
	                if(result.contains(":") && !result.startsWith(":") && !result.endsWith(":")) {
	                    this.email.setText(result.split(":")[0]);
	                    this.pass.setText(result.split(":")[1]);
	                }
            	}
        	} catch (Exception e) {}
    	} else if (button.id == 692) {
    		if (!this.email.getText().isEmpty() && !this.pass.getText().isEmpty()) {
    			this.login(new Alt(this.email.getText(), this.pass.getText()));
    		}
    	} else if (button.id == 6969694) {
			try {
	    		  Desktop desktop = java.awt.Desktop.getDesktop();
	    		  URI oURL = new URI("https://hydrogen.best/");
	    		  desktop.browse(oURL);
			} catch (URISyntaxException e) {}
    	}
    	Sight.instance.fileManager.saveAltsFile();
    }
}
