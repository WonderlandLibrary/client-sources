package xyz.mathax.mathaxclient.gui.tabs;

import xyz.mathax.mathaxclient.init.PreInit;
import xyz.mathax.mathaxclient.gui.tabs.builtin.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tabs {
    private static final List<Tab> tabs = new ArrayList<>();

    private static final Map<Class<? extends Tab>, Tab> tabInstances = new HashMap<>();

    @PreInit
    public static void init() {
        add(new ConfigTab());
        add(new AccountTab());
        add(new ModulesTab());
        add(new HudTab());
        add(new GuiTab());
        add(new FriendsTab());
        add(new EnemiesTab());
        add(new MacrosTab());
        add(new ProfilesTab());
        add(new FakePlayersTab());
        add(new BaritoneTab());
    }

    public static void add(Tab tab) {
        tabs.add(tab);
        tabInstances.put(tab.getClass(), tab);
    }

    public static List<Tab> get() {
        return tabs;
    }

    public static <T extends Tab> T get(Class<T> klass) {
        return (T) tabInstances.get(klass);
    }
}
