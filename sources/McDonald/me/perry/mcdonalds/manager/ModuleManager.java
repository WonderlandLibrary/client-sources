// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.manager;

import java.util.concurrent.TimeUnit;
import me.perry.mcdonalds.McDonalds;
import me.perry.mcdonalds.util.Util;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import me.perry.mcdonalds.features.gui.McDonaldsGui;
import org.lwjgl.input.Keyboard;
import java.util.Collection;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Comparator;
import me.perry.mcdonalds.event.events.Render3DEvent;
import me.perry.mcdonalds.event.events.Render2DEvent;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import java.util.stream.Stream;
import java.util.function.Consumer;
import java.util.Objects;
import net.minecraftforge.common.MinecraftForge;
import java.util.Arrays;
import java.util.Iterator;
import me.perry.mcdonalds.features.modules.render.Swing;
import me.perry.mcdonalds.features.modules.render.FullBright;
import me.perry.mcdonalds.features.modules.render.NoRender;
import me.perry.mcdonalds.features.modules.render.ViewModel;
import me.perry.mcdonalds.features.modules.render.ArrowESP;
import me.perry.mcdonalds.features.modules.render.ESP;
import me.perry.mcdonalds.features.modules.render.Trajectories;
import me.perry.mcdonalds.features.modules.render.HandChams;
import me.perry.mcdonalds.features.modules.render.SmallShield;
import me.perry.mcdonalds.features.modules.render.Wireframe;
import me.perry.mcdonalds.features.modules.render.Skeleton;
import me.perry.mcdonalds.features.modules.render.HoleESP;
import me.perry.mcdonalds.features.modules.render.BlockHighlight;
import me.perry.mcdonalds.features.modules.player.FastBow;
import me.perry.mcdonalds.features.modules.player.Quiver;
import me.perry.mcdonalds.features.modules.player.Freecam;
import me.perry.mcdonalds.features.modules.player.Blink;
import me.perry.mcdonalds.features.modules.player.Burrow;
import me.perry.mcdonalds.features.modules.player.FastPlace;
import me.perry.mcdonalds.features.modules.player.Speedmine;
import me.perry.mcdonalds.features.modules.player.LiquidInteract;
import me.perry.mcdonalds.features.modules.player.MCP;
import me.perry.mcdonalds.features.modules.player.MultiTask;
import me.perry.mcdonalds.features.modules.player.TpsSync;
import me.perry.mcdonalds.features.modules.player.FakePlayer;
import me.perry.mcdonalds.features.modules.player.Replenish;
import me.perry.mcdonalds.features.modules.movement.Velocity;
import me.perry.mcdonalds.features.modules.movement.Sprint;
import me.perry.mcdonalds.features.modules.movement.NoSlowDown;
import me.perry.mcdonalds.features.modules.movement.Anchor;
import me.perry.mcdonalds.features.modules.movement.PacketFly;
import me.perry.mcdonalds.features.modules.movement.Flight;
import me.perry.mcdonalds.features.modules.movement.Step;
import me.perry.mcdonalds.features.modules.movement.Speed;
import me.perry.mcdonalds.features.modules.movement.NoVoid;
import me.perry.mcdonalds.features.modules.movement.ReverseStep;
import me.perry.mcdonalds.features.modules.misc.AutoRespawn;
import me.perry.mcdonalds.features.modules.misc.BetterPortals;
import me.perry.mcdonalds.features.modules.misc.XCarry;
import me.perry.mcdonalds.features.modules.misc.PopCounter;
import me.perry.mcdonalds.features.modules.misc.McDonaldsDupe;
import me.perry.mcdonalds.features.modules.misc.NoHitBox;
import me.perry.mcdonalds.features.modules.misc.Tracker;
import me.perry.mcdonalds.features.modules.misc.ToolTips;
import me.perry.mcdonalds.features.modules.misc.AutoGG;
import me.perry.mcdonalds.features.modules.misc.PearlNotify;
import me.perry.mcdonalds.features.modules.misc.MCF;
import me.perry.mcdonalds.features.modules.misc.ChatModifier;
import me.perry.mcdonalds.features.modules.misc.BuildHeight2;
import me.perry.mcdonalds.features.modules.misc.BuildHeight;
import me.perry.mcdonalds.features.modules.misc.NoHandShake;
import me.perry.mcdonalds.features.modules.misc.ExtraTab;
import me.perry.mcdonalds.features.modules.combat.Selftrap;
import me.perry.mcdonalds.features.modules.combat.AntiTrap;
import me.perry.mcdonalds.features.modules.combat.AutoWeb;
import me.perry.mcdonalds.features.modules.combat.W0M3NMode;
import me.perry.mcdonalds.features.modules.combat.HoleFiller2;
import me.perry.mcdonalds.features.modules.combat.SelfFill;
import me.perry.mcdonalds.features.modules.combat.AutoSelfAnvil;
import me.perry.mcdonalds.features.modules.combat.AutoArmor;
import me.perry.mcdonalds.features.modules.combat.HoleFiller;
import me.perry.mcdonalds.features.modules.combat.Criticals;
import me.perry.mcdonalds.features.modules.combat.Killaura;
import me.perry.mcdonalds.features.modules.combat.AutoCrystal;
import me.perry.mcdonalds.features.modules.combat.AutoTrap;
import me.perry.mcdonalds.features.modules.combat.Surround;
import me.perry.mcdonalds.features.modules.combat.Offhand;
import me.perry.mcdonalds.features.modules.client.RPC;
import me.perry.mcdonalds.features.modules.client.HUD;
import me.perry.mcdonalds.features.modules.client.FontMod;
import me.perry.mcdonalds.features.modules.client.ClickGui;
import java.util.List;
import me.perry.mcdonalds.features.modules.Module;
import java.util.ArrayList;
import me.perry.mcdonalds.features.Feature;

public class ModuleManager extends Feature
{
    public ArrayList<Module> modules;
    public List<Module> sortedModules;
    public List<String> sortedModulesABC;
    public Animation animationThread;
    
    public ModuleManager() {
        this.modules = new ArrayList<Module>();
        this.sortedModules = new ArrayList<Module>();
        this.sortedModulesABC = new ArrayList<String>();
    }
    
    public void init() {
        this.modules.add(new ClickGui());
        this.modules.add(new FontMod());
        this.modules.add(new HUD());
        this.modules.add(new RPC());
        this.modules.add(new Offhand());
        this.modules.add(new Surround());
        this.modules.add(new AutoTrap());
        this.modules.add(new AutoCrystal());
        this.modules.add(new Killaura());
        this.modules.add(new Criticals());
        this.modules.add(new HoleFiller());
        this.modules.add(new AutoArmor());
        this.modules.add(new AutoSelfAnvil());
        this.modules.add(new SelfFill());
        this.modules.add(new HoleFiller2());
        this.modules.add(new W0M3NMode());
        this.modules.add(new AutoWeb());
        this.modules.add(new AntiTrap());
        this.modules.add(new Selftrap());
        this.modules.add(new ExtraTab());
        this.modules.add(new NoHandShake());
        this.modules.add(new BuildHeight());
        this.modules.add(new BuildHeight2());
        this.modules.add(new ChatModifier());
        this.modules.add(new MCF());
        this.modules.add(new PearlNotify());
        this.modules.add(new AutoGG());
        this.modules.add(new ToolTips());
        this.modules.add(new Tracker());
        this.modules.add(new NoHitBox());
        this.modules.add(new McDonaldsDupe());
        this.modules.add(new PopCounter());
        this.modules.add(new XCarry());
        this.modules.add(new BetterPortals());
        this.modules.add(new AutoRespawn());
        this.modules.add(new ReverseStep());
        this.modules.add(new NoVoid());
        this.modules.add(new Speed());
        this.modules.add(new Step());
        this.modules.add(new Flight());
        this.modules.add(new PacketFly());
        this.modules.add(new Anchor());
        this.modules.add(new NoSlowDown());
        this.modules.add(new Sprint());
        this.modules.add(new Velocity());
        this.modules.add(new Replenish());
        this.modules.add(new FakePlayer());
        this.modules.add(new TpsSync());
        this.modules.add(new MultiTask());
        this.modules.add(new MCP());
        this.modules.add(new LiquidInteract());
        this.modules.add(new Speedmine());
        this.modules.add(new FastPlace());
        this.modules.add(new Burrow());
        this.modules.add(new Blink());
        this.modules.add(new Freecam());
        this.modules.add(new Quiver());
        this.modules.add(new FastBow());
        this.modules.add(new BlockHighlight());
        this.modules.add(new HoleESP());
        this.modules.add(new Skeleton());
        this.modules.add(new Wireframe());
        this.modules.add(new SmallShield());
        this.modules.add(new HandChams());
        this.modules.add(new Trajectories());
        this.modules.add(new ESP());
        this.modules.add(new ArrowESP());
        this.modules.add(new ViewModel());
        this.modules.add(new NoRender());
        this.modules.add(new FullBright());
        this.modules.add(new Swing());
    }
    
    public Module getModuleByName(final String name) {
        for (final Module module : this.modules) {
            if (!module.getName().equalsIgnoreCase(name)) {
                continue;
            }
            return module;
        }
        return null;
    }
    
    public <T extends Module> T getModuleByClass(final Class<T> clazz) {
        for (final Module module : this.modules) {
            if (!clazz.isInstance(module)) {
                continue;
            }
            return (T)module;
        }
        return null;
    }
    
    public void enableModule(final Class<Module> clazz) {
        final Module module = this.getModuleByClass(clazz);
        if (module != null) {
            module.enable();
        }
    }
    
    public void disableModule(final Class<Module> clazz) {
        final Module module = this.getModuleByClass(clazz);
        if (module != null) {
            module.disable();
        }
    }
    
    public void enableModule(final String name) {
        final Module module = this.getModuleByName(name);
        if (module != null) {
            module.enable();
        }
    }
    
    public void disableModule(final String name) {
        final Module module = this.getModuleByName(name);
        if (module != null) {
            module.disable();
        }
    }
    
    public boolean isModuleEnabled(final String name) {
        final Module module = this.getModuleByName(name);
        return module != null && module.isOn();
    }
    
    public boolean isModuleEnabled(final Class<Module> clazz) {
        final Module module = this.getModuleByClass(clazz);
        return module != null && module.isOn();
    }
    
    public Module getModuleByDisplayName(final String displayName) {
        for (final Module module : this.modules) {
            if (!module.getDisplayName().equalsIgnoreCase(displayName)) {
                continue;
            }
            return module;
        }
        return null;
    }
    
    public ArrayList<Module> getEnabledModules() {
        final ArrayList<Module> enabledModules = new ArrayList<Module>();
        for (final Module module : this.modules) {
            if (!module.isEnabled()) {
                continue;
            }
            enabledModules.add(module);
        }
        return enabledModules;
    }
    
    public ArrayList<String> getEnabledModulesName() {
        final ArrayList<String> enabledModules = new ArrayList<String>();
        for (final Module module : this.modules) {
            if (module.isEnabled()) {
                if (!module.isDrawn()) {
                    continue;
                }
                enabledModules.add(module.getFullArrayString());
            }
        }
        return enabledModules;
    }
    
    public ArrayList<Module> getModulesByCategory(final Module.Category category) {
        final ArrayList<Module> modulesCategory = new ArrayList<Module>();
        final ArrayList<Module> list;
        this.modules.forEach(module -> {
            if (module.getCategory() == category) {
                list.add(module);
            }
            return;
        });
        return modulesCategory;
    }
    
    public List<Module.Category> getCategories() {
        return Arrays.asList(Module.Category.values());
    }
    
    public void onLoad() {
        final Stream<Object> filter = this.modules.stream().filter(Module::listening);
        final EventBus event_BUS = MinecraftForge.EVENT_BUS;
        Objects.requireNonNull(event_BUS);
        filter.forEach(event_BUS::register);
        this.modules.forEach(Module::onLoad);
    }
    
    public void onUpdate() {
        this.modules.stream().filter(Feature::isEnabled).forEach(Module::onUpdate);
    }
    
    public void onTick() {
        this.modules.stream().filter(Feature::isEnabled).forEach(Module::onTick);
    }
    
    public void onRender2D(final Render2DEvent event) {
        this.modules.stream().filter(Feature::isEnabled).forEach(module -> module.onRender2D(event));
    }
    
    public void onRender3D(final Render3DEvent event) {
        this.modules.stream().filter(Feature::isEnabled).forEach(module -> module.onRender3D(event));
    }
    
    public void sortModules(final boolean reverse) {
        this.sortedModules = this.getEnabledModules().stream().filter(Module::isDrawn).sorted(Comparator.comparing(module -> this.renderer.getStringWidth(module.getFullArrayString()) * (reverse ? -1 : 1))).collect((Collector<? super Object, ?, List<Module>>)Collectors.toList());
    }
    
    public void sortModulesABC() {
        (this.sortedModulesABC = new ArrayList<String>(this.getEnabledModulesName())).sort(String.CASE_INSENSITIVE_ORDER);
    }
    
    public void onLogout() {
        this.modules.forEach(Module::onLogout);
    }
    
    public void onLogin() {
        this.modules.forEach(Module::onLogin);
    }
    
    public void onUnload() {
        final ArrayList<Module> modules = this.modules;
        final EventBus event_BUS = MinecraftForge.EVENT_BUS;
        Objects.requireNonNull(event_BUS);
        modules.forEach(event_BUS::unregister);
        this.modules.forEach(Module::onUnload);
    }
    
    public void onUnloadPost() {
        for (final Module module : this.modules) {
            module.enabled.setValue(false);
        }
    }
    
    public void onKeyPressed(final int eventKey) {
        if (eventKey == 0 || !Keyboard.getEventKeyState() || ModuleManager.mc.currentScreen instanceof McDonaldsGui) {
            return;
        }
        this.modules.forEach(module -> {
            if (module.getBind().getKey() == eventKey) {
                module.toggle();
            }
        });
    }
    
    private class Animation extends Thread
    {
        public Module module;
        public float offset;
        public float vOffset;
        ScheduledExecutorService service;
        
        public Animation() {
            super("Animation");
            this.service = Executors.newSingleThreadScheduledExecutor();
        }
        
        @Override
        public void run() {
            if (HUD.getInstance().renderingMode.getValue() == HUD.RenderingMode.Length) {
                for (final Module module : ModuleManager.this.sortedModules) {
                    final String text = module.getDisplayName() + ChatFormatting.GRAY + ((module.getDisplayInfo() != null) ? (" [" + ChatFormatting.WHITE + module.getDisplayInfo() + ChatFormatting.GRAY + "]") : "");
                    module.offset = ModuleManager.this.renderer.getStringWidth(text) / (float)HUD.getInstance().animationHorizontalTime.getValue();
                    module.vOffset = ModuleManager.this.renderer.getFontHeight() / (float)HUD.getInstance().animationVerticalTime.getValue();
                    if (module.isEnabled() && HUD.getInstance().animationHorizontalTime.getValue() != 1) {
                        if (module.arrayListOffset <= module.offset) {
                            continue;
                        }
                        if (Util.mc.world == null) {
                            continue;
                        }
                        final Module module3 = module;
                        module3.arrayListOffset -= module.offset;
                        module.sliding = true;
                    }
                    else {
                        if (!module.isDisabled()) {
                            continue;
                        }
                        if (HUD.getInstance().animationHorizontalTime.getValue() == 1) {
                            continue;
                        }
                        if (module.arrayListOffset < ModuleManager.this.renderer.getStringWidth(text) && Util.mc.world != null) {
                            final Module module4 = module;
                            module4.arrayListOffset += module.offset;
                            module.sliding = true;
                        }
                        else {
                            module.sliding = false;
                        }
                    }
                }
            }
            else {
                for (final String e : ModuleManager.this.sortedModulesABC) {
                    final Module module2 = McDonalds.moduleManager.getModuleByName(e);
                    final String text2 = module2.getDisplayName() + ChatFormatting.GRAY + ((module2.getDisplayInfo() != null) ? (" [" + ChatFormatting.WHITE + module2.getDisplayInfo() + ChatFormatting.GRAY + "]") : "");
                    module2.offset = ModuleManager.this.renderer.getStringWidth(text2) / (float)HUD.getInstance().animationHorizontalTime.getValue();
                    module2.vOffset = ModuleManager.this.renderer.getFontHeight() / (float)HUD.getInstance().animationVerticalTime.getValue();
                    if (module2.isEnabled() && HUD.getInstance().animationHorizontalTime.getValue() != 1) {
                        if (module2.arrayListOffset <= module2.offset) {
                            continue;
                        }
                        if (Util.mc.world == null) {
                            continue;
                        }
                        final Module module5 = module2;
                        module5.arrayListOffset -= module2.offset;
                        module2.sliding = true;
                    }
                    else {
                        if (!module2.isDisabled()) {
                            continue;
                        }
                        if (HUD.getInstance().animationHorizontalTime.getValue() == 1) {
                            continue;
                        }
                        if (module2.arrayListOffset < ModuleManager.this.renderer.getStringWidth(text2) && Util.mc.world != null) {
                            final Module module6 = module2;
                            module6.arrayListOffset += module2.offset;
                            module2.sliding = true;
                        }
                        else {
                            module2.sliding = false;
                        }
                    }
                }
            }
        }
        
        @Override
        public void start() {
            System.out.println("Starting animation thread.");
            this.service.scheduleAtFixedRate(this, 0L, 1L, TimeUnit.MILLISECONDS);
        }
    }
}
