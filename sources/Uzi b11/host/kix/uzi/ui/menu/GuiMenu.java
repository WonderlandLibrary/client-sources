package host.kix.uzi.ui.menu;

import host.kix.uzi.ui.menu.component.impl.Button;
import host.kix.uzi.ui.menu.component.impl.Frame;
import host.kix.uzi.ui.menu.component.impl.Panel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kix on 5/29/2017.
 * Made for the eclipse project.
 */
public class GuiMenu extends GuiScreen {

    private List<Frame> frames = new ArrayList<>();
    private Panel panel = new Panel();

    public void initializeMenu() {
        Frame frame = new Frame("Test", panel.getX() + 4, panel.getY() + 4);
        frames.add(frame);
        frame.getButtons().add(new Button("Test Button", 2, 100));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        panel.drawScreen(mouseX, mouseY, partialTicks);
        if (!frames.isEmpty()) {
            frames
                    .forEach(frame -> frame.drawScreen(mouseX, mouseY, partialTicks));
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (!frames.isEmpty()) {
            frames
                    .forEach(frame -> frame.mouseClicked(mouseX, mouseY, mouseButton));
        }
    }
}
