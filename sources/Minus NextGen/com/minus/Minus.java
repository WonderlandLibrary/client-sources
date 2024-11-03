package com.minus;

import com.minus.commands.CommandManager;
import com.minus.module.ModuleManager;
import lombok.Getter;
import me.zero.alpine.bus.EventBus;
import me.zero.alpine.bus.EventManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
public class Minus {
    public static final String MOD_ID = "minus-client";
    public static final Minus instance = new Minus();
    public static final String name = "Minus", version = "1.0";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    private EventBus eventBus;
    private CommandManager commandManager;
    public static boolean cguiEnabled = false;
    public ModuleManager moduleManager;
    public void init(){
        LOGGER.info("{Minus} Initializing");
        eventBus = EventManager
                .builder()
                .setName(name)
                .setSuperListeners()
                .build();
        commandManager = new CommandManager();
        moduleManager = new ModuleManager();
        LOGGER.info("{Minus} Initialized Event Bus");
        LOGGER.info("{Minus} Initialized");
    }
}
