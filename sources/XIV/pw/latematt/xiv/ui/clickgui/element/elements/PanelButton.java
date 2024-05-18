package pw.latematt.xiv.ui.clickgui.element.elements;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.ui.clickgui.element.Element;
import pw.latematt.xiv.ui.clickgui.panel.Panel;

public class PanelButton extends Element {
    protected static Minecraft mc = Minecraft.getMinecraft();
    protected final Panel panel;

    public PanelButton(Panel panel, float x, float y, float width, float height) {
        super(x, y, width, height);

        this.panel = panel;
    }

    @Override
    public void drawElement(int mouseX, int mouseY) {
        XIV.getInstance().getGuiClick().getTheme().renderButton(getPanel().getName(), getPanel().isShowing(), getX(), getY(), getWidth(), getHeight(), isOverElement(mouseX, mouseY), this);
    }

    @Override
    public void keyPressed(int key) {

    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isOverElement(mouseX, mouseY) && mouseButton == 0) {
            mc.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("gui.button.press"), 1.0F));
            getPanel().setShowing(!getPanel().isShowing());
            XIV.getInstance().getFileManager().saveFile("gui");
        }
    }

    public Panel getPanel() {
        return panel;
    }

    @Override
    public void onGuiClosed() {
    }
}
