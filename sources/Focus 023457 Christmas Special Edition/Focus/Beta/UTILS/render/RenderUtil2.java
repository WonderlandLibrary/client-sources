package Focus.Beta.UTILS.render;

import net.minecraft.client.gui.Gui;

public class RenderUtil2 {

    public static void drawRoundedRect(float x, float y, float x1, float y1, int borderC, int insideC) {
        Gui. drawRect(x + 0.5F, y, x1 - 0.5F, y + 0.5F, insideC);
        Gui.drawRect(x + 0.5F, y1 - 0.5F, x1 - 0.5F, y1, insideC);
        Gui.drawRect(x, y + 0.5F, x1, y1 - 0.5F, insideC);
    }
}
