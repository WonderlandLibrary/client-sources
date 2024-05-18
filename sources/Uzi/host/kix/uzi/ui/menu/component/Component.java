package host.kix.uzi.ui.menu.component;

import host.kix.uzi.ui.menu.GuiMenu;

/**
 * Created by Kix on 5/29/2017.
 * Made for the eclipse project.
 */
public abstract class Component {

    /**
     * Labels the component with a tag.
     */
    private String label;

    public Component(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void mouseClicked(int mouseX, int mouseY, int button) {
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    }

}
