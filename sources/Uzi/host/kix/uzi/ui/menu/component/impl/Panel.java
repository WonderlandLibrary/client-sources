package host.kix.uzi.ui.menu.component.impl;

import host.kix.uzi.ui.menu.component.Component;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

/**
 * Created by Kix on 5/29/2017.
 * Made for the eclipse project.
 */
public class Panel extends Component {

    private int x, y;

    private int width = 200, height = 150;

    public Panel() {
        super("Uzi");
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
        x = (scaledResolution.getScaledWidth() / 2);
        y = (scaledResolution.getScaledHeight() / 2);
        Gui.drawRect(x - width, y - height, x + width, y + height, 0xFF4F4F4F);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
