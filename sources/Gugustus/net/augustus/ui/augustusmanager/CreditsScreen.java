package net.augustus.ui.augustusmanager;

import java.awt.Color;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import net.augustus.Augustus;
import net.augustus.ui.widgets.CustomButton;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;

public class CreditsScreen extends GuiScreen {
    private GuiScreen parent;
    private CustomButton pikabutton;

    public CreditsScreen(GuiScreen parent) {
        this.parent = parent;
    }

    public GuiScreen start(GuiScreen parent) {
        this.parent = parent;
        return this;
    }

    @Override
    public void initGui() {
        super.initGui();
        ScaledResolution sr = new ScaledResolution(this.mc);
        int scaledWidth = sr.getScaledWidth();
        int scaledHeight = sr.getScaledHeight();
        this.buttonList.add(new CustomButton(2, scaledWidth / 2 - 100, scaledHeight - scaledHeight / 10, 200, 20, "Back", Augustus.getInstance().getClientColor()));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawBackground(0);
        super.drawScreen(mouseX, mouseY, partialTicks);
        ScaledResolution sr = new ScaledResolution(this.mc);
        GL11.glScaled(2.0, 2.0, 1.0);
        this.fontRendererObj
                .drawStringWithShadow(
                        "Credits",
                        (float)sr.getScaledWidth() / 4.0F - (float)this.fontRendererObj.getStringWidth("Credits") / 2.0F,
                        10.0F,
                        Color.lightGray.getRGB()
                );
        GL11.glScaled(1.0, 1.0, 1.0);
        this.fontRendererObj.drawString("This is an Minecraft Hacked Client based on Xenza r2.", (sr.getScaledWidth() / 4) - (this.fontRendererObj.getStringWidth("This is an Minecraft Hacked Client based on Xenza r2.") / 2), (sr.getScaledHeight() / 4) - this.fontRendererObj.FONT_HEIGHT - 100, -1);
        this.fontRendererObj.drawString("I do not own all the code", (sr.getScaledWidth() / 4) - (this.fontRendererObj.getStringWidth("I do not own all the code") / 2), (sr.getScaledHeight() / 4) - this.fontRendererObj.FONT_HEIGHT, -1);
        this.fontRendererObj.drawString("I hope i dont get in legal trouble because of this client", (sr.getScaledWidth() / 4) - (this.fontRendererObj.getStringWidth("I hope i dont get in legal trouble because of this client") / 2), (sr.getScaledHeight() / 4) - this.fontRendererObj.FONT_HEIGHT + 100, -1);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        if (button.id == 2) {
            this.mc.displayGuiScreen(this.parent);
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == 1 && this.mc.theWorld == null) {
            this.mc.displayGuiScreen(this.parent);
        } else {
            super.keyTyped(typedChar, keyCode);
        }
    }
}
