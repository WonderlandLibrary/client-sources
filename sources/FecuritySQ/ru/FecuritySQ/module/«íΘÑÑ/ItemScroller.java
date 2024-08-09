package ru.FecuritySQ.module.общее;

import net.minecraft.network.play.server.SPlayerPositionLookPacket;
import org.lwjgl.glfw.GLFW;
import ru.FecuritySQ.event.Event;
import ru.FecuritySQ.event.imp.EventPacket;
import ru.FecuritySQ.module.Module;

public class ItemScroller extends Module {
    public ItemScroller() {
        super(Category.Общее, GLFW.GLFW_KEY_0);
    }
}
