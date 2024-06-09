package me.Emir.Karaguc.ui.credits;

import me.Emir.Karaguc.utils.RectangleUtils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.util.ArrayList;

public class GuiCredits extends GuiScreen {

    private int scrollAmount;
    public Developper selectedDev = null;
    private ArrayList<Developper> developers = new ArrayList<>();

    public GuiCredits() {
        this.developers.add(new Developper("903 / 6", "Backdooring and Spooky hacker"));
        this.developers.add(new Developper("H0p3", "Giving us perms"));
        this.developers.add(new Developper("Bans", "Sp00ky hacker"));
        this.developers.add(new Developper("Melanie", "Being a slut too Emir"));
        this.developers.add(new Developper("Team OVO / London Shooters", "Best Team in the community"));
        this.developers.add(new Developper("Omegafluffy you ain't allowed to releak our leak you stupid dumb thot", "Same goes with you Shaz ass boy"));
        this.developers.add(new Developper("None of you bitch ass releakers are allowed to releak our shit if we find out you releak our shit expect a bomb coming at you", "Emir this happens when you don't pay us lil nigga"));
    }

    @Override
    public void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0: {
                this.mc.displayGuiScreen(null);
                break;
            }

            case 1: {
                String s = (this.selectedDev != null) ? this.selectedDev.getDiscordName() : "";
                if (this.selectedDev != null) {
                    StringSelection stringSelection = new StringSelection(s);
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(stringSelection, null);
                }
            }
        }
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        if (Mouse.hasWheel()) {
            int wheel = Mouse.getDWheel();
            if (wheel < 0) {
                this.scrollAmount += 26;
                if (this.scrollAmount < 0) {
                    this.scrollAmount = 0;
                }
            } else if (wheel > 0) {
                this.scrollAmount -= 26;
                if (this.scrollAmount < 0) {
                    this.scrollAmount = 0;
                }
            }
        }
        this.drawDefaultBackground();
        this.drawCenteredString(fontRendererObj, "Credits", width / 2, 10, -1);
        GL11.glPushMatrix();
        this.prepareScissorBox(0.0f, 33.0f, width, height - 50);
        GL11.glEnable(3089);
        int y2 = 38;
        for (Developper dev2 : developers) {
            if (!this.isDevInArea(y2)) continue;
            String name = dev2.getName();
            String discordName = dev2.getDiscordName();
            if (dev2 == this.selectedDev) {
                if (this.isMouseOverDev(par1, par2, y2 - this.scrollAmount) && Mouse.isButtonDown(0)) {
                    RectangleUtils.drawBorderedRect(52.0f, y2 - this.scrollAmount - 4, width - 52, y2 - this.scrollAmount + 20, 1.0f, -16777216, -2142943931);
                } else if (this.isMouseOverDev(par1, par2, y2 - this.scrollAmount)) {
                    RectangleUtils.drawBorderedRect(52.0f, y2 - this.scrollAmount - 4, width - 52, y2 - this.scrollAmount + 20, 1.0f, -16777216, -2142088622);
                } else {
                    RectangleUtils.drawBorderedRect(52.0f, y2 - this.scrollAmount - 4, width - 52, y2 - this.scrollAmount + 20, 1.0f, -16777216, -2144259791);
                }
            } else if (this.isMouseOverDev(par1, par2, y2 - this.scrollAmount) && Mouse.isButtonDown(0)) {
                RectangleUtils.drawBorderedRect(52.0f, y2 - this.scrollAmount - 4, width - 52, y2 - this.scrollAmount + 20, 1.0f, -16777216, -2146101995);
            } else if (this.isMouseOverDev(par1, par2, y2 - this.scrollAmount)) {
                RectangleUtils.drawBorderedRect(52.0f, y2 - this.scrollAmount - 4, width - 52, y2 - this.scrollAmount + 20, 1.0f, -16777216, -2145180893);
            }
            this.drawCenteredString(this.fontRendererObj, name, width / 2, y2 - this.scrollAmount, -1);
            this.drawCenteredString(this.fontRendererObj, discordName, width / 2, y2 - this.scrollAmount + 10, 5592405);
            y2 += 26;
        }
        GL11.glDisable(3089);
        GL11.glPopMatrix();
        super.drawScreen(par1, par2, par3);
        if (Keyboard.isKeyDown(200)) {
            this.scrollAmount -= 26;
            if (this.scrollAmount < 0) {
                this.scrollAmount = 0;
            }
        } else if (Keyboard.isKeyDown(208)) {
            this.scrollAmount += 26;
            if (this.scrollAmount < 0) {
                this.scrollAmount = 0;
            }
        }
    }

    @Override
    public void initGui() {
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, height - 24, "Back"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, height - 48, "Copy Discord Name"));
    }

    private boolean isDevInArea(int y2) {
        if (y2 - this.scrollAmount <= height - 50) {
            return true;
        }
        return false;
    }

    private boolean isMouseOverDev(int x2, int y2, int y1) {
        if (x2 >= 52 && y2 >= y1 - 4 && x2 <= width - 52 && y2 <= y1 + 20 && x2 >= 0 && y2 >= 33 && x2 <= width && y2 <= height - 50) {
            return true;
        }
        return false;
    }

    @Override
    protected void mouseClicked(int par1, int par2, int par3) throws IOException {
        if (this.scrollAmount < 0) {
            this.scrollAmount = 0;
        }
        int y2 = 38 - this.scrollAmount;

        for (Developper dev2 : developers) {
            if (this.isMouseOverDev(par1, par2, y2)) {
                if (dev2 == this.selectedDev) {
                    this.actionPerformed((GuiButton)this.buttonList.get(1));
                    return;
                }
                this.selectedDev = dev2;
            }
            y2 += 26;
        }
        try {
            super.mouseClicked(par1, par2, par3);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void prepareScissorBox(float x2, float y2, float x22, float y22) {
        ScaledResolution scale = new ScaledResolution(this.mc);
        int factor = scale.getScaleFactor();
        GL11.glScissor((int)(x2 * (float)factor), (int)(((float)scale.getScaledHeight() - y22) * (float)factor), (int)((x22 - x2) * (float)factor), (int)((y22 - y2) * (float)factor));
    }
}

