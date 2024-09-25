package none.ui.login;

import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import none.Client;
import none.discordipc.DiscordRPCApi;

import org.lwjgl.input.Keyboard;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.thealtening.AltService;

public final class GuiAltLogin
extends GuiScreen {
	private static final AltService altService = new AltService();
    private PasswordField password;
    private final GuiScreen previousScreen;
    private AltLoginThread thread;
    private GuiTextField username;

    public GuiAltLogin(GuiScreen previousScreen) {
        this.previousScreen = previousScreen;
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
        	case 2: {
        		if (altService.getCurrentService() != AltService.EnumAltService.MOJANG) {
		            try {
		            	altService.switchService(AltService.EnumAltService.MOJANG);
		            } catch (final NoSuchFieldException | IllegalAccessException e) {
		            	e.printStackTrace();
		            	return;
		            }
		            Client.instance.notification.show(Client.notification("AL", "Mojang Service", 2));
		        }else {
		        	try {
		            	altService.switchService(AltService.EnumAltService.THEALTENING);
		            } catch (final NoSuchFieldException | IllegalAccessException e) {
		            	e.printStackTrace();
		            	return;
		            }
		        	Client.instance.notification.show(Client.notification("AL", "Altening Service", 2));
		        }
        		break;
        	}
            case 1: {
                this.mc.displayGuiScreen(this.previousScreen);
                break;
            }
            case 0: {
            	if (this.username.getText().isEmpty()) {
            		break;
            	}else {
            		if (this.password.getText().isEmpty()) {
            			String[] string = {"", ""};
            			for (int i = 0; i < this.username.getText().length(); i++) {
            				if (this.username.getText().charAt(i) == ':') {
            					String[] index = this.username.getText().split(":");
            					string = index;
            					break;
            				}
            				string = null;
            			}
            			if (string != null) {
            				this.thread = new AltLoginThread(string[0], string[1]);
                            this.thread.start();
            			}else {
            				this.thread = new AltLoginThread(this.username.getText(), this.password.getText());
                            this.thread.start();
            			}
            		}else {
            			this.thread = new AltLoginThread(this.username.getText(), this.password.getText());
                        this.thread.start();
            		}
            	}
                break;
            }
        }
    }

    @Override
    public void drawScreen(int x2, int y2, float z2) {
        this.drawDefaultBackground();
        this.username.drawTextBox();
        this.password.drawTextBox();
        this.drawCenteredString(this.mc.fontRendererObj, "Alt Login", width / 2, 20, -1);
        this.drawCenteredString(this.mc.fontRendererObj, this.thread == null ? (Object)(ChatFormatting.GRAY) + "Idle..." : this.thread.getStatus(), width / 2, 29, -1);
        if (this.username.getText().isEmpty()) {
            this.drawString(this.mc.fontRendererObj, "Username / E-Mail", width / 2 - 96, 66, -7829368);
        }
        if (this.password.getText().isEmpty()) {
            this.drawString(this.mc.fontRendererObj, "Password", width / 2 - 96, 106, -7829368);
        }

        Client.instance.CheckSession();
        Client.instance.notification.render();
        super.drawScreen(x2, y2, z2);
    }

    @Override
    public void initGui() {
        int var3 = height / 4 + 24;
        this.buttonList.add(new GuiButton(0, width / 2 - 100, var3 + 72 + 12, "Login"));
        this.buttonList.add(new GuiButton(1, width / 2 - 100, var3 + 72 + 12 + 24, "Back"));
        this.buttonList.add(new GuiButton(2, width / 2 - 100, var3 + 72 + 48 + 12, "Service"));
        this.username = new GuiTextField(var3, this.mc.fontRendererObj, width / 2 - 100, 60, 200, 20);
        this.password = new PasswordField(this.mc.fontRendererObj, width / 2 - 100, 100, 200, 20);
        this.username.setFocused(true);
        this.username.setMaxStringLength(256);
        Keyboard.enableRepeatEvents(true);
    }

    @Override
    protected void keyTyped(char character, int key) {
        try {
            super.keyTyped(character, key);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        if (character == '\t') {
            if (!this.username.isFocused() && !this.password.isFocused()) {
                this.username.setFocused(true);
            } else {
                this.username.setFocused(this.password.isFocused());
                this.password.setFocused(!this.username.isFocused());
            }
        }
        if (character == '\r') {
            this.actionPerformed((GuiButton)this.buttonList.get(0));
        }
        this.username.textboxKeyTyped(character, key);
        this.password.textboxKeyTyped(character, key);
    }

    @Override
    protected void mouseClicked(int x2, int y2, int button) {
        try {
            super.mouseClicked(x2, y2, button);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        this.username.mouseClicked(x2, y2, button);
        this.password.mouseClicked(x2, y2, button);
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    public void updateScreen() {
        this.username.updateCursorCounter();
        this.password.updateCursorCounter();
    }
}

