package host.kix.uzi.ui.menu.component.impl;

import host.kix.uzi.ui.menu.component.Component;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

/**
 * Created by Kix on 5/29/2017.
 * Made for the eclipse project.
 */
public class Button extends Component {

    /**
     * Is the button running?
     */
    private boolean running;

    private int x, y;
    public static final int WIDTH = 100;
    public static final int HEIGHT = 20;

    public Button(String label, int x, int y) {
        super(label);
        this.x = x;
        this.y = y;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        Gui.drawRect(x, y, x + WIDTH, y + HEIGHT, running ? 0xFF000000 : 0xFFFF0000);
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(getLabel(), x + 2, y + 2, -1);
    }

    public void toggle() {
        setRunning(!isRunning());
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (mouseX >= x && mouseX <= x + WIDTH && mouseY >= y && mouseY <= y + HEIGHT && button == 0) {
            toggle();
        }
    }
}
