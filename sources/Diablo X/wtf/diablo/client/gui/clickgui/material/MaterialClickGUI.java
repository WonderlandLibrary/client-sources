package wtf.diablo.client.gui.clickgui.material;

import net.minecraft.client.gui.GuiScreen;
import wtf.diablo.client.gui.clickgui.material.impl.main.MainPanel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public final class MaterialClickGUI extends GuiScreen {

    private final Collection<MainPanel> mainPanelCollection = new ArrayList<>();

    public MaterialClickGUI() {
        this.mainPanelCollection.add(new MainPanel());
    }

    @Override
    public void initGui() {
        super.initGui();
    }

    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        this.mainPanelCollection.forEach(mainPanel -> mainPanel.drawScreen(mouseX, mouseY, partialTicks));
    }

    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        this.mainPanelCollection.forEach(mainPanel -> mainPanel.mouseClicked(mouseX, mouseY, mouseButton));
    }


    @Override
    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
        super.mouseReleased(mouseX, mouseY, state);

        this.mainPanelCollection.forEach(mainPanel -> mainPanel.mouseReleased(mouseX, mouseY, state));
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();

        this.mainPanelCollection.forEach(MainPanel::onGuiClosed);
    }
}