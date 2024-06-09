package me.teus.eclipse.modules.impl.visuals.HudModes;

import me.teus.eclipse.Client;
import me.teus.eclipse.events.render.EventRender2D;
import me.teus.eclipse.modules.Module;
import me.teus.eclipse.utils.interfaces.HUDStyle;
import me.teus.eclipse.utils.managers.ModuleManager;
import net.minecraft.client.gui.FontRenderer;

import java.awt.*;
import java.util.Random;

public class Huzuni implements HUDStyle{

    public static void draw() {

        FontRenderer font = mc.fontRendererObj;

        String displayName = Client.getInstance().CLIENT_NAME + " v" + Client.getInstance().CLIENT_VERSION;
        font.drawStringWithShadow(displayName, 5, 5, -1);


        float index = 0;
        for(Module m : ModuleManager.modules){
            if(!m.toggled) continue;
            font.drawStringWithShadow(m.getDisplayName(), 5, 16 + (font.FONT_HEIGHT * index), -1);
            index += 1.2;
        }
    }

}
