package net.labymod.spm;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class GuiSPM extends GuiScreen
{
    private Scrollbar scrollbar;
    private GuiScreen lastScreen;
    private GuiButton doneButton;
    private GuiButton addButton;
    private GuiTextField addField;
    private GuiButton deleteButton;
    private GuiButton loadButton;
    private GuiButton overwriteButton;
    private long lastClick = 0L;
    private String message = null;
    private long messageDisplayTime = 0L;
    private String hoverMessage = null;
    private File selectedProfile;
    private SPMSettings storedSettings;

    public GuiSPM(GuiScreen lastScreen)
    {
        this.lastScreen = lastScreen;
        this.scrollbar = new Scrollbar(10);
        ProfileManager.getProfileManager().loadProfiles();
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.add(this.doneButton = new GuiButton(0, (this.width - this.width / 4) / 2 + this.width / 4 - 100, this.height - 30, "Done"));
        this.buttonList.add(this.addButton = new GuiButton(1, (this.width - 5) / 4 - 20, this.height - 30, 23, 20, "+"));
        this.buttonList.add(this.loadButton = new GuiButton(2, this.width - 70, 49, 60, 20, "Load"));
        this.buttonList.add(this.overwriteButton = new GuiButton(3, this.width - 70, 70, 60, 20, "Overwrite"));
        this.buttonList.add(this.deleteButton = new GuiButton(4, this.width - 70, 91, 60, 20, "Delete"));
        this.addField = new GuiTextField(0, this.mc.fontRendererObj, 6, this.height - 29, (this.width - 5) / 4 - 30, 18);
        this.addField.setMaxStringLength(25);
        this.scrollbar.setPosition(this.width / 4 - 5, 5, this.width / 4, this.height - 35);
        this.scrollbar.setSpeed(10);
        this.scrollbar.update(ProfileManager.getProfileManager().getProfiles().size());
        super.initGui();
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button.id == 0)
        {
            Minecraft.getMinecraft().displayGuiScreen(this.lastScreen);
        }

        String s = "\u00a7a";
        String s1 = "\u00a7c";

        if (button.id == 1)
        {
            if (ProfileManager.getProfileManager().saveProfileAs(this.addField.getText()))
            {
                this.addField.setText("");
                this.displayMessage(s + "Profile successfully created");
            }
            else
            {
                this.displayMessage(s1 + "Failed to create profile");
            }
        }

        if (button.id == 2 && this.selectedProfile != null)
        {
            if (ProfileManager.getProfileManager().loadProfile(this.selectedProfile))
            {
                button.enabled = false;
                this.displayMessage(s + "Profile successfully loaded");
            }
            else
            {
                this.displayMessage(s1 + "Failed to load profile");
            }
        }

        if (button.id == 3 && this.selectedProfile != null)
        {
            if (ProfileManager.getProfileManager().saveProfileAs(this.selectedProfile))
            {
                button.enabled = false;
                this.displayMessage(s + "Profile successfully overwritten");
                this.selectProfile(this.selectedProfile);
            }
            else
            {
                this.displayMessage(s1 + "Failed to overwrite profile");
            }
        }

        if (button.id == 4 && this.selectedProfile != null)
        {
            if (ProfileManager.getProfileManager().deleteProfile(this.selectedProfile))
            {
                this.selectedProfile = null;
                this.displayMessage(s + "Profile successfully deleted");
            }
            else
            {
                this.displayMessage(s1 + "Failed to delete profile");
            }
        }

        super.actionPerformed(button);
    }

    private void displayMessage(String message)
    {
        this.message = message;
        this.messageDisplayTime = System.currentTimeMillis() + 2000L;
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawBackground(0);
        DrawUtils.drawBackground(5, 5, this.width / 4, this.height - 35);
        DrawUtils.drawBackground(this.width / 4 + 5, 5, this.width - 5, this.height - 35);
        int i = 10 + this.scrollbar.getScrollY();

        for (File file1 : ProfileManager.getProfileManager().getProfiles())
        {
            if (this.selectedProfile == file1)
            {
                drawRect(5, i - 1, this.width / 4, i + 9, Integer.MAX_VALUE);
            }

            this.drawString(this.mc.fontRendererObj, file1.getName().replace(".txt", ""), 10, i, Color.WHITE.getRGB());
            i += 10;
        }

        DrawUtils.overlayBackground(0, this.height - 35, this.width / 4, this.height);
        DrawUtils.overlayBackground(0, 0, this.width / 4, 5);
        this.addButton.enabled = !this.addField.getText().replaceAll(" ", "").isEmpty() && this.isAlphaNumeric(this.addField.getText());
        this.addField.drawTextBox();
        this.scrollbar.update(ProfileManager.getProfileManager().getProfiles().size());
        this.scrollbar.draw();
        this.loadButton.visible = this.selectedProfile != null;
        this.overwriteButton.visible = this.selectedProfile != null;
        this.deleteButton.visible = this.selectedProfile != null;

        if (this.selectedProfile != null)
        {
            double d0 = 2.0D;
            GlStateManager.pushMatrix();
            GlStateManager.scale(d0, d0, d0);
            String s = this.selectedProfile.getName().replaceAll(".txt", "");
            this.drawCenteredString(this.mc.fontRendererObj, s, (int)((double)((this.width - this.width / 4) / 2 + this.width / 4) / d0), (int)(17.0D / d0), Color.WHITE.getRGB());
            GlStateManager.popMatrix();
        }

        if (this.storedSettings != null && this.selectedProfile != null)
        {
            this.drawKeyBoard(mouseX, mouseY);
        }

        if (this.message != null && this.messageDisplayTime > System.currentTimeMillis())
        {
            this.drawString(this.mc.fontRendererObj, this.message, this.width / 4 + 10, this.height - 47, Color.WHITE.getRGB());
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void drawKeyBoard(int mouseX, int mouseY)
    {
        this.hoverMessage = null;
        this.drawKey(this.storedSettings.keyBindForward, 1, 2, mouseX, mouseY);
        this.drawKey(this.storedSettings.keyBindBack, 1, 3, mouseX, mouseY);
        this.drawKey(this.storedSettings.keyBindLeft, 0, 3, mouseX, mouseY);
        this.drawKey(this.storedSettings.keyBindRight, 2, 3, mouseX, mouseY);
        this.drawKey(this.storedSettings.keyBindAttack, 10, 0, mouseX, mouseY);
        this.drawKey(this.storedSettings.keyBindPickBlock, 11, 0, mouseX, mouseY);
        this.drawKey(this.storedSettings.keyBindUseItem, 12, 0, mouseX, mouseY);
        this.drawKey(this.storedSettings.keyBindInventory, 4, 2, mouseX, mouseY);
        this.drawKey(this.storedSettings.keyBindDrop, 4, 3, mouseX, mouseY);
        this.drawKey(this.storedSettings.keyBindSprint, 4, 4, mouseX, mouseY);
        this.drawKey(this.storedSettings.keyBindSneak, 4, 5, mouseX, mouseY);
        this.drawKey(this.storedSettings.keyBindJump, 4, 6, mouseX, mouseY);
        int i = 0;

        for (SPMBinding spmbinding : this.storedSettings.keyBindsHotbar)
        {
            this.drawKey(spmbinding, i, 0, mouseX, mouseY);
            ++i;
        }

        if (this.hoverMessage != null)
        {
            List list = new ArrayList();
            list.add(I18n.format(this.hoverMessage, new Object[0]));
            this.drawHoveringText(list, mouseX, mouseY);
            GlStateManager.disableLighting();
        }

        drawRect(this.width / 4 + 20 + 170, 67, this.width / 4 + 20 + 221 - 1, 118, Integer.MAX_VALUE);
    }

    private void drawKey(SPMBinding keyBind, int x, int y, int mouseX, int mouseY)
    {
        this.drawKey(I18n.format(keyBind.getKeyDescription(), new Object[0]), keyBind.getKeyCode(), x, y, mouseX, mouseY);
    }

    private void drawKey(String name, int keyCode, int x, int y, int mouseX, int mouseY)
    {
        int i = this.width / 4 + 20 + x * 17;
        int j = 50 + y * 17;
        drawRect(i, j, i + 16, j + 16, Integer.MAX_VALUE);

        if (keyCode >= 0 && Keyboard.isKeyDown(keyCode) || keyCode < 0 && Mouse.isButtonDown(keyCode + 100))
        {
            drawRect(i, j, i + 16, j + 16, Integer.MAX_VALUE);
        }

        String s = null;

        if (keyCode >= 0)
        {
            s = Keyboard.getKeyName(keyCode);
        }

        if (s == null)
        {
            int k = keyCode + 100;

            if (k == 0)
            {
                s = "<";
            }

            if (k == 1)
            {
                s = ">";
            }

            if (k == 2)
            {
                s = "|";
            }

            if (k >= 3)
            {
                s = "B" + (k + 1);
            }
        }

        if (s != null && s.length() > 2)
        {
            s = s.substring(0, 2);
        }

        this.drawCenteredString(this.mc.fontRendererObj, s, i + 8, j + 4, Color.WHITE.getRGB());
        String s1 = I18n.format(name, new Object[0]);

        if (mouseX > i && mouseX < i + 16 && mouseY > j && mouseY < j + 16)
        {
            this.hoverMessage = s1;
        }

        if (x == 4 && y > 1)
        {
            this.drawString(this.mc.fontRendererObj, s1, i + 19, j + 4, Color.WHITE.getRGB());
        }
    }

    private boolean isAlphaNumeric(String s)
    {
        String s1 = "^[a-zA-Z0-9 _-]*$";
        return s.matches(s1);
    }

    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        this.addField.mouseClicked(mouseX, mouseY, mouseButton);
        this.scrollbar.mouseAction(mouseX, mouseY, false);
        int i = 10 + this.scrollbar.getScrollY();

        for (File file1 : ProfileManager.getProfileManager().getProfiles())
        {
            if (mouseX > 5 && mouseX < this.width / 4 && mouseY > i && mouseY < i + 10)
            {
                this.selectProfile(file1);
            }

            i += 10;
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    private void selectProfile(File profile)
    {
        this.selectedProfile = profile;
        this.loadButton.enabled = true;
        this.overwriteButton.enabled = true;
        this.deleteButton.enabled = true;

        if (this.lastClick > System.currentTimeMillis())
        {
            try
            {
                this.actionPerformed(this.loadButton);
            }
            catch (IOException ioexception)
            {
                ioexception.printStackTrace();
            }

            this.lastClick = System.currentTimeMillis();
        }
        else
        {
            this.lastClick = System.currentTimeMillis() + 200L;
        }

        this.storedSettings = new SPMSettings(this.selectedProfile);
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        if (keyCode == 1)
        {
            ;
        }

        if (this.addField.textboxKeyTyped(typedChar, keyCode))
        {
            ;
        }

        if (keyCode == 28)
        {
            this.actionPerformed(this.addButton);
        }

        if (keyCode == 211 && this.selectedProfile != null)
        {
            this.actionPerformed(this.deleteButton);
        }

        if (keyCode == 28 && this.selectedProfile != null && !this.addField.isFocused())
        {
            this.actionPerformed(this.loadButton);
        }

        super.keyTyped(typedChar, keyCode);
    }

    /**
     * Called when a mouse button is pressed and the mouse is moved around. Parameters are : mouseX, mouseY,
     * lastButtonClicked & timeSinceMouseClick.
     */
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick)
    {
        this.scrollbar.mouseAction(mouseX, mouseY, true);
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }

    /**
     * Handles mouse input.
     */
    public void handleMouseInput() throws IOException
    {
        this.scrollbar.mouseInput();
        super.handleMouseInput();
    }
}
