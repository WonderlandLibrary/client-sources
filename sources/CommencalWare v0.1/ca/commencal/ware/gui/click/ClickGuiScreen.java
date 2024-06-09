package ca.commencal.ware.gui.click;

import ca.commencal.ware.managers.FileManager;
import ca.commencal.ware.utils.visual.ColorUtils;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.io.IOException;
import java.util.ArrayList;


//SKID

public class ClickGuiScreen extends GuiScreen {

    public static String title = "";
    public static ClickGui clickGui;
    public static int[] mouse = new int[2];
    private static GuiTextField console;
    ArrayList cmds = new ArrayList();



    @Override
    protected void mouseClicked(int x, int y, int button) throws IOException {
        super.mouseClicked(x, y, button);
        this.console.mouseClicked(x, y, button);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        clickGui.render();
        this.console.drawTextBox(ca.commencal.ware.module.modules.render.ClickGui.color, ColorUtils.color(0.0F, 0.0F, 0.0F, 1.0F));
        this.console.setTextColor(ca.commencal.ware.module.modules.render.ClickGui.color);
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
        if(!console.getText().equals("")) {
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
                    if (Keyboard.getEventKey() == 1) {
                        setTitle();
                        mc.displayGuiScreen(null);
                        FileManager.saveModules();
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
