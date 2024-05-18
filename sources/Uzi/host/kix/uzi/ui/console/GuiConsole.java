package host.kix.uzi.ui.console;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Kix on 9/10/2016.
 */
public class GuiConsole extends GuiScreen {

    public static ArrayList console = new ArrayList();
    private GuiConsoleTextField textField = new GuiConsoleTextField(0, 10, 10, 200, 20);

    public GuiConsole() {
        textField.setFocused(true);
        textField.setCanLoseFocus(false);
    }

    public static void addConsoleMessage(String s){
        GuiConsole.console.add(0, s);
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == 1) {
            this.mc.displayGuiScreen((GuiScreen) null);
        } else if (keyCode != 28 && keyCode != 156) {

            this.textField.textboxKeyTyped(typedChar, keyCode);

        } else {
            String s = this.textField.getText().trim();

            if (!s.isEmpty()) {
                mc.thePlayer.sendChatMessage("." + s);
            }

            mc.displayGuiScreen(null);

        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
        textField.setxPosition(res.getScaledWidth() / 2 - 100);
        super.drawScreen(mouseX, mouseY, partialTicks);
        textField.drawTextBox();
    }
}
