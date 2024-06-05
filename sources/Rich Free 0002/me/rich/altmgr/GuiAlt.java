package me.rich.altmgr;

import java.awt.Color;

import java.io.IOException;
import java.security.SecureRandom;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.input.Keyboard;

import me.rich.Main;
import me.rich.font.Fonts;
import me.rich.helpers.render.RenderHelper;


public final class GuiAlt
extends GuiScreen {
    private PasswordField password;
    private final GuiScreen previousScreen;
    private AltLoginThread thread;
    private GuiTextField username;
    private final long initTime = System.currentTimeMillis();
    private static String alphabet = "QWERTYUIOPASDFGHJKLZXCVBNM1234567890";
    private static final SecureRandom secureRandom = new SecureRandom();
    
    public GuiAlt(GuiScreen previousScreen) {
        this.previousScreen = previousScreen;
    }

    public static String randomString(int strLength) {
        StringBuilder stringBuilder = new StringBuilder(strLength);
        for (int i = 0; i < strLength; ++i) {
            stringBuilder.append(alphabet.charAt(secureRandom.nextInt(alphabet.length())));
        }
        return stringBuilder.toString();
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 2: {
                this.thread = new AltLoginThread("RichBeach" + GuiAlt.randomString(5), "");
                this.thread.start();
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
        ScaledResolution sr = new ScaledResolution(this.mc);
        int lol = height / 4 + 24;
        Gui.drawRect(0, 0, 960, 540, new Color(14,14,14).getRGB());
        RenderHelper.drawBorderedRect(width / 2 - 70, lol - 21, width / 2 + 60, lol + 110, 0.5D, (new Color(16, 16, 16, 255)).getRGB(), (new Color(60, 60, 60, 255)).getRGB(), true);
        RenderHelper.drawGradientSideways(width / 2 - 69, lol - 20, width / 2 + 59, lol - 19, Main.getClientColor().getRGB(), Main.getClientColor().getRGB());
        this.username.drawTextBox();
        this.password.drawTextBox();
        mc.fontRendererObj.drawCenteredString("Alt", width / 2 + -6, lol, -1);
        Fonts.neverlose500_15.drawCenteredString(this.thread == null ? TextFormatting.GRAY + "" : this.thread.getStatus(), width / 2 - 5, lol + 98, -1);
        if (this.username.getText().isEmpty() && !this.username.isFocused()) {
        	mc.fontRendererObj.drawStringWithShadow("Type email.", width / 2 - 52, lol + 23, -7829368);
        }
        if (this.password.getText().isEmpty() && !this.password.isFocused()) {
        	mc.fontRendererObj.drawStringWithShadow("Type pass.", width / 2 - 52, lol + 43, -7829368);
        }
        super.drawScreen(x2, y2, z2);
    }

    @Override
    public void initGui() {
        int lol = height / 4 + 24;
        this.buttonList.add(new GuiButton(0, width / 2 - 50, lol + 60, 90, 13, "Login"));
        this.buttonList.add(new GuiButton(2, width / 2 - 50, lol + 64 + 12, 90, 13, "Random name"));
        this.username = new GuiTextField(lol, this.mc.fontRendererObj, width / 2 - 55, lol + 20, 100, 13);
        this.password = new PasswordField(this.mc.fontRendererObj, width / 2 - 55, lol + 40, 100, 13);
        this.username.setFocused(true);
        Keyboard.enableRepeatEvents(true);
    }

    @Override
    protected void keyTyped(char character, int key) {
        try {
            super.keyTyped(character, key);
        }
        catch (IOException iOException) {
            // empty catch block
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

    static {
        alphabet = alphabet + alphabet.toLowerCase();
    }
}

