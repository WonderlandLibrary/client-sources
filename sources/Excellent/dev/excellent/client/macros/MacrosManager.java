package dev.excellent.client.macros;


import dev.excellent.Excellent;
import dev.excellent.api.event.impl.input.KeyboardPressEvent;
import dev.excellent.api.event.impl.input.MouseInputEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.impl.util.chat.ChatUtil;
import dev.excellent.impl.util.file.FileManager;
import i.gishreloaded.protection.annotation.Native;

import java.io.File;
import java.util.ArrayList;

public class MacrosManager extends ArrayList<Macro> {
    public static File MACROS_DIRECTORY;

    @Native
    public void init() {
        MACROS_DIRECTORY = new File(FileManager.DIRECTORY, "macros");
        if (!MACROS_DIRECTORY.exists()) {
            if (MACROS_DIRECTORY.mkdir()) {
                System.out.println("Папка с списком макросов успешно создана.");
            } else {
                System.out.println("Произошла ошибка при создании папки с списком макросов.");
            }
        }

        Excellent.getInst().getEventBus().register(this);
    }

    public MacrosFile get() {
        final File file = new File(MACROS_DIRECTORY, "macros." + Excellent.getInst().getInfo().getNamespace());
        return new MacrosFile(file);
    }

    public void set() {
        final File file = new File(MACROS_DIRECTORY, "macros." + Excellent.getInst().getInfo().getNamespace());
        MacrosFile macrosFile = get();
        if (macrosFile == null) {
            macrosFile = new MacrosFile(file);
        }
        macrosFile.write();
    }

    public void addMacro(String name, int keyCode, String message) {
        Macro macros = new Macro(name, keyCode, message);
        if (!this.contains(macros)) {
            this.add(macros);
            set();
        }
    }

    public Macro getMacro(String name) {
        return this.stream().filter(macros -> macros.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public Macro getMacro(int keyCode) {
        return this.stream().filter(macros -> macros.getKeyCode() == keyCode).findFirst().orElse(null);
    }

    public void removeMacro(String name) {
        this.removeIf(macros -> macros.getName().equalsIgnoreCase(name));
        set();
    }

    public void removeMacro(int keyCode) {
        this.removeIf(macros -> macros.getKeyCode() == keyCode);
        set();
    }

    public void clearMacros() {
        this.clear();
        set();
    }

    private final Listener<KeyboardPressEvent> onKeyboardInput = event -> this.forEach(macros -> {
        if (macros.getKeyCode() == event.getKeyCode()) {
            ChatUtil.sendText(macros.getMessage());
        }
    });
    private final Listener<MouseInputEvent> onMouseInput = event -> this.forEach(macros -> {
        if (macros.getKeyCode() == event.getMouseButton()) {
            ChatUtil.sendText(macros.getMessage());
        }
    });

}
