package cc.slack.ui.alt;

import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

import cc.slack.ui.alt.cookie.CookieUtil;
import cc.slack.ui.alt.cookie.LoginData;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.ChatFormatting;
import net.minecraft.util.Session;

public final class GuiAltLogin extends GuiScreen {
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
            case 1: {
                this.mc.displayGuiScreen(this.previousScreen);
                break;
            }
            case 2: {
			    new Thread(() -> {
			    	//status = ChatFormatting.YELLOW + "Waiting for login...";

			        try {
			            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			        } catch (Exception e) {
			            e.printStackTrace();
			            return;
			        }

			        JDialog dialog = new JDialog();
			        dialog.setAlwaysOnTop(true);

			        JFileChooser chooser = new JFileChooser();
			        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
			        chooser.setFileFilter(filter);

			        dialog.add(chooser);

			        int returnVal = chooser.showOpenDialog(null);
			        if (returnVal == JFileChooser.APPROVE_OPTION) {
			            try {
			                //status = ChatFormatting.YELLOW + "Logging in...";
			                LoginData loginData = CookieUtil.instance.loginWithCookie(chooser.getSelectedFile());

			                if (loginData == null) {
			                  //status = ChatFormatting.RED + "Failed to login with cookie!";
			                    return;
			                }

			                //status = ChatFormatting.GREEN + "Logged in to " + loginData.username;
			                this.mc.setSession(
			                        new Session(loginData.username, loginData.uuid, loginData.mcToken, "legacy"));
			            } catch (Exception e) {
			                throw new RuntimeException(e);
			            } finally {
			                dialog.dispose();
			            }
			        }
			    }).start();
				break;
            }
            case 0: {
                this.thread = new AltLoginThread(this.username.getText(), this.password.getText());
                this.thread.start();
            }
        }
    }

    @Override
    public void drawScreen(int x2, int y2, float z2) {
        this.drawDefaultBackground();
        this.username.drawTextBox();
        this.password.drawTextBox();
        this.drawCenteredString(Minecraft.getFontRenderer(), "Alt Login", width / 2, 20, -1);
        this.drawCenteredString(Minecraft.getFontRenderer(), this.thread == null ? ChatFormatting.GRAY + "Idle..." : this.thread.getStatus(), width / 2, 29, -1);
        if (this.username.getText().isEmpty()) {
            this.drawString(Minecraft.getFontRenderer(), "Username / E-Mail", width / 2 - 96, 66, -7829368);
        }
        if (this.password.getText().isEmpty()) {
            this.drawString(Minecraft.getFontRenderer(), "Password", width / 2 - 96, 106, -7829368);
        }
        super.drawScreen(x2, y2, z2);
    }

    @Override
    public void initGui() {
        int var3 = height / 4 + 24;
        this.buttonList.add(new GuiButton(0, width / 2 - 100, var3 + 72 + 12, "Login"));
//        this.buttonList.add(new GuiButton(2, width / 2 - 100, var3 + 72 + 12 + 24, "Cookie Login"));
        this.buttonList.add(new GuiButton(1, width / 2 - 100, var3 + 72 + 12 + 24, "Back"));
        this.username = new GuiTextField(var3, Minecraft.getFontRenderer(), width / 2 - 100, 60, 200, 20);
        this.password = new PasswordField(Minecraft.getFontRenderer(), width / 2 - 100, 100, 200, 20);
        this.username.setFocused(true);
        Keyboard.enableRepeatEvents(true);
    }

    @Override
    protected void keyTyped(char character, int key) {
        try {
            super.keyTyped(character, key);
        } catch (IOException e) {
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
            this.actionPerformed(this.buttonList.get(0));
        }
        
        this.username.textboxKeyTyped(character, key);
        this.password.textboxKeyTyped(character, key);
    }

    @Override
    protected void mouseClicked(int x2, int y2, int button) {
        try {
            super.mouseClicked(x2, y2, button);
        } catch (IOException e) {
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

