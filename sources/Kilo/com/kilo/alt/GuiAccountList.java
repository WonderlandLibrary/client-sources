package com.kilo.alt;

import com.kilo.alt.handler.AccountManager;
import com.kilo.alt.handler.AccountsFile;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.Session;

import java.io.File;
import java.io.IOException;

public class GuiAccountList extends GuiScreen {
    private GuiScreen lastScreen;

    private final String title = "Accounts";
    private List list;
    private GuiTextField searchBox;

    public GuiAccountList(GuiScreen lastScreen) {
        this.lastScreen = lastScreen;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui() {
        this.buttonList.add(new GuiButton(0, this.width / 2 - 154, this.height - 52, 72, 20, "Login"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 76, this.height - 52, 72, 20, "Direct"));
        this.buttonList.add(new GuiButton(2, this.width / 2 + 4, this.height - 52, 72, 20, "Add"));
        this.buttonList.add(new GuiButton(3, this.width / 2 + 82, this.height - 52, 72, 20, "Edit"));
        this.buttonList.add(new GuiButton(4, this.width / 2 - 154, this.height - 28, 72, 20, "Random"));
        this.buttonList.add(new GuiButton(5, this.width / 2 - 76, this.height - 28, 72, 20, "Folder"));
        this.buttonList.add(new GuiButton(6, this.width / 2 + 4, this.height - 28, 72, 20, "Delete"));
        this.buttonList.add(new GuiButton(7, this.width / 2 + 82, this.height - 28, 72, 20, "Back"));

        this.searchBox = new GuiTextField(8, this.fontRendererObj, this.width / 2 + 43, 10, 110, 20);
        this.searchBox.setMaxStringLength(100);

        this.list = new List();
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen() {
        this.searchBox.updateCursorCounter();
        AccountManager.instance.checkServers();
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        this.searchBox.textboxKeyTyped(typedChar, keyCode);

        boolean found = false;
        if (this.searchBox.getText().length() > 0) {
            int i = 0;
            for (com.kilo.alt.handler.AccountManager.AccountInfo accountInfo : AccountManager.instance.getAccounts()) {
                if (accountInfo.getAccountName().toLowerCase().contains(this.searchBox.getText().toLowerCase())) {
                    list.slot = i;
                    list.scrollBy(-list.getAmountScrolled() + 36 * list.slot);
                    found = true;
                    break;
                }
                i++;
            }
        }

        if (!found) {
            list.slot = -1;
            list.scrollBy(-list.getAmountScrolled() + 36 * list.slot);
        }
    }

    /**
     * Handles mouse input.
     */
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.list.handleMouseInput();
    }

    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.searchBox.mouseClicked(mouseX, mouseY, mouseButton);
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0:
                if (AccountManager.instance.getAccounts().get(list.slot).getPassword().equals(""))
                    mc.setSession(new Session(AccountManager.instance.getAccounts().get(list.slot).getUsername(), AccountManager.instance.getAccounts().get(list.slot).getUsername(), "0", "legacy"));
                else
                    AccountManager.instance.YggdrasilAuthenticator(list.slot);
                break;
            case 1:
                this.mc.displayGuiScreen(new GuiAccountManagement(this, 3, -1));
                break;
            case 2:
                this.mc.displayGuiScreen(new GuiAccountManagement(this, 0, this.list.slot));
                break;
            case 3:
                this.mc.displayGuiScreen(new GuiAccountManagement(this, 2, this.list.slot));
                break;
            case 4:
                int random = (int) (Math.random() * AccountManager.instance.getAccounts().size());
                list.slot = random;
                if (AccountManager.instance.getAccounts().get(random).getPassword().equals(""))
                	mc.setSession(new Session(AccountManager.instance.getAccounts().get(random).getUsername(), AccountManager.instance.getAccounts().get(random).getUsername(), "0", "legacy"));
                else
                    AccountManager.instance.YggdrasilAuthenticator(random);
                list.scrollBy(-list.getAmountScrolled() + 36 * list.slot);
                break;
            case 5:
                File file1 = new File(mc.mcDataDir.getAbsolutePath() + "\\Kilo");
                OpenGlHelper.openFile(file1);
                break;
            case 6:
                this.mc.displayGuiScreen(new GuiAccountManagement(this, 1, this.list.slot));
                break;
            case 7:
                this.mc.displayGuiScreen(this.lastScreen);
                break;
        }
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (this.list.slot == -1) {
            this.buttonList.get(0).enabled = false;
            this.buttonList.get(3).enabled = false;
            this.buttonList.get(6).enabled = false;
        } else {
            this.buttonList.get(0).enabled = true;
            this.buttonList.get(3).enabled = true;
            this.buttonList.get(6).enabled = true;
        }
        if (this.list.slot != -1) {
            AccountManager.instance.getAccounts().get(list.slot).setAccountName(mc.getSession().getUsername());
       //     AccountManager.instance.setLastAccount(mc.getSession().getUsername());
            AccountsFile.instance.save();
        }
        this.list.drawScreen(mouseX, mouseY, partialTicks);
        this.drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 8, 16777215);
        this.drawCenteredString(this.fontRendererObj, ChatFormatting.GRAY + "(" + AccountManager.instance.getAccounts().size() + ")", this.width / 2, 20, 16777215);
        this.drawString(this.fontRendererObj, ChatFormatting.GRAY + "Username: " + mc.getSession().getUsername(), 2, 2, 16777215);
        this.searchBox.drawTextBox();
        if (!this.searchBox.isFocused()) {
            this.searchBox.setText("");
            this.drawString(this.fontRendererObj, ChatFormatting.DARK_GRAY + "Search", this.width / 2 + 47, 16, 16777215);
        }
        AccountManager.instance.drawServers();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private class List extends GuiSlot {
        public int slot = -1;

        public List() {
            super(GuiAccountList.this.mc, GuiAccountList.this.width, GuiAccountList.this.height, 40, GuiAccountList.this.height - 60, 36);
        }

        protected int getSize() {
            return AccountManager.instance.getAccounts().size();
        }

        protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
            AccountManager.instance.setLastAccount(mc.getSession().getUsername());
            this.slot = slotIndex;
            if (isDoubleClick)
                if (AccountManager.instance.getAccounts().get(slotIndex).getPassword().equals(""))
                    mc.setSession(new Session(AccountManager.instance.getAccounts().get(slotIndex).getUsername(), AccountManager.instance.getAccounts().get(slotIndex).getUsername(), "0", "legacy"));
                else
                    AccountManager.instance.YggdrasilAuthenticator(slotIndex);
        }

        protected boolean isSelected(int slotIndex) {
            return slotIndex == this.slot;
        }

        protected void drawBackground() {
        }

        protected void drawSlot(int entryID, int x, int y, int p_180791_4_, int mouseXIn, int mouseYIn) {
            if (y > 10 && y < this.height - 60){
                AccountManager.instance.drawFace(AccountManager.instance.getAccounts().get(entryID).getAccountName(), x, y);
	            GuiAccountList.this.fontRendererObj.drawString(AccountManager.instance.getAccounts().get(entryID).getAccountName(), x + 35, y, 16777215);
	            if (AccountManager.instance.getAccounts().get(entryID).getError().equals("Invalid credentials. Invalid username or password.")) {
	                GuiAccountList.this.fontRendererObj.drawString("\2474Invalid username or password.", x + 35, y + 10, 16777215);
	            } else if (AccountManager.instance.getAccounts().get(entryID).getError().equals("Invalid credentials. Account migrated, use email as username.")) {
	                GuiAccountList.this.fontRendererObj.drawString("\2474This account has been migrated.", x + 35, y + 10, 16777215);
	            } else if (AccountManager.instance.getAccounts().get(entryID).getError().equals("Invalid credentials.")) {
	                GuiAccountList.this.fontRendererObj.drawString("\2474Login has been temporarily blocked.", x + 35, y + 10, 16777215);
	            }
            }
        }

    }

}

