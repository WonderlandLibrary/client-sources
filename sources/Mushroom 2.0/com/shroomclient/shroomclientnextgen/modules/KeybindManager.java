package com.shroomclient.shroomclientnextgen.modules;

import com.shroomclient.shroomclientnextgen.annotations.RegisterListeners;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.KeyPressEvent;
import com.shroomclient.shroomclientnextgen.events.impl.KeyReleaseEvent;
import com.shroomclient.shroomclientnextgen.modules.impl.client.Notifications;
import com.shroomclient.shroomclientnextgen.modules.impl.render.Zoom;
import com.shroomclient.shroomclientnextgen.util.C;
import com.shroomclient.shroomclientnextgen.util.ThemeUtil;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;
import javax.annotation.Nullable;
import lombok.Getter;
import net.minecraft.client.gui.screen.ChatScreen;

@RegisterListeners
public class KeybindManager {

    public static final ArrayList<Class<? extends Module>> bindListeners =
        new ArrayList<>();

    @Getter
    private static final HashMap<
        Integer,
        ArrayList<Class<? extends Module>>
    > keyBinds = new HashMap<>();

    // .mushroom file my beloved
    public static File keybindsFile = new File(
        Paths.get(
            C.mc.runDirectory.getPath(),
            "mushroomSaves",
            "keybinds.mushroom"
        ).toUri()
    );
    static String[] sillyMessages = {
        "i love keybinds saving by scale!!",
        "shoutout to batman for keybinds",
        "anyone wanna kiss boys w me?",
        "*meow* nya~",
        "[insert swear word] :sunglasses:",
        "you're arn't *epic backflip*",
        "[funny message here]",
        "t.me/escamas1337 on top",
    };
    static String keybindsFileText;

    public static void unBind(Class<? extends Module> clazz, boolean save) {
        keyBinds
            .keySet()
            .stream()
            .filter(i -> keyBinds.get(i).contains(clazz))
            .findAny()
            .ifPresent(i -> {
                ArrayList<Class<? extends Module>> a = keyBinds.get(i);
                a.remove(clazz);
                keyBinds.put(i, a);
            });

        if (save) saveBinds();
    }

    public static void listenForBind(Class<? extends Module> clazz) {
        unBind(clazz, true);
        bindListeners.add(clazz);
    }

    public static void bind(
        int key,
        Class<? extends Module> clazz,
        boolean save
    ) {
        unBind(clazz, save);

        if (keyBinds.containsKey(key)) {
            ArrayList<Class<? extends Module>> a = keyBinds.get(key);
            a.add(clazz);
            keyBinds.put(key, a);
        } else {
            ArrayList<Class<? extends Module>> a = new ArrayList<>();
            a.add(clazz);
            keyBinds.put(key, a);
        }

        if (save) {
            Notifications.notify(
                "Bound " +
                ModuleManager.getModuleWithInfo(ModuleManager.getModule(clazz))
                    .an()
                    .name() +
                " to " +
                (char) key,
                ThemeUtil.themeColors()[0],
                1
            );

            saveBinds();
        }
    }

    public static void saveBinds() {
        String sillyMessage =
            sillyMessages[(int) (Math.random() * sillyMessages.length)];
        String configs = sillyMessage + "\n";
        System.out.println(
            "saved keybinds with note \"" + sillyMessage + "\" ;3"
        );

        for (ModuleManager.ModuleWithInfo mod : ModuleManager.getModules()) {
            int keybind = getKeyChar(mod.module().getClass());

            if (keybind != 0) {
                configs += "\n" + mod.an().uniqueId() + " : " + keybind + " |";
            }
        }

        try {
            Files.write(keybindsFile.toPath(), configs.getBytes());
        } catch (IOException e) {
            System.out.println("90s computer LOL!!! U A LOSER");
        }
    }

    public static void loadBind(Module module) {
        if (keybindsFileText != null) {
            ModuleManager.ModuleWithInfo m = ModuleManager.getModuleWithInfo(
                module
            );
            if (keybindsFileText.contains(m.an().uniqueId() + " :")) {
                int key = Integer.parseInt(
                    keybindsFileText
                        .split(m.an().uniqueId() + " : ")[1].split(" \\|")[0]
                );
                bind(key, module.getClass(), false);

                System.out.println("bound " + m.an().uniqueId() + " to " + key);
            }
        }
    }

    public static @Nullable String getKeyBind(Class<? extends Module> clazz) {
        if (bindListeners.contains(clazz)) return "LISTENING";
        AtomicReference<String> k = new AtomicReference<>(null);
        keyBinds
            .keySet()
            .stream()
            .filter(i -> keyBinds.get(i).contains(clazz))
            .findAny()
            .ifPresent(i -> {
                k.set(("" + ((char) (int) i)).toUpperCase());
            });
        return k.get();
    }

    public static int getKeyChar(Class<? extends Module> clazz) {
        AtomicReference<Integer> k = new AtomicReference<>(0);
        keyBinds
            .keySet()
            .stream()
            .filter(i -> keyBinds.get(i).contains(clazz))
            .findAny()
            .ifPresent(k::set);
        return k.get();
    }

    @SubscribeEvent
    public void onKeyRelease(KeyReleaseEvent e) {
        if (keyBinds.containsKey(e.key)) {
            ArrayList<Class<? extends Module>> a = keyBinds.get(e.key);
            for (Class<? extends Module> clazz : a) {
                if (clazz == Zoom.class) ModuleManager.getModule(
                    clazz
                ).setEnabled(false, false);
            }
        }
    }

    @SubscribeEvent
    public void onKeybindPress(KeyPressEvent e) {
        if (e.key == 46) C.mc.setScreen(new ChatScreen(""));

        if (keyBinds.containsKey(e.key)) {
            ArrayList<Class<? extends Module>> a = keyBinds.get(e.key);
            for (Class<? extends Module> clazz : a) {
                ModuleManager.getModule(clazz).toggle(false);
            }
        }

        for (Class<? extends Module> clazz : bindListeners) {
            if (e.key == 259) { // Backspace
                unBind(clazz, true);
                Notifications.notify(
                    "Unbound " +
                    ModuleManager.getModuleWithInfo(
                        ModuleManager.getModule(clazz)
                    )
                        .an()
                        .name(),
                    ThemeUtil.themeColors()[0],
                    1
                );
            } else {
                bind(e.key, clazz, true);
            }
        }
        bindListeners.clear();
    }
}
