package de.labystudio.gui;

import de.labystudio.games.EnumGame;
import de.labystudio.games.Mario;
import de.labystudio.games.Snake;
import de.labystudio.labymod.LabyMod;
import de.labystudio.language.L;
import de.labystudio.utils.Color;
import de.labystudio.utils.DrawUtils;
import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class GuiGames extends GuiMenuScreen
{
    DrawUtils draw;
    EnumGame selectedGame = EnumGame.None;
    private GuiButton btnPlay;

    public GuiGames()
    {
        super((GuiScreen)null);
        this.childScreen = this;
        this.draw = LabyMod.getInstance().draw;
        this.id = "Games";
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(this.btnPlay = new GuiButton(0, this.width / 2 - 100, this.height - 70, 200, 20, "Play"));
        int i = 0;

        for (EnumGame enumgame : EnumGame.values())
        {
            if (enumgame != EnumGame.None)
            {
                GuiButton guibutton = new GuiButton(-1, 5 + i, this.height - 26, 80, 20, enumgame.name());
                guibutton.enabled = enumgame != this.selectedGame;
                this.buttonList.add(guibutton);
                i += 82;
            }
        }

        super.initGui();
    }

    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    public void actionPerformed(GuiButton button) throws IOException
    {
        if (button.id == -1)
        {
            for (EnumGame enumgame : EnumGame.values())
            {
                if (enumgame.name().equals(button.displayString))
                {
                    this.selectedGame = enumgame;
                    this.initGui();
                }
            }
        }

        if (button.id == 0)
        {
            if (this.selectedGame == EnumGame.Snake)
            {
                this.mc.displayGuiScreen(new Snake());
                return;
            }

            if (this.selectedGame == EnumGame.Mario)
            {
                this.mc.displayGuiScreen(new Mario());
                return;
            }
        }

        super.actionPermformed(button);
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        super.keyTyped(typedChar, keyCode);
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
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

        GlStateManager.disableBlend();
        this.draw.overlayBackground(0, 32);
        this.draw.overlayBackground(this.height - 33, this.height);

        if (this.selectedGame == EnumGame.None)
        {
            this.draw.drawCenteredString(Color.cl("c") + L.f("gui_games_nogames", new Object[0]), this.width / 2, this.height / 2);
        }

        if (this.selectedGame == EnumGame.Snake)
        {
            GL11.glPushMatrix();
            int i = 2;
            GL11.glScaled((double)i, (double)i, (double)i);
            this.draw.drawCenteredString(Color.cl("a") + "Snake", this.width / 2 / i, (this.height / 2 - 82) / i);
            GL11.glPopMatrix();
            int j = 60;
            this.draw.drawCenteredString("The purpose of this game is to gain as much", this.width / 2, this.height / 2 - j);
            j = j - 10;
            this.draw.drawCenteredString("score as possible by collecting the dots that are randomly spawning", this.width / 2, this.height / 2 - j);
            j = j - 10;
            this.draw.drawCenteredString("There are several types of dots which are differentiate by their color", this.width / 2, this.height / 2 - j);
            j = j - 10;
            this.draw.drawCenteredString("The " + Color.cl("4") + "red" + Color.cl("f") + " dot is the normal one, which increases your score by 10,", this.width / 2, this.height / 2 - j);
            j = j - 10;
            this.draw.drawCenteredString("all the other dots increase your score by 20", this.width / 2, this.height / 2 - j);
            j = j - 10;
            this.draw.drawCenteredString("The " + Color.cl("9") + "blue" + Color.cl("f") + " dot makes you slower, while the " + Color.cl("b") + "cyan" + Color.cl("f") + " dot makes you faster.", this.width / 2, this.height / 2 - j);
            j = j - 10;
            this.draw.drawCenteredString("The last one, the " + Color.cl("6") + "golden" + Color.cl("f") + " dot, increases the spawning", this.width / 2, this.height / 2 - j);
            j = j - 10;
            this.draw.drawCenteredString("rate of the dots by one for each golden dot you collect.", this.width / 2, this.height / 2 - j);
            j = j - 10;
            this.draw.drawCenteredString("But collecting these dots also lets you become longer, and once you hit", this.width / 2, this.height / 2 - j);
            j = j - 10;
            this.draw.drawCenteredString("yourself with your head, the game is over. You can also increase your", this.width / 2, this.height / 2 - j);
            j = j - 10;
            this.draw.drawCenteredString("speed by manually keeping the W, A, S, D or the arrow keys pressed.", this.width / 2, this.height / 2 - j);
        }
        else if (this.selectedGame == EnumGame.Mario)
        {
            GL11.glPushMatrix();
            int k = 2;
            GL11.glScaled((double)k, (double)k, (double)k);
            this.draw.drawCenteredString(Color.cl("a") + "Mario", this.width / 2 / k, (this.height / 2 - 82) / k);
            GL11.glPopMatrix();
            int l = 60;
            this.draw.drawCenteredString("This game is similar to the popular game SuperMario.", this.width / 2, this.height / 2 - l);
            l = l - 10;
            this.draw.drawCenteredString("You are a blue pixel and have to run through a random generated world.", this.width / 2, this.height / 2 - l);
            l = l - 10;
            this.draw.drawCenteredString("The purpose is to get the highest distance from the spawn.", this.width / 2, this.height / 2 - l);
            l = l - 10;
            this.draw.drawCenteredString("You jump by pressing space, and by pressing A and D you move left/right.", this.width / 2, this.height / 2 - l);
            l = l - 10;
            this.draw.drawCenteredString(" That\'s it - have fun!", this.width / 2, this.height / 2 - l);
        }

        this.btnPlay.visible = this.selectedGame != EnumGame.None;
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
