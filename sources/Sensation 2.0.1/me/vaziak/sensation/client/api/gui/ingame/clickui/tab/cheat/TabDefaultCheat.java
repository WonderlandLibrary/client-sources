package me.vaziak.sensation.client.api.gui.ingame.clickui.tab.cheat;

import me.vaziak.sensation.Sensation;
import me.vaziak.sensation.client.api.Category;
import me.vaziak.sensation.client.api.Module;
import me.vaziak.sensation.client.api.gui.ingame.clickui.Interface;
import me.vaziak.sensation.client.api.gui.ingame.clickui.components.tab.cheat.ContainerCheats;
import me.vaziak.sensation.client.api.gui.ingame.clickui.components.tab.cheat.ContainerProperties;
import me.vaziak.sensation.client.api.gui.ingame.clickui.tab.Tab;

/**
 * @author antja03
 */
public class TabDefaultCheat extends Tab {

    private Category cheatCategory;

    private Module selectedCheat;

    public TabDefaultCheat(Interface theInterface, Category cheatCategory) {
        super(theInterface);
        this.cheatCategory = cheatCategory;
        components.add(new ContainerCheats(theInterface, this, cheatCategory, 25, 2, 150, theInterface.getHeight()));
        for (Module module : Sensation.instance.cheatManager.getCheatRegistry().values()) {
            if (module.getCategory() == cheatCategory) {
                this.components.add(new ContainerProperties(theInterface, this, module, 175, 0, 150, theInterface.getHeight()));
            }
        }
    }

    @Override
    public void onTick() {
        getInterface().setWidth(300);
        getInterface().setHeight(150);
        super.onTick();
    }

    public Module getSelectedCheat() {
        return selectedCheat;
    }

    public void setSelectedCheat(Module selectedCheat) {
        this.selectedCheat = selectedCheat;
    }
}
