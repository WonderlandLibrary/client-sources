package wtf.diablo.client.gui.clickgui.dropdown.impl.searchbar;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import wtf.diablo.client.gui.clickgui.dropdown.api.IGuiComponent;
import wtf.diablo.client.gui.clickgui.dropdown.impl.DropdownClickGui;


public final class SearchBar implements IGuiComponent {
    private static final Minecraft mc = Minecraft.getMinecraft();
    private final DropdownClickGui dropdownClickGui;

    public SearchBar(final DropdownClickGui dropdownClickGui) {
        this.dropdownClickGui = dropdownClickGui;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        final ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {

    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {

    }
}
