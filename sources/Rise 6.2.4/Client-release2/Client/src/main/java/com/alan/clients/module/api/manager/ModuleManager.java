package com.alan.clients.module.api.manager;

import com.alan.clients.Client;
import com.alan.clients.module.Module;
import com.alan.clients.module.impl.combat.*;
import com.alan.clients.module.impl.exploit.*;
import com.alan.clients.module.impl.ghost.*;
import com.alan.clients.module.impl.movement.*;
import com.alan.clients.module.impl.other.*;
import com.alan.clients.module.impl.player.*;
import com.alan.clients.module.impl.render.*;
import com.alan.clients.module.impl.render.chat.Chat;
import com.alan.clients.util.AdaptiveMap;

import java.util.ArrayList;
import java.util.Arrays;
public final class ModuleManager {

    private AdaptiveMap<Class<Module>, Module> moduleMap = new AdaptiveMap<>();

    /**
     * Called on client start
     */
    public void init() {
        moduleMap = new AdaptiveMap<>();

        // Combat
        this.put(AntiBot.class, new AntiBot());
        this.put(ComboOneHit.class, new ComboOneHit());
        this.put(Criticals.class, new Criticals());
        this.put(Fences.class, new Fences());
        this.put(KillAura.class, new KillAura());

        this.put(LegitReach.class, new LegitReach());
        this.put(Regen.class, new Regen());
        this.put(TeleportAura.class, new TeleportAura());
        this.put(TickBase.class, new TickBase());
        this.put(Velocity.class, new Velocity());
        this.put(WTap.class, new WTap());
        this.put(KeepRange.class, new KeepRange());

        // Exploit
        this.put(ConsoleSpammer.class, new ConsoleSpammer());
        this.put(Crasher.class, new Crasher());
        this.put(Disabler.class, new Disabler());
        this.put(GodMode.class, new GodMode());
        this.put(LightningTracker.class, new LightningTracker());
        this.put(NoRotate.class, new NoRotate());
        this.put(PingSpoof.class, new PingSpoof());
        this.put(StaffDetector.class, new StaffDetector());

        // Ghost
        this.put(AimAssist.class, new AimAssist());
        this.put(AimBacktrack.class, new AimBacktrack());
        this.put(AutoClicker.class, new AutoClicker());
        this.put(ClickAssist.class, new ClickAssist());
        this.put(Eagle.class, new Eagle());
        this.put(FastPlace.class, new FastPlace());
        this.put(GuiClicker.class, new GuiClicker());
        this.put(HitBox.class, new HitBox());
        this.put(KeepSprint.class, new KeepSprint());
        this.put(NoClickDelay.class, new NoClickDelay());
        this.put(Reach.class, new Reach());
        this.put(SafeWalk.class, new SafeWalk());

        // Movement
        this.put(Flight.class, new Flight());
        this.put(InventoryMove.class, new InventoryMove());
        this.put(Jesus.class, new Jesus());
        this.put(LongJump.class, new LongJump());
        this.put(NoClip.class, new NoClip());
        this.put(NoSlow.class, new NoSlow());
        this.put(Phase.class, new Phase());
        this.put(PotionExtender.class, new PotionExtender());
        this.put(Sneak.class, new Sneak());
        this.put(Speed.class, new Speed());
        this.put(Sprint.class, new Sprint());
        this.put(Step.class, new Step());
        this.put(Strafe.class, new Strafe());
        this.put(Stuck.class, new Stuck());
        this.put(TargetStrafe.class, new TargetStrafe());
        this.put(Teleport.class, new Teleport());
        this.put(WallClimb.class, new WallClimb());
        this.put(TerrainSpeed.class, new TerrainSpeed());
        this.put(NoJumpDelay.class, new NoJumpDelay());

        // Other

        this.put(Sampler.class, new Sampler());
        this.put(AntiAFK.class, new AntiAFK());
        this.put(AutoGG.class, new AutoGG());
        this.put(AutoGroomer.class, new AutoGroomer());
        this.put(ClickSounds.class, new ClickSounds());
        this.put(ClientSpoofer.class, new ClientSpoofer());
        this.put(Debugger.class, new Debugger());
        this.put(AutoPlay.class, new AutoPlay());
        this.put(Insults.class, new Insults());
        this.put(MurderMystery.class, new MurderMystery());
        this.put(NoGuiClose.class, new NoGuiClose());
        this.put(Nuker.class, new Nuker());
        this.put(PlayerNotifier.class, new PlayerNotifier());
        this.put(AntiCrash.class, new AntiCrash());
        this.put(Spammer.class, new Spammer());
        this.put(Spotify.class, new Spotify());
        this.put(Test.class, new Test());
        this.put(Timer.class, new Timer());
        this.put(Translator.class, new Translator());
        this.put(BedWarsUtils.class, new BedWarsUtils());
      //  this.put(ChatBypass.class, new ChatBypass());

        // Player
        this.put(AntiFireBall.class, new AntiFireBall());
        this.put(AntiSuffocate.class, new AntiSuffocate());
        this.put(AntiVoid.class, new AntiVoid());
        this.put(AutoHead.class, new AutoHead());
        this.put(AutoPot.class, new AutoPot());
        this.put(AutoTool.class, new AutoTool());
        this.put(Blink.class, new Blink());
        this.put(Breaker.class, new Breaker());
        this.put(Clutch.class, new Clutch());
        this.put(FastBreak.class, new FastBreak());
        this.put(FastUse.class, new FastUse());
        this.put(NoFall.class, new NoFall());
        this.put(InventorySync.class, new InventorySync());
        this.put(Manager.class, new Manager());
        this.put(NoFall.class, new NoFall());
        this.put(Scaffold.class, new Scaffold());
        this.put(Stealer.class, new Stealer());
        this.put(Twerk.class, new Twerk());
        this.put(PolarDetector.class, new PolarDetector());

        // Render
        this.put(Ambience.class, new Ambience());
        this.put(Animations.class, new Animations());
        this.put(Chat.class, new Chat());
        this.put(AppleSkin.class, new AppleSkin());
        this.put(BPSCounter.class, new BPSCounter());
        this.put(ChestESP.class, new ChestESP());
        this.put(ClickGUI.class, new ClickGUI());
        this.put(CPSCounter.class, new CPSCounter());
        this.put(FPSCounter.class, new FPSCounter());
        this.put(FreeCam.class, new FreeCam());
        this.put(FreeLook.class, new FreeLook());
        this.put(FullBright.class, new FullBright());
        this.put(Glint.class, new Glint());
        this.put(HotBar.class, new HotBar());
        this.put(HurtCamera.class, new HurtCamera());
        this.put(HurtColor.class, new HurtColor());
        this.put(Interface.class, new Interface());
        this.put(ItemPhysics.class, new ItemPhysics());
        this.put(KeyStrokes.class, new KeyStrokes());
        this.put(KillEffect.class, new KillEffect());
        this.put(NameTags.class, new NameTags());
        this.put(NoCameraClip.class, new NoCameraClip());
        this.put(Particles.class, new Particles());
        this.put(ProjectionESP.class, new ProjectionESP());
        this.put(ESP.class, new ESP());
        this.put(ScoreBoard.class, new ScoreBoard());
        this.put(Streamer.class, new Streamer());
        this.put(TargetInfo.class, new TargetInfo());
        this.put(Tracers.class, new Tracers());
        this.put(UnlimitedChat.class, new UnlimitedChat());
        this.put(ViewBobbing.class, new ViewBobbing());
        this.put(SessionStats.class, new SessionStats());
        this.put(IRC.class, new IRC());
        this.put(BreadCrumbs.class, new BreadCrumbs());
        this.put(JumpCirclesModule.class, new JumpCirclesModule());

        // Automatic initializations
        this.getAll().stream().filter(module -> module.getModuleInfo().autoEnabled()).forEach(module -> module.setEnabled(true));

        // Has to be a listener to handle the key presses
        Client.INSTANCE.getEventBus().register(this);
    }

    public ArrayList<Module> getAll() {
        return this.moduleMap.values();
    }

    public <T extends Module> T get(final Class<T> clazz) {
        return (T) this.moduleMap.get(clazz);
    }

    public <T extends Module> T get(final String name) {
        // noinspection unchecked
        return (T) this.getAll().stream()
                .filter(module -> Arrays.stream(module.getAliases()).anyMatch(alias ->
                        alias.replace(" ", "")
                                .equalsIgnoreCase(name.replace(" ", ""))))
                .findAny().orElse(null);
    }

    public void put(Class clazz, Module module) {
        this.moduleMap.put(clazz, module);
    }

    public void remove(Module key) {
        this.moduleMap.removeValue(key);
        this.updateArraylistCache();
    }

    public boolean add(final Module module) {
        this.moduleMap.put(module);
        this.updateArraylistCache();

        return true;
    }

    private void updateArraylistCache() {
        final Interface interfaceModule = this.get(Interface.class);

        if (interfaceModule == null) {
            return;
        }

        interfaceModule.createArrayList();
    }
}