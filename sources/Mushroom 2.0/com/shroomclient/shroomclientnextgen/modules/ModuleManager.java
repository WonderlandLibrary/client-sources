package com.shroomclient.shroomclientnextgen.modules;

import com.shroomclient.shroomclientnextgen.ShroomClient;
import com.shroomclient.shroomclientnextgen.annotations.RegisterListeners;
import com.shroomclient.shroomclientnextgen.config.ConfigHide;
import com.shroomclient.shroomclientnextgen.config.ConfigMaxFor;
import com.shroomclient.shroomclientnextgen.config.ConfigMinFor;
import com.shroomclient.shroomclientnextgen.config.types.*;
import com.shroomclient.shroomclientnextgen.events.Bus;
import com.shroomclient.shroomclientnextgen.protection.JnicInclude;
import com.shroomclient.shroomclientnextgen.util.C;
import com.shroomclient.shroomclientnextgen.util.RenderUtil;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class ModuleManager {

    private static final HashMap<
        Class<? extends Module>,
        ModuleWithInfo
    > modules = new HashMap<>();
    public static String configFileBaseString = Paths.get(
        C.mc.runDirectory.getPath(),
        "mushroomSaves"
    ).toString();
    public static File configsFileBase = new File(configFileBaseString);
    public static File configsFile = new File(
        configFileBaseString + "\\" + ShroomClient.selectedConfig
    );
    public static String configsFileText = "";
    // i thought this was a good idea :)
    static String[] sillyMessages = {
        "mushroom client configs!!! (shoutout to scale)",
        "112batman? more like 112BLACKMAN!!!! LOL",
        "rise on top long live the wood",
        "swig addons private on top :pray:",
        "i love lesbian anime girls",
        "welcome to the configs folder, enjoy ur stay!",
        "hello there!!!! ily <3",
        "hmu bud, t.me/escamas1337",
        "is this the best config system ever?",
        "who wanna kiss boys w me?",
        "*swings pendulum* \"you will let me lick your feet\"",
        "i got beamed by scale1337 :pray:",
    };
    public static boolean didFinishInit = false;

    public static ArrayList<ModuleWithInfo> getModules() {
        ArrayList<ModuleWithInfo> m = new ArrayList<>();
        for (ModuleWithInfo i : modules.values()) {
            m.add(i);
        }
        return m;
    }

    @JnicInclude
    public static void init() {
        if (configsFileBase.isDirectory()) {
            for (File file : Objects.requireNonNull(
                Paths.get(ModuleManager.configsFileBase.toURI())
                    .toFile()
                    .listFiles()
            )) {
                try {
                    if (Files.readString(file.toPath()).contains("[LOADED] ")) {
                        ShroomClient.selectedConfig = file.getName();
                        configsFile = new File(
                            configFileBaseString +
                            "\\" +
                            ShroomClient.selectedConfig
                        );
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        if (configsFile.isFile()) {
            System.out.println("reading config file: " + configsFile.getPath());
            try {
                configsFileText = Files.readString(configsFile.toPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (KeybindManager.keybindsFile.isFile()) {
            System.out.println(
                "reading keybinds file: " +
                KeybindManager.keybindsFile.getPath()
            );
            try {
                KeybindManager.keybindsFileText = Files.readString(
                    KeybindManager.keybindsFile.toPath()
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // Insert silly license checking code here
        // License check code may NOT be in another function, see https://jnic.dev/documentation/#writing-more-secure-code
        // $$$INSERT$$$___LICENSE-CHECK-1___$$$INSERT$$$
        // --------------------------------

        for (Class<?> clazz : C.reflections.getTypesAnnotatedWith(
            RegisterListeners.class
        )) {
            RegisterListeners ann = clazz.getAnnotation(
                RegisterListeners.class
            );
            if (ann.registerStatic()) {
                Bus.register(clazz, null);
            } else {
                try {
                    Bus.register(clazz, clazz.newInstance());
                } catch (Exception ex) {
                    C.logger.error("Couldn't instantiate class");
                    ex.printStackTrace();
                }
            }
        }

        // $$$INSERT$$$___LICENSE-CHECK-4___$$$INSERT$$$

        for (Class<?> clazz : C.reflections.getTypesAnnotatedWith(
            RegisterModule.class
        )) {
            try {
                RegisterModule an = clazz.getAnnotation(RegisterModule.class);

                Module m = (Module) clazz.newInstance();
                m.setEnabled(an.enabledByDefault(), true, false);
                modules.put(
                    (Class<? extends Module>) clazz,
                    new ModuleWithInfo(m, clazz, an)
                );
                load(m);
                Bus.register(clazz, m);
                KeybindManager.loadBind(m);
            } catch (Exception ex) {
                C.logger.error("Couldn't create module");
                ex.printStackTrace();
            }
        }

        for (ModuleWithInfo mod : modules.values()) {
            mod.module.postEnabledStateHandlers();
        }

        didFinishInit = true;
    }

    public static <T extends Module> T getModule(Class<T> clazz) {
        return (T) modules.get(clazz).module;
    }

    public static List<Module> getModulesByCategory(ModuleCategory category) {
        return modules
            .values()
            .stream()
            .filter(m -> m.an.category().equals(category))
            .map(m -> m.module)
            .collect(Collectors.toList());
    }

    // slow maybe
    public static List<Module> getSortedModulesByLengthAndCategory(
        ModuleCategory category
    ) {
        return modules
            .values()
            .stream()
            .filter(m -> m.an.category().equals(category))
            .sorted(
                Comparator.comparingDouble(
                    e -> RenderUtil.getFontWidth(e.an.name())
                )
            )
            .map(m -> m.module)
            .collect(Collectors.toList());
    }

    public static List<Module> getSortedModulesByAlphabetAndCategory(
        ModuleCategory category
    ) {
        return modules
            .values()
            .stream()
            .filter(m -> m.an.category().equals(category))
            .sorted(Comparator.comparingInt(e -> e.an.name().compareTo("a")))
            .map(m -> m.module)
            .collect(Collectors.toList());
    }

    public static List<Module> getSortedModulesByLength() {
        return sortedModules;
    }

    public static List<Module> sortedModules;

    // sort once!!
    public static void sortModulesByLength() {
        sortedModules = modules
            .values()
            .stream()
            .sorted(
                Comparator.comparingDouble(
                    e -> RenderUtil.getFontWidth(e.an.name())
                )
            )
            .map(m -> m.module)
            .collect(Collectors.toList());
    }

    public static List<Module> getModulesByToggledState(boolean toggled) {
        return modules
            .values()
            .stream()
            .filter(m -> m.module.isEnabled() == toggled)
            .map(m -> m.module)
            .collect(Collectors.toList());
    }

    public static ModuleWithInfo getModuleWithInfo(Module module) {
        return modules.get(module.getClass());
    }

    public static ModuleWithInfo getModuleWithInfo(
        Class<? extends Module> clazz
    ) {
        return modules.get(clazz);
    }

    public static boolean isEnabled(Class clazz) {
        return ModuleManager.getModule(clazz).isEnabled();
    }

    public static void setEnabled(
        Class<? extends Module> clazz,
        boolean flag,
        boolean silent
    ) {
        modules.get(clazz).module.setEnabled(flag, silent);
    }

    public static void setEnabled(Module module, boolean flag, boolean silent) {
        setEnabled(module.getClass(), flag, silent);
    }

    public static void toggle(Class<? extends Module> clazz, boolean silent) {
        modules.get(clazz).module.toggle(silent);
    }

    public static void toggle(Module module, boolean silent) {
        toggle(module.getClass(), silent);
    }

    public static List<ConfigOption> getModuleConfig(
        Module module,
        boolean includeHidden,
        boolean mergeMinMax
    ) {
        HashMap<String, Field> minFields = new HashMap<>();
        HashMap<String, Field> maxFields = new HashMap<>();

        ArrayList<ConfigOption> l = new ArrayList<>();
        for (Field f : module.getClass().getDeclaredFields()) {
            if (
                f.isAnnotationPresent(
                    com.shroomclient.shroomclientnextgen.config.ConfigOption.class
                ) &&
                (includeHidden || !f.isAnnotationPresent(ConfigHide.class))
            ) {
                com.shroomclient.shroomclientnextgen.config.ConfigOption ann =
                    f.getAnnotation(
                        com.shroomclient.shroomclientnextgen.config.ConfigOption.class
                    );

                if (mergeMinMax) {
                    if (f.isAnnotationPresent(ConfigMinFor.class)) {
                        ConfigMinFor minAnn = f.getAnnotation(
                            ConfigMinFor.class
                        );
                        if (maxFields.containsKey(minAnn.value())) {
                            l.add(
                                new ConfigOptionMinMax(
                                    new ConfigOptionNumber<>(f, module, ann),
                                    new ConfigOptionNumber<>(
                                        maxFields.get(minAnn.value()),
                                        module,
                                        maxFields
                                            .get(minAnn.value())
                                            .getAnnotation(
                                                com.shroomclient.shroomclientnextgen.config.ConfigOption.class
                                            )
                                    ),
                                    ann.name(),
                                    ann.description(),
                                    ann.order(),
                                    f
                                )
                            );
                        } else {
                            minFields.put(minAnn.value(), f);
                            continue;
                        }
                    } else if (f.isAnnotationPresent(ConfigMaxFor.class)) {
                        ConfigMaxFor maxAnn = f.getAnnotation(
                            ConfigMaxFor.class
                        );
                        if (minFields.containsKey(maxAnn.value())) {
                            l.add(
                                new ConfigOptionMinMax(
                                    new ConfigOptionNumber<>(
                                        minFields.get(maxAnn.value()),
                                        module,
                                        minFields
                                            .get(maxAnn.value())
                                            .getAnnotation(
                                                com.shroomclient.shroomclientnextgen.config.ConfigOption.class
                                            )
                                    ),
                                    new ConfigOptionNumber<>(f, module, ann),
                                    ann.name(),
                                    ann.description(),
                                    ann.order(),
                                    f
                                )
                            );
                        } else {
                            maxFields.put(maxAnn.value(), f);
                            continue;
                        }
                    }
                }

                Class<?> t = f.getType();
                if (
                    t.equals(Integer.class) ||
                    t.equals(Long.class) ||
                    t.equals(Float.class) ||
                    t.equals(Double.class)
                ) {
                    l.add(new ConfigOptionNumber(f, module, ann));
                } else if (t.equals(Boolean.class)) {
                    l.add(new ConfigOptionBoolean(f, module, ann));
                } else if (t.isEnum()) {
                    l.add(new ConfigOptionEnum(f, module, ann));
                    // TODO ill add color selector thingy too - scale
                } else {
                    throw new RuntimeException("Unsupported config field type");
                }
            }
        }
        return l;
    }

    public static List<ConfigOption> getModuleConfig(
        Module module,
        boolean includeHidden
    ) {
        return getModuleConfig(module, includeHidden, false);
    }

    @JnicInclude
    public static void removeOldConfig() {
        //File old = new File(System.getenv("APPDATA") + "\\mushroomSaves\\"+ ShroomClient.selectedConfig);
        //if (old.isFile()) {

        // for loop incase they have multiple loaded
        for (File file : Objects.requireNonNull(
            Paths.get(ModuleManager.configsFileBase.toURI())
                .toFile()
                .listFiles()
        )) {
            try {
                String oldText = Files.readString(file.toPath());
                if (oldText.contains("[LOADED] ")) {
                    oldText = oldText.replaceAll("\\[LOADED] ", "");
                    Files.write(file.toPath(), oldText.getBytes());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // This should write the current field values to a file
    // ok blackman (i made it just save every single module every time gui is closed, sowwy :( but it saves fps as u dont write to file every tick holding a slider down!)
    @JnicInclude
    public static void save() {
        String sillyMessage =
            sillyMessages[(int) (Math.random() * sillyMessages.length)];
        String configs = "[LOADED] " + sillyMessage + "\n";
        System.out.println(
            "saved configs with note \"" + sillyMessage + "\" ;3"
        );

        for (ModuleWithInfo module : modules.values()) {
            //System.out.println(module.module.getClass().getName() + ":" + module.module.isEnabled());
            configs +=
            "|" +
            module.an.uniqueId() +
            ":" +
            module.module.isEnabled() +
            "|\n";

            for (ConfigOption opt : ModuleManager.getModuleConfig(
                module.module,
                true
            )) {
                //System.out.println("saving option: " + module.module.getClass().getName() + "." + opt.getName());
                if (opt instanceof ConfigOptionBoolean b) {
                    //System.out.println("|"+module.module.getClass().getName() + "." + opt.getName() + ":" + (b.get() ? "true" : "false") + "|\n");
                    configs +=
                    "|" +
                    module.an.uniqueId() +
                    "." +
                    opt.getName() +
                    ":" +
                    (b.get() ? "true" : "false") +
                    "|\n";
                } else if (opt instanceof ConfigOptionEnum<?> e) {
                    //System.out.println(module.module.getClass().getName() + "." + opt.getName() + ":" + e.getOption().getValue() + "\n");
                    configs +=
                    "|" +
                    module.an.uniqueId() +
                    "." +
                    opt.getName() +
                    ":" +
                    e.getOption().getIndex() +
                    "|\n";
                } else if (opt instanceof ConfigOptionNumber<?> n) {
                    //System.out.println(module.module.getClass().getName() + "." + opt.getName() + ":" + n.get() + "\n");
                    configs +=
                    "|" +
                    module.an.uniqueId() +
                    "." +
                    opt.getName() +
                    ":" +
                    n.get() +
                    "|\n";
                }
            }

            configs += "\n";
        }

        try {
            Files.write(configsFile.toPath(), configs.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // This should load the field values from a value, and for all values present in the file, update the instance passed here with those values
    // This should also call setEnabled() with the values from the config (if present), if not present don't call it at all
    @JnicInclude
    public static void load(Module module) {
        if (configsFile.isFile()) {
            ModuleWithInfo m = ModuleManager.getModuleWithInfo(module);
            if (configsFileText.contains("|" + m.an.uniqueId() + ":")) {
                String state = configsFileText
                    .split("\\|" + m.an.uniqueId() + ":")[1].split("\\|")[0];
                module.setEnabled(Objects.equals(state, "true"), true, false);
            }

            for (ConfigOption opt : ModuleManager.getModuleConfig(
                module,
                true
            )) {
                try {
                    configsFileText = Files.readString(configsFile.toPath());
                } catch (IOException e) {
                    System.out.println("fail reading file again lol ;3");
                    throw new RuntimeException(e);
                }

                if (
                    configsFileText.contains(
                        "|" + m.an.uniqueId() + "." + opt.getName() + ":"
                    )
                ) {
                    String extraState = configsFileText
                        .split(
                            "\\|" +
                            m.an.uniqueId() +
                            "\\." +
                            opt.getName() +
                            ":"
                        )[1].split("\\|")[0];

                    if (opt instanceof ConfigOptionBoolean b) {
                        b.set(Objects.equals(extraState, "true"));
                    } else if (opt instanceof ConfigOptionEnum<?> e) {
                        // maybe not smart idrk
                        ((ConfigOptionEnum<Object>) e).set(
                                e
                                    .getOption()
                                    .setInt(Integer.parseInt(extraState))
                                    .getValue()
                            );
                    } else if (opt instanceof ConfigOptionNumber<?> n) {
                        Class<?> t = n.getType();
                        if (t.equals(Integer.class)) ((ConfigOptionNumber<
                                    Integer
                                >) n).set(
                                Integer.valueOf(extraState.split("\\.")[0])
                            );
                        else if (t.equals(Long.class)) ((ConfigOptionNumber<
                                    Long
                                >) n).set(Long.valueOf(extraState));
                        else if (t.equals(Float.class)) ((ConfigOptionNumber<
                                    Float
                                >) n).set(Float.valueOf(extraState));
                        else if (t.equals(Double.class)) ((ConfigOptionNumber<
                                    Double
                                >) n).set(Double.valueOf(extraState));
                    }
                }
            }

            try {
                configsFileText = Files.readString(configsFile.toPath());
            } catch (IOException e) {
                System.out.println("fail reading file again lol ;3");
                throw new RuntimeException(e);
            }
        } else {
            try {
                if (configsFileBase.mkdirs() && configsFile.createNewFile()) {
                    System.out.println(
                        "creating file: " + configsFile.getPath()
                    );
                    Files.write(
                        configsFile.toPath(),
                        "mushroom client configs!!! (shoutout to scale)\n".getBytes()
                    );
                } else {
                    System.out.println("failed making file??");
                }
            } catch (IOException e) {
                System.out.println(
                    "90s ahh computer, grow up (failed to create configs file???)"
                );
                throw new RuntimeException(e);
            }

            String sillyMessage =
                sillyMessages[(int) (Math.random() * sillyMessages.length)];
            String configs = sillyMessage + "\n";
            System.out.println(
                "saved configs with note \"" + sillyMessage + "\" ;3"
            );

            for (ModuleWithInfo moduleh : modules.values()) {
                Module mod = moduleh.module;
                //System.out.println(module.module.getClass().getName() + ":" + module.module.isEnabled());
                configs +=
                "|" +
                moduleh.an.uniqueId() +
                ":" +
                module
                    .getClass()
                    .getAnnotation(RegisterModule.class)
                    .enabledByDefault() +
                "|\n";

                for (ConfigOption opt : ModuleManager.getModuleConfig(
                    mod,
                    true
                )) {
                    //System.out.println("saving option: " + module.module.getClass().getName() + "." + opt.getName());
                    if (opt instanceof ConfigOptionBoolean b) {
                        //System.out.println("|"+module.module.getClass().getName() + "." + opt.getName() + ":" + (b.get() ? "true" : "false") + "|\n");
                        configs +=
                        "|" +
                        moduleh.an.uniqueId() +
                        "." +
                        opt.getName() +
                        ":" +
                        (b.get() ? "true" : "false") +
                        "|\n";
                    } else if (opt instanceof ConfigOptionEnum<?> e) {
                        //System.out.println(module.module.getClass().getName() + "." + opt.getName() + ":" + e.getOption().getValue() + "\n");
                        configs +=
                        "|" +
                        moduleh.an.uniqueId() +
                        "." +
                        opt.getName() +
                        ":" +
                        e.getOption().getIndex() +
                        "|\n";
                    } else if (opt instanceof ConfigOptionNumber<?> n) {
                        //System.out.println(module.module.getClass().getName() + "." + opt.getName() + ":" + n.get() + "\n");
                        configs +=
                        "|" +
                        moduleh.an.uniqueId() +
                        "." +
                        opt.getName() +
                        ":" +
                        n.get() +
                        "|\n";
                    }
                }

                configs += "\n";
            }

            try {
                Files.write(configsFile.toPath(), configs.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public record ModuleWithInfo(
        Module module,
        Class<?> clazz,
        RegisterModule an
    ) {}
}
