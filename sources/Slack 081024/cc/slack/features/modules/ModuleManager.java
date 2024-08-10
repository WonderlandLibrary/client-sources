// Slack Client (discord.gg/slackclient)

package cc.slack.features.modules;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.impl.combat.*;
import cc.slack.features.modules.impl.exploit.*;
import cc.slack.features.modules.impl.ghost.*;
import cc.slack.features.modules.impl.movement.*;
import cc.slack.features.modules.impl.other.*;
import cc.slack.features.modules.impl.player.*;
import cc.slack.features.modules.impl.render.*;
import cc.slack.features.modules.impl.utilties.*;
import cc.slack.features.modules.impl.world.*;

public class ModuleManager {
    public final Map<Class<? extends Module>, Module> modules = new LinkedHashMap<>();
    public final Map<Class<? extends Module>, Module> draggable = new LinkedHashMap<>();

    public void initialize() {
        try {
            addModules(
                    // Combat
                    new AntiFireball(),
                    new Criticals(),
                    new Hitbox(),
                    new KillAura(),
                    new TickBase(),
                    new Velocity(),

                    // Exploit
                    new ChatBypass(),
                    new ClientSpoofer(),
                    new Disabler(),
                    new FastBow(),
                    new MultiAction(),
                    new Phase(),
                    new PingSpoof(),
                    new RageQuit(),
                    new Regen(),

                    // Ghost
                    new AimAssist(),
                    new AutoTool(),
                    new Autoclicker(),
                    new Backtrack(),
                    new JumpReset(),
                    new KeepSprint(),
                    new LegitNofall(),
                    new LegitScaffold(),
                    new PearlAntiVoid(),
                    new Reach(),
                    new RealLag(),
                    new SilentHitbox(),
                    new Wtap(),

                    // Movement
                    new Flight(),
                    new Glide(),
                    new InvMove(),
                    new Jesus(),
                    new LongJump(),
                    new NoSlow(),
                    new NoWeb(),
                    new SafeWalk(),
                    new Speed(),
                    new Spider(),
                    new Sprint(),
                    new Step(),
                    new Strafe(),
                    new TargetStrafe(),
                    new VClip(),

                    // Other
                    new AntiBot(),
                    new FakePlayer(),
                    new Killsults(),
                    new Performance(),
                    new RemoveEffect(),
                    new RichPresence(),
                    new Targets(),
                    new Test(), // dev test shit
                    new Tweaks(),
                    new Warns(),

                    // Player
                    new AntiVoid(),
                    new Blink(),
                    new FastEat(),
                    new FreeCam(),
                    new FreeLook(),
                    new MCF(),
                    new NoFall(),
                    new TimerModule(),

                    // Render
                    new Ambience(),
                    new Animations(),
                    new BasicESP(),
                    new BedESP(),
                    new BlockOverlay(),
                    new Bobbing(),
                    new Camera(),
                    new Cape(),
                    new Chams(),
                    new ChinaHat(),
                    new ClickGUI(),
                    new Cosmetics(),
                    new ChestESP(),
                    new ESP(),
                    new FPSCounter(),
                    new ItemPhysics(),
                    new Interface(),
                    new NameTags(),
                    new PointerESP(),
                    new Projectiles(),
                    new Radar(),
                    new ScoreboardModule(),
                    new SessionInfo(),
                    new TargetHUD(),
                    new Tracers(),
                    new XRay(),
                    new Zoom(),
                    new XYZCounter(),
                    new KeyStrokes(),
                    new BPSCounter(),

                    // Utilities
                    new AutoCrafter(),
                    new AutoDisable(),
                    new AutoGapple(),
                    new AutoGG(),
                    new AutoLogin(),
                    new AutoPlay(),
                    new AutoPot(),
                    new AutoRespawn(),
                    new AutoRod(),// new CustomESP(), // Need Recode
                    new AutoSword(),
                    new AntiHarm(),
                    new AntiStaff(),
                    new LagbackChecker(),
                    new NameProtect(),
                    new TNTHelper(),

                    // World
                    new Breaker(),
                    new ChestAura(),
                    new FastPlace(),
                    new InvManager(),
                    new Scaffold(),
                    new SpeedMine(),
                    new Stealer()
            );

            for(Module m : modules.values()) {
                draggable.put(m.getClass(), m);
            }

        } catch (Exception e) {
            // Shut Up Exception
        }
    }

    public List<Module> getModules() {
        return new ArrayList<>(modules.values());
    }

    public List<Module> getDraggable() {
        return new ArrayList<>(draggable.values());
    }

    public <T extends Module> T getInstance(Class<T> clazz) {
        return (T) modules.get(clazz);
    }

    public Module getModuleByName(String name) {
        for (Module mod : modules.values()) {
            if (mod.getName().equalsIgnoreCase(name))
                return mod;
        }


        throw new IllegalArgumentException("Module not found.");
    }

    private void addModules(Module... mod) {
        for (Module m : mod) {

            modules.put(m.getClass(), m);
        }
    }

    public Module[] getModulesByCategory(Category cat) {
        ArrayList<Module> category = new ArrayList<>();

        modules.forEach((aClass, mod) -> {
            if (mod.category == cat)
                category.add(mod);
        });

        return category.toArray(new Module[0]);
    }
}
