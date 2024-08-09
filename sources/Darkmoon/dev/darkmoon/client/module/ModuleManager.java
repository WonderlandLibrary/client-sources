package dev.darkmoon.client.module;

import dev.darkmoon.client.module.impl.combat.*;
import dev.darkmoon.client.module.impl.movement.*;
import dev.darkmoon.client.module.impl.player.*;
import dev.darkmoon.client.module.impl.render.*;
import dev.darkmoon.client.module.impl.util.*;
import dev.darkmoon.client.module.impl.util.Optimize;
import dev.darkmoon.client.utility.render.font.Fonts;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ModuleManager {
    public CopyOnWriteArrayList<Module> modules = new CopyOnWriteArrayList<>();
    public Timer timer;
    public Optimize optimize;
    public BlurContainer blurContainer;
    public Hud hud;

    public ModuleManager() {
        // Combat
        modules.add(new AntiBot());
        modules.add(new AutoExplosion());
        modules.add(new KillAura());
        modules.add(new TriggerBot());
        modules.add(new AutoPotion());
        modules.add(new AutoTotem());
        modules.add(new FastBow());
        modules.add(new HitBox());
        modules.add(new SuperBow());
        modules.add(new TargetStrafe());
        modules.add(new Velocity());

        // Movement
        modules.add(new Sprint());
        modules.add(new AirJump());
        modules.add(new NoSlow());
        modules.add(new SunriseFirework());
        modules.add(new DamageSpeed());
        modules.add(new HighJump());
        modules.add(new ElytraFirework());
        modules.add(new SunriseFly());
        modules.add(new Flight());
        modules.add(new Strafe());
        modules.add(new Spider());
        modules.add(timer = new Timer());

        // Render
        modules.add(new Arraylist());
        modules.add(new TargetESP());
        modules.add(new Particle());
        modules.add(hud = new Hud());
        modules.add(new ClickGuiModule());
        modules.add(new ChunkAnimator());
        modules.add(new Trails());
        modules.add(new ChinaHat());
        modules.add(new CustomFog());
        modules.add(new FullBright());
        modules.add(new JumpCircle());
        modules.add(new Predict());
        modules.add(new Glint());
        modules.add(new KillEffect());
        modules.add(new WaterMark());
        modules.add(new TargetHud());
        modules.add(new Arrows());
        modules.add(new WorldTime());
        modules.add(blurContainer = new BlurContainer());
        modules.add(new ViewModel());
        modules.add(new ItemAnimate());
        modules.add(new Keybinds());
        modules.add(new NameTags());
        modules.add(new EntityESP());
        modules.add(new ItemESP());
        modules.add(new CameraClip());
        modules.add(new StaffList());
        modules.add(new Tracers());
        modules.add(new Crosshair());
        modules.add(new Notifications());
        modules.add(new NoRender());
        // Player

        modules.add(new AntiAFK());
        modules.add(new AutoArmor());
        modules.add(new AutoEat());
        modules.add(new AutoRespawn());
        modules.add(new AutoTPAccept());
        modules.add(new AutoTool());
        modules.add(new NoPush());
        modules.add(new NoDelay());
        modules.add(new NoRotate());
        modules.add(new SwapFix());
        modules.add(new AirStealler());
        modules.add(new Keystrokes());
        modules.add(new InventoryMove());
        modules.add(new MultiAction());
        modules.add(new ElytraSwap());
        modules.add(new ClickPearl());
        modules.add(new ClickFriend());
        modules.add(new ItemScroller());
        modules.add(new XCarry());
        modules.add(new NoInteract());
        modules.add(new NoClip());
        modules.add(new FreeCam());

        // Util

        modules.add(new NameProtect());
        modules.add(new FastWorld());
        modules.add(new CPUStabilize());
        modules.add(new EntityPotion());
        modules.add(new BetterChat());
        modules.add(new SendCoordinate());
        modules.add(new BetterTab());
        modules.add(new LightningDetect());
        modules.add(new TPSSync());
        modules.add(new ClientSound());
        modules.add(optimize = new Optimize());
        modules.add(new DiscordRPC());
        modules.add(new PearlLogger());
        modules.add(new PingSpoof());
        modules.add(new Physic());
        modules.add(new PearlThrowBlock());
        modules.add(new HitSounds());
        modules.add(new TeleportBack());
        modules.add(new DeathCoordinates());
        modules.add(new PasswordHider());
        modules.add(new ElytraFix());
        modules.add(new ElytraFix2());
        modules.sort(Comparator.comparingInt(m -> Fonts.mntsb12.getStringWidth(((Module) m).name)).reversed());
    }

    public void registerModule(Module module) {
        modules.add(module);
    }

    public List<Module> getModules() {
        return modules;
    }

    public Module[] getModulesFromCategory(Category category) {
        return modules.stream().filter(module -> module.category == category).toArray(Module[]::new);
    }

    public Module getModule(Class<? extends Module> classModule) {
        for (Module module : modules) {
            if (module != null && module.getClass() == classModule) {
                return module;
            }
        }
        return null;
    }

    public Module getModule(String name) {
        for (Module module : modules) {
            if (module != null && module.getName().equalsIgnoreCase(name)) {
                return module;
            }
        }
        return null;
    }
}
