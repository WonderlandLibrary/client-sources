package net.shoreline.client.api.manager;

import net.shoreline.client.Shoreline;
import net.shoreline.client.api.module.Module;
import net.shoreline.client.impl.module.client.ClickGuiModule;
import net.shoreline.client.impl.module.client.ColorsModule;
import net.shoreline.client.impl.module.client.HUDModule;
import net.shoreline.client.impl.module.client.RotationsModule;
import net.shoreline.client.impl.module.combat.*;
import net.shoreline.client.impl.module.exploit.*;
import net.shoreline.client.impl.module.misc.*;
import net.shoreline.client.impl.module.movement.*;
import net.shoreline.client.impl.module.render.*;
import net.shoreline.client.impl.module.world.*;

import java.util.*;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
public class ModuleManager
{
    // The client module register. Keeps a list of modules and their ids for
    // easy retrieval by id.
    private final Map<String, Module> modules =
            Collections.synchronizedMap(new LinkedHashMap<>());

    /**
     * Initializes the module register.
     */
    public ModuleManager()
    {
        // MAINTAIN ALPHABETICAL ORDER
        register(
                // Client
                new ClickGuiModule(),
                new ColorsModule(),
                new HUDModule(),
                new RotationsModule(),
                // Combat
                new AntiBotsModule(),
                new AuraModule(),
                new AutoArmorModule(),
                new AutoBowReleaseModule(),
                new AutoCrystalModule(),
                new AutoLogModule(),
                new AutoPotModule(),
                new AutoTotemModule(),
                new BlockLagModule(),
                new BowAimModule(),
                new CriticalsModule(),
                // new HoleFillModule(),
                new NoHitDelayModule(),
                new ReplenishModule(),
                // new SelfBowModule(),
                // new SelfTrapModule(),
                new SurroundModule(),
                new TriggerModule(),
                // Exploit
                new AntiHungerModule(),
                new ChorusControlModule(),
                new ClickTPModule(),
                new ColorSignsModule(),
                new CrasherModule(),
                new FakeLatencyModule(),
                new FastProjectileModule(),
                new NoMineAnimationModule(),
                new PacketCancelerModule(),
                new PacketFlyModule(),
                // new PhaseModule(),
                new PortalGodModeModule(),
                new ReachModule(),
                new SwingModule(),
                // Misc
                new AnnouncerModule(),
                // new AntiAFKModule(),
                new AntiAimModule(),
                new AntiBookBanModule(),
                new AntiSpamModule(),
                new AntiVanishModule(),
                new AutoAcceptModule(),
                new AutoEatModule(),
                new AutoFishModule(),
                new AutoMountModule(),
                new AutoReconnectModule(),
                new AutoRespawnModule(),
                new BeaconSelectorModule(),
                new ChestAuraModule(),
                // new ChestStealerModule(),
                new FakePlayerModule(),
                new InvCleanerModule(),
                new MiddleClickModule(),
                new NoSoundLagModule(),
                new SkinBlinkModule(),
                new TimerModule(),
                new TrueDurabilityModule(),
                new UnfocusedFPSModule(),
                new XCarryModule(),
                // Movement
                new AntiLevitationModule(),
                new AutoWalkModule(),
                // new ElytraFlyModule(),
                new EntityControlModule(),
                new EntitySpeedModule(),
                // new FakeLagModule(),
                new FastFallModule(),
                new FlightModule(),
                new HighJumpModule(),
                new IceSpeedModule(),
                new JesusModule(),
                new LongJumpModule(),
                new NoFallModule(),
                new NoSlowModule(),
                new ParkourModule(),
                new SpeedModule(),
                new SprintModule(),
                new StepModule(),
                new TickShiftModule(),
                new VelocityModule(),
                new YawModule(),
                // Render
                new BlockHighlightModule(),
                new BreakHighlightModule(),
                new ChamsModule(),
                new ESPModule(),
                new ExtraTabModule(),
                // new FreecamModule(),
                new FullbrightModule(),
                new HoleESPModule(),
                new NameProtectModule(),
                new NametagsModule(),
                new NoBobModule(),
                new NoRenderModule(),
                new NoRotateModule(),
                new NoWeatherModule(),
                new SkeletonModule(),
                new SkyboxModule(),
                new TooltipsModule(),
                new TracersModule(),
                new ViewClipModule(),
                // new ViewModelModule(),
                // World
                new AntiInteractModule(),
                new AvoidModule(),
                new FastDropModule(),
                new FastPlaceModule(),
                new MultitaskModule(),
                new NoGlitchBlocksModule(),
                // new ScaffoldModule(),
                new SpeedmineModule()
                // new WallhackModule()
        );
        Shoreline.info("Registered {} modules!", modules.size());
    }

    /**
     *
     */
    public void postInit()
    {
        // TODO
    }

    /**
     *
     *
     * @param modules
     *
     * @see #register(Module)
     */
    private void register(Module... modules)
    {
        for (Module module : modules)
        {
            register(module);
        }
    }

    /**
     *
     *
     * @param module
     */
    private void register(Module module)
    {
        modules.put(module.getId(), module);
    }

    /**
     *
     *
     * @param id
     * @return
     */
    public Module getModule(String id)
    {
        return modules.get(id);
    }

    /**
     *
     *
     * @return
     */
    public List<Module> getModules()
    {
        return new ArrayList<>(modules.values());
    }
}
