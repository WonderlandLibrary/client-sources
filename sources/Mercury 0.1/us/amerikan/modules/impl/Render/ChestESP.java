/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.modules.impl.Render;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import us.amerikan.events.EventRender;
import us.amerikan.modules.Category;
import us.amerikan.modules.Module;
import us.amerikan.utils.RenderHelper;

public class ChestESP
extends Module {
    public ChestESP() {
        super("ChestESP", "ChestESP", 0, Category.RENDER);
    }

    @Override
    public void onEnable() {
        EventManager.register(this);
        super.onDisable();
    }

    @Override
    public void onDisable() {
        EventManager.unregister(this);
        super.onEnable();
    }

    @EventTarget
    public void onRender(EventRender event) {
        for (Object o2 : ChestESP.mc.theWorld.loadedTileEntityList) {
            if (!(o2 instanceof TileEntityChest)) continue;
            TileEntityChest e2 = (TileEntityChest)o2;
            RenderHelper.blockESPBoxRGB(e2.getPos());
        }
    }
}

