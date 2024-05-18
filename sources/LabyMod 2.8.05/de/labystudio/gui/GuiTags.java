package de.labystudio.gui;

import de.labystudio.gui.extras.ModGuiTextField;
import de.labystudio.labymod.LabyMod;
import de.labystudio.language.L;
import de.labystudio.utils.Color;
import de.labystudio.utils.DrawUtils;
import de.labystudio.utils.FriendsLoader;
import de.labystudio.utils.Scrollbar;
import java.io.IOException;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class GuiTags extends GuiMenuScreen
{
    DrawUtils draw;
    Scrollbar scrollbar;
    String selectedFriend = "";
    private GuiButton btnAddFriend;
    private GuiButton btnEditFriend;
    private GuiButton btnDeleteFriend;
    boolean editor = false;
    ModGuiTextField editName = new ModGuiTextField(-1, (FontRenderer)null, 0, 0, 0, 0);
    ModGuiTextField editNick = new ModGuiTextField(-1, (FontRenderer)null, 0, 0, 0, 0);
    GuiButton done;
    boolean focus = false;

    public GuiTags()
    {
        super((GuiScreen)null);
        this.childScreen = this;
        this.draw = LabyMod.getInstance().draw;
        this.editor = false;
        this.id = "Tags";
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(this.btnAddFriend = new GuiButton(0, this.width / 2 - 154, this.height - 26, 100, 20, L.f("gui_tags_addfriend", new Object[0])));
        this.buttonList.add(this.btnEditFriend = new GuiButton(1, this.width / 2 - 50, this.height - 26, 100, 20, L.f("gui_tags_editfriend", new Object[0])));
        this.buttonList.add(this.btnDeleteFriend = new GuiButton(2, this.width / 2 + 4 + 50, this.height - 26, 100, 20, L.f("gui_tags_deletefriend", new Object[0])));
        super.initGui();
        this.initEditor(this.selectedFriend);

        if (FriendsLoader.friends == null)
        {
            this.scrollbar = new Scrollbar(0);
        }
        else
        {
            this.scrollbar = new Scrollbar(FriendsLoader.friends.size());
        }

        this.scrollbar.setPosition(this.width / 2 + 154, 40, this.width / 2 + 160, this.height - 40);
        this.scrollbar.update(35);
    }

    private void drawFriends()
    {
        int i = 0;

        if (FriendsLoader.friends != null && !FriendsLoader.friends.isEmpty())
        {
            for (String s : FriendsLoader.friends.keySet())
            {
                String s1 = (String)FriendsLoader.friends.get(s);

                if (this.selectedFriend.equalsIgnoreCase(s))
                {
                    DrawUtils drawutils = this.draw;
                    DrawUtils.drawRect(this.width / 2 - 151, 50 + this.scrollbar.getScrollY() + i - 4, this.width / 2 + 158, 50 + this.scrollbar.getScrollY() + i + 30, 632207020);
                }

                GlStateManager.color(1.0F, 1.0F, 1.0F);
                GL11.glColor3f(1.0F, 1.0F, 1.0F);
                LabyMod.getInstance().textureManager.drawPlayerHead(s, (double)(this.width / 2 - 150), (double)(50 + this.scrollbar.getScrollY() + i), 1.0D);
                this.draw.drawString(Color.cl("l") + s, (double)(this.width / 2 - 110), (double)(50 + this.scrollbar.getScrollY() + i));

                if (s1.isEmpty())
                {
                    this.draw.drawString(Color.cl("c") + L.f("gui_tags_nonickname", new Object[0]) + Color.cl("r"), (double)(this.width / 2 - 110), (double)(25 + this.scrollbar.getScrollY() + i + 35));
                }
                else
                {
                    this.draw.drawString(Color.cl("e") + L.f("gui_tags_nickname", new Object[0]) + ": " + Color.cl("r") + s1.replace("&", Color.c) + Color.cl("r"), (double)(this.width / 2 - 110), (double)(25 + this.scrollbar.getScrollY() + i + 35));
                }

                i += 35;
            }
        }
    }

    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.scrollbar.mouseAction(mouseX, mouseY, false);

        if (this.editor)
        {
            this.editName.mouseClicked(mouseX, mouseY, mouseButton);
            this.editNick.mouseClicked(mouseX, mouseY, mouseButton);
        }
        else
        {
            int i = 0;

            for (String s : FriendsLoader.friends.keySet())
            {
                String s1 = (String)FriendsLoader.friends.get(s);

                if (mouseX > this.width / 2 - 151 && mouseX < this.width / 2 + 160 && mouseY > 50 + this.scrollbar.getScrollY() + i - 4 && mouseY < 50 + this.scrollbar.getScrollY() + i + 31)
                {
                    this.selectedFriend = s;
                    return;
                }

                i += 35;
            }
        }
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

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    public void actionPerformed(GuiButton button) throws IOException
    {
        super.actionPermformed(button);

        if (button.id == 0)
        {
            this.selectedFriend = "";
            this.edit("");
        }

        if (button.id == 1)
        {
            this.edit(this.selectedFriend);
        }

        if (button.id == 2)
        {
            FriendsLoader.friends.remove(this.selectedFriend);
            FriendsLoader.saveFriends();
            this.initGui();
            this.selectedFriend = "";
        }

        if (button.id == 3)
        {
            if (!this.selectedFriend.isEmpty() && !this.editName.getText().equals(this.selectedFriend))
            {
                FriendsLoader.friends.remove(this.selectedFriend);
            }

            this.selectedFriend = this.editName.getText();
            FriendsLoader.friends.put(this.editName.getText(), this.editNick.getText());
            this.editor = false;
            FriendsLoader.saveFriends();
            this.initGui();
        }

        if (button.id == 4)
        {
            this.editor = false;
            this.initGui();
        }
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        this.editName.textboxKeyTyped(typedChar, keyCode);
        this.editNick.textboxKeyTyped(typedChar, keyCode);

        if (this.editor && this.done.enabled && (keyCode == 28 || keyCode == 156))
        {
            this.actionPerformed(this.done);
        }

        super.keyTyped(typedChar, keyCode);
    }

    private void edit(String name)
    {
        this.editor = true;
        this.selectedFriend = name;
        this.focus = false;
        this.initEditor(name);
    }

    private void initEditor(String name)
    {
        if (this.editor)
        {
            for (int i = 0; i <= this.buttonList.size() - 1; ++i)
            {
                GuiButton guibutton = (GuiButton)this.buttonList.get(i);
                guibutton.visible = false;
            }

            this.editName = new ModGuiTextField(-1, this.draw.fontRenderer, this.width / 2 - 100, this.height / 2 - 50, 200, 20);
            this.editName.setMaxStringLength(16);
            this.editName.setBlacklistWord(" ");
            this.editName.setText(name);
            this.editNick = new ModGuiTextField(-1, this.draw.fontRenderer, this.width / 2 - 100, this.height / 2, 200, 20);
            this.editNick.setMaxStringLength(50);

            if (FriendsLoader.friends.containsKey(name))
            {
                this.editNick.setText((String)FriendsLoader.friends.get(name));
            }

            GuiButton guibutton1 = new GuiButton(4, this.width / 2 - 100, this.height / 2 + 53, L.f("button_cancel", new Object[0]));
            this.buttonList.add(guibutton1);
            this.done = new GuiButton(3, this.width / 2 - 100, this.height / 2 + 28, L.f("button_done", new Object[0]));
            this.buttonList.add(this.done);
            this.btnDeleteFriend.visible = false;
            this.btnEditFriend.visible = false;
            this.btnAddFriend.visible = false;
        }
    }

    private void drawEditor()
    {
        this.draw.drawString(L.f("gui_tags_playername", new Object[0]) + ":", (double)(this.width / 2 - 100), (double)(this.height / 2 - 63));
        this.draw.drawString(L.f("gui_tags_nickname", new Object[0]) + ":", (double)(this.width / 2 - 100), (double)(this.height / 2 - 13));

        if (this.editName != null && this.editNick != null)
        {
            this.editName.drawTextBox();
            this.editNick.drawTextBox();

            if (this.done != null)
            {
                this.done.enabled = !this.editName.getText().replace(" ", "").isEmpty() && (!FriendsLoader.friends.containsKey(this.editName.getText()) || this.editName.getText().equals(this.selectedFriend));
            }
        }

        if (!this.focus)
        {
            this.editName.setFocused(true);
            this.focus = true;
        }
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        if (this.editor)
        {
            this.drawBackground(0);
            this.drawEditor();
            super.drawScreen(mouseX, mouseY, partialTicks);
        }
        else
        {
            if (LabyMod.getInstance().isInGame())
            {
                GlStateManager.enableBlend();
                this.draw.drawTransparentBackground(0, 32, this.width, this.height - 33);
            }
            else
            {
                this.drawDefaultBackground();
                this.draw.drawChatBackground(0, 32, this.width, this.height - 33);
            }

            this.drawFriends();
            GlStateManager.disableBlend();
            this.draw.overlayBackground(0, 32);
            this.draw.overlayBackground(this.height - 33, this.height);
            this.scrollbar.draw();
            this.btnEditFriend.enabled = !this.selectedFriend.isEmpty();
            this.btnDeleteFriend.enabled = !this.selectedFriend.isEmpty();
            super.drawScreen(mouseX, mouseY, partialTicks);
        }
    }
}
