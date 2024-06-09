package com.kilo.alt;

import com.kilo.alt.handler.AccountManager;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.Session;

import java.io.IOException;

public class GuiAccountManagement extends GuiScreen {

    private final AccountManager manager = new AccountManager(); 

	private GuiScreen lastScreen;
	private GuiTextField usernameBox, passwordBox;
    private String title;
    private int mode = 0, slot = -1;

    public GuiAccountManagement(GuiScreen lastScreen, int mode, int slot) {
    	this.lastScreen = lastScreen;
    	this.mode = mode;
    	this.slot = slot;
    	switch(mode){
			case 0:
				this.title = "Add Account";
				break;
			case 1:
				this.title = "Delete Account";
				break;
			case 2:
				this.title = "Edit Account";
				break;
			case 3:
				this.title = "Direct Login";
				break;
    	}
    }
    
    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui() {
        this.usernameBox = new GuiTextField(0, this.fontRendererObj, this.width / 2 - 100, this.height / 2 - 40, 200, 20);
        this.usernameBox.setFocused(true);
        this.usernameBox.setMaxStringLength(1500);
        this.passwordBox = new GuiPasswordTextField(1, this.fontRendererObj, this.width / 2 - 100, this.height / 2, 200, 20);
        this.passwordBox.setMaxStringLength(1500);
        this.passwordBox.setText("");
        this.buttonList.add(new GuiButton(0, this.width / 2 - 154, this.height - 28, 150, 20, "Confirm"));
        this.buttonList.add(new GuiButton(7, this.width / 2 + 4, this.height - 28, 150, 20, "Back"));
        if(this.mode == 1){
    		this.usernameBox.setVisible(false);
    		this.passwordBox.setVisible(false);
        }
        if(this.mode == 2){
        	this.usernameBox.setText(manager.getAccounts().get(slot).getUsername());
        	this.passwordBox.setText(manager.getAccounts().get(slot).getPassword());
        }
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        this.usernameBox.updateCursorCounter();
        this.passwordBox.updateCursorCounter();
        if(mode != 1)
        	this.buttonList.get(0).enabled = !this.usernameBox.getText().isEmpty();
    }
    
    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        this.usernameBox.textboxKeyTyped(typedChar, keyCode);
        this.passwordBox.textboxKeyTyped(typedChar, keyCode);

        if (keyCode == 15) {
            this.usernameBox.setFocused(!this.usernameBox.isFocused());
            this.passwordBox.setFocused(!this.passwordBox.isFocused());
        }

        if (keyCode == 28 || keyCode == 156) {
            this.actionPerformed((GuiButton) this.buttonList.get(0));
        }
    }
    
    /**
     * Handles mouse input.
     */
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
    }

    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.usernameBox.mouseClicked(mouseX, mouseY, mouseButton);
        this.passwordBox.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException{
    	switch(button.id) {
	    	case 0:
	    		if(this.mode == 3){
	    			String lastUsername = mc.getSession().getUsername();
	        		if(this.passwordBox.getText().equals(""))
	        			mc.setSession(new Session(this.usernameBox.getText(), this.usernameBox.getText(), "0", "legacy"));
	        		else
	        			manager.YggdrasilAuthenticator(this.usernameBox.getText(), this.passwordBox.getText());
	    			if(!lastUsername.equals(mc.getSession().getUsername()))
	    				this.mc.displayGuiScreen(lastScreen);
	    		}
	    		else{
		    		if(this.mode == 0)
	                    manager.addAccount(new Session("", "", "", "mojang"), this.usernameBox.getText(), this.usernameBox.getText(), this.passwordBox.getText());
		    		else if(this.mode == 1)
	                    manager.removeAccount(this.slot);
		    		else if(this.mode == 2)
	                    manager.setAccount(this.slot, null, this.usernameBox.getText(), this.usernameBox.getText(), this.passwordBox.getText());
		    		this.mc.displayGuiScreen(lastScreen);
	    		}
	    		break;
	    	case 7:
	    		//Client.getInstance().getAccountManager().reloadAccounts();
	    		this.mc.displayGuiScreen(lastScreen);
	    		break;
    	}
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.usernameBox.drawTextBox();
        this.passwordBox.drawTextBox();
        this.drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 8, 16777215);
    //    this.drawCenteredString(this.fontRendererObj, ChatFormatting.GRAY + "(" + manager.getAccounts().size() + ")", this.width / 2, 20, 16777215);
        this.drawString(this.fontRendererObj, ChatFormatting.GRAY + "Username: " + mc.getSession().getUsername(), 2, 2, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
