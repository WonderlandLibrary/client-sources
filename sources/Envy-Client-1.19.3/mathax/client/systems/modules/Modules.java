package mathax.client.systems.modules;

import com.google.common.collect.Ordering;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Lifecycle;
import mathax.client.MatHax;
import mathax.client.eventbus.EventHandler;
import mathax.client.eventbus.EventPriority;
import mathax.client.events.game.GameJoinedEvent;
import mathax.client.events.game.GameLeftEvent;
import mathax.client.events.game.OpenScreenEvent;
import mathax.client.events.mathax.ActiveModulesChangedEvent;
import mathax.client.events.mathax.KeyEvent;
import mathax.client.events.mathax.ModuleBindChangedEvent;
import mathax.client.events.mathax.MouseButtonEvent;
import mathax.client.settings.Setting;
import mathax.client.settings.SettingGroup;
import mathax.client.systems.System;
import mathax.client.systems.Systems;
import mathax.client.systems.config.Config;
import mathax.client.systems.modules.chat.*;
import mathax.client.systems.modules.client.*;
import mathax.client.systems.modules.client.swarm.Swarm;
import mathax.client.systems.modules.combat.*;
import mathax.client.systems.modules.crash.*;
import mathax.client.systems.modules.experimental.*;
import mathax.client.systems.modules.ghost.*;
import mathax.client.systems.modules.misc.*;
import mathax.client.systems.modules.movement.*;
import mathax.client.systems.modules.movement.elytrafly.ElytraFly;
import mathax.client.systems.modules.movement.speed.Speed;
import mathax.client.systems.modules.player.*;
import mathax.client.systems.modules.render.*;
import mathax.client.systems.modules.render.marker.Marker;
import mathax.client.systems.modules.render.search.Search;
import mathax.client.systems.modules.world.*;
import mathax.client.systems.modules.world.Timer;
import mathax.client.utils.Utils;
import mathax.client.utils.misc.ChatUtils;
import mathax.client.utils.misc.ValueComparableMap;
import mathax.client.utils.misc.input.Input;
import mathax.client.utils.misc.input.KeyAction;
import mathax.client.utils.render.ToastSystem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import java.io.File;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

import static mathax.client.MatHax.mc;

public class Modules extends System<Modules> {
    public static final ModuleRegistry REGISTRY = new ModuleRegistry();

    private static final List<Category> CATEGORIES = new ArrayList<>();

    private final List<Module> modules = new ArrayList<>();
    private final Map<Class<? extends Module>, Module> moduleInstances = new HashMap<>();
    private final Map<Category, List<Module>> groups = new HashMap<>();

    private final List<Module> active = new ArrayList<>();
    private Module moduleToBind;

    public Modules() {
        super("Modules");
    }

    public static Modules get() {
        return Systems.get(Modules.class);
    }

    @Override
    public void init() {
        initCombat();
        initPlayer();
        initMovement();
        initRender();
        initWorld();
        initChat();
        initMisc();
        initClient();
        initGhost();
        initFun();
        initExperimental();
        initCrash();
    }

    @Override
    public void load(File folder) {
        for (Module module : modules) {
            for (SettingGroup group : module.settings) {
                for (Setting<?> setting : group) setting.reset();
            }
        }

        super.load(folder);
    }

    public void sortModules() {
        for (List<Module> modules : groups.values()) {
            modules.sort(Comparator.comparing(o -> o.title));
        }

        modules.sort(Comparator.comparing(o -> o.title));
    }

    public static void registerCategory(Category category) {
        if (!Categories.REGISTERING) throw new RuntimeException("Modules.registerCategory - Cannot register category outside of onRegisterCategories callback.");

        CATEGORIES.add(category);
    }

    public static Iterable<Category> loopCategories() {
        return CATEGORIES;
    }

    public static Category getCategoryByHash(int hash) {
        for (Category category : CATEGORIES) {
            if (category.hashCode() == hash) return category;
        }

        return null;
    }

    public <T extends Module> T get(Class<T> klass) {
        return (T) moduleInstances.get(klass);
    }

    public Module get(String name) {
        for (Module module : moduleInstances.values()) {
            if (module.name.equalsIgnoreCase(name)) return module;
        }

        return null;
    }

    public boolean isActive(Class<? extends Module> klass) {
        Module module = get(klass);
        return module != null && module.isActive();
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

    public List<Module> getActive() {
        synchronized (active) {
            return active;
        }
    }

    public Set<Module> searchTitles(String text) {
        Map<Module, Integer> modules = new ValueComparableMap<>(Ordering.natural().reverse());

        for (Module module : this.moduleInstances.values()) {
            int words = Utils.search(module.title, text);
            if (words > 0) modules.put(module, modules.getOrDefault(module, 0) + words);
        }

        return modules.keySet();
    }

    public Set<Module> searchSettingTitles(String text) {
        Map<Module, Integer> modules = new ValueComparableMap<>(Ordering.natural().reverse());

        for (Module module : this.moduleInstances.values()) {
            for (SettingGroup sg : module.settings) {
                for (Setting<?> setting : sg) {
                    int words = Utils.search(setting.title, text);
                    if (words > 0) modules.put(module, modules.getOrDefault(module, 0) + words);
                }
            }
        }

        return modules.keySet();
    }

    void addActive(Module module) {
        synchronized (active) {
            if (!active.contains(module)) {
                active.add(module);
                MatHax.EVENT_BUS.post(ActiveModulesChangedEvent.get());
            }
        }
    }

    void removeActive(Module module) {
        synchronized (active) {
            if (active.remove(module)) MatHax.EVENT_BUS.post(ActiveModulesChangedEvent.get());
        }
    }

    // Binding

    public void setModuleToBind(Module moduleToBind) {
        this.moduleToBind = moduleToBind;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onKeyBinding(KeyEvent event) {
        if (onBinding(true, event.key)) event.cancel();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onButtonBinding(MouseButtonEvent event) {
        if (onBinding(false, event.button)) event.cancel();
    }

    private boolean onBinding(boolean isKey, int value) {
        if (moduleToBind != null && moduleToBind.keybind.canBindTo(isKey, value)) {
            if (value != GLFW.GLFW_KEY_ESCAPE) {
                moduleToBind.keybind.set(isKey, value);
                ChatUtils.info("KeyBinds", "Module (highlight)%s (default)bound to (highlight)%s(default).", moduleToBind.title, moduleToBind.keybind);
                mc.getToastManager().add(new ToastSystem(moduleToBind.icon, moduleToBind.category.color, moduleToBind.title, null, Formatting.GRAY + "Bound to " + Formatting.WHITE + moduleToBind.keybind + Formatting.GRAY + ".", Config.get().toastDuration.get()));
            }

            MatHax.EVENT_BUS.post(ModuleBindChangedEvent.get(moduleToBind));
            moduleToBind = null;
            return true;
        }

        return false;
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onKey(KeyEvent event) {
        if (event.action == KeyAction.Repeat) return;
        onAction(true, event.key, event.action == KeyAction.Press);
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onMouseButton(MouseButtonEvent event) {
        if (event.action == KeyAction.Repeat) return;
        onAction(false, event.button, event.action == KeyAction.Press);
    }

    private void onAction(boolean isKey, int value, boolean isPress) {
        if (mc.currentScreen == null && !Input.isKeyPressed(GLFW.GLFW_KEY_F3)) {
            for (Module module : moduleInstances.values()) {
                if (module.keybind.matches(isKey, value) && (isPress || module.toggleOnBindRelease)) {
                    module.toggle();
                    module.sendToggledMsg(module.name, module);
                    module.sendToggledToast(module.name, module);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST + 1)
    private void onOpenScreen(OpenScreenEvent event) {
        if (!Utils.canUpdate()) return;

        for (Module module : moduleInstances.values()) {
            if (module.toggleOnBindRelease && module.isActive()) {
                module.toggle();
                module.sendToggledMsg(module.name, module);
                module.sendToggledToast(module.name, module);
            }
        }
    }

    @EventHandler
    private void onGameJoined(GameJoinedEvent event) {
        synchronized (active) {
            for (Module module : modules) {
                if (module.isActive() && !module.runInMainMenu) {
                    MatHax.EVENT_BUS.subscribe(module);
                    module.onActivate();
                }
            }
        }
    }

    @EventHandler
    private void onGameLeft(GameLeftEvent event) {
        synchronized (active) {
            for (Module module : modules) {
                if (module.isActive() && !module.runInMainMenu) {
                    MatHax.EVENT_BUS.unsubscribe(module);
                    module.onDeactivate();
                }
            }
        }
    }

    public void disableAll() {
        synchronized (active) {
            for (Module module : modules) {
                if (module.isActive()) module.toggle();
            }
        }
    }

    @Override
    public NbtCompound toTag() {
        NbtCompound tag = new NbtCompound();

        NbtList modulesTag = new NbtList();
        for (Module module : getAll()) {
            NbtCompound moduleTag = module.toTag();
            if (moduleTag != null) modulesTag.add(moduleTag);
        }
        tag.put("modules", modulesTag);

        return tag;
    }

    @Override
    public Modules fromTag(NbtCompound tag) {
        disableAll();

        NbtList modulesTag = tag.getList("modules", 10);
        for (NbtElement moduleTagI : modulesTag) {
            NbtCompound moduleTag = (NbtCompound) moduleTagI;
            Module module = get(moduleTag.getString("name"));
            if (module != null) module.fromTag(moduleTag);
        }

        return this;
    }

    public void add(Module module) {
        if (!CATEGORIES.contains(module.category)) throw new RuntimeException("Modules.addModule - Module's category was not registered.");

        AtomicReference<Module> removedModule = new AtomicReference<>();
        if (moduleInstances.values().removeIf(module1 -> {
            if (module1.name.equals(module.name)) {
                removedModule.set(module1);
                module1.settings.unregisterColorSettings();
                return true;
            }

            return false;
        })) getGroup(removedModule.get().category).remove(removedModule.get());

        moduleInstances.put(module.getClass(), module);
        modules.add(module);
        getGroup(module.category).add(module);

        module.settings.registerColorSettings(module);
    }

    private void initCombat() {
        add(new KillAuraButNOt());
        add(new AnchorAura());
        add(new SyracuseAimAssist());
        add(new AntiAnvil());
        add(new AntiCrystalPhase());
        add(new AntiSurround());
        add(new AutoCrystal());
        add(new ArrowDodge());
        add(new Auto32K());
        add(new AutoAnvil());
        add(new AutoArmor());
        add(new AutoCity());
        add(new AutoEXP());
        add(new AutoLog());
        add(new TargetStrafe());
        //add(new AutoMinecart());
        add(new AutoPot());
        add(new AutoTotem());
        add(new AutoTrap());
        add(new AutoWeapon());
        add(new AutoWeb());
        add(new BedAura());
        add(new BedrockWalk());
        add(new BidenAura());
        add(new BidenHatesBots());
        add(new BidenZoom());
        //add(new Block());
        add(new BowAimbot());
        //add(new BowSpam());
        add(new BowBomb());
        add(new Burrow());
        add(new CEVBreaker());
        add(new Confuse());
        add(new Criticals());
        add(new CrystalAura());
        //add(new FastBow());
        add(new Hitboxes());
        add(new HoleFiller());
        //add(new InfiniteReach());
        add(new InstaAutoCity());
        add(new KillAura());
        add(new KillAuraBetter());
        add(new KillAuraButBad());
        add(new KillAuraButBad2());
        add(new KillAuraPlus());
        add(new Offhand());
        add(new PistonAura());
        add(new NoPortalHitbox());
        add(new Quiver());
        add(new SelfAnvil());
        add(new SelfProtect());
        add(new SelfTrap());
        add(new SelfTrapPlus());
        add(new SelfWeb());
        add(new Suicide());
        add(new OffHandPlus());
        add(new ShieldBypass());
        add(new Sniper());
        add(new Surround());
        add(new SurroundBreak());
        add(new TNTAura());
        add(new TriggerBot());
        add(new ZKillaura());
    }

    private void initPlayer() {
        add(new AntiHunger());
        add(new AfkLogout());
        add(new AntiSpawnpoint());
        add(new AutoBedCraft());
        add(new AutoCraft());
        add(new AutoEat());
        add(new AutoFish());
        add(new AutoGap());
        add(new AutoMend());
        add(new AutoReplenish());
        add(new AutoTool());
        add(new ChestSwap());
        add(new EXPThrower());
        add(new FastEat());
        add(new Ghost());
        add(new GhostHand());
        add(new LiquidInteract());
        add(new NoBreakDelay());
        add(new NoInteract());
        add(new NoMiningTrace());
        add(new NoRotate());
        add(new RainbowArmor());
        //add(new PacketMine());
        add(new PaperClickTp());
        add(new Portals());
        add(new InventoryScroll());
        add(new HeadRoll());
        add(new PotionSaver());
        add(new PotionSpoof());
        add(new Rotation());
        //add(new ShieldSpoof());
        add(new SpeedMine());
        add(new LoliFinder());
        add(new VehicleOneHit());
        add(new Worldborder());
    }

    private void add(LoliFinder loliFinder) {
    }

    private void initGhost() {
        add(new AimAssist());
        add(new AntiBotPlus());
        add(new AutoBlock());
        add(new AutoClicker());
        add(new AutoSoup());
        add(new FastUse());
        add(new Reach());
        add(new WTap());
        add(new Ninja());
    }

    private void initMovement() {
        add(new NoFallPlus());
        add(new Strafe());
        add(new VulcanJesus());
        add(new Clip());
        add(new AirJump());
        add(new AirWalk());
        add(new Anchor());
        add(new AnchorPlus());
        add(new AntiAFK());
        add(new AntiJebus());
        add(new AntiLevitation());
        add(new AntiVoid());
        add(new AutoClip());
        //add(new AspectOfTheEnd());
        add(new AutoJump());
        add(new AutoMLG());
        add(new AutoWalk());
        add(new Blink());
        add(new BoatFly());
        add(new BoatPhase());
        add(new ChorusExploit());
        add(new ClickTP());
        //add(new DepthStriderSpoof());
        add(new ElytraBoost());
        add(new ElytraFly());
        add(new ElytraFlyRecoded());
        add(new HypnoticFly());
        add(new TensorFly());
        add(new EntityControl());
        add(new EntityFly());
        add(new EntitySpeed());
        add(new FastClimb());
        add(new FastFall());
        add(new NoVulcan());
        add(new Flight());
        add(new Glide());
        add(new GodBridge());
        add(new Gravity());
        add(new GUIMove());
        add(new HighJump());
        //add(new HoleSneak());
        add(new Jesus());
        add(new JetPack());
        add(new JetPackPlus());
        add(new LongJump());
        add(new Moses());
        add(new NoFall());
        add(new NoSlow());
        add(new NoJumpCooldown());
        add(new PacketFly());
        add(new Parkour());
        add(new Phase());
        add(new Prone());
        add(new ReverseStep());
        add(new RubberbandFly());
        add(new SafeWalk());
        add(new SafeWalk());
        add(new RoboWalk());
        add(new Scaffold());
        add(new Slippy());
        add(new Sneak());
        add(new Speed());
        //       add(new StrafePlus);
        add(new Spider());
        add(new Sprint());
        add(new Step());
        add(new VulcanFly());
        add(new SwimSpeed());
        add(new TridentBoost());
        add(new Velocity());
        add(new WurstGlide());
        add(new ScaffoldPlus()); //prob broken
        add(new AutoPush());
    }

    private void initFun() {
        add(new BrokenPlayer());
        add(new CrazyCape());
        add(new FakeExplosion());
        add(new Twerk());
        add(new PacketPitch());
        add(new PacketBodyYaw());
        add(new PacketHeadYaw());
        add(new AlwaysSwinging());
        add(new Spinny());
    }

    private void initRender() {
        add(new Animations());
        add(new AntiSale());
        add(new NoVisualCooldown());
        //add(new AntiScreen());
        add(new Background());
        add(new CapesModule());
        add(new BetterTooltips());
        add(new Australia());
        //add(new BetterVisuals());
        add(new BlockSelection());
        add(new BossStack());
        add(new Breadcrumbs());
        add(new BreakIndicators());
        add(new CameraTweaks());
        add(new Chams());
        add(new CityESP());
        add(new Confetti());
        //add(new CustomCrosshair());
        add(new EntityOwner());
        add(new ESP());
        add(new Freecam());
        add(new FreeLook());
        add(new Fullbright());
        add(new HandView());
        add(new HoleESP());
        add(new InstantSneak());
        add(new InteractionMenu());
        add(new ItemHighlight());
        add(new ItemPhysics());
        add(new LightOverlay());
        add(new LogoutSpots());
        add(new Marker());
        add(new MountHUD());
        add(new Nametags());
        add(new NewChunks());
        add(new NewerNewChunks());
        add(new NoBob());
        //add(new NoCaveCulling());
        add(new NoSwang());
        add(new NoobDetector());
        add(new NoRender());
        //add(new PenisESP());
        add(new PopChams());
        add(new Rendering());
        add(new RideStats());
        add(new Search());
        add(new SkeletonESP());
        add(new SmallFire());
        //add(new SmoothChunks());
        add(new StorageESP());
        add(new TimeChanger());
        add(new Tracers());
        add(new Trail());
        add(new Trajectories());
        add(new TunnelESP());
        add(new UnfocusedCPU());
        add(new VoidESP());
        add(new WallHack());
        add(new Xray());
        add(new Zoom());
        add(new ScoreboardReplace());
    }

    private void initWorld() {
        add(new AirPlace());
        add(new HighwayBuilderPlus());
        add(new AntiSculkSensor());
        add(new Ambience());
        add(new AntiCactus());
        add(new AntiGhostBlock());
        add(new AutoBreed());
        add(new AutoBrewer());
        add(new AutoExtinguish());
        add(new AutoFarm());
//        add(new AutoGrind());
        add(new AutoMount());
        add(new AutoNametag());
        add(new AutoShearer());
        add(new AutoSign());
        add(new AutoSmelter());
        add(new AutoWither());
        add(new AntiCrash());
        add(new BuildHeight());
        add(new ColorSigns());
        add(new EChestFarmer());
        add(new EndermanLook());
        add(new Flamethrower());
        //add(new Fucker());
        add(new HighwayBuilder());
        add(new InfinityMiner());
        add(new PacketMine());
        add(new InstaMine());
        add(new ItemSucker());
        add(new LiquidFiller());
        add(new Lavacast());
        add(new MountBypass());
        add(new RedstoneNuker());
        add(new Nuker());
        add(new NoStrip());
        add(new SpawnProofer());
        add(new StashFinder());
        add(new SuperInstaMine());
        add(new Timer());
        add(new TreeAura());
        add(new VeinMiner());
        add(new VoiderPlus());
        add(new AirstrikePlus());
        add(new HandOfGod());
        add(new WorldBorderCrash());
    }

    private void initChat() {
        add(new Announcer());
        add(new ArmorNotifier());
        add(new AutoCope());
        add(new AutoEZ());
        add(new AutoL());
        add(new AutoLogin());
        add(new BetterChat());
        add(new BurrowNotifier());
        //add(new ChatBot());
        add(new ChatEncryption());
        add(new GroupChat());
        add(new MessageAura());
        add(new Roast());
        add(new Spam());
        add(new StayHydrated());
        add(new TotemNotifier());
        add(new VisualRange());
        add(new Welcomer());
    }

    private void initMisc() {
        add(new AutoNoKBJump());
        add(new Wolfer());
        add(new VillagerRoller());
        add(new AntiDesync());
        add(new AntiPacketKick());
        add(new AntiAim());
        add(new AutoGG());
        add(new AutoMolest());
        add(new AutoMountBypassDupe());
        add(new AutoReconnect());
        add(new AutoRespawn());
        add(new BetterTab());
        add(new BookBot());
        add(new Beyblade());
        add(new CoordinateLogger());
        //add(new DmSpam);
        add(new InventoryTweaks());
        //add(new LitematicaPrinter());
        add(new MiddleClickExtra());
        add(new MultiTask());
        add(new NameProtect());
        add(new NoSignatures());
        add(new Notebot());
        add(new PacketCanceller());
        add(new PacketSpammer());
        add(new PingSpoof());
        add(new ResourcePackSpoof());
        add(new SoundBlocker());
        add(new SoundLocator());
        add(new SpinBot());
        add(new TPSSync());
        add(new VanillaSpoof());
        add(new NoPauseOnLostFocus());

    }

    private void initClient() {
        add(new BaritoneTweaks());
        add(new ClientSpoof());
        add(new FakePlayer());
        add(new MiddleClickFriend());
        add(new Swarm());
    }

    private void initExperimental() {
        add(new MatrixElytra());
        add(new TestModule());
        add(new AntiWall());
        add(new Bot());
        add(new BoomPlus());
        add(new Disabler());
        //add(new murderalert());
        add(new ExplosionAura());
        add(new ToroDupe());
        add(new ShulkerDupe());
        add(new NewVelocity());
        add(new ResetVL());
        add(new PacketLogger());
        add(new PenisEsp());
        add(new ServerOpNuke());
        add(new SecretClose());
        add(new SuperPanic());
        //add(new EntityAlert());
        add(new Sudoku());
    }

    private void initCrash() {
        add(new AdvancedCrash());
        add(new ArmorStandCrash());
        add(new BookCrash());
        add(new BungeeCrash());
        add(new CraftingCrash());
        add(new CreativeCrash());
        add(new EntityCrash());
        add(new ExceptionCrash());
        add(new JigSawCrash());
        add(new LagMessage());
        add(new LecternCrash());
        add(new MovementCrash());
        add(new NullExceptionCrash());
        add(new PlayerCrash());
        add(new PositionCrash());
        add(new StorageCrash());
        add(new SwingCrash());
        add(new TradeCrash());
        add(new UDPFlood());
        add(new VehicleCrash());
        add(new WorldBorderCrash());
    }

    public static class ModuleRegistry extends SimpleRegistry<Module> {
        public ModuleRegistry() {
            super(RegistryKey.ofRegistry(new Identifier("mathax", "modules")), Lifecycle.stable());
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
        public void clearTags() {

        }

        @Override
        public void populateTags(Map<TagKey<Module>, List<RegistryEntry<Module>>> tagEntries) {

        }

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
