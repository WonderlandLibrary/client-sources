package dev.darkmoon.client.ui.menu.altmanager.alt;

import java.util.ArrayList;

public class AltManager {
    public static ArrayList<Alt> registry = new ArrayList<>();

    public ArrayList<Alt> getRegistry() {
        return registry;
    }
    public static void addAccount(Alt alt) {
        registry.add(alt);
    }
    public static void removeAccount(Alt alt) {
        registry.remove(alt);
    }

    public static void clearAccounts() {
        registry.clear();
    }
}
