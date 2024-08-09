package mathax.client.systems.modules.client;

import baritone.api.BaritoneAPI;
import mathax.client.eventbus.EventHandler;
import mathax.client.events.world.TickEvent;
import mathax.client.settings.*;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.item.Items;

public class BaritoneTweaks extends Module {
    private int pausedChunkX;
    private int pausedChunkZ;

    private boolean paused;

    private final SettingGroup sgSmartSprint = settings.createGroup("Smart Sprint");
    private final SettingGroup sgPauseOnUnloaded = settings.createGroup("Pause On Unloaded");

    // Smart Sprint

    private final Setting<Boolean> smartSprintActive = sgSmartSprint.add(new BoolSetting.Builder()
        .name("active")
        .description("Sprint with enough food saturation only.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Integer> smartSprintHunger = sgSmartSprint.add(new IntSetting.Builder()
        .name("hunger")
        .description("Smart sprint minimum food saturation level.")
        .defaultValue(8)
        .sliderRange(1, 20)
        .build()
    );

    // Pause On Unloaded

    private final Setting<Boolean> pauseOnLoadedActive = sgPauseOnUnloaded.add(new BoolSetting.Builder()
        .name("active")
        .description("Pauses Baritone when attempting to enter an unloaded chunk.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Double> lookAhead = sgPauseOnUnloaded.add(new DoubleSetting.Builder()
        .name("look-ahead")
        .description("Determines how far the module should 'look ahead' for unloaded chunks.")
        .range(1, 40)
        .sliderRange(1, 40)
        .defaultValue(12)
        .build()
    );

    public BaritoneTweaks() {
        super(Categories.Misc, Items.COMMAND_BLOCK, "baritone-tweaks", "Various baritone related tweaks.");
    }

    @Override
    public boolean onActivate() {
        paused = false;
        return false;
    }

    @EventHandler
    private void onTickPost(TickEvent.Post event) {
        if (!smartSprintActive.get()) return;

        if (mc.player.getHungerManager().getFoodLevel() >= smartSprintHunger.get()) BaritoneAPI.getSettings().allowSprint.value = true;
        else BaritoneAPI.getSettings().allowSprint.value = false;
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (!pauseOnLoadedActive.get()) return;

        int chunkX = (int) ((mc.player.getX() + (mc.player.getVelocity().getX() * lookAhead.get())) / 32);
        int chunkZ = (int) ((mc.player.getZ() + (mc.player.getVelocity().getZ() * lookAhead.get())) / 32);

        if (!mc.world.getChunkManager().isChunkLoaded(chunkX, chunkZ) && BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().isPathing() && !paused) {
            BaritoneAPI.getProvider().getPrimaryBaritone().getCommandManager().execute("pause");
            info("Entering unloaded chunk, pausing Baritone...");
            paused = true;
            pausedChunkX = chunkX;
            pausedChunkZ = chunkZ;
        } else if (paused && mc.world.getChunkManager().isChunkLoaded(pausedChunkX, pausedChunkZ)) {
            BaritoneAPI.getProvider().getPrimaryBaritone().getCommandManager().execute("resume");
            info("Chunk loaded, resuming Baritone.");
            paused = false;
        }
    }
}
