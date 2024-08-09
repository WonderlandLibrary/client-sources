package meteordevelopment.meteorclient.systems.modules.combat;

import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.player.FindItemResult;
import meteordevelopment.meteorclient.utils.player.InvUtils;
import meteordevelopment.meteorclient.utils.player.Rotations;
import meteordevelopment.meteorclient.utils.world.BlockUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;

public final class Extinguish extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Boolean> clientsideRots = sgGeneral.add(new BoolSetting.Builder()
        .name("clientside rots")
        .description("does rotations clientside")
        .defaultValue(true)
        .build()
    );




    public Extinguish() {
        super(Categories.Combat, "Extinguish", "Extinguishes fire using a water bucket.");
    }

    private boolean isPlaced;
    private BlockPos blockPos;

    @Override
    public void onActivate() {
        isPlaced = false;
    }

    // DONE: finding water bucket
    // DONE: Switching to item
    // DONE: placing block
    // TODO: Powdered snow
    // TODO: rotations

    @EventHandler
    private void onTick(final TickEvent.Pre e) {
        if (mc.player.isOnFire() && !isPlaced) {
            int oldSlot = mc.player.getInventory().selectedSlot;
            InvUtils.swap(findWaterOrPowderedSnowInt(), false);
            blockPos = mc.player.getBlockPos();
            BlockUtils.place(mc.player.getBlockPos(), findWaterOrPowderedSnow(), 0, false);
            InvUtils.swap(oldSlot, false);
            isPlaced = true;
        }

        if (isPlaced && !mc.player.isOnFire()) {
            useItem(InvUtils.findInHotbar(Items.BUCKET), this.isPlaced, blockPos, true);
        }

    }

    @EventHandler
    private void onTickPost(final TickEvent.Post e) {
        isPlaced = false;
    }

    private FindItemResult findWaterOrPowderedSnow() {
        return InvUtils.find(Items.WATER_BUCKET);
    }

    private int findWaterOrPowderedSnowInt() {
        FindItemResult waterBucket = InvUtils.find(Items.WATER_BUCKET);
        // FindItemResult powderedSnow = InvUtils.find(Items.POWDER_SNOW_BUCKET);

        // if (waterBucket == null) return powderedSnow.slot();
        return waterBucket.slot();
    }

    private void useItem(FindItemResult item, boolean isPlaced, BlockPos blockPos, boolean interactItem) {
        if (!item.found()) return;

        if (interactItem) {
            Rotations.rotate(Rotations.getYaw(blockPos), Rotations.getPitch(blockPos), 10, clientsideRots.get(), () -> {
                if (item.isOffhand()) {
                    mc.interactionManager.interactItem(mc.player, Hand.OFF_HAND);
                } else {
                    InvUtils.swap(item.slot(), true);
                    mc.interactionManager.interactItem(mc.player, Hand.MAIN_HAND);
                    InvUtils.swapBack();
                }
            });
        } else {
            BlockUtils.place(blockPos, item, BlockUtils.RotationPacket.Vanilla, 10, true);
        }
    }
}
