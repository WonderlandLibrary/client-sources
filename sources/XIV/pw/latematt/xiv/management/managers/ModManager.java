package pw.latematt.xiv.management.managers;

import com.google.common.io.Files;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.command.Command;
import pw.latematt.xiv.file.XIVFile;
import pw.latematt.xiv.management.ListManager;
import pw.latematt.xiv.mod.Mod;
import pw.latematt.xiv.mod.mods.combat.*;
import pw.latematt.xiv.mod.mods.combat.aura.KillAura;
import pw.latematt.xiv.mod.mods.misc.*;
import pw.latematt.xiv.mod.mods.misc.tray.TrayIcon;
import pw.latematt.xiv.mod.mods.movement.*;
import pw.latematt.xiv.mod.mods.player.*;
import pw.latematt.xiv.mod.mods.render.*;
import pw.latematt.xiv.mod.mods.render.waypoints.Waypoints;
import pw.latematt.xiv.mod.mods.world.BlockBBFixer;
import pw.latematt.xiv.mod.mods.world.FastPlace;
import pw.latematt.xiv.mod.mods.world.Speedmine;
import pw.latematt.xiv.mod.mods.world.Tower;
import pw.latematt.xiv.utils.ChatLogger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Matthew
 */
public class ModManager extends ListManager<Mod> {
    public ModManager() {
        super(new ArrayList<>());
    }

    @Override
    public void setup() {
        XIV.getInstance().getLogger().info(String.format("Starting to setup %s.", getClass().getSimpleName()));

        contents.add(new AntiDrown());
        contents.add(new AntiHunger());
        contents.add(new AntiHurtcam());
        contents.add(new AntiSuffocate());
        contents.add(new AntiTabComplete());
        contents.add(new ArmorBreaker());
        contents.add(new AutoAccept());
        contents.add(new AutoArmor());
        contents.add(new AutoBardKit());
        contents.add(new AutoHeal());
        contents.add(new AutoLog());
        contents.add(new AutoRespawn());
        contents.add(new AutoTool());
        contents.add(new Blink());
        contents.add(new BlockBBFixer());
        contents.add(new ChestStealer());
        contents.add(new ClickTeleport());
        contents.add(new Commands());
        contents.add(new Criticals());
        contents.add(new ESP());
        contents.add(new FakeLag());
        contents.add(new FastPlace());
        contents.add(new FastUse());
        contents.add(new Fly());
        contents.add(new FovFixer());
        contents.add(new Freecam());
        contents.add(new Fullbright());
        contents.add(new HUD());
        contents.add(new InventoryPlus());
        contents.add(new Jesus());
        contents.add(new Keybinds());
        contents.add(new KillAura());
        contents.add(new MineZ());
        contents.add(new NameProtect());
        contents.add(new Nametags());
        contents.add(new NoFall());
        contents.add(new NoItems());
        contents.add(new NoPotion());
        contents.add(new NoRotationSet());
        contents.add(new NoSlowdown());
        contents.add(new Phase());
        contents.add(new PingSpoof());
        contents.add(new PotionSaver());
        contents.add(new Projectiles());
        contents.add(new Regen());
        contents.add(new Search());
        contents.add(new SkinFlash());
        contents.add(new SlimeJump());
        contents.add(new SmoothAimbot());
        contents.add(new Sneak());
        contents.add(new Speed());
        contents.add(new Speedmine());
        contents.add(new Sprint());
        contents.add(new Step());
        contents.add(new StorageESP());
        contents.add(new Tower());
        contents.add(new TrayIcon());
        contents.add(new Triggerbot());
        contents.add(new Velocity());
        contents.add(new Waypoints());
        contents.add(new XIVChat());
        contents.add(new Zoot());

        /* always load clickgui after all mods */
        contents.add(new ClickGUI());

        /* disabled mods
        contents.add(new Instinct());
        */

        new XIVFile("modconfig", "json") {
            @Override
            public void load() throws IOException {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                BufferedReader reader = new BufferedReader(new FileReader(file));
                HashMap<String, ModOptions> modOptions = gson.fromJson(reader, new TypeToken<HashMap<String, ModOptions>>() {
                }.getType());
                for (Mod mod : XIV.getInstance().getModManager().getContents()) {
                    modOptions.keySet().stream()
                            .filter(modName -> mod.getName().equals(modName))
                            .forEach(modName -> {
                                ModOptions options = modOptions.get(modName);
                                mod.setKeybind(options.getKeybind().equals(Keyboard.getKeyName(Keyboard.KEY_NONE)) ? Keyboard.KEY_NONE : Keyboard.getKeyIndex(options.getKeybind()) > 0 ? Keyboard.getKeyIndex(options.getKeybind()) : Mouse.getButtonIndex(options.getKeybind()) + 256);
                                mod.setColor(options.getColor());
                                mod.setEnabled(options.isEnabled());
                                mod.setVisible(options.isVisible());
                            });
                }
            }

            @Override
            public void save() throws IOException {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                HashMap<String, ModOptions> modOptions = new HashMap<>();
                for (Mod mod : XIV.getInstance().getModManager().getContents()) {
                    modOptions.put(mod.getName(), new ModOptions(mod.getKeybind() >= 256 ? Mouse.getButtonName(mod.getKeybind() - 256) : Keyboard.getKeyName(mod.getKeybind()), mod.getColor(), mod.isEnabled(), mod.isVisible()));
                }
                Files.write(gson.toJson(modOptions).getBytes("UTF-8"), file);
            }
        };

        Command.newCommand()
                .cmd("mods")
                .description("Provides a list of all modules.")
                .aliases("modules", "hacks", "cheats", "lm")
                .handler(message -> {
                    StringBuilder mods = new StringBuilder("Mods (" + XIV.getInstance().getModManager().getContents().size() + "): ");
                    XIV.getInstance().getModManager().getContents().stream().forEach(mod -> mods.append(mod.isEnabled() ? "\247a" : "\247c").append(mod.getName()).append("\247r, "));
                    ChatLogger.print(mods.toString().substring(0, mods.length() - 2));
                }).build();

        Command.newCommand()
                .cmd("toggle")
                .description("Toggle modules on or off.")
                .aliases("t", "tog")
                .arguments("<module>")
                .handler(message -> {
                    String[] arguments = message.split(" ");
                    if (arguments.length >= 2) {
                        String modName = arguments[1];
                        Mod mod = XIV.getInstance().getModManager().find(modName);

                        if (mod != null) {
                            if (arguments.length >= 3) {
                                mod.setEnabled(Boolean.parseBoolean(arguments[2]));
                            } else {
                                mod.toggle();
                            }
                            ChatLogger.print(String.format("%s has been toggled %s.", mod.getName(), mod.isEnabled() ? "on" : "off"));
                        } else {
                            ChatLogger.print(String.format("Invalid module \"%s\"", modName));
                        }
                    } else {
                        ChatLogger.print("Invalid arguments, valid: toggle <module>");
                    }
                }).build();

        Command.newCommand()
                .cmd("visible")
                .description("Toggle visibility of modules on or off in the arraylist.")
                .aliases("vis")
                .arguments("<module>")
                .handler(message -> {
                    String[] arguments = message.split(" ");
                    if (arguments.length >= 2) {
                        String modName = arguments[1];
                        Mod mod = XIV.getInstance().getModManager().find(modName);

                        if (mod != null) {
                            mod.setVisible(!mod.isVisible());
                            ChatLogger.print(String.format("%s will %s be shown in the arraylist.", mod.getName(), mod.isVisible() ? "now" : "no longer"));
                        } else {
                            ChatLogger.print(String.format("Invalid module \"%s\"", modName));
                        }
                    } else {
                        ChatLogger.print("Invalid arguments, valid: visible <module>");
                    }
                }).build();

        Command.newCommand()
                .cmd("bind")
                .description("Rebinds a module.")
                .arguments("<module> <key>")
                .handler(message -> {
                    String[] arguments = message.split(" ");
                    if (arguments.length >= 3) {
                        String modName = arguments[1];
                        Mod mod = XIV.getInstance().getModManager().find(modName);

                        if (mod != null) {
                            String newBindName = arguments[2].toUpperCase();
                            int newBind = Mouse.getButtonIndex(newBindName);

                            if (newBind > -1) {
                                mod.setKeybind(newBind + 256);
                                ChatLogger.print(String.format("%s is now bound to %s", mod.getName(), Mouse.getButtonName(newBind)));
                            } else {
                                newBind = Keyboard.getKeyIndex(newBindName);

                                mod.setKeybind(newBind);
                                ChatLogger.print(String.format("%s is now bound to %s", mod.getName(), Keyboard.getKeyName(newBind)));
                            }
                        } else {
                            ChatLogger.print(String.format("Invalid module \"%s\"", modName));
                        }
                    } else {
                        ChatLogger.print("Invalid arguments, valid: bind <module> <key>");
                    }
                }).build();
        XIV.getInstance().getLogger().info(String.format("Successfully setup %s, loaded %s.", getClass().getSimpleName(), getContents().size()));
    }

    public Mod find(String name) {
        for (Mod mod : getContents()) {
            if (mod.getName().toLowerCase().replaceAll(" ", "").startsWith(name.toLowerCase().replaceAll(" ", ""))) {
                return mod;
            }
        }
        return null;
    }

    private class ModOptions {
        private final String keybind;
        private final int color;
        private final boolean visible, enabled;

        public ModOptions(String keybind, int color, boolean enabled, boolean visible) {
            this.keybind = keybind;
            this.color = color;
            this.enabled = enabled;
            this.visible = visible;
        }

        public String getKeybind() {
            return keybind;
        }

        public int getColor() {
            return color;
        }

        public boolean isVisible() {
            return visible;
        }

        public boolean isEnabled() {
            return enabled;
        }
    }
}
