package xyz.mathax.mathaxclient.systems.modules;

import com.google.common.collect.Ordering;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Lifecycle;
import xyz.mathax.mathaxclient.MatHax;
import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.eventbus.EventPriority;
import xyz.mathax.mathaxclient.events.game.GameJoinedEvent;
import xyz.mathax.mathaxclient.events.game.GameLeftEvent;
import xyz.mathax.mathaxclient.events.game.OpenScreenEvent;
import xyz.mathax.mathaxclient.events.mathax.EnabledModulesChangedEvent;
import xyz.mathax.mathaxclient.events.mathax.KeyEvent;
import xyz.mathax.mathaxclient.events.mathax.ModuleBindChangedEvent;
import xyz.mathax.mathaxclient.events.mathax.MouseButtonEvent;
import xyz.mathax.mathaxclient.init.PostInit;
import xyz.mathax.mathaxclient.settings.Setting;
import xyz.mathax.mathaxclient.settings.SettingGroup;
import xyz.mathax.mathaxclient.systems.System;
import xyz.mathax.mathaxclient.systems.Systems;
import xyz.mathax.mathaxclient.systems.modules.chat.AntiSale;
import xyz.mathax.mathaxclient.systems.modules.chat.Spam;
import xyz.mathax.mathaxclient.systems.modules.combat.*;
import xyz.mathax.mathaxclient.systems.modules.movement.*;
import xyz.mathax.mathaxclient.systems.modules.movement.speed.Speed;
import xyz.mathax.mathaxclient.systems.modules.player.*;
import xyz.mathax.mathaxclient.systems.modules.render.blockesp.BlockESP;
import xyz.mathax.mathaxclient.systems.modules.world.IgnoreBorder;
import xyz.mathax.mathaxclient.systems.modules.world.Timer;
import xyz.mathax.mathaxclient.utils.Utils;
import xyz.mathax.mathaxclient.utils.input.KeyBind;
import xyz.mathax.mathaxclient.utils.misc.MatHaxIdentifier;
import xyz.mathax.mathaxclient.utils.misc.ValueComparableMap;
import xyz.mathax.mathaxclient.utils.input.Input;
import xyz.mathax.mathaxclient.utils.input.KeyAction;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;
import xyz.mathax.mathaxclient.systems.modules.misc.*;
import xyz.mathax.mathaxclient.systems.modules.render.*;
import xyz.mathax.mathaxclient.systems.modules.world.*;

import java.io.File;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

public class Modules extends System<Modules> {
    public static final File MODULES_FOLDER = new File(MatHax.VERSION_FOLDER, "Modules");

    public static final ModuleRegistry REGISTRY = new ModuleRegistry();

    private static final List<Category> CATEGORIES = new ArrayList<>();

    private static final Map<Class<? extends Module>, Module> moduleInstances = new HashMap<>();
    private static final Map<Category, List<Module>> groups = new HashMap<>();

    private static final List<Module> modules = new ArrayList<>();
    private static final List<Module> enabled = new ArrayList<>();

    private static Module moduleToBind;

    public Modules() {
        super("Modules", null);
    }

    public static Modules get() {
        return Systems.get(Modules.class);
    }

    public <T extends Module> T get(Class<T> klass) {
        return (T) moduleInstances.get(klass);
    }

    public Module get(String name) {
        for (Module module : moduleInstances.values()) {
            if (module.name.equalsIgnoreCase(name)) {
                return module;
            }
        }

        return null;
    }

    @Override
    public void init() {
        Categories.init();

        // Combat
        Category combat = Categories.Combat;
        add(new AutoArmor(combat));
        add(new AutoCity(combat));
        add(new AutoDisconnect(combat));
        add(new AutoTotem(combat));
        add(new CrystalAura(combat));
        add(new KillAura(combat));
        add(new Surround(combat));

        // Render
        Category render = Categories.Render;
        add(new Ambience(render));
        add(new BetterTooltips(render));
        add(new BlockESP(render));
        add(new BlockSelection(render));
        add(new BossStack(render));
        add(new Breadcrumbs(render));
        add(new BreakIndicators(render));
        add(new CameraTweaks(render));
        add(new Chams(render));
        add(new Confetti(render));
        add(new EntityOwner(render));
        add(new ESP(render));
        add(new Freecam(render));
        add(new FreeLook(render));
        add(new Fullbright(render));
        add(new HandView(render));
        add(new HoleESP(render));
        add(new ItemHighlight(render));
        add(new ItemPhysics(render));
        add(new Nametags(render));
        add(new NoRender(render));
        add(new PopChams(render));
        add(new StorageESP(render));
        add(new Tracers(render));
        add(new Trail(render));
        add(new Trajectories(render));
        add(new TunnelESP(render));
        add(new UnfocusedCPU(render));
        add(new VoidESP(render));
        add(new WallHack(render));
        add(new WaypointsModule(render));
        add(new Xray(render));
        add(new Zoom(render));

        // Movement
        Category movement = Categories.Movement;
        add(new AirJump(movement));
        add(new Anchor(movement));
        add(new EntityControl(movement));
        add(new GuiMove(movement));
        add(new IgnoreBorder(movement));
        add(new Speed(movement));
        add(new Sprint(movement));
        add(new TridentBoost(movement));
        add(new Velocity(movement));

        // Player
        Category player = Categories.Player;
        add(new AntiHunger(player));
        add(new AutoFish(player));
        add(new AutoGap(player));
        add(new AutoMend(player));
        add(new AutoReplenish(player));
        add(new AutoTool(player));
        add(new BreakDelay(player));
        add(new ChestSwap(player));
        add(new EndermanLook(player));
        add(new ExpThrower(player));
        add(new FastUse(player));
        add(new GhostHand(player));
        add(new InstaMine(player));
        add(new LiquidInteract(player));
        add(new NoInteract(player));
        add(new NoMiningTrace(player));
        add(new NoRotate(player));
        add(new PacketMine(player));
        add(new PotionSaver(player));
        add(new PotionSpoof(player));
        add(new Reach(player));
        add(new Rotation(player));
        add(new SpeedMine(player));

        // World
        Category world = Categories.World;
        add(new AirPlace(world));
        add(new AntiGhostBlock(world));
        add(new AutoBreed(world));
        add(new AutoMount(world));
        add(new AutoNametag(world));
        add(new AutoShearer(world));
        add(new AutoSign(world));
        add(new BuildHeight(world));
        add(new Collisions(world));
        add(new EChestFarmer(world));
        add(new Flamethrower(world));
        add(new HighwayBuilder(world));
        add(new IgnoreBorder(world));
        add(new InfinityMiner(world));
        add(new LiquidFiller(world));
        add(new Nuker(world));
        add(new SpawnProofer(world));
        add(new StashFinder(world));
        add(new TimeChanger(world));
        add(new Timer(world));
        add(new VeinMiner(world));

        // Chat
        Category chat = Categories.Chat;
        add(new Spam(chat));
        add(new AntiSale(chat));

        // Misc
        Category misc = Categories.Misc;
        add(new AutoClicker(misc));
        add(new AutoReconnect(misc));
        add(new BetterBeacons(misc));
        add(new BetterTab(misc));
        add(new GUIBackground(misc));
        add(new InventoryTweaks(misc));
        add(new MiddleClickExtra(misc));
        add(new MiddleClickFriend(misc));
        add(new MountBypass(misc));
        add(new NameProtect(misc));
        add(new Notebot(misc));
        add(new OffhandCrash(misc));
        add(new PingSpoof(misc));
        add(new PortalChat(misc));
        add(new ServerSpoof(misc));
        add(new VanillaSpoof(misc));
    }

    public void add(Module module) {
        if (!CATEGORIES.contains(module.category)) {
            throw new RuntimeException("Modules.addModule - Module's category was not registered.");
        }

        AtomicReference<Module> removedModule = new AtomicReference<>();
        if (moduleInstances.values().removeIf(module1 -> {
            if (module1.name.equals(module.name)) {
                removedModule.set(module1);
                module1.settings.unregisterColorSettings();

                return true;
            }

            return false;
        })) {
            getGroup(removedModule.get().category).remove(removedModule.get());
        }

        moduleInstances.put(module.getClass(), module);
        modules.add(module);
        getGroup(module.category).add(module);

        module.settings.registerColorSettings(module);
    }

    public static void sortModules() {
        for (List<Module> modules : groups.values()) {
            modules.sort(Comparator.comparing(module -> module.name));
        }

        modules.sort(Comparator.comparing(module -> module.name));
    }

    public static void registerCategory(Category category) {
        if (!Categories.REGISTERING) {
            throw new RuntimeException("Modules.registerCategory - Cannot register category outside of onRegisterCategories callback.");
        }

        CATEGORIES.add(category);
    }

    public static Iterable<Category> loopCategories() {
        return CATEGORIES;
    }

    public static Category getCategoryByHash(int hash) {
        for (Category category : CATEGORIES) {
            if (category.hashCode() == hash) {
                return category;
            }
        }

        return null;
    }

    public List<Module> getGroup(Category category) {
        return groups.computeIfAbsent(category, category1 -> new ArrayList<>());
    }

    public Collection<Module> getAll() {
        return moduleInstances.values();
    }

    public List<Module> getList() {
        return modules;
    }

    public int getCount() {
        return moduleInstances.values().size();
    }

    public boolean isEnabled(Class<? extends Module> klass) {
        Module module = get(klass);
        return module != null && module.isEnabled();
    }

    public List<Module> getEnabled() {
        synchronized (enabled) {
            return enabled;
        }
    }

    void addEnabled(Module module) {
        synchronized (enabled) {
            if (!enabled.contains(module)) {
                enabled.add(module);
                MatHax.EVENT_BUS.post(EnabledModulesChangedEvent.get());
            }
        }
    }

    void removeEnabled(Module module) {
        synchronized (enabled) {
            if (enabled.remove(module)) {
                MatHax.EVENT_BUS.post(EnabledModulesChangedEvent.get());
            }
        }
    }

    public Set<Module> searchNames(String text) {
        Map<Module, Integer> modules = new ValueComparableMap<>(Ordering.natural());
        for (Module module : this.moduleInstances.values()) {
            int score = Utils.searchLevenshteinDefault(module.name, text, false);
            modules.put(module, modules.getOrDefault(module, 0) + score);
        }

        return modules.keySet();
    }

    public Set<Module> searchSettingNames(String text) {
        Map<Module, Integer> modules = new ValueComparableMap<>(Ordering.natural());
        for (Module module : this.moduleInstances.values()) {
            int lowest = Integer.MAX_VALUE;
            for (SettingGroup settingGroup : module.settings) {
                for (Setting<?> setting : settingGroup) {
                    int score = Utils.searchLevenshteinDefault(setting.name, text, false);
                    if (score < lowest) {
                        lowest = score;
                    }
                }
            }

            modules.put(module, modules.getOrDefault(module, 0) + lowest);
        }

        return modules.keySet();
    }

    // Binding

    public void setModuleToBind(Module moduleToBind) {
        this.moduleToBind = moduleToBind;
    }

    public boolean isBinding() {
        return moduleToBind != null;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onKeyBinding(KeyEvent event) {
        if (event.action == KeyAction.Press && onBinding(true, event.key)) {
            event.cancel();
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onButtonBinding(MouseButtonEvent event) {
        if (event.action == KeyAction.Press && onBinding(false, event.button)) {
            event.cancel();
        }
    }

    private boolean onBinding(boolean isKey, int value) {
        if (!isBinding()) {
            return false;
        }

        if (moduleToBind.keybind.canBindTo(isKey, value)) {
            moduleToBind.keybind.set(isKey, value);
            moduleToBind.sendBound();
        } else if (value == GLFW.GLFW_KEY_ESCAPE) {
            moduleToBind.keybind.set(KeyBind.none());
            moduleToBind.info("Removed bind.");
        } else {
            return false;
        }

        MatHax.EVENT_BUS.post(ModuleBindChangedEvent.get(moduleToBind));
        moduleToBind = null;

        return true;
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onKey(KeyEvent event) {
        if (event.action == KeyAction.Repeat) {
            return;
        }

        onAction(true, event.key, event.action == KeyAction.Press);
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onMouseButton(MouseButtonEvent event) {
        if (event.action == KeyAction.Repeat) {
            return;
        }

        onAction(false, event.button, event.action == KeyAction.Press);
    }

    private void onAction(boolean isKey, int value, boolean isPress) {
        if (MatHax.mc.currentScreen == null && !Input.isKeyPressed(GLFW.GLFW_KEY_F3)) {
            for (Module module : moduleInstances.values()) {
                if (module.keybind.matches(isKey, value) && (isPress || module.toggleOnBindRelease)) {
                    module.toggle();
                    module.sendToggled();
                }
            }
        }
    }

    // End of binding

    @EventHandler(priority = EventPriority.HIGHEST + 1)
    private void onOpenScreen(OpenScreenEvent event) {
        if (!Utils.canUpdate()) {
            return;
        }

        for (Module module : moduleInstances.values()) {
            if (module.toggleOnBindRelease && module.isEnabled()) {
                module.toggle();
                module.sendToggled();
            }
        }
    }

    @EventHandler
    private void onGameJoined(GameJoinedEvent event) {
        synchronized (enabled) {
            for (Module module : modules) {
                if (module.isEnabled() && !module.alwaysRun) {
                    MatHax.EVENT_BUS.subscribe(module);
                    module.onEnable();
                }
            }
        }
    }

    @EventHandler
    private void onGameLeft(GameLeftEvent event) {
        synchronized (enabled) {
            for (Module module : modules) {
                if (module.isEnabled() && !module.alwaysRun) {
                    MatHax.EVENT_BUS.unsubscribe(module);
                    module.onDisable();
                }
            }
        }
    }

    @Override
    public void save(File folder) {
        if (folder != null) {
            folder = new File(folder, "Modules");
        } else {
            folder = MODULES_FOLDER;
        }

        for (Module module : modules) {
            module.save(folder);
        }
    }

    @Override
    public void load(File folder) {
        if (folder != null) {
            folder = new File(folder, "Modules");
        } else {
            folder = MODULES_FOLDER;
        }

        for (Module module : modules) {
            for (SettingGroup group : module.settings) {
                for (Setting<?> setting : group) {
                    setting.reset();
                }
            }

            module.load(folder);
        }
    }

    public static class ModuleRegistry extends SimpleRegistry<Module> {
        public ModuleRegistry() {
            super(RegistryKey.ofRegistry(new MatHaxIdentifier("modules")), Lifecycle.stable());
        }

        @Override
        public int size() {
            return Modules.get().getAll().size();
        }

        @Override
        public Identifier getId(Module entry) {
            return null;
        }

        @Override
        public Optional<RegistryKey<Module>> getKey(Module entry) {
            return Optional.empty();
        }

        @Override
        public int getRawId(Module entry) {
            return 0;
        }

        @Override
        public Module get(RegistryKey<Module> key) {
            return null;
        }

        @Override
        public Module get(Identifier id) {
            return null;
        }

        @Override
        public Lifecycle getEntryLifecycle(Module object) {
            return null;
        }

        @Override
        public Lifecycle getLifecycle() {
            return null;
        }

        @Override
        public Set<Identifier> getIds() {
            return null;
        }

        @Override
        public boolean containsId(Identifier id) {
            return false;
        }

        @Nullable
        @Override
        public Module get(int index) {
            return null;
        }

        @Override
        public Iterator<Module> iterator() {
            return new ModuleIterator();
        }

        @Override
        public boolean contains(RegistryKey<Module> key) {
            return false;
        }

        @Override
        public Set<Map.Entry<RegistryKey<Module>, Module>> getEntrySet() {
            return null;
        }

        @Override
        public Set<RegistryKey<Module>> getKeys() {
            return null;
        }

        @Override
        public Optional<RegistryEntry.Reference<Module>> getRandom(Random random) {
            return Optional.empty();
        }

        @Override
        public Registry<Module> freeze() {
            return null;
        }

        @Override
        public RegistryEntry.Reference<Module> createEntry(Module value) {
            return null;
        }

        @Override
        public Optional<RegistryEntry.Reference<Module>> getEntry(int rawId) {
            return Optional.empty();
        }

        @Override
        public Optional<RegistryEntry.Reference<Module>> getEntry(RegistryKey<Module> key) {
            return Optional.empty();
        }

        @Override
        public Stream<RegistryEntry.Reference<Module>> streamEntries() {
            return null;
        }

        @Override
        public Optional<RegistryEntryList.Named<Module>> getEntryList(TagKey<Module> tag) {
            return Optional.empty();
        }

        @Override
        public RegistryEntryList.Named<Module> getOrCreateEntryList(TagKey<Module> tag) {
            return null;
        }

        @Override
        public Stream<Pair<TagKey<Module>, RegistryEntryList.Named<Module>>> streamTagsAndEntries() {
            return null;
        }

        @Override
        public Stream<TagKey<Module>> streamTags() {
            return null;
        }

        @Override
        public void clearTags() {}

        @Override
        public void populateTags(Map<TagKey<Module>, List<RegistryEntry<Module>>> tagEntries) {}

        private static class ModuleIterator implements Iterator<Module> {
            private final Iterator<Module> iterator = Modules.get().getAll().iterator();

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public Module next() {
                return iterator.next();
            }
        }
    }
}
