package de.Hero.clickgui;

import de.Hero.clickgui.elements.ModuleButton;

import java.util.Comparator;

public class ElementSorter implements Comparator<ModuleButton> {
    @Override
    public int compare(ModuleButton o1, ModuleButton o2) {
        return o1.mod.name.compareTo(o2.mod.name);
    }
}