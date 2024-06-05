package me.vaziak.sensation.client.api.gui.ingame.clickui.components.tab.main;

import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import me.vaziak.sensation.Sensation;
import me.vaziak.sensation.client.api.gui.ingame.clickui.Interface;
import me.vaziak.sensation.client.api.gui.ingame.clickui.components.base.BaseButton;
import me.vaziak.sensation.client.api.gui.ingame.clickui.tab.Tab;
import me.vaziak.sensation.utils.anthony.Draw;

/**
 * @author antja03
 */
public class MainButtonTab extends BaseButton {

    private Tab tab;
    private final ResourceLocation iconImageLocation;

    public MainButtonTab(Interface theInterface, Tab tab, ResourceLocation iconImageLocation, double positionX, double positionY, double maxWidth, double maxHeight, Action action) {
        super(theInterface, positionX, positionY, maxWidth, maxHeight, action);
        this.tab = tab;
        this.iconImageLocation = iconImageLocation;
    }

    @Override
    public void drawComponent(double x, double y) {
        super.drawComponent(x, y);
        double iconWidth = 16;
        double iconHeight = 16;
        if (Sensation.instance.userInterface.theInterface.getCurrentTab() == tab)
        	GL11.glColor3f(0.45f, 0.45f, 0.45f);
        else
            GL11.glColor3f(0.45f, 0.45f, 0.45f);
        Draw.drawImg(iconImageLocation, x, y, iconWidth, iconHeight);
    }

    public Tab getTab() {
        return tab;
    }
}
