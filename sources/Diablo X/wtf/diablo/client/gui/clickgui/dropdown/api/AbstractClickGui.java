package wtf.diablo.client.gui.clickgui.dropdown.api;

import net.minecraft.client.gui.GuiScreen;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractClickGui extends GuiScreen implements IGuiComponent {
    protected final List<IGuiComponent> guiComponents;

    public AbstractClickGui() {
        this.guiComponents = new LinkedList<>();
    }

    @Override
    public abstract void drawScreen(final int mouseX, final int mouseY, final float partialTicks);

    @Override
    public abstract void mouseClicked(final int mouseX, final int mouseY, final int mouseButton);

    @Override
    public abstract void mouseReleased(final int mouseX, final int mouseY, final int state);

    @Override
    public abstract void keyTyped(final char typedChar, final int keyCode);
}
