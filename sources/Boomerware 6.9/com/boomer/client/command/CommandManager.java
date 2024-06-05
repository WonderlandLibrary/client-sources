package com.boomer.client.command;

import com.boomer.client.command.impl.*;
import com.boomer.client.event.bus.Handler;
import com.boomer.client.Client;
import com.boomer.client.command.impl.*;
import com.boomer.client.event.events.game.ChatEvent;
import com.boomer.client.module.Module;
import com.boomer.client.utils.Printer;
import com.boomer.client.utils.value.impl.EnumValue;
import org.lwjgl.input.Keyboard;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    public Map<String, Command> map = new HashMap<>();

    public void initialize() {
        register(Bind.class);
        register(Friend.class);
        register(Vclip.class);
        register(Teleport.class);
        register(ConfigCMD.class);
        register(Help.class);
        register(Macros.class);
        register(StaffDetect.class);
        register(Waypoint.class);
        register(AntiStrike.class);
        register(Toggle.class);
        register(Modules.class);
        Client.INSTANCE.getBus().bind(this);
    }

    @Handler
    public void onPacket(ChatEvent event) {
        final String message = event.getMsg();
        if (message.startsWith(".")) {
            event.setCanceled(true);
            dispatch(message.substring(1));
        }
    }

    private void register(Class<? extends Command> commandClass) {
        try {
            Command createdCommand = commandClass.newInstance();
            map.put(createdCommand.getLabel().toLowerCase(), createdCommand);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dispatch(final String s) {
        final String[] command = s.split(" ");
        if (command.length > 1) {
            Module m = Client.INSTANCE.getModuleManager().getModule(command[0]);
            if (m != null) {
                if (command[1].equalsIgnoreCase("help")) {
                    if (!m.getValues().isEmpty()) {
                        Printer.print(m.getLabel() + "'s available properties are:");
                        m.getValues().forEach(prop -> {
                            Printer.print(prop.getLabel() + ": " + prop.getValue());
                            if (prop instanceof EnumValue) {
                                Printer.print(prop.getLabel() + "'s available modes are:");
                                StringBuilder modes = new StringBuilder();
                                for (Enum vals : ((EnumValue) prop).getConstants()) {
                                    modes.append(vals);
                                    modes.append(" ");
                                }
                                Printer.print(modes.toString());
                            }
                        });
                    } else {
                        Printer.print("This cheat has no properties.");
                    }
                    Printer.print(m.getLabel() + " is a " + (m.isHidden() ? "hidden " : "shown ") + "module.");
                    Printer.print(m.getLabel() + " is bound to " + Keyboard.getKeyName(m.getKeybind()) + ".");
                    return;
                }
                if (command.length > 2) {
                    if (command[1].equalsIgnoreCase("hidden")) {
                        m.setHidden(Boolean.parseBoolean(command[2].toLowerCase()));
                        Printer.print("Set " + m.getLabel() + " to " + m.isHidden() + ".");
                        return;
                    }
                    m.getValues().forEach(v -> {
                        if (v.getLabel().replace(" ","").toLowerCase().equals(command[1].toLowerCase())) {
                            v.setValue(command[2]);
                            Printer.print("Set " + command[0] + " " + v.getLabel() + " to " + v.getValue() + ".");
                            return;
                        }
                    });
                }
            }
        }
        Client.INSTANCE.getCommandManager().getCommandMap().values().forEach(c -> {
            for (String handle : c.getHandles()) {
                if (handle.toLowerCase().equals(command[0])) c.onRun(command);
            }
        });
    }

    public Map<String, Command> getCommandMap() {
        return map;
    }
}

