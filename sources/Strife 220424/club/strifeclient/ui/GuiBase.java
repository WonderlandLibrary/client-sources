package club.strifeclient.ui;

import java.io.IOException;

public interface GuiBase {
    void drawScreen(int mouseX, int mouseY, float partialTicks);
    void mouseClicked(int mouseX, int mouseY, int button);
    void mouseReleased(int mouseX, int mouseY, int button);
    void keyTyped(char typedChar, int keyCode) throws IOException;
    void onGuiClosed();
    void initGui();
    boolean isVisible();
    default void handleMouseInput() throws IOException {}
}
