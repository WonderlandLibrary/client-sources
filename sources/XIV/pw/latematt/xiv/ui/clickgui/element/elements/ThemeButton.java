package pw.latematt.xiv.ui.clickgui.element.elements;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.ui.clickgui.element.Element;
import pw.latematt.xiv.ui.clickgui.theme.ClickTheme;

public class ThemeButton extends Element {
    protected static Minecraft mc = Minecraft.getMinecraft();
    protected final ClickTheme theme;

    public ThemeButton(ClickTheme theme, float x, float y, float width, float height) {
        super(x, y, width, height);

        this.theme = theme;
    }

    @Override
    public void drawElement(int mouseX, int mouseY) {
        XIV.getInstance().getGuiClick().getTheme().renderButton(getTheme().getName(), XIV.getInstance().getGuiClick().getTheme() == getTheme(), getX(), getY(), getWidth(), getHeight(), isOverElement(mouseX, mouseY), this);
    }

    @Override
    public void keyPressed(int key) {

    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isOverElement(mouseX, mouseY) && mouseButton == 0) {
            mc.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("gui.button.press"), 1.0F));
            XIV.getInstance().getGuiClick().setTheme(theme);
            XIV.getInstance().getFileManager().saveFile("guiTheme");
        }
    }

    public ClickTheme getTheme() {
        return theme;
    }

    @Override
    public void onGuiClosed() {
    }
}
