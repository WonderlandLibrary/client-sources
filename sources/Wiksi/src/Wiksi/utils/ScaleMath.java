//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package src.Wiksi.utils;

import src.Wiksi.utils.client.Vec2i;
import net.minecraft.client.Minecraft;

public class ScaleMath {
    public ScaleMath() {
    }

    public static Vec2i getMouse(int mouseX, int mouseY) {
        return new Vec2i((int)((double)mouseX * Minecraft.getInstance().getMainWindow().getGuiScaleFactor() / 2.0), (int)((double)mouseY * Minecraft.getInstance().getMainWindow().getGuiScaleFactor() / 2.0));
    }
}
