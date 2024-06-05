package io.github.nevalackin.radium.module;

import com.google.common.collect.ImmutableClassToInstanceMap;
import io.github.nevalackin.radium.RadiumClient;
import io.github.nevalackin.radium.module.impl.combat.*;
import io.github.nevalackin.radium.module.impl.ghost.AimAssist;
import io.github.nevalackin.radium.module.impl.ghost.AutoClicker;
import io.github.nevalackin.radium.module.impl.ghost.HitBoxExpand;
import io.github.nevalackin.radium.module.impl.ghost.Reach;
import io.github.nevalackin.radium.module.impl.movement.*;
import io.github.nevalackin.radium.module.impl.other.*;
import io.github.nevalackin.radium.module.impl.player.*;
import io.github.nevalackin.radium.module.impl.render.*;
import io.github.nevalackin.radium.module.impl.render.hud.Hud;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public final class ModuleManager {

    private final ImmutableClassToInstanceMap<Module> instanceMap;

    public ModuleManager() {
        instanceMap = putInInstanceMap(
                // Movement
                new Sprint(),
                new Speed(),
                new NoSlowdown(),
                new Flight(),
                new Step(),
                new LongJump(),
                new AntiFall(),
                new Jesus(),
                new Scaffold(),
                // Combat
                new KillAura(),
                new Velocity(),
                new Criticals(),
                new AutoPotion(),
                new Regen(),
                new TargetStrafe(),
                new AntiBot(),
                // Render
                new Hud(),
                new StorageESP(),
                new Chams(),
                new ESP(),
                new Animations(),
                new BetterChat(),
                new WorldColor(),
                new NoHurtCamera(),
                new BlockOutline(),
                new DamageParticles(),
                new EnchantGlint(),
                // Other
                new InventoryMove(),
                new PingSpoof(),
                new MemoryFix(),
                new TimeChanger(),
                new ChestStealer(),
                new AutoHypixel(),
                // Player
                new AutoFish(),
                new FastUse(),
                new NoRotate(),
                new AutoTool(),
                new InventoryManager(),
                new NoFall(),
                // Ghost
                new AimAssist(),
                new Reach(),
                new AutoClicker(),
                new HitBoxExpand());

        getModules().forEach(Module::reflectProperties);

        getModules().forEach(Module::resetPropertyValues);

        RadiumClient.getInstance().getEventBus().subscribe(this);
    }

    public void postInit() {
        getModules().forEach(Module::resetPropertyValues);
    }

    private <T extends Module> ImmutableClassToInstanceMap<Module> putInInstanceMap(Module... modules) {
        ImmutableClassToInstanceMap.Builder<Module> moduleBuilder = ImmutableClassToInstanceMap.builder();
        Arrays.stream(modules).forEach(module -> moduleBuilder.put((Class<Module>) module.getClass(), module));
        return moduleBuilder.build();
    }

    public Collection<Module> getModules() {
        return instanceMap.values();
    }

    public <T extends Module> T getModule(Class<T> moduleClass) {
        return instanceMap.getInstance(moduleClass);
    }

    public static <T extends Module> T getInstance(Class<T> clazz) {
        return RadiumClient.getInstance().getModuleManager().getModule(clazz);
    }

    public List<Module> getModulesForCategory(ModuleCategory category) {
        return getModules().stream()
                .filter(module -> module.getCategory() == category)
                .collect(Collectors.toList());
    }
}