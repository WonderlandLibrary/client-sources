package me.Emir.Karaguc.module;

import me.Emir.Karaguc.module.combat.*;
import me.Emir.Karaguc.module.config.ConfigIngameAdderLocal;
import me.Emir.Karaguc.module.config.HypixelCfg;
import me.Emir.Karaguc.module.config.MineplexCfg;
import me.Emir.Karaguc.module.exploits.*;
import me.Emir.Karaguc.module.forgemods.*;
import me.Emir.Karaguc.module.gui.ClickGUI;
import me.Emir.Karaguc.module.gui.ClientFriend;
import me.Emir.Karaguc.module.gui.HUD;
import me.Emir.Karaguc.module.gui.IRC;
import me.Emir.Karaguc.module.misc.AutoBreaker;
import me.Emir.Karaguc.module.misc.Sneak;
import me.Emir.Karaguc.module.movement.*;
import me.Emir.Karaguc.module.player.*;
import me.Emir.Karaguc.module.render.*;
import me.Emir.Karaguc.module.world.FastPlace;
import me.Emir.Karaguc.module.world.RetarderMC;
import me.Emir.Karaguc.module.world.Scaffold;

import java.util.ArrayList;

public class ModuleManager {
    private ArrayList<Module> modules = new ArrayList<Module>();

    public ModuleManager() {
        // COMBAT
        modules.add(new AntiBot());
        modules.add(new AutoArmor());
        modules.add(new KillAura());
        modules.add(new Criticals());
        modules.add(new Velocity());
        modules.add(new SuperKnockBack());
        modules.add(new TriggerBot());

        // MOVEMENT
        modules.add(new Sprint());
        modules.add(new Fly());
        modules.add(new InventoryMove());
        modules.add(new NoSlowDown());
        modules.add(new HighJump());
        modules.add(new LongJump());
        modules.add(new Speed());
        modules.add(new Step());
        modules.add(new AutoGapple());
        modules.add(new Blink());
        modules.add(new Jesus());
        modules.add(new AirJump());
        modules.add(new Zoot());
        modules.add(new Spider());
        modules.add(new FastLadder());
        modules.add(new NameTags());
        modules.add(new AutoSplash());
        modules.add(new Dolphin());
        modules.add(new AutoFish());

        // PLAYER
        modules.add(new NoFall());
        modules.add(new Radio());
        modules.add(new ChestSteal());
        modules.add(new SwordTransitions());
        modules.add(new Spammer());
        modules.add(new InvCleaner());
        modules.add(new SmoothAimbot());
        modules.add(new AutoCommand());
        modules.add(new Tracers());
        modules.add(new NoRender());
        modules.add(new WorldTime());
        modules.add(new Timer());
        modules.add(new AntiCactus());
        modules.add(new Derp());
        modules.add(new AutoMover());
        modules.add(new NoBob());
        modules.add(new AutoSword());
        modules.add(new AutoTool());
        modules.add(new AutoEat());
        modules.add(new FastEat());
        modules.add(new ClientBot());
        modules.add(new MusicBot());
        modules.add(new PluginSearch());
        modules.add(new AutoRespawn());
        modules.add(new Crasher());
        modules.add(new Phase());
        modules.add(new Chams());
        modules.add(new NoScoreBoard());
        modules.add(new BlockOverlay());
        modules.add(new AntiInvis());
        modules.add(new Stream());
        modules.add(new AntiVoid());
        modules.add(new Trajectories());
        modules.add(new Panic());
        modules.add(new AutoBuild());
        modules.add(new Parkour());

        // RENDER
        modules.add(new ESP());
        modules.add(new Fullbright());
        modules.add(new Wings());
        modules.add(new OldAnimations());
        modules.add(new ItemPhysics());
        modules.add(new Cape());
        modules.add(new LSD());

        // World
        modules.add(new FastPlace());
        modules.add(new Scaffold());
        modules.add(new RetarderMC());

        // Gui
        modules.add(new ClickGUI());
        modules.add(new HUD());
        modules.add(new ClientFriend());
        modules.add(new IRC());

        // Exploits
        modules.add(new AirStuck());
        modules.add(new Regen());
        modules.add(new DamageParticles());
        modules.add(new Xray());
        modules.add(new Wtap());
        modules.add(new ItemTeleport());
        modules.add(new HypixelMurder());
        modules.add(new WorldEditKiller());
        modules.add(new JetPack());

        // MISC
        modules.add(new AutoBreaker());
        modules.add(new Sneak());

        // Config
        modules.add(new ConfigIngameAdderLocal());
        modules.add(new HypixelCfg());
        modules.add(new MineplexCfg());

        // ForgeMods
        modules.add(new CpsMod());
        modules.add(new TeamSpeakMod());
        modules.add(new KeystrokesMod());
        modules.add(new AutoCommand());
        modules.add(new AutoGgMod());
        modules.add(new SpotifyMod());

    }

    public ArrayList<Module> getModules() {
        return modules;
    }
    public Module getModuleByName(String name) {
        return modules.stream().filter(module -> module.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}
