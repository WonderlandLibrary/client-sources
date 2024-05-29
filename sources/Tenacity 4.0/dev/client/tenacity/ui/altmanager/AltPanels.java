package dev.client.tenacity.ui.altmanager;

import dev.client.tenacity.ui.altmanager.panels.AltListAltPanel;
import dev.client.tenacity.ui.altmanager.panels.KingAltsPanel;
import dev.client.tenacity.ui.altmanager.panels.LoginAltPanel;

import java.util.ArrayList;
import java.util.Arrays;

public class AltPanels {
    private final ArrayList<AltPanel> altPanels = new ArrayList<>();

    public AltPanels() {
        addPanels();
    }

    public void addPanels() {
        altPanels.addAll(Arrays.asList(
                new LoginAltPanel(),
                new AltListAltPanel()
        ));
    }

    public ArrayList<AltPanel> getPanels() {
        return altPanels;
    }

    public AltPanel getPanel(Class<? extends AltPanel> panel) {
        return getPanels().stream().filter(pan -> panel == pan.getClass()).findFirst().orElse(null);
    }
}
