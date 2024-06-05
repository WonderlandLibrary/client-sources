package rip.helium.ui.main;

import net.minecraft.client.*;
import net.minecraft.client.settings.*;
import rip.helium.*;
import rip.helium.cheat.*;
import rip.helium.utils.*;

import java.io.*;
import org.lwjgl.input.*;

import net.minecraft.client.gui.*;

public class Screen extends GuiScreen
{
    public final Minecraft game;
    public Interface theInterface;
    public boolean firstKeyPressed;
    
    public Screen() {
        this.game = Minecraft.getMinecraft();
        this.theInterface = new Interface(this);
        this.firstKeyPressed = false;
    }
    
    @Override
    public void initGui() {
        this.firstKeyPressed = false;
        this.theInterface.initializeInterface();
        super.initGui();
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.theInterface.drawInterface(mouseX, mouseY);
        KeyBinding[] array;
        for (int length = (array = new KeyBinding[] { Minecraft.getMinecraft().gameSettings.keyBindForward, Minecraft.getMinecraft().gameSettings.keyBindBack, Minecraft.getMinecraft().gameSettings.keyBindLeft, Minecraft.getMinecraft().gameSettings.keyBindRight, Minecraft.getMinecraft().gameSettings.keyBindJump, Minecraft.getMinecraft().gameSettings.keyBindSprint }).length, i = 0; i < length; ++i) {
            final KeyBinding keyBinding = array[i];
            KeyBinding.setKeyBindState(keyBinding.getKeyCode(), Keyboard.isKeyDown(keyBinding.getKeyCode()));
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    @Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        this.theInterface.mouseButtonClicked(mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    @Override
    protected void mouseReleased(final int mouseX, final int mouseY, final int state) {
        this.theInterface.mouseButtonReleased(state);
        super.mouseReleased(mouseX, mouseY, state);
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        int wheel = Mouse.getEventDWheel();
        if (wheel != 0) {
            if (wheel > 1) {
                wheel = -1;
            }
            if (wheel < -1) {
                wheel = 1;
            }
            this.theInterface.mouseScrolled(wheel);
        }
        super.handleMouseInput();
    }
    
    @Override
    protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
        if (!this.theInterface.keyTyped(typedChar, keyCode) && (keyCode == Helium.instance.cheatManager.getCheatRegistry().get("Interface").getBind() || keyCode == 1)) {
            this.closeInterface();
        }
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    public void closeInterface() {
        this.theInterface.setClosing(true);
        GuiUtil.closeScreenAndReturn();
    }
    
    public ScaledResolution getResolution() {
        return new ScaledResolution(this.game);
    }
}
