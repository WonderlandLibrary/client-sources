package host.kix.uzi.ui.circle;

import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;

/**
 * Created by Kix on 6/12/2017.
 * Made for the Uzi Universal project.
 */
public class GuiQuickSelectMenu extends GuiScreen {

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

    }

    @Override
    public void updateScreen() {
        if (!Keyboard.isKeyDown(Keyboard.KEY_INSERT)) {
            mc.displayGuiScreen(null);
        }
    }



}
