package me.finz0.osiris.gui.clickgui;

import java.io.IOException;
import java.util.ArrayList;

import me.finz0.osiris.ShutDownHookerino;
import me.finz0.osiris.command.Command;
import me.finz0.osiris.command.CommandManager;
import me.finz0.osiris.gui.GuiTextField;
import me.finz0.osiris.module.modules.gui.ClickGuiModule;
import me.finz0.osiris.util.ColourUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class ClickGuiScreen extends GuiScreen {

    public static String title = "Aurora Borealis";
    public static ClickGui clickGui;
    public static int[] mouse = new int[2];
    private static GuiTextField console;
    ArrayList<String> cmds = new ArrayList<>();

    public ClickGuiScreen() {
        this.cmds.clear();
        for(Command c : CommandManager.getCommands()){
            this.cmds.add(c + " - " + c.getSyntax());
        }
    }

    @Override
    protected void mouseClicked(int x, int y, int button) throws IOException {
        super.mouseClicked(x, y, button);
        this.console.mouseClicked(x, y, button);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        clickGui.render();
        int mainColor = ClickGuiModule.isLight ? ColourUtils.color(255, 255, 255, 255) : ColourUtils.color(0, 0, 0, 255);
        this.console.drawTextBox(ClickGuiModule.getColor(), mainColor);
        this.console.setTextColor(ClickGuiModule.getColor());
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.console = new GuiTextField(0, this.fontRenderer, this.width / 2 - 100, 0, 200, 14);
        this.console.setMaxStringLength(100);
        this.console.setText(title);
        this.console.setFocused(true);
        super.initGui();
    }

    @Override
    public void updateScreen() {
        this.console.updateCursorCounter();
        clickGui.onUpdate();
        super.updateScreen();
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        super.onGuiClosed();
    }

    void setTitle() {
        if(!console.getText().equals("Aurora Borealis")) {
            title = "";
        }
    }

    @Override
    public void handleInput() throws IOException {
        int scale = mc.gameSettings.guiScale;
        mc.gameSettings.guiScale = 2;
        if (Keyboard.isCreated()) {
            Keyboard.enableRepeatEvents(true);

            while (Keyboard.next()) {
                if (Keyboard.getEventKeyState()) {
                    console.textboxKeyTyped(Keyboard.getEventCharacter(), Keyboard.getEventKey());
                    if (Keyboard.getEventKey() == 28) {
                        setTitle();
                        CommandManager cmdManager = new CommandManager();
                        cmdManager.callCommand("" + console.getText());
                        mc.displayGuiScreen(null);
                        ShutDownHookerino.saveConfig();
                    } else
                    if (Keyboard.getEventKey() == 1) {
                        setTitle();
                        mc.displayGuiScreen(null);
                        ShutDownHookerino.saveConfig();
                    } else {
                        clickGui.onkeyPressed(Keyboard.getEventKey(), Keyboard.getEventCharacter());
                    }
                } else {
                    clickGui.onKeyRelease(Keyboard.getEventKey(), Keyboard.getEventCharacter());
                }


            }
        }

        if (Mouse.isCreated()) {
            while (Mouse.next()) {
                ScaledResolution scaledResolution = new ScaledResolution(mc);
                int mouseX = Mouse.getEventX() * scaledResolution.getScaledWidth() / mc.displayWidth;
                int mouseY = scaledResolution.getScaledHeight() - Mouse.getEventY() * scaledResolution.getScaledHeight() / mc.displayHeight - 1;

                if (Mouse.getEventButton() == -1) {
                    if (Mouse.getEventDWheel() != 0) {
                        int x = mouseX;
                        int y = mouseY;
                        clickGui.onMouseScroll((Mouse.getEventDWheel() / 100) * 3);
                    }

                    clickGui.onMouseUpdate(mouseX, mouseY);
                    mouse[0] = mouseX;
                    mouse[1] = mouseY;
                } else if (Mouse.getEventButtonState()) {
                    clickGui.onMouseClick(mouseX, mouseY, Mouse.getEventButton());
                } else {
                    clickGui.onMouseRelease(mouseX, mouseY);
                }
            }
        }

        mc.gameSettings.guiScale = scale;

        super.handleInput();
    }
}
