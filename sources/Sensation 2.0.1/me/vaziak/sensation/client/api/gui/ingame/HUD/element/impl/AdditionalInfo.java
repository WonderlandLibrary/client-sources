package me.vaziak.sensation.client.api.gui.ingame.HUD.element.impl;

import me.vaziak.sensation.Sensation;
import me.vaziak.sensation.client.api.event.EventSystem;
import me.vaziak.sensation.client.api.gui.ingame.HUD.element.Element;
import me.vaziak.sensation.client.api.gui.ingame.HUD.element.Quadrant;
import me.vaziak.sensation.client.api.gui.ingame.clickui.components.tab.cheat.PropertyComboBox;
import me.vaziak.sensation.client.api.property.impl.BooleanProperty;
import me.vaziak.sensation.client.api.property.impl.StringsProperty;
import me.vaziak.sensation.utils.anthony.basicfont.Fonts;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumChatFormatting;

import java.awt.*;

public class AdditionalInfo extends Element {

    private BooleanProperty enabled = new BooleanProperty("Enabled", "Rendering info", null, true);
    private StringsProperty options = new StringsProperty("Options", "", null, true, true, new String[] {"Coordinates", "FPS", "Ping"});

    public AdditionalInfo() {
        super("Additional Info", Quadrant.BOTTOM_LEFT, 2, 2);
        registerValue(enabled);
        registerValue(options);
		EventSystem.hook(this);
    }

    @Override
    public void drawElement(boolean editor) {
        Minecraft mc = Minecraft.getMinecraft();
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());

        editX = positionX = 99999999;
        editY = positionY = 99999999;
        width = 90;
        height = 50;

        if (!enabled.getValue())
            return;

        int x = (int) mc.thePlayer.posX;
        int y = (int) mc.thePlayer.posY;
        int z = (int) mc.thePlayer.posZ;

        Fonts.f18.drawString((options.getValue().get("Coordinates") ? EnumChatFormatting.RED + "XYZ: " + EnumChatFormatting.GRAY + x + ", " + y + ", " + z + " " : "")
                        + (options.getValue().get("Ping") ? EnumChatFormatting.RED + "Ping: " + EnumChatFormatting.GRAY + (mc.getCurrentServerData() != null ? mc.getCurrentServerData().pingToServer : "N/A") + " " : "")
                        + (options.getValue().get("FPS") ? EnumChatFormatting.RED + "FPS: " + EnumChatFormatting.GRAY + Minecraft.getDebugFPS() : ""), positionX,
                positionY, new Color(255,255,255).getRGB());
    }
}
