/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules;

import skizzle.Client;
import skizzle.files.ProfileManager;
import skizzle.modules.Module;
import skizzle.modules.combat.Aimbot;
import skizzle.modules.combat.AntiBot;
import skizzle.modules.combat.AutoArmour;
import skizzle.modules.combat.AutoClick;
import skizzle.modules.combat.AutoRod;
import skizzle.modules.combat.AutoSword;
import skizzle.modules.combat.Criticals;
import skizzle.modules.combat.Killaura;
import skizzle.modules.combat.Target;
import skizzle.modules.combat.Velocity;
import skizzle.modules.movement.AutoJump;
import skizzle.modules.movement.AutoWalk;
import skizzle.modules.movement.FastBridge;
import skizzle.modules.movement.FastFall;
import skizzle.modules.movement.FastSneak;
import skizzle.modules.movement.Fly;
import skizzle.modules.movement.InventoryMove;
import skizzle.modules.movement.LongJump;
import skizzle.modules.movement.Phase;
import skizzle.modules.movement.SafeWalk;
import skizzle.modules.movement.Scaffold;
import skizzle.modules.movement.Speed;
import skizzle.modules.movement.Sprint;
import skizzle.modules.movement.Step;
import skizzle.modules.movement.WTap;
import skizzle.modules.other.AutoJoin;
import skizzle.modules.other.AutoModule;
import skizzle.modules.other.DisableOnLagback.DisableLagback;
import skizzle.modules.other.Disabler;
import skizzle.modules.other.DiscordRPC;
import skizzle.modules.other.Ghost;
import skizzle.modules.other.HackerDetector.HackerDetector;
import skizzle.modules.other.MiddleClickFriend;
import skizzle.modules.other.PacketCrasher;
import skizzle.modules.other.SkizzleChat;
import skizzle.modules.other.Unstuck;
import skizzle.modules.other.UserDetector;
import skizzle.modules.other.Verify;
import skizzle.modules.player.AutoGapple;
import skizzle.modules.player.AutoPot;
import skizzle.modules.player.Blink;
import skizzle.modules.player.ChestAura;
import skizzle.modules.player.ChestStealer;
import skizzle.modules.player.FastEat;
import skizzle.modules.player.InventoryManager;
import skizzle.modules.player.NoFall;
import skizzle.modules.player.NoSlow;
import skizzle.modules.player.Toxic;
import skizzle.modules.render.Animations;
import skizzle.modules.render.ArmorStats;
import skizzle.modules.render.ChestESP;
import skizzle.modules.render.Child;
import skizzle.modules.render.ESP;
import skizzle.modules.render.Freecam;
import skizzle.modules.render.Fullbright;
import skizzle.modules.render.HUDModule;
import skizzle.modules.render.Hotbar;
import skizzle.modules.render.ItemESP;
import skizzle.modules.render.Keystrokes.Keystrokes;
import skizzle.modules.render.NameProtect;
import skizzle.modules.render.NameTags;
import skizzle.modules.render.NoFire;
import skizzle.modules.render.NoHurtCam;
import skizzle.modules.render.NoScoreboard;
import skizzle.modules.render.Notifications;
import skizzle.modules.render.PacketStats.NetStats;
import skizzle.modules.render.Skeleton;
import skizzle.modules.render.TABGui;
import skizzle.modules.render.Tracers;
import skizzle.modules.render.Trajectories;
import skizzle.modules.render.UserInfo;
import skizzle.modules.render.Zoom;
import skizzle.modules.server.AntiClose;
import skizzle.modules.server.NoRotations;
import skizzle.modules.server.Time;
import skizzle.modules.world.AntiVoid;
import skizzle.modules.world.Destroyer;
import skizzle.modules.world.FastPlace;
import skizzle.modules.world.Timer;
import skizzle.scripts.ScriptManager;
import skizzle.ui.clickgui.ClickGUI;

public class ModuleManager {
    public static NoScoreboard noScoreboard;
    public static FastSneak fastSneak;
    public static NoFire noFire;
    public static ESP esp;
    public static ItemESP itemESP;
    public static ChestStealer chestStealer;
    public static AntiBot antiBot;
    public static Notifications notifications;
    public static ClickGUI clickGUI;
    public static InventoryManager invManager;
    public static NameProtect nameProtect;
    public static AutoModule autoModule;
    public static Scaffold scaffold;
    public static HackerDetector hackerDetector;
    public static Phase phase;
    public static NoHurtCam noHurt;
    public static Animations animations;
    public static DiscordRPC discord;
    public static Ghost ghostModule;
    public static UserInfo userInfo;
    public static Verify verify;
    public static Tracers tracers;
    public static Fly fly;
    public static Target targeting;
    public static InventoryMove inventoryMove;
    public static skizzle.util.Timer debugTimer;
    public static Child child;
    public static HUDModule hudModule;
    public static Killaura killaura;
    public static NameTags nameTags;
    public static SafeWalk safeWalk;
    public static LongJump longJump;
    public static AutoSword autoSword;
    public static DisableLagback disableLag;
    public static FastEat fastEat;
    public static NoSlow noSlow;
    public static ArmorStats armorStats;
    public static Speed speed;

    public ModuleManager() {
        ModuleManager Nigga;
    }

    static {
        debugTimer = new skizzle.util.Timer();
    }

    public static void getModules() {
        System.out.println(Qprot0.0("\ubd0a\u71c4\u865b\uc6c3\u3dd3\u3892\u8c28\ud123\u362c\u969f\ub2b5\uaf19\u3fb4\u136b\u49f0\u216d\u42a7\u0b0f"));
        debugTimer.reset();
        killaura = new Killaura();
        esp = new ESP();
        noHurt = new NoHurtCam();
        nameTags = new NameTags();
        ghostModule = new Ghost();
        chestStealer = new ChestStealer();
        nameProtect = new NameProtect();
        targeting = new Target();
        invManager = new InventoryManager();
        noFire = new NoFire();
        Client.hudModule = hudModule = new HUDModule();
        discord = new DiscordRPC();
        autoSword = new AutoSword();
        notifications = new Notifications();
        animations = new Animations();
        speed = new Speed();
        fastSneak = new FastSneak();
        child = new Child();
        armorStats = new ArmorStats();
        phase = new Phase();
        longJump = new LongJump();
        safeWalk = new SafeWalk();
        fly = new Fly();
        noSlow = new NoSlow();
        disableLag = new DisableLagback();
        verify = new Verify();
        itemESP = new ItemESP();
        fastEat = new FastEat();
        userInfo = new UserInfo();
        scaffold = new Scaffold();
        inventoryMove = new InventoryMove();
        autoModule = new AutoModule();
        clickGUI = new ClickGUI();
        antiBot = new AntiBot();
        hackerDetector = new HackerDetector();
        tracers = new Tracers();
        noScoreboard = new NoScoreboard();
        Client.modules.add(new Zoom());
        Client.modules.add(new Hotbar());
        Client.modules.add(noScoreboard);
        Client.modules.add(new Disabler());
        Client.modules.add(new Keystrokes());
        Client.modules.add(new Phase());
        Client.modules.add(new MiddleClickFriend());
        Client.modules.add(new Freecam());
        Client.modules.add(hackerDetector);
        Client.modules.add(tracers);
        Client.modules.add(antiBot);
        Client.modules.add(new Time());
        Client.modules.add(clickGUI);
        Client.modules.add(new AntiClose());
        Client.modules.add(new FastBridge());
        Client.modules.add(new ChestESP());
        Client.modules.add(autoModule);
        Client.modules.add(userInfo);
        Client.modules.add(itemESP);
        Client.modules.add(new AutoGapple());
        Client.modules.add(new Unstuck());
        Client.modules.add(new NoRotations());
        Client.modules.add(new AutoJoin());
        Client.modules.add(new Destroyer());
        Client.modules.add(invManager);
        Client.modules.add(new AntiVoid());
        Client.modules.add(new WTap());
        Client.modules.add(new FastPlace());
        Client.modules.add(new Timer());
        Client.modules.add(new AutoPot());
        Client.modules.add(new AutoWalk());
        Client.modules.add(new AutoJump());
        Client.modules.add(chestStealer);
        Client.modules.add(fastEat);
        Client.modules.add(new AutoClick());
        Client.modules.add(nameProtect);
        Client.modules.add(targeting);
        Client.modules.add(noFire);
        Client.modules.add(noHurt);
        Client.modules.add(new Velocity());
        Client.modules.add(notifications);
        Client.modules.add(animations);
        Client.modules.add(fastSneak);
        Client.modules.add(child);
        Client.modules.add(armorStats);
        Client.modules.add(longJump);
        Client.modules.add(new ChestAura());
        Client.modules.add(safeWalk);
        Client.modules.add(noSlow);
        Client.modules.add(scaffold);
        Client.modules.add(disableLag);
        Client.modules.add(new Verify());
        Client.modules.add(fly);
        Client.modules.add(new Sprint());
        Client.modules.add(new Fullbright());
        Client.modules.add(new NoFall());
        Client.modules.add(speed);
        Client.modules.add(new Step());
        Client.modules.add(new FastFall());
        Client.modules.add(new TABGui());
        Client.modules.add(hudModule);
        Client.modules.add(new Toxic());
        Client.modules.add(ghostModule);
        Client.modules.add(discord);
        Client.modules.add(new Aimbot());
        Client.modules.add(new AutoArmour());
        Client.modules.add(autoSword);
        Client.modules.add(esp);
        Client.modules.add(new PacketCrasher());
        Client.modules.add(inventoryMove);
        Client.modules.add(new AutoRod());
        Client.modules.add(nameTags);
        Client.modules.add(new Blink());
        Client.modules.add(new Trajectories());
        Client.modules.add(new Criticals());
        Client.modules.add(new UserDetector());
        Client.modules.add(new SkizzleChat());
        Client.modules.add(new NetStats());
        Client.modules.add(killaura);
        Client.modules.add(new Skeleton());
        System.out.println(Qprot0.0("\ubd05\u71d9\u865f\uc6c6\u3dce\u3899\u8c2b\ud123\u3620\u969c\ub2bd\uaf4c\u3fb5\u1361\u49e7\u2136\u42e5\u0b44\u7630\u22db\u44e9\u01c4\uad33") + debugTimer.getDelay() + Qprot0.0("\ubd2b\u71d8"));
        System.out.println(Qprot0.0("\ubd15\u71ce\u864e\uc6d3\u3dd3\u3892\u8c28\ud123\u3634\u9680\ub2f1\uaf1f\u3fbd\u136f\u49f1\u2120\u42e1\u0b01\u762e\u229a\u44f0\u0184\uad3d\u8901"));
        debugTimer.reset();
        ClickGUI.setupSearch();
        System.out.println(Qprot0.0("\ubd15\u71ce\u864e\uc687\u3dc9\u3899\u8c2e\ud171\u3622\u9698\ub2f1\uaf01\u3fb9\u137e\u49a3\u212a\u42e7\u0b01") + debugTimer.getDelay() + Qprot0.0("\ubd2b\u71d8"));
        if (ModuleManager.getModule(Qprot0.0("\ubd1c\u71c4\u8655\uc6ca")).isEnabled()) {
            ModuleManager.getModule(Qprot0.0("\ubd1c\u71c4\u8655\uc6ca")).toggle();
        }
        ProfileManager.updateProfiles();
        System.out.println(Qprot0.0("\ubd07\u71cf\u865e\uc6ce\u3dd4\u389b\u8c6f\ud176\u3632\u9695\ub2a3\uaf4c\u3fab\u136d\u49f1\u212a\u42f9\u0b55\u7630\u22d5\u44ae\u0184"));
        debugTimer.reset();
        ScriptManager.addScripts();
        System.out.println(Qprot0.0("\ubd07\u71cf\u865e\uc6c2\u3dde\u38dc\u8c3a\ud170\u3624\u9682\ub2f1\uaf1f\u3fbb\u137c\u49ea\u2133\u42fd\u0b52\u7663\u2292\u44ee\u018a") + debugTimer.getDelay() + Qprot0.0("\ubd2b\u71d8"));
    }

    public static void reloadModules() {
        Client.modules.clear();
        Runnable Nigga = new Runnable(){

            public static {
                throw throwable;
            }

            @Override
            public void run() {
                Client.readSettings = Client.fileManager.readSettings();
                ModuleManager.getModules();
                Client.fileManager.updateClickGUIPanels();
                Client.commandManager.commands.clear();
                Client.commandManager.setup();
            }
            {
                1 Nigga;
            }
        };
        new Thread(Nigga).start();
    }

    public static Module getModule(String Nigga) {
        for (Module Nigga2 : Client.modules) {
            if (!Nigga2.name.equals(Nigga)) continue;
            return Nigga2;
        }
        return null;
    }
}

