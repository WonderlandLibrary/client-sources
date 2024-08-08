package me.xatzdevelopments.modules.render;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import org.lwjgl.input.Keyboard;

import me.xatzdevelopments.events.listeners.EventRenderWorld;
import me.xatzdevelopments.modules.Module;

import java.awt.*;

public class ChestESP extends Module {

    public ChestESP() {
        super("ChestESP", Keyboard.KEY_O, Category.RENDER, "Render a box around chests");
    }

/*    public void onEvent(Event e) {
        if (e instanceof EventRenderWorld) {
            for (TileEntity tileEntity : mc.theWorld.loadedTileEntityList) {


                if (!(tileEntity instanceof TileEntityChest))
                    continue;

                Color chestColor = new Color(-1);

                EspUtil.chestESPBox(tileEntity, 0, chestColor);
            }
        }

    } */

}