package io.github.liticane.electron;

import io.github.liticane.electron.event.api.EventBus;
import io.github.liticane.electron.module.ModuleManager;
import lombok.Getter;

@Getter
public class Electron {
    private static Electron instance;

    private final EventBus eventBus;
    private final ModuleManager moduleManager;

    private Electron() {
        this.eventBus = new EventBus();
        this.moduleManager = new ModuleManager();
    }

    public void start() {
        moduleManager.init();
    }

    public void stop() {

    }

    public static Electron getInstance() {
        if (instance == null)
            instance = new Electron();

        return instance;
    }
}