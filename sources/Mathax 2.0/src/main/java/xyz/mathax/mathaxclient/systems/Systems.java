package xyz.mathax.mathaxclient.systems;

import xyz.mathax.mathaxclient.MatHax;
import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.game.GameLeftEvent;
import xyz.mathax.mathaxclient.init.PreInit;
import xyz.mathax.mathaxclient.systems.accounts.Accounts;
import xyz.mathax.mathaxclient.systems.commands.Commands;
import xyz.mathax.mathaxclient.systems.config.Config;
import xyz.mathax.mathaxclient.systems.enemies.Enemies;
import xyz.mathax.mathaxclient.systems.friends.Friends;
import xyz.mathax.mathaxclient.systems.hud.Hud;
import xyz.mathax.mathaxclient.systems.macros.Macros;
import xyz.mathax.mathaxclient.systems.modules.Modules;
import xyz.mathax.mathaxclient.systems.profiles.Profiles;
import xyz.mathax.mathaxclient.systems.proxies.Proxies;
import xyz.mathax.mathaxclient.systems.themes.Themes;
import xyz.mathax.mathaxclient.systems.waypoints.Waypoints;
import xyz.mathax.mathaxclient.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Systems {
    private static final Map<Class<? extends System>, System<?>> systems = new HashMap<>();

    private static final List<Runnable> preLoadTasks = new ArrayList<>(1);
    private static final List<Runnable> postLoadTasks = new ArrayList<>(1);

    public static boolean loaded = false;

    public static void addPreLoadTask(Runnable task) {
        preLoadTasks.add(task);
    }

    public static void addPostLoadTask(Runnable task) {
        postLoadTasks.add(task);
    }

    @PreInit
    public static void init() {
        System<?> config = add(new Config());
        config.init();
        config.load();

        add(new Themes());
        add(new Modules());
        add(new Commands());
        add(new Friends());
        add(new Enemies());
        add(new Macros());
        add(new Accounts());
        add(new Waypoints());
        add(new Profiles());
        add(new Proxies());
        add(new Hud());

        MatHax.EVENT_BUS.subscribe(Systems.class);
    }

    public static <T extends System<?>> T get(Class<T> klass) {
        return (T) systems.get(klass);
    }

    private static System<?> add(System<?> system) {
        systems.put(system.getClass(), system);
        MatHax.EVENT_BUS.subscribe(system);
        system.init();

        return system;
    }

    @EventHandler
    private static void onGameLeft(GameLeftEvent event) {
        save();
    }

    public static void save(File folder) {
        long start = Utils.getCurrentTimeMillis();

        MatHax.LOG.info("Saving...");

        for (System<?> system : systems.values()) {
            system.save(folder);
        }

        MatHax.LOG.info("Saved in {} milliseconds.", Utils.getCurrentTimeMillis() - start);
    }

    public static void save() {
        save(null);
    }

    public static void load(File folder) {
        long start = Utils.getCurrentTimeMillis();

        MatHax.LOG.info("Loading...");

        for (Runnable task : preLoadTasks) {
            task.run();
        }

        for (System<?> system : systems.values()) {
            system.load(folder);
        }

        for (Runnable task : postLoadTasks) {
            task.run();
        }

        loaded = true;

        MatHax.LOG.info("Loaded in {} milliseconds.", Utils.getCurrentTimeMillis() - start);
    }

    public static void load() {
        load(null);
    }
}
