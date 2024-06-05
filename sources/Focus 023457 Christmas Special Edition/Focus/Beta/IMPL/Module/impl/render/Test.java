package Focus.Beta.IMPL.Module.impl.render;

import Focus.Beta.IMPL.font.FontLoaders;
import Focus.Beta.UTILS.render.RenderUtil2;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;

import java.awt.*;

public class Test extends GuiScreen {

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks){
        RenderUtil2.drawRoundedRect(mouseX, (float) (mouseY - 11.5), mouseX + 3 + FontLoaders.arial16.getStringWidth("Description"), mouseY + 1, 0, new Color(68, 68, 68).getRGB());
        FontLoaders.arial16.drawStringWithShadow("Description",mouseX + 1, (float)(mouseY - 7.5), -1);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
