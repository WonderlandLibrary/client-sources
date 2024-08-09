package dev.excellent.client.module.api;

import dev.excellent.Excellent;
import dev.excellent.api.event.impl.input.KeyboardPressEvent;
import dev.excellent.api.event.impl.input.MouseInputEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.impl.combat.*;
import dev.excellent.client.module.impl.misc.*;
import dev.excellent.client.module.impl.movement.*;
import dev.excellent.client.module.impl.player.*;
import dev.excellent.client.module.impl.render.*;
import i.gishreloaded.protection.annotation.Native;
import i.gishreloaded.protection.annotation.Protect;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class ModuleManager extends java.util.ArrayList<Module> {

    @Protect(Protect.Type.VIRTUALIZATION)
    @Native
    public void init() {
        registerAll(
                new ArrayList(),
                new AutoTpAccept(),
                new GlassHands(),
//                new Aura(),
                new HitBox(),
                new TargetStrafe(),
                new ChinaHat(),
                new BoatFly(),
                new FunTimeHelper(),
                new AutoExplosion(),
                new SlowPackets(),
                new NoPush(),
                new Baritone(),
                new Keystrokes(),
                new LeaveTracker(),
                new PearlPredict(),
                new FireFly(),
                new LittleBro(),
                new ArmorHud(),
                new ItemScroller(),
                new Notifications(),
                new BaseFinder(),
                new Kagune(),
                new AutoBuy(),
                new AntiBot(),
                new AntiAFK(),
                new AutoTotem(),
                new AutoSwap(),
                new CustomCooldown(),
                new AutoPotion(),
                new Velocity(),
                new AutoAnchor(),
                new NameProtect(),
                new StaffActivity(),
                new MiddleClickFriend(),
                new AutoAuth(),
                new AutoDuel(),
                new NoSlowDown(),
                new Timer(),
                new Flight(),
                new ElytraFly(),
                new ElytraSpeed(),
                new Strafe(),
                new InvWalk(),
                new Speed(),
                new AirJump(),
                new NoJumpDelay(),
                new FreeCam(),
                new ElytraHelper(),
                new MiddleClickPearl(),
                new FastPlace(),
                new Sprint(),
                new WorldTime(),
                new Trails(),
                new Esp(),
                new BlockOverlay(),
                new GlowEsp(),
                new HitBubbles(),
                new Hotbar(),
                new Crosshair(),
                new Particles(),
                new Fullbright(),
                new Animations(),
                new Watermark(),
                new Keybinds(),
                new MotionGraph(),
                new ActivePotions(),
                new AutoTransfer(),
                new JumpCircles(),
                new BadTrip(),
//                new TargetHud(),
                new NoRender(),
                new Arrows(),
                new Hud(),
                new FreeLook(),
                new CameraClip(),
                new SprintReset(),
                new AutoRespawn(),
                new AutoFish(),
                new TapeMouse(),
                new TNTTimer(),
                new Eagle(),
                new AutoGApple(),
                new ClickGui(),
                new NoPlayerTrace(),
                new ItemPhysic(),
                new SeeInvisibles(),
                new ChestStealer(),
                new Jesus(),
                new SRPSpoof(),
                new XCarry(),
                new NoClip(),
                new Phase(),
                new Parkour(),
                new AutoTool(),
                new BackTrack(),
                new Tracers(),
                new AutoArmor(),
                new NoInteract(),
                new AutoFix(),
                new TargetInfo(),
                new FogBlur(),
                new KillAura(),
//                new Marker(),
                new Spider()
        );
//        if (DeadCodeConstants.IDE) {
//            add(new Test());
//            add(new Marker());
//            add(new DevPanel());
//        }
        iterate();
        Excellent.getInst().getEventBus().register(this);
    }

    @Native
    private void iterate() {
        this.stream().filter(module -> module.getModuleInfo().autoEnabled()).forEach(module -> module.setEnabled(true, false));
    }

    @Native
    private void registerAll(Module... modules) {
        this.addAll(Arrays.asList(modules));
        this.updateArraylistCache();
    }

    public List<Module> getAll() {
        return new java.util.ArrayList<>(this);
    }

    public <T extends Module> T get(final String name) {
        return this.stream()
                .filter(module -> module.getDisplayName().equalsIgnoreCase(name))
                .map(module -> (T) module)
                .findAny()
                .orElse(null);
    }

    public <T extends Module> T get(final Class<T> clazz) {
        return this.stream()
                .filter(module -> module.getClass() == clazz)
                .map(module -> (T) module)
                .findAny()
                .orElse(null);
    }

    public List<Module> get(final Category category) {
        return this.stream()
                .filter(module -> module.getModuleInfo().category() == category)
                .collect(Collectors.toList());
    }


    private final Listener<KeyboardPressEvent> onKey = event -> {
        if (event.getScreen() != null) return;

        this.stream().filter(module -> module.getKeyCode() == event.getKeyCode())
                .forEach(Module::toggle);
    };
    private final Listener<MouseInputEvent> onMouse = event -> {
        this.stream().filter(module -> module.getKeyCode() == event.getMouseButton())
                .forEach(Module::toggle);
    };

    @Override
    public boolean add(final Module module) {
        final boolean result = super.add(module);
        this.updateArraylistCache();
        return result;
    }

    @Override
    public void add(final int i, final Module module) {
        super.add(i, module);
        this.updateArraylistCache();
    }

    @Override
    public Module remove(final int i) {
        final Module result = super.remove(i);
        this.updateArraylistCache();
        return result;
    }

    @Override
    public boolean remove(final Object o) {
        final boolean result = super.remove(o);
        this.updateArraylistCache();
        return result;
    }

    @Native
    private void updateArraylistCache() {
        final ArrayList arrayListModule = this.get(ArrayList.class);
        if (arrayListModule == null) return;
        arrayListModule.createModuleList();
    }
}