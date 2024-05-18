package us.dev.direkt.module.handler;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.ImmutableClassToInstanceMap;

import us.dev.api.interfaces.Toggleable;
import us.dev.direkt.Direkt;
import us.dev.direkt.event.internal.events.system.module.EventPostModuleLoading;
import us.dev.direkt.module.Module;
import us.dev.direkt.module.internal.combat.ArmorBreaker;
import us.dev.direkt.module.internal.combat.AutoClicker;
import us.dev.direkt.module.internal.combat.AutoRespawn;
import us.dev.direkt.module.internal.combat.Criticals;
import us.dev.direkt.module.internal.combat.HitBoxes;
import us.dev.direkt.module.internal.combat.KillAura;
import us.dev.direkt.module.internal.core.AntiCrash;
import us.dev.direkt.module.internal.core.ChatCommands;
import us.dev.direkt.module.internal.core.autocheat.AutoCheat;
import us.dev.direkt.module.internal.core.friends.Friends;
import us.dev.direkt.module.internal.core.listeners.EntityPositionListener;
import us.dev.direkt.module.internal.core.listeners.HomeSetListener;
import us.dev.direkt.module.internal.core.listeners.PlayerDeathListener;
import us.dev.direkt.module.internal.core.ui.ClickGui;
import us.dev.direkt.module.internal.core.ui.InGameUI;
import us.dev.direkt.module.internal.core.ui.MainMenuUI;
import us.dev.direkt.module.internal.core.ui.ReconnectorUI;
import us.dev.direkt.module.internal.core.ui.TabUI;
import us.dev.direkt.module.internal.misc.AutoAccept;
import us.dev.direkt.module.internal.misc.DeathDerp;
import us.dev.direkt.module.internal.misc.SkinDerp;
import us.dev.direkt.module.internal.misc.decoder.WordDecode;
import us.dev.direkt.module.internal.misc.styledchat.StyledChat;
import us.dev.direkt.module.internal.movement.AntiVelocity;
import us.dev.direkt.module.internal.movement.ElytraControl;
import us.dev.direkt.module.internal.movement.Fly;
import us.dev.direkt.module.internal.movement.JetSki;
import us.dev.direkt.module.internal.movement.LiquidWalk;
import us.dev.direkt.module.internal.movement.NoPush;
import us.dev.direkt.module.internal.movement.NoSlow;
import us.dev.direkt.module.internal.movement.SafeWalk;
import us.dev.direkt.module.internal.movement.Scaffold;
import us.dev.direkt.module.internal.movement.Sprint;
import us.dev.direkt.module.internal.movement.Step;
import us.dev.direkt.module.internal.movement.Timer;
import us.dev.direkt.module.internal.movement.speed.Speed;
import us.dev.direkt.module.internal.player.AutoArmor;
import us.dev.direkt.module.internal.player.AutoTool;
import us.dev.direkt.module.internal.player.Blink;
import us.dev.direkt.module.internal.player.Freecam;
import us.dev.direkt.module.internal.player.InventoryWalk;
import us.dev.direkt.module.internal.player.MoreInventory;
import us.dev.direkt.module.internal.player.RotationFix;
import us.dev.direkt.module.internal.render.Brightness;
import us.dev.direkt.module.internal.render.CaveFinder;
import us.dev.direkt.module.internal.render.HideESP;
import us.dev.direkt.module.internal.render.NameTags;
import us.dev.direkt.module.internal.render.NoRender;
import us.dev.direkt.module.internal.render.Projectories;
import us.dev.direkt.module.internal.render.Tracers;
import us.dev.direkt.module.internal.render.Trajectories;
import us.dev.direkt.module.internal.render.WorldTracer;
import us.dev.direkt.module.internal.render.Xray;
import us.dev.direkt.module.internal.render.esp.ESP;
import us.dev.direkt.module.internal.render.waypoints.Waypoints;
import us.dev.direkt.module.internal.world.FastPlace;
import us.dev.direkt.module.internal.world.Speedmine;


/**
 * @author Foundry
 */
public final class ModuleManager {
    private static final ClassToInstanceMap<Module> moduleRegistry = new ImmutableClassToInstanceMap.Builder<Module>()
            /* Combat */
            .put(AutoClicker.class, new AutoClicker())
            .put(Criticals.class, new Criticals())
            .put(HitBoxes.class, new HitBoxes())
            .put(KillAura.class, new KillAura())
            .put(AutoRespawn.class, new AutoRespawn())
            
            /* Core */
            .put(AutoCheat.class, new AutoCheat())
            .put(Friends.class, new Friends())
            .put(MainMenuUI.class, new MainMenuUI())
            .put(TabUI.class, new TabUI())
            .put(ChatCommands.class, new ChatCommands())
            .put(InGameUI.class, new InGameUI())
            .put(ReconnectorUI.class, new ReconnectorUI())
            .put(EntityPositionListener.class, new EntityPositionListener())
            .put(PlayerDeathListener.class, new PlayerDeathListener())
            .put(AntiCrash.class, new AntiCrash())
            .put(HomeSetListener.class, new HomeSetListener())
            .put(ClickGui.class, new ClickGui())
            
            /* Misc */
            .put(StyledChat.class, new StyledChat())
            .put(InventoryWalk.class, new InventoryWalk())
            .put(WordDecode.class, new WordDecode())
            .put(SkinDerp.class, new SkinDerp())
            .put(DeathDerp.class, new DeathDerp())
            .put(AutoAccept.class, new AutoAccept())

            /* Movement */
            .put(AntiVelocity.class, new AntiVelocity())
            .put(ElytraControl.class, new ElytraControl())
            .put(Fly.class, new Fly())
            .put(LiquidWalk.class, new LiquidWalk())
            .put(NoPush.class, new NoPush())
            .put(NoSlow.class, new NoSlow())
            .put(SafeWalk.class, new SafeWalk())
            .put(Scaffold.class, new Scaffold())
            .put(Speed.class, new Speed())
            .put(Sprint.class, new Sprint())
            .put(Step.class, new Step())
            .put(Timer.class, new Timer())
            .put(JetSki.class, new JetSki())
            .put(ArmorBreaker.class, new ArmorBreaker())

            /* Player */
            .put(AutoArmor.class, new AutoArmor())
            .put(Blink.class, new Blink())
            .put(Freecam.class, new Freecam())
            .put(MoreInventory.class, new MoreInventory())
            .put(RotationFix.class, new RotationFix())
            .put(AutoTool.class, new AutoTool())

            /* Render */
            .put(Brightness.class, new Brightness())
            .put(ESP.class, new ESP())
            .put(NameTags.class, new NameTags())
            .put(Tracers.class, new Tracers())
            .put(HideESP.class, new HideESP())
            .put(Waypoints.class, new Waypoints())
            .put(NoRender.class, new NoRender())
            .put(Projectories.class, new Projectories())
            .put(WorldTracer.class, new WorldTracer())
            .put(Trajectories.class, new Trajectories())
            .put(Xray.class, new Xray())
            .put(CaveFinder.class, new CaveFinder())

            /* World */
            .put(FastPlace.class, new FastPlace())
            .put(Speedmine.class, new Speedmine())
            .build();

    public ModuleManager() {
        Direkt.getInstance().getEventManager().call(new EventPostModuleLoading());
        moduleRegistry.values().forEach(m -> {
            if (!(m instanceof Toggleable)) {
                Direkt.getInstance().getEventManager().register(m);
            }
        });
    }

    public <T extends Module> T getModule(Class<T> clazz) {
        return moduleRegistry.getInstance(clazz);
    }

    public Collection<Module> getModules() {
        return Collections.unmodifiableCollection(moduleRegistry.values());
    }

    public Optional<Module> findModule(String modName) {
        final String lookupName = modName.replaceAll(" ", "");
        for (Module m : moduleRegistry.values()) {
            if (m.getLabel().replaceAll(" ", "").equalsIgnoreCase(lookupName)) {
                return Optional.of(m);
            }
            else {
                for (String alias : m.getAliases()) {
                    if (alias.replaceAll(" ", "").equalsIgnoreCase(lookupName)) {
                        return Optional.of(m);
                    }
                }
            }
        }
        return Optional.empty();
    }

}
