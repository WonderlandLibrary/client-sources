package me.protocol_client.ui.click.Protocol.Elements.moreElements;

import me.protocol_client.Protocol;
import me.protocol_client.ui.click.Protocol.Elements.Element;
import me.protocol_client.ui.click.Protocol.Panel.Panel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;

public class PanelStuff extends Element {
    protected static Minecraft mc = Minecraft.getMinecraft();
    protected final Panel panel;

    public PanelStuff(Panel panel, float x, float y, float width, float height) {
        super(x, y, width, height);

        this.panel = panel;
    }

    @Override
    public void drawElement(int mouseX, int mouseY) {
        Protocol.getGuiClick().getTheme().renderButton(getPanel().getName(), getPanel().isShowing(), getX(), getY(), getWidth(), getHeight(), isOverElement(mouseX, mouseY), this);
    }

    @Override
    public void keyPressed(int key) {

    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isOverElement(mouseX, mouseY) && mouseButton == 0) {
            getPanel().setShowing(!getPanel().isShowing());
        }
    }

    public Panel getPanel() {
        return panel;
    }

    @Override
    public void onGuiClosed() {
    }
}
