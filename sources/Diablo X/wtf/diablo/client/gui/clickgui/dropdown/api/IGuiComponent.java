package wtf.diablo.client.gui.clickgui.dropdown.api;

import net.minecraft.client.gui.FontRenderer;

public interface IGuiComponent {
    FontRenderer mcFontRenderer = net.minecraft.client.Minecraft.getMinecraft().fontRendererObj;

    void drawScreen(int mouseX, int mouseY, float partialTicks);
    void mouseClicked(int mouseX, int mouseY, int mouseButton);
    void mouseReleased(int mouseX, int mouseY, int state);

}
