package fun.ellant.utils;

import fun.ellant.utils.client.Vec2i;
import net.minecraft.client.Minecraft;

public class ScaleMath {
    public static Vec2i getMouse(int mouseX, int mouseY) {
        return new Vec2i((int)((double)mouseX * Minecraft.getInstance().getMainWindow().getGuiScaleFactor() / 2.0), (int)((double)mouseY * Minecraft.getInstance().getMainWindow().getGuiScaleFactor() / 2.0));
    }
}
