package me.vaziak.sensation.client.api.gui.ingame.clickui.tab;

import java.util.ArrayList;

import me.vaziak.sensation.client.api.gui.ingame.clickui.Interface;
import me.vaziak.sensation.client.api.gui.ingame.clickui.components.Component;

/**
 * @author antja03
 */
public class Tab {

    private Interface theInterface;

    public final ArrayList<Component> components = new ArrayList<>();

    public Tab(Interface theInterface) {
        this.theInterface = theInterface;
    }

    public void setup() {
    }

    public void onTick() {
    }

    public ArrayList<Component> getActiveComponents() {
        return components;
    }

    public void fixPositions() {
    }

    public Interface getInterface() {
        return theInterface;
    }
}
