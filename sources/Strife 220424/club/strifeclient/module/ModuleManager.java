package club.strifeclient.module;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import club.strifeclient.Client;
import club.strifeclient.event.implementations.system.KeyEvent;
import club.strifeclient.module.implementations.combat.*;
import club.strifeclient.module.implementations.exploit.Disabler;
import club.strifeclient.module.implementations.movement.*;
import club.strifeclient.module.implementations.player.*;
import club.strifeclient.module.implementations.visual.*;
import club.strifeclient.setting.Mode;
import club.strifeclient.setting.ModeEnum;
import club.strifeclient.setting.Setting;
import club.strifeclient.setting.implementations.ModeSetting;
import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.MutableClassToInstanceMap;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ModuleManager {

    private final ClassToInstanceMap<Module> modulesMap = MutableClassToInstanceMap.create();

    public void init() {
        Client.INSTANCE.getEventBus().register(this);
        add(new Sprint(),
                new Velocity(),
                new NoFall(),
                new Speed(),
                new NoSlowdown(),
                new SpeedMine(),
                new FastUse(),
                new InventoryMove(),
                new TargetStrafe(),
                new ChestStealer(),
                new InventoryManager(),
                new Scaffold(),
                new AutoClicker(),
                new HitBox(),
                new KillAura(),
                new AutoPotion(),
                new MotionBlur(),
                new Criticals(),
                new TimerSpeed(),
                new WTap(),
                new Reach(),
                new Disabler(),
                new NoGuiClose(),
                new FastSneak(),
                new ClickGUI(),
                new ItemPhysics(),
                new HUD(),
                new GlintColorize(),
                new Animations(),
                new Ambience(),
                new CameraClip(),
                new Fullbright(),
                new LowFire(),
                new ESP(),
                new NoBob(),
                new NoFov(),
                new NoHurtCam(),
                new Flight());
        registerProperties();
        System.out.println("Modules have been initialized.");
    }

    public void deInit() {
        modulesMap.forEach((clazz, module) -> module.onDisable());
        modulesMap.clear();
        Client.INSTANCE.getEventBus().unregister(this);
    }

    public void registerProperties() {
        modulesMap.values().forEach(module -> {
            for (Field field : module.getClass().getDeclaredFields()) {
                if (Setting.class.isAssignableFrom(field.getType())) {
                    if (!field.isAccessible()) field.setAccessible(true);
                    try {
                        final Setting<?> setting = (Setting<?>) field.get(module);
                        module.settings.add(setting);
                        try {
                            final ModeSetting<? extends ModeEnum<?>> modeSetting = (ModeSetting<? extends ModeEnum<?>>) setting;
                            ModeEnum<?>[] modes = modeSetting.getValues();
                            Arrays.stream(modes).forEach(enumMode -> {
                                final Mode<?> mode = enumMode.getMode();
                                if(modeSetting.getValue() == mode.getRepresentation())
                                    modeSetting.getValue().getMode().onSelect();
                                setting.addChangeCallback((old, value) -> {
                                    if (value.getValue() == mode.getRepresentation()) {
                                        mode.onSelect();
                                        if (module.isEnabled()) mode.onEnable();
                                    } else {
                                        mode.onDeselect();
                                        if (module.isEnabled()) mode.onDisable();
                                    }
                                });
                                module.addToggleCallback(enabled -> {
                                    if (mode.isSelected()) {
                                        if (enabled) mode.onEnable();
                                        else mode.onDisable();
                                    }
                                });
                                Class<?> modeClass = mode.getClass();
                                for (Field subDeclaredField : modeClass.getDeclaredFields()) {
                                    if (Setting.class.isAssignableFrom(subDeclaredField.getType())) {
                                        if (!subDeclaredField.isAccessible()) subDeclaredField.setAccessible(true);
                                        try {
                                            final Setting<?> subSetting = (Setting<?>) subDeclaredField.get(mode);
                                            final Supplier<Boolean> checked = subSetting.getDependency();
                                            subSetting.setDependency(() -> checked.get() && mode.isSelected());
                                            module.settings.add(subSetting);
                                        } catch (IllegalAccessException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            });
                        } catch (Exception ignored) {}
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void add(Module... modules) {
        Arrays.stream(modules).forEach(module -> {
            module.init();
            modulesMap.put(module.getClass(), module);
        });
    }

    @EventHandler
    private final Listener<KeyEvent> keyEventListener = e -> {
        modulesMap.values().stream().filter(module -> module.keybind == e.key).forEach(module -> {
            module.setEnabled(!module.enabled);
        });
    };

    public Collection<Module> getModules() {
        return modulesMap.values();
    }

    public Collection<Module> getModules(final Predicate<Module> predicate) {
        return modulesMap.values().stream().filter(predicate).collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public <T extends Module> T getModule(String name) {
        return (T) modulesMap.values().stream().filter(module -> module.name.trim().equalsIgnoreCase(name) ||
                Arrays.stream(module.aliases).anyMatch(name::equalsIgnoreCase)).findFirst().orElse(null);
    }

    public <T extends Module> T getModule(Class<T> clazz) {
        return modulesMap.getInstance(clazz);
    }
}
