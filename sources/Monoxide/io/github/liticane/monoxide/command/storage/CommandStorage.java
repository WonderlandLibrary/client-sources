package io.github.liticane.monoxide.command.storage;

import io.github.liticane.monoxide.listener.handling.EventHandling;
import org.reflections.Reflections;
import io.github.liticane.monoxide.command.Command;
import io.github.liticane.monoxide.command.data.CommandInfo;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class CommandStorage extends ArrayList<Command> {

    private static CommandStorage instance;

    public void init() {
        EventHandling.getInstance().registerListener(this);
        final Reflections reflections = new Reflections("io.github.liticane.monoxide");
        reflections.getTypesAnnotatedWith(CommandInfo.class).forEach(aClass -> {
            try {
                this.add((Command) aClass.getDeclaredConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                     InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }

    public static CommandStorage getInstance() {
        return instance;
    }

    public static void setInstance(CommandStorage instance) {
        CommandStorage.instance = instance;
    }
}
